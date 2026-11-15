@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.backtraceBacktraceLibunwindGetSp
import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.backtraceBacktraceLibunwindUnwindFindEnclosingFunction
import io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind.cinterop.backtraceBacktraceLibunwindUnwindGetIp
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.UintptrT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode

public actual fun unwindGetIp(ctx: MutPtr): UintptrT =
    backtraceBacktraceLibunwindUnwindGetIp(ctx.rawAddress.toCPointer<ByteVar>())

public actual fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr =
    MutPtr(backtraceBacktraceLibunwindUnwindFindEnclosingFunction(pc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getSp(ctx: MutPtr): UintptrT =
    backtraceBacktraceLibunwindGetSp(ctx.rawAddress.toCPointer<ByteVar>())
