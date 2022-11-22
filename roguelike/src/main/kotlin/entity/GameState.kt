package entity

enum class GameState(val isPaused: Boolean) {
    Default(isPaused = false),
    Inventory(isPaused = true),
    Death(isPaused = true),
    Win(isPaused = true),
}