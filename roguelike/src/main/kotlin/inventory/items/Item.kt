package inventory.items

/**
 * Item class.
 */
interface Item {
    val name: String
    val description: String
    val equipmentType: EquipmentType
}