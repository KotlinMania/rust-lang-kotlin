@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

private object NewHelenosFibrilJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilCreateGeneric(func: Long, arg: Long, stacksize: Long): Long

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilStart(f: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilExit(retval: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilDetach(f: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilYield()

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilUsleep(usec: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilFibrilGetId(): Long
}

public actual fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT =
    MutPtr(NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilCreateGeneric(0L, arg.rawAddress, stacksize.toLong()))

public actual fun fibrilStart(f: FidT) {
    NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilStart(f.rawAddress)
}

public actual fun fibrilExit(retval: CLong): Nothing {
    NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilExit(retval)
    error("Unreachable noreturn C function fibrilExit")
}

public actual fun fibrilDetach(f: FidT) {
    NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilDetach(f.rawAddress)
}

public actual fun fibrilYield() {
    NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilYield()
}

public actual fun fibrilUsleep(usec: UsecT) {
    NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilUsleep(usec)
}

public actual fun fibrilGetId(): FidT =
    MutPtr(NewHelenosFibrilJniBridge.libcNewHelenosFibrilFibrilGetId())
