package building

import commands.Command

class CommandFactory {

    private val commandConstructors: Map<String, () -> Command> =
        TODO("Not initialized!")

    fun getCommand(name: String): Command {
        TODO("Not yet implemented")
    }
}