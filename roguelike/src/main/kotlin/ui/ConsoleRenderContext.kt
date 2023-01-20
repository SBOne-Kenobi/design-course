package ui

import com.varabyte.kotter.runtime.render.RenderScope

/**
 * Context for console rendering.
 */
data class ConsoleRenderContext<out T>(val renderScope: RenderScope, val data: T)
