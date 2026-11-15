package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// `exchangedata` is prohibited on tvOS and watchOS.
public expect fun exchangedata(path1: ConstPtr, path2: ConstPtr, options: CUint): CInt
