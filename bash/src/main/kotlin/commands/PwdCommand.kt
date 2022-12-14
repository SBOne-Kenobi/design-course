package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.path.absolutePathString
import printException

/**
 * 'pwd' command implementation.
 *
 * Command prints out the current directory.
 *
 * Command line usage example:
 * ```
 * |> pwd
 * $ /path/to/current/directory
 * ```
 */
@CommandCallName("pwd")
class PwdCommand() : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        try {
            output.bufferedWriter().apply {
                append(context.currentDirectory.absolutePathString())
                flush()
            }
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
