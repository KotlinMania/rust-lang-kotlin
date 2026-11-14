// port-lint: tests x86/adx.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public fun testAddcarryxU32() {
    val a = UInt.MAX_VALUE
    val localStorageL92C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var out by localStorageL92C8
    val r = addcarryxU32(0u, a, 1u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r == 1u.toUByte())
    check(out == 0)
    val r1 = addcarryxU32(0u, a, 0u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r1 == 0u.toUByte())
    check(out == a)
    val r2 = addcarryxU32(1u, a, 1u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r2 == 1u.toUByte())
    check(out == 1)
    val r3 = addcarryxU32(1u, a, 0u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r3 == 1u.toUByte())
    check(out == 0)
    val r4 = addcarryxU32(0u, 3u, 4u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r4 == 0u.toUByte())
    check(out == 7)
    val r5 = addcarryxU32(1u, 3u, 4u, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r5 == 0u.toUByte())
    check(out == 8)
}

public fun testAddcarryxU322() {
    val localStorageL121C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var out by localStorageL121C8
    addcarryxU32(1u, 2u, 3u, RustMutableReference(localStorageL121C8.asMutPtr(), localStorageL121C8.layout))
    check(6 == out)
}

public class AdxInlineTests {
    @kotlin.test.Test
    public fun testAddcarryU32() {
        val a = UInt.MAX_VALUE
        val localStorageL62C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var out by localStorageL62C8
        val r = addcarryU32(0u, a, 1u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r == 1u.toUByte())
        check(out == 0)
        val r1 = addcarryU32(0u, a, 0u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r1 == 0u.toUByte())
        check(out == a)
        val r2 = addcarryU32(1u, a, 1u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r2 == 1u.toUByte())
        check(out == 1)
        val r3 = addcarryU32(1u, a, 0u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r3 == 1u.toUByte())
        check(out == 0)
        val r4 = addcarryU32(0u, 3u, 4u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r4 == 0u.toUByte())
        check(out == 7)
        val r5 = addcarryU32(1u, 3u, 4u, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r5 == 0u.toUByte())
        check(out == 8)
    }

    @kotlin.test.Test
    public fun testSubborrowU32() {
        val a = UInt.MAX_VALUE
        val localStorageL129C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var out by localStorageL129C8
        val r = subborrowU32(0u, 0u, 1u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r == 1u.toUByte())
        check(out == a)
        val r1 = subborrowU32(0u, 0u, 0u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r1 == 0u.toUByte())
        check(out == 0)
        val r2 = subborrowU32(1u, 0u, 1u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r2 == 1u.toUByte())
        check(out == a - 1u)
        val r3 = subborrowU32(1u, 0u, 0u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r3 == 1u.toUByte())
        check(out == a)
        val r4 = subborrowU32(0u, 7u, 3u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r4 == 0u.toUByte())
        check(out == 4)
        val r5 = subborrowU32(1u, 7u, 3u, RustMutableReference(localStorageL129C8.asMutPtr(), localStorageL129C8.layout))
        check(r5 == 0u.toUByte())
        check(out == 3)
    }
}
