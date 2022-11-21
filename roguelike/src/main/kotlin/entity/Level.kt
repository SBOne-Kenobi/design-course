package entity

import engine.GameScene
import entity.models.Entity

data class Level(
    val name: String,
    val description: String,
    val scene: GameScene,
    val entities: List<Entity>,
)
