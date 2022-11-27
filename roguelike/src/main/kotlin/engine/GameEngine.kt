package engine

/**
 * [GameEngine] provides a functionality of control [GameObject]s and checking their intersections.
 */
class GameEngine {
    /**
     * Get loaded [GameScene].
     */
    lateinit var currentScene: GameScene
        private set

    /**
     * Load new [scene].
     */
    fun loadScene(scene: GameScene) {
        currentScene = scene
    }

    /**
     * Get objects that are on the way of [obj] to [newPosition].
     */
    fun getObjectsWithPosition(obj: GameObject, newPosition: Position): Sequence<GameObject> {
        val fakeObject = GameObject(obj.id, newPosition, obj.shape)

        return getIntersectedWith(fakeObject)
    }

    /**
     * Tries to move [obj] onto [newPosition].
     *
     * It fails if there is any object on the way.
     */
    fun moveObject(obj: GameObject, newPosition: Position): Boolean {
        val objects = getObjectsWithPosition(obj, newPosition)

        if (objects.firstOrNull() == null) {
            obj.position = newPosition
            return true
        }

        return false
    }

    /**
     * Checks that two [GameObject]s are intersected.
     */
    fun areIntersected(objA: GameObject, objB: GameObject): Boolean =
        objA.shape.isIntersected(objA.position, objB.shape, objB.position)

    /**
     * Get [GameObject]s that contain the specified [position].
     */
    fun getPlacedInPosition(position: Position): Sequence<GameObject> =
        currentScene.objects
            .asSequence()
            .filter { position - it.position in it.shape }

    /**
     * Get [GameObject]s intersected with [gameObject].
     */
    fun getIntersectedWith(gameObject: GameObject): Sequence<GameObject> =
        currentScene.objects
            .asSequence()
            .filter {
                gameObject != it && gameObject.shape.isIntersected(gameObject.position, it.shape, it.position)
            }

}