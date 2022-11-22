package ui.inventory.items

import inventory.items.Item
import ui.inventory.items.renderers.ItemRenderer

interface ItemRenderStrategy {
    fun getItemRenderer(item: Item, maxWidth: Int): ItemRenderer
}