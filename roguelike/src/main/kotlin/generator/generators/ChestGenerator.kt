package generator.generators

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.info.ChestInfo

class ChestGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val abstractItemsGenerator: AbstractItemsGenerator,
) : InfoGenerator<ChestInfo> {
    override fun generate() = ChestInfo(
        abstractItemsGenerator.generate(),
        GameObject(idGenerator.generate(), position, RectShape())
    )
}