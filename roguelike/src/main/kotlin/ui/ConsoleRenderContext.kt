package ui

import com.varabyte.kotter.runtime.render.RenderScope

data class ConsoleRenderContext<out T>(val renderScope: RenderScope, val data: T)

fun <T> RenderScope.makeContext(data: T) =
    ConsoleRenderContext(this, data)
