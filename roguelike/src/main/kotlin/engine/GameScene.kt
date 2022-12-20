package engine

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

/**
 * Container for [GameObject]s.
 */
class GameScene {
    private val objectsData: MutableMap<Int, GameObject> = ConcurrentHashMap()

    private var maxId: Int = 0

    /**
     * Get objects in the scene.
     */
    val objects: List<GameObject>
        get() = objectsData.values.toList()

    /**
     * Add new [gameObject].
     */
    fun registerObject(gameObject: GameObject) {
        maxId = max(maxId, gameObject.id)
        objectsData[gameObject.id] = gameObject
    }

    /**
     * Delete [GameObject] with [id].
     */
    fun unregisterObject(id: Int): GameObject? =
        objectsData.remove(id)

    /**
     * Get [GameObject] by [id].
     */
    fun findObjectById(id: Int): GameObject? =
        objectsData[id]

    private fun getFreeId(): Int =
        maxId + 1

    /**
     * Create clone of [gameObject] and register it.
     */
    fun cloneGameObject(gameObject: GameObject): GameObject =
        gameObject.clone(getFreeId()).also {
            registerObject(it)
        }
}
