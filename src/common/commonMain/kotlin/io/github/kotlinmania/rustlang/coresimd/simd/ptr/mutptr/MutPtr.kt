// port-lint: source simd/ptr/mut_ptr.rs
package io.github.kotlinmania.rustlang.coresimd.simd.ptr.mutptr

import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public typealias Usize = Simd<Int, N>

public typealias Isize = Simd<Int, N>

public typealias CastPtr = Simd<MutPtr, N>

public typealias ConstPtr = Simd<ConstPtr, N>

public typealias Mask = Mask<Int, N>

/**
 * Operations on SIMD vectors of mutable pointers.
 */
public interface SimdMutPtr<SelfType, Usize, Isize, out CastPtr, out ConstPtr, out Mask> {
    /**
     * Returns `true` for each element that is null.
     */
    public fun isNull(): Mask

    /**
     * Casts to a pointer of another type.
     *
     * Equivalent to calling [pointer.cast] on each element.
     */
    public fun <T> cast(): CastPtr

    /**
     * Changes constness without changing the type.
     *
     * Equivalent to calling [pointer.castConst] on each element.
     */
    public fun castConst(): ConstPtr

    /**
     * Gets the "address" portion of the pointer.
     *
     * Equivalent to calling [pointer.addr] on each element.
     */
    public fun addr(): Usize

    /**
     * Creates a new pointer with the given address.
     *
     * Equivalent to calling [pointer.withAddr] on each element.
     */
    public fun withAddr(addr: Usize): SelfType

    /**
     * Exposes the "provenance" part of the pointer for future use in
     * [withExposedProvenanceMut] and returns the "address" portion.
     *
     * Equivalent to calling [pointer.exposeProvenance] on each element.
     *
     * See [withExposedProvenanceMut] for the matching constructor.
     */
    public fun exposeProvenance(): Usize

    /**
     * Calculates the offset from a pointer using wrapping arithmetic.
     *
     * Equivalent to calling [pointer.wrappingOffset] on each element.
     */
    public fun wrappingOffset(offset: Isize): SelfType

    /**
     * Calculates the offset from a pointer using wrapping arithmetic.
     *
     * Equivalent to calling [pointer.wrappingAdd] on each element.
     */
    public fun wrappingAdd(count: Usize): SelfType

    /**
     * Calculates the offset from a pointer using wrapping arithmetic.
     *
     * Equivalent to calling [pointer.wrappingSub] on each element.
     */
    public fun wrappingSub(count: Usize): SelfType
}

public fun Simd<MutPtr, N>.isNull(n: Int): Mask<Int> = Simd.splat(MutPtr(0L)).simdEq(this)

public fun Simd<MutPtr, N>.cast(n: Int): Simd<MutPtr> {
    check(sizeOf<Metadata>(typeLayoutT = ObjectSlotLayout<Metadata>()) == 0)
    check(sizeOf<Metadata>(typeLayoutT = ObjectSlotLayout<Metadata>()) == 0)
    // Safety: pointers can be cast
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<MutPtr, N>.castConst(n: Int): Simd<ConstPtr> {
    // Safety: pointers can be cast
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<MutPtr, N>.addr(n: Int): Simd<Int> {
    // Note(strictProvenanceMagic): I am magic and should be a compiler intrinsic.
    // SAFETY: Pointer-to-integer transmutes are valid (if you are okay with losing the
    // provenance).
    return transmuteCopy(this)
}

public fun Simd<MutPtr, N>.withAddr(addr: Simd<Int>, n: Int): Simd<MutPtr, N> {
    // Note(strictProvenanceMagic): I am magic and should be a compiler intrinsic.
    //
    // In the mean-time, this operation is defined to be "as if" it was
    // a wrappingOffset, so we can emulate it as such. This should properly
    // restore pointer provenance even under today's compiler.
    return this.cast().wrappingOffset(addr.cast() - this.addr().cast()).cast()
}

public fun Simd<MutPtr, N>.exposeProvenance(n: Int): Simd<Int> {
    // Safety: `self` is a pointer vector
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<MutPtr, N>.wrappingOffset(count: Simd<Int>, n: Int): Simd<MutPtr, N> {
    // Safety: simdArithOffset takes a vector of pointers and a vector of offsets
    return run { val _simdArg0 = this; val _simdArg1 = count; _simdArg0 }
}

public fun Simd<MutPtr, N>.wrappingAdd(count: Simd<Int>, n: Int): Simd<MutPtr, N> = this.wrappingOffset(count.cast())

public fun Simd<MutPtr, N>.wrappingSub(count: Simd<Int>, n: Int): Simd<MutPtr, N> = this.wrappingOffset(-count.cast())
