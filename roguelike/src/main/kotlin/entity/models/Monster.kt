package entity.models

import engine.GameEngine
import engine.GameObject
import entity.GameController
import entity.models.monsters.MonsterStrategy
import generator.Characteristics
import generator.MonsterType
import inventory.containers.DefaultContainer

/**
 * Class that represents monster with [type].
 */
class Monster(
    gameObject: GameObject,
    characteristics: Characteristics,
    val items: DefaultContainer,
    val type: MonsterType,
    val strategy: MonsterStrategy,
    override val engine: GameEngine,
    override val gameController: GameController,
) : MovableEntity(gameObject, characteristics) {

    override fun interactWith(entity: Entity): Boolean = when (entity) {
        is User -> entity.reduceHealth(characteristics.attackPoints)
        else -> false
    }

    override fun tick() {
        strategy.chooseNextPosition { newPosition ->
            moveTo(newPosition)
        }
    }

    override fun onDeath() {
        gameController.currentLevel.removeEntity(gameObject.id)
    }
}