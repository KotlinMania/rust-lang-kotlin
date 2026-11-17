// port-lint: tests map.rs
package io.github.kotlinmania.rustlang.hashbrown

import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.ops.arith.AddAssign
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.hashbrown.Entry.Occupied
import io.github.kotlinmania.rustlang.hashbrown.Entry.Vacant
import io.github.kotlinmania.rustlang.hashbrown.TryReserveError.CapacityOverflow
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.alloc.Global
import io.github.kotlinmania.rustlang.hashbrown.hasher.DefaultHashBuilder
import io.github.kotlinmania.rustlang.hashbrown.raw.prevPow2
import io.github.kotlinmania.rustlang.libc.mmap
import io.github.kotlinmania.rustlang.libc.munmap
import io.github.kotlinmania.rustlang.libc.sysconf
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.MAP_ANON
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_FAILED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_PRIVATE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_READ
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_WRITE
import io.github.kotlinmania.rustlang.libc.wasi.SC_PAGESIZE
import io.github.kotlinmania.rustlang.std.hash.random.DefaultHasher
import io.github.kotlinmania.rustlang.std.thread.lifecycle.scope
import io.github.kotlinmania.rustlang.std.thread.scoped.ScopedJoinHandle

public typealias HM = HashMap<Int, Int, DefaultHashBuilder, Global>

public typealias Hasher = DefaultHasher

// Omitted quickcheck/rand property test items pending quickcheck-kotlin / rand-kotlin port (Hermes Kanban card 19971811).

public open class Droppable(
    public var k: Int,
) {
    public open fun drop() {
        DROP_VECTOR.with({ slot ->
            slot.borrowMut()[this.k] -= 1
        })
    }

    public open fun clone(): Droppable = Droppable.new(this.k)

    public companion object {
        public fun typeLayout(): TypeLayout<Droppable> =
            object : TypeLayout<Droppable> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Droppable =
                    Droppable(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Droppable) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.k)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Droppable =
                    Droppable(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Droppable) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.k)
                }
            }

        public fun new(k: Int): Droppable {
            DROP_VECTOR.with({ slot ->
                slot.borrowMut()[k] += 1
            })
            return Droppable(k = k)
        }
    }
}

public open class MyAllocInner(
    public var dropCount: AtomicI8,
) {
    public open fun drop() {
        println("MyAlloc freed.")
        this.dropCount.fetchSub(1, Ordering.SeqCst)
    }
}

public open class MyAlloc(
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

    public companion object {
        public fun new(dropCount: AtomicI8): MyAlloc = MyAlloc(inner = MyAllocInner(dropCount = dropCount))
    }
}

public open class CheckedCloneDrop<T>(
    public var panicInClone: Boolean,
    public var panicInDrop: Boolean,
    public var dropped: Boolean,
    public var data: T,
) {
    public open fun clone(): CheckedCloneDrop<T> {
        if (this.panicInClone) {
            error("panic in clone")
        }
        return CheckedCloneDrop(panicInClone = this.panicInClone, panicInDrop = this.panicInDrop, dropped = this.dropped, data = this.data.clone())
    }

    public open fun drop() {
        if (this.panicInDrop) {
            this.dropped = true
            error("panic in drop")
        }
        if (this.dropped) {
            error("double drop")
        }
        this.dropped = true
    }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<CheckedCloneDrop<T>> =
            object : TypeLayout<CheckedCloneDrop<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                private val off3: Long = alignUpBytes(3L, typeLayoutT.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off3 + typeLayoutT.sizeBytes), alignBytes)
                override fun readNative(addr: Long): CheckedCloneDrop<T> =
                    CheckedCloneDrop(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (typeLayoutT).readNative(addr + off3),
                    )
                override fun writeNative(addr: Long, value: CheckedCloneDrop<T>) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.panicInClone)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.panicInDrop)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.dropped)
                    (typeLayoutT).writeNative(addr + off3, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CheckedCloneDrop<T> =
                    CheckedCloneDrop(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (typeLayoutT).readBytes(buf, offset + off3.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CheckedCloneDrop<T>) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.panicInClone)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.panicInDrop)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.dropped)
                    (typeLayoutT).writeBytes(buf, offset + off3.toInt(), value.data)
                }
            }

        public fun <T> new(panicInClone: Boolean, panicInDrop: Boolean, data: T): CheckedCloneDrop<T> = CheckedCloneDrop(panicInClone = panicInClone, panicInDrop = panicInDrop, dropped = false, data = data)
    }
}

/**
 * Return hashmap with predefined distribution of elements.
 * All elements will be located in the same order as elements
 * returned by iterator.
 *
 * This function does not panic, but returns an error as a `String`
 * to distinguish between a test panic and an error in the input data.
 */
public fun <I, T, A> getTestMap(iter: I, fn: (ULong) -> T, alloc: A, cloneIFn: (I) -> I = { it }): Result<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String> where I : Iterator<Pair<Boolean, Boolean>>, A : Allocator {
    val map: HashMap<ULong, CheckedCloneDrop<T>, A, Global>  = HashMap.withCapacityIn(iter.sizeHint().first, alloc)
    run {
        var guard =
            Guard(map, { map1 ->
                for ((_, value) in map1.iterMut()) {
                    value.panicInDrop = false
                }
            })
        var count = 0
        // Hash and Key must be equal to each other for controlling the elements placement.
        for ((panicInClone, panicInDrop) in cloneIFn(iter)) {
            if (true && panicInDrop) {
                return Result.Err<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>("panic_in_drop can be set with a type that doesn't need to be dropped")
            }
            guard.table.insert(count, Pair(count, CheckedCloneDrop.new(panicInClone, panicInDrop, fn(count))), { (k, _) -> k })
            count += 1
        }
        // Let's check that all elements are located as we wanted
        var checkCount = 0
        for ((key, value, panicInClone1, panicInDrop1) in (guard.iter().zip(iter))) {
            if (key != checkCount) {
                return Result.Err<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>("key != check_count,\nkey: `$key`,\ncheck_count: `$checkCount`")
            }
            if (value.dropped || value.panicInClone != panicInClone1 || value.panicInDrop != panicInDrop1 || value.data != fn(checkCount)) {
                return Result.Err<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>("Value is not equal to expected,\nvalue: `$value`,\nexpected: `CheckedCloneDrop { panic_in_clone: $panicInClone1, panic_in_drop: $panicInDrop1, dropped: false, data: ${fn(checkCount)} }`")
            }
            checkCount += 1
        }
        if (guard.len() != checkCount.toInt()) {
            return Result.Err<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>("map.len() != check_count,\nmap.len(): `${guard.len()}`,\ncheck_count: `$checkCount`")
        }
        if (count != checkCount) {
            return Result.Err<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>("count != check_count,\ncount: `$count`,\ncheck_count: `$checkCount`")
        }
        forget(guard)
    }
    return Result.Ok<HashMap<ULong, CheckedCloneDrop<T>, DefaultHashBuilder, A>, String>(map)
}

public val DISARMED: Boolean = false

public val ARMED: Boolean = true

public val ARMED_FLAGS: BooleanArray = booleanArrayOf(DISARMED, DISARMED, DISARMED, ARMED, DISARMED, DISARMED, DISARMED, DISARMED)

public val DISARMED_FLAGS: BooleanArray = booleanArrayOf(DISARMED, DISARMED, DISARMED, DISARMED, DISARMED, DISARMED, DISARMED, DISARMED)

public class MapTestMapInlineTests {
    @kotlin.test.Test
    public fun testZeroCapacities() {
        val m = HM.new()
        check(m.capacity() == 0)
        val m1 = HM.default()
        check(m1.capacity() == 0)
        val m2 = HM.withHasher(DefaultHashBuilder.default())
        check(m2.capacity() == 0)
        val m3 = HM.withCapacity(0)
        check(m3.capacity() == 0)
        val m4 = HM.withCapacityAndHasher(0, DefaultHashBuilder.default())
        check(m4.capacity() == 0)
        val m5 = HM.new()
        m5.insert(1, 1)
        m5.insert(2, 2)
        m5.remove(1)
        m5.remove(2)
        m5.shrinkToFit()
        check(m5.capacity() == 0)
        val m6 = HM.new()
        m6.reserve(0)
        check(m6.capacity() == 0)
    }

    @kotlin.test.Test
    public fun testCreateCapacityZero() {
        val m = HashMap.withCapacity(0)
        check(m.insert(1, 1) == null)
        check(m.containsKey(1))
        check(!m.containsKey(0))
    }

    @kotlin.test.Test
    public fun testInsert() {
        val m = HashMap.new()
        check(m.len() == 0)
        check(m.insert(1, 2) == null)
        check(m.len() == 1)
        check(m.insert(2, 4) == null)
        check(m.len() == 2)
        check(m.get(1)!! == 2)
        check(m.get(2)!! == 4)
    }

    @kotlin.test.Test
    public fun testClone() {
        val m = HashMap.new()
        check(m.len() == 0)
        check(m.insert(1, 2) == null)
        check(m.len() == 1)
        check(m.insert(2, 4) == null)
        check(m.len() == 2)
        val m2 = m.clone()
        check(m2.get(1)!! == 2)
        check(m2.get(2)!! == 4)
        check(m2.len() == 2)
    }

    @kotlin.test.Test
    public fun testCloneFrom() {
        val m = HashMap.new()
        val m2 = HashMap.new()
        check(m.len() == 0)
        check(m.insert(1, 2) == null)
        check(m.len() == 1)
        check(m.insert(2, 4) == null)
        check(m.len() == 2)
        m2.cloneFrom(m)
        check(m2.get(1)!! == 2)
        check(m2.get(2)!! == 4)
        check(m2.len() == 2)
    }

    @kotlin.test.Test
    public fun testDrops() {
        DROP_VECTOR.with({ slot ->
            slot.borrowMut() = mutableListOf(0, 200)
        })
        run {
            val m = HashMap.new()
            DROP_VECTOR.with({ v ->
                for (i in 0 until 200) {
                    check(v.borrow()[i] == 0)
                }
            })
            for (i in 0 until 100) {
                val d1 = Droppable.new(i)
                val d2 = Droppable.new(i + 100)
                m.insert(d1, d2)
            }
            DROP_VECTOR.with({ v ->
                for (i1 in 0 until 200) {
                    check(v.borrow()[i1] == 1)
                }
            })
            for (i1 in 0 until 50) {
                val k = Droppable.new(i1)
                val v = m.remove(k)
                check(v != null)
                DROP_VECTOR.with({ v1 ->
                    check(v1.borrow()[i1] == 1)
                    check(v1.borrow()[i1 + 100] == 1)
                })
            }
            DROP_VECTOR.with({ v1 ->
                for (i2 in 0 until 50) {
                    check(v1.borrow()[i2] == 0)
                    check(v1.borrow()[i2 + 100] == 0)
                }
                for (i3 in 50 until 100) {
                    check(v1.borrow()[i3] == 1)
                    check(v1.borrow()[i3 + 100] == 1)
                }
            })
        }
        DROP_VECTOR.with({ v1 ->
            for (i2 in 0 until 200) {
                check(v1.borrow()[i2] == 0)
            }
        })
    }

    @kotlin.test.Test
    public fun testIntoIterDrops() {
        DROP_VECTOR.with({ v ->
            v.borrowMut() = mutableListOf(0, 200)
        })
        val hm1 = run {
            val hm = HashMap.new()
            DROP_VECTOR.with({ v ->
                for (i in 0 until 200) {
                    check(v.borrow()[i] == 0)
                }
            })
            for (i in 0 until 100) {
                val d1 = Droppable.new(i)
                val d2 = Droppable.new(i + 100)
                hm.insert(d1, d2)
            }
            DROP_VECTOR.with({ v ->
                for (i1 in 0 until 200) {
                    check(v.borrow()[i1] == 1)
                }
            })
            hm
        }
        // By the way, ensure that cloning doesn't screw up the dropping.
        (hm1.clone()).let { }
        run {
            val half = hm1.intoIter().take(50)
            DROP_VECTOR.with({ v ->
                for (i1 in 0 until 200) {
                    check(v.borrow()[i1] == 1)
                }
            })
            for (unused in half.byRef()) {}
            DROP_VECTOR.with({ v ->
                val nk = (0 until 100).filter({ i1 -> v.borrow()[i1] == 1 }).count()
                val nv = (0 until 100).filter({ i1 -> v.borrow()[i1 + 100] == 1 }).count()
                check(nk == 50)
                check(nv == 50)
            })
        }
        DROP_VECTOR.with({ v ->
            for (i1 in 0 until 200) {
                check(v.borrow()[i1] == 0)
            }
        })
    }

    @kotlin.test.Test
    public fun testEmptyRemove() {
        val m: HashMap<Int, Boolean, DefaultHashBuilder, Global>  = HashMap.new()
        check(m.remove(0) == null)
    }

    @kotlin.test.Test
    public fun testEmptyEntry() {
        val m: HashMap<Int, Boolean, DefaultHashBuilder, Global>  = HashMap.new()
        when (val matchVal = m.entry(0)) {
            is Occupied -> error("panic")
            is Vacant -> {}
        }
        check(m.entry(0).orInsert(true))
        check(m.len() == 1)
    }

    @kotlin.test.Test
    public fun testEmptyEntryRef() {
        val m: HashMap<String, Boolean, DefaultHashBuilder, Global>  = HashMap.new()
        when (val matchVal = m.entryRef("poneyland")) {
            is EntryRef.Occupied -> error("panic")
            is EntryRef.Vacant -> {}
        }
        check(m.entryRef("poneyland").orInsert(true))
        check(m.len() == 1)
    }

    @kotlin.test.Test
    public fun testEmptyIter() {
        val m: HashMap<Int, Boolean, DefaultHashBuilder, Global>  = HashMap.new()
        check(m.drain().next() == null)
        check(m.keys().next() == null)
        check(m.values().next() == null)
        check(m.valuesMut().next() == null)
        check(m.iter().next() == null)
        check(m.iterMut().next() == null)
        check(m.len() == 0)
        check(m.isEmpty())
        check(m.intoIter().next() == null)
    }

    // Note: takes too long
    @kotlin.test.Test
    public fun testLotsOfInsertions() {
        val m = HashMap.new()
        // Try this a few times to make sure we never screw up the hashmap's
        // internal state.
        for (unused in 0 until 10) {
            check(m.isEmpty())
            for (i in 1 until 1001) {
                check(m.insert(i, i) == null)
                for (j in 1..i) {
                    val r = m.get(j)
                    check(r == j)
                }
                for (j1 in i + 1 until 1001) {
                    val r1 = m.get(j1)
                    check(r1 == null)
                }
            }
            for (i1 in 1001 until 2001) {
                check(!m.containsKey(i1))
            }
            // remove forwards
            for (i2 in 1 until 1001) {
                check(m.remove(i2) != null)
                for (j2 in 1..i2) {
                    check(!m.containsKey(j2))
                }
                for (j3 in i2 + 1 until 1001) {
                    check(m.containsKey(j3))
                }
            }
            for (i3 in 1 until 1001) {
                check(!m.containsKey(i3))
            }
            for (i4 in 1 until 1001) {
                check(m.insert(i4, i4) == null)
            }
            // remove backwards
            for (i5 in (1 until 1001).rev()) {
                check(m.remove(i5) != null)
                for (j4 in i5 until 1001) {
                    check(!m.containsKey(j4))
                }
                for (j5 in 1 until i5) {
                    check(m.containsKey(j5))
                }
            }
        }
    }

    @kotlin.test.Test
    public fun testFindMut() {
        val m = HashMap.new()
        check(m.insert(1, 12) == null)
        check(m.insert(2, 8) == null)
        check(m.insert(5, 14) == null)
        val new = 100
        when (val matchVal = m.getMut(5)) {
            null -> error("panic")
            else -> {
                val x = matchVal
                x = new
            }
        }
        check(m.get(5) == new)
        val hashmap: HashMap<Int, String, DefaultHashBuilder, Global>  = HashMap.default()
        val key = 1
        val result = hashmap.get(key)
        check(result == null)
    }

    @kotlin.test.Test
    public fun testInsertOverwrite() {
        val m = HashMap.new()
        check(m.insert(1, 2) == null)
        check(m.get(1)!! == 2)
        check(m.insert(1, 3) != null)
        check(m.get(1)!! == 3)
    }

    @kotlin.test.Test
    public fun testInsertConflicts() {
        val m = HashMap.withCapacity(4)
        check(m.insert(1, 2) == null)
        check(m.insert(5, 3) == null)
        check(m.insert(9, 4) == null)
        check(m.get(9)!! == 4)
        check(m.get(5)!! == 3)
        check(m.get(1)!! == 2)
    }

    @kotlin.test.Test
    public fun testConflictRemove() {
        val m = HashMap.withCapacity(4)
        check(m.insert(1, 2) == null)
        check(m.get(1)!! == 2)
        check(m.insert(5, 3) == null)
        check(m.get(1)!! == 2)
        check(m.get(5)!! == 3)
        check(m.insert(9, 4) == null)
        check(m.get(1)!! == 2)
        check(m.get(5)!! == 3)
        check(m.get(9)!! == 4)
        check(m.remove(1) != null)
        check(m.get(9)!! == 4)
        check(m.get(5)!! == 3)
    }

    @kotlin.test.Test
    public fun testInsertUniqueUnchecked() {
        val map = HashMap.new()
        val (k1, v1) = map.insertUniqueUnchecked(10, 11)
        check(Pair(10, RustMutableReference(allocateNativeCell(11, PrimitiveLayouts.I32), PrimitiveLayouts.I32)) == Pair(k1, v1))
        val (k2, v2) = map.insertUniqueUnchecked(20, 21)
        check(Pair(20, RustMutableReference(allocateNativeCell(21, PrimitiveLayouts.I32), PrimitiveLayouts.I32)) == Pair(k2, v2))
        check(11 == map.get(10))
        check(21 == map.get(20))
        check(null == map.get(30))
    }

    @kotlin.test.Test
    public fun testIsEmpty() {
        val m = HashMap.withCapacity(4)
        check(m.insert(1, 2) == null)
        check(!m.isEmpty())
        check(m.remove(1) != null)
        check(m.isEmpty())
    }

    @kotlin.test.Test
    public fun testRemove() {
        val m = HashMap.new()
        m.insert(1, 2)
        check(m.remove(1) == 2)
        check(m.remove(1) == null)
    }

    @kotlin.test.Test
    public fun testRemoveEntry() {
        val m = HashMap.new()
        m.insert(1, 2)
        check(m.removeEntry(1) == Pair(1, 2))
        check(m.remove(1) == null)
    }

    @kotlin.test.Test
    public fun testIterate() {
        val m = HashMap.withCapacity(4)
        for (i in 0 until 32) {
            check(m.insert(i, i * 2) == null)
        }
        check(m.len() == 32)
        var observed: UInt = 0u
        for ((k, v) in m) {
            check(v == k * 2)
            observed = observed or (1u shl k.toInt())
        }
        check(observed == 0xFFFFFFFFu)
    }

    @kotlin.test.Test
    public fun testKeys() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = ArrayDeque(vec).collect()
        val keys: Vec = map.keys().copied().collect()
        check(keys.len() == 3)
        check(keys.contains(1))
        check(keys.contains(2))
        check(keys.contains(3))
    }

    @kotlin.test.Test
    public fun testValues() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = ArrayDeque(vec).collect()
        val values: Vec = map.values().copied().collect()
        check(values.len() == 3)
        check(values.contains('a'))
        check(values.contains('b'))
        check(values.contains('c'))
    }

    @kotlin.test.Test
    public fun testValuesMut() {
        val vec = mutableListOf(Pair(1, 1), Pair(2, 2), Pair(3, 3))
        val map: HashMap  = ArrayDeque(vec).collect()
        for (value in map.valuesMut()) {
            value *= 2
        }
        val values: Vec = map.values().copied().collect()
        check(values.len() == 3)
        check(values.contains(2))
        check(values.contains(4))
        check(values.contains(6))
    }

    @kotlin.test.Test
    public fun testIntoKeys() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = ArrayDeque(vec).collect()
        val keys: Vec = map.intoKeys().collect()
        check(keys.len() == 3)
        check(keys.contains(1))
        check(keys.contains(2))
        check(keys.contains(3))
    }

    @kotlin.test.Test
    public fun testIntoValues() {
        val vec = mutableListOf(Pair(1, 'a'), Pair(2, 'b'), Pair(3, 'c'))
        val map: HashMap = ArrayDeque(vec).collect()
        val values: Vec = map.intoValues().collect()
        check(values.len() == 3)
        check(values.contains('a'))
        check(values.contains('b'))
        check(values.contains('c'))
    }

    @kotlin.test.Test
    public fun testFind() {
        val m = HashMap.new()
        check(m.get(1) == null)
        m.insert(1, 2)
        when (val matchVal = m.get(1)) {
            null -> error("panic")
            else -> {
                val v = matchVal
                check(v == 2)
            }
        }
    }

    @kotlin.test.Test
    public fun testEq() {
        val m1 = HashMap.new()
        m1.insert(1, 2)
        m1.insert(2, 3)
        m1.insert(3, 4)
        val m2 = HashMap.new()
        m2.insert(1, 2)
        m2.insert(2, 3)
        check(m1 != m2)
        m2.insert(3, 4)
        check(m1 == m2)
    }

    @kotlin.test.Test
    public fun testShow() {
        val map = HashMap.new()
        val empty: HashMap<Int, Int, DefaultHashBuilder, Global> = HashMap.new()
        map.insert(1, 2)
        map.insert(3, 4)
        val mapStr = "$map"
        check(mapStr == "{1: 2, 3: 4}" || mapStr == "{3: 4, 1: 2}")
        check("$empty" == "{}")
    }

    @kotlin.test.Test
    public fun testExpand() {
        val m = HashMap.new()
        check(m.len() == 0)
        check(m.isEmpty())
        var i = 0
        val oldRawCap = m.rawCapacity()
        while (oldRawCap == m.rawCapacity()) {
            m.insert(i, i)
            i += 1
        }
        check(m.len() == i)
        check(!m.isEmpty())
    }

    @kotlin.test.Test
    public fun testBehaviorResizePolicy() {
        val m = HashMap.new()
        check(m.len() == 0)
        check(m.rawCapacity() == 1)
        check(m.isEmpty())
        m.insert(0, 0)
        m.remove(0)
        check(m.isEmpty())
        val initialRawCap = m.rawCapacity()
        m.reserve(initialRawCap)
        val rawCap = m.rawCapacity()
        check(rawCap == initialRawCap * 2)
        var i = 0
        for (unused in 0 until rawCap * 3 / 4) {
            m.insert(i, i)
            i += 1
        }
        // three quarters full
        check(m.len() == i)
        check(m.rawCapacity() == rawCap)
        for (unused1 in 0 until rawCap / 4) {
            m.insert(i, i)
            i += 1
        }
        // half full
        val newRawCap = m.rawCapacity()
        check(newRawCap == rawCap * 2)
        for (unused2 in 0 until rawCap / 2 - 1) {
            i -= 1
            m.remove(i)
            check(m.rawCapacity() == newRawCap)
        }
        // A little more than one quarter full.
        m.shrinkToFit()
        check(m.rawCapacity() == rawCap)
        // again, a little more than half full
        for (unused3 in 0 until rawCap / 2) {
            i -= 1
            m.remove(i)
        }
        m.shrinkToFit()
        check(m.len() == i)
        check(!m.isEmpty())
        check(m.rawCapacity() == initialRawCap)
    }

    @kotlin.test.Test
    public fun testReserveShrinkToFit() {
        val m = HashMap.new()
        m.insert(0, 0)
        m.remove(0)
        check(m.capacity() >= m.len())
        for (i in 0 until 128) {
            m.insert(i, i)
        }
        m.reserve(256)
        val usableCap = m.capacity()
        for (i1 in 128 until (128 + 256)) {
            m.insert(i1, i1)
            check(m.capacity() == usableCap)
        }
        for (i2 in 100 until (128 + 256)) {
            check(m.remove(i2) == i2)
        }
        m.shrinkToFit()
        check(m.len() == 100)
        check(!m.isEmpty())
        check(m.capacity() >= m.len())
        for (i3 in 0 until 100) {
            check(m.remove(i3) == i3)
        }
        m.shrinkToFit()
        m.insert(0, 0)
        check(m.len() == 1)
        check(m.capacity() >= m.len())
        check(m.remove(0) == 0)
    }

    @kotlin.test.Test
    public fun testFromIter() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap = xs.iter().copied().collect()
        for ((k, v) in xs) {
            check(map.get(k) == v)
        }
        check(map.iter().len() == xs.len() - 1)
    }

    @kotlin.test.Test
    public fun testSizeHint() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap = xs.iter().copied().collect()
        val iter = map.iter()
        for (unused in iter.byRef().take(3)) {}
        check(iter.sizeHint() == Pair(3, 3))
    }

    @kotlin.test.Test
    public fun testIterLen() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap = xs.iter().copied().collect()
        val iter = map.iter()
        for (unused in iter.byRef().take(3)) {}
        check(iter.len() == 3)
    }

    @kotlin.test.Test
    public fun testMutSizeHint() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap  = xs.iter().copied().collect()
        val iter = map.iterMut()
        for (unused in iter.byRef().take(3)) {}
        check(iter.sizeHint() == Pair(3, 3))
    }

    @kotlin.test.Test
    public fun testIterMutLen() {
        val xs = listOf(Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        val map: HashMap  = xs.iter().copied().collect()
        val iter = map.iterMut()
        for (unused in iter.byRef().take(3)) {}
        check(iter.len() == 3)
    }

    @kotlin.test.Test
    public fun testIndex() {
        val map = HashMap.new()
        map.insert(1, 2)
        map.insert(2, 1)
        map.insert(3, 4)
        check(map[2] == 1)
    }

    @kotlin.test.Test
    public fun testIndexNonexistent() {
        kotlin.test.assertFails(block = fun() {
            val map = HashMap.new()
            map.insert(1, 2)
            map.insert(2, 1)
            map.insert(3, 4)
            map[4]
        })
    }

    @kotlin.test.Test
    public fun testEntry() {
        val xs = listOf(Pair(1, 10), Pair(2, 20), Pair(3, 30), Pair(4, 40), Pair(5, 50), Pair(6, 60))
        val map: HashMap  = xs.iter().copied().collect()
        // Existing key (insert)
        when (val matchVal = map.entry(1)) {
            is Vacant -> error("unreachable")
            is Occupied -> {
                val (view) = matchVal
                check(view.get() == 10)
                check(view.insert(100) == 10)
            }
        }
        check(map.get(1)!! == 100)
        check(map.len() == 6)
        // Existing key (update)
        when (val matchVal1 = map.entry(2)) {
            is Vacant -> error("unreachable")
            is Occupied -> {
                val (view1) = matchVal1
                val v = view1.getMut()
                val newV = v * 10
                v = newV
            }
        }
        check(map.get(2)!! == 200)
        check(map.len() == 6)
        // Existing key (take)
        when (val matchVal2 = map.entry(3)) {
            is Vacant -> error("unreachable")
            is Occupied -> {
                val (view2) = matchVal2
                check(view2.remove() == 30)
            }
        }
        check(map.get(3) == null)
        check(map.len() == 5)
        // Inexistent key (insert)
        when (val matchVal3 = map.entry(10)) {
            is Occupied -> error("unreachable")
            is Vacant -> {
                val (view3) = matchVal3
                check(view3.insert(1000) == 1000)
            }
        }
        check(map.get(10)!! == 1000)
        check(map.len() == 6)
    }

    @kotlin.test.Test
    public fun testEntryRef() {
        val xs = listOf(Pair("One".toOwned(), 10), Pair("Two".toOwned(), 20), Pair("Three".toOwned(), 30), Pair("Four".toOwned(), 40), Pair("Five".toOwned(), 50), Pair("Six".toOwned(), 60))
        val map: HashMap  = xs.iter().cloned().collect()
        // Existing key (insert)
        when (val matchVal = map.entryRef("One")) {
            is EntryRef.Vacant -> error("unreachable")
            is EntryRef.Occupied -> {
                val (view) = matchVal
                check(view.get() == 10)
                check(view.insert(100) == 10)
            }
        }
        check(map.get("One")!! == 100)
        check(map.len() == 6)
        // Existing key (update)
        when (val matchVal1 = map.entryRef("Two")) {
            is EntryRef.Vacant -> error("unreachable")
            is EntryRef.Occupied -> {
                val (view1) = matchVal1
                val v = view1.getMut()
                val newV = v * 10
                v = newV
            }
        }
        check(map.get("Two")!! == 200)
        check(map.len() == 6)
        // Existing key (take)
        when (val matchVal2 = map.entryRef("Three")) {
            is EntryRef.Vacant -> error("unreachable")
            is EntryRef.Occupied -> {
                val (view2) = matchVal2
                check(view2.remove() == 30)
            }
        }
        check(map.get("Three") == null)
        check(map.len() == 5)
        // Inexistent key (insert)
        when (val matchVal3 = map.entryRef("Ten")) {
            is EntryRef.Occupied -> error("unreachable")
            is EntryRef.Vacant -> {
                val (view3) = matchVal3
                check(view3.insert(1000) == 1000)
            }
        }
        check(map.get("Ten")!! == 1000)
        check(map.len() == 6)
    }

    @kotlin.test.Test
    public fun testExtendRefKRefV() {
        val a = HashMap.new()
        a.insert(1, "one")
        val b = HashMap.new()
        b.insert(2, "two")
        b.insert(3, "three")
        a.extend(b)
        check(a.len() == 3)
        check(a[1] == "one")
        check(a[2] == "two")
        check(a[3] == "three")
    }

    @kotlin.test.Test
    public fun testExtendRefKvTuple() {
        fun <T : AddAssign<T>> createArr(start: T, step: T, n: Int): List<Pair<T, T>> {
            val outs: List<Pair<T, T>>  = List(n) { Pair(start, start) }
            var element = step
            outs.iterMut().skip(1).forEach({ (k, v) ->
                k += element
                v += element
                element += step
            })
            return outs
        }
        val a = HashMap.new()
        a.insert(0, 0)
        val forIter: Vec = (0 until 100).map({ i -> Pair(i, i) }).collect()
        val iter = forIter.iter()
        val vec: Vec = (100 until 200).map({ i -> Pair(i, i) }).collect()
        a.extend(iter)
        a.extend(vec)
        a.extend(createArr<Int>(200, 1, 100))
        check(a.len() == 300)
        for (item in 0 until 300) {
            check(a[item] == item)
        }
    }

    @kotlin.test.Test
    public fun testCapacityNotLessThanLen() {
        val a = HashMap.new()
        var item = 0
        for (unused in 0 until 116) {
            a.insert(item, 0)
            item += 1
        }
        check(a.capacity() > a.len())
        val free = a.capacity() - a.len()
        for (unused1 in 0 until free) {
            a.insert(item, 0)
            item += 1
        }
        check(a.len() == a.capacity())
        // Insert at capacity should cause allocation.
        a.insert(item, 0)
        check(a.capacity() > a.len())
    }

    @kotlin.test.Test
    public fun testOccupiedEntryKey() {
        val a = HashMap.new()
        val key = "hello there"
        val value = "value goes here"
        check(a.isEmpty())
        a.insert(key, value)
        check(a.len() == 1)
        check(a[key] == value)
        when (val matchVal = a.entry(key)) {
            is Vacant -> error("panic")
            is Occupied -> {
                val (e) = matchVal
                check(key == e.key())
            }
        }
        check(a.len() == 1)
        check(a[key] == value)
    }

    @kotlin.test.Test
    public fun testOccupiedEntryRefKey() {
        val a = HashMap.new()
        val key = "hello there"
        val value = "value goes here"
        check(a.isEmpty())
        a.insert(key.toOwned(), value)
        check(a.len() == 1)
        check(a[key] == value)
        when (val matchVal = a.entryRef(key)) {
            is EntryRef.Vacant -> error("panic")
            is EntryRef.Occupied -> {
                val (e) = matchVal
                check(key == e.key())
            }
        }
        check(a.len() == 1)
        check(a[key] == value)
    }

    @kotlin.test.Test
    public fun testVacantEntryKey() {
        val a = HashMap.new()
        val key = "hello there"
        val value = "value goes here"
        check(a.isEmpty())
        when (val matchVal = a.entry(key)) {
            is Occupied -> error("panic")
            is Vacant -> {
                val (e) = matchVal
                check(key == e.key())
                e.insert(value)
            }
        }
        check(a.len() == 1)
        check(a[key] == value)
    }

    @kotlin.test.Test
    public fun testVacantEntryRefKey() {
        val a: HashMap<String, String, DefaultHashBuilder, Global>  = HashMap.new()
        val key = "hello there"
        val value = "value goes here"
        check(a.isEmpty())
        when (val matchVal = a.entryRef(key)) {
            is EntryRef.Occupied -> error("panic")
            is EntryRef.Vacant -> {
                val (e) = matchVal
                check(key == e.key())
                e.insert(value)
            }
        }
        check(a.len() == 1)
        check(a[key] == value)
    }

    @kotlin.test.Test
    public fun testOccupiedEntryReplaceEntryWith() {
        val a = HashMap.new()
        val key = "a key"
        val value = "an initial value"
        val newValue = "a new value"
        val entry =
            a.entry(key).insert(value).replaceEntryWith({ k, v ->
                check(k == key)
                check(v == value)
                newValue
            })
        when (entry) {
            is Occupied -> {
                val (e) = entry
                check(e.key() == key)
                check(e.get() == newValue)
            }
            is Vacant -> error("panic")
        }
        check(a[key] == newValue)
        check(a.len() == 1)
        val entry1 =
            when (val matchVal = a.entry(key)) {
                is Occupied -> {
                    val (e1) = matchVal
                    e1.replaceEntryWith({ k, v ->
                        check(k == key)
                        check(v == newValue)
                        null
                    })
                }
                is Vacant -> error("panic")
            }
        when (entry1) {
            is Vacant -> {
                val (e2) = entry1
                check(e2.key() == key)
            }
            is Occupied -> error("panic")
        }
        check(!a.containsKey(key))
        check(a.len() == 0)
    }

    @kotlin.test.Test
    public fun testEntryAndReplaceEntryWith() {
        val a = HashMap.new()
        val key = "a key"
        val value = "an initial value"
        val newValue = "a new value"
        val entry = a.entry(key).andReplaceEntryWith({ error("panic") })
        when (entry) {
            is Vacant -> {
                val (e) = entry
                check(e.key() == key)
            }
            is Occupied -> error("panic")
        }
        a.insert(key, value)
        val entry1 =
            a.entry(key).andReplaceEntryWith({ k, v ->
                check(k == key)
                check(v == value)
                newValue
            })
        when (entry1) {
            is Occupied -> {
                val (e1) = entry1
                check(e1.key() == key)
                check(e1.get() == newValue)
            }
            is Vacant -> error("panic")
        }
        check(a[key] == newValue)
        check(a.len() == 1)
        val entry2 =
            a.entry(key).andReplaceEntryWith({ k, v ->
                check(k == key)
                check(v == newValue)
                null
            })
        when (entry2) {
            is Vacant -> {
                val (e2) = entry2
                check(e2.key() == key)
            }
            is Occupied -> error("panic")
        }
        check(!a.containsKey(key))
        check(a.len() == 0)
    }

    @kotlin.test.Test
    public fun testRetain() {
        val map: HashMap<Int, Int, DefaultHashBuilder, Global>  = (0 until 100).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
        map.retain({ k -> k % 2 == 0 })
        check(map.len() == 50)
        check(map[2] == 20)
        check(map[4] == 40)
        check(map[6] == 60)
    }

    @kotlin.test.Test
    public fun testExtractIf() {
        run {
            val map: HashMap<Int, Int, DefaultHashBuilder, Global>  = (0 until 8).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
            val drained = map.extractIf({ k -> k % 2 == 0 })
            val out = drained.collect<Vec>()
            out.sortUnstable()
            check(mutableListOf(Pair(0, 0), Pair(2, 20), Pair(4, 40), Pair(6, 60)) == out)
            check(map.len() == 4)
        }
        run {
            val map1: HashMap<Int, Int, DefaultHashBuilder, Global>  = (0 until 8).map({ x -> Pair(x, x * 10) }).toMap(LinkedHashMap())
            map1.extractIf({ k -> k % 2 == 0 }).forEach(drop)
            check(map1.len() == 4)
        }
    }

    // Note: no OOM signalling (https://github.com/rust-lang/miri/issues/613)
    @kotlin.test.Test
    public fun testTryReserve() {
        val MAX_ISIZE: Int = Int.MAX_VALUE
        val emptyBytes: HashMap<UByte, UByte, DefaultHashBuilder, Global>  = HashMap.new()
        run {
            val matchVal = emptyBytes.tryReserve(Int.MAX_VALUE)
            if (matchVal is Result.Err && matchVal.error == CapacityOverflow) {} else {
                error("usize::MAX should trigger an overflow!")
            }
        }
        run {
            val matchVal1 = emptyBytes.tryReserve(MAX_ISIZE)
            if (matchVal1 is Result.Err && matchVal1.error == CapacityOverflow) {} else {
                error("isize::MAX should trigger an overflow!")
            }
        }
        run {
            val matchVal3 = emptyBytes.tryReserve(MAX_ISIZE / 5)
            if (matchVal3 is Result.Err && matchVal3.error is AllocError) {} else {
                // This may succeed if there is enough free memory. Attempt to
                // allocate a few more hashmaps to ensure the allocation will fail.
                val emptyBytes2: HashMap<UByte, UByte, DefaultHashBuilder, Global>  = HashMap.new()
                emptyBytes2.tryReserve(MAX_ISIZE / 5)
                val emptyBytes3: HashMap<UByte, UByte, DefaultHashBuilder, Global>  = HashMap.new()
                emptyBytes3.tryReserve(MAX_ISIZE / 5)
                val emptyBytes4: HashMap<UByte, UByte, DefaultHashBuilder, Global>  = HashMap.new()
                run {
                    val matchVal2 = emptyBytes4.tryReserve(MAX_ISIZE / 5)
                    if (matchVal2 is Result.Err && matchVal2.error is AllocError) {} else {
                        error("isize::MAX / 5 should trigger an OOM!")
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun testConstWithHasher() {
        open class MyHasher : BuildHasher {
            public open fun clone(): MyHasher = MyHasher()

            public open fun buildHasher(): DefaultHasher = DefaultHasher.new()
        }
        val EMPTY_MAP: HashMap<UInt, String, MyHasher, Global> = HashMap.withHasher(MyHasher())
        val map = EMPTY_MAP
        map.insert(17, "seventeen".toOwned())
        check("seventeen" == map[17])
    }

    @kotlin.test.Test
    public fun testGetDisjointMut() {
        val map = HashMap.new()
        map.insert("foo".toOwned(), 0)
        map.insert("bar".toOwned(), 10)
        map.insert("baz".toOwned(), 20)
        map.insert("qux".toOwned(), 30)
        val xs = map.getDisjointMut(listOf("foo", "qux"))
        check(xs == listOf(RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32), RustMutableReference(allocateNativeCell(30, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
        val xs1 = map.getDisjointMut(listOf("foo", "dud"))
        check(xs1 == listOf(RustMutableReference(allocateNativeCell(0, PrimitiveLayouts.I32), PrimitiveLayouts.I32), null))
        val ys = map.getDisjointKeyValueMut(listOf("bar", "baz"))
        check(ys == listOf(Pair("bar".toOwned(), RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), Pair("baz".toOwned(), RustMutableReference(allocateNativeCell(20, PrimitiveLayouts.I32), PrimitiveLayouts.I32))))
        val ys1 = map.getDisjointKeyValueMut(listOf("bar", "dip"))
        check(ys1 == listOf(Pair("bar".toOwned(), RustMutableReference(allocateNativeCell(10, PrimitiveLayouts.I32), PrimitiveLayouts.I32)), null))
    }

    @kotlin.test.Test
    public fun testGetDisjointMutDuplicate() {
        val panic = kotlin.test.assertFails(block = fun() {
            val map = HashMap.new()
            map.insert("foo".toOwned(), 0)
        })
        kotlin.test.assertTrue(panic.message?.contains("duplicate keys found") == true)
    }

    @kotlin.test.Test
    public fun testCloneFromDoubleDrop() {
        val panic = kotlin.test.assertFails(block = fun() {
            open class CheckedDrop(
                public var panicInDrop: Boolean,
                public var dropped: Boolean,
            ) {
                public open fun clone(): CheckedDrop = CheckedDrop(panicInDrop, dropped)

                public open fun drop() {
                    if (this.panicInDrop) {
                        this.dropped = true
                        error("panic in drop")
                    }
                    if (this.dropped) {
                        error("double drop")
                    }
                    this.dropped = true
                }
            }
            val DISARMED: CheckedDrop = CheckedDrop(panicInDrop = false, dropped = false)
            val ARMED: CheckedDrop = CheckedDrop(panicInDrop = true, dropped = false)
            val map1 = HashMap.new()
            map1.insert(1, DISARMED)
            map1.insert(2, DISARMED)
            map1.insert(3, DISARMED)
            map1.insert(4, DISARMED)
            val map2 = HashMap.new()
            map2.insert(1, DISARMED)
            map2.insert(2, ARMED)
            map2.insert(3, DISARMED)
            map2.insert(4, DISARMED)
            map2.cloneFrom(map1)
        })
        kotlin.test.assertTrue(panic.message?.contains("panic in drop") == true)
    }

    @kotlin.test.Test
    public fun testCloneFromMemoryLeaks() {
        val panic = kotlin.test.assertFails(block = fun() {
            open class CheckedClone(
                public var panicInClone: Boolean,
                public var needDrop: List<Int>,
            ) {
                public open fun clone(): CheckedClone {
                    if (this.panicInClone) {
                        error("panic in clone")
                    }
                    return CheckedClone(panicInClone = this.panicInClone, needDrop = this.needDrop.toMutableList())
                }
            }
            val map1 = HashMap.new()
            map1.insert(1, CheckedClone(panicInClone = false, needDrop = mutableListOf(0, 1, 2)))
            map1.insert(2, CheckedClone(panicInClone = false, needDrop = mutableListOf(3, 4, 5)))
            map1.insert(3, CheckedClone(panicInClone = true, needDrop = mutableListOf(6, 7, 8)))
        })
        kotlin.test.assertTrue(panic.message?.contains("panic in clone") == true)
    }

    @kotlin.test.Test
    public fun testHashmapIntoIterBug() {
        val dropped: AtomicI8 = AtomicI8.new(1)
        run {
            val map = HashMap.withCapacityIn(10, MyAlloc.new(dropped.clone()))
            for (i in 0 until 10) {
                map.entry(i).orInsertWith({ "i".toOwned() })
            }
            for ((k, v) in map) {
                println("$k, $v")
            }
        }
        // All allocator clones should already be dropped.
        check(dropped.load(Ordering.SeqCst) == 0)
    }

    @kotlin.test.Test
    public fun testCloneMemoryLeaksAndDoubleDropOne() {
        val panic = kotlin.test.assertFails(block = fun() {
            val dropped: AtomicI8 = AtomicI8.new(2)
            run {
                check(ARMED_FLAGS.size == DISARMED_FLAGS.size)
                val map1: HashMap<ULong, CheckedCloneDrop<List<ULong>>, DefaultHashBuilder, MyAlloc> =
                    run {
                        val matchVal = getTestMap((ARMED_FLAGS.zip(DISARMED_FLAGS)), { n -> mutableListOf(n) }, MyAlloc.new(dropped.clone()))
                        when (matchVal) { is Result.Ok -> {
                            val map = matchVal.value
                            map
                        }; is Result.Err -> {
                            val msg = matchVal.error
                            error("$msg")
                        } }
                    }
                // Clone should normally clone a few elements, and then (when the
                // clone function panics), deallocate both its own memory, memory
                // of `dropped: Arc<AtomicI8>` and the memory of already cloned
                // elements (Vec<i32> memory inside CheckedCloneDrop).
            }
        })
        kotlin.test.assertTrue(panic.message?.contains("panic in clone") == true)
    }

    @kotlin.test.Test
    public fun testCloneMemoryLeaksAndDoubleDropTwo() {
        val panic = kotlin.test.assertFails(block = fun() {
            val dropped: AtomicI8 = AtomicI8.new(2)
            run {
                check(ARMED_FLAGS.size == DISARMED_FLAGS.size)
                val map1: HashMap<ULong, CheckedCloneDrop<ULong>, DefaultHashBuilder, Global> =
                    run {
                        val matchVal = getTestMap((DISARMED_FLAGS.zip(DISARMED_FLAGS)), { n -> n }, MyAlloc.new(dropped.clone()))
                        when (matchVal) { is Result.Ok -> {
                            val map = matchVal.value
                            map
                        }; is Result.Err -> {
                            val msg = matchVal.error
                            error("$msg")
                        } }
                    }
                var map2 =
                    run {
                        val matchVal1 = getTestMap((DISARMED_FLAGS.zip(ARMED_FLAGS)), { n -> n }, MyAlloc.new(dropped.clone()))
                        when (matchVal1) { is Result.Ok -> {
                            val map2 = matchVal1.value
                            map2
                        }; is Result.Err -> {
                            val msg1 = matchVal1.error
                            error("$msg")
                        } }
                    }
                // The `cloneFrom` should try to drop the elements of `map2` without
                // double drop and leaking the allocator. Elements that have not been
                // dropped leak their memory.
                map2.cloneFrom(map1)
            }
        })
        kotlin.test.assertTrue(panic.message?.contains("panic in drop") == true)
    }

    /**
     * We check that we have a working table if the clone operation from another
     * thread ended in a panic (when buckets of maps are equal to each other).
     */
    @kotlin.test.Test
    public fun testCatchPanicCloneFromWhenLenIsEqual() {
        val dropped: AtomicI8 = AtomicI8.new(2)
        run {
            check(ARMED_FLAGS.size == DISARMED_FLAGS.size)
            var map1 =
                run {
                    val matchVal = getTestMap((DISARMED_FLAGS.zip(DISARMED_FLAGS)), { n -> mutableListOf(n) }, MyAlloc.new(dropped.clone()))
                    when (matchVal) { is Result.Ok -> {
                        val map = matchVal.value
                        map
                    }; is Result.Err -> {
                        val msg = matchVal.error
                        error("$msg")
                    } }
                }
            scope({ s ->
                val result: ScopedJoinHandle<String> =
                    s.spawn(closure@{
                        val scopeMap =
                            run {
                                val matchVal1 = getTestMap((ARMED_FLAGS.zip(DISARMED_FLAGS)), { n -> mutableListOf(n * 2) }, MyAlloc.new(dropped.clone()))
                                when (matchVal1) { is Result.Ok -> {
                                    val map2 = matchVal1.value
                                    map2
                                }; is Result.Err -> {
                                    val msg1 = matchVal1.error
                                    return@closure msg1
                                } }
                            }
                        if (map1.table.numBuckets() != scopeMap.table.numBuckets()) {
                            return@closure "map.table.num_buckets() != scope_map.table.num_buckets(),\nleft: `${map1.table.numBuckets()}`,\nright: `${scopeMap.table.numBuckets()}`"
                        }
                        map1.cloneFrom(scopeMap)
                        "We must fail the cloning!!!".toOwned()
                    })
                when (val matchVal1 = result.join()) {
                    is Ok -> {
                        error("$msg")
                    }
                    else -> {}
                }
            })
            // Let's check that all iterators work fine and do not return elements
            // (especially `RawIterRange`, which does not depend on the number of
            // elements in the table, but looks directly at the control bytes)
            //
            // SAFETY: We know for sure that `RawTable` will outlive
            // the returned `RawIter / RawIterRange` iterator.
            check(map1.len() == 0)
            check(map1.iter().count() == 0)
            check(map1.table.iter().count() == 0)
            check(map1.table.iter().iter.count() == 0)
            for (idx in 0 until map1.table.numBuckets()) {
                val idx1 = idx.toULong()
                check(map1.table.find(idx1, { (k, _) -> k == idx1 }) == null) { "Index: $idx" }
            }
        }
        // All allocator clones should already be dropped.
        check(dropped.load(Ordering.SeqCst) == 0)
    }

    /**
     * We check that we have a working table if the clone operation from another
     * thread ended in a panic (when buckets of maps are not equal to each other).
     */
    @kotlin.test.Test
    public fun testCatchPanicCloneFromWhenLenIsNotEqual() {
        val dropped: AtomicI8 = AtomicI8.new(2)
        run {
            check(ARMED_FLAGS.size == DISARMED_FLAGS.size)
            var map1 =
                run {
                    val matchVal = getTestMap((booleanArrayOf(DISARMED).zip(booleanArrayOf(DISARMED))), { n -> mutableListOf(n) }, MyAlloc.new(dropped.clone()))
                    when (matchVal) { is Result.Ok -> {
                        val map = matchVal.value
                        map
                    }; is Result.Err -> {
                        val msg = matchVal.error
                        error("$msg")
                    } }
                }
            scope({ s ->
                val result: ScopedJoinHandle<String> =
                    s.spawn(closure1@{
                        val scopeMap =
                            run {
                                val matchVal1 = getTestMap((ARMED_FLAGS.zip(DISARMED_FLAGS)), { n -> mutableListOf(n * 2) }, MyAlloc.new(dropped.clone()))
                                when (matchVal1) { is Result.Ok -> {
                                    val map2 = matchVal1.value
                                    map2
                                }; is Result.Err -> {
                                    val msg1 = matchVal1.error
                                    return@closure1 msg1
                                } }
                            }
                        if (map1.table.numBuckets() == scopeMap.table.numBuckets()) {
                            return@closure1 "map.table.num_buckets() == scope_map.table.num_buckets(): `${map1.table.numBuckets()}`"
                        }
                        map1.cloneFrom(scopeMap)
                        "We must fail the cloning!!!".toOwned()
                    })
                when (val matchVal1 = result.join()) {
                    is Ok -> {
                        error("$msg")
                    }
                    else -> {}
                }
            })
            // Let's check that all iterators work fine and do not return elements
            // (especially `RawIterRange`, which does not depend on the number of
            // elements in the table, but looks directly at the control bytes)
            //
            // SAFETY: We know for sure that `RawTable` will outlive
            // the returned `RawIter / RawIterRange` iterator.
            check(map1.len() == 0)
            check(map1.iter().count() == 0)
            check(map1.table.iter().count() == 0)
            check(map1.table.iter().iter.count() == 0)
            for (idx in 0 until map1.table.numBuckets()) {
                val idx1 = idx.toULong()
                check(map1.table.find(idx1, { (k, _) -> k == idx1 }) == null) { "Index: $idx" }
            }
        }
        // All allocator clones should already be dropped.
        check(dropped.load(Ordering.SeqCst) == 0)
    }

    @kotlin.test.Test
    public fun testAllocationInfo() {
        check(HashMap.new().allocationSize() == 0)
        check(HashMap.new().allocationSize() == 0)
        check(HashMap.withCapacity(1).allocationSize() > sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32))
    }
}

/**
 * This is not a production quality allocator, just good enough for
 * some basic tests.
 */
public open class MmapAllocator(
    /**
     * Guarantee this is a power of 2.
     */
    public var pageSize: Int,
) : Allocator {
    public open fun clone(): MmapAllocator = MmapAllocator(pageSize)

    public open fun fitToPageSize(n: Int): Result<Int, AllocError> {
        // If n=0, give a single page (wasteful, I know).
        val n1 =
            if (n == 0) {
                this.pageSize
            } else {
                n
            }
        return when (val matchVal = n1 and (this.pageSize - 1)) {
            0 -> Result.Ok<Int, AllocError>(n1)
            else -> {
                val rem = matchVal
                (n1.checkedAdd(this.pageSize - rem)?.let { Result.Ok<Int, AllocError>(it) } ?: Result.Err<Int, AllocError>(AllocError()))
            }
        }
    }

    public override fun allocate(layout: Layout): Result<NonNull, AllocError> {
        if (layout.align() > this.pageSize) {
            return Result.Err<NonNull, AllocError>(AllocError())
        }
        val nullVal = MutPtr(0L)
        val len = (when (val tryResult = this.fitToPageSize(layout.size())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonNull, AllocError>(tryResult.error) }).toULong()
        val prot = PROT_READ or PROT_WRITE
        val flags = MAP_PRIVATE or MAP_ANON
        val addr = mmap(nullVal, len, prot, flags, -1, 0)
        // mmap returns MAP_FAILED on failure, not Null.
        if (addr == MAP_FAILED) {
            return Result.Err<NonNull, AllocError>(AllocError())
        }
        return when (val matchVal = NonNull.new(addr.cast())) {
            null -> {
                // This branch shouldn't be taken in practice, but since we
                // cannot return null as a valid pointer in our type system,
                // we attempt to handle it.
                munmap(addr, len)
                Result.Err<NonNull, AllocError>(AllocError())
            }
            else -> {
                val data = matchVal
                // SAFETY: this is NonNull.sliceFromRawParts.
                Result.Ok<NonNull, AllocError>(NonNull.newUnchecked(sliceFromRawPartsMut(data.asPtr(), len)))
            }
        }
    }

    public override fun deallocate(ptr: NonNull, layout: Layout) {
        run {
            // If they allocated it with this layout, it must round correctly.
            val size = this.fitToPageSize(layout.size()).unwrap()
            val result = munmap(ptr.asPtr().cast(), size)
            check(0 == result)
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<MmapAllocator> =
            object : TypeLayout<MmapAllocator> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MmapAllocator =
                    MmapAllocator(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MmapAllocator) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.pageSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MmapAllocator =
                    MmapAllocator(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MmapAllocator) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.pageSize)
                }
            }

        public fun new(): Result<MmapAllocator, AllocError> {
            val result = sysconf(SC_PAGESIZE)
            if (result < 1) {
                return Result.Err<MmapAllocator, AllocError>(AllocError())
            }
            val pageSize = result.toInt()
            return if (pageSize.isPowerOfTwo()) {
                Result.Ok<MmapAllocator, AllocError>(MmapAllocator(pageSize = pageSize))
            } else {
                Result.Err<MmapAllocator, AllocError>(AllocError())
            }
        }
    }
}

public class MapTestMapWithMmapAllocationsInlineTests {
    @kotlin.test.Test
    public fun testTinyAllocationGetsRoundedToPageSize() {
        val alloc = MmapAllocator.new().unwrap()
        val map: HashMap<Int, Unit, DefaultHashBuilder, Global>  = HashMap.withCapacityIn(1, alloc)
        // Size of an element plus its control byte.
        val roughBucketSize = sizeOf<Pair<Int, Unit>>(typeLayoutT = UnresolvedTypeLayout<Pair<Int, Unit>>()) + 1
        // Accounting for some misc. padding that's likely in the allocation
        // due to rounding to group width, etc.
        val overhead = 3 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
        val numBuckets = (alloc.pageSize - overhead) / roughBucketSize
        // Buckets are always powers of 2.
        val minElems = prevPow2(numBuckets)
        // Real load-factor is 7/8, but this is a lower estimation, so 1/2.
        val minCapacity = minElems shr 1
        val capacity = map.capacity()
        check(capacity >= minCapacity) { "failed: $capacity >= $minCapacity" }
        // Fill it up.
        for (i in 0 until capacity) {
            map.insert(i)
        }
        // Capacity should not have changed and it should be full.
        check(capacity == map.len())
        check(capacity == map.capacity())
        // Alright, make it grow.
        map.insert(capacity)
        check(capacity < map.capacity()) { "failed: $capacity < ${map.capacity()}" }
    }
}
