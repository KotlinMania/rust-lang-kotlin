// port-lint: source vec/spec_from_elem.rs
package io.github.kotlinmania.rustlang.alloc.vec.specfromelem

import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// Specialization trait used for Vec.fromElem
internal interface SpecFromElem<T> {
    public fun <A : Allocator> fromElem(elem: T, n: Int, alloc: A): List<T>
}

public fun <T, A : Allocator> specFromElemFromElemT(elem: T, n: Int, alloc: A): List<T> {
    val v = Vec.withCapacityIn(n, alloc)
    v.extendWith(n, elem)
    return v
}

public fun <A : Allocator> specFromElemFromElemByte(elem: Byte, n: Int, alloc: A): List<Byte> {
    if (elem == 0.toByte()) {
        return Vec(buf = RawVec.withCapacityZeroedIn(n, alloc), len = n)
    }
    val v = Vec.withCapacityIn(n, alloc)
    // ignore-tidy-undocumented-unchecked
    run {
        ptr.writeBytes(v.asMutPtr(), elem.toUByte(), n)
        v.setLen(n)
    }
    return v
}

public fun <A : Allocator> specFromElemFromElemUByte(elem: UByte, n: Int, alloc: A): List<UByte> {
    if (elem == 0u.toUByte()) {
        return Vec(buf = RawVec.withCapacityZeroedIn(n, alloc), len = n)
    }
    val v = Vec.withCapacityIn(n, alloc)
    // ignore-tidy-undocumented-unchecked
    run {
        ptr.writeBytes(v.asMutPtr(), elem, n)
        v.setLen(n)
    }
    return v
}

// A better way would be to implement this for all ZSTs which are `Copy` and have trivial `Clone`
// but the latter cannot be detected currently
public fun <A : Allocator> specFromElemFromElem(elem: Unit, n: Int, alloc: A): List<Unit> {
    val v = Vec.withCapacityIn(n, alloc)
    // SAFETY: the capacity has just been set to `n`
    // and `()` is a ZST with trivial `Clone` implementation
    run {
        v.setLen(n)
    }
    return v
}
