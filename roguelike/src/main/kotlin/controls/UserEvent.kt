package controls

sealed interface UserEvent

data class UserKeyEvent(val key: Key, val type: KeyEventType) : UserEvent

enum class Key {
    Left,
    Right,
    Up,
    Down,
    Space,
    Enter,
    Back,
    Esc,
}

enum class KeyEventType {
    Pressed,
    Released,
}
