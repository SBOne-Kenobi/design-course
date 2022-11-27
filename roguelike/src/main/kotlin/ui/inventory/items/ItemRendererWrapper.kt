package ui.inventory.items

import ui.inventory.items.renderers.ItemRenderer

/**
 * Wrapper of items' renderer.
 */
abstract class ItemRendererWrapper(
    val delegate: ItemRenderer,
    maxWidth: Int = delegate.maxWidth
) : ItemRenderer(maxWidth)