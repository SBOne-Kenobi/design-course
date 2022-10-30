import java.nio.file.Path
import java.nio.file.Paths
import processors.LineUserProcessor
import processors.UserProcessor

private fun getRunDirectory(): Path =
    Paths.get("").toAbsolutePath()

fun main() {
    val userProcessor = init()
    userProcessor.loop()
}

private fun init(): UserProcessor {
    val context = SessionContext(getRunDirectory(), System.getenv().toMutableMap())
    return LineUserProcessor(context, System.`in`, System.out, System.err)
}
