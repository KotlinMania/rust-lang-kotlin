// port-lint: source marker.rs
package io.github.kotlinmania.rustlang.core.marker

import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

// Primitive traits and types representing basic properties of types.
//
// Rust types can be classified in various useful ways according to
// their intrinsic properties. These classifications are represented
// as traits.

/**
 * Types that can be transferred across thread boundaries.
 *
 * This trait is automatically implemented when the compiler determines it's
 * appropriate.
 *
 * An example of a non-`Send` type is the reference-counting pointer
 * [`rc.Rc`][Rc]. If two threads attempt to clone [Rc]s that point to the same
 * reference-counted value, they might try to update the reference count at the
 * same time, which is [undefined behavior][ub] because [Rc] doesn't use atomic
 * operations. Its cousin [`sync.Arc`][arc] does use atomic operations (incurring
 * some overhead) and thus is `Send`.
 *
 * See [the Nomicon](../../nomicon/send-and-sync.html) and the [Sync] trait for more details.
 *
 * [Rc]: ../../std/rc/struct.Rc.html
 * [arc]: ../../std/sync/struct.Arc.html
 * [ub]: ../../reference/behavior-considered-undefined.html
 */
public interface Send

// Most instances arise automatically, but this instance is needed to link up `T: Sync` with
// `T: Send` (and it also removes the unsound default instance `T Send` -> `T: Send` that would
// otherwise exist).

// for Default, for example, which requires that `[T]: !Default` be evaluatable
// `Sized` being coinductive, despite having supertraits, is okay as there are no user-written impls,
// and we know that the supertraits are always implemented if the subtrait is just by looking at
// the builtin impls.

/**
 * Types with a constant size known at compile time.
 *
 * All type parameters have an implicit bound of `Sized`. The special syntax
 * `? Sized` can be used to remove this bound if it's not appropriate.
 *
 * ```kotlin
 * open class Foo<T>(public var value: T) {
 *     public var field0: T
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 * }
 * open class Bar<T>(public var value: T) {
 *     public var field0: T
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 * }
 * // struct FooUse(Foo<[i32]>); // error: Sized is not implemented for [i32]
 * open class BarUse(public var value: Bar<List<Int>>) {
 *     public var field0: Bar<List<Int>>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 * }
 * // OK
 * ```
 *
 * The one exception is the implicit `Self` type of a trait. A trait does not
 * have an implicit `Sized` bound as this is incompatible with [trait object]s
 * where, by definition, the trait needs to work with all possible implementors,
 * and thus could be any size.
 *
 * Although Rust will let you bind `Sized` to a trait, you won't
 * be able to use it to form a trait object later:
 *
 * ```kotlin
 * interface Foo
 * interface Bar
 * open class Impl : Foo, Bar
 * val x: Foo = Impl()
 * // OK
 * // let y: &dynamic Bar = &Impl; // error: the trait `Bar` cannot be made into an object
 * ```
 *
 * [trait object]: ../../book/ch17-02-trait-objects.html
 */
public interface Sized

// `MetaSized` being coinductive, despite having supertraits, is okay for the same reasons as
// `Sized` above.

/**
 * Types with a size that can be determined from pointer metadata.
 */
public interface MetaSized

/**
 * Types that may or may not have a size.
 */
public interface PointeeSized

/**
 * Types that can be "unsized" to a dynamically-sized type.
 *
 * For example, the sized array type `[Byte; 2]` implements `Unsize<[Byte]>` and
 * `Unsize<dynamic fmt.Debug>`.
 *
 * All implementations of `Unsize` are provided automatically by the compiler.
 * Those implementations are:
 *
 * - Arrays `[T; N]` implement `Unsize<[T]>`.
 * - A type implements `Unsize<dynamic Trait + >` if all of these conditions are met:
 *   - The type implements `Trait`.
 *   - `Trait` is dynamic-compatible[^1].
 *   - The type is sized.
 *   - The type outlives `a`.
 * - Trait objects `dynamic TraitA + AutoA ... +` implement `Unsize<dynamic TraitB + AutoB ... + >`
 *    if all of these conditions are met:
 *   - `TraitB` is a supertrait of `TraitA`.
 *   - `AutoB ...` is a subset of `AutoA ...`.
 *   - `a` outlives `b`.
 * - Structs `Foo<..., T1, ..., Tn, ...>` implement `Unsize<Foo<..., U1, ..., Un, ...>>`
 *   where any number of (type and const) parameters may be changed if all of these conditions
 *   are met:
 *   - Only the last field of `Foo` has a type involving the parameters `T1`, ..., `Tn`.
 *   - All other parameters of the struct are equal.
 *   - `Field<T1, ..., Tn>: Unsize<Field<U1, ..., Un>>`, where `Field<...>` stands for the actual
 *     type of the struct's last field.
 *
 * `Unsize` is used along with [ops.CoerceUnsized] to allow
 * "user-defined" containers such as [Rc] to contain dynamically-sized
 * types. See the [DST coercion RFC][RFC982] and [the nomicon entry on coercion][nomicon-coerce]
 * for more details.
 *
 * [Rc]: ../../std/rc/struct.Rc.html
 * [RFC982]: https://rust-lang.github.io/rfcs/0982-dst-coercion.html
 * [nomicon-coerce]: ../../nomicon/coercions.html
 * [^1]: Formerly known as *object safe*.
 */
public interface Unsize<T>

/**
 * Required trait for constants used in pattern matches.
 *
 * Constants are only allowed as patterns if (a) their type implements
 * `PartialEq`, and (b) interpreting the value of the constant as a pattern
 * is equivalent to calling `PartialEq`. This ensures that constants used as
 * patterns cannot expose implementation details in an unexpected way or
 * cause semver hazards.
 *
 * This trait ensures point (b).
 * Any type that derives `PartialEq` automatically implements this trait.
 *
 * Implementing this trait (which is unstable) is a way for type authors to explicitly allow
 * comparing const values of this type; that operation will recursively compare all fields
 * (including private fields), even if that behavior differs from `PartialEq`. This can make it
 * semver-breaking to add further private fields to a type.
 */
public interface StructuralPartialEq

// Implementations of `Copy` for primitive types.
//
// Implementations that cannot be described in Rust
// are implemented in `traits.SelectionContext.copyCloneConditions()`
// in `rustcTraitSelection`.
// Note(adtConstParams): handle `ty.FnDef`/`ty.Closure`

/**
 * Types whose values can be duplicated simply by copying bits.
 *
 * By default, variable bindings have move semantics.' In other
 * words:
 *
 * ```kotlin
 * open class Foo
 * val x = Foo()
 * val y = x
 * // `x` has moved into `y`, and so cannot be used
 * // println("(x:?)"); // error: use of moved value
 * ```
 *
 * However, if a type implements `Copy`, it instead has copy semantics':
 *
 * ```kotlin
 * // We can derive a `Copy` implementation. `Clone` is also required, as it's
 * // a supertrait of `Copy`.
 * open class Foo {
 *     public open fun clone(): Foo = Foo()
 * }
 * val x = Foo()
 * val y = x
 * // `y` is a copy of `x`
 * println("$x")
 * // A-OK!
 * ```
 *
 * It's important to note that in these two examples, the only difference is whether you
 * are allowed to access `x` after the assignment. Under the hood, both a copy and a move
 * can result in bits being copied in memory, although this is sometimes optimized away.
 *
 * ## How can I implement `Copy`?
 *
 * There are two ways to implement `Copy` on your type. The simplest is to use `derive`:
 *
 * ```kotlin
 * open class MyStruct {
 *     public open fun clone(): MyStruct = MyStruct()
 * }
 * ```
 *
 * You can also implement `Copy` and `Clone` manually:
 *
 * ```kotlin
 * open class MyStruct {
 *     public open fun clone(): MyStruct {
 *         return MyStruct()
 *     }
 * }
 * ```
 *
 * There is a small difference between the two. The `derive` strategy will also place a `Copy`
 * bound on type parameters:
 *
 * ```kotlin
 * open class MyStruct<T>(public var value: T) {
 *     public var field0: T
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun clone(): MyStruct<T> = MyStruct(value)
 * }
 * ```
 *
 * This isn't always desired. For example, shared references (`T`) can be copied regardless of
 * whether `T` is `Copy`. Likewise, a generic struct containing markers such as [PhantomData]
 * could potentially be duplicated with a bit-wise copy.
 *
 * ## What's the difference between `Copy` and `Clone`?
 *
 * Copies happen implicitly, for example as part of an assignment `y = x`. The behavior of
 * `Copy` is not overloadable; it is always a simple bit-wise copy.
 *
 * Cloning is an explicit action, `x.clone()`. The implementation of [Clone] can
 * provide any type-specific behavior necessary to duplicate values safely. For example,
 * the implementation of [Clone] for [String] needs to copy the pointed-to string
 * buffer in the heap. A simple bitwise copy of [String] values would merely copy the
 * pointer, leading to a double free down the line. For this reason, [String] is [Clone]
 * but not `Copy`.
 *
 * [Clone] is a supertrait of `Copy`, so everything which is `Copy` must also implement
 * [Clone]. If a type is `Copy` then its [Clone] implementation only needs to return `* self`
 * (see the example above).
 *
 * ## When can my type be `Copy`?
 *
 * A type can implement `Copy` if all of its components implement `Copy`. For example, this
 * struct can be `Copy`:
 *
 * ```kotlin
 * open class Point(public var x: Int, public var y: Int) {
 *     public open fun clone(): Point = Point(x, y)
 * }
 * ```
 *
 * A struct can be `Copy`, and [i32] is `Copy`, therefore `Point` is eligible to be `Copy`.
 * By contrast, consider
 *
 * ```kotlin
 * open class PointList(public var points: List<Point>)
 * ```
 *
 * The struct `PointList` cannot implement `Copy`, because [Vec.T] is not `Copy`. If we
 * attempt to derive a `Copy` implementation, we'll get an error:
 *
 * ```text
 * the trait `Copy` cannot be implemented for this type; field `points` does not implement `Copy`
 * ```
 *
 * Shared references (`T`) are also `Copy`, so a type can be `Copy`, even when it holds
 * shared references of types `T` that are *not* `Copy`. Consider the following struct,
 * which can implement `Copy`, because it only holds a *shared reference* to our non-`Copy`
 * type `PointList` from above:
 *
 * ```kotlin
 * open class PointListWrapper(public var pointListRef: PointList) {
 *     public open fun clone(): PointListWrapper = PointListWrapper(pointListRef)
 * }
 * ```
 *
 * ## When *can't* my type be `Copy`?
 *
 * Some types can't be copied safely. For example, copying `T` would create an aliased
 * mutable reference. Copying [String] would duplicate responsibility for managing the
 * [String]s buffer, leading to a double free.
 *
 * Generalizing the latter case, any type implementing [Drop] can't be `Copy`, because it's
 * managing some resource besides its own [sizeOf.T] bytes.
 *
 * If you try to implement `Copy` on a struct or enum containing non-`Copy` data, you will get
 * the error [E0204].
 *
 * [E0204]: ../../errorCodes/E0204.html
 *
 * ## When *should* my type be `Copy`?
 *
 * Generally speaking, if your type _can_ implement `Copy`, it should. Keep in mind, though,
 * that implementing `Copy` is part of the public API of your type. If the type might become
 * non-`Copy` in the future, it could be prudent to omit the `Copy` implementation now, to
 * avoid a breaking API change.
 *
 * ## Additional implementors
 *
 * In addition to the [implementors listed below][impls],
 * the following types also implement `Copy`:
 *
 * * Function item types (i.e., the distinct types defined for each function)
 * * Function pointer types (e.g., `fun() -> Int`)
 * * Closure types, if they capture no value from the environment
 *   or if all such captured values implement `Copy` themselves.
 *   Note that variables captured by shared reference always implement `Copy`
 *   (even if the referent doesn't),
 *   while variables captured by mutable reference never implement `Copy`.
 *
 * [Vec.T]: ../../std/vec/struct.Vec.html
 * [String]: ../../std/string/struct.String.html
 * [sizeOf.T]: sizeOf
 * [impls]: #implementors
 */
public interface Copy

/**
 * Shared references can be copied, but mutable references *cannot*!
 */

/**
 * Marker trait for the types that are allowed in union fields and unchecked
 * binder types.
 *
 * Implemented for:
 * * `T`, `T` for all `T`,
 * * `ManuallyDrop<T>` for all `T`,
 * * tuples and arrays whose elements implement `BikeshedGuaranteedNoDrop`,
 * * or otherwise, all types that are `Copy`.
 *
 * Notably, this doesn't include all trivially-destructible types for semver
 * reasons.
 *
 * Bikeshed name for now. This trait does not do anything other than reflect the
 * set of types that are allowed within unions for field validity.
 */
public interface BikeshedGuaranteedNoDrop

/**
 * Types for which it is safe to share references between threads.
 *
 * This trait is automatically implemented when the compiler determines
 * it's appropriate.
 *
 * The precise definition is: a type `T` is [Sync] if and only if `T` is
 * [Send]. In other words, if there is no possibility of
 * [undefined behavior][ub] (including data races) when passing
 * `T` references between threads.
 *
 * As one would expect, primitive types like [u8] and [f64]
 * are all [Sync], and so are simple aggregate types containing them,
 * like tuples, structs and enums. More examples of basic [Sync]
 * types include "immutable" types like `T`, and those with simple
 * inherited mutability, such as [`Box.T`][box], [`Vec.T`][vec] and
 * most other collection types. (Generic parameters need to be [Sync]
 * for their container to be [Sync].)
 *
 * A somewhat surprising consequence of the definition is that `T`
 * is `Sync` (if `T` is `Sync`) even though it seems like that might
 * provide unsynchronized mutation. The trick is that a mutable
 * reference behind a shared reference (that is, `T`)
 * becomes read-only, as if it were a `T`. Hence there is no risk
 * of a data race.
 *
 * A shorter overview of how [Sync] and [Send] relate to referencing:
 * * `T` is [Send] if and only if `T` is [Sync]
 * * `T` is [Send] if and only if `T` is [Send]
 * * `T` and `T` are [Sync] if and only if `T` is [Sync]
 *
 * Types that are not `Sync` are those that have "interior
 * mutability" in a non-thread-safe form, such as [`Cell`][cell]
 * and [`RefCell`][refcell]. These types allow for mutation of
 * their contents even through an immutable, shared reference. For
 * example the `set` method on [`Cell.T`][cell] takes `self`, so it requires
 * only a shared reference [`Cell.T`][cell]. The method performs no
 * synchronization, thus [`Cell`][cell] cannot be `Sync`.
 *
 * Another example of a non-`Sync` type is the reference-counting
 * pointer [`Rc`][rc]. Given any reference [`Rc.T`][rc], you can clone
 * a new [`Rc.T`][rc], modifying the reference counts in a non-atomic way.
 *
 * For cases when one does need thread-safe interior mutability,
 * Rust provides [atomic data types], as well as explicit locking via
 * [`sync.Mutex`][mutex] and [`sync.RwLock`][rwlock]. These types
 * ensure that any mutation cannot cause data races, hence the types
 * are `Sync`. Likewise, [`sync.Arc`][arc] provides a thread-safe
 * analogue of [`Rc`][rc].
 *
 * Any types with interior mutability must also use the
 * [`cell.UnsafeCell`][unsafecell] wrapper around the value(s) which
 * can be mutated through a shared reference. Failing to doing this is
 * [undefined behavior][ub]. For example, [`transmute`][reinterpret]-ing
 * from `T` to `T` is invalid.
 *
 * See [the Nomicon][nomicon-send-and-sync] for more details about `Sync`.
 *
 * [box]: ../../std/boxed/struct.Holder.html
 * [vec]: ../../std/vec/struct.Vec.html
 * [rc]: ../../std/rc/struct.Rc.html
 * [arc]: ../../std/sync/struct.Arc.html
 * [mutex]: ../../std/sync/struct.Mutex.html
 * [rwlock]: ../../std/sync/struct.RwLock.html
 * [ub]: ../../reference/behavior-considered-undefined.html
 * [nomicon-send-and-sync]: ../../nomicon/send-and-sync.html
 */
public interface Sync

/**
 * Zero-sized type used to mark things that "act like" they own a `T`.
 *
 * Adding a `PhantomData<T>` field to your type tells the compiler that your
 * type acts as though it stores a value of type `T`, even though it doesn't
 * really. This information is used when computing certain safety properties.
 *
 * For a more in-depth explanation of how to use `PhantomData<T>`, please see
 * [the Nomicon](../../nomicon/phantom-data.html).
 *
 * # A ghastly note 👻👻👻
 *
 * Though they both have scary names, `PhantomData` and phantom types' are
 * related, but not identical. A phantom type parameter is simply a type
 * parameter which is never used. In Rust, this often causes the compiler to
 * complain, and the solution is to add a "dummy" use by way of `PhantomData`.
 *
 * # Examples
 *
 * ## Unused scope parameters
 *
 * Perhaps the most common use case for `PhantomData` is a struct that has an
 * unused scope parameter, typically as part of some unchecked code. For
 * example, here is a struct `Slice` that has two pointers of type `* const T`,
 * presumably pointing into an array somewhere:
 *
 * ```compile_fail,E0392
 * struct Slice<a, T> {
 *     start: *const T,
 *     end: *const T,
 * }
 * ```
 *
 * The intention is that the underlying data is only valid for the
 * scope `a`, so `Slice` should not outlive `a`. However, this
 * intent is not expressed in the code, since there are no uses of
 * the scope `a` and hence it is not clear what data it applies
 * to. We can correct this by telling the compiler to act *as if* the
 * `Slice` struct contained a reference `T`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * open class Slice<T>(
 *     public var start: ConstPtr,
 *     public var end: ConstPtr,
 *     public var phantom: Unit,
 * )
 * ```
 *
 * This also in turn infers the scope bound `T:`, indicating
 * that any references in `T` are valid over the scope `a`.
 *
 * When initializing a `Slice` you simply provide the value
 * `PhantomData` for the field `phantom`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun <T> borrowVec(vec: List<T>): Slice<T> {
 *     val ptr = vec.asPtr()
 *     return Slice(start = ptr, end = ConstPtr((ptr).rawAddress + ((vec.size).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), phantom = Unit)
 * }
 * ```
 *
 * ## Unused type parameters
 *
 * It sometimes happens that you have unused type parameters which
 * indicate what type of data a struct is "tied" to, even though that
 * data is not actually found in the struct itself. Here is an
 * example where this arises with [FFI]. The foreign interface uses
 * handles of type `* ()` to refer to Rust values of different
 * types. We track the Rust type using a phantom type parameter on
 * the struct `ExternalResource` which wraps a handle.
 *
 * [FFI]: ../../book/ch19-01-unchecked-rust.html#using-extern-functions-to-call-external-code
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.TypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 *
 * open class ExternalResource<R>(public var resourceHandle: MutPtr, public var resourceType: Unit) {
 *     public open fun new(typeLayoutR: TypeLayout<R> = UnresolvedTypeLayout()): ExternalResource<R> {
 *         val sizeOfRes = sizeOf<R>(typeLayoutT = typeLayoutR)
 *         return ExternalResource(resourceHandle = new(sizeOfRes), resourceType = Unit)
 *     }
 *
 *     public open fun doStuff(param: ParamType) {
 *         val foreignParams = convertParams(param)
 *         doStuff(this.resourceHandle, foreignParams)
 *     }
 * }
 * ```
 *
 * ## Ownership and the drop check
 *
 * The exact interaction of `PhantomData` with drop check **may change in the future**.
 *
 * Currently, adding a field of type `PhantomData<T>` indicates that your type *owns* data of type
 * `T` in very rare circumstances. This in turn has effects on the Rust compiler's [drop check]
 * analysis. For the exact rules, see the [drop check] documentation.
 *
 * ## Layout
 *
 * For all `T`, the following are guaranteed:
 * * `sizeOf<PhantomData<T>>() == 0`
 * * `alignOf<PhantomData<T>>() == 1`
 *
 * [drop check]: Drop#drop-check
 */
public open class PhantomData<T> : Copy, TrivialClone<PhantomData<T>>, StructuralPartialEq, Freeze, UnsafeUnpin, Move {
    public override fun hashCode(): Int = 0

    public open fun eq(other: PhantomData<T>): Boolean = true

    public open fun partialCmp(other: PhantomData<T>): cmp.Ordering? = Option.Some(cmp.Ordering.Equal)

    public open fun cmp(other: PhantomData<T>): cmp.Ordering = cmp.Ordering.Equal

    public open fun clone(): PhantomData<T> = PhantomData()

    public companion object {
        public fun <T> typeLayout(): TypeLayout<PhantomData<T>> =
            object : TypeLayout<PhantomData<T>> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): PhantomData<T> =
                    PhantomData()
                override fun writeNative(addr: Long, value: PhantomData<T>) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): PhantomData<T> =
                    PhantomData()
                override fun writeBytes(buf: ByteArray, offset: Int, value: PhantomData<T>) {
                }
            }

        public fun <T> default(): PhantomData<T> = PhantomData
    }
}

/**
 * Compiler-internal trait used to indicate the type of enum discriminants.
 *
 * This trait is automatically implemented for every type and does not add any
 * guarantees to [mem.Discriminant]. It is **undefined behavior** to reinterpret
 * between `DiscriminantKind.Discriminant` and `mem.Discriminant`.
 */
public interface DiscriminantKind<out Discriminant>

// Note: Eventually this trait should become `@rustcDenyExplicitImpl`.
// That requires porting the impls below to native internal impls.

/**
 * Used to determine whether a type contains
 * any `UnsafeCell` internally, but not through an indirection.
 * This affects, for example, whether a `static` of that type is
 * placed in read-only static memory or writable static memory.
 * This can be used to declare that a constant with a generic type
 * will not contain interior mutability, and subsequently allow
 * placing the constant behind references.
 *
 * # Safety
 *
 * This trait is a core part of the language, it is just expressed as a trait in libcore for
 * convenience. Do *not* implement it for other types.
 */
public interface Freeze

/**
 * Used to determine whether a type contains any `UnsafePinned` (or `PhantomPinned`) internally,
 * but not through an indirection. This affects, for example, whether we emit `noalias` metadata
 * for `T` or not.
 *
 * This is part of [RFC 3467](https://rust-lang.github.io/rfcs/3467-unchecked-pinned.html), and is
 * tracked by [#125735](https://github.com/rust-lang/rust/issues/125735).
 */
public interface UnsafeUnpin

/**
 * Types that do not require any pinning guarantees.
 *
 * For information on what "pinning" is, see the [`pin` module] documentation.
 *
 * Implementing the `Unpin` trait for `T` expresses the fact that `T` is pinning-agnostic:
 * it shall not expose nor rely on any pinning guarantees. This, in turn, means that a
 * `Pin`-wrapped pointer to such a type can feature a *fully unrestricted* API.
 * In other words, if `T: Unpin`, a value of type `T` will *not* be bound by the invariants
 * which pinning otherwise offers, even when "pinned" by a [Pin.Ptr] pointing at it.
 * When a value of type `T` is pointed at by a [Pin.Ptr], [Pin] will not restrict access
 * to the pointee value like it normally would, thus allowing the user to do anything that they
 * normally could with a non-[Pin]-wrapped `Ptr` to that value.
 *
 * The idea of this trait is to alleviate the reduced ergonomics of APIs that require the use
 * of [Pin] for soundness for some types, but which also want to be used by other types that
 * don't care about pinning. The prime example of such an API is [Future.poll]. There are many
 * [Future] types that don't care about pinning. These futures can implement `Unpin` and
 * therefore get around the pinning related restrictions in the API, while still allowing the
 * subset of [Future]s which *do* require pinning to be implemented soundly.
 *
 * For more discussion on the consequences of [Unpin] within the wider scope of the pinning
 * system, see the [section about `Unpin`] in the [`pin` module].
 *
 * `Unpin` has no consequence at all for non-pinned data. In particular, [mem.replace] happily
 * moves `!Unpin` data, which would be immovable when pinned ([mem.replace] works for any
 * `T`, not just when `T: Unpin`).
 *
 * *However*, you cannot use [mem.replace] on `!Unpin` data which is *pinned* by being wrapped
 * inside a [Pin.Ptr] pointing at it. This is because you cannot (safely) use a
 * [Pin.Ptr] to get a `T` to its pointee value, which you would need to call
 * [mem.replace], and *that* is what makes this system work.
 *
 * So this, for example, can only be done on types implementing `Unpin`:
 *
 * ```kotlin
 * import std.mem
 * import std.pin.Pin
 *
 * var string = "this".toString()
 * var pinnedString = Pin.new(string)
 * // We need a mutable reference to call `mem.replace`.
 * // We can obtain such a reference by (implicitly) invoking `Pin.derefMut`,
 * // but that is only possible because `String` implements `Unpin`.
 * mem.replace(pinnedString, "other".toString())
 * ```
 *
 * This trait is automatically implemented for almost every type. The compiler is free
 * to take the conservative stance of marking types as [Unpin] so long as all of the types that
 * compose its fields are also [Unpin]. This is because if a type implements [Unpin], then it
 * is unsound for that type's implementation to rely on pinning-related guarantees for soundness,
 * *even* when viewed through a "pinning" pointer! It is the responsibility of the implementor of
 * a type that relies upon pinning for soundness to ensure that type is *not* marked as [Unpin]
 * by adding [PhantomPinned] field. For more details, see the [`pin` module] docs.
 *
 * [unsafe]: ../../std/keyword.unchecked.html "keyword unchecked"
 */
public interface Unpin

//
// Note(unsafePinned): This is *not* a stable guarantee we want to make, at least not yet.
// Note that for backwards compatibility with the new [UnsafePinned] wrapper type, placing this
// marker in your struct acts as if you wrapped the entire struct in an `UnsafePinned`. This type
// will likely eventually be deprecated, and all new code should be using `UnsafePinned` instead.

/**
 * A marker type which does not implement `Unpin`.
 *
 * If a type contains a `PhantomPinned`, it will not implement `Unpin` by default.
 */
public open class PhantomPinned : UnsafeUnpin {
    public open fun clone(): PhantomPinned = PhantomPinned()

    // This is a small Note to allow existing code which uses PhantomPinned to opt-out of noalias to
    // continue working. Ideally PhantomPinned could just wrap an `UnsafePinned<Unit>` to get the same
    // effect, but we can't add a new field to an already stable unit struct -- that would be a breaking
    // change.

    public companion object {
        public fun typeLayout(): TypeLayout<PhantomPinned> =
            object : TypeLayout<PhantomPinned> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): PhantomPinned =
                    PhantomPinned()
                override fun writeNative(addr: Long, value: PhantomPinned) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): PhantomPinned =
                    PhantomPinned()
                override fun writeBytes(buf: ByteArray, offset: Int, value: PhantomPinned) {
                }
            }
    }
}

/**
 * A marker for types that can be dropped.
 *
 * This should be used for `[const]` bounds,
 * as non-const bounds will always hold for every type.
 */
public interface Destruct

/**
 * A marker for tuple types.
 *
 * The implementation of this trait is built-in and cannot be implemented
 * for any user type.
 */
public interface Tuple

// Note(khyperia): if this is missing, the gca macro's `impliedBy = "gcaAdts"` emits an error
public open class HackGcaAdtsFeature {
    public companion object {
        public fun typeLayout(): TypeLayout<HackGcaAdtsFeature> =
            object : TypeLayout<HackGcaAdtsFeature> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): HackGcaAdtsFeature =
                    HackGcaAdtsFeature()
                override fun writeNative(addr: Long, value: HackGcaAdtsFeature) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): HackGcaAdtsFeature =
                    HackGcaAdtsFeature()
                override fun writeBytes(buf: ByteArray, offset: Int, value: HackGcaAdtsFeature) {
                }
            }
    }
}

// We name this differently than the derive macro so that the `adtConstParams` can
// be used independently of `unsizedConstParams` without requiring a full path
// to the derive macro every time it is used. This should be renamed on stabilization.

/**
 * A marker for types which can be used as types of `const` generic parameters.
 *
 * These types must have a proper equivalence relation (`Eq`) and it must be automatically
 * derived (`StructuralPartialEq`). There's a hard-coded check in the compiler ensuring
 * that all fields are also `ConstParamTy`, which implies that recursively, all fields
 * are `StructuralPartialEq`.
 */
public interface ConstParamTy : StructuralPartialEq

/**
 * A trait that is implemented for ADTs with `derive(CoercePointee)` so that
 * the compiler can enforce the derive impls are valid post-expansion, since
 * the derive has stricter requirements than if the impls were written by hand.
 *
 * This trait is not intended to be implemented by users or used other than
 * validation, so it should never be stabilized.
 */
public interface CoercePointeeValidated

/**
 * Allows value to be reborrowed as exclusive, creating a copy of the value
 * that disables the source for reads and writes for the scope of the copy.
 */
public interface Reborrow

/**
 * Allows reborrowable value to be reborrowed as shared, creating a copy
 * that disables the source for writes for the scope of the copy.
 */
public interface CoerceShared<Target : Copy> : Reborrow

/**
 * Marks a type as "forgettable", giving it the ability to be passed to
 * [`mem.forget`](core.mem.forget), and other apis that may forget values.
 * A notable example of forgetting values, though slightly less obvious than
 * `mem.forget`, is using a value inside an `Rc`. Values inside `Rc`s or `Arc`s
 * can end up being forgotten as the result of a reference cycle.
 *
 * By default, all types are assumed to be `Forget`. Similar to [Sized], the
 * syntax `T: ? Forget` can be used to relax this bound for some type `T`. However,
 * `Forget` bounds are implicitly added in *all* cases unless explicitly opted out
 * of - notably, in trait definitions and associated types. If the `forgetTrait`
 * feature is enabled, these can similarly be opted out of:
 * ```ignore (illustrative, unsupported)
 * @file:feature(forgetTrait)
 * use std.marker.Forget;
 *
 * trait Foo: ?Forget {
 *     type Bar: ?Forget;
 * }
 * ```
 * The explicit goal of [`mem.forget`](core.mem.forget) is not to run the destructor
 * of a value (i.e. its implementation of [`Drop`](core.ops.Drop)).
 * As a dual to this, since `? Forget` types cannot be forgotten, the destructors
 * of `? Forget` types are **guaranteed to be run**.
 */
public interface Forget

/**
 * Marks a type as freely movable to another address in memory.
 *
 * By default, all types are assumed to be `Move`. Similar to [Sized], the
 * syntax `T: ? Move` can be used to relax this bound for some type `T`. However,
 * `Move` bounds are implicitly added in *all* cases unless explicitly opted out
 * of - notably, in trait definitions and associated types. If the `moveTrait`
 * feature is enabled, these can similarly be opted out of:
 * ```kotlin
 * import std.marker.Move
 *
 * @feature(moveTrait)
 * public fun doctestEntry(): Result<Unit> {
 *     interface Foo
 *     return Result.success(Unit)
 * }
 * ```
 */
public interface Move

/**
 * Creates a new style directly represented const argument.
 * ```ignore (cannot test this from within core yet)
 * const BAR<const N: Int>: Int = gca(N);
 * const FOO<const N: Int>: Int = gca(BAR::<N>);
 * ```
 */

public fun gca(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }
