package engine

import kotlin.math.abs

/**
 * This class represents any position.
 */
data class Position(val x: Int, val y: Int) {

    val length: Int
        get() = abs(x) + abs(y)

    operator fun plus(other: Position): Position =
        Position(x + other.x, y + other.y)

    operator fun minus(other: Position): Position =
        Position(x - other.x, y - other.y)

    operator fun unaryMinus(): Position =
        Position(-x, -y)
}
