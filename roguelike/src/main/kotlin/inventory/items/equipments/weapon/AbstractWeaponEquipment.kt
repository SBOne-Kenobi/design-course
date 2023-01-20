package inventory.items.equipments.weapon

import inventory.items.EquipmentType
import inventory.items.equipments.AbstractEquipment

abstract class AbstractWeaponEquipment : AbstractEquipment() {
    override val equipmentType: EquipmentType = EquipmentType.Weapon
}