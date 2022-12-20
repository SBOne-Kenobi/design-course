package generator.generators.base

import generator.FantasyStyle
import generator.MonsterStyle
import generator.ScifiStyle
import generator.generators.InfoGenerator
import generator.generators.levels.SimpleLevel
import generator.info.GameMapInfo
import generator.styles.AbstractMonsterInfoFactory
import generator.styles.FantasyMonsterInfoFactory
import generator.styles.ScifiMonsterInfoFactory
import kotlin.properties.Delegates

/**
 * The game map generator.
 */
class MapGenerator : InfoGenerator<GameMapInfo> {

    private var width by Delegates.notNull<Int>()
    private var height by Delegates.notNull<Int>()
    private lateinit var style: MonsterStyle

    fun setWidth(width: Int): MapGenerator {
        this.width = width
        return this
    }

    fun setHeight(height: Int): MapGenerator {
        this.height = height
        return this
    }

    fun setMonsterStyle(style: MonsterStyle): MapGenerator {
        this.style = style
        return this
    }

    private fun getMonsterInfoFactory(style: MonsterStyle): AbstractMonsterInfoFactory = when (style) {
        FantasyStyle -> FantasyMonsterInfoFactory()
        ScifiStyle -> ScifiMonsterInfoFactory()
    }

    override fun generate(): GameMapInfo {
        return GameMapInfo(
            listOf(
                SimpleLevel(width, height, getMonsterInfoFactory(style)).generate()
            )
        )
    }
}