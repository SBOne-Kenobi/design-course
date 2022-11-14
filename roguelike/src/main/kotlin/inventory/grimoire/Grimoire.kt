package inventory.grimoire

import inventory.items.Item

data class Grimoire(val recipes: MutableList<ItemRecipe> = mutableListOf()) {
    fun getAppliableRecipes(availableIngredients: Map<Item, Int>): List<ItemRecipe> =
        recipes.filter { it.isAppliable(availableIngredients) }
}