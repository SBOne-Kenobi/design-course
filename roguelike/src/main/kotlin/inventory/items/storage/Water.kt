package inventory.items.storage

object Water : AbstractStorageItem() {
    override val description: String = """
        Water. Most likely not drinkable.
    """.trimIndent()
}