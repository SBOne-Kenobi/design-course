package ui.entities.renderers

import engine.RectShape
import entity.models.Monster
import generator.FantasyStyle
import generator.MonsterStyle
import generator.ScifiStyle
import ui.Renderer
import ui.entities.GameMapStorage

class MonsterRenderer(gameMapStorage: GameMapStorage) : Renderer<Monster> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    private fun getChar(style: MonsterStyle): Char = when (style) {
        FantasyStyle -> 'm'
        ScifiStyle -> 'M'
    }

    override fun render(obj: Monster) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, getChar(obj.style)))
        }
    }
}