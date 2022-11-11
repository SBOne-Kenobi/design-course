package ui

interface ConsoleRenderer<in T> {
    fun render(obj: T)
}