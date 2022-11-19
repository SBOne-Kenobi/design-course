package ui.entities.renderers

import engine.RectShape
import entity.models.Monster
import ui.Renderer
import ui.entities.GameMapStorage

class MonsterRenderer(gameMapStorage: GameMapStorage) : Renderer<Monster> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    override fun render(obj: Monster) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, 'M'))
        }
    }
}