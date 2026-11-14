// port-lint: source sys/args/zkvm.rs
package io.github.kotlinmania.rustlang.std.sys.args.zkvm

import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.WORD_SIZE
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysAllocWords
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysArgc
import io.github.kotlinmania.rustlang.std.sys.pal.zkvm.abi.sysArgv

public fun args(): Args = Args(iter = ARGS.getOrInit({ getArgs() }).iter())

public fun getArgs(): List<OsStr> {
    val argc = sysArgc()
    val args = ArrayList<OsStr>(argc)
    for (i in 0 until argc) {
        // Get the size of the argument then the data.
        val argLen = sysArgv(MutPtr(0L), 0, i)
        val argLenWords = (argLen + WORD_SIZE - 1) / WORD_SIZE
        val words = sysAllocWords(argLenWords)
        val argLen2 = sysArgv(words, argLenWords, i)
        check(argLen == argLen2)
        val argBytes = Slice.fromRawParts(words, argLen)
        args.add(OsStr.fromEncodedBytesUnchecked(argBytes))
    }
    return args
}

public var ARGS: OnceLock<List<OsStr>> = OnceLock.new()

public open class Args(
    public var iter: ArrayDeque<OsStr>,
) : Iterable<OsString> {
    override fun iterator(): kotlin.collections.Iterator<OsString> = object : AbstractIterator<OsString>() {
        override fun computeNext() {
            when (val v = this@Args.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder) {
        this.iter.asSlice().fmt(f)
    }

    public open fun next(): OsString? = this.iter.removeFirstOrNull()?.let({ arg -> arg.toOsString() })

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun count(): Int = this.iter.size

    public open fun last(): OsString? = this.iter.last()?.let({ arg -> arg.toOsString() })

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBy(n)

    public open fun nextBack(): OsString? = this.iter.removeLastOrNull()?.let({ arg -> arg.toOsString() })

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBackBy(n)

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()
}
