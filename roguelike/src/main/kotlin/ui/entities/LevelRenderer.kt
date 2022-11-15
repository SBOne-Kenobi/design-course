package ui.entities

import entity.Level
import ui.Renderer

class LevelRenderer(gameMapStorage: GameMapStorage) : Renderer<Level> {
    private val entityRenderer = EntityRenderer(gameMapStorage)

    override fun render(obj: Level) {
        obj.entities.forEach(entityRenderer::render)
    }
}
