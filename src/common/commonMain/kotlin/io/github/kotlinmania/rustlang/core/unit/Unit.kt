// port-lint: source unit.rs
package io.github.kotlinmania.rustlang.core.unit

import io.github.kotlinmania.rustlang.core.any.TypeId

/**
 * Collapses all unit items from an iterator into one.
 *
 * This is more useful when combined with higher-level abstractions, like
 * collecting to a `Result<Unit, E>` where you only care about errors:
 *
 * ```kotlin
 * import std.io.*
 *
 * val data = mutableListOf(1, 2, 3, 4, 5)
 * val res: Result<Unit> = ArrayDeque((ArrayDeque(data)).map({ x -> stdout().appendLine("$x") })).collect()
 * check(res.isOk())
 * ```
 */
public fun <I : Iterable<Unit>> fromIteratorFromIter(iter: I) {
    iter.iterator().forEach({})
}

internal interface IsUnit {
    public val IS_UNIT: Boolean
}

// `typeId` erases scopes, but that's OK here because "is it ()" never depends on scopes
public val IS_UNIT: Boolean = TypeId<T>() == TypeId<Unit>()
