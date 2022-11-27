package inventory.containers

import inventory.grimoire.ItemRecipe
import inventory.items.Item

/**
 * The user's magic pot.
 */
class MagicPot : DefaultContainer() {

    /**
     * Try to apply [recipe].
     *
     * Note: removes items necessary for [recipe] if it is applicable.
     */
    fun applyRecipe(recipe: ItemRecipe): Item? {
        if (!recipe.isApplicable(itemsToCountData)) return null

        recipe.ingredients.forEach { (item, count) ->
            removeItem(item, count)
        }
        return recipe.resultingItem
    }
}