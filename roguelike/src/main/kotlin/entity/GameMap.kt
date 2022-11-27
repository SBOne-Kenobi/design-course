package entity

/**
 * Class contains all levels.
 */
data class GameMap(val levels: List<Level>) {

    /**
     * Get next level of [level].
     */
    fun getNextLevel(level: Level): Level? {
        return levels.getOrNull(levels.indexOf(level) + 1)
    }

    /**
     * Get previous level of [level].
     */
    fun getPrevLevel(level: Level): Level? {
        return levels.getOrNull(levels.indexOf(level) - 1)
    }
}