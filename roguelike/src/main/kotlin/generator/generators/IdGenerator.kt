package generator.generators

class IdGenerator : Generator<Int> {
    private var counter = 0

    override fun generate(): Int = counter++
}