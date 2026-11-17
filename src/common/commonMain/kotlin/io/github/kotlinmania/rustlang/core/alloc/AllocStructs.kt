package io.github.kotlinmania.rustlang.core.alloc

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * The `AllocError` error indicates an allocation failure
 * that may be due to resource exhaustion or to
 * something wrong when combining the given input arguments with this
 * allocator.
 */
public open class AllocError {
    public open fun clone(): AllocError = AllocError()

    // (we need this for downstream impl of trait Error)
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append("memory allocation failed"); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<AllocError> =
            object : TypeLayout<AllocError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): AllocError =
                    AllocError()
                override fun writeNative(addr: Long, value: AllocError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): AllocError =
                    AllocError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: AllocError) {
                }
            }
    }
}
