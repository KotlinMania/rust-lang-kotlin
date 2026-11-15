// port-lint: source sys/env/common.rs
package io.github.kotlinmania.rustlang.std.sys.env.common

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString

public open class Env(
    public var iter: ArrayDeque<Pair<OsString, OsString>>,
) : Iterable<Pair<OsString, OsString>> {
    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@Env.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.iter.toList()).finish()
    }

    public open fun next(): Pair<OsString, OsString>? = this.iter.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public companion object {
        public fun new(env: List<Pair<OsString, OsString>>): Env = Env(iter = ArrayDeque((env).toList()))
    }
}
