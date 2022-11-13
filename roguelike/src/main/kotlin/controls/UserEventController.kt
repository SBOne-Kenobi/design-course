package controls

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class UserEventController(private val generator: UserEventGenerator) : AutoCloseable {

    @OptIn(DelicateCoroutinesApi::class)
    private val threadContext = newSingleThreadContext("UserEventController")

    init {
        CoroutineScope(threadContext).launch {
            generator.eventFlow.collect {
                notifyAll(it)
            }
        }
    }

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

    override fun close() {
        threadContext.cancel()
        threadContext.close()
    }

}