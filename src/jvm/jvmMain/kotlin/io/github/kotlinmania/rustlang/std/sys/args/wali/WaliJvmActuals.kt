@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.args.wali.imp

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUint as WaliArgIdx

public actual fun clGetArgc(): WaliArgIdx =
    null as WaliArgIdx

public actual fun clGetArgvLen(offset: WaliArgIdx): CUint =
    0u

public actual fun clCopyArgv(buf: MutPtr, offset: WaliArgIdx): CUint =
    0u
