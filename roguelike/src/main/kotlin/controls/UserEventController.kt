package controls

class UserEventController(private val generator: UserEventGenerator) {
    // TODO: use generator

    private val listeners: MutableList<UserEventListener> = mutableListOf()

    fun addListener(listener: UserEventListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: UserEventListener) {
        listeners.remove(listener)
    }

    private fun notifyAll(event: UserEvent) {
        listeners.forEach {
            it.onEvent(event)
        }
    }

}