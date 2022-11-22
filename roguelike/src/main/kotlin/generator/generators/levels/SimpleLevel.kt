package generator.generators.levels

import engine.Position
import generator.generators.AbstractLevelGenerator
import generator.generators.GenerateItemInfo
import generator.generators.ItemsGenerator
import generator.generators.base.CharacteristicGenerator
import generator.generators.base.ChestGenerator
import generator.generators.base.IdGenerator
import generator.generators.base.UniformNumberGenerator
import generator.generators.base.UserGenerator
import inventory.items.ItemWithAmount
import inventory.items.equipments.arms.Gloves
import inventory.items.equipments.head.Helmet
import inventory.items.equipments.weapon.Sword
import inventory.items.recipes.StoneRecipe
import inventory.items.storage.Magma
import inventory.items.storage.Water
import kotlin.random.Random

class SimpleLevel : AbstractLevelGenerator(
    name = "Simple level",
    description = "Simple level with one chest",
    width = 20,
    height = 15,
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
                    GenerateItemInfo(Water, 5, 15),
                    GenerateItemInfo(Magma, 2, 10),
                ),
                UniformNumberGenerator(random = random)
            )
        )
        info.add(chestGenerator.generate())
    }

    override fun addMonsters() {
    }

    override fun addAdditional() {
        val userGenerator = UserGenerator(
            idGenerator,
            Position(2, 2),
            CharacteristicGenerator(
                UniformNumberGenerator(10, 50, random),
                UniformNumberGenerator(10, 50, random),
                UniformNumberGenerator(10, 50, random),
            ),
            ItemsGenerator(listOf(
                ItemWithAmount(Gloves, 1),
                ItemWithAmount(StoneRecipe, 1),
            ))
        )
        info.add(userGenerator.generate())
    }
}