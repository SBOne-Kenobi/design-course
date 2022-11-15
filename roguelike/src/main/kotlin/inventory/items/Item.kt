package inventory.items

interface Item {
    val name: String
    val description: String
    val equipmentType: EquipmentType
}