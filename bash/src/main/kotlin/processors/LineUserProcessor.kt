package processors

import SessionContext
import building.CommandExecutor
import building.CommandFactory
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.ParseResult
import com.github.h0tk3y.betterParse.parser.Parsed
import commands.parsed.ParsedCommand
import commands.parsed.visit
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import parsers.CommandParser

class LineUserProcessor(
    context: SessionContext,
    input: InputStream,
    private val output: OutputStream,
    private val error: OutputStream
) : UserProcessor(context) {
    private val inputReader = input.bufferedReader()

    private val commandParser = CommandParser
    private val commandFactory = CommandFactory()

    private fun getExecutor() =
        CommandExecutor(commandFactory, ReaderWrapper(inputReader), output, error, context)

    override fun process() {
        val inputLine = inputReader.readLine()

        val parsedCommand = commandParser
            .parse(inputLine).processParseResult("Parsing commands") ?: return

        val exitCode = parsedCommand.execute()
        processExitCode(exitCode)
    }

    private fun ParsedCommand.execute(): Int {
        val executor = getExecutor()
        executor.visit(this)
        return executor.exitCode
    }

    private fun <T> ParseResult<T>.processParseResult(name: String): T? = when(this) {
        is Parsed -> value
        is ErrorResult -> {
            error.bufferedWriter().use { writer ->
                writer.appendLine("Error while $name: $this")
            }
            null
        }
    }

    private fun processExitCode(exitCode: Int) {
        if (exitCode != 0) {
            error.bufferedWriter().apply {
                appendLine("Exit code: $exitCode")
                flush()
            }
        }
    }

    private class ReaderWrapper(private val reader: Reader): InputStream() {
        override fun read(): Int {
            return reader.read()
        }
    }
}