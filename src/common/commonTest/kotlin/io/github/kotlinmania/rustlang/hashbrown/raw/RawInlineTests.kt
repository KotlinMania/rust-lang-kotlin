// port-lint: tests raw.rs
package io.github.kotlinmania.rustlang.hashbrown.raw

import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.Global
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.Group
import io.github.kotlinmania.rustlang.std.thread.lifecycle.scope

public fun <T> rehashInPlace(table: RawTable<T, Global>, hasher: (T) -> ULong, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    run {
        table.table.rehashInPlace({ table1, index -> hasher(table1.bucket<T>(index).asRef()) }, sizeOf<T>(typeLayoutT = typeLayoutT), if (needsDrop<T>()) {
            { ptr -> dropInPlace(ptr.cast()) }
        } else {
            null
        })
    }
}

public class RawTestMapInlineTests {
    @kotlin.test.Test
    public fun testPrevPow2() {
        // Skip 0, not defined for that input.
        var pow2: Int = 1
        while ((pow2 shl 1) > 0) {
            val nextPow2 = pow2 shl 1
            check(pow2 == prevPow2(pow2))
            // Need to skip 2, because it's also a power of 2, so it doesn't
            // return the previous power of 2.
            if (nextPow2 > 2) {
                check(pow2 == prevPow2(pow2 + 1))
                check(pow2 == prevPow2(nextPow2 - 1))
            }
            pow2 = nextPow2
        }
    }

    @kotlin.test.Test
    public fun testMinimumCapacityForSmallTypes() {
        fun <T> testT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
            val rawTable: RawTable<T, Global> = RawTable.withCapacity(1)
            val actualBuckets = rawTable.numBuckets()
            val minBuckets = Group.WIDTH / sizeOf<T>(typeLayoutT = typeLayoutT)
            check(actualBuckets >= minBuckets) { "expected at least $minBuckets buckets, got $actualBuckets buckets" }
        }
        testT<UByte>(typeLayoutT = PrimitiveLayouts.U8)
        // This is only "small" for some platforms, like x8664 with SSE2, but
        // there's no harm in running it on other platforms.
        testT<UShort>(typeLayoutT = PrimitiveLayouts.U16)
    }

    @kotlin.test.Test
    public fun rehash() {
        val table = RawTable.new()
        val hasher = { i: ULong -> i }
        for (i in 0 until 100) {
            table.insert(i, i, hasher)
        }
        for (i1 in 0 until 100) {
            run {
                check(table.find(i1, { x -> x == i1 })?.let({ b -> b.read() }) == i1)
            }
            check(table.find(i1 + 100, { x -> x == i1 + 100 }) == null)
        }
        rehashInPlace(table, hasher)
        for (i2 in 0 until 100) {
            run {
                check(table.find(i2, { x -> x == i2 })?.let({ b -> b.read() }) == i2)
            }
            check(table.find(i2 + 100, { x -> x == i2 + 100 }) == null)
        }
    }

    /**
     * CHECKING THAT WE ARE NOT TRYING TO READ THE MEMORY OF
     * AN UNINITIALIZED TABLE DURING THE DROP
     */
    @kotlin.test.Test
    public fun testDropUninitialized() {
        val table = RawTable.newUninitialized(io.github.kotlinmania.rustlang.alloc.alloc.Global(), 8, Fallibility.Nothing).unwrap()
        (table).let { }
    }

    /**
     * CHECKING THAT WE DON'T TRY TO DROP DATA IF THE `ITEMS`
     * ARE ZERO, EVEN IF WE HAVE `FULL` CONTROL BYTES.
     */
    @kotlin.test.Test
    public fun testDropZeroItems() {
        run {
            // SAFETY: The `buckets` is power of two and we're not
            // trying to actually use the returned RawTable.
            val table = RawTable.newUninitialized(io.github.kotlinmania.rustlang.alloc.alloc.Global(), 8, Fallibility.Nothing).unwrap()
            // WE SIMULATE, AS IT WERE, A FULL TABLE.
            // SAFETY: We checked that the table is allocated and therefore the table already has
            // `self.bucketMask + 1 + Group.WIDTH` number of control bytes (see TableLayout.calculateLayoutFor)
            // so writing `table.table.numCtrlBytes() == bucketMask + 1 + Group.WIDTH` bytes is safe.
            table.table.ctrlSlice().fillEmpty()
            // SAFETY: table.capacity() is guaranteed to be smaller than table.numBuckets()
            table.table.ctrl(0).writeBytes(0, table.capacity())
            // Fix up the trailing control bytes. See the comments in setCtrl
            // for the handling of tables smaller than the group width.
            if (table.numBuckets() < Group.WIDTH) {
                // SAFETY: We have `self.bucketMask + 1 + Group.WIDTH` number of control bytes,
                // so copying `self.numBuckets() == self.bucketMask + 1` bytes with offset equal to
                // `Group.WIDTH` is safe
                table.table.ctrl(0).copyTo(table.table.ctrl(Group.WIDTH), table.table.numBuckets())
            } else {
                // SAFETY: We have `self.bucketMask + 1 + Group.WIDTH` number of
                // control bytes,so copying `Group.WIDTH` bytes with offset equal
                // to `self.numBuckets() == self.bucketMask + 1` is safe
                table.table.ctrl(0).copyTo(table.table.ctrl(table.table.numBuckets()), Group.WIDTH)
            }
            (table).let { }
        }
    }

    /**
     * CHECKING THAT WE DON'T TRY TO DROP DATA IF THE `ITEMS`
     * ARE ZERO, EVEN IF WE HAVE `FULL` CONTROL BYTES.
     */
    @kotlin.test.Test
    public fun testCatchPanicCloneFrom() {
        val DISARMED: Boolean = false
        val ARMED: Boolean = true
        open class CheckedCloneDrop(
            public var panicInClone: Boolean,
            public var dropped: Boolean,
            public var needDrop: List<ULong>,
        ) {
            public open fun clone(): CheckedCloneDrop {
                if (this.panicInClone) {
                    error("panic in clone")
                }
                return CheckedCloneDrop(panicInClone = this.panicInClone, dropped = this.dropped, needDrop = this.needDrop.toMutableList())
            }

            public open fun drop() {
                if (this.dropped) {
                    error("double drop")
                }
                this.dropped = true
            }
        }
        open class MyAllocInner(
            public var dropCount: AtomicI8,
        ) {
            public open fun drop() {
                println("MyAlloc freed.")
                this.dropCount.fetchSub(1, Ordering.SeqCst)
            }
        }
        open class MyAlloc(
            public var inner: MyAllocInner,
        ) : Allocator {
            public open fun clone(): MyAlloc = MyAlloc(inner)

            public override fun allocate(layout: Layout): Result<NonNull, AllocError> {
                val g = io.github.kotlinmania.rustlang.alloc.alloc.Global()
                return g.allocate(layout)
            }

            public override fun deallocate(ptr: NonNull, layout: Layout) {
                run {
                    val g = io.github.kotlinmania.rustlang.alloc.alloc.Global()
                    g.deallocate(ptr, layout)
                }
            }
        }
        val dropped: AtomicI8 = AtomicI8.new(2)
        val table = RawTable.newIn(MyAlloc(inner = MyAllocInner(dropCount = dropped.clone())))
        for ((idx, panicInClone) in iter.repeatN(DISARMED, 7).mapIndexed { index, item -> Pair(index, item) }) {
            val idx1 = idx.toULong()
            table.insert(idx1, Pair(idx1, CheckedCloneDrop(panicInClone = panicInClone, dropped = false, needDrop = mutableListOf(idx1))), { (k, _) -> k })
        }
        check(table.len() == 7)
        scope({ s ->
            val result =
                s.spawn({
                    val armedFlags = listOf(DISARMED, DISARMED, ARMED, DISARMED, DISARMED, DISARMED, DISARMED)
                    var scopeTable = RawTable.newIn(MyAlloc(inner = MyAllocInner(dropCount = dropped.clone())))
                    for ((idx2, panicInClone1) in armedFlags.iter().mapIndexed { index, item -> Pair(index, item) }) {
                        val idx3 = idx2.toULong()
                        scopeTable.insert(idx3, Pair(idx3, CheckedCloneDrop(panicInClone = panicInClone1, dropped = false, needDrop = mutableListOf(idx3 + 100uL))), { (k, _) -> k })
                    }
                    table.cloneFrom(scopeTable)
                })
            check(result.join().isErr())
        })
        // Let's check that all iterators work fine and do not return elements
        // (especially `RawIterRange`, which does not depend on the number of
        // elements in the table, but looks directly at the control bytes)
        //
        // SAFETY: We know for sure that `RawTable` will outlive
        // the returned `RawIter / RawIterRange` iterator.
        check(table.len() == 0)
        check(table.iter().count() == 0)
        check(table.iter().iter.count() == 0)
        for (idx2 in 0 until table.numBuckets()) {
            val idx3 = idx2.toULong()
            check(table.find(idx3, { (k, _) -> k == idx3 }) == null) { "Index: $idx" }
        }
        // All allocator clones should already be dropped.
        check(dropped.load(Ordering.SeqCst) == 1)
    }
}
