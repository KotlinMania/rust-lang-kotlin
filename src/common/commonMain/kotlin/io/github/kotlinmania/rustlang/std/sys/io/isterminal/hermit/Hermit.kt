// port-lint: source sys/io/is_terminal/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.io.isterminal.hermit

import io.github.kotlinmania.hermitabi.isatty
import io.github.kotlinmania.rustlang.std.os.fd.AsFd

public fun isTerminal(fd: AsFd): Boolean {
    val fd1 = fd.asFd()
    return isatty(fd1.asRawFd())
}
