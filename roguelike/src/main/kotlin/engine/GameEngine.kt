package engine

class GameEngine {
    lateinit var currentScene: GameScene
        private set

    fun loadScene(scene: GameScene) {
        currentScene = scene
    }

    fun moveObject(obj: GameObject, newPosition: Position): Boolean {
        val fakeObject = GameObject(obj.id, newPosition, obj.shape)

        val objects = getIntersectedWith(fakeObject)

        if (objects.firstOrNull() == null) {
            obj.position = newPosition
            return true
        }

        return false
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
            .filter {
                gameObject != it && gameObject.shape.isIntersected(gameObject.position, it.shape, it.position)
            }

}