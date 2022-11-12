package engine

class GameScene {
    private val objectsData: MutableMap<Int, GameObject> = HashMap()

    val objects: List<GameObject>
        get() = objectsData.values.toList()

    fun registerObject(gameObject: GameObject) {
        objectsData[gameObject.id] = gameObject
    }

    fun findObjectById(id: Int): GameObject? =
        objectsData[id]
}
