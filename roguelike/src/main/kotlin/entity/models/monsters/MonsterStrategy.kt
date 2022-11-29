package entity.models.monsters

import engine.Position

/**
 * Strategy of a monster's behaviour.
 */
interface MonsterStrategy {
    fun chooseNextPosition(block: (Position) -> Boolean)
}