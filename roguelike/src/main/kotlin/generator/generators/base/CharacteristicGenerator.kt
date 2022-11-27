package generator.generators.base

import generator.Characteristics
import generator.generators.AbstractNumberGenerator
import generator.generators.Generator

class CharacteristicGenerator(
    private val healthGenerator: AbstractNumberGenerator,
    private val attackGenerator: AbstractNumberGenerator,
    private val protectionGenerator: AbstractNumberGenerator,
) : Generator<Characteristics> {
    override fun generate(): Characteristics {
        val health = healthGenerator.generate().number
        return Characteristics(
            health,
            attackGenerator.generate().number,
            protectionGenerator.generate().number,
            health
        )
    }
}
