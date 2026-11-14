@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.env.wasi

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.env.wasi.cinterop.stdSysEnvWasiWasilibcGetEnviron
import kotlinx.cinterop.toLong

public actual fun wasilibcGetEnviron(): MutPtr =
    MutPtr(stdSysEnvWasiWasilibcGetEnviron()?.toLong() ?: 0L)
