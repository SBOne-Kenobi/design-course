package ui

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import com.varabyte.kotterx.decorations.BorderCharacters
import com.varabyte.kotterx.decorations.bordered
import com.varabyte.kotterx.text.Justification
import com.varabyte.kotterx.text.justified
import engine.RectShape
import entity.GameController
import entity.GameState.Death
import entity.GameState.Default
import entity.GameState.Inventory
import entity.GameState.Win
import entity.Level
import inventory.UserInventory
import ui.entities.GameMapStorage
import ui.entities.LevelRenderer
import ui.inventory.containers.ManyContainersRenderer

class GameRenderer(private val context: ConsoleContext) : ConsoleRenderer<GameController>() {

    private val paddingTop = 2

    private fun RenderScope.padding(padding: Int) {
        repeat(padding) {
            textLine()
        }
    }

    private fun RenderScope.renderGameMap(level: Level) {
        val gameMapArray = Array(context.gameMapViewHeight) {
            CharArray(context.gameMapViewWidth) { ' ' }
        }

        val viewShape = RectShape(
            context.gameMapViewWidth,
            context.gameMapViewHeight
        )
        val gameMapStorage = GameMapStorage(gameMapArray, viewShape)
        val levelRenderer = LevelRenderer(gameMapStorage)

        levelRenderer.render(level)

        justified(Justification.CENTER) {
            padding(paddingTop)
            bordered(BorderCharacters.ASCII) {
                gameMapArray.forEach {
                    textLine(String(it))
                }
            }
            textLine(" ".repeat(context.consoleWidth))
        }
    }

    private fun RenderScope.renderInventory(userInventory: UserInventory) {
        val manyContainersWithNavigation = context.getUserInventoryWithNavigation(userInventory)
        val renderer = ManyContainersRenderer(context.consoleWidth - 1, context.consoleHeight - 1)
        renderer.run {
            renderData(manyContainersWithNavigation)
        }
    }

    private fun RenderScope.renderWin() {
        justified(Justification.CENTER) {
            padding(paddingTop)
            textLine("Congratulations!")
            textLine(" ".repeat(context.consoleWidth))
            textLine("You win this game!")
        }
    }

    private fun RenderScope.renderDeath() {
        justified(Justification.CENTER) {
            padding(paddingTop)
            textLine("Failure!")
            textLine(" ".repeat(context.consoleWidth))
            textLine("You dead!")
        }
    }

    override fun RenderScope.renderData(data: GameController) {
        when (data.state) {
            Default -> renderGameMap(data.currentLevel)
            Inventory -> renderInventory(data.user.inventory)
            Death -> renderDeath()
            Win -> renderWin()
        }
    }
}