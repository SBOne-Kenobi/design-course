package commands

import SessionContext
import printStream
import withValue
import java.io.InputStream
import java.io.OutputStream

/**
 * Command that's not implemented. Trying to call command [callName] in subprocess.
 */
class UnknownCommand(val callName: String) : Command {
    /**
     * Executes command [callName] in subprocess with setting IO and environment.
     */
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        System.err.println(arguments.joinToString(" "))
        return System.`in`.withValue(input, System::setIn) {
            System.out.withValue(output.printStream(), System::setOut) {
                System.err.withValue(error.printStream(), System::setErr) {
                    val processBuilder = ProcessBuilder(callName, *arguments)
                        .directory(context.currentDirectory.toFile())
                        .redirectInput(ProcessBuilder.Redirect.INHERIT)
                        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                        .redirectError(ProcessBuilder.Redirect.INHERIT)
                        .setEnvironment(context)

                    val process = processBuilder.start()

                    process.waitFor()
                }
            }
        }
    }

    private fun ProcessBuilder.setEnvironment(context: SessionContext): ProcessBuilder = apply {
        environment().apply {
            context.variables.forEach { (key, value) ->
                set(key, value)
            }
        }
    }
}
