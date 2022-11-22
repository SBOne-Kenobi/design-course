package entity

import engine.GameScene
import entity.models.Entity

class Level(
    val name: String,
    val description: String,
    val scene: GameScene,
    entities: List<Entity>,
) {
    private val entitiesIndexById = HashMap<Int, Entity>(entities.associateBy { it.gameObject.id })

    val entities: List<Entity>
        get() = entitiesIndexById.values.toList()

    fun findEntity(objectId: Int): Entity? =
        entitiesIndexById[objectId]

    fun removeEntity(objectId: Int) {
        entitiesIndexById.remove(objectId)
        scene.unregisterObject(objectId)
    }

}
