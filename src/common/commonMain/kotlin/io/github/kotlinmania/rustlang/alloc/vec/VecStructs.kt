// rust-kotlin: manual
package io.github.kotlinmania.rustlang.alloc.vec

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.convert.AsMut
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.intrinsics.KmForgettableResource
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

private fun orderingFromCmpInt(cmp: Int): Ordering =
    when {
        cmp < 0 -> Ordering.Less
        cmp > 0 -> Ordering.Greater
        else -> Ordering.Equal
    }

private fun boundIntValue(value: Any?): Int {
    require(value is Int) { "range bound must be Int, got $value" }
    return value
}

private fun resolveRangeBounds(
    src: RangeBounds<*>,
    len: Int,
): IntRange {
    val start =
        when (val sb = src.startBound()) {
            is Bound.Included -> boundIntValue(sb.value)
            is Bound.Excluded -> boundIntValue(sb.value) + 1
            is Bound.Unbounded -> 0
        }
    val endExclusive =
        when (val eb = src.endBound()) {
            is Bound.Included -> boundIntValue(eb.value) + 1
            is Bound.Excluded -> boundIntValue(eb.value)
            is Bound.Unbounded -> len
        }
    check(start >= 0 && endExclusive <= len && start <= endExclusive) {
        "range $start..<$endExclusive out of bounds for Vec of len $len"
    }
    return start until endExclusive
}

/**
 * A contiguous growable array type, written as `Vec<T, A>`, short for 'vector'.
 */
public open class Vec<T, A : Allocator> internal constructor(
    bufCtorArg: RawVec<T, A>,
    lenCtorArg: Int,
    internal val elemLayout: TypeLayout<T> = UnresolvedTypeLayout(),
    private val heapElements: MutableList<T> = mutableListOf(),
    heapCapArg: Int = lenCtorArg,
) : ExtendFromWithinSpec,
    DerefPure,
    AsRef<Vec<T, A>>,
    AsMut<Vec<T, A>>,
    Iterable<T>,
    Drop,
    KmForgettableResource {
    public var buf: RawVec<T, A> = bufCtorArg
        internal set

    internal var len: Int = lenCtorArg

    private var heapCapacity: Int = maxOf(heapCapArg, heapElements.size)

    internal val isNativeBacked: Boolean
        get() = this.elemLayout !is UnresolvedTypeLayout && this.elemLayout.sizeBytes > 0L

    private fun readAt(index: Int): T {
        if (this.isNativeBacked) {
            val base = this.buf.ptr().rawAddress
            check(base != 0L) { "Vec native buffer pointer is null at index $index" }
            return this.elemLayout.readNative(base + index.toLong() * this.elemLayout.sizeBytes)
        }
        return this.heapElements[index]
    }

    private fun writeAt(
        index: Int,
        value: T,
    ) {
        if (this.isNativeBacked) {
            val base = this.buf.ptr().rawAddress
            check(base != 0L) { "Vec native buffer pointer is null at index $index" }
            this.elemLayout.writeNative(base + index.toLong() * this.elemLayout.sizeBytes, value)
        } else {
            if (index < this.heapElements.size) {
                this.heapElements[index] = value
            } else {
                check(index == this.heapElements.size) { "Non-contiguous heap write at $index for size ${this.heapElements.size}" }
                this.heapElements.add(value)
            }
        }
    }

    private fun shiftRight(
        fromIndex: Int,
        count: Int,
    ) {
        if (count <= 0) return
        if (this.isNativeBacked) {
            for (i in (fromIndex + count) downTo (fromIndex + 1)) {
                this.writeAt(i, this.readAt(i - 1))
            }
        } else {
            // heapElements is mutated directly in insertMut when heap-backed.
        }
    }

    private fun shiftLeft(
        fromIndex: Int,
        lenBefore: Int,
    ) {
        if (this.isNativeBacked) {
            for (i in fromIndex until (lenBefore - 1)) {
                this.writeAt(i, this.readAt(i + 1))
            }
        }
    }

    private fun growHeapCapacity(minCap: Int) {
        if (minCap <= this.heapCapacity) return
        val doubled = if (this.heapCapacity == 0) 4 else this.heapCapacity * 2
        this.heapCapacity = maxOf(doubled, minCap)
    }

    public override fun iterator(): Iterator<T> = this.asSlice().iterator()

    public override fun close() {
        this.drop()
    }

    public override fun disarmCleaner() {
        this.buf.disarmCleaner()
    }

    public override fun drop() {
        if (!this.isNativeBacked) {
            for (elem in this.heapElements) {
                if (elem is Drop) {
                    elem.drop()
                }
            }
            this.heapElements.clear()
            this.heapCapacity = 0
        }
        this.len = 0
        this.buf.drop(this.elemLayout)
    }

    public open fun intoRawPartsWithAllocator(): List<Any?> {
        val len = this.len()
        val capacity = this.capacity()
        val ptr = this.asMutPtr()
        val alloc = this.allocator()
        this.disarmCleaner()
        return listOf(ptr, len, capacity, alloc)
    }

    public open fun intoPartsWithAllocator(): List<Any?> {
        val len = this.len()
        val capacity = this.capacity()
        val ptr = this.asNonNull()
        val alloc = this.allocator()
        this.disarmCleaner()
        return listOf(ptr, len, capacity, alloc)
    }

    public open fun capacity(): Int =
        if (this.elemLayout is UnresolvedTypeLayout) {
            this.heapCapacity
        } else {
            this.buf.capacity(this.elemLayout)
        }

    public open fun reserve(additional: Int) {
        if (this.isNativeBacked) {
            this.buf.reserve(this.len, additional, this.elemLayout)
        } else {
            this.growHeapCapacity(this.len + additional)
        }
    }

    public open fun reserveExact(additional: Int) {
        if (this.isNativeBacked) {
            this.buf.reserveExact(this.len, additional, this.elemLayout)
        } else {
            if (this.len + additional > this.heapCapacity) {
                this.heapCapacity = this.len + additional
            }
        }
    }

    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> =
        if (this.isNativeBacked) {
            this.buf.tryReserve(this.len, additional, this.elemLayout)
        } else {
            this.growHeapCapacity(this.len + additional)
            Result.Ok(Unit)
        }

    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> =
        if (this.isNativeBacked) {
            this.buf.tryReserveExact(this.len, additional, this.elemLayout)
        } else {
            if (this.len + additional > this.heapCapacity) {
                this.heapCapacity = this.len + additional
            }
            Result.Ok(Unit)
        }

    public open fun shrinkToFit() {
        if (this.isNativeBacked) {
            if (this.capacity() > this.len) {
                this.buf.shrinkToFit(this.len, this.elemLayout)
            }
        } else {
            this.heapCapacity = this.len
        }
    }

    public open fun shrinkTo(minCapacity: Int) {
        if (this.isNativeBacked) {
            if (this.capacity() > minCapacity) {
                this.buf.shrinkToFit(maxOf(this.len, minCapacity), this.elemLayout)
            }
        } else if (this.heapCapacity > minCapacity) {
            this.heapCapacity = maxOf(this.len, minCapacity)
        }
    }

    public open fun tryShrinkToFit(): Result<Unit, TryReserveError> =
        if (this.isNativeBacked) {
            if (this.capacity() > this.len) {
                this.buf.tryShrinkToFit(this.len, this.elemLayout)
            } else {
                Result.Ok(Unit)
            }
        } else {
            this.heapCapacity = this.len
            Result.Ok(Unit)
        }

    public open fun tryShrinkTo(minCapacity: Int): Result<Unit, TryReserveError> =
        if (this.isNativeBacked) {
            if (this.capacity() > minCapacity) {
                this.buf.tryShrinkToFit(maxOf(this.len, minCapacity), this.elemLayout)
            } else {
                Result.Ok(Unit)
            }
        } else {
            if (this.heapCapacity > minCapacity) {
                this.heapCapacity = maxOf(this.len, minCapacity)
            }
            Result.Ok(Unit)
        }

    public open fun intoBoxedSlice(): List<T> {
        val slice = this.asSlice()
        this.drop()
        return slice
    }

    public open fun truncate(len: Int) {
        if (len >= this.len) {
            return
        }
        val target = len.coerceAtLeast(0)
        if (!this.isNativeBacked) {
            while (this.heapElements.size > target) {
                val removed = this.heapElements.removeAt(this.heapElements.size - 1)
                if (removed is Drop) {
                    removed.drop()
                }
            }
        }
        this.len = target
    }

    public open fun clear() {
        this.truncate(0)
    }

    public open fun asSlice(): List<T> {
        if (this.isNativeBacked) {
            if (this.len == 0) return emptyList()
            val base = this.buf.ptr().rawAddress
            return List(this.len) { idx ->
                this.elemLayout.readNative(base + idx.toLong() * this.elemLayout.sizeBytes)
            }
        }
        return this.heapElements.subList(0, minOf(this.len, this.heapElements.size)).toList()
    }

    public open fun get(index: Int): T {
        check(index in 0 until this.len) { "index out of bounds: $index not in 0..<${this.len}" }
        return this.readAt(index)
    }

    public open fun getChecked(index: Int): T? =
        if (index in 0 until this.len) {
            this.readAt(index)
        } else {
            null
        }

    public open fun set(
        index: Int,
        element: T,
    ): T {
        check(index in 0 until this.len) { "index out of bounds: $index not in 0..<${this.len}" }
        val prev = this.readAt(index)
        this.writeAt(index, element)
        return prev
    }

    public open fun first(): T? = if (this.len > 0) this.readAt(0) else null

    public open fun last(): T? = if (this.len > 0) this.readAt(this.len - 1) else null

    public open fun asPtr(): ConstPtr = this.buf.ptr().asConstPtr()

    public open fun asMutPtr(): MutPtr = this.buf.ptr()

    public open fun asNonNull(): NonNull = this.buf.nonNull()

    public open fun allocator(): A = this.buf.allocator()

    public open fun setLen(newLen: Int) {
        this.len = newLen
    }

    public open fun swapRemove(index: Int): T {
        val curLen = this.len
        check(index in 0 until curLen) { "swap_remove index (is $index) should be < len (is $curLen)" }
        val value = this.readAt(index)
        if (index != curLen - 1) {
            if (this.isNativeBacked) {
                this.writeAt(index, this.readAt(curLen - 1))
            } else {
                this.heapElements[index] = this.heapElements[curLen - 1]
            }
        }
        if (!this.isNativeBacked) {
            this.heapElements.removeAt(curLen - 1)
        }
        this.len = curLen - 1
        return value
    }

    public open fun insert(
        index: Int,
        element: T,
    ) {
        this.insertMut(index, element)
    }

    public open fun insertMut(
        index: Int,
        element: T,
    ): T {
        val curLen = this.len
        check(index in 0..curLen) { "insertion index (is $index) should be <= len (is $curLen)" }
        if (this.isNativeBacked) {
            if (curLen == this.capacity()) {
                this.buf.growOne(this.elemLayout)
            }
            if (index < curLen) {
                this.shiftRight(index, curLen - index)
            }
            this.writeAt(index, element)
        } else {
            this.growHeapCapacity(curLen + 1)
            this.heapElements.add(index, element)
        }
        this.len = curLen + 1
        return element
    }

    public open fun remove(index: Int): T =
        this.tryRemove(index) ?: error("removal index (is $index) should be < len (is ${this.len})")

    public open fun tryRemove(index: Int): T? {
        val curLen = this.len
        if (index !in 0 until curLen) {
            return null
        }
        val ret = this.readAt(index)
        if (this.isNativeBacked) {
            this.shiftLeft(index, curLen)
        } else {
            this.heapElements.removeAt(index)
        }
        this.len = curLen - 1
        return ret
    }

    public open fun retain(f: (T) -> Boolean) {
        this.retainMut(f)
    }

    public open fun retainMut(f: (T) -> Boolean) {
        var writeIdx = 0
        val curLen = this.len
        for (readIdx in 0 until curLen) {
            val elem = this.readAt(readIdx)
            if (f(elem)) {
                if (writeIdx != readIdx) {
                    this.writeAt(writeIdx, elem)
                }
                writeIdx += 1
            } else if (!this.isNativeBacked && elem is Drop) {
                elem.drop()
            }
        }
        if (!this.isNativeBacked) {
            while (this.heapElements.size > writeIdx) {
                this.heapElements.removeAt(this.heapElements.size - 1)
            }
        }
        this.len = writeIdx
    }

    public open fun retainUnstable(f: (T) -> Boolean) {
        var idx = 0
        while (idx < this.len) {
            if (f(this.readAt(idx))) {
                idx += 1
            } else {
                val removed = this.swapRemove(idx)
                if (!this.isNativeBacked && removed is Drop) {
                    removed.drop()
                }
            }
        }
    }

    public open fun <K> dedupByKey(key: (T) -> K) {
        this.dedupBy { a, b -> key(a) == key(b) }
    }

    public open fun dedup() {
        this.dedupBy { a, b -> a == b }
    }

    public open fun dedupBy(sameBucket: (T, T) -> Boolean) {
        val curLen = this.len
        if (curLen <= 1) return
        var writeIdx = 1
        var prev = this.readAt(0)
        for (readIdx in 1 until curLen) {
            val cur = this.readAt(readIdx)
            if (!sameBucket(cur, prev)) {
                if (writeIdx != readIdx) {
                    this.writeAt(writeIdx, cur)
                }
                prev = cur
                writeIdx += 1
            } else if (!this.isNativeBacked && cur is Drop) {
                cur.drop()
            }
        }
        if (!this.isNativeBacked) {
            while (this.heapElements.size > writeIdx) {
                this.heapElements.removeAt(this.heapElements.size - 1)
            }
        }
        this.len = writeIdx
    }

    public open fun push(value: T) {
        val curLen = this.len
        if (this.isNativeBacked) {
            if (curLen == this.capacity()) {
                this.buf.growOne(this.elemLayout)
            }
            this.writeAt(curLen, value)
        } else {
            this.growHeapCapacity(curLen + 1)
            this.heapElements.add(value)
        }
        this.len = curLen + 1
    }

    public open fun pushWithinCapacity(value: T): Result<Unit, T> {
        val curLen = this.len
        if (curLen == this.capacity()) {
            return Result.Err(value)
        }
        if (this.isNativeBacked) {
            this.writeAt(curLen, value)
        } else {
            this.heapElements.add(value)
        }
        this.len = curLen + 1
        return Result.Ok(Unit)
    }

    public open fun pop(): T? {
        val curLen = this.len
        if (curLen == 0) return null
        val nextLen = curLen - 1
        this.len = nextLen
        return if (this.isNativeBacked) {
            this.readAt(nextLen)
        } else {
            this.heapElements.removeAt(nextLen)
        }
    }

    public open fun popIf(predicate: (T) -> Boolean): T? {
        val lastVal = this.last() ?: return null
        return if (predicate(lastVal)) this.pop() else null
    }

    public open fun append(other: Vec<T, A>) {
        if (other.isEmpty()) return
        this.extendFromSlice(other.asSlice())
        if (!other.isNativeBacked) {
            other.heapElements.clear()
        }
        other.len = 0
    }

    public open fun splitOff(at: Int): Vec<T, A> {
        val curLen = this.len
        check(at in 0..curLen) { "`at` split index (is $at) should be <= len (is $curLen)" }
        val otherLen = curLen - at
        val other = Vec.withCapacityIn(otherLen, this.allocator(), this.elemLayout)
        for (i in at until curLen) {
            other.push(this.readAt(i))
        }
        if (!this.isNativeBacked) {
            while (this.heapElements.size > at) {
                this.heapElements.removeAt(this.heapElements.size - 1)
            }
        }
        this.len = at
        return other
    }

    public open fun resize(
        newLen: Int,
        value: T,
    ) {
        val curLen = this.len
        if (newLen > curLen) {
            val additional = newLen - curLen
            this.reserve(additional)
            repeat(additional) {
                this.push(value)
            }
        } else {
            this.truncate(newLen)
        }
    }

    public open fun resizeWith(
        newLen: Int,
        f: () -> T,
    ) {
        val curLen = this.len
        if (newLen > curLen) {
            val additional = newLen - curLen
            this.reserve(additional)
            repeat(additional) {
                this.push(f())
            }
        } else {
            this.truncate(newLen)
        }
    }

    public open fun leak(): List<T> {
        val snapshot = this.asSlice()
        this.disarmCleaner()
        return snapshot
    }

    public open fun extendFromSlice(other: List<T>) {
        if (other.isEmpty()) return
        this.reserve(other.size)
        for (item in other) {
            this.push(item)
        }
    }

    public open fun <I, R : RangeBounds<I>> extendFromWithin(src: R) {
        this.specExtendFromWithin(resolveRangeBounds(src, this.len))
    }

    public open fun extendFromWithin(src: IntRange) {
        this.specExtendFromWithin(src)
    }

    public override fun specExtendFromWithin(src: IntRange) {
        val start = src.first
        val endExclusive = if (src.isEmpty()) src.first else src.last + 1
        check(start >= 0 && endExclusive <= this.len && start <= endExclusive) {
            "range $start..<$endExclusive out of bounds for Vec of len ${this.len}"
        }
        val count = endExclusive - start
        if (count <= 0) return
        this.reserve(count)
        for (offset in 0 until count) {
            this.push(this.readAt(start + offset))
        }
    }

    public open fun extendWith(
        n: Int,
        value: T,
    ) {
        if (n <= 0) return
        this.reserve(n)
        repeat(n) {
            this.push(value)
        }
    }

    public open fun <I : Iterable<T>> extend(iter: I) {
        for (item in iter) {
            this.push(item)
        }
    }

    public open fun extendOne(item: T) {
        this.push(item)
    }

    public open fun extendReserve(additional: Int) {
        this.reserve(additional)
    }

    public open fun <U> recycle(): Vec<U, A> where U : Recyclable<T> =
        Vec.withCapacityIn(0, this.allocator(), UnresolvedTypeLayout())

    public open fun deref(): List<T> = this.asSlice()

    public open fun len(): Int = this.len

    public open fun isEmpty(): Boolean = this.len == 0

    public open fun clone(): Vec<T, A> =
        Vec.fromListIn(this.asSlice(), this.allocator(), this.elemLayout)

    public open fun cloneFrom(source: Vec<T, A>) {
        this.clear()
        this.extendFromSlice(source.asSlice())
    }

    public open fun <A2 : Allocator> eq(other: Vec<T, A2>): Boolean =
        this.asSlice() == other.asSlice()

    public open fun <A2 : Allocator> ne(other: Vec<T, A2>): Boolean = !this.eq(other)

    public open fun <A2 : Allocator> partialCmp(other: Vec<T, A2>): Ordering? =
        kmPartialCompareValues(this.asSlice(), other.asSlice())?.let(::orderingFromCmpInt)

    public open fun <A2 : Allocator> cmp(other: Vec<T, A2>): Ordering =
        orderingFromCmpInt(kmCompareValues(this.asSlice(), other.asSlice()))

    public override fun equals(other: Any?): Boolean =
        other is Vec<*, *> && this.asSlice() == other.asSlice()

    public override fun hashCode(): Int = this.asSlice().hashCode()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        f.append(this.asSlice().toString())
        return Result.Ok(Unit)
    }

    public override fun asRef(): Vec<T, A> = this

    public override fun asMut(): Vec<T, A> = this

    public open fun asRefTSlice(): List<T> = this.asSlice()

    public companion object {
        public fun <T, A : Allocator> withCapacityIn(
            capacity: Int,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> =
            Vec(
                bufCtorArg = RawVec.withCapacityIn(capacity, alloc, typeLayoutT),
                lenCtorArg = 0,
                elemLayout = typeLayoutT,
                heapCapArg = capacity,
            )

        public fun <T, A : Allocator> newIn(
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> =
            Vec(
                bufCtorArg = RawVec.newIn(alloc, typeLayoutT),
                lenCtorArg = 0,
                elemLayout = typeLayoutT,
                heapCapArg = 0,
            )

        public fun <T, A : Allocator> tryWithCapacityIn(
            capacity: Int,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Result<Vec<T, A>, TryReserveError> =
            when (val res = RawVec.tryWithCapacityIn(capacity, alloc, typeLayoutT)) {
                is Result.Ok ->
                    Result.Ok(
                        Vec(
                            bufCtorArg = res.value,
                            lenCtorArg = 0,
                            elemLayout = typeLayoutT,
                            heapCapArg = capacity,
                        ),
                    )
                is Result.Err -> Result.Err(res.value)
            }

        public fun <T, A : Allocator> fromRawPartsIn(
            ptr: MutPtr,
            length: Int,
            capacity: Int,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> =
            Vec(
                bufCtorArg = RawVec.fromRawPartsIn(ptr, capacity, alloc, typeLayoutT),
                lenCtorArg = length,
                elemLayout = typeLayoutT,
                heapCapArg = capacity,
            )

        public fun <T, A : Allocator> fromPartsIn(
            ptr: NonNull,
            length: Int,
            capacity: Int,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> =
            Vec(
                bufCtorArg = RawVec.fromNonnullIn(ptr, capacity, alloc, typeLayoutT),
                lenCtorArg = length,
                elemLayout = typeLayoutT,
                heapCapArg = capacity,
            )

        public fun <T, A : Allocator> fromListIn(
            items: List<T>,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> {
            val out = withCapacityIn(items.size, alloc, typeLayoutT)
            out.extendFromSlice(items)
            return out
        }

        public fun <T, A : Allocator> fromElemIn(
            elem: T,
            n: Int,
            alloc: A,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, A> {
            val out = withCapacityIn(n, alloc, typeLayoutT)
            out.extendWith(n, elem)
            return out
        }

        public fun <T> fromElem(
            elem: T,
            n: Int,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, Global> = fromElemIn(elem, n, Global(), typeLayoutT)

        public fun <T> fromList(
            items: List<T>,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, Global> = fromListIn(items, Global(), typeLayoutT)

        public fun <T> from(
            s: List<T>,
            typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
        ): Vec<T, Global> = fromList(s, typeLayoutT)
    }
}

public fun <T> Vec.Companion.new(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Vec<T, Global> =
    Vec.newIn(Global(), typeLayoutT)

public fun <T> Vec.Companion.withCapacity(
    capacity: Int,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> = Vec.withCapacityIn(capacity, Global(), typeLayoutT)

public fun <T> Vec.Companion.tryWithCapacity(
    capacity: Int,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Result<Vec<T, Global>, TryReserveError> = Vec.tryWithCapacityIn(capacity, Global(), typeLayoutT)

public fun <T> Vec.Companion.fromRawParts(
    ptr: MutPtr,
    length: Int,
    capacity: Int,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> = Vec.fromRawPartsIn(ptr, length, capacity, Global(), typeLayoutT)

public fun <T> Vec.Companion.fromParts(
    ptr: NonNull,
    length: Int,
    capacity: Int,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> = Vec.fromPartsIn(ptr, length, capacity, Global(), typeLayoutT)

public fun <T> Vec.Companion.fromFn(
    length: Int,
    f: (Int) -> T,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> {
    val out = Vec.withCapacity(length, typeLayoutT)
    for (i in 0 until length) {
        out.push(f(i))
    }
    return out
}

public fun <T> Vec<T, Global>.intoRawParts(): Triple<MutPtr, Int, Int> {
    val len = this.len()
    val cap = this.capacity()
    val ptr = this.asMutPtr()
    this.disarmCleaner()
    return Triple(ptr, len, cap)
}

public fun <T> Vec<T, Global>.intoParts(): Triple<NonNull, Int, Int> {
    val len = this.len()
    val cap = this.capacity()
    val ptr = this.asNonNull()
    this.disarmCleaner()
    return Triple(ptr, len, cap)
}

public fun <A : Allocator> Vec<UByte, A>.tryExtendFromSliceOfBytes(other: List<UByte>): Result<Unit, TryReserveError> {
    val tryRes = this.tryReserve(other.size)
    if (tryRes is Result.Err) return tryRes
    this.extendFromSlice(other)
    return Result.Ok(Unit)
}

public fun <T, A : Allocator> Vec<List<T>, A>.intoFlattened(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Vec<T, A> {
    var total = 0
    val slices = this.asSlice()
    for (s in slices) {
        total += s.size
    }
    val out = Vec.withCapacityIn(total, this.allocator(), typeLayoutT)
    for (s in slices) {
        out.extendFromSlice(s)
    }
    this.drop()
    return out
}

public fun <T, I : Iterable<T>> fromIteratorFromIterVecT(
    iter: I,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> {
    val out = Vec.new(typeLayoutT)
    out.extend(iter)
    return out
}

public fun <T> defaultVecT(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Vec<T, Global> =
    Vec.new(typeLayoutT)

public fun <T> fromVecT(
    s: List<T>,
    typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(),
): Vec<T, Global> = Vec.fromList(s, typeLayoutT)

public fun fromVecUByte(s: String): Vec<UByte, Global> =
    Vec.fromList(s.encodeToByteArray().map { it.toUByte() }, PrimitiveLayouts.U8)
