// port-lint: source collections/btree/mem.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.mem

import io.github.kotlinmania.rustlang.core.array.write
import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ptr.read

// keep as illustration and for future use

/**
 * This replaces the value behind the `v` unique reference by calling the
 * relevant function.
 *
 * If a panic occurs in the `change` closure, the entire process will be aborted.
 */
public fun <T> takeMut(v: T, change: (T) -> T) {
    replace(v, { value -> Pair(change(value), Unit) })
}

/**
 * This replaces the value behind the `v` unique reference by calling the
 * relevant function, and returns a result obtained along the way.
 *
 * If a panic occurs in the `change` closure, the entire process will be aborted.
 */
public fun <T, R> replace(v: T, change: (T) -> Pair<T, R>): R {
    open class PanicGuard {
        public open fun drop() {
            kmAbort()
        }
    }
    val guard = PanicGuard()
    // SAFETY: v is valid for reads and we write a new value before returning.
    val value = read(v, typeLayoutT = UnresolvedTypeLayout<T>())
    val (newValue, ret) = change(value)
    // SAFETY: newValue is T and v is valid for writes.
    run {
        write(v, newValue, typeLayoutT = UnresolvedTypeLayout<T>())
    }
    forget(guard)
    return ret
}
