package entity.models.monsters

import engine.GameObject
import engine.Position
import entity.GameController
import entity.TimeController

class AggressiveMonsterStrategy(
    private val gameObject: GameObject,
    private val gameController: GameController,
) : MonsterStrategy {
    private val attackController = TimeController(300)
    private val moveController = TimeController(500)

    private fun getPossibleMoves(position: Position) = listOf(
        position + Position(1, 0),
        position + Position(-1, 0),
        position + Position(0, 1),
        position + Position(0, -1),
    )

    override fun chooseNextPosition(block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = gameObject.position

        val nextPosition = getPossibleMoves(currentPosition)
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
}