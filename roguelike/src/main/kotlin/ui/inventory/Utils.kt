package ui.inventory

fun String.extend(size: Int): String =
    this + " ".repeat(size - length)
