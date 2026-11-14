// port-lint: source sys/args/wali.rs
package io.github.kotlinmania.rustlang.std.sys.args.wali.imp

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.unix.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.os.unix.ffi.fromVec
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.std.os.raw.CUint as WaliArgIdx

public expect fun clGetArgc(): WaliArgIdx

public expect fun clGetArgvLen(offset: WaliArgIdx): CUint

public expect fun clCopyArgv(buf: MutPtr, offset: WaliArgIdx): CUint

public var ARGS: OnceLock<List<OsString>> = OnceLock.new()

public fun init(argc: Int, argv: ConstPtr) {
    // Uses the WALI arguments API
    ARGS.set(argcArgv()).ok()
}

public fun loadArg(idx: CUint): OsString {
    val argLen = clGetArgvLen(idx)
    val argBuf = CString.new(mutableListOf(120u, argLen.toInt())).unwrap()
    val ptr = argBuf.intoRaw()
    val argBuf1 =
        run {
            clCopyArgv(ptr, idx)
            CString.fromRaw(ptr)
        }
    return OsStringExt.fromVec(argBuf1.intoBytes())
}

public fun argcArgv(): List<OsString> {
    val argc = clGetArgc()
    return (0 until argc).map({ x -> loadArg(x) }).toMutableList()
}

public fun args(): Args {
    val cached = ARGS.get().unwrapOrDefault()
    return Args.new(cached)
}
