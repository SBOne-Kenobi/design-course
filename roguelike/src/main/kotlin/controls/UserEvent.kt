package controls

/**
 * Interface of events from user.
 */
sealed interface UserEvent

/**
 * Any event with user's keyboard.
 */
data class UserKeyEvent(val key: Key, val type: KeyEventType) : UserEvent

/**
 * Key of keyboard.
 */
enum class Key {
    Left,
    Right,
    Up,
    Down,
    Space,
    Enter,
    Back,
    Esc,
    Q,
}

/**
 * Type of event with key.
 */
enum class KeyEventType {
    Pressed,
    Released,
}
