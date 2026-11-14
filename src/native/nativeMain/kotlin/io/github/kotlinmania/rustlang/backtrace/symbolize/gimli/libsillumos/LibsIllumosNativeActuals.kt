@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsillumos

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsillumos.cinterop.backtraceSymbolizeGimliLibsillumosLibsIllumosDlinfo
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun dlinfo(handle: ConstPtr, request: CInt, p: MutPtr): CInt =
    backtraceSymbolizeGimliLibsillumosLibsIllumosDlinfo(handle.rawAddress.toCPointer<ByteVar>(), request, p.rawAddress.toCPointer<ByteVar>())
