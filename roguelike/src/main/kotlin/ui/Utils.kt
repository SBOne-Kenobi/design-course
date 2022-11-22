package ui

fun calculateViewPosition(
    viewPosition: Int,
    targetPosition: Int,
    viewSize: Int,
    padding: Int = 0
) = if (targetPosition in viewPosition + padding until viewPosition + viewSize - padding) {
    viewPosition
} else if (targetPosition < viewPosition + padding) {
    targetPosition - padding
} else {
    targetPosition + 1 - viewSize + padding
}
