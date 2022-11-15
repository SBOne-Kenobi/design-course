package inventory.items.recipes

import inventory.grimoire.ItemRecipe
import inventory.items.storage.Magma
import inventory.items.storage.Stone
import inventory.items.storage.Water

object StoneRecipe : ItemRecipe(
    name = "Stone Recipe",
    ingredients = mapOf(
        Magma to 2,
        Water to 5,
    ),
    resultingItem = Stone,
)