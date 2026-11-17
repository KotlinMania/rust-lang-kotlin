// port-lint: source option.rs
package io.github.kotlinmania.rustlang.core.option

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.discriminantValue
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.marker.StructuralPartialEq
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.deref.Deref
import io.github.kotlinmania.rustlang.core.ops.deref.DerefMut
import io.github.kotlinmania.rustlang.core.ops.deref.derefMut
import io.github.kotlinmania.rustlang.core.ops.trytrait.FromResidual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ops.trytrait.Yeet
import io.github.kotlinmania.rustlang.core.panicking.panic
import io.github.kotlinmania.rustlang.core.panicking.panicDisplay
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.result.Result

// Optional values.
//
// Type [Option] represents an optional value: every [Option]
// is either [Some] and contains a value, or [None], and
// does not. [Option] types are very common in Rust code, as
// they have a number of uses:
//
// * Initial values
// * Return values for functions that are not defined
//   over their entire input range (partial functions)
// * Return value for otherwise reporting simple errors, where [None] is
//   returned on error
// * Optional struct fields
// * Struct fields that can be loaned or "taken"
// * Optional function arguments
// * Nullable pointers
// * Swapping things out of difficult situations
//
// [Option]s are commonly paired with pattern matching to query the presence
// of a value and take action, always accounting for the [None] case.
//
// ```kotlin
// fun divide(numerator: Double, denominator: Double): Double? {
//     return if (denominator == 0.0) {
//         null
//     } else {
//         numerator / denominator
//     }
// }
// // The return value of the function is an option
// val result = divide(2.0, 3.0)
// // Pattern match to retrieve the value
// when (result) {
//     // The division was invalid
//     null -> println("Cannot divide by 0")
//     // The division was valid
//     else -> {
//         val x = result
//         println("Result: $x")
//     }
// }
// ```
//
// # Options and pointers ("nullable" pointers)
//
// Rust's pointer types must always point to a valid location; there are
// no "null" references. Instead, Rust has *optional* pointers, like
// the optional owned box, <code>[Option]<[Holder\<T>]></code>.
//
// [Holder\<T>]: ../../std/boxed/struct.Holder.html
//
// The following example uses [Option] to create an optional box of
// [i32]. Notice that in order to use the inner [i32] value, the
// `checkOptional` function first needs to use pattern matching to
// determine whether the box has a value (i.e., it is [`Some`][Some]) or
// not ([None]).
//
// ```kotlin
// fun checkOptional(optional: Int?) {
//     when (optional) {
//         null -> println("has no value")
//         else -> {
//             val p = optional
//             println("has value $p")
//         }
//     }
// }
// val optional = null
// checkOptional(optional)
// val optional1 = 9000
// checkOptional(optional1)
// ```
//
// # The question mark operator, `?`
//
// Similar to the [Result] type, when writing code that calls many functions that return the
// [Option] type, handling `Some`/`None` can be tedious. The question mark
// operator, [?], hides some of the boilerplate of propagating values
// up the call stack.
//
// It replaces this:
//
// ```kotlin
// fun addLastNumbers(stack: List<Int>): Int? {
//     val a = stack.pop()
//     val b = stack.pop()
//     return run {
//         val matchVal = Pair(a, b)
//         when {
//             matchVal.first != null && matchVal.second != null -> {
//                 val x = matchVal.first
//                 val y = matchVal.second
//                 x + y
//             }
//             else -> null
//         }
//     }
// }
// ```
//
// With this:
//
// ```kotlin
// fun addLastNumbers(stack: List<Int>): Int? {
//     return (stack.pop() ?: return null) + (stack.pop() ?: return null)
// }
// ```
//
// *It's much nicer!*
//
// Ending the expression with [?] will result in the [Some]s unwrapped value, unless the
// result is [None], in which case [None] is returned early from the enclosing function.
//
// [?] can be used in functions that return [Option] because of the
// early return of [None] that it provides.
//
// [Some]: Some
// [None]: None
//
// # Representation
//
// Rust guarantees to optimize the following types `T` such that [Option.T]
// has the same size, alignment, and [function call ABI] as `T`. It is
// therefore sound, when `T` is one of these types, to reinterpret a value `t` of
// type `T` to type `Option<T>` (producing the value `Some(t)`) and to
// reinterpret a value `Some(t)` of type `Option<T>` to type `T` (producing the
// value `t`).
//
// In some of these cases, Rust further guarantees the following:
// - `reinterpret<_, Option<T>>([0u8; sizeOf<T>()])` is sound and produces
//   `Option<T>.None`
// - `reinterpret<_, [UByte; sizeOf<T>()]>(Option<T>.None)` is sound and produces
//   `[0u8; sizeOf<T>()]`
//
// These cases are identified by the second column:
//
// | `T`                                                                 | Transmuting between `[0u8; sizeOf<T>()]` and `Option<T>.None` sound? |
// |---------------------------------------------------------------------|----------------------------------------------------------------------------|
// | [Box.U] (specifically, only `Holder<U, Global>`)                    | when `U: Sized`                                                            |
// | `U`                                                                | when `U: Sized`                                                            |
// | `U`                                                            | when `U: Sized`                                                            |
// | `fun`, `extern "C" fun`[^externFn]                                   | always                                                                     |
// | [num.NonZero]                                                   | always                                                                     |
// | [ptr.NonNull.U]                                                 | when `U: Sized`                                                            |
// | `@repr(transparent)` struct around one of the types in this list. | when it holds for the inner type                                           |
//
// [^externFn]: this remains true for `unchecked` variants, any argument/return types, and any other ABI: `[unchecked] extern "abi" fun` (_e.g._, `extern "system" fun`)
//
// Under some conditions the above types `T` are also null pointer optimized when wrapped in a [`Result`][resultRepr].
//
// [Box.U]: ../../std/boxed/struct.Holder.html
// [function call ABI]: ../primitive.fn.html#abi-compatibility
//
// This is called the "null pointer optimization" or NPO.
//
// It is further guaranteed that, for the cases above, one can
// [mem.transmute] from all valid values of `T` to `Option<T>` and
// from `Some<T>(_)` to `T` (but transmuting `None<T>` to `T`
// is undefined behavior).
//
// # Method overview
//
// In addition to working with pattern matching, [Option] provides a wide
// variety of different methods.
//
// ## Querying the variant
//
// The [isSome] and [isNone] methods return [trueVal] if the [Option]
// is [Some] or [None], respectively.
//
// The [isSomeAnd] and [isNoneOr] methods apply the provided function
// to the contents of the [Option] to produce a boolean value.
// If this is [None] then a default result is returned instead without executing the function.
//
// [isNone]: Option.isNone
// [isSome]: Option.isSome
// [isSomeAnd]: Option.isSomeAnd
// [isNoneOr]: Option.isNoneOr
//
// ## Adapters for working with references
//
// * [asRef] converts from <code>[&][][Option]\<T></code> to <code>[Option]<[&]T></code>
// * [asMut] converts from <code>[&mut] [Option]\<T></code> to <code>[Option]<[&mut] T></code>
// * [asDeref] converts from <code>[&][][Option]\<T></code> to
//   <code>[Option]<[&]T::[Target]></code>
// * [asDerefMut] converts from <code>[&mut] [Option]\<T></code> to
//   <code>[Option]<[&mut] T::[Target]></code>
// * [asPinRef] converts from <code>[Pin]<[&][][Option]\<T>></code> to
//   <code>[Option]<[Pin]<[&]T>></code>
// * [asPinMut] converts from <code>[Pin]<[&mut] [Option]\<T>></code> to
//   <code>[Option]<[Pin]<[&mut] T>></code>
// * [asSlice] returns a one-element slice of the contained value, if any.
//   If this is [None], an empty slice is returned.
// * [asMutSlice] returns a mutable one-element slice of the contained value, if any.
//   If this is [None], an empty slice is returned.
//
// [&]: reference "shared reference"
// [&mut]: reference "mutable reference"
// [Target]: Deref.Target "ops.Deref.Target"
// [asDeref]: Option.asDeref
// [asDerefMut]: Option.asDerefMut
// [asMut]: Option.asMut
// [asPinMut]: Option.asPinMut
// [asPinRef]: Option.asPinRef
// [asRef]: Option.asRef
// [asSlice]: Option.asSlice
// [asMutSlice]: Option.asMutSlice
//
// ## Extracting the contained value
//
// These methods extract the contained value in an [Option.T] when it
// is the [Some] variant. If the [Option] is [None]:
//
// * [expect] panics with a provided custom message
// * [unwrap] panics with a generic message
// * [unwrapOr] returns the provided default value
// * [unwrapOrDefault] returns the default value of the type `T`
//   (which must implement the [Default] trait)
// * [unwrapOrElse] returns the result of evaluating the provided
//   function
// * [unwrapUnchecked] produces *[undefined behavior]*
//
// [expect]: Option.expect
// [unwrap]: Option.unwrap
// [unwrapOr]: Option.unwrapOr
// [unwrapOrDefault]: Option.unwrapOrDefault
// [unwrapOrElse]: Option.unwrapOrElse
// [unwrapUnchecked]: Option.unwrapUnchecked
// [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
//
// ## Transforming contained values
//
// These methods transform [Option] to [Result]:
//
// * [okOr] transforms [Some] to [Ok], and [None] to
//   [Err] using the provided default `err` value
// * [okOrElse] transforms [Some] to [Ok], and [None] to
//   a value of [Err] using the provided function
// * [transpose] transposes an [Option] of a [Result] into a
//   [Result] of an [Option]
//
// [Err]: Err
// [Ok]: Ok
// [Some]: Some
// [okOr]: Option.okOr
// [okOrElse]: Option.okOrElse
// [transpose]: Option.transpose
//
// These methods transform the [Some] variant:
//
// * [filter] calls the provided predicate function on the contained
//   value `t` if the [Option] is [Some], and returns [Some]
//   if the function returns `true`; otherwise, returns [None]
// * [flatten] removes one level of nesting from an [Option.Option.T]
// * [inspect] method takes ownership of the [Option] and applies
//   the provided function to the contained value by reference if [Some]
// * [map] transforms [Option.T] to [Option.U] by applying the
//   provided function to the contained value of [Some] and leaving
//   [None] values unchanged
//
// [Some]: Some
// [filter]: Option.filter
// [flatten]: Option.flatten
// [inspect]: Option.inspect
// [map]: Option.map
//
// These methods transform [Option.T] to a value of a possibly
// different type `U`:
//
// * [mapOr] applies the provided function to the contained value of
//   [Some], or returns the provided default value if the [Option] is
//   [None]
// * [mapOrElse] applies the provided function to the contained value
//   of [Some], or returns the result of evaluating the provided
//   fallback function if the [Option] is [None]
//
// [mapOr]: Option.mapOr
// [mapOrElse]: Option.mapOrElse
//
// These methods combine the [Some] variants of two [Option] values:
//
// * [zip] returns [Some] if `self` is [Some] and the
//   provided [Option] value is [Some]; otherwise, returns [None]
// * [zipWith] calls the provided function `f` and returns
//   [Some] if `self` is [Some] and the provided
//   [Option] value is [Some]; otherwise, returns [None]
//
// [Some]: Some
// [Some]: Some
// [Some]: Some
// [Some]: Some
// [zip]: Option.zip
// [zipWith]: Option.zipWith
//
// ## Boolean operators
//
// These methods treat the [Option] as a boolean value, where [Some]
// acts like [trueVal] and [None] acts like [falseVal]. There are two
// categories of these methods: ones that take an [Option] as input, and
// ones that take a function as input (to be lazily evaluated).
//
// The [and], [or], and [xor] methods take another [Option] as
// input, and produce an [Option] as output. Only the [and] method can
// produce an [Option.U] value having a different inner type `U` than
// [Option.T].
//
// | method  | self      | input     | output    |
// |---------|-----------|-----------|-----------|
// | [and] | `None`    | (ignored) | `None`    |
// | [and] | `Some(x)` | `None`    | `None`    |
// | [and] | `Some(x)` | `Some(y)` | `Some(y)` |
// | [or]  | `None`    | `None`    | `None`    |
// | [or]  | `None`    | `Some(y)` | `Some(y)` |
// | [or]  | `Some(x)` | (ignored) | `Some(x)` |
// | [xor] | `None`    | `None`    | `None`    |
// | [xor] | `None`    | `Some(y)` | `Some(y)` |
// | [xor] | `Some(x)` | `None`    | `Some(x)` |
// | [xor] | `Some(x)` | `Some(y)` | `None`    |
//
// [and]: Option.and
// [or]: Option.or
// [xor]: Option.xor
//
// The [andThen] and [orElse] methods take a function as input, and
// only evaluate the function when they need to produce a new value. Only
// the [andThen] method can produce an [Option.U] value having a
// different inner type `U` than [Option.T].
//
// | method       | self      | function input | function result | output    |
// |--------------|-----------|----------------|-----------------|-----------|
// | [andThen] | `None`    | (not provided) | (not evaluated) | `None`    |
// | [andThen] | `Some(x)` | `x`            | `None`          | `None`    |
// | [andThen] | `Some(x)` | `x`            | `Some(y)`       | `Some(y)` |
// | [orElse]  | `None`    | (not provided) | `None`          | `None`    |
// | [orElse]  | `None`    | (not provided) | `Some(y)`       | `Some(y)` |
// | [orElse]  | `Some(x)` | (not provided) | (not evaluated) | `Some(x)` |
//
// [andThen]: Option.andThen
// [orElse]: Option.orElse
//
// This is an example of using methods like [andThen] and [or] in a
// pipeline of method calls. Early stages of the pipeline pass failure
// values ([None]) through unchanged, and continue processing on
// success values ([Some]). Toward the end, [or] substitutes an error
// message if it receives [None].
//
// ```kotlin
// @file:OptIn(ExperimentalUnsignedTypes.cls)
//
// public fun doctestEntry() {
//     var bt = mutableMapOf()
//     bt.put(20u.toUByte(), "foo")
//     bt.put(42u.toUByte(), "bar")
//     val res = ubyteArrayOf(0u.toUByte(), 1u, 11u, 200u, 22u).map({ x ->
//         // `checkedSub()` returns `None` on error
//         (x.checkedSub(1)?.let({ x1 -> x1.checkedMul(2) })?.let({ x1 -> bt.get(x1) }) ?: "error!")!!
//     }).collect<Vec>()
//     check(res == listOf("error!", "error!", "foo", "error!", "bar"))
// }
// ```
//
// ## Comparison operators
//
// If `T` implements [PartialOrd] then [Option.T] will derive its
// [PartialOrd] implementation.  With this order, [None] compares as
// less than any [Some], and two [Some] compare the same way as their
// contained values would in `T`.  If `T` also implements
// [Ord], then so does [Option.T].
//
// ```kotlin
// check(null < 0)
// check(0 < 1)
// ```
//
// ## Iterating over `Option`
//
// An [Option] can be iterated over. This can be helpful if you need an
// iterator that is conditionally empty. The iterator will either produce
// a single value (when the [Option] is [Some]), or produce no values
// (when the [Option] is [None]). For example, [intoIter] acts like
// [once] if the [Option] is [Some], and like [empty] if
// the [Option] is [None].
//
// [Some]: Some
//
// Iterators over [Option.T] come in three types:
//
// * [intoIter] consumes the [Option] and produces the contained
//   value
// * [iter] produces an immutable reference of type `T` to the
//   contained value
// * [iterMut] produces a mutable reference of type `T` to the
//   contained value
//
// [intoIter]: Option.intoIter
// [iter]: Option.iter
// [iterMut]: Option.iterMut
//
// An iterator over [Option] can be useful when chaining iterators, for
// example, to conditionally insert items. (It's not always necessary to
// explicitly call an iterator constructor: many [Iterator] methods that
// accept other iterators will also accept iterable types that implement
// [IntoIterator], which includes [Option].)
//
// ```kotlin
// val yep = 42
// val nope = null
// // chain() already calls intoIter(), so we don't have to do so
// val nums: List<Int> = (0 until 4).chain(yep).chain(4 until 8).toMutableList()
// check(nums == listOf(0, 1, 2, 3, 42, 4, 5, 6, 7))
// val nums1: List<Int> = (0 until 4).chain(nope).chain(4 until 8).toMutableList()
// check(nums1 == listOf(0, 1, 2, 3, 4, 5, 6, 7))
// ```
//
// One reason to chain iterators in this way is that a function returning
// `implementation Iterator` must have all possible return values be of the same
// concrete type. Chaining an iterated [Option] can help with that.
//
// ```kotlin
// fun makeIter(doInsert: Boolean): Any {
//     // Explicit returns to illustrate return types matching
//     return when (doInsert) {
//         true -> { return (0 until 4).chain(42).chain(4 until 8) }
//         false -> { return (0 until 4).chain(null).chain(4 until 8) }
//     }
// }
// println("${makeIter(true).collect<Vec>()}")
// println("${makeIter(false).collect<Vec>()}")
// ```
//
// If we try to do the same thing, but using [once] and [empty],
// we can't return `implementation Iterator` anymore because the concrete types of
// the return values differ.
//
// ```compile_fail,E0308
// # use std.iter::{empty, once};
// // This won't compile because all possible returns from the function
// // must have the same concrete type.
// fn makeIter(doInsert: bool) -> impl Iterator<Item = i32> {
//     // Explicit returns to illustrate return types not matching
//     match doInsert {
//         true => return (0..4).chain(once(42)).chain(4..8),
//         false => return (0..4).chain(empty()).chain(4..8),
//     }
// }
// ```
//
// ## Collecting into `Option`
//
// [Option] implements the [`FromIterator`][impl-FromIterator] trait,
// which allows an iterator over [Option] values to be collected into an
// [Option] of a collection of each contained value of the original
// [Option] values, or [None] if any of the elements was [None].
//
// [impl-FromIterator]: Option#impl-FromIterator%3COption%3CT%3E%3E-for-Option%3CV%3E
//
// ```kotlin
// val v = listOf(2, 4, null, 8)
// val res: Vec? = v.intoIter().collect()
// check(res == null)
// val v1 = listOf(2, 4, 8)
// val res1: Vec? = v1.intoIter().collect()
// check(res1 == mutableListOf(2, 4, 8))
// ```
//
// [Option] also implements the [`Product`][impl-Product] and
// [`Sum`][impl-Sum] traits, allowing an iterator over [Option] values
// to provide the [`product`][Iterator.product] and
// [`sum`][Iterator.sum] methods.
//
// [impl-Product]: Option#impl-Product%3COption%3CU%3E%3E-for-Option%3CT%3E
// [impl-Sum]: Option#impl-Sum%3COption%3CU%3E%3E-for-Option%3CT%3E
//
// ```kotlin
// val v = listOf(null, 1, 2, 3)
// val res: Int? = v.intoIter().sum()
// check(res == null)
// val v1 = listOf(1, 2, 21)
// val res1: Int? = v1.intoIter().product()
// check(res1 == 42)
// ```
//
// ## Modifying an [Option] in-place
//
// These methods return a mutable reference to the contained value of an
// [Option.T]:
//
// * [insert] inserts a value, dropping any old contents
// * [getOrInsert] gets the current value, inserting a provided
//   default value if it is [None]
// * [getOrInsertDefault] gets the current value, inserting the
//   default value of type `T` (which must implement [Default]) if it is
//   [None]
// * [getOrInsertWith] gets the current value, inserting a default
//   computed by the provided function if it is [None]
//
// [getOrInsert]: Option.getOrInsert
// [getOrInsertDefault]: Option.getOrInsertDefault
// [getOrInsertWith]: Option.getOrInsertWith
// [insert]: Option.insert
//
// These methods transfer ownership of the contained value of an
// [Option]:
//
// * [take] takes ownership of the contained value of an [Option], if
//   any, replacing the [Option] with [None]
// * [replace] takes ownership of the contained value of an [Option],
//   if any, replacing the [Option] with a [Some] containing the
//   provided value
//
// [replace]: Option.replace
// [take]: Option.take
//
// # Examples
//
// Basic pattern matching on [Option]:
//
// ```kotlin
// val msg = "howdy"
// // Take a reference to the contained string
// if (msg != null) {
//     val m = msg
//     println("$m")
// }
// // Remove the contained string, destroying the Option
// val unwrappedMsg = msg.unwrapOr("default message")
// ```
//
// Initialize a result to [None] before a loop:
//
// ```kotlin
// sealed class Kingdom {
//     public data class Plant(public var field0: UInt, public var field1: String) : Kingdom()
//     public data class Animal(public var field0: UInt, public var field1: String) : Kingdom()
// }
// // A list of data to search through.
// val allTheBigThings = listOf(Kingdom.Plant(250, "redwood"), Kingdom.Plant(230, "noble fir"), Kingdom.Plant(229, "sugar pine"), Kingdom.Animal(25, "blue whale"), Kingdom.Animal(19, "fin whale"), Kingdom.Animal(15, "north pacific right whale"))
// // We're going to search for the name of the biggest animal,
// // but to start with we've just got `None`.
// var nameOfBiggestAnimal = null
// var sizeOfBiggestAnimal = 0
// for (bigThing in allTheBigThings) {
//     when {
//         bigThing is Kingdom.Animal && bigThing.field0 > sizeOfBiggestAnimal -> {
//             val (size, name) = bigThing
//             // Now we've found the name of some big animal
//             sizeOfBiggestAnimal = size
//             nameOfBiggestAnimal = name
//         }
//         bigThing is Kingdom.Animal -> Unit
//         else -> Unit
//     }
// }
// when (nameOfBiggestAnimal) {
//     null -> println("there are no animals :(")
//     else -> {
//         val name1 = nameOfBiggestAnimal
//         println("the biggest animal is $name")
//     }
// }
// ```

public typealias TryType<T> = Option<T>

// PartialEq is manually implemented equivalently

/**
 * The `Option` type. See [the module level documentation](self) for more.
 */
public sealed class Option<T> : StructuralPartialEq, Try<Option<T>, T, Option<Nothing>>, FromResidual<Option<Nothing>, Option<T>> {
    /**
     * No value.
     */
    public class None<T>() : Option<T>()
    /**
     * Some value of type `T`.
     */
    public data class Some<T>(public var value: T) : Option<T>()

    // ////////////////////////////////////////////////////////////////////////
    // Type implementation
    // ////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////
    // Querying the contained values
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns `true` if the option is a [Some] value.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UInt? = 2u
     * check(x != null == true)
     * val x1: UInt? = null
     * check(x1 != null == false)
     * ```
     */
    public open fun isSome(): Boolean =
        when (this) {
            is Some<*> -> true
            else -> false
        }

    /**
     * Returns `true` if the option is a [Some] and the value inside of it matches a predicate.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UInt? = 2u
     * check(x.isSomeAnd({ x1 -> x1 > 1 }) == true)
     * val x1: UInt? = 0u
     * check(x1.isSomeAnd({ x2 -> x2 > 1 }) == false)
     * val x2: UInt? = null
     * check(x2.isSomeAnd({ x3 -> x3 > 1 }) == false)
     * val x3: String? = "ownership".toString()
     * check(x3.isSomeAnd({ x4 -> x4.len() > 1 }) == true)
     * println("still alive $x3")
     * ```
     */
    public open fun isSomeAnd(f: (T) -> Boolean): Boolean =
        when (this) {
            null -> false
            is Some<*> -> {
                val (x) = this as Some<T>
                f(x)
            }
        }

    /**
     * Returns `true` if the option is a [None] value.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UInt? = 2u
     * check(x == null == false)
     * val x1: UInt? = null
     * check(x1 == null == true)
     * ```
     */
    public open fun isNone(): Boolean = !this.isSome()

    /**
     * Returns `true` if the option is a [None] or the value inside of it matches a predicate.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UInt? = 2u
     * check(x.isNoneOr({ x1 -> x1 > 1 }) == true)
     * val x1: UInt? = 0u
     * check(x1.isNoneOr({ x2 -> x2 > 1 }) == false)
     * val x2: UInt? = null
     * check(x2.isNoneOr({ x3 -> x3 > 1 }) == true)
     * val x3: String? = "ownership".toString()
     * check(x3.isNoneOr({ x4 -> x4.len() > 1 }) == true)
     * println("still alive $x3")
     * ```
     */
    public open fun isNoneOr(f: (T) -> Boolean): Boolean =
        when (this) {
            null -> true
            is Some<*> -> {
                val (x) = this as Some<T>
                f(x)
            }
        }

    // ////////////////////////////////////////////////////////////////////
    // Adapter for working with references
    // ////////////////////////////////////////////////////////////////////

    /**
     * Converts from `Option<T>` to `Option<T>`.
     *
     * # Examples
     *
     * Calculates the length of an <code>Option<[String]></code> as an <code>Option<[Int]></code>
     * without moving the [String]. The [map] method takes the `self` argument by value,
     * consuming the original, so this technique uses `asRef` to first take an `Option` to a
     * reference to the value inside the original.
     *
     * [map]: Option.map
     * [String]: ../../std/string/struct.String.html "String"
     * [String]: ../../std/string/struct.String.html "String"
     *
     * ```kotlin
     * val text: String? = "Hello, world!".toString()
     * // First, cast `Option<String>` to `Option<String>` with `asRef`,
     * // then consume *that* with `map`, leaving `text` on the stack.
     * val textLength: Int? = text?.let({ s -> s.len() })
     * println("still can print text: $text")
     * ```
     */
    public open fun asRef(): Option<T> =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                Some(x)
            }
            null -> None
        }

    /**
     * Converts from `Option<T>` to `Option<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * var x = 2
     * when (val matchVal = x.asMut()) {
     *     null -> {}
     *     else -> {
     *         val v = matchVal
     *         v = 42
     *     }
     * }
     * check(x == 42)
     * ```
     */
    public open fun asMut(): Option<T> =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                Some(x)
            }
            null -> None
        }

    /**
     * Converts from <code>[Pin]<[&]Option\<T>></code> to <code>Option<[Pin]<[&]T>></code>.
     *
     * [&]: reference "shared reference"
     */
    public open fun asPinRef(): Option<Pin<T>> {
        // Note(const-Note): use `map` once that is possible
        return when (val matchVal = Pin.getRef(this).asRef()) {
            // SAFETY: `x` is guaranteed to be pinned because it comes from `self`
            // which is pinned.
            is Some<*> -> {
                val (x) = matchVal
                Some(Pin.newUnchecked(x))
            }
            null -> None
        }
    }

    /**
     * Converts from <code>[Pin]<[&mut] Option\<T>></code> to <code>Option<[Pin]<[&mut] T>></code>.
     *
     * [&mut]: reference "mutable reference"
     */
    public open fun asPinMut(): Option<Pin<T>> {
        // SAFETY: `getUncheckedMut` is never used to move the `Option` inside `self`.
        // `x` is guaranteed to be pinned because it comes from `self` which is pinned.
        return when (val matchVal = Pin.getUncheckedMut(this).asMut()) {
            is Some<*> -> {
                val (x) = matchVal
                Some(Pin.newUnchecked(x))
            }
            null -> None
        }
    }

    public open fun len(): Int {
        // Using the intrinsic avoids emitting a branch to get the 0 or 1.
        val discriminant: Int = discriminantValue(this)
        return discriminant
    }

    /**
     * Returns a slice of the contained value, if any. If this is `None`, an
     * empty slice is returned. This can be useful to have a single type of
     * iterator over an `Option` or slice.
     *
     * Note: Should you have an `Option<T>` and wish to get a slice of `T`,
     * you can unpack it via `opt.mapOr([], std.slice.fromRef)`.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1234.asSlice(), null.asSlice()) == listOf(listOf(1234), listOf()))
     * ```
     *
     * The inverse of this function is (discounting
     * borrowing) [`first`](slice.first):
     *
     * ```kotlin
     * for (i in listOf(1234u.toUShort(), null)) {
     *     check(i.asRef() == i.asSlice().first())
     * }
     * ```
     */
    public open fun asSlice(): List<T> {
        // SAFETY: When the `Option` is `Some`, we're using the actual pointer
        // to the payload, with a length of 1, so this is equivalent to
        // `slice.fromRef`, and thus is safe.
        // When the `Option` is `None`, the length used is 0, so to be safe it
        // just needs to be aligned, which it is because `self` is aligned and
        // the offset used is a multiple of alignment.
        //
        // Here we assume that `offsetOf` always returns an offset to an
        // in-bounds and correctly aligned position for a `T` (even if in the
        // `None` case it's just padding).
        return linearSliceView(ConstPtr((ConstPtr(((constPtrFromRustCast(this))).rawAddress + ((0).toLong()))).rawAddress), this.len().toInt(), UnresolvedTypeLayout<Option<T>>())
    }

    /**
     * Returns a mutable slice of the contained value, if any. If this is
     * `None`, an empty slice is returned. This can be useful to have a
     * single type of iterator over an `Option` or slice.
     *
     * Note: Should you have an `Option<T>` instead of a
     * `Option<T>`, which this method takes, you can obtain a mutable
     * slice via `opt.mapOr([], std.slice.fromMut)`.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1234.asMutSlice(), null.asMutSlice()) == listOf(listOf(1234), listOf()))
     * ```
     *
     * The result is a mutable slice of zero or one items that points into
     * our original `Option`:
     *
     * ```kotlin
     * var x = 1234
     * x.asMutSlice()[0] += 1
     * check(x == 1235)
     * ```
     *
     * The inverse of this method (discounting borrowing)
     * is [`firstMut`](slice.firstMut):
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * check(123.asMutSlice().firstMut() == RustMutableReference(allocateNativeCell(123, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
     * ```
     */
    public open fun asMutSlice(): MutableList<T> {
        // SAFETY: When the `Option` is `Some`, we're using the actual pointer
        // to the payload, with a length of 1, so this is equivalent to
        // `slice.fromMut`, and thus is safe.
        // When the `Option` is `None`, the length used is 0, so to be safe it
        // just needs to be aligned, which it is because `self` is aligned and
        // the offset used is a multiple of alignment.
        //
        // In the new version, the intrinsic creates a `* const T` from a
        // mutable reference  so it is safe to cast back to a mutable pointer
        // here. As with `asSlice`, the intrinsic always returns a pointer to
        // an in-bounds and correctly aligned position for a `T` (even if in
        // the `None` case it's just padding).
        return linearMutSliceView(MutPtr((MutPtr(((mutPtrFromRustCast(this))).rawAddress + ((0).toLong()))).rawAddress), this.len().toInt(), UnresolvedTypeLayout<Option<T>>())
    }

    // ////////////////////////////////////////////////////////////////////
    // Getting to contained values
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns the contained [Some] value, consuming the `self` value.
     *
     * # Panics
     *
     * Panics if the value is a [None] with a custom panic message provided by
     * `msg`.
     *
     * # Examples
     *
     * ```kotlin
     * val x = "value"
     * check(x.expect("fruits are healthy") == "value")
     * ```
     *
     * ```kotlin
     * val x: String? = null
     * (x ?: error("fruits are healthy"))
     * // panics with `fruits are healthy`
     * ```
     *
     * # Recommended Message Style
     *
     * We recommend that `expect` messages are used to describe the reason you
     * _expect_ the `Option` should be `Some`.
     *
     * ```kotlin
     * val item = (slice.get(0) ?: error("slice should not be empty"))
     * ```
     *
     * **Hint**: If you're having trouble remembering how to phrase expect
     * error messages remember to focus on the word "should" as in "env
     * variable should be set by blah" or "the given binary should be available
     * and executable by the current user".
     *
     * For more detail on expect message styles and the reasoning behind our
     * recommendation please refer to the section on ["Common Message
     * Styles"](../../std/error/index.html#common-message-styles) in the [`std.error`](../../std/error/index.html) module docs.
     */
    public open fun expect(msg: String): T =
        when (this) {
            is Some<*> -> {
                val (value) = this as Some<T>
                value
            }
            null -> expectFailed(msg)
        }

    /**
     * Returns the contained [Some] value, consuming the `self` value.
     *
     * Because this function may panic, its use is generally discouraged.
     * Panics are meant for unrecoverable errors, and
     * [may abort the entire program][panic-abort].
     *
     * Instead, prefer to use pattern matching and handle the [None]
     * case explicitly, or call [unwrapOr], [unwrapOrElse], or
     * [unwrapOrDefault]. In functions returning `Option`, you can use
     * [the `?` (try) operator][try-option].
     *
     * [panic-abort]: https://doc.rust-lang.org/book/ch09-01-unrecoverable-errors-with-panic.html
     * [try-option]: https://doc.rust-lang.org/book/ch09-02-recoverable-errors-with-result.html#where-the--operator-can-be-used
     * [unwrapOr]: Option.unwrapOr
     * [unwrapOrElse]: Option.unwrapOrElse
     * [unwrapOrDefault]: Option.unwrapOrDefault
     *
     * # Panics
     *
     * Panics if the self value equals [None].
     *
     * # Examples
     *
     * ```kotlin
     * val x = "air"
     * check(x.unwrap() == "air")
     * ```
     *
     * ```kotlin
     * val x: String? = null
     * check(x!! == "air")
     * // fails
     * ```
     */
    public open fun unwrap(): T =
        when (this) {
            is Some<*> -> {
                val (value) = this as Some<T>
                value
            }
            null -> unwrapFailed()
        }

    /**
     * Returns the contained [Some] value or a provided default.
     *
     * Arguments passed to `unwrapOr` are eagerly evaluated; if you are passing
     * the result of a function call, it is recommended to use [unwrapOrElse],
     * which is lazily evaluated.
     *
     * [unwrapOrElse]: Option.unwrapOrElse
     *
     * # Examples
     *
     * ```kotlin
     * check("car".unwrapOr("bike") == "car")
     * check(null.unwrapOr("bike") == "bike")
     * ```
     */
    public open fun unwrapOr(default: T): T =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                x
            }
            null -> default
        }

    /**
     * Returns the contained [Some] value or computes it from a closure.
     *
     * # Examples
     *
     * ```kotlin
     * val k = 10
     * check(4.unwrapOrElse({ 2 * k }) == 4)
     * check(null.unwrapOrElse({ 2 * k }) == 20)
     * ```
     */
    public open fun unwrapOrElse(f: () -> T): T =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                x
            }
            null -> f()
        }

    /**
     * Returns the contained [Some] value or a default.
     *
     * Consumes the `self` argument then, if [Some], returns the contained
     * value, otherwise if [None], returns the [default value] for that
     * type.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UInt? = null
     * val y: UInt? = 12u
     * check(x.unwrapOrDefault() == 0)
     * check(y.unwrapOrDefault() == 12)
     * ```
     *
     * [default value]: Default.default
     * [parse]: str.parse
     */
    public open fun unwrapOrDefault(defaultTFn: () -> T): T =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                x
            }
            null -> defaultTFn()
        }

    /**
     * Returns the contained [Some] value, consuming the `self` value,
     * without checking that the value is not [None].
     *
     * # Safety
     *
     * Calling this method on [None] is *[undefined behavior]*.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * val x = "air"
     * check(x.unwrapUnchecked() == "air")
     * ```
     *
     * ```kotlin
     * val x: String? = null
     * check(x.unwrapUnchecked() == "air")
     * // Undefined behavior!
     * ```
     */
    public open fun unwrapUnchecked(): T =
        when (this) {
            is Some<*> -> {
                val (value) = this as Some<T>
                value
            }
            // SAFETY: the safety contract must be upheld by the caller.
            null -> unreachableUnchecked()
        }

    // ////////////////////////////////////////////////////////////////////
    // Transforming contained values
    // ////////////////////////////////////////////////////////////////////

    /**
     * Maps an `Option<T>` to `Option<U>` by applying a function to a contained value (if `Some`) or returns `None` (if `None`).
     *
     * # Examples
     *
     * Calculates the length of an <code>Option<[String]></code> as an
     * <code>Option<[Int]></code>, consuming the original:
     *
     * [String]: ../../std/string/struct.String.html "String"
     * ```kotlin
     * val maybeSomeString = "Hello, World!"
     * // `Option.map` takes self *by value*, consuming `maybeSomeString`
     * val maybeSomeLen = maybeSomeString.map({ s -> s.len() })
     * check(maybeSomeLen == 13)
     * val x: String? = null
     * check(x?.let({ s -> s.len() }) == null)
     * ```
     */
    public open fun <U> map(f: (T) -> U): Option<U> =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                Some(f(x))
            }
            null -> None
        }

    /**
     * Calls a function with a reference to the contained value if [Some].
     *
     * Returns the original option.
     *
     * # Examples
     *
     * ```kotlin
     * val list = mutableListOf(1, 2, 3)
     * // prints "got: 2"
     * val x = list.getOrNull(1).inspect({ x -> println("got: $x") }).expect("list should be long enough")
     * // prints nothing
     * list.getOrNull(5).inspect({ x1 -> println("got: $x") })
     * ```
     */
    public open fun inspect(f: (T) -> Unit): Option<T> {
        if (this is Some<*>) {
            val (x) = this
            f(x)
        }
        return this
    }

    /**
     * Returns the provided default result (if none),
     * or applies a function to the contained value (if any).
     *
     * The `default` argument passed to `mapOr` is eagerly evaluated; if you are passing
     * the result of a function call, it is recommended to use [mapOrElse],
     * which lazily evaluates `default`.
     *
     * [mapOrElse]: Option.mapOrElse
     *
     * # Examples
     *
     * ```kotlin
     * val x = "foo"
     * check(x.mapOr(42, { v -> v.len() }) == 3)
     * val x1: String? = null
     * check((x1?.let({ v -> v.len() }) ?: 42) == 42)
     * ```
     */
    public open fun <U> mapOr(default: U, f: (T) -> U): U =
        when (this) {
            is Some<*> -> {
                val (t) = this as Some<T>
                f(t)
            }
            null -> default
        }

    /**
     * Computes a default function result (if none), or
     * applies a different function to the contained value (if any).
     *
     * # Basic examples
     *
     * ```kotlin
     * val k = 21
     * val x = "foo"
     * check(x.mapOrElse({ 2 * k }, { v -> v.len() }) == 3)
     * val x1: String? = null
     * check((x1?.let({ v -> v.len() }) ?: 2 * k) == 42)
     * ```
     *
     * # Handling a Result-based fallback
     *
     * A somewhat common occurrence when dealing with optional values
     * in combination with [Result.T.E] is the case where one wants to invoke
     * a fallible fallback if the option is not present.  This example
     * parses a command line argument (if present), or the contents of a file to
     * an integer.  However, unlike accessing the command line argument, reading
     * the file is fallible, so it must be wrapped with `Ok`.
     *
     * ```kotlin
     * val v: ULong = ((args().nth(1).mapOrElse({ readToString("/etc/someconfig.conf") }, { Result.success(it) }).getOrElse { return Result.failure(it) }).toULongOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse ULong"))).getOrElse { return Result.failure(it) }
     * ```
     */
    public open fun <U> mapOrElse(default: () -> U, f: (T) -> U): U =
        when (this) {
            is Some<*> -> {
                val (t) = this as Some<T>
                f(t)
            }
            null -> default()
        }

    /**
     * Maps an `Option<T>` to a `U` by applying function `f` to the contained
     * value if the option is [Some], otherwise if [None], returns the
     * [default value] for the type `U`.
     *
     * # Examples
     *
     * ```kotlin
     * val x: String? = "hi"
     * val y: String? = null
     * check(x.mapOrDefault({ x1 -> x1.len() }) == 2)
     * check(y.mapOrDefault({ y1 -> y1.len() }) == 0)
     * ```
     *
     * [default value]: Default.default
     */
    public open fun <U> mapOrDefault(f: (T) -> U, defaultUFn: () -> U): U =
        when (this) {
            is Some<*> -> {
                val (t) = this as Some<T>
                f(t)
            }
            null -> defaultUFn()
        }

    /**
     * Transforms the `Option<T>` into a [Result.T.E], mapping [Some] to
     * [Ok] and [None] to [Err].
     *
     * Arguments passed to `okOr` are eagerly evaluated; if you are passing the
     * result of a function call, it is recommended to use [okOrElse], which is
     * lazily evaluated.
     *
     * [Ok]: Ok
     * [Err]: Err
     * [Some]: Some
     * [okOrElse]: Option.okOrElse
     *
     * # Examples
     *
     * ```kotlin
     * val x = "foo"
     * check(x.okOr(0) == Result.success("foo"))
     * val x1: String? = null
     * check((x1?.let { Result.success(it) } ?: Result.failure(0)) == Result.failure(0))
     * ```
     */
    public open fun <E> okOr(err: E): Result<T, E> =
        when (this) {
            is Some<*> -> {
                val (v) = this as Some<T>
                Result.Ok<T, E>(v)
            }
            null -> Result.Err<T, E>(err)
        }

    /**
     * Transforms the `Option<T>` into a [Result.T.E], mapping [Some] to
     * [Ok] and [None] to [Err].
     *
     * [Ok]: Ok
     * [Err]: Err
     * [Some]: Some
     *
     * # Examples
     *
     * ```kotlin
     * val x = "foo"
     * check(x.okOrElse({ 0 }) == Result.success("foo"))
     * val x1: String? = null
     * check((x1?.let { Result.success(it) } ?: Result.failure(0)) == Result.failure(0))
     * ```
     */
    public open fun <E> okOrElse(err: () -> E): Result<T, E> =
        when (this) {
            is Some<*> -> {
                val (v) = this as Some<T>
                Result.Ok<T, E>(v)
            }
            null -> Result.Err<T, E>(err())
        }

    /**
     * Converts from `Option<T>` (or `Option<T>`) to `Option<T.Target>`.
     *
     * Leaves the original Option in-place, creating a new one with a reference
     * to the original one, additionally coercing the contents via [Deref].
     *
     * # Examples
     *
     * ```kotlin
     * val x: String? = "hey".toOwned()
     * check(x == "hey")
     * val x1: String? = null
     * check(x1 == null)
     * ```
     */
    public open fun asDeref(): Option<Target> = this.asRef().map(Deref::deref)

    /**
     * Converts from `Option<T>` (or `Option<T>`) to `Option<T.Target>`.
     *
     * Leaves the original `Option` in-place, creating a new one containing a mutable reference to
     * the inner type's [Deref.Target] type.
     *
     * # Examples
     *
     * ```kotlin
     * var x: String? = "hey".toOwned()
     * check(x?.let({ x1 ->
     *     x1.makeAsciiUppercase()
     *     x1
     * }) == "HEY".toOwned().asMutStr())
     * ```
     */
    public open fun asDerefMut(): Option<Target> = this.asMut().map({ DerefMut.derefMut(it) })

    // ////////////////////////////////////////////////////////////////////
    // Iterator constructors
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns an iterator over the possibly contained value.
     *
     * # Examples
     *
     * ```kotlin
     * val x = 4
     * check(x.iter().next() == 4)
     * val x1: UInt? = null
     * check(x1.iter().next() == null)
     * ```
     */
    public open fun iter(): Iter<T> = Iter(inner = Item(opt = this.asRef()))

    /**
     * Returns a mutable iterator over the possibly contained value.
     *
     * # Examples
     *
     * ```kotlin
     * var x = 4
     * when (val matchVal = x.iterMut().next()) {
     *     null -> {}
     *     else -> {
     *         val v = matchVal
     *         v = 42
     *     }
     * }
     * check(x == 42)
     * var x1: UInt? = null
     * check(x1.iterMut().next() == null)
     * ```
     */
    public open fun iterMut(): IterMut<T> = IterMut(inner = Item(opt = this.asMut()))

    // ////////////////////////////////////////////////////////////////////
    // Boolean operations on the values, eager and lazy
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns [None] if the option is [None], otherwise returns `optb`.
     *
     * Arguments passed to `and` are eagerly evaluated; if you are passing the
     * result of a function call, it is recommended to use [andThen], which is
     * lazily evaluated.
     *
     * [andThen]: Option.andThen
     *
     * # Examples
     *
     * ```kotlin
     * val x = 2
     * val y: String? = null
     * check(x.and(y) == null)
     * val x1: UInt? = null
     * val y1 = "foo"
     * check(x1.and(y1) == null)
     * val x2 = 2
     * val y2 = "foo"
     * check(x2.and(y2) == "foo")
     * val x3: UInt? = null
     * val y3: String? = null
     * check(x3.and(y3) == null)
     * ```
     */
    public open fun <U> and(optb: Option<U>): Option<U> =
        when (this) {
            is Some<*> -> optb
            null -> None
        }

    /**
     * Returns [None] if the option is [None], otherwise calls `f` with the
     * wrapped value and returns the result.
     *
     * Some languages call this operation flatmap.
     *
     * # Examples
     *
     * ```kotlin
     * fun sqThenToString(x: UInt): String? {
     *     return x.checkedMul(x)?.let({ sq -> sq.toString() })
     * }
     * check(2.andThen(::sqThenToString) == 4.toString())
     * check(1000000.andThen(::sqThenToString) == null)
     * // overflowed!
     * check(null.andThen(::sqThenToString) == null)
     * ```
     *
     * Often used to chain fallible operations that may return [None].
     *
     * ```kotlin
     * val arr2d = listOf(listOf("A0", "A1"), listOf("B0", "B1"))
     * val item01 = arr2d.get(0)?.let({ row -> row.get(1) })
     * check(item01 == "A1")
     * val item20 = arr2d.get(2)?.let({ row -> row.get(0) })
     * check(item20 == null)
     * ```
     */
    public open fun <U> andThen(f: (T) -> Option<U>): Option<U> =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                f(x)
            }
            null -> None
        }

    /**
     * Returns [None] if the option is [None], otherwise calls `predicate`
     * with the wrapped value and returns:
     *
     * - [Some] if `predicate` returns `true` (where `t` is the wrapped
     *   value), and
     * - [None] if `predicate` returns `false`.
     *
     * This function works similar to [Iterator.filter]. You can imagine
     * the `Option<T>` being an iterator over one or zero elements. `filter()`
     * lets you decide which elements to keep.
     *
     * # Examples
     *
     * ```kotlin
     * fun isEven(n: Int): Boolean {
     *     return n % 2 == 0
     * }
     * check(null.filter(::isEven) == null)
     * check(3.filter(::isEven) == null)
     * check(4.filter(::isEven) == 4)
     * ```
     *
     * [Some]: Some
     */
    public open fun filter(predicate: (T) -> Boolean): Option<T> {
        if (this is Some<*>) {
            val (x) = this
            if (predicate(x)) {
                return Some(x)
            }
        }
        return None
    }

    /**
     * Returns the option if it contains a value, otherwise returns `optb`.
     *
     * Arguments passed to `or` are eagerly evaluated; if you are passing the
     * result of a function call, it is recommended to use [orElse], which is
     * lazily evaluated.
     *
     * [orElse]: Option.orElse
     *
     * # Examples
     *
     * ```kotlin
     * val x = 2
     * val y = null
     * check(x.or(y) == 2)
     * val x1 = null
     * val y1 = 100
     * check((x1 ?: y1) == 100)
     * val x2 = 2
     * val y2 = 100
     * check(x2.or(y2) == 2)
     * val x3: UInt? = null
     * val y3 = null
     * check((x3 ?: y3) == null)
     * ```
     */
    public open fun or(optb: Option<T>): Option<T> =
        when (this) {
            is Some<*> -> {
                val x = this
                x
            }
            null -> optb
        }

    /**
     * Returns the option if it contains a value, otherwise calls `f` and
     * returns the result.
     *
     * # Examples
     *
     * ```kotlin
     * fun nobody(): String? {
     *     return null
     * }
     * fun vikings(): String? {
     *     return "vikings"
     * }
     * check("barbarians".orElse(::vikings) == "barbarians")
     * check(null.orElse(::vikings) == "vikings")
     * check(null.orElse(::nobody) == null)
     * ```
     */
    public open fun orElse(f: () -> Option<T>): Option<T> =
        when (this) {
            is Some<*> -> {
                val x = this
                x
            }
            null -> f()
        }

    /**
     * Returns [Some] if exactly one of `self`, `optb` is [Some], otherwise returns [None].
     *
     * # Examples
     *
     * ```kotlin
     * val x = 2
     * val y: UInt? = null
     * check(x.xor(y) == 2)
     * val x1: UInt? = null
     * val y1 = 2
     * check(x1.xor(y1) == 2)
     * val x2 = 2
     * val y2 = 2
     * check(x2.xor(y2) == null)
     * val x3: UInt? = null
     * val y3: UInt? = null
     * check(x3.xor(y3) == null)
     * ```
     */
    public open fun xor(optb: Option<T>): Option<T> =
        run {
            val matchVal = Pair(this, optb)
            when {
                matchVal.first is Some<*> && matchVal.second == null -> {
                    val a = matchVal.first
                    a
                }
                matchVal.first == null && matchVal.second is Some<*> -> {
                    val b = matchVal.second
                    b
                }
                else -> None
            }
        }

    // ////////////////////////////////////////////////////////////////////
    // Entry-like operations to insert a value and return a reference
    // ////////////////////////////////////////////////////////////////////

    /**
     * Inserts `value` into the option, then returns a mutable reference to it.
     *
     * If the option already contains a value, the old value is dropped.
     *
     * See also [Option.getOrInsert], which doesn't update the value if
     * the option already contains [Some].
     *
     * # Example
     *
     * ```kotlin
     * var opt = null
     * val value = opt.insert(1)
     * check(value == 1)
     * check(opt!! == 1)
     * val value1 = opt.insert(2)
     * check(value1 == 2)
     * value1 = 3
     * check(opt!! == 3)
     * ```
     */
    public open fun insert(value: T): T {
        var selfMut = this
        selfMut = Some(value)
        // SAFETY: the code above just filled the option
        return selfMut.asMut().unwrapUnchecked()
    }

    /**
     * Inserts `value` into the option if it is [None], then
     * returns a mutable reference to the contained value.
     *
     * See also [Option.insert], which updates the value even if
     * the option already contains [Some].
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var x = null
     * run {
     *     val y: RustMutableReference<UInt> = x.getOrInsert(5)
     *     check(y == 5)
     *     (y).write(7)
     * }
     * check(x == 7)
     * ```
     */
    public open fun getOrInsert(value: T): T = this.getOrInsertWith({ value })

    /**
     * Inserts the default value into the option if it is [None], then
     * returns a mutable reference to the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var x = null
     * run {
     *     val y: RustMutableReference<UInt> = x.getOrInsertDefault()
     *     check(y == 0)
     *     (y).write(7)
     * }
     * check(x == 7)
     * ```
     */
    public open fun getOrInsertDefault(defaultTFn: () -> T): T = this.getOrInsertWith(defaultTFn)

    /**
     * Inserts a value computed from `f` into the option if it is [None],
     * then returns a mutable reference to the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var x = null
     * run {
     *     val y: RustMutableReference<UInt> = x.getOrInsertWith({ 5 })
     *     check(y == 5)
     *     (y).write(7)
     * }
     * check(x == 7)
     * ```
     */
    public open fun getOrInsertWith(f: () -> T): T {
        if (this == null) {
            // The effect of the following statement is identical to
            // *self = Some(f());
            // except that it does not drop the old value of `* self`. This is not a leak, because
            // we just checked that the old value is `None`, which contains no fields to drop.
            // This implementation strategy
            //
            // * avoids needing a `T: [const] Destruct` bound, to the benefit of `const` callers,
            // * and avoids possibly compiling needless drop code (as would sometimes happen in the
            // previous implementation), to the benefit of non-`const` callers.
            //
            // Note(const-Note): It would be nice if this weird trick were made obsolete
            // (though that is likely to be hard/wontfix).
            //
            // It could also be expressed as `unchecked ( core.ptr.write(self, Some(f())) )`, but
            // no reason is currently known to use additional unchecked code here.
            forget(this.replace(f()))
        }
        // SAFETY: a `None` variant for `self` would have been replaced by a `Some`
        // variant in the code above.
        return this.asMut().unwrapUnchecked()
    }

    /**
     * If the option is `None`, calls the closure and inserts its output if successful.
     *
     * If the closure returns a residual value such as `Err` or `None`,
     * that residual value is returned and nothing is inserted.
     *
     * If the option is `Some`, nothing is inserted.
     *
     * Unless a residual is returned, a mutable reference to the value
     * of the option will be output.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(optionGetOrTryInsertWith)
     * public fun doctestEntry() {
     *     var o1: UInt? = null
     *     var o2: UByte? = null
     *     val number = "12345"
     *     check(o1.getOrTryInsertWith({ number.parse() }).copied() == Result.success(12345))
     *     check(o2.getOrTryInsertWith({ number.parse() }).isErr())
     *     check(o1 == 12345u)
     *     check(o2 == null)
     * }
     * ```
     */
    public open fun <Residual, R> getOrTryInsertWith(f: () -> R): TryType where R : Try<R, T, Residual> {
        var selfMut = this
        if (selfMut == null) {
            selfMut = Some(when (val tryResult = f()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
        }
        // SAFETY: a `None` variant for `self` would have been replaced by a `Some`
        // variant in the code above.
        return Try.fromOutput(selfMut.asMut().unwrapUnchecked())
    }

    // ////////////////////////////////////////////////////////////////////
    // Misc
    // ////////////////////////////////////////////////////////////////////

    /**
     * Takes the value out of the option, leaving a [None] in its place.
     *
     * # Examples
     *
     * ```kotlin
     * var x = 2
     * val y = x.also { x = null }
     * check(x == null)
     * check(y == 2)
     * var x1: UInt? = null
     * val y1 = x1.also { x1 = null }
     * check(x1 == null)
     * check(y1 == null)
     * ```
     */
    public open fun take(): Option<T> {
        // Note(const-Note) replace `mem.replace` by `mem.take` when the latter is const ready
        return replace(this, None)
    }

    /**
     * Takes the value out of the option, but only if the predicate evaluates to
     * `true` on a mutable reference to the value.
     *
     * In other words, replaces `self` with `None` if the predicate returns `true`.
     * This method operates similar to [Option.take] but conditional.
     *
     * # Examples
     *
     * ```kotlin
     * var x = 42
     * val prev = x.takeIf({ v -> if (v == 42) {
     *     v += 1
     *     false
     * } else {
     *     false
     * } })
     * check(x == 43)
     * check(prev == null)
     * val prev1 = x.takeIf({ v -> v == 43 })
     * check(x == null)
     * check(prev1 == 43)
     * ```
     */
    public open fun takeIf(predicate: (T) -> Boolean): Option<T> =
        if (this.asMut().isSomeAnd(predicate)) {
            this.also { this = null }
        } else {
            None
        }

    /**
     * Replaces the actual value in the option by the value given in parameter,
     * returning the old value if present,
     * leaving a [Some] in its place without deinitializing either one.
     *
     * # Examples
     *
     * ```kotlin
     * var x = 2
     * val old = x.replace(5)
     * check(x == 5)
     * check(old == 2)
     * var x1 = null
     * val old1 = x1.replace(3)
     * check(x1 == 3)
     * check(old1 == null)
     * ```
     */
    public open fun replace(value: T): Option<T> = io.github.kotlinmania.rustlang.core.mem.replace(this, Some(value))

    /**
     * Makes a tuple of the value in `self` and the value in another `Option`.
     *
     * If `self` is `Some(s)` and `other` is `Some(o)`, this method returns `Some((s, o))`.
     * Otherwise, `None` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * val x = 1
     * val y = "hi"
     * val z = null
     * check((x.zip(y)) == Pair(1, "hi"))
     * check((x.zip(z)) == null)
     * ```
     */
    public open fun <U> zip(other: Option<U>): Option<Pair<T, U>> =
        run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (a) = matchVal.first as Some<T>
                    val (b) = matchVal.second as Some<T>
                    Some(Pair(a, b))
                }
                else -> None
            }
        }

    /**
     * Combines the value in `self` with the value in another `Option`, using the function `f`.
     *
     * If `self` is `Some(s)` and `other` is `Some(o)`, this method returns `Some(f(s, o))`.
     * Otherwise, `None` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(optionZip)
     * public fun doctestEntry() {
     *     open class Point(public var x: Double, public var y: Double) {
     *         public open fun new(x: Double, y: Double): Point {
     *             return Point(x = x, y = y)
     *         }
     *     }
     *     val x = 17.5
     *     val y = 42.7
     *     check(x.zipWith(y, Point.new) == Point(x = 17.5, y = 42.7))
     *     check(x.zipWith(null, Point.new) == null)
     * }
     * ```
     */
    public open fun <U, R> zipWith(other: Option<U>, f: (T, U) -> R): Option<R> =
        run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (a) = matchVal.first as Some<T>
                    val (b) = matchVal.second as Some<T>
                    Some(f(a, b))
                }
                else -> None
            }
        }

    /**
     * Reduces two options into one, using the provided function if both are `Some`.
     *
     * If `self` is `Some(s)` and `other` is `Some(o)`, this method returns `Some(f(s, o))`.
     * Otherwise, if only one of `self` and `other` is `Some`, that one is returned.
     * If both `self` and `other` are `None`, `None` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(optionReduce)
     * public fun doctestEntry() {
     *     val s12 = 12
     *     val s17 = 17
     *     val n = null
     *     val f = { a, b -> a + b }
     *     check(s12.reduce(s17, f) == 29)
     *     check(s12.reduce(n, f) == 12)
     *     check(n.reduce(s17, f) == 17)
     *     check(n.reduce(n, f) == null)
     * }
     * ```
     */
    public open fun <U, R> reduce(other: Option<U>, f: (T, U) -> R): Option<R> =
        run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (a) = matchVal.first as Some<T>
                    val (b) = matchVal.second as Some<T>
                    Some(f(a, b))
                }
                matchVal.first is Some<*> -> {
                    val (a1) = matchVal.first as Some<T>
                    Some(a1.into())
                }
                matchVal.second is Some<*> -> {
                    val (b1) = matchVal.second as Some<T>
                    Some(b1.into())
                }
                else -> None
            }
        }

    /**
     * Transforms an optional iterator into an iterator.
     *
     * If `self` is `None`, the resulting iterator is empty.
     * Otherwise, an iterator is made from the `Some` value and returned.
     * # Examples
     * ```kotlin
     * @feature(optionIntoFlatIter)
     * public fun doctestEntry() {
     *     val o1 = listOf(1, 2)
     *     val o2 = null
     *     check(o1.intoFlatIter().collect<Vec>() == listOf(1, 2))
     *     check(o2.intoFlatIter().collect<Vec>() == mutableListOf())
     * }
     * ```
     */
    public open fun intoFlatIter(): OptionFlatten<IntoIter> = OptionFlatten(iter = this.map(IntoIterator::intoIter))

    // ////////////////////////////////////////////////////////////////////////
    // Trait implementations
    // ////////////////////////////////////////////////////////////////////////
    // Note(constHack): the T: [const] Destruct should be inferred from the Self: [const] Destruct in cloneFrom.
    // See https://github.com/rust-lang/rust/issues/144207
    public open fun clone(cloneTFn: (T) -> T = { it }): Option<T> =
        when (this) {
            is Some<*> -> {
                val (x) = this as Some<T>
                Some(cloneTFn(x))
            }
            null -> None
        }

    public open fun cloneFrom(source: Option<T>, cloneTFn: (T) -> T = { it }) {
        run {
            val matchVal = Pair(this, source)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (to) = matchVal.first as Some<T>
                    val (from) = matchVal.second as Some<T>
                    to.cloneFrom(from)
                }
                else -> {
                    val to1 = matchVal.first
                    val from1 = matchVal.second
                    to1 = from1.clone()
                }
            }
        }
    }

    /**
     * Returns a consuming iterator over the possibly contained value.
     *
     * # Examples
     *
     * ```kotlin
     * val x = "string"
     * val v: List<String> = x.intoIter().toMutableList()
     * check(v == listOf("string"))
     * val x1 = null
     * val v1: List<String> = ArrayDeque(listOfNotNull(x1)).toMutableList()
     * check(v1.isEmpty())
     * ```
     */
    public open fun intoIter(): IntoIter<T> = IntoIter(inner = Item(opt = this))

    // Ideally, LLVM should be able to optimize our derive code to this.
    // Once https://github.com/llvm/llvm-project/issues/52622 is fixed, we can
    // go back to deriving `PartialEq`.

    public open fun eq(other: Option<T>): Boolean {
        // Spelling out the cases explicitly optimizes better than
        // `_ -> false`
        return run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (l) = matchVal.first as Some<T>
                    val (r) = matchVal.second as Some<T>
                    l == r
                }
                matchVal.first is Some<*> && matchVal.second == null -> false
                matchVal.first == null && matchVal.second is Some<*> -> false
                else -> true
            }
        }
    }

    // Manually implementing here somewhat improves codegen for
    // https://github.com/rust-lang/rust/issues/49892, although still
    // not optimal.

    /**
     * See [the documentation](https://doc.rust-lang.org/std/option/#comparison-operators) for details.
     * [None] always compares less than any [Some]
     */
    public open fun partialCmp(other: Option<T>): Option<cmp.Ordering> =
        run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (l) = matchVal.first as Some<T>
                    val (r) = matchVal.second as Some<T>
                    l.partialCmp(r)
                }
                matchVal.first is Some<*> && matchVal.second == null -> Some(cmp.Ordering.Greater)
                matchVal.first == null && matchVal.second is Some<*> -> Some(cmp.Ordering.Less)
                else -> Some(cmp.Ordering.Equal)
            }
        }

    public open fun cmp(other: Option<T>): cmp.Ordering =
        run {
            val matchVal = Pair(this, other)
            when {
                matchVal.first is Some<*> && matchVal.second is Some<*> -> {
                    val (l) = matchVal.first as Some<T>
                    val (r) = matchVal.second as Some<T>
                    l.cmp(r)
                }
                matchVal.first is Some<*> && matchVal.second == null -> cmp.Ordering.Greater
                matchVal.first == null && matchVal.second is Some<*> -> cmp.Ordering.Less
                else -> cmp.Ordering.Equal
            }
        }

    public override fun branch(): ControlFlow<Option<Nothing>, T> =
        when (this) {
            is Some<*> -> {
                val (v) = this as Some<T>
                ControlFlow.Continue(v)
            }
            null -> ControlFlow.Break(None)
        }

    public companion object {
        /**
         * Returns [`None`][Option.None].
         *
         * # Examples
         *
         * ```kotlin
         * val opt: UInt? = Option.default()
         * check(opt == null)
         * ```
         */
        public fun <T> default(): Option<T> = None

        /**
         * Moves `val` into a new [Some].
         *
         * # Examples
         *
         * ```kotlin
         * val o: UByte? = Option.from(67)
         * check(67u.toUByte() == o)
         * ```
         */
        public fun <T> from(value: T): Option<T> = Some(value)

        public fun <T> fromOutput(output: T): Option<T> = Some(output)

        public fun <T> fromResidual(residual: Option<Nothing>): Option<T> =
            when (residual) {
                null -> None
            }

        public fun <T> fromResidual(destructuredArg0: Yeet<Unit>): Option<T> = None
    }
}

/**
 * Unzips an option containing a tuple of two options.
 *
 * If `self` is `Some((a, b))` this method returns `(Some(a), Some(b))`.
 * Otherwise, `(None, None)` is returned.
 *
 * # Examples
 *
 * ```kotlin
 * val x = Pair(1, "hi")
 * val y = null
 * check(x.unzip() == Pair(1, "hi"))
 * check(y.unzip() == Pair(null, null))
 * ```
 */
public fun <T, U> Option<Pair<T, U>>.unzip(): Pair<Option<T>, Option<U>> =
    when (this) {
        is Some<*> -> {
            val a = (this as Some<T>).value.first
            val b = (this as Some<T>).value.second
            Pair(Some(a), Some(b))
        }
        null -> Pair(None, None)
    }

/**
 * Maps an `Option<T>` to an `Option<T>` by copying the contents of the
 * option.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 12
 * val optX = x
 * check(optX == 12)
 * val copied = optX.copied()
 * check(copied == 12)
 * ```
 */
public fun <T> Option<T>.copied(): Option<T> {
    // Note(const-Note): this implementation, which sidesteps using `Option.map` since it's not const
    // ready yet, should be reverted when possible to avoid code repetition
    return when (this) {
        is Some<*> -> {
            val (v) = this as Some<T>
            Some(v)
        }
        null -> None
    }
}

/**
 * Maps an `Option<T>` to an `Option<T>` by cloning the contents of the
 * option.
 *
 * # Examples
 *
 * ```kotlin
 * val x = 12
 * val optX = x
 * check(optX == 12)
 * val cloned = optX.cloned()
 * check(cloned == 12)
 * ```
 */
public fun <T> Option<T>.cloned(cloneTFn: (T) -> T = { it }): Option<T> = this.map(cloneTFn)

/**
 * Maps an `Option<T>` to an `Option<T>` by cloning the contents of the
 * option.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * val localStorageL3C0 = AddressableLocal(12, PrimitiveLayouts.I32)
 * val optX = RustMutableReference(localStorageL3C0.asMutPtr(), localStorageL3C0.layout)
 * check(optX == RustMutableReference(allocateNativeCell(12, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
 * val cloned = optX.cloned()
 * check(cloned == 12)
 * ```
 */
public fun <T> Option<T>.cloned(): Option<T> = this.asDeref().cloned()

/**
 * Transposes an `Option` of a [Result] into a [Result] of an `Option`.
 *
 * <code>[Some]\([Ok]\(\_))</code> is mapped to <code>[Ok]\([Some]\(\_))</code>,
 * <code>[Some]\([Err]\(\_))</code> is mapped to <code>[Err]\(\_)</code>,
 * and [None] will be mapped to <code>[Ok]\([None])</code>.
 *
 * # Examples
 *
 * ```kotlin
 * open class SomeErr
 * val x: Result<Int>? = Result.success(5)
 * val y: Result<Int?> = Result.success(5)
 * check(x.transpose() == y)
 * ```
 */
public fun <T, E> Option<Result<T, E>>.transpose(): Result<Option<T>, E> =
    when {
        this is Some<*> && (this as Some<T>).value is Result.Ok<*, *> -> {
            val x = ((this as Some<T>).value as Result.Ok<*, *>).okValue()
            Result.Ok<Option<T>, E>(Some(x))
        }
        this is Some<*> && (this as Some<T>).value is Result.Err<*, *> -> {
            val e = ((this as Some<T>).value as Result.Err<*, *>).errValue()
            Result.Err<Option<T>, E>(e)
        }
        else -> Result.Ok<Option<T>, E>(None)
    }

public fun unwrapFailed(): Nothing = panic("called `Option::unwrap()` on a `None` value")

// This is a separate function to reduce the code size of .expect() itself.
public fun expectFailed(msg: String): Nothing = panicDisplay(msg)

/**
 * Converts from `Option<T>` to `Option<T>`.
 *
 * # Examples
 *
 * Converts an <code>[Option]<[String]></code> into an <code>[Option]<[Int]></code>, preserving
 * the original. The [map] method takes the `self` argument by value, consuming the original,
 * so this technique uses `from` to first take an [Option] to a reference
 * to the value inside the original.
 *
 * [map]: Option.map
 * [String]: ../../std/string/struct.String.html "String"
 *
 * ```kotlin
 * val s: String? = "Hello, Rustaceans!"
 * val o: Int? = Option.from(s).map({ ss: String -> ss.len() })
 * println("Can still print s: $s")
 * check(o == 18)
 * ```
 */
public fun <T> fromOptionT(o: Option<T>): Option<T> = o.asRef()

// ////////////////////////////////////////////////////////////////////////
// The Option Iterators
// ////////////////////////////////////////////////////////////////////////
public open class Item<A>(
    public var opt: Option<A>,
) : TrustedLen<Any>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@Item.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Item<A> = Item(opt)

    public open fun next(): Option<A> = this.opt.also { this.opt = null }

    public open fun sizeHint(): Pair<Int, Option<Int>> {
        val len = this.opt.len()
        return Pair(len, Some(len))
    }

    public open fun nextBack(): Option<A> = this.opt.also { this.opt = null }

    public open fun len(): Int = this.opt.len()
}

/**
 * An iterator over a reference to the [Some] variant of an [Option].
 *
 * The iterator yields one value if the [Option] is a [Some], otherwise none.
 *
 * This `class` is created by the [Option.iter] function.
 */
public open class Iter<A>(
    public var inner: Item<A>,
) : TrustedLen<Any>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Option<A> = this.inner.next()

    public open fun sizeHint(): Pair<Int, Option<Int>> = this.inner.sizeHint()

    public open fun nextBack(): Option<A> = this.inner.nextBack()

    public open fun clone(): Iter<A> = Iter(inner = this.inner.clone())
}

/**
 * An iterator over a mutable reference to the [Some] variant of an [Option].
 *
 * The iterator yields one value if the [Option] is a [Some], otherwise none.
 *
 * This `class` is created by the [Option.iterMut] function.
 */
public open class IterMut<A>(
    public var inner: Item<A>,
) : TrustedLen<Any>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Option<A> = this.inner.next()

    public open fun sizeHint(): Pair<Int, Option<Int>> = this.inner.sizeHint()

    public open fun nextBack(): Option<A> = this.inner.nextBack()
}

/**
 * An iterator over the value in [Some] variant of an [Option].
 *
 * The iterator yields one value if the [Option] is a [Some], otherwise none.
 *
 * This `class` is created by the [Option.intoIter] function.
 */
public open class IntoIter<A>(
    public var inner: Item<A>,
) : TrustedLen<Any>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<A> = IntoIter(inner)

    public open fun next(): Option<A> = this.inner.next()

    public open fun sizeHint(): Pair<Int, Option<Int>> = this.inner.sizeHint()

    public open fun nextBack(): Option<A> = this.inner.nextBack()
}

/**
 * The iterator produced by [Option.intoFlatIter]. See its documentation for more.
 */
public open class OptionFlatten<A>(
    public var iter: Option<A>,
) : Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@OptionFlatten.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): OptionFlatten<A> = OptionFlatten(iter)

    public open fun next(): Option<Item> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.next()
            }
            null -> None
        }

    public open fun sizeHint(): Pair<Int, Option<Int>> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.sizeHint()
            }
            null -> Pair(0, Some(0))
        }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.advanceBy(n)
            }
            null -> NonZero.new<NonZeroUsizeInner, Int>(n).mapOr(Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit), { Result.Err(it) })
        }

    public open fun nth(n: Int): Option<Item> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.nth(n)
            }
            null -> None
        }

    public open fun <Acc> fold(init: Acc, fold: (Acc, Item) -> Acc): Acc =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.fold(init, fold)
            }
            null -> init
        }

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.tryFold(init, fold)
            }
            null -> init
        }

    public open fun count(): Int =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.count()
            }
            null -> 0
        }

    public open fun last(): Option<Item> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.last()
            }
            null -> None
        }

    public open fun nextBack(): Option<Item> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.nextBack()
            }
            null -> None
        }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.advanceBackBy(n)
            }
            null -> NonZero.new<NonZeroUsizeInner, Int>(n).mapOr(Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit), { Result.Err(it) })
        }

    public open fun nthBack(n: Int): Option<Item> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.nthBack(n)
            }
            null -> None
        }

    public open fun <Acc> rfold(init: Acc, fold: (Acc, Item) -> Acc): Acc =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.rfold(init, fold)
            }
            null -> init
        }

    public open fun <Residual, Acc, R> tryRfold(init: Acc, fold: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> =
        when (val matchVal = this.iter) {
            is Some<*> -> {
                val (iter) = matchVal
                iter.tryRfold(init, fold)
            }
            null -> init
        }
}

// ////////////////////////////////////////////////////////////////////////
// FromIterator
// ////////////////////////////////////////////////////////////////////////

/**
 * Takes each element in the [Iterator]: if it is [`None`][Option.None],
 * no further elements are taken, and the [`None`][Option.None] is
 * returned. Should no [`None`][Option.None] occur, a container of type
 * `V` containing the values of each [Option] is returned.
 *
 * # Examples
 *
 * Here is an example which increments every integer in a vector.
 * We use the checked variant of `add` that returns `None` when the
 * calculation would result in an overflow.
 *
 * ```kotlin
 * val items = mutableListOf(0u.toUShort(), 1, 2)
 * val res: List<UShort>? = ArrayDeque((ArrayDeque(items)).map({ x -> x.checkedAdd(1) })).toMutableList()
 * check(res == mutableListOf(1u, 2u, 3u))
 * ```
 *
 * As you can see, this will return the expected, valid items.
 *
 * Here is another example that tries to subtract one from another list
 * of integers, this time checking for underflow:
 *
 * ```kotlin
 * val items = mutableListOf(2u.toUShort(), 1, 0)
 * val res: List<UShort>? = ArrayDeque((ArrayDeque(items)).map({ x -> x.checkedSub(1) })).toMutableList()
 * check(res == null)
 * ```
 *
 * Since the last element is zero, it would underflow. Thus, the resulting
 * value is `None`.
 *
 * Here is a variation on the previous example, showing that no
 * further elements are taken from `iter` after the first `None`.
 *
 * ```kotlin
 * val items = mutableListOf(3u.toUShort(), 2, 1, 10)
 * var shared = 0
 * val res: List<UShort>? = ArrayDeque((ArrayDeque(items)).map({ x ->
 *     shared += x
 *     x.checkedSub(2)
 * })).toMutableList()
 * check(res == null)
 * check(shared == 6)
 * ```
 *
 * Since the third element caused an underflow, no further elements were taken,
 * so the final value of `shared` is 6 (= `3 + 2 + 1`), not 16.
 */
public fun <T, V, I : Iterable<Option<T>>> fromIteratorFromIterOptionV(iter: I): Option<V> = Iter.tryProcess(iter.iterator(), { i -> i.collect() })

/**
 * Converts from `Option<Option<T>>` to `Option<T>`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val x: UInt? = 6u
 * check(6u == x)
 * val x1: UInt? = null
 * check(null == x1)
 * val x2: UInt? = null
 * check(null == x2)
 * ```
 *
 * Flattening only removes one level of nesting at a time:
 *
 * ```kotlin
 * val x: UInt? = 6u
 * check(6u == x)
 * check(6u == x)
 * ```
 */
public fun <T> Option<Option<T>>.flatten(): Option<T> {
    // Note(const-Note): could be written with `andThen`
    return when (this) {
        is Some<*> -> {
            val (inner) = this as Some<T>
            inner
        }
        null -> None
    }
}

/**
 * Converts from `Option<Option<T>>` to `Option<T>`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(optionReferenceFlattening)
 * public fun doctestEntry() {
 *     val x: UInt? = 6u
 *     check(6 == x.flattenRef())
 *     val x1: UInt? = null
 *     check(null == x1.flattenRef())
 *     val x2: UInt? = null
 *     check(null == x2.flattenRef())
 * }
 * ```
 */
public fun <T> Option<Option<T>>.flattenRef(): Option<T> =
    when (this) {
        is Some<*> -> {
            val (inner) = this as Some<T>
            inner.asRef()
        }
        null -> None
    }

/**
 * Converts from `Option<Option<T>>` to `Option<T>`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
 *
 * @feature(optionReferenceFlattening)
 * public fun doctestEntry() {
 *     val y: UInt? = 6u
 *     val x: UInt? = y
 *     check(RustMutableReference(allocateNativeCell(6, PrimitiveLayouts.I32), PrimitiveLayouts.I32) == x.flattenMut())
 *     val y1: UInt? = null
 *     val x1: UInt? = y1
 *     check(null == x1.flattenMut())
 *     val x2: UInt? = null
 *     check(null == x2.flattenMut())
 * }
 * ```
 */
public fun <T> Option<Option<T>>.flattenMut(): Option<T> =
    when (this) {
        is Some<*> -> {
            val (inner) = this as Some<T>
            inner.asMut()
        }
        null -> None
    }

/**
 * Transposes a `[Option<T>; N]` into a `Option<[T; N]>`.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(optionArrayTranspose)
 * public fun doctestEntry() {
 *     val data = List(1000) { 0 }
 *     val data1: UByteArray? = data.transpose()
 *     check(data1 == UByteArray(1000) { 0u.toUByte() })
 *     val data2 = listOf(0, null)
 *     val data3: UByteArray? = data2.transpose()
 *     check(data3 == null)
 * }
 * ```
 */
public fun <T> List<Option<T>>.transpose(n: Int): Option<List<T>> = this.tryMap(identity)
