// port-lint: source string.rs
package io.github.kotlinmania.rustlang.alloc.string

import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.collections.binaryheap.Iter
import io.github.kotlinmania.rustlang.alloc.fmt.format
import io.github.kotlinmania.rustlang.alloc.str.fromBoxedUtf8Unchecked
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.sveretain.PanicGuard
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.fmt.Arguments
import io.github.kotlinmania.rustlang.core.hint.unlikely
import io.github.kotlinmania.rustlang.core.iter.sources.fromfn.fromFn
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.saturating.Saturating
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.AddAssign
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeBounds
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.castUninit
import io.github.kotlinmania.rustlang.core.ptr.mutptr.offset
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8UncheckedMut
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.iter.CharIndices
import io.github.kotlinmania.rustlang.core.str.iter.Chars
import io.github.kotlinmania.rustlang.core.str.pattern.Pattern
import io.github.kotlinmania.rustlang.core.str.pattern.Searcher
import io.github.kotlinmania.rustlang.core.str.pattern.Utf8Pattern
import io.github.kotlinmania.rustlang.core.str.traits.FromStr

// A UTF-8–encoded, growable string.
//
// This module contains the [String] type, the [ToString] trait for
// converting to strings, and several error types that may result from
// working with [String]s.
//
// # Examples
//
// There are multiple ways to create a new [String] from a string literal:
//
// ```kotlin
// val s = "Hello".toString()
// val s1 = "world"
// val s2: String = "also this".into()
// ```
//
// You can create a new [String] from an existing one by concatenating with
// `+`:
//
// ```kotlin
// val s = "Hello".toString()
// val message = s + " world!"
// ```
//
// If you have a vector of valid UTF-8 bytes, you can make a [String] out of
// it. You can do the reverse too.
//
// ```kotlin
// val sparkleHeart = mutableListOf(240, 159, 146, 150)
// // We know these bytes are valid, so we'll use `unwrap()`.
// val sparkleHeart1 = String.fromUtf8(sparkleHeart).unwrap()
// check("💖" == sparkleHeart1)
// val bytes = sparkleHeart1.intoBytes()
// check(bytes == listOf(240, 159, 146, 150))
// ```

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * A UTF-8–encoded, growable string.
 *
 * `String` is the most common string type. It has ownership over the contents
 * of the string, stored in a heap-allocated buffer (see [Representation](#representation)).
 * It is closely related to its borrowed counterpart, the primitive [str].
 *
 * # Examples
 *
 * You can create a `String` from [a literal string][str] with [String.from]:
 *
 * [String.from]: From.from
 *
 * ```kotlin
 * val hello = "Hello, world!"
 * ```
 *
 * You can append a [char] to a `String` with the [push] method, and
 * append a [str] with the [pushStr] method:
 *
 * ```kotlin
 * var hello = "Hello, "
 * hello += 'w'
 * hello += "orld!"
 * ```
 *
 * [push]: String.push
 * [pushStr]: String.pushStr
 *
 * If you have a vector of UTF-8 bytes, you can create a `String` from it with
 * the [fromUtf8] method:
 *
 * ```kotlin
 * // some bytes, in a vector
 * val sparkleHeart = mutableListOf(240, 159, 146, 150)
 * // We know these bytes are valid, so we'll use `unwrap()`.
 * val sparkleHeart1 = String.fromUtf8(sparkleHeart).unwrap()
 * check("💖" == sparkleHeart1)
 * ```
 *
 * [fromUtf8]: String.fromUtf8
 *
 * # UTF-8
 *
 * `String`s are always valid UTF-8. If you need a non-UTF-8 string, consider
 * [OsString]. It is similar, but without the UTF-8 constraint. Because UTF-8
 * is a variable width encoding, `String`s are typically smaller than an array of
 * the same `char`s:
 *
 * ```kotlin
 * // `s` is ASCII which represents each `char` as one byte
 * val s = "hello"
 * check(s.len() == 5)
 * // A `char` array with the same contents would be longer because
 * // every `char` is four bytes
 * val s1 = listOf('h', 'e', 'l', 'l', 'o')
 * val size: Int = s1.intoIter().map({ c -> sizeOfVal(c) }).sum()
 * check(size == 20)
 * // However, for non-ASCII strings, the difference will be smaller
 * // and sometimes they are the same
 * val s2 = "💖💖💖💖💖"
 * check(s2.len() == 20)
 * val s3 = listOf(0x1f496, 0x1f496, 0x1f496, 0x1f496, 0x1f496)
 * val size1: Int = s3.intoIter().map({ c -> sizeOfVal(c) }).sum()
 * check(size1 == 20)
 * ```
 *
 * This raises interesting questions as to how `s[i]` should work.
 * What should `i` be here? Several options include byte indices and
 * `char` indices but, because of UTF-8 encoding, only byte indices
 * would provide constant time indexing. Getting the `i`th `char`, for
 * example, is available using [chars]:
 *
 * ```kotlin
 * val s = "hello"
 * val thirdCharacter = s.chars().nth(2)
 * check(thirdCharacter == 'l')
 * val s1 = "💖💖💖💖💖"
 * val thirdCharacter1 = s1.chars().nth(2)
 * check(thirdCharacter1 == 0x1f496)
 * ```
 *
 * Next, what should `s[i]` return? Because indexing returns a reference
 * to underlying data it could be `UByte`, `[UByte]`, or something similar.
 * Since we're only providing one index, `UByte` makes the most sense but that
 * might not be what the user expects and can be explicitly achieved with
 * [asBytes]:
 *
 * ```kotlin
 * // The first byte is 104 - the byte value of ``h``
 * val s = "hello"
 * check(s.encodeToByteArray().map { it.toUByte() }[0] == 104u.toUByte())
 * // or
 * check(s.encodeToByteArray().map { it.toUByte() }[0] == 104u)
 * // The first byte is 240 which isn't obviously useful
 * val s1 = "💖💖💖💖💖"
 * check(s1.encodeToByteArray().map { it.toUByte() }[0] == 240u.toUByte())
 * ```
 *
 * Due to these ambiguities/restrictions, indexing with a `Int` is simply
 * forbidden:
 *
 * ```compile_fail,E0277
 * let s = "hello";
 *
 * // The following will not compile
 * println("The first letter of s is {}", s[0]);
 * ```
 *
 * It is more clear, however, how `s[i .. j]` should work (that is,
 * indexing with a range). It should accept byte indices (to be constant-time)
 * and return a `String` which is UTF-8 encoded. This is also called "string slicing".
 * Note this will panic if the byte indices provided are not character
 * boundaries - see [isCharBoundary] for more details. See the implementations
 * for [SliceIndex.str] for more details on string slicing. For a non-panicking
 * version of string slicing, see [get].
 *
 * [OsString]: ../../std/ffi/struct.OsString.html "ffi.OsString"
 * [asBytes]: str.asBytes
 * [get]: str.get
 * [isCharBoundary]: str.isCharBoundary
 *
 * The [bytes] and [chars] methods return iterators over the bytes and
 * codepoints of the string, respectively. To iterate over codepoints along
 * with byte indices, use [charIndices].
 *
 * [bytes]: str.bytes
 * [chars]: str.chars
 * [charIndices]: str.charIndices
 *
 * # Deref
 *
 * `String` implements <code>[Deref]<Target = [str]></code>, and so inherits all of [str]s
 * methods. In addition, this means that you can pass a `String` to a
 * function which takes a [str] by using an ampersand ():
 *
 * ```kotlin
 * fun takesStr(s: String) {}
 * val s = "Hello"
 * takesStr(s)
 * ```
 *
 * This will create a [str] from the `String` and pass it in. This
 * conversion is very inexpensive, and so generally, functions will accept
 * [str]s as arguments unless they need a `String` for some specific
 * reason.
 *
 * In certain cases Rust doesn't have enough information to make this
 * conversion, known as [Deref] coercion. In the following example a string
 * slice [`a.str`][str] implements the trait `TraitExample`, and the function
 * `exampleFunc` takes anything that implements the trait. In this case Rust
 * would need to make two implicit conversions, which Rust doesn't have the
 * means to do. For that reason, the following example will not compile.
 *
 * ```compile_fail,E0277
 * trait TraitExample {}
 *
 * impl<a> TraitExample for &a str {}
 *
 * fn exampleFunc<A: TraitExample>(exampleArg: A) {}
 *
 * let exampleString = String.from("exampleString");
 * exampleFunc(&exampleString);
 * ```
 *
 * There are two options that would work instead. The first would be to
 * change the line `exampleFunc(exampleString);` to
 * `exampleFunc(exampleString.asStr());`, using the method [asStr]
 * to explicitly extract the string slice containing the string. The second
 * way changes `exampleFunc(exampleString);` to
 * `exampleFunc(* exampleString);`. In this case we are dereferencing a
 * `String` to a [str], then referencing the [str] back to
 * [str]. The second way is more idiomatic, however both work to do the
 * conversion explicitly rather than relying on the implicit conversion.
 *
 * # Representation
 *
 * A `String` is made up of three components: a pointer to some bytes, a
 * length, and a capacity. The pointer points to the internal buffer which `String`
 * uses to store its data. The length is the number of bytes currently stored
 * in the buffer, and the capacity is the size of the buffer in bytes. As such,
 * the length will always be less than or equal to the capacity.
 *
 * This buffer is always stored on the heap.
 *
 * You can look at these with the [asPtr], [len], and [capacity]
 * methods:
 *
 * ```kotlin
 * val story = "Once upon a time..."
 * // Deconstruct the String into parts.
 * val (ptr, len, capacity) = story.intoRawParts()
 * // story has nineteen bytes
 * check(19 == len)
 * // We can re-build a String out of ptr, len, and capacity. This is all
 * // unchecked because we are responsible for making sure the components are
 * // valid:
 * val s = String.fromRawParts(ptr, len, capacity)
 * check("Once upon a time..." == s)
 * ```
 *
 * [asPtr]: str.asPtr
 * [len]: String.len
 * [capacity]: String.capacity
 *
 * If a `String` has enough capacity, adding elements to it will not
 * re-allocate. For example, consider this program:
 *
 * ```kotlin
 * var s = ""
 * println("${s.capacity()}")
 * for (unused in 0 until 5) {
 *     s += "hello"
 *     println("${s.capacity()}")
 * }
 * ```
 *
 * This will output the following:
 *
 * ```text
 * 0
 * 8
 * 16
 * 16
 * 32
 * 32
 * ```
 *
 * At first, we have no memory allocated at all, but as we append to the
 * string, it increases its capacity appropriately. If we instead use the
 * [withCapacity] method to allocate the correct capacity initially:
 *
 * ```kotlin
 * var s = ""
 * println("${s.capacity()}")
 * for (unused in 0 until 5) {
 *     s += "hello"
 *     println("${s.capacity()}")
 * }
 * ```
 *
 * [withCapacity]: String.withCapacity
 *
 * We end up with a different output:
 *
 * ```text
 * 25
 * 25
 * 25
 * 25
 * 25
 * 25
 * ```
 *
 * Here, there's no need to allocate more memory inside the loop.
 *
 * [str]: prim@str "str"
 * [str]: prim@str "str"
 * [str]: prim@str "&str"
 * [asStr]: String.asStr
 */
public open class RustAllocString(
    public var vec: MutableList<UByte>,
) : Pattern<Searcher>, Add<kotlin.String, String>, AddAssign<kotlin.String>, DerefPure, FromStr<Nothing>, SpecToString, AsRef<kotlin.String>, AsMut<kotlin.String>, Appendable {
    /**
     * Decomposes a `String` into its raw components: `(pointer, length, capacity)`.
     *
     * Returns the raw pointer to the underlying data, the length of
     * the string (in bytes), and the allocated capacity of the data
     * (in bytes). These are the same arguments in the same order as
     * the arguments to [fromRawParts].
     *
     * After calling this function, the caller is responsible for the
     * memory previously managed by the `String`. The only way to do
     * this is to convert the raw pointer, length, and capacity back
     * into a `String` with the [fromRawParts] function, allowing
     * the destructor to perform the cleanup.
     *
     * If `capacity == 0`, then `pointer` may be dangling. Otherwise,
     * it will always be valid for reads and writes over `capacity`
     * bytes, and point to the start of the string.
     *
     * [fromRawParts]: String.fromRawParts
     *
     * # Examples
     *
     * ```kotlin
     * val s = "hello"
     * val (ptr, len, cap) = s.intoRawParts()
     * val rebuilt = String.fromRawParts(ptr, len, cap)
     * check(rebuilt == "hello")
     * ```
     */
    public open fun intoRawParts(): Triple<MutPtr, Int, Int> = this.vec.intoRawParts()

    /**
     * Converts a `String` into a byte vector.
     *
     * This consumes the `String`, so we do not need to copy its contents.
     *
     * # Examples
     *
     * ```kotlin
     * val s = "hello"
     * val bytes = s.intoBytes()
     * check(listOf(104, 101, 108, 108, 111) == bytes)
     * ```
     */
    public open fun intoBytes(): List<UByte> = this.vec

    /**
     * Extracts a string slice containing the entire `String`.
     *
     * # Examples
     *
     * ```kotlin
     * val s = "foo"
     * check("foo" == s)
     * ```
     */
    public open fun asStr(): kotlin.String {
        // SAFETY: String contents are stipulated to be valid UTF-8, invalid contents are an error
        // at construction.
        return String.fromUtf8Unchecked(this.vec.asSlice())
    }

    /**
     * Converts a `String` into a mutable string slice.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "foobar"
     * val sMutStr = s.asMutStr()
     * sMutStr.makeAsciiUppercase()
     * check("FOOBAR" == sMutStr)
     * ```
     */
    public open fun asMutStr(): kotlin.String {
        // SAFETY: String contents are stipulated to be valid UTF-8, invalid contents are an error
        // at construction.
        return String.fromUtf8UncheckedMut(this.vec.asMutSlice())
    }

    /**
     * Appends a given string slice onto the end of this `String`.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "foo"
     * s += "bar"
     * check("foobar" == s)
     * ```
     */
    public open fun pushStr(string: kotlin.String) {
        this.vec.extendFromSlice(string.asBytes())
    }

    /**
     * Appends a given string slice onto the end of this `String`, returning
     * [TryReserveError] otherwise.
     */
    public open fun tryPushStr(string: kotlin.String): Result<Unit, TryReserveError> = this.vec.tryExtendFromSliceOfBytes(string.asBytes())

    public open fun pushStrSlice(slice: List<kotlin.String>) {
        var selfMut = this
        // use saturating arithmetic to ensure that in the case of an overflow, reserve() throws OOM
        val additional: Saturating<Int> = slice.map({ x -> Saturating(x.len()) }).sum()
        selfMut.reserve(additional.value)
        val (ptr, len, cap) = take(selfMut).intoRawParts()
        // SAFETY: `self` have reserved enough space for strs in `slice`, and we are copying from valid UTF-8 slices.
        // Therefore all unchecked operations are safe.
        run {
            var dst = ptr.add(len)
            for (new in slice) {
                copyNonoverlapping(new.asPtr(), dst, new.len())
                dst = dst.add(new.len())
            }
            selfMut = String.fromRawParts(ptr, len + additional.value, cap)
        }
    }

    /**
     * Copies elements from `src` range to the end of the string.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, if the range is
     * bounded on either end and does not lie on a [char] boundary, or if the
     * new capacity exceeds `Long.MAX` bytes.
     *
     * # Examples
     *
     * ```kotlin
     * var string = "abcde"
     * string.extendFromWithin(2 until Int.MAX_VALUE)
     * check(string == "abcdecde")
     * string.extendFromWithin(0 until 2)
     * check(string == "abcdecdeab")
     * string.extendFromWithin(4 until 8)
     * check(string == "abcdecdeabecde")
     * ```
     */
    public open fun <R> extendFromWithin(src: R) where R : RangeBounds<Int> {
        val src1 = Range(src, 0 until this.len())
        check(this.isCharBoundary(start))
        check(this.isCharBoundary(end))
        this.vec.extendFromWithin(src1)
    }

    /**
     * Returns this `String`s capacity, in bytes.
     *
     * # Examples
     *
     * ```kotlin
     * val s = ""
     * check(s.capacity() >= 10)
     * ```
     */
    public open fun capacity(): Int = this.vec.capacity()

    /**
     * Reserves capacity for at least `additional` bytes more than the
     * current length. The allocator may reserve more space to speculatively
     * avoid frequent allocations. After calling `reserve`,
     * capacity will be greater than or equal to `self.len() + additional`.
     * Does nothing if capacity is already sufficient.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var s = ""
     * s.reserve(10)
     * check(s.capacity() >= 10)
     * ```
     *
     * This might not actually increase the capacity:
     *
     * ```kotlin
     * var s = ""
     * s += 'a'
     * s += 'b'
     * // s now has a length of 2 and a capacity of at least 10
     * val capacity = s.capacity()
     * check(2 == s.length)
     * check(capacity >= 10)
     * // Since we already have at least an extra 8 capacity, calling this...
     * s.reserve(8)
     * // ... doesn't actually increase.
     * check(capacity == s.capacity())
     * ```
     */
    public open fun reserve(additional: Int) {
        this.vec.reserve(additional)
    }

    /**
     * Reserves the minimum capacity for at least `additional` bytes more than
     * the current length. Unlike [reserve], this will not
     * deliberately over-allocate to speculatively avoid frequent allocations.
     * After calling `reserveExact`, capacity will be greater than or equal to
     * `self.len() + additional`. Does nothing if the capacity is already
     * sufficient.
     *
     * [reserve]: String.reserve
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * var s = ""
     * s.reserveExact(10)
     * check(s.capacity() >= 10)
     * ```
     *
     * This might not actually increase the capacity:
     *
     * ```kotlin
     * var s = ""
     * s += 'a'
     * s += 'b'
     * // s now has a length of 2 and a capacity of at least 10
     * val capacity = s.capacity()
     * check(2 == s.length)
     * check(capacity >= 10)
     * // Since we already have at least an extra 8 capacity, calling this...
     * s.reserveExact(8)
     * // ... doesn't actually increase.
     * check(capacity == s.capacity())
     * ```
     */
    public open fun reserveExact(additional: Int) {
        this.vec.reserveExact(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` bytes more than the
     * current length. The allocator may reserve more space to speculatively
     * avoid frequent allocations. After calling `tryReserve`, capacity will be
     * greater than or equal to `self.len() + additional` if it returns
     * `Ok(())`. Does nothing if capacity is already sufficient. This method
     * preserves the contents even if an error occurs.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.TryReserveError
     *
     * fun processData(data: String): Result<String> {
     *     var output = ""
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserve(data.length).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     output += data
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.vec.tryReserve(additional)

    /**
     * Tries to reserve the minimum capacity for at least `additional` bytes
     * more than the current length. Unlike [tryReserve], this will not
     * deliberately over-allocate to speculatively avoid frequent allocations.
     * After calling `tryReserveExact`, capacity will be greater than or
     * equal to `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: String.tryReserve
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.TryReserveError
     *
     * fun processData(data: String): Result<String> {
     *     var output = ""
     *     // Pre-reserve the memory, exiting if we can't
     *     output.tryReserveExact(data.length).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     output += data
     *     return Result.success(output)
     * }
     * ```
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.vec.tryReserveExact(additional)

    /**
     * Shrinks the capacity of this `String` to match its length.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "foo"
     * s.reserve(100)
     * check(s.capacity() >= 100)
     * s.shrinkToFit()
     * check(3 == s.capacity())
     * ```
     */
    public open fun shrinkToFit() {
        this.vec.shrinkToFit()
    }

    /**
     * Shrinks the capacity of this `String` with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "foo"
     * s.reserve(100)
     * check(s.capacity() >= 100)
     * s.shrinkTo(10)
     * check(s.capacity() >= 10)
     * s.shrinkTo(0)
     * check(s.capacity() >= 3)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.vec.shrinkTo(minCapacity)
    }

    /**
     * Appends the given [char] to the end of this `String`.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` _bytes_.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "abc"
     * s += '1'
     * s += '2'
     * s += '3'
     * check("abc123" == s)
     * ```
     */
    public open fun push(ch: Char) {
        val len = this.len()
        val chLen = ch.lenUtf8()
        this.reserve(chLen)
        // SAFETY: Just reserved capacity for at least the length needed to encode `ch`.
        run {
            Char.encodeUtf8RawUnchecked(ch.toUInt(), this.vec.asMutPtr().add(len))
            this.vec.setLen(len + chLen)
        }
    }

    /**
     * Returns a byte slice of this `String`s contents.
     *
     * The inverse of this method is [fromUtf8].
     *
     * [fromUtf8]: String.fromUtf8
     *
     * # Examples
     *
     * ```kotlin
     * val s = "hello"
     * check(listOf(104, 101, 108, 108, 111) == s.encodeToByteArray().map { it.toUByte() })
     * ```
     */
    public open fun asBytes(): List<UByte> = this.vec.asSlice()

    /**
     * Shortens this `String` to the specified length.
     *
     * If `newLen` is greater than or equal to the string's current length,
     * this has no effect.
     *
     * Note that this method has no effect on the allocated capacity
     * of the string
     *
     * # Panics
     *
     * Panics if `newLen` does not lie on a [char] boundary.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "hello"
     * s.truncate(2)
     * check("he" == s)
     * ```
     */
    public open fun truncate(newLen: Int) {
        if (newLen >= this.len()) {
            return
        }
        check(this.isCharBoundary(newLen))
        this.vec.truncate(newLen)
    }

    /**
     * Removes the last character from the string buffer and returns it.
     *
     * Returns [None] if this `String` is empty.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "abč"
     * check(s.pop() == '\u010d')
     * check(s.pop() == 'b')
     * check(s.pop() == 'a')
     * check(s.pop() == null)
     * ```
     */
    public open fun pop(): Char? {
        val ch = (this.chars().rev().next() ?: return null)
        val newlen = this.len() - ch.lenUtf8()
        // SAFETY: `newlen` is less than old length.
        run {
            this.vec.setLen(newlen)
        }
        return ch
    }

    /**
     * Removes a [char] from this `String` at byte position `idx` and returns it.
     *
     * Copies all bytes after the removed char to new positions.
     *
     * Note that calling this in a loop can result in quadratic behavior.
     *
     * # Panics
     *
     * Panics if `idx` is larger than or equal to the `String`s length,
     * or if it does not lie on a [char] boundary.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "abç"
     * check(s.remove(0) == 'a')
     * check(s.remove(1) == '\u00e7')
     * check(s.remove(0) == 'b')
     * ```
     */
    public open fun remove(idx: Int): Char {
        val ch1 =
            when (val matchVal = this[idx until this.size].chars().next()) {
                null -> error("cannot remove a char from the end of a string")
                else -> {
                    val ch = matchVal
                    ch
                }
            }
        val next = idx + ch1.lenUtf8()
        val len = this.len()
        // SAFETY:
        // * Since `next` is not bigger than `len`, `self.vec.asPtr().add(next)`
        // is valid for reads and `self.vec.asMutPtr().add(idx)` is valid for writes.
        // Both pointers are valid for `len - next` bytes.
        // * new length is less than old length.
        run {
            copy(this.vec.asPtr().add(next), this.vec.asMutPtr().add(idx), len - next)
            this.vec.setLen(len - (next - idx))
        }
        return ch1
    }

    /**
     * Remove all matches of pattern `pat` in the `String`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(stringRemoveMatches)
     * public fun doctestEntry() {
     *     var s = "Trees are not green, the sky is not blue."
     *     s.removeMatches("not ")
     *     check("Trees are green, the sky is blue." == s)
     * }
     * ```
     *
     * Matches will be detected and removed iteratively, so in cases where
     * patterns overlap, only the first pattern will be removed:
     *
     * ```kotlin
     * @feature(stringRemoveMatches)
     * public fun doctestEntry() {
     *     var s = "banana"
     *     s.removeMatches("ana")
     *     check("bna" == s)
     * }
     * ```
     */
    public open fun <Searcher, P : Pattern<Searcher>> removeMatches(pat: P) {
        val rejections1 = run {
            val searcher = pat.intoSearcher(this)
            // Per Searcher.next:
            //
            // A Match result needs to contain the whole matched pattern,
            // however Reject results may be split up into arbitrary many
            // adjacent fragments. Both ranges may have zero length.
            //
            // In practice the implementation of Searcher.nextMatch tends to
            // be more efficient, so we use it here and do some work to invert
            // matches into rejections since that's what we want to copy below.
            var front = 0
            val rejections: Vec =
                fromFn(closure@{
                    val (start, end) = when (val tryResult = searcher.nextMatch()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
                    val prevFront = front
                    front = end
                    Pair(prevFront, start)
                }).collect()
            rejections.intoIter().chain(Iter.once(Pair(front, this.len())))
        }
        var len = 0
        val ptr = this.vec.asMutPtr()
        for ((start, end) in rejections1) {
            val count = end - start
            if (start != len) {
                // SAFETY: per Searcher.next:
                //
                // The stream of Match and Reject values up to a Done will
                // contain index ranges that are adjacent, non-overlapping,
                // covering the whole haystack, and laying on utf8
                // boundaries.
                run {
                    copy(ptr.add(start), ptr.add(len), count)
                }
            }
            len += count
        }
        // SAFETY: `len` is less than or equal to the original length of the string.
        run {
            this.vec.setLen(len)
        }
    }

    /**
     * Retains only the characters specified by the predicate.
     *
     * In other words, remove all characters `c` such that `f(c)` returns `false`.
     * This method operates in place, visiting each character exactly once in the
     * original order, and preserves the order of the retained characters.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "fOObAr"
     * s.retain({ c -> c != '_' })
     * check(s == "foobar")
     * ```
     *
     * Because the elements are visited exactly once in the original order,
     * external state may be used to decide which elements to keep.
     *
     * ```kotlin
     * var s = "abcde"
     * val keep = booleanArrayOf(false, true, true, false, true)
     * var iter = keep.iter()
     * s.retain({ iter.next()!! })
     * check(s == "bce")
     * ```
     */
    public open fun retain(f: (Char) -> Boolean) {
        open class PanicGuard(
            public var s: String,
            public var write: Int,
        ) {
            public open fun drop() {
                check(this.write <= this.s.length)
                check(String.fromUtf8(((this.s.vec).subList(0, this.write))).isOk())
                // SAFETY: Restore the string length to the number of bytes written so far.
                this.s.vec.setLen(this.write)
            }
        }
        val len = this.len()
        if (len == 0) {
            // Explicit check results in better optimization
            return
        }
        // Fast path: find the first character that should be removed or return early.
        val chars = this.charIndices()
        var (read, write) =
            while (true) {
                val (idx, ch) = chars.next() ?: return
                if (unlikely(!f(ch))) {
                    break
                }
            }
        (chars).let { }
        // Slow path: at least one character is going to be removed.
        val g = PanicGuard(s = this, write = write)
        while (read < len) {
            // SAFETY: Use `vec`: `String`s deref includes `write .. read`,
            // which may be invalid UTF-8. The vector's length is still `len`;
            // `read<len`, so `read .. len` is in bounds and non-empty.
            // At least one character was removed, so `write<read`.
            // Copies end before the next `read`, preserving unread bytes.
            // `read` starts after a `charIndices` character and advances by whole
            // characters, so it stays on a char boundary. The original string was valid UTF-8,
            // so the unchanged, non-empty tail is valid UTF-8 and `chars().next()` returns `Some`.
            val ch1 =
                run {
                    val tail = String.fromUtf8Unchecked(g.s.vec.getUnchecked(read until len))
                    tail.chars().next().unwrapUnchecked()
                }
            val chLen = ch1.lenUtf8()
            if (f(ch1)) {
                // SAFETY: `read` is on a char boundary, as guaranteed above; `g.write` is
                // within bounds because it is always behind `read`.
                run {
                    val ptr = g.s.vec.asMutPtr()
                    copy((MutPtr((ptr).rawAddress + ((read).toLong() * (PrimitiveLayouts.U8).strideBytes))).asConstPtr(), MutPtr((ptr).rawAddress + ((g.write).toLong() * (PrimitiveLayouts.U8).strideBytes)), chLen)
                }
                g.write += chLen
            }
            read += chLen
        }
        // All bytes processed; commit the final length by dropping the guard.
        (g).let { }
    }

    /**
     * Inserts a character into this `String` at byte position `idx`.
     *
     * Reallocates if `self.capacity()` is insufficient, which may involve copying all
     * `self.capacity()` bytes. Makes space for the insertion by copying all bytes of
     * `self[idx ..]` to new positions.
     *
     * Note that calling this in a loop can result in quadratic behavior.
     *
     * # Panics
     *
     * Panics if `idx` is larger than the `String`s length, or if it does not
     * lie on a [char] boundary.
     *
     * # Examples
     *
     * ```kotlin
     * var s = ""
     * s.insert(0, 'f')
     * s.insert(1, 'o')
     * s.insert(2, 'o')
     * check("foo" == s)
     * ```
     */
    public open fun insert(idx: Int, ch: Char) {
        check(this.isCharBoundary(idx))
        val len = this.len()
        val chLen = ch.lenUtf8()
        this.reserve(chLen)
        // SAFETY: Move the bytes starting from `idx` to their new location `chLen`
        // bytes ahead. This is safe because sufficient capacity was reserved, and `idx`
        // is a char boundary.
        run {
            copy(this.vec.asPtr().add(idx), this.vec.asMutPtr().add(idx + chLen), len - idx)
        }
        // SAFETY: Encode the character into the vacated region if `idx != len`,
        // or into the uninitialized spare capacity otherwise.
        run {
            Char.encodeUtf8RawUnchecked(ch.toUInt(), this.vec.asMutPtr().add(idx))
        }
        // SAFETY: Update the length to include the newly added bytes.
        run {
            this.vec.setLen(len + chLen)
        }
    }

    /**
     * Inserts a string slice into this `String` at byte position `idx`.
     *
     * Reallocates if `self.capacity()` is insufficient, which may involve copying all
     * `self.capacity()` bytes. Makes space for the insertion by copying all bytes of
     * `self[idx ..]` to new positions.
     *
     * Note that calling this in a loop can result in quadratic behavior.
     *
     * # Panics
     *
     * Panics if `idx` is larger than the `String`s length, or if it does not
     * lie on a [char] boundary.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "bar"
     * s.insertStr(0, "foo")
     * check("foobar" == s)
     * ```
     */
    public open fun insertStr(idx: Int, string: kotlin.String) {
        check(this.isCharBoundary(idx))
        val len = this.len()
        val amt = string.len()
        this.reserve(amt)
        // SAFETY: Move the bytes starting from `idx` to their new location `amt` bytes
        // ahead. This is safe because sufficient capacity was just reserved, and `idx`
        // is a char boundary.
        run {
            copy(this.vec.asPtr().add(idx), this.vec.asMutPtr().add(idx + amt), len - idx)
        }
        // SAFETY: Copy the new string slice into the vacated region if `idx != len`,
        // or into the uninitialized spare capacity otherwise. The borrow checker
        // ensures that the source and destination do not overlap.
        run {
            copyNonoverlapping(string.asPtr(), this.vec.asMutPtr().add(idx), amt, typeLayoutT = PrimitiveLayouts.U8)
        }
        // SAFETY: Update the length to include the newly added bytes.
        run {
            this.vec.setLen(len + amt)
        }
    }

    /**
     * Returns a mutable reference to the contents of this `String`.
     *
     * # Safety
     *
     * This function is unchecked because the returned `List` allows writing
     * bytes which are not valid UTF-8. If this constraint is violated, using
     * the original `String` after dropping the `List` may violate memory
     * safety, as the rest of the standard library assumes that `String`s are
     * valid UTF-8.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "hello"
     * run {
     *     val vec = s.asMutVec()
     *     check(listOf(104, 101, 108, 108, 111) == vec)
     *     vec.reverse()
     * }
     * check(s == "olleh")
     * ```
     */
    public open fun asMutVec(): List<UByte> = this.vec

    /**
     * Returns the length of this `String`, in bytes, not [char]s or
     * graphemes. In other words, it might not be what a human considers the
     * length of the string.
     *
     * # Examples
     *
     * ```kotlin
     * val a = "foo"
     * check(a.length == 3)
     * val fancyF = "ƒoo"
     * check(fancyF.length == 4)
     * check(fancyF.chars().count() == 3)
     * ```
     */
    public open fun len(): Int = this.vec.len()

    /**
     * Returns `true` if this `String` has a length of zero, and `false` otherwise.
     *
     * # Examples
     *
     * ```kotlin
     * var v = ""
     * check(v.isEmpty())
     * v += 'a'
     * check(!v.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.len() == 0

    /**
     * Splits the string into two at the given byte index.
     *
     * Returns a newly allocated `String`. `self` contains bytes `[0, at)`, and
     * the returned `String` contains bytes `[at, len)`. `at` must be on the
     * boundary of a UTF-8 code point.
     *
     * Note that the capacity of `self` does not change.
     *
     * # Panics
     *
     * Panics if `at` is not on a `UTF - 8` code point boundary, or if it is beyond the last
     * code point of the string.
     *
     * # Examples
     *
     * ```kotlin
     * var hello = "Hello, World!"
     * val world = hello.splitOff(7)
     * check(hello == "Hello, ")
     * check(world == "World!")
     * ```
     */
    public open fun splitOff(at: Int): String {
        check(this.isCharBoundary(at))
        val other = this.vec.splitOff(at)
        // SAFETY: `other` contains only valid UTF-8 because `at` is on a `UTF - 8` code point boundary.
        return String.fromUtf8Unchecked(other)
    }

    /**
     * Truncates this `String`, removing all contents.
     *
     * While this means the `String` will have a length of zero, it does not
     * touch its capacity.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "foo"
     * s = ""
     * check(s.isEmpty())
     * check(0 == s.length)
     * check(3 == s.capacity())
     * ```
     */
    public open fun clear() {
        this.vec.clear()
    }

    /**
     * Removes the specified range from the string in bulk, returning all
     * removed characters as an iterator.
     *
     * The returned iterator keeps a mutable borrow on the string to optimize
     * its implementation.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and does not lie on a [char] boundary.
     *
     * # Leaking
     *
     * If the returned iterator goes out of scope without being dropped (due to
     * [core.mem.forget], for example), the string may still contain a copy
     * of any drained characters, or may have lost characters arbitrarily,
     * including characters outside the range.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "α is alpha, β is beta"
     * val betaOffset = (s.indexOf('\u03b2').takeIf { it >= 0 } ?: s.length)
     * // Remove the range up until the β from the string
     * val t: String = s.drain(0 until betaOffset).joinToString("")
     * check(t == "α is alpha, ")
     * check(s == "β is beta")
     * // A full range clears the string, like `clear()` does
     * s.drain(0 until 0)
     * check(s == "")
     * ```
     */
    public open fun <R> drain(range: R): Drain where R : RangeBounds<Int> {
        // Memory safety
        //
        // The String version of Drain does not have the memory safety issues
        // of the vector version. The data is just plain bytes.
        // Because the range removal happens in Drop, if the Drain iterator is leaked,
        // the removal will not happen.
        val structVal = Range(range, 0 until this.len())
        val start = structVal.start
        val end = structVal.end
        check(this.isCharBoundary(start))
        check(this.isCharBoundary(end))
        // Take out two simultaneous borrows. The String won't be accessed
        // until iteration is over, in Drop.
        val selfPtr = mutPtrFromRustCast(this)
        // SAFETY: `slice.range` and `isCharBoundary` do the appropriate bounds checks.
        val charsIter = this.getUnchecked(start until end).chars()
        return Drain(start = start, end = end, iter = charsIter, string = selfPtr)
    }

    /**
     * Converts a `String` into an iterator over the [char]s of the string.
     *
     * As a string consists of valid UTF-8, we can iterate through a string
     * by [char]. This method returns such an iterator.
     *
     * It's important to remember that [char] represents a Unicode Scalar
     * Value, and might not match your idea of what a `character` is. Iteration
     * over grapheme clusters may be what you actually want. That functionality
     * is not provided by Rust's standard library, check crates.io instead.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val word = "goodbye"
     * var chars = word.intoChars()
     * check('g' == chars.next())
     * check('o' == chars.next())
     * check('o' == chars.next())
     * check('d' == chars.next())
     * check('b' == chars.next())
     * check('y' == chars.next())
     * check('e' == chars.next())
     * check(null == chars.next())
     * ```
     *
     * Remember, [char]s might not match your intuition about characters:
     *
     * ```kotlin
     * val y = "y̆"
     * var chars = y.intoChars()
     * check('y' == chars.next())
     * // not y̆'
     * check('\u0306' == chars.next())
     * check(null == chars.next())
     * ```
     *
     * [char]: prim@char
     */
    public open fun intoChars(): IntoChars = IntoChars(bytes = this.intoBytes().intoIter())

    /**
     * Removes the specified range in the string,
     * and replaces it with the given string.
     * The given string doesn't need to be the same length as the range.
     *
     * # Panics
     *
     * Panics if the range has `startBound> endBound`, or, if the range is
     * bounded on either end and does not lie on a [char] boundary.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "α is alpha, β is beta"
     * val betaOffset = (s.indexOf('\u03b2').takeIf { it >= 0 } ?: s.length)
     * // Replace the range up until the β from the string
     * s.replaceRange(0 until betaOffset, "Α is capital alpha; ")
     * check(s == "Α is capital alpha; β is beta")
     * ```
     */
    public open fun <R> replaceRange(range: R, replaceWith: kotlin.String) where R : RangeBounds<Int> {
        // We avoid #81138 (nondeterministic RangeBounds impls) because we only use `range` once, here.
        val checkedRange = Range(range, 0 until this.len())
        check(this.isCharBoundary(checkedRange.start)) { "start of range should be a character boundary" }
        check(this.isCharBoundary(checkedRange.end)) { "end of range should be a character boundary" }
        if (replaceWith.len() > checkedRange.len()) {
            this.reserve(replaceWith.len() - checkedRange.len())
        }
        // SAFETY: We ensure that we're not replacing across a char boundary and
        // that the new contents are valid UTF-8. The only potentially-unsound
        // unwind from `splice` that would leave the string in an invalid state
        // would be from an error growing the allocation, which we protect against
        // by reserving it preemptively.
        this.asMutVec().splice(checkedRange, replaceWith.bytes())
    }

    /**
     * Replaces the leftmost occurrence of a pattern with another string, in-place.
     *
     * This method can be preferred over [`string.string.replacen`][replacen],
     * as it can use the `String`s existing capacity to prevent a reallocation if
     * sufficient space is available.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(stringReplaceInPlace)
     * public fun doctestEntry() {
     *     var s = "Test Results: ❌❌❌"
     *     // Replace the leftmost ❌ with a ✅
     *     s.replaceFirst('\u274c', "✅")
     *     check(s == "Test Results: ✅❌❌")
     * }
     * ```
     *
     * [replacen]: ../../std/primitive.str.html#method.replacen
     */
    public open fun <Searcher, P : Pattern<Searcher>> replaceFirst(from: P, to: kotlin.String) {
        val range =
            when (val matchVal = this.matchIndices(from).next()) {
                null -> { return }
                else -> {
                    val start = matchVal.first
                    val matchStr = matchVal.second
                    start until start + matchStr.len()
                }
            }
        this.replaceRange(range, to)
    }

    /**
     * Replaces the rightmost occurrence of a pattern with another string, in-place.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(stringReplaceInPlace)
     * public fun doctestEntry() {
     *     var s = "Test Results: ❌❌❌"
     *     // Replace the rightmost ❌ with a ✅
     *     s.replaceLast('\u274c', "✅")
     *     check(s == "Test Results: ❌❌✅")
     * }
     * ```
     */
    public open fun <Searcher, P : Pattern<Searcher>> replaceLast(from: P, to: kotlin.String) {
        val range =
            when (val matchVal = this.rmatchIndices(from).next()) {
                null -> { return }
                else -> {
                    val start = matchVal.first
                    val matchStr = matchVal.second
                    start until start + matchStr.len()
                }
            }
        this.replaceRange(range, to)
    }

    /**
     * Converts this `String` into a <code>[Holder]<[str]></code>.
     *
     * Before doing the conversion, this method discards excess capacity like [shrinkToFit].
     * Note that this call may reallocate and copy the bytes of the string.
     *
     * [shrinkToFit]: String.shrinkToFit
     * [str]: prim@str "str"
     *
     * # Examples
     *
     * ```kotlin
     * val s = "hello"
     * val b = s.intoBoxedStr()
     * ```
     */
    public open fun intoBoxedStr(): kotlin.String {
        val slice = this.vec.intoBoxedSlice()
        // SAFETY: `slice` contains only valid UTF-8 because `String` is guaranteed to be valid UTF-8.
        return fromBoxedUtf8Unchecked(slice)
    }

    /**
     * Consumes and leaks the `String`, returning a mutable reference to the contents,
     * `String`.
     *
     * The caller has free choice over the returned scope, including `static`. Indeed,
     * this function is ideally used for data that lives for the remainder of the program's life,
     * as dropping the returned reference will cause a memory leak.
     *
     * It does not reallocate or shrink the `String`, so the leaked allocation may include unused
     * capacity that is not part of the returned slice. If you want to discard excess capacity,
     * call [intoBoxedStr], and then [Box.leak] instead. However, keep in mind that
     * trimming the capacity may result in a reallocation and copy.
     *
     * [intoBoxedStr]: Self.intoBoxedStr
     *
     * # Examples
     *
     * ```kotlin
     * val x = "bucket"
     * val staticRef: String = x.leak()
     * check(staticRef == "bucket")
     * ```
     */
    public open fun leak(): kotlin.String {
        val slice = this.vec.leak()
        // SAFETY: `slice` contains only valid UTF-8 because `String` is guaranteed to be valid UTF-8.
        return fromUtf8UncheckedMut(slice)
    }

    public open fun clone(): RustAllocString = String(vec = this.vec.clone())

    /**
     * Clones the contents of `source` into `self`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     */
    public open fun cloneFrom(source: RustAllocString) {
        this.vec = source.vec
    }

    public open fun <I : Iterable<Char>> extend(iter: I) {
        iter.specExtendInto(this)
    }

    public open fun extendOne(c: Char) {
        this.push(c)
    }

    public open fun extendReserve(additional: Int) {
        this.reserve(additional)
    }

    public open fun <I : Iterable<Char>> extendChar(iter: I) {
        this.extend(iter.iterator().cloned())
    }

    public open fun extendOneChar(c: Char) {
        this.push(c)
    }

    public open fun extendReserveChar(additional: Int) {
        this.reserve(additional)
    }

    public open fun <I : Iterable<kotlin.String>> extendString(iter: I, specExtendIntoIFn: (I, RustAllocString) -> Any?) {
        specExtendIntoIFn(iter, this)
    }

    public open fun extendOne(s: kotlin.String) {
        this.pushStr(s)
    }

    public open fun <I : Iterable<String>> extendString(iter: I) {
        iter.iterator().forEach({ s -> this.pushStr(s) })
    }

    public open fun extendOne(s: String) {
        this.pushStr(s)
    }

    public open fun <I : Iterable<kotlin.String>> extendCowString(iter: I) {
        iter.iterator().forEach({ s -> this.pushStr(s) })
    }

    public open fun extendOneCowString(s: kotlin.String) {
        this.pushStr(s)
    }

    public open fun <I : Iterable<Char>> extendCharChar(iter: I) {
        this.vec.extend(iter.iterator().map({ c -> c.toU8() }))
    }

    public open fun extendOneCharChar(c: Char) {
        this.vec.push(c.toU8())
    }

    /**
     * A convenience impl that delegates to the impl for `String`.
     *
     * # Examples
     *
     * ```kotlin
     * check("Hello world".indexOf("world").takeIf { it >= 0 } == 6)
     * ```
     */
    public open fun intoSearcher(haystack: kotlin.String): Searcher = this.intoSearcher(haystack)

    public open fun isContainedIn(haystack: kotlin.String): Boolean = this.isContainedIn(haystack)

    public open fun isPrefixOf(haystack: kotlin.String): Boolean = this.isPrefixOf(haystack)

    public open fun stripPrefixOf(haystack: kotlin.String): kotlin.String? = this.stripPrefixOf(haystack)

    public open fun isSuffixOf(haystack: kotlin.String): Boolean = this.isSuffixOf(haystack)

    public open fun stripSuffixOf(haystack: kotlin.String): kotlin.String? = this.stripSuffixOf(haystack)

    public open fun asUtf8Pattern(): Utf8Pattern? = Utf8Pattern.StringPattern(this.asStr())

    public open fun eq(other: kotlin.String): Boolean = PartialEq.eq(this, other)

    public open fun ne(other: kotlin.String): Boolean = PartialEq.ne(this, other)

    public open fun eqString(other: kotlin.String): Boolean = PartialEq.eq(this, other)

    public open fun neString(other: kotlin.String): Boolean = PartialEq.ne(this, other)

    public open fun eqCowString(other: kotlin.String): Boolean = PartialEq.eq(this, other)

    public open fun neCowString(other: kotlin.String): Boolean = PartialEq.ne(this, other)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public override fun hashCode(): Int {
        var hasher = 0
        hasher = 31 * hasher + (this).hashCode()
        return hasher
    }

    /**
     * Implements the `+` operator for concatenating two strings.
     *
     * This consumes the `String` on the left-hand side and re-uses its buffer (growing it if
     * necessary). This is done to avoid allocating a new `String` and copying the entire contents on
     * every operation, which would lead to *O*(*n*^2) running time when building an *n*-byte string by
     * repeated concatenation.
     *
     * The string on the right-hand side is only borrowed; its contents are copied into the returned
     * `String`.
     *
     * # Examples
     *
     * Concatenating two `String`s takes the first by value and borrows the second:
     *
     * ```kotlin
     * val a = "hello"
     * val b = " world"
     * val c = a + b
     * // `a` is moved and can no longer be used here.
     * ```
     *
     * If you want to keep using the first `String`, you can clone it and append to the clone instead:
     *
     * ```kotlin
     * val a = "hello"
     * val b = " world"
     * val c = a.clone() + b
     * // `a` is still valid here.
     * ```
     *
     * Concatenating `String` slices can be done by converting the first to a `String`:
     *
     * ```kotlin
     * val a = "hello"
     * val b = " world"
     * val c = a.toString() + b
     * ```
     */
    public open fun add(other: kotlin.String): String {
        this.pushStr(other)
        return this
    }

    /**
     * Implements the `+=` operator for appending to a `String`.
     *
     * This has the same behavior as the [`pushStr`][String.pushStr] method.
     */
    public open fun addAssign(other: kotlin.String) {
        this.pushStr(other)
    }

    public open fun deref(): kotlin.String = this.asStr()

    public open fun derefMut(): kotlin.String = this.asMutStr()

    public override fun specToString(): String {
        val s: kotlin.String = this
        return String.from(s)
    }

    public open fun extendFromEscapeDefault(escaper: EscapeDefault) {
        val (front, middle, tail) = escaper.intoParts()
        val lengths = front.asRef().mapOrDefault({ f -> f.sizeHint().first }) + middle.asRef().mapOrDefault({ m -> m.sizeHint().first }) + tail.asRef().mapOrDefault({ t -> t.sizeHint().first })
        this.reserve(lengths)
        if (front != null) {
            val front1 = front
            this.extend(front1)
        }
        if (middle != null) {
            val middle1 = middle
            /**
             * Ensures a spare capacity of N bytes and then passes that as MaybeUninit
             * slice to a closure that returns the bytes written, then
             * updates the length.
             *
             * # Safety:
             *
             * The caller must initialize the number of bytes returned by the closure.
             */
            fun <T> withSpare(bytes: List<UByte>, then: (List<MaybeUninit<T>>) -> Int, n: Int) {
                run {
                    check(sizeOf<T>(typeLayoutT = UnresolvedTypeLayout<T>()) == 1)
                }
                bytes.reserve(n)
                val oldLen = bytes.size
                // SAFETY: We reserved enough space for the requested number of bytes.
                run {
                    val spare: List<MaybeUninit<T>> = bytes.spareCapacityMut().asMutPtr().cast().castUninit().castArray().asMutUnchecked()
                    val written = then(spare)
                    bytes.setLen(oldLen + written)
                }
            }
            for (c in middle1) {
                when (c) {
                    '\'', '"', '\\' -> {
                        this.push('\\')
                        this.push(c)
                    }
                    in ' '..'~' -> this.push(c)
                    '\t' -> {
                        this.push('\\')
                        this.push('t')
                    }
                    '\r' -> {
                        this.push('\\')
                        this.push('r')
                    }
                    '\n' -> {
                        this.push('\\')
                        this.push('n')
                    }
                    else -> {
                        val c1 = c
                        // SAFETY: We only let the function write ascii chars, so it remains valid utf8.
                        // and it returns the number of bytes written, so so withSpare ensures the rest for us.
                        run {
                            withSpare<Char>(this.asMutVec(), { spare ->
                                escapeUnicodeInto(spare, c1)
                            }, 10)
                        }
                    }
                }
            }
        }
        if (tail != null) {
            val tail1 = tail
            this.extend(tail1)
        }
    }

    public open fun asRef(): kotlin.String = this

    public open fun asMut(): kotlin.String = this

    public open fun asRefUByteSlice(): List<UByte> = this.asBytes()

    /**
     * Note that all methods of this trait append to the `String`s existing
     * contents; they never truncate or replace what is already there. In
     * particular, [fmt.Write.writeStr] uses [`pushStr`][String.pushStr]
     * and [fmt.Write.writeChar] uses [`push`][String.push].
     */
    public open fun writeStr(s: kotlin.String): Result<Unit, Error> {
        this.pushStr(s)
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun writeChar(c: Char): Result<Unit, Error> {
        this.push(c)
        return Result.Ok<Unit, Error>(Unit)
    }

    public companion object {
        /**
         * Creates a new empty `String`.
         *
         * Given that the `String` is empty, this will not allocate any initial
         * buffer. While that means that this initial operation is very
         * inexpensive, it may cause excessive allocation later when you add
         * data. If you have an idea of how much data the `String` will hold,
         * consider the [withCapacity] method to prevent excessive
         * re-allocation.
         *
         * [withCapacity]: String.withCapacity
         *
         * # Examples
         *
         * ```kotlin
         * val s = ""
         * ```
         */
        public fun new(): String = String(vec = mutableListOf())

        /**
         * Creates a new empty `String` with at least the specified capacity.
         *
         * `String`s have an internal buffer to hold their data. The capacity is
         * the length of that buffer, and can be queried with the [capacity]
         * method. This method creates an empty `String`, but one with an initial
         * buffer that can hold at least `capacity` bytes. This is useful when you
         * may be appending a bunch of data to the `String`, reducing the number of
         * reallocations it needs to do.
         *
         * [capacity]: String.capacity
         *
         * If the given capacity is `0`, no allocation will occur, and this method
         * is identical to the [new] method.
         *
         * [new]: String.new
         *
         * # Panics
         *
         * Panics if the capacity exceeds `Long.MAX` _bytes_.
         *
         * # Examples
         *
         * ```kotlin
         * var s = ""
         * // The String contains no chars, even though it has capacity for more
         * check(s.length == 0)
         * // These are all done without reallocating...
         * val cap = s.capacity()
         * for (unused in 0 until 10) {
         *     s += 'a'
         * }
         * check(s.capacity() == cap)
         * // ...but this may make the string reallocate
         * s += 'a'
         * ```
         */
        public fun withCapacity(capacity: Int): String = String(vec = ArrayList(capacity))

        /**
         * Creates a new empty `String` with at least the specified capacity.
         *
         * # Errors
         *
         * Returns [Err] if the capacity exceeds `Long.MAX` bytes,
         * or if the memory allocator reports failure.
         */
        public fun tryWithCapacity(capacity: Int): Result<String, TryReserveError> {
            return Result.Ok<String, TryReserveError>(String(vec = when (val tryResult = Vec.tryWithCapacity(capacity)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<String, TryReserveError>(tryResult.errValue()) }))
        }

        /**
         * Converts a vector of bytes to a `String`.
         *
         * A string ([String]) is made of bytes ([u8]), and a vector of bytes
         * ([Vec.u8]) is made of bytes, so this function converts between the
         * two. Not all byte slices are valid `String`s, however: `String`
         * requires that it is valid UTF-8. `fromUtf8()` checks to ensure that
         * the bytes are valid UTF-8, and then does the conversion.
         *
         * If you are sure that the byte slice is valid UTF-8, and you don't want
         * to incur the overhead of the validity check, there is an unchecked version
         * of this function, [fromUtf8Unchecked], which has the same behavior
         * but skips the check.
         *
         * This method will take care to not copy the vector, for efficiency's
         * sake.
         *
         * If you need a [str] instead of a `String`, consider
         * [str.fromUtf8].
         *
         * The inverse of this method is [intoBytes].
         *
         * # Errors
         *
         * Returns [Err] if the slice is not UTF-8 with a description as to why the
         * provided bytes are not UTF-8. The vector you moved in is also included.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // some bytes, in a vector
         * val sparkleHeart = mutableListOf(240, 159, 146, 150)
         * // We know these bytes are valid, so we'll use `unwrap()`.
         * val sparkleHeart1 = String.fromUtf8(sparkleHeart).unwrap()
         * check("💖" == sparkleHeart1)
         * ```
         *
         * Incorrect bytes:
         *
         * ```kotlin
         * // some invalid bytes, in a vector
         * val sparkleHeart = mutableListOf(0, 159, 146, 150)
         * check(String.fromUtf8(sparkleHeart).isErr())
         * ```
         *
         * See the docs for [FromUtf8Error] for more details on what you can do
         * with this error.
         *
         * [fromUtf8Unchecked]: String.fromUtf8Unchecked
         * [str]: prim@str "&str"
         * [intoBytes]: String.intoBytes
         */
        public fun fromUtf8(vec: List<UByte>): Result<String, FromUtf8Error> =
            run {
                val matchVal = String.fromUtf8(vec)
                when (matchVal) { is Result.Ok<*, *> -> {
                    Result.Ok<String, FromUtf8Error>(String(vec = vec))
                }; is Result.Err<*, *> -> {
                    val e = matchVal.errValue()
                    Result.Err<String, FromUtf8Error>(FromUtf8Error(bytes = vec, error = e))
                } }
            }

        /**
         * Converts a slice of bytes to a string, including invalid characters.
         *
         * Strings are made of bytes ([u8]), and a slice of bytes
         * ([`&[u8]`][byteslice]) is made of bytes, so this function converts
         * between the two. Not all byte slices are valid strings, however: strings
         * are required to be valid UTF-8. During this conversion,
         * `fromUtf8Lossy()` will replace any invalid UTF-8 sequences with
         * [`U.Fffd.Replacement.Character`][U+FFFD], which looks like this: �
         *
         * [byteslice]: prim@slice
         * [U+FFFD]: char.ReplacementCharacter
         *
         * If you are sure that the byte slice is valid UTF-8, and you don't want
         * to incur the overhead of the conversion, there is an unchecked version
         * of this function, [fromUtf8Unchecked], which has the same behavior
         * but skips the checks.
         *
         * [fromUtf8Unchecked]: String.fromUtf8Unchecked
         *
         * This function returns a [Cow.a.str]. If our byte slice is invalid
         * UTF-8, then we need to insert the replacement characters, which will
         * change the size of the string, and hence, require a `String`. But if
         * it's already valid UTF-8, we don't need a new allocation. This return
         * type allows us to handle both cases.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // some bytes, in a vector
         * val sparkleHeart = mutableListOf(240, 159, 146, 150)
         * val sparkleHeart1 = String.fromUtf8Lossy(sparkleHeart)
         * check("💖" == sparkleHeart1)
         * ```
         *
         * Incorrect bytes:
         *
         * ```kotlin
         * // some invalid bytes
         * val input = "Hello ðWorld".encodeToByteArray()
         * val output = String.fromUtf8Lossy(input)
         * check("Hello �World" == output)
         * ```
         */
        public fun fromUtf8Lossy(v: List<UByte>): kotlin.String {
            val REPLACEMENT: kotlin.String = "�"
            val iter = v.utf8Chunks()
            val chunk = iter.next() ?: return ""
            val firstValid = chunk.valid()
            if (chunk.invalid().isEmpty()) {
                check(firstValid.len() == v.size)
                return firstValid
            }
            var res = String.withCapacity(v.size)
            res += firstValid
            res += REPLACEMENT
            for (chunk1 in iter) {
                res += chunk1.valid()
                if (!chunk1.invalid().isEmpty()) {
                    res += REPLACEMENT
                }
            }
            return res
        }

        /**
         * Converts a [Vec.u8] to a `String`, substituting invalid UTF-8
         * sequences with replacement characters.
         *
         * See [fromUtf8Lossy] for more details.
         *
         * [fromUtf8Lossy]: String.fromUtf8Lossy
         *
         * Note that this function does not guarantee reuse of the original `List`
         * allocation.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // some bytes, in a vector
         * val sparkleHeart = mutableListOf(240, 159, 146, 150)
         * val sparkleHeart1 = String.fromUtf8LossyOwned(sparkleHeart)
         * check("💖" == sparkleHeart1)
         * ```
         *
         * Incorrect bytes:
         *
         * ```kotlin
         * // some invalid bytes
         * val input: List<UByte> = "Hello ðWorld".encodeToByteArray().into()
         * val output = String.fromUtf8LossyOwned(input)
         * check("Hello �World" == output)
         * ```
         */
        public fun fromUtf8LossyOwned(v: List<UByte>): String =
            when (val matchVal = String.fromUtf8Lossy(v)) {
                is Cow.Owned<*, *> -> {
                    val (string) = matchVal
                    string
                }
                else -> {
                    // SAFETY: `String.fromUtf8Lossy`s contract ensures that if
                    // it returns a `Cow.Borrowed`, it is a valid UTF-8 string.
                    // Otherwise, it returns a new allocation of an owned `String`, with
                    // replacement characters for invalid sequences, which is returned
                    // above.
                    String.fromUtf8Unchecked(v)
                }
            }

        /**
         * Decode a native endian UTF-16–encoded vector `v` into a `String`,
         * returning [Err] if `v` contains any invalid data.
         *
         * # Examples
         *
         * ```kotlin
         * // 𝄞music
         * val v = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0x0073, 0x0069, 0x0063)
         * check("𝄞music" == String.fromUtf16(v).unwrap())
         * // 𝄞mu<invalid>ic
         * val v1 = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0xD800, 0x0069, 0x0063)
         * check(String.fromUtf16(v1).isErr())
         * ```
         */
        public fun fromUtf16(v: List<UShort>): Result<String, FromUtf16Error> = RustAllocString.fromUtf16Units(v.iter().cloned(), v.size)

        // This isn't done via collect::<Result<_, _>>() for performance reasons.
        // Note: the function can be simplified again when #48994 is closed.

        /**
         * Decodes an iterator of UTF-16 code units into a `String`, returning
         * [Err] on the first lone surrogate. `capacity` should be the number of
         * code units, which is used to preallocate the output buffer.
         */
        public fun fromUtf16Units(units: Any, capacity: Int): Result<String, FromUtf16Error> {
            var ret = String.withCapacity(capacity)
            for (c in Char.decodeUtf16(units)) {
                val c1 = when (val tryResult = c) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<String, FromUtf16Error>(FromUtf16Error(kind = FromUtf16ErrorKind.LoneSurrogate)) }
                ret += c1
            }
            return Result.Ok<String, FromUtf16Error>(ret)
        }

        /**
         * Decode a native endian UTF-16–encoded slice `v` into a `String`,
         * replacing invalid data with [the replacement character (`U + FFFD`)][U+FFFD].
         *
         * Unlike [fromUtf8Lossy] which returns a [Cow.a.str],
         * `fromUtf16Lossy` returns a `String` since the UTF-16 to UTF-8
         * conversion requires a memory allocation.
         *
         * [fromUtf8Lossy]: String.fromUtf8Lossy
         * [U+FFFD]: char.ReplacementCharacter
         *
         * # Examples
         *
         * ```kotlin
         * // 𝄞mus<invalid>ic<invalid>
         * val v = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0x0073, 0xDD1E, 0x0069, 0x0063, 0xD834)
         * check("𝄞mus�ic�" == String.fromUtf16Lossy(v))
         * ```
         */
        public fun fromUtf16Lossy(v: List<UShort>): String = Char.decodeUtf16(v.iter().cloned()).map({ r -> r.unwrapOr(Char.REPLACEMENT_CHARACTER) }).joinToString("")

        /**
         * Decode a UTF-16LE–encoded vector `v` into a `String`,
         * returning [Err] if `v` contains any invalid data.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // 𝄞music
         * val v = listOf(0x34, 0xD8, 0x1E, 0xDD, 0x6d, 0x00, 0x75, 0x00, 0x73, 0x00, 0x69, 0x00, 0x63, 0x00)
         * check("𝄞music" == String.fromUtf16le(v).unwrap())
         * // 𝄞mu<invalid>ic
         * val v1 = listOf(0x34, 0xD8, 0x1E, 0xDD, 0x6d, 0x00, 0x75, 0x00, 0x00, 0xD8, 0x69, 0x00, 0x63, 0x00)
         * check(String.fromUtf16le(v1).isErr())
         * ```
         */
        public fun fromUtf16le(v: List<UByte>): Result<String, FromUtf16Error> {
            val (chunks, _) = v.asChunks() ?: return Result.Err<String, FromUtf16Error>(FromUtf16Error(kind = FromUtf16ErrorKind.OddBytes))
            // SAFETY: The aligned part of `v` can be transmuted into a valid u16 slice.
            return run {
                val matchVal = Pair(true, v.alignTo<UShort>())
                when {
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.isEmpty() -> {
                        val v1 = matchVal.second.second
                        RustAllocString.fromUtf16(v1)
                    }
                    else -> {
                        RustAllocString.fromUtf16Units(chunks.iter().copied().map(UShort::fromLeBytes), chunks.len())
                    }
                }
            }
        }

        /**
         * Decode a UTF-16LE–encoded slice `v` into a `String`, replacing
         * invalid data with [the replacement character (`U + FFFD`)][U+FFFD].
         *
         * Unlike [fromUtf8Lossy] which returns a [Cow.a.str],
         * `fromUtf16leLossy` returns a `String` since the UTF-16 to UTF-8
         * conversion requires a memory allocation.
         *
         * [fromUtf8Lossy]: String.fromUtf8Lossy
         * [U+FFFD]: char.ReplacementCharacter
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // 𝄞mus<invalid>ic<invalid>
         * val v = listOf(0x34, 0xD8, 0x1E, 0xDD, 0x6d, 0x00, 0x75, 0x00, 0x73, 0x00, 0x1E, 0xDD, 0x69, 0x00, 0x63, 0x00, 0x34, 0xD8)
         * check("𝄞mus�ic�" == String.fromUtf16leLossy(v))
         * ```
         */
        public fun fromUtf16leLossy(v: List<UByte>): String {
            // SAFETY: The aligned part of `v` can be transmuted into a valid u16 slice.
            return run {
                val matchVal = Pair(true, v.alignTo<UShort>())
                when {
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.isEmpty() -> {
                        val v1 = matchVal.second.second
                        RustAllocString.fromUtf16Lossy(v1)
                    }
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.size == 1 -> {
                        val v2 = matchVal.second.second
                        RustAllocString.fromUtf16Lossy(v2) + "�"
                    }
                    else -> {
                        val (chunks, remainder) = v.asChunks()
                        val string = Char.decodeUtf16(chunks.iter().copied().map(UShort::fromLeBytes)).map({ r -> r.unwrapOr(Char.REPLACEMENT_CHARACTER) }).joinToString("")
                        if (remainder.isEmpty()) {
                            string
                        } else {
                            string + "�"
                        }
                    }
                }
            }
        }

        /**
         * Decode a UTF-16BE–encoded vector `v` into a `String`,
         * returning [Err] if `v` contains any invalid data.
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // 𝄞music
         * val v = listOf(0xD8, 0x34, 0xDD, 0x1E, 0x00, 0x6d, 0x00, 0x75, 0x00, 0x73, 0x00, 0x69, 0x00, 0x63)
         * check("𝄞music" == String.fromUtf16be(v).unwrap())
         * // 𝄞mu<invalid>ic
         * val v1 = listOf(0xD8, 0x34, 0xDD, 0x1E, 0x00, 0x6d, 0x00, 0x75, 0xD8, 0x00, 0x00, 0x69, 0x00, 0x63)
         * check(String.fromUtf16be(v1).isErr())
         * ```
         */
        public fun fromUtf16be(v: List<UByte>): Result<String, FromUtf16Error> {
            val (chunks, _) = v.asChunks() ?: return Result.Err<String, FromUtf16Error>(FromUtf16Error(kind = FromUtf16ErrorKind.OddBytes))
            // SAFETY: The aligned part of `v` can be transmuted into a valid u16 slice.
            return run {
                val matchVal = Pair(false, v.alignTo<UShort>())
                when {
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.isEmpty() -> {
                        val v1 = matchVal.second.second
                        RustAllocString.fromUtf16(v1)
                    }
                    else -> {
                        RustAllocString.fromUtf16Units(chunks.iter().copied().map(UShort::fromBeBytes), chunks.len())
                    }
                }
            }
        }

        /**
         * Decode a UTF-16BE–encoded slice `v` into a `String`, replacing
         * invalid data with [the replacement character (`U + FFFD`)][U+FFFD].
         *
         * Unlike [fromUtf8Lossy] which returns a [Cow.a.str],
         * `fromUtf16leLossy` returns a `String` since the UTF-16 to UTF-8
         * conversion requires a memory allocation.
         *
         * [fromUtf8Lossy]: String.fromUtf8Lossy
         * [U+FFFD]: char.ReplacementCharacter
         *
         * # Examples
         *
         * Basic usage:
         *
         * ```kotlin
         * // 𝄞mus<invalid>ic<invalid>
         * val v = listOf(0xD8, 0x34, 0xDD, 0x1E, 0x00, 0x6d, 0x00, 0x75, 0x00, 0x73, 0xDD, 0x1E, 0x00, 0x69, 0x00, 0x63, 0xD8, 0x34)
         * check("𝄞mus�ic�" == String.fromUtf16beLossy(v))
         * ```
         */
        public fun fromUtf16beLossy(v: List<UByte>): String {
            // SAFETY: The aligned part of `v` can be transmuted into a valid u16 slice.
            return run {
                val matchVal = Pair(false, v.alignTo<UShort>())
                when {
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.isEmpty() -> {
                        val v1 = matchVal.second.second
                        RustAllocString.fromUtf16Lossy(v1)
                    }
                    matchVal.first == true && matchVal.second.first.isEmpty() && matchVal.second.third.size == 1 -> {
                        val v2 = matchVal.second.second
                        RustAllocString.fromUtf16Lossy(v2) + "�"
                    }
                    else -> {
                        val (chunks, remainder) = v.asChunks()
                        val string = Char.decodeUtf16(chunks.iter().copied().map(UShort::fromBeBytes)).map({ r -> r.unwrapOr(Char.REPLACEMENT_CHARACTER) }).joinToString("")
                        if (remainder.isEmpty()) {
                            string
                        } else {
                            string + "�"
                        }
                    }
                }
            }
        }

        /**
         * Creates a new `String` from a pointer, a length and a capacity.
         *
         * # Safety
         *
         * This is highly unchecked, due to the number of invariants that aren't
         * checked:
         *
         * * all safety requirements for [Vec.u8.fromRawParts].
         * * all safety requirements for [String.fromUtf8Unchecked].
         *
         * Violating these may cause problems like corrupting the allocator's
         * internal data structures. For example, it is normally **not** safe to
         * build a `String` from a pointer to a C `char` array containing UTF-8
         * _unless_ you are certain that array was originally allocated by the
         * Rust standard library's allocator.
         *
         * The ownership of `buf` is effectively transferred to the
         * `String` which may then deallocate, reallocate or change the
         * contents of memory pointed to by the pointer at will. Ensure
         * that nothing else uses the pointer after calling this
         * function.
         *
         * # Examples
         *
         * ```kotlin
         * run {
         *     val s = "hello"
         *     // Deconstruct the String into parts.
         *     val (ptr, len, capacity) = s.intoRawParts()
         *     val s1 = String.fromRawParts(ptr, len, capacity)
         *     check("hello" == s1)
         * }
         * ```
         */
        public fun fromRawParts(buf: MutPtr, length: Int, capacity: Int): String {
            // SAFETY: Upheld by caller.
            return String(vec = Vec.fromRawParts(buf, length, capacity))
        }

        /**
         * Converts a vector of bytes to a `String` without checking that the
         * string contains valid UTF-8.
         *
         * See the safe version, [fromUtf8], for more details.
         *
         * [fromUtf8]: String.fromUtf8
         *
         * # Safety
         *
         * This function is unchecked because it does not check that the bytes passed
         * to it are valid UTF-8. If this constraint is violated, it may cause
         * memory unsafety issues with future users of the `String`, as the rest of
         * the standard library assumes that `String`s are valid UTF-8.
         *
         * # Examples
         *
         * ```kotlin
         * // some bytes, in a vector
         * val sparkleHeart = mutableListOf(240, 159, 146, 150)
         * val sparkleHeart1 = String.fromUtf8Unchecked(sparkleHeart)
         * check("💖" == sparkleHeart1)
         * ```
         */
        public fun fromUtf8Unchecked(bytes: List<UByte>): String = String(vec = bytes)

        public fun <I : Iterable<Char>> fromIter(iter: I): String = iter.iterator().toStringSpec()

        public fun <I : Iterable<Char>> fromIterChar(iter: I): String {
            val buf = String.new()
            buf.extend(iter)
            return buf
        }

        public fun <I : Iterable<kotlin.String>> fromIterString(iter: I): String {
            val buf = String.new()
            buf.extend(iter)
            return buf
        }

        public fun <I : Iterable<String>> fromIterStringString(iter: I): String {
            val iterator = iter.iterator()
            // Because we're iterating over `String`s, we can avoid at least
            // one allocation by getting the first string from the iterator
            // and appending to it all the subsequent strings.
            return when (val matchVal = iterator.next()) {
                null -> String.new()
                else -> {
                    val buf = matchVal
                    buf.extend(iterator)
                    buf
                }
            }
        }

        public fun <I : Iterable<kotlin.String>> fromIterCowString(iter: I): String {
            val iterator = iter.iterator()
            // Because we're iterating over CoWs, we can (potentially) avoid at least
            // one allocation by getting the first item and appending to it all the
            // subsequent items.
            return when (val matchVal = iterator.next()) {
                null -> String.new()
                else -> {
                    val cow = matchVal
                    var buf = cow.intoOwned()
                    buf.extend(iterator)
                    buf
                }
            }
        }

        public fun <I : Iterable<Char>> fromIterCharChar(iter: I): RustAllocString {
            val buf = iter.iterator().map(Char::toU8).collect()
            // SAFETY: `buf` is guaranteed to be valid UTF-8 because the `core.ascii.Char` type
            // only contains ASCII values (0x00-0x7F), which are valid UTF-8.
            return String.fromUtf8Unchecked(buf)
        }

        /**
         * Creates an empty `String`.
         */
        public fun default(): String = String.new()

        public fun fromStr(s: kotlin.String): Result<String, Nothing> = Result.Ok<String, Nothing>(String.from(s))

        /**
         * Converts a `String` into a [String].
         *
         * The result is allocated on the heap.
         */
        public fun from(s: kotlin.String): String = s.toOwned()

        /**
         * Converts a `String` into a [String].
         *
         * The result is allocated on the heap.
         */
        public fun fromString(s: kotlin.String): String = s.toOwned()

        /**
         * Converts a `String` into a [String].
         *
         * This clones `s` and returns the clone.
         */
        public fun from(s: String): String = s.clone()

        // note: test pulls in std, which causes errors here

        /**
         * Converts the given boxed `String` slice to a [String].
         * It is notable that the `String` slice is owned.
         *
         * # Examples
         *
         * ```kotlin
         * val s1: String = "hello world"
         * val s2: String = s1.intoBoxedStr()
         * val s3: String = s2
         * check("hello world" == s3)
         * ```
         */
        public fun fromBoxString(s: kotlin.String): String = s.intoString()

        /**
         * Converts a clone-on-write string to an owned
         * instance of [String].
         *
         * This extracts the owned string,
         * clones the string if it is not already owned.
         *
         * # Example
         *
         * ```kotlin
         * // If the string is not owned...
         * val cow: String = "eggplant"
         * // It will allocate on the heap and copy the string.
         * val owned: String = cow
         * check(owned == "eggplant")
         * ```
         */
        public fun fromCowString(s: kotlin.String): String = s.intoOwned()

        /**
         * Converts the given [Vec.u8] into a  [String] if it contains valid UTF-8 data.
         *
         * # Examples
         *
         * ```kotlin
         * val s1 = "hello world".encodeToByteArray().toVec()
         * val v1 = String.tryFrom(s1).unwrap()
         * check(v1 == "hello world")
         * ```
         */
        public fun tryFrom(bytes: List<UByte>): Result<RustAllocString, FromUtf8Error> = RustAllocString.fromUtf8(bytes)

        /**
         * Allocates an owned [String] from a single character.
         *
         * # Example
         * ```kotlin
         * val c: Char = 'a'
         * val s: String = (c).toString()
         * check("a" == s)
         * ```
         */
        public fun from(c: Char): RustAllocString = c.toString()
    }
}

/**
 * A possible error value when converting a `String` from a UTF-8 byte vector.
 *
 * This type is the error type for the [fromUtf8] method on [String]. It
 * is designed in such a way to carefully avoid reallocations: the
 * [intoBytes] method will give back the byte vector that was used in the
 * conversion attempt.
 *
 * [fromUtf8]: String.fromUtf8
 * [intoBytes]: FromUtf8Error.intoBytes
 *
 * The [Utf8Error] type provided by [std.str] represents an error that may
 * occur when converting a slice of [u8]s to a [str]. In this sense, it's
 * an analogue to `FromUtf8Error`, and you can get one from a `FromUtf8Error`
 * through the [utf8Error] method.
 *
 * [Utf8Error]: str.Utf8Error "std.str.Utf8Error"
 * [str]: prim@str "&str"
 * [utf8Error]: FromUtf8Error.utf8Error
 *
 * # Examples
 *
 * ```kotlin
 * // some invalid bytes, in a vector
 * val bytes = mutableListOf(0, 159)
 * val value = String.fromUtf8(bytes)
 * check(value.isErr())
 * check(mutableListOf(0, 159) == value.unwrapErr().intoBytes())
 * ```
 */
public open class FromUtf8Error(
    public var bytes: List<UByte>,
    public var error: Utf8Error,
) {
    /**
     * Returns a slice of [u8]s bytes that were attempted to convert to a `String`.
     *
     * # Examples
     *
     * ```kotlin
     * // some invalid bytes, in a vector
     * val bytes = mutableListOf(0, 159)
     * val value = String.fromUtf8(bytes)
     * check(listOf(0, 159) == value.unwrapErr().encodeToByteArray().map { it.toUByte() })
     * ```
     */
    public open fun asBytes(): List<UByte> = this.bytes

    /**
     * Converts the bytes into a `String` lossily, substituting invalid UTF-8
     * sequences with replacement characters.
     *
     * See [String.fromUtf8Lossy] for more details on replacement of
     * invalid sequences, and [String.fromUtf8LossyOwned] for the
     * `String` function which corresponds to this function.
     *
     * This is useful in conjunction with [String.fromUtf8] when you need
     * to branch on whether the bytes are valid UTF-8, but still want to
     * recover a lossily converted `String` in the error case. Use
     * [String.fromUtf8LossyOwned] if you always need a lossily converted
     * `String`.
     *
     * Since the original [String.fromUtf8] error records where validation
     * stopped, this method does not need to re-check the already valid prefix
     * of the byte sequence.
     *
     * # Examples
     *
     * ```kotlin
     * // some invalid bytes
     * val input: List<UByte> = "Hello ðWorld".encodeToByteArray().into()
     * val (output1, hadInvalidUtf8) = run {
     *     val matchVal = String.fromUtf8(input)
     *     if (matchVal.isSuccess) {
     *         val output = matchVal.getOrThrow()
     *         Pair(output, false)
     *     } else {
     *         val error = matchVal.exceptionOrNull()!!
     *         // The bytes were not valid UTF-8, but we can still recover a string.
     *         Pair(error.intoUtf8Lossy(), true)
     *     }
     * }
     * check("Hello �World" == output1)
     * check(hadInvalidUtf8)
     * ```
     */
    public open fun intoUtf8Lossy(): String {
        val REPLACEMENT: kotlin.String = "�"
        var res = run {
            val v = ArrayList<UByte>(this.bytes.size)
            // `Utf8Error.validUpTo` returns the maximum index of validated
            // UTF-8 bytes. Copy the valid bytes into the output buffer.
            v.addAll(((this.bytes).subList(0, this.error.validUpTo())))
            // SAFETY: This is safe because the only bytes present in the buffer
            // were validated as UTF-8 by the call to `String.fromUtf8` which
            // produced this `FromUtf8Error`.
            String.fromUtf8Unchecked(v)
        }
        val iter = ((this.bytes).subList(this.error.validUpTo(), (this.bytes).size)).utf8Chunks()
        for (chunk in iter) {
            res += chunk.valid()
            if (!chunk.invalid().isEmpty()) {
                res += REPLACEMENT
            }
        }
        return res
    }

    /**
     * Returns the bytes that were attempted to convert to a `String`.
     *
     * This method is carefully constructed to avoid allocation. It will
     * consume the error, moving out the bytes, so that a copy of the bytes
     * does not need to be made.
     *
     * # Examples
     *
     * ```kotlin
     * // some invalid bytes, in a vector
     * val bytes = mutableListOf(0, 159)
     * val value = String.fromUtf8(bytes)
     * check(mutableListOf(0, 159) == value.unwrapErr().intoBytes())
     * ```
     */
    public open fun intoBytes(): List<UByte> = this.bytes

    /**
     * Fetch a `Utf8Error` to get more details about the conversion failure.
     *
     * The [Utf8Error] type provided by [std.str] represents an error that may
     * occur when converting a slice of [u8]s to a [str]. In this sense, it's
     * an analogue to `FromUtf8Error`. See its documentation for more details
     * on using it.
     *
     * [str]: prim@str "&str"
     *
     * # Examples
     *
     * ```kotlin
     * // some invalid bytes, in a vector
     * val bytes = mutableListOf(0, 159)
     * val error = String.fromUtf8(bytes).unwrapErr().utf8Error()
     * // the first byte is invalid here
     * check(1 == error.validUpTo())
     * ```
     */
    public open fun utf8Error(): Utf8Error = this.error

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.error).toString()); Result.Ok<Unit, Error>(Unit) }
}

/**
 * A possible error value when converting a `String` from a UTF-16 byte slice.
 *
 * This type is the error type for the [fromUtf16] method on [String].
 *
 * [fromUtf16]: String.fromUtf16
 *
 * # Examples
 *
 * ```kotlin
 * // 𝄞mu<invalid>ic
 * val v = listOf(0xD834, 0xDD1E, 0x006d, 0x0075, 0xD800, 0x0069, 0x0063)
 * check(String.fromUtf16(v).isErr())
 * ```
 */
public open class FromUtf16Error(
    public var kind: FromUtf16ErrorKind,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this.kind) {
            FromUtf16ErrorKind.LoneSurrogate -> "invalid utf-16: lone surrogate found"
            FromUtf16ErrorKind.OddBytes -> "invalid utf-16: odd number of bytes"
        }.fmt(f)
}

public sealed class FromUtf16ErrorKind {
    public data object LoneSurrogate : FromUtf16ErrorKind()
    public data object OddBytes : FromUtf16ErrorKind()

    public fun rustEquals(other: FromUtf16ErrorKind): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        LoneSurrogate -> 0
        OddBytes -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

public interface FromCharIterSpec : Iterator<Char> {
    public fun toStringSpec(): String

    public fun next(): Char?
}

public fun EscapeDefault.toStringSpec(): String {
    // go through the ToString specialization
    return this.toString()
}

public fun <A : Allocator, I : Iterable<kotlin.String>> fromIteratorFromIterString(iter: I): String {
    val buf = String.new()
    buf.extend(iter)
    return buf
}

public interface SpecExtendChars {
    public fun specExtendInto(s: String)
}

public fun <T : Iterable<Char>> T.specExtendInto(target: String) {
    val iterator = this.intoIter()
    val (lowerBound, _) = iterator.sizeHint()
    target.reserve(lowerBound)
    iterator.forEach({ c -> target += c })
}

public fun EscapeDefault.specExtendInto(target: String) {
    target.extendFromEscapeDefault(this)
}

public interface SpecExtendStr {
    public fun specExtendInto(s: String)
}

public fun List<kotlin.String>.specExtendInto(target: String) {
    target.pushStrSlice(this)
}

public fun List<kotlin.String>.specExtendIntoFromStringArrayn(target: String, n: Int) {
    target.pushStrSlice(this)
}

public fun <A : Allocator, I : Iterable<kotlin.String>> String.extend(iter: I) {
    iter.iterator().forEach({ s -> this += s })
}

public fun kotlin.String.eq(other: String): Boolean = PartialEq.eq(this, other)

public fun kotlin.String.ne(other: String): Boolean = PartialEq.ne(this, other)

public fun kotlin.String.eqString(other: String): Boolean = PartialEq.eq(this, other)

public fun kotlin.String.neString(other: String): Boolean = PartialEq.ne(this, other)

public fun kotlin.String.eq(other: kotlin.String): Boolean = PartialEq.eq(this, other)

public fun kotlin.String.ne(other: kotlin.String): Boolean = PartialEq.ne(this, other)

public fun kotlin.String.eqCowString(other: kotlin.String): Boolean = PartialEq.eq(this, other)

public fun kotlin.String.neCowString(other: kotlin.String): Boolean = PartialEq.ne(this, other)

public fun kotlin.String.eqString(other: kotlin.String): Boolean = PartialEq.eq(this, other)

public fun kotlin.String.neString(other: kotlin.String): Boolean = PartialEq.ne(this, other)

public fun <Output, I> String.index(index: I): Output where I : SliceIndex<kotlin.String, Output> = index.index(this)

public fun <Output, I> String.indexMut(index: I): Output where I : SliceIndex<kotlin.String, Output> = index.indexMut(this.asMutStr())

/**
 * A type alias for [!].
 *
 * This alias exists for backwards compatibility, and may be eventually deprecated.
 */
public typealias ParseError = Nothing

/**
 * A trait for converting a value to a `String`.
 *
 * This trait is automatically implemented for any type which implements the
 * [Display] trait. As such, `ToString` shouldn't be implemented directly:
 * [Display] should be implemented instead, and you get the `ToString`
 * implementation for free.
 *
 * [Display]: fmt.Display
 */
public interface ToString {
    /**
     * Converts the given value to a `String`.
     *
     * # Examples
     *
     * ```kotlin
     * val i = 5
     * val five = "5"
     * check(five == i.toString())
     * ```
     */
    public fun toString(): String
}

/**
 * # Panics
 *
 * In this implementation, the `toString` method panics
 * if the `Display` implementation returns an error.
 * This indicates an incorrect `Display` implementation
 * since `fmt.Write for String` never returns an error itself.
 */
public fun <T> T.toString(specToStringTFn: (T) -> String): String = specToStringTFn(this)

public interface SpecToString {
    public fun specToString(): String
}

// A common guideline is to not inline generic functions. However,
// removing `@inline` from this method causes non-negligible regressions.
// See <https://github.com/rust-lang/rust/pull/74852>, the last attempt
// to try to remove it.
public fun <T> T.specToString(): String {
    val buf = String.new()
    val formatter = Formatter.new(buf, FormattingOptions.new())
    // Bypass formatArgs() to avoid writeStr with zero-length strs
    formatter.append((this).toString()).expect("a Display implementation returned an error unexpectedly")
    return buf
}

public fun Char.specToStringChar(): String = this.asStr().toOwned()

public fun Boolean.specToString(): String =
    String.from(if (this) {
        "true"
    } else {
        "false"
    })

public fun Byte.specToString(): String {
    val SIZE: Int = Byte.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var out: Any?
    if (this < 0.toByte()) {
        out = String.withCapacity(SIZE + 1)
        out.push('-')
    } else {
        out = String.withCapacity(SIZE)
    }
    run {
        out.pushStr(this.unsignedAbs().fmt(buf))
    }
    return out
}

public fun UByte.specToString(): String {
    val SIZE: Int = UByte.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    return this.fmt(buf).toString()
}

public fun Short.specToString(): String {
    val SIZE: Int = Short.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var out: Any?
    if (this < 0.toShort()) {
        out = String.withCapacity(SIZE + 1)
        out.push('-')
    } else {
        out = String.withCapacity(SIZE)
    }
    run {
        out.pushStr(this.unsignedAbs().fmt(buf))
    }
    return out
}

public fun UShort.specToString(): String {
    val SIZE: Int = UShort.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    return this.fmt(buf).toString()
}

public fun Int.specToString(): String {
    val SIZE: Int = Int.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var out: Any?
    if (this < 0) {
        out = String.withCapacity(SIZE + 1)
        out.push('-')
    } else {
        out = String.withCapacity(SIZE)
    }
    run {
        out.pushStr(this.unsignedAbs().fmt(buf))
    }
    return out
}

public fun UInt.specToString(): String {
    val SIZE: Int = UInt.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    return this.fmt(buf).toString()
}

public fun Long.specToString(): String {
    val SIZE: Int = Long.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var out: Any?
    if (this < 0L) {
        out = String.withCapacity(SIZE + 1)
        out.push('-')
    } else {
        out = String.withCapacity(SIZE)
    }
    run {
        out.pushStr(this.unsignedAbs().fmt(buf))
    }
    return out
}

public fun ULong.specToString(): String {
    val SIZE: Int = ULong.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    return this.fmt(buf).toString()
}

public fun I128.specToString(): String {
    val SIZE: Int = I128.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var out: Any?
    if (this < I128(0x0L, 0x0uL)) {
        out = String.withCapacity(SIZE + 1)
        out.push('-')
    } else {
        out = String.withCapacity(SIZE)
    }
    run {
        out.pushStr(this.unsignedAbs().fmt(buf))
    }
    return out
}

public fun U128.specToString(): String {
    val SIZE: Int = U128.MAX_VALUE.ilog10().toInt() + 1
    val buf = MutableList((SIZE).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    return this.fmt(buf).toString()
}

public fun kotlin.String.specToString(): String {
    val s: kotlin.String = this
    return String.from(s)
}

// Generic/generated code can sometimes have multiple, nested references
// for strings, including `&& & String`s that would never be written
// by hand.

public fun Arguments.specToString(): String = format(this)

public fun EscapeDefault.specToString(): String {
    val s = String.new()
    s.extendFromEscapeDefault(this.clone())
    return s
}

// adapted from core.escape.escapeUnicode, but it's left-aligned instead of right-aligned.
public fun escapeUnicodeInto(output: List<MaybeUninit<Char>>, c: Char): Int {
    val HEX_DIGITS: List<Char> = "0123456789abcdef".encodeToByteArray().asAscii().unwrap()
    val c1 = c.toUInt()
    // OR-ing `1` ensures that for `c == 0` the code computes that
    // one digit should be printed.
    val prefixSkip = (c1 or 1u).leadingZeros().toInt() / 4 - 2
    // Always write all digits, but for shorter representations the unused
    // digits get overwritten by later writes.
    output[3.saturatingSub(prefixSkip)].write(HEX_DIGITS[((c1 shr 20) and 15u).toInt()])
    output[4.saturatingSub(prefixSkip)].write(HEX_DIGITS[((c1 shr 16) and 15u).toInt()])
    output[5.saturatingSub(prefixSkip)].write(HEX_DIGITS[((c1 shr 12) and 15u).toInt()])
    output[6.saturatingSub(prefixSkip)].write(HEX_DIGITS[((c1 shr 8) and 15u).toInt()])
    output[7.saturatingSub(prefixSkip)].write(HEX_DIGITS[((c1 shr 4) and 15u).toInt()])
    output[8.saturatingSub(prefixSkip)].write(HEX_DIGITS[(c1 and 15u).toInt()])
    output[0].write(Char.ReverseSolidus)
    output[1].write(Char.SmallU)
    output[2].write(Char.LeftCurlyBracket)
    output[9 - prefixSkip].write(Char.RightCurlyBracket)
    return 10 - prefixSkip
}

/**
 * Converts the given [String] to a boxed `String` slice that is owned.
 *
 * # Examples
 *
 * ```kotlin
 * val s1: String = "hello world"
 * val s2: String = Holder.from(s1)
 * val s3: String = s2
 * check("hello world" == s3)
 * ```
 */
public fun kotlin.String.Companion.from(s: String): kotlin.String = s.intoBoxedStr()

/**
 * Converts a string slice into a [Borrowed] variant.
 * No heap allocation is performed, and the string
 * is not copied.
 *
 * # Example
 *
 * ```kotlin
 * check(Cow.from("eggplant") == "eggplant")
 * ```
 */
public fun kotlin.String.Companion.from(s: kotlin.String): kotlin.String = s

public fun <I : Iterable<Char>> fromIteratorFromIterCowString(it: I): kotlin.String = FromIterator.fromIter(it)

/**
 * Converts the given [String] to a vector [Vec] that holds values of type [u8].
 *
 * # Examples
 *
 * ```kotlin
 * val s1 = "hello world"
 * val v1 = s1.toMutableList()
 * for (b in v1) {
 *     println("$b")
 * }
 * ```
 */
public fun fromVecUByte(string: String): List<UByte> = string.intoBytes()

/**
 * An iterator over the [char]s of a string.
 *
 * This struct is created by the [intoChars] method on [String].
 * See its documentation for more.
 *
 * [char]: prim@char
 * [intoChars]: String.intoChars
 */
public open class IntoChars(
    public var bytes: ArrayDeque<UByte>,
) : FromCharIterSpec, Iterable<Char> {
    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@IntoChars.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "IntoChars").field(this.asStr()).finish(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * # Examples
     *
     * ```kotlin
     * var chars = "abc".intoChars()
     * check(chars == "abc")
     * chars.next()
     * check(chars == "bc")
     * chars.next()
     * chars.next()
     * check(chars == "")
     * ```
     */
    public open fun asStr(): kotlin.String {
        // SAFETY: `bytes` is a valid UTF-8 string.
        return String.fromUtf8Unchecked(this.bytes.toList())
    }

    /**
     * Consumes the `IntoChars`, returning the remaining string.
     *
     * # Examples
     *
     * ```kotlin
     * val chars = "abc".intoChars()
     * check(chars.intoString() == "abc")
     * var chars1 = "def".intoChars()
     * chars1.next()
     * check(chars1.intoString() == "ef")
     * ```
     */
    public open fun intoString(): String {
        // SAFETY: `bytes` are kept in UTF-8 form, only removing whole `char`s at a time.
        return String.fromUtf8Unchecked(this.bytes.toMutableList())
    }

    public open fun iter(): CharIndices = this.asStr().charIndices()

    public override fun next(): Char? {
        val iter = this.iter()
        return when (val matchVal = iter.next()) {
            null -> null
            else -> {
                val ch = matchVal.second
                val offset = iter.offset()
                // `offset` is a valid index.
                this.bytes.advanceBy(offset)
                ch
            }
        }
    }

    public open fun count(): Int = this.iter().count()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter().sizeHint()

    public open fun last(): Char? = this.nextBack()

    public open fun nextBack(): Char? {
        val len = this.asStr().len()
        val iter = this.iter()
        return when (val matchVal = iter.nextBack()) {
            null -> null
            else -> {
                val idx = matchVal.first
                val ch = matchVal.second
                // `idx` is a valid index.
                this.bytes.advanceBackBy(len - idx)
                ch
            }
        }
    }

    public override fun toStringSpec(): String {
        val buf = String.withCapacity(this.sizeHint().first)
        buf.extend(this)
        return buf
    }
}

/**
 * A draining iterator for `String`.
 *
 * This struct is created by the [drain] method on [String]. See its
 * documentation for more.
 *
 * [drain]: String.drain
 */
public open class Drain internal constructor(
    stringCtorArg: MutPtr,
    /**
     * Start of part to remove
     */
    public var start: Int,
    /**
     * End of part to remove
     */
    public var end: Int,
    /**
     * Current remaining range to remove
     */
    public var iter: Chars,
) : AsRef<kotlin.String>, FromCharIterSpec, Iterable<Char> {
    /**
     * Will be used as &a mut String in the destructor
     */
    public var string: MutPtr = stringCtorArg
        internal set

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "Drain").field(this.asStr()).finish(); Result.Ok<Unit, Error>(Unit) }

    public open fun drop() {
        // Use Vec.drain. "Reaffirm" the bounds checks to avoid
        // panic code being inserted again.
        // SAFETY: We only use the returned Vec to call drain.
        val selfVec = ((UnresolvedTypeLayout<String>()).readNative((this.string).rawAddress).asMutVec()).get()
        if (this.start <= this.end && this.end <= selfVec.size) {
            selfVec.drain(this.start until this.end)
        }
    }

    /**
     * Returns the remaining (sub)string of this iterator as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * var s = "abc"
     * var drain = s.drain(0 until 0)
     * check(drain == "abc")
     * drain.next()!!
     * check(drain == "bc")
     * ```
     */
    public open fun asStr(): kotlin.String = this.iter.asStr()

    public open fun asRef(): kotlin.String = this.asStr()

    public open fun asRefUByteSlice(): List<UByte> = this.asStr().asBytes()

    public override fun next(): Char? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun last(): Char? = this.nextBack()

    public open fun nextBack(): Char? = this.iter.nextBack()

    public override fun toStringSpec(): String {
        val buf = String.withCapacity(this.sizeHint().first)
        buf.extend(this)
        return buf
    }

    public companion object {
        public operator fun invoke(
            string: MutPtr,
            start: Int,
            end: Int,
            iter: Chars,
        ): Drain = Drain(
            stringCtorArg = string,
            start = start,
            end = end,
            iter = iter,
        )
    }
}
