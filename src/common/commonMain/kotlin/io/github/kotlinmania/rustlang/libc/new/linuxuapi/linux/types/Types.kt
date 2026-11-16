// port-lint: source new/linux_uapi/linux/types.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.libc.*

// * Header: `uapi / linux / types.h`
// * Header: `uapi / asm - generic / int - ll64.h`

// Definitions from `asm / types.h` -> `asm - generic / types.h` -> `asm - generic / int - ll64.h`
public typealias U8 = CUchar

public typealias U16 = CUshort

public typealias S16 = CShort

public typealias U32 = CUint

public typealias S32 = CInt

public typealias S64 = CLonglong

public typealias U64 = CUlonglong
