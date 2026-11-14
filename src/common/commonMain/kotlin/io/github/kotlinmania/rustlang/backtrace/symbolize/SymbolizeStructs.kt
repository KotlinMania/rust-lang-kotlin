package io.github.kotlinmania.rustlang.backtrace.symbolize

import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.fmt.Debug
import io.github.kotlinmania.rustlang.core.fmt.Display
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.rustcdemangle.Demangle
import io.github.kotlinmania.rustlang.rustcdemangle.tryDemangle

public fun interface FnCallback {
    public operator fun invoke(arg0: String, arg1: StringBuilder)
}

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
 * A trait representing the resolution of a symbol in a file.
 *
 * This trait is yielded as a trait object to the closure given to the
 * `backtrace.resolve` function, and it is virtually dispatched as it's
 * unknown which implementation is behind it.
 *
 * A symbol can give contextual information about a function, for example the
 * name, filename, line number, precise address, etc. Not all information is
 * always available in a symbol, however, so all methods return an `Option`.
 */
public open class Symbol(
    // Note: this scope bound needs to be persisted eventually to `Symbol`,
    // but that's currently a breaking change. For now this is safe since
    // `Symbol` is only ever handed out by reference and can't be cloned.
    public var inner: io.github.kotlinmania.rustlang.backtrace.symbolize.noop.Symbol,
) {
    /**
     * Returns the name of this function.
     *
     * The returned structure can be used to query various properties about the
     * symbol name:
     *
     * * The `Display` implementation will print out the demangled symbol.
     * * The raw `String` value of the symbol can be accessed (if it's valid
     *   utf-8).
     * * The raw bytes for the symbol name can be accessed.
     */
    public open fun name(): SymbolName? = this.inner.name()

    /**
     * Returns the starting address of this function.
     */
    public open fun addr(): MutPtr? = this.inner.addr()

    /**
     * Returns the raw filename as a slice. This is mainly useful for `noStd`
     * environments.
     */
    public open fun filenameRaw(): BytesOrWideString? = this.inner.filenameRaw()

    /**
     * Returns the column number for where this symbol is currently executing.
     *
     * Only gimli currently provides a value here and even then only if `filename`
     * returns `Some`, and so it is then consequently subject to similar caveats.
     */
    public open fun colno(): UInt? = this.inner.colno()

    /**
     * Returns the line number for where this symbol is currently executing.
     *
     * This return value is typically `Some` if `filename` returns `Some`, and
     * is consequently subject to similar caveats.
     */
    public open fun lineno(): UInt? = this.inner.lineno()

    /**
     * Returns the file name where this function was defined.
     *
     * This is currently only available when libbacktrace or gimli is being
     * used (e.g. unix platforms other) and when a binary is compiled with
     * debuginfo. If neither of these conditions is met then this will likely
     * return `None`.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun filename(): Path? = this.inner.filename()

    public open fun fmt(f: StringBuilder) {
        val d = DebugStruct(f, "Symbol")
        when (val matchVal = this.name()) {
            null -> {}
            else -> {
                val name = matchVal
                d.field("name", name)
            }
        }
        when (val matchVal1 = this.addr()) {
            null -> {}
            else -> {
                val addr = matchVal1
                d.field("addr", addr)
            }
        }
        when (val matchVal2 = this.filename()) {
            null -> {}
            else -> {
                val filename = matchVal2
                d.field("filename", filename)
            }
        }
        when (val matchVal3 = this.lineno()) {
            null -> {}
            else -> {
                val lineno = matchVal3
                d.field("lineno", lineno)
            }
        }
        d.finish()
    }
}

/**
 * A wrapper around a symbol name to provide ergonomic accessors to the
 * demangled name, the raw bytes, the raw string, etc.
 */
public open class SymbolName(
    public var bytes: List<UByte>,
    public var demangled: Demangle?,
) {
    /**
     * Returns the raw (mangled) symbol name as a `String` if the symbol is valid utf-8.
     *
     * Use the `Display` implementation if you want the demangled version.
     */
    public open fun asStr(): String? = (this.demangled?.let({ s -> s.asStr() }) ?: String.fromUtf8(this.bytes).ok())

    /**
     * Returns the raw symbol name as a list of bytes
     */
    public open fun asBytes(): List<UByte> = this.bytes

    public open fun fmt(f: StringBuilder) {
        when (val matchVal = this.demangled) {
            null -> {}
            else -> {
                val s = matchVal
                return s.fmt(f)
            }
        }
        formatSymbolName(Display::fmt, this.bytes, f)
    }

    public open fun fmtDebug(f: StringBuilder) {
        when (val matchVal = this.demangled) {
            null -> {}
            else -> {
                val s = matchVal
                return s.fmt(f)
            }
        }
        formatSymbolName(Debug::fmt, this.bytes, f)
    }

    public companion object {
        /**
         * Creates a new symbol name from the raw underlying bytes.
         */
        public fun new(bytes: List<UByte>): SymbolName {
            val strBytes = String.fromUtf8(bytes).ok()
            val demangled = strBytes.andThen({ s -> tryDemangle(s).ok() })
            return SymbolName(bytes = bytes, demangled = demangled, cppDemangled = cpp)
        }
    }
}
