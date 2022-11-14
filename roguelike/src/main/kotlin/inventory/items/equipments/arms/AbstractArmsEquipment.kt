package inventory.items.equipments.arms

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractArmsEquipment : Item {
    override val equipmentType: EquipmentType = EquipmentType.Arms
}