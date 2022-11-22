package inventory.items.storage

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractStorageItem : Item {
    override val equipmentType: EquipmentType = EquipmentType.None
}