// port-lint: source new/glibc/io/sys/statvfs.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

// Header: `io / sys / statvfs.h`

public expect fun statvfs(path: ConstPtr, buf: MutPtr): CInt

public expect fun fstatvfs(fd: CInt, buf: MutPtr): CInt

// Note(1.0,deprecate): lfs binding to be removed
public expect fun statvfs64(path: ConstPtr, buf: MutPtr): CInt

// Note(1.0,deprecate): lfs binding to be removed
public expect fun fstatvfs64(fd: CInt, buf: MutPtr): CInt
