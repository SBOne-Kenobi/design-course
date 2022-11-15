package inventory.items.equipments.body

import inventory.items.EquipmentType
import inventory.items.equipments.AbstractEquipment

abstract class AbstractBodyEquipment : AbstractEquipment() {
    override val equipmentType: EquipmentType = EquipmentType.Body
}