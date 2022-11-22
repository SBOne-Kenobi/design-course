package entity

import controls.Key
import controls.KeyEventType
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.GameEngine
import entity.models.User

class GameController(
    private val map: GameMap,
    val engine: GameEngine
) : UserEventListener {

    var state: GameState = GameState.Default

    var currentLevel: Level = map.levels.first()
        private set

    init {
        engine.loadScene(currentLevel.scene)
    }

    val user: User =
        currentLevel.entities.first { it is User } as User

    init {
        user.gameController = this
    }

    fun tick() {
        // TODO: move handling user events here
        if (!state.isPaused) {
            currentLevel.entities.forEach {
                it.tick()
            }
        }
    }

    fun loadNextLevel(): Boolean =
        map.getNextLevel(currentLevel)?.let {
            currentLevel = it
            true
        } ?: false

    fun loadPrevLevel(): Boolean =
        map.getPrevLevel(currentLevel)?.let {
            currentLevel = it
            true
        } ?: false

    fun openOrCloseInventory() {
        state = when (state) {
            GameState.Default -> GameState.Inventory
            GameState.Inventory -> GameState.Default
            else -> return
        }
    }

    override fun onEvent(event: UserEvent) {
        when (event) {
            is UserKeyEvent -> {
                if (event.type == KeyEventType.Pressed) {
                    when (event.key) {
                        Key.Q -> openOrCloseInventory()
                        Key.Esc -> {
                            state = when (state) {
                                GameState.Default -> GameState.Inventory
                                GameState.Inventory -> GameState.Death
                                GameState.Death -> GameState.Win
                                GameState.Win -> GameState.Default
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}