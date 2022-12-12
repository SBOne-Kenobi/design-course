package inventory

import inventory.containers.MagicPot
import inventory.containers.UserEquipment
import inventory.containers.UserStorage
import inventory.grimoire.Grimoire

/**
 * The user's inventory.
 */
class UserInventory {
    // TODO

    /**
     * The user's storage.
     */
    val storage: UserStorage = UserStorage()

    /**
     * The user's equipment.
     */
    val equipment: UserEquipment = UserEquipment()

    /**
     * The user's magic pot.
     */
    val pot: MagicPot = MagicPot()

    /**
     * The user's grimoire.
     */
    val grimoire: Grimoire = Grimoire()

}