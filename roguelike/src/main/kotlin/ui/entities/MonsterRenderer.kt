package ui.entities

import engine.RectShape
import entity.models.Monster
import ui.Renderer

class MonsterRenderer(gameMapStorage: GameMapStorage) : Renderer<Monster> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    override fun render(obj: Monster) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, 'M'))
        }
    }
}