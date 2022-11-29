package entity.models.interaction

import entity.models.Entity

/**
 * Interaction strategy with other entities.
 */
interface InteractionStrategy {
    fun interactWith(entity: Entity): Boolean
}