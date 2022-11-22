package generator.info

data class LevelInfo(
    val name: String,
    val description: String,
    val info: List<GenerationInfo>
) : GenerationInfo
