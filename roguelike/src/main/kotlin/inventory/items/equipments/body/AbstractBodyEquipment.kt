package inventory.items.equipments.body

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractBodyEquipment : Item {
    override val equipmentType: EquipmentType = EquipmentType.Body
}