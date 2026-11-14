// port-lint: source capture.rs
package io.github.kotlinmania.rustlang.backtrace.capture

import io.github.kotlinmania.rustlang.backtrace.backtrace.trace
import io.github.kotlinmania.rustlang.backtrace.print.BacktraceFmt
import io.github.kotlinmania.rustlang.backtrace.print.PrintFmt
import io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol
import io.github.kotlinmania.rustlang.backtrace.symbolize.SymbolName
import io.github.kotlinmania.rustlang.backtrace.symbolize.resolve
import io.github.kotlinmania.rustlang.backtrace.symbolize.resolveFrame
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.serde.Deserialize
import io.github.kotlinmania.serde.Serialize
import io.github.kotlinmania.serde.de.Deserializer
import io.github.kotlinmania.serde.de.Unexpected
import io.github.kotlinmania.serde.de.Visitor
import io.github.kotlinmania.serde.ser.Serializer

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

public typealias Value = TracePtr

/**
 * Representation of an owned and self-contained backtrace.
 *
 * This structure can be used to capture a backtrace at various points in a
 * program and later used to inspect what the backtrace was at that time.
 *
 * `Backtrace` supports pretty-printing of backtraces through its `Debug`
 * implementation.
 *
 * # Required features
 *
 * This function requires the `std` feature of the `backtrace` crate to be
 * enabled, and the `std` feature is enabled by default.
 */
public open class Backtrace(
    // Frames here are listed from top-to-bottom of the stack
    public var frames: List<BacktraceFrame>,
) {
    public open fun clone(): Backtrace = Backtrace(frames)

    /**
     * Returns the frames from when this backtrace was captured.
     *
     * The first entry of this slice is likely the function `Backtrace.new`,
     * and the last frame is likely something about how this thread or the main
     * function started.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun frames(): List<BacktraceFrame> = this.frames

    /**
     * If this backtrace was created from `newUnresolved` then this function
     * will resolve all addresses in the backtrace to their symbolic names.
     *
     * If this backtrace has been previously resolved or was created through
     * `new`, this function does nothing.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun resolve() {
        this.frames.forEach({ it.resolve() })
    }

    // we don't want to implement `implementation From<Backtrace> for List<BacktraceFrame>` on purpose,
    // because "... additional directions for Vec<T> can weaken type inference ..."
    // more information on https://github.com/rust-lang/backtrace-rs/pull/526
    public open fun into(): List<BacktraceFrame> = this.frames.intoVec()

    public open fun fmt(fmt: StringBuilder) {
        val style =
            if (fmt.alternate()) {
                PrintFmt.Full
            } else {
                PrintFmt.Short
            }
        // When printing paths we try to strip the cwd if it exists, otherwise
        // we just print the path as-is. Note that we also only do this for the
        // short format, because if it's full we presumably want to print
        // everything.
        val cwd = currentDir()
        var printPath =
            closure@{ fmt1: StringBuilder, path: BytesOrWideString ->
                val path1 = path.intoPathBuf()
                if (style != PrintFmt.Full) {
                    if (cwd is Result.Ok<*>) {
                        val cwd1 = (cwd as Result.Ok<*>).value
                        when (val matchVal = path1.takeIf { it.startsWith(cwd1) }?.removePrefix(cwd1)) {
                            is Ok -> {
                                val suffix = matchVal.value
                                return@closure fmt1.append((suffix.display()).toString())
                            }
                            else -> {}
                        }
                    }
                }
                fmt1.append((path1.display()).toString())
            }
        val f = BacktraceFmt.new(fmt, style, printPath)
        f.addContext()
        for (frame in this.frames) {
            f.frame().backtraceFrame(frame)
        }
        f.finish()
    }

    public companion object {
        // want to make sure there's a frame here to remove

        /**
         * Captures a backtrace at the callsite of this function, returning an
         * owned representation.
         *
         * This function is useful for representing a backtrace as an object in
         * Rust. This returned value can be sent across threads and printed
         * elsewhere, and the purpose of this value is to be entirely self
         * contained.
         *
         * Note that on some platforms acquiring a full backtrace and resolving it
         * can be extremely expensive. If the cost is too much for your application
         * it's recommended to instead use `Backtrace.newUnresolved()` which
         * avoids the symbol resolution step (which typically takes the longest)
         * and allows deferring that to a later date.
         *
         * # Examples
         *
         * ```kotlin
         * import backtrace.Backtrace
         *
         * val currentBacktrace = Backtrace.new()
         * ```
         *
         * # Required features
         *
         * This function requires the `std` feature of the `backtrace` crate to be
         * enabled, and the `std` feature is enabled by default.
         */
        public fun new(): Backtrace {
            val bt = Backtrace.create((constPtrFromRustCast(Backtrace::new)).rawAddress.toInt())
            bt.resolve()
            return bt
        }

        // want to make sure there's a frame here to remove

        /**
         * Similar to `new` except that this does not resolve any symbols, this
         * simply captures the backtrace as a list of addresses.
         *
         * At a later time the `resolve` function can be called to resolve this
         * backtrace's symbols into readable names. This function exists because
         * the resolution process can sometimes take a significant amount of time
         * whereas any one backtrace may only be rarely printed.
         *
         * # Examples
         *
         * ```kotlin
         * import backtrace.Backtrace
         *
         * var currentBacktrace = Backtrace.newUnresolved()
         * println("$currentBacktrace")
         * // no symbol names
         * currentBacktrace.resolve()
         * println("$currentBacktrace")
         * // symbol names now present
         * ```
         *
         * # Required features
         *
         * This function requires the `std` feature of the `backtrace` crate to be
         * enabled, and the `std` feature is enabled by default.
         */
        public fun newUnresolved(): Backtrace = Backtrace.create((constPtrFromRustCast(Backtrace::newUnresolved)).rawAddress.toInt())

        public fun create(ip: Int): Backtrace {
            var frames = mutableListOf<BacktraceFrame>()
            trace({ frame ->
                frames.add(BacktraceFrame(frame = Frame.Raw(frame.clone()), symbols = null))
                // clear inner frames, and start with call site.
                if (frame.symbolAddress().toInt() == ip) {
                    frames.clear()
                }
                true
            })
            frames.shrinkToFit()
            return Backtrace(frames = frames.intoBoxedSlice())
        }

        public fun from(frames: List<BacktraceFrame>): Backtrace = Backtrace(frames = frames.intoBoxedSlice())

        public fun default(): Backtrace = Backtrace.new()
    }
}

public open class TracePtr internal constructor(
    valueCtorArg: MutPtr,
) : Deserialize, Serialize {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TracePtr = TracePtr(value)

    /**
     * SAFETY: These pointers are always valid within a process and are not used for mutation.
     */

    /**
     * SAFETY: These pointers are always valid within a process and are not used for mutation.
     */

    public open fun intoVoid(): MutPtr = this.value

    public open fun <S> serialize(serializer: S): Result<Ok, Error> where S : Serializer = serializer.serializeU64((this.value.rawAddress.toInt()).toULong())

    public companion object {
        public fun typeLayout(): TypeLayout<TracePtr> =
            object : TypeLayout<TracePtr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TracePtr =
                    TracePtr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: TracePtr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TracePtr =
                    TracePtr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TracePtr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): TracePtr = TracePtr(
            valueCtorArg = value,
        )

        public fun fromAddr(addr: Int): TracePtr = TracePtr(MutPtr(addr.toLong()))

        public fun <D> deserialize(deserializer: D): Result<TracePtr, Error> where D : Deserializer {
            open class PrimitiveVisitor : Visitor {
                public open fun expecting(formatter: StringBuilder) {
                    formatter.append("usize")
                }

                public open fun <E> visitU8(v: UByte): Result<TracePtr, E> = Result.Ok(TracePtr(MutPtr((v.toInt()).toLong())))

                public open fun <E> visitU16(v: UShort): Result<TracePtr, E> = Result.Ok(TracePtr(MutPtr((v.toInt()).toLong())))

                public open fun <E> visitU32(v: UInt, invalidTypeEFn: (Any?, PrimitiveVisitor) -> Any?): Result<TracePtr, E> =
                    if (32u >= 32u) {
                        Result.Ok(TracePtr(MutPtr((v.toInt()).toLong())))
                    } else {
                        Result.Err(invalidTypeEFn(Unexpected.Unsigned(((v as Any?) as Result<TracePtr, E>)), this))
                    }

                public open fun <E> visitU64(v: ULong, invalidTypeEFn: (Any?, PrimitiveVisitor) -> Any?): Result<TracePtr, E> =
                    if (32u >= 64u) {
                        Result.Ok(TracePtr(MutPtr((v.toInt()).toLong())))
                    } else {
                        Result.Err(invalidTypeEFn(Unexpected.Unsigned(((v as Any?) as Result<TracePtr, E>)), this))
                    }
            }
            return deserializer.deserializeU64(PrimitiveVisitor())
        }
    }
}

public fun assertSendSync() {
    fun <T> assert() {}
    assert<Backtrace>()
}

/**
 * Captured version of a frame in a backtrace.
 *
 * This type is returned as a list from `Backtrace.frames` and represents one
 * stack frame in a captured backtrace.
 *
 * # Required features
 *
 * This function requires the `std` feature of the `backtrace` crate to be
 * enabled, and the `std` feature is enabled by default.
 */
public open class BacktraceFrame(
    public var frame: Frame,
    public var symbols: List<BacktraceSymbol>?,
) {
    public open fun clone(): BacktraceFrame = BacktraceFrame(frame, symbols)

    /**
     * Same as `Frame.ip`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun ip(): MutPtr = this.frame.ip()

    /**
     * Same as `Frame.symbolAddress`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun symbolAddress(): MutPtr = this.frame.symbolAddress()

    /**
     * Same as `Frame.moduleBaseAddress`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun moduleBaseAddress(): MutPtr? = this.frame.moduleBaseAddress()

    /**
     * Returns the list of symbols that this frame corresponds to.
     *
     * Normally there is only one symbol per frame, but sometimes if a number
     * of functions are inlined into one frame then multiple symbols will be
     * returned. The first symbol listed is the "innermost function", whereas
     * the last symbol is the outermost (last caller).
     *
     * Note that if this frame came from an unresolved backtrace then this will
     * return an empty list.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun symbols(): List<BacktraceSymbol> = (this.symbols?.let({ s -> s }) ?: listOf())

    /**
     * Resolve all addresses in this frame to their symbolic names.
     *
     * If this frame has been previously resolved, this function does nothing.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun resolve() {
        if (this.symbols == null) {
            this.symbols = this.frame.resolveSymbols()
        }
    }

    public open fun fmt(fmt: StringBuilder) {
        DebugStruct(fmt, "BacktraceFrame").field("ip", this.ip()).field("symbol_address", this.symbolAddress()).finish()
    }

    public companion object {
        public fun from(frame: Frame): BacktraceFrame = BacktraceFrame(frame = Frame.Raw(frame), symbols = null)
    }
}

public sealed class Frame {
    public data class Raw(public var value: Frame) : Frame()
    public data class Deserialized(public var ip: TracePtr, public var symbolAddress: TracePtr, public var moduleBaseAddress: TracePtr?) : Frame()

    public open fun ip(): MutPtr =
        when (this) {
            is Frame.Raw -> {
                val (f) = this
                f.ip()
            }
            is Frame.Deserialized -> {
                val ip = this.ip
                ip.intoVoid()
            }
        }

    public open fun symbolAddress(): MutPtr =
        when (this) {
            is Frame.Raw -> {
                val (f) = this
                f.symbolAddress()
            }
            is Frame.Deserialized -> {
                val symbolAddress = this.symbolAddress
                symbolAddress.intoVoid()
            }
        }

    public open fun moduleBaseAddress(): MutPtr? =
        when (this) {
            is Frame.Raw -> {
                val (f) = this
                f.moduleBaseAddress()
            }
            is Frame.Deserialized -> {
                val moduleBaseAddress = this.moduleBaseAddress
                moduleBaseAddress.map({ addr -> addr.intoVoid() })
            }
        }

    /**
     * Resolve all addresses in the frame to their symbolic names.
     */
    public open fun resolveSymbols(): List<BacktraceSymbol> {
        val symbols = mutableListOf<BacktraceSymbol>()
        val sym =
            { symbol: Symbol ->
                symbols.add(BacktraceSymbol(name = symbol.name().map({ m -> m.encodeToByteArray().map { it.toUByte() }.into() }), addr = symbol.addr().map({ TracePtr(it) }), filename = symbol.filename().map({ m -> m.toOwned() }), lineno = symbol.lineno(), colno = symbol.colno()))
            }
        when (this) {
            is Frame.Raw -> {
                val (f) = this
                resolveFrame(f, sym)
            }
            is Frame.Deserialized -> {
                val ip = this.ip
                resolve(ip.intoVoid(), sym)
            }
        }
        return symbols.intoBoxedSlice()
    }
}

/**
 * Captured version of a symbol in a backtrace.
 *
 * This type is returned as a list from `BacktraceFrame.symbols` and
 * represents the metadata for a symbol in a backtrace.
 *
 * # Required features
 *
 * This function requires the `std` feature of the `backtrace` crate to be
 * enabled, and the `std` feature is enabled by default.
 */
public open class BacktraceSymbol(
    public var name: List<UByte>?,
    public var addr: TracePtr?,
    public var filename: PathBuf?,
    public var lineno: UInt?,
    public var colno: UInt?,
) {
    public open fun clone(): BacktraceSymbol = BacktraceSymbol(name, addr, filename, lineno, colno)

    /**
     * Same as `Symbol.name`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun name(): SymbolName? = this.name?.let({ s -> SymbolName.new(s) })

    /**
     * Same as `Symbol.addr`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun addr(): MutPtr? = this.addr?.let({ s -> s.intoVoid() })

    /**
     * Same as `Symbol.filename`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun filename(): Path? = this.filename

    /**
     * Same as `Symbol.lineno`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun lineno(): UInt? = this.lineno

    /**
     * Same as `Symbol.colno`
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun colno(): UInt? = this.colno

    public open fun fmt(fmt: StringBuilder) {
        DebugStruct(fmt, "BacktraceSymbol").field("name", this.name()).field("addr", this.addr()).field("filename", this.filename()).field("lineno", this.lineno()).field("colno", this.colno()).finish()
    }
}

public open class SerializedFrame(
    public var ip: Int,
    public var symbolAddress: Int,
    public var moduleBaseAddress: Int?,
    public var symbols: List<BacktraceSymbol>?,
)

public fun <S> BacktraceFrame.serialize(s: S): Result<Ok, Error> where S : Serializer {
    val frame = this.frame
    val symbols = this.symbols
    return SerializedFrame(ip = frame.ip().rawAddress.toInt(), symbolAddress = frame.symbolAddress().rawAddress.toInt(), moduleBaseAddress = frame.moduleBaseAddress().map({ symA -> symA.toInt() }), symbols = symbols).serialize(s)
}

public fun <D> deserializeBacktraceFrame(d: D): Result<BacktraceFrame, Error> where D : Deserializer {
    val frame: SerializedFrame = when (val tryResult = SerializedFrame.deserialize(d)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(BacktraceFrame(frame = Frame.Deserialized(ip = TracePtr.fromAddr(frame.ip), symbolAddress = TracePtr.fromAddr(frame.symbolAddress), moduleBaseAddress = frame.moduleBaseAddress?.let(TracePtr::fromAddr)), symbols = frame.symbols))
}
