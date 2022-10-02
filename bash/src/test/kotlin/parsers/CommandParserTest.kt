package parsers

import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.Parsed
import commands.parsed.ParsedCallCommand
import java.util.stream.Stream
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
    }

}