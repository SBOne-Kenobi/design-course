package ui.entities

import engine.RectShape
import entity.models.Wall
import ui.Renderer

class WallRenderer(gameMapStorage: GameMapStorage) : Renderer<Wall> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    override fun render(obj: Wall) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, '#'))
        }
    }
}