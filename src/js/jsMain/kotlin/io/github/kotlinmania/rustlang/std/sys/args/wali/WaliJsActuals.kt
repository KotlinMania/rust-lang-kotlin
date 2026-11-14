@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.args.wali.imp

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUint as WaliArgIdx

public actual fun clGetArgc(): WaliArgIdx =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clGetArgvLen(offset: WaliArgIdx): CUint =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clCopyArgv(buf: MutPtr, offset: WaliArgIdx): CUint =
    throw UnsupportedOperationException("Native FFI addon is not available")
