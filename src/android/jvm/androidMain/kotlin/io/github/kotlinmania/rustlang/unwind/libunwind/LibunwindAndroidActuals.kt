@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

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

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode
