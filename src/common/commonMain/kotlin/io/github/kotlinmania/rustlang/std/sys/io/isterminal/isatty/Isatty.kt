// port-lint: source sys/io/is_terminal/isatty.rs
package io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty

import io.github.kotlinmania.rustlang.libc.isatty
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.asFd

public fun isTerminal(fd: AsFd): Boolean {
    val fd1 = fd.asFd()
    return isatty(fd1.asRawFd()) != 0
}
