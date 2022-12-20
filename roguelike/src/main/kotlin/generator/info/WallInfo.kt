package generator.info

import engine.GameObject

/**
 * Info for wall generation.
 */
data class WallInfo(
    val gameObject: GameObject
) : GenerationInfo
