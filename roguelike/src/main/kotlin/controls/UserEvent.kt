package controls

sealed interface UserEvent

object UserMinimizeWindow : UserEvent

object UserOpenWinder : UserEvent

// UserResizeWindow must be forbidden

data class UserKeyEvent(val key: Key, val type: KeyEventType) : UserEvent

class Key {
    // TODO
}

enum class KeyEventType {
    Pressed,
    Released,
}
