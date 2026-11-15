// port-lint: source new/glibc/sysdeps/unix/linux/mips/bits/socket_type.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.mips.bits.sockettype

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// Header: `sysdeps / unix / sysv / linux / mips / bits / socketType.h`

public val SOCK_DGRAM: CInt = 1

public val SOCK_STREAM: CInt = 2

public val SOCK_RAW: CInt = 3

public val SOCK_RDM: CInt = 4

public val SOCK_SEQPACKET: CInt = 5

public val SOCK_DCCP: CInt = 6

public val SOCK_PACKET: CInt = 10

public val SOCK_CLOEXEC: CInt = 524288

public val SOCK_NONBLOCK: CInt = 128
