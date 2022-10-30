package commands.parsed

/**
 * Represents command after parsing.
 */
interface ParsedCommand {

    /**
     * Traverse using [visitor].
     *
     * @see ParsedCommandVisitor
     */
    fun accept(visitor: ParsedCommandVisitor)
}