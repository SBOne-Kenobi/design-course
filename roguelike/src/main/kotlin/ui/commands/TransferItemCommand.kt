package ui.commands

import inventory.containers.MutableItemsContainer
import ui.inventory.containers.ContainerWithNavigation

class TransferItemCommand(
    private val from: MutableItemsContainer,
    private val to: MutableItemsContainer,
    private val navigation: ContainerWithNavigation<*, *>
) : Command {
    override fun execute() {
        navigation.getCurrentItem()?.let { item ->
            from.removeItem(item)
            to.addItem(item)
        }
    }
}