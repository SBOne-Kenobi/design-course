package inventory.items

object EmptyItem : Item {
    override val description: String = "Empty"

    override val equipmentType: EquipmentType = EquipmentType.Any
}