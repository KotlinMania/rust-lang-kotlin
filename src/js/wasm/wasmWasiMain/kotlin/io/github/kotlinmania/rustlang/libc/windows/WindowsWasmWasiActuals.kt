@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.windows

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
public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPrintf")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFprintf")

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSnprintf")

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSprintf")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsScanf")

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSscanf")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFscanf")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsalnum")
private external fun wasiRustlangLibcWindowsIsalnum(c: Int): Int

public actual fun isalnum(c: CInt): CInt =
    wasiRustlangLibcWindowsIsalnum(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsalpha")
private external fun wasiRustlangLibcWindowsIsalpha(c: Int): Int

public actual fun isalpha(c: CInt): CInt =
    wasiRustlangLibcWindowsIsalpha(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIscntrl")
private external fun wasiRustlangLibcWindowsIscntrl(c: Int): Int

public actual fun iscntrl(c: CInt): CInt =
    wasiRustlangLibcWindowsIscntrl(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsdigit")
private external fun wasiRustlangLibcWindowsIsdigit(c: Int): Int

public actual fun isdigit(c: CInt): CInt =
    wasiRustlangLibcWindowsIsdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsgraph")
private external fun wasiRustlangLibcWindowsIsgraph(c: Int): Int

public actual fun isgraph(c: CInt): CInt =
    wasiRustlangLibcWindowsIsgraph(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIslower")
private external fun wasiRustlangLibcWindowsIslower(c: Int): Int

public actual fun islower(c: CInt): CInt =
    wasiRustlangLibcWindowsIslower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsprint")
private external fun wasiRustlangLibcWindowsIsprint(c: Int): Int

public actual fun isprint(c: CInt): CInt =
    wasiRustlangLibcWindowsIsprint(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIspunct")
private external fun wasiRustlangLibcWindowsIspunct(c: Int): Int

public actual fun ispunct(c: CInt): CInt =
    wasiRustlangLibcWindowsIspunct(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsspace")
private external fun wasiRustlangLibcWindowsIsspace(c: Int): Int

public actual fun isspace(c: CInt): CInt =
    wasiRustlangLibcWindowsIsspace(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsupper")
private external fun wasiRustlangLibcWindowsIsupper(c: Int): Int

public actual fun isupper(c: CInt): CInt =
    wasiRustlangLibcWindowsIsupper(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsxdigit")
private external fun wasiRustlangLibcWindowsIsxdigit(c: Int): Int

public actual fun isxdigit(c: CInt): CInt =
    wasiRustlangLibcWindowsIsxdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsblank")
private external fun wasiRustlangLibcWindowsIsblank(c: Int): Int

public actual fun isblank(c: CInt): CInt =
    wasiRustlangLibcWindowsIsblank(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsTolower")
private external fun wasiRustlangLibcWindowsTolower(c: Int): Int

public actual fun tolower(c: CInt): CInt =
    wasiRustlangLibcWindowsTolower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsToupper")
private external fun wasiRustlangLibcWindowsToupper(c: Int): Int

public actual fun toupper(c: CInt): CInt =
    wasiRustlangLibcWindowsToupper(c).toInt()

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsQsort")
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsQsortS")
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFopen")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFreopen")

public actual fun fflush(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFflush")

public actual fun fclose(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFclose")

public actual fun remove(filename: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRemove")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRename")

public actual fun tmpfile(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsTmpfile")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSetvbuf")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSetbuf")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsGetchar")
private external fun wasiRustlangLibcWindowsGetchar(): Int

public actual fun getchar(): CInt =
    wasiRustlangLibcWindowsGetchar().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsPutchar")
private external fun wasiRustlangLibcWindowsPutchar(c: Int): Int

public actual fun putchar(c: CInt): CInt =
    wasiRustlangLibcWindowsPutchar(c).toInt()

public actual fun fgetc(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFgetc")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFgets")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFputc")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFputs")

public actual fun puts(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPuts")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsUngetc")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFread")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFwrite")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFseek")

public actual fun ftell(stream: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFtell")

public actual fun rewind(stream: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRewind")
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFgetpos")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFsetpos")

public actual fun feof(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFeof")

public actual fun ferror(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFerror")

public actual fun perror(s: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPerror")
}

public actual fun atof(s: ConstPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAtof")

public actual fun atoi(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAtoi")

public actual fun atol(s: ConstPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAtol")

public actual fun atoll(s: ConstPtr): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAtoll")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtod")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtof")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtol")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtoll")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtoul")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtoull")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsCalloc")

public actual fun malloc(size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMalloc")

public actual fun msize(p: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMsize")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRealloc")

public actual fun free(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFree")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsAbort")
private external fun wasiRustlangLibcWindowsAbort(): Unit

public actual fun abort(): Nothing {
    wasiRustlangLibcWindowsAbort()
    error("C noreturn function rustlangLibcWindowsAbort returned")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsExit")
private external fun wasiRustlangLibcWindowsExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiRustlangLibcWindowsExit(status)
    error("C noreturn function rustlangLibcWindowsExit returned")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAtexit")

public actual fun system(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSystem")

public actual fun getenv(s: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetenv")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrcpy")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrncpy")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrcat")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrncat")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrcmp")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrncmp")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrcoll")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrchr")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrrchr")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrspn")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrcspn")

public actual fun strdup(cs: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrdup")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrpbrk")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrstr")

public actual fun strlen(cs: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrlen")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrnlen")

public actual fun strerror(n: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrerror")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrtok")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStrxfrm")

public actual fun wcslen(buf: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWcslen")

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWcsnlen")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWcstombs")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMemchr")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMemcmp")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMemcpy")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMemmove")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMemset")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsAbs")
private external fun wasiRustlangLibcWindowsAbs(i: Int): Int

public actual fun abs(i: CInt): CInt =
    wasiRustlangLibcWindowsAbs(i).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsLabs")
private external fun wasiRustlangLibcWindowsLabs(i: Long): Long

public actual fun labs(i: CLong): CLong =
    wasiRustlangLibcWindowsLabs(i).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsRand")
private external fun wasiRustlangLibcWindowsRand(): Int

public actual fun rand(): CInt =
    wasiRustlangLibcWindowsRand().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsSrand")
private external fun wasiRustlangLibcWindowsSrand(seed: Int): Unit

public actual fun srand(seed: CUint): Unit =
    wasiRustlangLibcWindowsSrand(seed.toInt())

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsSignal")
private external fun wasiRustlangLibcWindowsSignal(signum: Int, handler: Long): Long

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    wasiRustlangLibcWindowsSignal(signum, handler.toLong()).toULong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsRaise")
private external fun wasiRustlangLibcWindowsRaise(signum: Int): Int

public actual fun raise(signum: CInt): CInt =
    wasiRustlangLibcWindowsRaise(signum).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsClock")
private external fun wasiRustlangLibcWindowsClock(): Int

public actual fun clock(): ClockT =
    wasiRustlangLibcWindowsClock().toInt()

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetDaylight")

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetDstbias")

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetTimezone")

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetTzname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsTzset")
private external fun wasiRustlangLibcWindowsTzset(): Unit

public actual fun tzset(): Unit =
    wasiRustlangLibcWindowsTzset()

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsChmod")

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWchmod")

public actual fun mkdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsMkdir")

public actual fun wrmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWrmdir")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFstat")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsStat")

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWstat")

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWutime")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPopen")

public actual fun pclose(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPclose")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFdopen")

public actual fun fileno(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsFileno")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsOpen")

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWopen")

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsCreat")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAccess")

public actual fun chdir(dir: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsChdir")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsClose")
private external fun wasiRustlangLibcWindowsClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiRustlangLibcWindowsClose(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsDup")
private external fun wasiRustlangLibcWindowsDup(fd: Int): Int

public actual fun dup(fd: CInt): CInt =
    wasiRustlangLibcWindowsDup(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsDup2")
private external fun wasiRustlangLibcWindowsDup2(src: Int, dst: Int): Int

public actual fun dup2(src: CInt, dst: CInt): CInt =
    wasiRustlangLibcWindowsDup2(src, dst).toInt()

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecl")

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecl")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecle")

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecle")

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExeclp")

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexeclp")

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExeclpe")

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexeclpe")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecv")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecve")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecvp")

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsExecvpe")

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecv")

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecve")

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecvp")

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWexecvpe")

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetcwd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsGetpid")
private external fun wasiRustlangLibcWindowsGetpid(): Int

public actual fun getpid(): CInt =
    wasiRustlangLibcWindowsGetpid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsIsatty")
private external fun wasiRustlangLibcWindowsIsatty(fd: Int): Int

public actual fun isatty(fd: CInt): CInt =
    wasiRustlangLibcWindowsIsatty(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsLseek")
private external fun wasiRustlangLibcWindowsLseek(fd: Int, offset: Long, origin: Int): Long

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    wasiRustlangLibcWindowsLseek(fd, offset, origin).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsLseek64")
private external fun wasiRustlangLibcWindowsLseek64(fd: Int, offset: Long, origin: Int): Long

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    wasiRustlangLibcWindowsLseek64(fd, offset, origin).toLong()

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPipe")

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRead")

public actual fun rmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRmdir")

public actual fun unlink(c: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsUnlink")

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWrite")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsCommit")
private external fun wasiRustlangLibcWindowsCommit(fd: Int): Int

public actual fun commit(fd: CInt): CInt =
    wasiRustlangLibcWindowsCommit(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsGetOsfhandle")
private external fun wasiRustlangLibcWindowsGetOsfhandle(fd: Int): Long

public actual fun getOsfhandle(fd: CInt): IntptrT =
    wasiRustlangLibcWindowsGetOsfhandle(fd).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsOpenOsfhandle")
private external fun wasiRustlangLibcWindowsOpenOsfhandle(osfhandle: Long, flags: Int): Int

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    wasiRustlangLibcWindowsOpenOsfhandle(osfhandle, flags).toInt()

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSetlocale")

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWsetlocale")

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAlignedMalloc")

public actual fun alignedFree(ptr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAlignedFree")
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAlignedRealloc")

public actual fun putenv(envstring: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPutenv")

public actual fun wputenv(envstring: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWputenv")

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsPutenvS")

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsWputenvS")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsListen")
private external fun wasiRustlangLibcWindowsListen(s: Long, backlog: Int): Int

public actual fun listen(s: Socket, backlog: CInt): CInt =
    wasiRustlangLibcWindowsListen(s.toLong(), backlog).toInt()

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsAccept")

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsBind")

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsConnect")

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetpeername")

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetsockname")

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsGetsockopt")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsRecvfrom")

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSendto")

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcWindowsSetsockopt")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcWindowsSocket")
private external fun wasiRustlangLibcWindowsSocket(af: Int, socketType: Int, protocol: Int): Long

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    wasiRustlangLibcWindowsSocket(af, socketType, protocol).toULong()
