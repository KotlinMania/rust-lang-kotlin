// port-lint: source new/glibc/signal.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.signal

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Header: `signal / signal.h`

public expect fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt
