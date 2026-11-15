package io.github.kotlinmania.rustlang.libc.windows.gnu

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.windows.SizeT
import io.github.kotlinmania.rustlang.windows.WcharT

public expect fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt

public expect fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt

// NOTE: For MSVC target, `wmemchr` is only a inline function in `<wchar.h>`
// header file. We cannot find a way to link to that symbol from Rust.
public expect fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr
