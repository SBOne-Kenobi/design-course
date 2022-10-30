package processors

import SessionContext

/**
 * Bash processing pipeline.
 *
 * @param context used for looping process.
 * @see [SessionContext.isRunning].
 */
abstract class UserProcessor(val context: SessionContext) {

    /**
     * One cycle of processing bash command.
     */
    abstract fun process()

    /**
     * Call [process] while flag [SessionContext.isRunning] is true.
     */
    fun loop() {
        while (context.isRunning) {
            process()
        }
    }

}