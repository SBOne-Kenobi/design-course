package inventory.items.storage

object Magma : AbstractStorageItem() {
    override val name: String = "Magma"

    override val description: String = """
        Magma. Caution: really hot!
    """.trimIndent()
}