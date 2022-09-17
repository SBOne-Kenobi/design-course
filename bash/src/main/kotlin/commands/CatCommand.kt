package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream

object CatCommand : Command {
    override fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>
    ): Int {
        TODO("Not yet implemented")
    }
}