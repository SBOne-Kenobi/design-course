package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream

object UnknownCommand : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>,
    ): Int {
        TODO("Not yet implemented")
    }
}