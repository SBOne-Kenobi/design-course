package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream
import printException
import kotlin.io.path.*

/**
 * 'wc' command implementation.
 *
 * Command prints out the number of lines, words and bytes in the file.
 *
 * Command line usage example:
 * ```
 * |> wc example.txt
 * $	15	40	450
 * ```
 */
@CommandCallName("wc")
class WcCommand() : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        try {
            val targetInput = if (arguments.isEmpty()) {
                input.readBytes()
            } else {
                (context.currentDirectory / arguments.first()).readBytes()
            }
            val bytes = targetInput.size
            var words = 0
            var lines = 0
            targetInput.decodeToString().lineSequence().forEach {
                lines += 1
                words += it.split(" ").filter { word -> word.isNotEmpty() }.size
            }
            output.bufferedWriter().apply {
                append("\t$lines\t$words\t$bytes")
                flush()
            }
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
