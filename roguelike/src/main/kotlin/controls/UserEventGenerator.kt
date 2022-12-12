package controls

import kotlinx.coroutines.flow.SharedFlow

/**
 * Provider of [UserEvent]s.
 */
interface UserEventGenerator : AutoCloseable {
    /**
     * [SharedFlow] of [UserEvent]s.
     */
    val eventFlow: SharedFlow<UserEvent>
}
