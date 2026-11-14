// port-lint: source io/util.rs
package io.github.kotlinmania.rustlang.core.io.util

import io.github.kotlinmania.rustlang.core.fmt.Arguments
import io.github.kotlinmania.rustlang.core.fmt.write
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.sizehint.SizeHint
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * `Empty` ignores any data written via [Write], and will always be empty
 * (returning zero bytes) when read via [Read].
 *
 * [Read]: ../../std/io/trait.Read.html
 *
 * This struct is generally created by calling [empty]. Please
 * see the documentation of [empty] for more details.
 */
public open class Empty : SizeHint, Write<Empty>, Seek {
    public open fun clone(): Empty = Empty()

    public override fun upperBound(): Int? = 0

    public override fun write(buf: List<UByte>): Result<Int> = Result.Ok(buf.size)

    public override fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        val totalLen = bufs.map({ b -> b.len() }).sum()
        return Result.Ok(totalLen)
    }

    public override fun isWriteVectored(): Boolean = true

    public override fun writeAll(buf: List<UByte>): Result<Unit> = Result.Ok(Unit)

    public override fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> = Result.Ok(Unit)

    public override fun writeFmt(args: Arguments): Result<Unit> = Result.Ok(Unit)

    public override fun flush(): Result<Unit> = Result.Ok(Unit)

    public override fun seek(pos: SeekFrom): Result<ULong> = Result.Ok(0)

    public override fun streamLen(): Result<ULong> = Result.Ok(0)

    public override fun streamPosition(): Result<ULong> = Result.Ok(0)

    public companion object {
        public fun typeLayout(): TypeLayout<Empty> =
            object : TypeLayout<Empty> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Empty =
                    Empty()
                override fun writeNative(addr: Long, value: Empty) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Empty =
                    Empty()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Empty) {
                }
            }
    }
}

/**
 * Creates a value that is always at EOF for reads, and ignores all data written.
 *
 * All calls to [write] on the returned instance will return [Ok]
 * and the contents of the buffer will not be inspected.
 *
 * All calls to [read] from the returned reader will return [Ok].
 *
 * [Ok]: Ok
 * [Ok]: Ok
 *
 * [read]: ../../std/io/trait.Read.html#method.read
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Write
 *
 * val buffer = mutableListOf(1, 2, 3, 5, 8)
 * val numBytes = io.empty().write(buffer).unwrap()
 * check(numBytes == 5)
 * ```
 *
 * ```kotlin
 * import std.io.Read
 *
 * var buffer = ""
 * io.empty().readToString(buffer).unwrap()
 * check(buffer.isEmpty())
 * ```
 */
public fun empty(): Empty = Empty()

/**
 * A reader which yields one byte over and over and over and over and over and...
 *
 * This struct is generally created by calling [repeat]. Please
 * see the documentation of [repeat] for more details.
 */
public open class Repeat(
    public var byte: UByte,
) : SizeHint {
    public override fun lowerBound(): Int = Int.MAX_VALUE

    public override fun upperBound(): Int? = null

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Repeat").finishNonExhaustive()

    public companion object {
        public fun typeLayout(): TypeLayout<Repeat> =
            object : TypeLayout<Repeat> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Repeat =
                    Repeat(
                        (PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Repeat) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.byte)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Repeat =
                    Repeat(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Repeat) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.byte)
                }
            }
    }
}

/**
 * Creates an instance of a reader that infinitely repeats one byte.
 *
 * All reads from this reader will succeed by filling the specified buffer with
 * the given byte.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Read
 *
 * var buffer = MutableList(3) { 0 }
 * io.repeat(0x5).readExact(buffer).unwrap()
 * check(buffer == listOf(0x5, 0x5, 0x5))
 * ```
 */
public fun repeat(byte: UByte): Repeat = Repeat(byte = byte)

/**
 * A writer which will move data into the void.
 *
 * This struct is generally created by calling [sink]. Please
 * see the documentation of [sink] for more details.
 */
public open class Sink : Write<Sink> {
    public open fun clone(): Sink = Sink()

    public override fun write(buf: List<UByte>): Result<Int> = Result.Ok(buf.size)

    public override fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        val totalLen = bufs.map({ b -> b.len() }).sum()
        return Result.Ok(totalLen)
    }

    public override fun isWriteVectored(): Boolean = true

    public override fun writeAll(buf: List<UByte>): Result<Unit> = Result.Ok(Unit)

    public override fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> = Result.Ok(Unit)

    public override fun writeFmt(args: Arguments): Result<Unit> = Result.Ok(Unit)

    public override fun flush(): Result<Unit> = Result.Ok(Unit)

    public companion object {
        public fun typeLayout(): TypeLayout<Sink> =
            object : TypeLayout<Sink> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Sink =
                    Sink()
                override fun writeNative(addr: Long, value: Sink) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sink =
                    Sink()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sink) {
                }
            }
    }
}

/**
 * Creates an instance of a writer which will successfully consume all data.
 *
 * All calls to [write] on the returned instance will return [Ok]
 * and the contents of the buffer will not be inspected.
 *
 * [Ok]: Ok
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Write
 *
 * val buffer = mutableListOf(1, 2, 3, 5, 8)
 * val numBytes = io.sink().write(buffer).unwrap()
 * check(numBytes == 5)
 * ```
 */
public fun sink(): Sink = Sink()

/**
 * Adapter to chain together two readers.
 *
 * This struct is generally created by calling [chain] on a reader.
 * Please see the documentation of [chain] for more details.
 *
 * [chain]: ../../std/io/trait.Read.html#method.chain
 */
public open class Chain<T, U>(
    public var first: T,
    public var second: U,
    public var doneFirst: Boolean,
) : SizeHint {
    public override fun lowerBound(): Int = this.first.lowerBound() + this.second.lowerBound()

    public override fun upperBound(): Int? =
        run {
            val matchVal = Pair(this.first.upperBound(), this.second.upperBound())
            when {
                matchVal.first != null && matchVal.second != null -> {
                    val first = matchVal.first
                    val second = matchVal.second
                    first.checkedAdd(second)
                }
                else -> null
            }
        }

    /**
     * Consumes the `Chain`, returning the wrapped readers.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var fooFile = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var barFile = File.open("bar.txt").getOrElse { return Result.failure(it) }
     *     val chain = fooFile.chain(barFile)
     *     val (fooFile1, barFile1) = chain.intoInner()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun intoInner(): Pair<T, U> = Pair(this.first, this.second)

    /**
     * Gets references to the underlying readers in this `Chain`.
     *
     * Care should be taken to avoid modifying the internal I/O state of the
     * underlying readers as doing so may corrupt the internal state of this
     * `Chain`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var fooFile = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var barFile = File.open("bar.txt").getOrElse { return Result.failure(it) }
     *     val chain = fooFile.chain(barFile)
     *     val (fooFile1, barFile1) = chain.getRef()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getRef(): Pair<T, U> = Pair(this.first, this.second)

    /**
     * Gets mutable references to the underlying readers in this `Chain`.
     *
     * Care should be taken to avoid modifying the internal I/O state of the
     * underlying readers as doing so may corrupt the internal state of this
     * `Chain`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var fooFile = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var barFile = File.open("bar.txt").getOrElse { return Result.failure(it) }
     *     var chain = fooFile.chain(barFile)
     *     val (fooFile1, barFile1) = chain.getMut()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getMut(): Pair<T, U> = Pair(this.first, this.second)

    public companion object {
        public fun <T, U> typeLayout(typeLayoutT: TypeLayout<T>, typeLayoutU: TypeLayout<U>): TypeLayout<Chain<T, U>> =
            object : TypeLayout<Chain<T, U>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes, typeLayoutU.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, typeLayoutU.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutU.sizeBytes), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 1L), alignBytes)
                override fun readNative(addr: Long): Chain<T, U> =
                    Chain(
                        (typeLayoutT).readNative(addr),
                        (typeLayoutU).readNative(addr + off1),
                        (PrimitiveLayouts.BOOL).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Chain<T, U>) {
                    (typeLayoutT).writeNative(addr, value.first)
                    (typeLayoutU).writeNative(addr + off1, value.second)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off2, value.doneFirst)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Chain<T, U> =
                    Chain(
                        (typeLayoutT).readBytes(buf, offset),
                        (typeLayoutU).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Chain<T, U>) {
                    (typeLayoutT).writeBytes(buf, offset, value.first)
                    (typeLayoutU).writeBytes(buf, offset + off1.toInt(), value.second)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off2.toInt(), value.doneFirst)
                }
            }
    }
}

public fun <T, U> chain(first: T, second: U): Chain<T, U> = Chain(first = first, second = second, doneFirst = false)

/**
 * Reader adapter which limits the bytes read from an underlying reader.
 *
 * This struct is generally created by calling [take] on a reader.
 * Please see the documentation of [take] for more details.
 *
 * [take]: ../../std/io/trait.Read.html#method.take
 */
public open class Take<T>(
    public var inner: T,
    public var len: ULong,
    public var limit: ULong,
) : SizeHint, Seek {
    public override fun lowerBound(): Int = minOf(this.inner.lowerBound().toULong(), this.limit).toInt()

    public override fun upperBound(): Int? =
        when (val matchVal = this.inner.upperBound()) {
            null -> this.limit.tryInto().ok()
            else -> {
                val upperBound = matchVal
                minOf(upperBound.toULong(), this.limit).toInt()
            }
        }

    /**
     * Returns the number of bytes that can be read before this instance will
     * return EOF.
     *
     * # Note
     *
     * This instance may reach `EOF` after reading fewer bytes than indicated by
     * this method if the underlying [Read] instance reaches EOF.
     *
     * [Read]: ../../std/io/trait.Read.html
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     // read at most five bytes
     *     val handle = f.take(5)
     *     println("limit: ${handle.limit()}")
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun limit(): ULong = this.limit

    /**
     * Returns the number of bytes read so far.
     */
    public open fun position(): ULong = this.len - this.limit

    /**
     * Sets the number of bytes that can be read before this instance will
     * return EOF. This is the same as constructing a new `Take` instance, so
     * the amount of bytes read and the previous limit value don't matter when
     * calling this method.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     // read at most five bytes
     *     var handle = f.take(5)
     *     handle.setLimit(10)
     *     check(handle.limit() == 10)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun setLimit(limit: ULong) {
        this.len = limit
        this.limit = limit
    }

    /**
     * Consumes the `Take`, returning the wrapped reader.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(5) { 0 }
     *     var handle = file.take(5)
     *     handle.read(buffer).getOrElse { return Result.failure(it) }
     *     val file1 = handle.intoInner()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun intoInner(): T = this.inner

    /**
     * Gets a reference to the underlying reader.
     *
     * Care should be taken to avoid modifying the internal I/O state of the
     * underlying reader as doing so may corrupt the internal limit of this
     * `Take`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(5) { 0 }
     *     var handle = file.take(5)
     *     handle.read(buffer).getOrElse { return Result.failure(it) }
     *     val file1 = handle.getRef()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getRef(): T = this.inner

    /**
     * Gets a mutable reference to the underlying reader.
     *
     * Care should be taken to avoid modifying the internal I/O state of the
     * underlying reader as doing so may corrupt the internal limit of this
     * `Take`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io
     * import std.io.prelude.*
     *
     * fun main(): io.Result<Unit> {
     *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var buffer = MutableList(5) { 0 }
     *     var handle = file.take(5)
     *     handle.read(buffer).getOrElse { return Result.failure(it) }
     *     val file1 = handle.getMut()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this::inner
    }

    public override fun seek(pos: SeekFrom): Result<ULong> {
        val newPosition =
            when (pos) {
                is SeekFrom.Start -> {
                    val (v) = pos
                    v
                }
                is SeekFrom.Current -> {
                    val (v1) = pos
                    this.position().checkedAddSigned(v1)
                }
                is SeekFrom.End -> {
                    val (v2) = pos
                    this.len.checkedAddSigned(v2)
                }
            }
        val newPosition1 =
            when {
                newPosition != null && newPosition <= this.len -> {
                    val v3 = newPosition
                    v3
                }
                else -> { return Result.Err(ErrorKind.InvalidInput.into()) }
            }
        while (newPosition1 != this.position()) {
            when (val matchVal = newPosition1.checkedSignedDiff(this.position())) {
                null -> {}
                else -> {
                    val offset = matchVal
                    when (val tryResult = this.inner.seekRelative(offset)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    this.limit = (this.limit - (offset.toULong()))
                    break
                }
            }
            val offset1 =
                if (newPosition1 > this.position()) {
                    Long.MAX_VALUE
                } else {
                    Long.MIN_VALUE
                }
            when (val tryResult = this.inner.seekRelative(offset1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            this.limit = (this.limit - (offset1.toULong()))
        }
        return Result.Ok(newPosition1)
    }

    public override fun streamLen(): Result<ULong> = Result.Ok(this.len)

    public override fun streamPosition(): Result<ULong> = Result.Ok(this.position())

    public override fun seekRelative(offset: Long): Result<Unit> {
        if (!this.position().checkedAddSigned(offset).isSomeAnd({ p -> p <= this.len })) {
            return Result.Err(ErrorKind.InvalidInput.into())
        }
        when (val tryResult = this.inner.seekRelative(offset)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        this.limit = (this.limit - (offset.toULong()))
        return Result.Ok(Unit)
    }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Take<T>> =
            object : TypeLayout<Take<T>> {
                override val alignBytes: Long = listOf(8L, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, 8L)
                private val off2: Long = alignUpBytes((off1 + 8L), 8L)
                override val sizeBytes: Long = alignUpBytes((off2 + 8L), alignBytes)
                override fun readNative(addr: Long): Take<T> =
                    Take(
                        (typeLayoutT).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + off1),
                        (PrimitiveLayouts.U64).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Take<T>) {
                    (typeLayoutT).writeNative(addr, value.inner)
                    (PrimitiveLayouts.U64).writeNative(addr + off1, value.len)
                    (PrimitiveLayouts.U64).writeNative(addr + off2, value.limit)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Take<T> =
                    Take(
                        (typeLayoutT).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Take<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.inner)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off1.toInt(), value.len)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off2.toInt(), value.limit)
                }
            }
    }
}

public fun <T> take(inner: T, limit: ULong): Take<T> = Take(inner = inner, limit = limit, len = limit)
