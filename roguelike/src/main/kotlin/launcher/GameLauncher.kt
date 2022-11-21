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
import generator.MapGenerator
import java.awt.Frame
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ui.ConsoleContext
import ui.GameRenderer

class GameLauncher : AutoCloseable {
    private val consoleContext: ConsoleContext =
        ConsoleContext(
            Settings.gameMapViewWidth,
            Settings.gameMapViewHeight,
            Settings.consoleWidth,
            Settings.consoleHeight
        )

    private val terminal: Terminal =
        VirtualTerminal.create(
            Settings.consoleTitle,
            TerminalSize(consoleContext.consoleWidth, consoleContext.consoleHeight)
        )

    init {
        Frame.getFrames().single().apply {
            isResizable = false
        }
    }

    private val engine: GameEngine =
        GameEngine()

    private val gameRenderer: GameRenderer =
        GameRenderer(consoleContext)

    private val kottorEventGenerator =
        KottorEventGenerator()

    private val inGameControl: UserEventController =
        UserEventController(kottorEventGenerator, activate = false)

    private val menuControl: UserEventController =
        UserEventController(kottorEventGenerator, activate = false)

    private val gameController: GameController =
        createGame()

    private fun generateMap(): GameMap {
        val generator = MapGenerator()
        val mapInfo = generator.generateMap()
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

        menuControl.addListener(consoleContext)
        menuControl.addListener(gameController)
    }

    fun run() {
        session(terminal) {
            section {
                gameRenderer.run {
                    renderData(gameController)
                }
            }.run {
                initListeners()
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