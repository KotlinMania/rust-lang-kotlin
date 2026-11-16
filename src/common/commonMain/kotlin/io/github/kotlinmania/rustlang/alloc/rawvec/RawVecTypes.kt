package io.github.kotlinmania.rustlang.alloc.rawvec

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind.*

public sealed class AllocInit {
    /**
     * The contents of the new memory are uninitialized.
     */
    public data object Uninitialized : AllocInit()
    /**
     * The new memory is guaranteed to be zeroed.
     */
    public data object Zeroed : AllocInit()
}

public typealias Cap = UsizeNoHighBit
