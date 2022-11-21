package generator

import engine.GameObject
import engine.Position
import engine.RectShape
import generator.info.UserInfo
import generator.info.WallInfo
import inventory.items.EquipmentType
import inventory.items.Item
import inventory.items.ItemWithAmount
import kotlin.random.Random

class MapGenerator {
    fun generateMap(): GameMapInfo {
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
                            (0 until 50).map {
                                ItemWithAmount(
                                    object : Item {
                                        override val name: String = "Item $it"
                                        override val description: String = ""
                                        override val equipmentType: EquipmentType = EquipmentType.None
                                    }, 
                                    Random.nextInt(1, 30)
                                )
                            },
                            GameObject(5, Position(2, 2), RectShape())
                        )
                    )
                )
            )
        )
    }
}