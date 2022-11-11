package entity.models

import engine.GameObject
import generator.Characteristics
import generator.MonsterType
import inventory.containers.DefaultContainer

class Monster(
    gameObject: GameObject,
    characteristics: Characteristics,
    val type: MonsterType,
) : EntityWithCharacteristics(gameObject, characteristics) {
    val items: DefaultContainer = TODO()
}