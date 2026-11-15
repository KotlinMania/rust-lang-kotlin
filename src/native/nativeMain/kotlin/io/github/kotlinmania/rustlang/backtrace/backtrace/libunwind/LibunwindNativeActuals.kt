@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.rustlangBacktraceBacktraceLibunwindGetSp
import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction
import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.rustlangBacktraceBacktraceLibunwindUnwindGetIp
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.UintptrT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    rustlangBacktraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress.toCPointer<ByteVar>())

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getSp(ctx: MutPtr): UintptrT =
    rustlangBacktraceBacktraceLibunwindGetSp(ctx.rawAddress.toCPointer<ByteVar>())
