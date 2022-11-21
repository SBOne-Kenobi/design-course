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
import inventory.UserInventory
import launcher.Settings
import ui.entities.GameMapStorage
import ui.entities.UserBasedViewNavigation
import ui.entities.renderers.LevelRenderer
import ui.inventory.containers.renderers.ManyContainersRenderer

class GameRenderer(private val navigationContext: NavigationContext) : ConsoleRenderer<GameController>() {

    private val pressToExitText = "Press ctrl+c to exit"

    private fun RenderScope.padding(padding: Int) {
        repeat(padding) {
            textLine()
        }
    }

    private fun UserBasedViewNavigation.toViewShape(): RectShape =
        RectShape(
            Settings.gameMapViewWidth,
            Settings.gameMapViewHeight,
            viewPosition
        )

    private fun recalculateViewNavigation(viewNavigation: UserBasedViewNavigation) {
        val viewX = calculateViewPosition(
            viewNavigation.viewPosition.x,
            viewNavigation.userPosition.x,
            Settings.gameMapViewWidth,
            padding = Settings.gameMapViewWidth / 5
        )
        val viewY = calculateViewPosition(
            viewNavigation.viewPosition.y,
            viewNavigation.userPosition.y,
            Settings.gameMapViewHeight,
            padding = Settings.gameMapViewHeight / 3
        )
        viewNavigation.viewPosition = Position(viewX, viewY)
    }

    private fun RenderScope.renderGameMap(data: GameController) {
        val gameMapArray = Array(Settings.gameMapViewHeight) {
            CharArray(Settings.gameMapViewWidth) { ' ' }
        }

        val viewNavigation = navigationContext.getViewNavigation()
        recalculateViewNavigation(viewNavigation)

        val viewShape = viewNavigation.toViewShape()
        val gameMapStorage = GameMapStorage(gameMapArray, viewShape)
        val levelRenderer = LevelRenderer(gameMapStorage)

        levelRenderer.render(data.currentLevel)

        bordered(BorderCharacters.ASCII) {
            gameMapArray.forEach {
                textLine(String(it))
            }
        }
    }

    private fun RenderScope.renderInventory(userInventory: UserInventory) {
        val manyContainersWithNavigation = navigationContext.getUserInventoryWithNavigation(userInventory)
        val renderer = ManyContainersRenderer(Settings.consoleWidth - 1, Settings.consoleHeight - 1)
        renderer.run {
            renderData(manyContainersWithNavigation)
        }
    }

    private fun RenderScope.renderWin() {
        justified(Justification.CENTER) {
            padding(2)
            textLine("Congratulations!")
            textLine(" ".repeat(Settings.consoleWidth))
            textLine("You win this game!")
            textLine()
            textLine(pressToExitText)
        }
    }

    private fun RenderScope.renderDeath() {
        justified(Justification.CENTER) {
            padding(2)
            textLine("Failure!")
            textLine(" ".repeat(Settings.consoleWidth))
            textLine("You dead!")
            textLine()
            textLine(pressToExitText)
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