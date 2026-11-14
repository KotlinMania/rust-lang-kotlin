package io.github.kotlinmania.rustlang.compilerbuiltins.mem

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public typealias WcharT = UShort

public fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr = memcpy(dest, src, n.toInt())

public fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr = memmove(dest, src, n.toInt())

public fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr = memset(s, c, n.toInt())

public fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = memcmp(s1, s2, n.toInt())

public fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = bcmp(s1, s2, n.toInt())

public fun strlen(s: ConstPtr): ULong = strlen(s).toULong()

public fun wcslen(s: ConstPtr): ULong = wcslen(s).toULong()
