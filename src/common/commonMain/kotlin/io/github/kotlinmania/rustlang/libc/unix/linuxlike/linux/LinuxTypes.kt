package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.types.CEnumRepr

public typealias DevT = ULong

public typealias SocklenT = UInt

public typealias ModeT = UInt

public typealias Ino64T = ULong

// Note(1.0,deprecate): lfs binding to be removed
public typealias Off64T = Long

public typealias Blkcnt64T = Long

public typealias Rlim64T = ULong

public typealias MqdT = CInt

public typealias NfdsT = CUlong

public typealias NlItem = CInt

public typealias IdtypeT = CUint

public typealias LoffT = CLonglong

public typealias PthreadKeyT = CUint

public typealias PthreadOnceT = CInt

public typealias PthreadSpinlockT = CInt

public typealias KernelFsidT = CAnonymousKernelFsidT

public typealias KernelClockidT = CInt

public typealias EventfdT = ULong

// linux/cnProc.h
// uapi/linux/magic.h
// Most `* _SUPER_MAGIC` constants are defined at the `linuxLike` level; the
// following are only available on newer Linux versions than the versions
// currently used in CI in some configurations, so we define them here.
// These functions are not available on OpenHarmony
// LFS64 extensions
//
// * musl has 64-bit versions only so aliases the LFS64 symbols to the standard ones
public typealias PidType = CEnumRepr

public typealias ProcCnMcastOp = CEnumRepr

public typealias ProcCnEvent = CEnumRepr
