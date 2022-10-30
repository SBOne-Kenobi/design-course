package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream
import printException

/**
 * 'echo' command implementation.
 *
 * Command prints out its argument(s).
 *
 * Command line usage example:
 * ```
 * |> echo 1 2 "hi!"
 * $ 1 2 hi!
 * ```
 */
@CommandCallName("echo")
class EchoCommand() : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        try {
            output.bufferedWriter().apply {
                appendLine(arguments.joinToString(" "))
                flush()
            }
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
