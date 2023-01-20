package ui.entities.renderers

import engine.RectShape
import entity.models.User
import ui.Renderer
import ui.entities.GameMapStorage

class UserRenderer(gameMapStorage: GameMapStorage) : Renderer<User> {
    private val rectRenderer = RectRenderer(gameMapStorage)

    override fun render(obj: User) {
        obj.gameObject.apply {
            rectRenderer.render(RectShapeWithChar(position, shape as RectShape, 'U'))
        }
    }
}