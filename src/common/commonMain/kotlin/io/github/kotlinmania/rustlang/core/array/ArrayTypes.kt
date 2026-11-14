package io.github.kotlinmania.rustlang.core.array

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.result.Result

public interface SpecArrayClone<T> {
    public fun clone(array: List<T>, n: Int): List<T>
}

internal interface SpecNextChunk<T, SelfType> : Iterator<T> {
    public fun specNextChunk(): Result<List<T>, IntoIter<T, N>>

    public fun next(): T?
}

internal interface SpecNextChunkBack<T, SelfType> {
    public fun specNextChunkBack(): Result<List<T>, IntoIter<T, N>>

    public fun next(): T?

    public fun nextBack(): T?
}
