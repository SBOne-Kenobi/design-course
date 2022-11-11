package engine

class GameScene {
    private val objectsData: MutableMap<Int, GameObject> = HashMap()
    private val rootsData: MutableList<GameObject> = mutableListOf()

    val objects: List<GameObject>
        get() = objectsData.values.toList()

    val roots: List<GameObject>
        get() = rootsData.toList()

    private inner class RegisterVisitor : GameObjectVisitor {
        override fun visitObject(obj: GameObject) {
            objectsData.putIfAbsent(obj.id, obj)
            obj.children.forEach {
                it.accept(this)
            }
        }
    }

    fun registerObject(gameObject: GameObject) {
        rootsData.add(gameObject)
        gameObject.accept(RegisterVisitor())
    }

    fun findObjectById(id: Int): GameObject? =
        objectsData[id]
}
