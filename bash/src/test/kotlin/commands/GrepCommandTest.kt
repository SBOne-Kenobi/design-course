package commands

import java.util.stream.Stream
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class GrepCommandTest : BaseCommandTest() {

    lateinit var cmd: GrepCommand

    @BeforeEach
    override fun init() {
        super.init()
        cmd = GrepCommand()
    }

    @ParameterizedTest
    @MethodSource("grepProvider")
    fun testGrep(options: Array<String>, expectedLines: List<String>) {
        val fileName = "grep_test.txt"
        assertEquals(0, cmd.execute(*options, "ok", fileName))

        val actual = output.toByteArray().decodeToString().lines().dropLast(1)
        assertContentEquals(expectedLines, actual)
    }

    companion object {
        private const val separator = "--"

        @JvmStatic
        fun grepProvider(): Stream<Arguments> = Stream.of(
            arguments(emptyArray<String>(), listOf(
                "Hello Hoke",
                "ok, say",
                "stirngstringcontainsokbutinthemiddle"
            )),
            arguments(arrayOf("-i"), listOf(
                "Hello Hoke",
                "Ok qss",
                "ok, say",
                "stirngstringcontainsokbutinthemiddle",
                "OK"
            )),
            arguments(arrayOf("-w"), listOf(
                "ok, say",
            )),
            arguments(arrayOf("-i", "-w"), listOf(
                "Ok qss",
                "ok, say",
                "OK"
            )),
            arguments(arrayOf("-A0"), listOf(
                "Hello Hoke",
                separator,
                "ok, say",
                separator,
                "stirngstringcontainsokbutinthemiddle",
            )),
            arguments(arrayOf("-i", "-A0"), listOf(
                "Hello Hoke",
                separator,
                "Ok qss",
                "ok, say",
                separator,
                "stirngstringcontainsokbutinthemiddle",
                separator,
                "OK"
            )),
            arguments(arrayOf("-i", "-A1"), listOf(
                "Hello Hoke",
                "Not this line",
                separator,
                "Ok qss",
                "ok, say",
                "",
                separator,
                "stirngstringcontainsokbutinthemiddle",
                "cq",
                separator,
                "OK",
                "end."
            )),
            arguments(arrayOf("-i", "-A3"), listOf(
                "Hello Hoke",
                "Not this line",
                "",
                "",
                separator,
                "Ok qss",
                "ok, say",
                "",
                "cz",
                "not match this",
                "stirngstringcontainsokbutinthemiddle",
                "cq",
                "x",
                "OK",
                "end."
            )),
        )
    }

}