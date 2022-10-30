import java.nio.file.Path

/**
 * Class that contains environment [variables], [currentDirectory] for a session.
 * Also contains [isRunning] flag to understand when session must be stopped.
 */
data class SessionContext(
    var currentDirectory: Path,
    val variables: MutableMap<String, String> = mutableMapOf(),
    var isRunning: Boolean = true
)
