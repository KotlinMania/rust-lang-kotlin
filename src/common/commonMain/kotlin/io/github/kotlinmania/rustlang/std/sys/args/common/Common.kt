// port-lint: source sys/args/common.rs
package io.github.kotlinmania.rustlang.std.sys.args.common

import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString

public open class Args(
    public var iter: ArrayDeque<OsString>,
) : Iterable<OsString> {
    override fun iterator(): Iterator<OsString> = object : AbstractIterator<OsString>() {
        override fun computeNext() {
            when (val v = this@Args.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        this.iter.toList().fmt(f)
    }

    public open fun next(): OsString? = this.iter.removeFirstOrNull()

    public open fun nextChunk(n: Int): Result<List<OsString>, IntoIter<OsString>> = this.iter.nextChunk()

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun count(): Int = this.iter.size

    public open fun last(): OsString? = this.iter.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBy(n)

    public open fun <B, R> tryFold(init: B, f: (B, OsString) -> R): R where R : Try<B> = this.iter.tryFold(init, f)

    public open fun <B> fold(init: B, f: (B, OsString) -> B): B = this.iter.fold(init, f)

    public open fun nextBack(): OsString? = this.iter.removeLastOrNull()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBackBy(n)

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        public fun new(args: List<OsString>): Args = Args(iter = ArrayDeque((args).toList()))
    }
}
