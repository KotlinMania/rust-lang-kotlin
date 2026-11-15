@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket.cinterop.rustlangLibcNewBioniclibcSysSocketRecvfrom
import io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket.cinterop.rustlangLibcNewBioniclibcSysSocketRecvmmsg
import io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket.cinterop.rustlangLibcNewBioniclibcSysSocketSendmmsg
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: ConstPtr): CInt =
    rustlangLibcNewBioniclibcSysSocketRecvmmsg(sockfd, msgvec.rawAddress.toCPointer<ByteVar>(), vlen, flags, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun sendmmsg(sockfd: CInt, msgvec: ConstPtr, vlen: CUint, flags: CInt): CInt =
    rustlangLibcNewBioniclibcSysSocketSendmmsg(sockfd, msgvec.rawAddress.toCPointer<ByteVar>(), vlen, flags)

public actual fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT =
    rustlangLibcNewBioniclibcSysSocketRecvfrom(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())
