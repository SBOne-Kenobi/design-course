package generator.generators

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.info.UserInfo

class UserGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val characteristicGenerator: AbstractCharacteristicGenerator,
    private val abstractItemsGenerator: AbstractItemsGenerator,
) : InfoGenerator<UserInfo> {
    override fun generate() = UserInfo(
        characteristicGenerator.generate(),
        abstractItemsGenerator.generate(),
        GameObject(idGenerator.generate(), position, RectShape())
    )
}