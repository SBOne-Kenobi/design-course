package entity

import entity.models.User

class GameController(val map: GameMap) {

    var state: GameState = GameState.Default

    var currentLevel: Level = map.levels.first()
        private set

    val user: User
        get() = currentLevel.entities.first { it is User } as User

    fun tick() {
        TODO()
    }
}