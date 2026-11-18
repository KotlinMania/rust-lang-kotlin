// port-lint: source bridge/handle.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.handle

import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.procmacro.bridge.fxhash.FxHashMap

// Server-side handles and storage for per-handle data.

public typealias Handle = NonZero<UInt, NonZeroU32Inner>

/**
 * A store that associates values of type `T` with numeric handles. A value can
 * be looked up using its handle.
 */
public open class OwnedStore<T> internal constructor(
    public var counter: AtomicU32,
    dataCtorArg: MutableMap<Handle, T>,
) {
    public var data: MutableMap<Handle, T> = dataCtorArg
        internal set

    public open fun alloc(x: T): Handle {
        val counter = this.counter.fetchAdd(1, Ordering.Relaxed)
        val handle = NonZero.new(counter).expect("`proc_macro` handle counter overflowed")
        check(this.data.put(handle, x) == null)
        return handle
    }

    public open fun take(h: Handle): T = (this.data.remove(h) ?: error("use-after-free in `proc_macro` handle"))

    public open fun index(h: Handle): T = (this.data.get(h) ?: error("use-after-free in `proc_macro` handle"))

    public companion object {
        public operator fun <T> invoke(
            counter: AtomicU32,
            data: MutableMap<Handle, T>,
        ): OwnedStore<T> = OwnedStore<T>(
            counter = counter,
            dataCtorArg = data,
        )

        public fun <T> new(counter: AtomicU32): OwnedStore<T> {
            // Ensure the handle counter isn't 0, which would panic later,
            // when `NonZero.new` (aka `Handle.new`) is called in `alloc`.
            check(counter.load(Ordering.Relaxed) != 0)
            return OwnedStore(counter = counter, data = mutableMapOf())
        }
    }
}

/**
 * Like `OwnedStore`, but avoids storing any value more than once.
 */
public open class InternedStore<T> internal constructor(
    public var owned: OwnedStore<T>,
    internerCtorArg: FxHashMap<T, Handle>,
) {
    public var interner: FxHashMap<T, Handle> = internerCtorArg
        internal set

    public open fun alloc(x: T): Handle {
        val owned = this.owned
        return this.interner.entry(x).orInsertWith({ owned.alloc(x) })
    }

    public open fun copy(h: Handle): T = this.owned[h]

    public companion object {
        public operator fun <T> invoke(
            owned: OwnedStore<T>,
            interner: FxHashMap<T, Handle>,
        ): InternedStore<T> = InternedStore<T>(
            owned = owned,
            internerCtorArg = interner,
        )

        public fun <T> new(counter: AtomicU32): InternedStore<T> = InternedStore(owned = OwnedStore.new(counter), interner = FxHashMap.default())
    }
}
