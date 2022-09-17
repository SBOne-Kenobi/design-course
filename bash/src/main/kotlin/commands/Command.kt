package commands

import SessionContext
import java.io.InputStream
import java.io.OutputStream

interface Command {
    fun execute(
        input: InputStream,
        output: OutputStream,
        error: OutputStream,
        context: SessionContext,
        arguments: Array<String>
    ): Int

}