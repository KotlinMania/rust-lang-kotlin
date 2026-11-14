// port-lint: source collections/btree/mem.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.mem

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout

// keep as illustration and for future use

/**
 * This replaces the value behind the `v` unique reference by calling the
 * relevant function.
 *
 * If a panic occurs in the `change` closure, the entire process will be aborted.
 */
internal fun <T> takeMut(v: T, change: (T) -> T) {
    replace(v, { value -> Pair(change(value), Unit) })
}

/**
 * This replaces the value behind the `v` unique reference by calling the
 * relevant function, and returns a result obtained along the way.
 *
 * If a panic occurs in the `change` closure, the entire process will be aborted.
 */
internal fun <T, R> replace(v: T, change: (T) -> Pair<T, R>): R {
    open class PanicGuard {
        public open fun drop() {
            abortImmediate()
        }
    }
    val guard = PanicGuard()
    // SAFETY: v is valid for reads and we write a new value before returning.
    val value = ptr.read(v, typeLayoutT = ObjectSlotLayout<T>())
    val (newValue, ret) = change(value)
    // SAFETY: newValue is T and v is valid for writes.
    run {
        ptr.write(v, newValue, typeLayoutT = ObjectSlotLayout<T>())
    }
    forget(guard)
    return ret
}
