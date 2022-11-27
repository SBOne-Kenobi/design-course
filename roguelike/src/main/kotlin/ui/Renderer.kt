package ui

/**
 * Class for rendering.
 */
interface Renderer<in T> {
    fun render(obj: T)
}