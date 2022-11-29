package engine

/**
 * Class of an object on a game map.
 *
 * @param id id of the object
 * @param position position of the object
 * @param shape shape of the object
 * @param isSolid specifies the object is solid or not
 */
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
