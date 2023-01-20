package inventory.containers

import inventory.items.EquipmentType
import inventory.items.Item

/**
 * The user's equipment.
 */
class UserEquipment : MutableItemsContainer {
    private val equipment: MutableMap<EquipmentType, Item> = mutableMapOf()

    /**
     * Returns one if there is [item] or zero otherwise.
     */
    override fun getItemAmount(item: Item): Int {
        val curItem = getEquipment(item.equipmentType)
        return if (item == curItem) 1 else 0
    }

    /**
     * Get item with [equipmentType] that's worn by user now.
     */
    fun getEquipment(equipmentType: EquipmentType): Item? =
        equipment[equipmentType]

    /**
     * Add worn items.
     */
    override fun getItemsList(): List<Item> = equipment.values.toList()

    /**
     * Put on [item]. [count] must be one.
     */
    override fun addItem(item: Item, count: Int): Item? {
        val type = item.equipmentType

        require(type != EquipmentType.None)
        require(count == 1)

        return equipment.put(type, item)
    }

    /**
     * Take off [item]. [count] must be one.
     */
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

    /**
     * Take off [item].
     */
    override fun removeAllEntriesOfItem(item: Item) {
        removeItem(item, 1)
    }
}