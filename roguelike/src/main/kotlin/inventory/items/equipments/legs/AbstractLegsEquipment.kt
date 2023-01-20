package inventory.items.equipments.legs

import inventory.items.EquipmentType
import inventory.items.equipments.AbstractEquipment

abstract class AbstractLegsEquipment : AbstractEquipment() {
    override val equipmentType: EquipmentType = EquipmentType.Legs
}