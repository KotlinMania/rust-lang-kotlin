// port-lint: source intrinsics/reflection.rs
package io.github.kotlinmania.rustlang.core.intrinsics.reflection

import io.github.kotlinmania.rustlang.any.TypeId
import io.github.kotlinmania.rustlang.core.mem.transmute

// Intrinsics for compile time reflection
//
// This are all methods operating on [`TypeId`](core.any.TypeId) which is the
// entry point to all compile time reflection.

/**
 * Tests (at compile-time) if two [any.TypeId] instances identify the
 * same type. This is necessary because at const-eval time the actual discriminating
 * data is opaque and cannot be inspected directly.
 *
 * The stabilized version of this intrinsic is the [PartialEq] impl for [core.any.TypeId].
 */
public fun typeIdEq(a: TypeId, b: TypeId): Boolean {
    // SAFETY: we know `TypeId` is 16 bytes of initialized data.
    // This is runtime-only code so we do not have to worry about provenance.
    return transmute(a) == transmute(b)
}
