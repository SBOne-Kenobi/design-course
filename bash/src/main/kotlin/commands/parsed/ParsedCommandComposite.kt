package commands.parsed

/**
 * Represents calling several commands.
 */
interface ParsedCommandComposite : ParsedCommand {
    val commands: List<ParsedCommand>
}