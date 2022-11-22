package generator.generators

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.Characteristics
import generator.info.ChestInfo
import generator.info.GameMapInfo
import generator.info.LevelInfo
import generator.info.UserInfo
import generator.info.WallInfo
import inventory.items.ItemWithAmount
import inventory.items.equipments.arms.Gloves
import inventory.items.equipments.head.Helmet
import inventory.items.equipments.weapon.Sword
import inventory.items.recipes.StoneRecipe
import inventory.items.storage.Magma
import inventory.items.storage.Water

class MapGenerator : InfoGenerator<GameMapInfo> {
    override fun generate(): GameMapInfo {
        return GameMapInfo(
            listOf(
                LevelInfo(
                    name = "name",
                    description = "description",
                    listOf(
                        WallInfo(GameObject(0, Position(0, 0), RectShape(height = 15))),
                        WallInfo(GameObject(1, Position(0, 0), RectShape(width = 20))),
                        WallInfo(GameObject(2, Position(19, 0), RectShape(height = 15))),
                        WallInfo(GameObject(3, Position(0, 14), RectShape(width = 20))),

                        WallInfo(GameObject(4, Position(5, 6), RectShape(2, 2))),

                        UserInfo(
                            Characteristics(0, 0, 0),
                            listOf(
                                ItemWithAmount(Gloves, 1),
                                ItemWithAmount(StoneRecipe, 1)
                            ),
                            GameObject(5, Position(2, 2), RectShape())
                        ),

                        ChestInfo(
                            listOf(
                                ItemWithAmount(Helmet, 1),
                                ItemWithAmount(Sword, 2),
                                ItemWithAmount(Water, 5),
                                ItemWithAmount(Magma, 6),
                            ),
                            GameObject(6, Position(7, 8), RectShape())
                        ),
                    )
                )
            )
        )
    }
}