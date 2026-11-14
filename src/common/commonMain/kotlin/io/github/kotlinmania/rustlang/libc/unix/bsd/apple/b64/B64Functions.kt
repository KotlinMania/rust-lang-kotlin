package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.libc.*

// `exchangedata` is prohibited on tvOS and watchOS.
public expect fun exchangedata(path1: ConstPtr, path2: ConstPtr, options: CUint): CInt
