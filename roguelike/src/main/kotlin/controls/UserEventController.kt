package controls

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class UserEventController(
    private val generator: UserEventGenerator,
    activate: Boolean = true,
) : AutoCloseable {

    @OptIn(DelicateCoroutinesApi::class)
    private val threadContext = newSingleThreadContext("UserEventController")

    private val _isActive: AtomicBoolean = AtomicBoolean(activate)

    var isActive: Boolean
        get() = _isActive.get()
        set(value) = _isActive.set(value)

    init {
        CoroutineScope(threadContext).launch {
            generator.eventFlow.collect {
                if (isActive) {
                    notifyAll(it)
                }
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