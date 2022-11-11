package entity.models

import engine.GameObject

abstract class Entity(val gameObject: GameObject) {
    open fun tick() {}
}