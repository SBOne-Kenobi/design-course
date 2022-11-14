package inventory.items.equipments.legs

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractLegsEquipment : Item {
    override val equipmentType: EquipmentType = EquipmentType.Legs
}