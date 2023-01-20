package generator.generators.base

import generator.generators.AbstractNumberGenerator
import kotlin.random.Random

class UniformNumberGenerator(
    min: Int = 0,
    max: Int = 0,
    private val random: Random = Random,
) : AbstractNumberGenerator(min, max) {
    override fun generate(): Int =
        random.nextInt(min, max + 1)
}