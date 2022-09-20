package building

import commands.Command
import commands.ExitCommand
import commands.UnknownCommand

class CommandFactory {

    private val commandConstructors: Map<String, () -> Command> =
        emptyMap()

    fun getCommand(name: String): Command {
        if (name == "exit") {
            return ExitCommand
        }
        return UnknownCommand(name)
    }
}