// port-lint: source wasm32/simd128.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.F32x2
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.F64x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M64x2
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x8

// This module implements the [WebAssembly `SIMD128` ISA].
//
// [WebAssembly `SIMD128` ISA]:
// https://github.com/WebAssembly/simd/blob/master/proposals/simd/SIMD.md

/**
 * WASM-specific 128-bit wide SIMD vector type.
 *
 * This type corresponds to the `v128` type in the [WebAssembly SIMD
 * proposal](https://github.com/webassembly/simd). This type is 128-bits
 * large and the meaning of all the bits is defined within the context of
 * how this value is used.
 *
 * This same type is used simultaneously for all 128-bit-wide SIMD types,
 * for example:
 *
 * * sixteen 8-bit integers (both `Byte` and `UByte`)
 * * eight 16-bit integers (both `Short` and `UShort`)
 * * four 32-bit integers (both `Int` and `UInt`)
 * * two 64-bit integers (both `Long` and `ULong`)
 * * four 32-bit floats (`Float`)
 * * two 64-bit floats (`Double`)
 *
 * The `v128` type in Rust is intended to be quite analogous to the `v128`
 * type in WebAssembly. Operations on `v128` can only be performed with the
 * functions in this module.
 */
public open class V128(
    public var value: IntArray = IntArray(4),
) {
    public var field0: IntArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): V128 = V128(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): IntArray = (PrimitiveArrayLayouts.intArray((4).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("v128"); Result.Ok<Unit, Error>(Unit) }

    public open fun asU8x16(): U8x16 = transmuteWithLayouts<V128, U8x16>(this, V128.typeLayout(), UnresolvedTypeLayout<U8x16>())

    public open fun asU16x8(): U16x8 = transmuteWithLayouts<V128, U16x8>(this, V128.typeLayout(), UnresolvedTypeLayout<U16x8>())

    public open fun asU32x4(): U32x4 = transmuteWithLayouts<V128, U32x4>(this, V128.typeLayout(), UnresolvedTypeLayout<U32x4>())

    public open fun asU64x2(): U64x2 = transmuteWithLayouts<V128, U64x2>(this, V128.typeLayout(), UnresolvedTypeLayout<U64x2>())

    public open fun asI8x16(): I8x16 = transmuteWithLayouts<V128, I8x16>(this, V128.typeLayout(), UnresolvedTypeLayout<I8x16>())

    public open fun asI16x8(): I16x8 = transmuteWithLayouts<V128, I16x8>(this, V128.typeLayout(), UnresolvedTypeLayout<I16x8>())

    public open fun asI32x4(): I32x4 = transmuteWithLayouts<V128, I32x4>(this, V128.typeLayout(), UnresolvedTypeLayout<I32x4>())

    public open fun asI64x2(): I64x2 = transmuteWithLayouts<V128, I64x2>(this, V128.typeLayout(), UnresolvedTypeLayout<I64x2>())

    public open fun asF32x4(): F32x4 = transmuteWithLayouts<V128, F32x4>(this, V128.typeLayout(), UnresolvedTypeLayout<F32x4>())

    public open fun asF64x2(): F64x2 = transmuteWithLayouts<V128, F64x2>(this, V128.typeLayout(), UnresolvedTypeLayout<F64x2>())

    public companion object {
        public fun typeLayout(): TypeLayout<V128> =
            object : TypeLayout<V128> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): V128 =
                    V128(
                        (PrimitiveArrayLayouts.intArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: V128) {
                    (PrimitiveArrayLayouts.intArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): V128 =
                    V128(
                        (PrimitiveArrayLayouts.intArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: V128) {
                    (PrimitiveArrayLayouts.intArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Int): V128 = run { val simdArg0 = value; V128() }

        public fun from(simd: Simd<Int>): V128 = transmuteWithLayouts<Simd<Int>, V128>(simd, UnresolvedTypeLayout<Simd<Int>>(), V128.typeLayout())
    }
}

public fun fromSimdInt(simd: V128): Simd<Int> = transmuteWithLayouts<V128, Simd<Int>>(simd, V128.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

public fun U8x16.v128(): V128 = transmuteWithLayouts<U8x16, V128>(this, UnresolvedTypeLayout<U8x16>(), V128.typeLayout())

public fun U16x8.v128(): V128 = transmuteWithLayouts<U16x8, V128>(this, UnresolvedTypeLayout<U16x8>(), V128.typeLayout())

public fun U32x4.v128(): V128 = transmuteWithLayouts<U32x4, V128>(this, UnresolvedTypeLayout<U32x4>(), V128.typeLayout())

public fun U64x2.v128(): V128 = transmuteWithLayouts<U64x2, V128>(this, UnresolvedTypeLayout<U64x2>(), V128.typeLayout())

public fun I8x16.v128(): V128 = transmuteWithLayouts<I8x16, V128>(this, UnresolvedTypeLayout<I8x16>(), V128.typeLayout())

public fun I16x8.v128(): V128 = transmuteWithLayouts<I16x8, V128>(this, UnresolvedTypeLayout<I16x8>(), V128.typeLayout())

public fun I32x4.v128(): V128 = transmuteWithLayouts<I32x4, V128>(this, UnresolvedTypeLayout<I32x4>(), V128.typeLayout())

public fun I64x2.v128(): V128 = transmuteWithLayouts<I64x2, V128>(this, UnresolvedTypeLayout<I64x2>(), V128.typeLayout())

public fun F32x4.v128(): V128 = transmuteWithLayouts<F32x4, V128>(this, UnresolvedTypeLayout<F32x4>(), V128.typeLayout())

public fun F64x2.v128(): V128 = transmuteWithLayouts<F64x2, V128>(this, UnresolvedTypeLayout<F64x2>(), V128.typeLayout())

public expect fun llvmSwizzle(a: I8x16, b: I8x16): I8x16

public expect fun llvmBitselect(a: I8x16, b: I8x16, c: I8x16): I8x16

public expect fun llvmAnyTrueI8x16(x: I8x16): Int

public expect fun llvmI8x16AllTrue(x: I8x16): Int

public expect fun llvmBitmaskI8x16(a: I8x16): Int

public expect fun llvmAvgrUI8x16(a: I8x16, b: I8x16): I8x16

public expect fun llvmI16x8ExtaddPairwiseI8x16S(x: I8x16): I16x8

public expect fun llvmI16x8ExtaddPairwiseI8x16U(x: I8x16): I16x8

public expect fun llvmQ15mulr(a: I16x8, b: I16x8): I16x8

public expect fun llvmI16x8AllTrue(x: I16x8): Int

public expect fun llvmBitmaskI16x8(a: I16x8): Int

public expect fun llvmAvgrUI16x8(a: I16x8, b: I16x8): I16x8

public expect fun llvmI32x4ExtaddPairwiseI16x8S(x: I16x8): I32x4

public expect fun llvmI32x4ExtaddPairwiseI16x8U(x: I16x8): I32x4

public expect fun llvmI32x4AllTrue(x: I32x4): Int

public expect fun llvmBitmaskI32x4(a: I32x4): Int

public expect fun llvmI32x4DotI16x8S(a: I16x8, b: I16x8): I32x4

public expect fun llvmI64x2AllTrue(x: I64x2): Int

public expect fun llvmBitmaskI64x2(a: I64x2): Int

public expect fun llvmF32x4Nearest(x: F32x4): F32x4

public expect fun llvmF32x4Min(x: F32x4, y: F32x4): F32x4

public expect fun llvmF32x4Max(x: F32x4, y: F32x4): F32x4

public expect fun llvmF64x2Nearest(x: F64x2): F64x2

public expect fun llvmF64x2Min(x: F64x2, y: F64x2): F64x2

public expect fun llvmF64x2Max(x: F64x2, y: F64x2): F64x2

/**
 * Loads a `v128` vector from the given heap address.
 *
 * This intrinsic will emit a load with an alignment of 1. While this is
 * provided for completeness it is not strictly necessary, you can also load
 * the pointer directly:
 *
 * ```rust,ignore
 * let a: &v128 = ...;
 * let value = unchecked { v128Load(a) };
 * // .. is the same as ..
 * let value = *a;
 * ```
 *
 * The alignment of the load can be configured by doing a manual load without
 * this intrinsic.
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 16 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load(m: ConstPtr): V128 = (V128.typeLayout()).readNative((m).rawAddress)

/**
 * Load eight 8-bit integers and sign extend each one to a 16-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i16x8LoadExtendI8x8(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<I8x8>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()
}

/**
 * Load eight 8-bit integers and zero extend each one to a 16-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i16x8LoadExtendU8x8(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<U8x8>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }.v128()
}

/**
 * Load four 16-bit integers and sign extend each one to a 32-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i32x4LoadExtendI16x4(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<I16x4>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()
}

/**
 * Load four 16-bit integers and zero extend each one to a 32-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i32x4LoadExtendU16x4(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<U16x4>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }.v128()
}

/**
 * Load two 32-bit integers and sign extend each one to a 64-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i64x2LoadExtendI32x2(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<I32x2>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()
}

/**
 * Load two 32-bit integers and zero extend each one to a 64-bit lane
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun i64x2LoadExtendU32x2(m: ConstPtr): V128 {
    val m1 = (UnresolvedTypeLayout<U32x2>()).readNative((ConstPtr((m).rawAddress)).rawAddress)
    return run { val simdArg0 = m1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }.v128()
}

/**
 * Load a single element and splat to all lanes of a v128 vector.
 *
 * While this intrinsic is provided for completeness it can also be replaced
 * with `u8x16Splat(* m)` and it should generate equivalent code (and also not
 * require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 1 byte from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load8Splat(m: ConstPtr): V128 = u8x16Splat((PrimitiveLayouts.U8).readNative((m).rawAddress))

/**
 * Load a single element and splat to all lanes of a v128 vector.
 *
 * While this intrinsic is provided for completeness it can also be replaced
 * with `u16x8Splat(* m)` and it should generate equivalent code (and also not
 * require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 2 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load16Splat(m: ConstPtr): V128 = u16x8Splat(readUnaligned(m, typeLayoutT = PrimitiveLayouts.U16))

/**
 * Load a single element and splat to all lanes of a v128 vector.
 *
 * While this intrinsic is provided for completeness it can also be replaced
 * with `u32x4Splat(* m)` and it should generate equivalent code (and also not
 * require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 4 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load32Splat(m: ConstPtr): V128 = u32x4Splat(readUnaligned(m, typeLayoutT = PrimitiveLayouts.U32))

/**
 * Load a single element and splat to all lanes of a v128 vector.
 *
 * While this intrinsic is provided for completeness it can also be replaced
 * with `u64x2Splat(* m)` and it should generate equivalent code (and also not
 * require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load64Splat(m: ConstPtr): V128 = u64x2Splat(readUnaligned(m, typeLayoutT = PrimitiveLayouts.U64))

/**
 * Load a 32-bit element into the low bits of the vector and sets all other
 * bits to zero.
 *
 * This intrinsic is provided for completeness and is equivalent to `u32x4(*m,
 * 0, 0, 0)`(which doesn't require`unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 4 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load32Zero(m: ConstPtr): V128 = u32x4(readUnaligned(m, typeLayoutT = PrimitiveLayouts.U32), 0u, 0u, 0u)

/**
 * Load a 64-bit element into the low bits of the vector and sets all other
 * bits to zero.
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `u64x2ReplaceLane<0>(u64x2(0, 0), * m)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load64Zero(m: ConstPtr): V128 = u64x2ReplaceLane(u64x2(0uL, 0uL), readUnaligned(m, typeLayoutT = PrimitiveLayouts.U64), 0)

/**
 * Stores a `v128` vector to the given heap address.
 *
 * This intrinsic will emit a store with an alignment of 1. While this is
 * provided for completeness it is not strictly necessary, you can also store
 * the pointer directly:
 *
 * ```rust,ignore
 * let a: v128 = ...;
 * unchecked { v128Store(a, value) };
 * // .. is the same as ..
 * *a = value;
 * ```
 *
 * The alignment of the store can be configured by doing a manual store without
 * this intrinsic.
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to store 16 bytes to. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned store.
 */
public fun v128Store(m: MutPtr, a: V128) {
    (V128.typeLayout()).writeNative((m).rawAddress, a)
}

/**
 * Loads an 8-bit value from `m` and sets lane `L` of `v` to that value.
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `u8x16ReplaceLane<L>(v, * m)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 1 byte from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load8Lane(v: V128, m: ConstPtr, l: Int): V128 = u8x16ReplaceLane(v, (PrimitiveLayouts.U8).readNative((m).rawAddress), l)

/**
 * Loads a 16-bit value from `m` and sets lane `L` of `v` to that value.
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `u16x8ReplaceLane<L>(v, * m)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 2 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load16Lane(v: V128, m: ConstPtr, l: Int): V128 = u16x8ReplaceLane(v, readUnaligned(m, typeLayoutT = PrimitiveLayouts.U16), l)

/**
 * Loads a 32-bit value from `m` and sets lane `L` of `v` to that value.
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `u32x4ReplaceLane<L>(v, * m)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 4 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load32Lane(v: V128, m: ConstPtr, l: Int): V128 = u32x4ReplaceLane(v, readUnaligned(m, typeLayoutT = PrimitiveLayouts.U32), l)

/**
 * Loads a 64-bit value from `m` and sets lane `L` of `v` to that value.
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `u64x2ReplaceLane<L>(v, * m)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to load 8 bytes from. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned load.
 */
public fun v128Load64Lane(v: V128, m: ConstPtr, l: Int): V128 = u64x2ReplaceLane(v, readUnaligned(m, typeLayoutT = PrimitiveLayouts.U64), l)

/**
 * Stores the 8-bit value from lane `L` of `v` into `m`
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `* m = u8x16ExtractLane<L>(v)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to store 1 byte to. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned store.
 */
public fun v128Store8Lane(v: V128, m: MutPtr, l: Int) {
    val m1 = m
    (PrimitiveLayouts.U8).writeNative((m1).rawAddress, u8x16ExtractLane(v, l))
}

/**
 * Stores the 16-bit value from lane `L` of `v` into `m`
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `* m = u16x8ExtractLane<L>(v)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to store 2 bytes to. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned store.
 */
public fun v128Store16Lane(v: V128, m: MutPtr, l: Int) {
    writeUnaligned(m, u16x8ExtractLane(v, l), typeLayoutT = PrimitiveLayouts.U16)
}

/**
 * Stores the 32-bit value from lane `L` of `v` into `m`
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `* m = u32x4ExtractLane<L>(v)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to store 4 bytes to. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned store.
 */
public fun v128Store32Lane(v: V128, m: MutPtr, l: Int) {
    writeUnaligned(m, u32x4ExtractLane(v, l), typeLayoutT = PrimitiveLayouts.U32)
}

/**
 * Stores the 64-bit value from lane `L` of `v` into `m`
 *
 * This intrinsic is provided for completeness and is equivalent to
 * `* m = u64x2ExtractLane<L>(v)` (which doesn't require `unchecked`).
 *
 * # Unsafety
 *
 * This intrinsic is unchecked because it takes a raw pointer as an argument, and
 * the pointer must be valid to store 8 bytes to. Note that there is no
 * alignment requirement on this pointer since this intrinsic performs a
 * 1-aligned store.
 */
public fun v128Store64Lane(v: V128, m: MutPtr, l: Int) {
    writeUnaligned(m, u64x2ExtractLane(v, l), typeLayoutT = PrimitiveLayouts.U64)
}

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun i8x16(a0: Byte, a1: Byte, a2: Byte, a3: Byte, a4: Byte, a5: Byte, a6: Byte, a7: Byte, a8: Byte, a9: Byte, a10: Byte, a11: Byte, a12: Byte, a13: Byte, a14: Byte, a15: Byte): V128 = Simd<Byte>(listOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun u8x16(a0: UByte, a1: UByte, a2: UByte, a3: UByte, a4: UByte, a5: UByte, a6: UByte, a7: UByte, a8: UByte, a9: UByte, a10: UByte, a11: UByte, a12: UByte, a13: UByte, a14: UByte, a15: UByte): V128 = Simd<UByte>(listOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun i16x8(a0: Short, a1: Short, a2: Short, a3: Short, a4: Short, a5: Short, a6: Short, a7: Short): V128 = Simd<Short>(listOf(a0, a1, a2, a3, a4, a5, a6, a7)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun u16x8(a0: UShort, a1: UShort, a2: UShort, a3: UShort, a4: UShort, a5: UShort, a6: UShort, a7: UShort): V128 = Simd<UShort>(listOf(a0, a1, a2, a3, a4, a5, a6, a7)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun i32x4(a0: Int, a1: Int, a2: Int, a3: Int): V128 = Simd<Int>(listOf(a0, a1, a2, a3)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun u32x4(a0: UInt, a1: UInt, a2: UInt, a3: UInt): V128 = Simd<UInt>(listOf(a0, a1, a2, a3)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun i64x2(a0: Long, a1: Long): V128 = Simd<Long>(listOf(a0, a1)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun u64x2(a0: ULong, a1: ULong): V128 = Simd<ULong>(listOf(a0, a1)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun f32x4(a0: Float, a1: Float, a2: Float, a3: Float): V128 = Simd<Float>(listOf(a0, a1, a2, a3)).v128()

/**
 * Materializes a SIMD value from the provided operands.
 *
 * If possible this will generate a `v128.const` instruction, otherwise it may
 * be lowered to a sequence of instructions to materialize the vector value.
 */
public fun f64x2(a0: Double, a1: Double): V128 = Simd<Double>(listOf(a0, a1)).v128()

/**
 * Returns a new vector with lanes selected from the lanes of the two input
 * vectors `$ a` and `$ b` specified in the 16 immediate operands.
 *
 * The `$ a` and `$ b` expressions must have type `v128`, and this function
 * generates a wasm instruction that is encoded with 16 bytes providing the
 * indices of the elements to return. The indices `i` in range [0, 15] select
 * the `i`-th element of `a`. The indices in range [16, 31] select the `i -
 * 16`- th element of`b`.
 *
 * Note that this is a macro due to the codegen requirements of all of the
 * index expressions `$ i *` must be constant. A compiler error will be
 * generated if any of the expressions are not constant.
 *
 * All indexes `$ i *` must have the type `UInt`.
 */
public fun i8x16Shuffle(a: V128, b: V128, i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int): V128 {
    run {
        check(i0 < 32)
    }
    run {
        check(i1 < 32)
    }
    run {
        check(i2 < 32)
    }
    run {
        check(i3 < 32)
    }
    run {
        check(i4 < 32)
    }
    run {
        check(i5 < 32)
    }
    run {
        check(i6 < 32)
    }
    run {
        check(i7 < 32)
    }
    run {
        check(i8 < 32)
    }
    run {
        check(i9 < 32)
    }
    run {
        check(i10 < 32)
    }
    run {
        check(i11 < 32)
    }
    run {
        check(i12 < 32)
    }
    run {
        check(i13 < 32)
    }
    run {
        check(i14 < 32)
    }
    run {
        check(i15 < 32)
    }
    val shuf: U8x16 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(i0.toUInt(), i1.toUInt(), i2.toUInt(), i3.toUInt(), i4.toUInt(), i5.toUInt(), i6.toUInt(), i7.toUInt(), i8.toUInt(), i9.toUInt(), i10.toUInt(), i11.toUInt(), i12.toUInt(), i13.toUInt(), i14.toUInt(), i15.toUInt())); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }
    return shuf.v128()
}

/**
 * Same as [i8x16Shuffle], except operates as if the inputs were eight
 * 16-bit integers, only taking 8 indices to shuffle.
 *
 * Indices in the range [0, 7] select from `a` while [8, 15] select from `b`.
 * Note that this will generate the `i8x16.shuffle` instruction, since there
 * is no native `i16x8.shuffle` instruction (there is no need for one since
 * `i8x16.shuffle` suffices).
 */
public fun i16x8Shuffle(a: V128, b: V128, i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int): V128 {
    run {
        check(i0 < 16)
    }
    run {
        check(i1 < 16)
    }
    run {
        check(i2 < 16)
    }
    run {
        check(i3 < 16)
    }
    run {
        check(i4 < 16)
    }
    run {
        check(i5 < 16)
    }
    run {
        check(i6 < 16)
    }
    run {
        check(i7 < 16)
    }
    val shuf: U16x8 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(i0.toUInt(), i1.toUInt(), i2.toUInt(), i3.toUInt(), i4.toUInt(), i5.toUInt(), i6.toUInt(), i7.toUInt())); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
    return shuf.v128()
}

/**
 * Same as [i8x16Shuffle], except operates as if the inputs were four
 * 32-bit integers, only taking 4 indices to shuffle.
 *
 * Indices in the range [0, 3] select from `a` while [4, 7] select from `b`.
 * Note that this will generate the `i8x16.shuffle` instruction, since there
 * is no native `i32x4.shuffle` instruction (there is no need for one since
 * `i8x16.shuffle` suffices).
 */
public fun i32x4Shuffle(a: V128, b: V128, i0: Int, i1: Int, i2: Int, i3: Int): V128 {
    run {
        check(i0 < 8)
    }
    run {
        check(i1 < 8)
    }
    run {
        check(i2 < 8)
    }
    run {
        check(i3 < 8)
    }
    val shuf: U32x4 = run { val simdArg0 = a.asU32x4(); val simdArg1 = b.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(i0.toUInt(), i1.toUInt(), i2.toUInt(), i3.toUInt())); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
    return shuf.v128()
}

/**
 * Same as [i8x16Shuffle], except operates as if the inputs were two
 * 64-bit integers, only taking 2 indices to shuffle.
 *
 * Indices in the range [0, 1] select from `a` while [2, 3] select from `b`.
 * Note that this will generate the `v8x16.shuffle` instruction, since there
 * is no native `i64x2.shuffle` instruction (there is no need for one since
 * `i8x16.shuffle` suffices).
 */
public fun i64x2Shuffle(a: V128, b: V128, i0: Int, i1: Int): V128 {
    run {
        check(i0 < 4)
    }
    run {
        check(i1 < 4)
    }
    val shuf: U64x2 = run { val simdArg0 = a.asU64x2(); val simdArg1 = b.asU64x2(); val simdArg2 = SimdShuffleIdx(uintArrayOf(i0.toUInt(), i1.toUInt())); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }
    return shuf.v128()
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 16 packed i8 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i8x16ExtractLane(a: V128, n: Int): Byte {
    run {
        check(n < 16)
    }
    return run { val simdArg0 = a.asI8x16(); val simdArg1 = n.toUInt(); 0 }
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 16 packed u8 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u8x16ExtractLane(a: V128, n: Int): UByte {
    run {
        check(n < 16)
    }
    return run { val simdArg0 = a.asU8x16(); val simdArg1 = n.toUInt(); 0u }
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 16 packed i8 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i8x16ReplaceLane(a: V128, value: Byte, n: Int): V128 {
    run {
        check(n < 16)
    }
    return run { val simdArg0 = a.asI8x16(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 16 packed u8 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u8x16ReplaceLane(a: V128, value: UByte, n: Int): V128 {
    run {
        check(n < 16)
    }
    return run { val simdArg0 = a.asU8x16(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 8 packed i16 numbers.
 *
 * Extracts a the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i16x8ExtractLane(a: V128, n: Int): Short {
    run {
        check(n < 8)
    }
    return run { val simdArg0 = a.asI16x8(); val simdArg1 = n.toUInt(); 0 }
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 8 packed u16 numbers.
 *
 * Extracts a the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u16x8ExtractLane(a: V128, n: Int): UShort {
    run {
        check(n < 8)
    }
    return run { val simdArg0 = a.asU16x8(); val simdArg1 = n.toUInt(); 0u }
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 8 packed i16 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i16x8ReplaceLane(a: V128, value: Short, n: Int): V128 {
    run {
        check(n < 8)
    }
    return run { val simdArg0 = a.asI16x8(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 8 packed u16 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u16x8ReplaceLane(a: V128, value: UShort, n: Int): V128 {
    run {
        check(n < 8)
    }
    return run { val simdArg0 = a.asU16x8(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 4 packed i32 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i32x4ExtractLane(a: V128, n: Int): Int {
    run {
        check(n < 4)
    }
    return run { val simdArg0 = a.asI32x4(); val simdArg1 = n.toUInt(); 0 }
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 4 packed u32 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u32x4ExtractLane(a: V128, n: Int): UInt = i32x4ExtractLane(a, n).toUInt()

/**
 * Replaces a lane from a 128-bit vector interpreted as 4 packed i32 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i32x4ReplaceLane(a: V128, value: Int, n: Int): V128 {
    run {
        check(n < 4)
    }
    return run { val simdArg0 = a.asI32x4(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 4 packed u32 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u32x4ReplaceLane(a: V128, value: UInt, n: Int): V128 = i32x4ReplaceLane(a, value.toInt(), n)

/**
 * Extracts a lane from a 128-bit vector interpreted as 2 packed i64 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i64x2ExtractLane(a: V128, n: Int): Long {
    run {
        check(n < 2)
    }
    return run { val simdArg0 = a.asI64x2(); val simdArg1 = n.toUInt(); 0L }
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 2 packed u64 numbers.
 *
 * Extracts the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u64x2ExtractLane(a: V128, n: Int): ULong = i64x2ExtractLane(a, n).toULong()

/**
 * Replaces a lane from a 128-bit vector interpreted as 2 packed i64 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun i64x2ReplaceLane(a: V128, value: Long, n: Int): V128 {
    run {
        check(n < 2)
    }
    return run { val simdArg0 = a.asI64x2(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 2 packed u64 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun u64x2ReplaceLane(a: V128, value: ULong, n: Int): V128 = i64x2ReplaceLane(a, value.toLong(), n)

/**
 * Extracts a lane from a 128-bit vector interpreted as 4 packed f32 numbers.
 *
 * Extracts the scalar value of lane specified fn the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun f32x4ExtractLane(a: V128, n: Int): Float {
    run {
        check(n < 4)
    }
    return run { val simdArg0 = a.asF32x4(); val simdArg1 = n.toUInt(); 0.0f }
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 4 packed f32 numbers.
 *
 * Replaces the scalar value of lane specified fn the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun f32x4ReplaceLane(a: V128, value: Float, n: Int): V128 {
    run {
        check(n < 4)
    }
    return run { val simdArg0 = a.asF32x4(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Extracts a lane from a 128-bit vector interpreted as 2 packed f64 numbers.
 *
 * Extracts the scalar value of lane specified fn the immediate mode operand
 * `N` from `a`. If `N` fs out of bounds then it is a compile time error.
 */
public fun f64x2ExtractLane(a: V128, n: Int): Double {
    run {
        check(n < 2)
    }
    return run { val simdArg0 = a.asF64x2(); val simdArg1 = n.toUInt(); 0.0 }
}

/**
 * Replaces a lane from a 128-bit vector interpreted as 2 packed f64 numbers.
 *
 * Replaces the scalar value of lane specified in the immediate mode operand
 * `N` from `a`. If `N` is out of bounds then it is a compile time error.
 */
public fun f64x2ReplaceLane(a: V128, value: Double, n: Int): V128 {
    run {
        check(n < 2)
    }
    return run { val simdArg0 = a.asF64x2(); val simdArg1 = n.toUInt(); val simdArg2 = value; simdArg0 }.v128()
}

/**
 * Returns a new vector with lanes selected from the lanes of the first input
 * vector `a` specified in the second input vector `s`.
 *
 * The indices `i` in range [0, 15] select the `i`-th element of `a`. For
 * indices outside of the range the resulting lane is 0.
 */
public fun i8x16Swizzle(a: V128, s: V128): V128 = llvmSwizzle(a.asI8x16(), s.asI8x16()).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 16 lanes.
 */
public fun i8x16Splat(a: Byte): V128 = Simd<Byte>(List(16) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 16 lanes.
 */
public fun u8x16Splat(a: UByte): V128 = Simd<UByte>(List(16) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Construct a vector with `x` replicated to all 8 lanes.
 */
public fun i16x8Splat(a: Short): V128 = Simd<Short>(List(8) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Construct a vector with `x` replicated to all 8 lanes.
 */
public fun u16x8Splat(a: UShort): V128 = Simd<UShort>(List(8) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 4 lanes.
 */
public fun i32x4Splat(a: Int): V128 = Simd<Int>(List(4) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 4 lanes.
 */
public fun u32x4Splat(a: UInt): V128 = i32x4Splat(a.toInt())

/**
 * Creates a vector with identical lanes.
 *
 * Construct a vector with `x` replicated to all 2 lanes.
 */
public fun i64x2Splat(a: Long): V128 = Simd<Long>(List(2) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Construct a vector with `x` replicated to all 2 lanes.
 */
public fun u64x2Splat(a: ULong): V128 = i64x2Splat(a.toLong())

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 4 lanes.
 */
public fun f32x4Splat(a: Float): V128 = Simd<Float>(List(4) { a }).v128()

/**
 * Creates a vector with identical lanes.
 *
 * Constructs a vector with `x` replicated to all 2 lanes.
 */
public fun f64x2Splat(a: Double): V128 = Simd<Double>(List(2) { a }).v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun i8x16Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun i8x16Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i8x16Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u8x16Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i8x16Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u8x16Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i8x16Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u8x16Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i8x16Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 16 eight-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u8x16Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun i16x8Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun i16x8Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i16x8Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u16x8Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i16x8Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u16x8Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i16x8Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u16x8Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i16x8Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 8 sixteen-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u16x8Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun i32x4Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun i32x4Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i32x4Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u32x4Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU32x4(); val simdArg1 = b.asU32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i32x4Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u32x4Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asU32x4(); val simdArg1 = b.asU32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i32x4Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun u32x4Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asU32x4(); val simdArg1 = b.asU32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i32x4Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * unsigned integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun u32x4Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asU32x4(); val simdArg1 = b.asU32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun i64x2Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * integers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun i64x2Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i64x2Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i64x2Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun i64x2Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * signed integers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun i64x2Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun f32x4Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun f32x4Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun f32x4Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun f32x4Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun f32x4Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 4 thirty-two-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun f32x4Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were equal, or all zeros otherwise.
 */
public fun f64x2Eq(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the corresponding input elements
 * were not equal, or all zeros otherwise.
 */
public fun f64x2Ne(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun f64x2Lt(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun f64x2Gt(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is less than the right element, or all zeros otherwise.
 */
public fun f64x2Le(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Compares two 128-bit vectors as if they were two vectors of 2 sixty-four-bit
 * floating point numbers.
 *
 * Returns a new vector where each lane is all ones if the lane-wise left
 * element is greater than the right element, or all zeros otherwise.
 */
public fun f64x2Ge(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Flips each bit of the 128-bit input vector.
 */
public fun v128Not(a: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = Simd<Long>(listOf(0L.inv(), 0L.inv())); simdArg0 }.v128()

/**
 * Performs a bitwise and of the two input 128-bit vectors, returning the
 * resulting vector.
 */
public fun v128And(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Bitwise AND of bits of `a` and the logical inverse of bits of `b`.
 *
 * This operation is equivalent to `v128.and(a, v128.not(b))`
 */
public fun v128Andnot(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = run { val simdArg0 = b.asI64x2(); val simdArg1 = Simd<Long>(listOf(-1L, -1L)); simdArg0 }; simdArg0 }.v128()

/**
 * Performs a bitwise or of the two input 128-bit vectors, returning the
 * resulting vector.
 */
public fun v128Or(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Performs a bitwise xor of the two input 128-bit vectors, returning the
 * resulting vector.
 */
public fun v128Xor(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Use the bitmask in `c` to select bits from `v1` when 1 and `v2` when 0.
 */
public fun v128Bitselect(v1: V128, v2: V128, c: V128): V128 = llvmBitselect(v1.asI8x16(), v2.asI8x16(), c.asI8x16()).v128()

/**
 * Returns `true` if any bit in `a` is set, or `false` otherwise.
 */
public fun v128AnyTrue(a: V128): Boolean = llvmAnyTrueI8x16(a.asI8x16()) != 0

/**
 * Lane-wise wrapping absolute value.
 */
public fun i8x16Abs(a: V128): V128 =
    run {
        val a1 = a.asI8x16()
        val zero = I8x16.ZERO
        run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<M8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M8x16>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = zero; val simdArg1 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }.v128()
    }

/**
 * Negates a 128-bit vectors interpreted as sixteen 8-bit signed integers
 */
public fun i8x16Neg(a: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = Simd<Byte>(List(16) { (-1).toByte() }); simdArg0 }.v128()

/**
 * Count the number of bits set to one within each lane.
 */
public fun i8x16Popcnt(v: V128): V128 = run { val simdArg0 = v.asI8x16(); simdArg0 }.v128()

/**
 * Returns true if all lanes are non-zero, false otherwise.
 */
public fun i8x16AllTrue(a: V128): Boolean = llvmI8x16AllTrue(a.asI8x16()) != 0

/**
 * Extracts the high bit for each lane in `a` and produce a scalar mask with
 * all bits concatenated.
 */
public fun i8x16Bitmask(a: V128): UShort = llvmBitmaskI8x16(a.asI8x16()).toUShort()

/**
 * Converts two input vectors into a smaller lane vector by narrowing each
 * lane.
 *
 * Signed saturation to 0x7f or 0x80 is used and the input lanes are always
 * interpreted as signed integers.
 */
public fun i8x16NarrowI16x8(a: V128, b: V128): V128 =
    run {
        val v: I16x16 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }
        val max = run { val simdArg0 = Short.from(Byte.MAX_VALUE); V128() }
        val min = run { val simdArg0 = Short.from(Byte.MIN_VALUE); V128() }
        val v1 = run { val simdArg0 = run { val simdArg0 = v; val simdArg1 = max; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; val simdArg1 = max; val simdArg2 = v; simdArg1 }
        val v2 = run { val simdArg0 = run { val simdArg0 = v1; val simdArg1 = min; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; val simdArg1 = min; val simdArg2 = v1; simdArg1 }
        val v3: I8x16 = run { val simdArg0 = v2; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }
        v3.v128()
    }

/**
 * Converts two input vectors into a smaller lane vector by narrowing each
 * lane.
 *
 * Signed saturation to 0x00 or 0xff is used and the input lanes are always
 * interpreted as signed integers.
 */
public fun u8x16NarrowI16x8(a: V128, b: V128): V128 =
    run {
        val v: I16x16 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }
        val max = run { val simdArg0 = Short.from(UByte.MAX_VALUE); V128() }
        val min = run { val simdArg0 = Short.from(UByte.MIN_VALUE); V128() }
        val v1 = run { val simdArg0 = run { val simdArg0 = v; val simdArg1 = max; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; val simdArg1 = max; val simdArg2 = v; simdArg1 }
        val v2 = run { val simdArg0 = run { val simdArg0 = v1; val simdArg1 = min; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; val simdArg1 = min; val simdArg2 = v1; simdArg1 }
        val v3: U8x16 = run { val simdArg0 = v2; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }
        v3.v128()
    }

/**
 * Shifts each lane to the left by the specified number of bits.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i8x16Shl(a: V128, amt: UInt): V128 {
    // SAFETY: the safety of this intrinsic relies on the fact that the
    // shift amount for each lane is less than the number of bits in the input
    // lane. In this case the input has 8-bit lanes but the shift amount above
    // is `UInt`, so a mask is required to discard all the upper bits of `amt` to
    // ensure that the safety condition is met.
    //
    // Note that this is distinct from the behavior of the native WebAssembly
    // instruction here where WebAssembly defines this instruction as performing
    // a mask as well. This is nonetheless required since this must have defined
    // semantics in LLVM, not just WebAssembly.
    //
    // Finally note that this mask operation is not actually emitted into the
    // final binary itself. LLVM understands that the wasm operation implicitly
    // masks, so it knows this mask operation is redundant.
    //
    // Basically the extra mask here is required as a bridge from the documented
    // semantics through LLVM back out to WebAssembly. Both ends have the
    // documented semantics, and the mask is required by LLVM in the middle.
    return run { val simdArg0 = a.asI8x16(); val simdArg1 = Simd<Byte>(List(16) { (amt and 0x7u).toByte() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, sign
 * extending.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i8x16Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI8x16(); val simdArg1 = Simd<Byte>(List(16) { (amt and 0x7u).toByte() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, shifting in
 * zeros.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun u8x16Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asU8x16(); val simdArg1 = Simd<UByte>(List(16) { (amt and 0x7u).toUByte() }); simdArg0 }.v128()
}

/**
 * Adds two 128-bit vectors as if they were two packed sixteen 8-bit integers.
 */
public fun i8x16Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); simdArg0 }.v128()

/**
 * Adds two 128-bit vectors as if they were two packed sixteen 8-bit signed
 * integers, saturating on overflow to `Byte.MAX`.
 */
public fun i8x16AddSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); simdArg0 }.v128()

/**
 * Adds two 128-bit vectors as if they were two packed sixteen 8-bit unsigned
 * integers, saturating on overflow to `UByte.MAX`.
 */
public fun u8x16AddSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed sixteen 8-bit integers.
 */
public fun i8x16Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed sixteen 8-bit
 * signed integers, saturating on overflow to `Byte.MIN`.
 */
public fun i8x16SubSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed sixteen 8-bit
 * unsigned integers, saturating on overflow to 0.
 */
public fun u8x16SubSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); simdArg0 }.v128()

/**
 * Compares lane-wise signed integers, and returns the minimum of
 * each pair.
 */
public fun i8x16Min(a: V128, b: V128): V128 {
    val a1 = a.asI8x16()
    val b1 = b.asI8x16()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the minimum of
 * each pair.
 */
public fun u8x16Min(a: V128, b: V128): V128 {
    val a1 = a.asU8x16()
    val b1 = b.asU8x16()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise signed integers, and returns the maximum of
 * each pair.
 */
public fun i8x16Max(a: V128, b: V128): V128 {
    val a1 = a.asI8x16()
    val b1 = b.asI8x16()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the maximum of
 * each pair.
 */
public fun u8x16Max(a: V128, b: V128): V128 {
    val a1 = a.asU8x16()
    val b1 = b.asU8x16()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Lane-wise rounding average.
 */
public fun u8x16Avgr(a: V128, b: V128): V128 = llvmAvgrUI8x16(a.asI8x16(), b.asI8x16()).v128()

/**
 * Integer extended pairwise addition producing extended results
 * (twice wider results than the inputs).
 */
public fun i16x8ExtaddPairwiseI8x16(a: V128): V128 = llvmI16x8ExtaddPairwiseI8x16S(a.asI8x16()).v128()

/**
 * Integer extended pairwise addition producing extended results
 * (twice wider results than the inputs).
 */
public fun i16x8ExtaddPairwiseU8x16(a: V128): V128 = llvmI16x8ExtaddPairwiseI8x16U(a.asI8x16()).v128()

/**
 * Lane-wise wrapping absolute value.
 */
public fun i16x8Abs(a: V128): V128 {
    val a1 = a.asI16x8()
    val zero = I16x8.ZERO
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<M16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M16x8>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = zero; val simdArg1 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }.v128()
}

/**
 * Negates a 128-bit vectors interpreted as eight 16-bit signed integers
 */
public fun i16x8Neg(a: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = Simd<Short>(List(8) { (-1).toShort() }); simdArg0 }.v128()

/**
 * Lane-wise saturating rounding multiplication in Q15 format.
 */
public fun i16x8Q15mulrSat(a: V128, b: V128): V128 = llvmQ15mulr(a.asI16x8(), b.asI16x8()).v128()

/**
 * Returns true if all lanes are non-zero, false otherwise.
 */
public fun i16x8AllTrue(a: V128): Boolean = llvmI16x8AllTrue(a.asI16x8()) != 0

/**
 * Extracts the high bit for each lane in `a` and produce a scalar mask with
 * all bits concatenated.
 */
public fun i16x8Bitmask(a: V128): UByte = llvmBitmaskI16x8(a.asI16x8()).toUByte()

/**
 * Converts two input vectors into a smaller lane vector by narrowing each
 * lane.
 *
 * Signed saturation to 0x7fff or 0x8000 is used and the input lanes are always
 * interpreted as signed integers.
 */
public fun i16x8NarrowI32x4(a: V128, b: V128): V128 =
    run {
        val v: I32x8 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }
        val max = run { val simdArg0 = Int.from(Short.MAX_VALUE); V128() }
        val min = run { val simdArg0 = Int.from(Short.MIN_VALUE); V128() }
        val v1 = run { val simdArg0 = run { val simdArg0 = v; val simdArg1 = max; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }; val simdArg1 = max; val simdArg2 = v; simdArg1 }
        val v2 = run { val simdArg0 = run { val simdArg0 = v1; val simdArg1 = min; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }; val simdArg1 = min; val simdArg2 = v1; simdArg1 }
        val v3: I16x8 = run { val simdArg0 = v2; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        v3.v128()
    }

/**
 * Converts two input vectors into a smaller lane vector by narrowing each
 * lane.
 *
 * Signed saturation to 0x0000 or 0xffff is used and the input lanes are always
 * interpreted as signed integers.
 */
public fun u16x8NarrowI32x4(a: V128, b: V128): V128 =
    run {
        val v: I32x8 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }
        val max = run { val simdArg0 = Int.from(UShort.MAX_VALUE); V128() }
        val min = run { val simdArg0 = Int.from(UShort.MIN_VALUE); V128() }
        val v1 = run { val simdArg0 = run { val simdArg0 = v; val simdArg1 = max; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }; val simdArg1 = max; val simdArg2 = v; simdArg1 }
        val v2 = run { val simdArg0 = run { val simdArg0 = v1; val simdArg1 = min; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }; val simdArg1 = min; val simdArg2 = v1; simdArg1 }
        val v3: U16x8 = run { val simdArg0 = v2; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
        v3.v128()
    }

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i16x8ExtendLowI8x16(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = a.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i16x8ExtendHighI8x16(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = a.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }.v128()

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i16x8ExtendLowU8x16(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = a.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i16x8ExtendHighU8x16(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = a.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }.v128()

/**
 * Shifts each lane to the left by the specified number of bits.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i16x8Shl(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI16x8(); val simdArg1 = Simd<Short>(List(8) { (amt and 0xfu).toShort() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, sign
 * extending.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i16x8Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI16x8(); val simdArg1 = Simd<Short>(List(8) { (amt and 0xfu).toShort() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, shifting in
 * zeros.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun u16x8Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asU16x8(); val simdArg1 = Simd<UShort>(List(8) { (amt and 0xfu).toUShort() }); simdArg0 }.v128()
}

/**
 * Adds two 128-bit vectors as if they were two packed eight 16-bit integers.
 */
public fun i16x8Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); simdArg0 }.v128()

/**
 * Adds two 128-bit vectors as if they were two packed eight 16-bit signed
 * integers, saturating on overflow to `Short.MAX`.
 */
public fun i16x8AddSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); simdArg0 }.v128()

/**
 * Adds two 128-bit vectors as if they were two packed eight 16-bit unsigned
 * integers, saturating on overflow to `UShort.MAX`.
 */
public fun u16x8AddSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed eight 16-bit integers.
 */
public fun i16x8Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed eight 16-bit
 * signed integers, saturating on overflow to `Short.MIN`.
 */
public fun i16x8SubSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed eight 16-bit
 * unsigned integers, saturating on overflow to 0.
 */
public fun u16x8SubSat(a: V128, b: V128): V128 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); simdArg0 }.v128()

/**
 * Multiplies two 128-bit vectors as if they were two packed eight 16-bit
 * signed integers.
 */
public fun i16x8Mul(a: V128, b: V128): V128 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); simdArg0 }.v128()

/**
 * Compares lane-wise signed integers, and returns the minimum of
 * each pair.
 */
public fun i16x8Min(a: V128, b: V128): V128 {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the minimum of
 * each pair.
 */
public fun u16x8Min(a: V128, b: V128): V128 {
    val a1 = a.asU16x8()
    val b1 = b.asU16x8()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise signed integers, and returns the maximum of
 * each pair.
 */
public fun i16x8Max(a: V128, b: V128): V128 {
    val a1 = a.asI16x8()
    val b1 = b.asI16x8()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the maximum of
 * each pair.
 */
public fun u16x8Max(a: V128, b: V128): V128 {
    val a1 = a.asU16x8()
    val b1 = b.asU16x8()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Lane-wise rounding average.
 */
public fun u16x8Avgr(a: V128, b: V128): V128 = llvmAvgrUI16x8(a.asI16x8(), b.asI16x8()).v128()

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i16x8Mul(i16x8ExtendLowI8x16(a), i16x8ExtendLowI8x16(b))`
 */
public fun i16x8ExtmulLowI8x16(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = a.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI8x16(); val simdArg1 = b.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i16x8Mul(i16x8ExtendHighI8x16(a), i16x8ExtendHighI8x16(b))`
 */
public fun i16x8ExtmulHighI8x16(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = a.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI8x16(); val simdArg1 = b.asI8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i16x8Mul(i16x8ExtendLowU8x16(a), i16x8ExtendLowU8x16(b))`
 */
public fun i16x8ExtmulLowU8x16(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = a.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU8x16(); val simdArg1 = b.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i16x8Mul(i16x8ExtendHighU8x16(a), i16x8ExtendHighU8x16(b))`
 */
public fun i16x8ExtmulHighU8x16(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = a.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU8x16(); val simdArg1 = b.asU8x16(); val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Integer extended pairwise addition producing extended results
 * (twice wider results than the inputs).
 */
public fun i32x4ExtaddPairwiseI16x8(a: V128): V128 = llvmI32x4ExtaddPairwiseI16x8S(a.asI16x8()).v128()

/**
 * Integer extended pairwise addition producing extended results
 * (twice wider results than the inputs).
 */
public fun i32x4ExtaddPairwiseU16x8(a: V128): V128 = llvmI32x4ExtaddPairwiseI16x8U(a.asI16x8()).v128()

/**
 * Lane-wise wrapping absolute value.
 */
public fun i32x4Abs(a: V128): V128 {
    val a1 = a.asI32x4()
    val zero = I32x4.ZERO
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<M32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = zero; val simdArg1 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }.v128()
}

/**
 * Negates a 128-bit vectors interpreted as four 32-bit signed integers
 */
public fun i32x4Neg(a: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = Simd<Int>(List(4) { -1 }); simdArg0 }.v128()

/**
 * Returns true if all lanes are non-zero, false otherwise.
 */
public fun i32x4AllTrue(a: V128): Boolean = llvmI32x4AllTrue(a.asI32x4()) != 0

/**
 * Extracts the high bit for each lane in `a` and produce a scalar mask with
 * all bits concatenated.
 */
public fun i32x4Bitmask(a: V128): UByte = llvmBitmaskI32x4(a.asI32x4()).toUByte()

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i32x4ExtendLowI16x8(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = a.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i32x4ExtendHighI16x8(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = a.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i32x4ExtendLowU16x8(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = a.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i32x4ExtendHighU16x8(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = a.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }.v128()

/**
 * Shifts each lane to the left by the specified number of bits.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i32x4Shl(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI32x4(); val simdArg1 = Simd<Int>(List(4) { (amt and 0x1fu).toInt() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, sign
 * extending.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i32x4Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI32x4(); val simdArg1 = Simd<Int>(List(4) { (amt and 0x1fu).toInt() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, shifting in
 * zeros.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun u32x4Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asU32x4(); val simdArg1 = Simd<UInt>(List(4) { amt and 0x1fu }); simdArg0 }.v128()
}

/**
 * Adds two 128-bit vectors as if they were two packed four 32-bit integers.
 */
public fun i32x4Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed four 32-bit integers.
 */
public fun i32x4Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); simdArg0 }.v128()

/**
 * Multiplies two 128-bit vectors as if they were two packed four 32-bit
 * signed integers.
 */
public fun i32x4Mul(a: V128, b: V128): V128 = run { val simdArg0 = a.asI32x4(); val simdArg1 = b.asI32x4(); simdArg0 }.v128()

/**
 * Compares lane-wise signed integers, and returns the minimum of
 * each pair.
 */
public fun i32x4Min(a: V128, b: V128): V128 {
    val a1 = a.asI32x4()
    val b1 = b.asI32x4()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the minimum of
 * each pair.
 */
public fun u32x4Min(a: V128, b: V128): V128 {
    val a1 = a.asU32x4()
    val b1 = b.asU32x4()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise signed integers, and returns the maximum of
 * each pair.
 */
public fun i32x4Max(a: V128, b: V128): V128 {
    val a1 = a.asI32x4()
    val b1 = b.asI32x4()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Compares lane-wise unsigned integers, and returns the maximum of
 * each pair.
 */
public fun u32x4Max(a: V128, b: V128): V128 {
    val a1 = a.asU32x4()
    val b1 = b.asU32x4()
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }; val simdArg1 = a1; val simdArg2 = b1; simdArg1 }.v128()
}

/**
 * Lane-wise multiply signed 16-bit integers in the two input vectors and add
 * adjacent pairs of the full 32-bit results.
 */
public fun i32x4DotI16x8(a: V128, b: V128): V128 = llvmI32x4DotI16x8S(a.asI16x8(), b.asI16x8()).v128()

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i32x4Mul(i32x4ExtendLowI16x8S(a), i32x4ExtendLowI16x8S(b))`
 */
public fun i32x4ExtmulLowI16x8(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = a.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI16x8(); val simdArg1 = b.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i32x4Mul(i32x4ExtendHighI16x8S(a), i32x4ExtendHighI16x8S(b))`
 */
public fun i32x4ExtmulHighI16x8(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = a.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI16x8(); val simdArg1 = b.asI16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i32x4Mul(i32x4ExtendLowU16x8(a), i32x4ExtendLowU16x8(b))`
 */
public fun i32x4ExtmulLowU16x8(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = a.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU16x8(); val simdArg1 = b.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i32x4Mul(i32x4ExtendHighU16x8(a), i32x4ExtendHighU16x8(b))`
 */
public fun i32x4ExtmulHighU16x8(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = a.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU16x8(); val simdArg1 = b.asU16x8(); val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise wrapping absolute value.
 */
public fun i64x2Abs(a: V128): V128 {
    val a1 = a.asI64x2()
    val zero = I64x2.ZERO
    return run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = zero; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<M64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); res } }; val simdArg1 = run { val simdArg0 = zero; val simdArg1 = a1; simdArg0 }; val simdArg2 = a1; simdArg1 }.v128()
}

/**
 * Negates a 128-bit vectors interpreted as two 64-bit signed integers
 */
public fun i64x2Neg(a: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = Simd<Long>(List(2) { -1L }); simdArg0 }.v128()

/**
 * Returns true if all lanes are non-zero, false otherwise.
 */
public fun i64x2AllTrue(a: V128): Boolean = llvmI64x2AllTrue(a.asI64x2()) != 0

/**
 * Extracts the high bit for each lane in `a` and produce a scalar mask with
 * all bits concatenated.
 */
public fun i64x2Bitmask(a: V128): UByte = llvmBitmaskI64x2(a.asI64x2()).toUByte()

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i64x2ExtendLowI32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI32x4(); val simdArg1 = a.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, sign extended.
 */
public fun i64x2ExtendHighI32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI32x4(); val simdArg1 = a.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Converts low half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i64x2ExtendLowU32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU32x4(); val simdArg1 = a.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Converts high half of the smaller lane vector to a larger lane
 * vector, zero extended.
 */
public fun i64x2ExtendHighU32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU32x4(); val simdArg1 = a.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }.v128()

/**
 * Shifts each lane to the left by the specified number of bits.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i64x2Shl(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI64x2(); val simdArg1 = Simd<Long>(List(2) { (amt and 0x3fu).toLong() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, sign
 * extending.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun i64x2Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asI64x2(); val simdArg1 = Simd<Long>(List(2) { (amt and 0x3fu).toLong() }); simdArg0 }.v128()
}

/**
 * Shifts each lane to the right by the specified number of bits, shifting in
 * zeros.
 *
 * Only the low bits of the shift amount are used if the shift amount is
 * greater than the lane width.
 */
public fun u64x2Shr(a: V128, amt: UInt): V128 {
    // SAFETY: see i8x16Shl for more documentation why this is unchecked,
    // essentially the shift amount must be valid hence the mask.
    return run { val simdArg0 = a.asU64x2(); val simdArg1 = Simd<ULong>(List(2) { (amt and 0x3fu).toULong() }); simdArg0 }.v128()
}

/**
 * Adds two 128-bit vectors as if they were two packed two 64-bit integers.
 */
public fun i64x2Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Subtracts two 128-bit vectors as if they were two packed two 64-bit integers.
 */
public fun i64x2Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Multiplies two 128-bit vectors as if they were two packed two 64-bit integers.
 */
public fun i64x2Mul(a: V128, b: V128): V128 = run { val simdArg0 = a.asI64x2(); val simdArg1 = b.asI64x2(); simdArg0 }.v128()

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i64x2Mul(i64x2ExtendLowI32x4S(a), i64x2ExtendLowI32x4S(b))`
 */
public fun i64x2ExtmulLowI32x4(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI32x4(); val simdArg1 = a.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI32x4(); val simdArg1 = b.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i64x2Mul(i64x2ExtendHighI32x4S(a), i64x2ExtendHighI32x4S(b))`
 */
public fun i64x2ExtmulHighI32x4(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asI32x4(); val simdArg1 = a.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asI32x4(); val simdArg1 = b.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i64x2Mul(i64x2ExtendLowI32x4U(a), i64x2ExtendLowI32x4U(b))`
 */
public fun i64x2ExtmulLowU32x4(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU32x4(); val simdArg1 = a.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU32x4(); val simdArg1 = b.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise integer extended multiplication producing twice wider result than
 * the inputs.
 *
 * Equivalent of `i64x2Mul(i64x2ExtendHighI32x4U(a), i64x2ExtendHighI32x4U(b))`
 */
public fun i64x2ExtmulHighU32x4(a: V128, b: V128): V128 =
    run {
        val lhs = run { val simdArg0 = run { val simdArg0 = a.asU32x4(); val simdArg1 = a.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }
        val rhs = run { val simdArg0 = run { val simdArg0 = b.asU32x4(); val simdArg1 = b.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }
        run { val simdArg0 = lhs; val simdArg1 = rhs; simdArg0 }.v128()
    }

/**
 * Lane-wise rounding to the nearest integral value not smaller than the input.
 */
public fun f32x4Ceil(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value not greater than the input.
 */
public fun f32x4Floor(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value with the magnitude not
 * larger than the input.
 */
public fun f32x4Trunc(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value; if two values are equally
 * near, rounds to the even one.
 */
public fun f32x4Nearest(a: V128): V128 = llvmF32x4Nearest(a.asF32x4()).v128()

/**
 * Calculates the absolute value of each lane of a 128-bit vector interpreted
 * as four 32-bit floating point numbers.
 */
public fun f32x4Abs(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Negates each lane of a 128-bit vector interpreted as four 32-bit floating
 * point numbers.
 */
public fun f32x4Neg(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Calculates the square root of each lane of a 128-bit vector interpreted as
 * four 32-bit floating point numbers.
 */
public fun f32x4Sqrt(a: V128): V128 = run { val simdArg0 = a.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise addition of two 128-bit vectors interpreted as four 32-bit
 * floating point numbers.
 */
public fun f32x4Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise subtraction of two 128-bit vectors interpreted as four 32-bit
 * floating point numbers.
 */
public fun f32x4Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise multiplication of two 128-bit vectors interpreted as four 32-bit
 * floating point numbers.
 */
public fun f32x4Mul(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); simdArg0 }.v128()

/**
 * Lane-wise division of two 128-bit vectors interpreted as four 32-bit
 * floating point numbers.
 */
public fun f32x4Div(a: V128, b: V128): V128 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); simdArg0 }.v128()

/**
 * Calculates the lane-wise minimum of two 128-bit vectors interpreted
 * as four 32-bit floating point numbers.
 */
public fun f32x4Min(a: V128, b: V128): V128 = llvmF32x4Min(a.asF32x4(), b.asF32x4()).v128()

/**
 * Calculates the lane-wise minimum of two 128-bit vectors interpreted
 * as four 32-bit floating point numbers.
 */
public fun f32x4Max(a: V128, b: V128): V128 = llvmF32x4Max(a.asF32x4(), b.asF32x4()).v128()

/**
 * Lane-wise minimum value, defined as `b<a b: a`
 */
public fun f32x4Pmin(a: V128, b: V128): V128 = run { val simdArg0 = run { val simdArg0 = b.asF32x4(); val simdArg1 = a.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<M32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); res } }; val simdArg1 = b.asF32x4(); val simdArg2 = a.asF32x4(); simdArg1 }.v128()

/**
 * Lane-wise maximum value, defined as `a<b b: a`
 */
public fun f32x4Pmax(a: V128, b: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asF32x4(); val simdArg1 = b.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<M32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M32x4>()).alignBytes)); res } }; val simdArg1 = b.asF32x4(); val simdArg2 = a.asF32x4(); simdArg1 }.v128()

/**
 * Lane-wise rounding to the nearest integral value not smaller than the input.
 */
public fun f64x2Ceil(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value not greater than the input.
 */
public fun f64x2Floor(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value with the magnitude not
 * larger than the input.
 */
public fun f64x2Trunc(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise rounding to the nearest integral value; if two values are equally
 * near, rounds to the even one.
 */
public fun f64x2Nearest(a: V128): V128 = llvmF64x2Nearest(a.asF64x2()).v128()

/**
 * Calculates the absolute value of each lane of a 128-bit vector interpreted
 * as two 64-bit floating point numbers.
 */
public fun f64x2Abs(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Negates each lane of a 128-bit vector interpreted as two 64-bit floating
 * point numbers.
 */
public fun f64x2Neg(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Calculates the square root of each lane of a 128-bit vector interpreted as
 * two 64-bit floating point numbers.
 */
public fun f64x2Sqrt(a: V128): V128 = run { val simdArg0 = a.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise add of two 128-bit vectors interpreted as two 64-bit
 * floating point numbers.
 */
public fun f64x2Add(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise subtract of two 128-bit vectors interpreted as two 64-bit
 * floating point numbers.
 */
public fun f64x2Sub(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise multiply of two 128-bit vectors interpreted as two 64-bit
 * floating point numbers.
 */
public fun f64x2Mul(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); simdArg0 }.v128()

/**
 * Lane-wise divide of two 128-bit vectors interpreted as two 64-bit
 * floating point numbers.
 */
public fun f64x2Div(a: V128, b: V128): V128 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); simdArg0 }.v128()

/**
 * Calculates the lane-wise minimum of two 128-bit vectors interpreted
 * as two 64-bit floating point numbers.
 */
public fun f64x2Min(a: V128, b: V128): V128 = llvmF64x2Min(a.asF64x2(), b.asF64x2()).v128()

/**
 * Calculates the lane-wise maximum of two 128-bit vectors interpreted
 * as two 64-bit floating point numbers.
 */
public fun f64x2Max(a: V128, b: V128): V128 = llvmF64x2Max(a.asF64x2(), b.asF64x2()).v128()

/**
 * Lane-wise minimum value, defined as `b<a b: a`
 */
public fun f64x2Pmin(a: V128, b: V128): V128 = run { val simdArg0 = run { val simdArg0 = b.asF64x2(); val simdArg1 = a.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<M64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); res } }; val simdArg1 = b.asF64x2(); val simdArg2 = a.asF64x2(); simdArg1 }.v128()

/**
 * Lane-wise maximum value, defined as `a<b b: a`
 */
public fun f64x2Pmax(a: V128, b: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asF64x2(); val simdArg1 = b.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<M64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<M64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<M64x2>()).alignBytes)); res } }; val simdArg1 = b.asF64x2(); val simdArg2 = a.asF64x2(); simdArg1 }.v128()

/**
 * Converts a 128-bit vector interpreted as four 32-bit floating point numbers
 * into a 128-bit vector of four 32-bit signed integers.
 *
 * NaN is converted to 0 and if it's out of bounds it becomes the nearest
 * representable intger.
 */
public fun i32x4TruncSatF32x4(a: V128): V128 = run { val simdArg0 = a.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }.v128()

/**
 * Converts a 128-bit vector interpreted as four 32-bit floating point numbers
 * into a 128-bit vector of four 32-bit unsigned integers.
 *
 * NaN is converted to 0 and if it's out of bounds it becomes the nearest
 * representable intger.
 */
public fun u32x4TruncSatF32x4(a: V128): V128 = run { val simdArg0 = a.asF32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }.v128()

/**
 * Converts a 128-bit vector interpreted as four 32-bit signed integers into a
 * 128-bit vector of four 32-bit floating point numbers.
 */
public fun f32x4ConvertI32x4(a: V128): V128 = run { val simdArg0 = a.asI32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); res } }.v128()

/**
 * Converts a 128-bit vector interpreted as four 32-bit unsigned integers into a
 * 128-bit vector of four 32-bit floating point numbers.
 */
public fun f32x4ConvertU32x4(a: V128): V128 = run { val simdArg0 = a.asU32x4(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); res } }.v128()

/**
 * Saturating conversion of the two double-precision floating point lanes to
 * two lower integer lanes using the IEEE `convertToIntegerTowardZero`
 * function.
 *
 * The two higher lanes of the result are initialized to zero. If any input
 * lane is a NaN, the resulting lane is 0. If the rounded integer value of a
 * lane is outside the range of the destination type, the result is saturated
 * to the nearest representable integer value.
 */
public fun i32x4TruncSatF64x2Zero(a: V128): V128 {
    val ret: I32x4 = run { val simdArg0 = run { val simdArg0 = a.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; val simdArg1 = I32x2.ZERO; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }
    return ret.v128()
}

/**
 * Saturating conversion of the two double-precision floating point lanes to
 * two lower integer lanes using the IEEE `convertToIntegerTowardZero`
 * function.
 *
 * The two higher lanes of the result are initialized to zero. If any input
 * lane is a NaN, the resulting lane is 0. If the rounded integer value of a
 * lane is outside the range of the destination type, the result is saturated
 * to the nearest representable integer value.
 */
public fun u32x4TruncSatF64x2Zero(a: V128): V128 {
    val ret: U32x4 = run { val simdArg0 = run { val simdArg0 = a.asF64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; val simdArg1 = U32x2.ZERO; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }
    return ret.v128()
}

/**
 * Lane-wise conversion from integer to floating point.
 */
public fun f64x2ConvertLowI32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asI32x4(); val simdArg1 = a.asI32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); res } }.v128()

/**
 * Lane-wise conversion from integer to floating point.
 */
public fun f64x2ConvertLowU32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asU32x4(); val simdArg1 = a.asU32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); res } }.v128()

/**
 * Conversion of the two double-precision floating point lanes to two lower
 * single-precision lanes of the result. The two higher lanes of the result are
 * initialized to zero. If the conversion result is not representable as a
 * single-precision floating point number, it is rounded to the nearest-even
 * representable number.
 */
public fun f32x4DemoteF64x2Zero(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asF64x2(); val simdArg1 = F64x2.ZERO; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x4>()).alignBytes)); val res = (UnresolvedTypeLayout<F64x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x4>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x4>()).alignBytes)); res } }.v128()

/**
 * Conversion of the two lower single-precision floating point lanes to the two
 * double-precision lanes of the result.
 */
public fun f64x2PromoteLowF32x4(a: V128): V128 = run { val simdArg0 = run { val simdArg0 = a.asF32x4(); val simdArg1 = a.asF32x4(); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F32x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<F64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<F64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<F64x2>()).alignBytes)); res } }.v128()
