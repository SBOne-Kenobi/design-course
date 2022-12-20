package entity.models

import engine.GameObject

/**
 * The object placed on the game map.
 */
abstract class Entity(val gameObject: GameObject) {
    val id: Int
        get() = gameObject.id

    /**
     * Called on a next tick of the game.
     */
    open fun tick() {}
}