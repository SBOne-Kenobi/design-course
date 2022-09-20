import java.io.OutputStream
import java.io.PrintStream

internal fun OutputStream.printStream() = when (this) {
    is PrintStream -> this
    else -> PrintStream(this, true)
}

internal inline fun <reified T, reified V> T.withValue(value: T, set: (value: T) -> Unit, block: () -> V): V {
    set(value)
    try {
        return block()
    } finally {
        set(this)
    }
}
