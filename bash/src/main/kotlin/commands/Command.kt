package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream

/**
 * Interface for command.
 */
interface Command {

    /**
     * Execute command.
     *
     * @param input input stream of command.
     * @param output output stream of command.
     * @param error error stream of command.
     * @param context [SessionContext] for command. Command may change it while executing.
     * @param arguments arguments for command.
     */
    fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>
    ): Int

}