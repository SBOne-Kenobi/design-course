package generator

import engine.Position
import engine.RectShape
import generator.generators.base.IdGenerator
import generator.generators.base.WallGenerator
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WallGeneratorTest {
    @ParameterizedTest
    @MethodSource("getTestData")
    fun testGenerateWall(position: Position, width: Int, height: Int) {
        val idGenerator = IdGenerator()
        val generator = WallGenerator(idGenerator, position, width, height)
        val info = generator.generate()
        assertEquals(position, info.gameObject.position)
        assertEquals(RectShape(width, height), info.gameObject.shape)
        assertTrue(info.gameObject.isSolid)
    }

    companion object {
        @JvmStatic
        fun getTestData(): List<Arguments> = listOf(
            Arguments.of(
                Position(0, 0),
                1, 1,
            ),
            Arguments.of(
                Position(4, 2),
                3, 1,
            ),
            Arguments.of(
                Position(1, 8),
                1, 5,
            ),
            Arguments.of(
                Position(0, 5),
                2, 2,
            ),
        )
    }
}