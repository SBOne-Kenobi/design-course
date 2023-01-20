package entity.models.monsters

import engine.Position
import engine.Prototype
import entity.models.Monster

/**
 * Strategy of a monster's behaviour.
 */
interface MonsterStrategy: Prototype<MonsterStrategy> {
    fun cloneStage(monster: Monster): Boolean =
        false

    fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean)
}