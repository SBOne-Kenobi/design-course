package commands.parsed

interface ParsedCommandVisitor {
    fun visitCall(cmd: ParsedCallCommand)
}

fun ParsedCommandVisitor.visit(cmd: ParsedCommand) {
    cmd.accept(this)
}