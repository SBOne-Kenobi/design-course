package generator.generators.base

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.generators.ItemsGenerator
import generator.generators.InfoGenerator
import generator.info.ChestInfo

/**
 * Generator for chest.
 */
class ChestGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val abstractItemsGenerator: ItemsGenerator,
) : InfoGenerator<ChestInfo> {
    override fun generate() = ChestInfo(
        abstractItemsGenerator.generate(),
        GameObject(idGenerator.generate(), position, RectShape())
    )
}