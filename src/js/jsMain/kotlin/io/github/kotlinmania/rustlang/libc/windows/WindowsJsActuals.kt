@file:OptIn(ExperimentalUnsignedTypes::class)

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

private val libcWindowsNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSnprintf(buffer.rawAddress.toString(), count.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSprintf(buffer.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSscanf(buffer.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalnum(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsalnum(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsalpha(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIscntrl(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsgraph(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIslower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsprint(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIspunct(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsspace(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsxdigit(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsblank(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsTolower(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsToupper(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsQsort(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsQsortS(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsFopen(filename.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsFreopen(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(file: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFflush(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(file: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFclose(file.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(filename: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRemove(filename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRename(oldname.rawAddress.toString(), newname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile(): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsTmpfile() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getchar(): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetchar() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(c: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPutchar(c) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFgetc(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFputc(c, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFputs(s.rawAddress.toString(), stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(s: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPuts(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsUngetc(c, stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFread(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFwrite(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFseek(stream.rawAddress.toString(), offset.toString(), whence) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFtell(stream.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(stream: MutPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsRewind(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFgetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFsetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFeof(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFerror(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun perror(s: ConstPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsPerror(s.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atof(s: ConstPtr): CDouble =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAtof(s.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAtoi(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAtol(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAtoll(s.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtof(s.rawAddress.toString(), endp.rawAddress.toString()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsCalloc(nobj.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsMalloc(size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msize(p: MutPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsMsize(p.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsRealloc(p.rawAddress.toString(), size.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(p: MutPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsFree(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun abort(): Nothing {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsExit(status)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAtexit(0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun system(s: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSystem(s.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsGetenv(s.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrcat(s.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrrchr(cs.rawAddress.toString(), c) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrdup(cs.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrlen(cs.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrnlen(cs.rawAddress.toString(), maxlen.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrerror(n) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsStrtok(s.rawAddress.toString(), t.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcslen(buf: ConstPtr): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWcslen(buf.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWcsnlen(str.rawAddress.toString(), numberOfElements.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWcstombs(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsMemchr(cx.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsMemset(dest.rawAddress.toString(), c, n.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAbs(i) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsLabs(i.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRand() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSignal(signum, handler.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun raise(signum: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRaise(signum) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clock(): ClockT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsClock() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetDaylight(hours.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetDstbias(seconds.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetTimezone(seconds.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetTzname(pReturnValue.rawAddress.toString(), timeZoneName.rawAddress.toString(), sizeInBytes.toString(), index) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tzset() {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsTzset()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsChmod(path.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWchmod(path.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsMkdir(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wrmdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWrmdir(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFstat(fildes, buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsStat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWstat(path.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWutime(file.rawAddress.toString(), buf.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsPopen(command.rawAddress.toString(), mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pclose(stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPclose(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsFdopen(fd, mode.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsFileno(stream.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWopen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsCreat(path.rawAddress.toString(), mode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAccess(path.rawAddress.toString(), amode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsChdir(dir.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsClose(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup(fd: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsDup(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup2(src: CInt, dst: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsDup2(src, dst) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecv(prog.rawAddress.toString(), argv.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecvp(c.rawAddress.toString(), argv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsExecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecv(prog.rawAddress.toString(), argv.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecvp(c.rawAddress.toString(), argv.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWexecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsGetcwd(buf.rawAddress.toString(), size) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetpid() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsIsatty(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsLseek(fd, offset.toString(), origin) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsLseek64(fd, offset.toString(), origin) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPipe(fds.rawAddress.toString(), psize.toInt(), textmode) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRead(fd, buf.rawAddress.toString(), count.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRmdir(path.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsUnlink(c.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWrite(fd, buf.rawAddress.toString(), count.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun commit(fd: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsCommit(fd) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOsfhandle(fd: CInt): IntptrT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetOsfhandle(fd) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsOpenOsfhandle(osfhandle.toString(), flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsSetlocale(category, locale.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsWsetlocale(category, locale.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsAlignedMalloc(size.toString(), alignment.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedFree(ptr: MutPtr) {
    if (libcWindowsNapiAddon != null) {
        libcWindowsNapiAddon.rustlangLibcWindowsAlignedFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    if (libcWindowsNapiAddon != null) MutPtr((libcWindowsNapiAddon.rustlangLibcWindowsAlignedRealloc(memblock.rawAddress.toString(), size.toString(), alignment.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenv(envstring: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPutenv(envstring.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenv(envstring: ConstPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWputenv(envstring.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsPutenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsWputenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsListen(s.toString(), backlog) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsBind(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsConnect(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetpeername(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetsockname(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    if (libcWindowsNapiAddon != null) (libcWindowsNapiAddon.rustlangLibcWindowsSocket(af, socketType, protocol) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
