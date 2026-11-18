// port-lint: source num/imp/x87_f80.rs
package io.github.kotlinmania.rustlang.core.num.imp.x87f80

import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.fmt.Result
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

private class DebugTuple(
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
 * The raw x87 80-bit extended-precision floating-point type.
 *
 * This type has a size of 80 bits, and corresponds to the store size (what a load/store touches)
 * of x87F80. This is different to what LLVM calls the alloc size, the number of bytes used to
 * calculate field offsets or the size of an alloca.
 *
 * The `x87F80` wrapper type below corresponds to the alloc size: it applies the target-specific
 * alignment, raising the total size to 12 or 16.
 *
 * NOTE: this type should not be used anywhere except in the x87F80 wrapper below. The backend
 * can handle it fine, but callconv calculation will likely panic.
 */
public open class X87F80Raw {
    public open fun clone(): X87F80Raw = X87F80Raw()

    public companion object {
        public fun typeLayout(): TypeLayout<X87F80Raw> =
            object : TypeLayout<X87F80Raw> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): X87F80Raw =
                    X87F80Raw()
                override fun writeNative(addr: Long, value: X87F80Raw) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): X87F80Raw =
                    X87F80Raw()
                override fun writeBytes(buf: ByteArray, offset: Int, value: X87F80Raw) {
                }
            }
    }
}

/**
 * The x87 80-bit extended-precision floating-point lang item.
 *
 * Here we define just the lang item, the public-facing type is defined
 * in coreArch. It transparently wraps this definition.
 *
 * This x87F80 type should only be used on x86 and x8664 targets, but is always defined
 * for the purpose of doc builds, specifically doc builds in the stdarch repo.
 */
public open class X87F80(
    public var value: X87F80Raw,
) {
    public var field0: X87F80Raw
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): X87F80 = X87F80(value)

    public open fun fmt(f: Formatter): Result = DebugTuple(f, "x87_f80").finish()

    public companion object {
        public fun typeLayout(): TypeLayout<X87F80> =
            object : TypeLayout<X87F80> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): X87F80 =
                    X87F80(
                        (X87F80Raw.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: X87F80) {
                    (X87F80Raw.typeLayout()).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): X87F80 =
                    X87F80(
                        (X87F80Raw.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: X87F80) {
                    (X87F80Raw.typeLayout()).writeBytes(buf, offset, value.value)
                }
            }
    }
}
