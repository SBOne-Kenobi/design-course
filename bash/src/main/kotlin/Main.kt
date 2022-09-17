import java.nio.file.Path
import java.nio.file.Paths
import processors.LineUserProcessor
import processors.UserProcessor

private fun getRunDirectory(): Path =
    Paths.get("").toAbsolutePath()

fun main() {
    val userProcessor = init()
    loop(userProcessor)
}

fun init(): UserProcessor {
    val context = SessionContext(getRunDirectory())
    return LineUserProcessor(context, System.`in`, System.out, System.err)
}

fun loop(userProcessor: UserProcessor) {
    while (userProcessor.context.isRunning) {
        userProcessor.process()
    }
}
