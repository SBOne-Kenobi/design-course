package inventory.items.equipments.head

object Helmet : AbstractHeadEquipment() {
    override val name: String = "Helmet"

    override val protectionBonus: Double = 5.0

    override val description: String = """
        An ordinary helmet made of thin metal plate. 
        ${bonusesToString()}
    """.trimIndent()
}