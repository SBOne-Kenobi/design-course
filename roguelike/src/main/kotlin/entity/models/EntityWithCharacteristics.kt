package entity.models

import engine.GameObject
import generator.Characteristics

abstract class EntityWithCharacteristics(
    gameObject: GameObject,
    val characteristics: Characteristics
) : Entity(gameObject) {
    abstract fun onDeath()

    fun reduceHealth(damage: Int) {
        characteristics.healthPoints -= (damage - characteristics.protectionPoints).coerceAtLeast(0)
        if (characteristics.healthPoints <= 0) {
            onDeath()
        }
    }
}