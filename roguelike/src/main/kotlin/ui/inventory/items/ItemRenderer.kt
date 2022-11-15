package ui.inventory.items

import inventory.items.Item
import ui.ConsoleRenderer

abstract class ItemRenderer(var maxWidth: Int) : ConsoleRenderer<Item>()

inline fun <reified T> ItemRenderer.withMaxWidth(width: Int, block: ItemRenderer.() -> T): T {
    val oldWidth = maxWidth
    return try {
        maxWidth = width
        block()
    } finally {
        maxWidth = oldWidth
    }
}

