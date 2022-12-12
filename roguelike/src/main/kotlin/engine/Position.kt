package engine

import kotlin.math.abs

/**
 * This class represents any position.
 */
data class Position(val x: Int, val y: Int) : Prototype<Position> {

    val length: Int
        get() = abs(x) + abs(y)

    fun getNeighbours() = listOf(
        this + Position(1, 0),
        this + Position(-1, 0),
        this + Position(0, 1),
        this + Position(0, -1),
    )

    operator fun plus(other: Position): Position =
        Position(x + other.x, y + other.y)

    operator fun minus(other: Position): Position =
        Position(x - other.x, y - other.y)

    operator fun unaryMinus(): Position =
        Position(-x, -y)

    override fun clone(): Position =
        copy()
}
