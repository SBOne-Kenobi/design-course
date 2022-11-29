package entity

internal class TimeController(private val minEventTimeMs: Long) {
    private var lastEventTime = 0L

    fun event(): Boolean {
        val current = System.currentTimeMillis()
        if (current - lastEventTime > minEventTimeMs) {
            lastEventTime = current
            return true
        }
        return false
    }

}