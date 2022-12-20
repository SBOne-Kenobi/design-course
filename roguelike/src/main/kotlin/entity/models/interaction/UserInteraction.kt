package entity.models.interaction

import entity.models.Chest
import entity.models.Entity
import entity.models.Monster
import entity.models.User

class UserInteraction(private val user: User) : InteractionStrategy {
    override fun interactWith(entity: Entity): Boolean =
        when (entity) {
            is Chest -> {
                entity.items.getItemsList().forEach { item ->
                    user.inventory.storage.addItem(item, entity.items.getItemAmount(item))
                }
                user.gameController.currentLevel.removeEntity(entity.id)
                true
            }
            is Monster -> entity.reduceHealth(user.characteristics.attackPoints).also { killed ->
                if (killed) {
                    entity.items.getItemsList().forEach { item ->
                        val amount = entity.items.getItemAmount(item)
                        user.inventory.storage.addItem(item, amount)
                    }
                    user.userExperience.addExp(entity.type.experiencePoints)
                }
            }

            else -> false
        }
}