package building

import SessionContext
import commands.Command
import commands.parsed.ParsedCommand
import commands.parsed.ParsedCallCommand
import commands.parsed.ParsedCommandPipe
import commands.parsed.ParsedCommandSequence
import commands.parsed.ParsedCommandVisitor
import commands.parsed.ParsedSetVariableCommand
import java.io.InputStream
import java.io.OutputStream
import kotlin.properties.Delegates

/**
 * Traversing [ParsedCommand] with instantiating [Command] by [commandFactory] and calling [Command.execute].
 *
 * @property exitCode result of executing
 *
 * @see CommandFactory
 * @see ParsedCommandVisitor
 */
class CommandExecutor(
    private val commandFactory: CommandFactory,
    private val input: InputStream,
    private val output: OutputStream,
    private val error: OutputStream,
    private val context: SessionContext
) : ParsedCommandVisitor {

    var exitCode by Delegates.notNull<Int>()
        private set

    /**
     * Build [Command] from name [ParsedCallCommand.command] and [Command.execute] it with specified arguments.
     */
    override fun visitCall(cmd: ParsedCallCommand) {
        val command = commandFactory.getCommand(cmd.command)
        exitCode = command.execute(input, output, error, context, cmd.arguments.toTypedArray())
    }

    override fun visitSetVar(cmd: ParsedSetVariableCommand) {
        TODO("Not yet implemented")
    }

    override fun visitPipe(cmd: ParsedCommandPipe) {
        TODO("Not yet implemented")
    }

    override fun visitSequence(cmd: ParsedCommandSequence) {
        TODO("Not yet implemented")
    }
}