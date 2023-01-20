package entity

internal class TimeController(private val minEventTimeMs: Long) {
    private var lastEventTime = 0L

    fun event(updateIfCan: Boolean = true, force: Boolean = false): Boolean {
        val current = System.currentTimeMillis()
        if (force || current - lastEventTime > minEventTimeMs) {
            if (updateIfCan) {
                lastEventTime = current
            }
            return true
        }
        return false
    }

}