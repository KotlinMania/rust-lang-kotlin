// port-lint: source boxed/thin.rs
package io.github.kotlinmania.rustlang.alloc.boxed.thin

import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.alloc.layout.LayoutError
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.intrinsics.constAllocate
import io.github.kotlinmania.rustlang.core.intrinsics.constMakeGlobal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dangling
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.metadata.Pointee
import io.github.kotlinmania.rustlang.core.ptr.metadata.metadata
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isAligned
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result

// Based on
// <https://github.com/matthieu-m/rfc2580/blob/b58d1d3cba0d4b5e859d3617ea2d0943aaa31329/examples/thin.rs>
// by matthieu-m

/**
 * ThinBox.
 *
 * A thin pointer for heap allocation, regardless of T.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.boxed.ThinBox
 *
 * @feature(thinBox)
 * public fun doctestEntry() {
 *     val five = ThinBox.new(5)
 *     val thinSlice = ThinBox.newUnsize(listOf(1, 2, 3, 4))
 *     val sizeOfPtr = sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))
 *     check(sizeOfPtr == sizeOfVal(five))
 *     check(sizeOfPtr == sizeOfVal(thinSlice))
 * }
 * ```
 */
public open class ThinBox<T>(
    // This is essentially `WithHeader<<T as Pointee>.Metadata>`,
    // but that would be invariant in `T`, and we want covariance.
    public var ptr: WithOpaqueHeader,
    public var marker: Unit,
) {
    /**
     * `ThinBox<T>` is `Send` if `T` is `Send` because the data is owned.
     */

    /**
     * `ThinBox<T>` is `Sync` if `T` is `Sync` because the data is owned.
     */

    public open fun fmt(f: Formatter): Result<Unit, Error> = run { f.append((this.deref()).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: Formatter): Result<Unit, Error> = run { f.append((this.deref()).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun deref(): T {
        val value = this.data()
        val metadata = this.meta()
        val pointer = linearSliceView(constPtrFromRustCast(value), metadata.toInt(), PrimitiveLayouts.UNIT)
        // SAFETY: &ThinBox<T> points to a valid pointer for T.
        return pointer
    }

    public open fun derefMut(): T {
        val value = this.data()
        val metadata = this.meta()
        val pointer = linearMutSliceView(mutPtrFromRustCast(value), metadata.toInt(), PrimitiveLayouts.UNIT)
        // SAFETY: ThinBox<T> points to a valid and unique pointer for T.
        return pointer
    }

    public open fun drop() {
        val value = this.derefMut()
        val value1 = mutPtrFromRustCast(value)
        // SAFETY: `value` is valid for reads and writes for our `T`.
        run {
            this.withHeader().drop<T>(value1)
        }
    }

    public open fun meta(): Metadata {
        // SAFETY: NonNull and valid.
        return this.withHeader().header()
    }

    public open fun data(): MutPtr = this.withHeader().value()

    public open fun withHeader(): WithHeader<Metadata> {
        // SAFETY: both types are transparent to `NonNull<UByte>`
        return (UnresolvedTypeLayout<WithHeader>()).readNative(((((addrOf(this.ptr))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
    }

    public open fun source(): Any? = this.deref().source()

    public companion object {
        /**
         * Moves a type to the heap with its [Metadata] stored in the heap allocation instead of on
         * the stack.
         *
         * # Examples
         *
         * ```kotlin
         * import std.boxed.ThinBox
         *
         * @feature(thinBox)
         * public fun doctestEntry() {
         *     val five = ThinBox.new(5)
         * }
         * ```
         */
        public fun <T> new(value: T): ThinBox<T> {
            val meta = metadata(value)
            val ptr = WithOpaqueHeader.new(meta, value, fieldLayout = UnresolvedTypeLayout<NonNull>())
            return ThinBox(ptr = ptr, marker = Unit)
        }

        /**
         * Moves a type to the heap with its [Metadata] stored in the heap allocation instead of on
         * the stack. Returns an error if allocation fails, instead of aborting.
         *
         * # Examples
         *
         * ```kotlin
         * import std.boxed.ThinBox
         *
         * @feature(thinBox)
         * public fun doctestEntry(): Result<Unit> {
         *     val five = ThinBox.tryNew(5).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T> tryNew(value: T): Result<ThinBox<T>, AllocError> {
            val meta = metadata(value)
            return WithOpaqueHeader.tryNew(meta, value, fieldLayout = UnresolvedTypeLayout<NonNull>()).map({ ptr -> ThinBox(ptr = ptr, marker = Unit) })
        }

        public fun <T> from(value: T): ThinBox<T> = ThinBox.new(value)
    }
}

/**
 * Moves a type to the heap with its [Metadata] stored in the heap allocation instead of on
 * the stack.
 *
 * # Examples
 *
 * ```kotlin
 * import std.boxed.ThinBox
 *
 * @feature(thinBox)
 * public fun doctestEntry() {
 *     val thinSlice = ThinBox.newUnsize(listOf(1, 2, 3, 4))
 * }
 * ```
 */
public fun <Dyn, T> ThinBox.Companion.newUnsize(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): ThinBox<Dyn> =
    if (((typeLayoutT).sizeBytes == 0L)) {
        val ptr = WithOpaqueHeader.newUnsizeZst<Dyn, T>(value, fieldLayout = UnresolvedTypeLayout<NonNull>())
        ThinBox(ptr = ptr, marker = Unit)
    } else {
        val meta = metadata(((value as Any?) as Dyn))
        val ptr1 = WithOpaqueHeader.new(meta, value, fieldLayout = UnresolvedTypeLayout<NonNull>())
        ThinBox(ptr = ptr1, marker = Unit)
    }

/**
 * A pointer to type-erased data, guaranteed to either be:
 * 1. `NonNull.dangling()`, in the case where both the pointee (`T`) and
 *    metadata (`H`) are ZSTs.
 * 2. A pointer to a valid `T` that has a header `H` directly before the
 *    pointed-to location.
 */
public open class WithHeader<H> internal constructor(
    field0CtorArg: NonNull,
    public var field1: Unit,
) {
    public var field0: NonNull = field0CtorArg
        internal set

    /**
     * # Safety
     *
     * `value` must point to an undropped owned `T`, and `self` must not be
     * accessed again after this is called.
     */
    public open fun <T> drop(value: MutPtr) {
        open class DropGuard<H>(
            public var ptr: NonNull,
            public var valueLayout: Layout,
            public var marker: Unit,
        ) {
            public open fun drop() {
                // All ZST are allocated statically.
                if (this.valueLayout.size() == 0) {
                    return
                }
                val (layout, valueOffset) = WithHeader.allocLayout(this.valueLayout).unwrapUnchecked()
                // Since we only allocate for non-ZSTs, the layout size cannot be zero.
                check(layout.size() != 0)
                // SAFETY: We own the allocation with `layout` at `ptr - valueOffset`.
                alloc.dealloc(this.ptr.asPtr().sub(valueOffset), layout)
            }
        }
        // `_guard` will deallocate the memory when dropped, even if `dropInPlace` unwinds.
        // We only drop the value because the Pointee trait requires that the metadata is copy
        // aka trivially droppable.
        // SAFETY: We're the only droppers of `value` and it's not dropped again.
        dropInPlace<T>(value)
    }

    public open fun header(): MutPtr {
        // SAFETY:
        // - At least `sizeOf<H>()` bytes are allocated ahead of the pointer.
        // - We know that H will be aligned because the middle pointer is aligned to the greater
        // of the alignment of the header and the data and the header size includes the padding
        // needed to align the header. Subtracting the header size from the aligned data pointer
        // will always result in an aligned header pointer, it just may not point to the
        // beginning of the allocation.
        val hp = mutPtrFromRustCast(this.field0.asPtr().sub(WithHeader.headerSize()))
        check(hp.isAligned())
        return hp
    }

    public open fun value(): MutPtr = this.field0.asPtr()

    public companion object {
        public operator fun <H> invoke(
            field0: NonNull,
            field1: Unit,
        ): WithHeader<H> = WithHeader<H>(
            field0CtorArg = field0,
            field1 = field1,
        )

        public fun <H, T> new(header: H, value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): WithHeader<H> {
            val valueLayout = Layout.new<T>(typeLayoutT = typeLayoutT)
            val (layout, valueOffset) = WithHeader.allocLayout(valueLayout) ?: // We pass an empty layout here because we do not know which layout caused the
// arithmetic overflow in `Layout.extend` and `handleAllocError` takes `Layout` as
// its argument rather than `Result<Layout, LayoutError>`, also this function has been
// stable since 1.28 ._.
//
// On the other hand, look at this gorgeous turbofish!
alloc.handleAllocError(Layout.new<Unit>(typeLayoutT = PrimitiveLayouts.UNIT))
            // Note: It's UB to pass a layout with a zero size to `alloc.alloc`, so
            // we use `layout.dangling()` for this case, which should have a valid
            // alignment for both `T` and `H`.
            val ptr2 =
                if (layout.size() == 0) {
                    // Some paranoia checking, mostly so that the ThinBox tests are
                    // more able to catch issues.
                    check(valueOffset == 0 && ((typeLayoutT).sizeBytes == 0L) && ((UnresolvedTypeLayout<H>()).sizeBytes == 0L))
                    layout.danglingPtr()
                } else {
                    // SAFETY: We check above that the layout size is nonzero.
                    val ptr = alloc.alloc(layout)
                    if (ptr.isNull()) {
                        alloc.handleAllocError(layout)
                    }
                    // SAFETY:
                    // - The size is at least `alignedHeaderSize`.
                    run {
                        val ptr1 = mutPtrFromRustCast(ptr.add(valueOffset))
                        NonNull.newUnchecked(ptr1)
                    }
                }
            val result = WithHeader(ptr2, Unit)
            // SAFETY: `result.header()` promises to give us a valid place for writing
            // the header, and `result.value()` promises the same for the value.
            run {
                write(result.header(), header)
                write(result.value().cast(), value)
            }
            return result
        }

        /**
         * Non-panicking version of `new`.
         * Any error is returned as `Err(core.alloc.AllocError)`.
         */
        public fun <H, T> tryNew(header: H, value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<WithHeader<H>, AllocError> {
            val valueLayout = Layout.new<T>(typeLayoutT = typeLayoutT)
            val (layout, valueOffset) = WithHeader.allocLayout(valueLayout) ?: return Result.Err<WithHeader<H>, AllocError>(alloc.AllocError)
            // Note: It's UB to pass a layout with a zero size to `alloc.alloc`, so
            // we use `layout.dangling()` for this case, which should have a valid
            // alignment for both `T` and `H`.
            val ptr2 =
                if (layout.size() == 0) {
                    // Some paranoia checking, mostly so that the ThinBox tests are
                    // more able to catch issues.
                    check(valueOffset == 0 && ((typeLayoutT).sizeBytes == 0L) && ((UnresolvedTypeLayout<H>()).sizeBytes == 0L))
                    layout.danglingPtr()
                } else {
                    // SAFETY: We check above that the layout size is nonzero.
                    val ptr = alloc.alloc(layout)
                    if (ptr.isNull()) {
                        return Result.Err<WithHeader<H>, AllocError>(alloc.AllocError)
                    }
                    // SAFETY:
                    // - The size is at least `alignedHeaderSize`.
                    run {
                        val ptr1 = mutPtrFromRustCast(ptr.add(valueOffset))
                        NonNull.newUnchecked(ptr1)
                    }
                }
            val result = WithHeader(ptr2, Unit)
            // SAFETY: `result.header()` promises to give us a valid place for writing
            // the header, and `result.value()` promises the same for the value.
            run {
                write(result.header(), header)
                write(result.value().cast(), value)
            }
            return Result.Ok<WithHeader<H>, AllocError>(result)
        }

        // `Dyn` is `? Sized` type like `[UInt]`, and `T` is ZST type like `[UInt; 0]`.
        public fun <H, Dyn, T> newUnsizeZst(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutDyn: TypeLayout<Dyn> = UnresolvedTypeLayout()): WithHeader<H> where Dyn : Pointee<H> {
            fun max(a: Int, b: Int): Int =
                if (a > b) {
                    a
                } else {
                    b
                }
            check(((typeLayoutT).sizeBytes == 0L))
            // Compute a pointer to the right metadata. This will point to the beginning
            // of the header, past the padding, so the assigned type makes sense.
            // It also ensures that the address at the end of the header is sufficiently
            // aligned for T.
            val alloc1: Metadata =
                run {
                    // Note: just call `WithHeader.allocLayout` with size reset to 0.
                    // Currently that's blocked on `Layout.extend` not being `const fun`.
                    val allocAlign = max(alignOf<T>(typeLayoutT = typeLayoutT), alignOf<Metadata>(typeLayoutT = UnresolvedTypeLayout<Metadata>()))
                    val allocSize = max(alignOf<T>(typeLayoutT = typeLayoutT), sizeOf<Metadata>(typeLayoutT = UnresolvedTypeLayout<Metadata>()))
                    // SAFETY: align is power of two because it is the maximum of two alignments.
                    val alloc: MutPtr = constAllocate(allocSize, allocAlign)
                    val metadataOffset = allocSize.checkedSub(sizeOf<Metadata>(typeLayoutT = UnresolvedTypeLayout<Metadata>()))!!
                    val metadataPtr: MutPtr = MutPtr((MutPtr((alloc).rawAddress + ((metadataOffset).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
                    // SAFETY: `* metadataPtr` is within the allocation.
                    run {
                        (UnresolvedTypeLayout<Metadata>()).writeNative((metadataPtr).rawAddress, metadata<Dyn>(constPtrFromRustCast(dangling<T>(typeLayoutT = typeLayoutT))))
                    }
                    // SAFETY: valid heap allocation
                    constMakeGlobal(alloc)
                    // SAFETY: we have just written the metadata.
                    (UnresolvedTypeLayout<Metadata>()).readNative((metadataPtr).rawAddress)
                }
            val valuePtr = MutPtr((ConstPtr((ConstPtr(((constPtrFromRustCast(alloc1))).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<Metadata>()).strideBytes))).rawAddress)).rawAddress)
            check(valuePtr.isAligned())
            forget(value)
            return WithHeader(NonNull.new(valuePtr)!!, Unit)
        }

        public fun <H> headerSize(typeLayoutH: TypeLayout<H> = UnresolvedTypeLayout()): Int = sizeOf<H>(typeLayoutT = typeLayoutH)

        public fun <H> allocLayout(valueLayout: Layout, typeLayoutH: TypeLayout<H> = UnresolvedTypeLayout()): Result<Pair<Layout, Int>, LayoutError> = Layout.new<H>(typeLayoutT = typeLayoutH).extend(valueLayout)
    }
}

/**
 * An opaque representation of `WithHeader<H>` to avoid the
 * projection invariance of `<T as Pointee>.Metadata`.
 */
public open class WithOpaqueHeader internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout(),
) {
    public var field0: NonNull
        get() = value
        set(v) {
            value = v
        }

    public var value: NonNull
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: NonNull, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<WithOpaqueHeader> = TransparentTypeLayout(fieldLayout, { ptr, layout -> WithOpaqueHeader(ptr, layout) }, { it.value })

    public companion object {
        public operator fun invoke(value: NonNull, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): WithOpaqueHeader = WithOpaqueHeader(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): WithOpaqueHeader = WithOpaqueHeader(fieldStorage, fieldLayout)

        public fun <H, T> new(header: H, value: T, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): WithOpaqueHeader {
            val ptr = WithHeader.new(header, value)
            return WithOpaqueHeader(ptr.value, fieldLayout = fieldLayout)
        }

        public fun <Dyn, T> newUnsizeZst(value: T, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): WithOpaqueHeader {
            val ptr = WithHeader.newUnsizeZst<Dyn, T>(value)
            return WithOpaqueHeader(ptr.value, fieldLayout = fieldLayout)
        }

        public fun <H, T> tryNew(header: H, value: T, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): Result<WithOpaqueHeader, AllocError> = WithHeader.tryNew(header, value).map({ ptr -> WithOpaqueHeader(ptr.value, fieldLayout = fieldLayout) })
    }
}
