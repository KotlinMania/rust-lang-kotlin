@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.types.UnwindWord

private object LibunwindJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibunwindUnwindResume(exception: Long)

    @JvmStatic
    external fun rustlangLibunwindUnwindDeleteException(exception: Long)

    @JvmStatic
    external fun rustlangLibunwindUnwindGetLanguageSpecificData(ctx: Long): Long

    @JvmStatic
    external fun rustlangLibunwindUnwindGetRegionStart(ctx: Long): Long

    @JvmStatic
    external fun rustlangLibunwindUnwindGetTextRelBase(ctx: Long): Long

    @JvmStatic
    external fun rustlangLibunwindUnwindGetDataRelBase(ctx: Long): Long

    @JvmStatic
    external fun rustlangLibunwindUnwindGetGr(ctx: Long, regIndex: Int): Long

    @JvmStatic
    external fun rustlangLibunwindUnwindSetGr(ctx: Long, regIndex: Int, value: Long)

    @JvmStatic
    external fun rustlangLibunwindUnwindSetIp(ctx: Long, value: Long)

    @JvmStatic
    external fun rustlangLibunwindUnwindGetIpinfo(ctx: Long, ipBeforeInsn: Long): Long

    @JvmStatic
    external fun rustlangLibunwindGccSpecificHandler(exceptionRecord: Long, establisherFrame: Long, contextRecord: Long, dispatcherContext: Long, personality: Long): Int
}

public actual fun unwindResume(exception: MutPtr): Nothing {
    LibunwindJniBridge.rustlangLibunwindUnwindResume(exception.rawAddress)
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    LibunwindJniBridge.rustlangLibunwindUnwindDeleteException(exception.rawAddress)
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    MutPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress))

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetRegionStart(ctx.rawAddress))

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetTextRelBase(ctx.rawAddress))

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetDataRelBase(ctx.rawAddress))

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    ConstPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetGr(ctx.rawAddress, regIndex))

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    LibunwindJniBridge.rustlangLibunwindUnwindSetGr(ctx.rawAddress, regIndex, value.rawAddress)
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    LibunwindJniBridge.rustlangLibunwindUnwindSetIp(ctx.rawAddress, value.rawAddress)
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    ConstPtr(LibunwindJniBridge.rustlangLibunwindUnwindGetIpinfo(ctx.rawAddress, ipBeforeInsn.rawAddress))

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    LibunwindJniBridge.rustlangLibunwindGccSpecificHandler(exceptionRecord.rawAddress, establisherFrame.rawAddress, contextRecord.rawAddress, dispatcherContext.rawAddress, 0L)
