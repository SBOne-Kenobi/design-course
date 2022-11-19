package controls

import kotlinx.coroutines.flow.SharedFlow

interface UserEventGenerator : AutoCloseable {
    val eventFlow: SharedFlow<UserEvent>
}
