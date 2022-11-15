package inventory.containers

import inventory.items.EquipmentType
import inventory.items.Item

class UserEquipment : MutableItemsContainer {
    private val equipment: MutableMap<EquipmentType, Item> = mutableMapOf()

    override fun getItemAmount(item: Item): Int {
        val curItem = getEquipment(item.equipmentType)
        return if (item == curItem) 1 else 0
    }

    fun getEquipment(equipmentType: EquipmentType): Item? =
        equipment[equipmentType]

    override fun getItemsList(): List<Item> = equipment.values.toList()

    override fun addItem(item: Item, count: Int): Item? {
        val type = item.equipmentType

        require(type != EquipmentType.None)
        require(count == 1)

        return equipment.put(type, item)
    }

    override fun removeItem(item: Item, count: Int): Boolean {
        val type = item.equipmentType

        require(type != EquipmentType.None)
        require(count == 1)

        if (equipment[type] == item) {
            equipment.remove(type)
            return true
        }

        return false
    }

    override fun removeAllEntriesOfItem(item: Item) {
        removeItem(item, 1)
    }
}