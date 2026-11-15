@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.windows

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
import io.github.kotlinmania.rustlang.libc.*

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPrintf")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFprintf")

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSnprintf")

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSprintf")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsScanf")

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSscanf")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFscanf")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsalnum")
private external fun wasiLibcWindowsIsalnum(c: Int): Int

public actual fun isalnum(c: CInt): CInt =
    wasiLibcWindowsIsalnum(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsalpha")
private external fun wasiLibcWindowsIsalpha(c: Int): Int

public actual fun isalpha(c: CInt): CInt =
    wasiLibcWindowsIsalpha(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIscntrl")
private external fun wasiLibcWindowsIscntrl(c: Int): Int

public actual fun iscntrl(c: CInt): CInt =
    wasiLibcWindowsIscntrl(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsdigit")
private external fun wasiLibcWindowsIsdigit(c: Int): Int

public actual fun isdigit(c: CInt): CInt =
    wasiLibcWindowsIsdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsgraph")
private external fun wasiLibcWindowsIsgraph(c: Int): Int

public actual fun isgraph(c: CInt): CInt =
    wasiLibcWindowsIsgraph(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIslower")
private external fun wasiLibcWindowsIslower(c: Int): Int

public actual fun islower(c: CInt): CInt =
    wasiLibcWindowsIslower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsprint")
private external fun wasiLibcWindowsIsprint(c: Int): Int

public actual fun isprint(c: CInt): CInt =
    wasiLibcWindowsIsprint(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIspunct")
private external fun wasiLibcWindowsIspunct(c: Int): Int

public actual fun ispunct(c: CInt): CInt =
    wasiLibcWindowsIspunct(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsspace")
private external fun wasiLibcWindowsIsspace(c: Int): Int

public actual fun isspace(c: CInt): CInt =
    wasiLibcWindowsIsspace(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsupper")
private external fun wasiLibcWindowsIsupper(c: Int): Int

public actual fun isupper(c: CInt): CInt =
    wasiLibcWindowsIsupper(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsxdigit")
private external fun wasiLibcWindowsIsxdigit(c: Int): Int

public actual fun isxdigit(c: CInt): CInt =
    wasiLibcWindowsIsxdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsblank")
private external fun wasiLibcWindowsIsblank(c: Int): Int

public actual fun isblank(c: CInt): CInt =
    wasiLibcWindowsIsblank(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsTolower")
private external fun wasiLibcWindowsTolower(c: Int): Int

public actual fun tolower(c: CInt): CInt =
    wasiLibcWindowsTolower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsToupper")
private external fun wasiLibcWindowsToupper(c: Int): Int

public actual fun toupper(c: CInt): CInt =
    wasiLibcWindowsToupper(c).toInt()

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsQsort")

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsQsortS")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFopen")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFreopen")

public actual fun fflush(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFflush")

public actual fun fclose(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFclose")

public actual fun remove(filename: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRemove")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRename")

public actual fun tmpfile(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsTmpfile")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSetvbuf")

public actual fun setbuf(stream: MutPtr, buf: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSetbuf")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsGetchar")
private external fun wasiLibcWindowsGetchar(): Int

public actual fun getchar(): CInt =
    wasiLibcWindowsGetchar().toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsPutchar")
private external fun wasiLibcWindowsPutchar(c: Int): Int

public actual fun putchar(c: CInt): CInt =
    wasiLibcWindowsPutchar(c).toInt()

public actual fun fgetc(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFgetc")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFgets")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFputc")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFputs")

public actual fun puts(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPuts")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsUngetc")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFread")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFwrite")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFseek")

public actual fun ftell(stream: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFtell")

public actual fun rewind(stream: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRewind")

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFgetpos")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFsetpos")

public actual fun feof(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFeof")

public actual fun ferror(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFerror")

public actual fun perror(s: ConstPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPerror")

public actual fun atof(s: ConstPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAtof")

public actual fun atoi(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAtoi")

public actual fun atol(s: ConstPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAtol")

public actual fun atoll(s: ConstPtr): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAtoll")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtod")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtof")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtol")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtoll")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtoul")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtoull")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsCalloc")

public actual fun malloc(size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMalloc")

public actual fun msize(p: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMsize")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRealloc")

public actual fun free(p: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFree")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsAbort")
private external fun wasiLibcWindowsAbort(): Unit

public actual fun abort(): Nothing {
    wasiLibcWindowsAbort()
    error("C noreturn function libcWindowsAbort returned")
}

@kotlin.wasm.WasmImport("kmcore", "libcWindowsExit")
private external fun wasiLibcWindowsExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiLibcWindowsExit(status)
    error("C noreturn function libcWindowsExit returned")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAtexit")

public actual fun system(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSystem")

public actual fun getenv(s: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetenv")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrcpy")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrncpy")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrcat")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrncat")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrcmp")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrncmp")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrcoll")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrchr")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrrchr")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrspn")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrcspn")

public actual fun strdup(cs: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrdup")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrpbrk")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrstr")

public actual fun strlen(cs: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrlen")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrnlen")

public actual fun strerror(n: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrerror")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrtok")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStrxfrm")

public actual fun wcslen(buf: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWcslen")

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWcsnlen")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWcstombs")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMemchr")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMemcmp")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMemcpy")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMemmove")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMemset")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsAbs")
private external fun wasiLibcWindowsAbs(i: Int): Int

public actual fun abs(i: CInt): CInt =
    wasiLibcWindowsAbs(i).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsLabs")
private external fun wasiLibcWindowsLabs(i: Long): Long

public actual fun labs(i: CLong): CLong =
    wasiLibcWindowsLabs(i).toLong()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsRand")
private external fun wasiLibcWindowsRand(): Int

public actual fun rand(): CInt =
    wasiLibcWindowsRand().toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsSrand")
private external fun wasiLibcWindowsSrand(seed: Int): Unit

public actual fun srand(seed: CUint): Unit =
    wasiLibcWindowsSrand(seed.toInt())

@kotlin.wasm.WasmImport("kmcore", "libcWindowsSignal")
private external fun wasiLibcWindowsSignal(signum: Int, handler: Long): Long

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    wasiLibcWindowsSignal(signum, handler.toLong()).toULong()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsRaise")
private external fun wasiLibcWindowsRaise(signum: Int): Int

public actual fun raise(signum: CInt): CInt =
    wasiLibcWindowsRaise(signum).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsClock")
private external fun wasiLibcWindowsClock(): Int

public actual fun clock(): ClockT =
    wasiLibcWindowsClock().toInt()

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetDaylight")

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetDstbias")

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetTimezone")

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetTzname")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsTzset")
private external fun wasiLibcWindowsTzset(): Unit

public actual fun tzset(): Unit =
    wasiLibcWindowsTzset()

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsChmod")

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWchmod")

public actual fun mkdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsMkdir")

public actual fun wrmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWrmdir")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFstat")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsStat")

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWstat")

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWutime")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPopen")

public actual fun pclose(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPclose")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFdopen")

public actual fun fileno(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsFileno")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsOpen")

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWopen")

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsCreat")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAccess")

public actual fun chdir(dir: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsChdir")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsClose")
private external fun wasiLibcWindowsClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiLibcWindowsClose(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsDup")
private external fun wasiLibcWindowsDup(fd: Int): Int

public actual fun dup(fd: CInt): CInt =
    wasiLibcWindowsDup(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsDup2")
private external fun wasiLibcWindowsDup2(src: Int, dst: Int): Int

public actual fun dup2(src: CInt, dst: CInt): CInt =
    wasiLibcWindowsDup2(src, dst).toInt()

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecl")

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecl")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecle")

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecle")

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExeclp")

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexeclp")

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExeclpe")

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexeclpe")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecv")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecve")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecvp")

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsExecvpe")

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecv")

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecve")

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecvp")

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWexecvpe")

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetcwd")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsGetpid")
private external fun wasiLibcWindowsGetpid(): Int

public actual fun getpid(): CInt =
    wasiLibcWindowsGetpid().toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsIsatty")
private external fun wasiLibcWindowsIsatty(fd: Int): Int

public actual fun isatty(fd: CInt): CInt =
    wasiLibcWindowsIsatty(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsLseek")
private external fun wasiLibcWindowsLseek(fd: Int, offset: Long, origin: Int): Long

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    wasiLibcWindowsLseek(fd, offset, origin).toLong()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsLseek64")
private external fun wasiLibcWindowsLseek64(fd: Int, offset: Long, origin: Int): Long

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    wasiLibcWindowsLseek64(fd, offset, origin).toLong()

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPipe")

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRead")

public actual fun rmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRmdir")

public actual fun unlink(c: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsUnlink")

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWrite")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsCommit")
private external fun wasiLibcWindowsCommit(fd: Int): Int

public actual fun commit(fd: CInt): CInt =
    wasiLibcWindowsCommit(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsGetOsfhandle")
private external fun wasiLibcWindowsGetOsfhandle(fd: Int): Long

public actual fun getOsfhandle(fd: CInt): IntptrT =
    wasiLibcWindowsGetOsfhandle(fd).toLong()

@kotlin.wasm.WasmImport("kmcore", "libcWindowsOpenOsfhandle")
private external fun wasiLibcWindowsOpenOsfhandle(osfhandle: Long, flags: Int): Int

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    wasiLibcWindowsOpenOsfhandle(osfhandle, flags).toInt()

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSetlocale")

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWsetlocale")

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAlignedMalloc")

public actual fun alignedFree(ptr: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAlignedFree")

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAlignedRealloc")

public actual fun putenv(envstring: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPutenv")

public actual fun wputenv(envstring: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWputenv")

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsPutenvS")

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsWputenvS")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsListen")
private external fun wasiLibcWindowsListen(s: Long, backlog: Int): Int

public actual fun listen(s: Socket, backlog: CInt): CInt =
    wasiLibcWindowsListen(s.toLong(), backlog).toInt()

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsAccept")

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsBind")

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsConnect")

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetpeername")

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetsockname")

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsGetsockopt")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsRecvfrom")

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSendto")

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcWindowsSetsockopt")

@kotlin.wasm.WasmImport("kmcore", "libcWindowsSocket")
private external fun wasiLibcWindowsSocket(af: Int, socketType: Int, protocol: Int): Long

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    wasiLibcWindowsSocket(af, socketType, protocol).toULong()
