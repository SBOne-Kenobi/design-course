package ui

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import com.varabyte.kotterx.decorations.BorderCharacters
import com.varabyte.kotterx.decorations.bordered
import com.varabyte.kotterx.text.Justification
import com.varabyte.kotterx.text.justified
import engine.Position
import engine.RectShape
import entity.GameController
import entity.GameState.Death
import entity.GameState.Default
import entity.GameState.Inventory
import entity.GameState.Win
import entity.Level
import inventory.UserInventory
import ui.entities.GameMapStorage
import ui.entities.UserBasedViewNavigation
import ui.entities.renderers.LevelRenderer
import ui.inventory.containers.renderers.ManyContainersRenderer

class GameRenderer(private val context: ConsoleContext) : ConsoleRenderer<GameController>() {

    private fun RenderScope.padding(padding: Int) {
        repeat(padding) {
            textLine()
        }
    }

    private fun UserBasedViewNavigation.toViewShape(): RectShape =
        RectShape(
            context.gameMapViewWidth,
            context.gameMapViewHeight,
            viewPosition
        )

    private fun recalculateViewNavigation(viewNavigation: UserBasedViewNavigation) {
        val viewX = calculateViewPosition(
            viewNavigation.viewPosition.x,
            viewNavigation.userPosition.x,
            context.gameMapViewWidth,
            padding = context.gameMapViewWidth / 5
        )
        val viewY = calculateViewPosition(
            viewNavigation.viewPosition.y,
            viewNavigation.userPosition.y,
            context.gameMapViewHeight,
            padding = context.gameMapViewHeight / 3
        )
        viewNavigation.viewPosition = Position(viewX, viewY)
    }

    private fun RenderScope.renderGameMap(data: GameController) {
        val gameMapArray = Array(context.gameMapViewHeight) {
            CharArray(context.gameMapViewWidth) { ' ' }
        }

        val viewNavigation = context.getViewNavigation(data.user.gameObject.position)
        recalculateViewNavigation(viewNavigation)

        val viewShape = viewNavigation.toViewShape()
        val gameMapStorage = GameMapStorage(gameMapArray, viewShape)
        val levelRenderer = LevelRenderer(gameMapStorage)

        levelRenderer.render(data.currentLevel)

        justified(Justification.CENTER) {
            padding(2)
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
            padding(2)
            textLine("Congratulations!")
            textLine(" ".repeat(context.consoleWidth))
            textLine("You win this game!")
        }
    }

    private fun RenderScope.renderDeath() {
        justified(Justification.CENTER) {
            padding(2)
            textLine("Failure!")
            textLine(" ".repeat(context.consoleWidth))
            textLine("You dead!")
        }
    }

    override fun RenderScope.renderData(data: GameController) {
        when (data.state) {
            Default -> renderGameMap(data)
            Inventory -> renderInventory(data.user.inventory)
            Death -> renderDeath()
            Win -> renderWin()
        }
    }
}