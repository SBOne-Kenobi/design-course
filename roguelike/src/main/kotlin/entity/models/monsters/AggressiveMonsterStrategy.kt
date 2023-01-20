package entity.models.monsters

import engine.Position
import entity.GameController
import entity.TimeController
import entity.models.Monster

class AggressiveMonsterStrategy(
    private val gameController: GameController,
) : MonsterStrategy {
    private val attackController = TimeController(300)
    private val moveController = TimeController(500)

    override fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = monster.gameObject.position

        val nextPosition = currentPosition.getNeighbours()
            .shuffled()
            .minBy { (it - userPosition).length }
        val can = if (nextPosition == userPosition) {
            attackController.event()
        } else {
            moveController.event()
        }
        if (can) {
            block(nextPosition)
        } else {
            block(currentPosition)
        }
    }

    private constructor(other: AggressiveMonsterStrategy) : this(other.gameController)

    override fun clone(): AggressiveMonsterStrategy =
        AggressiveMonsterStrategy(this)
}