package ui.inventory.items

import ui.inventory.items.renderers.ItemRenderer

abstract class ItemRendererWrapper(
    val delegate: ItemRenderer,
    maxWidth: Int = delegate.maxWidth
) : ItemRenderer(maxWidth)