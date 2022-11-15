package inventory.items.equipments.weapon

object Sword : AbstractWeaponEquipment() {
    override val name: String = "Sword"

    override val description: String = """
        Standard sword, a great helper in the first battles.
        +10% to attack.
    """.trimIndent()
}