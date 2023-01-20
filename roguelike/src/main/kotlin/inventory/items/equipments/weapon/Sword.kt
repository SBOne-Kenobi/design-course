package inventory.items.equipments.weapon

object Sword : AbstractWeaponEquipment() {
    override val name: String = "Sword"

    override val attackPointsBonus: Int = 10

    override val description: String = """
        Standard sword, a great helper in the first battles.
        ${bonusesToString()}
    """.trimIndent()
}