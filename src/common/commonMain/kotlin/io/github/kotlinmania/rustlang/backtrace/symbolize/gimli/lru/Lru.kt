// port-lint: source symbolize/gimli/lru.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.lru

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

/**
 * least-recently-used cache with static size
 */
public open class Lru<T>(
    // SAFETY: len <= initialized values
    public var len: Int,
    public var arr: List<MaybeUninit<T>>,
) {
    public open fun clear() {
        val len = this.len
        this.len = 0
        // SAFETY: we can't touch these values again due to setting self.len = 0
        dropInPlace(mutPtrFromRustCast(addrOfMut(((this.arr).subList(0, len)))))
    }

    public open fun iter(): Any = ((this.arr).subList(0, this.len)).map({ init -> init.assumeInitRef() })

    public open fun pushFront(value: T): T? {
        if (N == 0) {
            return null
        } else if (this.len == N) {
            this.len = N - 1
            // SAFETY: we maintain len invariant and bail on N == 0
            dropInPlace(MutPtr((MutPtr((this.arr.asMutPtr()).rawAddress)).rawAddress + ((N - 1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)))
        }
        val lenToInit = this.len + 1
        var last = MaybeUninit.new(value, UnresolvedTypeLayout<T>())
        for (elem in ((this.arr).subList(0, lenToInit)).iterMut()) {
            // OPT(size): using `mem.swap` allows surprising size regressions
            last = replace(elem, last)
        }
        this.len = lenToInit
        return this.arr.firstMut()?.let({ elem1 -> elem1.assumeInitMut() })
    }

    public open fun moveToFront(idx: Int): T? {
        val elem = (((this.arr).subList(0, this.len)).getMut(idx) ?: return null)
        // SAFETY: we already bailed if the index is bad, so our slicing will be infallible,
        // so it is permissible to allow the len invariant to decay, as we always restore it
        var last = replace(elem, MaybeUninit.uninit(UnresolvedTypeLayout<T?>()))
        for (elem1 in ((this.arr).subList(0, ((idx) + 1))).iterMut()) {
            // OPT(size): using `mem.swap` allows surprising size regressions
            last = replace(elem1, last)
        }
        return this.arr.firstMut()?.let({ elem2 -> elem2.assumeInitMut() })
    }

    public companion object {
        public fun <T> default(n: Int): Lru<T> = Lru(len = 0, arr = List(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) })
    }
}
