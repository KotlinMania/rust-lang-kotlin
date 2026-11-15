// port-lint: source net/display_buffer.rs
package io.github.kotlinmania.rustlang.core.net.displaybuffer

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Used for slow path in `Display` implementations when alignment is required.
 */
public open class DisplayBuffer internal constructor(
    bufCtorArg: MutableList<MaybeUninit<UByte>>,
    public var len: Int,
) : Appendable {
    public var buf: MutableList<MaybeUninit<UByte>> = bufCtorArg
        internal set

    public open fun asStr(): String {
        // SAFETY: `buf` is only written to by the `fmt.Write.writeStr` implementation
        // which writes a valid UTF-8 string to `buf` and correctly sets `len`.
        return run {
            val s = ((this.buf).subList(0, this.len)).assumeInitRef()
            String.fromUtf8Unchecked(s)
        }
    }

    public open fun writeStr(s: String): Result<Unit, Unit> {
        val bytes = s.encodeToByteArray().map { it.toUByte() }
        return when (val matchVal = this.buf.getMut(this.len until (this.len + bytes.size))) {
            null -> {
                Result.Err(fmt.Error)
            }
            else -> {
                val buf = matchVal
                buf.writeCopyOfSlice(bytes)
                this.len += bytes.size
                Result.Ok(Unit)
            }
        }
    }

    public companion object {
        public operator fun invoke(
            buf: MutableList<MaybeUninit<UByte>>,
            len: Int,
        ): DisplayBuffer =
            DisplayBuffer(
                bufCtorArg = buf,
                len = len,
            )

        public fun new(buf: MutableList<MaybeUninit<UByte>>): DisplayBuffer = DisplayBuffer(buf = buf, len = 0)

        public fun buffer(size: Int): List<MaybeUninit<UByte>> = List(size) { MaybeUninit.uninit(UnresolvedTypeLayout<List<MaybeUninit<UByte>>>()) }
    }
}
