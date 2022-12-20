package ui.commands

import entity.models.User
import inventory.items.equipments.AbstractEquipment
import ui.inventory.containers.ContainerWithNavigation

class TakeOffEquipmentCommand(private val user: User, private val navigation: ContainerWithNavigation<*, *>) : Command {
    override fun execute() {
        navigation.getCurrentItem()?.let {
            user.takeOffEquipment(it as AbstractEquipment)
        }
    }
}