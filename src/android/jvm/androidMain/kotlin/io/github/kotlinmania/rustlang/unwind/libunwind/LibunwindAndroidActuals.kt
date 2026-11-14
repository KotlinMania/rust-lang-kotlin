@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.types.UnwindWord

private object LibunwindJniBridge {
    init {
        System.loadLibrary("unwind_jni")
    }

    @JvmStatic
    external fun unwindLibunwindUnwindResume(exception: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindDeleteException(exception: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindGetLanguageSpecificData(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetRegionStart(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetTextRelBase(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetDataRelBase(ctx: Long): Long

    @JvmStatic
    external fun unwindLibunwindUnwindGetGr(ctx: Long, regIndex: Int): Long

    @JvmStatic
    external fun unwindLibunwindUnwindSetGr(ctx: Long, regIndex: Int, value: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindSetIp(ctx: Long, value: Long)

    @JvmStatic
    external fun unwindLibunwindUnwindGetIpinfo(ctx: Long, ipBeforeInsn: Long): Long

    @JvmStatic
    external fun unwindLibunwindGccSpecificHandler(exceptionRecord: Long, establisherFrame: Long, contextRecord: Long, dispatcherContext: Long, personality: Long): Int
}

public actual fun unwindResume(exception: MutPtr): Nothing {
    LibunwindJniBridge.unwindLibunwindUnwindResume(exception.rawAddress)
    error("Unreachable noreturn C function unwindResume")
}

public actual fun unwindDeleteException(exception: MutPtr) {
    LibunwindJniBridge.unwindLibunwindUnwindDeleteException(exception.rawAddress)
}

public actual fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr =
    MutPtr(LibunwindJniBridge.unwindLibunwindUnwindGetLanguageSpecificData(ctx.rawAddress))

public actual fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetRegionStart(ctx.rawAddress))

public actual fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetTextRelBase(ctx.rawAddress))

public actual fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetDataRelBase(ctx.rawAddress))

public actual fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetGr(ctx.rawAddress, regIndex))

public actual fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord) {
    LibunwindJniBridge.unwindLibunwindUnwindSetGr(ctx.rawAddress, regIndex, value.rawAddress)
}

public actual fun unwindSetIp(ctx: MutPtr, value: UnwindWord) {
    LibunwindJniBridge.unwindLibunwindUnwindSetIp(ctx.rawAddress, value.rawAddress)
}

public actual fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord =
    ConstPtr(LibunwindJniBridge.unwindLibunwindUnwindGetIpinfo(ctx.rawAddress, ipBeforeInsn.rawAddress))

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition =
    LibunwindJniBridge.unwindLibunwindGccSpecificHandler(exceptionRecord.rawAddress, establisherFrame.rawAddress, contextRecord.rawAddress, dispatcherContext.rawAddress, 0L)
