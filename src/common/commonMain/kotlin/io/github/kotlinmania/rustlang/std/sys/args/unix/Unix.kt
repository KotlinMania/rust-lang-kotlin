// port-lint: source sys/args/unix.rs
package io.github.kotlinmania.rustlang.std.sys.args.unix

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.std.os.hermit.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.std.sys.args.unix.imp.argcArgv

// Global initialization and retrieval of command line arguments.
//
// On some platforms these are stored during runtime startup,
// and on some they are retrieved from the system on demand.

/**
 * One-time global initialization.
 */
public fun init(argc: Int, argv: ConstPtr) {
    init(argc, argv)
}

/**
 * Returns the command line arguments
 */
public fun args(): Args {
    val (argc, argv) = argcArgv()
    val vec = ArrayList(argc.toInt())
    for (i in 0 until argc) {
        // SAFETY: `argv` is non-null if `argc` is positive, and it is
        // guaranteed to be at least as long as `argc`, so reading from it
        // should be safe.
        val ptr = argv.offset(i).read()
        // Some C commandline parsers (e.g. GLib and Qt) are replacing already
        // handled arguments in `argv` with `NULL` and move them to the end.
        //
        // Since they can't directly ensure updates to `argc` as well, this
        // means that `argc` might be bigger than the actual number of
        // non-`NULL` pointers in `argv` at this point.
        //
        // To handle this we simply stop iterating at the first `NULL`
        // argument. `argv` is also guaranteed to be `NULL`-terminated so any
        // non-`NULL` arguments after the first `NULL` can safely be ignored.
        if (ptr.isNull()) {
            // NOTE: On Apple platforms, `- [NSProcessInfo arguments]` does not
            // stop iterating here, but instead `continue`, always iterating
            // up until it reached `argc`.
            //
            // This difference will only matter in very specific circumstances
            // where `argc`/`argv` have been modified, but in unexpected ways,
            // so it likely doesn't really matter which option we choose.
            // See the following PR for further discussion:
            // <https://github.com/rust-lang/rust/pull/125225>
            break
        }
        // SAFETY: Just checked that the pointer is not NULL, and arguments
        // are otherwise guaranteed to be valid C strings.
        val cstr = CStr.fromPtr(ptr)
        vec.add(OsStringExt.fromVec(cstr.toBytes().toVec()))
    }
    return Args.new(vec)
}
