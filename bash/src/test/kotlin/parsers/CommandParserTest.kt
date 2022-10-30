package parsers

import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed
import commands.parsed.ParsedCallCommand
import commands.parsed.ParsedCommand
import commands.parsed.ParsedCommandPipe
import commands.parsed.ParsedCommandSequence
import commands.parsed.ParsedCommandVisitor
import commands.parsed.ParsedSetVariableCommand
import commands.parsed.visit
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class CommandParserTest {

    @ParameterizedTest
    @MethodSource("callCommandProvider")
    fun testCallCommand(commandLine: String, expectedName: String?, expectedArguments: List<String>) {
        val parsedCommand = CommandParser.parse(commandLine)
        if (expectedName == null) {
            assertIs<ErrorResult>(parsedCommand)
        } else {
            assertIs<Parsed<ParsedCallCommand>>(parsedCommand)
            assertEquals(expectedName, parsedCommand.value.command)
            assertContentEquals(expectedArguments, parsedCommand.value.arguments)
        }
    }

    @ParameterizedTest
    @MethodSource("setVarProvider")
    fun testSetVariable(commandLine: String, expectedName: String?, expectedValue: String?) {
        val parsedCommand = CommandParser.parse(commandLine)
        if (expectedName == null) {
            assertIs<ErrorResult>(parsedCommand)
        } else {
            assertIs<Parsed<ParsedSetVariableCommand>>(parsedCommand)
            assertEquals(expectedName, parsedCommand.value.name)
            assertEquals(expectedValue, parsedCommand.value.value)
        }
    }

    @Test
    fun testPipe() {
        val commandLine = """
            cmd | cmd 2 4 5 | a=45 | le \| | echo 31
        """.trimIndent()
        val parsedCommand = CommandParser.parse(commandLine)
        assertIs<Parsed<ParsedCommandPipe>>(parsedCommand)
        assertContentEquals(
            listOf(
                ParsedCallCommand("cmd", emptyList()),
                ParsedCallCommand("cmd", listOf("2", "4", "5")),
                ParsedSetVariableCommand("a", "45"),
                ParsedCallCommand("le", listOf("|")),
                ParsedCallCommand("echo", listOf("31"))
            ),
            parsedCommand.value.commands
        )
    }

    @Test
    fun testSeq() {
        val commandLine = """
            cmd; a=13d; ok 1 2 3; echo   12 "  1 214r q"
        """.trimIndent()
        val parsedCommand = CommandParser.parse(commandLine)
        assertIs<Parsed<ParsedCommandSequence>>(parsedCommand)
        assertContentEquals(
            listOf(
                ParsedCallCommand("cmd", emptyList()),
                ParsedSetVariableCommand("a", "13d"),
                ParsedCallCommand("ok", listOf("1", "2", "3")),
                ParsedCallCommand("echo", listOf("12", "  1 214r q"))
            ),
            parsedCommand.value.commands
        )
    }

    @Test
    fun testComplicated() {
        val commandLine = """
            a=5; echo ${"$"}a | echo 1 ${"$"}a; cmd | { b=3; echo ${"$"}b; }; cat;
        """.trimIndent()
        val parsedCommand = CommandParser.parse(commandLine)
        println(parsedCommand)
        val expected = ParsedCommandSequence(listOf(
            ParsedSetVariableCommand("a", "5"),
            ParsedCommandPipe(listOf(
                ParsedCallCommand("echo", listOf("\$a")),
                ParsedCallCommand("echo", listOf("1", "\$a"))
            )),
            ParsedCommandPipe(listOf(
                ParsedCallCommand("cmd", emptyList()),
                ParsedCommandSequence(listOf(
                    ParsedSetVariableCommand("b", "3"),
                    ParsedCallCommand("echo", listOf("\$b"))
                ))
            )),
            ParsedCallCommand("cat", emptyList())
        ))
        assertIs<Parsed<ParsedCommand>>(parsedCommand)
        assertCommandEquals(expected, parsedCommand.value)
    }

    private fun assertCommandEquals(expected: ParsedCommand, actual: ParsedCommand) {
        val assertVisitor = object : ParsedCommandVisitor {
            private var currentCommand: ParsedCommand = actual

            override fun visitCall(cmd: ParsedCallCommand) {
                currentCommand.let {
                    assertIs<ParsedCallCommand>(it)
                    assertEquals(cmd, it)
                }
            }

            override fun visitSetVar(cmd: ParsedSetVariableCommand) {
                currentCommand.let {
                    assertIs<ParsedSetVariableCommand>(it)
                    assertEquals(cmd, it)
                }
            }

            override fun visitPipe(cmd: ParsedCommandPipe) {
                currentCommand.let {
                    assertIs<ParsedCommandPipe>(it)
                    assertEquals(cmd.commands.size, it.commands.size)
                    (cmd.commands zip it.commands).forEach { (exp, cur) ->
                        currentCommand = cur
                        exp.accept(this)
                    }
                }
            }

            override fun visitSequence(cmd: ParsedCommandSequence) {
                currentCommand.let {
                    assertIs<ParsedCommandSequence>(it)
                    assertEquals(cmd.commands.size, it.commands.size)
                    (cmd.commands zip it.commands).forEach { (exp, cur) ->
                        currentCommand = cur
                        exp.accept(this)
                    }
                }
            }
        }
        assertVisitor.visit(expected)
    }


    companion object {
        @JvmStatic
        fun callCommandProvider(): Stream<Arguments> = Stream.of(
            arguments("cmd", "cmd", emptyList<String>()),
            arguments("     cmd   ", "cmd", emptyList<String>()),
            arguments("cmd arg1 arg2", "cmd", listOf("arg1", "arg2")),
            arguments(
                """ cmd   arg1 "arg2   " " arg3"       arg4""",
                "cmd", listOf("arg1", "arg2   ", " arg3", "arg4")
            ),
            arguments(
                """
                    'c'm"d"   \''  3\"\\' "\"\\\'   \ " \ sda
                """.trimIndent(),
                "cmd", listOf("'  3\\\"\\\\", "\"\\\\'   \\ ", " sda")
            ),
            arguments("""cmd '\''""", null, emptyList<String>())
        )

        @JvmStatic
        fun setVarProvider(): Stream<Arguments> = Stream.of(
            arguments("a=41", "a", "41"),
            arguments("a= 51", null, null),
            arguments("awwaq='41wd 1 24 11 \\q'", "awwaq", "41wd 1 24 11 \\q"),
            arguments(""" a="41wd 1 24 \" \= 11" """, "a", "41wd 1 24 \" \\= 11"),
        )
    }

}