// port-lint: source sys/net/hostname/windows.rs
package io.github.kotlinmania.rustlang.std.sys.net.hostname.windows

import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getHostNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.lstrlenW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.winsock.startup
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun hostname(): Result<OsString> {
    startup()
    // The documentation of GetHostNameW says that a buffer size of 256 is
    // always enough.
    val buffer = MutableList(256) { MaybeUninit.uninit(PrimitiveLayouts.U16) }
    // SAFETY: these parameters specify a valid, writable region of memory.
    when (val tryResult = cvt(getHostNameW(buffer.asMutPtr().cast(), buffer.size))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<OsString, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    // Use `lstrlenW` here as it does not require the bytes after the nul
    // terminator to be initialized.
    // SAFETY: if `GetHostNameW` returns successfully, the name is nul-terminated.
    val len = lstrlenW(buffer.asPtr().cast())
    // SAFETY: the length of the name is `len`, hence `len` bytes have been
    // initialized by `GetHostNameW`.
    val name = buffer[0 until len].assumeInitRef()
    return RustResult.Ok<OsString, io.github.kotlinmania.rustlang.core.io.error.Error>(OsString.fromWide(name))
}
