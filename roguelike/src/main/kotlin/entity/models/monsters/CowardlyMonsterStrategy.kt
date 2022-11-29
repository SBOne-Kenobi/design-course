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

    override fun chooseNextPosition(block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = gameObject.position

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
}