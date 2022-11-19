package ui.entities.renderers

import entity.models.Chest
import entity.models.Entity
import entity.models.Monster
import entity.models.User
import entity.models.Wall
import ui.Renderer
import ui.entities.GameMapStorage

class EntityRenderer(private val gameMapStorage: GameMapStorage) : Renderer<Entity> {
    override fun render(obj: Entity) {
        when (obj) {
            is Chest -> ChestRenderer(gameMapStorage).render(obj)
            is Monster -> MonsterRenderer(gameMapStorage).render(obj)
            is User -> UserRenderer(gameMapStorage).render(obj)
            is Wall -> WallRenderer(gameMapStorage).render(obj)
            else -> {}
        }
    }
}
