package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.musl.b64.aarch64.Stat

public typealias PthreadT = MutPtr

public typealias ClockT = CLong

public typealias TimeT = Long

public typealias SusecondsT = Long

public typealias InoT = ULong

public typealias OffT = Long

public typealias BlkcntT = Long

public typealias ShmattT = CUlong

public typealias MsgqnumT = CUlong

public typealias MsglenT = CUlong

public typealias FsblkcntT = CUlonglong

public typealias FsfilcntT = CUlonglong

public typealias RlimT = CUlonglong

// Note(1.0,deprecate): lfs binding to be removed
public typealias Fsblkcnt64T = CUlonglong

// Note(1.0,deprecate): lfs binding to be removed
public typealias Fsfilcnt64T = CUlonglong

// Note(1.0,deprecate): lfs binding to be removed
public typealias Stat64 = Stat

public typealias Ioctl = CInt
