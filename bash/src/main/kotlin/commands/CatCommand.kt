package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.path.div
import kotlin.io.path.inputStream
import printException

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
            val filePath = context.currentDirectory / arguments.first()
            filePath.inputStream().copyTo(output)
            return 0
        } catch (ex: Throwable) {
            error.printException(ex)
        }
        return 1
    }
}
