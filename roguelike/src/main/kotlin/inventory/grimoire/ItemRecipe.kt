package inventory.grimoire

import inventory.items.EquipmentType
import inventory.items.Item


abstract class ItemRecipe(
    override val name: String,
    val ingredients: Map<Item, Int>,
    val resultingItem: Item,
) : Item {
    override val equipmentType: EquipmentType = EquipmentType.None

    override val description: String = buildString {
        appendLine("To get 1 piece of ${resultingItem.name} mix following ingredients in tha Magic Pot:")
        ingredients.forEach { (item, count) ->
            appendLine("  ${item.name} - $count")
        }
    }

    fun isApplicable(availableItemsAmount: Map<Item, Int>): Boolean = ingredients.all { (item, count) ->
        availableItemsAmount.getOrDefault(item, 0) >= count
    }

    fun isMatch(availableItemsAmount: Map<Item, Int>): Boolean =
        isApplicable(availableItemsAmount) && availableItemsAmount.all { (item, count) ->
            ingredients.getOrDefault(item, 0) >= count
        }
}
