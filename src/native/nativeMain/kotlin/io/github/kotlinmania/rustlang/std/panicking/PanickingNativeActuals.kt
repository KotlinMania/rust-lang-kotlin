@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.panicking

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rustPanicCleanup(payload: MutPtr): RustAny =
    null as RustAny

public actual fun rustStartPanic(payload: PanicPayload): UInt =
    0u
