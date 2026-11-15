// port-lint: tests x86/kl.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// AES Key Locker Intrinsics
//
// The Intrinsics here correspond to those in the `keylockerintrin.h` C header.

public fun encodekey128(): UByteArray {
    val localStorageL356C8 = AddressableArrayLocal.fromIntArray(IntArray(48) { 0 })
    val handle by localStorageL356C8
    mmEncodekey128U32(0u, mmSetzeroSi128(), localStorageL356C8.elementMutPtr(0))
    return handle
}

public fun encodekey256(): UByteArray {
    val localStorageL363C8 = AddressableArrayLocal.fromIntArray(IntArray(64) { 0 })
    val handle by localStorageL363C8
    mmEncodekey256U32(0u, mmSetzeroSi128(), mmSetzeroSi128(), localStorageL363C8.elementMutPtr(0))
    return handle
}

public fun testMmEncodekey128U32() {
    encodekey128()
}

public fun testMmEncodekey256U32() {
    encodekey256()
}

public fun testMmAesenc128klU8() {
    val localStorageL387C8 = AddressableLocal(mmSetzeroSi128(), UnresolvedTypeLayout<M128i>())
    val buffer by localStorageL387C8
    val key = encodekey128()
    for (unused in 0 until 100) {
        val status = mmAesenc128klU8(RustMutableReference(localStorageL387C8.asMutPtr(), localStorageL387C8.layout), buffer, key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesdec128klU8(RustMutableReference(localStorageL387C8.asMutPtr(), localStorageL387C8.layout), buffer, key.asPtr())
        check(status1 == 0u.toUByte())
    }
    assertEqM128i(buffer, mmSetzeroSi128())
}

public fun testMmAesdec128klU8() {
    val localStorageL404C8 = AddressableLocal(mmSetzeroSi128(), UnresolvedTypeLayout<M128i>())
    val buffer by localStorageL404C8
    val key = encodekey128()
    for (unused in 0 until 100) {
        val status = mmAesdec128klU8(RustMutableReference(localStorageL404C8.asMutPtr(), localStorageL404C8.layout), buffer, key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesenc128klU8(RustMutableReference(localStorageL404C8.asMutPtr(), localStorageL404C8.layout), buffer, key.asPtr())
        check(status1 == 0u.toUByte())
    }
    assertEqM128i(buffer, mmSetzeroSi128())
}

public fun testMmAesenc256klU8() {
    val localStorageL421C8 = AddressableLocal(mmSetzeroSi128(), UnresolvedTypeLayout<M128i>())
    val buffer by localStorageL421C8
    val key = encodekey256()
    for (unused in 0 until 100) {
        val status = mmAesenc256klU8(RustMutableReference(localStorageL421C8.asMutPtr(), localStorageL421C8.layout), buffer, key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesdec256klU8(RustMutableReference(localStorageL421C8.asMutPtr(), localStorageL421C8.layout), buffer, key.asPtr())
        check(status1 == 0u.toUByte())
    }
    assertEqM128i(buffer, mmSetzeroSi128())
}

public fun testMmAesdec256klU8() {
    val localStorageL438C8 = AddressableLocal(mmSetzeroSi128(), UnresolvedTypeLayout<M128i>())
    val buffer by localStorageL438C8
    val key = encodekey256()
    for (unused in 0 until 100) {
        val status = mmAesdec256klU8(RustMutableReference(localStorageL438C8.asMutPtr(), localStorageL438C8.layout), buffer, key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesenc256klU8(RustMutableReference(localStorageL438C8.asMutPtr(), localStorageL438C8.layout), buffer, key.asPtr())
        check(status1 == 0u.toUByte())
    }
    assertEqM128i(buffer, mmSetzeroSi128())
}

public fun testMmAesencwide128klU8() {
    val buffer = MutableList(8) { mmSetzeroSi128() }
    val key = encodekey128()
    for (unused in 0 until 100) {
        val status = mmAesencwide128klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesdecwide128klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status1 == 0u.toUByte())
    }
    for (elem in buffer) {
        assertEqM128i(elem, mmSetzeroSi128())
    }
}

public fun testMmAesdecwide128klU8() {
    val buffer = MutableList(8) { mmSetzeroSi128() }
    val key = encodekey128()
    for (unused in 0 until 100) {
        val status = mmAesdecwide128klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesencwide128klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status1 == 0u.toUByte())
    }
    for (elem in buffer) {
        assertEqM128i(elem, mmSetzeroSi128())
    }
}

public fun testMmAesencwide256klU8() {
    val buffer = MutableList(8) { mmSetzeroSi128() }
    val key = encodekey256()
    for (unused in 0 until 100) {
        val status = mmAesencwide256klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesdecwide256klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status1 == 0u.toUByte())
    }
    for (elem in buffer) {
        assertEqM128i(elem, mmSetzeroSi128())
    }
}

public fun testMmAesdecwide256klU8() {
    val buffer = MutableList(8) { mmSetzeroSi128() }
    val key = encodekey256()
    for (unused in 0 until 100) {
        val status = mmAesdecwide256klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status == 0u.toUByte())
    }
    for (unused1 in 0 until 100) {
        val status1 = mmAesencwide256klU8(buffer.asMutPtr(), buffer.asPtr(), key.asPtr())
        check(status1 == 0u.toUByte())
    }
    for (elem in buffer) {
        assertEqM128i(elem, mmSetzeroSi128())
    }
}
