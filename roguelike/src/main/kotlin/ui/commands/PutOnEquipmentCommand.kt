package ui.commands

import entity.models.User
import inventory.items.equipments.AbstractEquipment
import ui.inventory.containers.ContainerWithNavigation

class PutOnEquipmentCommand(private val user: User, private val navigation: ContainerWithNavigation<*, *>) : Command {
    override fun execute() {
        navigation.getCurrentItem()?.let {
            if (it is AbstractEquipment) {
                user.putOnEquipment(it)
            }
        }
    }
}