@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.errno

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.errno.cinterop.libcNewHelenosErrnoErrno
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toLong

public actual fun errno(): MutPtr =
    MutPtr(libcNewHelenosErrnoErrno()?.toLong() ?: 0L)
