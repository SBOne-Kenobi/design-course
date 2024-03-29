package inventory.containers

import inventory.items.Item

/**
 * Container of items that can be modified.
 */
interface MutableItemsContainer : ItemsContainer {
    fun addItem(item: Item, count: Int = 1): Item?

    fun removeItem(item: Item, count: Int = 1): Boolean

    fun removeAllEntriesOfItem(item: Item)
}