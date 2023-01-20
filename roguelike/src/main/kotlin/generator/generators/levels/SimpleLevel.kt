package generator.generators.levels

import engine.Position
import generator.AggressiveMonsterType
import generator.CowardlyMonsterType
import generator.MonsterType
import generator.PassiveMonsterType
import generator.ReplicableMonsterType
import generator.SmartMonsterType
import generator.generators.AbstractLevelGenerator
import generator.generators.AbstractMonsterTypeGenerator
import generator.generators.GenerateItemInfo
import generator.generators.ItemsGenerator
import generator.generators.base.CharacteristicGenerator
import generator.generators.base.ChestGenerator
import generator.generators.base.IdGenerator
import generator.generators.base.MonsterGenerator
import generator.generators.base.UniformNumberGenerator
import generator.generators.base.UserGenerator
import generator.styles.AbstractMonsterInfoFactory
import inventory.items.ItemWithAmount
import inventory.items.equipments.arms.Gloves
import inventory.items.equipments.body.Armor
import inventory.items.equipments.head.Helmet
import inventory.items.equipments.legs.Boots
import inventory.items.equipments.weapon.Sword
import inventory.items.recipes.StoneRecipe
import inventory.items.storage.Magma
import inventory.items.storage.Water
import kotlin.random.Random

/**
 * Very simple level generator with one obstacle, one chest and one player.
 */
class SimpleLevel(
    width: Int = 20,
    height: Int = 15,
    private val monsterInfoFactory: AbstractMonsterInfoFactory,
) : AbstractLevelGenerator(
    name = "Simple level",
    description = "Simple level with one chest",
    width = width,
    height = height,
    idGenerator = IdGenerator(),
) {
    private val random = Random(42)

    override fun addObstacles() {
        addWall(Position(5, 6), width = 2, height = 2)
    }

    override fun addChests() {
        val chestGenerator = ChestGenerator(
            idGenerator,
            Position(9, 10),
            ItemsGenerator(
                listOf(
                    ItemWithAmount(Helmet, 1),
                    ItemWithAmount(Sword, 2),
                ),
                listOf(
                    GenerateItemInfo(Water, 1, 3),
                ),
                UniformNumberGenerator(random = random)
            )
        )
        info.add(chestGenerator.generate())
    }

    override fun addMonsters() {
        var monsterGenerator = MonsterGenerator(
            idGenerator,
            Position(10, 6),
            ItemsGenerator(
                listOf(ItemWithAmount(Armor, 1)),
                listOf(),
                UniformNumberGenerator(random = random)
            ),
            object : AbstractMonsterTypeGenerator() {
                override fun generate(): MonsterType {
                    return CowardlyMonsterType
                }
            },
            monsterInfoFactory
        )
        info.add(monsterGenerator.generate())

        monsterGenerator = MonsterGenerator(
            idGenerator,
            Position(12, 6),
            ItemsGenerator(
                listOf(),
                listOf(GenerateItemInfo(Water, 3, 10)),
                UniformNumberGenerator(random = random)
            ),
            object : AbstractMonsterTypeGenerator() {
                override fun generate(): MonsterType {
                    return AggressiveMonsterType
                }
            },
            monsterInfoFactory
        )
        info.add(monsterGenerator.generate())

        monsterGenerator = MonsterGenerator(
            idGenerator,
            Position(11, 8),
            ItemsGenerator(
                listOf(),
                listOf(GenerateItemInfo(Magma, 2, 10)),
                UniformNumberGenerator(random = random)
            ),
            object : AbstractMonsterTypeGenerator() {
                override fun generate(): MonsterType {
                    return ReplicableMonsterType(PassiveMonsterType)
                }
            },
            monsterInfoFactory
        )
        info.add(monsterGenerator.generate())

        monsterGenerator = MonsterGenerator(
            idGenerator,
            Position(18, 13),
            ItemsGenerator(
                listOf(ItemWithAmount(Boots, 1)),
                listOf(),
                UniformNumberGenerator(random = random)
            ),
            object : AbstractMonsterTypeGenerator() {
                override fun generate(): MonsterType {
                    return SmartMonsterType(cowardlyHP = 90, PassiveMonsterType)
                }
            },
            monsterInfoFactory
        )
        info.add(monsterGenerator.generate())
    }

    override fun addAdditional() {
        val userGenerator = UserGenerator(
            idGenerator,
            Position(2, 2),
            CharacteristicGenerator(
                UniformNumberGenerator(30, 50, random),
                UniformNumberGenerator(30, 50, random),
                UniformNumberGenerator(30, 50, random),
            ),
            ItemsGenerator(listOf(
                ItemWithAmount(Gloves, 1),
                ItemWithAmount(StoneRecipe, 1),
            ))
        )
        info.add(userGenerator.generate())
    }
}