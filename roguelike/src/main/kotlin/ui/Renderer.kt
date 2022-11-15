package ui

interface Renderer<in T> {
    fun render(obj: T)
}