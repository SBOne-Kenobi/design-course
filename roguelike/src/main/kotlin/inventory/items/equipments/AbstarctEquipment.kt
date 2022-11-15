package inventory.items.equipments

import inventory.items.Item

abstract class AbstractEquipment : Item {
    open val healthBonus: Double = 0.0
    open val attackBonus: Double = 0.0
    open val protectionBonus: Double = 0.0

    fun bonusesToString(): String = buildString {
        if (healthBonus > 0) {
            appendLine("$healthBonus% to health.")
        }
        if (attackBonus > 0) {
            appendLine("$attackBonus% to attack.")
        }
        if (protectionBonus > 0) {
            appendLine("$protectionBonus% to protection.")
        }
    }
}