package entity.models

import engine.GameObject
import generator.Characteristics

abstract class EntityWithCharacteristics(
    gameObject: GameObject,
    val characteristics: Characteristics
) : Entity(gameObject) {
    fun reduceHealth(damage: Int) {
        TODO()
    }
}