import com.varabyte.kotter.foundation.input.Keys
import com.varabyte.kotter.foundation.input.onKeyPressed
import com.varabyte.kotter.foundation.runUntilSignal
import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.terminal.virtual.TerminalSize
import com.varabyte.kotter.terminal.virtual.VirtualTerminal
import engine.Position
import entity.GameController
import entity.GameMap
import entity.GameState
import entity.Level
import inventory.containers.UserEquipment
import inventory.items.EquipmentType
import inventory.items.Item
import inventory.items.equipments.legs.Boots
import inventory.items.storage.Stone
import inventory.items.storage.Water
import java.awt.Frame
import kotlin.random.Random
import ui.ConsoleContext
import ui.GameRenderer
import ui.makeContext

fun main() {
    val consoleContext = ConsoleContext(
        gameMapViewWidth = 50,
        gameMapViewHeight = 15,
        consoleWidth = 100,
        consoleHeight = 25,
    )

    val terminal = VirtualTerminal.create(
        title = "Test",
        terminalSize = TerminalSize(consoleContext.consoleWidth, consoleContext.consoleHeight),
    )

    val frame = Frame.getFrames().single().apply {
        isResizable = false
    }

    val gameRenderer = GameRenderer(consoleContext)
    val gameController = GameController(GameMap(listOf(Level())))
    val user = gameController.user

    for (i in 0 until 50) {
        user.inventory.storage.addItem(object : Item {
            override val name: String = "Item $i"
            override val description: String = ""
            override val equipmentType: EquipmentType = EquipmentType.None
        }, Random.nextInt(1, 30))
    }

    user.inventory.equipment.addItem(Boots)

    user.inventory.pot.addItem(Stone)
    user.inventory.pot.addItem(Water)

    session(terminal) {
        section {
            gameRenderer.render(makeContext(gameController))
        }.runUntilSignal {
            onKeyPressed {
                val navigation = consoleContext.getUserInventoryWithNavigation(user.inventory)
                val currentNav = navigation.containers[navigation.currentContainerPosition]
                when (key) {
                    Keys.LEFT -> {
                        if (gameController.state == GameState.Default) {
                            user.gameObject.position = user.gameObject.position + Position(-1, 0)
                        } else {
                            navigation.currentContainerPosition =
                                (navigation.currentContainerPosition - 1).coerceAtLeast(0)
                        }
                    }

                    Keys.RIGHT -> {
                        if (gameController.state == GameState.Default) {
                            user.gameObject.position = user.gameObject.position + Position(1, 0)
                        } else {
                            navigation.currentContainerPosition =
                                (navigation.currentContainerPosition + 1).coerceAtMost(2)
                        }
                    }

                    Keys.UP -> {
                        if (gameController.state == GameState.Default) {
                            user.gameObject.position = user.gameObject.position + Position(0, -1)
                        } else {
                            currentNav.currentItemPosition =
                                (currentNav.currentItemPosition - 1).coerceAtLeast(0)
                        }
                    }

                    Keys.DOWN -> {
                        if (gameController.state == GameState.Default) {
                            user.gameObject.position = user.gameObject.position + Position(0, 1)
                        } else {
                            if (currentNav.container is UserEquipment) {
                                currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                                    .coerceAtMost(4)
                            } else {
                                currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                                    .coerceAtMost(currentNav.container.getItemsList().size - 1)
                            }
                        }
                    }

                    Keys.ESC -> {
                        signal()
                        frame.dispose()
                    }

                    Keys.SPACE -> {
                        gameController.state = when (gameController.state) {
                            GameState.Default -> GameState.Inventory
                            GameState.Inventory -> GameState.Death
                            GameState.Death -> GameState.Win
                            GameState.Win -> GameState.Default
                        }
                    }
                }
                rerender()
            }

            // Logic
        }
    }
}