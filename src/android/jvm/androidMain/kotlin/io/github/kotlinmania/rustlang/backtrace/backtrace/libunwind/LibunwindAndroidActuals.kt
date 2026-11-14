@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.libc.fuchsia.UintptrT
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object BacktraceLibunwindJniBridge {
    init {
        System.loadLibrary("backtrace_jni")
    }

    @JvmStatic
    external fun backtraceBacktraceLibunwindUnwindGetIp(ctx: Long): Long

    @JvmStatic
    external fun backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc: Long): Long

    @JvmStatic
    external fun backtraceBacktraceLibunwindGetSp(ctx: Long): Long
}

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress).toULong()

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress))

public actual fun getSp(ctx: MutPtr): UintptrT =
    BacktraceLibunwindJniBridge.backtraceBacktraceLibunwindGetSp(ctx.rawAddress).toULong()
