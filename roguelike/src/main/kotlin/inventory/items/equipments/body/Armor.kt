package inventory.items.equipments.body

object Armor : AbstractBodyEquipment() {
    override val name: String = "Armor"

    override val protectionBonus: Double = 10.0

    override val description: String = """
        Standard armor for beginner fighters.
        ${bonusesToString()}
    """.trimIndent()
}