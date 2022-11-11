package engine

sealed interface Shape

object EmptyShape : Shape

data class RectShape(val width: Int, val height: Int, val origin: Position)
