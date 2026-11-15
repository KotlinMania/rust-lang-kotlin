@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.types.UnwindWord
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindGccSpecificHandler
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindDeleteException
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetDataRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetGr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetIpinfo
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetLanguageSpecificData
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetRegionStart
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindGetTextRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindResume
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindSetGr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.rustlangLibunwindUnwindSetIp
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun unwindResume(exception: MutPtr): Nothing {
    rustlangLibunwindUnwindResume(exception.rawAddress.toCPointer<ByteVar>())
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    rustlangLibunwindUnwindDeleteException(exception.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    MutPtr(rustlangLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    ConstPtr(rustlangLibunwindUnwindGetRegionStart(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(rustlangLibunwindUnwindGetTextRelBase(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(rustlangLibunwindUnwindGetDataRelBase(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    ConstPtr(rustlangLibunwindUnwindGetGr(ctx.rawAddress.toCPointer<ByteVar>(), regIndex)?.toLong() ?: 0L)

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    rustlangLibunwindUnwindSetGr(ctx.rawAddress.toCPointer<ByteVar>(), regIndex, value.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    rustlangLibunwindUnwindSetIp(ctx.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    ConstPtr(rustlangLibunwindUnwindGetIpinfo(ctx.rawAddress.toCPointer<ByteVar>(), ipBeforeInsn.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    rustlangLibunwindGccSpecificHandler(exceptionRecord.rawAddress.toCPointer<ByteVar>(), establisherFrame.rawAddress.toCPointer<ByteVar>(), contextRecord.rawAddress.toCPointer<ByteVar>(), dispatcherContext.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())
