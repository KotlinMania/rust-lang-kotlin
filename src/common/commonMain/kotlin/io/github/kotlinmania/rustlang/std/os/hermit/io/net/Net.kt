// port-lint: source os/hermit/io/net.rs
package io.github.kotlinmania.rustlang.std.os.hermit.io.net

import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.std.os.solid.io.OwnedFd
import io.github.kotlinmania.rustlang.std.os.solid.io.RawFd
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.motor.Socket

public fun TcpStream.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun TcpListener.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun UdpSocket.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun fromRawFdTcpStream(fd: RawFd): TcpStream =
    run {
        val socket = Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd)))
        net.TcpStream.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpStream.fromInner(socket))
    }

public fun fromRawFdTcpListener(fd: RawFd): TcpListener =
    run {
        val socket = Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd)))
        net.TcpListener.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpListener.fromInner(socket))
    }

public fun fromRawFdUdpSocket(fd: RawFd): UdpSocket =
    run {
        val socket = Socket.fromInner(FromInner.fromInner(OwnedFd.fromRawFd(fd)))
        net.UdpSocket.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.UdpSocket.fromInner(socket))
    }

public fun TcpStream.intoRawFd(): RawFd = this.intoInner().intoSocket().intoInner().intoInner().intoRawFd()

public fun TcpListener.intoRawFd(): RawFd = this.intoInner().intoSocket().intoInner().intoInner().intoRawFd()

public fun UdpSocket.intoRawFd(): RawFd = this.intoInner().intoSocket().intoInner().intoInner().intoRawFd()
