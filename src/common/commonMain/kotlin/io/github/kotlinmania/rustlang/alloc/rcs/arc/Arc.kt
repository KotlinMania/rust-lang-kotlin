// port-lint: source rcs/arc.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.alloc.rcs.arc

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.borrow.ToOwned
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.rcs.rc.MarkerEq
import io.github.kotlinmania.rustlang.alloc.rcs.rc.isDangling
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.alloc.AllocatorNightly
import io.github.kotlinmania.rustlang.core.alloc.StaticAllocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.clone.Share
import io.github.kotlinmania.rustlang.core.clone.UseCloned
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
import io.github.kotlinmania.rustlang.core.iter
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroUsize
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.pin.PinSafePointer
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.addrEq
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.byteAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castSlice
import io.github.kotlinmania.rustlang.core.ptr.mutptr.withMetadataOf
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenance
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize

/**
 * A soft limit on the amount of references that may be made to an `Arc`.
 *
 * Going above this limit will abort your program (although not
 * necessarily) at _exactly_ `MAX_REFCOUNT + 1` references.
 * Trying to go above it might call a `panic` (if not actually going above it).
 *
 * This is a global invariant, and also applies when using a compare-exchange loop.
 *
 * See comment in `Arc.clone`.
 */
public val MAX_REFCOUNT: Int = (Int.MAX_VALUE)

public fun panicArcOverflow(): Nothing {
    error("Arc counter overflow")
}

// Note: When stabilizing this parameter, the `AllocatorNightly`
// bound on the `From<Holder>` impl *must* be weaked to `Allocator`,
// as `Holder` is fundamental!

/**
 * A thread-safe reference-counting pointer. `Arc` stands for Atomically
 * Reference Counted'.
 *
 * The type `Arc<T>` provides shared ownership of a value of type `T`,
 * allocated in the heap. Invoking [`clone`][clone] on `Arc` produces
 * a new `Arc` instance, which points to the same allocation on the heap as the
 * source `Arc`, while increasing a reference count. When the last `Arc`
 * pointer to a given allocation is destroyed, the value stored in that allocation (often
 * referred to as "inner value") is also dropped.
 *
 * Shared references in Rust disallow mutation by default, and `Arc` is no
 * exception: you cannot generally obtain a mutable reference to something
 * inside an `Arc`. If you do need to mutate through an `Arc`, you have several options:
 *
 * 1. Use interior mutability with synchronization primitives like [`Mutex`][mutex],
 *    [`RwLock`][rwlock], or one of the [`Atomic`][atomic] types.
 *
 * 2. Use clone-on-write semantics with [Arc.makeMut] which provides efficient mutation
 *    without requiring interior mutability. This approach clones the data only when
 *    needed (when there are multiple references) and can be more efficient when mutations
 *    are infrequent.
 *
 * 3. Use [Arc.getMut] when you know your `Arc` is not shared (has a reference count of 1),
 *    which provides direct mutable access to the inner value without any cloning.
 *
 * ```kotlin
 * var data = mutableListOf(1, 2, 3)
 * // This will clone the vector only if there are other references to it
 * Arc.makeMut(data).push(4)
 * check(data == mutableListOf(1, 2, 3, 4))
 * ```
 *
 * **Note**: This type is only available on platforms that support atomic
 * loads and stores of pointers, which includes all platforms that support
 * the `std` crate but not all those which only support [`alloc`](crate).
 * This may be detected at compile time using `@cfg(targetHasAtomic = "ptr")`.
 *
 * ## Thread Safety
 *
 * Unlike [Rc.T], `Arc<T>` uses atomic operations for its reference
 * counting. This means that it is thread-safe. The disadvantage is that
 * atomic operations are more expensive than ordinary memory accesses. If you
 * are not sharing reference-counted allocations between threads, consider using
 * [Rc.T] for lower overhead. [Rc.T] is a safe default, because the
 * compiler will catch any attempt to send an [Rc.T] between threads.
 * However, a library might choose `Arc<T>` in order to give library consumers
 * more flexibility.
 *
 * `Arc<T>` will implement [Send] and [Sync] as long as the `T` implements
 * [Send] and [Sync]. Why can't you put a non-thread-safe type `T` in an
 * `Arc<T>` to make it thread-safe? This may be a bit counter-intuitive at
 * first: after all, isn't the point of `Arc<T>` thread safety? The key is
 * this: `Arc<T>` makes it thread safe to have multiple ownership of the same
 * data, but it  doesn't add thread safety to its data. Consider
 * <code>Arc<[RefCell\<T>]></code>. [RefCell.T] isn't [Sync], and if `Arc<T>` was always
 * [Send], <code>Arc<[RefCell\<T>]></code> would be as well. But then we'd have a problem:
 * [RefCell.T] is not thread safe; it keeps track of the borrowing count using
 * non-atomic operations.
 *
 * In the end, this means that you may need to pair `Arc<T>` with some sort of
 * [std.sync] type, usually [`Mutex.T`][mutex].
 *
 * ## Breaking cycles with `Weak`
 *
 * The [`downgrade`][downgrade] method can be used to create a non-owning
 * [Weak] pointer. A [Weak] pointer can be [`upgrade`][upgrade]d
 * to an `Arc`, but this will return [None] if the value stored in the allocation has
 * already been dropped. In other words, `Weak` pointers do not keep the value
 * inside the allocation alive; however, they *do* keep the allocation
 * (the backing store for the value) alive.
 *
 * A cycle between `Arc` pointers will never be deallocated. For this reason,
 * [Weak] is used to break cycles. For example, a tree could have
 * strong `Arc` pointers from parent nodes to children, and [Weak]
 * pointers from children back to their parents.
 *
 * # Cloning references
 *
 * Creating a new reference from an existing reference-counted pointer is done using the
 * `Clone` trait implemented for [`Arc.T`][Arc] and [`Weak.T`][Weak].
 *
 * ```kotlin
 * val foo = mutableListOf(1.0, 2.0, 3.0)
 * // The two syntaxes below are equivalent.
 * val a = foo.toMutableList()
 * val b = foo
 * // a, b, and foo are all Arcs that point to the same memory location
 * ```
 *
 * ## `Deref` behavior
 *
 * `Arc<T>` automatically dereferences to `T` (via the [Deref] trait),
 * so you can call `T`s methods on a value of type `Arc<T>`. To avoid name
 * clashes with `T`s methods, the methods of `Arc<T>` itself are associated
 * functions, called using [fully qualified syntax]:
 *
 * ```kotlin
 * val myArc = Arc.new(Unit)
 * val myWeak = Arc.downgrade(myArc)
 * ```
 *
 * `Arc<T>`s implementations of traits like `Clone` may also be called using
 * fully qualified syntax. Some people prefer to use fully qualified syntax,
 * while others prefer using method-call syntax.
 *
 * ```kotlin
 * val arc = Arc.new(Unit)
 * // Method-call syntax
 * val arc2 = arc.clone()
 * // Fully qualified syntax
 * val arc3 = arc
 * ```
 *
 * [`Weak.T`][Weak] does not auto-dereference to `T`, because the inner value may have
 * already been dropped.
 *
 * [clone]: Clone.clone
 * [mutex]: ../../std/sync/struct.Mutex.html
 * [rwlock]: ../../std/sync/struct.RwLock.html
 * [downgrade]: Arc.downgrade
 * [upgrade]: Weak.upgrade
 * [std.sync]: ../../std/sync/index.html
 * [Arc.clone]: Arc.clone
 * [fully qualified syntax]: https://doc.rust-lang.org/book/ch19-03-advanced-traits.html#fully-qualified-syntax-for-disambiguation-calling-methods-with-the-same-name
 *
 * # Examples
 *
 * Sharing some immutable data between threads:
 *
 * ```ignore-wasm
 * use std.sync.Arc;
 * use std.thread;
 *
 * let five = Arc.new(5);
 *
 * for _ in 0..10 {
 *     let five = Arc.clone(&five);
 *
 *     thread.spawn(move || {
 *         println("{five:?}");
 *     });
 * }
 * ```
 *
 * Sharing a mutable [AtomicUsize]:
 *
 * ```ignore-wasm
 * use std.sync.Arc;
 * use std.sync.atomic::{AtomicUsize, Ordering};
 * use std.thread;
 *
 * let val = Arc.new(AtomicUsize.new(5));
 *
 * for _ in 0..10 {
 *     let val = Arc.clone(&val);
 *
 *     thread.spawn(move || {
 *         let v = val.fetchAdd(1, Ordering.Relaxed);
 *         println("{v:?}");
 *     });
 * }
 * ```
 *
 * See the [`rc` documentation][rcExamples] for more examples of reference
 * counting in general.
 */
public open class Arc<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    public var phantom: Unit,
    public var alloc: A,
) : UseCloned, Share, PinSafePointer, DerefPure, ArcEqIdent<T, A>, AsRef<T> {
    public var ptr: NonNull = ptrCtorArg
        internal set

    public open fun inner(): ArcInner<T> {
        // SAFETY: While this arc is alive we're guaranteed
        // that the inner pointer is valid. Furthermore, we know that the
        // `ArcInner` structure itself is `Sync` if the inner data is
        // `Sync` as well, so we're ok loaning out an immutable pointer to these
        // contents.
        return this.ptr.asRef()
    }

    // Non-inlined part of `drop`.
    public open fun dropSlow() {
        // Drop the weak ref collectively held by all strong references when this
        // variable goes out of scope. This ensures that the memory is deallocated
        // even if the destructor of `T` panics.
        // Take a reference to `self.alloc` instead of cloning because 1. it'll last long
        // enough, and 2. you should be able to drop `Arc`s with unclonable allocators
        val weak = Weak(ptr = this.ptr, alloc = this.alloc)
        // Destroy the data at this time, even though we must not free the box
        // allocation itself (there might still be weak pointers lying around).
        // We cannot use `getMutUnchecked` here, because `self.alloc` is borrowed.
        // ignore-tidy-undocumented-unchecked
        dropInPlace(this.ptr.asPtr().data)
    }

    /**
     * Makes a clone of the `Arc` pointer.
     *
     * This creates another pointer to the same allocation, increasing the
     * strong reference count.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * five
     * ```
     */
    public open fun clone(): Arc<T, A> {
        // Using a relaxed ordering is alright here, as knowledge of the
        // original reference prevents other threads from erroneously deleting
        // the object.
        //
        // As explained in the [Boost documentation][1], Increasing the
        // reference counter can always be done with memoryOrderRelaxed: New
        // references to an object can only be formed from an existing
        // reference, and passing an existing reference from one thread to
        // another must already provide any required synchronization.
        //
        // [1]: (www.boost.org/doc/libs/1_55_0/doc/html/atomic/usageExamples.html)
        val oldSize = this.inner().strong.fetchAdd(1, Relaxed)
        // However we need to guard against massive refcounts in case someone is `mem.forget`ing
        // Arcs. If we don't do this the count can overflow and users will use-after free. This
        // branch will never be taken in any realistic program. We abort because such a program is
        // incredibly degenerate, and we don't care to support it.
        //
        // This check is not 100% water-proof: we error when the refcount grows beyond `Long.MAX`.
        // But we do that check *after* having done the increment, so there is a chance here that
        // the worst already happened and we actually do overflow the `Int` counter. However, that
        // requires the counter to grow from `Long.MAX` to `Int.MAX` between the increment
        // above and the `abort` below, which seems exceedingly unlikely.
        //
        // This is a global invariant, and also applies when using a compare-exchange loop to increment
        // counters in other methods.
        // Otherwise, the counter could be brought to an almost-overflow using a compare-exchange loop,
        // and then overflow using a few `fetchAdd`s.
        if (oldSize > MAX_REFCOUNT) {
            kmAbort()
        }
        // SAFETY: Pointer is valid & allocator corresponds to the one used to allocate it.
        return Arc.fromInnerIn(this.ptr, this.alloc.clone())
    }

    public open fun deref(): T = this.inner().data

    // The API of this pointer type enforces that if the `T` is pinned, then *all*
    // clones of this `Arc<T>` are wrapped as `Pin<Arc<T>>`. Since an `Arc<T>`
    // could be used to obtain an `Arc<T>` that is not wrapped in `Pin` (and later
    // used with `Arc.getMut`), this means that this type treats `Arc<T>` as
    // evidence that the `T` is not pinned. The implementations of various traits
    // are written accordingly. Since this type is not fundamental, downstream
    // crates cannot provide malicious implementations of any of the traits relevant
    // for `Pin`.

    /**
     * Drops the `Arc`.
     *
     * This will decrement the strong reference count. If the strong reference
     * count reaches zero then the only other references (if any) are
     * [Weak], so we `drop` the inner value.
     *
     * # Examples
     *
     * ```kotlin
     * open class Foo {
     *     public open fun drop() {
     *         println("dropped!")
     *     }
     * }
     * val foo = Foo()
     * val foo2 = foo
     * (foo).let { }
     * // Doesn't print anything
     * (foo2).let { }
     * // Prints "dropped!"
     * ```
     */
    public open fun drop() {
        // Because `fetchSub` is already atomic, we do not need to synchronize
        // with other threads unless we are going to delete the object. This
        // same logic applies to the below `fetchSub` to the `weak` count.
        if (this.inner().strong.fetchSub(1, Release) != 1) {
            return
        }
        this.inner().strong.load(Acquire)
        // Make sure we aren't trying to "drop" the shared static for empty slices
        // used by Default.default.
        check(!addrEq((this.ptr.asPtr()).asConstPtr(), STATIC_INNER_SLICE.inner)) { "Arcs backed by a static should never reach a strong count of 0. Likely decrement_strong_count or from_raw were called too many times." }
        // ignore-tidy-undocumented-unchecked
        run {
            this.dropSlow()
        }
    }

    public override fun eq(other: Arc<T, A>): Boolean = this == other

    public override fun ne(other: Arc<T, A>): Boolean = this != other

    /**
     * Equality for two `Arc`s.
     *
     * Two `Arc`s are equal if their inner values are equal, even if they are
     * stored in different allocation.
     *
     * If `T` also implements `Eq` (implying reflexivity of equality),
     * two `Arc`s that point to the same allocation are always equal.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five == 5)
     * ```
     */
    public open fun eqPartialEq(other: Arc<T, A>): Boolean = this.eq(other)

    /**
     * Inequality for two `Arc`s.
     *
     * Two `Arc`s are not equal if their inner values are not equal.
     *
     * If `T` also implements `Eq` (implying reflexivity of equality),
     * two `Arc`s that point to the same value are always equal.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five != 6)
     * ```
     */
    public open fun nePartialEq(other: Arc<T, A>): Boolean = this.ne(other)

    /**
     * Partial comparison for two `Arc`s.
     *
     * The two are compared by calling `partialCmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * val five = 5
     * check(Ordering.Less == five.partialCmp(6))
     * ```
     */
    public open fun partialCmp(other: Arc<T, A>): Ordering? = this.partialCmp(other)

    /**
     * Less-than comparison for two `Arc`s.
     *
     * The two are compared by calling `<` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five < 6)
     * ```
     */
    public open fun lt(other: Arc<T, A>): Boolean = this < other

    /**
     * Less than or equal to' comparison for two `Arc`s.
     *
     * The two are compared by calling `<=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five <= 5)
     * ```
     */
    public open fun le(other: Arc<T, A>): Boolean = this <= other

    /**
     * Greater-than comparison for two `Arc`s.
     *
     * The two are compared by calling `>` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five > 4)
     * ```
     */
    public open fun gt(other: Arc<T, A>): Boolean = this > other

    /**
     * Greater than or equal to' comparison for two `Arc`s.
     *
     * The two are compared by calling `>=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five >= 5)
     * ```
     */
    public open fun ge(other: Arc<T, A>): Boolean = this >= other

    /**
     * Comparison for two `Arc`s.
     *
     * The two are compared by calling `cmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * val five = 5
     * check(Ordering.Less == five.cmp(6))
     * ```
     */
    public open fun cmp(other: Arc<T, A>): Ordering = this.cmp(other)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Error> = run { f.append(((this)).toString()); Result.Ok<Unit, Error>(Unit) }

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun borrow(): T = this

    public open fun asRef(): T = this

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            phantom: Unit,
            alloc: A,
        ): Arc<T, A> = Arc<T, A>(
            ptrCtorArg = ptr,
            phantom = phantom,
            alloc = alloc,
        )

        public fun <T, A : Allocator> intoInnerWithAllocator(thisRef: Arc<T, A>): Pair<NonNull, A> {
            val thisRef1 = thisRef
            // SAFETY: Pointer is valid for reads.
            return Pair(thisRef1.ptr, read(thisRef1.alloc))
        }

        public fun <T, A : Allocator> fromInnerIn(ptr: NonNull, alloc: A): Arc<T, A> = Arc(ptr = ptr, phantom = Unit, alloc = alloc)

        public fun <T, A : Allocator> fromPtrIn(ptr: MutPtr, alloc: A): Arc<T, A> {
            // SAFETY: Upheld by caller.
            return Arc.fromInnerIn(NonNull.newUnchecked(ptr), alloc)
        }

        /**
         * Constructs a new `Arc<T>` in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Arc.newIn(5, System)
         * }
         * ```
         */
        public fun <T, A : Allocator> newIn(data: T, alloc: A): Arc<T, A> {
            // Start the weak pointer count as 1 which is the weak pointer that's
            // held by all the strong pointers (kinda), see std/rc.rs for more info
            val x = Box.newIn(ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = data), alloc)
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(x)
            // SAFETY: Pointer is valid.
            return Arc.fromInnerIn(ptr, alloc1)
        }

        /**
         * Constructs a new `Arc` with uninitialized contents in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(getMutUnchecked)
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     var five = Arc.newUninitIn(System)
         *     val five1 = run {
         *         // Deferred initialization:
         *         Arc.getMutUnchecked(five).asMutPtr().write(5)
         *         five.assumeInit()
         *     }
         *     check(five1 == 5)
         * }
         * ```
         */
        public fun <T, A : Allocator> newUninitIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<MaybeUninit<T>, A> {
            // ignore-tidy-undocumented-unchecked
            return Arc.fromPtrIn(Arc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocate(layout) }, MutPtr.cast), alloc)
        }

        /**
         * Constructs a new `Arc` with uninitialized contents, with the memory
         * being filled with `0` bytes, in the provided allocator.
         *
         * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
         * of this method.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val zero = Arc.newZeroedIn(System)
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> newZeroedIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<MaybeUninit<T>, A> {
            // ignore-tidy-undocumented-unchecked
            return Arc.fromPtrIn(Arc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocateZeroed(layout) }, MutPtr.cast), alloc)
        }

        /**
         * Constructs a new `Arc<T, A>` in the given allocator while giving you a `Weak<T, A>` to the allocation,
         * to allow you to construct a `T` which holds a weak pointer to itself.
         *
         * Generally, a structure circularly referencing itself, either directly or
         * indirectly, should not hold a strong reference to itself to prevent a memory leak.
         * Using this function, you get access to the weak pointer during the
         * initialization of `T`, before the `Arc<T, A>` is created, such that you can
         * clone and store it inside the `T`.
         *
         * `newCyclicIn` first allocates the managed allocation for the `Arc<T, A>`,
         * then calls your closure, giving it a `Weak<T, A>` to this allocation,
         * and only afterwards completes the construction of the `Arc<T, A>` by placing
         * the `T` returned from your closure into the allocation.
         *
         * Since the new `Arc<T, A>` is not fully-constructed until `Arc<T, A>.newCyclicIn`
         * returns, calling [upgrade] on the weak reference inside your closure will
         * fail and result in a `None` value.
         *
         * # Panics
         *
         * If `dataFn` panics, the panic is propagated to the caller, and the
         * temporary [Weak.T] is dropped normally.
         *
         * # Example
         *
         * See [newCyclic]
         *
         * [newCyclic]: Arc.newCyclic
         * [upgrade]: Weak.upgrade
         */
        public fun <T, A : Allocator> newCyclicIn(dataFn: (Weak<T, A>) -> T, alloc: A): Arc<T, A> {
            // Construct the inner in the "uninitialized" state with a single
            // weak reference.
            val (uninitPtr, alloc1) = Box.intoNonNullWithAllocator(Box.newIn(ArcInner(strong = AtomicUsize.new(0), weak = AtomicUsize.new(1), data = MaybeUninit.uninit(UnresolvedTypeLayout<T>())), alloc))
            val initPtr: NonNull = uninitPtr.cast()
            val weak = Weak(ptr = initPtr, alloc = alloc1)
            // It's important we don't give up ownership of the weak pointer, or
            // else the memory might be freed by the time `dataFn` returns. If
            // we really wanted to pass ownership, we could create an additional
            // weak pointer for ourselves, but this would result in additional
            // updates to the weak reference count which might not be necessary
            // otherwise.
            val data = dataFn(weak)
            // Now we can properly initialize the inner value and turn our weak
            // reference into a strong reference.
            val inner = initPtr.asPtr()
            // ignore-tidy-undocumented-unchecked
            return run {
                write(addrOfMut(inner.data), data, typeLayoutT = UnresolvedTypeLayout<T>())
                // The above write to the data field must be visible to any threads which
                // observe a non-zero strong count. Therefore we need at least "Release" ordering
                // in order to synchronize with the `compareExchangeWeak` in `Weak.upgrade`.
                //
                // "Acquire" ordering is not required. When considering the possible behaviors
                // of `dataFn` we only need to look at what it could do with a reference to a
                // non-upgradeable `Weak`:
                // - It can *clone* the `Weak`, increasing the weak reference count.
                // - It can drop those clones, decreasing the weak reference count (but never to zero).
                //
                // These side effects do not impact us in any way, and no other side effects are
                // possible with safe code alone.
                val prevValue = inner.strong.fetchAdd(1, Release)
                check(prevValue == 0)
                // Strong references should collectively own a shared weak reference,
                // so don't run the destructor for our old weak reference.
                // Calling intoRawWithAllocator has the double effect of giving us back the allocator,
                // and forgetting the weak reference.
                val alloc2 = weak.intoRawWithAllocator().second
                Arc.fromInnerIn(initPtr, alloc2)
            }
        }

        /**
         * Constructs a new `Pin<Arc<T, A>>` in the provided allocator. If `T` does not implement `Unpin`,
         * then `data` will be pinned in memory and unable to be moved.
         */
        public fun <T, A : Allocator> pinIn(data: T, alloc: A): Pin<Arc<T, A>> where A : StaticAllocator {
            // SAFETY: We own and create the pinned pointer.
            return Pin.newUnchecked(Arc.newIn(data, alloc))
        }

        /**
         * Constructs a new `Pin<Arc<T, A>>` in the provided allocator, return an error if allocation
         * fails.
         */
        public fun <T, A : Allocator> tryPinIn(data: T, alloc: A): Result<Pin<Arc<T, A>>, AllocError> where A : StaticAllocator {
            // SAFETY: We own and create the pinned pointer.
            return Result.Ok<Pin<Arc<T, A>>, AllocError>(Pin.newUnchecked(when (val tryResult = Arc.tryNewIn(data, alloc)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pin<Arc<T, A>>, AllocError>(tryResult.errValue()) }))
        }

        /**
         * Constructs a new `Arc<T, A>` in the provided allocator, returning an error if allocation fails.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val five = Arc.tryNewIn(5, System).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryNewIn(data: T, alloc: A): Result<Arc<T, A>, AllocError> {
            // Start the weak pointer count as 1 which is the weak pointer that's
            // held by all the strong pointers (kinda), see std/rc.rs for more info
            val x = when (val tryResult = Box.tryNewIn(ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = data), alloc)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<T, A>, AllocError>(tryResult.errValue()) }
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(x)
            // SAFETY: Pointer is valid since we created it.
            return Result.Ok<Arc<T, A>, AllocError>(Arc.fromInnerIn(ptr, alloc1))
        }

        /**
         * Constructs a new `Arc` with uninitialized contents, in the provided allocator, returning an
         * error if allocation fails.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * @feature(getMutUnchecked)
         * public fun doctestEntry(): Result<Unit> {
         *     var five = Arc.tryNewUninitIn(System).getOrElse { return Result.failure(it) }
         *     val five1 = run {
         *         // Deferred initialization:
         *         Arc.getMutUnchecked(five).asMutPtr().write(5)
         *         five.assumeInit()
         *     }
         *     check(five1 == 5)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryNewUninitIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Arc<MaybeUninit<T>, A>, AllocError> {
            // ignore-tidy-undocumented-unchecked
            return Result.Ok<Arc<MaybeUninit<T>, A>, AllocError>(Arc.fromPtrIn(when (val tryResult = Arc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocate(layout) }, MutPtr.cast)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<MaybeUninit<T>, A>, AllocError>(tryResult.errValue()) }, alloc))
        }

        /**
         * Constructs a new `Arc` with uninitialized contents, with the memory
         * being filled with `0` bytes, in the provided allocator, returning an error if allocation
         * fails.
         *
         * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
         * of this method.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val zero = Arc.tryNewZeroedIn(System).getOrElse { return Result.failure(it) }
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> tryNewZeroedIn(alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Arc<MaybeUninit<T>, A>, AllocError> {
            // ignore-tidy-undocumented-unchecked
            return Result.Ok<Arc<MaybeUninit<T>, A>, AllocError>(Arc.fromPtrIn(when (val tryResult = Arc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocateZeroed(layout) }, MutPtr.cast)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<MaybeUninit<T>, A>, AllocError>(tryResult.errValue()) }, alloc))
        }

        /**
         * Returns the inner value, if the `Arc` has exactly one strong reference.
         *
         * Otherwise, an [Err] is returned with the same `Arc` that was
         * passed in.
         *
         * This will succeed even if there are outstanding weak references.
         *
         * It is strongly recommended to use [Arc.intoInner] instead if you don't
         * keep the `Arc` in the [Err] case.
         * Immediately dropping the [Err]-value, as the expression
         * `Arc.tryUnwrap(this).ok()` does, can cause the strong count to
         * drop to zero and the inner value of the `Arc` to be dropped.
         * For instance, if two threads execute such an expression in parallel,
         * there is a race condition without the possibility of unsafety:
         * The threads could first both check whether they own the last instance
         * in `Arc.tryUnwrap`, determine that they both do not, and then both
         * discard and drop their instance in the call to [`ok`][Result.ok].
         * In this scenario, the value inside the `Arc` is safely destroyed
         * by exactly one of the threads, but neither thread will ever be able
         * to use the value.
         *
         * # Examples
         *
         * ```kotlin
         * val x = 3
         * check(Arc.tryUnwrap(x) == Result.success(3))
         * val x1 = 4
         * val y = x1
         * check(Arc.tryUnwrap(x1).unwrapErr() == 4)
         * ```
         */
        public fun <T, A : Allocator> tryUnwrap(thisRef: Arc<T, A>): Result<T, Arc<T, A>> {
            if (thisRef.inner().strong.compareExchange(1, 0, Relaxed, Relaxed).isErr()) {
                return Result.Err<T, Arc<T, A>>(thisRef)
            }
            thisRef.inner().strong.load(Acquire)
            val thisRef1 = thisRef
            // SAFETY: Pointer is valid for reads, contains initialised memory,
            // and not dropped multiple times (we return it).
            val elem: T = read(thisRef1.ptr.asRef().data)
            // SAFETY: As above, but we explicitly drop the allocator only once
            // upon creating and dropping a weak pointer.
            // copy the allocator
            // Make a weak pointer to clean up the implicit strong-weak reference
            return Result.Ok<T, Arc<T, A>>(elem)
        }

        /**
         * Returns the inner value, if the `Arc` has exactly one strong reference.
         *
         * Otherwise, [None] is returned and the `Arc` is dropped.
         *
         * This will succeed even if there are outstanding weak references.
         *
         * If `Arc.intoInner` is called on every clone of this `Arc`,
         * it is guaranteed that exactly one of the calls returns the inner value.
         * This means in particular that the inner value is not dropped.
         *
         * [Arc.tryUnwrap] is conceptually similar to `Arc.intoInner`, but it
         * is meant for different use-cases. If used as a direct replacement
         * for `Arc.intoInner` anyway, such as with the expression
         * <code>[Arc.tryUnwrap]\(this).[ok][Result.ok]()</code>, then it does
         * **not** give the same guarantee as described in the previous paragraph.
         * For more information, see the examples below and read the documentation
         * of [Arc.tryUnwrap].
         *
         * # Examples
         *
         * Minimal example demonstrating the guarantee that `Arc.intoInner` gives.
         * ```ignore-wasm
         * use std.sync.Arc;
         *
         * let x = Arc.new(3);
         * let y = Arc.clone(&x);
         *
         * // Two threads calling `Arc.intoInner` on both clones of an `Arc`:
         * let xThread = std.thread.spawn(|| Arc.intoInner(x));
         * let yThread = std.thread.spawn(|| Arc.intoInner(y));
         *
         * let xInnerValue = xThread.join().unwrap();
         * let yInnerValue = yThread.join().unwrap();
         *
         * // One of the threads is guaranteed to receive the inner value:
         * assert(matches(
         *     (xInnerValue, yInnerValue),
         *     (None, Some(3)) | (Some(3), None)
         * ));
         * // The result could also be `(None, None)` if the threads called
         * // `Arc.tryUnwrap(x).ok()` and `Arc.tryUnwrap(y).ok()` instead.
         * ```
         *
         * A more practical example demonstrating the need for `Arc.intoInner`:
         * ```ignore-wasm
         * use std.sync.Arc;
         *
         * // Definition of a simple singly linked list using `Arc`:
         * @derive(Clone)
         * struct LinkedList<T>(Option<Arc<Node<T>>>);
         * struct Node<T>(T, Option<Arc<Node<T>>>);
         *
         * // Dropping a long `LinkedList<T>` relying on the destructor of `Arc`
         * // can cause a stack overflow. To prevent this, we can provide a
         * // manual `Drop` implementation that does the destruction in a loop:
         * impl<T> Drop for LinkedList<T> {
         *     fn drop(self) {
         *         let mut link = self.0.take();
         *         while let Some(arcNode) = link.take() {
         *             if let Some(Node(_value, next)) = Arc.intoInner(arcNode) {
         *                 link = next;
         *             }
         *         }
         *     }
         * }
         *
         * // Implementation of `new` and `push` omitted
         * impl<T> LinkedList<T> {
         *     / * ... * /
         * #   fn new() -> Self {
         * #       LinkedList(None)
         * #   }
         * #   fn push(self, x: T) {
         * #       self.0 = Some(Arc.new(Node(x, self.0.take())));
         * #   }
         * }
         *
         * // The following code could have still caused a stack overflow
         * // despite the manual `Drop` impl if that `Drop` impl had used
         * // `Arc.tryUnwrap(arc).ok()` instead of `Arc.intoInner(arc)`.
         *
         * // Create a long list and clone it
         * let mut x = LinkedList.new();
         * let size = 100000;
         * # let size = if cfg(miri) { 100 } else { size };
         * for i in 0..size {
         *     x.push(i); // Adds i to the front of x
         * }
         * let y = x.clone();
         *
         * // Drop the clones in parallel
         * let xThread = std.thread.spawn(|| drop(x));
         * let yThread = std.thread.spawn(|| drop(y));
         * xThread.join().unwrap();
         * yThread.join().unwrap();
         * ```
         */
        public fun <T, A : Allocator> intoInner(thisRef: Arc<T, A>): T? {
            // Make sure that the ordinary `Drop` implementation isn’t called as well
            val thisRef1 = thisRef
            // Following the implementation of `drop` and `dropSlow`
            if (thisRef1.inner().strong.fetchSub(1, Release) != 1) {
                return null
            }
            thisRef1.inner().strong.load(Acquire)
            // SAFETY: This mirrors the line
            //
            // unchecked ( ptr.dropInPlace(Self.getMutUnchecked(self)) );
            //
            // in `dropSlow`. Instead of dropping the value behind the pointer,
            // it is read and eventually returned; `ptr.read` has the same
            // safety conditions as `ptr.dropInPlace`.
            val inner = read(Arc.getMutUnchecked(thisRef1))
            // SAFETY: Pointer is valid for reads.
            val alloc = read(thisRef1.alloc)
            (Weak(ptr = thisRef1.ptr, alloc = alloc)).let { }
            return inner
        }

        /**
         * Maps the value in an `Arc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `Arc`, and the result is returned, also in
         * an `Arc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Arc.map(a, f)` instead of `r.map(a)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * val r = 7
         * val new = Arc.map(r, { i -> i + 7 })
         * check(new == 14)
         * ```
         */
        public fun <T, A : Allocator, U> map(thisRef: Arc<T, A>, f: (T) -> U, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): Arc<U, A> =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU) && Arc.isUnique(thisRef)) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = Arc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = Arc.fromRawIn(ptr.cast(), alloc)
                    Arc.getMutUnchecked(allocation).write(f(value))
                    allocation.assumeInit()
                }
            } else {
                val output = f(thisRef)
                val (ptr1, alloc1) = Arc.intoRawWithAllocator(thisRef)
                // ignore-tidy-undocumented-unchecked
                Arc.decrementStrongCountIn(ptr1, alloc1)
                Arc.newIn(output, alloc1)
            }

        /**
         * Attempts to map the value in an `Arc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `Arc`, and if the operation succeeds, the
         * result is returned, also in an `Arc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Arc.tryMap(a, f)` instead of `a.tryMap(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(smartPointerTryMap)
         * public fun doctestEntry() {
         *     val b = 7
         *     val new = Arc.tryMap(b, { i -> UInt.tryFrom(i) }).unwrap()
         *     check(new == 7)
         * }
         * ```
         */
        public fun <Output, Residual, T, A : Allocator, R> tryMap(thisRef: Arc<T, A>, f: (T) -> R, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): TryType where R : Try<Output, Residual> {
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>()) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>()) && Arc.isUnique(thisRef)) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = Arc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = Arc.fromRawIn(ptr.cast(), alloc)
                    Arc.getMutUnchecked(allocation).write(when (val tryResult = f(value)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
                    allocation.assumeInit()
                }
            } else {
                val output = when (val tryResult = f(thisRef)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                val (ptr1, alloc1) = Arc.intoRawWithAllocator(thisRef)
                // ignore-tidy-undocumented-unchecked
                Arc.decrementStrongCountIn(ptr1, alloc1)
                Arc.newIn(output, alloc1)
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Constructs a new `Arc<T>` with a clone of `value` in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val hello: String = Arc.cloneFromRefIn("hello", System)
         * }
         * ```
         */
        public fun <T, A : Allocator> cloneFromRefIn(value: T, alloc: A): Arc<T, A> {
            // `inProgress` drops the allocation if we panic before finishing initializing it.
            val inProgress: UniqueArcUninit<T, A>  = UniqueArcUninit.new(value, alloc)
            // Initialize with clone of value.
            // ignore-tidy-undocumented-unchecked
            return run {
                // Clone. If the clone panics, `inProgress` will be dropped and clean up.
                value.cloneToUninit(inProgress.dataPtr().cast())
                // Cast type of pointer, now that it is initialized.
                inProgress.intoArc()
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Constructs a new `Arc<T>` with a clone of `value` in the provided allocator, returning an error if allocation fails
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val hello: String = Arc.tryCloneFromRefIn("hello", System).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryCloneFromRefIn(value: T, alloc: A): Result<Arc<T, A>, AllocError> {
            // `inProgress` drops the allocation if we panic before finishing initializing it.
            val inProgress: UniqueArcUninit<T, A>  = when (val tryResult = UniqueArcUninit.tryNew(value, alloc)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<T, A>, AllocError>(tryResult.errValue()) }
            // Initialize with clone of value.
            // ignore-tidy-undocumented-unchecked
            val initializedClone =
                run {
                    // Clone. If the clone panics, `inProgress` will be dropped and clean up.
                    value.cloneToUninit(inProgress.dataPtr().cast())
                    // Cast type of pointer, now that it is initialized.
                    inProgress.intoArc()
                }
            return Result.Ok<Arc<T, A>, AllocError>(initializedClone)
        }

        /**
         * Returns a reference to the underlying allocator.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Arc.allocator(a)` instead of `a.allocator()`. This
         * is so that there is no conflict with a method on the inner type.
         */
        public fun <T, A : Allocator> allocator(thisRef: Arc<T, A>): A = thisRef.alloc

        /**
         * Consumes the `Arc`, returning the wrapped pointer and allocator.
         *
         * To avoid a memory leak the pointer must be converted back to an `Arc` using
         * [Arc.fromRawIn].
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x = Arc.newIn("hello".toOwned(), System)
         *     val (ptr, alloc) = Arc.intoRawWithAllocator(x)
         *     check(ptr == "hello")
         *     val x1 = Arc.fromRawIn(ptr, alloc)
         *     check(x1 == "hello")
         * }
         * ```
         */
        public fun <T, A : Allocator> intoRawWithAllocator(thisRef: Arc<T, A>): Pair<ConstPtr, A> {
            val thisRef1 = thisRef
            val ptr = Arc.asPtr(thisRef1)
            // SAFETY: `this` is ManuallyDrop so the allocator will not be double-dropped
            val alloc = read(thisRef1.alloc)
            return Pair(ptr, alloc)
        }

        /**
         * Provides a raw pointer to the data.
         *
         * The counts are not affected in any way and the `Arc` is not consumed. The pointer is valid for
         * as long as there are strong counts in the `Arc`.
         *
         * # Examples
         *
         * ```kotlin
         * val x = "hello".toOwned()
         * val y = x
         * val xPtr = Arc.asPtr(x)
         * check(xPtr == Arc.asPtr(y))
         * check(xPtr == "hello")
         * ```
         */
        public fun <T, A : Allocator> asPtr(thisRef: Arc<T, A>): ConstPtr {
            val ptr: MutPtr = NonNull.asPtr(thisRef.ptr)
            // SAFETY: This cannot go through Deref.deref or ArcInnerPtr.inner because
            // this is required to retain raw/mut provenance such that e.g. `getMut` can
            // write through the pointer after the Arc is recovered through `fromRaw`.
            return addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((ptr).rawAddress).data)
        }

        /**
         * Constructs an `Arc<T, A>` from a raw pointer.
         *
         * The raw pointer must have been previously returned by a call to [`Arc<U,
         * A>::intoRaw`][intoRaw] or [`Arc<U, A>::intoRawWithAllocator`][intoRawWithAllocator].
         *
         * # Safety
         *
         * * Creating a `Arc<T, A>` from a pointer other than one returned from
         *   [`Arc.U.A.intoRaw`][intoRaw] or [`Arc.U.A.intoRawWithAllocator`][intoRawWithAllocator]
         *   is undefined behavior.
         * * If `U` is sized, it must have the same size and alignment as `T`. This
         *   is trivially true if `U` is `T`.
         * * If `U` is unsized, its data pointer must have the same size and
         *   alignment as `T`. This is trivially true if `Arc<U, A>` was constructed
         *   through `Arc<T, A>` and then converted to `Arc<U, A>` through an [unsized
         *   coercion].
         * * Note that if `U` or `U`s data pointer is not `T` but has the same size
         *   and alignment, this is basically like transmuting references of
         *   different types. See [`mem.transmute`][reinterpret] for more information
         *   on what restrictions apply in this case.
         * * The raw pointer must point to a block of memory allocated by `alloc`
         * * The user of `fromRaw` has to make sure a specific value of `T` is only
         *   dropped once.
         *
         * This function is unchecked because improper use may lead to memory unsafety,
         * even if the returned `Arc<T>` is never accessed.
         *
         * [intoRaw]: Arc.intoRaw
         * [intoRawWithAllocator]: Arc.intoRawWithAllocator
         * [unsized coercion]: https://doc.rust-lang.org/reference/type-coercions.html#unsized-coercions
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x = Arc.newIn("hello".toOwned(), System)
         *     val (xPtr, alloc) = Arc.intoRawWithAllocator(x)
         *     run {
         *         // Convert back to an `Arc` to prevent leak.
         *         val x1 = Arc.fromRawIn(xPtr, System)
         *         check(x1 == "hello")
         *         // Further calls to `Arc.fromRaw(xPtr)` would be memory-unchecked.
         *     }
         *     // The memory was freed when `x` went out of scope above, so `xPtr` is now dangling!
         * }
         * ```
         *
         * Convert a slice back into its original array:
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x: List<UInt> = Arc.newIn(listOf(1, 2, 3), System)
         *     val xPtr: ConstPtr = Arc.intoRawWithAllocator(x).first
         *     run {
         *         val x1: UIntArray = Arc.fromRawIn(ConstPtr((xPtr).rawAddress), System)
         *         check(x1 == listOf(1, 2, 3))
         *     }
         * }
         * ```
         */
        public fun <T, A : Allocator> fromRawIn(ptr: ConstPtr, alloc: A): Arc<T, A> {
            // SAFETY: Upheld by caller.
            return run {
                val offset = dataOffset(ptr)
                // Reverse the offset to find the original ArcInner.
                val arcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                Arc.fromPtrIn(arcPtr, alloc)
            }
        }

        /**
         * Creates a new [Weak] pointer to this allocation.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val weakFive = Arc.downgrade(five)
         * ```
         */
        public fun <T, A : Allocator> downgrade(thisRef: Arc<T, A>): Weak<T, A> where A : AllocatorClone {
            // This Relaxed is OK because we're checking the value in the CAS
            // below.
            var cur = thisRef.inner().weak.load(Relaxed)
            while (true) {
                // check if the weak counter is currently "locked"; if so, spin.
                if (cur == Int.MAX_VALUE) {
                    kmSpinLoopHint()
                    cur = thisRef.inner().weak.load(Relaxed)
                    continue
                }
                // We can't allow the refcount to increase much past `MAX_REFCOUNT`.
                if (cur > MAX_REFCOUNT) {
                    panicArcOverflow()
                }
                // NOTE: this code currently ignores the possibility of overflow
                // into usize.Max; in general both Rc and Arc need to be adjusted
                // to deal with overflow.
                // Unlike with Clone(), we need this to be an Acquire read to
                // synchronize with the write coming from `isUnique`, so that the
                // events prior to that write happen before this read.
                run {
                    val matchVal = thisRef.inner().weak.compareExchangeWeak(cur, cur + 1, Acquire, Relaxed)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        // Make sure we do not create a dangling Weak
                        check(!isDangling(thisRef.ptr.asPtr()))
                        return Weak(ptr = thisRef.ptr, alloc = thisRef.alloc.clone())
                    }; is Result.Err<*, *> -> {
                        val old = matchVal.errValue()
                        cur = old
                    } }
                }
            }
        }

        /**
         * Gets the number of [Weak] pointers to this allocation.
         *
         * # Safety
         *
         * This method by itself is safe, but using it correctly requires extra care.
         * Another thread can change the weak count at any time,
         * including potentially between calling this method and acting on the result.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val weakFive = Arc.downgrade(five)
         * // This assertion is deterministic because we haven't shared
         * // the `Arc` or `Weak` between threads.
         * check(1 == Arc.weakCount(five))
         * ```
         */
        public fun <T, A : Allocator> weakCount(thisRef: Arc<T, A>): Int {
            val cnt = thisRef.inner().weak.load(Relaxed)
            // If the weak count is currently locked, the value of the
            // count was 0 just before taking the lock.
            return if (cnt == Int.MAX_VALUE) {
                0
            } else {
                cnt - 1
            }
        }

        /**
         * Gets the number of strong (`Arc`) pointers to this allocation.
         *
         * # Safety
         *
         * This method by itself is safe, but using it correctly requires extra care.
         * Another thread can change the strong count at any time,
         * including potentially between calling this method and acting on the result.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val alsoFive = five
         * // This assertion is deterministic because we haven't shared
         * // the `Arc` between threads.
         * check(2 == Arc.strongCount(five))
         * ```
         */
        public fun <T, A : Allocator> strongCount(thisRef: Arc<T, A>): Int = thisRef.inner().strong.load(Relaxed)

        /**
         * Increments the strong reference count on the `Arc<T>` associated with the
         * provided pointer by one.
         *
         * # Safety
         *
         * The pointer must have been obtained through `Arc.intoRaw` and must satisfy the
         * same layout requirements specified in [`Arc.fromRawIn`][fromRawIn].
         * The associated `Arc` instance must be valid (i.e. the strong count must be at
         * least 1) for the duration of this method, and `ptr` must point to a block of memory
         * allocated by `alloc`.
         *
         * [fromRawIn]: Arc.fromRawIn
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Arc.newIn(5, System)
         *     run {
         *         val (ptr, alloc) = Arc.intoRawWithAllocator(five)
         *         Arc.incrementStrongCountIn(ptr, System)
         *         // This assertion is deterministic because we haven't shared
         *         // the `Arc` between threads.
         *         val five1 = Arc.fromRawIn(ptr, System)
         *         check(2 == Arc.strongCount(five1))
         *     }
         * }
         * ```
         */
        public fun <A : Allocator> incrementStrongCountIn(ptr: ConstPtr, alloc: A) where A : AllocatorClone {
            // Retain Arc, but don't touch refcount by wrapping in ManuallyDrop
            // SAFETY: Upheld by caller.
            // Now increase refcount, but don't drop new refcount either
        }

        /**
         * Decrements the strong reference count on the `Arc<T>` associated with the
         * provided pointer by one.
         *
         * # Safety
         *
         * The pointer must have been obtained through `Arc.intoRaw` and must satisfy the
         * same layout requirements specified in [`Arc.fromRawIn`][fromRawIn].
         * The associated `Arc` instance must be valid (i.e. the strong count must be at
         * least 1) when invoking this method, and `ptr` must point to a block of memory
         * allocated by `alloc`. This method can be used to release the final
         * `Arc` and backing storage, but **should not** be called after the final `Arc` has been
         * released.
         *
         * [fromRawIn]: Arc.fromRawIn
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Arc.newIn(5, System)
         *     run {
         *         val (ptr, alloc) = Arc.intoRawWithAllocator(five)
         *         Arc.incrementStrongCountIn(ptr, System)
         *         // Those assertions are deterministic because we haven't shared
         *         // the `Arc` between threads.
         *         val five1 = Arc.fromRawIn(ptr, System)
         *         check(2 == Arc.strongCount(five1))
         *         Arc.decrementStrongCountIn(ptr, System)
         *         check(1 == Arc.strongCount(five1))
         *     }
         * }
         * ```
         */
        public fun <A : Allocator> decrementStrongCountIn(ptr: ConstPtr, alloc: A) {
            // SAFETY: Upheld by caller.
            (Arc.fromRawIn(ptr, alloc)).let { }
        }

        /**
         * Returns `true` if the two `Arc`s point to the same allocation in a vein similar to
         * [ptr.eq]. This function ignores the metadata of  `dynamic Trait` pointers.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val sameFive = five
         * val otherFive = 5
         * check(Arc.ptrEq(five, sameFive))
         * check(!Arc.ptrEq(five, otherFive))
         * ```
         */
        public fun <T, A : Allocator> ptrEq(thisRef: Arc<T, A>, other: Arc<T, A>): Boolean = addrEq((thisRef.ptr.asPtr()).asConstPtr(), (other.ptr.asPtr()).asConstPtr())

        /**
         * Allocates an `ArcInner<T>` with sufficient space for an unsized inner value.
         */
        public fun <A : Allocator> allocateForPtrIn(ptr: ConstPtr, alloc: A): MutPtr {
            // Allocate for the `ArcInner<T>` using the given value.
            // ignore-tidy-undocumented-unchecked
            return Arc.allocateForLayout(Layout.forValueRaw(ptr), { layout -> alloc.allocate(layout) }, { mem -> mem.withMetadataOf(ConstPtr(ptr.rawAddress, ptr.rawMeta)) })
        }

        public fun <T, A : Allocator> fromBoxIn(src: T): Arc<T, A> {
            // ignore-tidy-undocumented-unchecked
            return run {
                val valueSize = sizeOfVal(src)
                val ptr = Arc.allocateForPtrIn(src, Box.allocator(src))
                // Copy value as bytes
                copyNonoverlapping(((src)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, ((addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((ptr).rawAddress).data))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, valueSize, typeLayoutT = PrimitiveLayouts.U8)
                // Free the allocation without dropping its contents
                val (bptr, alloc) = Box.intoRawWithAllocator(src)
                val src1 = Box.fromRawIn(mutPtrFromRustCast(bptr), alloc)
                (src1).let { }
                Arc.fromPtrIn(ptr, alloc)
            }
        }

        /**
         * Makes a mutable reference into the given `Arc`.
         *
         * If there are other `Arc` pointers to the same allocation, then `makeMut` will
         * [clone] the inner value to a new allocation to ensure unique ownership.  This is also
         * referred to as clone-on-write.
         *
         * However, if there are no other `Arc` pointers to this allocation, but some [Weak]
         * pointers, then the [Weak] pointers will be dissociated and the inner value will not
         * be cloned.
         *
         * See also [getMut], which will fail rather than cloning the inner value
         * or dissociating [Weak] pointers.
         *
         * [clone]: Clone.clone
         * [getMut]: Arc.getMut
         *
         * # Examples
         *
         * ```kotlin
         * var data = 5
         * Arc.makeMut(data) += 1
         * // Won't clone anything
         * var otherData = data
         * // Won't clone inner data
         * Arc.makeMut(data) += 1
         * // Clones inner data
         * Arc.makeMut(data) += 1
         * // Won't clone anything
         * Arc.makeMut(otherData) *= 2
         * // Won't clone anything
         * // Now `data` and `otherData` point to different allocations.
         * check(data == 8)
         * check(otherData == 12)
         * ```
         *
         * [Weak] pointers will be dissociated:
         *
         * ```kotlin
         * var data = 75
         * val weak = Arc.downgrade(data)
         * check(75 == data)
         * check(75 == weak.upgrade().unwrap())
         * Arc.makeMut(data) += 1
         * check(76 == data)
         * check(weak.upgrade() == null)
         * ```
         */
        public fun <T, A : Allocator> makeMut(thisRef: Arc<T, A>): T {
            var thisRef1 = thisRef
            val sizeOfVal = sizeOfVal<T>(thisRef1, typeLayoutT = UnresolvedTypeLayout<T>())
            // Note that we hold both a strong reference and a weak reference.
            // Thus, releasing our strong reference only will not, by itself, cause
            // the memory to be deallocated.
            //
            // Use Acquire to ensure that we see any writes to `weak` that happen
            // before release writes (i.e., decrements) to `strong`. Since we hold a
            // weak count, there's no chance the ArcInner itself could be
            // deallocated.
            if (thisRef1.inner().strong.compareExchange(1, 0, Acquire, Relaxed).isErr()) {
                // Another strong pointer exists, so we must clone.
                thisRef1 = Arc.cloneFromRefIn(thisRef1, thisRef1.alloc.clone())
            } else if (thisRef1.inner().weak.load(Relaxed) != 1) {
                // Relaxed suffices in the above because this is fundamentally an
                // optimization: we are always racing with weak pointers being
                // dropped. Worst case, we end up allocated a new Arc unnecessarily.
                // We removed the last strong ref, but there are additional weak
                // refs remaining. We'll move the contents to a new Arc, and
                // invalidate the other weak refs.
                // Note that it is not possible for the read of `weak` to yield
                // usize.Max (i.e., locked), since the weak count can only be
                // locked by a thread with a strong reference.
                // Guard against panics while using the allocator.
                // If we unwind before the Arc is overwritten, we expose a strong
                // count of 0, resulting in a UAF (#155746, #157203).
                // Until the new Arc is written, the old Arc must remain valid
                open class Guard<T>(
                    public var inner: ArcInner<T>,
                ) {
                    public open fun drop() {
                        this.inner.strong.store(1, Release)
                    }
                }
                val guard = Guard(inner = thisRef1.inner())
                // Can just steal the data, all that's left is Weaks
                // Note that this can panic in two ways:
                // - The allocation can fail
                // - The allocator clone can fail
                val inProgress: UniqueArcUninit<T, A>  = UniqueArcUninit.new(thisRef1, thisRef1.alloc.clone())
                // ignore-tidy-undocumented-unchecked
                run {
                    // Initialize `inProgress` with move of **this.
                    // We have to express this in terms of bytes because `T: ? Sized`; there is no
                    // operation that just copies a value based on its `sizeOfVal()`.
                    copyNonoverlapping(thisRef1.cast(), inProgress.dataPtr().cast(), sizeOfVal, typeLayoutT = PrimitiveLayouts.U8)
                    // We are now safe from panics.
                    forget(guard)
                    // Materialize our own implicit weak pointer, so that it can clean
                    // up the ArcInner as needed.
                    // Make sure the allocator is not leaked when the Arc is overwritten.
                    // Only drop at the end of the scope to avoid panics.
                    write(thisRef1, inProgress.intoArc())
                }
            } else {
                // We were the sole reference of either kind; bump back up the
                // strong ref count.
                thisRef1.inner().strong.store(1, Release)
            }
            // SAFETY: As with `getMut()`, our reference was
            // either unique to begin with, or became one upon cloning the contents.
            return Arc.getMutUnchecked(thisRef1)
        }

        /**
         * If we have the only reference to `T` then unwrap it. Otherwise, clone `T` and return the
         * clone.
         *
         * Assuming `arcT` is of type `Arc<T>`, this function is functionally equivalent to
         * `(* arcT).clone()`, but will avoid cloning the inner value where possible.
         *
         * # Examples
         *
         * ```kotlin
         * val inner = "test"
         * val ptr = inner.asPtr()
         * val arc = inner
         * val inner1 = Arc.unwrapOrClone(arc)
         * // The inner value was not cloned
         * check(eq(ptr, inner1.asPtr()))
         * val arc1 = inner1
         * val arc2 = arc1.clone()
         * val inner2 = Arc.unwrapOrClone(arc1)
         * // Because there were 2 references, we had to clone the inner value.
         * check(!eq(ptr, inner2.asPtr()))
         * // `arc2` is the last reference, so when we unwrap it we get back
         * // the original `String`.
         * val inner3 = Arc.unwrapOrClone(arc2)
         * check(eq(ptr, inner3.asPtr()))
         * ```
         */
        public fun <T, A : Allocator> unwrapOrClone(thisRef: Arc<T, A>): T = Arc.tryUnwrap(thisRef).unwrapOrElse({ arc -> arc.clone() })

        /**
         * Returns a mutable reference into the given `Arc`, if there are
         * no other `Arc` or [Weak] pointers to the same allocation.
         *
         * Returns [None] otherwise, because it is not safe to
         * mutate a shared value.
         *
         * See also [`makeMut`][makeMut], which will [`clone`][clone]
         * the inner value when there are other `Arc` pointers.
         *
         * [makeMut]: Arc.makeMut
         * [clone]: Clone.clone
         *
         * # Examples
         *
         * ```kotlin
         * var x = 3
         * Arc.getMut(x).unwrap() = 4
         * check(x == 4)
         * val y = x
         * check(Arc.getMut(x) == null)
         * ```
         */
        public fun <T, A : Allocator> getMut(thisRef: Arc<T, A>): T? =
            if (Arc.isUnique(thisRef)) {
                // SAFETY: We're guaranteed that the pointer
                // returned is the *only* pointer that will ever be returned to T. Our
                // reference count is guaranteed to be 1 at this point, and we required
                // the Arc itself to be , so we're returning the only possible
                // reference to the inner data.
                Arc.getMutUnchecked(thisRef)
            } else {
                null
            }

        /**
         * Returns a mutable reference into the given `Arc`,
         * without any check.
         *
         * See also [getMut], which is safe and does appropriate checks.
         *
         * [getMut]: Arc.getMut
         *
         * # Safety
         *
         * If any other `Arc` or [Weak] pointers to the same allocation exist, then
         * they must not be dereferenced or have active borrows for the duration
         * of the returned borrow, and their inner type must be exactly the same as the
         * inner type of this Arc (including scopes). This is trivially the case if no
         * such pointers exist, for example immediately after `Arc.new`.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(getMutUnchecked)
         * public fun doctestEntry() {
         *     var x = ""
         *     Arc.getMutUnchecked(x).pushStr("foo")
         *     check(x == "foo")
         * }
         * ```
         * Other `Arc` pointers to the same allocation must be to the same type.
         * ```kotlin
         * @feature(getMutUnchecked)
         * public fun doctestEntry() {
         *     val x: String = Arc.from("Hello, world!")
         *     var y: List<UByte> = x.clone().into()
         *     run {
         *         // this is Undefined Behavior, because x's inner type is str, not [u8]
         *         Arc.getMutUnchecked(y).fill(0xff)
         *         // 0xff is invalid in UTF-8
         *     }
         *     println("$x")
         *     // Invalid UTF-8 in a str
         * }
         * ```
         * Other `Arc` pointers to the same allocation must be to the exact same type, including scopes.
         * ```kotlin
         * @feature(getMutUnchecked)
         * public fun doctestEntry() {
         *     val x: String = "Hello, world!"
         *     run {
         *         val s = "Oh, no!"
         *         var y: String = x.clone()
         *         run {
         *             // this is Undefined Behavior, because x's inner type
         *             // is &long str, not &short str
         *             Arc.getMutUnchecked(y) = s
         *         }
         *     }
         *     println("$x")
         *     // Use-after-free
         * }
         * ```
         */
        public fun <T, A : Allocator> getMutUnchecked(thisRef: Arc<T, A>): T {
            // We are careful to *not* create a reference covering the "count" fields, as
            // this would alias with concurrent access to the reference counts (e.g. by `Weak`).
            // ignore-tidy-undocumented-unchecked
            return thisRef.ptr.asPtr().data
        }

        /**
         * Determine whether this is the unique reference to the underlying data.
         *
         * Returns `true` if there are no other `Arc` or [Weak] pointers to the same allocation;
         * returns `false` otherwise.
         *
         * If this function returns `true`, then it is guaranteed to be safe to call [getMutUnchecked]
         * on this `Arc`, as long as no clones occur in between.
         *
         * The result can become stale if another thread can call `clone` or
         * `downgrade` through a shared reference. Make sure you have exclusive
         * access to the `Arc`.
         *
         * # Examples
         *
         * ```kotlin
         * val x = 3
         * check(Arc.isUnique(x))
         * val y = x
         * check(!Arc.isUnique(x))
         * (y).let { }
         * // Weak references also count, because they could be upgraded at any time.
         * val z = Arc.downgrade(x)
         * check(!Arc.isUnique(x))
         * ```
         *
         * # Pointer invalidation
         *
         * This function will always return the same value as `Arc.getMut(arc).isSome()`. However,
         * unlike that operation it does not produce any mutable references to the underlying data,
         * meaning no pointers to the data inside the `Arc` are invalidated by the call. Thus, the
         * following code is valid:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         *
         * val arc = 5
         * val pointer: ConstPtr = arc
         * check(Arc.isUnique(arc))
         * check((PrimitiveLayouts.I32).readNative((pointer).rawAddress) == 5)
         * ```
         *
         * The following changes would make the example unsound:
         *
         * - Using `Arc.getMut` to create a mutable reference while also accessing the value
         *   through `pointer`.
         * - Dropping `arc` before dereferencing `pointer`, leaving it dangling.
         *
         * # Atomic orderings
         *
         * Concurrent drops to other `Arc` pointers to the same allocation will synchronize with this
         * call - that is, this call performs an `Acquire` operation on the underlying strong and weak
         * ref counts. This ensures that calling `getMutUnchecked` is safe.
         *
         * Note that this operation requires locking the weak ref count, so concurrent calls to
         * `downgrade` may spin-loop for a short period of time.
         *
         * [getMutUnchecked]: Self.getMutUnchecked
         */
        public fun <T, A : Allocator> isUnique(thisRef: Arc<T, A>): Boolean {
            // lock the weak pointer count if we appear to be the sole weak pointer
            // holder.
            //
            // The acquire label here ensures a happens-before relationship with any
            // writes to `strong` (in particular in `Weak.upgrade`) prior to decrements
            // of the `weak` count (via `Weak.drop`, which uses release). If the upgraded
            // weak ref was never dropped, the CAS here will fail so we do not care to synchronize.
            return if (thisRef.inner().weak.compareExchange(1, Int.MAX_VALUE, Acquire, Relaxed).isOk()) {
                // This needs to be an `Acquire` to synchronize with the decrement of the `strong`
                // counter in `drop` -- the only access that happens when any but the last reference
                // is being dropped.
                val unique = thisRef.inner().strong.load(Acquire) == 1
                // The release write here synchronizes with a read in `downgrade`,
                // effectively preventing the above read of `strong` from happening
                // after the write.
                thisRef.inner().weak.store(1, Release)
                // release the lock
                unique
            } else {
                false
            }
        }

        /**
         * Move a boxed object to a new, reference-counted allocation.
         *
         * # Example
         *
         * ```kotlin
         * val unique: String = Holder.from("eggplant")
         * val shared: String = Arc.from(unique)
         * check("eggplant" == shared)
         * ```
         */
        public fun <T, A : Allocator> from(v: T): Arc<T, A> = Arc.fromBoxIn(v)
    }
}

// SAFETY: `Arc.clone` doesn't access any `Cell`s which could contain the `Arc` being cloned.

public fun <T> Arc.Companion.fromInner(ptr: NonNull): Arc<T, Global> {
    // SAFETY: Upheld by caller.
    return Arc.fromInnerIn(ptr, Global())
}

public fun <T> Arc.Companion.fromPtr(ptr: MutPtr): Arc<T, Global> {
    // SAFETY: Upheld by caller.
    return Arc.fromPtrIn(ptr, Global())
}

/**
 * `Weak` is a version of [Arc] that holds a non-owning reference to the
 * managed allocation.
 *
 * The allocation is accessed by calling [upgrade] on the `Weak`
 * pointer, which returns an <code>[Option]<[Arc]\<T>></code>.
 *
 * Since a `Weak` reference does not count towards ownership, it will not
 * prevent the value stored in the allocation from being dropped, and `Weak` itself makes no
 * guarantees about the value still being present. Thus it may return [None]
 * when [upgrade]d. Note however that a `Weak` reference *does* prevent the allocation
 * itself (the backing store) from being deallocated.
 *
 * A `Weak` pointer is useful for keeping a temporary reference to the allocation
 * managed by [Arc] without preventing its inner value from being dropped. It is also used to
 * prevent circular references between [Arc] pointers, since mutual owning references
 * would never allow either [Arc] to be dropped. For example, a tree could
 * have strong [Arc] pointers from parent nodes to children, and `Weak`
 * pointers from children back to their parents.
 *
 * The typical way to obtain a `Weak` pointer is to call [Arc.downgrade].
 *
 * [upgrade]: Weak.upgrade
 */
public open class Weak<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    public var alloc: A,
) : UseCloned {
    // This is a `NonNull` to allow optimizing the size of this type in enums,
    // but it is not necessarily a valid pointer.
    // `Weak.new` sets this to `Int.MAX` so that it doesn’t need
    // to allocate space on the heap. That's not a value a real pointer
    // will ever have because ArcInner has alignment at least 2.
    public var ptr: NonNull = ptrCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("(Weak)"); Result.Ok<Unit, Error>(Unit) }

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A = this.alloc

    /**
     * Returns a raw pointer to the object `T` pointed to by this `Weak<T>`.
     *
     * The pointer is valid only if there are some strong references. The pointer may be dangling,
     * unaligned or even [nullVal] otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ptr
     *
     * val strong = "hello".toOwned()
     * val weak = Arc.downgrade(strong)
     * // Both point to the same object
     * check(ptr.eq(strong, weak.asPtr()))
     * // The strong here keeps it alive, so we can still access the object.
     * check("hello" == weak.asPtr())
     * (strong).let { }
     * // But not any more. We can do weak.asPtr(), but accessing the pointer would lead to
     * // undefined behavior.
     * // assertEq("hello", unchecked ( &*weak.asPtr() ));
     * ```
     */
    public open fun asPtr(): ConstPtr {
        val ptr: MutPtr = NonNull.asPtr(this.ptr)
        return if (isDangling(ptr)) {
            // If the pointer is dangling, we return the sentinel directly. This cannot be
            // a valid payload address, as the payload is at least as aligned as ArcInner (Int).
            ConstPtr(ptr.rawAddress, ptr.rawMeta)
        } else {
            // SAFETY: if isDangling returns false, then the pointer is dereferenceable.
            // The payload may be dropped at this point, and we have to maintain provenance,
            // so use raw pointer manipulation.
            addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((ptr).rawAddress).data)
        }
    }

    /**
     * Consumes the `Weak<T>`, returning the wrapped pointer and allocator.
     *
     * This converts the weak pointer into a raw pointer, while still preserving the ownership of
     * one weak reference (the weak count is not modified by this operation). It can be turned
     * back into the `Weak<T>` with [fromRawIn].
     *
     * The same restrictions of accessing the target of the pointer as with
     * [asPtr] apply.
     *
     * # Examples
     *
     * ```kotlin
     * import std.alloc.System
     * import std.sync.Weak
     *
     * @feature(allocatorExt)
     * public fun doctestEntry() {
     *     val strong = Arc.newIn("hello".toOwned(), System)
     *     val weak = Arc.downgrade(strong)
     *     val (raw, alloc) = weak.intoRawWithAllocator()
     *     check(1 == Arc.weakCount(strong))
     *     check("hello" == raw)
     *     (Weak.fromRawIn(raw, alloc)).let { }
     *     check(0 == Arc.weakCount(strong))
     * }
     * ```
     *
     * [fromRawIn]: Weak.fromRawIn
     * [asPtr]: Weak.asPtr
     */
    public open fun intoRawWithAllocator(): Pair<ConstPtr, A> {
        val thisRef = this
        val result = thisRef.asPtr()
        // SAFETY: `this` is ManuallyDrop so the allocator will not be double-dropped
        val alloc = read(thisRef.alloc)
        return Pair(result, alloc)
    }

    /**
     * Attempts to upgrade the `Weak` pointer to an [Arc], delaying
     * dropping of the inner value if successful.
     *
     * Returns [None] in the following cases:
     *
     * 1. The inner value has since been dropped or moved out.
     *
     * 2. This `Weak` does not point to an allocation.
     *
     * 3. The owning reference this `Weak` is associated with is either not fully-constructed or does not allow an upgrade.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * val weakFive = Arc.downgrade(five)
     * val strongFive: Arc? = weakFive.upgrade()
     * check(strongFive != null)
     * // Destroy all strong pointers.
     * (strongFive).let { }
     * (five).let { }
     * check(weakFive.upgrade() == null)
     * ```
     */
    public open fun upgrade(): Arc<T, A>? {
        fun checkedIncrement(n: Int): Int? {
            // Any write of 0 we can observe leaves the field in permanently zero state.
            if (n == 0) {
                return null
            }
            // See comments in `Arc.clone` for why we do this (for `mem.forget`).
            if (n > MAX_REFCOUNT) {
                panicArcOverflow()
            }
            return n + 1
        }
        // We use a CAS loop to increment the strong count instead of a
        // fetchAdd as this function should never take the reference count
        // from zero to one.
        //
        // Relaxed is fine for the failure case because we don't have any expectations about the new state.
        // Acquire is necessary for the success case to synchronise with `Arc.newCyclic`, when the inner
        // value can be initialized after `Weak` references have already been created. In that case, we
        // expect to observe the fully initialized value.
        return if ((this.inner() ?: return null).strong.tryUpdate(Acquire, Relaxed, ::checkedIncrement).isOk()) {
            // SAFETY: pointer is not null, verified in checkedIncrement
            Arc.fromInnerIn(this.ptr, this.alloc.clone())
        } else {
            null
        }
    }

    /**
     * Gets the number of strong (`Arc`) pointers pointing to this allocation.
     *
     * If `self` was created using [Weak.new], this will return 0.
     */
    public open fun strongCount(): Int =
        when (val matchVal = this.inner()) {
            null -> {
                0
            }
            else -> {
                val inner = matchVal
                inner.strong.load(Relaxed)
            }
        }

    /**
     * Gets an approximation of the number of `Weak` pointers pointing to this
     * allocation.
     *
     * If `self` was created using [Weak.new], or if there are no remaining
     * strong pointers, this will return 0.
     *
     * # Accuracy
     *
     * Due to implementation details, the returned value can be off by 1 in
     * either direction when other threads are manipulating any `Arc`s or
     * `Weak`s pointing to the same allocation.
     */
    public open fun weakCount(): Int =
        when (val matchVal = this.inner()) {
            null -> {
                0
            }
            else -> {
                val inner = matchVal
                val weak = inner.weak.load(Acquire)
                val strong = inner.strong.load(Relaxed)
                if (strong == 0) {
                    0
                } else {
                    // Since we observed that there was at least one strong pointer
                    // after reading the weak count, we know that the implicit weak
                    // reference (present whenever any strong references are alive)
                    // was still around when we observed the weak count, and can
                    // therefore safely subtract it.
                    weak - 1
                }
            }
        }

    /**
     * Returns `None` when the pointer is dangling and there is no allocated `ArcInner`,
     * (i.e., when this `Weak` was created by `Weak.new`).
     */
    public open fun inner(): WeakInner? {
        val ptr = this.ptr.asPtr()
        return if (isDangling(ptr)) {
            null
        } else {
            // We are careful to *not* create a reference covering the "data" field, as
            // the field may be mutated concurrently (for example, if the last `Arc`
            // is dropped, the data field will be dropped in-place).
            // ignore-tidy-undocumented-unchecked
            WeakInner(strong = ptr.strong, weak = ptr.weak)
        }
    }

    /**
     * Returns `true` if the two `Weak`s point to the same allocation similar to [ptr.eq], or if
     * both don't point to any allocation (because they were created with `Weak.new()`). However,
     * this function ignores the metadata of  `dynamic Trait` pointers.
     *
     * # Notes
     *
     * Since this compares pointers it means that `Weak.new()` will equal each
     * other, even though they don't point to any allocation.
     *
     * # Examples
     *
     * ```kotlin
     * val firstRc = 5
     * val first = Arc.downgrade(firstRc)
     * val second = Arc.downgrade(firstRc)
     * check(first.ptrEq(second))
     * val thirdRc = 5
     * val third = Arc.downgrade(thirdRc)
     * check(!first.ptrEq(third))
     * ```
     *
     * Comparing `Weak.new`.
     *
     * ```kotlin
     * import std.sync.Weak
     *
     * val first = Weak.new()
     * val second = Weak.new()
     * check(first.ptrEq(second))
     * val thirdRc = Arc.new(Unit)
     * val third = Arc.downgrade(thirdRc)
     * check(!first.ptrEq(third))
     * ```
     */
    public open fun ptrEq(other: Weak<T, A>): Boolean = addrEq((this.ptr.asPtr()).asConstPtr(), (other.ptr.asPtr()).asConstPtr())

    /**
     * Makes a clone of the `Weak` pointer that points to the same allocation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Weak
     *
     * val weakFive = Arc.downgrade(5)
     * Weak.clone(weakFive)
     * ```
     */
    public open fun clone(): Weak<T, A> {
        when (val matchVal = this.inner()) {
            null -> {}
            else -> {
                val inner = matchVal
                // See comments in Arc.clone() for why this is relaxed. This can use a
                // fetchAdd (ignoring the lock) because the weak count is only locked
                // where are *no other* weak pointers in existence. (So we can't be
                // running this code in that case).
                val oldSize = inner.weak.fetchAdd(1, Relaxed)
                // See comments in Arc.clone() for why we do this (for mem.forget).
                if (oldSize > MAX_REFCOUNT) {
                    kmAbort()
                }
            }
        }
        return Weak(ptr = this.ptr, alloc = this.alloc.clone())
    }

    /**
     * Drops the `Weak` pointer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Weak
     *
     * open class Foo {
     *     public open fun drop() {
     *         println("dropped!")
     *     }
     * }
     * val foo = Foo()
     * val weakFoo = Arc.downgrade(foo)
     * val otherWeakFoo = Weak.clone(weakFoo)
     * (weakFoo).let { }
     * // Doesn't print anything
     * (foo).let { }
     * // Prints "dropped!"
     * check(otherWeakFoo.upgrade() == null)
     * ```
     */
    public open fun drop() {
        // If we find out that we were the last weak pointer, then its time to
        // deallocate the data entirely. See the discussion in Arc.drop() about
        // the memory orderings
        //
        // It's not necessary to check for the locked state here, because the
        // weak count can only be locked if there was precisely one weak ref,
        // meaning that drop could only subsequently run ON that remaining weak
        // ref, which can only happen after the lock is released.
        val inner1 =
            when (val matchVal = this.inner()) {
                null -> {
                    return
                }
                else -> {
                    val inner = matchVal
                    inner
                }
            }
        if (inner1.weak.fetchSub(1, Release) == 1) {
            inner1.weak.load(Acquire)
            // Make sure we aren't trying to "deallocate" the shared static for empty slices
            // used by Default.default.
            check(!addrEq((this.ptr.asPtr()).asConstPtr(), STATIC_INNER_SLICE.inner)) { "Arc/Weaks backed by a static should never be deallocated. Likely decrement_strong_count or from_raw were called too many times." }
            // ignore-tidy-undocumented-unchecked
            this.alloc.deallocate(this.ptr.cast(), Layout.forValueRaw(this.ptr.asPtr()))
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            alloc: A,
        ): Weak<T, A> = Weak<T, A>(
            ptrCtorArg = ptr,
            alloc = alloc,
        )

        /**
         * Constructs a new `Weak<T, A>`, without allocating any memory, technically in the provided
         * allocator.
         * Calling [upgrade] on the return value always gives [None].
         *
         * [upgrade]: Weak.upgrade
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         * import std.sync.Weak
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val empty: Weak<Long> = Weak.newIn(System)
         *     check(empty.upgrade() == null)
         * }
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): Weak<T, A> = Weak(ptr = NonNull.withoutProvenance(NonZeroUsize.MAX), alloc = alloc)

        /**
         * Converts a raw pointer previously created by [intoRaw] back into `Weak<T>` in the provided
         * allocator.
         *
         * This can be used to safely get a strong reference (by calling [upgrade]
         * later) or to deallocate the weak count by dropping the `Weak<T>`.
         *
         * It takes ownership of one weak reference (with the exception of pointers created by [new],
         * as these don't own anything; the method still works on them).
         *
         * # Safety
         *
         * The pointer must have originated from the [intoRaw] and must still own its potential
         * weak reference, and must point to a block of memory allocated by `alloc`.
         *
         * It is allowed for the strong count to be 0 at the time of calling this. Nevertheless, this
         * takes ownership of one weak reference currently represented as a raw pointer (the weak
         * count is not modified by this operation) and therefore it must be paired with a previous
         * call to [intoRaw].
         * # Examples
         *
         * ```kotlin
         * import std.sync.Weak
         *
         * val strong = "hello".toOwned()
         * val raw1 = Arc.downgrade(strong).intoRaw()
         * val raw2 = Arc.downgrade(strong).intoRaw()
         * check(2 == Arc.weakCount(strong))
         * check("hello" == Weak.fromRaw(raw1).upgrade().unwrap())
         * check(1 == Arc.weakCount(strong))
         * (strong).let { }
         * // Decrement the last weak count.
         * check(Weak.fromRaw(raw2).upgrade() == null)
         * ```
         *
         * [new]: Weak.new
         * [intoRaw]: Weak.intoRaw
         * [upgrade]: Weak.upgrade
         */
        public fun <T, A : Allocator> fromRawIn(ptr: ConstPtr, alloc: A): Weak<T, A> {
            // See Weak.asPtr for context on how the input pointer is derived.
            val ptr1 =
                if (isDangling(ptr)) {
                    // This is a dangling Weak.
                    MutPtr(ptr.rawAddress, ptr.rawMeta)
                } else {
                    // Otherwise, we're guaranteed the pointer came from a nondangling Weak.
                    // SAFETY: dataOffset is safe to call, as ptr references a real (potentially dropped) T.
                    val offset = dataOffset(ptr)
                    // Thus, we reverse the offset to get the whole ArcInner.
                    // SAFETY: the pointer originated from a Weak, so this offset is safe.
                    (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                }
            // SAFETY: we now have recovered the original Weak pointer, so can create the Weak.
            return Weak(ptr = NonNull.newUnchecked(ptr1), alloc = alloc)
        }
    }
}

// SAFETY: `Weak.clone` doesn't access any `Cell`s which could contain the `Weak` being cloned.

// This is repr(C) to future-proof against possible field-reordering, which
// would interfere with otherwise safe [into|from]_raw() of transmutable
// inner types.
// Unlike RcInner, repr(align(2)) is not strictly required because atomic types
// have the alignment same as its size, but we use it for consistency and clarity.
public open class ArcInner<T> internal constructor(
    strongCtorArg: Atomic<Int, Align8<Int>>,
    weakCtorArg: Atomic<Int, Align8<Int>>,
    public var data: T,
) {
    public var strong: Atomic<Int, Align8<Int>> = strongCtorArg
        internal set

    // the value usize.Max acts as a sentinel for temporarily "locking" the
    // weak count, preventing `Arc.downgrade` from racing to create new
    // `Weak` references. `Arc.isUnique` (which backs `Arc.getMut`)
    // needs to observe both the strong and weak counts as indicating
    // uniqueness in one logical atomic step; since they live in separate
    // atomic words, it locks the weak count while reading the strong
    // count to keep the two reads consistent.
    public var weak: Atomic<Int, Align8<Int>> = weakCtorArg
        internal set

    public companion object {
        public operator fun <T> invoke(
            strong: Atomic<Int, Align8<Int>>,
            weak: Atomic<Int, Align8<Int>>,
            data: T,
        ): ArcInner<T> = ArcInner<T>(
            strongCtorArg = strong,
            weakCtorArg = weak,
            data = data,
        )
    }
}

/**
 * Calculate layout for `ArcInner<T>` using the inner value's layout
 */
public fun arcinnerLayoutForValueLayout(layout: Layout): Layout {
    // Calculate layout using the given value layout.
    // Previously, layout was calculated on the expression
    // `* (ptr as * const ArcInner<T>)`, but this created a misaligned
    // reference (see #54908).
    return Layout.new<ArcInner<Unit>>(typeLayoutT = UnresolvedTypeLayout<ArcInner<Unit>>()).extend(layout).unwrapOrElse({ error("capacity overflow") }).first.padToAlign()
}

/**
 * Constructs a new `Arc<T>`.
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * ```
 */
public fun <T> Arc.Companion.new(data: T): Arc<T, Global> {
    // Start the weak pointer count as 1 which is the weak pointer that's
    // held by all the strong pointers (kinda), see std/rc.rs for more info
    val x: Box = ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = data)
    // SAFETY: Pointer is valid.
    return Arc.fromInner(Box.intoNonNull(x))
}

/**
 * Constructs a new `Arc<T>` while giving you a `Weak<T>` to the allocation,
 * to allow you to construct a `T` which holds a weak pointer to itself.
 *
 * Generally, a structure circularly referencing itself, either directly or
 * indirectly, should not hold a strong reference to itself to prevent a memory leak.
 * Using this function, you get access to the weak pointer during the
 * initialization of `T`, before the `Arc<T>` is created, such that you can
 * clone and store it inside the `T`.
 *
 * `newCyclic` first allocates the managed allocation for the `Arc<T>`,
 * then calls your closure, giving it a `Weak<T>` to this allocation,
 * and only afterwards completes the construction of the `Arc<T>` by placing
 * the `T` returned from your closure into the allocation.
 *
 * Since the new `Arc<T>` is not fully-constructed until `Arc<T>.newCyclic`
 * returns, calling [upgrade] on the weak reference inside your closure will
 * fail and result in a `None` value.
 *
 * # Panics
 *
 * If `dataFn` panics, the panic is propagated to the caller, and the
 * temporary [Weak.T] is dropped normally.
 *
 * # Example
 *
 * ```kotlin
 * import std.sync.Weak
 *
 * open class Gadget(public var me: Weak<Gadget>) {
 *     / **
 *      * Constructs a reference counted Gadget.
 *      * /
 *     public open fun new(): Gadget {
 *         // `me` is a `Weak<Gadget>` pointing at the new allocation of the
 *         // `Arc` we're constructing.
 *         return Arc.newCyclic({ me ->
 *             // Create the actual struct here.
 *             Gadget(me = me.clone())
 *         })
 *     }
 *
 *     / **
 *      * Returns a reference counted pointer to Self.
 *      * /
 *     public open fun me(): Gadget {
 *         return this.me.upgrade().unwrap()
 *     }
 * }
 * ```
 * [upgrade]: Weak.upgrade
 */
public fun <T> Arc.Companion.newCyclic(dataFn: (Weak<T, Global>) -> T): Arc<T, Global> = Arc.newCyclicIn(dataFn, Global())

/**
 * Constructs a new `Arc` with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var five = Arc.newUninit()
 * // Deferred initialization:
 * Arc.getMut(five).unwrap().write(5)
 * val five1 = five.assumeInit()
 * check(five1 == 5)
 * ```
 */
public fun <T> Arc.Companion.newUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<MaybeUninit<T>, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtr(Arc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global().allocate(layout) }, MutPtr.cast))
}

/**
 * Constructs a new `Arc` with uninitialized contents, with the memory
 * being filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val zero = Arc.newZeroed()
 * val zero1 = zero.assumeInit()
 * check(zero1 == 0)
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Arc.Companion.newZeroed(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<MaybeUninit<T>, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtr(Arc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global().allocateZeroed(layout) }, MutPtr.cast))
}

/**
 * Constructs a new `Pin<Arc<T>>`. If `T` does not implement `Unpin`, then
 * `data` will be pinned in memory and unable to be moved.
 */
public fun <T> Arc.Companion.pin(data: T): Pin<Arc<T, Global>> {
    // SAFETY: We own and create the pinned pointer.
    return Pin.newUnchecked(Arc.new(data))
}

/**
 * Constructs a new `Pin<Arc<T>>`, return an error if allocation fails.
 */
public fun <T> Arc.Companion.tryPin(data: T): Result<Pin<Arc<T, Global>>, AllocError> {
    // SAFETY: We own and create the pinned pointer.
    return Result.Ok<Pin<Arc<T, Global>>, AllocError>(Pin.newUnchecked(when (val tryResult = Arc.tryNew(data)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pin<Arc<T, Global>>, AllocError>(tryResult.errValue()) }))
}

/**
 * Constructs a new `Arc<T>`, returning an error if allocation fails.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val five = Arc.tryNew(5).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> Arc.Companion.tryNew(data: T): Result<Arc<T, Global>, AllocError> {
    // Start the weak pointer count as 1 which is the weak pointer that's
    // held by all the strong pointers (kinda), see std/rc.rs for more info
    val x: Box = when (val tryResult = Box.tryNew(ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = data))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<T, Global>, AllocError>(tryResult.errValue()) }
    // SAFETY: Pointer is valid.
    return Result.Ok<Arc<T, Global>, AllocError>(Arc.fromInner(Box.intoNonNull(x)))
}

/**
 * Constructs a new `Arc` with uninitialized contents, returning an error
 * if allocation fails.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     var five = Arc.tryNewUninit().getOrElse { return Result.failure(it) }
 *     // Deferred initialization:
 *     Arc.getMut(five).unwrap().write(5)
 *     val five1 = five.assumeInit()
 *     check(five1 == 5)
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> Arc.Companion.tryNewUninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Arc<MaybeUninit<T>, Global>, AllocError> {
    // ignore-tidy-undocumented-unchecked
    return Result.Ok<Arc<MaybeUninit<T>, Global>, AllocError>(Arc.fromPtr(when (val tryResult = Arc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global().allocate(layout) }, MutPtr.cast)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<MaybeUninit<T>, Global>, AllocError>(tryResult.errValue()) }))
}

/**
 * Constructs a new `Arc` with uninitialized contents, with the memory
 * being filled with `0` bytes, returning an error if allocation fails.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and incorrect usage
 * of this method.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val zero = Arc.tryNewZeroed().getOrElse { return Result.failure(it) }
 *     val zero1 = zero.assumeInit()
 *     check(zero1 == 0)
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Arc.Companion.tryNewZeroed(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Arc<MaybeUninit<T>, Global>, AllocError> {
    // ignore-tidy-undocumented-unchecked
    return Result.Ok<Arc<MaybeUninit<T>, Global>, AllocError>(Arc.fromPtr(when (val tryResult = Arc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global().allocateZeroed(layout) }, MutPtr.cast)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Arc<MaybeUninit<T>, Global>, AllocError>(tryResult.errValue()) }))
}

/**
 * Constructs a new atomically reference-counted slice with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var values = Arc.newUninitSlice(3)
 * // Deferred initialization:
 * val data = Arc.getMut(values).unwrap()
 * data[0].write(1)
 * data[1].write(2)
 * data[2].write(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(1, 2, 3))
 * ```
 */
public fun <T> Arc.Companion.newUninitSlice(len: Int): Arc<List<MaybeUninit<T>>, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtr(Arc.allocateForSlice(len))
}

/**
 * Constructs a new atomically reference-counted slice with uninitialized contents, with the memory being
 * filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and
 * incorrect usage of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val values = Arc.newZeroedSlice(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(0, 0, 0))
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Arc.Companion.newZeroedSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<List<MaybeUninit<T>>, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtr(Arc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> Global().allocateZeroed(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) }))
}

/**
 * Constructs a new atomically reference-counted slice with uninitialized contents in the
 * provided allocator.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(getMutUnchecked)
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     var values = Arc.newUninitSliceIn(3, System)
 *     val values1 = run {
 *         // Deferred initialization:
 *         Arc.getMutUnchecked(values)[0].asMutPtr().write(1)
 *         Arc.getMutUnchecked(values)[1].asMutPtr().write(2)
 *         Arc.getMutUnchecked(values)[2].asMutPtr().write(3)
 *         values.assumeInit()
 *     }
 *     check(values1 == listOf(1, 2, 3))
 * }
 * ```
 */
public fun <T, A : Allocator> Arc.Companion.newUninitSliceIn(len: Int, alloc: A): Arc<List<MaybeUninit<T>>, A> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtrIn(Arc.allocateForSliceIn(len, alloc), alloc)
}

/**
 * Constructs a new atomically reference-counted slice with uninitialized contents, with the memory being
 * filled with `0` bytes, in the provided allocator.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and
 * incorrect usage of this method.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     val values = Arc.newZeroedSliceIn(3, System)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(0, 0, 0))
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T, A : Allocator> Arc.Companion.newZeroedSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<List<MaybeUninit<T>>, A> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtrIn(Arc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> alloc.allocateZeroed(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) }), alloc)
}

/**
 * Converts the reference-counted slice into a reference-counted array.
 *
 * This operation does not reallocate; the underlying array of the slice is simply reinterpreted as an array type.
 *
 * # Errors
 *
 * Returns the original `Arc<[T]>` in the `Err` variant if `self.len()` does not equal `N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocSliceIntoArray)
 * public fun doctestEntry() {
 *     val arcSlice: List<Int> = listOf(1, 2, 3)
 *     val arcArray: IntArray = arcSlice.intoArray().unwrap()
 * }
 * ```
 */
public fun <T, A : Allocator> Arc<List<T>, A>.intoArray(n: Int): Result<Arc<List<T>, A>, Arc<List<T>, A>> =
    if (this.len() == n) {
        val (ptr, alloc) = Arc.intoRawWithAllocator(this)
        val ptr1 = constPtrFromRustCast(ptr)
        // SAFETY: The underlying array of a slice has the exact same layout as an actual array `[T; N]` if `N` is equal to the slice's length.
        val me = Arc.fromRawIn(ptr1, alloc)
        Result.Ok<Arc<List<T>, A>, Arc<List<T>, A>>(me)
    } else {
        Result.Err<Arc<List<T>, A>, Arc<List<T>, A>>(this)
    }

/**
 * Converts to `Arc<T>`.
 *
 * # Safety
 *
 * As with [MaybeUninit.assumeInit],
 * it is up to the caller to guarantee that the inner value
 * really is in an initialized state.
 * Calling this when the content is not yet fully initialized
 * causes immediate undefined behavior.
 *
 * [MaybeUninit.assumeInit]: mem.MaybeUninit.assumeInit
 *
 * # Examples
 *
 * ```kotlin
 * var five = Arc.newUninit()
 * // Deferred initialization:
 * Arc.getMut(five).unwrap().write(5)
 * val five1 = five.assumeInit()
 * check(five1 == 5)
 * ```
 */
public fun <T, A : Allocator> Arc<MaybeUninit<T>, A>.assumeInit(): Arc<T, A> {
    val (ptr, alloc) = Arc.intoInnerWithAllocator(this)
    // ignore-tidy-undocumented-unchecked
    return Arc.fromInnerIn(ptr.cast(), alloc)
}

/**
 * Constructs a new `Arc<T>` with a clone of `value`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry() {
 *     val hello: String = Arc.cloneFromRef("hello")
 * }
 * ```
 */
public fun <T : CloneToUninit> Arc.Companion.cloneFromRef(value: T): Arc<T, Global> = Arc.cloneFromRefIn(value, Global())

// @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

/**
 * Constructs a new `Arc<T>` with a clone of `value`, returning an error if allocation fails
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry(): Result<Unit> {
 *     val hello: String = Arc.tryCloneFromRef("hello").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T : CloneToUninit> Arc.Companion.tryCloneFromRef(value: T): Result<Arc<T, Global>, AllocError> = Arc.tryCloneFromRefIn(value, Global())

/**
 * Constructs an `Arc<T>` from a raw pointer.
 *
 * The raw pointer must have been previously returned by a call to
 * [`Arc.U.intoRaw`][intoRaw] or [`Arc.U.intoRawWithAllocator`][intoRawWithAllocator].
 *
 * # Safety
 *
 * * Creating a `Arc<T>` from a pointer other than one returned from
 *   [`Arc.U.intoRaw`][intoRaw] or [`Arc.U.intoRawWithAllocator`][intoRawWithAllocator]
 *   is undefined behavior.
 * * If `U` is sized, it must have the same size and alignment as `T`. This
 *   is trivially true if `U` is `T`.
 * * If `U` is unsized, its data pointer must have the same size and
 *   alignment as `T`. This is trivially true if `Arc<U>` was constructed
 *   through `Arc<T>` and then converted to `Arc<U>` through an [unsized
 *   coercion].
 * * Note that if `U` or `U`s data pointer is not `T` but has the same size
 *   and alignment, this is basically like transmuting references of
 *   different types. See [`mem.transmute`][reinterpret] for more information
 *   on what restrictions apply in this case.
 * * The raw pointer must point to a block of memory allocated by the global allocator.
 * * The user of `fromRaw` has to make sure a specific value of `T` is only
 *   dropped once.
 *
 * This function is unchecked because improper use may lead to memory unsafety,
 * even if the returned `Arc<T>` is never accessed.
 *
 * [intoRaw]: Arc.intoRaw
 * [intoRawWithAllocator]: Arc.intoRawWithAllocator
 * [unsized coercion]: https://doc.rust-lang.org/reference/type-coercions.html#unsized-coercions
 *
 * # Examples
 *
 * ```kotlin
 * val x = "hello".toOwned()
 * val xPtr = Arc.intoRaw(x)
 * run {
 *     // Convert back to an `Arc` to prevent leak.
 *     val x1 = Arc.fromRaw(xPtr)
 *     check(x1 == "hello")
 *     // Further calls to `Arc.fromRaw(xPtr)` would be memory-unchecked.
 * }
 * // The memory was freed when `x` went out of scope above, so `xPtr` is now dangling!
 * ```
 *
 * Convert a slice back into its original array:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * public fun doctestEntry() {
 *     val x: List<UInt> = listOf(1u, 2u, 3u)
 *     val xPtr: ConstPtr = Arc.intoRaw(x)
 *     run {
 *         val x1: UIntArray = Arc.fromRaw(ConstPtr((xPtr).rawAddress))
 *         check(x1 == listOf(1, 2, 3))
 *     }
 * }
 * ```
 */
public fun <T> Arc.Companion.fromRaw(ptr: ConstPtr): Arc<T, Global> {
    // SAFETY: Upheld by caller.
    return Arc.fromRawIn(ptr, Global())
}

/**
 * Consumes the `Arc`, returning the wrapped pointer.
 *
 * To avoid a memory leak the pointer must be converted back to an `Arc` using
 * [Arc.fromRaw].
 *
 * # Examples
 *
 * ```kotlin
 * val x = "hello".toOwned()
 * val xPtr = Arc.intoRaw(x)
 * check(xPtr == "hello")
 * ```
 */
public fun <T> Arc.Companion.intoRaw(thisRef: Arc<T, Global>): ConstPtr {
    val thisRef1 = thisRef
    return Arc.asPtr(thisRef1)
}

/**
 * Increments the strong reference count on the `Arc<T>` associated with the
 * provided pointer by one.
 *
 * # Safety
 *
 * The pointer must have been obtained through `Arc.intoRaw` and must satisfy the
 * same layout requirements specified in [`Arc.fromRawIn`][fromRawIn].
 * The associated `Arc` instance must be valid (i.e. the strong count must be at
 * least 1) for the duration of this method, and `ptr` must point to a block of memory
 * allocated by the global allocator.
 *
 * [fromRawIn]: Arc.fromRawIn
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * run {
 *     val ptr = Arc.intoRaw(five)
 *     Arc.incrementStrongCount(ptr)
 *     // This assertion is deterministic because we haven't shared
 *     // the `Arc` between threads.
 *     val five1 = Arc.fromRaw(ptr)
 *     check(2 == Arc.strongCount(five1))
 * }
 * ```
 */
public fun Arc.Companion.incrementStrongCount(ptr: ConstPtr) {
    // SAFETY: Upheld by caller.
    Arc.incrementStrongCountIn(ptr, Global())
}

/**
 * Decrements the strong reference count on the `Arc<T>` associated with the
 * provided pointer by one.
 *
 * # Safety
 *
 * The pointer must have been obtained through `Arc.intoRaw` and must satisfy the
 * same layout requirements specified in [`Arc.fromRawIn`][fromRawIn].
 * The associated `Arc` instance must be valid (i.e. the strong count must be at
 * least 1) when invoking this method, and `ptr` must point to a block of memory
 * allocated by the global allocator. This method can be used to release the final
 * `Arc` and backing storage, but **should not** be called after the final `Arc` has been
 * released.
 *
 * [fromRawIn]: Arc.fromRawIn
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * run {
 *     val ptr = Arc.intoRaw(five)
 *     Arc.incrementStrongCount(ptr)
 *     // Those assertions are deterministic because we haven't shared
 *     // the `Arc` between threads.
 *     val five1 = Arc.fromRaw(ptr)
 *     check(2 == Arc.strongCount(five1))
 *     Arc.decrementStrongCount(ptr)
 *     check(1 == Arc.strongCount(five1))
 * }
 * ```
 */
public fun Arc.Companion.decrementStrongCount(ptr: ConstPtr) {
    // SAFETY: Upheld by caller.
    Arc.decrementStrongCountIn(ptr, Global())
}

/**
 * Gets the number of strong (`Arc`) pointers to the allocation behind the given raw
 * pointer.
 *
 * This method does not consume or drop the `Arc` behind this pointer.
 *
 * # Safety
 *
 * The pointer must point to (and have valid metadata for) the value inside a live `Arc`
 * allocation, such as a pointer returned by [Arc.intoRaw],
 * [Arc.intoRawWithAllocator], or [Arc.asPtr].
 * `T` must have the same alignment as that value.
 * The associated `Arc` instance must be valid (i.e. the strong count must be at
 * least 1) for the duration of this method.
 *
 * Using this method correctly also requires extra care: another thread can change the
 * strong count at any time, including between calling this method and acting on the
 * result.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(arcRawGetStrong)
 * public fun doctestEntry() {
 *     val five = 5
 *     val alsoFive = five
 *     val ptr = Arc.intoRaw(five)
 *     run {
 *         // This assertion is deterministic because we haven't shared
 *         // the `Arc` between threads.
 *         check(2 == Arc.strongCountFromRaw(ptr))
 *         // Convert back to an `Arc` to avoid leaking memory.
 *         val five1 = Arc.fromRaw(ptr)
 *         check(2 == Arc.strongCount(five1))
 *     }
 * }
 * ```
 */
public fun <T> Arc.Companion.strongCountFromRaw(ptr: ConstPtr): Int {
    // SAFETY: Upheld by caller.
    val offset = dataOffset(ptr)
    // Reverse the offset to find the original ArcInner.
    // SAFETY: Caller ensures this pointer was to an `Arc` allocation,
    // so offsetting must be inbounds.
    val arcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    // SAFETY: Per the above, an `ArcInner` is stored here.
    return (UnresolvedTypeLayout<ArcInner<T>>()).readNative((arcPtr).rawAddress).strong.load(Relaxed)
}

/**
 * Allocates an `ArcInner<T>` with sufficient space for
 * a possibly-unsized inner value where the value has the layout provided.
 *
 * The function `memToArcinner` is called with the data pointer
 * and must return back a (potentially fat)-pointer for the `ArcInner<T>`.
 */
public fun Arc.Companion.allocateForLayout(valueLayout: Layout, allocate: (Layout) -> Result<NonNull, AllocError>, memToArcinner: (MutPtr) -> MutPtr): MutPtr {
    val layout = arcinnerLayoutForValueLayout(valueLayout)
    val ptr = allocate(layout).unwrapOrElse({ handleAllocError(layout) })
    // ignore-tidy-undocumented-unchecked
    return Arc.initializeArcinner(ptr, layout, memToArcinner)
}

/**
 * Allocates an `ArcInner<T>` with sufficient space for
 * a possibly-unsized inner value where the value has the layout provided,
 * returning an error if allocation fails.
 *
 * The function `memToArcinner` is called with the data pointer
 * and must return back a (potentially fat)-pointer for the `ArcInner<T>`.
 */
public fun Arc.Companion.tryAllocateForLayout(valueLayout: Layout, allocate: (Layout) -> Result<NonNull, AllocError>, memToArcinner: (MutPtr) -> MutPtr): Result<MutPtr, AllocError> {
    val layout = arcinnerLayoutForValueLayout(valueLayout)
    val ptr = when (val tryResult = allocate(layout)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<MutPtr, AllocError>(tryResult.errValue()) }
    // ignore-tidy-undocumented-unchecked
    val inner = Arc.initializeArcinner(ptr, layout, memToArcinner)
    return Result.Ok<MutPtr, AllocError>(inner)
}

public fun <T> Arc.Companion.initializeArcinner(ptr: NonNull, layout: Layout, memToArcinner: (MutPtr) -> MutPtr): MutPtr {
    val inner = memToArcinner(ptr.asNonNullPtr().asPtr())
    // SAFETY: Upheld by caller.
    check(Layout.forValueRaw(inner) == layout)
    // ignore-tidy-undocumented-unchecked
    run {
        (UnresolvedTypeLayout<Atomic<Int, Align8<Int>>>()).writeNative(((addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((inner).rawAddress).strong))).rawAddress, AtomicUsize.new(1))
        (UnresolvedTypeLayout<Atomic<Int, Align8<Int>>>()).writeNative(((addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((inner).rawAddress).weak))).rawAddress, AtomicUsize.new(1))
    }
    return inner
}

/**
 * Allocates an `ArcInner<[T]>` with the given length.
 */
public fun <T> Arc.Companion.allocateForSlice(len: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    // ignore-tidy-undocumented-unchecked
    return Arc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> Global().allocate(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) })
}

/**
 * Copy elements from slice into newly allocated `Arc<[T]>`
 *
 * Unsafe because the caller must either take ownership, bind `T: Copy` or
 * bind `T: TrivialClone`.
 */
public fun <T> Arc.Companion.copyFromSlice(v: List<T>): Arc<List<T>, Global> {
    // ignore-tidy-undocumented-unchecked
    return run {
        val ptr = Arc.allocateForSlice(v.size)
        copyNonoverlapping(v.asPtr(), mutPtrFromRustCast((addrOfMut(ptr.data))), v.size, typeLayoutT = UnresolvedTypeLayout<T>())
        Arc.fromPtr(ptr)
    }
}

/**
 * Constructs an `Arc<[T]>` from an iterator known to be of a certain size.
 *
 * Behavior is undefined should the size be wrong.
 */
public fun <T> Arc.Companion.fromIterExact(iter: Any, len: Int): Arc<List<T>, Global> {
    // Panic guard while cloning T elements.
    // In the event of a panic, elements that have been written
    // into the new ArcInner will be dropped, then the memory freed.
    open class Guard<T>(
        public var mem: NonNull,
        public var elems: MutPtr,
        public var layout: Layout,
        public var nElems: Int,
    ) {
        public open fun drop() {
            // ignore-tidy-undocumented-unchecked
            run {
                val slice = linearMutSliceView(this.elems, this.nElems.toInt(), PrimitiveLayouts.U8)
                dropInPlace(slice)
                Global().deallocate(this.mem, this.layout)
            }
        }
    }
    // ignore-tidy-undocumented-unchecked
    return run {
        val ptr = Arc.allocateForSlice(len)
        val mem = (mutPtrFromRustCast(ptr)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        val layout = Layout.forValueRaw(ptr)
        // Pointer to first element
        val elems = mutPtrFromRustCast((addrOfMut(ptr.data)))
        val guard = Guard(mem = NonNull.newUnchecked(mem), elems = elems, layout = layout, nElems = 0)
        for ((i, item) in iter.mapIndexed { index, item -> Pair(index, item) }) {
            write(MutPtr((elems).rawAddress + ((i).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), item, typeLayoutT = UnresolvedTypeLayout<T>())
            guard.nElems += 1
        }
        // All clear. Forget the guard so it doesn't free the new ArcInner.
        forget(guard)
        Arc.fromPtr(ptr)
    }
}

/**
 * Allocates an `ArcInner<[T]>` with the given length.
 */
public fun <T, A : Allocator> Arc.Companion.allocateForSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutPtr {
    // ignore-tidy-undocumented-unchecked
    return Arc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> alloc.allocate(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) })
}

/**
 * Specialization trait used for `From<[T]>`.
 */
public interface ArcFromSlice<T, SelfType> {
    public fun fromSlice(slice: List<T>): SelfType
}

public fun <T> arcFromSliceFromSliceArcTSlice(v: List<T>): Arc<List<T>, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromIterExact(v.iter().cloned(), v.size)
}

/**
 * Attempts to downcast the `Arc<dynamic Any + ThreadSafe>` to a concrete type.
 *
 * # Examples
 *
 * ```kotlin
 * fun printIfString(value: RustAny) {
 *     when (val matchVal = value.downcast<String>()) {
 *         is Ok -> {
 *             val (string) = matchVal
 *             println("String (${string.len()}): $string")
 *         }
 *         else -> {}
 *     }
 * }
 * val myString = "Hello World".toString()
 * printIfString(myString)
 * printIfString(0)
 * ```
 */
public fun <A : Allocator, T> Arc<RustAny, A>.downcast(): Result<Arc<T, A>, Arc<RustAny, A>> =
    if (this.isMatch<T>()) {
        // SAFETY: Check ensures the typecast is okay.
        run {
            val (ptr, alloc) = Arc.intoInnerWithAllocator(this)
            Result.Ok<Arc<T, A>, Arc<RustAny, A>>(Arc.fromInnerIn(ptr.cast(), alloc))
        }
    } else {
        Result.Err<Arc<T, A>, Arc<RustAny, A>>(this)
    }

/**
 * Downcasts the `Arc<dynamic Any + ThreadSafe>` to a concrete type.
 *
 * For a safe alternative see [downcast].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(downcastUnchecked)
 * public fun doctestEntry() {
 *     val x: RustAny = RustAny.from(1)
 *     run {
 *         check(x.downcastUnchecked<Int>() == 1)
 *     }
 * }
 * ```
 *
 * # Safety
 *
 * The contained value must be of type `T`. Calling this method
 * with the incorrect type is *undefined behavior*.
 *
 * [downcast]: Self.downcast
 */
public fun <A : Allocator, T> Arc<RustAny, A>.downcastUnchecked(): Arc<T, A> {
    // SAFETY: Upheld by caller.
    return run {
        val (ptr, alloc) = Arc.intoInnerWithAllocator(this)
        Arc.fromInnerIn(ptr.cast(), alloc)
    }
}

/**
 * Constructs a new `Weak<T>`, without allocating any memory.
 * Calling [upgrade] on the return value always gives [None].
 *
 * [upgrade]: Weak.upgrade
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Weak
 *
 * val empty: Weak<Long> = Weak.new()
 * check(empty.upgrade() == null)
 * ```
 */
public fun <T> Weak.Companion.new(): Weak<T, Global> = Weak(ptr = NonNull.withoutProvenance(NonZeroUsize.MAX), alloc = Global())

/**
 * Helper type to allow accessing the reference counts without
 * making any assertions about the data field.
 */
public open class WeakInner internal constructor(
    weakCtorArg: Atomic<Int, Align8<Int>>,
    strongCtorArg: Atomic<Int, Align8<Int>>,
) {
    public var weak: Atomic<Int, Align8<Int>> = weakCtorArg
        internal set

    public var strong: Atomic<Int, Align8<Int>> = strongCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            weak: Atomic<Int, Align8<Int>>,
            strong: Atomic<Int, Align8<Int>>,
        ): WeakInner = WeakInner(
            weakCtorArg = weak,
            strongCtorArg = strong,
        )
    }
}

/**
 * Converts a raw pointer previously created by [intoRaw] back into `Weak<T>`.
 *
 * This can be used to safely get a strong reference (by calling [upgrade]
 * later) or to deallocate the weak count by dropping the `Weak<T>`.
 *
 * It takes ownership of one weak reference (with the exception of pointers created by [new],
 * as these don't own anything; the method still works on them).
 *
 * # Safety
 *
 * The pointer must have originated from the [intoRaw] and must still own its potential
 * weak reference, and must point to a block of memory allocated by global allocator.
 *
 * It is allowed for the strong count to be 0 at the time of calling this. Nevertheless, this
 * takes ownership of one weak reference currently represented as a raw pointer (the weak
 * count is not modified by this operation) and therefore it must be paired with a previous
 * call to [intoRaw].
 * # Examples
 *
 * ```kotlin
 * import std.sync.Weak
 *
 * val strong = "hello".toOwned()
 * val raw1 = Arc.downgrade(strong).intoRaw()
 * val raw2 = Arc.downgrade(strong).intoRaw()
 * check(2 == Arc.weakCount(strong))
 * check("hello" == Weak.fromRaw(raw1).upgrade().unwrap())
 * check(1 == Arc.weakCount(strong))
 * (strong).let { }
 * // Decrement the last weak count.
 * check(Weak.fromRaw(raw2).upgrade() == null)
 * ```
 *
 * [new]: Weak.new
 * [intoRaw]: Weak.intoRaw
 * [upgrade]: Weak.upgrade
 */
public fun <T> Weak.Companion.fromRaw(ptr: ConstPtr): Weak<T, Global> {
    // SAFETY: Upheld by caller.
    return Weak.fromRawIn(ptr, Global())
}

/**
 * Consumes the `Weak<T>` and turns it into a raw pointer.
 *
 * This converts the weak pointer into a raw pointer, while still preserving the ownership of
 * one weak reference (the weak count is not modified by this operation). It can be turned
 * back into the `Weak<T>` with [fromRaw].
 *
 * The same restrictions of accessing the target of the pointer as with
 * [asPtr] apply.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Weak
 *
 * val strong = "hello".toOwned()
 * val weak = Arc.downgrade(strong)
 * val raw = weak.intoRaw()
 * check(1 == Arc.weakCount(strong))
 * check("hello" == raw)
 * (Weak.fromRaw(raw)).let { }
 * check(0 == Arc.weakCount(strong))
 * ```
 *
 * [fromRaw]: Weak.fromRaw
 * [asPtr]: Weak.asPtr
 */
public fun <T> Weak<T, Global>.intoRaw(): ConstPtr = this.asPtr()

/**
 * Constructs a new `Weak<T>`, without allocating memory.
 * Calling [upgrade] on the return value always
 * gives [None].
 *
 * [upgrade]: Weak.upgrade
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Weak
 *
 * val empty: Weak<Long> = Default.default()
 * check(empty.upgrade() == null)
 * ```
 */
public fun <T> defaultWeakT(): Weak<T, Global> = Weak.new()

public interface ArcEqIdent<T, A : Allocator> {
    public fun eq(other: Arc<T, A>): Boolean

    public fun ne(other: Arc<T, A>): Boolean
}

/**
 * We're doing this specialization here, and not as a more general optimization on `T`, because it
 * would otherwise add a cost to all equality checks on refs. We assume that `Arc`s are used to
 * store large values, that are slow to clone, but also heavy to check for equality, causing this
 * cost to pay off more easily. It's also more likely to have two `Arc` clones, that point to
 * the same value, than two `T`s.
 *
 * We can only do this when `T: Eq` as a `PartialEq` might be deliberately irreflexive.
 */
public fun <T : MarkerEq, A : Allocator> Arc<T, A>.eq(other: Arc<T, A>): Boolean = io.github.kotlinmania.rustlang.core.ptr.eq((this.ptr.asPtr()).asConstPtr(), (other.ptr.asPtr()).asConstPtr()) || this == other

public fun <T : MarkerEq, A : Allocator> Arc<T, A>.ne(other: Arc<T, A>): Boolean = !eq((this.ptr.asPtr()).asConstPtr(), (other.ptr.asPtr()).asConstPtr()) && this != other

/**
 * Creates a new `Arc<T>`, with the `Default` value for `T`.
 *
 * # Examples
 *
 * ```kotlin
 * val x: Int = 0
 * check(x == 0)
 * ```
 */
public fun <T> defaultArcT(defaultTFn: () -> T): Arc<T, Global> {
    // ignore-tidy-undocumented-unchecked
    return Arc.fromInner(Box.intoNonNull(Box.write(Box.newUninit(), ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = defaultTFn()))))
}

/**
 * Struct to hold the static `ArcInner` used for empty `Arc<String / CStr / [T]>` as
 * returned by `Default.default`.
 *
 * Layout notes:
 * * `repr(align(16))` so we can use it for `[T]` with `alignOf<T>() <= 16`.
 * * `repr(C)` so `inner` is at offset 0 (and thus guaranteed to actually be aligned to 16).
 * * `[UByte; 1]` (to be initialized with 0) so it can be used for `Arc<CStr>`.
 */
public open class SliceArcInnerForStatic internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ArcInner<UByteArray>> = UnresolvedTypeLayout(),
) {
    public var inner: ArcInner<UByteArray>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: ArcInner<UByteArray>, fieldLayout: TypeLayout<ArcInner<UByteArray>> = UnresolvedTypeLayout()) : this(allocateNativeCell(inner, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<SliceArcInnerForStatic> = TransparentTypeLayout(fieldLayout, { ptr, layout -> SliceArcInnerForStatic(ptr, layout) }, { it.inner })

    public companion object {
        public operator fun invoke(inner: ArcInner<UByteArray>, fieldLayout: TypeLayout<ArcInner<UByteArray>> = UnresolvedTypeLayout()): SliceArcInnerForStatic = SliceArcInnerForStatic(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ArcInner<UByteArray>> = UnresolvedTypeLayout()): SliceArcInnerForStatic = SliceArcInnerForStatic(fieldStorage, fieldLayout)
    }
}

public val MAX_STATIC_INNER_SLICE_ALIGNMENT: Int = 16

public var STATIC_INNER_SLICE: SliceArcInnerForStatic = SliceArcInnerForStatic(inner = ArcInner(strong = AtomicUsize.new(1), weak = AtomicUsize.new(1), data = listOf(0)), fieldLayout = UnresolvedTypeLayout<ArcInner<UByteArray>>())

/**
 * Creates an empty str inside an Arc
 *
 * This may or may not share an allocation with other Arcs.
 */
public fun defaultArcString(): Arc<String, Global> {
    val arc: Arc<List<UByte>, Global> = Arc.default()
    check(String.fromUtf8(arc).isOk())
    val (ptr, alloc) = Arc.intoInnerWithAllocator(arc)
    // ignore-tidy-undocumented-unchecked
    return Arc.fromPtrIn(mutPtrFromRustCast(ptr.asPtr()), alloc)
}

/**
 * Creates an empty CStr inside an Arc
 *
 * This may or may not share an allocation with other Arcs.
 */
public fun defaultArcCStr(): Arc<CStr, Global> {
    val inner: NonNull = NonNull.from(STATIC_INNER_SLICE.inner)
    val inner1: NonNull = NonNull.new(mutPtrFromRustCast(inner.asPtr()))!!
    // `this` semantically is the Arc "owned" by the static, so make sure not to drop it.
    val thisRef: Arc<CStr, Global> = Arc.fromInner(inner1)
    return thisRef.clone()
}

/**
 * Creates an empty `[T]` inside an Arc
 *
 * This may or may not share an allocation with other Arcs.
 */
public fun <T> defaultArcTSlice(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Arc<List<T>, Global> {
    if (alignOf<T>(typeLayoutT = typeLayoutT) <= MAX_STATIC_INNER_SLICE_ALIGNMENT) {
        // We take a reference to the whole struct instead of the ArcInner<[u8; 1]> inside it so
        // we don't shrink the range of bytes the ptr is allowed to access under Stacked Borrows.
        // (Miri complains on 32-bit targets with Arc<[Align16]> otherwise.)
        // (Note that NonNull.from(&STATIC_INNER_SLICE.inner) is fine under Tree Borrows.)
        val inner: NonNull = NonNull.from(STATIC_INNER_SLICE)
        val inner1: NonNull = inner.cast()
        // `this` semantically is the Arc "owned" by the static, so make sure not to drop it.
        val thisRef: Arc<List<T>, Global> = Arc.fromInner(inner1)
        return thisRef.clone()
    }
    // If T's alignment is too large for the static, make a new unique allocation.
    val arr: List<T> = listOf()
    return Arc.from(arr)
}

public fun <T> defaultPinArcT(): Pin<Arc<T, Global>> {
    // SAFETY: We own and create the pinned pointer.
    return Pin.newUnchecked(Arc.default())
}

/**
 * Converts a `T` into an `Arc<T>`
 *
 * The conversion moves the value into a
 * newly allocated `Arc`. It is equivalent to
 * calling `Arc.new(t)`.
 *
 * # Example
 * ```kotlin
 * val x = 5
 * val arc = 5
 * check(Arc.from(x) == arc)
 * ```
 */
public fun <T> fromArcT(t: T): Arc<T, Global> = Arc.new(t)

/**
 * Converts a [`[T; N]`](prim@array) into an `Arc<[T]>`.
 *
 * The conversion moves the array into a newly allocated `Arc`.
 *
 * # Example
 *
 * ```kotlin
 * val original: IntArray = intArrayOf(1, 2, 3)
 * val shared: List<Int> = Arc.from(original)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromArcTSlice(v: List<T>, n: Int): Arc<List<T>, Global> = Arc.from(v, n)

/**
 * Allocates a reference-counted slice and fills it by cloning `v`s items.
 *
 * # Example
 *
 * ```kotlin
 * val original: List<Int> = listOf(1, 2, 3)
 * val shared: List<Int> = Arc.from(original)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromArcTSlice(v: List<T>): Arc<List<T>, Global> = Arc.fromSlice(v)

/**
 * Allocates a reference-counted slice and fills it by cloning `v`s items.
 *
 * # Example
 *
 * ```kotlin
 * var original = mutableListOf(1, 2, 3)
 * val original1: MutableList<Int> = original
 * val shared: List<Int> = Arc.from(original1)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromArcTSlice(v: MutableList<T>): Arc<List<T>, Global> = Arc.from(v)

/**
 * Allocates a reference-counted `String` and copies `v` into it.
 *
 * # Example
 *
 * ```kotlin
 * val shared: String = Arc.from("eggplant")
 * check("eggplant" == shared)
 * ```
 */
public fun fromArcString(v: String): Arc<String, Global> {
    val arc = Arc.from(v.encodeToByteArray().map { it.toUByte() })
    // ignore-tidy-undocumented-unchecked
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Allocates a reference-counted slice and moves `v`s items into it.
 *
 * # Example
 *
 * ```kotlin
 * val unique: List<Int> = mutableListOf(1, 2, 3)
 * val shared: List<Int> = Arc.from(unique)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T, A : AllocatorNightly> fromArcTSliceA(v: List<T>): Arc<List<T>, A> {
    // ignore-tidy-undocumented-unchecked
    return run {
        val (vecPtr, len, cap, alloc) = v.intoRawPartsWithAllocator()
        val rcPtr = Arc.allocateForSliceIn(len, alloc)
        copyNonoverlapping(vecPtr, mutPtrFromRustCast((addrOfMut(rcPtr.data))), len)
        // Create a `List<T, A>` with length 0, to deallocate the buffer
        // without dropping its contents or the allocator
        Vec.fromRawPartsIn(vecPtr, 0, cap, alloc)
        Arc.fromPtrIn(rcPtr, alloc)
    }
}

/**
 * Creates an atomically reference-counted pointer from a clone-on-write
 * pointer by copying its content.
 *
 * # Example
 *
 * ```kotlin
 * val cow: String = "eggplant"
 * val shared: String = Arc.from(cow)
 * check("eggplant" == shared)
 * ```
 */
public fun <Owned, B> fromArcB(cow: B): Arc<B, Global> where B : ToOwned<Owned> =
    when (cow) {
        is Cow.Borrowed<*, *> -> {
            val (s) = cow
            Arc.from(s)
        }
        is Cow.Owned<*, *> -> {
            val (s1) = cow
            Arc.from(s1)
        }
    }

/**
 * Converts an atomically reference-counted string slice into a byte slice.
 *
 * # Example
 *
 * ```kotlin
 * val string: String = Arc.from("eggplant")
 * val bytes: List<UByte> = Arc.from(string)
 * check("eggplant".encodeToByteArray().map { it.toUByte() } == bytes)
 * ```
 */
public fun fromArcUByteSlice(rc: Arc<String, Global>): Arc<List<UByte>, Global> {
    // SAFETY: `String` has the same layout as `[UByte]`.
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(rc)))
}

public fun <T, A : Allocator> tryFromArcTArraynA(boxedSlice: Arc<List<T>, A>, n: Int): Result<Arc<List<T>, A>, Arc<List<T>, A>> =
    if (boxedSlice.len() == n) {
        val (ptr, alloc) = Arc.intoInnerWithAllocator(boxedSlice)
        // ignore-tidy-undocumented-unchecked
        Result.Ok<Arc<List<T>, A>, Arc<List<T>, A>>(Arc.fromInnerIn(ptr.cast(), alloc))
    } else {
        Result.Err<Arc<List<T>, A>, Arc<List<T>, A>>(boxedSlice)
    }

/**
 * Takes each element in the `Iterator` and collects it into an `Arc<[T]>`.
 *
 * # Performance characteristics
 *
 * ## The general case
 *
 * In the general case, collecting into `Arc<[T]>` is done by first
 * collecting into a `List<T>`. That is, when writing the following:
 *
 * ```kotlin
 * val evens: List<UByte> = (0 until 10).filter({ x -> x % 2 == 0 }).toMutableList()
 * ```
 *
 * this behaves as if we wrote:
 *
 * ```kotlin
 * val evens: List<UByte> = (0 until 10).filter({ x -> x % 2 == 0 }).collect<Vec>().into()
 * // A second allocation for `Arc<[T]>` happens here.
 * ```
 *
 * This will allocate as many times as needed for constructing the `List<T>`
 * and then it will allocate once for turning the `List<T>` into the `Arc<[T]>`.
 *
 * ## Iterators of known length
 *
 * When your `Iterator` implements `TrustedLen` and is of an exact size,
 * a single allocation will be made for the `Arc<[T]>`. For example:
 *
 * ```kotlin
 * val evens: List<UByte> = (0 until 10).toMutableList()
 * // Just a single allocation happens here.
 * ```
 */
public fun <T, I : Iterable<T>> fromIteratorFromIterArcTSlice(iter: I): Arc<List<T>, Global> = iter.iterator().toArcSlice()

/**
 * Specialization trait used for collecting into `Arc<[T]>`.
 */
public interface ToArcSlice<T> : Iterator<T> {
    public fun toArcSlice(): Arc<List<T>, Global>

    public fun next(): T?
}

public fun <T, I : Iterator<T>> I.toArcSlice(): Arc<List<T>, Global> = this.toMutableList().into()

public fun <T, I : TrustedLen<T>> I.toArcSliceT(): Arc<List<T>, Global> {
    // This is the case for a `TrustedLen` iterator.
    val (low, high) = this.sizeHint()
    return if (high != null) {
        val high1 = high
        check(low == high1)
        // SAFETY: We need to ensure that the iterator has an exact length and we have.
        Arc.fromIterExact(this, low)
    } else {
        // TrustedLen contract guarantees that `upperBound == None` implies an iterator
        // length exceeding `Int.MAX`.
        // The default implementation would collect into a vec which would panic.
        // Thus we panic here immediately without invoking `List` code.
        error("capacity overflow")
    }
}

/**
 * Gets the offset within an `ArcInner` for the payload behind a pointer.
 *
 * # Safety
 *
 * The pointer must point to (and have valid metadata for) a previously
 * valid instance of T, but the T is allowed to be dropped.
 */
public fun dataOffset(ptr: ConstPtr): Int {
    // Align the unsized value to the end of the ArcInner.
    // Because ArcInner is repr(C), it will always be the last field in memory.
    // SAFETY: since the only unsized types possible are slices, trait objects,
    // and extern types, the input safety requirement is currently enough to
    // satisfy the requirements of Alignment.ofValRaw; this is an implementation
    // detail of the language that must not be relied upon outside of std.
    return dataOffsetAlignment(Alignment.ofValRaw(ptr))
}

public fun dataOffsetAlignment(alignment: Alignment): Int {
    val layout = Layout.new<ArcInner<Unit>>(typeLayoutT = UnresolvedTypeLayout<ArcInner<Unit>>())
    return layout.size() + layout.paddingNeededFor(alignment)
}

/**
 * A unique owning pointer to an [ArcInner] **that does not imply the contents are initialized,**
 * but will deallocate it (without dropping the value) when dropped.
 *
 * This is a helper for [Arc.makeMut] to ensure correct cleanup on panic.
 */
public open class UniqueArcUninit<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    public var layoutForValue: Layout,
    public var alloc: A?,
) {
    public var ptr: NonNull = ptrCtorArg
        internal set

    /**
     * Returns the pointer to be written into to initialize the [Arc].
     */
    public open fun dataPtr(): MutPtr {
        val offset = dataOffsetAlignment(this.layoutForValue.alignment())
        // ignore-tidy-undocumented-unchecked
        return mutPtrFromRustCast(this.ptr.asPtr().byteAdd(offset))
    }

    /**
     * Upgrade this into a normal [Arc].
     *
     * # Safety
     *
     * The data must have been initialized (by writing to [Self.dataPtr]).
     */
    public open fun intoArc(): Arc<T, A> {
        val thisRef = this
        val ptr = thisRef.ptr.asPtr()
        val alloc = (thisRef.alloc.also { thisRef.alloc = null })!!
        // SAFETY: The pointer is valid as per `UniqueArcUninit.new`, and the caller is responsible
        // for having initialized the data.
        return Arc.fromPtrIn(ptr, alloc)
    }

    public open fun drop() {
        // SAFETY:
        // * new() produced a pointer safe to deallocate.
        // * We own the pointer unless intoArc() was called, which forgets us.
        run {
            (this.alloc.also { this.alloc = null })!!.deallocate(this.ptr.cast(), arcinnerLayoutForValueLayout(this.layoutForValue))
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            layoutForValue: Layout,
            alloc: A?,
        ): UniqueArcUninit<T, A> = UniqueArcUninit<T, A>(
            ptrCtorArg = ptr,
            layoutForValue = layoutForValue,
            alloc = alloc,
        )

        /**
         * Allocates an ArcInner with layout suitable to contain `forValue` or a clone of it.
         */
        public fun <T, A : Allocator> new(forValue: T, alloc: A): UniqueArcUninit<T, A> {
            val layout = Layout.forValue(forValue)
            // ignore-tidy-undocumented-unchecked
            val ptr = Arc.allocateForLayout(layout, { layoutForArcinner -> alloc.allocate(layoutForArcinner) }, { mem -> mem.withMetadataOf(constPtrFromRustCast(forValue)) })
            return UniqueArcUninit(ptr = NonNull.new(ptr)!!, layoutForValue = layout, alloc = alloc)
        }

        /**
         * Allocates an ArcInner with layout suitable to contain `forValue` or a clone of it,
         * returning an error if allocation fails.
         */
        public fun <T, A : Allocator> tryNew(forValue: T, alloc: A): Result<UniqueArcUninit<T, A>, AllocError> {
            val layout = Layout.forValue(forValue)
            // ignore-tidy-undocumented-unchecked
            val ptr = when (val tryResult = Arc.tryAllocateForLayout(layout, { layoutForArcinner -> alloc.allocate(layoutForArcinner) }, { mem -> mem.withMetadataOf(constPtrFromRustCast(forValue)) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UniqueArcUninit<T, A>, AllocError>(tryResult.errValue()) }
            return Result.Ok<UniqueArcUninit<T, A>, AllocError>(UniqueArcUninit(ptr = NonNull.new(ptr)!!, layoutForValue = layout, alloc = alloc))
        }
    }
}

public fun <T> Arc<T, Global>.cause(): Any? = Error.cause(this)

public fun <T> Arc<T, Global>.source(): Any? = Error.source(this)

public fun <T> Arc<T, Global>.provide(req: Request) {
    Error.provide(this, req)
}

/**
 * A uniquely owned [Arc].
 *
 * This represents an `Arc` that is known to be uniquely owned -- that is, have exactly one strong
 * reference. Multiple weak pointers can be created, but attempts to upgrade those to strong
 * references will fail unless the `UniqueArc` they point to has been converted into a regular `Arc`.
 *
 * Because it is uniquely owned, the contents of a `UniqueArc` can be freely mutated. A common
 * use case is to have an object be mutable during its initialization phase but then have it become
 * immutable and converted to a normal `Arc`.
 *
 * This can be used as a flexible way to create cyclic data structures, as in the example below.
 *
 * ```kotlin
 * import std.sync.UniqueArc
 * import std.sync.Weak
 *
 * @feature(uniqueRcArc)
 * public fun doctestEntry() {
 *     open class Gadget(public var me: Weak<Gadget>)
 *     fun createGadget(): Gadget? {
 *         var rc = UniqueArc.new(Gadget(me = Weak.new()))
 *         rc.me = UniqueArc.downgrade(rc)
 *         return UniqueArc.intoArc(rc)
 *     }
 *     createGadget()!!
 * }
 * ```
 *
 * An advantage of using `UniqueArc` over [Arc.newCyclic] to build cyclic data structures is that
 * [Arc.newCyclic]s `dataFn` parameter cannot be async or return a [Result]. As shown in the
 * previous example, `UniqueArc` allows for more flexibility in the construction of cyclic data,
 * including fallible or async constructors.
 */
public open class UniqueArc<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    // Define the ownership of `ArcInner<T>` for drop-check
    public var marker: Unit,
    // Invariance is necessary for soundness: once other `Weak`
    // references exist, we already have a form of shared mutability!
    public var marker2: Unit,
    public var alloc: A,
) : BorrowMut<T>, AsRef<T>, AsMut<T>, PinSafePointer, DerefPure {
    public var ptr: NonNull = ptrCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Error> = run { f.append(((this)).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun borrow(): T = this

    public open fun borrowMut(): T = this

    public open fun asRef(): T = this

    public open fun asMut(): T = this

    /**
     * Equality for two `UniqueArc`s.
     *
     * Two `UniqueArc`s are equal if their inner values are equal.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(five == UniqueArc.new(5))
     * }
     * ```
     */
    public open fun eq(other: UniqueArc<T, A>): Boolean = PartialEq.eq(this, other)

    /**
     * Partial comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `partialCmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(Ordering.Less == five.partialCmp(UniqueArc.new(6)))
     * }
     * ```
     */
    public open fun partialCmp(other: UniqueArc<T, A>): Ordering? = this.partialCmp(other)

    /**
     * Less-than comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `<` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(five < UniqueArc.new(6))
     * }
     * ```
     */
    public open fun lt(other: UniqueArc<T, A>): Boolean = this < other

    /**
     * Less than or equal to' comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `<=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(five <= UniqueArc.new(5))
     * }
     * ```
     */
    public open fun le(other: UniqueArc<T, A>): Boolean = this <= other

    /**
     * Greater-than comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `>` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(five > UniqueArc.new(4))
     * }
     * ```
     */
    public open fun gt(other: UniqueArc<T, A>): Boolean = this > other

    /**
     * Greater than or equal to' comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `>=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(five >= UniqueArc.new(5))
     * }
     * ```
     */
    public open fun ge(other: UniqueArc<T, A>): Boolean = this >= other

    /**
     * Comparison for two `UniqueArc`s.
     *
     * The two are compared by calling `cmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     * import std.sync.UniqueArc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueArc.new(5)
     *     check(Ordering.Less == five.cmp(UniqueArc.new(6)))
     * }
     * ```
     */
    public open fun cmp(other: UniqueArc<T, A>): Ordering = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun inner(): ArcInner<T> {
        // SAFETY: while this UniqueArc is alive we're guaranteed that the inner pointer is valid.
        return this.ptr.asRef()
    }

    public open fun deref(): T {
        // SAFETY: This pointer was allocated at creation time so we know it is valid.
        return this.ptr.asRef().data
    }

    // @unstable(feature = "uniqueRcArc", issue = "112566")

    public open fun derefMut(): T {
        // SAFETY: This pointer was allocated at creation time so we know it is valid. We know we
        // have unique ownership and therefore it's safe to make a mutable reference because
        // `UniqueArc` owns the only strong reference to itself.
        // We also need to be careful to only create a mutable reference to the `data` field,
        // as a mutable reference to the entire `ArcInner` would assert uniqueness over the
        // ref count fields too, invalidating any attempt by `Weak`s to access the ref count.
        return this.ptr.asPtr().data
    }

    // @unstable(feature = "derefPureTrait", issue = "87121")

    public open fun drop() {
        // See `Arc.dropSlow` which drops an `Arc` with a strong count of 0.
        // SAFETY: This pointer was allocated at creation time so we know it is valid.
        // ignore-tidy-undocumented-unchecked
        dropInPlace(this.ptr.asPtr().data)
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            marker: Unit,
            marker2: Unit,
            alloc: A,
        ): UniqueArc<T, A> = UniqueArc<T, A>(
            ptrCtorArg = ptr,
            marker = marker,
            marker2 = marker2,
            alloc = alloc,
        )

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Creates a new `UniqueArc` in the provided allocator.
         *
         * Weak references to this `UniqueArc` can be created with [UniqueArc.downgrade]. Upgrading
         * these weak references will fail before the `UniqueArc` has been converted into an [Arc].
         * After converting the `UniqueArc` into an [Arc], any weak references created beforehand will
         * point to the new [Arc].
         */
        public fun <T, A : Allocator> newIn(data: T, alloc: A): UniqueArc<T, A> {
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(Box.newIn(ArcInner(strong = AtomicUsize.new(0), weak = AtomicUsize.new(1), data = data), alloc))
            return UniqueArc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc1)
        }

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Like [`newIn`](Self.newIn), but returns an error if the allocation
         * fails, instead of calling [handleAllocError].
         */
        public fun <T, A : Allocator> tryNewIn(data: T, alloc: A): Result<UniqueArc<T, A>, AllocError> {
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(when (val tryResult = Box.tryNewIn(ArcInner(strong = AtomicUsize.new(0), weak = AtomicUsize.new(1), data = data), alloc)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UniqueArc<T, A>, AllocError>(tryResult.errValue()) })
            return Result.Ok<UniqueArc<T, A>, AllocError>(UniqueArc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc1))
        }

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Consumes the `UniqueArc`, returning its wrapped value and allocator.
         */
        public fun <T, A : Allocator> unwrapWithAllocator(thisRef: UniqueArc<T, A>): Pair<T, A> {
            val innerPtr = thisRef.ptr
            val (dataPtr, alloc) = UniqueArc.intoRawWithAllocator(thisRef)
            // SAFETY: Conceptually moves out of the `UniqueRc`.
            // We do not use the data inside ever again.
            val value = dataPtr.read()
            // Drop the strong-weak ref
            (Weak(ptr = innerPtr, alloc = alloc)).let { }
            return Pair(value, alloc)
        }

        /**
         * Consumes the `UniqueArc`, returning its wrapped value.
         */
        public fun <T, A : Allocator> unwrap(thisRef: UniqueArc<T, A>): T = UniqueArc.unwrapWithAllocator(thisRef).first

        /**
         * Maps the value in a `UniqueArc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `UniqueArc`, and the result is returned,
         * also in a `UniqueArc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `UniqueArc.map(u, f)` instead of `u.map(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.UniqueArc
         *
         * @feature(uniqueRcArc)
         * public fun doctestEntry() {
         *     val r = UniqueArc.new(7)
         *     val new = UniqueArc.map(r, { i -> i + 7 })
         *     check(new == 14)
         * }
         * ```
         */
        public fun <T, A : Allocator, U> map(thisRef: UniqueArc<T, A>, f: (T) -> U, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), typeLayoutU: TypeLayout<U> = UnresolvedTypeLayout()): UniqueArc<U, A> =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU) && UniqueArc.weakCount(thisRef) == 0) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = UniqueArc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = UniqueArc.fromRawWithAllocator(ptr.cast(), alloc)
                    UniqueArc.write(allocation, f(value))
                }
            } else {
                val (value, alloc1) = UniqueArc.unwrapWithAllocator(thisRef)
                UniqueArc.newIn(f(value), alloc1)
            }

        /**
         * Attempts to map the value in a `UniqueArc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `UniqueArc`, and if the operation succeeds,
         * the result is returned, also in a `UniqueArc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `UniqueArc.tryMap(u, f)` instead of `u.tryMap(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.UniqueArc
         *
         * @feature(smartPointerTryMap)
         * @feature(uniqueRcArc)
         * public fun doctestEntry() {
         *     val b = UniqueArc.new(7)
         *     val new = UniqueArc.tryMap(b, UInt.tryFrom).unwrap()
         *     check(new == 7)
         * }
         * ```
         */
        public fun <Output, Residual, T, A : Allocator, R> tryMap(thisRef: UniqueArc<T, A>, f: (T) -> R, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): TryType where R : Try<Output, Residual> {
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>()) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<Output>(typeLayoutT = UnresolvedTypeLayout<Output>()) && UniqueArc.weakCount(thisRef) == 0) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = UniqueArc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = UniqueArc.fromRawWithAllocator(ptr.cast(), alloc)
                    UniqueArc.write(allocation, when (val tryResult = f(value)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
                }
            } else {
                val (value, alloc1) = UniqueArc.unwrapWithAllocator(thisRef)
                UniqueArc.newIn(when (val tryResult = f(value)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }, alloc1)
            }
        }

        public fun <T, A : Allocator> fromRawWithAllocator(ptr: ConstPtr, alloc: A): UniqueArc<T, A> {
            // SAFETY: Upheld by caller.
            val offset = dataOffset(ptr)
            // Reverse the offset to find the original ArcInner.
            // SAFETY: Upheld by caller.
            val rcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            return UniqueArc(ptr = NonNull.newUnchecked(rcPtr), marker = Unit, marker2 = Unit, alloc = alloc)
        }

        public fun <T, A : Allocator> intoRawWithAllocator(thisRef: UniqueArc<T, A>): Pair<ConstPtr, A> {
            val thisRef1 = thisRef
            // SAFETY: The copy of the allocator stored in `this` is forgotten
            return Pair(UniqueArc.asPtr(thisRef1), read(thisRef1.alloc))
        }

        /**
         * Converts the `UniqueArc` into a regular [Arc].
         *
         * This consumes the `UniqueArc` and returns a regular [Arc] that contains the `value` that
         * is passed to `intoArc`.
         *
         * Any weak references created before this method is called can now be upgraded to strong
         * references.
         */
        public fun <T, A : Allocator> intoArc(thisRef: UniqueArc<T, A>): Arc<T, A> {
            val thisRef1 = thisRef
            // Move the allocator out.
            // SAFETY: `this.alloc` will not be accessed again, nor dropped because it is in
            // a `ManuallyDrop`.
            val alloc: A = read(thisRef1.alloc)
            // SAFETY: This pointer was allocated at creation time so we know it is valid.
            return run {
                // Convert our weak reference into a strong reference
                thisRef1.ptr.asPtr().strong.store(1, Release)
                Arc.fromInnerIn(thisRef1.ptr, alloc)
            }
        }

        public fun <T, A : Allocator> weakCount(thisRef: UniqueArc<T, A>): Int = thisRef.inner().weak.load(Acquire) - 1

        public fun <T, A : Allocator> asPtr(thisRef: UniqueArc<T, A>): ConstPtr {
            val ptr: MutPtr = NonNull.asPtr(thisRef.ptr)
            // SAFETY: This cannot go through Deref.deref or UniqueArc.inner because
            // this is required to retain raw/mut provenance such that e.g. `getMut` can
            // write through the pointer after the Rc is recovered through `fromRaw`.
            return addrOfMut((UnresolvedTypeLayout<ArcInner<T>>()).readNative((ptr).rawAddress).data)
        }

        public fun <T, A : Allocator> intoInnerWithAllocator(thisRef: UniqueArc<T, A>): Pair<NonNull, A> {
            val thisRef1 = thisRef
            // SAFETY: Pointer is valid for reads and only read once.
            return Pair(thisRef1.ptr, read(thisRef1.alloc))
        }

        public fun <T, A : Allocator> fromInnerIn(ptr: NonNull, alloc: A): UniqueArc<T, A> = UniqueArc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc)

        /**
         * Creates a new weak reference to the `UniqueArc`.
         *
         * Attempting to upgrade this weak reference will fail before the `UniqueArc` has been converted
         * to a [Arc] using [UniqueArc.intoArc].
         */
        public fun <T, A : Allocator> downgrade(thisRef: UniqueArc<T, A>): Weak<T, A> {
            // Using a relaxed ordering is alright here, as knowledge of the
            // original reference prevents other threads from erroneously deleting
            // the object or converting the object to a normal `Arc<T, A>`.
            //
            // Note that we don't need to test if the weak counter is locked because there
            // are no such operations like `Arc.getMut` or `Arc.makeMut` that will lock
            // the weak counter.
            //
            // SAFETY: This pointer was allocated at creation time so we know it is valid.
            val oldSize = thisRef.ptr.asPtr().weak.fetchAdd(1, Relaxed)
            // See comments in Arc.clone() for why we do this (for mem.forget).
            if (oldSize > MAX_REFCOUNT) {
                kmAbort()
            }
            return Weak(ptr = thisRef.ptr, alloc = thisRef.alloc.clone())
        }
    }
}

// @unstable(feature = "coerceUnsized", issue = "18598")

// @unstable(feature = "uniqueRcArc", issue = "112566")

public fun <T> fromUniqueArcT(value: T): UniqueArc<T, Global> = UniqueArc.new(value)

/**
 * Creates a new `UniqueArc`.
 *
 * Weak references to this `UniqueArc` can be created with [UniqueArc.downgrade]. Upgrading
 * these weak references will fail before the `UniqueArc` has been converted into an [Arc].
 * After converting the `UniqueArc` into an [Arc], any weak references created beforehand will
 * point to the new [Arc].
 */
public fun <T> UniqueArc.Companion.new(value: T): UniqueArc<T, Global> = UniqueArc.newIn(value, Global())

/**
 * Like [`new`](Self.new), but returns an error if the allocation
 * fails, instead of calling [handleAllocError].
 */
public fun <T> UniqueArc.Companion.tryNew(value: T): Result<UniqueArc<T, Global>, AllocError> = UniqueArc.tryNewIn(value, Global())

/**
 * Writes the value and converts to `UniqueArc<T, A>`.
 *
 * This method converts similarly to [`assumeInit`](Self.assumeInit) but
 * writes `value` into it before conversion, thus guaranteeing safety.
 */
public fun <T, A : Allocator> UniqueArc.Companion.write(thisRef: UniqueArc<MaybeUninit<T>, A>, value: T): UniqueArc<T, A> {
    // SAFETY: Writing initialises the wrapped value.
    return run {
        thisRef.write(value)
        thisRef.assumeInit()
    }
}

/**
 * Converts to `UniqueArc<T, A>`.
 *
 * # Safety
 *
 * As with [MaybeUninit.assumeInit],
 * it is up to the caller to guarantee that the value
 * really is in an initialized state.
 * Calling this when the content is not yet fully initialized
 * causes immediate undefined behavior.
 *
 * [MaybeUninit.assumeInit]: mem.MaybeUninit.assumeInit
 */
public fun <T, A : Allocator> UniqueArc<MaybeUninit<T>, A>.assumeInit(): UniqueArc<T, A> {
    val (ptr, alloc) = UniqueArc.intoInnerWithAllocator(this)
    // SAFETY: Upheld by caller.
    return UniqueArc.fromInnerIn(ptr.cast(), alloc)
}

public fun <T : Allocator, A : Allocator> Arc<T, A>.allocate(layout: Layout): Result<NonNull, AllocError> = this.allocate(layout)

public fun <T : Allocator, A : Allocator> Arc<T, A>.allocateZeroed(layout: Layout): Result<NonNull, AllocError> = this.allocateZeroed(layout)

public fun <T : Allocator, A : Allocator> Arc<T, A>.deallocate(ptr: NonNull, layout: Layout) {
    // SAFETY: the safety contract must be upheld by the caller
    this.deallocate(ptr, layout)
}

public fun <T : Allocator, A : Allocator> Arc<T, A>.grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.grow(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Arc<T, A>.growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.growZeroed(ptr, oldLayout, newLayout)
}

public fun <T : Allocator, A : Allocator> Arc<T, A>.shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
    // SAFETY: the safety contract must be upheld by the caller
    return this.shrink(ptr, oldLayout, newLayout)
}

/**
 * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
 */
