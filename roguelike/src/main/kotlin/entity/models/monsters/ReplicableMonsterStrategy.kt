package entity.models.monsters

import engine.Position
import entity.TimeController
import entity.models.Monster
import kotlin.random.Random

class ReplicableMonsterStrategy(
    private val baseStrategy: MonsterStrategy,
) : MonsterStrategy {
    private val probabilityOfClone: Double = 0.05
    private val cloneController = TimeController(5000)

    override fun chooseNextPosition(monster: Monster, block: (Position) -> Boolean) {
        baseStrategy.chooseNextPosition(monster, block)
    }

    override fun cloneStage(monster: Monster): Boolean {
        if (!monster.isBashed() && cloneController.event(updateIfCan = false)) {
            if (Random.nextDouble() < probabilityOfClone) {
                cloneController.event(force = true)
                return monster.gameObject.position.getNeighbours()
                    .shuffled()
                    .firstOrNull {
                        monster.engine.getPlacedInPosition(it).firstOrNull() == null
                    }?.let { freePosition ->
                        val cloned = monster.clone()
                        monster.gameObject.position = freePosition
                        cloned.gameController.currentLevel.addEntity(cloned)
                        true
                    } ?: false
            }
        }
        return false
    }

    private constructor(other: ReplicableMonsterStrategy) : this(
        other.baseStrategy.clone()
    ) {
        cloneController.event(force = true)
    }

    override fun clone(): ReplicableMonsterStrategy =
        ReplicableMonsterStrategy(this)
}