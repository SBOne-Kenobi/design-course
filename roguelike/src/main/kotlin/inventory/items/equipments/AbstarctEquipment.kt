package inventory.items.equipments

import inventory.items.Item

abstract class AbstractEquipment : Item {
    open val healthPointsBonus: Int = 0
    open val attackPointsBonus: Int = 0
    open val protectionPointsBonus: Int = 0

    fun bonusesToString(): String = buildString {
        if (healthPointsBonus > 0) {
            appendLine("+$healthPointsBonus points to health.")
        }
        if (attackPointsBonus > 0) {
            appendLine("+$attackPointsBonus points to attack.")
        }
        if (protectionPointsBonus > 0) {
            appendLine("+$protectionPointsBonus points to protection.")
        }
    }
}