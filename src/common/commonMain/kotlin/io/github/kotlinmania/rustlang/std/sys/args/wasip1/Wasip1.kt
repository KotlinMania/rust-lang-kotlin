// port-lint: source sys/args/wasip1.rs
package io.github.kotlinmania.rustlang.std.sys.args.wasip1

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.wasip1.argsGet
import io.github.kotlinmania.rustlang.wasip1.argsSizesGet

/**
 * Returns the command line arguments
 */
public fun args(): Args = Args.new((maybeArgs() ?: mutableListOf()))

public fun maybeArgs(): List<OsString>? {
    return run {
        val (argc, bufSize) = (argsSizesGet().ok() ?: return null)
        val argv = ArrayList(argc)
        val buf = ArrayList(bufSize)
        (argsGet(argv.asMutPtr(), buf.asMutPtr()).ok() ?: return null)
        argv.setLen(argc)
        val ret = ArrayList(argc)
        for (ptr in argv) {
            val s = CStr.fromPtr(ptr.cast())
            ret.add(OsStr.fromBytes(s.toBytes()).toOwned())
        }
        ret
    }
}
