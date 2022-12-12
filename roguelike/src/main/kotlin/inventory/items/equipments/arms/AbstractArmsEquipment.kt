package inventory.items.equipments.arms

import inventory.items.EquipmentType
import inventory.items.equipments.AbstractEquipment

abstract class AbstractArmsEquipment : AbstractEquipment() {
    override val equipmentType: EquipmentType = EquipmentType.Arms
}