package commands.parsed

/**
 * Represents sequential commands.
 */
data class ParsedCommandSequence(
    override val commands: List<ParsedCommand>
) : ParsedCommandComposite {
    override fun accept(visitor: ParsedCommandVisitor) {
        visitor.visitSequence(this)
    }
}