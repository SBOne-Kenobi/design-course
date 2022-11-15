package inventory.containers

import inventory.grimoire.ItemRecipe
import inventory.items.Item

class MagicPot : DefaultContainer() {
    fun applyRecipe(recipe: ItemRecipe): Item? {
        if (!recipe.isApplicable(itemsToCountData)) return null

        recipe.ingredients.forEach { (item, count) ->
            removeItem(item, count)
        }
        return recipe.resultingItem
    }
}