package entity.models

import engine.GameObject
import inventory.containers.DefaultContainer

/**
 * Chest object with [items].
 */
class Chest(
    gameObject: GameObject,
    val items: DefaultContainer,
) : Entity(gameObject)
