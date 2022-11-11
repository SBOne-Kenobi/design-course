package generator.generators

import generator.info.GenerationInfo

interface InfoGenerator<out T: GenerationInfo> {
    fun generate(): T
}