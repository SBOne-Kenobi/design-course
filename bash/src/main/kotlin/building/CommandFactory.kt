package building

import commands.Command
import commands.CommandCallName
import commands.UnknownCommand
import org.reflections.Reflections
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor

/**
 * Registering available commands as inheritors of [Command] and instantiating command by its call name.
 */
class CommandFactory {
    private val commandConstructors: MutableMap<String, () -> Command> = mutableMapOf()

    init {
        val reflections = Reflections("commands")
        val subTypes = reflections.getSubTypesOf(Command::class.java).map { it.kotlin }
        subTypes.forEach { commandClass ->
            if (commandClass.isSubclassOf(UnknownCommand::class)) {
                return@forEach
            }
            val commandConstructor = commandClass.primaryConstructor ?: throw RuntimeException()
            val commandName = commandClass.findAnnotation<CommandCallName>()?.name ?: throw RuntimeException()
            commandConstructors[commandName] = { commandConstructor.call() }
        }
    }

    /**
     * Get command instance by its call name.
     *
     * @param name command call name
     */
    fun getCommand(name: String): Command = commandConstructors[name]?.invoke() ?: UnknownCommand(name)
}
