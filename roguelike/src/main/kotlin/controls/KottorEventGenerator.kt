package controls

import com.varabyte.kotter.foundation.input.Keys
import com.varabyte.kotter.foundation.input.onKeyPressed
import com.varabyte.kotter.runtime.RunScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class KottorEventGenerator : UserEventGenerator {

    @OptIn(DelicateCoroutinesApi::class)
    private val threadContext = newSingleThreadContext("KottorEventGenerator")
    private val coroutineScope = CoroutineScope(threadContext)

    private val _eventFlow: MutableSharedFlow<UserEvent> = MutableSharedFlow()
    override val eventFlow: SharedFlow<UserEvent>
        get() = _eventFlow.asSharedFlow()

    override fun close() {
        threadContext.close()
    }

    fun start(scope: RunScope) {
        scope.onKeyPressed {
            val pressedKey = when (key) {
                Keys.LEFT -> Key.Left
                Keys.RIGHT -> Key.Right
                Keys.UP -> Key.Up
                Keys.DOWN -> Key.Down
                Keys.SPACE -> Key.Space
                Keys.ENTER -> Key.Enter
                Keys.BACKSPACE -> Key.Back
                Keys.ESC -> Key.Esc
                Keys.Q -> Key.Q
                else -> return@onKeyPressed
            }
            coroutineScope.launch {
                _eventFlow.emit(UserKeyEvent(pressedKey, KeyEventType.Pressed))
            }
        }
    }

}