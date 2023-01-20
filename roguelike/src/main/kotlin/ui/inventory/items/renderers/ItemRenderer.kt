package ui.inventory.items.renderers

import inventory.items.Item
import ui.ConsoleRenderer

/**
 * Items' renderer.
 */
abstract class ItemRenderer(var maxWidth: Int) : ConsoleRenderer<Item>()

/**
 * Sets max width of rendering.
 */
inline fun <reified T> ItemRenderer.withMaxWidth(width: Int, block: ItemRenderer.() -> T): T {
    val oldWidth = maxWidth
    return try {
        maxWidth = width
        block()
    } finally {
        maxWidth = oldWidth
    }
}

