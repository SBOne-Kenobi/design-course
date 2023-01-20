package generator

import engine.Position
import engine.RectShape
import generator.generators.GenerateItemInfo
import generator.generators.ItemsGenerator
import generator.generators.base.ChestGenerator
import generator.generators.base.IdGenerator
import generator.generators.base.UniformNumberGenerator
import inventory.items.ItemWithAmount
import inventory.items.equipments.body.Armor
import inventory.items.equipments.legs.Boots
import inventory.items.equipments.weapon.Sword
import inventory.items.storage.Water
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChestGeneratorTest {
    @ParameterizedTest
    @MethodSource("getTestData")
    fun testGenerateChest(
        position: Position,
        requiredItems: List<ItemWithAmount>,
        additionalItems: List<GenerateItemInfo> = emptyList(),
    ) {
        val idGenerator = IdGenerator()
        val amountGenerator = UniformNumberGenerator()
        val itemsGenerator = ItemsGenerator(requiredItems, additionalItems, amountGenerator)
        val generator = ChestGenerator(idGenerator, position, itemsGenerator)
        val info = generator.generate()
        assertEquals(position, info.gameObject.position)
        assertEquals(RectShape(1, 1), info.gameObject.shape)
        assertTrue(info.gameObject.isSolid)
        assertTrue(info.items.containsAll(requiredItems))
        additionalItems.forEach {
            val amount = info.items.find { item -> item.item == it.item }?.amount ?: it.minAmount
            assertTrue { it.minAmount <= amount && it.maxAmount >= amount }
        }
    }

    companion object {
        @JvmStatic
        fun getTestData(): List<Arguments> = listOf(
            Arguments.of(
                Position(0, 0),
                listOf<ItemWithAmount>(),
                listOf<GenerateItemInfo>(),
            ),
            Arguments.of(
                Position(4, 2),
                listOf(
                    ItemWithAmount(Armor, 1),
                ),
                listOf<GenerateItemInfo>(),
            ),
            Arguments.of(
                Position(1, 8),
                listOf(
                    ItemWithAmount(Armor, 1),
                    ItemWithAmount(Boots, 2),
                ),
                listOf(
                    GenerateItemInfo(Water, 10, 10),
                    GenerateItemInfo(Sword, 3, 6),
                ),
            ),
        )
    }
}