@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcWasiNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual var ENVIRON: MutPtr
    get() = if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiGetENVIRON() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcWasiNapiAddon != null) {
            libcWasiNapiAddon.rustlangLibcWasiSetENVIRON(value.rawAddress.toString())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual val CLOCK_MONOTONIC1: UByte
    get() = if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetCLOCKMONOTONIC1() as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val CLOCK_REALTIME1: UByte
    get() = if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetCLOCKREALTIME1() as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exit(code: CInt): Nothing {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiExit(code)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun alignedAlloc(a: SizeT, b: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiAlignedAlloc(a.toString(), b.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(amt: SizeT, amt2: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiCalloc(amt.toString(), amt2.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(ptr: MutPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiGetenv(s.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(amt: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMalloc(amt.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiMallocUsableSize(ptr.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: IntptrT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiSbrk(increment.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRand() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, ptr: MutPtr, size: SizeT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRead(fd, ptr.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(ptr: MutPtr, amt: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiRealloc(ptr.rawAddress.toString(), amt.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setenv(k: ConstPtr, v: ConstPtr, a: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSetenv(k.rawAddress.toString(), v.rawAddress.toString(), a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unsetenv(k: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUnsetenv(k.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearenv(): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiClearenv() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, ptr: ConstPtr, size: SizeT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWrite(fd, ptr.rawAddress.toString(), size.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(a: ConstPtr, b: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiFopen(a.rawAddress.toString(), b.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(a: ConstPtr, b: ConstPtr, f: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiFreopen(a.rawAddress.toString(), b.rawAddress.toString(), f.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFclose(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(a: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRemove(a.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(a: ConstPtr, b: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRename(a.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFeof(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFerror(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFflush(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearerr(f: MutPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiClearerr(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fseek(f: MutPtr, b: CLong, c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFseek(f.rawAddress.toString(), b.toString(), c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(f: MutPtr): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFtell(f.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(f: MutPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiRewind(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(f: MutPtr, pos: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFgetpos(f.rawAddress.toString(), pos.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(f: MutPtr, pos: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFsetpos(f.rawAddress.toString(), pos.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(buf: MutPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFread(buf.rawAddress.toString(), a.toString(), b.toString(), f.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(buf: ConstPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFwrite(buf.rawAddress.toString(), a.toString(), b.toString(), f.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFgetc(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getc(f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetc(f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getchar(): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetchar() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUngetc(a, f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFputc(a, f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPutc(a, f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(a: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPutchar(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(a: ConstPtr, f: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFputs(a.rawAddress.toString(), f.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(a: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPuts(a.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun perror(a: ConstPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiPerror(a.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun srand(a: CUint) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiSrand(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atexit(a: AtexitACallback): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtexit(0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atQuickExit(a: AtexitACallback): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtQuickExit(0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quickExit(a: CInt): Nothing {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiQuickExit(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function quickExit")
}

public actual fun posixMemalign(a: MutPtr, b: SizeT, c: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPosixMemalign(a.rawAddress.toString(), b.toString(), c.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun randR(a: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRandR(a.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun random(): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRandom() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srandom(a: CUint) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiSrandom(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun putenv(a: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPutenv(a.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clock(): ClockT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiClock() as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun time(a: MutPtr): TimeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTime(a.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun difftime(a: TimeT, b: TimeT): CDouble =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiDifftime(a.toString(), b.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mktime(a: MutPtr): TimeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiMktime(a.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(a: MutPtr, b: SizeT, c: ConstPtr, d: ConstPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrftime(a.rawAddress.toString(), b.toString(), c.rawAddress.toString(), d.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtime(a: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiGmtime(a.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiGmtimeR(a.rawAddress.toString(), b.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtime(a: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiLocaltime(a.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiLocaltimeR(a.rawAddress.toString(), b.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiAsctimeR(a.rawAddress.toString(), b.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiCtimeR(a.rawAddress.toString(), b.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nanosleep(a: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiNanosleep(a.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(a: ClockidT, a2: CInt, b: ConstPtr, c: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalnum(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsalnum(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsalpha(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIscntrl(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsgraph(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIslower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsprint(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIspunct(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsspace(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsxdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsblank(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTolower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiToupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atof(s: ConstPtr): CDouble =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtof(s.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtoi(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtol(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAtoll(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtof(s.rawAddress.toString(), endp.rawAddress.toString()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrcat(s.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrdup(cs.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrndup(cs.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrcasecmp(s1.rawAddress.toString(), s2.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrncasecmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrlen(cs.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrnlen(cs.rawAddress.toString(), maxlen.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrerror(n) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrtok(s.rawAddress.toString(), t.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMemchr(cx.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMemset(dest.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSnprintf(s.rawAddress.toString(), n.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSprintf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSscanf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcharUnlocked(): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetcharUnlocked() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putcharUnlocked(c: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPutcharUnlocked(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiShutdown(socket, how) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFstat(fildes, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiMkdir(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiFdopen(fd, mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFileno(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiCreat(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiOpendir(dirname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopendir(fd: CInt): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiFdopendir(fd) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir(dirp: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiReaddir(dirp.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closedir(dirp: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiClosedir(dirp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewinddir(dirp: MutPtr) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiRewinddir(dirp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiDirfd(dirp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTelldir(dirp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiOpenat(dirfd, pathname.rawAddress.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFstatat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiMkdirat(dirfd, pathname.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiReadlinkat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), bufsiz.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSymlinkat(target.rawAddress.toString(), newdirfd, linkpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUnlinkat(dirfd, pathname.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAccess(path.rawAddress.toString(), amode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiClose(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFpathconf(filedes, name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetopt(argc, argv.rawAddress.toString(), optstr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIsatty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiLink(src.rawAddress.toString(), dst.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiLseek(fd, offset.toString(), whence) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPathconf(path.rawAddress.toString(), name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRmdir(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(secs: CUint): CUint =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSleep(secs.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUnlink(c.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPread(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPwrite(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiLstat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsync(fd: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFsync(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdatasync(fd: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFdatasync(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSymlink(path1.rawAddress.toString(), path2.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTruncate(path.rawAddress.toString(), length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFtruncate(fd, length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetrusage(resource, usage.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun times(buf: MutPtr): io.github.kotlinmania.rustlang.wasi.ClockT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTimes(buf.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun usleep(secs: CUint): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUsleep(secs.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSend(socket, buf.rawAddress.toString(), len.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiRecv(socket, buf.rawAddress.toString(), len.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPoll(fds.rawAddress.toString(), nfds.toString(), timeout) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiSetlocale(category, locale.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconv(): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiLocaleconv() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiReadlink(path.rawAddress.toString(), buf.rawAddress.toString(), bufsz.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timegm(tm: MutPtr): TimeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiTimegm(tm.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysconf(name: CInt): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSysconf(name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctl(fd: CInt, request: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiIoctl(fd, request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFseeko(stream.rawAddress.toString(), offset.toString(), whence) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftello(stream: MutPtr): OffT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFtello(stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPosixFallocate(fd, offset.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiStrcasestr(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetline(lineptr.rawAddress.toString(), n.rawAddress.toString(), stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWritev(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiReadv(fd, iov.rawAddress.toString(), iovcnt) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPosixFadvise(fd, offset.toString(), len.toString(), advise) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFutimens(fd, times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiGetentropy(buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiMemrchr(cx.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiAbs(i) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiLabs(i.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiDuplocale(base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiFreelocale(loc.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiUselocale(loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedYield(): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSchedYield() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiGetcwd(buf.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiChdir(dir.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfo(item: io.github.kotlinmania.rustlang.wasi.NlItem): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiNlLanginfo(item) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfoL(item: io.github.kotlinmania.rustlang.wasi.NlItem, loc: io.github.kotlinmania.rustlang.wasi.LocaleT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiNlLanginfoL(item, loc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRegisterPreopenedFd(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcRegisterPreopenedFd(fd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcFdRenumber(fd: CInt, newfd: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcFdRenumber(fd, newfd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcUnlinkat(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcUnlinkat(fd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRmdirat(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcRmdirat(fd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcFindRelpath(path: ConstPtr, absPrefix: MutPtr, relativePath: MutPtr, relativePathLen: ULong): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcFindRelpath(path.rawAddress.toString(), absPrefix.rawAddress.toString(), relativePath.rawAddress.toString(), relativePathLen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcTell(fd: CInt): OffT =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcTell(fd) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdWasilibcUnlinkat(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdWasilibcRmdirat(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdWasilibcRmdirat(dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdLinkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdSymlinkat(target: ConstPtr, dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdSymlinkat(target.rawAddress.toString(), dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdReadlinkat(dirfd: CInt, path: ConstPtr, buf: MutPtr, bufsize: ULong): Long =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdReadlinkat(dirfd, path.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdFaccessat(dirfd: CInt, path: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdFaccessat(dirfd, path.rawAddress.toString(), mode, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdRenameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdOpenatNomode(dirfd: CInt, path: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdOpenatNomode(dirfd, path.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdFstatat(dirfd: CInt, path: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdFstatat(dirfd, path.rawAddress.toString(), buf.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdMkdiratNomode(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdMkdiratNomode(dirfd, path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdUtimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdOpendirat(dirfd: CInt, path: ConstPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiWasilibcNocwdOpendirat(dirfd, path.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcAccess(pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcAccess(pathname.rawAddress.toString(), mode, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcStat(pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcStat(pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcUtimens(pathname: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcUtimens(pathname.rawAddress.toString(), times.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLink(oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcLink(oldpath.rawAddress.toString(), newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLinkOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcLinkOldat(olddirfd, oldpath.rawAddress.toString(), newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLinkNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcLinkNewat(oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRenameOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcRenameOldat(olddirfd, oldpath.rawAddress.toString(), newpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRenameNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiWasilibcRenameNewat(oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4random(): UInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiArc4random() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(a: MutPtr, b: SizeT) {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiArc4randomBuf(a.rawAddress.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4randomUniform(a: UInt): UInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiArc4randomUniform(a.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun errnoLocation(): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiErrnoLocation() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiChmod(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiFchmod(fd, mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiRealpath(pathname.rawAddress.toString(), resolved.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSelf(): PthreadT =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiPthreadSelf() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadEqual(t1.rawAddress.toString(), t2.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadJoin(native.rawAddress.toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrGetstacksize(attr.rawAddress.toString(), stacksize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrSetstacksize(attr.rawAddress.toString(), stackSize.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, state: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrGetdetachstate(attr.rawAddress.toString(), state.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrSetdetachstate(attr.rawAddress.toString(), state) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrGetguardsize(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, b: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrSetguardsize(attr.rawAddress.toString(), b.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstack(attr: ConstPtr, b: MutPtr, c: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrGetstack(attr.rawAddress.toString(), b.rawAddress.toString(), c.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstack(attr: MutPtr, b: MutPtr, c: SizeT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadAttrSetstack(attr.rawAddress.toString(), b.rawAddress.toString(), c.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadDetach(thread.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetcancelstate(a: CInt, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSetcancelstate(a, b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetcanceltype(a: CInt, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSetcanceltype(a, b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTestcancel() {
    if (libcWasiNapiAddon != null) {
        libcWasiNapiAddon.rustlangLibcWasiPthreadTestcancel()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadOnce(a: MutPtr, b: AtexitACallback): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadOnce(a.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadKeyDelete(key.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (libcWasiNapiAddon != null) MutPtr((libcWasiNapiAddon.rustlangLibcWasiPthreadGetspecific(key.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSetspecific(key.toInt(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexInit(lock.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexLock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexTrylock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTimedlock(lock: MutPtr, ts: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexTimedlock(lock.rawAddress.toString(), ts.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexConsistent(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexConsistent(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexGetprioceiling(lock: MutPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexGetprioceiling(lock.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrSettype(attr.rawAddress.toString(), type) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrGetprotocol(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrGetrobust(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGettype(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrGettype(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetprotocol(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrSetprotocol(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetpshared(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrSetpshared(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetrobust(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadMutexattrSetrobust(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondInit(cond.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondWait(cond.rawAddress.toString(), lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondTimedwait(cond.rawAddress.toString(), lock.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondSignal(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondBroadcast(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondDestroy(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondattrGetclock(attr.rawAddress.toString(), clockId.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondattrSetpshared(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadCondattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockInit(lock.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockRdlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockTryrdlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockWrlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockTrywrlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTimedrdlock(lock: MutPtr, at: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockTimedrdlock(lock.rawAddress.toString(), at.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTimedwrlock(lock: MutPtr, at: ConstPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockTimedwrlock(lock.rawAddress.toString(), at.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockattrSetpshared(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadRwlockattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinInit(lock: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSpinInit(lock.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSpinDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSpinLock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSpinTrylock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadSpinUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierInit(barrier.rawAddress.toString(), attr.rawAddress.toString(), count.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierDestroy(barrier.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierWait(barrier.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierattrSetpshared(attr.rawAddress.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadBarrierattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetattrNp(thread: PthreadT, attr: MutPtr): CInt =
    if (libcWasiNapiAddon != null) (libcWasiNapiAddon.rustlangLibcWasiPthreadGetattrNp(thread.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
