// port-lint: source wasm.rs
package io.github.kotlinmania.rustlang.unwind.wasm

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindException
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

// A shim for libunwind implemented in terms of the native wasm `throw` instruction.

public fun unwindDeleteException(exception: MutPtr) {
    when (val matchVal = (ObjectSlotLayout<UnwindException>()).readNative((exception).rawAddress).exceptionCleanup) {
        null -> {}
        else -> {
            val exceptionCleanup = matchVal
            exceptionCleanup(URC_FOREIGN_EXCEPTION_CAUGHT, exception)
        }
    }
}

public fun unwindRaiseException(exception: MutPtr): UnwindReasonCode {
    // This implementation is only used for `wasm * - unknown - unknown` targets. Such targets are not
    // guaranteed to support exceptions, and they default to `- C panic = abort`. Because an unknown
    // instruction is a load-time error on wasm, instead of a runtime error like on traditional
    // architectures, we never want to codegen a `throw` instruction unless the user explicitly
    // enabled exceptions via `- Z build - std` with `- C panic = unwind`.
    return cfgSelect(panic, "unwind", extern, "C-unwind", doc, " LLVM lowers this intrinsic to the `throw` instruction.", linkName, "llvm.wasm.throw", wasmThrow, tag, ptr, CPP_EXCEPTION_TAG, 0, wasmThrow, CPP_EXCEPTION_TAG, exception, cast, exception, core, arch, wasm, unreachable)
}
