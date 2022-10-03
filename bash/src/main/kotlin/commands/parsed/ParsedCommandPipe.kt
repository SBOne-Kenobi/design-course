package commands.parsed

/**
 * Represents commands connected by pipe.
 */
class ParsedCommandPipe(
    override val commands: List<ParsedCommand>
) : ParsedCommandComposite {
    override fun accept(visitor: ParsedCommandVisitor) {
        visitor.visitPipe(this)
    }
}