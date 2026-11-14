@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdio

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFclose
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFflush
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFileno
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFopen
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFputc
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFputs
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFread
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFseek
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFtell
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioFwrite
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioGetSTDERR
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioGetSTDIN
import io.github.kotlinmania.rustlang.libc.new.helenos.stdio.cinterop.libcNewHelenosStdioGetSTDOUT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual val STDIN: MutPtr
    get() = MutPtr(libcNewHelenosStdioGetSTDIN()?.toLong() ?: 0L)

public actual val STDOUT: MutPtr
    get() = MutPtr(libcNewHelenosStdioGetSTDOUT()?.toLong() ?: 0L)

public actual val STDERR: MutPtr
    get() = MutPtr(libcNewHelenosStdioGetSTDERR()?.toLong() ?: 0L)

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    libcNewHelenosStdioFputc(c, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    libcNewHelenosStdioFputs(s.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(libcNewHelenosStdioFopen(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fclose(stream: MutPtr): CInt =
    libcNewHelenosStdioFclose(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fflush(stream: MutPtr): CInt =
    libcNewHelenosStdioFflush(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fread(buf: MutPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    libcNewHelenosStdioFread(buf.rawAddress.toCPointer<ByteVar>(), size, nmemb, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fwrite(buf: ConstPtr, size: SizeT, nmemb: SizeT, stream: MutPtr): SizeT =
    libcNewHelenosStdioFwrite(buf.rawAddress.toCPointer<ByteVar>(), size, nmemb, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    libcNewHelenosStdioFseek(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    libcNewHelenosStdioFtell(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fileno(stream: MutPtr): CInt =
    libcNewHelenosStdioFileno(stream.rawAddress.toCPointer<ByteVar>())
