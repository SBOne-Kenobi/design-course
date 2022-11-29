package engine

/**
 * Container for [GameObject]s.
 */
class GameScene {
    private val objectsData: MutableMap<Int, GameObject> = HashMap()

    /**
     * Get objects in the scene.
     */
    val objects: List<GameObject>
        get() = objectsData.values.toList()

    /**
     * Add new [gameObject].
     */
    fun registerObject(gameObject: GameObject) {
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
}
