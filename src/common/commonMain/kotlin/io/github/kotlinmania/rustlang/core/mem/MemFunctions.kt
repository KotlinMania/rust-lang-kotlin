package io.github.kotlinmania.rustlang.core.mem

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.intrinsics.discriminantValue
import io.github.kotlinmania.rustlang.core.intrinsics.readViaCopy
import io.github.kotlinmania.rustlang.core.intrinsics.typedSwapNonoverlapping
import io.github.kotlinmania.rustlang.core.intrinsics.writeViaMove
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.panic.constAssert
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

/**
 * Takes ownership and "forgets" about the value **without running its destructor**.
 *
 * Any resources the value manages, such as heap memory or a file handle, will linger
 * forever in an unreachable state. However, it does not guarantee that pointers
 * to this memory will remain valid.
 *
 * * If you want to leak memory, see [Box.leak].
 * * If you want to obtain a raw pointer to the memory, see [Box.intoRaw].
 * * If you want to dispose of a value properly, running its destructor, see
 *   [mem.drop].
 *
 * # Safety
 *
 * `forget` is not marked as `unchecked`, because Rust's safety guarantees
 * do not include a guarantee that destructors will always run. For example,
 * a program can create a reference cycle using [`Rc`][rc], or call
 * [`process.exit`][exit] to exit without running destructors. Thus, allowing
 * `mem.forget` from safe code does not fundamentally change Rust's safety
 * guarantees.
 *
 * That said, leaking resources such as memory or I/O objects is usually undesirable.
 * The need comes up in some specialized use cases for FFI or unchecked code, but even
 * then, [ManuallyDrop] is typically preferred.
 *
 * Because forgetting a value is allowed, any `unchecked` code you write must
 * allow for this possibility. You cannot return a value and expect that the
 * caller will necessarily run the value's destructor.
 *
 * [rc]: ../../std/rc/struct.Rc.html
 * [exit]: ../../std/process/fn.exit.html
 *
 * # Examples
 *
 * The canonical safe use of `mem.forget` is to circumvent a value's destructor
 * implemented by the `Drop` trait. For example, this will leak a `File`, i.e. reclaim
 * the space taken by the variable but never close the underlying system resource:
 *
 * ```kotlin
 * import std.fs.File
 * import std.mem
 *
 * val file = File.open("foo.txt").unwrap()
 * mem.forget(file)
 * ```
 *
 * This is useful when the ownership of the underlying resource was previously
 * transferred to code outside of Rust, for example by transmitting the raw
 * file descriptor to C code.
 *
 * # Relationship with `ManuallyDrop`
 *
 * While `mem.forget` can also be used to transfer *memory* ownership, doing so is error-prone.
 * [ManuallyDrop] should be used instead. Consider, for example, this code:
 *
 * ```kotlin
 * import std.mem
 *
 * var v = mutableListOf(65, 122)
 * // Build a `String` using the contents of `v`
 * val s = String.fromRawParts(v.asMutPtr(), v.size, v.capacity())
 * // leak `v` because its memory is now managed by `s`
 * mem.forget(v)
 * // ERROR - v is invalid and must not be passed to a function
 * check(s == "Az")
 * // `s` is implicitly dropped and its memory deallocated.
 * ```
 *
 * There are two issues with the above example:
 *
 * * If more code were added between the construction of `String` and the invocation of
 *   `mem.forget()`, a panic within it would cause a double free because the same memory
 *   is handled by both `v` and `s`.
 * * After calling `v.asMutPtr()` and transmitting the ownership of the data to `s`,
 *   the `v` value is invalid. Even when a value is just moved to `mem.forget` (which won't
 *   inspect it), some types have strict requirements on their values that
 *   make them invalid when dangling or no longer owned. Using invalid values in any
 *   way, including passing them to or returning them from functions, constitutes
 *   undefined behavior and may break the assumptions made by the compiler.
 *
 * Switching to `ManuallyDrop` avoids both issues:
 *
 * ```kotlin
 * import ptr
 *
 * val v = mutableListOf(65, 122)
 * // Before we disassemble `v` into its raw parts, make sure it
 * // does not get dropped!
 * var v1 = v
 * // Now disassemble `v`. These operations cannot panic, so there cannot be a leak.
 * val (ptr, len, cap) = Triple(v1.asMutPtr(), v1.len(), v1.capacity())
 * // Finally, build a `String`.
 * val s = String.fromRawParts(ptr, len, cap)
 * check(s == "Az")
 * // `s` is implicitly dropped and its memory deallocated.
 * ```
 *
 * `ManuallyDrop` robustly prevents double-free because we disable `v`s destructor
 * before doing anything else. `mem.forget()` doesn't allow this because it consumes its
 * argument, forcing us to call it only after extracting anything we need from `v`. Even
 * if a panic were introduced between construction of `ManuallyDrop` and building the
 * string (which cannot happen in the code as shown), it would result in a leak and not a
 * double free. In other words, `ManuallyDrop` errs on the side of leaking instead of
 * erring on the side of (double-)dropping.
 *
 * Also, `ManuallyDrop` prevents us from having to "touch" `v` after transferring the
 * ownership to `s` — the final step of interacting with `v` to dispose of it without
 * running its destructor is entirely avoided.
 *
 * [Box]: ../../std/boxed/struct.Holder.html
 * [Box.leak]: ../../std/boxed/struct.Holder.html#method.leak
 * [Box.intoRaw]: ../../std/boxed/struct.Holder.html#method.intoRaw
 * [mem.drop]: drop
 * [ub]: ../../reference/behavior-considered-undefined.html
 */
public fun <T> forget(t: T) {
    t
}

/**
 * Like [forget], but also accepts unsized values.
 *
 * While Rust does not permit unsized locals since its removal in [#111942] it is
 * still possible to call functions with unsized values from a function argument
 * or place expression.
 *
 * ```kotlin
 * @feature(unsizedFnParams, forgetUnsized)
 * public fun doctestEntry() {
 *     fun inPlace() {
 *         forgetUnsized(Holder.from("str"))
 *     }
 *     fun param(x: String) {
 *         forgetUnsized(x)
 *     }
 * }
 * ```
 *
 * This works because the compiler will alter these functions to pass the parameter
 * by reference instead. This trick is necessary to support `Holder<dynamic FnOnce()>: FnOnce()`.
 * See [#68304] and [#71170] for more information.
 *
 * [#111942]: https://github.com/rust-lang/rust/issues/111942
 * [#68304]: https://github.com/rust-lang/rust/issues/68304
 * [#71170]: https://github.com/rust-lang/rust/pull/71170
 */
public fun <T> forgetUnsized(t: T) {}

/**
 * Returns the size of a type in bytes.
 *
 * More specifically, this is the offset in bytes between successive elements
 * in an array with that item type including alignment padding. Thus, for any
 * type `T` and length `n`, `[T; n]` has a size of `n * sizeOf<T>()`.
 *
 * In general, the size of a type is not stable across compilations, but
 * specific types such as primitives are.
 *
 * The following table gives the size for primitives.
 *
 * Type | `sizeOf<Type>()`
 * ---- | ---------------
 * () | 0
 * bool | 1
 * u8 | 1
 * u16 | 2
 * u32 | 4
 * u64 | 8
 * u128 | 16
 * i8 | 1
 * i16 | 2
 * i32 | 4
 * i64 | 8
 * i128 | 16
 * f32 | 4
 * f64 | 8
 * char | 4
 *
 * Furthermore, `Int` and `Long` have the same size.
 *
 * The types [const.T], `T`, [Box.T], [Option.T], and `Option<Holder<T>>` all have
 * the same size. If `T` is `Sized`, all of those types have the same size as `Int`.
 *
 * The mutability of a pointer does not change its size. As such, `T` and `T`
 * have the same size. Likewise for `* const T` and `* T`.
 *
 * # Size of `@repr(C)` items
 *
 * The `C` representation for items has a defined layout. With this layout,
 * the size of items is also stable as long as all fields have a stable size.
 *
 * ## Size of Structs
 *
 * For `class`s, the size is determined by the following algorithm.
 *
 * For each field in the struct ordered by declaration order:
 *
 * 1. Add the size of the field.
 * 2. Round up the current size to the nearest multiple of the next field's [alignment].
 *
 * Finally, round the size of the struct to the nearest multiple of its [alignment].
 * The alignment of the struct is usually the largest alignment of all its
 * fields; this can be changed with the use of `repr(align(N))`.
 *
 * Unlike `C`, zero sized structs are not rounded up to one byte in size.
 *
 * ## Size of Enums
 *
 * Enums that carry no data other than the discriminant have the same size as C enums
 * on the platform they are compiled for.
 *
 * ## Size of Unions
 *
 * The size of a union is the size of its largest field.
 *
 * Unlike `C`, zero sized unions are not rounded up to one byte in size.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * // Some primitives
 * check(4 == sizeOf<Int>())
 * check(8 == sizeOf<Double>())
 * check(0 == sizeOf<Unit>())
 * // Some arrays
 * check(8 == sizeOf<IntArray>())
 * check(12 == sizeOf<IntArray>())
 * check(0 == sizeOf<IntArray>())
 * // Pointer size equality
 * check(sizeOf<Int>() == sizeOf<ConstPtr>())
 * check(sizeOf<Int>() == sizeOf<Int>())
 * check(sizeOf<Int>() == sizeOf<Int?>())
 * check(sizeOf<Int>() == sizeOf<Int?>())
 * ```
 *
 * Using `@repr(C)`.
 *
 * ```kotlin
 * @repr(C)
 * open class FieldStruct(
 *     public var first: UByte,
 *     public var second: UShort,
 *     public var third: UByte,
 * )
 * @repr(C)
 * open class TupleStruct(
 *     public var field0: UByte,
 *     public var field1: UShort,
 *     public var field2: UByte,
 * )
 * // Note that reordering the fields can lower the size. We can remove both padding bytes
 * // by putting `third` before `second`.
 * @repr(C)
 * open class FieldStructOptimized(
 *     public var first: UByte,
 *     public var third: UByte,
 *     public var second: UShort,
 * )
 * // Union size is the size of the largest field.
 * @repr(C)
 * open class ExampleUnion(public var smaller: UByte? = null, public var larger: UShort? = null)
 * // The size of the first field is 1, so add 1 to the size. Size is 1.
 * // The alignment of the second field is 2, so add 1 to the size for padding. Size is 2.
 * // The size of the second field is 2, so add 2 to the size. Size is 4.
 * // The alignment of the third field is 1, so add 0 to the size for padding. Size is 4.
 * // The size of the third field is 1, so add 1 to the size. Size is 5.
 * // Finally, the alignment of the struct is 2 (because the largest alignment amongst its
 * // fields is 2), so add 1 to the size for padding. Size is 6.
 * check(6 == sizeOf<FieldStruct>())
 * // Tuple structs follow the same rules.
 * check(6 == sizeOf<TupleStruct>())
 * check(4 == sizeOf<FieldStructOptimized>())
 * check(2 == sizeOf<ExampleUnion>())
 * ```
 *
 * [alignment]: alignOf
 * [const.T]: primitive@pointer
 * [Box.T]: ../../std/boxed/struct.Holder.html
 */
public fun <T> sizeOf(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // By making this a constant, we also guarantee that the constant can be successfully evaluated
    // in any program execution that actually executes `sizeOf`. Which is relevant because the
    // constant can fail to evaluate if the type is too big. Someone might do something cursed where
    // soundness relies on a certain type not being too big, and they check that by just invoking
    // sizeOf on the type to ensure it exists, so if we fully DCE'd sizeOf calls that would be
    // considered unsound... but by making this a constant, it participates in the usual "required
    // consts" system, and we are safe.
    return (typeLayoutT).sizeBytes.toInt()
}

/**
 * Returns the size of the pointed-to value in bytes.
 *
 * This is usually the same as [sizeOf.T]. However, when `T` *has* no
 * statically-known size, e.g., a slice [`[T]`][slice] or a [trait object],
 * then `sizeOfVal` can be used to get the dynamically-known size.
 *
 * [trait object]: ../../book/ch17-02-trait-objects.html
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     check(4 == sizeOfVal(5))
 *     val x: UByteArray = UByteArray(13) { 0u.toUByte() }
 *     val y: List<UByte> = x
 *     check(13 == sizeOfVal(y))
 * }
 * ```
 *
 * [sizeOf.T]: sizeOf
 */
public fun <T> sizeOfVal(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: `val` is a reference, so it's a valid raw pointer
    return (typeLayoutT).sizeBytes.toInt()
}

// NOTE: the reason this is safe is that if an overflow were to occur already with size 0,
// then we would stop compilation as even the "statically known" part of the type would
// already be too big (or the call may be in dead code and optimized away, but then it
// doesn't matter).

/**
 * Returns the size of the pointed-to value in bytes.
 *
 * This is usually the same as [sizeOf.T]. However, when `T` *has* no
 * statically-known size, e.g., a slice [`[T]`][slice] or a [trait object],
 * then `sizeOfValRaw` can be used to get the dynamically-known size.
 *
 * # Safety
 *
 * This function is safe to call if the pointer is safe to reborrow as `T`
 * (in which case you could also call [sizeOfVal]).
 * Otherwise, the following conditions must hold:
 *
 * - If `T` is `Sized`, this function is always safe to call.
 * - If the *unsized tail* of `T` is:
 *     - a [slice] `[U]`, `String`, or a [trait object] `dynamic Trait`, then the size of the *entire value*
 *       (dynamic tail length + statically sized prefix) must fit in `Long`.
 *       For the special case where the dynamic tail length is 0, this function
 *       is safe to call.
 *     - No other kind of unsized tail currently exists that satisfies the trait bounds for this
 *       function. If more kinds of unsized tails get introduced in the future, the documentation
 *       of this function will have to be extended before it can be used for such types.
 *
 * Here, *unsized tail* refers to the type obtained by recursively descending through the last
 * field of a tuple or struct until we arrived at a built-in unsized type.
 *
 * As a consequence of these rules, it is the case that whenever it is allowed to convert `val`
 * into a shared reference, then it is also allowed to invoke this function.
 *
 * [sizeOf.T]: sizeOf
 * [trait object]: ../../book/ch17-02-trait-objects.html
 * [extern type]: ../../unstable-book/language-features/extern-types.html
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import std.mem
 *
 * public fun doctestEntry() {
 *     check(4 == sizeOfVal(5))
 *     val x: UByteArray = UByteArray(13) { 0u.toUByte() }
 *     val y: List<UByte> = x
 *     check(13 == mem.sizeOfValRaw(y))
 * }
 * ```
 */
public fun <T> sizeOfValRaw(value: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: the caller must provide a valid raw pointer
    return (if (value.rawMeta > 0L) (value.rawMeta * (typeLayoutT).sizeBytes).toInt() else (typeLayoutT).sizeBytes.toInt())
}

/**
 * Returns the [ABI]-required minimum alignment of a type in bytes.
 *
 * Every reference to a value of the type `T` must be a multiple of this number.
 *
 * This is the alignment used for struct fields. It may be smaller than the preferred alignment.
 *
 * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
 *
 * # Examples
 *
 * ```kotlin
 * import std.mem
 *
 * check(4 == mem.minAlignOf<Int>())
 * ```
 */
public fun <T> minAlignOf(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int = (typeLayoutT).alignBytes.toInt()

/**
 * Returns the [ABI]-required minimum alignment of the type of the value that `val` points to in
 * bytes.
 *
 * Every reference to a value of the type `T` must be a multiple of this number.
 *
 * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
 *
 * # Examples
 *
 * ```kotlin
 * import std.mem
 *
 * check(4 == mem.minAlignOfVal(5))
 * ```
 */
public fun <T> minAlignOfVal(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: val is a reference, so it's a valid raw pointer
    return (typeLayoutT).alignBytes.toInt()
}

/**
 * Returns the [ABI]-required minimum alignment of a type, in bytes.
 *
 * Every reference to a value of the type `T` must be a multiple of this number.
 *
 * This is the alignment used for struct fields. It may be smaller than the preferred alignment.
 *
 * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
 *
 * # Examples
 *
 * ```kotlin
 * check(4 == alignOf<Int>())
 * ```
 *
 * (Caution: [it is not guaranteed][type-layout] that the alignment of `Int` is `4`;
 * that is, the above assertion does not pass on all platforms.)
 *
 * [type-layout]: ../../reference/type-layout.html#r-layout.primitive
 */
public fun <T> alignOf(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int = (typeLayoutT).alignBytes.toInt()

/**
 * Returns the [ABI]-required minimum alignment of the type of the value that `val` points to, in
 * bytes.
 *
 * This function is identical to [`alignOf.T`][alignOf] whenever <code>T: [Sized]</code>,
 * but also supports determining the alignment required by a `dynamic Trait` value, which is the
 * alignment of the underlying concrete type.
 *
 * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
 *
 * # Examples
 *
 * ```kotlin
 * check(4 == alignOfVal(5))
 * ```
 *
 * (Caution: [it is not guaranteed][type-layout] that the alignment of `Int` is `4`;
 * that is, this example assertion does not pass on all platforms.)
 *
 * `dynamic` types may have different alignments for different values;
 * `alignOfVal` can be used to learn those alignments:
 *
 * ```kotlin
 * val a: ToString = 1234u.toUShort()
 * val b: ToString = "abcd"
 * check(alignOfVal(a) == alignOf<UShort>())
 * check(alignOfVal(b) == alignOf<String>())
 * ```
 *
 * [type-layout]: ../../reference/type-layout.html#r-layout.primitive
 */
public fun <T> alignOfVal(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: val is a reference, so it's a valid raw pointer
    return (typeLayoutT).alignBytes.toInt()
}

// NOTE: the reason this is safe is that if an overflow were to occur already with size 0,
// then we would stop compilation as even the "statically known" part of the type would
// already be too big (or the call may be in dead code and optimized away, but then it
// doesn't matter).

/**
 * Returns the [ABI]-required minimum alignment of the type of the value that `val` points to, in
 * bytes.
 *
 * This function is identical to [alignOfVal], except that it can be used with raw pointers
 * in situations where it would be unsound or undesirable to convert them to
 * [ references][primitive@reference] and impose the aliasing rules that come with that.
 *
 * [ABI]: https://en.wikipedia.org/wiki/ApplicationBinaryInterface
 *
 * # Safety
 *
 * This function is safe to call if the pointer is safe to reborrow as `T`
 * (in which case you could also call [alignOfVal]).
 * Otherwise, the following conditions must hold:
 *
 * - If `T` is `Sized`, this function is always safe to call.
 * - If the unsized tail of `T` is:
 *     - a [slice] `[U]`, `String`, or a [trait object] `dynamic Trait`, then the size of the *entire value*
 *       (dynamic tail length + statically sized prefix) must fit in `Long`.
 *       For the special case where the dynamic tail length is 0, this function
 *       is safe to call.
 *     - No other kind of unsized tail currently exists that satisfies the trait bounds for this
 *       function. If more kinds of unsized tails get introduced in the future, the documentation
 *       of this function will have to be extended before it can be used for such types.
 *
 * Here, *unsized tail* refers to the type obtained by recursively descending through the last
 * field of a tuple or struct until we arrived at a built-in unsized type.
 *
 * As a consequence of these rules, it is the case that whenever it is allowed to convert `val`
 * into a shared reference, then it is also allowed to invoke this function.
 *
 * [trait object]: ../../book/ch17-02-trait-objects.html
 * [extern type]: ../../unstable-book/language-features/extern-types.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.mem
 *
 * check(4 == mem.alignOfValRaw(5))
 * ```
 *
 * (Caution: [it is not guaranteed][type-layout] that the alignment of `Int` is `4`;
 * that is, the above assertion does not pass on all platforms.)
 *
 * [type-layout]: ../../reference/type-layout.html#r-layout.primitive
 */
public fun <T> alignOfValRaw(value: ConstPtr, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Int {
    // SAFETY: the caller must provide a valid raw pointer
    return (typeLayoutT).alignBytes.toInt()
}

/**
 * Returns `true` if dropping values of type `T` matters.
 *
 * This is purely an optimization hint, and may be implemented conservatively:
 * it may return `true` for types that don't actually need to be dropped.
 * As such always returning `true` would be a valid implementation of
 * this function. However if this function actually returns `false`, then you
 * can be certain dropping `T` has no side effect.
 *
 * Low level implementations of things like collections, which need to manually
 * drop their data, should use this function to avoid unnecessarily
 * trying to drop all their contents when they are destroyed. This might not
 * make a difference in release builds (where a loop that has no side-effects
 * is easily detected and eliminated), but is often a big win for debug builds.
 *
 * Note that [dropInPlace] already performs this check, so if your workload
 * can be reduced to some small number of [dropInPlace] calls, using this is
 * unnecessary. In particular note that you can [dropInPlace] a slice, and that
 * will do a single needsDrop check for all the values.
 *
 * Types like Vec therefore just `dropInPlace(self[..])` without using
 * `needsDrop` explicitly. Types like [HashMap], on the other hand, have to drop
 * values one at a time and should use this API.
 *
 * [HashMap]: ../../std/collections/struct.HashMap.html
 *
 * # Examples
 *
 * Here's an example of how a collection might make use of `needsDrop`:
 *
 * ```kotlin
 * import ptr
 * import std.mem
 *
 * open class MyCollection<T> {
 *     public open fun drop() {
 *         run {
 *             // drop the data
 *             if (mem.needsDrop<T>()) {
 *                 for (x in this.iterMut()) {
 *                     ptr.dropInPlace(x)
 *                 }
 *             }
 *             this.freeBuffer()
 *         }
 *     }
 * }
 * ```
 */
public fun <T> needsDrop(): Boolean = true

/**
 * Returns the value of type `T` represented by the all-zero byte-pattern.
 *
 * This means that, for example, the padding byte in `(UByte, UShort)` is not
 * necessarily zeroed.
 *
 * This has the same effect as [`MaybeUninit.zeroed.assumeInit`][zeroed].
 * It is useful for FFI sometimes, but should generally be avoided.
 *
 * # Safety
 *
 * The all-zero byte-pattern must represent a valid value of type `T`.
 * For example, it is not valid for reference types (`T`, `T`) or function
 * pointers. Using `zeroed` on such types causes immediate [undefined behavior][ub]
 * because [the Rust compiler assumes][inv] that there always is a valid value in a
 * variable it considers initialized.
 *
 * [zeroed]: MaybeUninit.zeroed
 * [ub]: ../../reference/behavior-considered-undefined.html
 * [inv]: MaybeUninit#initialization-invariant
 *
 * # Examples
 *
 * Correct usage of this function: initializing an integer with zero.
 *
 * ```kotlin
 * val x: Int = 0
 * check(0 == x)
 * ```
 *
 * *Incorrect* usage of this function: initializing a reference with zero.
 *
 * ```kotlin
 * public fun interface FnCallback {
 *     public operator fun invoke()
 * }
 *
 * public fun doctestEntry() {
 *     val x: Int = 0
 *     // Undefined behavior!
 *     val y: FnCallback = FnCallback {}
 *     // And again!
 * }
 * ```
 */
public inline fun <reified T> zeroed(): T {
    // SAFETY: the caller must guarantee that an all-zero value is valid for `T`.
    return run {
        MaybeUninit.zeroed(UnresolvedTypeLayout<T>()).assumeInit()
    }
}

/**
 * Bypasses Rust's normal memory-initialization checks by pretending to
 * produce a value of type `T`, while doing nothing at all.
 *
 * **This function is deprecated.** Use [MaybeUninit.T] instead.
 * It also might be slower than using `MaybeUninit<T>` due to mitigations that were put in place to
 * limit the potential harm caused by incorrect use of this function in legacy code.
 *
 * The reason for deprecation is that the function basically cannot be used
 * correctly: it has the same effect as [`MaybeUninit.uninit.assumeInit`][uninit].
 * As the [`assumeInit` documentation][assumeInit] explains,
 * [the Rust compiler assumes][inv] that values are properly initialized.
 *
 * Truly uninitialized memory like what gets returned here
 * is special in that the compiler knows that it does not have a fixed value.
 * This makes it undefined behavior to have uninitialized data in a variable even
 * if that variable has an integer type.
 *
 * Therefore, it is immediate undefined behavior to call this function on nearly all types,
 * including integer types and arrays of integer types, and even if the result is unused.
 *
 * [uninit]: MaybeUninit.uninit
 * [assumeInit]: MaybeUninit.assumeInit
 * [inv]: MaybeUninit#initialization-invariant
 */
public inline fun <reified T> uninitialized(): T {
    // SAFETY: the caller must guarantee that an uninitialized value is valid for `T`.
    return run {
        val value = MaybeUninit.uninit(UnresolvedTypeLayout<T>())
        // Fill memory with 0x01, as an imperfect mitigation for old code that uses this function on
        // bool, nonnull, and noundef types. But don't do this if we actively want to detect UB.
        if (!false) {
            value.asMutPtr().writeBytes(0x01, 1)
        }
        value.assumeInit()
    }
}

/**
 * Swaps the values at two mutable locations, without deinitializing either one.
 *
 * * If you want to swap with a default or dummy value, see [take].
 * * If you want to swap with a passed value, returning the old value, see [replace].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.mem
 *
 * val localStorageL5C0 = AddressableLocal(5, PrimitiveLayouts.I32)
 * var x by localStorageL5C0
 * val localStorageL6C0 = AddressableLocal(42, PrimitiveLayouts.I32)
 * var y by localStorageL6C0
 * mem.swap(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout), RustMutableReference(localStorageL6C0.asMutPtr(), localStorageL6C0.layout))
 * check(42 == x)
 * check(5 == y)
 * ```
 */
public fun <T> swap(x: T, y: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
    // SAFETY:  guarantees these are typed readable and writable
    // as well as non-overlapping.
    typedSwapNonoverlapping(x, y, typeLayoutT = typeLayoutT)
}

/**
 * Replaces `dest` with the default value of `T`, returning the previous `dest` value.
 *
 * * If you want to switch the values of two variables, see [swap].
 * * If you want to replace with a passed value instead of the default value, see [replace].
 *
 * # Examples
 *
 * A simple example:
 *
 * ```kotlin
 * import std.mem
 *
 * var v: List<Int> = mutableListOf(1, 2)
 * val oldV = mem.take(v)
 * check(mutableListOf(1, 2) == oldV)
 * check(v.isEmpty())
 * ```
 *
 * `take` allows taking ownership of a struct field by replacing it with an "empty" value.
 * Without `take` you can run into issues like these:
 *
 * ```compile_fail,E0507
 * struct Buffer<T> { buf: Vec<T> }
 *
 * impl<T> Buffer<T> {
 *     fn getAndReset(self) -> Vec<T> {
 *         // error: cannot move out of dereference of `&mut`-pointer
 *         let buf = self.buf;
 *         self.buf = Vec.new();
 *         buf
 *     }
 * }
 * ```
 *
 * Note that `T` does not necessarily implement [Clone], so it can't even clone and reset
 * `self.buf`. But `take` can be used to disassociate the original value of `self.buf` from
 * `self`, allowing it to be returned:
 *
 * ```kotlin
 * import std.mem
 *
 * fun <T> Buffer<T>.getAndReset(): List<T> {
 *     return mem.take(this.buf)
 * }
 * var buffer = Buffer(buf = mutableListOf(0, 1))
 * check(buffer.buf.len() == 2)
 * check(buffer.getAndReset() == mutableListOf(0, 1))
 * check(buffer.buf.len() == 0)
 * ```
 */
public fun <T> take(dest: T, defaultTFn: () -> T): T = replace(dest, defaultTFn())

/**
 * Moves `src` into the referenced `dest`, returning the previous `dest` value.
 *
 * Neither value is dropped.
 *
 * * If you want to replace the values of two variables, see [swap].
 * * If you want to replace with a default value, see [take].
 *
 * # Examples
 *
 * A simple example:
 *
 * ```kotlin
 * import std.mem
 *
 * var v: List<Int> = mutableListOf(1, 2)
 * val oldV = mem.replace(v, mutableListOf(3, 4, 5))
 * check(mutableListOf(1, 2) == oldV)
 * check(mutableListOf(3, 4, 5) == v)
 * ```
 *
 * `replace` allows consumption of a struct field by replacing it with another value.
 * Without `replace` you can run into issues like these:
 *
 * ```compile_fail,E0507
 * struct Buffer<T> { buf: Vec<T> }
 *
 * impl<T> Buffer<T> {
 *     fn replaceIndex(self, i: Int, v: T) -> T {
 *         // error: cannot move out of dereference of `&mut`-pointer
 *         let t = self.buf[i];
 *         self.buf[i] = v;
 *         t
 *     }
 * }
 * ```
 *
 * Note that `T` does not necessarily implement [Clone], so we can't even clone `self.buf[i]` to
 * avoid the move. But `replace` can be used to disassociate the original value at that index from
 * `self`, allowing it to be returned:
 *
 * ```kotlin
 * import std.mem
 *
 * fun <T> Buffer<T>.replaceIndex(i: Int, v: T): T {
 *     return mem.replace(this.buf[i], v)
 * }
 * var buffer = Buffer(buf = mutableListOf(0, 1))
 * check(buffer.buf[0] == 0)
 * check(buffer.replaceIndex(0, 2) == 0)
 * check(buffer.buf[0] == 2)
 * ```
 */
public fun <T> replace(dest: T, src: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // It may be tempting to use `swap` to avoid `unchecked` here. Don't!
    // The compiler optimizes the implementation below to two `memcpy`s
    // while `swap` would require at least three. See PR#83022 for details.
    // SAFETY: We read from `dest` but directly write `src` into it afterwards,
    // such that the old value is not duplicated. Nothing is dropped and
    // nothing here can panic.
    return run {
        // Ideally we wouldn't use the intrinsics here, but going through the
        // `ptr` methods introduces two unnecessary UbChecks, so until we can
        // remove those for pointers that come from references, this uses the
        // intrinsics instead so this stays very cheap in MIR (and debug).
        val result = readViaCopy(dest, typeLayoutT = typeLayoutT)
        writeViaMove(dest, src, typeLayoutT = typeLayoutT)
        result
    }
}

/**
 * Disposes of a value.
 *
 * This effectively does nothing for types which implement `Copy`, e.g.
 * integers. Such values are copied and _then_ moved into the function, so the
 * value persists after this function call.
 *
 * This function is not magic; it is literally defined as
 *
 * ```kotlin
 * fun <T> drop(x: T) {}
 * ```
 *
 * Because `_x` is moved into the function, it is automatically [dropped][drop] before
 * the function returns.
 *
 * [drop]: Drop
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val v = mutableListOf(1, 2, 3)
 * drop(v)
 * // explicitly drop the vector
 * ```
 *
 * Since [RefCell] enforces the borrow rules at runtime, `drop` can
 * release a [RefCell] borrow:
 *
 * ```kotlin
 * import std.cell.RefCell
 *
 * val x = RefCell.new(1)
 * var mutableBorrow = x.borrowMut()
 * mutableBorrow = 1
 * drop(mutableBorrow)
 * // relinquish the mutable borrow on this slot
 * val borrow = x.borrow()
 * println("$borrow")
 * ```
 *
 * Integers and other types implementing [Copy] are unaffected by `drop`.
 *
 * ```kotlin
 * import clone
 *
 * open class Foo(public var value: UByte) {
 *     public var field0: UByte
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun clone(): Foo = Foo(value)
 * }
 * val x = 1
 * val y = Foo(2)
 * drop(x)
 * // a copy of `x` is moved and dropped
 * drop(y)
 * // a copy of `y` is moved and dropped
 * println("x: $x, y: ${y.first}")
 * // still available
 * ```
 */
public fun <T> drop(x: T) {}

/**
 * Bitwise-copies a value.
 *
 * This function is not magic; it is literally defined as
 * ```kotlin
 * fun <T> copy(x: T): T {
 *     return x
 * }
 * ```
 *
 * It is useful when you want to pass a function pointer to a combinator, rather than defining a new closure.
 *
 * Example:
 * ```kotlin
 * @feature(memCopyFn)
 * public fun doctestEntry() {
 *     val resultFromFfiFunction: Result<Unit> = Result.failure(1)
 *     val resultCopied: Result<Unit> = resultFromFfiFunction.mapErr(::copy)
 * }
 * ```
 */
public fun <T> copy(x: T): T = x

/**
 * Interprets `src` as having type `Dst`, and then reads `src` without moving
 * the contained value.
 *
 * This function will unsafely assume the pointer `src` is valid for [`sizeOf.Dst`][sizeOf]
 * bytes by transmuting `Src` to `Dst` and then reading the `Dst` (except that this is done
 * in a way that is correct even when `Dst` has stricter alignment requirements than `Src`).
 * It will also unsafely create a copy of the contained value instead of moving out of `src`.
 *
 * It is not a compile-time error if `Src` and `Dst` have different sizes, but it
 * is highly encouraged to only invoke this function where `Src` and `Dst` have the
 * same size. This function triggers [undefined behavior][ub] if `Dst` is larger than
 * `Src`.
 *
 * [ub]: ../../reference/behavior-considered-undefined.html
 *
 * If you have a raw pointer instead of a reference, you might be looking for
 * `src.cast<Dst>().`[`readUnaligned`](pointer#method.readUnaligned) instead.
 *
 * # Safety
 *
 * - Requires `sizeOfVal<Src>(src) >= sizeOf<Dst>()`
 * - The first `sizeOf<Dst>()` bytes behind `src` must be *readable*
 * - The first `sizeOf<Dst>()` bytes behind `src` must be *[valid]*
 *   when interpreted as a `Dst`.
 *
 * On top of that, remember that most types have additional invariants beyond merely
 * being considered initialized at the type level. For example, a `1`-initialized [Vec.T]
 * is considered initialized (under the current implementation; this does not constitute
 * a stable guarantee) because the only requirement the compiler knows about it
 * is that the data pointer must be non-null. Creating such a `List<T>` does not cause
 * *immediate* undefined behavior, but will cause undefined behavior with most
 * safe operations (including dropping it).
 *
 * [valid]: ../../reference/behavior-considered-undefined.html#r-undefined.validity
 * [Vec.T]: ../../std/vec/struct.Vec.html
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import std.mem
 *
 * public fun doctestEntry() {
 *     @repr(packed)
 *     open class Foo(public var bar: UByte)
 *     val fooArray = ubyteArrayOf(10u.toUByte())
 *     run {
 *         // Copy the data from `fooArray` and treat it as a `Foo`
 *         var fooStruct: Foo = mem.transmuteCopy(fooArray)
 *         check(fooStruct.bar == 10u.toUByte())
 *         // Modify the copied data
 *         fooStruct.bar = 20u
 *         check(fooStruct.bar == 20u.toUByte())
 *     }
 *     // The contents of `fooArray` should not have changed
 *     check(fooArray == ubyteArrayOf(10u))
 *     val bytes: List<UByte> = listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte())
 *     check(mem.transmuteCopy<List<UByte>, UInt>(bytes) == run { val bytesTmp0 = bytes.firstChunk().unwrap(); ((bytesTmp0[3].toUInt() shl 24) or (bytesTmp0[2].toUInt() shl 16) or (bytesTmp0[1].toUInt() shl 8) or bytesTmp0[0].toUInt()) })
 * }
 * ```
 */
public fun <Src, Dst> transmuteCopy(src: Src, typeLayoutDst: TypeLayout<Dst> = UnresolvedTypeLayout(), typeLayoutSrc: TypeLayout<Src> = UnresolvedTypeLayout()): Dst {
    // library UB because it's possible for the `Src` to be only a subset of the allocation
    // and thus for a failure to not be immediate language UB
    Unit
    // If Dst has a higher alignment requirement, src might not be suitably aligned.
    return if (alignOf<Dst>(typeLayoutT = typeLayoutDst) > alignOfVal<Src>(src, typeLayoutT = typeLayoutSrc)) {
        // SAFETY: `src` is a reference which is guaranteed to be valid for reads.
        // The caller must guarantee that the actual transmutation is safe.
        readUnaligned((constPtrFromRustCast(src)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, typeLayoutT = typeLayoutDst)
    } else {
        // SAFETY: `src` is a reference which is guaranteed to be valid for reads.
        // We just checked that `src as * const Dst` was properly aligned.
        // The caller must guarantee that the actual transmutation is safe.
        read((constPtrFromRustCast(src)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, typeLayoutT = typeLayoutDst)
    }
}

/**
 * Like [transmute], but only initializes the "common prefix" of the first
 * `min(sizeOf<Src>(), sizeOf<Dst>())` bytes of the destination from the
 * corresponding bytes of the source.
 *
 * This is equivalent to a "union cast" through a `union` with `@repr(C)`.
 *
 * That means some size mismatches are not UB, like `[T; 2]` to `[T; 1]`.
 * Increasing size is usually UB from being insufficiently initialized -- like
 * `UByte` to `UInt` -- but isn't always.  For example, going from `UByte` to
 * `@repr(C, align(4)) AlignedU8(UByte);` is sound.
 *
 * Prefer normal `reinterpret` where possible, for the extra checking, since
 * both do exactly the same thing at runtime, if they both compile.
 *
 * # Safety
 *
 * If `sizeOf<Src>() >= sizeOf<Dst>()`, the first `sizeOf<Dst>()` bytes
 * of `src` must be be *valid* when interpreted as a `Dst`.  (In this case, the
 * preconditions are the same as for `transmuteCopy(ManuallyDrop.new(src))`.)
 *
 * If `sizeOf<Src>() <= sizeOf<Dst>()`, the bytes of `src` padded with
 * uninitialized bytes afterwards up to a total size of `sizeOf<Dst>()`
 * must be *valid* when interpreted as a `Dst`.
 *
 * In both cases, any safety preconditions of the `Dst` type must also be upheld.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 *
 * @feature(transmutePrefix)
 * public fun doctestEntry() {
 *     check(transmutePrefix<IntArray, IntArray>(listOf(1, 2, 3, 4)) == listOf(1, 2))
 *     val expected = if (true) {
 *         0x34
 *     } else {
 *         0x12
 *     }
 *     check(transmutePrefix<UShort, UByte>(0x1234) == expected)
 *     // Would be UB because the destination is incompletely initialized.
 *     // transmutePrefix::<u8, u16>(123)
 *     // OK because the destination is allowed to be partially initialized.
 *     val unused: MaybeUninit<UShort> = transmutePrefix(123u.toUByte())
 * }
 * ```
 */
public fun <Src, Dst> transmutePrefix(src: Src, typeLayoutSrc: TypeLayout<Src> = UnresolvedTypeLayout(), typeLayoutDst: TypeLayout<Dst> = UnresolvedTypeLayout()): Dst {
    open class Transmute<A, B>(
        public var a: A? = null,
        public var b: B? = null,
    )
    return when (((typeLayoutSrc).sizeBytes.toInt()).cmp((typeLayoutDst).sizeBytes.toInt())) {
        // SAFETY: When Dst is bigger, the union is the size of Dst
        Ordering.Less -> run {
            val a = transmuteNeo(src, typeLayoutSrc = typeLayoutSrc)
            transmuteUnchecked(Transmute(a = a))
        }
        // SAFETY: When they're the same size, we can use the MIR primitive
        Ordering.Equal -> transmuteWithLayouts(src, typeLayoutSrc, typeLayoutDst)
        // SAFETY: When Src is bigger, the union is the size of Src
        Ordering.Greater -> run {
            val u: Transmute<Src, Dst> = transmuteWithLayouts(src, typeLayoutSrc, typeLayoutDst)
            transmuteNeo(u.b, typeLayoutSrc = UnresolvedTypeLayout<B>())
        }
    }
}

// even without panics, this helps for Miri backtraces

/**
 * New version of `reinterpret`, exposed under this name so it can be iterated upon
 * without risking breakage to uses of "real" reinterpret.
 *
 * Uses a `const`-`assert` to check the sizes instead of typeck hacks,
 * but is semantially identical to `reinterpret` otherwise.
 *
 * It will not be stabilized under this name.
 *
 * # Safety
 *
 * Refer to [transmute] for safety requirements.
 * This function is semantically identical to `reinterpret`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(transmuteNeo)
 * public fun doctestEntry() {
 *     check(transmuteNeo<Float, UInt>(0.0) == 0)
 * }
 * ```
 *
 * ```compile_fail,E0080
 * @file:feature(transmuteNeo)
 * use std.mem.transmuteNeo;
 *
 * unchecked { transmuteNeo::<u32, u16>(123) };
 * ```
 */
public fun <Src, Dst> transmuteNeo(src: Src, typeLayoutSrc: TypeLayout<Src> = UnresolvedTypeLayout()): Dst {
    check((typeLayoutSrc).sizeBytes.toInt() == (UnresolvedTypeLayout<Dst>()).sizeBytes.toInt())
    // SAFETY: the const-assert just checked that they're the same size,
    // and any other safety invariants need to be upheld by the caller.
    return transmuteWithLayouts(src, typeLayoutSrc, UnresolvedTypeLayout<Dst>())
}

// even without panics, this helps for Miri backtraces

/**
 * Returns a value uniquely identifying the enum variant in `v`.
 *
 * If `T` is not an enum, calling this function will not result in undefined behavior, but the
 * return value is unspecified.
 *
 * # Stability
 *
 * The discriminant of an enum variant may change if the enum definition changes. A discriminant
 * of some variant will not change between compilations with the same compiler. See the [Reference]
 * for more information.
 *
 * [Reference]: ../../reference/items/enumerations.html#custom-discriminant-values-for-fieldless-enumerations
 *
 * The value of a [Discriminant.T] is independent of any *free scopes* in `T`. As such,
 * reading or writing a `Discriminant<Foo<>>` as a `Discriminant<Foo<>>` (whether via
 * [transmute] or otherwise) is always sound. Note that this is **not** true for other kinds
 * of generic parameters and for higher-ranked scopes; `Discriminant<Foo<A>>` and
 * `Discriminant<Foo<B>>` as well as `Discriminant<Bar<dynamic for<> Trait<>>>` and
 * `Discriminant<Bar<dynamic Trait<>>>` may be incompatible.
 *
 * # Examples
 *
 * This can be used to compare enums that carry data, while disregarding
 * the actual data:
 *
 * ```kotlin
 * import std.mem
 *
 * sealed class Foo {
 *     public data class A(public var value: String) : Foo()
 *     public data class B(public var value: Int) : Foo()
 *     public data class C(public var value: Int) : Foo()
 * }
 * check(mem.discriminant(Foo.A("bar")) == mem.discriminant(Foo.A("baz")))
 * check(mem.discriminant(Foo.B(1)) == mem.discriminant(Foo.B(2)))
 * check(mem.discriminant(Foo.B(3)) != mem.discriminant(Foo.C(3)))
 * ```
 *
 * ## Accessing the numeric value of the discriminant
 *
 * Note that it is *undefined behavior* to [transmute] from [Discriminant] to a primitive!
 *
 * If an enum has only unit variants, then the numeric value of the discriminant can be accessed
 * with an [asTarget] cast:
 *
 * ```kotlin
 * sealed class Enum {
 *     public data object Foo : Enum()
 *     public data object Bar : Enum()
 *     public data object Baz : Enum()
 * }
 * check(0 == Enum.Foo.value.toInt())
 * check(1 == Enum.Bar.value.toInt())
 * check(2 == Enum.Baz.value.toInt())
 * ```
 *
 * If an enum has opted-in to having a [primitive representation] for its discriminant,
 * then it's possible to use pointers to read the memory location storing the discriminant.
 * That **cannot** be done for enums using the [default representation], however, as it's
 * undefined what layout the discriminant has and where it's stored — it might not even be
 * stored at all!
 *
 * [asTarget]: ../../std/keyword.as.html
 * [primitive representation]: ../../reference/type-layout.html#primitive-representations
 * [default representation]: ../../reference/type-layout.html#the-default-representation
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @repr(UByte)
 * sealed class Enum {
 *     public data object Unit : Enum()
 *     public data class Tuple(public var value: Boolean) : Enum()
 *     public data class Struct(public var a: Boolean) : Enum()
 *
 *     public open fun discriminant(): UByte {
 *         // SAFETY: Because `Self` is marked `repr(UByte)`, its layout is a `repr(C)` `union`
 *         // between `repr(C)` structs, each of which has the `UByte` discriminant as its first
 *         // field, so we can read the discriminant without offsetting the pointer.
 *         return ConstPtr.from(this).cast()
 *     }
 * }
 * val unitLike = Enum.Unit
 * val tupleLike = Enum.Tuple(true)
 * val structLike = Enum.Struct(a = false)
 * check(0 == unitLike.discriminant())
 * check(1 == tupleLike.discriminant())
 * check(2 == structLike.discriminant())
 * // ⚠️ This is undefined behavior. Don't do this. ⚠️
 * // assertEq(0, unchecked ( std.mem.reinterpret::<_, u8>(std.mem.discriminant(&unitLike)) ));
 * ```
 */
public fun <T> discriminant(v: T): Discriminant<T> = Discriminant(discriminantValue(v))

/**
 * Returns the number of variants in the enum type `T`.
 *
 * If `T` is not an enum, calling this function will not result in undefined behavior, but the
 * return value is unspecified. Equally, if `T` is an enum with more variants than `Int.MAX`
 * the return value is unspecified. Uninhabited variants will be counted.
 *
 * Note that an enum may be expanded with additional variants in the future
 * as a non-breaking change, for example if it is marked `@nonExhaustive`,
 * which will change the result of this function.
 *
 * # Examples
 *
 * ```kotlin
 * import ptr
 * import std.mem
 *
 * open class Void(public val handle: Long = -1L) {
 *     public val ptr: Long get() = handle
 * }
 * sealed class Foo {
 *     public data class A(public var value: String) : Foo()
 *     public data class B(public var value: Int) : Foo()
 *     public data class C(public var value: Int) : Foo()
 * }
 * check(mem.variantCount<Void>() == 0)
 * check(mem.variantCount<Foo>() == 3)
 * check(mem.variantCount<Nothing?>() == 2)
 * check(mem.variantCount<Result<Nothing>>() == 2)
 * ```
 */
public fun <T> variantCount(): Int = 1

// typeName() called only at run time

/**
 * Create a fresh instance of the inhabited ZST type `T`.
 *
 * Prefer this to [zeroed] or [uninitialized] or [transmuteCopy]
 * in places where you know that `T` is zero-sized, but don't have a bound
 * (such as [Default]) that would allow you to instantiate it using safe code.
 *
 * If you're not sure whether `T` is an inhabited ZST, then you should be
 * using [MaybeUninit], not this function.
 *
 * # Panics
 *
 * If `sizeOf<T>() != 0`.
 *
 * # Safety
 *
 * - `T` must be *[inhabited]*, i.e. possible to construct. This means that types
 *   like zero-variant enums and [!] are unsound to conjure.
 * - You must use the value only in ways which do not violate any *safety*
 *   invariants of the type.
 *
 * While it's easy to create a *valid* instance of an inhabited ZST, since having
 * no bits in its representation means there's only one possible value, that
 * doesn't mean that it's always *sound* to do so.
 *
 * For example, a library could design zero-sized tokens that are `!Default + !Clone`, limiting
 * their creation to functions that initialize some state or establish a scope. Conjuring such a
 * token could break invariants and lead to unsoundness.
 *
 * # Examples
 *
 * ```kotlin
 * check(conjureZst<Unit>() == Unit)
 * check(conjureZst<IntArray>() == listOf())
 * ```
 *
 * [inhabited]: https://doc.rust-lang.org/reference/glossary.html#inhabited
 */
public inline fun <reified T> conjureZst(): T {
    constAssert(((UnresolvedTypeLayout<T>()).sizeBytes == 0L), "mem::conjure_zst invoked on a non-zero-sized type", "mem::conjure_zst invoked on type {name}, which is not zero-sized", name, any, typeName, T)
    // SAFETY: because the caller must guarantee that it's inhabited and zero-sized,
    // there's nothing in the representation that needs to be set.
    // `assumeInit` calls `assertInhabited`, so we don't need to here.
    return MaybeUninit.uninit(UnresolvedTypeLayout<T>()).assumeInit()
}
