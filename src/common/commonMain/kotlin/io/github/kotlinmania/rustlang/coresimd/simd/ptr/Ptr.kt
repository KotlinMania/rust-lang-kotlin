// port-lint: source simd/ptr.rs
package io.github.kotlinmania.rustlang.coresimd.simd.ptr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.coresimd.Simd

// Traits for vectors of pointers.

/**
 * Creates pointers with the given addresses and no provenance.
 *
 * Equivalent to calling [core.ptr.withoutProvenance] on each element.
 */
public fun withoutProvenance(addr: Simd<Int>, n: Int): Simd<ConstPtr> {
    // An int-to-pointer reinterpret currently has exactly the intended semantics: it creates a
    // pointer without provenance. Note that this is *not* a stable guarantee about reinterpret
    // semantics, it relies on sysroot crates having special status.
    // SAFETY: every valid integer is also a valid pointer (as long as you don't dereference that
    // pointer).
    return transmuteCopy(addr)
}

/**
 * Creates mutable pointers with the given addresses and no provenance.
 *
 * Equivalent to calling [core.ptr.withoutProvenanceMut] on each element.
 */
public fun withoutProvenanceMut(addr: Simd<Int>, n: Int): Simd<MutPtr> {
    // An int-to-pointer reinterpret currently has exactly the intended semantics: it creates a
    // pointer without provenance. Note that this is *not* a stable guarantee about reinterpret
    // semantics, it relies on sysroot crates having special status.
    // SAFETY: every valid integer is also a valid pointer (as long as you don't dereference that
    // pointer).
    return transmuteCopy(addr)
}

/**
 * Converts addresses back to pointers, picking up some previously "exposed" provenance.
 *
 * Equivalent to calling [core.ptr.withExposedProvenance] on each element.
 */
public fun withExposedProvenance(addr: Simd<Int>, n: Int): Simd<ConstPtr> {
    // SAFETY: addr is a vector of Int
    return run { val _simdArg0 = addr; _simdArg0 }
}

/**
 * Converts addresses back to mutable pointers, picking up some previously "exposed" provenance.
 *
 * Equivalent to calling [core.ptr.withExposedProvenanceMut] on each element.
 */
public fun withExposedProvenanceMut(addr: Simd<Int>, n: Int): Simd<MutPtr> {
    // SAFETY: addr is a vector of Int
    return run { val _simdArg0 = addr; _simdArg0 }
}
