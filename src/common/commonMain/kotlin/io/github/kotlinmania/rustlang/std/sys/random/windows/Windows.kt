// port-lint: source sys/random/windows.rs
package io.github.kotlinmania.rustlang.std.sys.random.windows

import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.std.sys.c

public fun fillBytes(bytes: MutableList<UByte>) {
    val ret = c.ProcessPrng(bytes.asMutPtr(), bytes.size)
    // ProcessPrng is documented as always returning `TRUE`.
    // https://learn.microsoft.com/en-us/windows/win32/seccng/processprng#return-value
    check(ret == c.TRUE)
}
