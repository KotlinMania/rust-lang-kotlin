// port-lint: source helpers/shuffle.rs
package io.github.kotlinmania.rustlang.test.helpers.shuffle

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.std.hash.random.DefaultHasher
import io.github.kotlinmania.rustlang.std.time.SystemTime
import io.github.kotlinmania.rustlang.std.time.UNIX_EPOCH
import io.github.kotlinmania.rustlang.test.TestDescAndFn
import io.github.kotlinmania.rustlang.test.TestId
import io.github.kotlinmania.rustlang.test.TestName
import io.github.kotlinmania.rustlang.test.cli.TestOpts

public fun getShuffleSeed(opts: TestOpts): ULong? =
    opts.shuffleSeed.orElse({
        if (opts.shuffle) {
            SystemTime.now().durationSince(UNIX_EPOCH).expect("Failed to get system time").asNanos().toULong()
        } else {
            null
        }
    })

public fun shuffleTests(shuffleSeed: ULong, tests: MutableList<Pair<TestId, TestDescAndFn>>) {
    val testNames: List<TestName> = tests.map({ test -> test.second.desc.name }).toMutableList()
    val testNamesHash = calculateHash(testNames)
    val rng = Rng.new(shuffleSeed, testNamesHash)
    shuffle(rng, tests)
}

// `shuffle` is from `rust - analyzer / src / cli / analysisStats.rs`.
public fun <T> shuffle(rng: Rng, slice: MutableList<T>) {
    fun <T> randomizeFirst(rng1: Rng, slice1: MutableList<T>) {
        check(!slice1.isEmpty())
        val idx = rng1.randRange(0 until slice1.size.toULong()).toInt()
        slice1.swap(0, idx)
    }
    for (i in 0 until slice.size) {
        randomizeFirst(rng, slice.subList(i, slice.size))
    }
}

public open class Rng(
    public var state: ULong,
    public var extra: ULong,
) {
    public open fun randRange(range: Range<ULong>): ULong = this.randU64() % (range.end - range.start) + range.start

    public open fun randU64(): ULong {
        this.state = calculateHash(Pair(this.state, this.extra))
        return this.state
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Rng> =
            object : TypeLayout<Rng> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Rng =
                    Rng(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Rng) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.state)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.extra)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rng =
                    Rng(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rng) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.state)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.extra)
                }
            }

        public fun new(seed: ULong, extra: ULong): Rng = Rng(state = seed, extra = extra)
    }
}

// `calculateHash` is from `core / src / hash / mod.rs`.
public fun <T> calculateHash(t: T): ULong {
    val s = DefaultHasher.new()
    t.hash(s)
    return s.finish()
}
