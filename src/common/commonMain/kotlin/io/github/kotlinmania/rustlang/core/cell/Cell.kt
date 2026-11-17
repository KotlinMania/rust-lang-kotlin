// port-lint: source cell.rs
package io.github.kotlinmania.rustlang.core.cell

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.panic.constPanic
import io.github.kotlinmania.rustlang.core.panic.location.Location
import io.github.kotlinmania.rustlang.core.pin.PinSafePointer
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.ptr.fromMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingAdd
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

// Shareable mutable containers.
//
// Rust memory safety is based on this rule: Given an object `T`, it is only possible to
// have one of the following:
//
// - Several immutable references (`T`) to the object (also known as **aliasing**).
// - One mutable reference (`T`) to the object (also known as **mutability**).
//
// This is enforced by the Rust compiler. However, there are situations where this rule is not
// flexible enough. Sometimes it is required to have multiple references to an object and yet
// mutate it.
//
// Shareable mutable containers exist to permit mutability in a controlled manner, even in the
// presence of aliasing. The cell types in this module allow this from a single
// thread (as enforced by not implementing [Sync]).
// If you need to mutate while sharing across threads, each cell type has a
// corresponding [std.sync] type that you can use. See also [atomic] types
// which provide cross thread shared mutability for primitive types.
//
// Values of the cell types types may be mutated through shared references (i.e. the common `T`
// type), whereas most Rust types can only be mutated through unique (`T`) references.
// We say these cell types provide interior mutability' (mutable via `T`), in contrast with
// typical Rust types that exhibit inherited mutability' (mutable only via `T`).
//
// Cell types come in four flavors: `Cell<T>`, `RefCell<T>`, `OnceCell<T>`, and
// `LazyCell<T>`. Each provides a different way of providing safe interior mutability.
//
// [std.sync]: ../sync/index.html
//
// ## `Cell<T>`
//
// [Cell.T] implements interior mutability by moving values in and out of the cell. That is, a
// `T` to the inner value can never be obtained, and the value itself cannot be directly
// obtained without replacing it with something else. This type provides the following
// methods:
//
//  - For types that implement [Copy], the [`get`](Cell.get) method retrieves the current
//    interior value by duplicating it.
//  - For types that implement [Default], the [`take`](Cell.take) method replaces the current
//    interior value with [Default.default] and returns the replaced value.
//  - All types have:
//    - [`replace`](Cell.replace): replaces the current interior value and returns the replaced
//      value.
//    - [`intoInner`](Cell.intoInner): this method consumes the `Cell<T>` and returns the
//      interior value.
//    - [`set`](Cell.set): this method replaces the interior value, dropping the replaced value.
//
// `Cell<T>` is typically used for more simple types where copying or moving values isn't too
// resource intensive (e.g. numbers), and should usually be preferred over other cell types when
// possible. For larger and non-copy types, `RefCell` provides some advantages.
//
// ## `RefCell<T>`
//
// [RefCell.T] uses Rust's scopes to implement "dynamic borrowing", a process whereby one can
// claim temporary, exclusive, mutable access to the inner value. Borrows for `RefCell<T>`s are
// tracked at _runtime_, unlike Rust's native reference types which are entirely tracked
// statically, at compile time.
//
// An immutable reference to a `RefCell`s inner value (`T`) can be obtained with
// [`borrow`](`RefCell.borrow`), and a mutable borrow (`T`) can be obtained with
// [`borrowMut`](`RefCell.borrowMut`). When these functions are called, they first verify that
// Rust's borrow rules will be satisfied: any number of immutable borrows are allowed or a
// single mutable borrow is allowed, but never both. If a borrow is attempted that would violate
// these rules, the thread will panic.
//
// The corresponding [Sync] version of `RefCell<T>` is [RwLock.T].
//
// ## `OnceCell<T>`
//
// [OnceCell.T] is somewhat of a hybrid of `Cell` and `RefCell` that works for values that
// typically only need to be set once. This means that a reference `T` can be obtained without
// moving or copying the inner value (unlike `Cell`) but also without runtime checks (unlike
// `RefCell`). However, once set, its value cannot be updated unless you have a mutable
// reference to the `OnceCell`.
//
// `OnceCell` provides the following methods:
//
// - [`get`](OnceCell.get): obtain a reference to the inner value
// - [`set`](OnceCell.set): set the inner value if it is unset (returns a `Result`)
// - [`getOrInit`](OnceCell.getOrInit): return the inner value, initializing it if needed
// - [`getMut`](OnceCell.getMut): provide a mutable reference to the inner value, only available
//   if you have a mutable reference to the cell itself.
//
// The corresponding [Sync] version of `OnceCell<T>` is [OnceLock.T].
//
// ## `LazyCell<T, F>`
//
// A common pattern with OnceCell is, for a given OnceCell, to use the same function on every
// call to [OnceCell.getOrInit] with that cell. This is what is offered by [LazyCell],
// which pairs cells of `T` with functions of `F`, and always calls `F` before it yields `T`.
// This happens implicitly by simply attempting to dereference the LazyCell to get its contents,
// so its use is much more transparent with a place which has been initialized by a constant.
//
// More complicated patterns that don't fit this description can be built on `OnceCell<T>` instead.
//
// `LazyCell` works by providing an implementation of `implementation Deref` that calls the function,
// so you can just use it by dereference (e.g. `* lazyCell` or `lazyCell.deref()`).
//
// The corresponding [Sync] version of `LazyCell<T, F>` is [LazyLock.T.F].
//
// # When to choose interior mutability
//
// The more common inherited mutability, where one must have unique access to mutate a value, is
// one of the key language elements that enables Rust to reason strongly about pointer aliasing,
// statically preventing crash bugs. Because of that, inherited mutability is preferred, and
// interior mutability is something of a last resort. Since cell types enable mutation where it
// would otherwise be disallowed though, there are occasions when interior mutability might be
// appropriate, or even *must* be used, e.g.
//
// * Introducing mutability `inside` of something immutable
// * Implementation details of logically-immutable methods.
// * Mutating implementations of [Clone].
//
// ## Introducing mutability `inside` of something immutable
//
// Many shared smart pointer types, including [Rc.T] and [Arc.T], provide containers that can
// be cloned and shared between multiple parties. Because the contained values may be
// multiply-aliased, they can only be borrowed with , not . Without cells it would be
// impossible to mutate data inside of these smart pointers at all.
//
// It's very common then to put a `RefCell<T>` inside shared pointer types to reintroduce
// mutability:
//
// ```kotlin
// import std.cell.RefCell
// import std.cell.RefMut
//
// fun main() {
//     val sharedMap: RefCell = RefCell.new(mutableMapOf())
//     // Create a new block to limit the scope of the dynamic borrow
//     run {
//         var map: RefMut = sharedMap.borrowMut()
//         map.insert("africa", 92388)
//         map.insert("kyoto", 11837)
//         map.insert("piccadilly", 11826)
//         map.insert("marbles", 38)
//     }
//     // Note that if we had not let the previous borrow of the cache fall out
//     // of scope then the subsequent borrow would cause a dynamic thread panic.
//     // This is the major hazard of using `RefCell`.
//     val total: Int = sharedMap.borrow().values().sum()
//     println("$total")
// }
// ```
//
// Note that this example uses `Rc<T>` and not `Arc<T>`. `RefCell<T>`s are for single-threaded
// scenarios. Consider using [RwLock.T] or [Mutex.T] if you need shared mutability in a
// multi-threaded situation.
//
// ## Implementation details of logically-immutable methods
//
// Occasionally it may be desirable not to expose in an API that there is mutation happening
// "under the hood". This may be because logically the operation is immutable, but e.g., caching
// forces the implementation to perform mutation; or because you must employ mutation to implement
// a trait method that was originally defined to take `self`.
//
// ```kotlin
// import std.cell.OnceCell
//
// open class Graph(public var edges: List<Pair<Int, Int>>, public var spanTreeCache: OnceCell<List<Pair<Int, Int>>>) {
//     public open fun minimumSpanningTree(): List<Pair<Int, Int>> {
//         return this.spanTreeCache.getOrInit({ this.calcSpanTree() }).clone()
//     }
//
//     public open fun calcSpanTree(): List<Pair<Int, Int>> {
//         // Expensive computation goes here
//         return mutableListOf()
//     }
// }
// ```
//
// ## Mutating implementations of `Clone`
//
// This is simply a special - but common - case of the previous: hiding mutability for operations
// that appear to be immutable. The [`clone`](Clone.clone) method is expected to not change the
// source value, and is declared to take `self`, not `self`. Therefore, any mutation that
// happens in the `clone` method must use cell types. For example, [Rc.T] maintains its
// reference counts within a `Cell<T>`.
//
// ```kotlin
// import std.cell.Cell
// import std.process.abort
// import std.ptr.NonNull
//
// open class RcInner<T>(
//     public var strong: Cell<Int>,
//     public var refcount: Cell<Int>,
//     public var value: T,
// )
// interface RcInnerPtr<T> {
//     public fun inner(): RcInner<T>
//
//     public fun strong(): Int {
//         return this.inner().strong.get()
//     }
//
//     public fun incStrong() {
//         this.inner().strong.set((this.strong().checkedAdd(1) ?: abort()))
//     }
// }
// open class Rc<T>(public var ptr: NonNull, public var phantom: Unit) : RcInnerPtr<T> {
//     public open fun clone(): T {
//         this.incStrong()
//         return Rc(ptr = this.ptr, phantom = Unit)
//     }
//
//     public open fun inner(): RcInner<T> {
//         return this.ptr.asRef()
//     }
// }
// ```
//
// [Arc.T]: ../../std/sync/struct.Arc.html
// [Rc.T]: ../../std/rc/struct.Rc.html
// [RwLock.T]: ../../std/sync/struct.RwLock.html
// [Mutex.T]: ../../std/sync/struct.Mutex.html
// [OnceLock.T]: ../../std/sync/struct.OnceLock.html
// [LazyLock.T.F]: ../../std/sync/struct.LazyLock.html
// [Sync]: ../../std/marker/trait.Sync.html

/**
 * A mutable memory location.
 *
 * # Memory layout
 *
 * `Cell<T>` has the same [memory layout and caveats as
 * `UnsafeCell<T>`](UnsafeCell#memory-layout). In particular, this means that
 * `Cell<T>` has the same in-memory representation as its inner type `T`.
 *
 * # Examples
 *
 * In this example, you can see that `Cell<T>` enables mutation inside an
 * immutable struct. In other words, it enables "interior mutability".
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * open class SomeStruct(public var regularField: UByte, public var specialField: Cell<UByte>)
 * val myStruct = SomeStruct(regularField = 0u, specialField = Cell.new(1))
 * val newValue = 100
 * // ERROR: `myStruct` is immutable
 * // myStruct.regularField = newValue;
 * // WORKS: although `myStruct` is immutable, `specialField` is a `Cell`,
 * // which can always be mutated
 * myStruct.specialField.set(newValue)
 * check(myStruct.specialField.get() == newValue)
 * ```
 *
 * See the [module-level documentation](self) for more.
 */
public open class Cell<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout(),
) {
    public var value: UnsafeCell<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Cell<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Cell<T>(ptr, layout) }, { it.value })

    // Note that this negative impl isn't strictly necessary for correctness,
    // as `Cell` wraps `UnsafeCell`, which is itself `!Sync`.
    // However, given how important `Cell`s `!Sync`-ness is,
    // having an explicit negative impl is nice for documentation purposes
    // and results in nicer error messages.

    public open fun clone(): Cell<T> = Cell.new(this.get(), fieldLayout = this.value.fieldLayout)

    public open fun eq(other: Cell<T>): Boolean = this.get() == other.get()

    public open fun partialCmp(other: Cell<T>): Ordering? = this.get().partialCmp(other.get())

    public open fun lt(other: Cell<T>): Boolean = this.get() < other.get()

    public open fun le(other: Cell<T>): Boolean = this.get() <= other.get()

    public open fun gt(other: Cell<T>): Boolean = this.get() > other.get()

    public open fun ge(other: Cell<T>): Boolean = this.get() >= other.get()

    public open fun cmp(other: Cell<T>): Ordering = this.get().cmp(other.get())

    /**
     * Sets the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * c.set(10)
     * ```
     */
    public open fun set(value: T) {
        this.replace(value)
    }

    /**
     * Swaps the values of two `Cell`s.
     *
     * The difference with `std.mem.swap` is that this function doesn't
     * require a  reference.
     *
     * # Panics
     *
     * This function will panic if `self` and `other` are different `Cell`s that partially overlap.
     * (Using just standard library methods, it is impossible to create such partially overlapping `Cell`s.
     * However, unchecked code is allowed to e.g. create two `Cell<[Int; 2]>` that partially overlap.)
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c1 = Cell.new(5)
     * val c2 = Cell.new(10)
     * c1.swap(c2)
     * check(10 == c1.get())
     * check(5 == c2.get())
     * ```
     */
    public open fun swap(other: Cell<T>) {
        // This function documents that it *will* panic, and intrinsics.isNonoverlapping doesn't
        // do the check in const, so trying to use it here would be inviting unnecessary fragility.
        fun <T> isNonoverlapping(src: ConstPtr, dst: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean {
            val srcUsize = (src).rawAddress.toInt()
            val dstUsize = (dst).rawAddress.toInt()
            val diff = srcUsize.absDiff(dstUsize)
            return diff >= sizeOf<T>(typeLayoutT = typeLayoutT)
        }
        if (eq(this, other)) {
            // Swapping wouldn't change anything.
            return
        }
        if (!isNonoverlapping(this, other)) {
            // See <https://github.com/rust-lang/rust/issues/80778> for why we need to stop here.
            error("`Cell::swap` on overlapping non-identical `Cell`s")
        }
        // SAFETY: This can be risky if called from separate threads, but `Cell`
        // is `!Sync` so this won't happen. This also won't invalidate any
        // pointers since `Cell` makes sure nothing else will be pointing into
        // either of these `Cell`s. We also excluded shenanigans like partially overlapping `Cell`s,
        // so `swap` will just properly copy two full values of type `T` back and forth.
        run {
            kotlin.Pair((this.value), (other.value)).let { (placeReceiver, placeOther) -> val placePrevious = placeReceiver.value; placeReceiver.value = placeOther.value; placeOther.value = placePrevious }
        }
    }

    /**
     * Replaces the contained value with `val`, and returns the old contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val cell = Cell.new(5)
     * check(cell.get() == 5)
     * check(cell.replace(10) == 5)
     * check(cell.get() == 10)
     * ```
     */
    public open fun replace(value: T): T {
        // SAFETY: This can cause data races if called from a separate thread,
        // but `Cell` is `!Sync` so this won't happen.
        return kotlin.Pair((this.value), value).let { (placeReceiver, placeValue) -> val placePrevious = placeReceiver.value; placeReceiver.value = placeValue; placePrevious }
    }

    /**
     * Unwraps the value, consuming the cell.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * val five = c.intoInner()
     * check(five == 5)
     * ```
     */
    public open fun intoInner(): T = this.value.intoInner()

    /**
     * Returns a copy of the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * val five = c.get()
     * ```
     */
    public open fun get(): T {
        // SAFETY: This can cause data races if called from a separate thread,
        // but `Cell` is `!Sync` so this won't happen.
        return (this.value.get()).get()
    }

    /**
     * Updates the contained value using a function.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * c.update({ x -> x + 1 })
     * check(c.get() == 6)
     * ```
     */
    public open fun update(f: (T) -> T) {
        val old = this.get()
        this.set(f(old))
    }

    /**
     * Returns a raw pointer to the underlying data in this cell.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * val ptr = c.asPtr()
     * ```
     */
    public open fun asPtr(): kotlin.reflect.KMutableProperty0<T> = this.value.get()

    /**
     * Returns a mutable reference to the underlying data.
     *
     * This call borrows `Cell` mutably (at compile-time) which guarantees
     * that we possess the only reference.
     *
     * However be cautious: this method expects `self` to be mutable, which is
     * generally not the case when using a `Cell`. If you require interior
     * mutability by reference, consider using `RefCell` which provides
     * run-time checked mutable borrows through its [borrowMut] method.
     *
     * [borrowMut]: RefCell.borrowMut()
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * var c = Cell.new(5)
     * c.getMut() += 1
     * check(c.get() == 6)
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this.value::value
    }

    /**
     * Takes the value of the cell, leaving `Default.default()` in its place.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     *
     * val c = Cell.new(5)
     * val five = c.also { c = null }
     * check(five == 5)
     * check(c.intoInner() == 0)
     * ```
     */
    public open fun take(defaultTFn: () -> T): T = this.replace(defaultTFn())

    /**
     * Get a clone of the `Cell` that contains a copy of the original value.
     *
     * This allows a cheaply `Clone`-able type like an `Rc` to be stored in a `Cell`, exposing the
     * cheaper `clone()` method.
     *
     * # Examples
     *
     * ```kotlin
     * import core.cell.Cell
     *
     * @feature(cellGetCloned)
     * public fun doctestEntry() {
     *     val rc = 1
     *     val c1 = Cell.new(rc)
     *     val c2 = c1.getCloned()
     *     check(c2.intoInner() == 1)
     * }
     * ```
     */
    public open fun getCloned(cloneTFn: (T) -> T = { it }): Cell<T> {
        // SAFETY: T is CloneFromCell, which guarantees that this is sound.
        return Cell.new(cloneTFn((this.asPtr()).get()), fieldLayout = this.value.fieldLayout)
    }

    public companion object {
        public operator fun <T> invoke(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()): Cell<T> = Cell<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()): Cell<T> = Cell<T>(fieldStorage, fieldLayout)

        /**
         * Creates a `Cell<T>`, with the `Default` value for T.
         */
        public fun <T> default(fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): Cell<T> = Cell.new(defaultTFn(), fieldLayout = fieldLayout)

        /**
         * Creates a new `Cell<T>` containing the given value.
         */
        public fun <T> from(t: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): Cell<T> = Cell.new(t, fieldLayout = fieldLayout)

        /**
         * Creates a new `Cell` containing the given value.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Cell
         *
         * val c = Cell.new(5)
         * ```
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): Cell<T> = Cell(value = UnsafeCell.new(value, fieldLayout = fieldLayout), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }))

        /**
         * Returns a `Cell<T>` from a `T`
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Cell
         *
         * val slice: MutableList<Int> = mutableListOf(1, 2, 3)
         * val cellSlice: Cell<List<Int>> = Cell.fromMut(slice)
         * val sliceCell: List<Cell<Int>> = cellSlice.asSliceOfCells()
         * check(sliceCell.size == 3)
         * ```
         */
        public fun <T> fromMut(t: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Cell<T> {
            // SAFETY:  ensures unique access.
            return (TransparentTypeLayout(TransparentTypeLayout(typeLayoutT, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }), { ptr, layout -> Cell<T>(ptr, layout) }, { it.value })).readNative((((mutPtrFromRustCast(t)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }
    }
}

// Allow types that wrap `Cell` to also implement `DispatchFromDyn`
// and become dynamic-compatible method receivers.
// Note that currently `Cell` itself cannot be a method receiver
// because it does not implement Deref.
// In other words:
// `self: Cell<Self>` won't work
// `self: CellWrapper<Self>` becomes possible

public fun <T> Cell<List<T>>.asRef(n: Int): List<Cell<T>> = this.asArrayOfCells(n)

public fun <T> Cell<List<T>>.asRefCellTSlice(n: Int): List<Cell<T>> = this.asArrayOfCells(n)

public fun <T> Cell<List<T>>.asRef(): List<Cell<T>> = this.asSliceOfCells()

/**
 * Returns a `[Cell<T>]` from a `Cell<[T]>`
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * val slice: MutableList<Int> = mutableListOf(1, 2, 3)
 * val cellSlice: Cell<List<Int>> = Cell.fromMut(slice)
 * val sliceCell: List<Cell<Int>> = cellSlice.asSliceOfCells()
 * check(sliceCell.size == 3)
 * ```
 */
public fun <T> Cell<List<T>>.asSliceOfCells(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<Cell<T>> {
    // SAFETY: `Cell<T>` has the same memory layout as `T`.
    return (UnresolvedTypeLayout<List<Cell<T>>>()).readNative((((ConstPtr(this.fieldStorage.rawAddress)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
}

/**
 * Returns a `[Cell<T>; N]` from a `Cell<[T; N]>`
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * var array: IntArray = intArrayOf(1, 2, 3)
 * val cellArray: Cell<IntArray> = Cell.fromMut(array)
 * val arrayCell: List<Cell<Int>> = cellArray.asArrayOfCells()
 * ```
 */
public fun <T> Cell<List<T>>.asArrayOfCells(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<Cell<T>> {
    // SAFETY: `Cell<T>` has the same memory layout as `T`.
    return (fixedListTypeLayout(TransparentTypeLayout(this.fieldLayout, { ptr, layout -> Cell<T>(ptr, layout) }, { it.value }), (n).toInt())).readNative((((ConstPtr(this.fieldStorage.rawAddress)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
}

// Allow potential overlapping implementations in user code

/**
 * Types for which cloning `Cell<Self>` is sound.
 *
 * # Safety
 *
 * Implementing this trait for a type is sound if and only if the following code is sound for T =
 * that type.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.cell.Cell
 *
 * @feature(cellGetCloned)
 * public fun doctestEntry() {
 *     fun <T : CloneFromCell> cloneFromCell(cell: Cell<T>, cloneTFn: (T) -> T = { it }): T {
 *         return cloneTFn(cell.asPtr())
 *     }
 * }
 * ```
 *
 * Importantly, you can't just implement `CloneFromCell` for any arbitrary `Copy` type, e.g. the
 * following is unsound:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.cell.Cell
 *
 * open class Bad(public var field0: Cell<Bad>?, public var field1: UByte) {
 *     public open fun clone(): Bad {
 *         val a: UByte = this.field1
 *         // when self.0 points to self, we write to self.1 while we have a live `UByte` pointing to
 *         // it -- this is UB
 *         this.field0!!.set(Bad(null, 1))
 *         dbg(Pair(a, this))
 *         return Bad(null, 0)
 *     }
 * }
 * // this is not sound
 * // unchecked impl CloneFromCell for Bad<_> ()
 * ```
 */
public interface CloneFromCell

// `CloneFromCell` can be implemented for types that don't have indirection and which don't access
// `Cell`s in their `Clone` implementation. A commonly-used subset is covered here.

/**
 * A mutable memory location with dynamically checked borrow rules
 *
 * See the [module-level documentation](self) for more.
 */
public open class RefCell<T> internal constructor(
    borrowCtorArg: Cell<BorrowCounter>,
    borrowedAtCtorArg: Cell<Location?>,
    public var value: UnsafeCell<T>,
) {
    public var borrow: Cell<BorrowCounter> = borrowCtorArg
        internal set

    // Stores the location of the earliest currently active borrow.
    // This gets updated whenever we go from having zero borrows
    // to having a single borrow. When a borrow occurs, this gets included
    // in the generated `BorrowError`/`BorrowMutError`
    public var borrowedAt: Cell<Location?> = borrowedAtCtorArg
        internal set

    /**
     * Consumes the `RefCell`, returning the wrapped value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val five = c.intoInner()
     * ```
     */
    public open fun intoInner(): T {
        // Since this function takes `self` (the `RefCell`) by value, the
        // compiler statically verifies that it is not currently borrowed.
        return this.value.intoInner()
    }

    /**
     * Replaces the wrapped value with a new one, returning the old value,
     * without deinitializing either one.
     *
     * This function corresponds to [`std.mem.replace`](../mem/fn.replace.html).
     *
     * # Panics
     *
     * Panics if the value is currently borrowed.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val cell = RefCell.new(5)
     * val oldValue = cell.replace(6)
     * check(oldValue == 5)
     * check(cell == RefCell.new(6))
     * ```
     */
    public open fun replace(t: T): T = io.github.kotlinmania.rustlang.core.mem.replace(this.borrowMut(), t)

    /**
     * Replaces the wrapped value with a new one computed from `f`, returning
     * the old value, without deinitializing either one.
     *
     * # Panics
     *
     * Panics if the value is currently borrowed.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val cell = RefCell.new(5)
     * val oldValue = cell.replaceWith({ old -> old + 1 })
     * check(oldValue == 5)
     * check(cell == RefCell.new(6))
     * ```
     */
    public open fun replaceWith(f: (T) -> T): T {
        val mutBorrow = this.borrowMut()
        val replacement = f(mutBorrow)
        return replace(mutBorrow, replacement)
    }

    /**
     * Swaps the wrapped value of `self` with the wrapped value of `other`,
     * without deinitializing either one.
     *
     * This function corresponds to [`std.mem.swap`](../mem/fn.swap.html).
     *
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently borrowed, or
     * if `self` and `other` point to the same `RefCell`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val d = RefCell.new(6)
     * c.swap(d)
     * check(c == RefCell.new(6))
     * check(d == RefCell.new(5))
     * ```
     */
    public open fun swap(other: RefCell<T>) {
        io.github.kotlinmania.rustlang.core.mem.swap(this.borrowMut(), other.borrowMut())
    }

    /**
     * Immutably borrows the wrapped value.
     *
     * The borrow lasts until the returned `Ref` exits scope. Multiple
     * immutable borrows can be taken out at the same time.
     *
     * # Panics
     *
     * Panics if the value is currently mutably borrowed. For a non-panicking variant, use
     * [`tryBorrow`](#method.tryBorrow).
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val borrowedFive = c.borrow()
     * val borrowedFive2 = c.borrow()
     * ```
     *
     * An example of panic:
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val m = c.borrowMut()
     * val b = c.borrow()
     * // this causes a panic
     * ```
     */
    public open fun borrow(): Ref<T> =
        run {
            val matchVal = this.tryBorrow()
            when (matchVal) { is Result.Ok<*, *> -> {
                val b = matchVal.okValue()
                b
            }; is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                panicAlreadyMutablyBorrowed(err)
            } }
        }

    /**
     * Immutably borrows the wrapped value, returning an error if the value is currently mutably
     * borrowed.
     *
     * The borrow lasts until the returned `Ref` exits scope. Multiple immutable borrows can be
     * taken out at the same time.
     *
     * This is the non-panicking variant of [`borrow`](#method.borrow).
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * run {
     *     val m = c.borrowMut()
     *     check(c.tryBorrow().isErr())
     * }
     * run {
     *     val m1 = c.borrow()
     *     check(c.tryBorrow().isOk())
     * }
     * ```
     */
    public open fun tryBorrow(): Result<Ref<T>, BorrowError> =
        when (val matchVal = BorrowRef.new(this.borrow)) {
            null -> Result.Err<Ref<T>, BorrowError>(BorrowError(location = this.borrowedAt.get()!!))
            else -> {
                val b = matchVal
                if (b.borrow.get() == 1) {
                    this.borrowedAt.replace(Location.caller())
                }
                // SAFETY: `BorrowRef` ensures that there is only immutable access
                // to the value while borrowed.
                val value = NonNull.newUnchecked(this.value.get())
                Result.Ok<Ref<T>, BorrowError>(Ref(value = value, borrow = b))
            }
        }

    /**
     * Mutably borrows the wrapped value.
     *
     * The borrow lasts until the returned `RefMut` or all `RefMut`s derived
     * from it exit scope. The value cannot be borrowed while this borrow is
     * active.
     *
     * # Panics
     *
     * Panics if the value is currently borrowed. For a non-panicking variant, use
     * [`tryBorrowMut`](#method.tryBorrowMut).
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new("hello".toOwned())
     * c.borrowMut() = "bonjour".toOwned()
     * check(c.borrow() == "bonjour")
     * ```
     *
     * An example of panic:
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val m = c.borrow()
     * val b = c.borrowMut()
     * // this causes a panic
     * ```
     */
    public open fun borrowMut(): RefMut<T> =
        run {
            val matchVal = this.tryBorrowMut()
            when (matchVal) { is Result.Ok<*, *> -> {
                val b = matchVal.okValue()
                b
            }; is Result.Err<*, *> -> {
                val err = matchVal.errValue()
                panicAlreadyBorrowed(err)
            } }
        }

    /**
     * Mutably borrows the wrapped value, returning an error if the value is currently borrowed.
     *
     * The borrow lasts until the returned `RefMut` or all `RefMut`s derived
     * from it exit scope. The value cannot be borrowed while this borrow is
     * active.
     *
     * This is the non-panicking variant of [`borrowMut`](#method.borrowMut).
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * run {
     *     val m = c.borrow()
     *     check(c.tryBorrowMut().isErr())
     * }
     * check(c.tryBorrowMut().isOk())
     * ```
     */
    public open fun tryBorrowMut(): Result<RefMut<T>, BorrowMutError> =
        when (val matchVal = BorrowRefMut.new(this.borrow)) {
            null -> Result.Err<RefMut<T>, BorrowMutError>(BorrowMutError(location = this.borrowedAt.get()!!))
            else -> {
                val b = matchVal
                run {
                    this.borrowedAt.replace(Location.caller())
                }
                // SAFETY: `BorrowRefMut` guarantees unique access.
                val value = NonNull.newUnchecked(this.value.get())
                Result.Ok<RefMut<T>, BorrowMutError>(RefMut(value = value, borrow = b, marker = Unit))
            }
        }

    /**
     * Returns a raw pointer to the underlying data in this cell.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val ptr = c.asPtr()
     * ```
     */
    public open fun asPtr(): kotlin.reflect.KMutableProperty0<T> = this.value.get()

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this method borrows `RefCell` mutably, it is statically guaranteed
     * that no borrows to the underlying data exist. The dynamic checks inherent
     * in [borrowMut] and most other methods of `RefCell` are therefore
     * unnecessary. Note that this method does not reset the borrowing state if borrows were previously leaked
     * (e.g., via [forget] on a [Ref] or [RefMut]). For that purpose,
     * consider using the unstable [undoLeak] method.
     *
     * This method can only be called if `RefCell` can be mutably borrowed,
     * which in general is only the case directly after the `RefCell` has
     * been created. In these situations, skipping the aforementioned dynamic
     * borrowing checks may yield better ergonomics and runtime-performance.
     *
     * In most situations where `RefCell` is used, it can't be borrowed mutably.
     * Use [borrowMut] to get mutable access to the underlying data then.
     *
     * [borrowMut]: RefCell.borrowMut()
     * [forget]: mem.forget
     * [undoLeak]: RefCell.undoLeak()
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * var c = RefCell.new(5)
     * c.getMut() += 1
     * check(c == RefCell.new(6))
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this.value::value
    }

    /**
     * Undo the effect of leaked guards on the borrow state of the `RefCell`.
     *
     * This call is similar to [getMut] but more specialized. It borrows `RefCell` mutably to
     * ensure no borrows exist and then resets the state tracking shared borrows. This is relevant
     * if some `Ref` or `RefMut` borrows have been leaked.
     *
     * [getMut]: RefCell.getMut()
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * @feature(cellLeak)
     * public fun doctestEntry() {
     *     var c = RefCell.new(0)
     *     forget(c.borrowMut())
     *     check(c.tryBorrow().isErr())
     *     c.undoLeak()
     *     check(c.tryBorrow().isOk())
     * }
     * ```
     */
    public open fun undoLeak(): kotlin.reflect.KMutableProperty0<T> {
        (this.borrow.getMut()).set(UNUSED)
        return this.getMut()
    }

    /**
     * Immutably borrows the wrapped value, returning an error if the value is
     * currently mutably borrowed.
     *
     * # Safety
     *
     * Unlike `RefCell.borrow`, this method is unchecked because it does not
     * return a `Ref`, thus leaving the borrow flag untouched. Mutably
     * borrowing the `RefCell` while the reference returned by this method
     * is alive is undefined behavior.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * run {
     *     val m = c.borrowMut()
     *     check(c.tryBorrowUnguarded().isErr())
     * }
     * run {
     *     val m1 = c.borrow()
     *     check(c.tryBorrowUnguarded().isOk())
     * }
     * ```
     */
    public open fun tryBorrowUnguarded(): Result<T, BorrowError> =
        if (!isWriting(this.borrow.get())) {
            // SAFETY: We check that nobody is actively writing now, but it is
            // the caller's responsibility to ensure that nobody writes until
            // the returned reference is no longer in use.
            // Also, `self.value.get()` refers to the value owned by `self`
            // and is thus guaranteed to be valid for the scope of `self`.
            Result.Ok<T, BorrowError>((this.value.get()).get())
        } else {
            Result.Err<T, BorrowError>(BorrowError(location = this.borrowedAt.get()!!))
        }

    /**
     * Takes the wrapped value, leaving `Default.default()` in its place.
     *
     * # Panics
     *
     * Panics if the value is currently borrowed.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.RefCell
     *
     * val c = RefCell.new(5)
     * val five = c.also { c = null }
     * check(five == 5)
     * check(c.intoInner() == 0)
     * ```
     */
    public open fun take(defaultTFn: () -> T): T = this.replace(defaultTFn())

    /**
     * # Panics
     *
     * Panics if the value is currently mutably borrowed.
     */
    public open fun clone(): RefCell<T> = RefCell.new(this.borrow().clone(), typeLayoutT = UnresolvedTypeLayout<Any?>())

    /**
     * # Panics
     *
     * Panics if `source` is currently mutably borrowed.
     */
    public open fun cloneFrom(source: RefCell<T>) {
        this.getMut().cloneFrom(source.borrow())
    }

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun eq(other: RefCell<T>): Boolean = this.borrow() == other.borrow()

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun partialCmp(other: RefCell<T>): Ordering? = this.borrow().partialCmp(other.borrow())

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun lt(other: RefCell<T>): Boolean = this.borrow() < other.borrow()

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun le(other: RefCell<T>): Boolean = this.borrow() <= other.borrow()

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun gt(other: RefCell<T>): Boolean = this.borrow() > other.borrow()

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun ge(other: RefCell<T>): Boolean = this.borrow() >= other.borrow()

    /**
     * # Panics
     *
     * Panics if the value in either `RefCell` is currently mutably borrowed.
     */
    public open fun cmp(other: RefCell<T>): Ordering = this.borrow().cmp(other.borrow())

    public companion object {
        public operator fun <T> invoke(
            borrow: Cell<BorrowCounter>,
            borrowedAt: Cell<Location?>,
            value: UnsafeCell<T>,
        ): RefCell<T> = RefCell<T>(
            borrowCtorArg = borrow,
            borrowedAtCtorArg = borrowedAt,
            value = value,
        )

        /**
         * Creates a new `RefCell` containing `value`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         *
         * val c = RefCell.new(5)
         * ```
         */
        public fun <T> new(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RefCell<T> = RefCell(value = UnsafeCell.new(value, fieldLayout = typeLayoutT), borrow = Cell.new(UNUSED, fieldLayout = typeLayoutT), borrowedAt = Cell.new(null, fieldLayout = typeLayoutT))

        /**
         * Creates a `RefCell<T>`, with the `Default` value for T.
         */
        public fun <T> default(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): RefCell<T> = RefCell.new(defaultTFn(), typeLayoutT = UnresolvedTypeLayout<Any?>())

        /**
         * Creates a new `RefCell<T>` containing the given value.
         */
        public fun <T> from(t: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RefCell<T> = RefCell.new(t, typeLayoutT = typeLayoutT)
    }
}

/**
 * An error returned by [RefCell.tryBorrow].
 */
public open class BorrowError(
    public var location: Location,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val res = f.writeFmt("RefCell already mutably borrowed; a previous borrow was at ${this.location}")
        return res
    }
}

/**
 * An error returned by [RefCell.tryBorrowMut].
 */
public open class BorrowMutError(
    public var location: Location,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val res = f.writeFmt("RefCell already borrowed; a previous borrow was at ${this.location}")
        return res
    }
}

// This ensures the panicking code is outlined from `borrowMut` for `RefCell`.
public fun panicAlreadyBorrowed(err: BorrowMutError): Nothing = constPanic("RefCell already borrowed", "{err}", err, BorrowMutError, err)

// This ensures the panicking code is outlined from `borrow` for `RefCell`.
public fun panicAlreadyMutablyBorrowed(err: BorrowError): Nothing = constPanic("RefCell already mutably borrowed", "{err}", err, BorrowError, err)

// Positive values represent the number of `Ref` active. Negative values
// represent the number of `RefMut` active. Multiple `RefMut`s can only be
// active at a time if they refer to distinct, nonoverlapping components of a
// `RefCell` (e.g., different ranges of a slice).
//
// `Ref` and `RefMut` are both two words in size, and so there will likely never
// be enough `Ref`s or `RefMut`s in existence to overflow half of the `Int`
// range. Thus, a `BorrowCounter` will probably never overflow or underflow.
// However, this is not a guarantee, as a pathological program could repeatedly
// create and then mem.forget `Ref`s or `RefMut`s. Thus, all code must
// explicitly check for overflow and underflow in order to avoid unsafety, or at
// least behave correctly in the event that overflow or underflow happens (e.g.,
// see BorrowRef.new).
public typealias BorrowCounter = Int

public val UNUSED: BorrowCounter = 0

public fun isWriting(x: BorrowCounter): Boolean = x < UNUSED

public fun isReading(x: BorrowCounter): Boolean = x > UNUSED

public open class BorrowRef internal constructor(
    borrowCtorArg: Cell<BorrowCounter>,
) {
    public var borrow: Cell<BorrowCounter> = borrowCtorArg
        internal set

    public open fun drop() {
        val borrow = this.borrow.get()
        if (false) {
            check(isReading(borrow))
        }
        this.borrow.replace(borrow - 1)
    }

    public open fun clone(): BorrowRef {
        // Since this Ref exists, we know the borrow flag
        // is a reading borrow.
        val borrow = this.borrow.get()
        if (false) {
            check(isReading(borrow))
        }
        // Prevent the borrow counter from overflowing into
        // a writing borrow.
        check(borrow != Int.MAX_VALUE)
        this.borrow.replace(borrow + 1)
        return BorrowRef(borrow = this.borrow)
    }

    public companion object {
        public operator fun invoke(
            borrow: Cell<BorrowCounter>,
        ): BorrowRef = BorrowRef(
            borrowCtorArg = borrow,
        )

        public fun new(borrow: Cell<BorrowCounter>): BorrowRef? {
            val b = borrow.get().wrappingAdd(1)
            return if (!isReading(b)) {
                // Incrementing borrow can result in a non-reading value (<= 0) in these cases:
                // 1. It was < 0, i.e. there are writing borrows, so we can't allow a read borrow
                // due to Rust's reference aliasing rules
                // 2. It was isize.Max (the max amount of reading borrows) and it overflowed
                // into isize.Min (the max amount of writing borrows) so we can't allow
                // an additional read borrow because isize can't represent so many read borrows
                // (this can only happen if you mem.forget more than a small constant amount of
                // `Ref`s, which is not good practice)
                null
            } else {
                // Incrementing borrow can result in a reading value (> 0) in these cases:
                // 1. It was = 0, i.e. it wasn't borrowed, and we are taking the first read borrow
                // 2. It was > 0 and < isize.Max, i.e. there were read borrows, and isize
                // is large enough to represent having one more read borrow
                borrow.replace(b)
                BorrowRef(borrow = borrow)
            }
        }
    }
}

/**
 * Wraps a borrowed reference to a value in a `RefCell` box.
 * A wrapper type for an immutably borrowed value from a `RefCell<T>`.
 *
 * See the [module-level documentation](self) for more.
 */
public open class Ref<T>(
    // NB: we use a pointer instead of `T` to avoid `noalias` violations, because a
    // `Ref` argument doesn't hold immutability for its whole scope, only until it drops.
    // `NonNull` is also covariant over `T`, just like we would have with `T`.
    public var value: NonNull,
    public var borrow: BorrowRef,
) : DerefPure, PinSafePointer {
    public open fun deref(): T {
        // SAFETY: the value is accessible as long as we hold our borrow.
        return (this).value.value.asRef()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = (this).value.fmt(f)

    // The implementations of Deref/DerefMut are not malicious, so we can allow the
    // user to perform unsizing coercions with `Pin<Ref<T>>` pointers if they
    // can manage to create one.

    public companion object {
        /**
         * Copies a `Ref`.
         *
         * The `RefCell` is already immutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `Ref.clone(...)`. A `Clone` implementation or a method would interfere
         * with the widespread use of `r.borrow().clone()` to clone the contents of
         * a `RefCell`.
         */
        public fun <T> clone(orig: Ref<T>): Ref<T> = Ref(value = (orig).value.value, borrow = (orig).value.borrow.clone())

        /**
         * Makes a new `Ref` for a component of the borrowed data.
         *
         * The `RefCell` is already immutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as `Ref.map(...)`.
         * A method would interfere with methods of the same name on the contents
         * of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Ref
         * import std.cell.RefCell
         *
         * val c = RefCell.new(Pair(5, 'b'))
         * val b1: Ref<Pair<UInt, Char>> = c.borrow()
         * val b2: Ref<UInt> = Ref.map(b1, { t -> t.first })
         * check((b2).value == 5u)
         * ```
         */
        public fun <T, U> map(orig: Ref<T>, f: (T) -> U): Ref<U> = Ref(value = NonNull.from(f((orig).value)), borrow = (orig).value.borrow)

        /**
         * Makes a new `Ref` for an optional component of the borrowed data. The
         * original guard is returned as an `Err(..)` if the closure returns
         * `None`.
         *
         * The `RefCell` is already immutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `Ref.filterMap(...)`. A method would interfere with methods of the same
         * name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Ref
         * import std.cell.RefCell
         *
         * val c = RefCell.new(mutableListOf(1, 2, 3))
         * val b1: Ref<List<UInt>> = c.borrow()
         * val b2: Result<Ref<UInt>> = Ref.filterMap(b1, { v -> v.get(1) })
         * check(b2.unwrap() == 2)
         * ```
         */
        public fun <T, U> filterMap(orig: Ref<T>, f: (T) -> U?): Result<Ref<U>, Ref<T>> =
            when (val matchVal = f((orig).value)) {
                null -> Result.Err<Ref<U>, Ref<T>>(orig)
                else -> {
                    val value = matchVal
                    Result.Ok<Ref<U>, Ref<T>>(Ref(value = NonNull.from(value), borrow = (orig).value.borrow))
                }
            }

        /**
         * Tries to makes a new `Ref` for a component of the borrowed data.
         * On failure, the original guard is returned alongside with the error
         * returned by the closure.
         *
         * The `RefCell` is already immutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `Ref.tryMap(...)`. A method would interfere with methods of the same
         * name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Ref
         * import std.cell.RefCell
         * import std.str.Utf8Error
         * import std.str.fromUtf8
         *
         * @feature(refcellTryMap)
         * public fun doctestEntry() {
         *     val c = RefCell.new(mutableListOf(0xF0, 0x9F, 0xA6, 0x80))
         *     val b1: Ref<List<UByte>> = c.borrow()
         *     val b2: Result<Ref<String>> = Ref.tryMap(b1, { v -> fromUtf8(v) })
         *     check(b2.unwrap() == "🦀")
         *     val c1 = RefCell.new(mutableListOf(0xF0, 0x9F, 0xA6))
         *     val b11: Ref<List<UByte>> = c1.borrow()
         *     val b21: Result<Pair<Ref<List<UByte>>, Utf8Error>> = Ref.tryMap(b11, { v -> fromUtf8(v) })
         *     val (b3, e) = b21.unwrapErr()
         *     check(b3 == mutableListOf(0xF0, 0x9F, 0xA6))
         *     check(e.validUpTo() == 0)
         * }
         * ```
         */
        public fun <T, U, E> tryMap(orig: Ref<T>, f: (T) -> Result<U, E>): Result<Ref<U>, Pair<Ref<T>, E>> =
            run {
                val matchVal = f((orig).value)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val value = matchVal.okValue()
                    Result.Ok<Ref<U>, Pair<Ref<T>, E>>(Ref(value = NonNull.from(value), borrow = (orig).value.borrow))
                }; is Result.Err<*, *> -> {
                    val e = matchVal.errValue()
                    Result.Err<Ref<U>, Pair<Ref<T>, E>>(Pair(orig, e))
                } }
            }

        /**
         * Splits a `Ref` into multiple `Ref`s for different components of the
         * borrowed data.
         *
         * The `RefCell` is already immutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `Ref.mapSplit(...)`. A method would interfere with methods of the same
         * name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Ref
         * import std.cell.RefCell
         *
         * val cell = RefCell.new(listOf(1, 2, 3, 4))
         * val borrow = cell.borrow()
         * val (begin, end) = Ref.mapSplit(borrow, { slice -> slice.splitAt(2) })
         * check(begin == listOf(1, 2))
         * check(end == listOf(3, 4))
         * ```
         */
        public fun <T, U, V> mapSplit(orig: Ref<T>, f: (T) -> Pair<U, V>): Pair<Ref<U>, Ref<V>> {
            val (a, b) = f((orig).value)
            val borrow = (orig).value.borrow.clone()
            return Pair(Ref(value = NonNull.from(a), borrow = borrow), Ref(value = NonNull.from(b), borrow = (orig).value.borrow))
        }

        /**
         * Converts into a reference to the underlying data.
         *
         * The underlying `RefCell` can never be mutably borrowed from again and will always appear
         * already immutably borrowed. It is not a good idea to leak more than a constant number of
         * references. The `RefCell` can be immutably borrowed again if only a smaller number of leaks
         * have occurred in total.
         *
         * This is an associated function that needs to be used as
         * `Ref.leak(...)`. A method would interfere with methods of the
         * same name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.Ref
         * import std.cell.RefCell
         *
         * @feature(cellLeak)
         * public fun doctestEntry() {
         *     val cell = RefCell.new(0)
         *     val value = Ref.leak(cell.borrow())
         *     check(value == 0)
         *     check(cell.tryBorrow().isOk())
         *     check(cell.tryBorrowMut().isErr())
         * }
         * ```
         */
        public fun <T> leak(orig: Ref<T>): T {
            // By forgetting this Ref we ensure that the borrow counter in the RefCell can't go back to
            // UNUSED within the scope `b`. Resetting the reference tracking state would require a
            // unique reference to the borrowed RefCell. No further mutable references can be created
            // from the original cell.
            forget((orig).value.borrow)
            // SAFETY: after forgetting, we can form a reference for the rest of scope `b`.
            return (orig).value.value.asRef()
        }
    }
}

public open class BorrowRefMut internal constructor(
    borrowCtorArg: Cell<BorrowCounter>,
) {
    public var borrow: Cell<BorrowCounter> = borrowCtorArg
        internal set

    public open fun drop() {
        val borrow = this.borrow.get()
        if (false) {
            check(isWriting(borrow))
        }
        this.borrow.replace(borrow + 1)
    }

    // Clones a `BorrowRefMut`.
    //
    // This is only valid if each `BorrowRefMut` is used to track a mutable
    // reference to a distinct, nonoverlapping range of the original object.
    // This isn't in a Clone impl so that code doesn't call this implicitly.
    public open fun clone(): BorrowRefMut {
        val borrow = this.borrow.get()
        if (false) {
            check(isWriting(borrow))
        }
        // Prevent the borrow counter from underflowing.
        check(borrow != Int.MIN_VALUE)
        this.borrow.set(borrow - 1)
        return BorrowRefMut(borrow = this.borrow)
    }

    public companion object {
        public operator fun invoke(
            borrow: Cell<BorrowCounter>,
        ): BorrowRefMut = BorrowRefMut(
            borrowCtorArg = borrow,
        )

        public fun new(borrow: Cell<BorrowCounter>): BorrowRefMut? {
            // NOTE: Unlike BorrowRefMut.clone, new is called to create the initial
            // mutable reference, and so there must currently be no existing
            // references. Thus, while clone increments the mutable refcount, here
            // we explicitly only allow going from UNUSED to UNUSED - 1.
            return when (borrow.get()) {
                UNUSED -> {
                    borrow.replace(UNUSED - 1)
                    BorrowRefMut(borrow = borrow)
                }
                else -> null
            }
        }
    }
}

/**
 * A wrapper type for a mutably borrowed value from a `RefCell<T>`.
 *
 * See the [module-level documentation](self) for more.
 */
public open class RefMut<T>(
    // NB: we use a pointer instead of `T` to avoid `noalias` violations, because a
    // `RefMut` argument doesn't hold exclusivity for its whole scope, only until it drops.
    public var value: NonNull,
    public var borrow: BorrowRefMut,
    // `NonNull` is covariant over `T`, so we need to reintroduce invariance.
    public var marker: Unit,
) : DerefPure, PinSafePointer {
    public open fun deref(): T {
        // SAFETY: the value is accessible as long as we hold our borrow.
        return (this).value.value.asRef()
    }

    public open fun derefMut(): T {
        // SAFETY: the value is accessible as long as we hold our borrow.
        return (this).value.value.asMut()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = (this).value.fmt(f)

    // The implementations of Deref/DerefMut are not malicious, so we can allow the
    // user to perform unsizing coercions with `Pin<RefMut<T>>` pointers if they
    // can manage to create one.

    public companion object {
        /**
         * Makes a new `RefMut` for a component of the borrowed data, e.g., an enum
         * variant.
         *
         * The `RefCell` is already mutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RefMut.map(...)`. A method would interfere with methods of the same
         * name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         * import std.cell.RefMut
         *
         * val c = RefCell.new(Pair(5, 'b'))
         * run {
         *     val b1: RefMut<Pair<UInt, Char>> = c.borrowMut()
         *     var b2: RefMut<UInt> = RefMut.map(b1, { t -> t.first })
         *     check((b2).value == 5u)
         *     b2 = 42
         * }
         * check(c.borrow() == Pair(42, 'b'))
         * ```
         */
        public fun <T, U> map(orig: RefMut<T>, f: (T) -> U): RefMut<U> {
            val value = NonNull.from(f((orig).value))
            return RefMut(value = value, borrow = (orig).value.borrow, marker = Unit)
        }

        /**
         * Makes a new `RefMut` for an optional component of the borrowed data. The
         * original guard is returned as an `Err(..)` if the closure returns
         * `None`.
         *
         * The `RefCell` is already mutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RefMut.filterMap(...)`. A method would interfere with methods of the
         * same name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         * import std.cell.RefMut
         *
         * val c = RefCell.new(mutableListOf(1, 2, 3))
         * run {
         *     val b1: RefMut<List<UInt>> = c.borrowMut()
         *     var b2: Result<RefMut<UInt>> = RefMut.filterMap(b1, { v -> v.getMut(1) })
         *     if (b2 is Ok) {
         *         val (b21) = b2
         *         b21 += 2
         *     }
         * }
         * check(c.borrow() == mutableListOf(1, 4, 3))
         * ```
         */
        public fun <T, U> filterMap(orig: RefMut<T>, f: (T) -> U?): Result<RefMut<U>, RefMut<T>> {
            // SAFETY: function holds onto an exclusive reference for the duration
            // of its call through `orig`, and the pointer is only de-referenced
            // inside of the function call never allowing the exclusive reference to
            // escape.
            return when (val matchVal = f((orig).value)) {
                null -> Result.Err<RefMut<U>, RefMut<T>>(orig)
                else -> {
                    val value = matchVal
                    Result.Ok<RefMut<U>, RefMut<T>>(RefMut(value = NonNull.from(value), borrow = (orig).value.borrow, marker = Unit))
                }
            }
        }

        /**
         * Tries to makes a new `RefMut` for a component of the borrowed data.
         * On failure, the original guard is returned alongside with the error
         * returned by the closure.
         *
         * The `RefCell` is already mutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RefMut.tryMap(...)`. A method would interfere with methods of the same
         * name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         * import std.cell.RefMut
         * import std.str.Utf8Error
         * import std.str.fromUtf8Mut
         *
         * @feature(refcellTryMap)
         * public fun doctestEntry() {
         *     val c = RefCell.new(mutableListOf(0x68, 0x65, 0x6C, 0x6C, 0x6F))
         *     run {
         *         val b1: RefMut<List<UByte>> = c.borrowMut()
         *         val b2: Result<RefMut<String>> = RefMut.tryMap(b1, { v -> fromUtf8Mut(v) })
         *         var b21 = b2.unwrap()
         *         check(b21 == "hello")
         *         b21.makeAsciiUppercase()
         *     }
         *     check(c.borrow() == "HELLO".encodeToByteArray().map { it.toUByte() })
         *     val c1 = RefCell.new(mutableListOf(0xFF))
         *     val b11: RefMut<List<UByte>> = c1.borrowMut()
         *     val b22: Result<Pair<RefMut<List<UByte>>, Utf8Error>> = RefMut.tryMap(b11, { v -> fromUtf8Mut(v) })
         *     val (b3, e) = b22.unwrapErr()
         *     check(b3 == mutableListOf(0xFF))
         *     check(e.validUpTo() == 0)
         * }
         * ```
         */
        public fun <T, U, E> tryMap(orig: RefMut<T>, f: (T) -> Result<U, E>): Result<RefMut<U>, Pair<RefMut<T>, E>> {
            // SAFETY: function holds onto an exclusive reference for the duration
            // of its call through `orig`, and the pointer is only de-referenced
            // inside of the function call never allowing the exclusive reference to
            // escape.
            return run {
                val matchVal = f((orig).value)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val value = matchVal.okValue()
                    Result.Ok<RefMut<U>, Pair<RefMut<T>, E>>(RefMut(value = NonNull.from(value), borrow = (orig).value.borrow, marker = Unit))
                }; is Result.Err<*, *> -> {
                    val e = matchVal.errValue()
                    Result.Err<RefMut<U>, Pair<RefMut<T>, E>>(Pair(orig, e))
                } }
            }
        }

        /**
         * Splits a `RefMut` into multiple `RefMut`s for different components of the
         * borrowed data.
         *
         * The underlying `RefCell` will remain mutably borrowed until both
         * returned `RefMut`s go out of scope.
         *
         * The `RefCell` is already mutably borrowed, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RefMut.mapSplit(...)`. A method would interfere with methods of the
         * same name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         * import std.cell.RefMut
         *
         * val cell = RefCell.new(listOf(1, 2, 3, 4))
         * val borrow = cell.borrowMut()
         * var (begin, end) = RefMut.mapSplit(borrow, { slice -> slice.splitAtMut(2) })
         * check(begin == listOf(1, 2))
         * check(end == listOf(3, 4))
         * begin.copyFromSlice(listOf(4, 3))
         * end.copyFromSlice(listOf(2, 1))
         * ```
         */
        public fun <T, U, V> mapSplit(orig: RefMut<T>, f: (T) -> Pair<U, V>): Pair<RefMut<U>, RefMut<V>> {
            val borrow = (orig).value.borrow.clone()
            val (a, b) = f((orig).value)
            return Pair(RefMut(value = NonNull.from(a), borrow = borrow, marker = Unit), RefMut(value = NonNull.from(b), borrow = (orig).value.borrow, marker = Unit))
        }

        /**
         * Converts into a mutable reference to the underlying data.
         *
         * The underlying `RefCell` can not be borrowed from again and will always appear already
         * mutably borrowed, making the returned reference the only to the interior.
         *
         * This is an associated function that needs to be used as
         * `RefMut.leak(...)`. A method would interfere with methods of the
         * same name on the contents of a `RefCell` used through `Deref`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.RefCell
         * import std.cell.RefMut
         *
         * @feature(cellLeak)
         * public fun doctestEntry() {
         *     val cell = RefCell.new(0)
         *     val value = RefMut.leak(cell.borrowMut())
         *     check(value == 0)
         *     value = 1
         *     check(cell.tryBorrowMut().isErr())
         * }
         * ```
         */
        public fun <T> leak(orig: RefMut<T>): T {
            // By forgetting this BorrowRefMut we ensure that the borrow counter in the RefCell can't
            // go back to UNUSED within the scope `b`. Resetting the reference tracking state would
            // require a unique reference to the borrowed RefCell. No further references can be created
            // from the original cell within that scope, making the current borrow the only
            // reference for the remaining scope.
            forget((orig).value.borrow)
            // SAFETY: after forgetting, we can form a reference for the rest of scope `b`.
            return (orig).value.value.asMut()
        }
    }
}

/**
 * The core primitive for interior mutability in Rust.
 *
 * If you have a reference `T`, then normally in Rust the compiler performs optimizations based on
 * the knowledge that `T` points to immutable data. Mutating that data, for example through an
 * alias or by transmuting a `T` into a `T`, is considered undefined behavior.
 * `UnsafeCell<T>` opts-out of the immutability guarantee for `T`: a shared reference
 * `UnsafeCell<T>` may point to data that is being mutated. This is called "interior mutability".
 *
 * All other types that allow internal mutability, such as [Cell.T] and [RefCell.T], internally
 * use `UnsafeCell` to wrap their data.
 *
 * Note that only the immutability guarantee for shared references is affected by `UnsafeCell`. The
 * uniqueness guarantee for mutable references is unaffected. As explained below, for the duration
 * of the scope of an , no other reference may exist and no pointer may be used to access
 * that memory; this applies even with `UnsafeCell<T>`.
 *
 * `UnsafeCell` does nothing to avoid data races; they are still undefined behavior. If multiple
 * threads have access to the same `UnsafeCell`, they must follow the usual rules of the
 * [concurrent memory model]: conflicting non-synchronized accesses must be done via the APIs in
 * [core.sync.atomic].
 *
 * The `UnsafeCell` API itself is technically very simple: [get] gives you a raw pointer
 * `* T` to its contents. It is up to _you_ as the abstraction designer to use that raw pointer
 * correctly.
 *
 * [get]: `UnsafeCell.get`
 * [concurrent memory model]: ../sync/atomic/index.html#memory-model-for-atomic-accesses
 *
 * # Aliasing rules
 *
 * The precise Rust aliasing rules are somewhat in flux, but the main points are not contentious:
 *
 * - If you create a safe reference with scope `a` (either a `T` or `T` reference), then
 *   you must not access the data in any way that contradicts that reference for the remainder of
 *   `a`, and you must not create any contradicting references. For example, this means that if
 *   you take the `* T` from an `UnsafeCell<T>` and cast it to a `T`, then the data in `T` must
 *   remain immutable (modulo any `UnsafeCell` data found within `T`, of course) until that
 *   reference's scope expires, and no  reference to this data may be created. Similarly,
 *   if you create a `T` reference, then you must not access the data within the `UnsafeCell`
 *   with any other pointer/reference until that reference expires, and no reference of any kind
 *   may be created.
 *
 * - For both `T` without `UnsafeCell<_>` and `T`, you must also not deallocate the data
 *   until the reference expires. As a special exception, given a `T`, any part of it that is
 *   inside an `UnsafeCell<_>` may be deallocated during the scope of the reference, after the
 *   last time the reference is used (dereferenced or reborrowed). Since you cannot deallocate a part
 *   of what a reference points to, this means the memory a `T` points to can be deallocated only if
 *   *every part of it* (including padding) is inside an `UnsafeCell`.
 *
 * However, whenever a `UnsafeCell<T>` is constructed or dereferenced, it must still point to
 * live memory and the compiler is allowed to insert spurious reads if it can prove that this
 * memory has not yet been deallocated.
 *
 * To assist with proper design, the following scenarios are explicitly declared legal
 * for single-threaded code:
 *
 * 1. A `T` reference can be released to safe code and there it can co-exist with other `T`
 *    references, but not with a `T`
 *
 * 2. A `T` reference may be released to safe code provided neither other `T` nor `T`
 *    co-exist with it. A `T` must always be unique.
 *
 * Note that whilst mutating the contents of a `UnsafeCell<T>` (even while other
 * `UnsafeCell<T>` references alias the cell) is
 * ok (provided you enforce the above invariants some other way), it is still undefined behavior
 * to have aliasing `UnsafeCell<T>` (or aliasing  of *any* type). That is, `UnsafeCell` is a wrapper
 * designed to have a special interaction with _shared_ accesses (_i.e._, through an
 * `UnsafeCell<_>` reference); there is no magic whatsoever when dealing with _exclusive_
 * accesses (_e.g._, through a `UnsafeCell<_>`): neither the cell nor the wrapped value
 * may be aliased for the duration of that  borrow.
 * This is showcased by the [getMut] accessor, which is a _safe_ getter that yields
 * a `T`.
 *
 * [getMut]: `UnsafeCell.getMut`
 *
 * # Memory layout
 *
 * `UnsafeCell<T>` has the same in-memory representation as its inner type `T`. A consequence
 * of this guarantee is that it is possible to convert between `T` and `UnsafeCell<T>`.
 * Special care has to be taken when converting a nested `T` inside of an `Outer<T>` type
 * to an `Outer<UnsafeCell<T>>` type: this is not sound when the `Outer<T>` type enables [niche]
 * optimizations. For example, the type `Option<NonNull<UByte>>` is typically 8 bytes large on
 * 64-bit platforms, but the type `Option<UnsafeCell<NonNull<UByte>>>` takes up 16 bytes of space.
 * Therefore this is not a valid conversion, despite `NonNull<UByte>` and `UnsafeCell<NonNull<UByte>>>`
 * having the same memory layout. This is because `UnsafeCell` disables niche optimizations in
 * order to avoid its interior mutability property from spreading from `T` into the `Outer` type,
 * thus this can cause distortions in the type size in these cases.
 *
 * The following examples make use of this guarantee:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * / **
 *  * # Safety
 *  * The caller must not call `getMutUnchecked` again (on any alias of `ptr`) for the duration
 *  * of the scope of the returned reference.
 *  * /
 * fun <T> getMutUnchecked(ptr: UnsafeCell<T>): T {
 *     val t = (constPtrFromRustCast(ptr)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     return RustMutableReference(MutPtr((t).rawAddress), UnresolvedTypeLayout<T>())
 * }
 * ```
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun <T> getShared(ptr: T): UnsafeCell<T> {
 *     val t = (mutPtrFromRustCast(ptr)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
 *     // SAFETY: `T` and `UnsafeCell<T>` have the same memory layout
 *     return (UnresolvedTypeLayout<UnsafeCell<T>>()).readNative((t).rawAddress)
 * }
 * ```
 *
 * [niche]: https://rust-lang.github.io/unchecked-code-guidelines/glossary.html#niche
 *
 * # Examples
 *
 * Here is an example showcasing how to soundly mutate the contents of an `UnsafeCell<_>` despite
 * there being multiple references aliasing the cell:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.cell.UnsafeCell
 *
 * val x: UnsafeCell<Int> = 42.into()
 * // Get multiple / concurrent / shared references to the same `x`.
 * val (p1: UnsafeCell<Int>, p2: UnsafeCell<Int>) = Pair(x, x)
 * run {
 *     // SAFETY: within this scope there are no other references to `x`s contents,
 *     // so ours is effectively unique.
 *     val p1Exclusive: RustMutableReference<Int> = p1.get()
 *     // -- borrow --+
 *     (p1Exclusive).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (27); targetPlace.write(currentValue) }
 *     // |
 * }
 * // <---------- cannot go beyond this point -------------------+
 * run {
 *     // SAFETY: within this scope nobody expects to have exclusive access to `x`s contents,
 *     // so we can have multiple shared accesses concurrently.
 *     val p2Shared: Int = p2.get()
 *     check(p2Shared == 42 + 27)
 *     val p1Shared: Int = p1.get()
 *     check(p1Shared == p2Shared)
 * }
 * ```
 *
 * The following example showcases the fact that exclusive access to an `UnsafeCell<T>`
 * implies exclusive access to its `T`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
 * import std.cell.UnsafeCell
 *
 * val localStorageL8C0 = AddressableLocal(42.into(), TransparentTypeLayout(PrimitiveLayouts.I32, { ptr, layout -> UnsafeCell<Int>(ptr, layout) }, { it.value }))
 * var x by localStorageL8C0
 * // Get a compile-time-checked unique reference to `x`.
 * val pUnique: RustMutableReference<UnsafeCell<Int>> = RustMutableReference(localStorageL8C0.asMutPtr(), localStorageL8C0.layout)
 * // With an exclusive reference, we can mutate the contents for free.
 * (pUnique).read().getMut() = 0
 * // Or, equivalently:
 * x = UnsafeCell.new(0)
 * // When we own the value, we can extract the contents for free.
 * val contents: Int = x.intoInner()
 * check(contents == 0)
 * ```
 */
public open class UnsafeCell<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<UnsafeCell<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value })

    /**
     * Unwraps the value, consuming the cell.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * val uc = UnsafeCell.new(5)
     * val five = uc.intoInner()
     * ```
     */
    public open fun intoInner(): T = this.value

    /**
     * Replace the value in this `UnsafeCell` and return the old value.
     *
     * # Safety
     *
     * The caller must take care to avoid aliasing and data races.
     *
     * - It is Undefined Behavior to allow calls to race with
     *   any other access to the wrapped value.
     * - It is Undefined Behavior to call this while any other
     *   reference(s) to the wrapped value are alive.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * val uc = UnsafeCell.new(5)
     * val old = uc.replace(10)
     * check(old == 5)
     * ```
     */
    public open fun replace(value: T): T {
        // SAFETY: pointer comes from `self` so naturally satisfies invariants.
        return kotlin.Pair((this), value).let { (placeReceiver, placeValue) -> val placePrevious = placeReceiver.value; placeReceiver.value = placeValue; placePrevious }
    }

    /**
     * Gets a mutable pointer to the wrapped value.
     *
     * This can be cast to a pointer of any kind. When creating (shared or mutable) references, you
     * must uphold the aliasing rules; see [the type-level docs][UnsafeCell#aliasing-rules] for
     * more discussion and caveats.
     *
     * This is equivalent to casting `self` to a raw pointer and then casting that raw
     * pointer to `* T`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * val uc = UnsafeCell.new(5)
     * val five = uc.get()
     * ```
     */
    public open fun get(): kotlin.reflect.KMutableProperty0<T> {
        return this.fieldStorage
    }

    /**
     * Returns a mutable reference to the underlying data.
     *
     * This call borrows the `UnsafeCell` mutably (at compile-time) which
     * guarantees that we possess the only reference.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * var c = UnsafeCell.new(5)
     * c.getMut() += 1
     * check(c.getMut() == 6)
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this::value
    }

    /**
     * Get a shared reference to the value within the `UnsafeCell`.
     *
     * # Safety
     *
     * - It is Undefined Behavior to call this while any mutable
     *   reference to the wrapped value is alive.
     * - Mutating the wrapped value while the returned
     *   reference is alive is Undefined Behavior.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * val uc = UnsafeCell.new(5)
     * val value = uc.asRefUnchecked()
     * check(value == 5)
     * ```
     */
    public open fun asRefUnchecked(): T {
        // SAFETY: pointer comes from `self` so naturally satisfies ptr-to-ref invariants.
        return this.get().asRefUnchecked()
    }

    /**
     * Get an exclusive reference to the value within the `UnsafeCell`.
     *
     * # Safety
     *
     * - It is Undefined Behavior to call this while any other
     *   reference(s) to the wrapped value are alive.
     * - Mutating the wrapped value through other means while the
     *   returned reference is alive is Undefined Behavior.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.UnsafeCell
     *
     * val uc = UnsafeCell.new(5)
     * run {
     *     uc.asMutUnchecked() += 1
     * }
     * check(uc.intoInner() == 6)
     * ```
     */
    public open fun asMutUnchecked(): T {
        // SAFETY: pointer comes from `self` so naturally satisfies ptr-to-ref invariants.
        return this.get().asMutUnchecked()
    }

    public companion object {
        /**
         * Constructs a new instance of `UnsafeCell` which will wrap the specified
         * value.
         *
         * All access to the inner value through `UnsafeCell<T>` requires `unchecked` code.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.UnsafeCell
         *
         * val uc = UnsafeCell.new(5)
         * ```
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): UnsafeCell<T> = UnsafeCell(value = value, fieldLayout = fieldLayout)

        /**
         * Converts from `T` to `UnsafeCell<T>`.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
         * import std.cell.UnsafeCell
         *
         * val localStorageL5C0 = AddressableLocal(42, PrimitiveLayouts.I32)
         * val uc = UnsafeCell.fromMut(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
         * uc.getMut() -= 1
         * check(uc.getMut() == 41)
         * ```
         */
        public fun <T> fromMut(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RustMutableReference<UnsafeCell<T>> {
            // SAFETY: `UnsafeCell<T>` has the same memory layout as `T` due to @repr(transparent).
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(value)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(typeLayoutT, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }))
        }

        /**
         * Gets a mutable pointer to the wrapped value.
         * The difference from [get] is that this function accepts a raw pointer,
         * which is useful to avoid the creation of temporary references.
         *
         * This can be cast to a pointer of any kind. When creating (shared or mutable) references, you
         * must uphold the aliasing rules; see [the type-level docs][UnsafeCell#aliasing-rules] for
         * more discussion and caveats.
         *
         * This is equivalent to casting `this` to `* T`.
         *
         * [get]: UnsafeCell.get()
         *
         * # Examples
         *
         * Gradual initialization of an `UnsafeCell` requires `rawGet`, as
         * calling `get` would require creating a reference to uninitialized data:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.cell.UnsafeCell
         * import std.mem.MaybeUninit
         *
         * val m = MaybeUninit.uninit(UnresolvedTypeLayout<UnsafeCell<Int>>())
         * run {
         *     UnsafeCell.rawGet(m.asPtr()).write(5)
         * }
         * // avoid below which references to uninitialized data
         * // unchecked ( UnsafeCell.get(&*m.asPtr()).write(5); )
         * val uc = m.assumeInit()
         * check(uc.intoInner() == 5)
         * ```
         */
        public fun rawGet(thisRef: ConstPtr): MutPtr {
            // We can just cast the pointer from `UnsafeCell<T>` to `T` because of
            // @repr(transparent).
            return (ConstPtr(thisRef.rawAddress, thisRef.rawMeta)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        }

        /**
         * Creates an `UnsafeCell`, with the `Default` value for T.
         */
        public fun <T> default(fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): UnsafeCell<T> = UnsafeCell.new(defaultTFn(), fieldLayout = fieldLayout)

        /**
         * Creates a new `UnsafeCell<T>` containing the given value.
         */
        public fun <T> from(t: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): UnsafeCell<T> = UnsafeCell.new(t, fieldLayout = fieldLayout)
    }
}

// Allow types that wrap `UnsafeCell` to also implement `DispatchFromDyn`
// and become dynamic-compatible method receivers.
// Note that currently `UnsafeCell` itself cannot be a method receiver
// because it does not implement Deref.
// In other words:
// `self: UnsafeCell<Self>` won't work
// `self: UnsafeCellWrapper<Self>` becomes possible

/**
 * [UnsafeCell], but [Sync].
 *
 * This is just an `UnsafeCell`, except it implements `Sync`
 * if `T` implements `Sync`.
 *
 * `UnsafeCell` doesn't implement `Sync`, to prevent accidental mis-use.
 * You can use `SyncUnsafeCell` instead of `UnsafeCell` to allow it to be
 * shared between threads, if that's intentional.
 * Providing proper synchronization is still the task of the user,
 * making this type just as unchecked to use.
 *
 * See [UnsafeCell] for details.
 */
public open class SyncUnsafeCell<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout(),
) {
    public var value: UnsafeCell<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<SyncUnsafeCell<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> SyncUnsafeCell<T>(ptr, layout) }, { it.value })

    /**
     * Unwraps the value, consuming the cell.
     */
    public open fun intoInner(): T = this.value.intoInner()

    /**
     * Gets a mutable pointer to the wrapped value.
     *
     * This can be cast to a pointer of any kind.
     * Ensure that the access is unique (no active references, mutable or not)
     * when casting to `T`, and ensure that there are no mutations
     * or mutable aliases going on when casting to `T`
     */
    public open fun get(): kotlin.reflect.KMutableProperty0<T> = this.value.get()

    /**
     * Returns a mutable reference to the underlying data.
     *
     * This call borrows the `SyncUnsafeCell` mutably (at compile-time) which
     * guarantees that we possess the only reference.
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this.value::value
    }

    public companion object {
        public operator fun <T> invoke(value: UnsafeCell<T>, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()): SyncUnsafeCell<T> = SyncUnsafeCell<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UnsafeCell<T>> = UnresolvedTypeLayout()): SyncUnsafeCell<T> = SyncUnsafeCell<T>(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `SyncUnsafeCell` which will wrap the specified value.
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): SyncUnsafeCell<T> = SyncUnsafeCell(value = UnsafeCell(value = value, fieldLayout = fieldLayout), fieldLayout = TransparentTypeLayout(fieldLayout, { ptr, layout -> UnsafeCell<T>(ptr, layout) }, { it.value }))

        /**
         * Gets a mutable pointer to the wrapped value.
         *
         * See [UnsafeCell.get] for details.
         */
        public fun rawGet(thisRef: ConstPtr): MutPtr {
            // We can just cast the pointer from `SyncUnsafeCell<T>` to `T` because
            // of @repr(transparent) on both SyncUnsafeCell and UnsafeCell.
            // See UnsafeCell.rawGet.
            return (ConstPtr(thisRef.rawAddress, thisRef.rawMeta)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        }

        /**
         * Creates an `SyncUnsafeCell`, with the `Default` value for T.
         */
        public fun <T> default(fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): SyncUnsafeCell<T> = SyncUnsafeCell.new(defaultTFn(), fieldLayout = fieldLayout)

        /**
         * Creates a new `SyncUnsafeCell<T>` containing the given value.
         */
        public fun <T> from(t: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): SyncUnsafeCell<T> = SyncUnsafeCell.new(t, fieldLayout = fieldLayout)
    }
}

// @unstable(feature = "syncUnsafeCell", issue = "95439")

// Allow types that wrap `SyncUnsafeCell` to also implement `DispatchFromDyn`
// and become dynamic-compatible method receivers.
// Note that currently `SyncUnsafeCell` itself cannot be a method receiver
// because it does not implement Deref.
// In other words:
// `self: SyncUnsafeCell<Self>` won't work
// `self: SyncUnsafeCellWrapper<Self>` becomes possible
// @unstable(feature = "syncUnsafeCell", issue = "95439")

public fun assertCoerceUnsized(a: UnsafeCell<Int>, b: SyncUnsafeCell<Int>, c: Cell<Int>, d: RefCell<Int>) {}
