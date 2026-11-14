// port-lint: source sys/io/is_terminal/motor.rs
package io.github.kotlinmania.rustlang.std.sys.io.isterminal.motor

import io.github.kotlinmania.rustlang.std.os.fd.AsFd

public fun isTerminal(fd: AsFd): Boolean {
    val fd1 = fd.asFd()
    return io.github.kotlinmania.motort.fs.isTerminal(fd1.asRawFd())
}
