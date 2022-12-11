package entity.models

import engine.GameEngine
import engine.GameObject
import entity.GameController
import entity.TimeController
import entity.models.interaction.InteractionStrategy
import entity.models.monsters.MonsterStrategy
import generator.Characteristics
import generator.MonsterStyle
import generator.MonsterType
import inventory.containers.DefaultContainer

/**
 * Class that represents monster with [type].
 */
class Monster(
    gameObject: GameObject,
    characteristics: Characteristics,
    val name: String,
    val items: DefaultContainer,
    val type: MonsterType,
    val style: MonsterStyle,
    val strategy: MonsterStrategy,
    override val engine: GameEngine,
    override val gameController: GameController,
) : MovableEntity(gameObject, characteristics), InteractionStrategy {

    private val bashController = TimeController(2500)

    override fun interactWith(entity: Entity): Boolean = when (entity) {
        is User -> entity.reduceHealth(characteristics.attackPoints)
        else -> false
    }

    override fun tick() {
        if (bashController.event(updateIfCan = false)) {
            strategy.chooseNextPosition { newPosition ->
                moveTo(newPosition)
            }
        }
    }

    fun bash() {
        bashController.event(force = true)
        for (position in gameObject.position.getNeighbours()) {
            if (engine.moveObject(gameObject, position))
                break
        }
    }

    override val interaction: InteractionStrategy = this

    override fun onDeath() {
        gameController.currentLevel.removeEntity(gameObject.id)
    }
}