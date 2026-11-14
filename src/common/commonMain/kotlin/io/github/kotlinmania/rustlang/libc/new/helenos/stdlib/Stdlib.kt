// port-lint: source new/helenos/stdlib.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

public expect fun malloc(size: ULong): MutPtr

public expect fun calloc(nmemb: ULong, size: ULong): MutPtr

public expect fun realloc(addr: MutPtr, size: ULong): MutPtr

public expect fun free(addr: MutPtr)

public expect fun memalign(align: ULong, size: ULong): MutPtr

public expect fun rand(): CInt

public expect fun srand(seed: CUint)

public expect fun exit(code: CInt): Nothing

public expect fun abort(): Nothing

public expect fun getenv(env: ConstPtr): MutPtr
