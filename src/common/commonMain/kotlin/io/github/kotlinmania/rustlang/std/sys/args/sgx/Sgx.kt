// port-lint: source sys/args/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.args.sgx

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.alloc.sgx.alloc
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Buf
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput

// Specifying linkage/symbol name is solely to ensure a single instance between this crate and its unit tests
public var ARGS: OnceLock<List<OsString>> = OnceLock.new()

public fun init(argc: Int, argv: ConstPtr) {
    if (argc != 0) {
        val args = alloc.User.fromRawParts(((argv as Any?) as Unit), ((argc as Any?) as Unit))
        val args1 = args.iter().map({ a -> OsString.fromInner(Buf(inner = a.copyUserBuffer())) }).collect<Vec>()
        when (val matchVal = ARGS.set(args1)) {
            is Err -> {
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal1
                            Write.writeFmt(out, "fatal runtime error: " + ("init called twice").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("init called twice").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            else -> {}
        }
    }
}

public fun args(): Args = Args(iter = ARGS.get().mapOrDefault({ args -> args.iter() }))

public open class Args(
    public var iter: ArrayDeque<OsString>,
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

    public open fun next(): OsString? = this.iter.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun count(): Int = this.iter.size

    public open fun last(): OsString? = this.iter.last()

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBy(n)

    public open fun <B, R> tryFold(init: B, f: (B, OsString) -> R): R where R : Try<B> = this.iter.byRef().cloned().tryFold(init, f)

    public open fun <B> fold(init: B, f: (B, OsString) -> B): B = this.iter.cloned().fold(init, f)

    public open fun nextBack(): OsString? = this.iter.removeLastOrNull()

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.iter.advanceBackBy(n)

    public open fun len(): Int = this.iter.size

    public open fun isEmpty(): Boolean = this.iter.isEmpty()
}
