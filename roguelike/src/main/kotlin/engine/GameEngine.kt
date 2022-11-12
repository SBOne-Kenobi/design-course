package engine

class GameEngine {
    lateinit var currentScene: GameScene
        private set

    fun loadScene(scene: GameScene) {
        currentScene = scene
    }

    fun moveObject(obj: GameObject, newPosition: Position): Boolean {
        val objects = getPlacedInPosition(newPosition)
            .filter { it.isSolid && it != obj }
        if (objects.firstOrNull() == null) {
            return false
        }
        obj.position = newPosition
        return true
    }

    fun areIntersected(objA: GameObject, objB: GameObject): Boolean =
        objA.shape.isIntersected(objA.position, objB.shape, objB.position)

    fun getPlacedInPosition(position: Position): Sequence<GameObject> =
        currentScene.objects
            .asSequence()
            .filter { position - it.position in it.shape }

    fun getIntersectedWith(gameObject: GameObject): Sequence<GameObject> =
        currentScene.objects
            .asSequence()
            .filter { gameObject.shape.isIntersected(gameObject.position, it.shape, it.position) }

}