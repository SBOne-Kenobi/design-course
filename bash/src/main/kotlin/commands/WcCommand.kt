package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.path.div
import kotlin.io.path.fileSize
import kotlin.io.path.forEachLine
import printException

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
            val filePath = context.currentDirectory / arguments.first()
            val bytes = filePath.fileSize()
            var words = 0
            var lines = 0
            filePath.forEachLine {
                lines += 1
                words += it.split(" ").filter { word -> word.isNotEmpty() }.size
            }
            output.bufferedWriter().apply {
                appendLine("\t$lines\t$words\t$bytes")
                flush()
            }
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
