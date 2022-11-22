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
import generator.generators.MapGenerator
import java.awt.Frame
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ui.GameRenderer
import ui.NavigationContext

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

    private fun generateMap(): GameMap {
        val generator = MapGenerator()
        val mapInfo = generator.generate()
        val translator = InfoTranslator(engine)
        return translator.translate(mapInfo)
    }

    private fun createGame(): GameController {
        val map = generateMap()
        return GameController(map, engine)
    }

    private fun initListeners() {
        inGameControl.addListener(gameController.user)
        inGameControl.addListener(gameController)

        menuControl.addListener(navigationContext)
        menuControl.addListener(gameController)
    }

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