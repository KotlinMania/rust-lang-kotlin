// port-lint: source sys/thread_local/key/unix.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.ffi.CInt
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput

public fun interface PthreadKeyCreateDestructorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public fun interface CreateDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public typealias PthreadKeyT = io.github.kotlinmania.rustlang.std.ffi.CUint

public expect fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt

public expect fun pthreadGetspecific(key: PthreadKeyT): MutPtr

public expect fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt

public expect fun pthreadKeyDelete(key: PthreadKeyT): CInt

public typealias Key = PthreadKeyT

public fun create(dtor: CreateDtorCallback?): Key {
    val localStorageL27C4 = AddressableLocal(0, PrimitiveLayouts.I32)
    val key by localStorageL27C4
    if (pthreadKeyCreate(RustMutableReference(localStorageL27C4.asMutPtr(), localStorageL27C4.layout), transmute(dtor)) != 0) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("out of TLS keys").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("out of TLS keys").toString() + ", aborting\n"
            }
            abort()
        }
    }
    return key.toUInt()
}

public fun fail(): Nothing =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("Unexpected TLS failure").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("Unexpected TLS failure").toString() + ", aborting\n"
        }
        abort()
    }

public fun set(key: Key, value: MutPtr) {
    val r = pthreadSetspecific(key, value)
    // May happen on memory exhaustion
    if (r != 0) {
        fail()
    }
}

public fun destroy(key: Key) {
    val r = pthreadKeyDelete(key)
    // only documented error is for invalid keys
    if (r != 0) {
        fail()
    }
}

public class InlineTests {
    @kotlin.test.Test
    public fun get(key: Key): MutPtr = (pthreadGetspecific(key)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
}
