package inventory.containers

import inventory.items.Item

interface MutableItemsContainer : ItemsContainer {
    fun addItem(item: Item, index: Int? = null)

    fun addItemAfterCurrent(item: Item)

    fun removeItem(index: Int? = null)

    fun removeCurrentItem()
}