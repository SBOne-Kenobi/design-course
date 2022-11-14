package entity.models

import engine.GameObject
import generator.Characteristics

abstract class EntityWithCharacteristics(
    gameObject: GameObject,
    val characteristics: Characteristics
) : Entity(gameObject) {
    fun increaseHealth(shift: Double) = characteristics.shiftHealth(shift)

    fun decreaseHealth(shift: Double) = characteristics.shiftHealth(-shift)

    fun increaseAttack(shift: Double) = characteristics.shiftAttack(shift)

    fun decreaseAttack(shift: Double) = characteristics.shiftAttack(-shift)

    fun increaseProtection(shift: Double) = characteristics.shiftProtection(shift)

    fun decreaseProtection(shift: Double) = characteristics.shiftProtection(-shift)
}