package engine

interface Prototype<T: Prototype<T>> {
    fun clone(): T
}

interface Prototype1<T: Prototype1<T, H>, H> {
    fun clone(arg: H): T
}
