package generator.generators

import generator.info.GenerationInfo

/**
 * Generator of any.
 */
interface Generator<out T> {
    fun generate(): T
}

/**
 * Generator of [GenerationInfo].
 */
interface InfoGenerator<out T : GenerationInfo> : Generator<T>