package io.github.kotlinmania.rustlang.std.sys.net.connection.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// Similarly to reads (see READ_LIMIT), the `send` syscall on most platforms
// takes a `sizeT` length, but returns an `ssizeT` of bytes written. So the
// actual maximum number of bytes we can send in one call is SSIZE_MAX.
//
// On Apple targets however, apparently the 64-bit libc is either buggy or
// intentionally showing odd behavior by rejecting send calls with a size
// larger than INT_MAX. So cap the send size to INT_MAX.
//
// Meanwhile on QNX, reads/writes/sends larger than INT_MAX return the wrong
// number of bytes written (eg, writing 2^31 bytes returns (2^64 - 2^31) instead
// of the correct byte count).
//
// On Windows, the relevant syscall takes an `Int` (unlike for read/write),
// so we need to clamp to i32.Max.
public val MAX_SEND_LEN: Int = Int.MAX_VALUE.toInt()

/**
 * Some systems support setting the nonblocking mode upon socket creation, which
 * saves a syscall.
 */
public val SOCK_NONBLOCK: Int = c.SOCK_NONBLOCK

public val MSG_NOSIGNAL: CInt = 0x0
