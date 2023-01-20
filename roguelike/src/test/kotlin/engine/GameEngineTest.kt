package engine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameEngineTest {

    @Test
    fun testMoveObject() {
        val engine = GameEngine()
        val scene = GameScene().apply {
            registerObject(GameObject(0, Position(0, 0), RectShape()))
            registerObject(GameObject(1, Position(1, 1), RectShape()))
        }
        engine.loadScene(scene)

        assertTrue {
            engine.moveObject(scene.findObjectById(0)!!, Position(0, 1))
        }
        assertFalse {
            engine.moveObject(scene.findObjectById(0)!!, Position(1, 1))
        }
    }

    @Test
    fun testGetPlacedInPosition() {
        val engine = GameEngine()
        val scene = GameScene().apply {
            registerObject(GameObject(0, Position(0, 0), RectShape(2, 2)))
            registerObject(GameObject(1, Position(1, 1), RectShape()))
        }
        engine.loadScene(scene)

        assertEquals(2, engine.getPlacedInPosition(Position(1, 1)).count())
        assertEquals(1, engine.getPlacedInPosition(Position(0, 0)).count())
    }

    @Test
    fun testGetIntersectedWith() {
        val engine = GameEngine()
        val obj1 = GameObject(0, Position(0, 0), RectShape(2, 2))
        val obj2 = GameObject(1, Position(1, 1), RectShape())
        val scene = GameScene().apply {
            registerObject(obj1)
            registerObject(obj2)
        }
        engine.loadScene(scene)

        assertEquals(obj2, engine.getIntersectedWith(obj1).single())
        assertEquals(obj1, engine.getIntersectedWith(obj2).single())
    }

    @Test
    fun testGetObjectsWithPosition() {
        val engine = GameEngine()
        val obj1 = GameObject(0, Position(0, 0), RectShape())
        val obj2 = GameObject(1, Position(0, 1), RectShape())
        val scene = GameScene().apply {
            registerObject(obj1)
            registerObject(obj2)
        }
        engine.loadScene(scene)

        assertTrue {
            engine.getObjectsWithPosition(obj1, Position(1, 0)).none()
        }
        assertEquals(obj2, engine.getObjectsWithPosition(obj1, Position(0, 1)).single())
    }

}
