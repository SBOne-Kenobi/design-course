package launcher

import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.runtime.terminal.Terminal
import com.varabyte.kotter.terminal.virtual.TerminalSize
import com.varabyte.kotter.terminal.virtual.VirtualTerminal
import controls.KottorEventGenerator
import controls.UserEventController
import engine.GameEngine
import entity.GameController
import entity.GameMap
import entity.models.monsters.MonsterStrategyFactory
import generator.generators.base.MapGenerator
import java.awt.Frame
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ui.GameRenderer
import ui.NavigationContext

/**
 * Game launcher controls the main pipeline of the game.
 *
 * 1. Initialise console
 * 2. Generate the game map
 * 3. Translate generated into entities
 * 4. Initialise UI
 * 5. Do main loop
 */
class GameLauncher : AutoCloseable {

    private val terminal: Terminal =
        VirtualTerminal.create(
            Settings.consoleTitle,
            TerminalSize(Settings.consoleWidth, Settings.consoleHeight)
        )

    init {
        Frame.getFrames().single().apply {
            isResizable = false
        }
    }

    private val engine: GameEngine =
        GameEngine()

    private val gameController: GameController =
        createGame()

    private val navigationContext: NavigationContext =
        NavigationContext(gameController.user)

    private val gameRenderer: GameRenderer =
        GameRenderer(navigationContext)

    private val kottorEventGenerator =
        KottorEventGenerator()

    private val inGameControl: UserEventController =
        UserEventController(kottorEventGenerator, activate = false)

    private val menuControl: UserEventController =
        UserEventController(kottorEventGenerator, activate = false)

    init {
        initListeners()
    }

    private fun GameController.generateMap(): GameMap {
        val generator = MapGenerator()
        val mapInfo = generator.generate()
        val translator = InfoTranslator(engine, this, MonsterStrategyFactory())
        return translator.translate(mapInfo)
    }

    private fun createGame(): GameController {
        return GameController(engine) {
            generateMap()
        }
    }

    private fun initListeners() {
        inGameControl.addListener(gameController.user)
        inGameControl.addListener(gameController)

        menuControl.addListener(navigationContext)
        menuControl.addListener(gameController)
    }

    /**
     * Run the game.
     */
    fun run() {
        session(terminal) {
            section {
                gameRenderer.run {
                    renderData(gameController)
                }
            }.run {
                kottorEventGenerator.start(this)

                coroutineScope {
                    launch {
                        while (isActive) {
                            gameController.tick()
                            manageControls()
                            rerender()
                            delay(10)
                        }
                    }
                }
            }
        }
    }

    private fun manageControls() {
        inGameControl.isActive = !gameController.state.isPaused
        menuControl.isActive = gameController.state.isPaused
    }

    override fun close() {
        kottorEventGenerator.close()
        inGameControl.close()
    }

}