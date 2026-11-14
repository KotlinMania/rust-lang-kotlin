@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindGccSpecificHandler
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindDeleteException
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetDataRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetGr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetIpinfo
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetLanguageSpecificData
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetRegionStart
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetTextRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindResume
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindSetGr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindSetIp
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.types.UnwindWord
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun unwindResume(exception: MutPtr): Nothing {
    unwindLibunwindUnwindResume(exception.rawAddress.toCPointer<ByteVar>())
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    unwindLibunwindUnwindDeleteException(exception.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    MutPtr(unwindLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    ConstPtr(unwindLibunwindUnwindGetRegionStart(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(unwindLibunwindUnwindGetTextRelBase(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(unwindLibunwindUnwindGetDataRelBase(ctx.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    ConstPtr(unwindLibunwindUnwindGetGr(ctx.rawAddress.toCPointer<ByteVar>(), regIndex)?.toLong() ?: 0L)

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    unwindLibunwindUnwindSetGr(ctx.rawAddress.toCPointer<ByteVar>(), regIndex, value.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    unwindLibunwindUnwindSetIp(ctx.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    ConstPtr(unwindLibunwindUnwindGetIpinfo(ctx.rawAddress.toCPointer<ByteVar>(), ipBeforeInsn.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    unwindLibunwindGccSpecificHandler(exceptionRecord.rawAddress.toCPointer<ByteVar>(), establisherFrame.rawAddress.toCPointer<ByteVar>(), contextRecord.rawAddress.toCPointer<ByteVar>(), dispatcherContext.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())
