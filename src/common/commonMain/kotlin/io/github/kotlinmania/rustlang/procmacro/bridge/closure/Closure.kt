// port-lint: source bridge/closure.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.closure

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer

// Closure type (equivalent to `dynamic FnMut(Buffer) -> Buffer`) that's `repr(C)`.

public fun interface ClosureCallCallback {
    public operator fun invoke(arg0: MutPtr, arg1: Buffer): Buffer
}

public open class Closure internal constructor(
    public var call: ClosureCallCallback,
    envCtorArg: MutPtr,
    // Prevent Send and Sync impls.
    //
    // The `a` scope parameter represents the scope of `Env`.
    public var marker: Unit,
) {
    public var env: MutPtr = envCtorArg
        internal set

    public open fun call(arg: Buffer): Buffer = (this.call)(this.env, arg)

    public companion object {
        public operator fun invoke(
            call: ClosureCallCallback,
            env: MutPtr,
            marker: Unit,
        ): Closure = Closure(
            call = call,
            envCtorArg = env,
            marker = marker,
        )

        public fun from(f: (Buffer) -> Buffer): Closure {
            fun call(env: MutPtr, arg: Buffer, typeLayoutF: TypeLayout<F> = UnresolvedTypeLayout()): Buffer = (UnresolvedTypeLayout<(Buffer) -> Buffer>()).readNative((((env).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)(arg)
            return Closure(call = call<(Buffer) -> Buffer>, env = (mutPtrFromRustCast(f)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, marker = Unit)
        }
    }
}

public open class Env {
    public companion object {
        public fun typeLayout(): TypeLayout<Env> =
            object : TypeLayout<Env> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Env =
                    Env()
                override fun writeNative(addr: Long, value: Env) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Env =
                    Env()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Env) {
                }
            }
    }
}
