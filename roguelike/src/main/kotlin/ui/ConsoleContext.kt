package ui

import controls.Key
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.Position
import inventory.UserInventory
import inventory.containers.UserEquipment
import inventory.items.EquipmentType
import ui.entities.UserBasedViewNavigation
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation


class ConsoleContext(
    val gameMapViewWidth: Int,
    val gameMapViewHeight: Int,
    val consoleWidth: Int,
    val consoleHeight: Int,
) : UserEventListener {

    private var containersNavigation: ManyContainersWithNavigation? = null

    private var viewNavigation: UserBasedViewNavigation? = null

    fun getUserInventoryWithNavigation(userInventory: UserInventory): ManyContainersWithNavigation {
        if (containersNavigation == null) {
            containersNavigation = ManyContainersWithNavigation(
                listOf(
                    ContainerWithNavigation(userInventory.equipment),
                    ContainerWithNavigation(userInventory.storage),
                    ContainerWithNavigation(userInventory.pot)
                ),
                currentContainerPosition = 1
            )
        }
        return containersNavigation!!
    }

    fun getViewNavigation(userPosition: Position): UserBasedViewNavigation {
        if (viewNavigation == null) {
            viewNavigation = UserBasedViewNavigation(
                userPosition,
                userPosition - Position(gameMapViewWidth / 2, gameMapViewHeight / 2)
            )
        }
        return viewNavigation!!.apply {
            this.userPosition = userPosition
        }
    }

    override fun onEvent(event: UserEvent) {
        val navigation = containersNavigation ?: return
        val currentNav = navigation.containers[navigation.currentContainerPosition]
        when (event) {
            is UserKeyEvent -> when (event.key) {
                Key.Left -> {
                    navigation.currentContainerPosition =
                        (navigation.currentContainerPosition - 1).coerceAtLeast(0)
                }

                Key.Right -> {
                    navigation.currentContainerPosition =
                        (navigation.currentContainerPosition + 1).coerceAtMost(2)
                }

                Key.Up -> {
                    currentNav.currentItemPosition =
                        (currentNav.currentItemPosition - 1).coerceAtLeast(0)
                }

                Key.Down -> {
                    if (currentNav.container is UserEquipment) {
                        currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                            .coerceAtMost(EquipmentType.values().size - 2)
                    } else {
                        currentNav.currentItemPosition = (currentNav.currentItemPosition + 1)
                            .coerceAtMost(currentNav.container.getItemsList().size - 1)
                    }
                }

                else -> {}
            }
        }
    }
}
