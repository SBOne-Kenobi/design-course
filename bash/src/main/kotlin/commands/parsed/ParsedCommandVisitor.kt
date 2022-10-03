package commands.parsed

/**
 * Interface for traversing [ParsedCommand].
 */
interface ParsedCommandVisitor {

    /**
     * Process [ParsedCallCommand].
     */
    fun visitCall(cmd: ParsedCallCommand)

    /**
     * Process [ParsedSetVariableCommand].
     */
    fun visitSetVar(cmd: ParsedSetVariableCommand)

    /**
     * Process [ParsedCommandPipe].
     */
    fun visitPipe(cmd: ParsedCommandPipe)

    /**
     * Process [ParsedCommandSequence].
     */
    fun visitSequence(cmd: ParsedCommandSequence)
}

/**
 * Just call [ParsedCommand.accept].
 *
 * @see ParsedCommand.accept
 */
fun ParsedCommandVisitor.visit(cmd: ParsedCommand) {
    cmd.accept(this)
}