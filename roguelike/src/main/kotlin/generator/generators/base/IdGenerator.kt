package generator.generators.base

import generator.generators.Generator

/**
 * Generator of unique ids.
 */
class IdGenerator : Generator<Int> {
    private var counter = 0

    override fun generate(): Int = counter++
}