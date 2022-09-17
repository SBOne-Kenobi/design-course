package building

import SessionContext
import commands.parsed.ParsedCallCommand
import commands.parsed.ParsedCommandVisitor
import java.io.InputStream
import java.io.OutputStream
import kotlin.properties.Delegates

class CommandExecutor(
    private val commandFactory: CommandFactory,
    private val input: InputStream,
    private val output: OutputStream,
    private val error: OutputStream,
    private val context: SessionContext
) : ParsedCommandVisitor {

    var exitCode by Delegates.notNull<Int>()
        private set

    override fun visitCall(cmd: ParsedCallCommand) {
        val command = commandFactory.getCommand(cmd.command)
        exitCode = command.execute(input, output, error, context, cmd.arguments.toTypedArray())
    }
}