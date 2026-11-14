package io.github.kotlinmania.rustlang.libc.unix

import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

public typealias IntmaxT = Long

public typealias UintmaxT = ULong

public typealias SizeT = ULong

public typealias PtrdiffT = Long

public typealias IntptrT = Long

public typealias UintptrT = ULong

public typealias SsizeT = Long

public typealias PidT = Int

public typealias InAddrT = UInt

public typealias InPortT = UShort

public typealias SighandlerT = SizeT

public typealias CcT = CUchar

// Note(1.0): We want to remove these directives and instead expect that no-std users add their
// own link configuration when required, rather than unconditionally linking everything that may
// possibly be needed.
public typealias UidT = Int

public typealias GidT = Int

public typealias LocaleT = MutPtr
