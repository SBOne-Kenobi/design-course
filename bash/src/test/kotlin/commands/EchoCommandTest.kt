package commands

import java.util.stream.Stream
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class EchoCommandTest : BaseCommandTest() {

    lateinit var cmd: EchoCommand

    @BeforeEach
    override fun init() {
        super.init()
        cmd = EchoCommand()
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun testEchoCommand(arguments: Array<String>) {
        assertEquals(0, cmd.execute(*arguments))
        assertEquals(arguments.joinToString(" "), output.toByteArray().decodeToString())
        assert(error.toByteArray().isEmpty())
    }

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments> = Stream.of(
            arguments(emptyArray<String>()),
            arguments(arrayOf(" ", "", "  12s 21r5 v\\as !")),
            arguments(arrayOf("Amogus Пуп Сус")),
        )
    }

}