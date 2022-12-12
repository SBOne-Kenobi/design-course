package entity.models.interaction

import entity.models.Entity
import entity.models.Monster
import kotlin.random.Random

class BashInteractionDecorator(
    private val base: InteractionStrategy,
    private val bashProbability: Double,
) : InteractionStrategy {
    private val random = Random

    override fun interactWith(entity: Entity): Boolean {
        if (entity is Monster) {
            if (random.nextDouble() < bashProbability) {
                entity.bash()
            }
        }
        return base.interactWith(entity)
    }
}