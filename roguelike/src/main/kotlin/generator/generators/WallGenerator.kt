package generator.generators

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.info.WallInfo

class WallGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val width: Int = 1,
    private val height: Int = 1,
) : InfoGenerator<WallInfo> {
    override fun generate() = WallInfo(
        GameObject(idGenerator.generate(), position, RectShape(width, height))
    )
}