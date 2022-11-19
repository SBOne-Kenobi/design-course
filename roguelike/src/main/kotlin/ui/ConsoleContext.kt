package ui

import engine.Position
import inventory.UserInventory
import ui.entities.UserBasedViewNavigation
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation


class ConsoleContext(
    val gameMapViewWidth: Int,
    val gameMapViewHeight: Int,
    val consoleWidth: Int,
    val consoleHeight: Int,
) {

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
}
