package generator.info

/**
 * Info for level generation.
 */
data class LevelInfo(
    val name: String,
    val description: String,
    val info: List<GenerationInfo>
) : GenerationInfo
