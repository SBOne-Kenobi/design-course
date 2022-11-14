package inventory.containers

import inventory.items.Item

open class DefaultContainer : MutableItemsContainer {
    protected val itemsData: MutableList<Item> = mutableListOf()
    protected val itemsToCountData: MutableMap<Item, Int> = mutableMapOf()

    override fun getItemAmount(item: Item): Int = itemsToCountData.getOrDefault(item, 0)

    override fun getItemsList(): List<Item> = itemsData.toList()

    override fun addItem(item: Item, count: Int): Item {
        if (!itemsData.contains(item)) {
            itemsData.add(item)
        }
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
            itemsData.remove(item)
            itemsToCountData.remove(item)
        }
        return true
    }

    override fun removeAllEntriesOfItem(item: Item) {
        removeItem(item, itemsToCountData.getOrDefault(item, 0))
    }
}