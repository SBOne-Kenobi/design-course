package entity

import engine.GameScene
import entity.models.Entity

/**
 * Class of level.
 */
class Level(
    val name: String,
    val description: String,
    val scene: GameScene,
    entities: List<Entity>,
) {
    private val entitiesIndexById = HashMap<Int, Entity>(entities.associateBy { it.gameObject.id })

    /**
     * Get all entities in the level.
     */
    val entities: List<Entity>
        get() = entitiesIndexById.values.toList()

    /**
     * Add new [entity].
     */
    fun addEntity(entity: Entity) {
        entitiesIndexById[entity.id] = entity
        scene.registerObject(entity.gameObject)
    }

    /**
     * Find entity by its [objectId].
     */
    fun findEntity(objectId: Int): Entity? =
        entitiesIndexById[objectId]

    /**
     * Remove entity with [objectId].
     */
    fun removeEntity(objectId: Int) {
        entitiesIndexById.remove(objectId)
        scene.unregisterObject(objectId)
    }

}
