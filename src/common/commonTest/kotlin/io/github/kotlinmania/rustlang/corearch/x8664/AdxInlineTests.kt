// port-lint: tests x86_64/adx.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public fun testAddcarryxU64() {
    val a = ULong.MAX_VALUE
    val localStorageL92C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var out by localStorageL92C8
    val r = addcarryxU64(0u.toUByte(), a, 1uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r == 1u.toUByte())
    check(out == 0)
    val r1 = addcarryxU64(0u.toUByte(), a, 0uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r1 == 0u.toUByte())
    check(out == a)
    val r2 = addcarryxU64(1u.toUByte(), a, 1uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r2 == 1u.toUByte())
    check(out == 1)
    val r3 = addcarryxU64(1u.toUByte(), a, 0uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r3 == 1u.toUByte())
    check(out == 0)
    val r4 = addcarryxU64(0u.toUByte(), 3uL, 4uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r4 == 0u.toUByte())
    check(out == 7)
    val r5 = addcarryxU64(1u.toUByte(), 3uL, 4uL, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout))
    check(r5 == 0u.toUByte())
    check(out == 8)
}

public class AdxInlineTests {
    @kotlin.test.Test
    public fun testAddcarryU64() {
        val a = ULong.MAX_VALUE
        val localStorageL62C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var out by localStorageL62C8
        val r = addcarryU64(0u.toUByte(), a, 1uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r == 1u.toUByte())
        check(out == 0)
        val r1 = addcarryU64(0u.toUByte(), a, 0uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r1 == 0u.toUByte())
        check(out == a)
        val r2 = addcarryU64(1u.toUByte(), a, 1uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r2 == 1u.toUByte())
        check(out == 1)
        val r3 = addcarryU64(1u.toUByte(), a, 0uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r3 == 1u.toUByte())
        check(out == 0)
        val r4 = addcarryU64(0u.toUByte(), 3uL, 4uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r4 == 0u.toUByte())
        check(out == 7)
        val r5 = addcarryU64(1u.toUByte(), 3uL, 4uL, RustMutableReference(localStorageL62C8.asMutPtr(), localStorageL62C8.layout))
        check(r5 == 0u.toUByte())
        check(out == 8)
    }

    @kotlin.test.Test
    public fun testSubborrowU64() {
        val a = ULong.MAX_VALUE
        val localStorageL122C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var out by localStorageL122C8
        val r = subborrowU64(0u.toUByte(), 0uL, 1uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r == 1u.toUByte())
        check(out == a)
        val r1 = subborrowU64(0u.toUByte(), 0uL, 0uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r1 == 0u.toUByte())
        check(out == 0)
        val r2 = subborrowU64(1u.toUByte(), 0uL, 1uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r2 == 1u.toUByte())
        check(out == a - 1uL)
        val r3 = subborrowU64(1u.toUByte(), 0uL, 0uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r3 == 1u.toUByte())
        check(out == a)
        val r4 = subborrowU64(0u.toUByte(), 7uL, 3uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r4 == 0u.toUByte())
        check(out == 4)
        val r5 = subborrowU64(1u.toUByte(), 7uL, 3uL, RustMutableReference(localStorageL122C8.asMutPtr(), localStorageL122C8.layout))
        check(r5 == 0u.toUByte())
        check(out == 3)
    }
}
