package ui.entities.renderers

import entity.Level
import ui.Renderer
import ui.entities.GameMapStorage

class LevelRenderer(gameMapStorage: GameMapStorage) : Renderer<Level> {
    private val entityRenderer = EntityRenderer(gameMapStorage)

    override fun render(obj: Level) {
        obj.entities.forEach(entityRenderer::render)
    }
}
