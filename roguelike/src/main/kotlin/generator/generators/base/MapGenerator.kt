package generator.generators.base

import generator.generators.InfoGenerator
import generator.generators.levels.SimpleLevel
import generator.info.GameMapInfo

class MapGenerator : InfoGenerator<GameMapInfo> {
    override fun generate(): GameMapInfo {
        return GameMapInfo(
            listOf(
                SimpleLevel().generate()
            )
        )
    }
}