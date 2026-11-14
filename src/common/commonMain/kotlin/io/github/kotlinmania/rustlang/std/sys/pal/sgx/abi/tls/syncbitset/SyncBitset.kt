// port-lint: source sys/pal/sgx/abi/tls/sync_bitset.rs
package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.syncbitset

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.iter.adapters.enumerate.Enumerate
import io.github.kotlinmania.rustlang.core.iter.adapters.peekable.Peekable
import io.github.kotlinmania.rustlang.core.num.rustWrappingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.USIZE_BITS
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.alloc.Iter
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput

/**
 * A bitset that can be used synchronously.
 */
internal open class SyncBitset(
    public var value: List<Atomic<Int>>,
) {
    public var field0: List<Atomic<Int>>
        get() = value
        set(v) {
            value = v
        }

    public open fun innerGet(idx: Int): Atomic<Int> =
        run {
            val matchVal = this.value.get(idx)
            when {
                matchVal != null -> {
                    val v = matchVal
                    v
                }
                else -> {
                    val err = matchVal
                    val err1 = err.asRef().map(drop)
                    run {
                        when (val matchVal1 = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal1
                                Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: self . 0 . get (idx) = $err1").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("unwrap failed: self . 0 . get (idx) = $err1").toString() + ", aborting\n"
                        }
                        abort()
                    }
                }
            }
        }

    public open fun get(index: Int): Boolean {
        val (hi, lo) = SyncBitset.split(index)
        return ((this.innerGet(hi).load(Ordering.Relaxed) and lo) != 0).toUByte()
    }

    /**
     * Not atomic.
     */
    public open fun iter(): SyncBitsetIter = SyncBitsetIter(iter = this.value.mapIndexed { index, item -> Pair(index, item) }.peekable(), elemIdx = 0)

    public open fun clear(index: Int) {
        val (hi, lo) = SyncBitset.split(index)
        this.innerGet(hi).fetchAnd(!lo, Ordering.Relaxed)
    }

    /**
     * Sets any unset bit. Not atomic. Returns `None` if all bits were
     * observed to be set.
     */
    public open fun set(): Int? {
        for ((idx, elem) in this.value.mapIndexed { index, item -> Pair(index, item) }) {
            var current = elem.load(Ordering.Relaxed)
            while (true) {
                if (0 == !current) {
                    continue
                }
                val trailingOnes = (!current).trailingZeros().toInt()
                run {
                    val matchVal = elem.compareExchange(current, current or (1 shl trailingOnes), Ordering.AcqRel, Ordering.Relaxed)
                    when (matchVal) { is Result.Ok<*> -> {
                        return idx.wrappingMul(USIZE_BITS).wrappingAdd(trailingOnes)
                    }; is Result.Err<*> -> {
                        val previous = matchVal.error
                        current = previous
                    } }
                }
            }
        }
        return null
    }

    public companion object {
        public fun split(index: Int): Pair<Int, Int> = Pair(index / USIZE_BITS, 1 shl (index % USIZE_BITS))
    }
}

internal val SYNC_BITSET_INIT: SyncBitset = SyncBitset(listOf(AtomicUsize.new(0), AtomicUsize.new(0)))

internal open class SyncBitsetIter(
    public var iter: Peekable<Enumerate<Iter<Atomic<Int>>>>,
    public var elemIdx: Int,
) : Iterable<Int> {
    override fun iterator(): kotlin.collections.Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@SyncBitsetIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Int? =
        this.iter.peek().cloned().andThen({ (idx, elem) ->
            val elem1 = elem.load(Ordering.Relaxed)
            val lowMask = rustWrappingSub(1 shl this.elemIdx, 1)
            val next = elem1 and lowMask.inv()
            val nextIdx = next.trailingZeros().toInt()
            this.elemIdx = (nextIdx + 1)
            if (this.elemIdx >= 64) {
                this.elemIdx = 0
                this.iter.next()
            }
            when (nextIdx) {
                64 -> this.next()
                else -> idx.wrappingMul(USIZE_BITS).wrappingAdd(nextIdx)
            }
        })
}
