@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilCreateGeneric
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilDetach
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilExit
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilGetId
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilStart
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilUsleep
import io.github.kotlinmania.rustlang.libc.new.helenos.fibril.cinterop.libcNewHelenosFibrilFibrilYield
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT =
    MutPtr(libcNewHelenosFibrilFibrilCreateGeneric(0L.toCPointer<ByteVar>(), arg.rawAddress.toCPointer<ByteVar>(), stacksize)?.toLong() ?: 0L)

public actual fun fibrilStart(f: FidT) {
    libcNewHelenosFibrilFibrilStart(f.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilExit(retval: CLong): Nothing {
    libcNewHelenosFibrilFibrilExit(retval)
    error("Unreachable noreturn C function fibrilExit")
}

public actual fun fibrilDetach(f: FidT) {
    libcNewHelenosFibrilFibrilDetach(f.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilYield() {
    libcNewHelenosFibrilFibrilYield()
}

public actual fun fibrilUsleep(usec: UsecT) {
    libcNewHelenosFibrilFibrilUsleep(usec)
}

public actual fun fibrilGetId(): FidT =
    MutPtr(libcNewHelenosFibrilFibrilGetId()?.toLong() ?: 0L)
