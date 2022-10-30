package commands.parsed

/**
 * Represents calling command with name [command] and [arguments].
 */
data class ParsedCallCommand(
    val command: String,
    val arguments: List<String>
) : ParsedCommand {

    /**
     * Just call [ParsedCommandVisitor.visitCall].
     *
     * @see ParsedCommandVisitor.visitCall
     */
    override fun accept(visitor: ParsedCommandVisitor) {
        visitor.visitCall(this)
    }
}