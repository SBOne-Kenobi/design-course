package inventory.containers

import inventory.items.Item

/**
 * Container for items.
 */
interface ItemsContainer {
    fun getItemAmount(item: Item): Int

    fun getItemsList(): List<Item>
}