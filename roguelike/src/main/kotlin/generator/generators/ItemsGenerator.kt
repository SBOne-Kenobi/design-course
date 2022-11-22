package generator.generators

import inventory.items.Item
import inventory.items.ItemWithAmount

data class GenerateItemInfo(
    val item: Item,
    val minAmount: Int = 0,
    val maxAmount: Int = 100,
)

class ItemsGenerator(
    private val requiredItems: List<ItemWithAmount>,
    private val additionalItems: List<GenerateItemInfo> = emptyList(),
    private val amountGenerator: AbstractNumberGenerator? = null,
) : Generator<List<ItemWithAmount>> {
    override fun generate(): List<ItemWithAmount> =
        requiredItems + generateAdditionalItems()

    private fun generateAdditionalItems(): List<ItemWithAmount> =
        additionalItems.mapNotNull { info ->
            amountGenerator!!.apply {
                min = info.minAmount
                max = info.maxAmount
            }.generate()
                .number.takeIf { it != 0 }
                ?.let { ItemWithAmount(info.item, it) }
        }

}