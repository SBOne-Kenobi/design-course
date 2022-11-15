import com.varabyte.kotter.foundation.input.Keys
import com.varabyte.kotter.foundation.input.onKeyPressed
import com.varabyte.kotter.foundation.runUntilSignal
import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.terminal.virtual.TerminalSize
import com.varabyte.kotter.terminal.virtual.VirtualTerminal
import entity.GameController
import entity.models.User
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
    val gameController = GameController()
    val user = gameController.currentLevel.entities.first { it is User } as User

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
                        navigation.currentContainerPosition =
                            (navigation.currentContainerPosition - 1).coerceAtLeast(0)
                    }
                    Keys.RIGHT -> {
                        navigation.currentContainerPosition =
                            (navigation.currentContainerPosition + 1).coerceAtMost(2)
                    }
                    Keys.UP -> {
                        currentNav.currentItemPosition =
                            (currentNav.currentItemPosition - 1).coerceAtLeast(0)
                    }
                    Keys.DOWN -> {
                        if (currentNav.container is UserEquipment) {
                            currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                                .coerceAtMost(4)
                        } else {
                            currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                                .coerceAtMost(currentNav.container.getItemsList().size - 1)
                        }
                    }
                    Keys.ESC -> {
                        signal()
                        frame.dispose()
                    }
                }
                rerender()
            }

            // Logic
        }
    }
}