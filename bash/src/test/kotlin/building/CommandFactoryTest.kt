package building

import commands.CatCommand
import commands.EchoCommand
import commands.ExitCommand
import commands.PwdCommand
import commands.UnknownCommand
import commands.WcCommand
import java.util.stream.Stream
import kotlin.reflect.KClass
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class CommandFactoryTest {

    @ParameterizedTest
    @MethodSource("commandsProvider")
    fun testCommandFactory(name: String, expectedClass: KClass<*>) {
        val factory = CommandFactory()
        assert(expectedClass.isInstance(factory.getCommand(name)))
    }

    companion object {
        @JvmStatic
        fun commandsProvider(): Stream<Arguments> = Stream.of(
            arguments("cat", CatCommand::class),
            arguments("echo", EchoCommand::class),
            arguments("exit", ExitCommand::class),
            arguments("pwd", PwdCommand::class),
            arguments("wc", WcCommand::class),
            arguments("acacxz", UnknownCommand::class),
        )
    }

}