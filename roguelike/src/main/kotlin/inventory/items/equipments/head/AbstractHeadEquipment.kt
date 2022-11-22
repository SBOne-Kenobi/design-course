package inventory.items.equipments.head

import inventory.items.EquipmentType
import inventory.items.equipments.AbstractEquipment

abstract class AbstractHeadEquipment : AbstractEquipment() {
    override val equipmentType: EquipmentType = EquipmentType.Head
}