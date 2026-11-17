package io.github.kotlinmania.rustlang.core.fmt

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.Ref
import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.cell.RefMut
import io.github.kotlinmania.rustlang.core.cell.SyncUnsafeCell
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.char.methods.EscapeDebugExtArgs
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugStruct
import io.github.kotlinmania.rustlang.core.fmt.builders.DebugTuple
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.exposeProvenance
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

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

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Takes an output stream and an `Arguments` struct that can be precompiled with
 * the `formatArgs` macro.
 *
 * The arguments will be formatted according to the specified format string
 * into the output stream provided.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.fmt
 *
 * var output = ""
 * fmt.write(output, "Hello " + ("world").toString() + "!").expect("writing to a `String` should not fail")
 * check(output == "Hello world!")
 * ```
 *
 * Please note that using [write] might be preferable. Example:
 *
 * ```kotlin
 * var output = ""
 * output.append("Hello " + ("world").toString() + "!").expect("writing to a `String` should not fail")
 * check(output == "Hello world!")
 * ```
 */
public fun write(output: Write, fmt: Arguments): Result {
    when (val matchVal = fmt.asStr()) {
        null -> {}
        else -> {
            val s = matchVal
            return run { output.append(s); RustResult.Ok<Unit, Error>(Unit) }
        }
    }
    var template = fmt.template
    val args = fmt.args
    var argIndex = 0
    // See comment on `fmt.Arguments` for the details of how the template is encoded.
    // This must match the encoding from `expandFormatArgs` in
    // compiler/rustcAstLowering/src/format.rs.
    while (true) {
        // SAFETY: We can assume the template is valid.
        val n1 =
            run {
                val n = template.read()
                template = template.add(1)
                n
            }
        if (n1 == 0) {
            // End of template.
            return RustResult.Ok(Unit)
        } else if (n1 < 0x80) {
            // Literal string piece of length `n`.
            // SAFETY: We can assume the strings in the template are valid.
            val s2 =
                run {
                    val s1 = linearSliceView(template.asPtr(), n1.toInt(), PrimitiveLayouts.U8)
                    template = template.add(n1.toInt())
                    s1
                }
            output.append(s2)
        } else if (n1 == 0x80) {
            // Literal string piece with a 16-bit length.
            // SAFETY: We can assume the strings in the template are valid.
            val s4 =
                run {
                    val len = Int.from(run { val bytesTmp1 = template.castArray().read(); ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
                    template = template.add(2)
                    val s3 = linearSliceView(template.asPtr(), len.toInt(), PrimitiveLayouts.U8)
                    template = template.add(len)
                    s3
                }
            output.append(s4)
        } else if (n1 == 0xC0) {
            // Note for next argument with default options.
            //
            // Having this as a separate case improves performance for the common case.
            // SAFETY: We can assume the template only refers to arguments that exist.
            run {
                when (val tryResult = args.add(argIndex).asRef().fmt(Formatter.new(output, FormattingOptions.new()))) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            }
            argIndex += 1
        } else {
            // SAFETY: We can assume the template is valid.
            assertUnchecked(n1 > 0xC0)
            // Note with custom options.
            val opt = FormattingOptions.new()
            // SAFETY: We can assume the template is valid.
            run {
                if (n1 and 1 != 0) {
                    opt.flags = run { val bytesTmp2 = template.castArray().read(); ((bytesTmp2[3].toUInt() shl 24) or (bytesTmp2[2].toUInt() shl 16) or (bytesTmp2[1].toUInt() shl 8) or bytesTmp2[0].toUInt()) }
                    template = template.add(4)
                }
                if (n1 and 2 != 0) {
                    opt.width = run { val bytesTmp3 = template.castArray().read(); ((bytesTmp3[1].toInt() shl 8) or bytesTmp3[0].toInt()).toUShort() }
                    template = template.add(2)
                }
                if (n1 and 4 != 0) {
                    opt.precision = run { val bytesTmp4 = template.castArray().read(); ((bytesTmp4[1].toInt() shl 8) or bytesTmp4[0].toInt()).toUShort() }
                    template = template.add(2)
                }
                if (n1 and 8 != 0) {
                    argIndex = Int.from(run { val bytesTmp6 = template.castArray().read(); ((bytesTmp6[1].toInt() shl 8) or bytesTmp6[0].toInt()).toUShort() })
                    template = template.add(2)
                }
            }
            if (n1 and 16 != 0) {
                // Dynamic width from a Int argument.
                // SAFETY: We can assume the template only refers to arguments that exist.
                run {
                    opt.width = args.add(opt.width.toInt()).asRef().asU16().unwrapUnchecked()
                }
            }
            if (n1 and 32 != 0) {
                // Dynamic precision from a Int argument.
                // SAFETY: We can assume the template only refers to arguments that exist.
                run {
                    opt.precision = args.add(opt.precision.toInt()).asRef().asU16().unwrapUnchecked()
                }
            }
            // SAFETY: We can assume the template only refers to arguments that exist.
            run {
                when (val tryResult = args.add(argIndex).asRef().fmt(Formatter.new(output, opt))) { is RustResult.Ok<*, *> -> tryResult.value; is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.error) }
            }
            argIndex += 1
        }
    }
}

/**
 * Like `.split(' ')`, but doesn't bloat binary size
 */
public fun splitStrBySpace(s: String): Any = s.encodeToByteArray().map { it.toUByte() }.split({ b -> b == 32u }).map({ bytes -> String.fromUtf8Unchecked(bytes) })

/**
 * Formats an address in `fmt.Pointer` style.
 *
 * This uses `ptrAddr: Int` and not `ptr: * const()` to be able to use this for
 * `fun(...) -> ...` without using [problematic] "Oxford Casts".
 *
 * [problematic]: https://github.com/rust-lang/rust/issues/95489
 */
public fun pointerFmtInner(ptrAddr: Int, f: Formatter): Result {
    val oldOptions = f.options
    // The alternate flag is already treated by LowerHex as being special-
    // it denotes whether to prefix with 0x. We use it to work out whether
    // or not to zero extend, and then unconditionally set it to get the
    // prefix.
    if (f.options.getAlternate()) {
        f.options.signAwareZeroPad(true)
        if (f.options.getWidth() == null) {
            f.options.width((32u / 4u).toUShort() + 2u)
        }
    }
    f.options.alternate(true)
    val ret = f.append((ptrAddr).toString())
    f.options = oldOptions
    return ret
}

/**
 * Formats a wide pointer (address and type-erased metadata) in `fmt.Pointer` style.
 */
public fun widePointerFmtInner(ptrAddr: Int, metadata: Debug, f: Formatter): Result = DebugStruct(f, "Pointer").fieldWith("addr", { f1 -> pointerFmtInner(ptrAddr, f1) }).field("metadata", metadata).finish()

public fun Nothing.fmt(arg0: Formatter): Result = this

public fun Boolean.fmt(f: Formatter): Result = f.append((this).toString())

public fun String.fmt(f: Formatter): Result {
    fun needsEscape(b: UByte): Boolean = b > 0x7Eu.toUByte() || b < 0x20u.toUByte() || b == 92u || b == 34u
    f.append('"')
    // substring we know is printable
    val printableRange = 0 until 0
    // the loop here first skips over runs of printable ASCII as a fast path.
    // other chars (unicode, or ASCII that needs escaping) are then handled per-`char`.
    var rest = this
    while (!rest.isEmpty()) {
        val nonPrintableStart = (rest.encodeToByteArray().map { it.toUByte() }).indexOfFirst({ b -> needsEscape(b) }).takeIf { it >= 0 } ?: run {
            printableRange.end += rest.length
            break
        }
        printableRange.end += nonPrintableStart
        // SAFETY: the position was derived from an iterator, so is known to be within bounds, and at a char boundary
        rest = rest.getUnchecked(nonPrintableStart until Int.MAX_VALUE)
        val chars = rest.chars()
        when (val matchVal = chars.next()) {
            null -> {}
            else -> {
                val c = matchVal
                val esc = c.escapeDebugExt(EscapeDebugExtArgs(escapeSingleQuote = false, escapeDoubleQuote = true))
                if (esc.len() != 1) {
                    f.append(this[printableRange.clone()])
                    f.append((esc).toString())
                    printableRange.start = printableRange.end + c.lenUtf8()
                }
                printableRange.end += c.lenUtf8()
            }
        }
        rest = chars.asStr()
    }
    f.append(this[printableRange])
    return f.append('"')
}

public fun Char.fmt(f: Formatter): Result {
    f.append('\'')
    val esc = this.escapeDebugExt(EscapeDebugExtArgs(escapeSingleQuote = true, escapeDoubleQuote = false))
    f.append((esc).toString())
    return f.append('\'')
}

public fun <T> ConstPtr.fmt(f: Formatter, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result {
    // Since the formatting will be identical for all pointer types, erase the pointee type and
    // metadata type to reduce the amount of codegen work needed for each distinct type.
    val ptr: ConstPtr = (typeLayoutT).readNative((this).rawAddress)
    val ptrAddr = ptr.exposeProvenance()
    return if (Metadata.IS_UNIT) {
        pointerFmtInner(ptrAddr, f)
    } else {
        widePointerFmtInner(ptrAddr, metadata(ptr), f)
    }
}

public fun <T> MutPtr.fmt(f: Formatter, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result = f.append(((constPtrFromRustCast((typeLayoutT).readNative((this).rawAddress)))).toString())

public fun <T> T.fmt(f: Formatter): Result = f.append(((constPtrFromRustCast(this))).toString())

// Implementation of Display/Debug for various core types
public fun ConstPtr.fmt(f: Formatter): Result = f.append((this).toString())

public fun MutPtr.fmt(f: Formatter): Result = f.append((this).toString())

public fun <E : Debug, D : Debug, C : Debug, B : Debug, A : Debug, Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmt(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (E, D, C, B, A, Z, Y, X, W, V, U, T) = this
    builder.field(E)
    builder.field(D)
    builder.field(C)
    builder.field(B)
    builder.field(A)
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <D : Debug, C : Debug, B : Debug, A : Debug, Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromDCBAZYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (D, C, B, A, Z, Y, X, W, V, U, T) = this
    builder.field(D)
    builder.field(C)
    builder.field(B)
    builder.field(A)
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <C : Debug, B : Debug, A : Debug, Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromCBAZYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (C, B, A, Z, Y, X, W, V, U, T) = this
    builder.field(C)
    builder.field(B)
    builder.field(A)
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <B : Debug, A : Debug, Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromBAZYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (B, A, Z, Y, X, W, V, U, T) = this
    builder.field(B)
    builder.field(A)
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <A : Debug, Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromAZYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (A, Z, Y, X, W, V, U, T) = this
    builder.field(A)
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <Z : Debug, Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromZYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (Z, Y, X, W, V, U, T) = this
    builder.field(Z)
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <Y : Debug, X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromYXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (Y, X, W, V, U, T) = this
    builder.field(Y)
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <X : Debug, W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromXWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (X, W, V, U, T) = this
    builder.field(X)
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <W : Debug, V : Debug, U : Debug, T : Debug> List<Any?>.fmtFromWVUT(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (W, V, U, T) = this
    builder.field(W)
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <V : Debug, U : Debug, T : Debug> Triple<V, U, T>.fmt(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (V, U, T) = this
    builder.field(V)
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <U : Debug, T : Debug> Pair<U, T>.fmt(f: Formatter): Result {
    val builder = DebugTuple(f, "")
    val (U, T) = this
    builder.field(U)
    builder.field(T)
    return builder.finish()
}

public fun <T : Debug> List<T>.fmtFromTSlice(f: Formatter): Result = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter()).finish()

public fun Unit.fmt(f: Formatter): Result = f.append("()")

public fun <T : Debug> Cell<T>.fmt(f: Formatter): Result = DebugStruct(f, "Cell").field("value", this.get()).finish()

public fun <T : Debug> RefCell<T>.fmt(f: Formatter): Result {
    val d = DebugStruct(f, "RefCell")
    run {
        val matchVal = this.tryBorrow()
        when (matchVal) { is RustResult.Ok<*, *> -> {
            val borrow = matchVal.value
            d.field("value", borrow)
        }; is RustResult.Err<*, *> -> {
            d.field("value", "<borrowed>")
        } }
    }
    return d.finish()
}

public fun <T : Debug> Ref<T>.fmt(f: Formatter): Result = f.append(((this).value).toString())

public fun <T : Debug> RefMut<T>.fmt(f: Formatter): Result = f.append((this.deref()).toString())

public fun <T> UnsafeCell<T>.fmt(f: Formatter): Result = DebugStruct(f, "UnsafeCell").finishNonExhaustive()

public fun <T> SyncUnsafeCell<T>.fmt(f: Formatter): Result = DebugStruct(f, "SyncUnsafeCell").finishNonExhaustive()
