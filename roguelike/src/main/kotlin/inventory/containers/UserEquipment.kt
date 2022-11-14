package inventory.containers

import inventory.items.EquipmentType
import inventory.items.Item

class UserEquipment : MutableItemsContainer {
    private val equipment: MutableMap<Int, Item?> =
        (minPriority..maxPriority).associateWith { null }.toMutableMap()

    override fun getItemAmount(item: Item): Int {
        return when (item.equipmentType) {
            EquipmentType.None -> 0
            EquipmentType.Any -> 0
            else -> {
                val curItem = equipment[item.equipmentType.priority]
                if (item == curItem) 1 else 0
            }
        }
    }

    override fun getItemsList(): List<Item> = equipment.values.filterNotNull()

    override fun addItem(item: Item, count: Int): Item? {
        val priority = item.equipmentType.priority

        require(priority in minPriority..maxPriority)
        require(count == 1)

        return equipment.put(priority, item)
    }

    override fun removeItem(item: Item, count: Int): Boolean {
        val priority = item.equipmentType.priority

        require(priority in minPriority..maxPriority)
        require(count == 1)

        if (equipment[priority] == item) {
            equipment[priority] = null
            return true
        }

        return false
    }

    override fun removeAllEntriesOfItem(item: Item) {
        removeItem(item, 1)
    }

    companion object {
        private const val minPriority: Int = 1
        private const val maxPriority: Int = 5
    }
}