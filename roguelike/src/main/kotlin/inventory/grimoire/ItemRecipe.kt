package inventory.grimoire

import inventory.items.Item


data class ItemRecipe(val ingredients: Map<Item, Int>, val resultingItem: Item) {
    fun isAppliable(availableItemsAmount: Map<Item, Int>): Boolean = ingredients.all { (item, count) ->
        availableItemsAmount.getOrDefault(item, 0) >= count
    }
}
