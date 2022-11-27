package generator.generators.base

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.generators.ItemsGenerator
import generator.generators.InfoGenerator
import generator.info.UserInfo

/**
 * The user generator.
 */
class UserGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val characteristicGenerator: CharacteristicGenerator,
    private val itemsGenerator: ItemsGenerator,
) : InfoGenerator<UserInfo> {
    override fun generate() = UserInfo(
        characteristicGenerator.generate(),
        itemsGenerator.generate(),
        GameObject(idGenerator.generate(), position, RectShape())
    )
}