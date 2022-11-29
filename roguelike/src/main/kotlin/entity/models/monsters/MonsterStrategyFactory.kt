package entity.models.monsters

import engine.GameObject
import entity.GameController
import generator.MonsterType
import generator.PassiveMonsterType

class MonsterStrategyFactory {
    fun getStrategy(
        type: MonsterType,
        gameObject: GameObject,
        gameController: GameController,
    ): MonsterStrategy = when (type) {
        is PassiveMonsterType -> PassiveMonsterStrategy(gameObject, gameController)
    }
}