package engine

open class GameObject(
    val id: Int,
    val position: Position,
    val shape: Shape = EmptyShape,
    val isSolid: Boolean = true,
    var parent: GameObject? = null
) {

    private val childrenData: MutableMap<Int, GameObject> = HashMap()

    val children: List<GameObject>
        get() = childrenData.values.toList()

    fun addChild(child: GameObject) {
        childrenData.putIfAbsent(child.id, child)
    }

    fun removeChild(childId: Int): GameObject? =
        childrenData.remove(childId)

    fun accept(visitor: GameObjectVisitor) {
        visitor.visitObject(this)
    }

}

interface GameObjectVisitor {
    fun visitObject(obj: GameObject)
}
