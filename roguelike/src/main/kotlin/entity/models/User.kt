package entity.models

import controls.Key
import controls.UserEvent
import controls.UserEventListener
import controls.UserKeyEvent
import engine.GameEngine
import engine.GameObject
import engine.Position
import entity.GameController
import entity.leveling.UserExperience
import generator.Characteristics
import inventory.UserInventory
import inventory.items.equipments.AbstractEquipment


/**
 * User's class.
 */
class User(
    gameObject: GameObject,
    characteristics: Characteristics,
    override val engine: GameEngine,
    override val gameController: GameController,
) : MovableEntity(gameObject, characteristics), UserEventListener {

    /**
     * The user's inventory.
     */
    val inventory: UserInventory = UserInventory()

    /**
     * The user's experience points.
     */
    val userExperience: UserExperience = UserExperience()

    override fun interactWith(entity: Entity): Boolean =
        when (entity) {
            is Chest -> {
                entity.items.getItemsList().forEach { item ->
                    inventory.storage.addItem(item, entity.items.getItemAmount(item))
                }
                gameController.currentLevel.removeEntity(entity.id)
                true
            }
            is Monster -> entity.reduceHealth(characteristics.attackPoints).also { killed ->
                if (killed) {
                    entity.items.getItemsList().forEach { item ->
                        val amount = entity.items.getItemAmount(item)
                        inventory.storage.addItem(item, amount)
                    }
                    userExperience.addExp(entity.type.experiencePoints)
                }
            }

            else -> false
        }

    /**
     * Put on equipment [item] from the user's storage and apply bonuses from it.
     */
    fun putOnEquipment(item: AbstractEquipment) {
        if (inventory.storage.removeItem(item)) {
            val prevItem = inventory.equipment.addItem(item)

            prevItem?.let {
                if (prevItem is AbstractEquipment) {
                    undoBonuses(prevItem)
                    inventory.storage.addItem(prevItem)
                }
            }

            applyBonuses(item)
        }
    }

    /**
     * Take off [item] and undo bonuses from it.
     */
    fun takeOffEquipment(item: AbstractEquipment): Boolean =
        inventory.equipment.removeItem(item).also {
            if (it) {
                undoBonuses(item)
                inventory.storage.addItem(item)
            }
        }

    /**
     * Create new item from items in the user's magic pot.
     */
    fun createFromMagicPot(): Boolean {
        val recipe = inventory.run {
            grimoire.getMatchRecipe(pot.getItemsList().associateWith {
                pot.getItemAmount(it)
            })
        } ?: return false
        return inventory.pot.applyRecipe(recipe)?.let {
            inventory.storage.addItem(it)
            true
        } ?: false
    }

    private fun applyBonuses(item: AbstractEquipment) {
        characteristics.healthPoints += item.healthPointsBonus
        characteristics.maxHealthPoints += item.healthPointsBonus
        characteristics.attackPoints += item.attackPointsBonus
        characteristics.protectionPoints += item.protectionPointsBonus
    }

    private fun undoBonuses(item: AbstractEquipment) {
        characteristics.healthPoints =
            (characteristics.healthPoints - item.healthPointsBonus).coerceAtLeast(1)
        characteristics.maxHealthPoints -= item.healthPointsBonus
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
                moveTo(newPosition)
            }
        }
    }

    override fun onDeath() {
        gameController.userDeath()
    }
}