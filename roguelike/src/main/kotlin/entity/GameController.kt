package entity

import controls.Key
import controls.KeyEventType
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.GameEngine
import entity.models.User

/**
 * Class that contains and controls the state of the game.
 */
class GameController(
    val engine: GameEngine,
    mapInit: GameController.() -> GameMap,
) : UserEventListener {

    private val map: GameMap = mapInit(this)

    /**
     * Current state.
     */
    var state: GameState = GameState.Default

    /**
     * Current level.
     */
    var currentLevel: Level = map.levels.first()
        private set

    init {
        engine.loadScene(currentLevel.scene)
    }

    /**
     * Get [User] of the game.
     */
    val user: User =
        currentLevel.entities.first { it is User } as User

    /**
     * Next loop of the game.
     */
    fun tick() {
        // TODO: move handling user events here
        if (!state.isPaused) {
            currentLevel.entities.forEach {
                it.tick()
            }
        }
    }

    /**
     * Load next level.
     */
    fun loadNextLevel(): Boolean =
        map.getNextLevel(currentLevel)?.let {
            currentLevel = it
            true
        } ?: false

    /**
     * Load previous level.
     */
    fun loadPrevLevel(): Boolean =
        map.getPrevLevel(currentLevel)?.let {
            currentLevel = it
            true
        } ?: false

    /**
     * Change the game state to opened or closed the user's inventory.
     */
    fun openOrCloseInventory() {
        state = when (state) {
            GameState.Default -> GameState.Inventory
            GameState.Inventory -> GameState.Default
            else -> return
        }
    }

    /**
     * Change the game state to user's death.
     */
    fun userDeath() {
        state = GameState.Death
    }

    /**
     * When Q key pressed - open or close the user's inventory.
     *
     * Esc - just change the game state for dev mode.
     */
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