package commands.parsed

/**
 * Interface for traversing [ParsedCommand].
 */
interface ParsedCommandVisitor {

    /**
     * Process [ParsedCallCommand].
     */
    fun visitCall(cmd: ParsedCallCommand)
}

/**
 * Just call [ParsedCommand.accept].
 *
 * @see ParsedCommand.accept
 */
fun ParsedCommandVisitor.visit(cmd: ParsedCommand) {
    cmd.accept(this)
}