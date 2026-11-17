// port-lint: source simd/ptr/const_ptr.rs
package io.github.kotlinmania.rustlang.coresimd.simd.ptr.constptr

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.coresimd.Simd

public typealias Usize = Simd<Int, N>

public typealias Isize = Simd<Int, N>

public typealias CastPtr = Simd<ConstPtr, N>

public typealias MutPtr = Simd<MutPtr, N>

public typealias Mask = Mask<Int, N>

/**
 * Operations on SIMD vectors of constant pointers.
 */
public interface SimdConstPtr<SelfType, Usize, Isize, out CastPtr, out MutPtr, out Mask> {
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
     * Equivalent to calling [pointer.castMut] on each element.
     */
    public fun castMut(): MutPtr

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
     * [withExposedProvenance] and returns the "address" portion.
     *
     * Equivalent to calling [pointer.exposeProvenance] on each element.
     *
     * See [withExposedProvenance] for the matching constructor.
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

public fun Simd<ConstPtr>.isNull(n: Int): Mask<Int> = Simd.splat(ConstPtr(0L), n).simdEq(this)

public fun Simd<ConstPtr>.cast(n: Int): Simd<ConstPtr> {
    check(sizeOf<Metadata>(typeLayoutT = UnresolvedTypeLayout<Metadata>()) == 0)
    check(sizeOf<Metadata>(typeLayoutT = UnresolvedTypeLayout<Metadata>()) == 0)
    // Safety: pointers can be cast
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<ConstPtr>.castMut(n: Int): Simd<MutPtr> {
    // Safety: pointers can be cast
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<ConstPtr>.addr(n: Int): Simd<Int> {
    // Note(strictProvenanceMagic): I am magic and should be a compiler intrinsic.
    // SAFETY: Pointer-to-integer transmutes are valid (if you are okay with losing the
    // provenance).
    return transmuteCopy(this)
}

public fun Simd<ConstPtr>.withAddr(addr: Simd<Int>, n: Int): Simd<ConstPtr> {
    // Note(strictProvenanceMagic): I am magic and should be a compiler intrinsic.
    //
    // In the mean-time, this operation is defined to be "as if" it was
    // a wrappingOffset, so we can emulate it as such. This should properly
    // restore pointer provenance even under today's compiler.
    return this.cast<UByte>(n).wrappingOffset(addr.cast<Int>(n) - this.addr(n).cast<Int>(n), n).cast(n)
}

public fun Simd<ConstPtr>.exposeProvenance(n: Int): Simd<Int> {
    // Safety: `self` is a pointer vector
    return run { val _simdArg0 = this; _simdArg0 }
}

public fun Simd<ConstPtr>.wrappingOffset(count: Simd<Int>, n: Int): Simd<ConstPtr> {
    // Safety: simdArithOffset takes a vector of pointers and a vector of offsets
    return run { val _simdArg0 = this; val _simdArg1 = count; _simdArg0 }
}

public fun Simd<ConstPtr>.wrappingAdd(count: Simd<Int>, n: Int): Simd<ConstPtr> = this.wrappingOffset(count.cast(n), n)

public fun Simd<ConstPtr>.wrappingSub(count: Simd<Int>, n: Int): Simd<ConstPtr> = this.wrappingOffset(-count.cast<Int>(n), n)
