package entity.models

import engine.GameObject
import generator.Characteristics

/**
 * Entity with [Characteristics].
 */
abstract class EntityWithCharacteristics(
    gameObject: GameObject,
    val characteristics: Characteristics
) : Entity(gameObject) {

    /**
     * Called when health of the entity less or equal zero.
     */
    abstract fun onDeath()

    /**
     * Recalculate entity's heath with given [damage].
     */
    fun reduceHealth(damage: Int): Boolean {
        characteristics.healthPoints -= (damage - characteristics.protectionPoints).coerceAtLeast(0)
        if (characteristics.healthPoints <= 0) {
            onDeath()
            return true
        }
        return false
    }
}