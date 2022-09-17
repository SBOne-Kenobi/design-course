package commands.parsed

data class ParsedCallCommand(
    val command: String,
    val arguments: List<String>
) : ParsedCommand {
    override fun accept(visitor: ParsedCommandVisitor) {
        visitor.visitCall(this)
    }
}