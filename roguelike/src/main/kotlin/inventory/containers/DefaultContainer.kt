package inventory.containers

import inventory.items.Item
import inventory.items.ItemWithAmount

/**
 * Default implementation of mutable items' container.
 */
open class DefaultContainer() : MutableItemsContainer {
    protected val itemsToCountData: MutableMap<Item, Int> = mutableMapOf()

    constructor(items: Iterable<ItemWithAmount>) : this() {
        itemsToCountData.putAll(items.map { it.item to it.amount })
    }

    override fun getItemAmount(item: Item): Int = itemsToCountData.getOrDefault(item, 0)

    override fun getItemsList(): List<Item> = itemsToCountData.keys.toList()

    override fun addItem(item: Item, count: Int): Item {
        itemsToCountData[item] = itemsToCountData.getOrDefault(item, 0) + count

        return item
    }

    override fun removeItem(item: Item, count: Int): Boolean {
        val curItemCount = itemsToCountData.getOrDefault(item, 0)
        if (curItemCount < count) {
            return false
        }

        itemsToCountData[item] = curItemCount - count
        if (itemsToCountData[item] == 0) {
            itemsToCountData.remove(item)
        }
        return true
    }

    override fun removeAllEntriesOfItem(item: Item) {
        removeItem(item, itemsToCountData.getOrDefault(item, 0))
    }
}