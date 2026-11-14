// port-lint: source new/musl/arch/mips64/bits/socket.rs
package io.github.kotlinmania.rustlang.libc.new.musl.arch.mips64.bits.socket

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.libc.*

public val SOCK_STREAM: CInt = 2

public val SOCK_DGRAM: CInt = 1

public val SOCK_NONBLOCK: CInt = 128

public val SOCK_CLOEXEC: CInt = 524288
