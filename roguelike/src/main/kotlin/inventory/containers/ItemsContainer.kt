package inventory.containers

import inventory.items.Item

interface ItemsContainer {
    // FIXME: re-architect this to respect an amount of items in containers
    // maybe use map items to amount...

    val items: List<Item>

    fun getCurrentItem(): Item

    fun resetCurrentItem()

    fun setNextItem(): Boolean

    fun setPrevItem(): Boolean
}