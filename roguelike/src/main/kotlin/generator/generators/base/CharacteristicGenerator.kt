package generator.generators.base

import generator.Characteristics
import generator.generators.AbstractNumberGenerator
import generator.generators.Generator

/**
 * Generator for characteristics.
 */
class CharacteristicGenerator(
    private val healthGenerator: AbstractNumberGenerator,
    private val attackGenerator: AbstractNumberGenerator,
    private val protectionGenerator: AbstractNumberGenerator,
) : Generator<Characteristics> {
    override fun generate(): Characteristics {
        val health = healthGenerator.generate()
        return Characteristics(
            health,
            attackGenerator.generate(),
            protectionGenerator.generate(),
        )
    }
}
