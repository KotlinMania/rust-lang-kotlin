// port-lint: source dummy.rs
package io.github.kotlinmania.rustlang.panicunwind.dummy

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Unwinding for unsupported target.
//
// Stubs that simply abort for targets that don't support unwinding otherwise.

// This is defined in std.rt
public expect fun rustAbort(): Nothing

public fun cleanup(ptr: MutPtr): RustAny = rustAbort()

public fun panic(data: PanicPayload): UInt = rustAbort()
