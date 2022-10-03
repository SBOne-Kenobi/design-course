package commands.parsed

/**
 * Represents setting variable.
 */
data class ParsedSetVariableCommand(
    val name: String,
    val value: String
) : ParsedCommand {
    override fun accept(visitor: ParsedCommandVisitor) {
        visitor.visitSetVar(this)
    }
}