package generator.generators.base

import generator.generators.Generator

class IdGenerator : Generator<Int> {
    private var counter = 0

    override fun generate(): Int = counter++
}