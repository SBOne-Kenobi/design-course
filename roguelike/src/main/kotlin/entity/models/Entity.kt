package entity.models

import engine.GameObject

abstract class Entity(val gameObject: GameObject) {
    val id: Int
        get() = gameObject.id

    open fun tick() {}
}