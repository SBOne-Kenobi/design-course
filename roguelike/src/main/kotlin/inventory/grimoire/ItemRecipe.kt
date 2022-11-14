package inventory.grimoire

import inventory.items.EquipmentType
import inventory.items.Item


data class ItemRecipe(
    val ingredients: Map<Item, Int>,
    val resultingItem: Item,
    override val description: String
): Item {
    override val equipmentType: EquipmentType = EquipmentType.None

    fun isAppliable(availableItemsAmount: Map<Item, Int>): Boolean = ingredients.all { (item, count) ->
        availableItemsAmount.getOrDefault(item, 0) >= count
    }
}
