package controls

import kotlinx.coroutines.flow.Flow

interface UserEventGenerator {
    val eventFlow: Flow<UserEvent>
}
