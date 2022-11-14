package inventory.items.equipments.weapon

import inventory.items.EquipmentType
import inventory.items.Item

abstract class AbstractWeaponEquipment : Item {
    override val equipmentType: EquipmentType = EquipmentType.Weapon
}