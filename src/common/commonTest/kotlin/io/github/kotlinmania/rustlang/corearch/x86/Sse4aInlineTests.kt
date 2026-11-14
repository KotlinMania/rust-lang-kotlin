// port-lint: tests x86/sse4a.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// `i686`s Streaming SIMD Extensions 4a (`SSE4a`)

// Normally this requires SSE2, but for tests it does not matter whether we use the instruction.
public fun mmCvtsi128Si64(a: M128i): Long = run { val _simdArg0 = a.asI64x2(); val _simdArg1 = 0L; _simdArg0 }

public fun testMmExtractSi64() {
    val b = 0x600L
    // ^^^^ bit range extracted
    val x = mmSetrEpi64x(b, 0)
    val v = 0x804L
    // ^idx: 2^3 = 8 ^length = 2^2 = 4
    val y = mmSetrEpi64x(v, 0)
    val e = mmSetrEpi64x(0x6L, 0)
    val r = mmExtractSi64(x, y)
    // The upper quadword of the destination register is undefined.
    val r1 = mmCvtsi128Si64(r)
    val e1 = mmCvtsi128Si64(e)
    check(r1 == e1)
}

public fun testMmExtractiSi64() {
    val a = mmSetrEpi64x(0x0123456789abcdef, 0)
    val r = mmExtractiSi64(a, 8, 8)
    val e = mmSetrEpi64x(0xcd, 0)
    // The upper quadword of the destination register is undefined.
    val r1 = mmCvtsi128Si64(r)
    val e1 = mmCvtsi128Si64(e)
    check(r1 == e1)
}

public fun testMmInsertSi64() {
    val i = 0x6L
    // ^^^^ bit range inserted
    val z = 0xAAAL
    // ^^^^ bit range replaced
    val e = 0x6AAL
    // ^^^^ replaced 1010 with 0110
    val x = mmSetrEpi64x(z, 0)
    val expected = mmSetrEpi64x(e, 0)
    val v = 0x804L
    // ^idx: 2^3 = 8 ^length = 2^2 = 4
    val y = mmSetrEpi64x(i, v)
    val r = mmInsertSi64(x, y)
    // The upper quadword of the destination register is undefined.
    val r1 = mmCvtsi128Si64(r)
    val expected1 = mmCvtsi128Si64(expected)
    check(r1 == expected1)
}

public fun testMmInsertiSi64() {
    val a = mmSetrEpi64x(0x0123456789abcdef, 0)
    val b = mmSetrEpi64x(0x0011223344556677, 0)
    val r = mmInsertiSi64(a, b, 8, 8)
    val e = mmSetrEpi64x(0x0123456789ab77ef, 0)
    // The upper quadword of the destination register is undefined.
    val r1 = mmCvtsi128Si64(r)
    val e1 = mmCvtsi128Si64(e)
    check(r1 == e1)
}

public open class MemoryF64 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<DoubleArray>,
) {
    public var data: DoubleArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MemoryF64> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MemoryF64(ptr, layout) }, { it.data })

    public companion object {
        public operator fun invoke(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray>): MemoryF64 = MemoryF64(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<DoubleArray>): MemoryF64 = MemoryF64(fieldStorage, fieldLayout)
    }
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamSd() {
    val mem = MemoryF64(data = doubleArrayOf(1.0, 2.0), fieldLayout = PrimitiveArrayLayouts.doubleArray((2).toInt()))
    run {
        val vals = mem.data
        val d = vals.asMutPtr()
        val x = mmSetrPd(3.0, 4.0)
        run {
            mmStreamSd(d, x)
        }
        mmSfence()
    }
    check(mem.data[0] == 3.0.toDouble())
    check(mem.data[1] == 2.0.toDouble())
}

public open class MemoryF32 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<FloatArray>,
) {
    public var data: FloatArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MemoryF32> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MemoryF32(ptr, layout) }, { it.data })

    public companion object {
        public operator fun invoke(data: FloatArray, fieldLayout: TypeLayout<FloatArray>): MemoryF32 = MemoryF32(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<FloatArray>): MemoryF32 = MemoryF32(fieldStorage, fieldLayout)
    }
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamSs() {
    val mem = MemoryF32(data = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f), fieldLayout = PrimitiveArrayLayouts.floatArray((4).toInt()))
    run {
        val vals = mem.data
        val d = vals.asMutPtr()
        val x = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
        run {
            mmStreamSs(d, x)
        }
        mmSfence()
    }
    check(mem.data[0] == 5.0f.toFloat())
    check(mem.data[1] == 2.0f.toFloat())
    check(mem.data[2] == 3.0f.toFloat())
    check(mem.data[3] == 4.0f.toFloat())
}
