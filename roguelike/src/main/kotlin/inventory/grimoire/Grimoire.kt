package inventory.grimoire

import inventory.items.Item

/**
 * The user's grimoire with [recipes].
 */
data class Grimoire(val recipes: MutableList<ItemRecipe> = mutableListOf()) {

    /**
     * Get all recipes that can be done with [availableIngredients].
     */
    fun getAppliableRecipes(availableIngredients: Map<Item, Int>): List<ItemRecipe> =
        recipes.filter { it.isApplicable(availableIngredients) }

    /**
     * Get a recipe that perfectly matches to [availableIngredients].
     */
    fun getMatchRecipe(availableIngredients: Map<Item, Int>): ItemRecipe? =
        recipes.singleOrNull { it.isMatch(availableIngredients) }

}