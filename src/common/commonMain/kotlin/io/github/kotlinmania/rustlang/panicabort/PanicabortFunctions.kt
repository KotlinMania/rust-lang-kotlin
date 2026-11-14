package io.github.kotlinmania.rustlang.panicabort

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.panicabort.android.androidSetAbortMessage

public fun rustPanicCleanup(arg0: MutPtr): RustAny {
    error("unreachable")
}

// "Leak" the payload and shim to the relevant abort on the platform in question.
public fun rustStartPanic(payload: PanicPayload): UInt {
    // This is defined in std.rt.
    fun rustAbort(): Nothing = null as Nothing
    // Android has the ability to attach a message as part of the abort.
    androidSetAbortMessage(payload)
    return rustAbort()
}
