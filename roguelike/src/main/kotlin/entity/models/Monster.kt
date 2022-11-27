package entity.models

import engine.GameObject
import generator.Characteristics
import generator.MonsterType
import inventory.containers.DefaultContainer

/**
 * Class that represents monster with [type].
 */
class Monster(
    gameObject: GameObject,
    characteristics: Characteristics,
    val type: MonsterType,
) : EntityWithCharacteristics(gameObject, characteristics) {
    val items: DefaultContainer = TODO()
    override fun onDeath() {
        TODO("Not yet implemented")
    }
}