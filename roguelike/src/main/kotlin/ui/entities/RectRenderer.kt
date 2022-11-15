package ui.entities

import engine.Position
import engine.RectShape
import kotlin.math.max
import kotlin.math.min
import ui.Renderer

data class RectShapeWithChar(val position: Position, val shape: RectShape, val char: Char)

class RectRenderer(private val gameMapStorage: GameMapStorage) : Renderer<RectShapeWithChar> {
    override fun render(obj: RectShapeWithChar) {
        val viewLU = gameMapStorage.viewShape.origin
        val viewRD = viewLU + Position(gameMapStorage.viewShape.width, gameMapStorage.viewShape.height)

        val objLU = obj.position + obj.shape.origin
        val objRD = objLU + Position(obj.shape.width, obj.shape.height)

        val xRange = max(viewLU.x, objLU.x) until min(viewRD.x, objRD.x)
        val yRange = max(viewLU.y, objLU.y) until min(viewRD.y, objRD.y)

        for (y in yRange) {
            for (x in xRange) {
                gameMapStorage.storage[y - viewLU.y][x - viewLU.x] = obj.char
            }
        }
    }
}