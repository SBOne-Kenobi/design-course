package entity.models.monsters

import entity.GameController
import generator.AggressiveMonsterType
import generator.CowardlyMonsterType
import generator.MonsterType
import generator.PassiveMonsterType
import generator.ReplicableMonsterType

/**
 * Factory of a monster's strategy.
 */
class MonsterStrategyFactory {
    fun getStrategy(
        type: MonsterType,
        gameController: GameController,
    ): MonsterStrategy = when (type) {
        is PassiveMonsterType -> PassiveMonsterStrategy(gameController)
        is AggressiveMonsterType -> AggressiveMonsterStrategy(gameController)
        is CowardlyMonsterType -> CowardlyMonsterStrategy(gameController)
        is ReplicableMonsterType -> ReplicableMonsterStrategy(getStrategy(type.baseType, gameController))
    }
}