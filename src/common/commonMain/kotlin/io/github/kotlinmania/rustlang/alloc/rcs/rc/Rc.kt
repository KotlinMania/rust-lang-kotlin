// port-lint: source rcs/rc.rs
package io.github.kotlinmania.rustlang.alloc.rcs.rc

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.alloc.handleAllocError
import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.borrow.ToOwned
import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.string.String
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.AllocatorClone
import io.github.kotlinmania.rustlang.core.alloc.AllocatorNightly
import io.github.kotlinmania.rustlang.core.alloc.StaticAllocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.borrow.BorrowMut
import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.clone.Share
import io.github.kotlinmania.rustlang.core.clone.UseCloned
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.Pointer
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.alignment.Alignment
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroUsize
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.pin.PinSafePointer
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.addrEq
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList

// Single-threaded reference-counting pointers. 'Rc' stands for Reference
// Counted'.
//
// The type [`Rc.T`][Rc] provides shared ownership of a value of type `T`,
// allocated in the heap. Invoking [`clone`][clone] on [Rc] produces a new
// pointer to the same allocation in the heap. When the last [Rc] pointer to a
// given allocation is destroyed, the value stored in that allocation (often
// referred to as "inner value") is also dropped.
//
// Shared references in Rust disallow mutation by default, and [Rc]
// is no exception: you cannot generally obtain a mutable reference to
// something inside an [Rc]. If you need mutability, put a [Cell]
// or [RefCell] inside the [Rc]; see [an example of mutability
// inside an `Rc`][mutability].
//
// [Rc] uses non-atomic reference counting. This means that overhead is very
// low, but an [Rc] cannot be sent between threads, and consequently [Rc]
// does not implement [Send]. As a result, the Rust compiler
// will check *at compile time* that you are not sending [Rc]s between
// threads. If you need multi-threaded, atomic reference counting, use
// [`sync.Arc`][arc].
//
// The [`downgrade`][downgrade] method can be used to create a non-owning
// [Weak] pointer. A [Weak] pointer can be [`upgrade`][upgrade]d
// to an [Rc], but this will return [None] if the value stored in the allocation has
// already been dropped. In other words, `Weak` pointers do not keep the value
// inside the allocation alive; however, they *do* keep the allocation
// (the backing store for the inner value) alive.
//
// A cycle between [Rc] pointers will never be deallocated. For this reason,
// [Weak] is used to break cycles. For example, a tree could have strong
// [Rc] pointers from parent nodes to children, and [Weak] pointers from
// children back to their parents.
//
// `Rc<T>` automatically dereferences to `T` (via the [Deref] trait),
// so you can call `T`s methods on a value of type [`Rc.T`][Rc]. To avoid name
// clashes with `T`s methods, the methods of [`Rc.T`][Rc] itself are associated
// functions, called using [fully qualified syntax]:
//
// ```kotlin
// val myRc = Rc.new()
// val myWeak = Rc.downgrade(myRc)
// ```
//
// `Rc<T>`s implementations of traits like `Clone` may also be called using
// fully qualified syntax. Some people prefer to use fully qualified syntax,
// while others prefer using method-call syntax.
//
// ```kotlin
// val rc = Rc.new()
// // Method-call syntax
// val rc2 = rc.clone()
// // Fully qualified syntax
// val rc3 = rc
// ```
//
// [`Weak.T`][Weak] does not auto-dereference to `T`, because the inner value may have
// already been dropped.
//
// # Cloning references
//
// Creating a new reference to the same allocation as an existing reference counted pointer
// is done using the `Clone` trait implemented for [`Rc.T`][Rc] and [`Weak.T`][Weak].
//
// ```kotlin
// val foo = mutableListOf(1.0, 2.0, 3.0)
// // The two syntaxes below are equivalent.
// val a = foo.toMutableList()
// val b = foo
// // a and b both point to the same memory location as foo.
// ```
//
// The `Rc.clone(from)` syntax is the most idiomatic because it conveys more explicitly
// the meaning of the code. In the example above, this syntax makes it easier to see that
// this code is creating a new reference rather than copying the whole content of foo.
//
// # Examples
//
// Consider a scenario where a set of `Gadget`s are owned by a given `Owner`.
// We want to have our `Gadget`s point to their `Owner`. We can't do this with
// unique ownership, because more than one gadget may belong to the same
// `Owner`. [Rc] allows us to share an `Owner` between multiple `Gadget`s,
// and have the `Owner` remain allocated as long as any `Gadget` points at it.
//
// ```kotlin
// open class Owner(public var name: String)
// open class Gadget(public var id: Int, public var owner: Owner)
// fun main() {
//     // Create a reference-counted `Owner`.
//     val gadgetOwner: Owner = Owner(name = "Gadget Man".toString())
//     // Create `Gadget`s belonging to `gadgetOwner`. Cloning the `Rc<Owner>`
//     // gives us a new pointer to the same `Owner` allocation, incrementing
//     // the reference count in the process.
//     val gadget1 = Gadget(id = 1, owner = gadgetOwner)
//     val gadget2 = Gadget(id = 2, owner = gadgetOwner)
//     // Dispose of our local variable `gadgetOwner`.
//     (gadgetOwner).let { }
//     // Despite dropping `gadgetOwner`, we're still able to print out the name
//     // of the `Owner` of the `Gadget`s. This is because we've only dropped a
//     // single `Rc<Owner>`, not the `Owner` it points to. As long as there are
//     // other `Rc<Owner>` pointing at the same `Owner` allocation, it will remain
//     // live. The field projection `gadget1.owner.name` works because
//     // `Rc<Owner>` automatically dereferences to `Owner`.
//     println("Gadget ${gadget1.id} owned by ${gadget1.owner.name}")
//     println("Gadget ${gadget2.id} owned by ${gadget2.owner.name}")
//     // At the end of the function, `gadget1` and `gadget2` are destroyed, and
//     // with them the last counted references to our `Owner`. Gadget Man now
//     // gets destroyed as well.
// }
// ```
//
// If our requirements change, and we also need to be able to traverse from
// `Owner` to `Gadget`, we will run into problems. An [Rc] pointer from `Owner`
// to `Gadget` introduces a cycle. This means that their
// reference counts can never reach 0, and the allocation will never be destroyed:
// a memory leak. In order to get around this, we can use [Weak]
// pointers.
//
// Rust actually makes it somewhat difficult to produce this loop in the first
// place. In order to end up with two values that point at each other, one of
// them needs to be mutable. This is difficult because [Rc] enforces
// memory safety by only giving out shared references to the value it wraps,
// and these don't allow direct mutation. We need to wrap the part of the
// value we wish to mutate in a [RefCell], which provides *interior
// mutability*: a method to achieve mutability through a shared reference.
// [RefCell] enforces Rust's borrowing rules at runtime.
//
// ```kotlin
// import std.cell.RefCell
// import std.rc.Weak
//
// open class Owner(public var name: String, public var gadgets: RefCell<List<Weak<Gadget>>>)
// open class Gadget(public var id: Int, public var owner: Owner)
// fun main() {
//     // Create a reference-counted `Owner`. Note that we've put the `Owner`s
//     // vector of `Gadget`s inside a `RefCell` so that we can mutate it through
//     // a shared reference.
//     val gadgetOwner: Owner = Owner(name = "Gadget Man".toString(), gadgets = RefCell.new(mutableListOf()))
//     // Create `Gadget`s belonging to `gadgetOwner`, as before.
//     val gadget1 = Gadget(id = 1, owner = gadgetOwner)
//     val gadget2 = Gadget(id = 2, owner = gadgetOwner)
//     // Add the `Gadget`s to their `Owner`.
//     run {
//         gadgetOwner.gadgets.borrowMut().use { gadgets ->
//             gadgets.push(Rc.downgrade(gadget1))
//             gadgets.push(Rc.downgrade(gadget2))
//         }
//         // `RefCell` dynamic borrow ends here.
//     }
//     // Iterate over our `Gadget`s, printing their details out.
//     for (gadgetWeak in gadgetOwner.gadgets.borrow().iter()) {
//         // `gadgetWeak` is a `Weak<Gadget>`. Since `Weak` pointers can't
//         // guarantee the allocation still exists, we need to call
//         // `upgrade`, which returns an `Option<Rc<Gadget>>`.
//         //
//         // In this case we know the allocation still exists, so we simply
//         // `unwrap` the `Option`. In a more complicated program, you might
//         // need graceful error handling for a `None` result.
//         val gadget = gadgetWeak.upgrade().unwrap()
//         println("Gadget ${gadget.id} owned by ${gadget.owner.name}")
//     }
//     // At the end of the function, `gadgetOwner`, `gadget1`, and `gadget2`
//     // are destroyed. There are now no strong (`Rc`) pointers to the
//     // gadgets, so they are destroyed. This zeroes the reference count on
//     // Gadget Man, so he gets destroyed as well.
// }
// ```
//
// [clone]: Clone.clone
// [downgrade]: Rc.downgrade
// [upgrade]: Weak.upgrade
// [fully qualified syntax]: https://doc.rust-lang.org/book/ch19-03-advanced-traits.html#fully-qualified-syntax-for-disambiguation-calling-methods-with-the-same-name

// This is repr(C) to future-proof against possible field-reordering, which
// would interfere with otherwise safe [into|from]_raw() of transmutable
// inner types.
// repr(align(2)) (forcing alignment to at least 2) is required because Int
// has 1-byte alignment on AVR.
public open class RcInner<T> internal constructor(
    strongCtorArg: Cell<Int>,
    weakCtorArg: Cell<Int>,
    public var value: T,
) : RcInnerPtr {
    public var strong: Cell<Int> = strongCtorArg
        internal set

    public var weak: Cell<Int> = weakCtorArg
        internal set

    public override fun weakRef(): Cell<Int> = this.weak

    public override fun strongRef(): Cell<Int> = this.strong

    public companion object {
        public operator fun <T> invoke(
            strong: Cell<Int>,
            weak: Cell<Int>,
            value: T,
        ): RcInner<T> = RcInner<T>(
            strongCtorArg = strong,
            weakCtorArg = weak,
            value = value,
        )
    }
}

/**
 * Calculate layout for `RcInner<T>` using the inner value's layout
 */
public fun rcInnerLayoutForValueLayout(layout: Layout): Layout {
    // Calculate layout using the given value layout.
    // Previously, layout was calculated on the expression
    // `* (ptr as * const RcInner<T>)`, but this created a misaligned
    // reference (see #54908).
    return Layout.new<RcInner<Unit>>(typeLayoutT = ObjectSlotLayout<RcInner<Unit>>()).extend(layout).unwrapOrElse({ error("capacity overflow") }).first.padToAlign()
}

// Note: When stabilizing this parameter, the `AllocatorNightly`
// bound on the `From<Holder>` impl *must* be weaked to `Allocator`,
// as `Holder` is fundamental!

/**
 * A single-threaded reference-counting pointer. 'Rc' stands for Reference
 * Counted'.
 *
 * See the [module-level documentation](./index.html) for more details.
 *
 * The inherent methods of `Rc` are all associated functions, which means
 * that you have to call them as e.g., [`Rc.getMut`][getMut] instead of
 * `value.getMut()`. This avoids conflicts with methods of the inner type `T`.
 *
 * [getMut]: Rc.getMut
 */
public open class Rc<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    public var phantom: Unit,
    public var alloc: A,
) : PinSafePointer, DerefPure, UseCloned, Share, RcEqIdent<T, A>, Pointer, AsRef<T>, Allocator, AllocatorNightly, AllocatorClone {
    public var ptr: NonNull = ptrCtorArg
        internal set

    // Note that this negative impl isn't strictly necessary for correctness,
    // as `Rc` transitively contains a `Cell`, which is itself `!Sync`.
    // However, given how important `Rc`s `!Sync`-ness is,
    // having an explicit negative impl is nice for documentation purposes
    // and results in nicer error messages.

    public open fun inner(): RcInner<T> {
        // SAFETY: While this Rc is alive we're guaranteed
        // that the inner pointer is valid.
        return this.ptr.asRef()
    }

    // Non-inlined part of `drop`.
    public open fun dropSlow() {
        // Reconstruct the "strong weak" pointer and drop it when this
        // variable goes out of scope. This ensures that the memory is
        // deallocated even if the destructor of `T` panics.
        // Destroy the contained object.
        // We cannot use `getMutUnchecked` here, because `self.alloc` is borrowed.
        // SAFETY: `self.ptr` is *not* borrowed.
        run {
            dropInPlace(this.ptr.asPtr().value)
        }
    }

    public open fun deref(): T = this.inner().value

    // The API of this pointer type enforces that if the `T` is pinned, then *all*
    // clones of this `Rc<T>` are wrapped as `Pin<Rc<T>>`. Since an `Rc<T>` could
    // be used to obtain an `Rc<T>` that is not wrapped in `Pin` (and later used
    // with `Rc.getMut`), this means that this type treats `Rc<T>` as evidence
    // that the `T` is not pinned. The implementations of various traits are written
    // accordingly. Since this type is not fundamental, downstream crates cannot
    // provide malicious implementations of any of the traits relevant for `Pin`.

    /**
     * Drops the `Rc`.
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
        // ignore-tidy-undocumented-unchecked
        run {
            this.inner().decStrong()
            if (this.inner().strong() == 0) {
                this.dropSlow()
            }
        }
    }

    /**
     * Makes a clone of the `Rc` pointer.
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
    public open fun clone(): Rc<T, A> {
        // ignore-tidy-undocumented-unchecked
        return run {
            this.inner().incStrong()
            Rc.fromInnerIn(this.ptr, this.alloc.clone())
        }
    }

    public override fun eq(other: Rc<T, A>): Boolean = this == other

    public override fun ne(other: Rc<T, A>): Boolean = this != other

    /**
     * We're doing this specialization here, and not as a more general optimization on `T`, because it
     * would otherwise add a cost to all equality checks on refs. We assume that `Rc`s are used to
     * store large values, that are slow to clone, but also heavy to check for equality, causing this
     * cost to pay off more easily. It's also more likely to have two `Rc` clones, that point to
     * the same value, than two `T`s.
     *
     * We can only do this when `T: Eq` as a `PartialEq` might be deliberately irreflexive.
     */
    public open fun eqTA(other: Rc<T, A>): Boolean = io.github.kotlinmania.rustlang.core.ptr.eq(this.ptr.asPtr(), other.ptr.asPtr()) || this == other

    public open fun neTA(other: Rc<T, A>): Boolean = !eq(this.ptr.asPtr(), other.ptr.asPtr()) && this != other

    /**
     * Equality for two `Rc`s.
     *
     * Two `Rc`s are equal if their inner values are equal, even if they are
     * stored in different allocation.
     *
     * If `T` also implements `Eq` (implying reflexivity of equality),
     * two `Rc`s that point to the same allocation are
     * always equal.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five == 5)
     * ```
     */
    public open fun eqPartialEq(other: Rc<T, A>): Boolean = this.eq(other)

    /**
     * Inequality for two `Rc`s.
     *
     * Two `Rc`s are not equal if their inner values are not equal.
     *
     * If `T` also implements `Eq` (implying reflexivity of equality),
     * two `Rc`s that point to the same allocation are
     * always equal.
     *
     * # Examples
     *
     * ```kotlin
     * val five = 5
     * check(five != 6)
     * ```
     */
    public open fun nePartialEq(other: Rc<T, A>): Boolean = this.ne(other)

    /**
     * Partial comparison for two `Rc`s.
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
    public open fun partialCmp(other: Rc<T, A>): Ordering? = this.partialCmp(other)

    /**
     * Less-than comparison for two `Rc`s.
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
    public open fun lt(other: Rc<T, A>): Boolean = this < other

    /**
     * Less than or equal to' comparison for two `Rc`s.
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
    public open fun le(other: Rc<T, A>): Boolean = this <= other

    /**
     * Greater-than comparison for two `Rc`s.
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
    public open fun gt(other: Rc<T, A>): Boolean = this > other

    /**
     * Greater than or equal to' comparison for two `Rc`s.
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
    public open fun ge(other: Rc<T, A>): Boolean = this >= other

    /**
     * Comparison for two `Rc`s.
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
    public open fun cmp(other: Rc<T, A>): Ordering = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtDebug(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtPointer(f: StringBuilder) {
        Pointer.fmt((this), f)
    }

    public open fun borrow(): T = this

    public open fun asRef(): T = this

    public open fun allocate(layout: Layout): Result<NonNull, AllocError> = this.allocate(layout)

    public open fun allocateZeroed(layout: Layout): Result<NonNull, AllocError> = this.allocateZeroed(layout)

    public open fun deallocate(ptr: NonNull, layout: Layout) {
        // SAFETY: the safety contract must be upheld by the caller
        this.deallocate(ptr, layout)
    }

    public open fun grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: the safety contract must be upheld by the caller
        return this.grow(ptr, oldLayout, newLayout)
    }

    public open fun growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: the safety contract must be upheld by the caller
        return this.growZeroed(ptr, oldLayout, newLayout)
    }

    public open fun shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: the safety contract must be upheld by the caller
        return this.shrink(ptr, oldLayout, newLayout)
    }

    /**
     * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
     */

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            phantom: Unit,
            alloc: A,
        ): Rc<T, A> = Rc<T, A>(
            ptrCtorArg = ptr,
            phantom = phantom,
            alloc = alloc,
        )

        public fun <T, A : Allocator> intoInnerWithAllocator(thisRef: Rc<T, A>): Pair<NonNull, A> {
            val thisRef1 = thisRef
            // SAFETY: Pulling out the allocator we already own.
            return Pair(thisRef1.ptr, read(thisRef1.alloc))
        }

        public fun <T, A : Allocator> fromInnerIn(ptr: NonNull, alloc: A): Rc<T, A> = Rc(ptr = ptr, phantom = Unit, alloc = alloc)

        public fun <T, A : Allocator> fromPtrIn(ptr: MutPtr, alloc: A): Rc<T, A> {
            // SAFETY: Upheld by caller.
            return Rc.fromInnerIn(NonNull.newUnchecked(ptr), alloc)
        }

        /**
         * Constructs a new `Rc` in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Rc.newIn(5, System)
         * }
         * ```
         */
        public fun <T, A : Allocator> newIn(value: T, alloc: A, typeLayoutT: TypeLayout<T>): Rc<T, A> {
            // NOTE: Prefer match over unwrapOrElse since closure sometimes not inlineable.
            // That would make code size bigger.
            return run {
                val matchVal = Rc.tryNewIn(value, alloc)
                when (matchVal) { is Result.Ok<*> -> {
                    val m = matchVal.value
                    m
                }; is Result.Err<*> -> {
                    handleAllocError(Layout.new<RcInner<T>>(typeLayoutT = ObjectSlotLayout<RcInner<T>>()))
                } }
            }
        }

        /**
         * Constructs a new `Rc` with uninitialized contents in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(getMutUnchecked)
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     var five = Rc.newUninitIn(System)
         *     val five1 = run {
         *         // Deferred initialization:
         *         Rc.getMutUnchecked(five).asMutPtr().write(5)
         *         five.assumeInit()
         *     }
         *     check(five1 == 5)
         * }
         * ```
         */
        public fun <T, A : Allocator> newUninitIn(alloc: A, typeLayoutT: TypeLayout<T>): Rc<MaybeUninit<T>, A> {
            // ignore-tidy-undocumented-unchecked
            return Rc.fromPtrIn(Rc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocate(layout) }, MutPtr.cast), alloc)
        }

        /**
         * Constructs a new `Rc` with uninitialized contents, with the memory
         * being filled with `0` bytes, in the provided allocator.
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
         *     val zero = Rc.newZeroedIn(System)
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> newZeroedIn(alloc: A, typeLayoutT: TypeLayout<T>): Rc<MaybeUninit<T>, A> {
            // ignore-tidy-undocumented-unchecked
            return Rc.fromPtrIn(Rc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocateZeroed(layout) }, MutPtr.cast), alloc)
        }

        /**
         * Constructs a new `Rc<T, A>` in the given allocator while giving you a `Weak<T, A>` to the allocation,
         * to allow you to construct a `T` which holds a weak pointer to itself.
         *
         * Generally, a structure circularly referencing itself, either directly or
         * indirectly, should not hold a strong reference to itself to prevent a memory leak.
         * Using this function, you get access to the weak pointer during the
         * initialization of `T`, before the `Rc<T, A>` is created, such that you can
         * clone and store it inside the `T`.
         *
         * `newCyclicIn` first allocates the managed allocation for the `Rc<T, A>`,
         * then calls your closure, giving it a `Weak<T, A>` to this allocation,
         * and only afterwards completes the construction of the `Rc<T, A>` by placing
         * the `T` returned from your closure into the allocation.
         *
         * Since the new `Rc<T, A>` is not fully-constructed until `Rc<T, A>.newCyclicIn`
         * returns, calling [upgrade] on the weak reference inside your closure will
         * fail and result in a `None` value.
         *
         * # Panics
         *
         * If `dataFn` panics, the panic is propagated to the caller, and the
         * temporary [Weak.T.A] is dropped normally.
         *
         * # Examples
         *
         * See [newCyclic].
         *
         * [newCyclic]: Rc.newCyclic
         * [upgrade]: Weak.upgrade
         */
        public fun <T, A : Allocator> newCyclicIn(dataFn: (Weak<T, A>) -> T, alloc: A): Rc<T, A> {
            // Construct the inner in the "uninitialized" state with a single
            // weak reference.
            val (uninitPtr, alloc1) = Box.intoNonNullWithAllocator(Box.newIn(RcInner(strong = Cell.new(0), weak = Cell.new(1), value = MaybeUninit.uninit(ObjectSlotLayout<T>())), alloc))
            val initPtr: NonNull = uninitPtr.cast()
            val weak = Weak(ptr = initPtr, alloc = alloc1)
            // It's important we don't give up ownership of the weak pointer, or
            // else the memory might be freed by the time `dataFn` returns. If
            // we really wanted to pass ownership, we could create an additional
            // weak pointer for ourselves, but this would result in additional
            // updates to the weak reference count which might not be necessary
            // otherwise.
            val data = dataFn(weak)
            // ignore-tidy-undocumented-unchecked
            return run {
                val inner = initPtr.asPtr()
                write(addrOfMut(inner.value), data)
                val prevValue = inner.strong.get()
                check(prevValue == 0)
                inner.strong.set(1)
                // Strong references should collectively own a shared weak reference,
                // so don't run the destructor for our old weak reference.
                // Calling intoRawWithAllocator has the double effect of giving us back the allocator,
                // and forgetting the weak reference.
                val alloc2 = weak.intoRawWithAllocator().second
                Rc.fromInnerIn(initPtr, alloc2)
            }
        }

        /**
         * Constructs a new `Rc<T>` in the provided allocator, returning an error if the allocation
         * fails
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Rc.tryNewIn(5, System)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryNewIn(value: T, alloc: A): Result<Rc<T, A>, AllocError> {
            // There is an implicit weak pointer owned by all the strong
            // pointers, which ensures that the weak destructor never frees
            // the allocation while the strong destructor is running, even
            // if the weak pointer is stored inside the strong one.
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(when (val tryResult = Box.tryNewIn(RcInner(strong = Cell.new(1), weak = Cell.new(1), value = value), alloc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
            // SAFETY: Pointer is valid.
            return Result.Ok(Rc.fromInnerIn(ptr, alloc1))
        }

        /**
         * Constructs a new `Rc` with uninitialized contents, in the provided allocator, returning an
         * error if the allocation fails
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * @feature(getMutUnchecked)
         * public fun doctestEntry(): Result<Unit> {
         *     var five = Rc.tryNewUninitIn(System).getOrElse { return Result.failure(it) }
         *     val five1 = run {
         *         // Deferred initialization:
         *         Rc.getMutUnchecked(five).asMutPtr().write(5)
         *         five.assumeInit()
         *     }
         *     check(five1 == 5)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryNewUninitIn(alloc: A, typeLayoutT: TypeLayout<T>): Result<Rc<MaybeUninit<T>, A>, AllocError> {
            // ignore-tidy-undocumented-unchecked
            return Result.Ok(Rc.fromPtrIn(when (val tryResult = Rc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocate(layout) }, MutPtr.cast)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, alloc))
        }

        /**
         * Constructs a new `Rc` with uninitialized contents, with the memory
         * being filled with `0` bytes, in the provided allocator, returning an error if the allocation
         * fails
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
         * public fun doctestEntry(): Result<Unit> {
         *     val zero = Rc.tryNewZeroedIn(System).getOrElse { return Result.failure(it) }
         *     val zero1 = zero.assumeInit()
         *     check(zero1 == 0)
         *     return Result.success(Unit)
         * }
         * ```
         *
         * [zeroed]: mem.MaybeUninit.zeroed
         */
        public fun <T, A : Allocator> tryNewZeroedIn(alloc: A, typeLayoutT: TypeLayout<T>): Result<Rc<MaybeUninit<T>, A>, AllocError> {
            // ignore-tidy-undocumented-unchecked
            return Result.Ok(Rc.fromPtrIn(when (val tryResult = Rc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> alloc.allocateZeroed(layout) }, MutPtr.cast)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, alloc))
        }

        /**
         * Constructs a new `Pin<Rc<T>>` in the provided allocator. If `T` does not implement `Unpin`, then
         * `value` will be pinned in memory and unable to be moved.
         */
        public fun <T, A : Allocator> pinIn(value: T, alloc: A): Pin<Rc<T, A>> where A : StaticAllocator {
            // SAFETY: We own and create the pinned pointer.
            return Pin.newUnchecked(Rc.newIn(value, alloc))
        }

        /**
         * Returns the inner value, if the `Rc` has exactly one strong reference.
         *
         * Otherwise, an [Err] is returned with the same `Rc` that was
         * passed in.
         *
         * This will succeed even if there are outstanding weak references.
         *
         * # Examples
         *
         * ```kotlin
         * val x = 3
         * check(Rc.tryUnwrap(x) == Result.success(3))
         * val x1 = 4
         * val y = x1
         * check(Rc.tryUnwrap(x1).unwrapErr() == 4)
         * ```
         */
        public fun <T, A : Allocator> tryUnwrap(thisRef: Rc<T, A>): Result<T, Rc<T, A>> =
            if (Rc.strongCount(thisRef) == 1) {
                val thisRef1 = thisRef
                // ignore-tidy-undocumented-unchecked
                val value: T = read(thisRef1)
                // copy the contained object
                // ignore-tidy-undocumented-unchecked
                // copy the allocator
                // Indicate to Weaks that they can't be promoted by decrementing
                // the strong count, and then remove the implicit "strong weak"
                // pointer while also handling drop logic by just crafting a
                // fake Weak.
                thisRef1.inner().decStrong()
                Result.Ok(value)
            } else {
                Result.Err(thisRef)
            }

        /**
         * Returns the inner value, if the `Rc` has exactly one strong reference.
         *
         * Otherwise, [None] is returned and the `Rc` is dropped.
         *
         * This will succeed even if there are outstanding weak references.
         *
         * If `Rc.intoInner` is called on every clone of this `Rc`,
         * it is guaranteed that exactly one of the calls returns the inner value.
         * This means in particular that the inner value is not dropped.
         *
         * [Rc.tryUnwrap] is conceptually similar to `Rc.intoInner`.
         * And while they are meant for different use-cases, `Rc.intoInner(this)`
         * is in fact equivalent to <code>[Rc.tryUnwrap]\(this).[ok][Result.ok]()</code>.
         * (Note that the same kind of equivalence does **not** hold true for
         * [`Arc`](sync.Arc), due to race conditions that do not apply to `Rc`!)
         *
         * # Examples
         *
         * ```kotlin
         * val x = 3
         * check(Rc.intoInner(x) == 3)
         * val x1 = 4
         * val y = x1
         * check(Rc.intoInner(y) == null)
         * check(Rc.intoInner(x1) == 4)
         * ```
         */
        public fun <T, A : Allocator> intoInner(thisRef: Rc<T, A>): T? = Rc.tryUnwrap(thisRef).ok()

        /**
         * Maps the value in an `Rc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `Rc`, and the result is returned, also in
         * an `Rc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Rc.map(r, f)` instead of `r.map(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * val r = 7
         * val new = Rc.map(r, { i -> i + 7 })
         * check(new == 14)
         * ```
         */
        public fun <T, A : Allocator, U> map(thisRef: Rc<T, A>, f: (T) -> U, typeLayoutT: TypeLayout<T>, typeLayoutU: TypeLayout<U>): Rc<U, A> =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU) && Rc.isUnique(thisRef)) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = Rc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = Rc.fromRawIn(ptr.cast(), alloc)
                    Rc.getMutUnchecked(allocation).write(f(value))
                    allocation.assumeInit()
                }
            } else {
                val output = f(thisRef)
                val (ptr1, alloc1) = Rc.intoRawWithAllocator(thisRef)
                // ignore-tidy-undocumented-unchecked
                Rc.decrementStrongCountIn(ptr1, alloc1)
                Rc.newIn(output, alloc1)
            }

        /**
         * Attempts to map the value in an `Rc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `Rc`, and if the operation succeeds, the
         * result is returned, also in an `Rc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Rc.tryMap(r, f)` instead of `r.tryMap(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(smartPointerTryMap)
         * public fun doctestEntry() {
         *     val b = 7
         *     val new = Rc.tryMap(b, { i -> UInt.tryFrom(i) }).unwrap()
         *     check(new == 7)
         * }
         * ```
         */
        public fun <T, A : Allocator, R> tryMap(thisRef: Rc<T, A>, f: (T) -> R, typeLayoutT: TypeLayout<T>): TryType where R : Try {
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Output>(typeLayoutT = ObjectSlotLayout<Output>()) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<Output>(typeLayoutT = ObjectSlotLayout<Output>()) && Rc.isUnique(thisRef)) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = Rc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = Rc.fromRawIn(ptr.cast(), alloc)
                    Rc.getMutUnchecked(allocation).write(when (val tryResult = f(value)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                    allocation.assumeInit()
                }
            } else {
                val output = when (val tryResult = f(thisRef)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                val (ptr1, alloc1) = Rc.intoRawWithAllocator(thisRef)
                // ignore-tidy-undocumented-unchecked
                Rc.decrementStrongCountIn(ptr1, alloc1)
                Rc.newIn(output, alloc1)
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Constructs a new `Rc<T>` with a clone of `value` in the provided allocator.
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val hello: String = Rc.cloneFromRefIn("hello", System)
         * }
         * ```
         */
        public fun <T, A : Allocator> cloneFromRefIn(value: T, alloc: A): Rc<T, A> {
            // `inProgress` drops the allocation if we panic before finishing initializing it.
            val inProgress: UniqueRcUninit<T, A>  = UniqueRcUninit.new(value, alloc)
            // Initialize with clone of value.
            // ignore-tidy-undocumented-unchecked
            return run {
                // Clone. If the clone panics, `inProgress` will be dropped and clean up.
                value.cloneToUninit(inProgress.dataPtr().cast())
                // Cast type of pointer, now that it is initialized.
                inProgress.intoRc()
            }
        }

        // @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

        /**
         * Constructs a new `Rc<T>` with a clone of `value` in the provided allocator, returning an error if allocation fails
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(cloneFromRef)
         * @feature(allocatorExt)
         * public fun doctestEntry(): Result<Unit> {
         *     val hello: String = Rc.tryCloneFromRefIn("hello", System).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T, A : Allocator> tryCloneFromRefIn(value: T, alloc: A): Result<Rc<T, A>, AllocError> {
            // `inProgress` drops the allocation if we panic before finishing initializing it.
            val inProgress: UniqueRcUninit<T, A>  = when (val tryResult = UniqueRcUninit.tryNew(value, alloc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            // Initialize with clone of value.
            // ignore-tidy-undocumented-unchecked
            val initializedClone =
                run {
                    // Clone. If the clone panics, `inProgress` will be dropped and clean up.
                    value.cloneToUninit(inProgress.dataPtr().cast())
                    // Cast type of pointer, now that it is initialized.
                    inProgress.intoRc()
                }
            return Result.Ok(initializedClone)
        }

        /**
         * Returns a reference to the underlying allocator.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `Rc.allocator(r)` instead of `r.allocator()`. This
         * is so that there is no conflict with a method on the inner type.
         */
        public fun <T, A : Allocator> allocator(thisRef: Rc<T, A>): A = thisRef.alloc

        /**
         * Consumes the `Rc`, returning the wrapped pointer and allocator.
         *
         * To avoid a memory leak the pointer must be converted back to an `Rc` using
         * [Rc.fromRawIn].
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x = Rc.newIn("hello".toOwned(), System)
         *     val (ptr, alloc) = Rc.intoRawWithAllocator(x)
         *     check(ptr == "hello")
         *     val x1 = Rc.fromRawIn(ptr, alloc)
         *     check(x1 == "hello")
         * }
         * ```
         */
        public fun <T, A : Allocator> intoRawWithAllocator(thisRef: Rc<T, A>): Pair<ConstPtr, A> {
            val thisRef1 = thisRef
            val ptr = Rc.asPtr(thisRef1)
            // SAFETY: `this` is ManuallyDrop so the allocator will not be double-dropped
            val alloc = read(thisRef1.alloc)
            return Pair(ptr, alloc)
        }

        /**
         * Provides a raw pointer to the data.
         *
         * The counts are not affected in any way and the `Rc` is not consumed. The pointer is valid
         * for as long as there are strong counts in the `Rc`.
         *
         * # Examples
         *
         * ```kotlin
         * val x = 0
         * val y = x
         * val xPtr = Rc.asPtr(x)
         * check(xPtr == Rc.asPtr(y))
         * check(xPtr == 0)
         * ```
         */
        public fun <T, A : Allocator> asPtr(thisRef: Rc<T, A>): ConstPtr {
            val ptr: MutPtr = NonNull.asPtr(thisRef.ptr)
            // SAFETY: This cannot go through Deref.deref or Rc.inner because
            // this is required to retain raw/mut provenance such that e.g. `getMut` can
            // write through the pointer after the Rc is recovered through `fromRaw`.
            return addrOfMut((ObjectSlotLayout<RcInner<T>>()).readNative((ptr).rawAddress).value)
        }

        /**
         * Constructs an `Rc<T, A>` from a raw pointer in the provided allocator.
         *
         * The raw pointer must have been previously returned by a call to [`Rc<U,
         * A>::intoRaw`][intoRaw] or [`Rc<U, A>::intoRawWithAllocator`][intoRawWithAllocator].
         *
         * # Safety
         *
         * * Creating a `Rc<T, A>` from a pointer other than one returned from
         *   [`Rc.U.A.intoRaw`][intoRaw] or [`Rc.U.A.intoRawWithAllocator`][intoRawWithAllocator]
         *   is undefined behavior.
         * * If `U` is sized, it must have the same size and alignment as `T`. This
         *   is trivially true if `U` is `T`.
         * * If `U` is unsized, its data pointer must have the same size and
         *   alignment as `T`. This is trivially true if `Rc<U, A>` was constructed
         *   through `Rc<T, A>` and then converted to `Rc<U, A>` through an [unsized
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
         * even if the returned `Rc<T, A>` is never accessed.
         *
         * [intoRaw]: Rc.intoRaw
         * [intoRawWithAllocator]: Rc.intoRawWithAllocator
         * [unsized coercion]: https://doc.rust-lang.org/reference/type-coercions.html#unsized-coercions
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val x = Rc.newIn("hello".toOwned(), System)
         *     val (xPtr, alloc) = Rc.intoRawWithAllocator(x)
         *     run {
         *         // Convert back to an `Rc` to prevent leak.
         *         val x1 = Rc.fromRawIn(xPtr, System)
         *         check(x1 == "hello")
         *         // Further calls to `Rc.fromRaw(xPtr)` would be memory-unchecked.
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
         *     val x: List<UInt> = Rc.newIn(listOf(1, 2, 3), System)
         *     val xPtr: ConstPtr = Rc.intoRawWithAllocator(x).first
         *     run {
         *         val x1: UIntArray = Rc.fromRawIn(ConstPtr((xPtr).rawAddress), System)
         *         check(x1 == listOf(1, 2, 3))
         *     }
         * }
         * ```
         */
        public fun <T, A : Allocator> fromRawIn(ptr: ConstPtr, alloc: A): Rc<T, A> {
            // ignore-tidy-undocumented-unchecked
            val offset = dataOffset(ptr)
            // Reverse the offset to find the original RcInner.
            // ignore-tidy-undocumented-unchecked
            val rcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            // ignore-tidy-undocumented-unchecked
            return Rc.fromPtrIn(rcPtr, alloc)
        }

        /**
         * Creates a new [Weak] pointer to this allocation.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val weakFive = Rc.downgrade(five)
         * ```
         */
        public fun <T, A : Allocator> downgrade(thisRef: Rc<T, A>): Weak<T, A> where A : AllocatorClone {
            thisRef.inner().incWeak()
            // Make sure we do not create a dangling Weak
            check(!isDangling(thisRef.ptr.asPtr()))
            return Weak(ptr = thisRef.ptr, alloc = thisRef.alloc.clone())
        }

        /**
         * Gets the number of [Weak] pointers to this allocation.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val weakFive = Rc.downgrade(five)
         * check(1 == Rc.weakCount(five))
         * ```
         */
        public fun <T, A : Allocator> weakCount(thisRef: Rc<T, A>): Int = thisRef.inner().weak() - 1

        /**
         * Gets the number of strong (`Rc`) pointers to this allocation.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val alsoFive = five
         * check(2 == Rc.strongCount(five))
         * ```
         */
        public fun <T, A : Allocator> strongCount(thisRef: Rc<T, A>): Int = thisRef.inner().strong()

        /**
         * Increments the strong reference count on the `Rc<T>` associated with the
         * provided pointer by one.
         *
         * # Safety
         *
         * The pointer must have been obtained through `Rc.intoRaw` and must satisfy the
         * same layout requirements specified in [`Rc.fromRawIn`][fromRawIn].
         * The associated `Rc` instance must be valid (i.e. the strong count must be at
         * least 1) for the duration of this method, and `ptr` must point to a block of memory
         * allocated by `alloc`.
         *
         * [fromRawIn]: Rc.fromRawIn
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Rc.newIn(5, System)
         *     run {
         *         val (ptr, alloc) = Rc.intoRawWithAllocator(five)
         *         Rc.incrementStrongCountIn(ptr, System)
         *         val five1 = Rc.fromRawIn(ptr, System)
         *         check(2 == Rc.strongCount(five1))
         *     }
         * }
         * ```
         */
        public fun <A : Allocator> incrementStrongCountIn(ptr: ConstPtr, alloc: A) where A : AllocatorClone {
            // Retain Rc, but don't touch refcount by wrapping in ManuallyDrop
            // ignore-tidy-undocumented-unchecked
            // Now increase refcount, but don't drop new refcount either
        }

        /**
         * Decrements the strong reference count on the `Rc<T>` associated with the
         * provided pointer by one.
         *
         * # Safety
         *
         * The pointer must have been obtained through `Rc.intoRaw`and must satisfy the
         * same layout requirements specified in [`Rc.fromRawIn`][fromRawIn].
         * The associated `Rc` instance must be valid (i.e. the strong count must be at
         * least 1) when invoking this method, and `ptr` must point to a block of memory
         * allocated by `alloc`. This method can be used to release the final `Rc` and
         * backing storage, but **should not** be called after the final `Rc` has been released.
         *
         * [fromRawIn]: Rc.fromRawIn
         *
         * # Examples
         *
         * ```kotlin
         * import std.alloc.System
         *
         * @feature(allocatorExt)
         * public fun doctestEntry() {
         *     val five = Rc.newIn(5, System)
         *     run {
         *         val (ptr, alloc) = Rc.intoRawWithAllocator(five)
         *         Rc.incrementStrongCountIn(ptr, System)
         *         val five1 = Rc.fromRawIn(ptr, System)
         *         check(2 == Rc.strongCount(five1))
         *         Rc.decrementStrongCountIn(ptr, System)
         *         check(1 == Rc.strongCount(five1))
         *     }
         * }
         * ```
         */
        public fun <A : Allocator> decrementStrongCountIn(ptr: ConstPtr, alloc: A) {
            // SAFETY: Upheld by caller.
            (Rc.fromRawIn(ptr, alloc)).let { }
        }

        /**
         * Determine whether this is the unique reference to the underlying data.
         *
         * Returns `true` if there are no other `Rc` or [Weak] pointers to the same allocation;
         * returns `false` otherwise.
         *
         * If this function returns `true`, then it is guaranteed to be safe to call
         * [getMutUnchecked] on this `Rc`, as long as no clones occur in between.
         *
         * The result can become stale if the `Rc` is cloned or downgraded between
         * the check and its use. Make sure no new `Rc` or `Weak` pointers are created
         * in between.
         *
         * # Examples
         *
         * ```kotlin
         * val x = 3
         * check(Rc.isUnique(x))
         * val y = x
         * check(!Rc.isUnique(x))
         * (y).let { }
         * // Weak references also count, because they could be upgraded at any time.
         * val z = Rc.downgrade(x)
         * check(!Rc.isUnique(x))
         * ```
         *
         * # Pointer invalidation
         *
         * This function will always return the same value as `Rc.getMut(rc).isSome()`. However,
         * unlike that operation it does not produce any mutable references to the underlying data,
         * meaning no pointers to the data inside the `Rc` are invalidated by the call. Thus, the
         * following code is valid:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         *
         * val rc = 5
         * val pointer: ConstPtr = rc
         * check(Rc.isUnique(rc))
         * check((PrimitiveLayouts.I32).readNative((pointer).rawAddress) == 5)
         * ```
         *
         * The following changes would make the example unsound:
         *
         * - Using `Rc.getMut` to create a mutable reference while also accessing the value
         *   through `pointer`.
         * - Dropping `rc` before dereferencing `pointer`, leaving it dangling.
         *
         * [getMutUnchecked]: Self.getMutUnchecked
         */
        public fun <T, A : Allocator> isUnique(thisRef: Rc<T, A>): Boolean = Rc.weakCount(thisRef) == 0 && Rc.strongCount(thisRef) == 1

        /**
         * Returns a mutable reference into the given `Rc`, if there are
         * no other `Rc` or [Weak] pointers to the same allocation.
         *
         * Returns [None] otherwise, because it is not safe to
         * mutate a shared value.
         *
         * See also [`makeMut`][makeMut], which will [`clone`][clone]
         * the inner value when there are other `Rc` pointers.
         *
         * [makeMut]: Rc.makeMut
         * [clone]: Clone.clone
         *
         * # Examples
         *
         * ```kotlin
         * var x = 3
         * Rc.getMut(x).unwrap() = 4
         * check(x == 4)
         * val y = x
         * check(Rc.getMut(x) == null)
         * ```
         */
        public fun <T, A : Allocator> getMut(thisRef: Rc<T, A>): T? {
            // SAFETY: Ensured by uniqueness check.
            return if (Rc.isUnique(thisRef)) {
                Rc.getMutUnchecked(thisRef)
            } else {
                null
            }
        }

        /**
         * Returns a mutable reference into the given `Rc`,
         * without any check.
         *
         * See also [getMut], which is safe and does appropriate checks.
         *
         * [getMut]: Rc.getMut
         *
         * # Safety
         *
         * If any other `Rc` or [Weak] pointers to the same allocation exist, then
         * they must not be dereferenced or have active borrows for the duration
         * of the returned borrow, and their inner type must be exactly the same as the
         * inner type of this Rc (including scopes). This is trivially the case if no
         * such pointers exist, for example immediately after `Rc.new`.
         *
         * # Examples
         *
         * ```kotlin
         * @feature(getMutUnchecked)
         * public fun doctestEntry() {
         *     var x = ""
         *     Rc.getMutUnchecked(x).pushStr("foo")
         *     check(x == "foo")
         * }
         * ```
         * Other `Rc` pointers to the same allocation must be to the same type.
         * ```kotlin
         * @feature(getMutUnchecked)
         * public fun doctestEntry() {
         *     val x: String = Rc.from("Hello, world!")
         *     var y: List<UByte> = x.clone().into()
         *     run {
         *         // this is Undefined Behavior, because x's inner type is str, not [u8]
         *         Rc.getMutUnchecked(y).fill(0xff)
         *         // 0xff is invalid in UTF-8
         *     }
         *     println("$x")
         *     // Invalid UTF-8 in a str
         * }
         * ```
         * Other `Rc` pointers to the same allocation must be to the exact same type, including scopes.
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
         *             Rc.getMutUnchecked(y) = s
         *         }
         *     }
         *     println("$x")
         *     // Use-after-free
         * }
         * ```
         */
        public fun <T, A : Allocator> getMutUnchecked(thisRef: Rc<T, A>): T {
            // We are careful to *not* create a reference covering the "count" fields, as
            // this would conflict with accesses to the reference counts (e.g. by `Weak`).
            // ignore-tidy-undocumented-unchecked
            return thisRef.ptr.asPtr().value
        }

        /**
         * Returns `true` if the two `Rc`s point to the same allocation in a vein similar to
         * [ptr.eq]. This function ignores the metadata of  `dynamic Trait` pointers.
         *
         * # Examples
         *
         * ```kotlin
         * val five = 5
         * val sameFive = five
         * val otherFive = 5
         * check(Rc.ptrEq(five, sameFive))
         * check(!Rc.ptrEq(five, otherFive))
         * ```
         */
        public fun <T, A : Allocator> ptrEq(thisRef: Rc<T, A>, other: Rc<T, A>): Boolean = addrEq(thisRef.ptr.asPtr(), other.ptr.asPtr())

        /**
         * Makes a mutable reference into the given `Rc`.
         *
         * If there are other `Rc` pointers to the same allocation, then `makeMut` will
         * [clone] the inner value to a new allocation to ensure unique ownership.  This is also
         * referred to as clone-on-write.
         *
         * However, if there are no other `Rc` pointers to this allocation, but some [Weak]
         * pointers, then the [Weak] pointers will be disassociated and the inner value will not
         * be cloned.
         *
         * See also [getMut], which will fail rather than cloning the inner value
         * or disassociating [Weak] pointers.
         *
         * [clone]: Clone.clone
         * [getMut]: Rc.getMut
         *
         * # Examples
         *
         * ```kotlin
         * var data = 5
         * Rc.makeMut(data) += 1
         * // Won't clone anything
         * var otherData = data
         * // Won't clone inner data
         * Rc.makeMut(data) += 1
         * // Clones inner data
         * Rc.makeMut(data) += 1
         * // Won't clone anything
         * Rc.makeMut(otherData) *= 2
         * // Won't clone anything
         * // Now `data` and `otherData` point to different allocations.
         * check(data == 8)
         * check(otherData == 12)
         * ```
         *
         * [Weak] pointers will be disassociated:
         *
         * ```kotlin
         * var data = 75
         * val weak = Rc.downgrade(data)
         * check(75 == data)
         * check(75 == weak.upgrade().unwrap())
         * Rc.makeMut(data) += 1
         * check(76 == data)
         * check(weak.upgrade() == null)
         * ```
         */
        public fun <T, A : Allocator> makeMut(thisRef: Rc<T, A>): T {
            var thisRef1 = thisRef
            val sizeOfVal = sizeOfVal<T>(thisRef1)
            if (Rc.strongCount(thisRef1) != 1) {
                // Gotta clone the data, there are other Rcs.
                thisRef1 = Rc.cloneFromRefIn(thisRef1, thisRef1.alloc.clone())
            } else if (Rc.weakCount(thisRef1) != 0) {
                // Can just steal the data, all that's left is Weaks
                val inProgress: UniqueRcUninit<T, A>  = UniqueRcUninit.new(thisRef1, thisRef1.alloc.clone())
                // ignore-tidy-undocumented-unchecked
                run {
                    // Initialize `inProgress` with move of **this.
                    // We have to express this in terms of bytes because `T: ? Sized`; there is no
                    // operation that just copies a value based on its `sizeOfVal()`.
                    copyNonoverlapping(thisRef1.cast(), inProgress.dataPtr().cast(), sizeOfVal)
                    // This leaves us with 0 strong refs, so the data has
                    // effectively been moved to the new rc.
                    thisRef1.inner().decStrong()
                    // Remove implicit strong-weak ref (no need to craft a fake
                    // Weak here -- we know other Weaks can clean up for us)
                    thisRef1.inner().decWeak()
                    // Last chance to not accidentally forget the allocator.
                    // Only drop at the end of the scope to avoid panics.
                    // Replace `this` with newly constructed Rc that has the moved data.
                    write(thisRef1, inProgress.intoRc())
                }
            }
            // SAFETY: We're guaranteed that the pointer
            // returned is the *only* pointer that will ever be returned to T. Our
            // reference count is guaranteed to be 1 at this point, and we required
            // the `Rc<T>` itself to be , so we're returning the only possible
            // reference to the allocation.
            return thisRef1.ptr.asMut().value
        }

        /**
         * If we have the only reference to `T` then unwrap it. Otherwise, clone `T` and return the
         * clone.
         *
         * Assuming `rcT` is of type `Rc<T>`, this function is functionally equivalent to
         * `(* rcT).clone()`, but will avoid cloning the inner value where possible.
         *
         * # Examples
         *
         * ```kotlin
         * val inner = "test"
         * val ptr = inner.asPtr()
         * val rc = inner
         * val inner1 = Rc.unwrapOrClone(rc)
         * // The inner value was not cloned
         * check(eq(ptr, inner1.asPtr()))
         * val rc1 = inner1
         * val rc2 = rc1.clone()
         * val inner2 = Rc.unwrapOrClone(rc1)
         * // Because there were 2 references, we had to clone the inner value.
         * check(!eq(ptr, inner2.asPtr()))
         * // `rc2` is the last reference, so when we unwrap it we get back
         * // the original `String`.
         * val inner3 = Rc.unwrapOrClone(rc2)
         * check(eq(ptr, inner3.asPtr()))
         * ```
         */
        public fun <T, A : Allocator> unwrapOrClone(thisRef: Rc<T, A>): T = Rc.tryUnwrap(thisRef).unwrapOrElse({ rc -> rc.clone() })

        /**
         * Allocates an `RcInner<T>` with sufficient space for an unsized inner value
         */
        public fun <A : Allocator> allocateForPtrIn(ptr: ConstPtr, alloc: A): MutPtr {
            // Allocate for the `RcInner<T>` using the given value.
            // ignore-tidy-undocumented-unchecked
            return Rc.allocateForLayout(Layout.forValueRaw(ptr), { layout -> alloc.allocate(layout) }, { mem -> mem.withMetadataOf(ConstPtr(ptr.rawAddress, ptr.rawMeta)) })
        }

        public fun <T, A : Allocator> fromBoxIn(src: T): Rc<T, A> {
            val valueSize = sizeOfVal(src)
            // ignore-tidy-undocumented-unchecked
            return run {
                val ptr = Rc.allocateForPtrIn(src, Box.allocator(src))
                // Copy value as bytes
                copyNonoverlapping(((src)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, ((addrOfMut((ObjectSlotLayout<RcInner<T>>()).readNative((ptr).rawAddress).value))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, valueSize, typeLayoutT = PrimitiveLayouts.U8)
                // Free the allocation without dropping its contents
                val (bptr, alloc) = Box.intoRawWithAllocator(src)
                val src1 = Box.fromRawIn(mutPtrFromRustCast(bptr), alloc)
                (src1).let { }
                Rc.fromPtrIn(ptr, alloc)
            }
        }

        /**
         * Move a boxed object to a new, reference counted, allocation.
         *
         * # Example
         *
         * ```kotlin
         * val original: Int = 1
         * val shared: Int = Rc.from(original)
         * check(1 == shared)
         * ```
         */
        public fun <T, A : Allocator> from(v: T): Rc<T, A> = Rc.fromBoxIn(v)
    }
}

// SAFETY: `Rc.clone` doesn't access any `Cell`s which could contain the `Rc` being cloned.

public fun <T> Rc.Companion.fromInner(ptr: NonNull): Rc<T> {
    // SAFETY: Upheld by caller.
    return Rc.fromInnerIn(ptr, Global)
}

public fun <T> Rc.Companion.fromPtr(ptr: MutPtr): Rc<T> {
    // SAFETY: Upheld by caller.
    return Rc.fromInner(NonNull.newUnchecked(ptr))
}

/**
 * Constructs a new `Rc<T>`.
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * ```
 */
public fun <T> Rc.Companion.new(value: T): Rc<T> {
    // SAFETY: There is an implicit weak pointer owned by all the strong
    // pointers, which ensures that the weak destructor never frees
    // the allocation while the strong destructor is running, even
    // if the weak pointer is stored inside the strong one.
    return Rc.fromInner(Box.intoNonNull(RcInner(strong = Cell.new(1), weak = Cell.new(1), value = value)))
}

/**
 * Constructs a new `Rc<T>` while giving you a `Weak<T>` to the allocation,
 * to allow you to construct a `T` which holds a weak pointer to itself.
 *
 * Generally, a structure circularly referencing itself, either directly or
 * indirectly, should not hold a strong reference to itself to prevent a memory leak.
 * Using this function, you get access to the weak pointer during the
 * initialization of `T`, before the `Rc<T>` is created, such that you can
 * clone and store it inside the `T`.
 *
 * `newCyclic` first allocates the managed allocation for the `Rc<T>`,
 * then calls your closure, giving it a `Weak<T>` to this allocation,
 * and only afterwards completes the construction of the `Rc<T>` by placing
 * the `T` returned from your closure into the allocation.
 *
 * Since the new `Rc<T>` is not fully-constructed until `Rc<T>.newCyclic`
 * returns, calling [upgrade] on the weak reference inside your closure will
 * fail and result in a `None` value.
 *
 * # Panics
 *
 * If `dataFn` panics, the panic is propagated to the caller, and the
 * temporary [Weak.T] is dropped normally.
 *
 * # Examples
 *
 * ```kotlin
 * import std.rc.Weak
 *
 * open class Gadget(public var me: Weak<Gadget>) {
 *     / **
 *      * Constructs a reference counted Gadget.
 *      * /
 *     public open fun new(): Gadget {
 *         // `me` is a `Weak<Gadget>` pointing at the new allocation of the
 *         // `Rc` we're constructing.
 *         return Rc.newCyclic({ me ->
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
public fun <T> Rc.Companion.newCyclic(dataFn: (Weak<T>) -> T): Rc<T> = Rc.newCyclicIn(dataFn, Global)

/**
 * Constructs a new `Rc` with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var five = Rc.newUninit()
 * // Deferred initialization:
 * Rc.getMut(five).unwrap().write(5)
 * val five1 = five.assumeInit()
 * check(five1 == 5)
 * ```
 */
public fun <T> Rc.Companion.newUninit(typeLayoutT: TypeLayout<T>): Rc<MaybeUninit<T>> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtr(Rc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global.allocate(layout) }, MutPtr.cast))
}

/**
 * Constructs a new `Rc` with uninitialized contents, with the memory
 * being filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and
 * incorrect usage of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val zero = Rc.newZeroed()
 * val zero1 = zero.assumeInit()
 * check(zero1 == 0)
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Rc.Companion.newZeroed(typeLayoutT: TypeLayout<T>): Rc<MaybeUninit<T>> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtr(Rc.allocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global.allocateZeroed(layout) }, MutPtr.cast))
}

/**
 * Constructs a new `Rc<T>`, returning an error if the allocation fails
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     val five = Rc.tryNew(5)
 * }
 * ```
 */
public fun <T> Rc.Companion.tryNew(value: T): Result<Rc<T>, AllocError> {
    // SAFETY: There is an implicit weak pointer owned by all the strong
    // pointers, which ensures that the weak destructor never frees
    // the allocation while the strong destructor is running, even
    // if the weak pointer is stored inside the strong one.
    return Result.Ok(Rc.fromInner(Box.intoNonNull(when (val tryResult = Box.tryNew(RcInner(strong = Cell.new(1), weak = Cell.new(1), value = value))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })))
}

/**
 * Constructs a new `Rc` with uninitialized contents, returning an error if the allocation fails
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     var five = Rc.tryNewUninit().getOrElse { return Result.failure(it) }
 *     // Deferred initialization:
 *     Rc.getMut(five).unwrap().write(5)
 *     val five1 = five.assumeInit()
 *     check(five1 == 5)
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T> Rc.Companion.tryNewUninit(typeLayoutT: TypeLayout<T>): Result<Rc<MaybeUninit<T>>, AllocError> {
    // ignore-tidy-undocumented-unchecked
    return Result.Ok(Rc.fromPtr(when (val tryResult = Rc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global.allocate(layout) }, MutPtr.cast)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
}

/**
 * Constructs a new `Rc` with uninitialized contents, with the memory
 * being filled with `0` bytes, returning an error if the allocation fails
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and
 * incorrect usage of this method.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocatorExt)
 * public fun doctestEntry(): Result<Unit> {
 *     val zero = Rc.tryNewZeroed().getOrElse { return Result.failure(it) }
 *     val zero1 = zero.assumeInit()
 *     check(zero1 == 0)
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Rc.Companion.tryNewZeroed(typeLayoutT: TypeLayout<T>): Result<Rc<MaybeUninit<T>>, AllocError> {
    // ignore-tidy-undocumented-unchecked
    return Result.Ok(Rc.fromPtr(when (val tryResult = Rc.tryAllocateForLayout(Layout.new<T>(typeLayoutT = typeLayoutT), { layout -> Global.allocateZeroed(layout) }, MutPtr.cast)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
}

/**
 * Constructs a new `Pin<Rc<T>>`. If `T` does not implement `Unpin`, then
 * `value` will be pinned in memory and unable to be moved.
 */
public fun <T> Rc.Companion.pin(value: T): Pin<Rc<T>> {
    // SAFETY: We own and create the pinned pointer.
    return Pin.newUnchecked(Rc.new(value))
}

/**
 * Constructs a new reference-counted slice with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * var values = Rc.newUninitSlice(3)
 * // Deferred initialization:
 * val data = Rc.getMut(values).unwrap()
 * data[0].write(1)
 * data[1].write(2)
 * data[2].write(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(1, 2, 3))
 * ```
 */
public fun <T> Rc.Companion.newUninitSlice(len: Int): Rc<List<MaybeUninit<T>>> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtr(Rc.allocateForSlice(len))
}

/**
 * Constructs a new reference-counted slice with uninitialized contents, with the memory being
 * filled with `0` bytes.
 *
 * See [`MaybeUninit.zeroed`][zeroed] for examples of correct and
 * incorrect usage of this method.
 *
 * # Examples
 *
 * ```kotlin
 * val values = Rc.newZeroedSlice(3)
 * val values1 = values.assumeInit()
 * check(values1 == listOf(0, 0, 0))
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T> Rc.Companion.newZeroedSlice(len: Int, typeLayoutT: TypeLayout<T>): Rc<List<MaybeUninit<T>>> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtr(Rc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> Global.allocateZeroed(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) }))
}

/**
 * Constructs a new reference-counted slice with uninitialized contents.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @feature(getMutUnchecked)
 * @feature(allocatorExt)
 * public fun doctestEntry() {
 *     var values = Rc.newUninitSliceIn(3, System)
 *     val values1 = run {
 *         // Deferred initialization:
 *         Rc.getMutUnchecked(values)[0].asMutPtr().write(1)
 *         Rc.getMutUnchecked(values)[1].asMutPtr().write(2)
 *         Rc.getMutUnchecked(values)[2].asMutPtr().write(3)
 *         values.assumeInit()
 *     }
 *     check(values1 == listOf(1, 2, 3))
 * }
 * ```
 */
public fun <T, A : Allocator> Rc.Companion.newUninitSliceIn(len: Int, alloc: A): Rc<List<MaybeUninit<T>>, A> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtrIn(Rc.allocateForSliceIn(len, alloc), alloc)
}

/**
 * Constructs a new reference-counted slice with uninitialized contents, with the memory being
 * filled with `0` bytes.
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
 *     val values = Rc.newZeroedSliceIn(3, System)
 *     val values1 = values.assumeInit()
 *     check(values1 == listOf(0, 0, 0))
 * }
 * ```
 *
 * [zeroed]: mem.MaybeUninit.zeroed
 */
public fun <T, A : Allocator> Rc.Companion.newZeroedSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T>): Rc<List<MaybeUninit<T>>, A> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromPtrIn(Rc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> alloc.allocateZeroed(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) }), alloc)
}

/**
 * Converts the reference-counted slice into a reference-counted array.
 *
 * This operation does not reallocate; the underlying array of the slice is simply reinterpreted as an array type.
 *
 * # Errors
 *
 * Returns the original `Rc<[T]>` in the `Err` variant if `self.len()` does not equal `N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(allocSliceIntoArray)
 * public fun doctestEntry() {
 *     val rcSlice: List<Int> = listOf(1, 2, 3)
 *     val rcArray: IntArray = rcSlice.intoArray().unwrap()
 * }
 * ```
 */
public fun <T, A : Allocator> Rc<List<T>, A>.intoArray(n: Int): Result<Rc<List<T>, A>, Rc<List<T>, A>> =
    if (this.len() == n) {
        val (ptr, alloc) = Rc.intoRawWithAllocator(this)
        val ptr1 = constPtrFromRustCast(ptr)
        // SAFETY: The underlying array of a slice has the exact same layout as an actual array `[T; N]` if `N` is equal to the slice's length.
        val me = Rc.fromRawIn(ptr1, alloc)
        Result.Ok(me)
    } else {
        Result.Err(this)
    }

/**
 * Converts to `Rc<T>`.
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
 * var five = Rc.newUninit()
 * // Deferred initialization:
 * Rc.getMut(five).unwrap().write(5)
 * val five1 = five.assumeInit()
 * check(five1 == 5)
 * ```
 */
public fun <T, A : Allocator> Rc<MaybeUninit<T>, A>.assumeInit(): Rc<T, A> {
    val (ptr, alloc) = Rc.intoInnerWithAllocator(this)
    // ignore-tidy-undocumented-unchecked
    return Rc.fromInnerIn(ptr.cast(), alloc)
}

/**
 * Constructs a new `Rc<T>` with a clone of `value`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry() {
 *     val hello: String = Rc.cloneFromRef("hello")
 * }
 * ```
 */
public fun <T : CloneToUninit> Rc.Companion.cloneFromRef(value: T): Rc<T> = Rc.cloneFromRefIn(value, Global)

// @unstable(feature = "allocatorExt", issue = "163177", impliedBy = "allocatorApi")

/**
 * Constructs a new `Rc<T>` with a clone of `value`, returning an error if allocation fails
 *
 * # Examples
 *
 * ```kotlin
 * @feature(cloneFromRef)
 * public fun doctestEntry(): Result<Unit> {
 *     val hello: String = Rc.tryCloneFromRef("hello").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T : CloneToUninit> Rc.Companion.tryCloneFromRef(value: T): Result<Rc<T>, AllocError> = Rc.tryCloneFromRefIn(value, Global)

/**
 * Constructs an `Rc<T>` from a raw pointer.
 *
 * The raw pointer must have been previously returned by a call to
 * [`Rc.U.intoRaw`][intoRaw] or [`Rc.U.intoRawWithAllocator`][intoRawWithAllocator].
 *
 * # Safety
 *
 * * Creating a `Rc<T>` from a pointer other than one returned from
 *   [`Rc.U.intoRaw`][intoRaw] or [`Rc.U.intoRawWithAllocator`][intoRawWithAllocator]
 *   is undefined behavior.
 * * If `U` is sized, it must have the same size and alignment as `T`. This
 *   is trivially true if `U` is `T`.
 * * If `U` is unsized, its data pointer must have the same size and
 *   alignment as `T`. This is trivially true if `Rc<U>` was constructed
 *   through `Rc<T>` and then converted to `Rc<U>` through an [unsized
 *   coercion].
 * * Note that if `U` or `U`s data pointer is not `T` but has the same size
 *   and alignment, this is basically like transmuting references of
 *   different types. See [`mem.transmute`][reinterpret] for more information
 *   on what restrictions apply in this case.
 * * The raw pointer must point to a block of memory allocated by the global allocator
 * * The user of `fromRaw` has to make sure a specific value of `T` is only
 *   dropped once.
 *
 * This function is unchecked because improper use may lead to memory unsafety,
 * even if the returned `Rc<T>` is never accessed.
 *
 * [intoRaw]: Rc.intoRaw
 * [intoRawWithAllocator]: Rc.intoRawWithAllocator
 * [unsized coercion]: https://doc.rust-lang.org/reference/type-coercions.html#unsized-coercions
 *
 * # Examples
 *
 * ```kotlin
 * val x = "hello".toOwned()
 * val xPtr = Rc.intoRaw(x)
 * run {
 *     // Convert back to an `Rc` to prevent leak.
 *     val x1 = Rc.fromRaw(xPtr)
 *     check(x1 == "hello")
 *     // Further calls to `Rc.fromRaw(xPtr)` would be memory-unchecked.
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
 *     val xPtr: ConstPtr = Rc.intoRaw(x)
 *     run {
 *         val x1: UIntArray = Rc.fromRaw(ConstPtr((xPtr).rawAddress))
 *         check(x1 == listOf(1, 2, 3))
 *     }
 * }
 * ```
 */
public fun <T> Rc.Companion.fromRaw(ptr: ConstPtr): Rc<T> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromRawIn(ptr, Global)
}

/**
 * Consumes the `Rc`, returning the wrapped pointer.
 *
 * To avoid a memory leak the pointer must be converted back to an `Rc` using
 * [Rc.fromRaw].
 *
 * # Examples
 *
 * ```kotlin
 * val x = "hello".toOwned()
 * val xPtr = Rc.intoRaw(x)
 * check(xPtr == "hello")
 * ```
 */
public fun <T> Rc.Companion.intoRaw(thisRef: Rc<T>): ConstPtr {
    val thisRef1 = thisRef
    return Rc.asPtr(thisRef1)
}

/**
 * Increments the strong reference count on the `Rc<T>` associated with the
 * provided pointer by one.
 *
 * # Safety
 *
 * The pointer must have been obtained through [Rc.intoRaw] and must satisfy the
 * same layout requirements specified in [Rc.fromRawIn].
 * The associated `Rc` instance must be valid (i.e. the strong count must be at
 * least 1) for the duration of this method, and `ptr` must point to a block of memory
 * allocated by the global allocator.
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * run {
 *     val ptr = Rc.intoRaw(five)
 *     Rc.incrementStrongCount(ptr)
 *     val five1 = Rc.fromRaw(ptr)
 *     check(2 == Rc.strongCount(five1))
 * }
 * ```
 */
public fun Rc.Companion.incrementStrongCount(ptr: ConstPtr) {
    // ignore-tidy-undocumented-unchecked
    Rc.incrementStrongCountIn(ptr, Global)
}

/**
 * Decrements the strong reference count on the `Rc<T>` associated with the
 * provided pointer by one.
 *
 * # Safety
 *
 * The pointer must have been obtained through `Rc.intoRaw` and must satisfy the
 * same layout requirements specified in [`Rc.fromRawIn`][fromRawIn].
 * The associated `Rc` instance must be valid (i.e. the strong count must be at
 * least 1) when invoking this method, and `ptr` must point to a block of memory
 * allocated by the global allocator. This method can be used to release the final `Rc` and
 * backing storage, but **should not** be called after the final `Rc` has been released.
 *
 * [fromRawIn]: Rc.fromRawIn
 *
 * # Examples
 *
 * ```kotlin
 * val five = 5
 * run {
 *     val ptr = Rc.intoRaw(five)
 *     Rc.incrementStrongCount(ptr)
 *     val five1 = Rc.fromRaw(ptr)
 *     check(2 == Rc.strongCount(five1))
 *     Rc.decrementStrongCount(ptr)
 *     check(1 == Rc.strongCount(five1))
 * }
 * ```
 */
public fun Rc.Companion.decrementStrongCount(ptr: ConstPtr) {
    // ignore-tidy-undocumented-unchecked
    Rc.decrementStrongCountIn(ptr, Global)
}

/**
 * Gets the number of strong (`Rc`) pointers to the allocation behind the given raw pointer.
 *
 * This method does not consume or drop the `Rc` behind this pointer.
 *
 * # Safety
 *
 * The pointer must point to (and have valid metadata for) the value inside a live `Rc`
 * allocation, such as a pointer returned by [Rc.intoRaw],
 * [Rc.intoRawWithAllocator], or [Rc.asPtr].
 * `T` must have the same alignment as that value.
 * The associated `Rc` instance must be valid (i.e. the strong count must be at
 * least 1) for the duration of this method.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(arcRawGetStrong)
 * public fun doctestEntry() {
 *     val five = 5
 *     val alsoFive = five
 *     val ptr = Rc.intoRaw(five)
 *     run {
 *         check(2 == Rc.strongCountFromRaw(ptr))
 *         // Convert back to an `Rc` to avoid leaking memory.
 *         val five1 = Rc.fromRaw(ptr)
 *         check(2 == Rc.strongCount(five1))
 *     }
 * }
 * ```
 */
public fun <T> Rc.Companion.strongCountFromRaw(ptr: ConstPtr): Int {
    // SAFETY: Upheld by caller.
    val offset = dataOffset(ptr)
    // Reverse the offset to find the original RcInner.
    // SAFETY: Caller ensures this pointer was to an `Rc` allocation,
    // so offsetting must be inbounds.
    val rcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    // SAFETY: Per the above, an `RcInner` is stored here.
    return (ObjectSlotLayout<RcInner<T>>()).readNative((rcPtr).rawAddress).strong.get()
}

/**
 * Attempts to downcast the `Rc<dynamic Any>` to a concrete type.
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
public fun <A : Allocator, T> Rc<RustAny, A>.downcast(): Result<Rc<T, A>, Rc<RustAny, A>> =
    if (this.isMatch<T>()) {
        // SAFETY: Check ensures typecast is corrext.
        run {
            val (ptr, alloc) = Rc.intoInnerWithAllocator(this)
            Result.Ok(Rc.fromInnerIn(ptr.cast(), alloc))
        }
    } else {
        Result.Err(this)
    }

/**
 * Downcasts the `Rc<dynamic Any>` to a concrete type.
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
public fun <A : Allocator, T> Rc<RustAny, A>.downcastUnchecked(): Rc<T, A> {
    // SAFETY: Caller ensures typecast is correct.
    return run {
        val (ptr, alloc) = Rc.intoInnerWithAllocator(this)
        Rc.fromInnerIn(ptr.cast(), alloc)
    }
}

/**
 * Allocates an `RcInner<T>` with sufficient space for
 * a possibly-unsized inner value where the value has the layout provided.
 *
 * The function `memToRcInner` is called with the data pointer
 * and must return back a (potentially fat)-pointer for the `RcInner<T>`.
 */
public fun Rc.Companion.allocateForLayout(valueLayout: Layout, allocate: (Layout) -> Result<NonNull, AllocError>, memToRcInner: (MutPtr) -> MutPtr): MutPtr {
    val layout = rcInnerLayoutForValueLayout(valueLayout)
    // ignore-tidy-undocumented-unchecked
    return Rc.tryAllocateForLayout(valueLayout, allocate, memToRcInner).unwrapOrElse({ handleAllocError(layout) })
}

/**
 * Allocates an `RcInner<T>` with sufficient space for
 * a possibly-unsized inner value where the value has the layout provided,
 * returning an error if allocation fails.
 *
 * The function `memToRcInner` is called with the data pointer
 * and must return back a (potentially fat)-pointer for the `RcInner<T>`.
 */
public fun Rc.Companion.tryAllocateForLayout(valueLayout: Layout, allocate: (Layout) -> Result<NonNull, AllocError>, memToRcInner: (MutPtr) -> MutPtr): Result<MutPtr, AllocError> {
    val layout = rcInnerLayoutForValueLayout(valueLayout)
    // Allocate for the layout.
    val ptr = when (val tryResult = allocate(layout)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // Initialize the RcInner
    val inner = memToRcInner(ptr.asNonNullPtr().asPtr())
    // ignore-tidy-undocumented-unchecked
    run {
        check(Layout.forValueRaw(inner) == layout)
        (addrOfMut(inner.strong)).write(Cell.new(1))
        (addrOfMut(inner.weak)).write(Cell.new(1))
    }
    return Result.Ok(inner)
}

/**
 * Allocates an `RcInner<[T]>` with the given length.
 */
public fun <T> Rc.Companion.allocateForSlice(len: Int, typeLayoutT: TypeLayout<T>): MutPtr {
    // ignore-tidy-undocumented-unchecked
    return Rc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> Global.allocate(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) })
}

/**
 * Copy elements from slice into newly allocated `Rc<[T]>`
 *
 * Unsafe because the caller must either take ownership, bind `T: Copy` or
 * bind `T: TrivialClone`.
 */
public fun <T> Rc.Companion.copyFromSlice(v: List<T>): Rc<List<T>> {
    // ignore-tidy-undocumented-unchecked
    return run {
        val ptr = Rc.allocateForSlice(v.size)
        copyNonoverlapping(v.asPtr(), mutPtrFromRustCast((addrOfMut(ptr.value))), v.size)
        Rc.fromPtr(ptr)
    }
}

/**
 * Constructs an `Rc<[T]>` from an iterator known to be of a certain size.
 *
 * Behavior is undefined should the size be wrong.
 */
public fun <T> Rc.Companion.fromIterExact(iter: Any, len: Int): Rc<List<T>> {
    // Panic guard while cloning T elements.
    // In the event of a panic, elements that have been written
    // into the new RcInner will be dropped, then the memory freed.
    open class Guard<T>(
        public var mem: NonNull,
        public var elems: MutPtr,
        public var layout: Layout,
        public var nElems: Int,
    ) {
        public open fun drop() {
            // ignore-tidy-undocumented-unchecked
            run {
                val slice = rawMutSliceList(this.elems, this.nElems.toInt(), ObjectSlotLayout<T>())
                dropInPlace(slice)
                Global.deallocate(this.mem, this.layout)
            }
        }
    }
    // ignore-tidy-undocumented-unchecked
    return run {
        val ptr = Rc.allocateForSlice(len)
        val mem = (mutPtrFromRustCast(ptr)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        val layout = Layout.forValueRaw(ptr)
        // Pointer to first element
        val elems = mutPtrFromRustCast((addrOfMut(ptr.value)))
        val guard = Guard(mem = NonNull.newUnchecked(mem), elems = elems, layout = layout, nElems = 0)
        for ((i, item) in iter.mapIndexed { index, item -> Pair(index, item) }) {
            write(MutPtr((elems).rawAddress + ((i).toLong() * (ObjectSlotLayout<T>()).strideBytes)), item, typeLayoutT = ObjectSlotLayout<T>())
            guard.nElems += 1
        }
        // All clear. Forget the guard so it doesn't free the new RcInner.
        forget(guard)
        Rc.fromPtr(ptr)
    }
}

/**
 * Allocates an `RcInner<[T]>` with the given length.
 */
public fun <T, A : Allocator> Rc.Companion.allocateForSliceIn(len: Int, alloc: A, typeLayoutT: TypeLayout<T>): MutPtr {
    // ignore-tidy-undocumented-unchecked
    return Rc.allocateForLayout(Layout.array<T>(len, typeLayoutT = typeLayoutT).unwrap(), { layout -> alloc.allocate(layout) }, { mem -> mutPtrFromRustCast(mem.cast().castSlice(len)) })
}

/**
 * Specialization trait used for `From<[T]>`.
 */
public interface RcFromSlice<T, SelfType> {
    public fun fromSlice(slice: List<T>): SelfType
}

public fun <T> rcFromSliceFromSliceRcTSlice(v: List<T>): Rc<List<T>> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromIterExact(v.iter().cloned(), v.size)
}

/**
 * Creates a new `Rc<T>`, with the `Default` value for `T`.
 *
 * # Examples
 *
 * ```kotlin
 * val x: Int = Default.default()
 * check(x == 0)
 * ```
 */
public fun <T> defaultRcT(defaultTFn: () -> T): Rc<T> {
    // ignore-tidy-undocumented-unchecked
    return Rc.fromInner(Box.intoNonNull(Box.write(Box.newUninit(), RcInner(strong = Cell.new(1), weak = Cell.new(1), value = defaultTFn()))))
}

/**
 * Creates an empty `String` inside an `Rc`.
 *
 * This may or may not share an allocation with other Rcs on the same thread.
 */
public fun defaultRcString(): Rc<String> {
    val rc: Rc<String> = Rc.default()
    // SAFETY: `[UByte]` has the same layout as `String`.
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Creates an empty `[T]` inside an `Rc`.
 *
 * This may or may not share an allocation with other Rcs on the same thread.
 */
public fun <T> defaultRcTSlice(): Rc<List<T>> {
    val arr: List<T> = listOf()
    return Rc.from(arr)
}

public fun <T> defaultPinRcT(): Pin<Rc<T>> {
    // SAFETY: We own and create the pinned pointer.
    return Pin.newUnchecked(Rc.default())
}

public interface RcEqIdent<T, A : Allocator> {
    public fun eq(other: Rc<T, A>): Boolean

    public fun ne(other: Rc<T, A>): Boolean
}

// Note to allow specializing on `Eq` even though `Eq` has a method.
public interface MarkerEq

/**
 * Converts a generic type `T` into an `Rc<T>`
 *
 * The conversion allocates on the heap and moves `t`
 * from the stack into it.
 *
 * # Example
 * ```kotlin
 * val x = 5
 * val rc = 5
 * check(Rc.from(x) == rc)
 * ```
 */
public fun <T> fromRcT(t: T): Rc<T> = Rc.new(t)

/**
 * Converts a [`[T; N]`](prim@array) into an `Rc<[T]>`.
 *
 * The conversion moves the array into a newly allocated `Rc`.
 *
 * # Example
 *
 * ```kotlin
 * val original: IntArray = intArrayOf(1, 2, 3)
 * val shared: List<Int> = Rc.from(original)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromRcTSlice(v: List<T>, n: Int): Rc<List<T>> = Rc.from(v)

/**
 * Allocates a reference-counted slice and fills it by cloning `v`s items.
 *
 * # Example
 *
 * ```kotlin
 * val original: List<Int> = listOf(1, 2, 3)
 * val shared: List<Int> = Rc.from(original)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromRcTSlice(v: List<T>): Rc<List<T>> = Rc.fromSlice(v)

/**
 * Allocates a reference-counted slice and fills it by cloning `v`s items.
 *
 * # Example
 *
 * ```kotlin
 * var original = mutableListOf(1, 2, 3)
 * val original1: MutableList<Int> = original
 * val shared: List<Int> = Rc.from(original1)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T> fromRcTSlice(v: MutableList<T>): Rc<List<T>> = Rc.from(v)

/**
 * Allocates a reference-counted string slice and copies `v` into it.
 *
 * # Example
 *
 * ```kotlin
 * val shared: String = Rc.from("statue")
 * check("statue" == shared)
 * ```
 */
public fun fromRcString(v: String): Rc<String> {
    val rc = Rc.from(v.encodeToByteArray().map { it.toUByte() })
    // ignore-tidy-undocumented-unchecked
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Allocates a reference-counted slice and moves `v`s items into it.
 *
 * # Example
 *
 * ```kotlin
 * val unique: List<Int> = mutableListOf(1, 2, 3)
 * val shared: List<Int> = Rc.from(unique)
 * check(listOf(1, 2, 3) == shared)
 * ```
 */
public fun <T, A : AllocatorNightly> fromRcTSliceA(v: List<T>): Rc<List<T>, A> {
    // ignore-tidy-undocumented-unchecked
    return run {
        val (vecPtr, len, cap, alloc) = v.intoRawPartsWithAllocator()
        val rcPtr = Rc.allocateForSliceIn(len, alloc)
        copyNonoverlapping(vecPtr, mutPtrFromRustCast((addrOfMut(rcPtr.value))), len)
        // Create a `List<T, A>` with length 0, to deallocate the buffer
        // without dropping its contents or the allocator
        Vec.fromRawPartsIn(vecPtr, 0, cap, alloc)
        Rc.fromPtrIn(rcPtr, alloc)
    }
}

/**
 * Creates a reference-counted pointer from a clone-on-write pointer by
 * copying its content.
 *
 * # Example
 *
 * ```kotlin
 * val cow: String = "eggplant"
 * val shared: String = Rc.from(cow)
 * check("eggplant" == shared)
 * ```
 */
public fun <Owned, B> fromRcB(cow: B): Rc<B> where B : ToOwned<Any?> =
    when (cow) {
        is Cow.Borrowed -> {
            val (s) = cow
            Rc.from(s)
        }
        is Cow.Owned -> {
            val (s1) = cow
            Rc.from(s1)
        }
    }

/**
 * Converts a reference-counted string slice into a byte slice.
 *
 * # Example
 *
 * ```kotlin
 * val string: String = Rc.from("eggplant")
 * val bytes: List<UByte> = Rc.from(string)
 * check("eggplant".encodeToByteArray().map { it.toUByte() } == bytes)
 * ```
 */
public fun fromRcUByteSlice(rc: Rc<String>): Rc<List<UByte>> {
    // SAFETY: `String` has the same layout as `[UByte]`.
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

public fun <T, A : Allocator> tryFromRcTArraynA(boxedSlice: Rc<List<T>, A>, n: Int): Result<Rc<List<T>, A>, Rc<List<T>, A>> =
    if (boxedSlice.len() == n) {
        val (ptr, alloc) = Rc.intoInnerWithAllocator(boxedSlice)
        // ignore-tidy-undocumented-unchecked
        Result.Ok(Rc.fromInnerIn(ptr.cast(), alloc))
    } else {
        Result.Err(boxedSlice)
    }

/**
 * Takes each element in the `Iterator` and collects it into an `Rc<[T]>`.
 *
 * # Performance characteristics
 *
 * ## The general case
 *
 * In the general case, collecting into `Rc<[T]>` is done by first
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
 * // A second allocation for `Rc<[T]>` happens here.
 * ```
 *
 * This will allocate as many times as needed for constructing the `List<T>`
 * and then it will allocate once for turning the `List<T>` into the `Rc<[T]>`.
 *
 * ## Iterators of known length
 *
 * When your `Iterator` implements `TrustedLen` and is of an exact size,
 * a single allocation will be made for the `Rc<[T]>`. For example:
 *
 * ```kotlin
 * val evens: List<UByte> = (0 until 10).toMutableList()
 * // Just a single allocation happens here.
 * ```
 */
public fun <T, I : Iterable<T>> fromIteratorFromIterRcTSlice(iter: I): Rc<List<T>> = ToRcSlice.ToRcSlice(iter.iterator())

/**
 * Specialization trait used for collecting into `Rc<[T]>`.
 */
public interface ToRcSlice<T> : Iterator<T> {
    public fun toRcSlice(): Rc<List<T>>

    public fun next(): T?
}

public fun <T, I : Iterator<T>> I.toRcSlice(): Rc<List<T>> = this.toMutableList().into()

public fun <T, I : TrustedLen<T>> I.toRcSliceT(): Rc<List<T>> {
    // This is the case for a `TrustedLen` iterator.
    val (low, high) = this.sizeHint()
    return if (high != null) {
        val high1 = high
        check(low == high1)
        // SAFETY: We need to ensure that the iterator has an exact length and we have.
        Rc.fromIterExact(this, low)
    } else {
        // TrustedLen contract guarantees that `upperBound == None` implies an iterator
        // length exceeding `Int.MAX`.
        // The default implementation would collect into a vec which would panic.
        // Thus we panic here immediately without invoking `List` code.
        error("capacity overflow")
    }
}

/**
 * `Weak` is a version of [Rc] that holds a non-owning reference to the
 * managed allocation.
 *
 * The allocation is accessed by calling [upgrade] on the `Weak`
 * pointer, which returns an <code>[Option]<[Rc]\<T>></code>.
 *
 * Since a `Weak` reference does not count towards ownership, it will not
 * prevent the value stored in the allocation from being dropped, and `Weak` itself makes no
 * guarantees about the value still being present. Thus it may return [None]
 * when [upgrade]d. Note however that a `Weak` reference *does* prevent the allocation
 * itself (the backing store) from being deallocated.
 *
 * A `Weak` pointer is useful for keeping a temporary reference to the allocation
 * managed by [Rc] without preventing its inner value from being dropped. It is also used to
 * prevent circular references between [Rc] pointers, since mutual owning references
 * would never allow either [Rc] to be dropped. For example, a tree could
 * have strong [Rc] pointers from parent nodes to children, and `Weak`
 * pointers from children back to their parents.
 *
 * The typical way to obtain a `Weak` pointer is to call [Rc.downgrade].
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
    // will ever have because RcInner has alignment at least 2.
    public var ptr: NonNull = ptrCtorArg
        internal set

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
     * val weak = Rc.downgrade(strong)
     * // Both point to the same object
     * check(ptr.eq(strong, weak.asPtr()))
     * // The strong here keeps it alive, so we can still access the object.
     * check("hello" == weak.asPtr())
     * (strong).let { }
     * // But not any more. We can do weak.asPtr(), but accessing the pointer would lead to
     * // undefined behavior.
     * // assertEq("hello", unchecked { &*weak.asPtr() });
     * ```
     *
     * [nullVal]: ptr.nullVal
     */
    public open fun asPtr(): ConstPtr {
        val ptr: MutPtr = NonNull.asPtr(this.ptr)
        return if (isDangling(ptr)) {
            // If the pointer is dangling, we return the sentinel directly. This cannot be
            // a valid payload address, as the payload is at least as aligned as RcInner (Int).
            ConstPtr(ptr.rawAddress, ptr.rawMeta)
        } else {
            // SAFETY: if isDangling returns false, then the pointer is dereferenceable.
            // The payload may be dropped at this point, and we have to maintain provenance,
            // so use raw pointer manipulation.
            addrOfMut((ObjectSlotLayout<RcInner<T>>()).readNative((ptr).rawAddress).value)
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
     * import std.rc.Weak
     *
     * @feature(allocatorExt)
     * public fun doctestEntry() {
     *     val strong = Rc.newIn("hello".toOwned(), System)
     *     val weak = Rc.downgrade(strong)
     *     val (raw, alloc) = weak.intoRawWithAllocator()
     *     check(1 == Rc.weakCount(strong))
     *     check("hello" == raw)
     *     (Weak.fromRawIn(raw, alloc)).let { }
     *     check(0 == Rc.weakCount(strong))
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
     * Attempts to upgrade the `Weak` pointer to an [Rc], delaying
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
     * val weakFive = Rc.downgrade(five)
     * val strongFive: Rc? = weakFive.upgrade()
     * check(strongFive != null)
     * // Destroy all strong pointers.
     * (strongFive).let { }
     * (five).let { }
     * check(weakFive.upgrade() == null)
     * ```
     */
    public open fun upgrade(): Rc<T, A>? {
        val inner = (this.inner() ?: return null)
        return if (inner.strong() == 0) {
            null
        } else {
            // ignore-tidy-undocumented-unchecked
            run {
                inner.incStrong()
                Rc.fromInnerIn(this.ptr, this.alloc.clone())
            }
        }
    }

    /**
     * Gets the number of strong (`Rc`) pointers pointing to this allocation.
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
                inner.strong()
            }
        }

    /**
     * Gets the number of `Weak` pointers pointing to this allocation.
     *
     * If no strong pointers remain, this will return zero.
     */
    public open fun weakCount(): Int =
        when (val matchVal = this.inner()) {
            null -> {
                0
            }
            else -> {
                val inner = matchVal
                if (inner.strong() > 0) {
                    inner.weak() - 1
                    // subtract the implicit weak ptr
                } else {
                    0
                }
            }
        }

    /**
     * Returns `None` when the pointer is dangling and there is no allocated `RcInner`,
     * (i.e., when this `Weak` was created by `Weak.new`).
     */
    public open fun inner(): WeakInner? =
        if (isDangling(this.ptr.asPtr())) {
            null
        } else {
            // We are careful to *not* create a reference covering the "data" field, as
            // the field may be mutated concurrently (for example, if the last `Rc`
            // is dropped, the data field will be dropped in-place).
            // ignore-tidy-undocumented-unchecked
            run {
                val ptr = this.ptr.asPtr()
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
     * val first = Rc.downgrade(firstRc)
     * val second = Rc.downgrade(firstRc)
     * check(first.ptrEq(second))
     * val thirdRc = 5
     * val third = Rc.downgrade(thirdRc)
     * check(!first.ptrEq(third))
     * ```
     *
     * Comparing `Weak.new`.
     *
     * ```kotlin
     * import std.rc.Weak
     *
     * val first = Weak.new()
     * val second = Weak.new()
     * check(first.ptrEq(second))
     * val thirdRc = Rc.new()
     * val third = Rc.downgrade(thirdRc)
     * check(!first.ptrEq(third))
     * ```
     */
    public open fun ptrEq(other: Weak<T, A>): Boolean = addrEq(this.ptr.asPtr(), other.ptr.asPtr())

    /**
     * Drops the `Weak` pointer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.Weak
     *
     * open class Foo {
     *     public open fun drop() {
     *         println("dropped!")
     *     }
     * }
     * val foo = Foo()
     * val weakFoo = Rc.downgrade(foo)
     * val otherWeakFoo = Weak.clone(weakFoo)
     * (weakFoo).let { }
     * // Doesn't print anything
     * (foo).let { }
     * // Prints "dropped!"
     * check(otherWeakFoo.upgrade() == null)
     * ```
     */
    public open fun drop() {
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
        inner1.decWeak()
        // the weak count starts at 1, and will only go to zero if all
        // the strong pointers have disappeared.
        if (inner1.weak() == 0) {
            // ignore-tidy-undocumented-unchecked
            run {
                this.alloc.deallocate(this.ptr.cast(), Layout.forValueRaw(this.ptr.asPtr()))
            }
        }
    }

    /**
     * Makes a clone of the `Weak` pointer that points to the same allocation.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.Weak
     *
     * val weakFive = Rc.downgrade(5)
     * Weak.clone(weakFive)
     * ```
     */
    public open fun clone(): Weak<T, A> {
        when (val matchVal = this.inner()) {
            null -> {}
            else -> {
                val inner = matchVal
                inner.incWeak()
            }
        }
        return Weak(ptr = this.ptr, alloc = this.alloc.clone())
    }

    public open fun fmt(f: StringBuilder) {
        f.append("(Weak)")
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
         * Constructs a new `Weak<T>`, without allocating any memory, technically in the provided
         * allocator.
         * Calling [upgrade] on the return value always gives [None].
         *
         * [upgrade]: Weak.upgrade
         *
         * # Examples
         *
         * ```kotlin
         * import std.rc.Weak
         *
         * val empty: Weak<Long> = Weak.new()
         * check(empty.upgrade() == null)
         * ```
         */
        public fun <T, A : Allocator> newIn(alloc: A): Weak<T, A> = Weak(ptr = NonNull.withoutProvenance(NonZeroUsize.MAX), alloc = alloc)

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
         * weak reference, and `ptr` must point to a block of memory allocated by `alloc`.
         *
         * It is allowed for the strong count to be 0 at the time of calling this. Nevertheless, this
         * takes ownership of one weak reference currently represented as a raw pointer (the weak
         * count is not modified by this operation) and therefore it must be paired with a previous
         * call to [intoRaw].
         *
         * # Examples
         *
         * ```kotlin
         * import std.rc.Weak
         *
         * val strong = "hello".toOwned()
         * val raw1 = Rc.downgrade(strong).intoRaw()
         * val raw2 = Rc.downgrade(strong).intoRaw()
         * check(2 == Rc.weakCount(strong))
         * check("hello" == Weak.fromRaw(raw1).upgrade().unwrap())
         * check(1 == Rc.weakCount(strong))
         * (strong).let { }
         * // Decrement the last weak count.
         * check(Weak.fromRaw(raw2).upgrade() == null)
         * ```
         *
         * [intoRaw]: Weak.intoRaw
         * [upgrade]: Weak.upgrade
         * [new]: Weak.new
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
                    // Thus, we reverse the offset to get the whole RcInner.
                    // SAFETY: the pointer originated from a Weak, so this offset is safe.
                    (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
                }
            // SAFETY: we now have recovered the original Weak pointer, so can create the Weak.
            return Weak(ptr = NonNull.newUnchecked(ptr1), alloc = alloc)
        }
    }
}

// SAFETY: `Weak.clone` doesn't access any `Cell`s which could contain the `Weak` being cloned.

/**
 * Constructs a new `Weak<T>`, without allocating any memory.
 * Calling [upgrade] on the return value always gives [None].
 *
 * [upgrade]: Weak.upgrade
 *
 * # Examples
 *
 * ```kotlin
 * import std.rc.Weak
 *
 * val empty: Weak<Long> = Weak.new()
 * check(empty.upgrade() == null)
 * ```
 */
public fun <T> Weak.Companion.new(): Weak<T> = Weak(ptr = NonNull.withoutProvenance(NonZeroUsize.MAX), alloc = Global)

public fun isDangling(ptr: ConstPtr): Boolean = ((ConstPtr((ptr).rawAddress))).rawAddress.toInt() == Int.MAX_VALUE

/**
 * Helper type to allow accessing the reference counts without
 * making any assertions about the data field.
 */
public open class WeakInner internal constructor(
    weakCtorArg: Cell<Int>,
    strongCtorArg: Cell<Int>,
) : RcInnerPtr {
    public var weak: Cell<Int> = weakCtorArg
        internal set

    public var strong: Cell<Int> = strongCtorArg
        internal set

    public override fun weakRef(): Cell<Int> = this.weak

    public override fun strongRef(): Cell<Int> = this.strong

    public companion object {
        public operator fun invoke(
            weak: Cell<Int>,
            strong: Cell<Int>,
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
 * weak reference, and `ptr` must point to a block of memory allocated by the global allocator.
 *
 * It is allowed for the strong count to be 0 at the time of calling this. Nevertheless, this
 * takes ownership of one weak reference currently represented as a raw pointer (the weak
 * count is not modified by this operation) and therefore it must be paired with a previous
 * call to [intoRaw].
 *
 * # Examples
 *
 * ```kotlin
 * import std.rc.Weak
 *
 * val strong = "hello".toOwned()
 * val raw1 = Rc.downgrade(strong).intoRaw()
 * val raw2 = Rc.downgrade(strong).intoRaw()
 * check(2 == Rc.weakCount(strong))
 * check("hello" == Weak.fromRaw(raw1).upgrade().unwrap())
 * check(1 == Rc.weakCount(strong))
 * (strong).let { }
 * // Decrement the last weak count.
 * check(Weak.fromRaw(raw2).upgrade() == null)
 * ```
 *
 * [intoRaw]: Weak.intoRaw
 * [upgrade]: Weak.upgrade
 * [new]: Weak.new
 */
public fun <T> Weak.Companion.fromRaw(ptr: ConstPtr): Weak<T> {
    // SAFETY: Upheld by caller.
    return Weak.fromRawIn(ptr, Global)
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
 * import std.rc.Weak
 *
 * val strong = "hello".toOwned()
 * val weak = Rc.downgrade(strong)
 * val raw = weak.intoRaw()
 * check(1 == Rc.weakCount(strong))
 * check("hello" == raw)
 * (Weak.fromRaw(raw)).let { }
 * check(0 == Rc.weakCount(strong))
 * ```
 *
 * [fromRaw]: Weak.fromRaw
 * [asPtr]: Weak.asPtr
 */
public fun <T> Weak<T>.intoRaw(): ConstPtr = this.asPtr()

/**
 * Constructs a new `Weak<T>`, without allocating any memory.
 * Calling [upgrade] on the return value always gives [None].
 *
 * [upgrade]: Weak.upgrade
 *
 * # Examples
 *
 * ```kotlin
 * import std.rc.Weak
 *
 * val empty: Weak<Long> = Default.default()
 * check(empty.upgrade() == null)
 * ```
 */
public fun <T> defaultWeakT(): Weak<T> = Weak.new()

// NOTE: If you mem.forget Rcs (or Weaks), drop is skipped and the ref-count
// is not decremented, meaning the ref-count can overflow, and then you can
// free the allocation while outstanding Rcs (or Weaks) exist, which would be
// unsound. We abort because this is such a degenerate scenario that we don't
// care about what happens -- no real program should ever experience this.
//
// This should have negligible overhead since you don't actually need to
// clone these much in Rust thanks to ownership and move-semantics.
public interface RcInnerPtr {
    public fun weakRef(): Cell<Int>

    public fun strongRef(): Cell<Int>

    public fun strong(): Int = this.strongRef().get()

    public fun incStrong() {
        val strong = this.strong()
        // We insert an `assume` here to hint LLVM at an otherwise
        // missed optimization.
        // SAFETY: The reference count will never be zero when this is
        // called.
        run {
            assertUnchecked(strong != 0)
        }
        val strong1 = strong.wrappingAdd(1)
        this.strongRef().set(strong1)
        // We want to abort on overflow instead of dropping the value.
        // Checking for overflow after the store instead of before
        // allows for slightly better code generation.
        if (strong1 == 0) {
            abortImmediate()
        }
    }

    public fun decStrong() {
        this.strongRef().set(this.strong() - 1)
    }

    public fun weak(): Int = this.weakRef().get()

    public fun incWeak() {
        val weak = this.weak()
        // We insert an `assume` here to hint LLVM at an otherwise
        // missed optimization.
        // SAFETY: The reference count will never be zero when this is
        // called.
        run {
            assertUnchecked(weak != 0)
        }
        val weak1 = weak.wrappingAdd(1)
        this.weakRef().set(weak1)
        // We want to abort on overflow instead of dropping the value.
        // Checking for overflow after the store instead of before
        // allows for slightly better code generation.
        if (weak1 == 0) {
            abortImmediate()
        }
    }

    public fun decWeak() {
        this.weakRef().set(this.weak() - 1)
    }
}

/**
 * Gets the offset within an `RcInner` for the payload behind a pointer.
 *
 * # Safety
 *
 * The pointer must point to (and have valid metadata for) a previously
 * valid instance of T, but the T is allowed to be dropped.
 */
public fun dataOffset(ptr: ConstPtr): Int {
    // Align the unsized value to the end of the RcInner.
    // Because RcInner is repr(C), it will always be the last field in memory.
    // SAFETY: since the only unsized types possible are slices, trait objects,
    // and extern types, the input safety requirement is currently enough to
    // satisfy the requirements of Alignment.ofValRaw; this is an implementation
    // detail of the language that must not be relied upon outside of std.
    return dataOffsetAlignment(Alignment.ofValRaw(ptr))
}

public fun dataOffsetAlignment(alignment: Alignment): Int {
    val layout = Layout.new<RcInner<Unit>>(typeLayoutT = ObjectSlotLayout<RcInner<Unit>>())
    return layout.size() + layout.paddingNeededFor(alignment)
}

/**
 * A uniquely owned [Rc].
 *
 * This represents an `Rc` that is known to be uniquely owned -- that is, have exactly one strong
 * reference. Multiple weak pointers can be created, but attempts to upgrade those to strong
 * references will fail unless the `UniqueRc` they point to has been converted into a regular `Rc`.
 *
 * Because they are uniquely owned, the contents of a `UniqueRc` can be freely mutated. A common
 * use case is to have an object be mutable during its initialization phase but then have it become
 * immutable and converted to a normal `Rc`.
 *
 * This can be used as a flexible way to create cyclic data structures, as in the example below.
 *
 * ```kotlin
 * import std.rc.UniqueRc
 * import std.rc.Weak
 *
 * @feature(uniqueRcArc)
 * public fun doctestEntry() {
 *     open class Gadget(public var me: Weak<Gadget>)
 *     fun createGadget(): Gadget? {
 *         var rc = UniqueRc.new(Gadget(me = Weak.new()))
 *         rc.me = UniqueRc.downgrade(rc)
 *         return UniqueRc.intoRc(rc)
 *     }
 *     createGadget()!!
 * }
 * ```
 *
 * An advantage of using `UniqueRc` over [Rc.newCyclic] to build cyclic data structures is that
 * [Rc.newCyclic]s `dataFn` parameter cannot be async or return a [Result]. As shown in the
 * previous example, `UniqueRc` allows for more flexibility in the construction of cyclic data,
 * including fallible or async constructors.
 */
public open class UniqueRc<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    // Define the ownership of `RcInner<T>` for drop-check
    public var marker: Unit,
    // Invariance is necessary for soundness: once other `Weak`
    // references exist, we already have a form of shared mutability!
    public var marker2: Unit,
    public var alloc: A,
) : PinSafePointer, DerefPure, Pointer, BorrowMut<T>, AsRef<T>, AsMut<T> {
    public var ptr: NonNull = ptrCtorArg
        internal set

    // @unstable(feature = "uniqueRcArc", issue = "112566")

    // @unstable(feature = "uniqueRcArc", issue = "112566")

    // Not necessary for correctness since `UniqueRc` contains `NonNull`,
    // but having an explicit negative impl is nice for documentation purposes
    // and results in nicer error messages.

    // Not necessary for correctness since `UniqueRc` contains `NonNull`,
    // but having an explicit negative impl is nice for documentation purposes
    // and results in nicer error messages.

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtDebug(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtPointer(f: StringBuilder) {
        Pointer.fmt((this), f)
    }

    public open fun borrow(): T = this

    public open fun borrowMut(): T = this

    public open fun asRef(): T = this

    public open fun asMut(): T = this

    /**
     * Equality for two `UniqueRc`s.
     *
     * Two `UniqueRc`s are equal if their inner values are equal.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five == UniqueRc.new(5))
     * }
     * ```
     */
    public open fun eq(other: UniqueRc<T, A>): Boolean = PartialEq.eq(this, other)

    /**
     * Inequality for two `UniqueRc`s.
     *
     * Two `UniqueRc`s are not equal if their inner values are not equal.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five != UniqueRc.new(6))
     * }
     * ```
     */
    public open fun ne(other: UniqueRc<T, A>): Boolean = PartialEq.ne(this, other)

    /**
     * Partial comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `partialCmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(Ordering.Less == five.partialCmp(UniqueRc.new(6)))
     * }
     * ```
     */
    public open fun partialCmp(other: UniqueRc<T, A>): Ordering? = this.partialCmp(other)

    /**
     * Less-than comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `<` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five < UniqueRc.new(6))
     * }
     * ```
     */
    public open fun lt(other: UniqueRc<T, A>): Boolean = this < other

    /**
     * Less than or equal to' comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `<=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five <= UniqueRc.new(5))
     * }
     * ```
     */
    public open fun le(other: UniqueRc<T, A>): Boolean = this <= other

    /**
     * Greater-than comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `>` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five > UniqueRc.new(4))
     * }
     * ```
     */
    public open fun gt(other: UniqueRc<T, A>): Boolean = this > other

    /**
     * Greater than or equal to' comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `>=` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(five >= UniqueRc.new(5))
     * }
     * ```
     */
    public open fun ge(other: UniqueRc<T, A>): Boolean = this >= other

    /**
     * Comparison for two `UniqueRc`s.
     *
     * The two are compared by calling `cmp()` on their inner values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     * import std.rc.UniqueRc
     *
     * @feature(uniqueRcArc)
     * public fun doctestEntry() {
     *     val five = UniqueRc.new(5)
     *     check(Ordering.Less == five.cmp(UniqueRc.new(6)))
     * }
     * ```
     */
    public open fun cmp(other: UniqueRc<T, A>): Ordering = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun inner(): RcInner<T> {
        // SAFETY: while this UniqueRc is alive we're guaranteed that the inner pointer is valid.
        return this.ptr.asRef()
    }

    public open fun deref(): T {
        // SAFETY: This pointer was allocated at creation time so we know it is valid.
        return this.ptr.asRef().value
    }

    public open fun derefMut(): T {
        // SAFETY: This pointer was allocated at creation time so we know it is valid. We know we
        // have unique ownership and therefore it's safe to make a mutable reference because
        // `UniqueRc` owns the only strong reference to itself.
        return this.ptr.asPtr().value
    }

    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        run {
            // destroy the contained object
            dropInPlace(DerefMut.derefMut(this))
            // remove the implicit "strong weak" pointer now that we've destroyed the contents.
            this.ptr.asRef().decWeak()
            if (this.ptr.asRef().weak() == 0) {
                this.alloc.deallocate(this.ptr.cast(), Layout.forValueRaw(this.ptr.asPtr()))
            }
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            marker: Unit,
            marker2: Unit,
            alloc: A,
        ): UniqueRc<T, A> = UniqueRc<T, A>(
            ptrCtorArg = ptr,
            marker = marker,
            marker2 = marker2,
            alloc = alloc,
        )

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Creates a new `UniqueRc` in the provided allocator.
         *
         * Weak references to this `UniqueRc` can be created with [UniqueRc.downgrade]. Upgrading
         * these weak references will fail before the `UniqueRc` has been converted into an [Rc].
         * After converting the `UniqueRc` into an [Rc], any weak references created beforehand will
         * point to the new [Rc].
         */
        public fun <T, A : Allocator> newIn(value: T, alloc: A): UniqueRc<T, A> {
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(Box.newIn(RcInner(strong = Cell.new(0), weak = Cell.new(1), value = value), alloc))
            return UniqueRc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc1)
        }

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Like [`newIn`](Self.newIn), but returns an error if the allocation
         * fails, instead of calling [handleAllocError].
         */
        public fun <T, A : Allocator> tryNewIn(value: T, alloc: A): Result<UniqueRc<T, A>, AllocError> {
            val (ptr, alloc1) = Box.intoNonNullWithAllocator(when (val tryResult = Box.tryNewIn(RcInner(strong = Cell.new(0), weak = Cell.new(1), value = value), alloc)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
            return Result.Ok(UniqueRc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc1))
        }

        // @unstable(feature = "allocatorApi", issue = "163177")

        /**
         * Consumes the `UniqueRc`, returning its wrapped value and allocator.
         */
        public fun <T, A : Allocator> unwrapWithAllocator(thisRef: UniqueRc<T, A>): Pair<T, A> {
            val innerPtr = thisRef.ptr
            val (dataPtr, alloc) = UniqueRc.intoRawWithAllocator(thisRef)
            // SAFETY: Conceptually moves out of the `UniqueRc`.
            // We do not use the data inside ever again.
            val value = dataPtr.read()
            // Drop the strong-weak ref
            (Weak(ptr = innerPtr, alloc = alloc)).let { }
            return Pair(value, alloc)
        }

        /**
         * Consumes the `UniqueRc`, returning its wrapped value.
         */
        public fun <T, A : Allocator> unwrap(thisRef: UniqueRc<T, A>): T = UniqueRc.unwrapWithAllocator(thisRef).first

        /**
         * Maps the value in a `UniqueRc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `UniqueRc`, and the result is returned,
         * also in a `UniqueRc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `UniqueRc.map(u, f)` instead of `u.map(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.rc.UniqueRc
         *
         * @feature(uniqueRcArc)
         * public fun doctestEntry() {
         *     val r = UniqueRc.new(7)
         *     val new = UniqueRc.map(r, { i -> i + 7 })
         *     check(new == 14)
         * }
         * ```
         */
        public fun <T, A : Allocator, U> map(thisRef: UniqueRc<T, A>, f: (T) -> U, typeLayoutT: TypeLayout<T>, typeLayoutU: TypeLayout<U>): UniqueRc<U, A> =
            if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<U>(typeLayoutT = typeLayoutU) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<U>(typeLayoutT = typeLayoutU) && UniqueRc.weakCount(thisRef) == 0) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = UniqueRc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = UniqueRc.fromRawWithAllocator(ptr.cast(), alloc)
                    UniqueRc.write(allocation, f(value))
                }
            } else {
                val (value, alloc1) = UniqueRc.unwrapWithAllocator(thisRef)
                UniqueRc.newIn(f(value), alloc1)
            }

        /**
         * Attempts to map the value in a `UniqueRc`, reusing the allocation if possible.
         *
         * `f` is called on a reference to the value in the `UniqueRc`, and if the operation succeeds,
         * the result is returned, also in a `UniqueRc`.
         *
         * Note: this is an associated function, which means that you have
         * to call it as `UniqueRc.tryMap(u, f)` instead of `u.tryMap(f)`. This
         * is so that there is no conflict with a method on the inner type.
         *
         * # Examples
         *
         * ```kotlin
         * import std.rc.UniqueRc
         *
         * @feature(smartPointerTryMap)
         * @feature(uniqueRcArc)
         * public fun doctestEntry() {
         *     val b = UniqueRc.new(7)
         *     val new = UniqueRc.tryMap(b, UInt.tryFrom).unwrap()
         *     check(new == 7)
         * }
         * ```
         */
        public fun <T, A : Allocator, R> tryMap(thisRef: UniqueRc<T, A>, f: (T) -> R, typeLayoutT: TypeLayout<T>): TryType where R : Try {
            return if (sizeOf<T>(typeLayoutT = typeLayoutT) == sizeOf<Output>(typeLayoutT = ObjectSlotLayout<Output>()) && alignOf<T>(typeLayoutT = typeLayoutT) == alignOf<Output>(typeLayoutT = ObjectSlotLayout<Output>()) && UniqueRc.weakCount(thisRef) == 0) {
                // ignore-tidy-undocumented-unchecked
                run {
                    val (ptr, alloc) = UniqueRc.intoRawWithAllocator(thisRef)
                    val value = ptr.read()
                    val allocation = UniqueRc.fromRawWithAllocator(ptr.cast(), alloc)
                    UniqueRc.write(allocation, when (val tryResult = f(value)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                }
            } else {
                val (value, alloc1) = UniqueRc.unwrapWithAllocator(thisRef)
                UniqueRc.newIn(when (val tryResult = f(value)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, alloc1)
            }
        }

        public fun <T, A : Allocator> fromRawWithAllocator(ptr: ConstPtr, alloc: A): UniqueRc<T, A> {
            // SAFETY: Upheld by caller
            val offset = dataOffset(ptr)
            // Reverse the offset to find the original RcInner.
            // SAFETY: As above.
            val rcPtr = (ConstPtr((ptr).rawAddress - ((offset).toLong()))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            return UniqueRc(ptr = NonNull.newUnchecked(rcPtr), marker = Unit, marker2 = Unit, alloc = alloc)
        }

        public fun <T, A : Allocator> intoRawWithAllocator(thisRef: UniqueRc<T, A>): Pair<ConstPtr, A> {
            val thisRef1 = thisRef
            // SAFETY: The copy of the allocator stored in `this` is forgotten
            return Pair(UniqueRc.asPtr(thisRef1), read(thisRef1.alloc))
        }

        /**
         * Converts the `UniqueRc` into a regular [Rc].
         *
         * This consumes the `UniqueRc` and returns a regular [Rc] that contains the `value` that
         * is passed to `intoRc`.
         *
         * Any weak references created before this method is called can now be upgraded to strong
         * references.
         */
        public fun <T, A : Allocator> intoRc(thisRef: UniqueRc<T, A>): Rc<T, A> {
            val thisRef1 = thisRef
            // Move the allocator out.
            // SAFETY: `this.alloc` will not be accessed again, nor dropped because it is in
            // a `ManuallyDrop`.
            val alloc: A = read(thisRef1.alloc)
            // SAFETY: This pointer was allocated at creation time so we know it is valid.
            return run {
                // Convert our weak reference into a strong reference
                thisRef1.ptr.asMut().strong.set(1)
                Rc.fromInnerIn(thisRef1.ptr, alloc)
            }
        }

        public fun <T, A : Allocator> weakCount(thisRef: UniqueRc<T, A>): Int = thisRef.inner().weak() - 1

        public fun <T, A : Allocator> asPtr(thisRef: UniqueRc<T, A>): ConstPtr {
            val ptr: MutPtr = NonNull.asPtr(thisRef.ptr)
            // SAFETY: This cannot go through Deref.deref or UniqueRc.inner because
            // this is required to retain raw/mut provenance such that e.g. `getMut` can
            // write through the pointer after the Rc is recovered through `fromRaw`.
            return addrOfMut((ObjectSlotLayout<RcInner<T>>()).readNative((ptr).rawAddress).value)
        }

        public fun <T, A : Allocator> intoInnerWithAllocator(thisRef: UniqueRc<T, A>): Pair<NonNull, A> {
            val thisRef1 = thisRef
            // SAFETY: Pointer is valid for reads.
            return Pair(thisRef1.ptr, read(thisRef1.alloc))
        }

        public fun <T, A : Allocator> fromInnerIn(ptr: NonNull, alloc: A): UniqueRc<T, A> = UniqueRc(ptr = ptr, marker = Unit, marker2 = Unit, alloc = alloc)

        /**
         * Creates a new weak reference to the `UniqueRc`.
         *
         * Attempting to upgrade this weak reference will fail before the `UniqueRc` has been converted
         * to a [Rc] using [UniqueRc.intoRc].
         */
        public fun <T, A : Allocator> downgrade(thisRef: UniqueRc<T, A>): Weak<T, A> {
            // SAFETY: This pointer was allocated at creation time and we guarantee that we only have
            // one strong reference before converting to a regular Rc.
            run {
                thisRef.ptr.asRef().incWeak()
            }
            return Weak(ptr = thisRef.ptr, alloc = thisRef.alloc.clone())
        }
    }
}

// @unstable(feature = "uniqueRcArc", issue = "112566")

public fun <T> fromUniqueRcT(value: T): UniqueRc<T> = UniqueRc.new(value)

// Depends on A = Global

/**
 * Creates a new `UniqueRc`.
 *
 * Weak references to this `UniqueRc` can be created with [UniqueRc.downgrade]. Upgrading
 * these weak references will fail before the `UniqueRc` has been converted into an [Rc].
 * After converting the `UniqueRc` into an [Rc], any weak references created beforehand will
 * point to the new [Rc].
 */
public fun <T> UniqueRc.Companion.new(value: T): UniqueRc<T> = UniqueRc.newIn(value, Global)

/**
 * Like [`new`](Self.new), but returns an error if the allocation
 * fails, instead of calling [handleAllocError].
 */
public fun <T> UniqueRc.Companion.tryNew(value: T): Result<UniqueRc<T>, AllocError> = UniqueRc.tryNewIn(value, Global)

/**
 * Writes the value and converts to `UniqueRc<T, A>`.
 *
 * This method converts similarly to [`assumeInit`](Self.assumeInit) but
 * writes `value` into it before conversion, thus guaranteeing safety.
 */
public fun <T, A : Allocator> UniqueRc.Companion.write(thisRef: UniqueRc<MaybeUninit<T>, A>, value: T): UniqueRc<T, A> {
    // SAFETY: Writing initialises the wrapped value.
    return run {
        thisRef.write(value)
        thisRef.assumeInit()
    }
}

/**
 * Converts to `UniqueRc<T, A>`.
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
public fun <T, A : Allocator> UniqueRc<MaybeUninit<T>, A>.assumeInit(): UniqueRc<T, A> {
    val (ptr, alloc) = UniqueRc.intoInnerWithAllocator(this)
    // SAFETY: Upheld by caller.
    return UniqueRc.fromInnerIn(ptr.cast(), alloc)
}

/**
 * A unique owning pointer to a [RcInner] **that does not imply the contents are initialized,**
 * but will deallocate it (without dropping the value) when dropped.
 *
 * This is a helper for [Rc.makeMut] to ensure correct cleanup on panic.
 * It is nearly a duplicate of `UniqueRc<MaybeUninit<T>, A>` except that it allows `T: !Sized`,
 * which `MaybeUninit` does not.
 */
public open class UniqueRcUninit<T, A : Allocator> internal constructor(
    ptrCtorArg: NonNull,
    public var layoutForValue: Layout,
    public var alloc: A?,
) {
    public var ptr: NonNull = ptrCtorArg
        internal set

    /**
     * Returns the pointer to be written into to initialize the [Rc].
     */
    public open fun dataPtr(): MutPtr {
        val offset = dataOffsetAlignment(this.layoutForValue.alignment())
        // ignore-tidy-undocumented-unchecked
        return mutPtrFromRustCast(this.ptr.asPtr().byteAdd(offset))
    }

    /**
     * Upgrade this into a normal [Rc].
     *
     * # Safety
     *
     * The data must have been initialized (by writing to [Self.dataPtr]).
     */
    public open fun intoRc(): Rc<T, A> {
        val thisRef = this
        val ptr = thisRef.ptr
        val alloc = (thisRef.alloc.also { thisRef.alloc = null })!!
        // SAFETY: The pointer is valid as per `UniqueRcUninit.new`, and the caller is responsible
        // for having initialized the data.
        return Rc.fromPtrIn(ptr.asPtr(), alloc)
    }

    public open fun drop() {
        // SAFETY:
        // * new() produced a pointer safe to deallocate.
        // * We own the pointer unless intoRc() was called, which forgets us.
        run {
            (this.alloc.also { this.alloc = null })!!.deallocate(this.ptr.cast(), rcInnerLayoutForValueLayout(this.layoutForValue))
        }
    }

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            ptr: NonNull,
            layoutForValue: Layout,
            alloc: A?,
        ): UniqueRcUninit<T, A> = UniqueRcUninit<T, A>(
            ptrCtorArg = ptr,
            layoutForValue = layoutForValue,
            alloc = alloc,
        )

        /**
         * Allocates a RcInner with layout suitable to contain `forValue` or a clone of it.
         */
        public fun <T, A : Allocator> new(forValue: T, alloc: A): UniqueRcUninit<T, A> {
            val layout = Layout.forValue(forValue)
            // ignore-tidy-undocumented-unchecked
            val ptr = Rc.allocateForLayout(layout, { layoutForRcInner -> alloc.allocate(layoutForRcInner) }, { mem -> mem.withMetadataOf(constPtrFromRustCast(forValue)) })
            return UniqueRcUninit(ptr = NonNull.new(ptr).unwrap(), layoutForValue = layout, alloc = alloc)
        }

        /**
         * Allocates a RcInner with layout suitable to contain `forValue` or a clone of it,
         * returning an error if allocation fails.
         */
        public fun <T, A : Allocator> tryNew(forValue: T, alloc: A): Result<UniqueRcUninit<T, A>, AllocError> {
            val layout = Layout.forValue(forValue)
            // ignore-tidy-undocumented-unchecked
            val ptr = when (val tryResult = Rc.tryAllocateForLayout(layout, { layoutForRcInner -> alloc.allocate(layoutForRcInner) }, { mem -> mem.withMetadataOf(constPtrFromRustCast(forValue)) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(UniqueRcUninit(ptr = NonNull.new(ptr).unwrap(), layoutForValue = layout, alloc = alloc))
        }
    }
}
