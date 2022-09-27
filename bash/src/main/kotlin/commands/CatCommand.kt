package commands

import SessionContext
import printException
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * 'cat' command implementation.
 *
 * Command prints out the contents of the file.
 *
 * Command line usage example:
 * ```
 * |> cat example.txt
 * $ Hello world!
 * ```
 */
@CommandCallName("cat")
class CatCommand() : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>
    ): Int {
        try {
            val file = File(arguments.first())
            file.forEachLine {
                output.bufferedWriter().apply {
                    appendLine(it)
                    flush()
                }
            }
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
