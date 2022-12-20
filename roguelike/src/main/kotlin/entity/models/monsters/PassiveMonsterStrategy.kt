package entity.models.monsters

import engine.Position
import entity.GameController
import entity.TimeController
import entity.models.Monster

class PassiveMonsterStrategy(
    private val gameController: GameController
) : MonsterStrategy {

    private val attackController = TimeController(1000)

    override fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean) {
        val userPosition = gameController.user.gameObject.position
        val currentPosition = monster.gameObject.position
        var targetPosition = currentPosition

        if ((userPosition - currentPosition).length <= 1) {
            if (attackController.event()) {
                targetPosition = userPosition
            }
        }
        block(targetPosition)
    }

    private constructor(other: PassiveMonsterStrategy) : this(other.gameController)

    override fun clone(): PassiveMonsterStrategy =
        PassiveMonsterStrategy(this)
}