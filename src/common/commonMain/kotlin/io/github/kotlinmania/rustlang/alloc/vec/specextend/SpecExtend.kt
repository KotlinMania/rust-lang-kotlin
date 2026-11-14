// port-lint: source vec/spec_extend.rs
package io.github.kotlinmania.rustlang.alloc.vec.specextend

import io.github.kotlinmania.rustlang.alloc.vec.intoiter.IntoIter
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen

// Specialization trait used for Vec.extend
internal interface SpecExtend<T, I> {
    public fun specExtend(iter: I)
}

public fun <T, I, A : Allocator> List<T>.specExtend(iter: I) where I : Iterator<T> {
    this.extendDesugared(iter)
}

public fun <T, I, A : Allocator> List<T>.specExtendTI(iterator: I) where I : TrustedLen<T> {
    this.extendTrusted(iterator)
}

public fun <T, A1 : Allocator, A2 : Allocator> List<T>.specExtend(iterator: IntoIter<T, A2>) {
    // ignore-tidy-undocumented-unchecked
    run {
        this.appendElements(((iterator.asSlice() as Any?) as Unit))
    }
    iterator.forgetRemainingElementsAndDealloc()
}

public fun <T, A : Allocator> List<T>.specExtend(iterator: ArrayDeque<T>) where T : TrivialClone {
    val slice = iterator.toList()
    // ignore-tidy-undocumented-unchecked
    this.appendElements(slice)
}
