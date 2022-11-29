package generator

/**
 * Characteristics of entities.
 */
data class Characteristics(
    var healthPoints: Int,
    var attackPoints: Int,
    var protectionPoints: Int,

    var maxHealthPoints: Int = healthPoints,
)