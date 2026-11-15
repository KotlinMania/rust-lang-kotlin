package io.github.kotlinmania.rustlang.panicabort

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.android.androidSetAbortMessage
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun rustPanicCleanup(arg0: MutPtr): RustAny {
    error("unreachable")
}

// "Leak" the payload and shim to the relevant abort on the platform in question.
public fun rustStartPanic(payload: PanicPayload): UInt {
    // This is defined in std.rt.
    fun rustAbort(): Nothing = run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<Nothing>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<Nothing>()).alignBytes)); val res = (ObjectSlotLayout<Nothing>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<Nothing>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<Nothing>()).alignBytes)); res }
    // Android has the ability to attach a message as part of the abort.
    androidSetAbortMessage(payload)
    return rustAbort()
}
