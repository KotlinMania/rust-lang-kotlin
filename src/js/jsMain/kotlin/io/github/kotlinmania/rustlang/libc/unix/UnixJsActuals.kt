@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcUnixNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual val IN6ADDR_LOOPBACK: In6Addr
    get() = In6Addr()

public actual val IN6ADDR_ANY: In6Addr
    get() = In6Addr()

public actual fun isalnum(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsalnum(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsalpha(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIscntrl(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsgraph(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIslower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsprint(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIspunct(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsspace(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsxdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsblank(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTolower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixToupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixQsort(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun bsearch(key: ConstPtr, base: ConstPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixBsearch(key.rawAddress.toString(), base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFopen(filename.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFreopen(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(file: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFflush(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(file: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFclose(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(filename: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRemove(filename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRename(oldname.rawAddress.toString(), newname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile(): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixTmpfile() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getchar(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetchar() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPutchar(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFgetc(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFputc(c, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFputs(s.rawAddress.toString(), stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(s: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPuts(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUngetc(c, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFread(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFwrite(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFseek(stream.rawAddress.toString(), offset.toString(), whence) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFtell(stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(stream: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixRewind(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFgetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFsetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFeof(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFerror(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearerr(stream: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixClearerr(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun perror(s: ConstPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixPerror(s.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atof(s: ConstPtr): CDouble =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAtof(s.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAtoi(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAtol(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAtoll(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtof(s.rawAddress.toString(), endp.rawAddress.toString()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixCalloc(nobj.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMalloc(size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixRealloc(p.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(p: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixFree(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun abort(): Nothing {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixExit(status)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun system(s: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSystem(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetenv(s.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stpcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStpcpy(dst.rawAddress.toString(), src.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrcat(s.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrdup(cs.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrndup(cs.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrcasecmp(s1.rawAddress.toString(), s2.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrncasecmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrlen(cs.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrnlen(cs.rawAddress.toString(), maxlen.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrerror(n) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrtok(s.rawAddress.toString(), t.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtokR(s: MutPtr, t: ConstPtr, p: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrtokR(s.rawAddress.toString(), t.rawAddress.toString(), p.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strsignal(sig: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrsignal(sig) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcslen(buf: ConstPtr): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixWcslen(buf.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixWcstombs(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMemchr(cx.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixWmemchr(cx.rawAddress.toString(), c.toInt(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMemset(dest.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMemccpy(dest.rawAddress.toString(), src.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwnam(name: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetpwnam(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuid(uid: io.github.kotlinmania.rustlang.unix.UidT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetpwuid(uid) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSnprintf(s.rawAddress.toString(), n.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSprintf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSscanf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcharUnlocked(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetcharUnlocked() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putcharUnlocked(c: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPutcharUnlocked(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socket(domain: CInt, ty: CInt, protocol: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSocket(domain, ty, protocol) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(socket: CInt, address: ConstPtr, len: SocklenT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixConnect(socket, address.rawAddress.toString(), len.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(socket: CInt, backlog: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixListen(socket, backlog) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAccept(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetpeername(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetsockname(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(socket: CInt, level: CInt, name: CInt, value: ConstPtr, optionLen: SocklenT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetsockopt(socket, level, name, value.rawAddress.toString(), optionLen.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socketpair(domain: CInt, type: CInt, protocol: CInt, socketVector: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSocketpair(domain, type, protocol, socketVector.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt, addr: ConstPtr, addrlen: SocklenT): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSendto(socket, buf.rawAddress.toString(), len.toString(), flags, addr.rawAddress.toString(), addrlen.toInt()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixShutdown(socket, how) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixChmod(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFchmod(fd, mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFstat(fildes, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMkdir(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pclose(stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPclose(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFdopen(fd, mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFileno(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCreat(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixOpendir(dirname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopendir(fd: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFdopendir(fd) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir(dirp: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixReaddir(dirp.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closedir(dirp: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixClosedir(dirp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewinddir(dirp: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixRewinddir(dirp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fchmodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFchmodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchown(fd: CInt, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFchown(fd, owner, group) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchownat(dirfd: CInt, pathname: ConstPtr, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFchownat(dirfd, pathname.rawAddress.toString(), owner, group, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixOpenat(dirfd, pathname.rawAddress.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFstatat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMkdirat(dirfd, pathname.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSymlinkat(target.rawAddress.toString(), newdirfd, linkpath.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUnlinkat(dirfd, pathname.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAccess(path.rawAddress.toString(), amode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alarm(seconds: CUint): CUint =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAlarm(seconds.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixChdir(dir.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchdir(dirfd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFchdir(dirfd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixChown(path.rawAddress.toString(), uid, gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLchown(path.rawAddress.toString(), uid, gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixClose(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixDup(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup2(src: CInt, dst: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixDup2(src, dst) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlp(file: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExeclp(file.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExecv(prog.rawAddress.toString(), argv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixExecvp(c.rawAddress.toString(), argv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fork(): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFork() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFpathconf(filedes, name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetcwd(buf.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getegid(): GidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetegid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun geteuid(): UidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGeteuid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgid(): GidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetgid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgroups(ngroupsMax: CInt, groups: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetgroups(ngroupsMax, groups.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getlogin(): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetlogin() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetopt(argc, argv.rawAddress.toString(), optstr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpgid(pid: PidT): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetpgid(pid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpgrp(): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetpgrp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetpid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getppid(): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetppid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getuid(): UidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetuid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIsatty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLink(src.rawAddress.toString(), dst.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLseek(fd, offset.toString(), whence) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPathconf(path.rawAddress.toString(), name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pause(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPause() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe(fds: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPipe(fds.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixMemalign(memptr: MutPtr, align: SizeT, size: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPosixMemalign(memptr.rawAddress.toString(), align.toString(), size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedAlloc(alignment: SizeT, size: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixAlignedAlloc(alignment.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: SizeT): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRead(fd, buf.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRmdir(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seteuid(uid: UidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSeteuid(uid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setegid(gid: GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetegid(gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgid(gid: GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetgid(gid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpgid(pid: PidT, pgid: PidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetpgid(pid, pgid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsid(): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetsid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setuid(uid: UidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetuid(uid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setreuid(ruid: UidT, euid: UidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetreuid(ruid, euid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setregid(rgid: GidT, egid: GidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetregid(rgid, egid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(secs: CUint): CUint =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSleep(secs.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixNanosleep(rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetpgrp(fd: CInt): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcgetpgrp(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsetpgrp(fd: CInt, pgrp: io.github.kotlinmania.rustlang.unix.PidT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcsetpgrp(fd, pgrp) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ttyname(fd: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixTtyname(fd) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ttynameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTtynameR(fd, buf.rawAddress.toString(), buflen.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUnlink(c.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait(status: MutPtr): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixWait(status.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitpid(pid: PidT, status: MutPtr, options: CInt): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixWaitpid(pid, status.rawAddress.toString(), options) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: SizeT): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixWrite(fd, buf.rawAddress.toString(), count.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPread(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPwrite(fd, buf.rawAddress.toString(), count.toString(), offset.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umask(mask: ModeT): ModeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUmask(mask.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utime(file: ConstPtr, buf: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUtime(file.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kill(pid: PidT, sig: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixKill(pid, sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun killpg(pgrp: PidT, sig: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixKillpg(pgrp, sig) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock(addr: ConstPtr, len: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMlock(addr.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munlock(addr: ConstPtr, len: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMunlock(addr.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlockall(flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMlockall(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munlockall(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMunlockall() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mmap(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: OffT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMmap(addr.rawAddress.toString(), len.toString(), prot, flags, fd, offset.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munmap(addr: MutPtr, len: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMunmap(addr.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNametoindex(ifname: ConstPtr): CUint =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixIfNametoindex(ifname.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifIndextoname(ifindex: CUint, ifname: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixIfIndextoname(ifindex.toInt(), ifname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLstat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsync(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFsync(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setenv(name: ConstPtr, value: ConstPtr, overwrite: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetenv(name.rawAddress.toString(), value.rawAddress.toString(), overwrite) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unsetenv(name: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUnsetenv(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSymlink(path1.rawAddress.toString(), path2.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTruncate(path.rawAddress.toString(), length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFtruncate(fd, length.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSignal(signum, handler.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetrusage(resource, usage.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixRealpath(pathname.rawAddress.toString(), resolved.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun times(buf: MutPtr): ClockT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTimes(buf.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSelf(): PthreadT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadSelf() as String).toLong().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadEqual(t1.toLong().toString(), t2.toLong().toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadJoin(native.toLong().toString(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadExit(value: MutPtr): Nothing {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixPthreadExit(value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function pthreadExit")
}

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadAttrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadAttrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadAttrGetstacksize(attr.rawAddress.toString(), stacksize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadAttrSetstacksize(attr.rawAddress.toString(), stackSize.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadAttrSetdetachstate(attr.rawAddress.toString(), state) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadDetach(thread.toLong().toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedYield(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSchedYield() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadKeyDelete(key.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixPthreadGetspecific(key.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadSetspecific(key.toInt(), value.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexInit(lock.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexLock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexTrylock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadMutexattrSettype(attr.rawAddress.toString(), type) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondInit(cond.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondWait(cond.rawAddress.toString(), lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondTimedwait(cond.rawAddress.toString(), lock.rawAddress.toString(), abstime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondSignal(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondBroadcast(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondDestroy(cond.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadCondattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockInit(lock.rawAddress.toString(), attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockDestroy(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockRdlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockTryrdlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockWrlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockTrywrlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockUnlock(lock.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockattrInit(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPthreadRwlockattrDestroy(attr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(sockfd: CInt, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetsockopt(sockfd, level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun raise(signum: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRaise(signum) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimes(filename: ConstPtr, times: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUtimes(filename.rawAddress.toString(), times.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlopen(filename: ConstPtr, flag: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixDlopen(filename.rawAddress.toString(), flag) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlerror(): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixDlerror() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlsym(handle: MutPtr, symbol: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixDlsym(handle.rawAddress.toString(), symbol.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlclose(handle: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixDlclose(handle.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetaddrinfo(node.rawAddress.toString(), service.rawAddress.toString(), hints.rawAddress.toString(), res.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(res: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixFreeaddrinfo(res.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun hstrerror(errcode: CInt): ConstPtr =
    if (libcUnixNapiAddon != null) ConstPtr((libcUnixNapiAddon.rustlangLibcUnixHstrerror(errcode) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gaiStrerror(errcode: CInt): ConstPtr =
    if (libcUnixNapiAddon != null) ConstPtr((libcUnixNapiAddon.rustlangLibcUnixGaiStrerror(errcode) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun resInit(): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixResInit() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGmtimeR(timeP.rawAddress.toString(), result.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixLocaltimeR(timeP.rawAddress.toString(), result.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mktime(tm: MutPtr): TimeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMktime(tm.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun time(time: MutPtr): TimeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTime(time.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtime(timeP: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGmtime(timeP.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtime(timeP: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixLocaltime(timeP.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun difftime(time1: TimeT, time0: TimeT): CDouble =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixDifftime(time1.toString(), time0.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timegm(tm: MutPtr): TimeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTimegm(tm.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknod(pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMknod(pathname.rawAddress.toString(), mode.toInt(), dev) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostname(name: MutPtr, len: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGethostname(name.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun endservent() {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixEndservent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getservbyname(name: ConstPtr, proto: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetservbyname(name.rawAddress.toString(), proto.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getservbyport(port: CInt, proto: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetservbyport(port, proto.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getservent(): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetservent() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setservent(stayopen: CInt) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixSetservent(stayopen)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getprotobyname(name: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetprotobyname(name.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getprotobynumber(proto: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixGetprotobynumber(proto) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chroot(name: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixChroot(name.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun usleep(secs: CUint): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUsleep(secs.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSend(socket, buf.rawAddress.toString(), len.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixRecv(socket, buf.rawAddress.toString(), len.toString(), flags) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenv(string: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPutenv(string.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPoll(fds.rawAddress.toString(), nfds.toString(), timeout) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixSetlocale(category, locale.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconv(): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixLocaleconv() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semWait(sem: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSemWait(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semTrywait(sem: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSemTrywait(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semPost(sem: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSemPost(sem.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixStatvfs(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFstatvfs(fd, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigemptyset(set: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigemptyset(set.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaddset(set: MutPtr, signum: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigaddset(set.rawAddress.toString(), signum) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigfillset(set: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigfillset(set.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigdelset(set: MutPtr, signum: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigdelset(set.rawAddress.toString(), signum) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigismember(set: ConstPtr, signum: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigismember(set.rawAddress.toString(), signum) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigprocmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigprocmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigpending(set: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigpending(set.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysconf(name: CInt): CLong =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSysconf(name) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifo(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMkfifo(path.rawAddress.toString(), mode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFseeko(stream.rawAddress.toString(), offset.toString(), whence) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftello(stream: MutPtr): OffT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFtello(stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcdrain(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcdrain(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfgetispeed(termios: ConstPtr): SpeedT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCfgetispeed(termios.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfgetospeed(termios: ConstPtr): SpeedT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCfgetospeed(termios.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfsetispeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCfsetispeed(termios.rawAddress.toString(), speed.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfsetospeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCfsetospeed(termios.rawAddress.toString(), speed.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetattr(fd: CInt, termios: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcgetattr(fd, termios.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsetattr(fd: CInt, optionalActions: CInt, termios: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcsetattr(fd, optionalActions, termios.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcflow(fd: CInt, action: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcflow(fd, action) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcflush(fd: CInt, action: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcflush(fd, action) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetsid(fd: CInt): io.github.kotlinmania.rustlang.unix.PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcgetsid(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsendbreak(fd: CInt, duration: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixTcsendbreak(fd, duration) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemp(template: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixMkstemp(template.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdtemp(template: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixMkdtemp(template.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpnam(ptr: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixTmpnam(ptr.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openlog(ident: ConstPtr, logopt: CInt, facility: CInt) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixOpenlog(ident.rawAddress.toString(), logopt, facility)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun closelog() {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixCloselog()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setlogmask(maskpri: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSetlogmask(maskpri) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syslog(priority: CInt, message: ConstPtr, vararg variadicArgs: Long) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixSyslog(priority, message.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun nice(incr: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixNice(incr) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun grantpt(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGrantpt(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixOpenpt(flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPosixOpenpt(flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptsname(fd: CInt): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixPtsname(fd) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockpt(fd: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixUnlockpt(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStrcasestr(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetline(lineptr.rawAddress.toString(), n.rawAddress.toString(), stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockf(fd: CInt, cmd: CInt, len: OffT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixLockf(fd, cmd, len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtime(delta: ConstPtr, olddelta: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAdjtime(delta.rawAddress.toString(), olddelta.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stpncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixStpncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigqueue(pid: PidT, sig: CInt, value: io.github.kotlinmania.rustlang.unix.Sigval): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun confstr(name: CInt, buf: MutPtr, len: SizeT): SizeT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixConfstr(name, buf.rawAddress.toString(), len.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dladdr(addr: ConstPtr, info: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixDladdr(addr.rawAddress.toString(), info.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flock(fd: CInt, operation: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFlock(fd, operation) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openWmemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixOpenWmemstream(ptr.rawAddress.toString(), sizeloc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsid(pid: PidT): PidT =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixGetsid(pid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdirR(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixReaddirR(dirp.rawAddress.toString(), entry.rawAddress.toString(), result.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixReadlinkat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), bufsiz.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixReadlink(path.rawAddress.toString(), buf.rawAddress.toString(), bufsz.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pselect(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr, sigmask: ConstPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixPselect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString(), sigmask.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixSigaction(signum, act.rawAddress.toString(), oldact.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmemopen(buf: MutPtr, size: SizeT, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixFmemopen(buf.rawAddress.toString(), size.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openMemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    if (libcUnixNapiAddon != null) MutPtr((libcUnixNapiAddon.rustlangLibcUnixOpenMemstream(ptr.rawAddress.toString(), sizeloc.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixAtexit(0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfmakeraw(termios: MutPtr) {
    if (libcUnixNapiAddon != null) {
        libcUnixNapiAddon.rustlangLibcUnixCfmakeraw(termios.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cfsetspeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixCfsetspeed(termios.rawAddress.toString(), speed.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fnmatch(pattern: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAddon != null) (libcUnixNapiAddon.rustlangLibcUnixFnmatch(pattern.rawAddress.toString(), name.rawAddress.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
