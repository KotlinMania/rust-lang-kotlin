// port-lint: source net/display_buffer.rs
package io.github.kotlinmania.rustlang.core.net.displaybuffer

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Used for slow path in `Display` implementations when alignment is required.
 */
internal open class DisplayBuffer(
    public var buf: MutableList<MaybeUninit<UByte>>,
    public var len: Int,
) : Appendable {
    internal open fun asStr(): String {
        // SAFETY: `buf` is only written to by the `fmt.Write.writeStr` implementation
        // which writes a valid UTF-8 string to `buf` and correctly sets `len`.
        return run {
            val s = ((this.buf).slice(0 until this.len)).assumeInitRef()
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
        internal fun new(buf: MutableList<MaybeUninit<UByte>>): DisplayBuffer = DisplayBuffer(buf = buf, len = 0)

        internal fun buffer(size: Int): List<MaybeUninit<UByte>> = List(size) { MaybeUninit.uninit(ObjectSlotLayout<List<MaybeUninit<UByte>>>()) }
    }
}
