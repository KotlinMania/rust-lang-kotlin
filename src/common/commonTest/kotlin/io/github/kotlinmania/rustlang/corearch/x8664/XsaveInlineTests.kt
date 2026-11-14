// port-lint: tests x86_64/xsave.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// `x8664`s `xsave` and `xsaveopt` target feature intrinsics

// Register saving/restoring is not supported in Miri
public fun testXsave64() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsave64(a.ptr(), m)
        xrstor64(a.ptr(), m)
        xsave64(b.ptr(), m)
    }
}

// Register saving/restoring is not supported in Miri
public fun testXsaveopt64() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsaveopt64(a.ptr(), m)
        xrstor64(a.ptr(), m)
        xsaveopt64(b.ptr(), m)
    }
}

// Register saving/restoring is not supported in Miri
public fun testXsavec64() {
    val m = 0xFFFFFFFFFFFFFFFFuL
    // < all registers
    val a = XsaveArea.new()
    val b = XsaveArea.new()
    run {
        xsavec64(a.ptr(), m)
        xrstor64(a.ptr(), m)
        xsavec64(b.ptr(), m)
    }
}
