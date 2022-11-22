package inventory

import inventory.containers.MagicPot
import inventory.containers.UserEquipment
import inventory.containers.UserStorage
import inventory.grimoire.Grimoire

class UserInventory {
    // TODO

    val storage: UserStorage = UserStorage()

    val equipment: UserEquipment = UserEquipment()

    val pot: MagicPot = MagicPot()

    val grimoire: Grimoire = Grimoire()

}