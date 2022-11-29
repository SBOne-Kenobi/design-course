package inventory.items.storage

object Stone : AbstractStorageItem() {
    override val name: String = "Stone"

    override val description: String = """
        Stone. Pretty solid.
    """.trimIndent()
}