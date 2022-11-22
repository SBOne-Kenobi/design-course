package inventory.grimoire

import inventory.items.Item

data class Grimoire(val recipes: MutableList<ItemRecipe> = mutableListOf()) {
    fun getAppliableRecipes(availableIngredients: Map<Item, Int>): List<ItemRecipe> =
        recipes.filter { it.isApplicable(availableIngredients) }

    fun getMatchRecipe(availableIngredients: Map<Item, Int>): ItemRecipe? =
        recipes.singleOrNull { it.isMatch(availableIngredients) }

}