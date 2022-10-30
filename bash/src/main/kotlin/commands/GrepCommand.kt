package commands

import SessionContext
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.path.div
import kotlin.io.path.inputStream

/**
 * 'grep' command implementation.
 *
 * Command is used for finding lines by pattern or regexp in text or files' content.
 * ```
 * Usage: grep [options] pattern [filename]
 * ```
 *
 * Example:
 * ```
 * |> grep "Minimal" README.md
 * $ Minimal syntax grep
 * ```
 *
 * Options:
 * - -w - search only words
 * - -i - case-insensitive search
 * - -A n - print out next n lines below found line.
 */
@CommandCallName("grep")
class GrepCommand() : Command {

    private class GrepArguments(parser: ArgParser) {
        val onlyWords by parser.flagging(
            "-w",
            help = "search only words"
        )

        val caseInsensitive by parser.flagging(
            "-i",
            help = "case-insensitive search"
        )

        val linesBelow by parser.storing(
            "-A",
            help = "print out next n lines below found line"
        ) { toInt().coerceAtLeast(0) }.default(null)

        val pattern by parser.positional(
            "PATTERN",
            help = "search pattern, may be regex"
        )

        val file by parser.positional("FILENAME").default(null)
    }

    private val separator = "--"

    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>
    ): Int {
        try {
            val grepArguments = try {
                ArgParser(arguments).parseInto(::GrepArguments)
            } catch (e: SystemExitException) {
                val writer = if (e is ShowHelpException) {
                    output
                } else {
                    error
                }.writer()
                e.printUserMessage(writer, programName = "grep", columns = 0)
                writer.flush()
                return e.returnCode
            }

            val filePath = grepArguments.file?.let { context.currentDirectory / it }
            filePath?.inputStream().use { fileInput ->
                val searchInput = fileInput ?: input

                val regexOptions = mutableSetOf(RegexOption.DOT_MATCHES_ALL)
                if (grepArguments.caseInsensitive) {
                    regexOptions += RegexOption.IGNORE_CASE
                }

                var pattern = grepArguments.pattern
                if (grepArguments.onlyWords) {
                    pattern = "\\b$pattern\\b"
                }

                val regex = pattern.toRegex(regexOptions)

                val writer = output.bufferedWriter()

                val needToPrintSeparator = grepArguments.linesBelow != null
                var haveToPrint = 0
                var skippedLine = false
                var anyPrinted = false
                searchInput.reader().forEachLine {
                    if (regex.containsMatchIn(it)) {
                        haveToPrint = (grepArguments.linesBelow ?: 0) + 1
                    }
                    if (haveToPrint > 0) {
                        if (needToPrintSeparator && anyPrinted && skippedLine) {
                            writer.appendLine(separator)
                        }
                        skippedLine = false
                        anyPrinted = true
                        writer.appendLine(it)
                        --haveToPrint
                    } else {
                        skippedLine = true
                    }
                    writer.flush()
                }
            }
            return 0
        } catch (e: Throwable) {
            error.writer().appendLine(e.toString())
            return -1
        }
    }
}