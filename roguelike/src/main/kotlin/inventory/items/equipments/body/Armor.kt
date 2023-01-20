package inventory.items.equipments.body

object Armor : AbstractBodyEquipment() {
    override val name: String = "Armor"

    override val protectionPointsBonus: Int = 10

    override val description: String = """
        Standard armor for beginner fighters.
        ${bonusesToString()}
    """.trimIndent()
}