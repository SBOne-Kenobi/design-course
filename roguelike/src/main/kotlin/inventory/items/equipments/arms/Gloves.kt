package inventory.items.equipments.arms

object Gloves : AbstractArmsEquipment() {
    override val name: String = "Gloves"

    override val healthPointsBonus: Int = 10

    override val description: String = """
        Gloves. Not particularly useful in combat, but will protect against age-old dust and bacteria.
        ${bonusesToString()}
    """.trimIndent()
}