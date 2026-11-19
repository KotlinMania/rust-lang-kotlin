// rust-kotlin: manual
package io.github.kotlinmania.rustlang

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.boxed.fromRaw
import io.github.kotlinmania.rustlang.alloc.boxed.intoRaw
import io.github.kotlinmania.rustlang.alloc.boxed.new
import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.alloc.rawvec.RawVec
import io.github.kotlinmania.rustlang.alloc.string.RustAllocString
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.new
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.cell.lazy.LazyCell
import io.github.kotlinmania.rustlang.core.cell.once.OnceCell
import io.github.kotlinmania.rustlang.core.char.RustChar
import io.github.kotlinmania.rustlang.core.cmp.Reverse
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.cmp.partialCmp
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.num.f32.toBits
import io.github.kotlinmania.rustlang.core.num.f64.toBits
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU32
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU8
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroUsize
import io.github.kotlinmania.rustlang.core.num.saturating.Saturating
import io.github.kotlinmania.rustlang.core.num.saturating.add
import io.github.kotlinmania.rustlang.core.num.wrapping.Wrapping
import io.github.kotlinmania.rustlang.core.num.wrapping.add
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.OneSidedRange
import io.github.kotlinmania.rustlang.core.ops.range.OneSidedRangeBound
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.ops.range.RangeTo
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asChunksMut
import io.github.kotlinmania.rustlang.core.slice.copyWithin
import io.github.kotlinmania.rustlang.core.slice.first
import io.github.kotlinmania.rustlang.core.slice.getMut
import io.github.kotlinmania.rustlang.core.slice.last
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.slice.memchr.memrchr
import io.github.kotlinmania.rustlang.core.slice.splitFirst
import io.github.kotlinmania.rustlang.core.slice.splitLast
import io.github.kotlinmania.rustlang.core.slice.splitOff
import io.github.kotlinmania.rustlang.core.str.fromUtf8
import io.github.kotlinmania.rustlang.core.str.fromUtf8Lossy
import io.github.kotlinmania.rustlang.core.str.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU64
import io.github.kotlinmania.rustlang.core.sync.atomic.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.intoInner
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.new
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.core.sync.atomic.swap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import io.github.kotlinmania.rustlang.core.cmp.Ordering as CmpOrdering
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering as AtomicOrdering

private class KernelRangeBounds(
    private val start: Int,
    private val end: Int,
) : RangeBounds<Int> {
    override fun startBound(): Bound<Int> = Bound.Included(start)

    override fun endBound(): Bound<Int> = Bound.Excluded(end)
}

private class KernelOneSidedEnd(
    private val end: Int,
) : OneSidedRange<Int> {
    override fun startBound(): Bound<Int> = Bound.Unbounded()

    override fun endBound(): Bound<Int> = Bound.Excluded(end)

    override fun bound(): Pair<OneSidedRangeBound, Int> = Pair(OneSidedRangeBound.End, end)
}

class KernelMemoryAndCoreTest {
    @Test
    fun linearMemoryReadWriteAndSliceView() {
        val elemBytes = PrimitiveLayouts.I32.strideBytes
        val totalBytes = elemBytes * 4L
        val addr = kmAlloc(totalBytes, PrimitiveLayouts.I32.alignBytes)
        assertTrue(addr != 0L)
        try {
            val base = MutPtr(addr)
            base.write(11, typeLayoutT = PrimitiveLayouts.I32)
            base.add(1, typeLayoutT = PrimitiveLayouts.I32).write(22, typeLayoutT = PrimitiveLayouts.I32)
            base.byteAdd(elemBytes.toInt() * 2).write(33, typeLayoutT = PrimitiveLayouts.I32)
            base.add(3, typeLayoutT = PrimitiveLayouts.I32).write(44, typeLayoutT = PrimitiveLayouts.I32)

            assertEquals(11, base.read(typeLayoutT = PrimitiveLayouts.I32))
            assertEquals(33, base.add(2, typeLayoutT = PrimitiveLayouts.I32).read(typeLayoutT = PrimitiveLayouts.I32))
            assertEquals(44, PrimitiveLayouts.I32.readNative(addr + elemBytes * 3L))

            val view = linearSliceView(ConstPtr(addr), 4, PrimitiveLayouts.I32)
            assertEquals(listOf(11, 22, 33, 44), view.toList())
        } finally {
            kmDealloc(addr, totalBytes, PrimitiveLayouts.I32.alignBytes)
        }
    }

    @Test
    fun layoutAndAlignmentSemantics() {
        val okResult = Layout.fromSizeAlign(16, 8)
        assertTrue(okResult.isOk())
        val layout = okResult.unwrap()
        assertEquals(16, layout.size())
        assertEquals(8, layout.align())
        assertEquals(8, layout.alignment().asUsize())

        val unchecked = Layout.fromSizeAlignUnchecked(12, 4)
        assertEquals(12, unchecked.size())
        assertEquals(4, unchecked.align())

        val badAlign = Layout.fromSizeAlign(8, 3)
        assertTrue(badAlign.isErr())

        val align16 = Alignment.new(16)
        assertNotNull(align16)
        assertEquals(16, align16.asUsize())
        assertEquals(4u, align16.log2())
        assertEquals(1, Alignment.MIN.asUsize())
        assertNull(Alignment.new(3))
        assertNull(Alignment.new(0))
    }

    @Test
    fun nonNullOperations() {
        assertNull(NonNull.new(MutPtr(0L)))
        val dangling = NonNull.dangling()
        assertTrue(dangling.rawAddress != 0L)

        val nz64 = NonZero.new<NonZeroUsizeInner, Int>(64)
        assertNotNull(nz64)
        val withoutProv = NonNull.withoutProvenance(nz64)
        assertEquals(64L, withoutProv.rawAddress)
        assertEquals(72L, withoutProv.byteAdd(8).rawAddress)
        assertEquals(60L, withoutProv.byteAdd(8).byteSub(12).rawAddress)

        val addr = kmAlloc(8L, 8L)
        try {
            val nn = NonNull.new(MutPtr(addr))
            assertNotNull(nn)
            assertEquals(addr, nn.asPtr().rawAddress)
        } finally {
            kmDealloc(addr, 8L, 8L)
        }
    }

    @Test
    fun numeric128BitFloatBitsWrappingAndSaturating() {
        val a = U128.fromULong(1uL shl 60)
        val b = U128.fromInt(16)
        val prod = a * b
        assertEquals(a, prod / b)
        assertEquals(U128.fromInt(0), prod % b)
        assertEquals(U128.fromInt(8), U128.fromInt(2).pow(3u))
        assertEquals(U128.fromInt(32), U128.fromInt(2) shl 4)
        assertEquals(U128.fromInt(2), U128.fromInt(32) shr 4)
        assertEquals(128u, U128.MAX.countOnes())

        val neg = -I128.fromInt(42)
        assertEquals(I128.fromInt(-40), neg + I128.fromInt(2))
        assertEquals(I128.fromInt(-44), neg - I128.fromInt(2))

        assertEquals(0x3F800000u, 1.0f.toBits())
        assertEquals(0x3FF0000000000000uL, 1.0.toBits())
        val half = F16.fromFloat(2.5f)
        assertEquals(2.5f, half.toFloat())

        val wrapped = Wrapping(Int.MAX_VALUE).add(Wrapping(1))
        assertEquals(Int.MIN_VALUE, wrapped.value)
        val saturated = Saturating(Int.MAX_VALUE - 2).add(Saturating(10))
        assertEquals(Int.MAX_VALUE, saturated.value)
    }

    @Test
    fun charAndUtf8SliceConversions() {
        val ch = RustChar.fromU32('€'.code.toUInt())
        assertNotNull(ch)
        assertEquals(3, ch.lenUtf8())
        assertFalse(ch.isAscii())
        val buf = mutableListOf<UByte>(0u, 0u, 0u)
        val encoded = ch.encodeUtf8(buf)
        assertEquals(listOf(0xE2u.toUByte(), 0x82u.toUByte(), 0xACu.toUByte()), encoded)
        assertEquals(RustChar.fromU32Unchecked('a'.code.toUInt()), RustChar.fromDigit(10u, 16u))

        val okStr = fromUtf8(encoded)
        assertTrue(okStr.isOk())
        assertEquals("€", okStr.unwrap())
        assertEquals("€", fromUtf8Unchecked(encoded))

        val invalidBytes = listOf(0x41u.toUByte(), 0xFFu.toUByte(), 0x42u.toUByte())
        assertTrue(fromUtf8(invalidBytes).isErr())
        assertEquals("A\uFFFD" + "B", fromUtf8Lossy(invalidBytes))
    }

    @Test
    fun sliceFunctionsAndMemchrWordScan() {
        val nums = mutableListOf(10, 20, 30, 40, 50)
        assertEquals(10, nums.first())
        assertEquals(50, nums.last())

        val sf = nums.splitFirst()
        assertNotNull(sf)
        assertEquals(10, sf.first)
        assertEquals(listOf(20, 30, 40, 50), sf.second)

        val sl = nums.splitLast()
        assertNotNull(sl)
        assertEquals(50, sl.first)
        assertEquals(listOf(10, 20, 30, 40), sl.second)

        assertEquals(listOf(20, 30), nums.getMut(1..2))
        nums.copyWithin(KernelRangeBounds(0, 2), 3)
        assertEquals(listOf(10, 20, 30, 10, 20), nums)

        val chunks = listOf(1, 2, 3, 4, 5).asChunksMut(2)
        assertEquals(2, chunks.first.size)
        assertEquals(listOf(5), chunks.second)

        val splitPrefix = listOf('a', 'b', 'c', 'd').splitOff(KernelOneSidedEnd(2))
        assertEquals(listOf('a', 'b'), splitPrefix)

        // Buffer >= 16 bytes exercises memchr/memrchr 4-byte aligned word scanning path.
        val haystack = "alpha-beta-gamma-omega-needle-z".encodeToByteArray().map { it.toUByte() }
        val target = 'e'.code.toUByte()
        assertEquals(7, memchr(target, haystack))
        assertEquals(28, memrchr(target, haystack))
        assertNull(memchr('Q'.code.toUByte(), haystack))
    }

    @Test
    fun optionAndResultCombinators() {
        val someTen: Option<Int> = Option.Some(10)
        val noneInt: Option<Int> = Option.None()
        assertTrue(someTen.isSome())
        assertTrue(noneInt.isNone())
        assertEquals(10, someTen.unwrap())
        assertEquals(99, noneInt.unwrapOr(99))
        assertEquals(20, someTen.map { it * 2 }.unwrap())
        assertTrue(someTen.andThen<Int> { Option.None() }.isNone())
        assertEquals(listOf(10), someTen.asMutSlice())

        val okVal: Result<Int, String> = Result.Ok(7)
        val errVal: Result<Int, String> = Result.Err("fail")
        assertTrue(okVal.isOk())
        assertTrue(errVal.isErr())
        assertEquals(7, okVal.unwrap())
        assertEquals(-1, errVal.unwrapOr(-1))
        assertEquals(14, okVal.map { it * 2 }.unwrap())
        assertEquals("fail!", errVal.mapErr { "$it!" }.unwrapErr())
        assertEquals(10, okVal.andThen { Result.Ok<Int, String>(it + 3) }.unwrap())
    }

    @Test
    fun orderingMinMaxAndRanges() {
        assertTrue(CmpOrdering.Equal.isEq())
        assertFalse(CmpOrdering.Less.isEq())
        assertFalse(CmpOrdering.Greater.isEq())
        assertEquals(3, min(3, 9))
        assertEquals(9, max(3, 9))
        assertEquals(CmpOrdering.Greater, Reverse(3).partialCmp(Reverse(9)))

        val r = Range(start = 2, end = 5)
        assertTrue(r.contains(2))
        assertFalse(r.contains(5))
        assertTrue(RangeFrom(start = 10).contains(100))
        assertFalse(RangeFrom(start = 10).contains(9))
        assertTrue(RangeTo(end = 4).contains(3))
        assertFalse(RangeTo(end = 4).contains(4))
        val inc = RangeInclusive(start = 1, end = 3, exhausted = false)
        assertTrue(inc.contains(3))
    }

    @Test
    fun nonZeroAndForget() {
        val nzU8 = NonZero.new<NonZeroU8Inner, UByte>(7u.toUByte())
        assertNotNull(nzU8)
        assertEquals(7u.toUByte(), NonZeroU8(nzU8.get()).get())
        assertNull(NonZero.new<NonZeroU8Inner, UByte>(0u.toUByte()))

        val nzA = NonZero.new<NonZeroU32Inner, UInt>(10u)
        val nzB = NonZero.new<NonZeroU32Inner, UInt>(4u)
        assertNotNull(nzA)
        assertNotNull(nzB)
        assertEquals(4u, NonZeroU32(nzA.get()).min(NonZeroU32(nzB.get())).get())

        val nzUsize = NonZero.new<NonZeroUsizeInner, Int>(12)
        assertNotNull(nzUsize)
        assertEquals(12, NonZeroUsize(nzUsize.get()).get())

        forget("dropped-value")
    }

    @Test
    fun cellsInteriorMutability() {
        val c1 = Cell.new(5)
        val c2 = Cell.new(20)
        assertEquals(5, c1.get())
        assertEquals(5, c1.replace(10))
        c1.set(12)
        c1.swap(c2)
        assertEquals(20, c1.get())
        assertEquals(12, c2.get())

        val rc = RefCell.new(100)
        val mutGuard = rc.borrowMut()
        try {
            mutGuard.value = 250
        } finally {
            mutGuard.close()
        }
        val readGuard = rc.borrow()
        try {
            assertEquals(250, readGuard.value)
        } finally {
            readGuard.close()
        }

        val once = OnceCell.new<String>()
        assertNull(once.get())
        assertTrue(once.set("hello").isOk())
        assertTrue(once.set("world").isErr())
        assertEquals("hello", once.get())

        var initCount = 0
        val lazy =
            LazyCell.new {
                initCount += 1
                42
            }
        assertNull(LazyCell.get(lazy))
        assertEquals(42, LazyCell.force(lazy))
        assertEquals(42, LazyCell.get(lazy))
        assertEquals(1, initCount)
    }

    @Test
    fun atomicsNativeStorage() {
        val ab = AtomicBool.new(false)
        try {
            assertFalse(ab.load(AtomicOrdering.SeqCst))
            ab.store(true, AtomicOrdering.SeqCst)
            assertTrue(ab.swap(false, AtomicOrdering.AcqRel))
            assertFalse(ab.intoInner())
        } finally {
            val abLayout = ab.fieldLayout
            kmDealloc(ab.asPtr().rawAddress, maxOf(1L, abLayout.sizeBytes), maxOf(1L, abLayout.alignBytes))
        }

        val au64 = AtomicU64.new(10uL)
        try {
            assertEquals(10uL, au64.load(AtomicOrdering.Acquire))
            assertEquals(10uL, au64.fetchAdd(5uL, AtomicOrdering.SeqCst))
            assertEquals(15uL, au64.intoInner())
        } finally {
            val u64Layout = au64.fieldLayout
            kmDealloc(au64.fieldStorage.rawAddress, maxOf(1L, u64Layout.sizeBytes), maxOf(1L, u64Layout.alignBytes))
        }

        val targetAddr = kmAlloc(4L, 4L)
        val ap = AtomicPtr.new(MutPtr(targetAddr))
        try {
            assertEquals(targetAddr, ap.load(AtomicOrdering.Acquire).rawAddress)
            assertEquals(targetAddr, ap.swap(MutPtr(0L), AtomicOrdering.SeqCst).rawAddress)
            assertEquals(0L, ap.intoInner().rawAddress)
        } finally {
            val ptrLayout = ap.fieldLayout
            kmDealloc(ap.asPtr().rawAddress, maxOf(1L, ptrLayout.sizeBytes), maxOf(1L, ptrLayout.alignBytes))
            kmDealloc(targetAddr, 4L, 4L)
        }
    }

    @Test
    fun cStrAndCStringRoundTrip() {
        val fromStrRes = CString.new("hello")
        assertTrue(fromStrRes.isOk())
        val cstr1 = fromStrRes.unwrap()
        try {
            val ptrCStr = CStr.fromPtr(cstr1.asPtr())
            assertEquals("hello", ptrCStr.toStr().unwrap())
            assertEquals(5, ptrCStr.countBytes())
        } finally {
            cstr1.drop()
        }

        val withInnerNul = CString.new("ab\u0000cd")
        assertTrue(withInnerNul.isErr())

        val cstr2 = CString.fromVecWithNulUnchecked(listOf(0x61u.toUByte(), 0x62u.toUByte(), 0u.toUByte()))
        val rawPtr = cstr2.intoRaw()
        val rebuilt = CString.fromRaw(rawPtr)
        try {
            assertEquals("ab", CStr.fromPtr(rebuilt.asPtr()).toStr().unwrap())
        } finally {
            rebuilt.drop()
        }

        val nzBytes =
            listOf(0x72u.toUByte(), 0x75u.toUByte(), 0x73u.toUByte(), 0x74u.toUByte()).map { b ->
                NonZeroU8(NonZero.new<NonZeroU8Inner, UByte>(b)!!.get())
            }
        val fromNz = CString.from(nzBytes)
        try {
            assertEquals("rust", CStr.fromPtr(fromNz.asPtr()).toStr().unwrap())
        } finally {
            fromNz.drop()
        }

        val untilNul = CStr.fromBytesUntilNul(listOf(0x6Fu.toUByte(), 0x6Bu.toUByte(), 0u.toUByte(), 0x78u.toUByte()))
        assertTrue(untilNul.isOk())
        assertEquals("ok", untilNul.unwrap().toStr().unwrap())

        val withNul = CStr.fromBytesWithNul(listOf(0x68u.toUByte(), 0x69u.toUByte(), 0u.toUByte()))
        assertTrue(withNul.isOk())
        assertEquals("hi", withNul.unwrap().toStr().unwrap())
    }

    @Test
    fun allocBoxRawVecVecAndString() {
        val boxed = Box.new(123, typeLayoutT = PrimitiveLayouts.I32)
        val boxedRaw = Box.intoRaw(boxed)
        val boxedBack = Box.fromRaw(boxedRaw, typeLayoutT = PrimitiveLayouts.I32)
        assertEquals(123, Box.intoInner(boxedBack))

        val rawVec = RawVec.newIn(Global(), typeLayoutT = PrimitiveLayouts.I32)
        try {
            rawVec.reserve(0, 8, typeLayoutT = PrimitiveLayouts.I32)
            assertTrue(rawVec.capacity(typeLayoutT = PrimitiveLayouts.I32) >= 8)
        } finally {
            rawVec.drop(PrimitiveLayouts.I32)
        }

        val vec = Vec.new(typeLayoutT = PrimitiveLayouts.I32)
        try {
            vec.reserve(4)
            vec.push(10)
            vec.push(20)
            vec.push(30)
            vec.extendFromWithin(0..1)
            assertEquals(listOf(10, 20, 30, 10, 20), vec.asSlice())
            assertEquals(20, vec.pop())
            assertEquals(4, vec.len())
        } finally {
            vec.drop()
        }

        val allocStr = RustAllocString.from("hi")
        try {
            allocStr.pushStr("-rust")
            allocStr.push('!')
            assertEquals("hi-rust!", allocStr.asStr())
            assertEquals(8, allocStr.len())
            assertFalse(allocStr.isEmpty())
            assertEquals(RustChar('!'.code.toUInt()), allocStr.pop())
            allocStr.retain { ch -> ch.toChar() != '-' }
            assertEquals("hirust", allocStr.asStr())
            allocStr.truncate(2)
            assertEquals(listOf(0x68u.toUByte(), 0x69u.toUByte()), allocStr.asBytes())
        } finally {
            allocStr.drop()
        }
    }
}
