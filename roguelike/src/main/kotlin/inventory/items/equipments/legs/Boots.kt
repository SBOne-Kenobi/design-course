package inventory.items.equipments.legs

object Boots : AbstractLegsEquipment() {
    override val name: String = "Boots"

    override val healthBonus: Double = 5.0

    override val description: String = """
        Rubber boots. Will save you from dampness, but not from monsters.
        ${bonusesToString()}
    """.trimIndent()
}