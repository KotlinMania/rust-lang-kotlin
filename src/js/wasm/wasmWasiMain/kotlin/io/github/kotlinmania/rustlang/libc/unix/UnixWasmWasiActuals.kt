@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.unix

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
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ClockT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.WcharT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual val IN6ADDR_LOOPBACK: In6Addr
    get() = throw UnsupportedOperationException("WasmWasi foreign static in6addr_loopback requires a host memory/aggregate ABI adapter")

public actual val IN6ADDR_ANY: In6Addr
    get() = throw UnsupportedOperationException("WasmWasi foreign static in6addr_any requires a host memory/aggregate ABI adapter")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsalnum")
private external fun wasiRustlangLibcUnixIsalnum(c: Int): Int

public actual fun isalnum(c: CInt): CInt =
    wasiRustlangLibcUnixIsalnum(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsalpha")
private external fun wasiRustlangLibcUnixIsalpha(c: Int): Int

public actual fun isalpha(c: CInt): CInt =
    wasiRustlangLibcUnixIsalpha(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIscntrl")
private external fun wasiRustlangLibcUnixIscntrl(c: Int): Int

public actual fun iscntrl(c: CInt): CInt =
    wasiRustlangLibcUnixIscntrl(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsdigit")
private external fun wasiRustlangLibcUnixIsdigit(c: Int): Int

public actual fun isdigit(c: CInt): CInt =
    wasiRustlangLibcUnixIsdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsgraph")
private external fun wasiRustlangLibcUnixIsgraph(c: Int): Int

public actual fun isgraph(c: CInt): CInt =
    wasiRustlangLibcUnixIsgraph(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIslower")
private external fun wasiRustlangLibcUnixIslower(c: Int): Int

public actual fun islower(c: CInt): CInt =
    wasiRustlangLibcUnixIslower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsprint")
private external fun wasiRustlangLibcUnixIsprint(c: Int): Int

public actual fun isprint(c: CInt): CInt =
    wasiRustlangLibcUnixIsprint(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIspunct")
private external fun wasiRustlangLibcUnixIspunct(c: Int): Int

public actual fun ispunct(c: CInt): CInt =
    wasiRustlangLibcUnixIspunct(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsspace")
private external fun wasiRustlangLibcUnixIsspace(c: Int): Int

public actual fun isspace(c: CInt): CInt =
    wasiRustlangLibcUnixIsspace(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsupper")
private external fun wasiRustlangLibcUnixIsupper(c: Int): Int

public actual fun isupper(c: CInt): CInt =
    wasiRustlangLibcUnixIsupper(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsxdigit")
private external fun wasiRustlangLibcUnixIsxdigit(c: Int): Int

public actual fun isxdigit(c: CInt): CInt =
    wasiRustlangLibcUnixIsxdigit(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsblank")
private external fun wasiRustlangLibcUnixIsblank(c: Int): Int

public actual fun isblank(c: CInt): CInt =
    wasiRustlangLibcUnixIsblank(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTolower")
private external fun wasiRustlangLibcUnixTolower(c: Int): Int

public actual fun tolower(c: CInt): CInt =
    wasiRustlangLibcUnixTolower(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixToupper")
private external fun wasiRustlangLibcUnixToupper(c: Int): Int

public actual fun toupper(c: CInt): CInt =
    wasiRustlangLibcUnixToupper(c).toInt()

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixQsort")
}

public actual fun bsearch(key: ConstPtr, base: ConstPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixBsearch")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFopen")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFreopen")

public actual fun fflush(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFflush")

public actual fun fclose(file: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFclose")

public actual fun remove(filename: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRemove")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRename")

public actual fun tmpfile(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTmpfile")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSetvbuf")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSetbuf")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetchar")
private external fun wasiRustlangLibcUnixGetchar(): Int

public actual fun getchar(): CInt =
    wasiRustlangLibcUnixGetchar().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixPutchar")
private external fun wasiRustlangLibcUnixPutchar(c: Int): Int

public actual fun putchar(c: CInt): CInt =
    wasiRustlangLibcUnixPutchar(c).toInt()

public actual fun fgetc(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFgetc")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFgets")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFputc")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFputs")

public actual fun puts(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPuts")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUngetc")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFread")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFwrite")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFseek")

public actual fun ftell(stream: MutPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFtell")

public actual fun rewind(stream: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRewind")
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFgetpos")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFsetpos")

public actual fun feof(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFeof")

public actual fun ferror(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFerror")

public actual fun clearerr(stream: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixClearerr")
}

public actual fun perror(s: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPerror")
}

public actual fun atof(s: ConstPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAtof")

public actual fun atoi(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAtoi")

public actual fun atol(s: ConstPtr): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAtol")

public actual fun atoll(s: ConstPtr): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAtoll")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtod")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtof")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtol")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtoll")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtoul")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtoull")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCalloc")

public actual fun malloc(size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMalloc")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRealloc")

public actual fun free(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFree")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixAbort")
private external fun wasiRustlangLibcUnixAbort(): Unit

public actual fun abort(): Nothing {
    wasiRustlangLibcUnixAbort()
    error("C noreturn function rustlangLibcUnixAbort returned")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixExit")
private external fun wasiRustlangLibcUnixExit(status: Int): Unit

public actual fun exit(status: CInt): Nothing {
    wasiRustlangLibcUnixExit(status)
    error("C noreturn function rustlangLibcUnixExit returned")
}

public actual fun system(s: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSystem")

public actual fun getenv(s: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetenv")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcpy")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrncpy")

public actual fun stpcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStpcpy")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcat")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrncat")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcmp")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrncmp")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcoll")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrchr")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrrchr")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrspn")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcspn")

public actual fun strdup(cs: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrdup")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrndup")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrpbrk")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrstr")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcasecmp")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrncasecmp")

public actual fun strlen(cs: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrlen")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrnlen")

public actual fun strerror(n: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrerror")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtok")

public actual fun strtokR(s: MutPtr, t: ConstPtr, p: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrtokR")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrxfrm")

public actual fun strsignal(sig: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrsignal")

public actual fun wcslen(buf: ConstPtr): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWcslen")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWcstombs")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemchr")

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWmemchr")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemcmp")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemcpy")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemmove")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemset")

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMemccpy")

public actual fun getpwnam(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetpwnam")

public actual fun getpwuid(uid: io.github.kotlinmania.rustlang.unix.UidT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetpwuid")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFprintf")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPrintf")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSnprintf")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSprintf")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFscanf")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixScanf")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSscanf")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetcharUnlocked")
private external fun wasiRustlangLibcUnixGetcharUnlocked(): Int

public actual fun getcharUnlocked(): CInt =
    wasiRustlangLibcUnixGetcharUnlocked().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixPutcharUnlocked")
private external fun wasiRustlangLibcUnixPutcharUnlocked(c: Int): Int

public actual fun putcharUnlocked(c: CInt): CInt =
    wasiRustlangLibcUnixPutcharUnlocked(c).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSocket")
private external fun wasiRustlangLibcUnixSocket(domain: Int, ty: Int, protocol: Int): Int

public actual fun socket(domain: CInt, ty: CInt, protocol: CInt): CInt =
    wasiRustlangLibcUnixSocket(domain, ty, protocol).toInt()

public actual fun connect(socket: CInt, address: ConstPtr, len: SocklenT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixConnect")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixListen")
private external fun wasiRustlangLibcUnixListen(socket: Int, backlog: Int): Int

public actual fun listen(socket: CInt, backlog: CInt): CInt =
    wasiRustlangLibcUnixListen(socket, backlog).toInt()

public actual fun accept(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAccept")

public actual fun getpeername(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetpeername")

public actual fun getsockname(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetsockname")

public actual fun setsockopt(socket: CInt, level: CInt, name: CInt, value: ConstPtr, optionLen: SocklenT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSetsockopt")

public actual fun socketpair(domain: CInt, type: CInt, protocol: CInt, socketVector: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSocketpair")

public actual fun sendto(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt, addr: ConstPtr, addrlen: SocklenT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSendto")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixShutdown")
private external fun wasiRustlangLibcUnixShutdown(socket: Int, how: Int): Int

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    wasiRustlangLibcUnixShutdown(socket, how).toInt()

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixChmod")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFchmod")
private external fun wasiRustlangLibcUnixFchmod(fd: Int, mode: Int): Int

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    wasiRustlangLibcUnixFchmod(fd, mode.toInt()).toInt()

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFstat")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMkdir")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStat")

public actual fun pclose(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPclose")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFdopen")

public actual fun fileno(stream: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFileno")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpen")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCreat")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFcntl")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpendir")

public actual fun fdopendir(fd: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFdopendir")

public actual fun readdir(dirp: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixReaddir")

public actual fun closedir(dirp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixClosedir")

public actual fun rewinddir(dirp: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRewinddir")
}

public actual fun fchmodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFchmodat")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFchown")
private external fun wasiRustlangLibcUnixFchown(fd: Int, owner: Int, group: Int): Int

public actual fun fchown(fd: CInt, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT): CInt =
    wasiRustlangLibcUnixFchown(fd, owner, group).toInt()

public actual fun fchownat(dirfd: CInt, pathname: ConstPtr, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFchownat")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpenat")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFstatat")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLinkat")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMkdirat")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRenameat")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSymlinkat")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUnlinkat")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAccess")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixAlarm")
private external fun wasiRustlangLibcUnixAlarm(seconds: Int): Int

public actual fun alarm(seconds: CUint): CUint =
    wasiRustlangLibcUnixAlarm(seconds.toInt()).toUInt()

public actual fun chdir(dir: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixChdir")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFchdir")
private external fun wasiRustlangLibcUnixFchdir(dirfd: Int): Int

public actual fun fchdir(dirfd: CInt): CInt =
    wasiRustlangLibcUnixFchdir(dirfd).toInt()

public actual fun chown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixChown")

public actual fun lchown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLchown")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixClose")
private external fun wasiRustlangLibcUnixClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiRustlangLibcUnixClose(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixDup")
private external fun wasiRustlangLibcUnixDup(fd: Int): Int

public actual fun dup(fd: CInt): CInt =
    wasiRustlangLibcUnixDup(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixDup2")
private external fun wasiRustlangLibcUnixDup2(src: Int, dst: Int): Int

public actual fun dup2(src: CInt, dst: CInt): CInt =
    wasiRustlangLibcUnixDup2(src, dst).toInt()

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExecl")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExecle")

public actual fun execlp(file: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExeclp")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExecv")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExecve")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixExecvp")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFork")
private external fun wasiRustlangLibcUnixFork(): Int

public actual fun fork(): PidT =
    wasiRustlangLibcUnixFork().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFpathconf")
private external fun wasiRustlangLibcUnixFpathconf(filedes: Int, name: Int): Long

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    wasiRustlangLibcUnixFpathconf(filedes, name).toLong()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetcwd")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetegid")
private external fun wasiRustlangLibcUnixGetegid(): Int

public actual fun getegid(): GidT =
    wasiRustlangLibcUnixGetegid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGeteuid")
private external fun wasiRustlangLibcUnixGeteuid(): Int

public actual fun geteuid(): UidT =
    wasiRustlangLibcUnixGeteuid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetgid")
private external fun wasiRustlangLibcUnixGetgid(): Int

public actual fun getgid(): GidT =
    wasiRustlangLibcUnixGetgid().toInt()

public actual fun getgroups(ngroupsMax: CInt, groups: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetgroups")

public actual fun getlogin(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetlogin")

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetopt")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetpgid")
private external fun wasiRustlangLibcUnixGetpgid(pid: Int): Int

public actual fun getpgid(pid: PidT): PidT =
    wasiRustlangLibcUnixGetpgid(pid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetpgrp")
private external fun wasiRustlangLibcUnixGetpgrp(): Int

public actual fun getpgrp(): PidT =
    wasiRustlangLibcUnixGetpgrp().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetpid")
private external fun wasiRustlangLibcUnixGetpid(): Int

public actual fun getpid(): PidT =
    wasiRustlangLibcUnixGetpid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetppid")
private external fun wasiRustlangLibcUnixGetppid(): Int

public actual fun getppid(): PidT =
    wasiRustlangLibcUnixGetppid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetuid")
private external fun wasiRustlangLibcUnixGetuid(): Int

public actual fun getuid(): UidT =
    wasiRustlangLibcUnixGetuid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixIsatty")
private external fun wasiRustlangLibcUnixIsatty(fd: Int): Int

public actual fun isatty(fd: CInt): CInt =
    wasiRustlangLibcUnixIsatty(fd).toInt()

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLink")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLseek")
private external fun wasiRustlangLibcUnixLseek(fd: Int, offset: Long, whence: Int): Long

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    wasiRustlangLibcUnixLseek(fd, offset, whence).toLong()

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPathconf")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixPause")
private external fun wasiRustlangLibcUnixPause(): Int

public actual fun pause(): CInt =
    wasiRustlangLibcUnixPause().toInt()

public actual fun pipe(fds: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPipe")

public actual fun posixMemalign(memptr: MutPtr, align: SizeT, size: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPosixMemalign")

public actual fun alignedAlloc(alignment: SizeT, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAlignedAlloc")

public actual fun read(fd: CInt, buf: MutPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRead")

public actual fun rmdir(path: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRmdir")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSeteuid")
private external fun wasiRustlangLibcUnixSeteuid(uid: Int): Int

public actual fun seteuid(uid: UidT): CInt =
    wasiRustlangLibcUnixSeteuid(uid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetegid")
private external fun wasiRustlangLibcUnixSetegid(gid: Int): Int

public actual fun setegid(gid: GidT): CInt =
    wasiRustlangLibcUnixSetegid(gid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetgid")
private external fun wasiRustlangLibcUnixSetgid(gid: Int): Int

public actual fun setgid(gid: GidT): CInt =
    wasiRustlangLibcUnixSetgid(gid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetpgid")
private external fun wasiRustlangLibcUnixSetpgid(pid: Int, pgid: Int): Int

public actual fun setpgid(pid: PidT, pgid: PidT): CInt =
    wasiRustlangLibcUnixSetpgid(pid, pgid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetsid")
private external fun wasiRustlangLibcUnixSetsid(): Int

public actual fun setsid(): PidT =
    wasiRustlangLibcUnixSetsid().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetuid")
private external fun wasiRustlangLibcUnixSetuid(uid: Int): Int

public actual fun setuid(uid: UidT): CInt =
    wasiRustlangLibcUnixSetuid(uid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetreuid")
private external fun wasiRustlangLibcUnixSetreuid(ruid: Int, euid: Int): Int

public actual fun setreuid(ruid: UidT, euid: UidT): CInt =
    wasiRustlangLibcUnixSetreuid(ruid, euid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetregid")
private external fun wasiRustlangLibcUnixSetregid(rgid: Int, egid: Int): Int

public actual fun setregid(rgid: GidT, egid: GidT): CInt =
    wasiRustlangLibcUnixSetregid(rgid, egid).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSleep")
private external fun wasiRustlangLibcUnixSleep(secs: Int): Int

public actual fun sleep(secs: CUint): CUint =
    wasiRustlangLibcUnixSleep(secs.toInt()).toUInt()

public actual fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixNanosleep")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcgetpgrp")
private external fun wasiRustlangLibcUnixTcgetpgrp(fd: Int): Int

public actual fun tcgetpgrp(fd: CInt): PidT =
    wasiRustlangLibcUnixTcgetpgrp(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcsetpgrp")
private external fun wasiRustlangLibcUnixTcsetpgrp(fd: Int, pgrp: Int): Int

public actual fun tcsetpgrp(fd: CInt, pgrp: io.github.kotlinmania.rustlang.unix.PidT): CInt =
    wasiRustlangLibcUnixTcsetpgrp(fd, pgrp).toInt()

public actual fun ttyname(fd: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTtyname")

public actual fun ttynameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTtynameR")

public actual fun unlink(c: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUnlink")

public actual fun wait(status: MutPtr): PidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWait")

public actual fun waitpid(pid: PidT, status: MutPtr, options: CInt): PidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWaitpid")

public actual fun write(fd: CInt, buf: ConstPtr, count: SizeT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixWrite")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPread")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPwrite")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixUmask")
private external fun wasiRustlangLibcUnixUmask(mask: Int): Int

public actual fun umask(mask: ModeT): ModeT =
    wasiRustlangLibcUnixUmask(mask.toInt()).toUInt()

public actual fun utime(file: ConstPtr, buf: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUtime")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixKill")
private external fun wasiRustlangLibcUnixKill(pid: Int, sig: Int): Int

public actual fun kill(pid: PidT, sig: CInt): CInt =
    wasiRustlangLibcUnixKill(pid, sig).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixKillpg")
private external fun wasiRustlangLibcUnixKillpg(pgrp: Int, sig: Int): Int

public actual fun killpg(pgrp: PidT, sig: CInt): CInt =
    wasiRustlangLibcUnixKillpg(pgrp, sig).toInt()

public actual fun mlock(addr: ConstPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMlock")

public actual fun munlock(addr: ConstPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMunlock")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixMlockall")
private external fun wasiRustlangLibcUnixMlockall(flags: Int): Int

public actual fun mlockall(flags: CInt): CInt =
    wasiRustlangLibcUnixMlockall(flags).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixMunlockall")
private external fun wasiRustlangLibcUnixMunlockall(): Int

public actual fun munlockall(): CInt =
    wasiRustlangLibcUnixMunlockall().toInt()

public actual fun mmap(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: OffT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMmap")

public actual fun munmap(addr: MutPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMunmap")

public actual fun ifNametoindex(ifname: ConstPtr): CUint =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixIfNametoindex")

public actual fun ifIndextoname(ifindex: CUint, ifname: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixIfIndextoname")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLstat")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFsync")
private external fun wasiRustlangLibcUnixFsync(fd: Int): Int

public actual fun fsync(fd: CInt): CInt =
    wasiRustlangLibcUnixFsync(fd).toInt()

public actual fun setenv(name: ConstPtr, value: ConstPtr, overwrite: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSetenv")

public actual fun unsetenv(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUnsetenv")

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSymlink")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTruncate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFtruncate")
private external fun wasiRustlangLibcUnixFtruncate(fd: Int, length: Long): Int

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    wasiRustlangLibcUnixFtruncate(fd, length).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSignal")
private external fun wasiRustlangLibcUnixSignal(signum: Int, handler: Long): Long

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    wasiRustlangLibcUnixSignal(signum, handler.toLong()).toULong()

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetrusage")

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRealpath")

public actual fun times(buf: MutPtr): ClockT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTimes")

public actual fun pthreadSelf(): PthreadT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadSelf")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadEqual")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadJoin")

public actual fun pthreadExit(value: MutPtr): Nothing =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadExit")

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadAttrInit")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadAttrDestroy")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadAttrGetstacksize")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadAttrSetstacksize")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadAttrSetdetachstate")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadDetach")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSchedYield")
private external fun wasiRustlangLibcUnixSchedYield(): Int

public actual fun schedYield(): CInt =
    wasiRustlangLibcUnixSchedYield().toInt()

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadKeyCreate")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixPthreadKeyDelete")
private external fun wasiRustlangLibcUnixPthreadKeyDelete(key: Int): Int

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    wasiRustlangLibcUnixPthreadKeyDelete(key.toInt()).toInt()

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadGetspecific")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadSetspecific")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexInit")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexDestroy")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexLock")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexTrylock")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexUnlock")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexattrInit")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexattrDestroy")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadMutexattrSettype")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondInit")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondWait")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondTimedwait")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondSignal")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondBroadcast")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondDestroy")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondattrInit")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadCondattrDestroy")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockInit")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockDestroy")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockRdlock")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockTryrdlock")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockWrlock")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockTrywrlock")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockUnlock")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockattrInit")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPthreadRwlockattrDestroy")

public actual fun getsockopt(sockfd: CInt, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetsockopt")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixRaise")
private external fun wasiRustlangLibcUnixRaise(signum: Int): Int

public actual fun raise(signum: CInt): CInt =
    wasiRustlangLibcUnixRaise(signum).toInt()

public actual fun utimes(filename: ConstPtr, times: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixUtimes")

public actual fun dlopen(filename: ConstPtr, flag: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixDlopen")

public actual fun dlerror(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixDlerror")

public actual fun dlsym(handle: MutPtr, symbol: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixDlsym")

public actual fun dlclose(handle: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixDlclose")

public actual fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetaddrinfo")

public actual fun freeaddrinfo(res: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFreeaddrinfo")
}

public actual fun hstrerror(errcode: CInt): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixHstrerror")

public actual fun gaiStrerror(errcode: CInt): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGaiStrerror")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixResInit")
private external fun wasiRustlangLibcUnixResInit(): Int

public actual fun resInit(): CInt =
    wasiRustlangLibcUnixResInit().toInt()

public actual fun gmtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGmtimeR")

public actual fun localtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLocaltimeR")

public actual fun mktime(tm: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMktime")

public actual fun time(time: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTime")

public actual fun gmtime(timeP: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGmtime")

public actual fun localtime(timeP: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLocaltime")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixDifftime")
private external fun wasiRustlangLibcUnixDifftime(time1: Long, time0: Long): Double

public actual fun difftime(time1: TimeT, time0: TimeT): CDouble =
    wasiRustlangLibcUnixDifftime(time1, time0).toDouble()

public actual fun timegm(tm: MutPtr): TimeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTimegm")

public actual fun mknod(pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMknod")

public actual fun gethostname(name: MutPtr, len: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGethostname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixEndservent")
private external fun wasiRustlangLibcUnixEndservent(): Unit

public actual fun endservent(): Unit =
    wasiRustlangLibcUnixEndservent()

public actual fun getservbyname(name: ConstPtr, proto: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetservbyname")

public actual fun getservbyport(port: CInt, proto: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetservbyport")

public actual fun getservent(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetservent")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetservent")
private external fun wasiRustlangLibcUnixSetservent(stayopen: Int): Unit

public actual fun setservent(stayopen: CInt): Unit =
    wasiRustlangLibcUnixSetservent(stayopen)

public actual fun getprotobyname(name: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetprotobyname")

public actual fun getprotobynumber(proto: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetprotobynumber")

public actual fun chroot(name: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixChroot")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixUsleep")
private external fun wasiRustlangLibcUnixUsleep(secs: Int): Int

public actual fun usleep(secs: CUint): CInt =
    wasiRustlangLibcUnixUsleep(secs.toInt()).toInt()

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSend")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixRecv")

public actual fun putenv(string: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPutenv")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPoll")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSelect")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSetlocale")

public actual fun localeconv(): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixLocaleconv")

public actual fun semWait(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSemWait")

public actual fun semTrywait(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSemTrywait")

public actual fun semPost(sem: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSemPost")

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStatvfs")

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFstatvfs")

public actual fun sigemptyset(set: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigemptyset")

public actual fun sigaddset(set: MutPtr, signum: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigaddset")

public actual fun sigfillset(set: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigfillset")

public actual fun sigdelset(set: MutPtr, signum: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigdelset")

public actual fun sigismember(set: ConstPtr, signum: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigismember")

public actual fun sigprocmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigprocmask")

public actual fun sigpending(set: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigpending")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSysconf")
private external fun wasiRustlangLibcUnixSysconf(name: Int): Long

public actual fun sysconf(name: CInt): CLong =
    wasiRustlangLibcUnixSysconf(name).toLong()

public actual fun mkfifo(path: ConstPtr, mode: ModeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMkfifo")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFseeko")

public actual fun ftello(stream: MutPtr): OffT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFtello")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcdrain")
private external fun wasiRustlangLibcUnixTcdrain(fd: Int): Int

public actual fun tcdrain(fd: CInt): CInt =
    wasiRustlangLibcUnixTcdrain(fd).toInt()

public actual fun cfgetispeed(termios: ConstPtr): SpeedT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfgetispeed")

public actual fun cfgetospeed(termios: ConstPtr): SpeedT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfgetospeed")

public actual fun cfsetispeed(termios: MutPtr, speed: SpeedT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfsetispeed")

public actual fun cfsetospeed(termios: MutPtr, speed: SpeedT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfsetospeed")

public actual fun tcgetattr(fd: CInt, termios: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTcgetattr")

public actual fun tcsetattr(fd: CInt, optionalActions: CInt, termios: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTcsetattr")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcflow")
private external fun wasiRustlangLibcUnixTcflow(fd: Int, action: Int): Int

public actual fun tcflow(fd: CInt, action: CInt): CInt =
    wasiRustlangLibcUnixTcflow(fd, action).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcflush")
private external fun wasiRustlangLibcUnixTcflush(fd: Int, action: Int): Int

public actual fun tcflush(fd: CInt, action: CInt): CInt =
    wasiRustlangLibcUnixTcflush(fd, action).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcgetsid")
private external fun wasiRustlangLibcUnixTcgetsid(fd: Int): Int

public actual fun tcgetsid(fd: CInt): io.github.kotlinmania.rustlang.unix.PidT =
    wasiRustlangLibcUnixTcgetsid(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixTcsendbreak")
private external fun wasiRustlangLibcUnixTcsendbreak(fd: Int, duration: Int): Int

public actual fun tcsendbreak(fd: CInt, duration: CInt): CInt =
    wasiRustlangLibcUnixTcsendbreak(fd, duration).toInt()

public actual fun mkstemp(template: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMkstemp")

public actual fun mkdtemp(template: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixMkdtemp")

public actual fun tmpnam(ptr: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixTmpnam")

public actual fun openlog(ident: ConstPtr, logopt: CInt, facility: CInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpenlog")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixCloselog")
private external fun wasiRustlangLibcUnixCloselog(): Unit

public actual fun closelog(): Unit =
    wasiRustlangLibcUnixCloselog()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixSetlogmask")
private external fun wasiRustlangLibcUnixSetlogmask(maskpri: Int): Int

public actual fun setlogmask(maskpri: CInt): CInt =
    wasiRustlangLibcUnixSetlogmask(maskpri).toInt()

public actual fun syslog(priority: CInt, message: ConstPtr, vararg variadicArgs: Long) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSyslog")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixNice")
private external fun wasiRustlangLibcUnixNice(incr: Int): Int

public actual fun nice(incr: CInt): CInt =
    wasiRustlangLibcUnixNice(incr).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGrantpt")
private external fun wasiRustlangLibcUnixGrantpt(fd: Int): Int

public actual fun grantpt(fd: CInt): CInt =
    wasiRustlangLibcUnixGrantpt(fd).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixPosixOpenpt")
private external fun wasiRustlangLibcUnixPosixOpenpt(flags: Int): Int

public actual fun posixOpenpt(flags: CInt): CInt =
    wasiRustlangLibcUnixPosixOpenpt(flags).toInt()

public actual fun ptsname(fd: CInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPtsname")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixUnlockpt")
private external fun wasiRustlangLibcUnixUnlockpt(fd: Int): Int

public actual fun unlockpt(fd: CInt): CInt =
    wasiRustlangLibcUnixUnlockpt(fd).toInt()

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStrcasestr")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixGetline")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixLockf")
private external fun wasiRustlangLibcUnixLockf(fd: Int, cmd: Int, len: Long): Int

public actual fun lockf(fd: CInt, cmd: CInt, len: OffT): CInt =
    wasiRustlangLibcUnixLockf(fd, cmd, len).toInt()

public actual fun adjtime(delta: ConstPtr, olddelta: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAdjtime")

public actual fun stpncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixStpncpy")

public actual fun sigqueue(pid: PidT, sig: CInt, value: io.github.kotlinmania.rustlang.unix.Sigval): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigqueue")

public actual fun confstr(name: CInt, buf: MutPtr, len: SizeT): SizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixConfstr")

public actual fun dladdr(addr: ConstPtr, info: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixDladdr")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixFlock")
private external fun wasiRustlangLibcUnixFlock(fd: Int, operation: Int): Int

public actual fun flock(fd: CInt, operation: CInt): CInt =
    wasiRustlangLibcUnixFlock(fd, operation).toInt()

public actual fun openWmemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpenWmemstream")

@kotlin.wasm.WasmImport("kmcore", "rustlangLibcUnixGetsid")
private external fun wasiRustlangLibcUnixGetsid(pid: Int): Int

public actual fun getsid(pid: PidT): PidT =
    wasiRustlangLibcUnixGetsid(pid).toInt()

public actual fun readdirR(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixReaddirR")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixReadlinkat")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixReadlink")

public actual fun pselect(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr, sigmask: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixPselect")

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixSigaction")

public actual fun fmemopen(buf: MutPtr, size: SizeT, mode: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFmemopen")

public actual fun openMemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixOpenMemstream")

public actual fun atexit(cb: AtexitCbCallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixAtexit")

public actual fun cfmakeraw(termios: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfmakeraw")
}

public actual fun cfsetspeed(termios: MutPtr, speed: SpeedT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixCfsetspeed")

public actual fun fnmatch(pattern: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcUnixFnmatch")
