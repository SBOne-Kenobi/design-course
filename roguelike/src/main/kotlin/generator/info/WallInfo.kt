package generator.info

import engine.GameObject

data class WallInfo(
    override val gameObject: GameObject
) : GenerationInfo
