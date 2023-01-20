package inventory.items

/**
 * Available equipment types.
 */
enum class EquipmentType(val priority: Int) {
    None(0),
    Head(1),
    Body(2),
    Arms(3),
    Weapon(4),
    Legs(5),
}