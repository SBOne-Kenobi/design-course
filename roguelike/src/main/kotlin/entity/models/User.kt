package entity.models

import controls.Key
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.GameEngine
import engine.GameObject
import engine.Position
import entity.GameController
import generator.Characteristics
import inventory.UserInventory
import inventory.items.equipments.AbstractEquipment

class User(
    gameObject: GameObject,
    characteristics: Characteristics,
    private val engine: GameEngine
) : EntityWithCharacteristics(gameObject, characteristics), UserEventListener {
    lateinit var gameController: GameController

    val inventory: UserInventory = UserInventory()

    private fun interactWith(entity: Entity): Boolean =
        when (entity) {
            is Chest -> {
                entity.items.getItemsList().forEach { item ->
                    inventory.storage.addItem(item, entity.items.getItemAmount(item))
                }
                gameController.currentLevel.removeEntity(entity.id)
                true
            }
            else -> false
        }

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
        when (event) {
            is UserKeyEvent -> {
                val diff = when (event.key) {
                    Key.Left -> Position(-1, 0)
                    Key.Right -> Position(1, 0)
                    Key.Up -> Position(0, -1)
                    Key.Down -> Position(0, 1)
                    else -> return
                }
                val newPosition = gameObject.position + diff
                val objects = engine.getObjectsWithPosition(gameObject, newPosition)
                val canMove = objects.fold(true) { acc, obj ->
                    val entity = gameController.currentLevel.findEntity(obj.id)
                    val interact = entity?.let { interactWith(it) } ?: true
                    acc && interact
                }
                if (canMove) {
                    engine.moveObject(gameObject, newPosition)
                }
            }
        }
    }

    override fun onDeath() {
        TODO("Not yet implemented")
    }
}