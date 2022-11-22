package generator.generators

import engine.Position
import generator.info.GenerationInfo
import generator.info.LevelInfo

abstract class AbstractLevelGenerator(
    private val name: String,
    private val description: String,
    private val width: Int,
    private val height: Int,
    private val idGenerator: IdGenerator,
) : InfoGenerator<LevelInfo> {
    protected val info = mutableListOf<GenerationInfo>()

    override fun generate(): LevelInfo {
        addBorders()
        addObstacles()
        addChests()
        addMonsters()
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
}