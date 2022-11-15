package ui.entities

import engine.RectShape
import entity.models.Chest
import ui.Renderer

class ChestRenderer(gameMapStorage: GameMapStorage) : Renderer<Chest> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    override fun render(obj: Chest) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, 'c'))
        }
    }
}