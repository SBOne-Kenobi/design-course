package ui

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import generator.Characteristics

/**
 * Rendering of characteristics.
 */
class CharacteristicsRenderer : ConsoleRenderer<Characteristics>() {
    override fun RenderScope.renderData(data: Characteristics) {
        textLine("HP:         ${data.healthPoints}/${data.maxHealthPoints}")
        textLine("Attack:     ${data.attackPoints}")
        textLine("Protection: ${data.protectionPoints}")
    }
}