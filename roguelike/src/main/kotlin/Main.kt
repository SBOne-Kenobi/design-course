import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.terminal.virtual.TerminalSize
import com.varabyte.kotter.terminal.virtual.VirtualTerminal
import java.awt.Frame

fun main() {
    val terminal = VirtualTerminal.create(
        title = "Test",
        terminalSize = TerminalSize(width = 100, height = 20),
    )

    Frame.getFrames().single().apply {
        isResizable = false
    }

    session(terminal) {
        section {
            // Render
        }.run {
            // Logic
        }
    }
}