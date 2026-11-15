// port-lint: source clone.rs
package io.github.kotlinmania.rustlang.core.clone

import io.github.kotlinmania.rustlang.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.ffi.cstr.CStr

// The `Clone` trait for types that cannot be implicitly copied'.
//
// In Rust, some simple types are "implicitly copyable" and when you
// assign them or pass them as arguments, the receiver will get a copy,
// leaving the original value in place. These types do not require
// allocation to copy and do not have finalizers (i.e., they do not
// contain owned boxes or implement [Drop]), so the compiler considers
// them cheap and safe to copy. For other types copies must be made
// explicitly, by convention implementing the [Clone] trait and calling
// the [clone] method.
//
// [clone]: Clone.clone
//
// Basic usage example:
//
// ```kotlin
// val s = ""
// // String type implements Clone
// val copy = s.clone()
// // so we can clone it
// ```
//
// To easily implement the Clone trait, you can also use
// `@derive(Clone)`. Example:
//
// ```kotlin
// // we add the Clone trait to Morpheus struct
// open class Morpheus(public var bluePill: Float, public var redPill: Long) {
//     public open fun clone(): Morpheus = Morpheus(bluePill, redPill)
// }
// fun main() {
//     val f = Morpheus(bluePill = 0.0f, redPill = 0L)
//     val copy = f.clone()
//     // and now we can clone it!
// }
// ```

/**
 * A common trait that allows explicit creation of a duplicate value.
 *
 * Calling [clone] always produces a new value.
 * However, for types that are references to other data (such as smart pointers or references),
 * the new value may still point to the same underlying data, rather than duplicating it.
 * See [Clone.clone] for more details.
 *
 * This distinction is especially important when using `@derive(Clone)` on structs containing
 * smart pointers like `Arc<Mutex<T>>` - the cloned struct will share mutable state with the
 * original.
 *
 * Differs from [Copy] in that [Copy] is implicit and an inexpensive bit-wise copy, while
 * `Clone` is always explicit and may or may not be expensive. [Copy] has no methods, so you
 * cannot change its behavior, but when implementing `Clone`, the `clone` method you provide
 * may run arbitrary code.
 *
 * Since `Clone` is a supertrait of [Copy], any type that implements `Copy` must also implement
 * `Clone`.
 *
 * ## Derivable
 *
 * This trait can be used with `@derive` if all fields are `Clone`. The `derive`d
 * implementation of [Clone] calls [clone] on each field.
 *
 * [clone]: Clone.clone
 *
 * For a generic struct, `@derive` implements `Clone` conditionally by adding bound `Clone` on
 * generic parameters.
 *
 * ```kotlin
 * // `derive` implements Clone for Reading<T> when T is Clone.
 * open class Reading<T>(public var frequency: T) {
 *     public open fun clone(): Reading<T> = Reading(frequency)
 * }
 * ```
 *
 * ## How can I implement `Clone`?
 *
 * Types that are [Copy] should have a trivial implementation of `Clone`. More formally:
 * if `T: Copy`, `x: T`, and `y: T`, then `val x = y.clone();` is equivalent to `val x = * y;`.
 * Manual implementations should be careful to uphold this invariant; however, unchecked code
 * must not rely on it to ensure memory safety.
 *
 * An example is a generic struct holding a function pointer. In this case, the
 * implementation of `Clone` cannot be `derive`d, but can be implemented as:
 *
 * ```kotlin
 * public fun interface FnCallback {
 *     public operator fun invoke(): T
 * }
 *
 * public fun doctestEntry() {
 *     open class Generate<T>(public var value: () -> T) {
 *         public var field0: FnCallback
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun clone(): Generate<T> {
 *             return Generate(this.value)
 *         }
 *     }
 * }
 * ```
 *
 * If we `derive`:
 *
 * ```kotlin
 * public fun interface FnCallback {
 *     public operator fun invoke(): T
 * }
 *
 * public fun doctestEntry() {
 *     open class Generate<T>(public var value: () -> T) {
 *         public var field0: FnCallback
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun clone(): Generate<T> = Generate(value)
 *     }
 * }
 * ```
 *
 * the auto-derived implementations will have unnecessary `T: Copy` and `T: Clone` bounds:
 *
 * ```kotlin
 * // Automatically derived
 * // Automatically derived
 * fun <T> Generate<T>.clone(): Generate<T> {
 *     return Generate(Clone.clone(this.value))
 * }
 * ```
 *
 * The bounds are unnecessary because clearly the function itself should be
 * copy- and cloneable even if its return type is not:
 *
 * ```compile_fail,E0599
 * @derive(Copy, Clone)
 * struct Generate<T>(fn() -> T);
 *
 * struct NotCloneable;
 *
 * fn generateNotCloneable() -> NotCloneable {
 *     NotCloneable
 * }
 *
 * Generate(generateNotCloneable).clone(); // error: trait bounds were not satisfied
 * // Note: With the manual implementations the above line will compile.
 * ```
 *
 * ## `Clone` and `PartialEq`/`Eq`
 * `Clone` is intended for the duplication of objects. Consequently, when implementing
 * both `Clone` and [PartialEq], the following property is expected to hold:
 * ```text
 * x == x -> x.clone() == x
 * ```
 * In other words, if an object compares equal to itself,
 * its clone must also compare equal to the original.
 *
 * For types that also implement [Eq] – for which `x == x` always holds –
 * this implies that `x.clone() == x` must always be true.
 * Standard library collections such as
 * [HashMap], [HashSet], [BTreeMap], [BTreeSet] and [BinaryHeap]
 * rely on their keys respecting this property for correct behavior.
 * Furthermore, these collections require that cloning a key preserves the outcome of the
 * [Hash] and [Ord] methods. Thankfully, this follows automatically from `x.clone() == x`
 * if `Hash` and `Ord` are correctly implemented according to their own requirements.
 *
 * When deriving both `Clone` and [PartialEq] using `@derive(Clone, PartialEq)`
 * or when additionally deriving [Eq] using `@derive(Clone, PartialEq, Eq)`,
 * then this property is automatically upheld – provided that it is satisfied by
 * the underlying types.
 *
 * Violating this property is a logic error. The behavior resulting from a logic error is not
 * specified, but users of the trait must ensure that such logic errors do *not* result in
 * undefined behavior. This means that `unchecked` code **must not** rely on this property
 * being satisfied.
 *
 * ## Additional implementors
 *
 * In addition to the [implementors listed below][impls],
 * the following types also implement `Clone`:
 *
 * * Function item types (i.e., the distinct types defined for each function)
 * * Function pointer types (e.g., `fun() -> Int`)
 * * Closure types, if they capture no value from the environment
 *   or if all such captured values implement `Clone` themselves.
 *   Note that variables captured by shared reference always implement `Clone`
 *   (even if the referent doesn't),
 *   while variables captured by mutable reference never implement `Clone`.
 *
 * [HashMap]: ../../std/collections/struct.HashMap.html
 * [HashSet]: ../../std/collections/struct.HashSet.html
 * [BTreeMap]: ../../std/collections/struct.BTreeMap.html
 * [BTreeSet]: ../../std/collections/struct.BTreeSet.html
 * [BinaryHeap]: ../../std/collections/struct.BinaryHeap.html
 * [impls]: #implementors
 */
public interface Clone<T> {
    /**
     * Returns a duplicate of the value.
     *
     * Note that what "duplicate" means varies by type:
     * - For most types, this creates a deep, independent copy
     * - For reference types like `T`, this creates another reference to the same value
     * - For smart pointers like [Arc] or [Rc], this increments the reference count
     *   but still points to the same underlying data
     *
     * [Arc]: ../../std/sync/struct.Arc.html
     * [Rc]: ../../std/rc/struct.Rc.html
     *
     * # Examples
     *
     * ```kotlin
     * val hello = "Hello"
     * // &str implements Clone
     * check("Hello" == hello.clone())
     * ```
     *
     * Example with a reference-counted type:
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * val data = Mutex.new(mutableListOf(1, 2, 3))
     * val dataClone = data.clone()
     * // Creates another Arc pointing to the same Mutex
     * run {
     *     var lock = data.lock().unwrap()
     *     lock.push(4)
     * }
     * // Changes are visible through the clone because they share the same underlying data
     * check(dataClone.lock().unwrap() == mutableListOf(1, 2, 3, 4))
     * ```
     */
    public fun clone(): T

    /**
     * Performs copy-assignment from `source`.
     *
     * `a.cloneFrom(b)` is equivalent to `a = b.clone()` in functionality,
     * but can be overridden to reuse the resources of `a` to avoid unnecessary
     * allocations.
     */
    public fun cloneFrom(source: T)
}

// SAFETY:
// It is sound to specialize on this because the `clone` implementation cannot be
// scope-dependent. Therefore, if `TrivialClone` is implemented for any scope,
// its invariant holds whenever `Clone` is implemented, even if the actual
// `TrivialClone` bound would not be satisfied because of scope bounds.
// If `@derive(Clone, Clone, Copy)` is written, there will be multiple
// implementations of `TrivialClone`. To keep it from appearing in error
// messages, make it a `@marker` trait.

/**
 * Indicates that the `Clone` implementation is identical to copying the value.
 *
 * This is used for some optimizations in the standard library, which specializes
 * on this trait to select faster implementations of functions such as
 * [`cloneFromSlice`](slice.cloneFromSlice). It is automatically implemented
 * when using `@derive(Clone, Copy)`.
 *
 * Note that this trait does not imply that the type is `Copy`, because e.g.
 * `core.ops.Range<Int>` could soundly implement this trait.
 *
 * # Safety
 * `Clone.clone` must be equivalent to copying the value, otherwise calling functions
 * such as `slice.cloneFromSlice` can have undefined behaviour.
 */
public interface TrivialClone<T> : Clone<T>

/**
 * A trait for types whose [Clone] operation creates another alias to the same
 * logical resource or shared state.
 *
 * `Share` refines the meaning of [Clone] for types where cloning a value
 * creates another handle, reference, or alias to the same logical resource or
 * shared state, rather than an independent owned value. The distinction is
 * semantic, not operational: `Share` does not mean merely that cloning is
 * cheap, constant-time, allocation-free, or convenient.
 *
 * `Share` is a third way to think about creating another usable value:
 *
 * * [Copy] may duplicate a value implicitly.
 * * [Clone] explicitly creates another value.
 * * `Share` explicitly creates another value that aliases the same underlying
 *   logical resource or shared state.
 *
 * `Share` is not a replacement for either [Copy] or [Clone], and neither
 * trait implies it. For example, integers are [Copy] but not `Share`, because
 * copying an integer creates an independent value. Likewise, not every cheap
 * [Clone] implementation is `Share`.
 *
 * Shared references, `Rc<T>`, `Arc<T>`, `Sender<T>`, and `SyncSender<T>` are
 * examples of types that can be shared this way. Types such as `List<T>`,
 * `String`, `Holder<T>`, owned collections, and similar owned values are not
 * `Share`, even though they implement [Clone], because cloning them creates
 * independent owned storage or value ownership. Mutable references (`T`)
 * are neither `Clone` nor `Share`, because you cannot have two active at once.
 *
 * Calling [`share`](Share.share) is equivalent to calling [`clone`](Clone.clone)
 * for implementors, but communicates that the resulting value aliases the same
 * underlying resource. The `share` method is final, so implementors should
 * define the operation through [Clone.clone] and implement `Share` only when
 * those cloning semantics are clone-as-alias semantics.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 * import std.clone.Share
 * import std.sync.atomic.AtomicUsize
 * import std.sync.atomic.Ordering
 *
 * @feature(shareTrait)
 * public fun doctestEntry() {
 *     val value = 1
 *     val reference = value
 *     check(eq(reference, reference.share()))
 *     val rc = Cell.new(2)
 *     val sharedRc = rc.share()
 *     check(Rc.ptrEq(rc, sharedRc))
 *     sharedRc.set(3)
 *     check(rc.get() == 3)
 *     val arc = AtomicUsize.new(4)
 *     val sharedArc = arc.share()
 *     check(Arc.ptrEq(arc, sharedArc))
 *     sharedArc.store(5, Ordering.Relaxed)
 *     check(arc.load(Ordering.Relaxed) == 5)
 * }
 * ```
 *
 * ```kotlin
 * import std.clone.Share
 * import std.sync.mpsc.channel
 * import std.sync.mpsc.syncChannel
 *
 * @feature(shareTrait)
 * public fun doctestEntry() {
 *     val (sender, receiver) = channel()
 *     val sharedSender = sender.share()
 *     sender.send(1).unwrap()
 *     sharedSender.send(2).unwrap()
 *     var received = mutableListOf(receiver.recv().unwrap(), receiver.recv().unwrap())
 *     received.sort()
 *     check(received == listOf(1, 2))
 *     val (syncSender, syncReceiver) = syncChannel(2)
 *     val sharedSyncSender = syncSender.share()
 *     syncSender.send(3).unwrap()
 *     sharedSyncSender.send(4).unwrap()
 *     var received1 = mutableListOf(syncReceiver.recv().unwrap(), syncReceiver.recv().unwrap())
 *     received1.sort()
 *     check(received1 == listOf(3, 4))
 * }
 * ```
 */
public interface Share<T> : Clone<T> {
    /**
     * Creates another alias to the same underlying resource or shared state.
     *
     * This is equivalent to calling [Clone.clone]. Use `share` at call
     * sites to make aliasing intent explicit; implementors define this
     * operation through [Clone.clone], not by overriding this method.
     */
    public fun share(): T = Clone.Clone(this)
}

/**
 * Trait for objects whose [Clone] impl is lightweight (e.g. reference-counted)
 *
 * Cloning an object implementing this trait should in general:
 * - be O(1) (constant) time regardless of the amount of data managed by the object,
 * - not require a memory allocation,
 * - not require copying more than roughly 64 bytes (a typical cache line size),
 * - not block the current thread,
 * - not have any semantic side effects (e.g. allocating a file descriptor), and
 * - not have overhead larger than a couple of atomic operations.
 *
 * The `UseCloned` trait does not provide a method; instead, it indicates that
 * `Clone.clone` is lightweight, and allows the use of the `.import` syntax.
 *
 * ## .use postfix syntax
 *
 * Values can be `.import`d by adding `.import` postfix to the value you want to use.
 *
 * ```ignore (this won't work until we land use)
 * fn foo(f: Foo) {
 *     // if `Foo` implements `Copy` f would be copied into x.
 *     // if `Foo` implements `UseCloned` f would be cloned into x.
 *     // otherwise f would be moved into x.
 *     let x = f.use;
 *     // ...
 * }
 * ```
 *
 * ## use closures
 *
 * Use closures allow captured values to be automatically used.
 * This is similar to have a closure that you would call `.import` over each captured value.
 */
public interface UseCloned<T> : Clone<T>

// Note(aburka): these structs are used solely by @derive to
// assert that every component of a type implements Clone or Copy.
//
// These structs should never appear in user code.
public open class AssertParamIsClone<T : Clone<T>>(
    public var field: Unit,
)

public open class AssertParamIsCopy<T>(
    public var field: Unit,
)

// Note(#126799): when `Holder.clone` allows use of `CloneToUninit`, rewrite these examples with it
// since `Rc` is a distraction.

/**
 * A generalization of [Clone] to [dynamically-sized types][DST] stored in arbitrary containers.
 *
 * This trait is implemented for all types implementing [Clone], [slices](slice) of all
 * such types, and other dynamically-sized types in the standard library.
 * You may also implement this trait to enable cloning custom DSTs
 * (structures containing dynamically-sized fields), or use it as a supertrait to enable
 * cloning a [trait object].
 *
 * This trait is normally used via operations on container types which support DSTs,
 * so you should not typically need to call `.cloneToUninit()` explicitly except when
 * implementing such a container or otherwise performing explicit management of an allocation,
 * or when implementing `CloneToUninit` itself.
 *
 * # Safety
 *
 * Implementations must ensure that when `.cloneToUninit(dest)` returns normally rather than
 * panicking, it always leaves `* dest` initialized as a valid value of type `Self`.
 *
 * # Examples
 *
 * If you are defining a trait, you can add `CloneToUninit` as a supertrait to enable cloning of
 * `dynamic` values of your trait:
 *
 * ```kotlin
 * @feature(cloneToUninit)
 * public fun doctestEntry() {
 *     interface Foo : CloneToUninit {
 *         public fun modify()
 *
 *         public fun value(): Int
 *     }
 *     fun Int.modify() {
 *         this *= 10
 *     }
 *
 *     fun Int.value(): Int {
 *         return this
 *     }
 *     val first: Foo = 1234
 *     var second = first.clone()
 *     Rc.makeMut(second).modify()
 *     // makeMut() will call cloneToUninit()
 *     check(first.value() == 1234)
 *     check(second.value() == 12340)
 * }
 * ```
 *
 * The following is an example of implementing `CloneToUninit` for a custom DST.
 * (It is essentially a limited form of what `derive(CloneToUninit)` would do,
 * if such a derive macro existed.)
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.clone.CloneToUninit
 * import std.mem.offsetOf
 *
 * @feature(cloneToUninit)
 * public fun doctestEntry(): Result<Unit> {
 *     open class MyDst<T>(public var label: String, public var contents: T) : CloneToUninit {
 *         public open fun cloneToUninit(dest: MutPtr) {
 *             // The offset of `self.contents` is dynamic because it depends on the alignment of T
 *             // which can be dynamic (if `T = dynamic SomeTrait`). Therefore, we have to obtain it
 *             // dynamically by examining `self`, rather than using `offsetOf`.
 *             //
 *             // SAFETY: `self` by definition points somewhere before `self.contents` in the same
 *             // allocation.
 *             val offsetOfContents = (addrOf(this.contents)).byteOffsetFromUnsigned(this)
 *             // Clone the *sized* fields of `self` (just one, in this example).
 *             // (By cloning this first and storing it temporarily in a local variable, we avoid
 *             // leaking it in case of any panic, using the ordinary automatic cleanup of local
 *             // variables. Such a leak would be sound, but undesirable.)
 *             val label = this.label.clone()
 *             // SAFETY: The caller must provide a `dest` such that these field offsets are valid
 *             // to write to.
 *             run {
 *                 // Clone the unsized field directly from `self` to `dest`.
 *                 this.contents.cloneToUninit(MutPtr((dest).rawAddress + ((offsetOfContents).toLong() * (PrimitiveLayouts.U8).strideBytes)))
 *                 // Now write all the sized fields.
 *                 //
 *                 // Note that we only do this once all of the clone() and cloneToUninit() calls
 *                 // have completed, and therefore we know that there are no more possible panics;
 *                 // this ensures no memory leaks in case of panic.
 *                 (ObjectSlotLayout<String>()).writeNative((MutPtr((MutPtr((dest).rawAddress + ((0).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)).rawAddress, label)
 *             }
 *             // All fields of the struct have been initialized; therefore, the struct is initialized,
 *             // and we have satisfied our `unchecked implementation CloneToUninit` obligations.
 *         }
 *     }
 *     fun main() {
 *         // Construct MyDst<[u8; 4]>, then coerce to MyDst<[u8]>.
 *         val first: MyDst<List<UByte>> = MyDst(label = "hello", contents = listOf(1, 2, 3, 4))
 *         var second = first.clone()
 *         // makeMut() will call cloneToUninit().
 *         for (idx in (Rc.makeMut(second).contents).indices) {
 *             (Rc.makeMut(second).contents)[idx] *= 10
 *         }
 *         check(first.contents == listOf(1, 2, 3, 4))
 *         check(second.contents == listOf(10, 20, 30, 40))
 *         check(second.label == "hello")
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * # See Also
 *
 * * [Clone.cloneFrom] is a safe function which may be used instead when [`Self.Sized`](Sized)
 *   and the destination is already initialized; it may be able to reuse allocations owned by
 *   the destination, whereas `cloneToUninit` cannot, since its destination is assumed to be
 *   uninitialized.
 * * [ToOwned], which allocates a new destination container.
 *
 * [ToOwned]: ../../std/borrow/trait.ToOwned.html
 * [DST]: https://doc.rust-lang.org/reference/dynamically-sized-types.html
 * [trait object]: https://doc.rust-lang.org/reference/types/trait-object.html
 */
public interface CloneToUninit {
    /**
     * Performs copy-assignment from `self` to `dest`.
     *
     * This is analogous to `std.ptr.write(dest.cast(), self.clone())`,
     * except that `Self` may be a dynamically-sized type ([`Sized`](Sized)).
     *
     * Before this function is called, `dest` may point to uninitialized memory.
     * After this function is called, `dest` will point to initialized memory; it will be
     * sound to create a `Self` reference from the pointer with the [pointer metadata]
     * from `self`.
     *
     * # Safety
     *
     * Behavior is undefined if any of the following conditions are violated:
     *
     * * `dest` must be [valid] for writes for `sizeOfVal(self)` bytes.
     * * `dest` must be properly aligned to `alignOfVal(self)`.
     *
     * # Panics
     *
     * This function may panic. (For example, it might panic if memory allocation for a clone
     * of a value owned by `self` fails.)
     * If the call panics, then `* dest` should be treated as uninitialized memory; it must not be
     * read or dropped, because even if it was previously valid, it may have been partially
     * overwritten.
     *
     * The caller may wish to take care to deallocate the allocation pointed to by `dest`,
     * if applicable, to avoid a memory leak (but this is not a requirement).
     *
     * Implementors should avoid leaking values by, upon unwinding, dropping all component values
     * that might have already been created. (For example, if a `[Foo]` of length 3 is being
     * cloned, and the second of the three calls to `Foo.clone()` unwinds, then the first `Foo`
     * cloned should be dropped.)
     */
    public fun cloneToUninit(dest: MutPtr)
}

public fun <T : Clone<T>> T.cloneToUninit(dest: MutPtr, cloneOneTFn: (T, MutPtr) -> Any?) {
    // SAFETY: we're calling a specialization with the same contract
    cloneOneTFn(this, MutPtr((dest).rawAddress))
}

public fun <T : Clone<T>> List<T>.cloneToUninit(dest: MutPtr, cloneSliceTFn: (List<T>, MutPtr) -> Any?) {
    val dest1: MutPtr = dest.withMetadataOf(this)
    // SAFETY: we're calling a specialization with the same contract
    cloneSliceTFn(this, dest1)
}

public fun String.cloneToUninit(dest: MutPtr) {
    // SAFETY: str is just a [u8] with UTF-8 invariant
    this.encodeToByteArray().map { it.toUByte() }.cloneToUninit(dest)
}

public fun CStr.cloneToUninit(dest: MutPtr) {
    // SAFETY: For now, CStr is just a @repr(trasnsparent) [cChar] with some invariants.
    // And we can cast [cChar] to [u8] on all supported platforms (see: toBytesWithNul).
    // The pointer metadata properly preserves the length (so NUL is also copied).
    // See: `cstrMetadataIsLengthWithNul` in tests.
    this.toBytesWithNul().cloneToUninit(dest)
}

public fun ByteStr.cloneToUninit(dst: MutPtr) {
    // SAFETY: ByteStr is a `@repr(transparent)` wrapper around `[UByte]`
    this.asBytes().cloneToUninit(dst)
}

public fun Int.clone(): Int = this

public fun UByte.clone(): UByte = this

public fun UShort.clone(): UShort = this

public fun UInt.clone(): UInt = this

public fun ULong.clone(): ULong = this

public fun U128.clone(): U128 = this

public fun Byte.clone(): Byte = this

public fun Short.clone(): Short = this

public fun Long.clone(): Long = this

public fun I128.clone(): I128 = this

public fun F16.clone(): F16 = this

public fun kotlin.Float.clone(): kotlin.Float = this

public fun Double.clone(): Double = this

public fun F128.clone(): F128 = this

public fun Boolean.clone(): Boolean = this

public fun Char.clone(): Char = this

public fun Nothing.clone(): Nothing = this

public fun <T> ConstPtr.clone(typeLayoutT: TypeLayout<T>): ConstPtr = (typeLayoutT).readNative((this).rawAddress)

public fun <T> MutPtr.clone(typeLayoutT: TypeLayout<T>): MutPtr = (typeLayoutT).readNative((this).rawAddress)

/**
 * Shared references can be cloned, but mutable references *cannot*!
 */
public fun <T> T.clone(): T = this

/**
 * Shared references can be cloned, but mutable references *cannot*!
 */
