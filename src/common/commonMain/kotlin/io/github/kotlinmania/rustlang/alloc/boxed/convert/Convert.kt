// port-lint: source boxed/convert.rs
package io.github.kotlinmania.rustlang.alloc.boxed.convert

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.StaticAllocator
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Converts a `T` into a `Holder<T>`
 *
 * The conversion allocates on the heap and moves `t`
 * from the stack into it.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 5
 * val boxed = 5
 * check(Holder.from(x) == boxed)
 * ```
 */
public fun <T> fromBoxT(t: T): T = t

/**
 * Converts a `Holder<T>` into a `Pin<Holder<T>>`. If `T` does not implement [Unpin], then
 * `* boxed` will be pinned in memory and unable to be moved.
 *
 * This conversion does not allocate on the heap and happens in place.
 *
 * This is also available via [Box.intoPin].
 *
 * Constructing and pinning a `Holder` with <code><Pin<Holder\<T>>>::from([Box.new]\(x))</code>
 * can also be written more concisely using <code>[Box.pin]\(x)</code>.
 * This `From` implementation is useful if you already have a `Holder<T>`, or you are
 * constructing a (pinned) `Holder` in a different way than with [Box.new].
 */
public fun <T, A : Allocator> fromPinBoxTA(boxed: T): Pin<T> where A : StaticAllocator = Box.intoPin(boxed)

/**
 * Converts a `[T]` into a `Holder<[T]>`
 *
 * This conversion allocates on the heap
 * and performs a copy of `slice` and its contents.
 *
 * # Examples
 * ```kotlin
 * // create a &[u8] which will be used to create a Holder<[u8]>
 * val slice: List<UByte> = listOf(104u.toUByte(), 101u.toUByte(), 108u.toUByte(), 108u.toUByte(), 111u.toUByte())
 * val boxedSlice: List<UByte> = Holder.from(slice)
 * println("$boxedSlice")
 * ```
 */
public fun <T> fromBoxTSlice(slice: List<T>): List<T> = Box.cloneFromRef(slice)

/**
 * Converts a `[T]` into a `Holder<[T]>`
 *
 * This conversion allocates on the heap
 * and performs a copy of `slice` and its contents.
 *
 * # Examples
 * ```kotlin
 * // create a [u8] which will be used to create a Holder<[u8]>
 * var array = mutableListOf(104, 101, 108, 108, 111)
 * val slice: MutableList<UByte> = array
 * val boxedSlice: List<UByte> = Holder.from(slice)
 * println("$boxedSlice")
 * ```
 */
public fun <T> fromBoxTSlice(slice: MutableList<T>): List<T> = List.from(slice)

/**
 * Converts a `String` into a `Holder<String>`
 *
 * This conversion allocates on the heap
 * and performs a copy of `s`.
 *
 * # Examples
 *
 * ```kotlin
 * val boxed: String = Holder.from("hello")
 * println("$boxed")
 * ```
 */
public fun fromBoxString(s: String): String = Box.cloneFromRef(s)

/**
 * Converts a `Holder<String>` into a `Holder<[UByte]>`
 *
 * This conversion does not allocate on the heap and happens in place.
 *
 * # Examples
 * ```kotlin
 * // create a Holder<str> which will be used to create a Holder<[u8]>
 * val boxed: String = Holder.from("hello")
 * val boxedStr: List<UByte> = Holder.from(boxed)
 * // create a &[u8] which will be used to create a Holder<[u8]>
 * val slice: List<UByte> = listOf(104u.toUByte(), 101u.toUByte(), 108u.toUByte(), 108u.toUByte(), 111u.toUByte())
 * val boxedSlice = Holder.from(slice)
 * check(boxedSlice == boxedStr)
 * ```
 */
public fun <A : Allocator> fromBoxUByteSliceA(s: String): List<UByte> {
    val (raw, alloc) = Box.intoRawWithAllocator(s)
    // SAFETY: All `String`s are also valid if reinterpreted as `[UByte]`s.
    return Box.fromRawIn(mutPtrFromRustCast(raw), alloc)
}

/**
 * Converts a `[T; N]` into a `Holder<[T]>`
 *
 * This conversion moves the array to newly heap-allocated memory.
 *
 * # Examples
 *
 * ```kotlin
 * val boxed: List<UByte> = Holder.from(listOf(4, 2))
 * println("$boxed")
 * ```
 */
public fun <T> fromBoxTSlice(array: List<T>, n: Int): List<T> = array

/**
 * Casts a boxed slice to a boxed array.
 *
 * # Safety
 *
 * `boxedSlice.len()` must be exactly `N`.
 */
public fun <T, A : Allocator> boxedSliceAsArrayUnchecked(boxedSlice: List<T>, n: Int): List<T> {
    check(boxedSlice.size == n)
    // SAFETY: Caller ensures that the length is correct.
    return Box.castUnchecked(boxedSlice)
}

/**
 * Attempts to convert a `Holder<[T]>` into a `Holder<[T; N]>`.
 *
 * The conversion occurs in-place and does not require a
 * new memory allocation.
 *
 * # Errors
 *
 * Returns the old `Holder<[T]>` in the `Err` variant if
 * `boxedSlice.len()` does not equal `N`.
 */
public fun <T, A : Allocator> tryFromBoxTArraynA(boxedSlice: List<T>, n: Int): Result<List<T>, List<T>> =
    if (boxedSlice.size == n) {
        // SAFETY: Checked length.
        Result.Ok<List<T>, List<T>>(boxedSliceAsArrayUnchecked(boxedSlice, n))
    } else {
        Result.Err<List<T>, List<T>>(boxedSlice)
    }

public fun <T, A : Allocator, Dst> downcastAny(thisRef: T, typeIdTFn: (T) -> Any?): Result<Dst, T> =
    if (typeIdTFn(thisRef) == RustTypeId.of<Dst>()) {
        // SAFETY: The type is correct
        Result.Ok<Dst, T>(castUnchecked(thisRef))
    } else {
        Result.Err<Dst, T>(thisRef)
    }

/**
 * Casts a box by converting to a raw pointer and back, whatever that means
 * in the given context.
 */
public inline fun <T, A : Allocator, reified Dst> castUnchecked(thisRef: T): Dst {
    // SAFETY: Caller ensures the cast is correct
    return run {
        val (raw, alloc) = Box.intoRawWithAllocator(thisRef)
        Box.fromRawIn(raw.cast(), alloc)
    }
}

/**
 * Attempts to downcast the box to a concrete type.
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
public fun <A : Allocator, T> RustAny.downcast(): Result<T, RustAny> = RustAny.downcastAny(this)

/**
 * Downcasts the box to a concrete type.
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
public inline fun <A : Allocator, reified T> RustAny.downcastUnchecked(): T {
    check(this.isType<T>())
    // SAFETY: Caller ensures the type is correct.
    return RustAny.castUnchecked(this)
}

/**
 * Converts a type of [Error] into a box of dynamic [Error].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.error.Error
 * import std.fmt
 *
 * open class AnError {
 *     public open fun fmt(f: StringBuilder) {
 *         f.append("An error")
 *     }
 * }
 * val anError = AnError()
 * check(0 == sizeOfVal(anError))
 * val aBoxedError = Holder.from(anError)
 * check(sizeOf<Any>(typeLayoutT = UnresolvedTypeLayout<Any>()) == sizeOfVal(aBoxedError))
 * ```
 */
public fun <E> fromBoxAny(err: E): Any = err

/**
 * Converts a [String] into a box of dynamic [Error] + [Send] + [Sync].
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.error.Error
 *
 * val aStringError = "a string error".toString()
 * val aBoxedError = Holder.from(aStringError)
 * check(sizeOf<Any>(typeLayoutT = UnresolvedTypeLayout<Any>()) == sizeOfVal(aBoxedError))
 * ```
 */
public fun fromBoxAny(err: String): Any {
    open class StringError(
        public var value: String,
    ) {
        public var field0: String
            get() = value
            set(v) {
                value = v
            }

        public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.value).toString()); Result.Ok(Unit) }

        // Purposefully skip printing "StringError(..)"
        public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this.value).toString()); Result.Ok(Unit) }
    }
    return StringError(err)
}

public fun <T, A : Allocator, Dst> downcastError(thisRef: T): Result<Dst, T> =
    if (errorTypeId<T>(thisRef) == RustTypeId.of<Dst>()) {
        // SAFETY: The type is correct
        Result.Ok<Dst, T>(castUnchecked(thisRef))
    } else {
        Result.Err<Dst, T>(thisRef)
    }

/**
 * Attempts to downcast the box to a concrete type.
 */
public fun <T> Any.downcast(): Result<T, Any> = Box.downcastError(this)
