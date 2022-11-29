package engine

/**
 * This interface represents a shape of the object.
 */
sealed interface Shape {
    /**
     * Check a presence of an intersection with another shape with specified positions.
     */
    fun isIntersected(position: Position, otherShape: Shape, otherPosition: Position): Boolean

    /**
     * Check that a point with [position] is in this shape.
     */
    operator fun contains(position: Position): Boolean

}

/**
 * An empty shape without any point inside.
 */
object EmptyShape : Shape {
    override fun isIntersected(position: Position, otherShape: Shape, otherPosition: Position): Boolean =
        false

    override fun contains(position: Position): Boolean =
        false
}

/**
 * A half-opened rectangle shape with [width] and [height]. Also, you can specify an origin of this shape.
 *
 * Contains all points within x-dimension [[origin.x, origin.x + width]] and y-dimension [[origin.y, origin.y + height]].
 */
data class RectShape(val width: Int = 1, val height: Int = 1, val origin: Position = Position(x = 0, y = 0)) : Shape {
    override fun isIntersected(position: Position, otherShape: Shape, otherPosition: Position): Boolean =
        when (otherShape) {
            EmptyShape -> false
            is RectShape -> this.isAnyCornerIn(otherPosition - position, otherShape) ||
                    otherShape.isAnyCornerIn(position - otherPosition, this) ||
                    this.copy(origin = position + origin) == otherShape.copy(origin = otherPosition + otherShape.origin)
        }

    override fun contains(position: Position): Boolean =
        (position - origin).let { p ->
            p.x in 0 until width && p.y in 0 until height
        }

    private fun isAnyCornerIn(otherPosition: Position, other: RectShape): Boolean {
        val leftUpCorner = otherPosition - origin + other.origin
        val rightDownCorner = leftUpCorner + Position(other.width, other.height)
        val xRange = leftUpCorner.x until rightDownCorner.x
        val yRange = leftUpCorner.y until rightDownCorner.y
        return (0 in xRange || width - 1 in xRange) && (0 in yRange || height - 1 in yRange)
    }
}
