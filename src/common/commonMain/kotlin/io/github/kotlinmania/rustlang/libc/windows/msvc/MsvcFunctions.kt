package io.github.kotlinmania.rustlang.libc.windows.msvc

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.windows.SizeT

public expect fun stricmp(s1: ConstPtr, s2: ConstPtr): CInt

public expect fun strnicmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt

public expect fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, count: SizeT): MutPtr
