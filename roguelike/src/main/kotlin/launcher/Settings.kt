package launcher

import generator.FantasyStyle
import generator.MonsterStyle
import generator.ScifiStyle

/**
 * Global game settings.
 */
object Settings {
    const val consoleTitle = "Roguelike"
    const val consoleWidth = 100
    const val consoleHeight = 25

    const val gameMapViewWidth = 50
    const val gameMapViewHeight = 15

    const val useBash = false
    val monsterStyle: MonsterStyle = FantasyStyle
}