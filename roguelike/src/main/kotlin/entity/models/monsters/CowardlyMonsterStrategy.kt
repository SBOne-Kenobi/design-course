package entity.models.monsters

import engine.Position
import entity.GameController
import entity.TimeController
import entity.models.Monster

class CowardlyMonsterStrategy(
    private val gameController: GameController,
) : MonsterStrategy {
    private val moveController = TimeController(200)

    override fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = monster.gameObject.position

        val canMove = moveController.event()
        if (canMove) {
            val nextPositions = currentPosition.getNeighbours()
                .shuffled()
                .sortedByDescending { (it - userPosition).length }
            for (nextPosition in nextPositions) {
                if (block(nextPosition)) {
                    break
                }
            }
        }
    }

    private constructor(other: CowardlyMonsterStrategy) : this(other.gameController)

    override fun clone(): CowardlyMonsterStrategy =
        CowardlyMonsterStrategy(this)
}