@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.miri

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.panicunwind.miri.cinterop.panicunwindMiriMiriStartUnwind
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun miriStartUnwind(payload: MutPtr): Nothing {
    panicunwindMiriMiriStartUnwind(payload.rawAddress.toCPointer<ByteVar>())
    error("Unreachable noreturn C function miriStartUnwind")
}
