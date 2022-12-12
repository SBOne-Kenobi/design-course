package generator.generators

import engine.Position
import generator.generators.base.IdGenerator
import generator.generators.base.WallGenerator
import generator.info.GenerationInfo
import generator.info.LevelInfo

/**
 * Abstract level generator.
 *
 * There are several steps for level generation:
 * 1. generate borders of level
 * 2. generate any obstacles within level
 * 3. generate chests
 * 4. generate monster
 * 5. and generate something additional
 */
abstract class AbstractLevelGenerator(
    protected val name: String,
    protected val description: String,
    protected val width: Int,
    protected val height: Int,
    protected val idGenerator: IdGenerator,
) : InfoGenerator<LevelInfo> {
    protected val info = mutableListOf<GenerationInfo>()

    override fun generate(): LevelInfo {
        addBorders()
        addObstacles()
        addChests()
        addMonsters()
        addAdditional()
        return LevelInfo(name, description, info.toList())
    }

    protected fun addBorders() {
        addWall(Position(0, 0), height = height)
        addWall(Position(0, 0), width = width)
        addWall(Position(width - 1, 0), height = height)
        addWall(Position(0, height - 1), width = width)
    }

    protected fun addWall(position: Position, width: Int = 1, height: Int = 1) {
        info.add(WallGenerator(idGenerator, position, width, height).generate())
    }

    abstract fun addObstacles()

    abstract fun addChests()

    abstract fun addMonsters()

    abstract fun addAdditional()
}