// port-lint: source sys/args/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.args.unsupported

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.ffi.osstr.OsString

public open class Args : Iterable<OsString> {
    override fun iterator(): Iterator<OsString> = object : AbstractIterator<OsString>() {
        override fun computeNext() {
            when (val v = this@Args.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.finish()
    }

    public open fun next(): OsString? = null

    public open fun sizeHint(): Pair<Int, Int?> = Pair(0, 0)

    public open fun nextBack(): OsString? = null

    public open fun len(): Int = 0

    public companion object {
        public fun typeLayout(): TypeLayout<Args> =
            object : TypeLayout<Args> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Args =
                    Args()
                override fun writeNative(addr: Long, value: Args) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Args =
                    Args()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Args) {
                }
            }
    }
}

public fun args(): Args = Args()
