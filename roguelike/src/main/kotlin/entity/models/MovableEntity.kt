package entity.models

import engine.GameEngine
import engine.GameObject
import engine.Position
import entity.GameController
import generator.Characteristics


/**
 * Entity with possibility of moving and interaction with other.
 */
abstract class MovableEntity(
    gameObject: GameObject,
    characteristics: Characteristics
) : EntityWithCharacteristics(gameObject, characteristics) {

    protected abstract val engine: GameEngine
    protected abstract val gameController: GameController

    protected open fun interactWith(entity: Entity): Boolean {
        return false
    }

    /**
     * Try to move to [position] and interact if it's necessary.
     */
    fun moveTo(position: Position): Boolean {
        val objects = engine.getObjectsWithPosition(gameObject, position)
        val canMove = objects.fold(true) { acc, obj ->
            val entity = gameController.currentLevel.findEntity(obj.id)
            val interact = entity?.let { interactWith(it) } ?: true
            acc && interact
        }
        if (canMove) {
            return engine.moveObject(gameObject, position)
        }
        return false
    }

}