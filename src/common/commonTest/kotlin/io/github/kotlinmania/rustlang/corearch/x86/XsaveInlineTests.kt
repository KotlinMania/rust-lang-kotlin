// port-lint: tests x86/xsave.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.num.divCeil
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// `i586`s `xsave` and `xsaveopt` target feature intrinsics

public open class XsaveArea(
    public var data: List<AlignedArray>,
) {
    public open fun ptr(): MutPtr = MutPtr((this.data.asMutPtr()).rawAddress)

    public companion object {
        public fun new(): XsaveArea {
            // `CPUID.(EAX = 0DH, ECX = 0): ECX` contains the size required to hold all supported xsave
            // components. `EBX` contains the size required to hold all xsave components currently
            // enabled in `XCR0`. We are using `ECX` to ensure enough space in all scenarios
            val structVal = cpuid(0x0du)
            val ecx = structVal.ecx
            return XsaveArea(data = mutableListOf(AlignedArray(UByteArray(64) { 0u.toUByte() }, fieldLayout = PrimitiveArrayLayouts.uByteArray((64).toInt())), ecx.divCeil(64).toInt()).intoBoxedSlice())
        }
    }
}

public open class AlignedArray internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout(),
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public var value: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

    public fun pointerLayout(): TypeLayout<AlignedArray> = TransparentTypeLayout(fieldLayout, { ptr, layout -> AlignedArray(ptr, layout) }, { it.value })

    public open fun clone(): AlignedArray = AlignedArray(value, fieldLayout)

    public companion object {
        public operator fun invoke(value: UByteArray, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): AlignedArray = AlignedArray(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray> = UnresolvedTypeLayout()): AlignedArray = AlignedArray(fieldStorage, fieldLayout)
    }
}

// Register saving/restoring is not supported in Miri
public fun testXsave() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsave(a.ptr(), m)
        xrstor((a.ptr()).asConstPtr(), m)
        xsave(b.ptr(), m)
    }
}

// Register saving/restoring is not supported in Miri
public fun testXgetbv() {
    val xcrN: UInt = XCR_XFEATURE_ENABLED_MASK
    val xcr: ULong = xgetbv(xcrN)
    val xcrCpy: ULong = xgetbv(xcrN)
    check(xcr == xcrCpy)
}

// Register saving/restoring is not supported in Miri
public fun testXsaveopt() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsaveopt(a.ptr(), m)
        xrstor((a.ptr()).asConstPtr(), m)
        xsaveopt(b.ptr(), m)
    }
}

// Register saving/restoring is not supported in Miri
public fun testXsavec() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsavec(a.ptr(), m)
        xrstor((a.ptr()).asConstPtr(), m)
        xsavec(b.ptr(), m)
    }
}
