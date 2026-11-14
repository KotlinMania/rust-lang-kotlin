package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

public typealias IntmaxT = Long

public typealias UintmaxT = ULong

public typealias SizeT = ULong

public typealias SsizeT = Long

public typealias PtrdiffT = Long

public typealias IntptrT = Long

public typealias UintptrT = ULong

public typealias OffT = CLonglong

public typealias PidT = Int

public typealias ClockT = CLonglong

public typealias TimeT = CLonglong

public typealias InoT = ULong

public typealias SigsetT = CUchar

public typealias SusecondsT = CLonglong

public typealias ModeT = UInt

public typealias DevT = ULong

public typealias UidT = UInt

public typealias GidT = UInt

public typealias NlinkT = ULong

public typealias BlksizeT = CLong

public typealias BlkcntT = Long

public typealias NfdsT = CUlong

public typealias WcharT = Int

public typealias NlItem = CInt

public typealias WasiRightsT = ULong

public typealias LocaleT = MutPtr

public typealias PthreadT = MutPtr

public typealias PthreadOnceT = CInt

public typealias PthreadKeyT = CUint

public typealias PthreadSpinlockT = CInt
