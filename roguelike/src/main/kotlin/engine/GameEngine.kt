package engine

class GameEngine {
    lateinit var currentScene: GameScene
        private set

    fun loadScene(scene: GameScene) {
        currentScene = scene
    }

    fun moveObject(obj: GameObject, newPosition: Position): Boolean {
        TODO()
    }

    fun areIntersected(objA: GameObject, objB: GameObject): Boolean {
        TODO()
    }

    fun getPlacedInPosition(position: Position, root: GameObject? = null): List<GameObject> {
        TODO()
    }

    fun getIntersectedWith(gameObject: GameObject, root: GameObject? = null): List<GameObject> {
        TODO()
    }

}