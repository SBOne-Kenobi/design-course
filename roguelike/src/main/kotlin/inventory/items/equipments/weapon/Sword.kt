package inventory.items.equipments.weapon

object Sword : AbstractWeaponEquipment() {
    override val name: String = "Sword"

    override val attackBonus: Double = 10.0

    override val description: String = """
        Standard sword, a great helper in the first battles.
        ${bonusesToString()}
    """.trimIndent()
}