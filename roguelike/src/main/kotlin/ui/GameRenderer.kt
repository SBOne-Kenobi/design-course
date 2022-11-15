package ui

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import com.varabyte.kotterx.decorations.BorderCharacters
import com.varabyte.kotterx.decorations.bordered
import engine.RectShape
import entity.GameController
import entity.Level
import entity.models.User
import inventory.UserInventory
import ui.entities.GameMapStorage
import ui.entities.LevelRenderer
import ui.inventory.containers.ManyContainersRenderer

class GameRenderer(private val context: ConsoleContext) : ConsoleRenderer<GameController>() {
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

        bordered(BorderCharacters.ASCII) {
            gameMapArray.forEach {
                textLine(String(it))
            }
        }
    }

    private fun RenderScope.renderInventory(userInventory: UserInventory) {
        val manyContainersWithNavigation = context.getUserInventoryWithNavigation(userInventory)
        val renderer = ManyContainersRenderer(context.consoleWidth - 1, context.consoleHeight - 1)
        renderer.run {
            renderData(manyContainersWithNavigation)
        }
    }

    override fun RenderScope.renderData(data: GameController) {
//        renderGameMap(data.currentLevel)
        val user = data.currentLevel.entities.first { it is User } as User
        renderInventory(user.inventory)
    }
}