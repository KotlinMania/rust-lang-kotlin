@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsaix

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.libsaix.cinterop.backtraceSymbolizeGimliLibsaixLibsAixErrnoLocation
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.toLong

public actual fun errnoLocation(): MutPtr =
    MutPtr(backtraceSymbolizeGimliLibsaixLibsAixErrnoLocation()?.toLong() ?: 0L)
