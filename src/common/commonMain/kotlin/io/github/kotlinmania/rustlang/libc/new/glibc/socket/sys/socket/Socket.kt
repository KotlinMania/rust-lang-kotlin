// port-lint: source new/glibc/socket/sys/socket.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.socket.sys.socket

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.new.bioniclibc.sys.socket.Msghdr

// Header: `socket / sys / socket.h`

public val SHUT_RD: CInt = 0

public val SHUT_WR: CInt = SHUT_RD + 1

public val SHUT_RDWR: CInt = SHUT_WR + 1

public data class Mmsghdr(
    public var msgHdr: Msghdr = Msghdr(),
    public var msgLen: CUint = 0u,
)
