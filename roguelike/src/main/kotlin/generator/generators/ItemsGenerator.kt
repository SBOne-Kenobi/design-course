package generator.generators

import inventory.items.Item
import inventory.items.ItemWithAmount

/**
 * Info about possible values of generated item.
 */
data class GenerateItemInfo(
    val item: Item,
    val minAmount: Int = 0,
    val maxAmount: Int = 100,
)

/**
 * Generator of items.
 *
 * @param requiredItems items that will always be in the result of generation
 * @param additionalItems items that will generate according to given [GenerateItemInfo]
 * @param amountGenerator number generator that will be used for generation of amount for each item
 */
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
                .takeIf { it != 0 }
                ?.let { ItemWithAmount(info.item, it) }
        }

}