package engine

/**
 * This class represents any position.
 */
data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position): Position =
        Position(x + other.x, y + other.y)

    operator fun minus(other: Position): Position =
        Position(x - other.x, y - other.y)

    operator fun unaryMinus(): Position =
        Position(-x, -y)
}
