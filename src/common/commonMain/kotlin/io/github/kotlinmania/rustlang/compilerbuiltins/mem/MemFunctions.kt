package io.github.kotlinmania.rustlang.compilerbuiltins.mem

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.mem.x8664.cStringLength
import io.github.kotlinmania.rustlang.mem.x8664.compareBytes
import io.github.kotlinmania.rustlang.mem.x8664.copyBackward
import io.github.kotlinmania.rustlang.mem.x8664.copyForward
import io.github.kotlinmania.rustlang.mem.x8664.setBytes

public fun memcpy(dest: MutPtr, src: ConstPtr, n: Int): MutPtr {
    copyForward(dest, src, n)
    return dest
}

public fun memmove(dest: MutPtr, src: ConstPtr, n: Int): MutPtr {
    val delta = ((dest.rawAddress.toInt()) - (src.rawAddress.toInt()))
    if (delta >= n) {
        // We can copy forwards because either dest is far enough ahead of src,
        // or src is ahead of dest (and delta overflowed).
        copyForward(dest, src, n)
    } else {
        copyBackward(dest, src, n)
    }
    return dest
}

public fun memset(s: MutPtr, c: CInt, n: Int): MutPtr {
    setBytes(s, c.toUByte(), n)
    return s
}

public fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = compareBytes(s1, s2, n.toInt())

public fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt = memcmp(s1, s2, n.toInt())

public fun strlen(s: ConstPtr): ULong = cStringLength(s).toULong()

public fun wcslen(s: ConstPtr): Int {
    val s1 = s
    var s2 = s1
    var n = 0
    while (s2 != 0) {
        n += 1
        s2 = s2.add(1)
    }
    return n
}
