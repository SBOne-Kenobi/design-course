package generator

import generator.info.GenerationInfo

data class LevelInfo(
    val name: String,
    val description: String,
    val info: List<GenerationInfo>
)
