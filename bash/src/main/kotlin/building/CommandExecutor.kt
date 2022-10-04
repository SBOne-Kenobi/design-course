package building

import SessionContext
import commands.Command
import commands.parsed.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
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
        context.variables[cmd.name] = cmd.value
        exitCode = 0
    }

    override fun visitPipe(cmd: ParsedCommandPipe) {
        val resultInput = cmd.commands.fold(input) { curInput, curCmd ->
            val contextCopy = context.copy(
                variables = context.variables.toMutableMap()
            )
            val curOutput = ByteArrayOutputStream()

            val executor = CommandExecutor(commandFactory, curInput, curOutput, error, contextCopy)
            executor.visit(curCmd)
            exitCode = executor.exitCode

            ByteArrayInputStream(curOutput.toByteArray())
        }
        resultInput.copyTo(output)
    }

    override fun visitSequence(cmd: ParsedCommandSequence) {
        cmd.commands.forEach {
            visit(it)
            if (exitCode != 0 || !context.isRunning) {
                return
            }
        }
    }
}