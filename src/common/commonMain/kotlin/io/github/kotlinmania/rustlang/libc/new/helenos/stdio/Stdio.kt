// port-lint: source new/helenos/stdio.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.stdio

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT

// HelenOS libc standard I/O APIs
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/stdio.h>

public val SEEK_SET: CInt = 0

public val SEEK_CUR: CInt = 1

public val SEEK_END: CInt = 2

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class FILE(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public expect val STDIN: MutPtr

public expect val STDOUT: MutPtr

public expect val STDERR: MutPtr

public expect fun fputc(c: CInt, stream: MutPtr): CInt

public expect fun fputs(s: ConstPtr, stream: MutPtr): CInt

public expect fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr

public expect fun fclose(stream: MutPtr): CInt

public expect fun fflush(stream: MutPtr): CInt

public expect fun fread(buf: MutPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT

public expect fun fwrite(buf: ConstPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT

public expect fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt

public expect fun ftell(stream: MutPtr): CLong

public expect fun fileno(stream: MutPtr): CInt
