package commands.parsed

interface ParsedCommand {
    fun accept(visitor: ParsedCommandVisitor)
}