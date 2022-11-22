package generator.generators

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.info.MonsterInfo

class MonsterGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val characteristicGenerator: AbstractCharacteristicGenerator,
    private val itemsGenerator: AbstractItemsGenerator,
    private val monsterTypeGenerator: AbstractMonsterTypeGenerator,
) : InfoGenerator<MonsterInfo> {
    override fun generate() = MonsterInfo(
        monsterTypeGenerator.generate(),
        GameObject(idGenerator.generate(), position, RectShape()),
        characteristicGenerator.generate(),
        itemsGenerator.generate(),
    )
}