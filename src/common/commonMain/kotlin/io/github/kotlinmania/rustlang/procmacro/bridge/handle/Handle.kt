// port-lint: source bridge/handle.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.handle

import io.github.kotlinmania.rustlang.alloc.collections.linkedlist.index
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.procmacro.bridge.fxhash.FxHashMap

// Server-side handles and storage for per-handle data.

internal typealias Handle = NonZero<UInt>

/**
 * A store that associates values of type `T` with numeric handles. A value can
 * be looked up using its handle.
 */
internal open class OwnedStore<T>(
    public var counter: AtomicU32,
    public var data: MutableMap<Handle, T>,
) {
    internal open fun alloc(x: T): Handle {
        val counter = this.counter.fetchAdd(1, Ordering.Relaxed)
        val handle = Handle.new(counter).expect("`proc_macro` handle counter overflowed")
        check(this.data.put(handle, x) == null)
        return handle
    }

    internal open fun take(h: Handle): T = (this.data.remove(h) ?: error("use-after-free in `proc_macro` handle"))

    public open fun index(h: Handle): T = (this.data.get(h) ?: error("use-after-free in `proc_macro` handle"))

    public companion object {
        internal fun <T> new(counter: AtomicU32): OwnedStore<T> {
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
internal open class InternedStore<T>(
    public var owned: OwnedStore<T>,
    public var interner: FxHashMap<T, Handle>,
) {
    internal open fun alloc(x: T): Handle {
        val owned = this.owned
        return this.interner.entry(x).orInsertWith({ owned.alloc(x) })
    }

    internal open fun copy(h: Handle): T = this.owned[h]

    public companion object {
        internal fun <T> new(counter: AtomicU32): InternedStore<T> = InternedStore(owned = OwnedStore.new(counter), interner = FxHashMap.default())
    }
}
