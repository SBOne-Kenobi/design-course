package entity.models.monsters

import engine.GameObject
import engine.Position
import entity.GameController
import entity.TimeController

class CowardlyMonsterStrategy(
    private val gameObject: GameObject,
    private val gameController: GameController,
) : MonsterStrategy {
    private val moveController = TimeController(200)

    private fun getPossibleMoves(position: Position) = listOf(
        position + Position(1, 0),
        position + Position(-1, 0),
        position + Position(0, 1),
        position + Position(0, -1),
    )

    override fun chooseNextPosition(block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = gameObject.position

        val canMove = moveController.event()
        if (canMove) {
            val nextPositions = getPossibleMoves(currentPosition)
                .shuffled()
                .sortedByDescending { (it - userPosition).length }
            for (nextPosition in nextPositions) {
                if (block(nextPosition)) {
                    break
                }
            }
        }
    }
}