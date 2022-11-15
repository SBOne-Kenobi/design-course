package ui.inventory.items

abstract class ItemRendererWrapper(
    val delegate: ItemRenderer,
    maxWidth: Int = delegate.maxWidth
) : ItemRenderer(maxWidth)