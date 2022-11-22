package inventory.containers

import inventory.items.Item

interface ItemsContainer {
    fun getItemAmount(item: Item): Int

    fun getItemsList(): List<Item>
}