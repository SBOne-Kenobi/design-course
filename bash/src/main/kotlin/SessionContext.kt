import java.nio.file.Path

data class SessionContext(
    var currentDirectory: Path,
    val variables: MutableMap<String, String> = mutableMapOf(),
    var isRunning: Boolean = true
)
