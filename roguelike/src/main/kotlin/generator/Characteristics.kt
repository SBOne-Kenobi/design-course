package generator

import engine.Prototype

/**
 * Characteristics of entities.
 */
data class Characteristics(
    var healthPoints: Int,
    var attackPoints: Int,
    var protectionPoints: Int,

    var maxHealthPoints: Int = healthPoints,
) : Prototype<Characteristics> {
    override fun clone(): Characteristics =
        copy()

}