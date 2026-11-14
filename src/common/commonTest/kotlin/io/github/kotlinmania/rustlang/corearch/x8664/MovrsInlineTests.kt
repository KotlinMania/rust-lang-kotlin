// port-lint: tests x86_64/movrs.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts

// Read-shared Move instructions

public fun testMovrsI8() {
    val localStorageL69C8 = AddressableLocal(42, PrimitiveLayouts.I8)
    var x by localStorageL69C8
    val y = movrsI8(x)
    check(x == y)
}

public fun testMovrsI16() {
    val localStorageL76C8 = AddressableLocal(42, PrimitiveLayouts.I16)
    var x by localStorageL76C8
    val y = movrsI16(x)
    check(x == y)
}

public fun testMovrsI32() {
    val localStorageL83C8 = AddressableLocal(42, PrimitiveLayouts.I32)
    var x by localStorageL83C8
    val y = movrsI32(x)
    check(x == y)
}

public fun testMovrsI64() {
    val localStorageL90C8 = AddressableLocal(42L, PrimitiveLayouts.I64)
    var x by localStorageL90C8
    val y = movrsI64(x)
    check(x == y)
}
