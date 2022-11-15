package entity.models

import controls.UserEvent
import controls.UserEventListener
import engine.GameObject
import generator.Characteristics
import inventory.UserInventory
import inventory.items.equipments.AbstractEquipment

class User(
    gameObject: GameObject,
    characteristics: Characteristics,
) : EntityWithCharacteristics(gameObject, characteristics), UserEventListener {
    val inventory: UserInventory = UserInventory()

    fun putOnEquipment(item: AbstractEquipment) {
        val prevItem = inventory.equipment.addItem(item)

        prevItem?.let {
            if (prevItem is AbstractEquipment) {
                undoBonuses(prevItem)
                inventory.storage.addItem(prevItem)
            }
        }

        applyBonuses(item)
    }

    fun takeOffEquipment(item: AbstractEquipment): Boolean =
        inventory.equipment.removeItem(item).also {
            if (it) {
                undoBonuses(item)
                inventory.storage.addItem(item)
            }
        }

    private fun applyBonuses(item: AbstractEquipment) {
        characteristics.healthPoints += item.healthPointsBonus
        characteristics.attackPoints += item.attackPointsBonus
        characteristics.protectionPoints += item.protectionPointsBonus
    }

    private fun undoBonuses(item: AbstractEquipment) {
        characteristics.healthPoints =
            (characteristics.healthPoints - item.healthPointsBonus).coerceAtLeast(1)
        characteristics.attackPoints -= item.attackPointsBonus
        characteristics.protectionPoints -= item.protectionPointsBonus
    }

    override fun onEvent(event: UserEvent) {
        TODO("Not yet implemented")
    }

    override fun onDeath() {
        TODO("Not yet implemented")
    }
}