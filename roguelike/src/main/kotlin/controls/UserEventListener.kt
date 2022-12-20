package controls

/**
 * Listener of [UserEvent]s.
 */
interface UserEventListener {

    /**
     * This method is called on [event].
     */
    fun onEvent(event: UserEvent)
}