package generator.generators.base

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.generators.ItemsGenerator
import generator.generators.AbstractMonsterTypeGenerator
import generator.generators.InfoGenerator
import generator.info.MonsterInfo

/**
 * The monster generator.
 */
class MonsterGenerator(
    private val idGenerator: IdGenerator,
    private val position: Position,
    private val itemsGenerator: ItemsGenerator,
    private val monsterTypeGenerator: AbstractMonsterTypeGenerator,
) : InfoGenerator<MonsterInfo> {
    override fun generate(): MonsterInfo {
        val type = monsterTypeGenerator.generate()
        return MonsterInfo(
            type,
            GameObject(idGenerator.generate(), position, RectShape()),
            type.initCharacteristics,
            itemsGenerator.generate(),
        )
    }
}