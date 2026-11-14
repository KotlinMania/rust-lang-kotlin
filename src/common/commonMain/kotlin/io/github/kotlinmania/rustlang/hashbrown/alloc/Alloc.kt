// port-lint: source alloc.rs
package io.github.kotlinmania.rustlang.hashbrown.alloc

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.alloc.inner.Allocator

public fun <A : Allocator> doAlloc(alloc: A, layout: Layout): Result<NonNull, Unit> =
    run {
        val matchVal = alloc.allocate(layout)
        when (matchVal) { is Result.Ok<*> -> {
            val ptr = matchVal.value
            Result.Ok(ptr)
        }; is Result.Err<*> -> {
            Result.Err(Unit)
        } }
    }
