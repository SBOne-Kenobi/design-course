package inventory.items.equipments.legs

object Boots : AbstractLegsEquipment() {
    override val description: String = """
        Rubber boots. Will save you from dampness, but not from monsters.
        +5% to health.
    """.trimIndent()
}