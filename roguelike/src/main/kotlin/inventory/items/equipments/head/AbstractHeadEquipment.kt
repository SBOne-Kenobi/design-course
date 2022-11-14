package inventory.items.equipments.head

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractHeadEquipment: Item {
    override val equipmentType: EquipmentType = EquipmentType.Head
}