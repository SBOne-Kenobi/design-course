package inventory.items.equipments.head

object Helmet : AbstractHeadEquipment() {
    override val name: String = "Helmet"

    override val protectionPointsBonus: Int = 5

    override val description: String = """
        An ordinary helmet made of thin metal plate. 
        ${bonusesToString()}
    """.trimIndent()
}