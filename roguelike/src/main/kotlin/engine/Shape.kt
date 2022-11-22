package engine

sealed interface Shape {
    fun isIntersected(position: Position, otherShape: Shape, otherPosition: Position): Boolean

    operator fun contains(position: Position): Boolean

}

object EmptyShape : Shape {
    override fun isIntersected(position: Position, otherShape: Shape, otherPosition: Position): Boolean =
        false

    override fun contains(position: Position): Boolean =
        false
}

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
