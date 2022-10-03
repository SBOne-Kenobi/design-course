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
import parsers.Substitutor
import printStream

class LineUserProcessor(
    context: SessionContext,
    input: InputStream,
    output: OutputStream,
    error: OutputStream
) : UserProcessor(context) {
    private val inputReader = input.bufferedReader()
    private val outputPrint = output.printStream()
    private val errorPrint = error.printStream()

    private val commandParser = CommandParser
    private val substitutor = Substitutor
    private val commandFactory = CommandFactory()

    private fun getExecutor() =
        CommandExecutor(commandFactory, ReaderWrapper(inputReader), outputPrint, errorPrint, context)

    override fun process() {
        outputPrint.append("|> ")
        val inputLine = inputReader.readLine()

        val substituted = substitutor
            .parse(context, inputLine).processParseResult("Substitution") ?: return

        val parsedCommand = commandParser
            .parse(substituted).processParseResult("Parsing commands") ?: return

        try {
            val exitCode = parsedCommand.execute()
            processExitCode(exitCode)
        } catch (t: Throwable) {
            processError(t)
        }
        Thread.sleep(100) // wait for error stream
        errorPrint.flush()
    }

    private fun ParsedCommand.execute(): Int {
        val executor = getExecutor()
        executor.visit(this)
        return executor.exitCode
    }

    private fun <T> ParseResult<T>.processParseResult(name: String): T? = when(this) {
        is Parsed -> value
        is ErrorResult -> {
            errorPrint.appendLine("Error while $name: $this")
            null
        }
    }

    private fun processExitCode(exitCode: Int) {
        if (exitCode != 0) {
            errorPrint.appendLine("Exit code: $exitCode")
        }
    }

    private fun processError(throwable: Throwable) {
        errorPrint.appendLine("Error: ${throwable.message}")
    }

    private class ReaderWrapper(private val reader: Reader): InputStream() {
        override fun read(): Int {
            return reader.read()
        }
    }
}