package generator.generators

abstract class AbstractNumberGenerator(
    var min: Int = 0,
    var max: Int = 0
) : Generator<Int>
