package inventory.items

object EmptyItem : Item {
    override val name: String = "Empty"

    override val description: String = "Empty"

    override val equipmentType: EquipmentType = EquipmentType.Any
}