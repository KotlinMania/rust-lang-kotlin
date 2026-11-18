// port-lint: source fmt/builders.rs
package io.github.kotlinmania.rustlang.core.fmt.builders

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.default.Default
import io.github.kotlinmania.rustlang.core.default.default
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen

public open class PadAdapter(
    public var buf: Appendable,
    public var state: PadAdapterState,
) : Appendable {
    public open fun writeStr(s: String): Result<Unit, Unit> {
        for (s1 in s.splitInclusive('\n')) {
            if (this.state.onNewline) {
                this.buf.append("    ")
            }
            this.state.onNewline = s1.endsWith('\n')
            this.buf.append(s1)
        }
        return Result.Ok<Unit, Unit>(Unit)
    }

    public open fun writeChar(c: Char): Result<Unit, Unit> {
        if (this.state.onNewline) {
            this.buf.append("    ")
        }
        this.state.onNewline = c == '\n'
        return run { this.buf.append(c); Result.Ok<Unit, Unit>(Unit) }
    }

    public companion object {
        public fun wrap(fmt: StringBuilder, slot: PadAdapter?, state: PadAdapterState): StringBuilder = fmt.wrapBuf({ buf -> slot.insert(PadAdapter(buf = buf, state = state)) })
    }
}

public open class PadAdapterState(
    public var onNewline: Boolean,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PadAdapterState> =
            object : TypeLayout<PadAdapterState> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): PadAdapterState =
                    PadAdapterState(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PadAdapterState) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.onNewline)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PadAdapterState =
                    PadAdapterState(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PadAdapterState) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.onNewline)
                }
            }

        public fun default(): PadAdapterState = PadAdapterState(onNewline = true)
    }
}

/**
 * Wraps an `FnOnce` formatting closure in a type that implements [fmt.Debug] by calling the
 * closure, allowing the `* _with` builder methods to forward to their `dynamic fmt.Debug`
 * counterparts.
 *
 * By doing this, the builder logic is monomorphized only once and not for every closure type
 * (see #149745).
 *
 * Formatting a `DebugOnce` consumes the closure, so attempting to format it more than once
 * panics. This never happens because the debug builders format each value exactly once.
 */
public open class DebugOnce<F>(
    public var value: Cell<F?>,
) {
    public var field0: Cell<F?>
        get() = value
        set(v) {
            value = v
        }
}

public fun DebugOnce<F>.fmt(f: StringBuilder): Result<Unit, Unit> =
    when (val matchVal = this.value.also { this.value = null }) {
        null -> error("formatting closure called more than once")
        else -> {
            val valueFmt = matchVal
            valueFmt(f)
        }
    }

/**
 * A struct to help with [`fmt.Debug`](Debug) implementations.
 *
 * This is useful when you wish to output a formatted struct as a part of your
 * [Debug.fmt] implementation.
 *
 * This can be constructed by the [Formatter.debugStruct] method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Foo(public var bar: Int, public var baz: String) {
 *     public open fun fmt(fmt: StringBuilder) {
 *         DebugStruct(fmt, "Foo").field("bar", this.bar).field("baz", this.baz).finish()
 *     }
 * }
 * check((Foo(bar = 10, baz = "Hello World".toString())).toString() == "Foo { bar: 10, baz: \"Hello World\" }")
 * ```
 */
public open class DebugStruct internal constructor(
    public var fmt: StringBuilder,
    resultCtorArg: Result<Unit, Error>,
    public var hasFields: Boolean,
) {
    public var result: Result<Unit, Error> = resultCtorArg
        internal set

    /**
     * Adds a new field to the generated struct output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Bar(public var bar: Int, public var another: String) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         DebugStruct(fmt, "Bar").field("bar", this.bar).field("another", this.another).field("nonexistentField", 1).finish()
     *         // We're good to go!
     *     }
     * }
     * check((Bar(bar = 10, another = "Hello World".toString())).toString() == "Bar { bar: 10, another: \"Hello World\", nonexistentField: 1 }")
     * ```
     */
    public open fun field(name: String, value: Any): DebugStruct {
        this.result = this.result.andThen(closure@{
            if (this.isPretty()) {
                if (!this.hasFields) {
                    this.fmt.append(" {\n")
                }
                val slot = null
                val state = Default.default()
                val writer = PadAdapter.wrap(this.fmt, slot, state)
                writer.append(name)
                writer.append(": ")
                when (val tryResult = value.fmt(writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
                writer.append(",\n")
            } else {
                val prefix =
                    if (this.hasFields) {
                        ", "
                    } else {
                        " { "
                    }
                this.fmt.append(prefix)
                this.fmt.append(name)
                this.fmt.append(": ")
                value.fmt(this.fmt)
            }
        })
        this.hasFields = true
        return this
    }

    /**
     * Adds a new field to the generated struct output.
     *
     * This method is equivalent to [DebugStruct.field], but formats the
     * value using a provided closure rather than by calling [Debug.fmt].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Bar(public var bar: Int, public var another: String) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         DebugStruct(fmt, "Bar").fieldWith("bar", { fmt1 -> fmt1.append("${this.bar}") }).field("another", this.another).finish()
     *     }
     * }
     * check((Bar(bar = 10, another = "Hello World".toString())).toString() == "Bar { bar: 0x0000000a, another: \"Hello World\" }")
     * ```
     */
    public open fun fieldWith(name: String, valueFmt: (StringBuilder) -> Result<Unit, Error>): DebugStruct = this.field(name, DebugOnce(Cell.new(valueFmt)))

    /**
     * Marks the struct as non-exhaustive, indicating to the reader that there are some other
     * fields that are not shown in the debug representation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Bar(public var bar: Int, public var hidden: Float) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         DebugStruct(fmt, "Bar").field("bar", this.bar).finishNonExhaustive()
     *         // Show that some other field(s) exist.
     *     }
     * }
     * check("${Bar(bar = 10, hidden = 1.0f)}" == "Bar { bar: 10, .. }")
     * ```
     */
    public open fun finishNonExhaustive(): Result<Unit, Error> {
        this.result = this.result.andThen(closure1@{
            if (this.hasFields) {
                if (this.isPretty()) {
                    val slot = null
                    val state = Default.default()
                    val writer = PadAdapter.wrap(this.fmt, slot, state)
                    writer.append("..\n")
                    this.fmt.append("}")
                } else {
                    this.fmt.append(", .. }")
                }
            } else {
                this.fmt.append(" { .. }")
            }
        })
        return this.result
    }

    /**
     * Finishes output and returns any error encountered.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Bar(public var bar: Int, public var baz: String) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         DebugStruct(fmt, "Bar").field("bar", this.bar).field("baz", this.baz).finish()
     *         // You need to call it to "finish" the
     *         // struct formatting.
     *     }
     * }
     * check((Bar(bar = 10, baz = "Hello World".toString())).toString() == "Bar { bar: 10, baz: \"Hello World\" }")
     * ```
     */
    public open fun finish(): Result<Unit, Error> {
        if (this.hasFields) {
            this.result = this.result.andThen({
                if (this.isPretty()) {
                    this.fmt.append("}")
                } else {
                    this.fmt.append(" }")
                }
            })
        }
        return this.result
    }

    public open fun isPretty(): Boolean = this.fmt.alternate()

    public companion object {
        public operator fun invoke(
            fmt: StringBuilder,
            result: Result<Unit, Error>,
            hasFields: Boolean,
        ): DebugStruct = DebugStruct(
            fmt = fmt,
            resultCtorArg = result,
            hasFields = hasFields,
        )
    }
}

public fun debugStructNew(fmt: StringBuilder, name: String): DebugStruct {
    val result = fmt.append(name)
    return DebugStruct(fmt = fmt, result = result, hasFields = false)
}

/**
 * A struct to help with [`fmt.Debug`](Debug) implementations.
 *
 * This is useful when you wish to output a formatted tuple as a part of your
 * [Debug.fmt] implementation.
 *
 * This can be constructed by the [Formatter.debugTuple] method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * public class DebugTuple(
 *     private val out: StringBuilder,
 *     private val name: String,
 * ) {
 *     private val fields: MutableList<String> = mutableListOf()
 *
 *     public fun field(value: Any?): DebugTuple =
 *         apply {
 *             fields.add(value.toString())
 *         }
 *
 *     public fun finish() {
 *         if (fields.isEmpty()) {
 *             out.append(name)
 *         } else if (name.isEmpty()) {
 *             out.append("(${fields.joinToString(", ")})")
 *         } else {
 *             out.append("$name(${fields.joinToString(", ")})")
 *         }
 *     }
 * }
 *
 * public fun doctestEntry() {
 *     open class Foo(public var field0: Int, public var field1: String) {
 *         public open fun fmt(fmt: StringBuilder) {
 *             DebugTuple(fmt, "Foo").field(this.field0).field(this.field1).finish()
 *         }
 *     }
 *     check((Foo(10, "Hello World".toString())).toString() == "Foo(10, \"Hello World\")")
 * }
 * ```
 */
public open class DebugTuple internal constructor(
    public var fmt: StringBuilder,
    resultCtorArg: Result<Unit, Error>,
    public var fields: Int,
    public var emptyName: Boolean,
) {
    public var result: Result<Unit, Error> = resultCtorArg
        internal set

    /**
     * Adds a new field to the generated tuple struct output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Foo(public var field0: Int, public var field1: String) {
     *         public open fun fmt(fmt: StringBuilder) {
     *             DebugTuple(fmt, "Foo").field(this.field0).field(this.field1).finish()
     *             // We're good to go!
     *         }
     *     }
     *     check((Foo(10, "Hello World".toString())).toString() == "Foo(10, \"Hello World\")")
     * }
     * ```
     */
    public open fun field(value: Any): DebugTuple {
        this.result = this.result.andThen(closure2@{
            if (this.isPretty()) {
                if (this.fields == 0) {
                    this.fmt.append("(\n")
                }
                val slot = null
                val state = Default.default()
                val writer = PadAdapter.wrap(this.fmt, slot, state)
                when (val tryResult = value.fmt(writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure2 Result.Err(tryResult.errValue()) }
                writer.append(",\n")
            } else {
                val prefix =
                    if (this.fields == 0) {
                        "("
                    } else {
                        ", "
                    }
                this.fmt.append(prefix)
                value.fmt(this.fmt)
            }
        })
        this.fields += 1
        return this
    }

    /**
     * Adds a new field to the generated tuple struct output.
     *
     * This method is equivalent to [DebugTuple.field], but formats the
     * value using a provided closure rather than by calling [Debug.fmt].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Foo(public var field0: Int, public var field1: String) {
     *         public open fun fmt(fmt: StringBuilder) {
     *             DebugTuple(fmt, "Foo").fieldWith({ fmt1 -> fmt1.append("${this.field0}") }).field(this.field1).finish()
     *         }
     *     }
     *     check((Foo(10, "Hello World".toString())).toString() == "Foo(0x0000000a, \"Hello World\")")
     * }
     * ```
     */
    public open fun fieldWith(valueFmt: (StringBuilder) -> Result<Unit, Error>): DebugTuple = this.field(DebugOnce(Cell.new(valueFmt)))

    /**
     * Marks the tuple struct as non-exhaustive, indicating to the reader that there are some
     * other fields that are not shown in the debug representation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Foo(public var field0: Int, public var field1: String) {
     *         public open fun fmt(fmt: StringBuilder) {
     *             DebugTuple(fmt, "Foo").field(this.field0).finishNonExhaustive()
     *             // Show that some other field(s) exist.
     *         }
     *     }
     *     check((Foo(10, "secret!".toOwned())).toString() == "Foo(10, ..)")
     * }
     * ```
     */
    public open fun finishNonExhaustive(): Result<Unit, Error> {
        this.result = this.result.andThen(closure3@{
            if (this.fields > 0) {
                if (this.isPretty()) {
                    val slot = null
                    val state = Default.default()
                    val writer = PadAdapter.wrap(this.fmt, slot, state)
                    writer.append("..\n")
                    this.fmt.append(")")
                } else {
                    this.fmt.append(", ..)")
                }
            } else {
                this.fmt.append("(..)")
            }
        })
        return this.result
    }

    /**
     * Finishes output and returns any error encountered.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Foo(public var field0: Int, public var field1: String) {
     *         public open fun fmt(fmt: StringBuilder) {
     *             DebugTuple(fmt, "Foo").field(this.field0).field(this.field1).finish()
     *             // You need to call it to "finish" the
     *             // tuple formatting.
     *         }
     *     }
     *     check((Foo(10, "Hello World".toString())).toString() == "Foo(10, \"Hello World\")")
     * }
     * ```
     */
    public open fun finish(): Result<Unit, Error> {
        if (this.fields > 0) {
            this.result = this.result.andThen(closure4@{
                if (this.fields == 1 && this.emptyName && !this.isPretty()) {
                    this.fmt.append(",")
                }
                this.fmt.append(")")
            })
        }
        return this.result
    }

    public open fun isPretty(): Boolean = this.fmt.alternate()

    public companion object {
        public operator fun invoke(
            fmt: StringBuilder,
            result: Result<Unit, Error>,
            fields: Int,
            emptyName: Boolean,
        ): DebugTuple = DebugTuple(
            fmt = fmt,
            resultCtorArg = result,
            fields = fields,
            emptyName = emptyName,
        )
    }
}

public fun debugTupleNew(fmt: StringBuilder, name: String): DebugTuple {
    val result = fmt.append(name)
    return DebugTuple(fmt = fmt, result = result, fields = 0, emptyName = name.isEmpty())
}

/**
 * A helper used to print list-like items with no special formatting.
 */
public open class DebugInner internal constructor(
    public var fmt: StringBuilder,
    resultCtorArg: Result<Unit, Error>,
    public var hasFields: Boolean,
) {
    public var result: Result<Unit, Error> = resultCtorArg
        internal set

    public open fun entry(entry: Any) {
        this.result = this.result.andThen(closure5@{
            if (this.isPretty()) {
                if (!this.hasFields) {
                    this.fmt.append("\n")
                }
                val slot = null
                val state = Default.default()
                val writer = PadAdapter.wrap(this.fmt, slot, state)
                when (val tryResult = entry.fmt(writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure5 Result.Err(tryResult.errValue()) }
                writer.append(",\n")
            } else {
                if (this.hasFields) {
                    this.fmt.append(", ")
                }
                entry.fmt(this.fmt)
            }
        })
        this.hasFields = true
    }

    public open fun entryWith(entryFmt: (StringBuilder) -> Result<Unit, Error>) {
        this.entry(DebugOnce(Cell.new(entryFmt)))
    }

    public open fun isPretty(): Boolean = this.fmt.alternate()

    public companion object {
        public operator fun invoke(
            fmt: StringBuilder,
            result: Result<Unit, Error>,
            hasFields: Boolean,
        ): DebugInner = DebugInner(
            fmt = fmt,
            resultCtorArg = result,
            hasFields = hasFields,
        )
    }
}

/**
 * A struct to help with [`fmt.Debug`](Debug) implementations.
 *
 * This is useful when you wish to output a formatted set of items as a part
 * of your [Debug.fmt] implementation.
 *
 * This can be constructed by the [Formatter.debugSet] method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Foo(public var value: List<Int>) {
 *     public var field0: List<Int>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(fmt: StringBuilder) {
 *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.iter()).finish()
 *     }
 * }
 * check("${Foo(mutableListOf(10, 11))}" == "{10, 11}")
 * ```
 */
public open class DebugSet(
    public var inner: DebugInner,
) {
    /**
     * Adds a new entry to the set output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entry(this.field0).entry(this.field1).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "{[10, 11], [12, 13]}")
     * ```
     */
    public open fun entry(entry: Any): DebugSet {
        this.inner.entry(entry)
        return this
    }

    /**
     * Adds a new entry to the set output.
     *
     * This method is equivalent to [DebugSet.entry], but formats the
     * entry using a provided closure rather than by calling [Debug.fmt].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entry(this.field0).entryWith({ fmt1 -> object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt1.append(entries.joinToString(", ", "{", "}")) } }.entries(this.field1).finish() }).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "{[10, 11], {12, 13}}")
     * ```
     */
    public open fun entryWith(entryFmt: (StringBuilder) -> Result<Unit, Error>): DebugSet {
        this.inner.entryWith(entryFmt)
        return this
    }

    /**
     * Adds the contents of an iterator of entries to the set output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.field0.iter()).entries(this.field1.iter()).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "{10, 11, 12, 13}")
     * ```
     */
    public open fun <D, I> entries(entries: I): DebugSet where I : Iterable<D> {
        for (entry in entries) {
            this.entry(entry)
        }
        return this
    }

    /**
     * Marks the set as non-exhaustive, indicating to the reader that there are some other
     * elements that are not shown in the debug representation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         // Print at most two elements, abbreviate the rest
     *         var f = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }
     *         var f1 = f.entries(this.value.iter().take(2))
     *         if (this.value.size > 2) {
     *             f1.finishNonExhaustive()
     *         } else {
     *             f1.finish()
     *         }
     *     }
     * }
     * check("${Foo(mutableListOf(1, 2, 3, 4))}" == "{1, 2, ..}")
     * ```
     */
    public open fun finishNonExhaustive(): Result<Unit, Error> {
        this.inner.result = this.inner.result.andThen(closure6@{
            if (this.inner.hasFields) {
                if (this.inner.isPretty()) {
                    val slot = null
                    val state = Default.default()
                    val writer = PadAdapter.wrap(this.inner.fmt, slot, state)
                    writer.append("..\n")
                    this.inner.fmt.append("}")
                } else {
                    this.inner.fmt.append(", ..}")
                }
            } else {
                this.inner.fmt.append("..}")
            }
        })
        return this.inner.result
    }

    /**
     * Finishes output and returns any error encountered.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.iter()).finish()
     *         // Ends the set formatting.
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11))}" == "{10, 11}")
     * ```
     */
    public open fun finish(): Result<Unit, Error> {
        this.inner.result = this.inner.result.andThen({ this.inner.fmt.append("}") })
        return this.inner.result
    }
}

public fun debugSetNew(fmt: StringBuilder): DebugSet {
    val result = fmt.append("{")
    return DebugSet(inner = DebugInner(fmt = fmt, result = result, hasFields = false))
}

/**
 * A struct to help with [`fmt.Debug`](Debug) implementations.
 *
 * This is useful when you wish to output a formatted list of items as a part
 * of your [Debug.fmt] implementation.
 *
 * This can be constructed by the [Formatter.debugList] method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Foo(public var value: List<Int>) {
 *     public var field0: List<Int>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(fmt: StringBuilder) {
 *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entries(this.value.iter()).finish()
 *     }
 * }
 * check("${Foo(mutableListOf(10, 11))}" == "[10, 11]")
 * ```
 */
public open class DebugList(
    public var inner: DebugInner,
) {
    /**
     * Adds a new entry to the list output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entry(this.field0).entry(this.field1).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "[[10, 11], [12, 13]]")
     * ```
     */
    public open fun entry(entry: Any): DebugList {
        this.inner.entry(entry)
        return this
    }

    /**
     * Adds a new entry to the list output.
     *
     * This method is equivalent to [DebugList.entry], but formats the
     * entry using a provided closure rather than by calling [Debug.fmt].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entry(this.field0).entryWith({ fmt1 -> object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt1.append(entries.joinToString(", ", "{", "}")) } }.entries(this.field1).finish() }).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "[[10, 11], {12, 13}]")
     * ```
     */
    public open fun entryWith(entryFmt: (StringBuilder) -> Result<Unit, Error>): DebugList {
        this.inner.entryWith(entryFmt)
        return this
    }

    /**
     * Adds the contents of an iterator of entries to the list output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var field0: List<Int>, public var field1: List<UInt>) {
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entries(this.field0.iter()).entries(this.field1.iter()).finish()
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11), mutableListOf(12u, 13u))}" == "[10, 11, 12, 13]")
     * ```
     */
    public open fun <D, I> entries(entries: I): DebugList where I : Iterable<D> {
        for (entry in entries) {
            this.entry(entry)
        }
        return this
    }

    /**
     * Marks the list as non-exhaustive, indicating to the reader that there are some other
     * elements that are not shown in the debug representation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         // Print at most two elements, abbreviate the rest
     *         var f = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }
     *         var f1 = f.entries(this.value.iter().take(2))
     *         if (this.value.size > 2) {
     *             f1.finishNonExhaustive()
     *         } else {
     *             f1.finish()
     *         }
     *     }
     * }
     * check("${Foo(mutableListOf(1, 2, 3, 4))}" == "[1, 2, ..]")
     * ```
     */
    public open fun finishNonExhaustive(): Result<Unit, Error> =
        this.inner.result.andThen(closure7@{
            if (this.inner.hasFields) {
                if (this.inner.isPretty()) {
                    val slot = null
                    val state = Default.default()
                    val writer = PadAdapter.wrap(this.inner.fmt, slot, state)
                    writer.append("..\n")
                    this.inner.fmt.append("]")
                } else {
                    this.inner.fmt.append(", ..]")
                }
            } else {
                this.inner.fmt.append("..]")
            }
        })

    /**
     * Finishes output and returns any error encountered.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Int>) {
     *     public var field0: List<Int>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { fmt.append(entries.joinToString(", ", "[", "]")) } }.entries(this.value.iter()).finish()
     *         // Ends the list formatting.
     *     }
     * }
     * check("${Foo(mutableListOf(10, 11))}" == "[10, 11]")
     * ```
     */
    public open fun finish(): Result<Unit, Error> {
        this.inner.result = this.inner.result.andThen({ this.inner.fmt.append("]") })
        return this.inner.result
    }
}

public fun debugListNew(fmt: StringBuilder): DebugList {
    val result = fmt.append("[")
    return DebugList(inner = DebugInner(fmt = fmt, result = result, hasFields = false))
}

/**
 * A struct to help with [`fmt.Debug`](Debug) implementations.
 *
 * This is useful when you wish to output a formatted map as a part of your
 * [Debug.fmt] implementation.
 *
 * This can be constructed by the [Formatter.debugMap] method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Foo(public var value: List<Pair<String, Int>>) {
 *     public var field0: List<Pair<String, Int>>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(fmt: StringBuilder) {
 *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.map({ (k, v) -> Pair(k, v) })).finish()
 *     }
 * }
 * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"A\": 10, \"B\": 11}")
 * ```
 */
public open class DebugMap internal constructor(
    public var fmt: StringBuilder,
    resultCtorArg: Result<Unit, Error>,
    public var hasFields: Boolean,
    public var hasKey: Boolean,
    // The state of newlines is tracked between keys and values
    public var state: PadAdapterState,
) {
    public var result: Result<Unit, Error> = resultCtorArg
        internal set

    /**
     * Adds a new entry to the map output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entry("whole", this.value).finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"whole\": [(\"A\", 10), (\"B\", 11)]}")
     * ```
     */
    public open fun entry(key: Any, value: Any): DebugMap = this.key(key).value(value)

    /**
     * Adds the key part of a new entry to the map output.
     *
     * This method, together with `value`, is an alternative to `entry` that
     * can be used when the complete entry isn't known upfront. Prefer the `entry`
     * method when it's possible to use.
     *
     * # Panics
     *
     * `key` or `keyWith` must be called before `value` or `valueWith`, and each
     * `key` call must be followed by a corresponding `value` call. Otherwise this
     * method will panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.key("whole").value(this.value).finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"whole\": [(\"A\", 10), (\"B\", 11)]}")
     * ```
     */
    public open fun key(key: Any): DebugMap {
        this.result = this.result.andThen(closure8@{
            check(!this.hasKey) { "attempted to begin a new map entry without completing the previous one" }
            if (this.isPretty()) {
                if (!this.hasFields) {
                    this.fmt.append("\n")
                }
                val slot = null
                this.state = PadAdapterState.default()
                val writer = PadAdapter.wrap(this.fmt, slot, this.state)
                when (val tryResult = key.fmt(writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
                writer.append(": ")
            } else {
                if (this.hasFields) {
                    this.fmt.append(", ")
                }
                when (val tryResult = key.fmt(this.fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
                this.fmt.append(": ")
            }
            this.hasKey = true
            Result.Ok<Unit, Error>(Unit)
        })
        return this
    }

    /**
     * Adds the key part of a new entry to the map output.
     *
     * This method is equivalent to [DebugMap.key], but formats the
     * key using a provided closure rather than by calling [Debug.fmt].
     *
     * # Panics
     *
     * `key` or `keyWith` must be called before `value` or `valueWith`, and each
     * `key` call must be followed by a corresponding `value` call. Otherwise this
     * method will panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         var map = object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }
     *         for ((k, v) in this.value) {
     *             // Append "entry" to each key
     *             map.keyWith({ fmt1 -> fmt1.append("entry $k") })
     *             // Write values as hex
     *             map.valueWith({ fmt1 -> fmt1.append("$v") })
     *         }
     *         map.finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{entry A: 0x0000000a, entry B: 0x0000000b}")
     * ```
     */
    public open fun keyWith(keyFmt: (StringBuilder) -> Result<Unit, Error>): DebugMap = this.key(DebugOnce(Cell.new(keyFmt)))

    /**
     * Adds the value part of a new entry to the map output.
     *
     * This method, together with `key`, is an alternative to `entry` that
     * can be used when the complete entry isn't known upfront. Prefer the `entry`
     * method when it's possible to use.
     *
     * # Panics
     *
     * `key` or `keyWith` must be called before `value` or `valueWith`, and each
     * `key` call must be followed by a corresponding `value` call. Otherwise this
     * method will panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.key("whole").value(this.value).finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"whole\": [(\"A\", 10), (\"B\", 11)]}")
     * ```
     */
    public open fun value(value: Any): DebugMap {
        this.result = this.result.andThen(closure9@{
            check(this.hasKey) { "attempted to format a map value before its key" }
            if (this.isPretty()) {
                val slot = null
                val writer = PadAdapter.wrap(this.fmt, slot, this.state)
                when (val tryResult = value.fmt(writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure9 Result.Err(tryResult.errValue()) }
                writer.append(",\n")
            } else {
                when (val tryResult = value.fmt(this.fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure9 Result.Err(tryResult.errValue()) }
            }
            this.hasKey = false
            Result.Ok<Unit, Error>(Unit)
        })
        this.hasFields = true
        return this
    }

    /**
     * Adds the value part of a new entry to the map output.
     *
     * This method is equivalent to [DebugMap.value], but formats the
     * value using a provided closure rather than by calling [Debug.fmt].
     *
     * # Panics
     *
     * `key` or `keyWith` must be called before `value` or `valueWith`, and each
     * `key` call must be followed by a corresponding `value` call. Otherwise this
     * method will panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         var map = object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }
     *         for ((k, v) in this.value) {
     *             // Append "entry" to each key
     *             map.keyWith({ fmt1 -> fmt1.append("entry $k") })
     *             // Write values as hex
     *             map.valueWith({ fmt1 -> fmt1.append("$v") })
     *         }
     *         map.finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{entry A: 0x0000000a, entry B: 0x0000000b}")
     * ```
     */
    public open fun valueWith(valueFmt: (StringBuilder) -> Result<Unit, Error>): DebugMap = this.value(DebugOnce(Cell.new(valueFmt)))

    /**
     * Adds the contents of an iterator of entries to the map output.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.map({ (k, v) -> Pair(k, v) })).finish()
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"A\": 10, \"B\": 11}")
     * ```
     */
    public open fun <K, V, I> entries(entries: I): DebugMap where I : Iterable<Pair<K, V>> {
        for ((k, v) in entries) {
            this.entry(k, v)
        }
        return this
    }

    /**
     * Marks the map as non-exhaustive, indicating to the reader that there are some other
     * entries that are not shown in the debug representation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         // Print at most two elements, abbreviate the rest
     *         var f = object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }
     *         var f1 = f.entries(this.value.iter().take(2)?.let({ (k, v) -> Pair(k, v) }))
     *         if (this.value.size > 2) {
     *             f1.finishNonExhaustive()
     *         } else {
     *             f1.finish()
     *         }
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11), Pair("C".toString(), 12)))).toString() == "{\"A\": 10, \"B\": 11, ..}")
     * ```
     */
    public open fun finishNonExhaustive(): Result<Unit, Error> {
        this.result = this.result.andThen(closure10@{
            check(!this.hasKey) { "attempted to finish a map with a partial entry" }
            if (this.hasFields) {
                if (this.isPretty()) {
                    val slot = null
                    val state = Default.default()
                    val writer = PadAdapter.wrap(this.fmt, slot, state)
                    writer.append("..\n")
                    this.fmt.append("}")
                } else {
                    this.fmt.append(", ..}")
                }
            } else {
                this.fmt.append("..}")
            }
        })
        return this.result
    }

    /**
     * Finishes output and returns any error encountered.
     *
     * # Panics
     *
     * `key` or `keyWith` must be called before `value` or `valueWith`, and each
     * `key` call must be followed by a corresponding `value` call. Otherwise this
     * method will panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Foo(public var value: List<Pair<String, Int>>) {
     *     public var field0: List<Pair<String, Int>>
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun fmt(fmt: StringBuilder) {
     *         object { val entries = mutableListOf<String>(); fun entry(k: Any?, v: Any?) = apply { entries.add("$k: $v") }; fun finish() { fmt.append(entries.joinToString(", ", "{", "}")) } }.entries(this.value.map({ (k, v) -> Pair(k, v) })).finish()
     *         // Ends the map formatting.
     *     }
     * }
     * check((Foo(mutableListOf(Pair("A".toString(), 10), Pair("B".toString(), 11)))).toString() == "{\"A\": 10, \"B\": 11}")
     * ```
     */
    public open fun finish(): Result<Unit, Error> {
        this.result = this.result.andThen({
            check(!this.hasKey) { "attempted to finish a map with a partial entry" }
            this.fmt.append("}")
        })
        return this.result
    }

    public open fun isPretty(): Boolean = this.fmt.alternate()

    public companion object {
        public operator fun invoke(
            fmt: StringBuilder,
            result: Result<Unit, Error>,
            hasFields: Boolean,
            hasKey: Boolean,
            state: PadAdapterState,
        ): DebugMap = DebugMap(
            fmt = fmt,
            resultCtorArg = result,
            hasFields = hasFields,
            hasKey = hasKey,
            state = state,
        )
    }
}

public fun debugMapNew(fmt: StringBuilder): DebugMap {
    val result = fmt.append("{")
    return DebugMap(fmt = fmt, result = result, hasFields = false, hasKey = false, state = PadAdapterState.default())
}

/**
 * Creates a type whose [fmt.Debug] and [fmt.Display] impls are
 * forwarded to the provided closure.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * val value = 'a'
 * check("$value" == "a")
 * check("$value" == "'a'")
 * val wrapped = fmt.fromFn({ f -> f.append("$value") })
 * check("$wrapped" == "'a'")
 * check("$wrapped" == "'a'")
 * ```
 */
public fun fromFn(f: (StringBuilder) -> Result<Unit, Error>): FromFn<(StringBuilder) -> Result<Unit, Error>> = FromFn(f)

/**
 * Implements [fmt.Debug] and [fmt.Display] via the provided closure.
 *
 * Created with [fromFn].
 */
public open class FromFn<F>(
    public var value: F,
) {
    public var field0: F
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <F> typeLayout(typeLayoutF: TypeLayout<F>): TypeLayout<FromFn<F>> =
            object : TypeLayout<FromFn<F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutF.sizeBytes, alignBytes)
                override fun readNative(addr: Long): FromFn<F> =
                    FromFn(
                        (typeLayoutF).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FromFn<F>) {
                    (typeLayoutF).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FromFn<F> =
                    FromFn(
                        (typeLayoutF).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FromFn<F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun FromFn<F>.fmt(f: StringBuilder): Result<Unit, Unit> = (this.value)(f)
