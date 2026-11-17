package io.github.kotlinmania.rustlang.std.os.wasi.net

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.sys.pal.wasi.err2io
import io.github.kotlinmania.rustlang.wasip1.Fd

public fun TcpListener.sockAccept(flags: UShort): Result<UInt, Error> = io.github.kotlinmania.rustlang.wasip1.sockAccept(((this.asRawFd() as Any?) as Fd), flags).mapErr(err2io)
