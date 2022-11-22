package entity.models

import engine.GameObject
import inventory.containers.DefaultContainer

class Chest(
    gameObject: GameObject,
    val items: DefaultContainer,
) : Entity(gameObject)
