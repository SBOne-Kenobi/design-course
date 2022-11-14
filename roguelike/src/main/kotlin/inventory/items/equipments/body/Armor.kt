package inventory.items.equipments.body

object Armor : AbstractBodyEquipment() {
    override val description: String = """
        Standard armor for beginner fighters.
        +10% to protection.
    """.trimIndent()
}