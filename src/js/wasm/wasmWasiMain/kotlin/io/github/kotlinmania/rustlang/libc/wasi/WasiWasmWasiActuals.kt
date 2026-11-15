@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CFloat
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual var ENVIRON: MutPtr
    get() = throw UnsupportedOperationException("WasmWasi foreign static environ requires a host memory/aggregate ABI adapter")
    set(value) {
        throw UnsupportedOperationException("WasmWasi foreign static environ requires a host memory/aggregate ABI adapter")
    }

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiGetCLOCKMONOTONIC1")
private external fun wasiRustlangLibcWasiGetCLOCKMONOTONIC1(): Int

public actual val CLOCK_MONOTONIC1: UByte
    get() = wasiRustlangLibcWasiGetCLOCKMONOTONIC1().toUByte()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiGetCLOCKREALTIME1")
private external fun wasiRustlangLibcWasiGetCLOCKREALTIME1(): Int

public actual val CLOCK_REALTIME1: UByte
    get() = wasiRustlangLibcWasiGetCLOCKREALTIME1().toUByte()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiExit")
private external fun wasiRustlangLibcWasiExit(code: Int): Unit

public actual fun exit(code: CInt): Nothing {
    wasiRustlangLibcWasiExit(code)
    error("C noreturn function rustlangLibcWasiExit returned")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiAbort")
private external fun wasiRustlangLibcWasiAbort(): Unit

public actual fun abort(): Nothing {
    wasiRustlangLibcWasiAbort()
    error("C noreturn function rustlangLibcWasiAbort returned")
}

public actual fun alignedAlloc(a: SizeT, b: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAlignedAlloc")

public actual fun calloc(amt: SizeT, amt2: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiCalloc")

public actual fun free(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFree")
}

public actual fun getenv(s: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetenv")

public actual fun malloc(amt: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMalloc")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMallocUsableSize")

public actual fun sbrk(increment: IntptrT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSbrk")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiRand")
private external fun wasiRustlangLibcWasiRand(): Int

public actual fun rand(): CInt =
    wasiRustlangLibcWasiRand().toInt()

public actual fun read(fd: CInt, ptr: MutPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRead")

public actual fun realloc(ptr: MutPtr, amt: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRealloc")

public actual fun setenv(k: ConstPtr, v: ConstPtr, a: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSetenv")

public actual fun unsetenv(k: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUnsetenv")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiClearenv")
private external fun wasiRustlangLibcWasiClearenv(): Int

public actual fun clearenv(): CInt =
    wasiRustlangLibcWasiClearenv().toInt()

public actual fun write(fd: CInt, ptr: ConstPtr, size: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWrite")

public actual fun fopen(a: ConstPtr, b: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFopen")

public actual fun freopen(a: ConstPtr, b: ConstPtr, f: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFreopen")

public actual fun fclose(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFclose")

public actual fun remove(a: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRemove")

public actual fun rename(a: ConstPtr, b: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRename")

public actual fun feof(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFeof")

public actual fun ferror(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFerror")

public actual fun fflush(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFflush")

public actual fun clearerr(f: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiClearerr")
}

public actual fun fseek(f: MutPtr, b: CLong, c: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFseek")

public actual fun ftell(f: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFtell")

public actual fun rewind(f: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRewind")
}

public actual fun fgetpos(f: MutPtr, pos: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFgetpos")

public actual fun fsetpos(f: MutPtr, pos: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFsetpos")

public actual fun fread(buf: MutPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFread")

public actual fun fwrite(buf: ConstPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFwrite")

public actual fun fgetc(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFgetc")

public actual fun getc(f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetc")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiGetchar")
private external fun wasiRustlangLibcWasiGetchar(): Int

public actual fun getchar(): CInt =
    wasiRustlangLibcWasiGetchar().toInt()

public actual fun ungetc(a: CInt, f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUngetc")

public actual fun fputc(a: CInt, f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFputc")

public actual fun putc(a: CInt, f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPutc")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPutchar")
private external fun wasiRustlangLibcWasiPutchar(a: Int): Int

public actual fun putchar(a: CInt): CInt =
    wasiRustlangLibcWasiPutchar(a).toInt()

public actual fun fputs(a: ConstPtr, f: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFputs")

public actual fun puts(a: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPuts")

public actual fun perror(a: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPerror")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiSrand")
private external fun wasiRustlangLibcWasiSrand(a: Int): Unit

public actual fun srand(a: CUint): Unit =
    wasiRustlangLibcWasiSrand(a.toInt())

public actual fun atexit(a: AtexitACallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtexit")

public actual fun atQuickExit(a: AtexitACallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtQuickExit")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiQuickExit")
private external fun wasiRustlangLibcWasiQuickExit(a: Int): Unit

public actual fun quickExit(a: CInt): Nothing {
    wasiRustlangLibcWasiQuickExit(a)
    error("C noreturn function rustlangLibcWasiQuickExit returned")
}

public actual fun posixMemalign(a: MutPtr, b: SizeT, c: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPosixMemalign")

public actual fun randR(a: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRandR")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiRandom")
private external fun wasiRustlangLibcWasiRandom(): Long

public actual fun random(): CLong =
    wasiRustlangLibcWasiRandom().toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiSrandom")
private external fun wasiRustlangLibcWasiSrandom(a: Int): Unit

public actual fun srandom(a: CUint): Unit =
    wasiRustlangLibcWasiSrandom(a.toInt())

public actual fun putenv(a: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPutenv")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiClock")
private external fun wasiRustlangLibcWasiClock(): Long

public actual fun clock(): ClockT =
    wasiRustlangLibcWasiClock().toLong()

public actual fun time(a: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiTime")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiDifftime")
private external fun wasiRustlangLibcWasiDifftime(a: Long, b: Long): Double

public actual fun difftime(a: TimeT, b: TimeT): CDouble =
    wasiRustlangLibcWasiDifftime(a, b).toDouble()

public actual fun mktime(a: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMktime")

public actual fun strftime(a: MutPtr, b: SizeT, c: ConstPtr, d: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrftime")

public actual fun gmtime(a: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGmtime")

public actual fun gmtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGmtimeR")

public actual fun localtime(a: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLocaltime")

public actual fun localtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLocaltimeR")

public actual fun asctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAsctimeR")

public actual fun ctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiCtimeR")

public actual fun nanosleep(a: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiNanosleep")

public actual fun clockGetres(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiClockGetres")

public actual fun clockGettime(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiClockGettime")

public actual fun clockNanosleep(a: ClockidT, a2: CInt, b: ConstPtr, c: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiClockNanosleep")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsalnum")
private external fun wasiRustlangLibcWasiIsalnum(c: Int): Int

public actual fun isalnum(c: CInt): CInt =
    wasiRustlangLibcWasiIsalnum(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsalpha")
private external fun wasiRustlangLibcWasiIsalpha(c: Int): Int

public actual fun isalpha(c: CInt): CInt =
    wasiRustlangLibcWasiIsalpha(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIscntrl")
private external fun wasiRustlangLibcWasiIscntrl(c: Int): Int

public actual fun iscntrl(c: CInt): CInt =
    wasiRustlangLibcWasiIscntrl(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsdigit")
private external fun wasiRustlangLibcWasiIsdigit(c: Int): Int

public actual fun isdigit(c: CInt): CInt =
    wasiRustlangLibcWasiIsdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsgraph")
private external fun wasiRustlangLibcWasiIsgraph(c: Int): Int

public actual fun isgraph(c: CInt): CInt =
    wasiRustlangLibcWasiIsgraph(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIslower")
private external fun wasiRustlangLibcWasiIslower(c: Int): Int

public actual fun islower(c: CInt): CInt =
    wasiRustlangLibcWasiIslower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsprint")
private external fun wasiRustlangLibcWasiIsprint(c: Int): Int

public actual fun isprint(c: CInt): CInt =
    wasiRustlangLibcWasiIsprint(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIspunct")
private external fun wasiRustlangLibcWasiIspunct(c: Int): Int

public actual fun ispunct(c: CInt): CInt =
    wasiRustlangLibcWasiIspunct(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsspace")
private external fun wasiRustlangLibcWasiIsspace(c: Int): Int

public actual fun isspace(c: CInt): CInt =
    wasiRustlangLibcWasiIsspace(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsupper")
private external fun wasiRustlangLibcWasiIsupper(c: Int): Int

public actual fun isupper(c: CInt): CInt =
    wasiRustlangLibcWasiIsupper(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsxdigit")
private external fun wasiRustlangLibcWasiIsxdigit(c: Int): Int

public actual fun isxdigit(c: CInt): CInt =
    wasiRustlangLibcWasiIsxdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsblank")
private external fun wasiRustlangLibcWasiIsblank(c: Int): Int

public actual fun isblank(c: CInt): CInt =
    wasiRustlangLibcWasiIsblank(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiTolower")
private external fun wasiRustlangLibcWasiTolower(c: Int): Int

public actual fun tolower(c: CInt): CInt =
    wasiRustlangLibcWasiTolower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiToupper")
private external fun wasiRustlangLibcWasiToupper(c: Int): Int

public actual fun toupper(c: CInt): CInt =
    wasiRustlangLibcWasiToupper(c).toInt()

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSetvbuf")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSetbuf")
}

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFgets")

public actual fun atof(s: ConstPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtof")

public actual fun atoi(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtoi")

public actual fun atol(s: ConstPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtol")

public actual fun atoll(s: ConstPtr): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAtoll")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtod")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtof")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtol")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtoll")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtoul")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtoull")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcpy")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrncpy")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcat")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrncat")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcmp")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrncmp")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcoll")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrchr")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrrchr")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrspn")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcspn")

public actual fun strdup(cs: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrdup")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrndup")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrpbrk")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrstr")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcasecmp")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrncasecmp")

public actual fun strlen(cs: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrlen")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrnlen")

public actual fun strerror(n: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrerror")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrtok")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrxfrm")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemchr")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemcmp")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemcpy")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemmove")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemset")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFprintf")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPrintf")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSnprintf")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSprintf")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFscanf")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiScanf")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSscanf")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiGetcharUnlocked")
private external fun wasiRustlangLibcWasiGetcharUnlocked(): Int

public actual fun getcharUnlocked(): CInt =
    wasiRustlangLibcWasiGetcharUnlocked().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPutcharUnlocked")
private external fun wasiRustlangLibcWasiPutcharUnlocked(c: Int): Int

public actual fun putcharUnlocked(c: CInt): CInt =
    wasiRustlangLibcWasiPutcharUnlocked(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiShutdown")
private external fun wasiRustlangLibcWasiShutdown(socket: Int, how: Int): Int

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    wasiRustlangLibcWasiShutdown(socket, how).toInt()

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFstat")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMkdir")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStat")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFdopen")

public actual fun fileno(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFileno")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiOpen")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiCreat")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFcntl")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiOpendir")

public actual fun fdopendir(fd: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFdopendir")

public actual fun readdir(dirp: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiReaddir")

public actual fun closedir(dirp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiClosedir")

public actual fun rewinddir(dirp: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRewinddir")
}

public actual fun dirfd(dirp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiDirfd")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSeekdir")
}

public actual fun telldir(dirp: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiTelldir")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiOpenat")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFstatat")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLinkat")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMkdirat")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiReadlinkat")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRenameat")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSymlinkat")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUnlinkat")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiAccess")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiClose")
private external fun wasiRustlangLibcWasiClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiRustlangLibcWasiClose(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiFpathconf")
private external fun wasiRustlangLibcWasiFpathconf(filedes: Int, name: Int): Long

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    wasiRustlangLibcWasiFpathconf(filedes, name).toLong()

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetopt")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiIsatty")
private external fun wasiRustlangLibcWasiIsatty(fd: Int): Int

public actual fun isatty(fd: CInt): CInt =
    wasiRustlangLibcWasiIsatty(fd).toInt()

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLink")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiLseek")
private external fun wasiRustlangLibcWasiLseek(fd: Int, offset: Long, whence: Int): Long

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    wasiRustlangLibcWasiLseek(fd, offset, whence).toLong()

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPathconf")

public actual fun rmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRmdir")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiSleep")
private external fun wasiRustlangLibcWasiSleep(secs: Int): Int

public actual fun sleep(secs: CUint): CUint =
    wasiRustlangLibcWasiSleep(secs.toInt()).toUInt()

public actual fun unlink(c: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUnlink")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPread")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPwrite")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLstat")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiFsync")
private external fun wasiRustlangLibcWasiFsync(fd: Int): Int

public actual fun fsync(fd: CInt): CInt =
    wasiRustlangLibcWasiFsync(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiFdatasync")
private external fun wasiRustlangLibcWasiFdatasync(fd: Int): Int

public actual fun fdatasync(fd: CInt): CInt =
    wasiRustlangLibcWasiFdatasync(fd).toInt()

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSymlink")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiTruncate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiFtruncate")
private external fun wasiRustlangLibcWasiFtruncate(fd: Int, length: Long): Int

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    wasiRustlangLibcWasiFtruncate(fd, length).toInt()

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetrusage")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGettimeofday")

public actual fun times(buf: MutPtr): io.github.kotlinmania.rustlang.wasi.ClockT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiTimes")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrerrorR")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiUsleep")
private external fun wasiRustlangLibcWasiUsleep(secs: Int): Int

public actual fun usleep(secs: CUint): CInt =
    wasiRustlangLibcWasiUsleep(secs.toInt()).toInt()

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSend")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRecv")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPoll")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSetlocale")

public actual fun localeconv(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiLocaleconv")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiReadlink")

public actual fun timegm(tm: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiTimegm")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiSysconf")
private external fun wasiRustlangLibcWasiSysconf(name: Int): Long

public actual fun sysconf(name: CInt): CLong =
    wasiRustlangLibcWasiSysconf(name).toLong()

public actual fun ioctl(fd: CInt, request: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiIoctl")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFseeko")

public actual fun ftello(stream: MutPtr): OffT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFtello")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPosixFallocate")
private external fun wasiRustlangLibcWasiPosixFallocate(fd: Int, offset: Long, len: Long): Int

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    wasiRustlangLibcWasiPosixFallocate(fd, offset, len).toInt()

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiStrcasestr")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetline")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFaccessat")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWritev")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiReadv")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPwritev")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPreadv")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPosixFadvise")
private external fun wasiRustlangLibcWasiPosixFadvise(fd: Int, offset: Long, len: Long, advise: Int): Int

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    wasiRustlangLibcWasiPosixFadvise(fd, offset, len, advise).toInt()

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFutimens")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUtimensat")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetentropy")

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiMemrchr")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiAbs")
private external fun wasiRustlangLibcWasiAbs(i: Int): Int

public actual fun abs(i: CInt): CInt =
    wasiRustlangLibcWasiAbs(i).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiLabs")
private external fun wasiRustlangLibcWasiLabs(i: Long): Long

public actual fun labs(i: CLong): CLong =
    wasiRustlangLibcWasiLabs(i).toLong()

public actual fun duplocale(base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiDuplocale")

public actual fun freelocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiFreelocale")
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiNewlocale")

public actual fun uselocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiUselocale")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiSchedYield")
private external fun wasiRustlangLibcWasiSchedYield(): Int

public actual fun schedYield(): CInt =
    wasiRustlangLibcWasiSchedYield().toInt()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiGetcwd")

public actual fun chdir(dir: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiChdir")

public actual fun nlLanginfo(item: io.github.kotlinmania.rustlang.wasi.NlItem): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiNlLanginfo")

public actual fun nlLanginfoL(item: io.github.kotlinmania.rustlang.wasi.NlItem, loc: io.github.kotlinmania.rustlang.wasi.LocaleT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiNlLanginfoL")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiSelect")

public actual fun wasilibcRegisterPreopenedFd(fd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcRegisterPreopenedFd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiWasilibcFdRenumber")
private external fun wasiRustlangLibcWasiWasilibcFdRenumber(fd: Int, newfd: Int): Int

public actual fun wasilibcFdRenumber(fd: CInt, newfd: CInt): CInt =
    wasiRustlangLibcWasiWasilibcFdRenumber(fd, newfd).toInt()

public actual fun wasilibcUnlinkat(fd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcUnlinkat")

public actual fun wasilibcRmdirat(fd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcRmdirat")

public actual fun wasilibcFindRelpath(path: ConstPtr, absPrefix: MutPtr, relativePath: MutPtr, relativePathLen: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcFindRelpath")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiWasilibcTell")
private external fun wasiRustlangLibcWasiWasilibcTell(fd: Int): Long

public actual fun wasilibcTell(fd: CInt): OffT =
    wasiRustlangLibcWasiWasilibcTell(fd).toLong()

public actual fun wasilibcNocwdWasilibcUnlinkat(dirfd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat")

public actual fun wasilibcNocwdWasilibcRmdirat(dirfd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdWasilibcRmdirat")

public actual fun wasilibcNocwdLinkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdLinkat")

public actual fun wasilibcNocwdSymlinkat(target: ConstPtr, dirfd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdSymlinkat")

public actual fun wasilibcNocwdReadlinkat(dirfd: CInt, path: ConstPtr, buf: MutPtr, bufsize: ULong): Long =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdReadlinkat")

public actual fun wasilibcNocwdFaccessat(dirfd: CInt, path: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdFaccessat")

public actual fun wasilibcNocwdRenameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdRenameat")

public actual fun wasilibcNocwdOpenatNomode(dirfd: CInt, path: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdOpenatNomode")

public actual fun wasilibcNocwdFstatat(dirfd: CInt, path: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdFstatat")

public actual fun wasilibcNocwdMkdiratNomode(dirfd: CInt, path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdMkdiratNomode")

public actual fun wasilibcNocwdUtimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdUtimensat")

public actual fun wasilibcNocwdOpendirat(dirfd: CInt, path: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcNocwdOpendirat")

public actual fun wasilibcAccess(pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcAccess")

public actual fun wasilibcStat(pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcStat")

public actual fun wasilibcUtimens(pathname: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcUtimens")

public actual fun wasilibcLink(oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcLink")

public actual fun wasilibcLinkOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcLinkOldat")

public actual fun wasilibcLinkNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcLinkNewat")

public actual fun wasilibcRenameOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcRenameOldat")

public actual fun wasilibcRenameNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiWasilibcRenameNewat")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiArc4random")
private external fun wasiRustlangLibcWasiArc4random(): Int

public actual fun arc4random(): UInt =
    wasiRustlangLibcWasiArc4random().toUInt()

public actual fun arc4randomBuf(a: MutPtr, b: SizeT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiArc4randomBuf")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiArc4randomUniform")
private external fun wasiRustlangLibcWasiArc4randomUniform(a: Int): Int

public actual fun arc4randomUniform(a: UInt): UInt =
    wasiRustlangLibcWasiArc4randomUniform(a.toInt()).toUInt()

public actual fun errnoLocation(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiErrnoLocation")

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiChmod")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiFchmod")
private external fun wasiRustlangLibcWasiFchmod(fd: Int, mode: Int): Int

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    wasiRustlangLibcWasiFchmod(fd, mode.toInt()).toInt()

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiRealpath")

public actual fun pthreadSelf(): PthreadT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSelf")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCreate")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadEqual")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadJoin")

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrInit")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrDestroy")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrGetstacksize")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrSetstacksize")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, state: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrGetdetachstate")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrSetdetachstate")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrGetguardsize")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, b: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrSetguardsize")

public actual fun pthreadAttrGetstack(attr: ConstPtr, b: MutPtr, c: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrGetstack")

public actual fun pthreadAttrSetstack(attr: MutPtr, b: MutPtr, c: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadAttrSetstack")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadDetach")

public actual fun pthreadSetcancelstate(a: CInt, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSetcancelstate")

public actual fun pthreadSetcanceltype(a: CInt, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSetcanceltype")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPthreadTestcancel")
private external fun wasiRustlangLibcWasiPthreadTestcancel(): Unit

public actual fun pthreadTestcancel(): Unit =
    wasiRustlangLibcWasiPthreadTestcancel()

public actual fun pthreadOnce(a: MutPtr, b: AtexitACallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadOnce")

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadKeyCreate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWasiPthreadKeyDelete")
private external fun wasiRustlangLibcWasiPthreadKeyDelete(key: Int): Int

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    wasiRustlangLibcWasiPthreadKeyDelete(key.toInt()).toInt()

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadGetspecific")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSetspecific")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexInit")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexDestroy")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexLock")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexTrylock")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexUnlock")

public actual fun pthreadMutexTimedlock(lock: MutPtr, ts: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexTimedlock")

public actual fun pthreadMutexConsistent(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexConsistent")

public actual fun pthreadMutexGetprioceiling(lock: MutPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexGetprioceiling")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrInit")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrDestroy")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrSettype")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrGetprotocol")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrGetpshared")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrGetrobust")

public actual fun pthreadMutexattrGettype(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrGettype")

public actual fun pthreadMutexattrSetprotocol(attr: ConstPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrSetprotocol")

public actual fun pthreadMutexattrSetpshared(attr: ConstPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrSetpshared")

public actual fun pthreadMutexattrSetrobust(attr: ConstPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadMutexattrSetrobust")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondInit")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondWait")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondTimedwait")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondSignal")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondBroadcast")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondDestroy")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrInit")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrDestroy")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrSetclock")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrGetclock")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrSetpshared")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadCondattrGetpshared")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockInit")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockDestroy")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockRdlock")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockTryrdlock")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockWrlock")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockTrywrlock")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockUnlock")

public actual fun pthreadRwlockTimedrdlock(lock: MutPtr, at: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockTimedrdlock")

public actual fun pthreadRwlockTimedwrlock(lock: MutPtr, at: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockTimedwrlock")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockattrInit")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockattrDestroy")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockattrSetpshared")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadRwlockattrGetpshared")

public actual fun pthreadSpinInit(lock: MutPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSpinInit")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSpinDestroy")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSpinLock")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSpinTrylock")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadSpinUnlock")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierInit")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierDestroy")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierWait")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierattrInit")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierattrDestroy")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, b: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierattrSetpshared")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadBarrierattrGetpshared")

public actual fun pthreadGetattrNp(thread: PthreadT, attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWasiPthreadGetattrNp")
