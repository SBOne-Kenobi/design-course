package entity.models.monsters

import engine.GameObject
import engine.Position
import entity.GameController
import entity.TimeController

class PassiveMonsterStrategy(
    private val gameObject: GameObject,
    private val gameController: GameController
) : MonsterStrategy {

    private val attackController = TimeController(1000)

    override fun chooseNextPosition(block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = gameObject.position
        var targetPosition = currentPosition

        if ((userPosition - currentPosition).length <= 1) {
            if (attackController.event()) {
                targetPosition = userPosition
            }
        }
        block(targetPosition)
    }
}