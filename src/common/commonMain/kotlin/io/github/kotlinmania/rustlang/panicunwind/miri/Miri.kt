// port-lint: source miri.rs
package io.github.kotlinmania.rustlang.panicunwind.miri

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Unwinding panics for Miri.

// The type of the payload that the Miri engine propagates through unwinding for us.
// Must be pointer-sized.
public typealias Payload = RustAny

/**
 * Miri-provided extern function to begin unwinding.
 */
public expect fun miriStartUnwind(payload: MutPtr): Nothing

internal fun panic(payload: PanicPayload): UInt {
    // The payload we pass to `miriStartUnwind` will be exactly the argument we get
    // in `cleanup` below. So we just box it up once, to get something pointer-sized.
    val payloadBox: Payload = payload.takeBox()
    return miriStartUnwind(mutPtrFromRustCast(Box.intoRaw(payloadBox)))
}

internal fun cleanup(payloadBox: MutPtr): RustAny {
    // Recover the underlying `Holder`.
    val payloadBox1: Payload = Box.fromRaw(payloadBox)
    return payloadBox1
}
