package ui.inventory

internal fun String.extend(size: Int): String =
    this + " ".repeat(size - length)
