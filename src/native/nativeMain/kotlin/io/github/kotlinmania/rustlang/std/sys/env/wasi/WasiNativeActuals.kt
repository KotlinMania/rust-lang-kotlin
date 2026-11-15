@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.env.wasi

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.env.wasi.cinterop.rustlangStdSysEnvWasiWasilibcGetEnviron
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toLong

public actual fun wasilibcGetEnviron(): MutPtr =
    MutPtr(rustlangStdSysEnvWasiWasilibcGetEnviron()?.toLong() ?: 0L)
