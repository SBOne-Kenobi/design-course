package ui

import inventory.UserInventory
import ui.inventory.containers.ContainerWithNavigation
import ui.inventory.containers.ManyContainersWithNavigation


class ConsoleContext(
    val gameMapViewWidth: Int,
    val gameMapViewHeight: Int,
    val consoleWidth: Int,
    val consoleHeight: Int,
) {

    private var navigation: ManyContainersWithNavigation? = null

    fun getUserInventoryWithNavigation(userInventory: UserInventory): ManyContainersWithNavigation {
        if (navigation == null) {
            navigation = ManyContainersWithNavigation(
                listOf(
                    ContainerWithNavigation(userInventory.equipment),
                    ContainerWithNavigation(userInventory.storage),
                    ContainerWithNavigation(userInventory.pot)
                ),
                currentContainerPosition = 1
            )
        }
        return navigation!!
    }
}
