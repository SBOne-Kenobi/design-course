package entity.models.monsters

import engine.Position
import entity.GameController
import entity.models.Monster

class SmartMonsterStrategy(
    private val cowardlyHP: Int,
    private val baseStrategy: MonsterStrategy,
    private val gameController: GameController,
) : MonsterStrategy {

    private interface State {
        val strategy: MonsterStrategy

        fun getState(monster: Monster): State
    }

    private val cowardlyState: State
        get() = object : State {
            override val strategy: MonsterStrategy = CowardlyMonsterStrategy(gameController)
            override fun getState(monster: Monster): State =
                if (monster.characteristics.healthPoints > cowardlyHP) {
                    normalState
                } else {
                    this
                }
        }

    private val normalState: State
        get() = object : State {
            override val strategy: MonsterStrategy = baseStrategy
            override fun getState(monster: Monster): State =
                if (monster.characteristics.healthPoints > cowardlyHP) {
                    this
                } else {
                    cowardlyState
                }
        }

    private var state = normalState

    private constructor(other: SmartMonsterStrategy) : this(
        other.cowardlyHP,
        other.baseStrategy.clone(),
        other.gameController
    )

    override fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean) {
        state = state.getState(monster)
        state.strategy.chooseNextPosition(monster, block)
    }

    override fun clone(): SmartMonsterStrategy =
        SmartMonsterStrategy(this)
}