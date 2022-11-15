package entity

import engine.GameObject
import engine.GameScene
import engine.Position
import engine.RectShape
import entity.models.Entity
import entity.models.User
import entity.models.Wall
import generator.Characteristics

class Level {
    val scene: GameScene = GameScene()
    val entities: List<Entity> = listOf(
        Wall(GameObject(0, Position(0, 0), RectShape(height = 15))),
        Wall(GameObject(1, Position(0, 0), RectShape(width = 20))),
        Wall(GameObject(2, Position(19, 0), RectShape(height = 15))),
        Wall(GameObject(3, Position(0, 14), RectShape(width = 20))),

        Wall(GameObject(4, Position(5, 6), RectShape(2, 2))),

        User(GameObject(5, Position(2, 2), RectShape()), Characteristics(0, 0, 0))
    )
    val description: String = "description"
}