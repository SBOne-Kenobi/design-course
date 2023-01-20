package entity.models

import engine.GameEngine
import engine.GameObject
import engine.Position
import entity.GameController
import entity.models.interaction.InteractionStrategy
import generator.Characteristics


/**
 * Entity with possibility of moving and interaction with other.
 */
abstract class MovableEntity(
    gameObject: GameObject,
    characteristics: Characteristics
) : EntityWithCharacteristics(gameObject, characteristics) {

    abstract val engine: GameEngine
    abstract val gameController: GameController
    protected abstract val interaction: InteractionStrategy

    /**
     * Try to move to [position] and interact if it's necessary.
     */
    fun moveTo(position: Position): Boolean {
        val objects = engine.getObjectsWithPosition(gameObject, position)
        val canMove = objects.fold(true) { acc, obj ->
            val entity = gameController.currentLevel.findEntity(obj.id)
            val interact = entity?.let { interaction.interactWith(it) } ?: true
            acc && interact
        }
        if (canMove) {
            return engine.moveObject(gameObject, position)
        }
        return false
    }

}