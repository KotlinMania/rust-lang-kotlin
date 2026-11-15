package io.github.kotlinmania.rustlang.compilerbuiltins.mem

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public typealias WcharT = UShort

public fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr = io.github.kotlinmania.rustlang.mem.memcpy(dest, src, n.toInt())

public fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr = io.github.kotlinmania.rustlang.mem.memmove(dest, src, n.toInt())

public fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr = io.github.kotlinmania.rustlang.mem.memset(s, c, n.toInt())

public fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = io.github.kotlinmania.rustlang.mem.memcmp(s1, s2, n.toInt())

public fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = io.github.kotlinmania.rustlang.mem.bcmp(s1, s2, n.toInt())

public fun strlen(s: ConstPtr): ULong = io.github.kotlinmania.rustlang.mem.strlen(s).toULong()

public fun wcslen(s: ConstPtr): ULong = io.github.kotlinmania.rustlang.mem.wcslen(s).toULong()
