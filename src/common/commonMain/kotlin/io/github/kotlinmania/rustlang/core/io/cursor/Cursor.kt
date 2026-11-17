// port-lint: source io/cursor.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.io.cursor

import io.github.kotlinmania.rustlang.core.io.constError
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.WRITE_ALL_EOF
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.result.Result

// Note(#74481): Hard-links required to link from `core` to `std`

/**
 * A `Cursor` wraps an in-memory buffer and provides it with a
 * [Seek] implementation.
 *
 * `Cursor`s are used with in-memory buffers, anything implementing
 * <code>[AsRef]<\[u8]></code>, to allow them to implement [Read] and/or [Write],
 * allowing these buffers to be used anywhere you might use a reader or writer
 * that does actual I/O.
 *
 * The standard library implements some I/O traits on various types which
 * are commonly used as a buffer, like <code>Cursor<[Vec]\<u8>></code> and
 * <code>Cursor<[&\[u8\]][bytes]></code>.
 *
 * # Examples
 *
 * We may want to write bytes to a [File] in our production
 * code, but use an in-memory buffer in our tests. We can do this with
 * `Cursor`:
 *
 * [File]: ../../std/fs/struct.File.html
 * [Read]: ../../std/io/trait.Read.html
 * [Vec]: ../../alloc/vec/struct.Vec.html
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.SeekFrom
 * import std.io.prelude.*
 *
 * // a library function we've written
 * fun <W> writeTenBytesAtEnd(writer: W): io.Result<Unit> where W : Write, W : Seek {
 *     writer.seek(SeekFrom.End(-10)).getOrElse { return Result.failure(it) }
 *     for (i in 0 until 10) {
 *         writer.write(listOf(i)).getOrElse { return Result.failure(it) }
 *     }
 *     // all went well
 *     return Result.success(Unit)
 * }
 * // Here's some code that uses this library function.
 * //
 * // We might want to use a BufReader here for efficiency, but let's
 * // keep this example focused.
 * var file = File.create("foo.txt").getOrElse { return Result.failure(it) }
 * // First, we need to allocate 10 bytes to be able to write into.
 * file.setLen(10).getOrElse { return Result.failure(it) }
 * writeTenBytesAtEnd(file).getOrElse { return Result.failure(it) }
 * ```
 */
public open class Cursor<T>(
    public var inner: T,
    public var pos: ULong,
) : Seek {
    /**
     * Consumes this cursor, returning the underlying value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * val buff = Cursor.new(mutableListOf())
     * val vec = buff.intoInner()
     * ```
     */
    public open fun intoInner(): T = this.inner

    /**
     * Gets a reference to the underlying value in this cursor.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * val buff = Cursor.new(mutableListOf())
     * val reference = buff.getRef()
     * ```
     */
    public open fun getRef(): T = this.inner

    /**
     * Gets a mutable reference to the underlying value in this cursor.
     *
     * Care should be taken to avoid modifying the internal I/O state of the
     * underlying value as it may corrupt this cursor's position.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * var buff = Cursor.new(mutableListOf())
     * val reference = buff.getMut()
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this::inner
    }

    /**
     * Returns the current position of this cursor.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     * import std.io.SeekFrom
     * import std.io.prelude.*
     *
     * var buff = Cursor.new(mutableListOf(1, 2, 3, 4, 5))
     * check(buff.position() == 0)
     * buff.seek(SeekFrom.Current(2)).unwrap()
     * check(buff.position() == 2)
     * buff.seek(SeekFrom.Current(-1)).unwrap()
     * check(buff.position() == 1)
     * ```
     */
    public open fun position(): ULong = this.pos

    /**
     * Sets the position of this cursor.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * var buff = Cursor.new(mutableListOf(1, 2, 3, 4, 5))
     * check(buff.position() == 0)
     * buff.setPosition(2)
     * check(buff.position() == 2)
     * buff.setPosition(4)
     * check(buff.position() == 4)
     * ```
     */
    public open fun setPosition(pos: ULong) {
        this.pos = pos
    }

    public open fun intoPartsMut(): Pair<RustMutableReference<ULong>, T> = Pair(this.pos, this.inner)

    /**
     * Splits the underlying slice at the cursor position and returns them.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * @feature(cursorSplit)
     * public fun doctestEntry() {
     *     var buff = Cursor.new(mutableListOf(1, 2, 3, 4, 5))
     *     check(buff.split() == Pair(listOf().asSlice(), listOf(1, 2, 3, 4, 5).asSlice()))
     *     buff.setPosition(2)
     *     check(buff.split() == Pair(listOf(1, 2).asSlice(), listOf(3, 4, 5).asSlice()))
     *     buff.setPosition(6)
     *     check(buff.split() == Pair(listOf(1, 2, 3, 4, 5).asSlice(), listOf().asSlice()))
     * }
     * ```
     */
    public open fun split(): Pair<List<UByte>, List<UByte>> {
        val slice = this.inner
        val pos = this.pos.min(slice.len().toULong())
        return slice.splitAt(pos.toInt())
    }

    /**
     * Splits the underlying slice at the cursor position and returns them
     * mutably.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Cursor
     *
     * @feature(cursorSplit)
     * public fun doctestEntry() {
     *     var buff = Cursor.new(mutableListOf(1, 2, 3, 4, 5))
     *     check(buff.splitMut() == Pair(listOf().asMutSlice(), listOf(1, 2, 3, 4, 5).asMutSlice()))
     *     buff.setPosition(2)
     *     check(buff.splitMut() == Pair(listOf(1, 2).asMutSlice(), listOf(3, 4, 5).asMutSlice()))
     *     buff.setPosition(6)
     *     check(buff.splitMut() == Pair(listOf(1, 2, 3, 4, 5).asMutSlice(), listOf().asMutSlice()))
     * }
     * ```
     */
    public open fun splitMut(): Pair<MutableList<UByte>, MutableList<UByte>> {
        val slice = this.inner.asMut()
        val pos = this.pos.min(slice.len().toULong())
        return slice.splitAtMut(pos.toInt())
    }

    public open fun clone(): Cursor<T> = Cursor(inner = this.inner.clone(), pos = this.pos)

    public open fun cloneFrom(other: Cursor<T>) {
        this.inner = other.inner
        this.pos = other.pos
    }

    public override fun seek(style: SeekFrom): Result<ULong> {
        val (basePos, offset) =
            when (style) {
                is SeekFrom.Start -> {
                    val (n) = style
                    this.setPosition(n)
                    return Result.Ok(n)
                }
                is SeekFrom.End -> {
                    val (n1) = style
                    Pair(this.getRef().asRef().len().toULong(), n1)
                }
                is SeekFrom.Current -> {
                    val (n2) = style
                    Pair(this.position(), n2)
                }
            }
        return when (val matchVal = basePos.checkedAddSigned(offset)) {
            null -> Result.Err(constError(ErrorKind.InvalidInput, "invalid seek to a negative or overflowing position"))
            else -> {
                val n3 = matchVal
                this.setPosition(n3)
                Result.Ok(n3)
            }
        }
    }

    public override fun streamLen(): Result<ULong> = Result.Ok(this.getRef().asRef().len().toULong())

    public override fun streamPosition(): Result<ULong> = Result.Ok(this.position())

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Cursor<T>> =
            object : TypeLayout<Cursor<T>> {
                override val alignBytes: Long = listOf(8L, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, 8L)
                override val sizeBytes: Long = alignUpBytes((off1 + 8L), alignBytes)
                override fun readNative(addr: Long): Cursor<T> =
                    Cursor(
                        (typeLayoutT).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Cursor<T>) {
                    (typeLayoutT).writeNative(addr, value.inner)
                    (PrimitiveLayouts.U64).writeNative(addr + off1, value.pos)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cursor<T> =
                    Cursor(
                        (typeLayoutT).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cursor<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.inner)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off1.toInt(), value.pos)
                }
            }

        // Note(#74481): Hard-links required to link from `core` to `alloc`

        /**
         * Creates a new cursor wrapping the provided underlying in-memory buffer.
         *
         * Cursor initial position is `0` even if underlying buffer (e.g., [Vec])
         * is not empty. So writing to cursor starts with overwriting [Vec]
         * content, not with appending to it.
         *
         * [Vec]: ../../alloc/vec/struct.Vec.html
         *
         * # Examples
         *
         * ```kotlin
         * import std.io.Cursor
         *
         * val buff = Cursor.new(mutableListOf())
         * ```
         */
        public fun <T> new(inner: T): Cursor<T> = Cursor(pos = 0uL, inner = inner)
    }
}

/**
 * Non-resizing [Write.write] implementation for slices.
 * Exported for `Cursor<Holder<[UByte], A>>`s implementation of [Write].
 */
public fun sliceWrite(posMut: RustMutableReference<ULong>, slice: MutableList<UByte>, buf: List<UByte>): Result<Int> {
    val posMut1 = posMut
    val pos = minOf((posMut1).read(), slice.size.toULong())
    val dst = slice.subList((pos.toInt()), slice.size)
    val amt = minOf(buf.size, dst.size)
    run { val dstTmp0 = dst; val srcTmp0 = ((buf).subList(0, amt)); for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
    (posMut1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (amt.toULong()); targetPlace.write(currentValue) }
    return Result.Ok(amt)
}

/**
 * Non-resizing [Write.writeVectored] implementation for slices.
 * Exported for `Cursor<Holder<[UByte], A>>`s implementation of [Write].
 */
public fun sliceWriteVectored(posMut: RustMutableReference<ULong>, slice: MutableList<UByte>, bufs: List<IoSlice>): Result<Int> {
    var nwritten = 0
    for (buf in bufs) {
        val n = when (val tryResult = sliceWrite(posMut, slice, buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        nwritten += n
        if (n < buf.len()) {
            break
        }
    }
    return Result.Ok(nwritten)
}

/**
 * Non-resizing [Write.writeAll] implementation for slices.
 * Exported for `Cursor<Holder<[UByte], A>>`s implementation of [Write].
 */
public fun sliceWriteAll(posMut: RustMutableReference<ULong>, slice: MutableList<UByte>, buf: List<UByte>): Result<Unit> {
    val n = when (val tryResult = sliceWrite(posMut, slice, buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return if (n < buf.size) {
        Result.Err(WRITE_ALL_EOF)
    } else {
        Result.Ok(Unit)
    }
}

/**
 * Non-resizing [Write.writeAllVectored] implementation for slices.
 * Exported for `Cursor<Holder<[UByte], A>>`s implementation of [Write].
 */
public fun sliceWriteAllVectored(posMut: RustMutableReference<ULong>, slice: MutableList<UByte>, bufs: List<IoSlice>): Result<Unit> {
    for (buf in bufs) {
        val n = when (val tryResult = sliceWrite(posMut, slice, buf)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
        if (n < buf.len()) {
            return Result.Err(WRITE_ALL_EOF)
        }
    }
    return Result.Ok(Unit)
}

public fun Cursor<MutableList<UByte>>.write(buf: List<UByte>): Result<Int> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWrite(pos, inner, buf)
}

public fun Cursor<MutableList<UByte>>.writeVectored(bufs: List<IoSlice>): Result<Int> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteVectored(pos, inner, bufs)
}

public fun Cursor<MutableList<UByte>>.isWriteVectored(): Boolean = true

public fun Cursor<MutableList<UByte>>.writeAll(buf: List<UByte>): Result<Unit> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteAll(pos, inner, buf)
}

public fun Cursor<MutableList<UByte>>.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteAllVectored(pos, inner, bufs)
}

public fun Cursor<MutableList<UByte>>.flush(): Result<Unit> = Result.Ok(Unit)

public fun Cursor<UByteArray>.write(buf: List<UByte>, n: Int): Result<Int> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWrite(pos, inner, buf)
}

public fun Cursor<UByteArray>.writeVectored(bufs: List<IoSlice>, n: Int): Result<Int> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteVectored(pos, inner, bufs)
}

public fun Cursor<UByteArray>.isWriteVectored(n: Int): Boolean = true

public fun Cursor<UByteArray>.writeAll(buf: List<UByte>, n: Int): Result<Unit> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteAll(pos, inner, buf)
}

public fun Cursor<UByteArray>.writeAllVectored(bufs: MutableList<IoSlice>, n: Int): Result<Unit> {
    val (pos, inner) = this.intoPartsMut()
    return sliceWriteAllVectored(pos, inner, bufs)
}

public fun Cursor<UByteArray>.flush(n: Int): Result<Unit> = Result.Ok(Unit)

/**
 * Trait used to allow indirect implementation of `Write` for `Cursor<Self>`.
 * Since [Cursor] is not a foundational type, it is not possible to implement
 * `Write` for `Cursor<T>` if `Write` is defined in `libcore` and `T` is in a
 * downstream crate (e.g., `liballoc` or `libstd`).
 *
 * Methods are identical in purpose and meaning to their `Write` namesakes.
 */
public interface WriteThroughCursor<T> {
    public fun write(thisRef: Cursor<T>, buf: List<UByte>): Result<Int>

    public fun writeVectored(thisRef: Cursor<T>, bufs: List<IoSlice>): Result<Int>

    public fun isWriteVectored(thisRef: Cursor<T>): Boolean

    public fun writeAll(thisRef: Cursor<T>, buf: List<UByte>): Result<Unit>

    public fun writeAllVectored(thisRef: Cursor<T>, bufs: MutableList<IoSlice>): Result<Unit>

    public fun flush(thisRef: Cursor<T>): Result<Unit>
}
