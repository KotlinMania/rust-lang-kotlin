@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.UintptrT

private object BacktraceBacktraceLibunwindJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangBacktraceBacktraceLibunwindUnwindGetIp(ctx: Long): Long

    @JvmStatic
    external fun rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc: Long): Long

    @JvmStatic
    external fun rustlangBacktraceBacktraceLibunwindGetSp(ctx: Long): Long
}

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress).toULong()

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress))

public actual fun getSp(ctx: MutPtr): UintptrT =
    BacktraceBacktraceLibunwindJniBridge.rustlangBacktraceBacktraceLibunwindGetSp(ctx.rawAddress).toULong()
