package ui

import com.varabyte.kotter.foundation.text.textLine
import com.varabyte.kotter.runtime.render.RenderScope
import entity.leveling.UserExperience


/**
 * Renderer of the user's experience.
 */
class LevelingRenderer : ConsoleRenderer<UserExperience>() {
    override fun RenderScope.renderData(data: UserExperience) {
        textLine("Level:      ${data.level}")
        textLine("XP:         ${data.experiencePoints}/${data.experiencePointsInCurrentLevel}")
    }
}