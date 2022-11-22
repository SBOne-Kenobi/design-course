package generator.generators

import generator.info.GenerationInfo

data class NumberInfo(val number: Int): GenerationInfo

abstract class AbstractNumberGenerator(
    var min: Int = 0,
    var max: Int = 0
) : InfoGenerator<NumberInfo>
