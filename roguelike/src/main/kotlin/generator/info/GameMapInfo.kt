package generator.info

/**
 * Info for the game map generation.
 */
data class GameMapInfo(
    val levels: List<LevelInfo>
) : GenerationInfo
