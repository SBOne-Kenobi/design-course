package ui

import com.varabyte.kotter.runtime.render.RenderScope

abstract class ConsoleRenderer<in T> : Renderer<ConsoleRenderContext<T>> {
    final override fun render(obj: ConsoleRenderContext<T>) {
        obj.renderScope.run {
            renderData(obj.data)
        }
    }

    abstract fun RenderScope.renderData(data: T)
}
