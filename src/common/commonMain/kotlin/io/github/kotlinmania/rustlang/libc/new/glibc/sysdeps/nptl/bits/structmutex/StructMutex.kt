// port-lint: source new/glibc/sysdeps/nptl/bits/struct_mutex.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.nptl.bits.structmutex

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.types.replaceArrayItems
import io.github.kotlinmania.rustlang.types.u8SliceCastCharSlice
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.PthreadMutexT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_MUTEX_T

// Header: `sysdeps / nptl / bits / structMutex.h`

public fun pthreadMutexInitializer(kind: CInt): PthreadMutexT {
    // We don't need the whole complicated `__pthread_mutex_s` definition, just use the
    // offset of the `__kind` field.
    val pthreadMutexHavePrev = true
    val kindOffset =
        if (pthreadMutexHavePrev) {
            4 * sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>())
        } else {
            3 * sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>())
        }
    val size = List(SIZEOF_PTHREAD_MUTEX_T) { 0 }
    val kindBytes = run { val rawBits = kind.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
    val repl = u8SliceCastCharSlice(kindBytes)
    val size1 = replaceArrayItems(size, repl, kindOffset)
    return PthreadMutexT(size = size1)
}
