// port-lint: source any.rs
package io.github.kotlinmania.rustlang.core.any

import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeId
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdEq
import io.github.kotlinmania.rustlang.core.intrinsics.reflection.typeIdVtable
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.mem.typeinfo.TraitImpl
import io.github.kotlinmania.rustlang.core.mem.typeinfo.TypeKind
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.metadata.DynMetadata
import io.github.kotlinmania.rustlang.core.ptr.metadata.Pointee
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr

// Utilities for dynamic typing or type reflection.
//
// # `Any` and `TypeId`
//
// `Any` itself can be used to get a `TypeId`, and has more features when used
// as a trait object. As `dynamic Any` (a borrowed trait object), it has the `is`
// and `downcastRef` methods, to test if the contained value is of a given type,
// and to get a reference to the inner value as a type. As `dynamic Any`, there
// is also the `downcastMut` method, for getting a mutable reference to the
// inner value. `Holder<dynamic Any>` adds the `downcast` method, which attempts to
// convert to a `Holder<T>`. See the [Box] documentation for the full details.
//
// Note that `dynamic Any` is limited to testing whether a value is of a specified
// concrete type, and cannot be used to test whether a type implements a trait.
//
// [Box]: ../../std/boxed/struct.Holder.html
//
// # Smart pointers and `dynamic Any`
//
// One piece of behavior to keep in mind when using `Any` as a trait object,
// especially with types like `Holder<dynamic Any>` or `Arc<dynamic Any>`, is that simply
// calling `.typeId()` on the value will produce the `TypeId` of the
// *container*, not the underlying trait object. This can be avoided by
// converting the smart pointer into a `dynamic Any` instead, which will return
// the object's `TypeId`. For example:
//
// ```kotlin
// val boxed: RustAny = RustAny.from(3)
// // You're more likely to want this:
// val actualId = (boxed).typeId
// // ... than this:
// val boxedId = boxed.typeId
// check(actualId == RustTypeId.of<Int>())
// check(boxedId == RustTypeId.of<RustAny>())
// ```
//
// ## Examples
//
// Consider a situation where we want to log a value passed to a function.
// We know the value we're working on implements `Debug`, but we don't know its
// concrete type. We want to give special treatment to certain types: in this
// case printing out the length of `String` values prior to their value.
// We don't know the concrete type of our value at compile time, so we need to
// use runtime reflection instead.
//
// ```kotlin
// // Logger function for any type that implements `Debug`.
// fun <T> log(value: T) {
//     val valueAny = RustAny.from(value)
//     // Try to convert our value to a `String`. If successful, we want to
//     // output the `String`s length as well as its value. If not, it's a
//     // different type: just print it out unadorned.
//     when (val matchVal = valueAny.downcastRef<String>()) {
//         null -> {
//             println("$value")
//         }
//         else -> {
//             val asString = matchVal
//             println("String (${asString.len()}): $asString")
//         }
//     }
// }
// // This function wants to log its parameter out prior to doing work with it.
// fun <T> doWork(value: T) {
//     log(value)
//     // ...do some other work
// }
// fun main() {
//     val myString = "Hello World".toString()
//     doWork(myString)
//     val myI8: Byte = 100
//     doWork(myI8)
// }
// ```

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

// //////////////////////////////////////////////////////////////////////////
// Any trait
// //////////////////////////////////////////////////////////////////////////
// This trait is not unchecked, though we rely on the specifics of it's sole impl's
// `typeId` function in unchecked code (e.g., `downcast`). Normally, that would be
// a problem, but because the only impl of `Any` is a blanket implementation, no
// other code can implement `Any`.
//
// We could plausibly make this trait unchecked -- it would not cause breakage,
// since we control all the implementations -- but we choose not to as that's
// both not really necessary and may confuse users about the distinction of
// unchecked traits and unchecked methods (i.e., `typeId` would still be safe to call,
// but we would likely want to indicate as such in documentation).

/**
 * A trait to emulate dynamic typing.
 *
 * Most types implement `Any`. However, any type which contains a non-`static` reference does not.
 * See the [module-level documentation][mod] for more details.
 */
public interface Any {
    /**
     * Gets the `TypeId` of `self`.
     *
     * If called on a `dynamic Any` trait object
     * (or a trait object of a subtrait of `Any`),
     * this returns the `TypeId` of the underlying
     * concrete type, not that of `dynamic Any` itself.
     *
     * # Examples
     *
     * ```kotlin
     * fun isString(s: RustAny): Boolean {
     *     return RustTypeId.of<String>() == s.typeId
     * }
     * check(isString(0) == false)
     * check(isString("cookie monster".toString()) == true)
     * ```
     */
    public fun typeId(): TypeId
}

public fun <T> T.typeId(): TypeId = RustTypeId.of<T>()

// //////////////////////////////////////////////////////////////////////////
// Extension methods for Any trait objects.
// //////////////////////////////////////////////////////////////////////////
public fun Any.fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "Any").finishNonExhaustive()

// Ensure that the result of e.g., joining a thread can be printed and
// hence used with `unwrap`. May eventually no longer be needed if
// dispatch works with upcasting.

/**
 * Returns `true` if the inner type is the same as `T`.
 *
 * # Examples
 *
 * ```kotlin
 * fun isString(s: RustAny) {
 *     if (s.isType<String>()) {
 *         println("It's a string!")
 *     } else {
 *         println("Not a string...")
 *     }
 * }
 * isString(0)
 * isString("cookie monster".toString())
 * ```
 */
public fun <T> Any.isMatch(): Boolean {
    // Get `TypeId` of the type this function is instantiated with.
    val t = RustTypeId.of<T>()
    // Get `TypeId` of the type in the trait object (`self`).
    val concrete = this.typeId()
    // Compare both `TypeId`s on equality.
    return t == concrete
}

/**
 * Returns some reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 *
 * # Examples
 *
 * ```kotlin
 * fun printIfString(s: RustAny) {
 *     when (val matchVal = s.downcastRef<String>()) {
 *         null -> {
 *             println("Not a string...")
 *         }
 *         else -> {
 *             val string = matchVal
 *             println("It's a string(${string.len()}): '$string'")
 *         }
 *     }
 * }
 * printIfString(0)
 * printIfString("cookie monster".toString())
 * ```
 */
public inline fun <reified T> Any.downcastRef(): T? =
    if (this.isMatch<T>()) {
        // SAFETY: just checked whether we are pointing to the correct type, and we can rely on
        // that check for memory safety because we have implemented Any for all types; no other
        // impls can exist as they would conflict with our impl.
        this.downcastUncheckedRef()
    } else {
        null
    }

/**
 * Returns some mutable reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 *
 * fun modifyIfU32(s: RustAny) {
 *     when (val matchVal = s.downcastMut<UInt>()) {
 *         null -> {}
 *         else -> {
 *             val num = matchVal
 *             num = 42
 *         }
 *     }
 * }
 * val localStorageL11C0 = AddressableLocal(10u, PrimitiveLayouts.U32)
 * var x by localStorageL11C0
 * var s = "starlord".toString()
 * modifyIfU32(RustMutableReference(localStorageL11C0.asMutPtr(), localStorageL11C0.layout))
 * modifyIfU32(s)
 * check(x == 42u)
 * check(s == "starlord")
 * ```
 */
public inline fun <reified T> Any.downcastMut(): T? =
    if (this.isMatch<T>()) {
        // SAFETY: just checked whether we are pointing to the correct type, and we can rely on
        // that check for memory safety because we have implemented Any for all types; no other
        // impls can exist as they would conflict with our impl.
        this.downcastUncheckedMut()
    } else {
        null
    }

/**
 * Returns a reference to the inner value as type `dynamic T`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(downcastUnchecked)
 * public fun doctestEntry() {
 *     val x: RustAny = RustAny.from(1)
 *     run {
 *         check(x.downcastUncheckedRef<Int>() == 1)
 *     }
 * }
 * ```
 *
 * # Safety
 *
 * The contained value must be of type `T`. Calling this method
 * with the incorrect type is *undefined behavior*.
 */
public fun <T> Any.downcastUncheckedRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    if (false) {
        check(this.isMatch<T>())
    }
    // SAFETY: caller guarantees that T is the correct type
    return (typeLayoutT).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
}

/**
 * Returns a mutable reference to the inner value as type `dynamic T`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(downcastUnchecked)
 * public fun doctestEntry() {
 *     var x: RustAny = RustAny.from(1)
 *     run {
 *         x.downcastUncheckedMut<Int>() += 1
 *     }
 *     check(x.downcastRef<Int>().unwrap() == 2)
 * }
 * ```
 *
 * # Safety
 *
 * The contained value must be of type `T`. Calling this method
 * with the incorrect type is *undefined behavior*.
 */
public fun <T> Any.downcastUncheckedMut(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    if (false) {
        check(this.isMatch<T>())
    }
    // SAFETY: caller guarantees that T is the correct type
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), typeLayoutT)
}

/**
 * Forwards to the method defined on the type `dynamic Any`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(downcastUnchecked)
 * public fun doctestEntry() {
 *     val x: RustAny = RustAny.from(1)
 *     run {
 *         check(x.downcastUncheckedRef<Int>() == 1)
 *     }
 * }
 * ```
 *
 * # Safety
 *
 * The contained value must be of type `T`. Calling this method
 * with the incorrect type is *undefined behavior*.
 */
public inline fun <reified T> Any.downcastUncheckedRef(): T {
    // SAFETY: guaranteed by caller
    return Any.downcastUncheckedRef<T>(this)
}

/**
 * Forwards to the method defined on the type `dynamic Any`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(downcastUnchecked)
 * public fun doctestEntry() {
 *     var x: RustAny = RustAny.from(1)
 *     run {
 *         x.downcastUncheckedMut<Int>() += 1
 *     }
 *     check(x.downcastRef<Int>().unwrap() == 2)
 * }
 * ```
 *
 * # Safety
 *
 * The contained value must be of type `T`. Calling this method
 * with the incorrect type is *undefined behavior*.
 */
public inline fun <reified T> Any.downcastUncheckedMut(): T {
    // SAFETY: guaranteed by caller
    return Any.downcastUncheckedMut<T>(this)
}

// //////////////////////////////////////////////////////////////////////////
// TypeID and its methods
// //////////////////////////////////////////////////////////////////////////

/**
 * A `TypeId` represents a globally unique identifier for a type.
 *
 * Each `TypeId` is an opaque object which does not allow inspection of what's
 * inside but does allow basic operations such as cloning, comparison,
 * printing, and showing.
 *
 * A `TypeId` is currently only available for types which ascribe to `static`,
 * but this limitation may be removed in the future.
 *
 * While `TypeId` implements `Hash`, `PartialOrd`, and `Ord`, it is worth
 * noting that the hashes and ordering will vary between Rust releases. Beware
 * of relying on them inside of your code!
 *
 * # Layout
 *
 * Like other [`Rust`-representation][repr-rust] types, `TypeId`s size and layout are unstable.
 * In particular, this means that you cannot rely on the size and layout of `TypeId` remaining the
 * same between Rust releases; they are subject to change without prior notice between Rust
 * releases.
 *
 * [repr-rust]: https://doc.rust-lang.org/reference/type-layout.html#r-layout.repr.rust.unspecified
 *
 * # Danger of Improper Variance
 *
 * You might think that subtyping is impossible between two static types,
 * but this is false; there exists a static type with a static subtype.
 * To wit, `fun(String)`, which is short for `for<> fun(String)`, and
 * `fun(String)`, are two distinct, static types, and yet,
 * `fun(String)` is a subtype of `fun(String)`, since any value of type
 * `fun(String)` can be used where a value of type `fun(String)` is needed.
 *
 * This means that abstractions around `TypeId`, despite its
 * `static` bound on arguments, still need to worry about unnecessary
 * and improper variance: it is advisable to strive for invariance
 * first. The usability impact will be negligible, while the reduction
 * in the risk of unsoundness will be most welcome.
 *
 * ## Examples
 *
 * Suppose `SubType` is a subtype of `SuperType`, that is,
 * a value of type `SubType` can be used wherever
 * a value of type `SuperType` is expected.
 * Suppose also that `CoVar<T>` is a generic type, which is covariant over `T`
 * (like many other types, including `PhantomData<T>` and `List<T>`).
 *
 * Then, by covariance, `CoVar<SubType>` is a subtype of `CoVar<SuperType>`,
 * that is, a value of type `CoVar<SubType>` can be used wherever
 * a value of type `CoVar<SuperType>` is expected.
 *
 * Then if `CoVar<SuperType>` relies on `TypeId.of<SuperType>()` to uphold any invariants,
 * those invariants may be broken because a value of type `CoVar<SuperType>` can be created
 * without going through any of its methods, like so:
 * ```kotlin
 * public fun interface FnCallback {
 *     public operator fun invoke(arg0: Unit)
 * }
 *
 * public typealias SubType = FnCallback
 *
 * public typealias SuperType = FnCallback
 *
 * public typealias CoVar<T> = List<T>
 *
 * public fun doctestEntry() {
 *     // imagine something more complicated
 *     val sub: CoVar<SubType> = CoVar.new()
 *     // we have a `CoVar<SuperType>` instance without
 *     // *ever* having called `CoVar<SuperType>.new()`!
 *     val fakeSuper: CoVar<SuperType> = sub
 * }
 * ```
 *
 * The following is an example program that tries to use `TypeId.of` to
 * implement a generic type `Unique<T>` that guarantees unique instances for each `Unique<T>`,
 * that is, for each type `T` there can be at most one value of type `Unique<T>` at any time.
 *
 * ```kotlin
 * import std.sync.Mutex
 * import unique.Unique
 *
 * public fun interface FnCallback {
 *     public operator fun invoke(arg0: Unit)
 * }
 *
 * public typealias TheOneRing = FnCallback
 *
 * public typealias OtherRing = FnCallback
 *
 * public fun doctestEntry() {
 *     var ID_SET: Mutex<MutableSet<RustTypeId>> = Mutex.new(mutableSetOf())
 *
 *     // TypeId has only covariant uses, which makes Unique covariant over TypeAsId 🚨
 *     open class Unique(public var value: Unit) {
 *         public var field0: Unit
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun new(): Unique? {
 *             var set = ID_SET.lock().unwrap()
 *             return (set.insert(RustTypeId.of<TypeAsId>())).then({ Unique(Unit) })
 *         }
 *
 *         public open fun drop() {
 *             var set = ID_SET.lock().unwrap()
 *             (!set.remove(RustTypeId.of<TypeAsId>())).then({ error("duplicity detected") })
 *         }
 *     }
 *     // `OtherRing` is a subtype of `TheOneRing`. Both are static, and thus have a TypeId.
 *     fun main() {
 *         val theOneRing: Unique = Unique.new().unwrap()
 *         check(Unique.new() == null)
 *         val otherRing: Unique = Unique.new().unwrap()
 *         // Use that `Unique<OtherRing>` is a subtype of `Unique<TheOneRing>` 🚨
 *         val fakeOneRing: Unique = otherRing
 *         check(fakeOneRing == theOneRing)
 *         forget(fakeOneRing)
 *     }
 * }
 * ```
 */
public open class TypeId internal constructor(
    dataCtorArg: List<ConstPtr>,
) {
    /**
     * This needs to be an array of pointers, since there is provenance
     * in the first array field. This provenance knows exactly which type
     * the TypeId actually is, allowing CTFE and miri to operate based off it.
     * At runtime all the pointers in the array contain bits of the hash, making
     * the entire `TypeId` actually just be a `ULong` hash of the type.
     */
    public var data: List<ConstPtr> = dataCtorArg
        internal set

    public open fun clone(): TypeId = TypeId(data)

    // SAFETY: the raw pointer is always an integer

    // SAFETY: the raw pointer is always an integer

    public open fun eq(other: TypeId): Boolean = typeIdEq(this, other)

    /**
     * Checks if the [TypeId] implements the trait. If it does it returns [TraitImpl] which can be used to build a fat pointer.
     * It can only be called at compile time. `self` must be the [TypeId] of a sized type or None will be returned.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     interface Blah
     *     check(RustTypeId.of<UByte>().traitInfoOf<Blah>() != null)
     *     check(RustTypeId.of<UShort>().traitInfoOf<Blah>() == null)
     * }
     * ```
     */
    public open fun <T : TryAsDynCompatible> traitInfoOf(): TraitImpl<T>? {
        // SAFETY: The vtable was obtained for `T`, so it is guaranteed to be `DynMetadata<T>`.
        // The intrinsic can't infer this because it is designed to work with arbitrary TypeIds.
        return transmuteWithLayouts<TraitImpl<ConstPtr>?, TraitImpl<T>?>(this.traitInfoOfTraitTypeId(TypeId<T>()), UnresolvedTypeLayout<TraitImpl<ConstPtr>?>(), UnresolvedTypeLayout<TraitImpl<T>?>())
    }

    /**
     * Checks if the [TypeId] implements the trait of `traitRepresentedByTypeId`. If it does it returns [TraitImpl] which can be used to build a fat pointer.
     * It can only be called at compile time. `self` must be the [TypeId] of a sized type or None will be returned.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(typeInfo)
     * public fun doctestEntry() {
     *     interface Blah
     *     check(RustTypeId.of<UByte>().traitInfoOfTraitTypeId(RustTypeId.of<Blah>()) != null)
     *     check(RustTypeId.of<UShort>().traitInfoOfTraitTypeId(RustTypeId.of<Blah>()) == null)
     * }
     * ```
     */
    public open fun traitInfoOfTraitTypeId(traitRepresentedByTypeId: TypeId): TraitImpl<ConstPtr>? {
        if (this.size() == null) {
            return null
        }
        return if (when (val matchVal = traitRepresentedByTypeId.info().kind) {
            is TypeKind.DynTrait -> true
            else -> false
        } && typeIdVtable(this, traitRepresentedByTypeId) != null) {
            TraitImpl(vtable = vtable)
        } else {
            null
        }
    }

    public open fun asU128(): U128 {
        val bytes = MutableList(16) { 0 }
        // This is a provenance-stripping memcpy.
        for ((i, chunk) in this.data.iter().copied().mapIndexed { index, item -> Pair(index, item) }) {
            val chunk1 = chunk.addr().toNeBytes()
            val start = i * chunk1.size
            run { val dstTmp0 = bytes; val srcTmp0 = chunk1; for (idxTmp0 in srcTmp0.indices) dstTmp0[(start) + idxTmp0] = srcTmp0[idxTmp0] }
        }
        return U128.fromLeBytes(bytes)
    }

    public override fun hashCode(): Int {
        var state = 0
        // We only hash the lower 64 bits of our (128 bit) internal numeric ID,
        // because:
        // - The hashing algorithm which backs `TypeId` is expected to be
        // unbiased and high quality, meaning further mixing would be somewhat
        // redundant compared to choosing (the lower) 64 bits arbitrarily.
        // - `Hasher.finish` returns a u64 anyway, so the extra entropy we'd
        // get from hashing the full value would probably not be useful
        // (especially given the previous point about the lower 64 bits being
        // high quality on their own).
        // - It is correct to do so -- only hashing a subset of `self` is still
        // compatible with an `Eq` implementation that considers the entire
        // value, as ours does.
        val data = readUnaligned(ConstPtr((ConstPtr((this.data.asPtr()).rawAddress)).rawAddress + ((1).toLong() * (PrimitiveLayouts.U64).strideBytes)), typeLayoutT = PrimitiveLayouts.U64)
        state = 31 * state + (data).hashCode()
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, fmt.Error> = f.writeFmt("TypeId(${this.asU128()})")

    public companion object {
        public fun typeLayout(): TypeLayout<TypeId> =
            object : TypeLayout<TypeId> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes(((16 / sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toLong() * (PrimitiveLayouts.constPtrLayout(64)).strideBytes), 8L)
                override fun readNative(addr: Long): TypeId =
                    TypeId(
                        (fixedListTypeLayout(PrimitiveLayouts.constPtrLayout(64), (16 / sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: TypeId) {
                    (fixedListTypeLayout(PrimitiveLayouts.constPtrLayout(64), (16 / sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt())).writeNative(addr, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TypeId =
                    TypeId(
                        (fixedListTypeLayout(PrimitiveLayouts.constPtrLayout(64), (16 / sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TypeId) {
                    (fixedListTypeLayout(PrimitiveLayouts.constPtrLayout(64), (16 / sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))).toInt())).writeBytes(buf, offset, value.data)
                }
            }

        public operator fun invoke(
            data: List<ConstPtr>,
        ): TypeId = TypeId(
            dataCtorArg = data,
        )

        /**
         * Returns the `TypeId` of the generic type parameter.
         *
         * # Examples
         *
         * ```kotlin
         * fun <T> isString(s: T): Boolean {
         *     return RustTypeId.of<String>() == RustTypeId.of<T>()
         * }
         * check(isString(0) == false)
         * check(isString("cookie monster".toString()) == true)
         * ```
         */
        public fun <T> of(): TypeId = typeId<T>()
    }
}

/**
 * Returns the name of a type as a string slice.
 *
 * # Note
 *
 * This is intended for diagnostic use. The exact contents and format of the
 * string returned are not specified, other than being a best-effort
 * description of the type. For example, amongst the strings
 * that `typeName<Option<String>>()` might return are `"Option<String>"` and
 * `"std.option.Option<std.string.String>"`.
 *
 * The returned string must not be considered to be a unique identifier of a
 * type as multiple types may map to the same type name. Similarly, there is no
 * guarantee that all parts of a type will appear in the returned string. In
 * addition, the output may change between versions of the compiler. For
 * example, scope specifiers were omitted in some earlier versions.
 *
 * The current implementation uses the same infrastructure as compiler
 * diagnostics and debuginfo, but this is not guaranteed.
 *
 * # Examples
 *
 * ```kotlin
 * check("Any" == "core.option.Option<alloc.string.String>")
 * ```
 */
public fun <T> typeName(): String = "T"

/**
 * Returns the type name of the pointed-to value as a string slice.
 *
 * This is the same as `typeName<T>()`, but can be used where the type of a
 * variable is not easily available.
 *
 * # Note
 *
 * Like [typeName], this is intended for diagnostic use and the exact output is not
 * guaranteed. It provides a best-effort description, but the output may change between
 * versions of the compiler.
 *
 * In short: use this for debugging, avoid using the output to affect program behavior. More
 * information is available at [typeName].
 *
 * Additionally, this function does not resolve trait objects. This means that
 * `typeNameOfVal(7u32 as & dynamic Debug)` may return `"dynamic Debug"`, but will not return `"u32"`
 * at this time.
 *
 * # Examples
 *
 * Prints the default integer and float types.
 *
 * ```kotlin
 * import std.any.typeNameOfVal
 *
 * val s = "foo"
 * val x: Int = 1
 * val y: Float = 1.0f
 * check(typeNameOfVal(s).contains("str"))
 * check(typeNameOfVal(x).contains("i32"))
 * check(typeNameOfVal(y).contains("f32"))
 * ```
 */
public fun <T> typeNameOfVal(value: T): String = "Any"

/**
 * Trait that is automatically implemented for all `dynamic Trait<C> +` without assoc type bounds.
 * The scope parameter should be the same that is used to constrain generic type parameters
 * that are turned into the dynamic trait constrained by `TryAsDynCompatible`.
 *
 * This is required for `tryAsDyn` to be able to soundly convert non-static
 * types to `dynamic Trait`.
 *
 * Note: these requirements are sufficient for soundness, but it is unclear
 * if they are all necessary. We may be able to lift some requirements in favor
 * of more precise ones.
 */
public interface TryAsDynCompatible : Pointee<DynMetadata<TryAsDynCompatible>>

/**
 * Returns `Some(U)` if `T` can be coerced to the dynamic trait type `U`. Otherwise, it returns `None`.
 *
 * <div class="warning">
 *
 * This function is implemented on a best-effort basis. It is not always possible to determine
 * whether a generic type implements a trait; thus, this function may produce false negatives,
 * returning `None` even when `T` implements the requested trait.
 *
 * `tryAsDyn` is guaranteed to return `None` if `T` does *not* implement the requested trait, but
 * it is never guaranteed to return `Some`. It is intended  to be used for performance
 * optimizations and debugging, and `tryAsDyn` succeeding for a particular type should never be
 * relied upon for correctness (i.e. callers must behave correctly even if `tryAsDyn` spuriously
 * returns `None`).
 *
 * </div>
 *
 * # Examples of false negatives
 *
 * Some examples of situations where `tryAsDyn<T, dynamic Trait>` returns `None` in practice even
 * when `T` implements `Trait`:
 * * `T`s impl for `Trait` is scope-dependent
 * * `T`s impl for `Trait` is a builtin impl (e.g. `dynamic Debug` implements `Debug`)
 * * `T`s impl for `Trait` has a trait bound which requires transitively reasoning about
 * scope-dependent or builtin impls
 *
 * This list is not exhaustive. There is some detailed documentation about these limitations at
 * <https://doc.rust-lang.org/unstable-book/library-features/try-as-dynamic.html> But the gist is
 * summarized below:
 *
 * ## Lifetime-dependent impls
 *
 * `tryAsDyn` does not have access to scope information, thus it cannot differentiate between
 * `static` and other scopes and cannot reason about outlives bounds on impls. Thus it cannot
 * reason about impls that have `static` scopes or outlives bounds of any kind.
 *
 * The following impls are scope-dependent and produce false negatives when used with
 * `tryAsDyn`:
 *
 * ```kotlin
 * // impl mentions a static scope
 * ```
 *
 * ```kotlin
 * // impl contains an outlives bound
 * ```
 *
 * Impls that mention a generic parameter more than once are scope-dependent and produce false
 * negatives, even if they don't expressly mention any scopes:
 *
 * ```kotlin
 * // impl mentions T more than once, creating an implied scope dependence
 * ```
 *
 * The following impl is scope-**independent**, because even though it *mentions* scopes,
 * implementation of the trait is not *conditional* over the scopes:
 * ```kotlin
 * ```
 *
 * Impls without generic parameters at all are also scope-independent, as long as they contain
 * no `static` scopes.
 *
 * ## Builtin impls
 *
 * Builtin impls (like `implementation Debug for dynamic Debug`, or automatic implementations of `Send` and
 * `Sync`) have various obscure rules and often are not fully generic. To simplify reasoning about
 * what is allowed and what not, all builtin impls are rejected and will neither directly nor
 * indirectly contribute to a `Some` result.
 *
 * # Compile-time failures
 * Determining whether `T` can be coerced to the dynamic trait type `U` requires compiler trait resolution.
 * In some cases, that resolution can exceed the recursion limit,
 * and compilation will fail instead of this function returning `None`.
 *
 * The input type `T` must outlive the scope `a` on the `dynamic Trait +`.
 * This is basically the same rule that forbids `val x: dynamic Trait + = && someLocalVariable;`
 * So if you see borrow check errors around `tryAsDyn`, think about whether a normal unsizing
 * coercion would be possible at all if you were using concrete types or had bounds on the input type.
 *
 * # Examples
 *
 * Using `tryAsDyn` to use bytewise comparison instead of PartialEq for certain types, similar to
 * the standard library's optimization for slices:
 *
 * ```kotlin
 * import core.any.tryAsDyn
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(tryAsDyn)
 * public fun doctestEntry(): Result<Unit> {
 *     / **
 *      * Marker trait for types that can be compared for equality
 *      * using a bytewise comparison (i.e. memcmp).
 *      *
 *      * Implementations must ensure the type contains no uninitialized bytes,
 *      * and that a bytewise comparison will produce the same result as PartialEq.
 *      * /
 *     interface BytewiseEq
 *     / **
 *      * Compares two objects for equality,
 *      * /
 *     fun <T> eq(x: T, y: T): Boolean {
 *         return if (tryAsDyn<T, BytewiseEq>(x) != null) {
 *             // T implements BytewiseEq, so we cast the slices to u8 and compare their bytes
 *             // instead of calling PartialEq on each individual element.
 *             run {
 *                 // SAFETY: x and y are valid for reads of sizeOf::<T>() bytes
 *                 // BytewiseEq trait guarantees we can interperet these bytes as u8's
 *                 // and compare them for equality
 *                 val x1 = sliceFromRawParts(ConstPtr(((x)).rawAddress), sizeOfVal(x, typeLayoutT = UnresolvedTypeLayout<T>()))
 *                 val y1 = sliceFromRawParts(ConstPtr(((y)).rawAddress), sizeOfVal(y, typeLayoutT = UnresolvedTypeLayout<T>()))
 *                 x1 == y1
 *             }
 *         } else {
 *             // T does not implement BytewiseEq, or tryAsDyn returned a false negative.
 *             // Fallback to PartialEq.
 *             //
 *             // BytewiseEq guarantees bytewise comparison and PartialEq will produce the same
 *             // results, so our code behaves correctly if tryAsDyn produces false negatives.
 *             x == y
 *         }
 *     }
 *     // u16 implements BytewiseEq, so eq::<u16> will use bytewise comparison
 *     // (unless tryAsDyn returns a false negative)
 *     check(eq(5u.toUShort(), 5u.toUShort()))
 *     // f32 does not implement BytewiseEq, so eq::<f32> will use element-wise comparison
 *     check(eq(5.0f, 5.0f))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Using `tryAsDyn` for debugging:
 *
 * ```kotlin
 * import core.any.tryAsDyn
 * import core.any.typeName
 *
 * @feature(tryAsDyn)
 * public fun doctestEntry(): Result<Unit> {
 *     / **
 *      * Prints a value of type T, attempting to use its Debug implementation with tryAsDyn.
 *      * /
 *     fun <T> debugPrintln(x: T) {
 *         when (val matchVal = tryAsDyn<T, Any>(x)) {
 *             null -> {
 *                 // T does not implement Debug, or tryAsDyn returned a false negative.
 *                 // Print the name of the type instead.
 *                 //
 *                 // We're not relying on this for correctness; it's just for debugging,
 *                 // so we can tolerate false negatives.
 *                 println("<" + ("Any").toString() + ">")
 *             }
 *             else -> {
 *                 val debug = matchVal
 *                 println("$debug")
 *             }
 *         }
 *     }
 *     / **
 *      * This type does not implement Debug.
 *      * /
 *     open class NoDebug
 *     // Prints "Hello, world!" unless tryAsDyn returns a false negative.
 *     debugPrintln("Hello, world!")
 *     // Prints the name of the type, since it does not have a Debug implementation.
 *     debugPrintln(NoDebug())
 *     // The current implementation of tryAsDyn gives a false positive in this case!
 *     debugPrintln((("Hello, world!" as Any?) as Any))
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun <T, U : TryAsDynCompatible> tryAsDyn(t: T): U? {
    // For unsized `T`, `traitInfoOf` always returns `None` (vtable lookup is
    // only supported for sized types). The function therefore unconditionally
    // returns `None` in that case.
    val vtable: DynMetadata<U>? = TypeId<T>().traitInfoOf<U>().asRef().map(TraitImpl::getVtable)
    return when (vtable) {
        null -> null
        else -> {
            val dynMetadata = vtable
            val pointer = linearSliceView((constPtrFromRustCast(t)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, dynMetadata.toInt(), PrimitiveLayouts.UNIT)
            // SAFETY: `t` is a reference to a type, so we know it is valid.
            // `dynMetadata` is a vtable for T, implementing the trait of `U`.
            // `T` is sized here because `traitInfoOf` only returns `Some` for sized types,
            // so the thin data pointer fully describes the value.
            (UnresolvedTypeLayout<T>()).readNative((pointer).rawAddress)
        }
    }
}

/**
 * Returns `Some(U)` if `T` can be coerced to the dynamic trait type `U`. Otherwise, it returns `None`.
 *
 * See documentation of [tryAsDyn] for details about the behaviour and limitations.
 */
public fun <T, U : TryAsDynCompatible> tryAsDynMut(t: T): U? {
    // For unsized `T`, `traitInfoOf` always returns `None` (vtable lookup is
    // only supported for sized types). The function therefore unconditionally
    // returns `None` in that case.
    val vtable: DynMetadata<U>? = TypeId<T>().traitInfoOf<U>().asRef().map(TraitImpl::getVtable)
    return when (vtable) {
        null -> null
        else -> {
            val dynMetadata = vtable
            val pointer = linearMutSliceView((mutPtrFromRustCast(t)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, dynMetadata.toInt(), PrimitiveLayouts.UNIT)
            // SAFETY: `t` is a reference to a type, so we know it is valid.
            // `dynMetadata` is a vtable for T, implementing the trait of `U`.
            // `T` is sized here because `traitInfoOf` only returns `Some` for sized types,
            // so the thin data pointer fully describes the value.
            RustMutableReference(MutPtr((pointer).rawAddress), UnresolvedTypeLayout<T>())
        }
    }
}
