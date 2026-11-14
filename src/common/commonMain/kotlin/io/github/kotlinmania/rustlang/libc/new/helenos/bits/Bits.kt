// port-lint: source new/helenos/bits.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.bits

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.UintptrT

// HelenOS ABI bits
//
// * Headers: <https://github.com/HelenOS/helenos/tree/master/abi/include/_bits>

// `errno.h`
public typealias ErrnoT = CInt

// `native.h`
public typealias SysargT = UintptrT
