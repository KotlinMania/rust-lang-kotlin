// port-lint: source symbolize/gimli/lru.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.lru

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

/**
 * least-recently-used cache with static size
 */
public open class Lru<T>(
    // SAFETY: len <= initialized values
    public var len: Int,
    public var arr: List<MaybeUninit<T>>,
)

public fun <T> defaultLruTN(n: Int): Lru<T, N> = Lru(len = 0, arr = List(n) { MaybeUninit.uninit(ObjectSlotLayout<T>()) })

public fun <T> Lru<T, N>.clear(n: Int) {
    val len = this.len
    this.len = 0
    // SAFETY: we can't touch these values again due to setting self.len = 0
    ptr.dropInPlace(mutPtrFromRustCast(addrOfMut(((this.arr).subList(0, len)))))
}

public fun <T> Lru<T, N>.iter(n: Int): Any = ((this.arr).subList(0, this.len)).map({ init -> init.assumeInitRef() })

public fun <T> Lru<T, N>.pushFront(value: T, n: Int): T? {
    if (n == 0) {
        return null
    } else if (this.len == n) {
        this.len = n - 1
        // SAFETY: we maintain len invariant and bail on N == 0
        ptr.dropInPlace(MutPtr((MutPtr((this.arr.asMutPtr()).rawAddress)).rawAddress + ((n - 1).toLong() * (ObjectSlotLayout<T>()).strideBytes)))
    }
    val lenToInit = this.len + 1
    var last = MaybeUninit.new(value, ObjectSlotLayout<T>())
    for (elem in ((this.arr).subList(0, lenToInit)).iterMut()) {
        // OPT(size): using `mem.swap` allows surprising size regressions
        last = replace(elem, last)
    }
    this.len = lenToInit
    return this.arr.firstMut()?.let({ elem1 -> elem1.assumeInitMut() })
}

public fun <T> Lru<T, N>.moveToFront(idx: Int, n: Int): T? {
    val elem = (((this.arr).subList(0, this.len)).getMut(idx) ?: return null)
    // SAFETY: we already bailed if the index is bad, so our slicing will be infallible,
    // so it is permissible to allow the len invariant to decay, as we always restore it
    var last = replace(elem, MaybeUninit.uninit(ObjectSlotLayout<T?>()))
    for (elem1 in ((this.arr).subList(0, ((idx) + 1))).iterMut()) {
        // OPT(size): using `mem.swap` allows surprising size regressions
        last = replace(elem1, last)
    }
    return this.arr.firstMut()?.let({ elem2 -> elem2.assumeInitMut() })
}
