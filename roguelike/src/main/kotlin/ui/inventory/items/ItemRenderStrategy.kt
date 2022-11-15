package ui.inventory.items

import inventory.items.Item

interface ItemRenderStrategy {
    fun getItemRenderer(item: Item, maxWidth: Int): ItemRenderer
}