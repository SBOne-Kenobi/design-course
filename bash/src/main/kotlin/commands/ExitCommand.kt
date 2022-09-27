package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream

/**
 * 'exit' command implementation.
 *
 * Command forces session terminating.
 */
@CommandCallName("exit")
class ExitCommand() : Command {
    /**
     * Set [SessionContext.isRunning] false.
     */
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        context.isRunning = false
        return 0
    }
}
