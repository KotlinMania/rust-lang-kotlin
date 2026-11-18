// port-lint: source new/glibc/socket/sys/socket.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.socket.sys.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket.Msghdr

// Header: `socket / sys / socket.h`

public val SHUT_RD: CInt = 0

public val SHUT_WR: CInt = SHUT_RD + 1

public val SHUT_RDWR: CInt = SHUT_WR + 1

public open class Mmsghdr(
    public var msgHdr: Msghdr,
    public var msgLen: CUint,
) {
    public open fun clone(): Mmsghdr = Mmsghdr(msgHdr, msgLen)

    public companion object
}
