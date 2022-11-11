package inventory.containers

import inventory.items.EmptyItem
import inventory.items.EquipmentType
import inventory.items.Item

class UserEquipment : ItemsContainer {
    private var currentPosition: Int = minPriority

    private val equipment: MutableMap<Int, Item> =
        (minPriority..maxPriority).associateWith { EmptyItem }.toMutableMap()

    override val items: List<Item>
        get() = equipment.values.toList()

    fun setEquipment(type: EquipmentType, item: Item): Item {
        require(type.priority in minPriority..maxPriority)
        require(item.equipmentType == EquipmentType.Any || type == item.equipmentType)
        return equipment.put(type.priority, item)!!
    }

    override fun getCurrentItem(): Item =
        equipment[currentPosition]!!

    override fun resetCurrentItem() {
        currentPosition = minPriority
    }

    override fun setNextItem(): Boolean =
        if (currentPosition < maxPriority) {
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

    companion object {
        private const val minPriority: Int = 1
        private const val maxPriority: Int = 5
    }
}