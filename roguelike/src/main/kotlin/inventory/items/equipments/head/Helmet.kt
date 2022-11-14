package inventory.items.equipments.head

object Helmet : AbstractHeadEquipment() {
    override val description: String = """
        An ordinary helmet made of thin metal plate. 
        +5% to protection.
    """.trimIndent()
}