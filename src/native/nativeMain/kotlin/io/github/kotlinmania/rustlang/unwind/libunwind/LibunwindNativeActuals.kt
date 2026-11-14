@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindDeleteException
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetDataRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetLanguageSpecificData
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetRegionStart
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindGetTextRelBase
import io.github.kotlinmania.rustlang.unwind.libunwind.cinterop.unwindLibunwindUnwindResume
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import kotlinx.cinterop.ByteVar
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

public actual fun unwindRaiseException(exception: MutPtr): UnwindReasonCode =
    null as UnwindReasonCode
