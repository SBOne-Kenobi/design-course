package generator.generators

import generator.info.GenerationInfo

interface Generator<out T> {
    fun generate(): T
}

interface InfoGenerator<out T : GenerationInfo> : Generator<T>