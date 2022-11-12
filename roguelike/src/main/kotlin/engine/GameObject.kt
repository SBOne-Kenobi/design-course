package engine

open class GameObject(
    val id: Int,
    var position: Position,
    val shape: Shape = EmptyShape,
    val isSolid: Boolean = true,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GameObject) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
