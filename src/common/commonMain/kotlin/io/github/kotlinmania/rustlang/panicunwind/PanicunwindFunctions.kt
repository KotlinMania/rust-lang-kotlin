package io.github.kotlinmania.rustlang.panicunwind

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.panicunwind.imp.cleanup
import io.github.kotlinmania.rustlang.panicunwind.imp.panic

/**
 * Handler in std called when a panic object is dropped outside of
 * `catchUnwind`.
 */
public expect fun rustDropPanic(): Nothing

/**
 * Handler in std called when a foreign exception is caught.
 */
public expect fun rustForeignException(): Nothing

public fun rustPanicCleanup(payload: MutPtr): RustAny = cleanup(payload)

// Entry point for raising an exception, just delegates to the platform-specific
// implementation.
public fun rustStartPanic(payload: PanicPayload): UInt = panic(payload)
