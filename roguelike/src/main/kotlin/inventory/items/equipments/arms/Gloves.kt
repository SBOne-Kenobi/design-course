package inventory.items.equipments.arms

object Gloves : AbstractArmsEquipment() {
    override val description: String = """
        Gloves. Not particularly useful in combat, but will protect against age-old dust and bacteria.
        +10% to health.
    """.trimIndent()
}