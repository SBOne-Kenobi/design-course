package inventory.containers

import inventory.items.Item

open class DefaultContainer : MutableItemsContainer {
    private val itemsData: MutableList<Item> = mutableListOf()
    private var currentPosition: Int = 0

    override val items: List<Item>
        get() = itemsData.toList()

    override fun addItem(item: Item, index: Int?) {
        if (index == null) {
            itemsData.add(item)
        } else {
            itemsData.add(index, item)
        }
    }

    override fun addItemAfterCurrent(item: Item) {
        addItem(item, currentPosition + 1)
    }

    override fun removeItem(index: Int?) {
        if (index == null) {
            itemsData.removeLast()
        } else {
            itemsData.removeAt(index)
        }
        if (itemsData.isNotEmpty()) {
            currentPosition %= itemsData.size
        }
    }

    override fun removeCurrentItem() {
        removeItem(currentPosition)
    }

    override fun getCurrentItem(): Item =
        itemsData[currentPosition]

    override fun resetCurrentItem() {
        currentPosition = 0
    }

    override fun setNextItem(): Boolean =
        if (currentPosition + 1 < itemsData.size) {
            ++currentPosition
            true
        } else {
            false
        }

    override fun setPrevItem(): Boolean =
        if (currentPosition > 0) {
            --currentPosition
            true
        } else {
            false
        }
}