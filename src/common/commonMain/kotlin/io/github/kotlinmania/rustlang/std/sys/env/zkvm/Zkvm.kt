// port-lint: source sys/env/zkvm.rs
package io.github.kotlinmania.rustlang.std.sys.env.zkvm

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.WORD_SIZE
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysAllocWords
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysGetenv

public fun getenv(varname: OsStr): OsString? {
    val varname1 = varname.asEncodedBytes()
    val nbytes = sysGetenv(MutPtr(0L), 0, varname1.asPtr(), varname1.len())
    if (nbytes == Int.MAX_VALUE) {
        return null
    }
    val nwords = (nbytes + WORD_SIZE - 1) / WORD_SIZE
    val words = sysAllocWords(nwords)
    val nbytes2 = sysGetenv(words, nwords, varname1.asPtr(), varname1.len())
    check(nbytes == nbytes2)
    // Convert to OsString.
    //
    // Note: We can probably get rid of the extra copy here if we
    // reimplement "osStr" instead of just using the generic unix
    // "osStr".
    val u8s: List<UByte> = rawSliceList(constPtrFromRustCast(words), nbytes.toInt(), PrimitiveLayouts.U8)
    return OsString.fromInner(OsStr.Buf(inner = u8s.toVec()))
}
