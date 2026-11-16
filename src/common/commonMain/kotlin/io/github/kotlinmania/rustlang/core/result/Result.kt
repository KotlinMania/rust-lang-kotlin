// port-lint: source result.rs
package io.github.kotlinmania.rustlang.core.result

import io.github.kotlinmania.rustlang.core.clone.UseCloned
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.deref.Deref
import io.github.kotlinmania.rustlang.core.ops.deref.DerefMut
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ops.trytrait.Yeet

// Error handling with the `Result` type.
//
// [`Result.T.E`][Result] is the type used for returning and propagating
// errors. It is an enum with the variants, [Ok], representing
// success and containing a value, and [Err], representing error
// and containing an error value.
//
// ```kotlin
// sealed class Result<T, E> {
//     public data class Ok<T, E>(public var value: T) : Result<T, E>()
//     public data class Err<T, E>(public var value: E) : Result<T, E>() {
//         public val error: E get() = value
//     }
// }
// ```
//
// Functions return [Result] whenever errors are expected and
// recoverable. In the `std` crate, [Result] is most prominently used
// for [I/O](../../std/io/index.html).
//
// A simple function returning [Result] might be
// defined and used like so:
//
// ```kotlin
// sealed class Version {
//     public data object Version1 : Version()
//     public data object Version2 : Version()
// }
// fun parseVersion(header: List<UByte>): Result<Version> {
//     return when (val matchVal = header.get(0)) {
//         null -> Result.failure("invalid header length")
//         1 -> Result.success(Version.Version1)
//         2 -> Result.success(Version.Version2)
//         else -> Result.failure("invalid version")
//     }
// }
// val version = parseVersion(listOf(1, 2, 3, 4))
// if (version.isSuccess) {
//     val v = version.getOrThrow()
//     println("working with version: $v")
// } else {
//     val e = version.exceptionOrNull()!!
//     println("error parsing header: $e")
// }
// ```
//
// Pattern matching on [Result]s is clear and straightforward for
// simple cases, but [Result] comes with some convenience methods
// that make working with it more succinct.
//
// ```kotlin
// // The `isOk` and `isErr` methods do what they say.
// val goodResult: Result<Int> = Result.success(10)
// val badResult: Result<Int> = Result.failure(10)
// check(goodResult.isOk() && !goodResult.isErr())
// check(badResult.isErr() && !badResult.isOk())
// // `map` and `mapErr` consume the `Result` and produce another.
// val goodResult1: Result<Int> = goodResult.map({ i -> i + 1 })
// val badResult1: Result<Int> = badResult.mapErr({ i -> i - 1 })
// check(goodResult1 == Result.success(11))
// check(badResult1 == Result.failure(9))
// // Use `andThen` to continue the computation.
// val goodResult2: Result<Boolean> = goodResult1.andThen({ i -> Result.success(i == 11) })
// check(goodResult2 == Result.success(true))
// // Use `orElse` to handle the error.
// val badResult2: Result<Int> = badResult1.orElse({ i -> Result.success(i + 20) })
// check(badResult2 == Result.success(29))
// // Consume the result and return the contents with `unwrap`.
// val finalAwesomeResult = goodResult2.unwrap()
// check(finalAwesomeResult)
// ```
//
// # Results must be used
//
// A common problem with using return values to indicate errors is
// that it is easy to ignore the return value, thus failing to handle
// the error. [Result] is annotated with the `@mustUse` attribute,
// which will cause the compiler to issue a warning when a Result
// value is ignored. This makes [Result] especially useful with
// functions that may encounter errors but don't otherwise return a
// useful value.
//
// Consider the [writeAll] method defined for I/O types
// by the [Write] trait:
//
// ```kotlin
// import std.io
//
// interface Write {
//     public fun writeAll(bytes: List<UByte>): Result<Unit>
// }
// ```
//
// *Note: The actual definition of [Write] uses [io.Result], which
// is just a synonym for <code>[Result]<T, [io.Error]></code>.*
//
// This method doesn't produce a value, but the write may
// fail. It's crucial to handle the error case, and *not* write
// something like this:
//
// ```kotlin
// import std.fs.File
// import std.io.prelude.*
//
// var file = File.create("valuableData.txt").unwrap()
// // If `writeAll` errors, then we'll never know, because the return
// // value is ignored.
// file.writeAll("important message".encodeToByteArray())
// ```
//
// If you *do* write that in Rust, the compiler will give you a
// warning (by default, controlled by the `unusedMustUse` lint).
//
// You might instead, if you don't want to handle the error, simply
// assert success with [expect]. This will panic if the
// write fails, providing a message explaining why the write was expected
// to succeed:
//
// ```kotlin
// import std.fs.File
// import std.io.prelude.*
//
// var file = File.create("valuableData.txt").unwrap()
// file.writeAll("important message".encodeToByteArray()).expect("writing to the file should succeed")
// ```
//
// You might also simply assert success:
//
// ```kotlin
// check(file.writeAll("important message".encodeToByteArray()).isOk())
// ```
//
// Or propagate the error up the call stack with [?]:
//
// ```kotlin
// import io.Result
//
// fun writeMessage(): Result<Unit> {
//     var file = File.create("valuableData.txt").getOrElse { return Result.failure(it) }
//     file.writeAll("important message".encodeToByteArray()).getOrElse { return Result.failure(it) }
//     return Result.success(Unit)
// }
// ```
//
// # The question mark operator, `?`
//
// When writing code that calls many functions that return the
// [Result] type, the error handling can be tedious. The question mark
// operator, [?], hides some of the boilerplate of propagating errors
// up the call stack.
//
// It replaces this:
//
// ```kotlin
// import std.fs.File
// import std.io
// import std.io.prelude.*
//
// open class Info(
//     public var name: String,
//     public var age: Int,
//     public var rating: Int,
// )
// fun writeInfo(info: Info): io.Result<Unit> {
//     // Early return on error
//     var file = run {
//         val matchVal = File.create("myBestFriends.txt")
//         if (matchVal.isSuccess) {
//             val f = matchVal.getOrThrow()
//             f
//         } else {
//             val e = matchVal.exceptionOrNull()!!
//             return Result.failure(e)
//         }
//     }
//     when (val matchVal1 = file.writeAll(("name: ${info.name}\n").encodeToByteArray().map { it.toUByte() })) {
//         is Err -> {
//             val (e1) = matchVal1
//             return Result.failure(e1)
//         }
//         else -> {}
//     }
//     when (val matchVal2 = file.writeAll(("age: ${info.age}\n").encodeToByteArray().map { it.toUByte() })) {
//         is Err -> {
//             val (e2) = matchVal2
//             return Result.failure(e2)
//         }
//         else -> {}
//     }
//     when (val matchVal3 = file.writeAll(("rating: ${info.rating}\n").encodeToByteArray().map { it.toUByte() })) {
//         is Err -> {
//             val (e3) = matchVal3
//             return Result.failure(e3)
//         }
//         else -> {}
//     }
//     return Result.success(Unit)
// }
// ```
//
// With this:
//
// ```kotlin
// import std.fs.File
// import std.io
// import std.io.prelude.*
//
// open class Info(
//     public var name: String,
//     public var age: Int,
//     public var rating: Int,
// )
// fun writeInfo(info: Info): io.Result<Unit> {
//     var file = File.create("myBestFriends.txt").getOrElse { return Result.failure(it) }
//     // Early return on error
//     file.writeAll(("name: ${info.name}\n").encodeToByteArray().map { it.toUByte() }).getOrElse { return Result.failure(it) }
//     file.writeAll(("age: ${info.age}\n").encodeToByteArray().map { it.toUByte() }).getOrElse { return Result.failure(it) }
//     file.writeAll(("rating: ${info.rating}\n").encodeToByteArray().map { it.toUByte() }).getOrElse { return Result.failure(it) }
//     return Result.success(Unit)
// }
// ```
//
// *It's much nicer!*
//
// Ending the expression with [?] will result in the [Ok]s unwrapped value, unless the result
// is [Err], in which case [Err] is returned early from the enclosing function.
//
// [?] can be used in functions that return [Result] because of the
// early return of [Err] that it provides.
//
// [expect]: Result.expect
// [Write]: ../../std/io/trait.Write.html "io.Write"
// [writeAll]: ../../std/io/trait.Write.html#method.writeAll "io.Write.writeAll"
// [io.Result]: ../../std/io/type.Result.html "io.Result"
// [Ok]: Ok
// [Err]: Err
// [io.Error]: ../../std/io/struct.Error.html "io.Error"
//
// # Representation
//
// In some cases, [Result.T.E] comes with size, alignment, and ABI
// guarantees. Specifically, one of either the `T` or `E` type must be a type
// that qualifies for the `Option` [representation guarantees][opt-rep] (let's
// call that type `I`), and the *other* type is a zero-sized type with
// alignment 1 (a "1-ZST").
//
// If that is the case, then `Result<T, E>` has the same size, alignment, and
// [function call ABI] as `I` (and therefore, as `Option<I>`). If `I` is `T`,
// it is therefore sound to reinterpret a value `t` of type `I` to type
// `Result<T, E>` (producing the value `Ok(t)`) and to reinterpret a value
// `Ok(t)` of type `Result<T, E>` to type `I` (producing the value `t`). If `I`
// is `E`, the same applies with `Ok` replaced by `Err`.
//
// For example, `NonZeroI32` qualifies for the `Option` representation
// guarantees and `()` is a zero-sized type with alignment 1. This means that
// both `Result<NonZeroI32, ()>` and `Result<Unit, NonZeroI32>` have the same
// size, alignment, and ABI as `NonZeroI32` (and `Option<NonZeroI32>`). The
// only difference between these is in the implied semantics:
//
// * `Option<NonZeroI32>` is "a non-zero i32 might be present"
// * `Result<NonZeroI32, ()>` is "a non-zero i32 success result, if any"
// * `Result<Unit, NonZeroI32>` is "a non-zero i32 error result, if any"
//
// [opt-rep]: ../option/index.html#representation "Option Representation"
// [function call ABI]: ../primitive.fn.html#abi-compatibility
//
// # Method overview
//
// In addition to working with pattern matching, [Result] provides a
// wide variety of different methods.
//
// ## Querying the variant
//
// The [isOk] and [isErr] methods return [trueVal] if the [Result]
// is [Ok] or [Err], respectively.
//
// The [isOkAnd] and [isErrAnd] methods apply the provided function
// to the contents of the [Result] to produce a boolean value. If the [Result] does not have the expected variant
// then [falseVal] is returned instead without executing the function.
//
// [isErr]: Result.isErr
// [isOk]: Result.isOk
// [isOkAnd]: Result.isOkAnd
// [isErrAnd]: Result.isErrAnd
//
// ## Adapters for working with references
//
// * [asRef] converts from `Result<T, E>` to `Result<T, E>`
// * [asMut] converts from `Result<T, E>` to `Result<T, E>`
// * [asDeref] converts from `Result<T, E>` to `Result<T.Target, E>`
// * [asDerefMut] converts from `Result<T, E>` to
//   `Result<T.Target, E>`
//
// [asDeref]: Result.asDeref
// [asDerefMut]: Result.asDerefMut
// [asMut]: Result.asMut
// [asRef]: Result.asRef
//
// ## Extracting contained values
//
// These methods extract the contained value in a [Result.T.E] when it
// is the [Ok] variant. If the [Result] is [Err]:
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
// The panicking methods [expect] and [unwrap] require `E` to
// implement the [Debug] trait.
//
// [expect]: Result.expect
// [unwrap]: Result.unwrap
// [unwrapOr]: Result.unwrapOr
// [unwrapOrDefault]: Result.unwrapOrDefault
// [unwrapOrElse]: Result.unwrapOrElse
// [unwrapUnchecked]: Result.unwrapUnchecked
// [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
//
// These methods extract the contained value in a [Result.T.E] when it
// is the [Err] variant. They require `T` to implement the [Debug]
// trait. If the [Result] is [Ok]:
//
// * [expectErr] panics with a provided custom message
// * [unwrapErr] panics with a generic message
// * [unwrapErrUnchecked] produces *[undefined behavior]*
//
// [expectErr]: Result.expectErr
// [unwrapErr]: Result.unwrapErr
// [unwrapErrUnchecked]: Result.unwrapErrUnchecked
// [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
//
// ## Transforming contained values
//
// These methods transform [Result] to [Option]:
//
// * [`err`][Result.err] transforms [Result.T.E] into [Option.E],
//   mapping [Err] to [Some] and [Ok] to [None]
// * [`ok`][Result.ok] transforms [Result.T.E] into [Option.T],
//   mapping [Ok] to [Some] and [Err] to [None]
// * [transpose] transposes a [Result] of an [Option] into an
//   [Option] of a [Result]
//
// [Err]: Err
// [Ok]: Ok
// [Some]: Option.Some
// [Some]: Option.Some
// [transpose]: Result.transpose
//
// These methods transform the contained value of the [Ok] variant:
//
// * [map] transforms [Result.T.E] into [Result.U.E] by applying
//   the provided function to the contained value of [Ok] and leaving
//   [Err] values unchanged
// * [inspect] takes ownership of the [Result], applies the
//   provided function to the contained value by reference,
//   and then returns the [Result]
//
// [map]: Result.map
// [inspect]: Result.inspect
//
// These methods transform the contained value of the [Err] variant:
//
// * [mapErr] transforms [Result.T.E] into [Result.T.F] by
//   applying the provided function to the contained value of [Err] and
//   leaving [Ok] values unchanged
// * [inspectErr] takes ownership of the [Result], applies the
//   provided function to the contained value of [Err] by reference,
//   and then returns the [Result]
//
// [mapErr]: Result.mapErr
// [inspectErr]: Result.inspectErr
//
// These methods transform a [Result.T.E] into a value of a possibly
// different type `U`:
//
// * [mapOr] applies the provided function to the contained value of
//   [Ok], or returns the provided default value if the [Result] is
//   [Err]
// * [mapOrElse] applies the provided function to the contained value
//   of [Ok], or applies the provided default fallback function to the
//   contained value of [Err]
//
// [mapOr]: Result.mapOr
// [mapOrElse]: Result.mapOrElse
//
// ## Boolean operators
//
// These methods treat the [Result] as a boolean value, where [Ok]
// acts like [trueVal] and [Err] acts like [falseVal]. There are two
// categories of these methods: ones that take a [Result] as input, and
// ones that take a function as input (to be lazily evaluated).
//
// The [and] and [or] methods take another [Result] as input, and
// produce a [Result] as output. The [and] method can produce a
// [Result.U.E] value having a different inner type `U` than
// [Result.T.E]. The [or] method can produce a [Result.T.F]
// value having a different error type `F` than [Result.T.E].
//
// | method  | self     | input     | output   |
// |---------|----------|-----------|----------|
// | [and] | `Err(e)` | (ignored) | `Err(e)` |
// | [and] | `Ok(x)`  | `Err(d)`  | `Err(d)` |
// | [and] | `Ok(x)`  | `Ok(y)`   | `Ok(y)`  |
// | [or]  | `Err(e)` | `Err(d)`  | `Err(d)` |
// | [or]  | `Err(e)` | `Ok(y)`   | `Ok(y)`  |
// | [or]  | `Ok(x)`  | (ignored) | `Ok(x)`  |
//
// [and]: Result.and
// [or]: Result.or
//
// The [andThen] and [orElse] methods take a function as input, and
// only evaluate the function when they need to produce a new value. The
// [andThen] method can produce a [Result.U.E] value having a
// different inner type `U` than [Result.T.E]. The [orElse] method
// can produce a [Result.T.F] value having a different error type `F`
// than [Result.T.E].
//
// | method       | self     | function input | function result | output   |
// |--------------|----------|----------------|-----------------|----------|
// | [andThen] | `Err(e)` | (not provided) | (not evaluated) | `Err(e)` |
// | [andThen] | `Ok(x)`  | `x`            | `Err(d)`        | `Err(d)` |
// | [andThen] | `Ok(x)`  | `x`            | `Ok(y)`         | `Ok(y)`  |
// | [orElse]  | `Err(e)` | `e`            | `Err(d)`        | `Err(d)` |
// | [orElse]  | `Err(e)` | `e`            | `Ok(y)`         | `Ok(y)`  |
// | [orElse]  | `Ok(x)`  | (not provided) | (not evaluated) | `Ok(x)`  |
//
// [andThen]: Result.andThen
// [orElse]: Result.orElse
//
// ## Comparison operators
//
// If `T` and `E` both implement [PartialOrd] then [Result.T.E] will
// derive its [PartialOrd] implementation.  With this order, an [Ok]
// compares as less than any [Err], while two [Ok] or two [Err]
// compare as their contained values would in `T` or `E` respectively.  If `T`
// and `E` both also implement [Ord], then so does [Result.T.E].
//
// ```kotlin
// check(Result.success(1) < Result.failure(0))
// val x: Result<Int> = Result.success(0)
// val y = Result.success(1)
// check(x < y)
// val x1: Result<Unit> = Result.failure(0)
// val y1 = Result.failure(1)
// check(x1 < y1)
// ```
//
// ## Iterating over `Result`
//
// A [Result] can be iterated over. This can be helpful if you need an
// iterator that is conditionally empty. The iterator will either produce
// a single value (when the [Result] is [Ok]), or produce no values
// (when the [Result] is [Err]). For example, [intoIter] acts like
// [once] if the [Result] is [Ok], and like [empty] if the
// [Result] is [Err].
//
// [Ok]: Ok
//
// Iterators over [Result.T.E] come in three types:
//
// * [intoIter] consumes the [Result] and produces the contained
//   value
// * [iter] produces an immutable reference of type `T` to the
//   contained value
// * [iterMut] produces a mutable reference of type `T` to the
//   contained value
//
// See [Iterating over `Option`] for examples of how this can be useful.
//
// [intoIter]: Result.intoIter
// [iter]: Result.iter
// [iterMut]: Result.iterMut
//
// You might want to use an iterator chain to do multiple instances of an
// operation that can fail, but would like to ignore failures while
// continuing to process the successful results. In this example, we take
// advantage of the iterable nature of [Result] to select only the
// [Ok] values using [`flatten`][Iterator.flatten].
//
// ```kotlin
// var results = mutableListOf()
// var errs = mutableListOf()
// val nums: Vec = listOf("17", "not a number", "99", "-27", "768").intoIter().map(UByte.fromStr).inspect({ x -> results.add(x.clone()) }).inspect({ x -> errs.extend(x.clone().err()) }).flatten().collect()
// check(errs.size == 3)
// check(nums == listOf(17, 99))
// println("results $results")
// println("errs $errs")
// println("nums $nums")
// ```
//
// ## Collecting into `Result`
//
// [Result] implements the [`FromIterator`][impl-FromIterator] trait,
// which allows an iterator over [Result] values to be collected into a
// [Result] of a collection of each contained value of the original
// [Result] values, or [Err] if any of the elements was [Err].
//
// [impl-FromIterator]: Result#impl-FromIterator%3CResult%3CT,+E%3E%3E-for-Result%3CV,+E%3E
//
// ```kotlin
// val v = listOf(Result.success(2), Result.success(4), Result.failure("err!"), Result.success(8))
// val res: Result<Vec> = v.intoIter().collect()
// check(res == Result.failure("err!"))
// val v1 = listOf(Result.success(2), Result.success(4), Result.success(8))
// val res1: Result<Vec> = v1.intoIter().collect()
// check(res1 == Result.success(mutableListOf(2, 4, 8)))
// ```
//
// [Result] also implements the [`Product`][impl-Product] and
// [`Sum`][impl-Sum] traits, allowing an iterator over [Result] values
// to provide the [`product`][Iterator.product] and
// [`sum`][Iterator.sum] methods.
//
// [impl-Product]: Result#impl-Product%3CResult%3CU,+E%3E%3E-for-Result%3CT,+E%3E
// [impl-Sum]: Result#impl-Sum%3CResult%3CU,+E%3E%3E-for-Result%3CT,+E%3E
//
// ```kotlin
// val v = listOf(Result.failure("error!"), Result.success(1), Result.success(2), Result.success(3), Result.failure("foo"))
// val res: Result<Int> = v.intoIter().sum()
// check(res == Result.failure("error!"))
// val v1 = listOf(Result.success(1), Result.success(2), Result.success(21))
// val res1: Result<Int> = v1.intoIter().product()
// check(res1 == Result.success(42))
// ```

public typealias TryType<T, E> = Result<T, E>

/**
 * `Result` is a type that represents either success ([Ok]) or failure ([Err]).
 *
 * See the [module documentation](self) for details.
 */
public sealed class Result<T, E> : UseCloned<Result<T, E>>, Try<Result<T, E>, T, Result<Nothing, E>> {
    /**
     * Contains the success value
     */
    public data class Ok<T, E>(public var value: T) : Result<T, E>()
    /**
     * Contains the error value
     */
    public data class Err<T, E>(public var value: E) : Result<T, E>() {
        public val error: E get() = value
    }

    public fun rustEquals(other: Result<T, E>): Boolean = this == other

    // ////////////////////////////////////////////////////////////////////////
    // Type implementation
    // ////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////
    // Querying the contained values
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns `true` if the result is [Ok].
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<Int> = Result.success(-3)
     * check(x.isOk() == true)
     * val x1: Result<Int> = Result.failure("Some error message")
     * check(x1.isOk() == false)
     * ```
     */
    public open fun isOk(): Boolean =
        when (this) {
            is Ok -> true
            else -> false
        }

    /**
     * Returns `true` if the result is [Ok] and the value inside of it matches a predicate.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.isOkAnd({ x1 -> x1 > 1 }) == true)
     * val x1: Result<UInt> = Result.success(0u)
     * check(x1.isOkAnd({ x2 -> x2 > 1 }) == false)
     * val x2: Result<UInt> = Result.failure("hey")
     * check(x2.isOkAnd({ x3 -> x3 > 1 }) == false)
     * val x3: Result<String> = Result.success("ownership".toString())
     * check(x3.asRef().isOkAnd({ x4 -> x4.len() > 1 }) == true)
     * println("still alive $x3")
     * ```
     */
    public open fun isOkAnd(f: (T) -> Boolean): Boolean =
        when (this) {
            is Err -> false
            is Ok -> {
                val (x) = this
                f(x)
            }
        }

    /**
     * Returns `true` if the result is [Err].
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<Int> = Result.success(-3)
     * check(x.isErr() == false)
     * val x1: Result<Int> = Result.failure("Some error message")
     * check(x1.isErr() == true)
     * ```
     */
    public open fun isErr(): Boolean = !this.isOk()

    /**
     * Returns `true` if the result is [Err] and the value inside of it matches a predicate.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Error
     * import std.io.ErrorKind
     *
     * val x: Result<UInt> = Result.failure(Error.new(ErrorKind.NotFound, "!"))
     * check(x.isErrAnd({ x1 -> x1.kind() == ErrorKind.NotFound }) == true)
     * val x1: Result<UInt> = Result.failure(Error.new(ErrorKind.PermissionDenied, "!"))
     * check(x1.isErrAnd({ x2 -> x2.kind() == ErrorKind.NotFound }) == false)
     * val x2: Result<UInt> = Result.success(123u)
     * check(x2.isErrAnd({ x3 -> x3.kind() == ErrorKind.NotFound }) == false)
     * val x3: Result<UInt> = Result.failure("ownership".toString())
     * check(x3.asRef().isErrAnd({ x4 -> x4.len() > 1 }) == true)
     * println("still alive $x3")
     * ```
     */
    public open fun isErrAnd(f: (E) -> Boolean): Boolean =
        when (this) {
            is Ok -> false
            is Err -> {
                val (e) = this
                f(e)
            }
        }

    // ////////////////////////////////////////////////////////////////////
    // Adapter for each variant
    // ////////////////////////////////////////////////////////////////////

    /**
     * Converts from `Result<T, E>` to [Option.T].
     *
     * Converts `self` into an [Option.T], consuming `self`,
     * and converting the error to `None`, if any.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.ok() == 2)
     * val x1: Result<UInt> = Result.failure("Nothing here")
     * check(x1.ok() == null)
     * ```
     */
    public open fun ok(): T? =
        when (this) {
            is Ok -> {
                val (x) = this
                x
            }
            is Err -> null
        }

    /**
     * Converts from `Result<T, E>` to [Option.E].
     *
     * Converts `self` into an [Option.E], consuming `self`,
     * and discarding the success value, if any.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.err() == null)
     * val x1: Result<UInt> = Result.failure("Nothing here")
     * check(x1.err() == "Nothing here")
     * ```
     */
    public open fun err(): E? =
        when (this) {
            is Ok -> null
            is Err -> {
                val (x) = this
                x
            }
        }

    // ////////////////////////////////////////////////////////////////////
    // Adapter for working with references
    // ////////////////////////////////////////////////////////////////////

    /**
     * Converts from `Result<T, E>` to `Result<T, E>`.
     *
     * Produces a new `Result`, containing a reference
     * into the original, leaving the original in place.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.asRef() == Result.success(2))
     * val x1: Result<UInt> = Result.failure("Error")
     * check(x1.asRef() == Result.failure("Error"))
     * ```
     */
    public open fun asRef(): Result<T, E> =
        when (this) {
            is Ok -> {
                val (x) = this
                Ok(x)
            }
            is Err -> {
                val (x1) = this
                Err(x1)
            }
        }

    /**
     * Converts from `Result<T, E>` to `Result<T, E>`.
     *
     * # Examples
     *
     * ```kotlin
     * fun mutate(r: Result<Int>) {
     *     run {
     *         val matchVal = r.asMut()
     *         if (matchVal.isSuccess) {
     *             val v = matchVal.getOrThrow()
     *             v = 42
     *         } else {
     *             val e = matchVal.exceptionOrNull()!!
     *             e = 0
     *         }
     *     }
     * }
     * var x: Result<Int> = Result.success(2)
     * mutate(x)
     * check(x.unwrap() == 42)
     * var x1: Result<Int> = Result.failure(13)
     * mutate(x1)
     * check(x1.unwrapErr() == 0)
     * ```
     */
    public open fun asMut(): Result<T, E> =
        when (this) {
            is Ok -> {
                val (x) = this
                Ok(x)
            }
            is Err -> {
                val (x1) = this
                Err(x1)
            }
        }

    // ////////////////////////////////////////////////////////////////////
    // Transforming contained values
    // ////////////////////////////////////////////////////////////////////

    /**
     * Maps a `Result<T, E>` to `Result<U, E>` by applying a function to a
     * contained [Ok] value, leaving an [Err] value untouched.
     *
     * This function can be used to compose the results of two functions.
     *
     * # Examples
     *
     * Print the numbers on each line of a string multiplied by two.
     *
     * ```kotlin
     * val line = "1\n2\n3\n4\n"
     * for (num in line.lines()) {
     *     run {
     *         val matchVal = (num.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).map({ i -> i * 2 })
     *         if (matchVal.isSuccess) {
     *             val n = matchVal.getOrThrow()
     *             println("$n")
     *         } else {}
     *     }
     * }
     * ```
     */
    public open fun <U> map(op: (T) -> U): Result<U, E> =
        when (this) {
            is Ok -> {
                val (t) = this
                Ok(op(t))
            }
            is Err -> {
                val (e) = this
                Err(e)
            }
        }

    /**
     * Returns the provided default (if [Err]), or
     * applies a function to the contained value (if [Ok]).
     *
     * The `default` argument passed to `mapOr` is eagerly evaluated; if you are passing
     * the result of a function call, it is recommended to use [mapOrElse],
     * which lazily evaluates `default`.
     *
     * [mapOrElse]: Result.mapOrElse
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<String> = Result.success("foo")
     * check(x.mapOr(42, { v -> v.len() }) == 3)
     * val x1: Result<String> = Result.failure("bar")
     * check(x1.mapOr(42, { v -> v.len() }) == 42)
     * ```
     */
    public open fun <U> mapOr(default: U, f: (T) -> U): U =
        when (this) {
            is Ok -> {
                val (t) = this
                f(t)
            }
            is Err -> default
        }

    /**
     * Maps a `Result<T, E>` to `U` by applying fallback function `default` to
     * a contained [Err] value, or function `f` to a contained [Ok] value.
     *
     * This function can be used to unpack a successful result
     * while handling an error.
     *
     * # Examples
     *
     * ```kotlin
     * val k = 21
     * val x: Result<String> = Result.success("foo")
     * check(x.mapOrElse({ e -> k * 2 }, { v -> v.len() }) == 3)
     * val x1: Result<String> = Result.failure("bar")
     * check(x1.mapOrElse({ e -> k * 2 }, { v -> v.len() }) == 42)
     * ```
     */
    public open fun <U> mapOrElse(default: (E) -> U, f: (T) -> U): U =
        when (this) {
            is Ok -> {
                val (t) = this
                f(t)
            }
            is Err -> {
                val (e) = this
                default(e)
            }
        }

    /**
     * Maps a `Result<T, E>` to a `U` by applying function `f` to the contained
     * value if the result is [Ok], otherwise if [Err], returns the
     * [default value] for the type `U`.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<String> = Result.success("foo")
     * val y: Result<String> = Result.failure("bar")
     * check(x.mapOrDefault({ x1 -> x1.len() }) == 3)
     * check(y.mapOrDefault({ y1 -> y1.len() }) == 0)
     * ```
     *
     * [default value]: Default.default
     */
    public open fun <U> mapOrDefault(f: (T) -> U, defaultUFn: () -> U): U =
        when (this) {
            is Ok -> {
                val (t) = this
                f(t)
            }
            is Err -> defaultUFn()
        }

    /**
     * Maps a `Result<T, E>` to `Result<T, F>` by applying a function to a
     * contained [Err] value, leaving an [Ok] value untouched.
     *
     * This function can be used to pass through a successful result while handling
     * an error.
     *
     * # Examples
     *
     * ```kotlin
     * fun stringify(x: UInt): String {
     *     return "error code: $x"
     * }
     * val x: Result<UInt> = Result.success(2u)
     * check(x.mapErr(::stringify) == Result.success(2))
     * val x1: Result<UInt> = Result.failure(13)
     * check(x1.mapErr(::stringify) == Result.failure("error code: 13".toString()))
     * ```
     */
    public open fun <F> mapErr(op: (E) -> F): Result<T, F> =
        when (this) {
            is Ok -> {
                val (t) = this
                Ok(t)
            }
            is Err -> {
                val (e) = this
                Err(op(e))
            }
        }

    /**
     * Calls a function with a reference to the contained value if [Ok].
     *
     * Returns the original result.
     *
     * # Examples
     *
     * ```kotlin
     * val x: UByte = ("4".toUByteOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse UByte"))).inspect({ x -> println("original: $x") }).map({ x -> x.pow(3) }).expect("literal `4` should parse as a `u8`")
     * ```
     */
    public open fun inspect(f: (T) -> Unit): Result<T, E> {
        if (this is Ok) {
            val (t) = this
            f(t)
        }
        return this
    }

    /**
     * Calls a function with a reference to the contained value if [Err].
     *
     * Returns the original result.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     * import std.io
     *
     * fun read(): io.Result<String> {
     *     return readToString("address.txt").inspectErr({ e -> println("failed to read file: $e") })
     * }
     * ```
     */
    public open fun inspectErr(f: (E) -> Unit): Result<T, E> {
        if (this is Err) {
            val (e) = this
            f(e)
        }
        return this
    }

    /**
     * Converts from `Result<T, E>` (or `Result<T, E>`) to `Result<<T as Deref>.Target, E>`.
     *
     * Coerces the [Ok] variant of the original [Result] via [`Deref`](ops.Deref)
     * and returns the new [Result].
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<String> = Result.success("hello".toString())
     * val y: Result<String> = Result.success("hello")
     * check(x.asDeref() == y)
     * val x1: Result<String> = Result.failure(42)
     * val y1: Result<String> = Result.failure(42)
     * check(x1.asDeref() == y1)
     * ```
     */
    public open fun <Target> asDeref(): Result<Target, E> = this.asRef().map({ Deref.deref(it) })

    /**
     * Converts from `Result<T, E>` (or `Result<T, E>`) to `Result<<T as DerefMut>.Target, E>`.
     *
     * Coerces the [Ok] variant of the original [Result] via [`DerefMut`](ops.DerefMut)
     * and returns the new [Result].
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     *
     * var s = "HELLO".toString()
     * var x: Result<String> = Result.success("hello".toString())
     * val y: Result<String> = Result.success(s)
     * check(x.asDerefMut().map({ x1 ->
     *     x1.makeAsciiUppercase()
     *     x1
     * }) == y)
     * val localStorageL8C0 = AddressableLocal(42, PrimitiveLayouts.I32)
     * var x1: Result<String> = Result.failure(42)
     * val y1: Result<String> = Result.failure(RustMutableReference(localStorageL8C0.asMutPtr(), localStorageL8C0.layout))
     * check(x1.asDerefMut().map({ x2 ->
     *     x2.makeAsciiUppercase()
     *     x2
     * }) == y1)
     * ```
     */
    public open fun asDerefMut(): Result<Target, E> = this.asMut().map({ DerefMut.derefMut(it) })

    // ////////////////////////////////////////////////////////////////////
    // Iterator constructors
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns an iterator over the possibly contained value.
     *
     * The iterator yields one value if the result is [Result.Ok], otherwise none.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(7u)
     * check(x.iter().next() == 7)
     * val x1: Result<UInt> = Result.failure("nothing!")
     * check(x1.iter().next() == null)
     * ```
     */
    public open fun iter(): Iter<T> = Iter(inner = this.asRef().ok())

    /**
     * Returns a mutable iterator over the possibly contained value.
     *
     * The iterator yields one value if the result is [Result.Ok], otherwise none.
     *
     * # Examples
     *
     * ```kotlin
     * var x: Result<UInt> = Result.success(7u)
     * when (val matchVal = x.iterMut().next()) {
     *     null -> {}
     *     else -> {
     *         val v = matchVal
     *         v = 40
     *     }
     * }
     * check(x == Result.success(40))
     * var x1: Result<UInt> = Result.failure("nothing!")
     * check(x1.iterMut().next() == null)
     * ```
     */
    public open fun iterMut(): IterMut<T> = IterMut(inner = this.asMut().ok())

    // ////////////////////////////////////////////////////////////////////
    // Extract a value
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns the contained [Ok] value, consuming the `self` value.
     *
     * Because this function may panic, its use is generally discouraged.
     * Instead, prefer to use pattern matching and handle the [Err]
     * case explicitly, or call [unwrapOr], [unwrapOrElse], or
     * [unwrapOrDefault].
     *
     * [unwrapOr]: Result.unwrapOr
     * [unwrapOrElse]: Result.unwrapOrElse
     * [unwrapOrDefault]: Result.unwrapOrDefault
     *
     * # Panics
     *
     * Panics if the value is an [Err], with a panic message including the
     * passed message, and the content of the [Err].
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.failure("emergency failure")
     * x.expect("Testing expect")
     * // panics with `Testing expect: emergency failure`
     * ```
     *
     * # Recommended Message Style
     *
     * We recommend that `expect` messages are used to describe the reason you
     * _expect_ the `Result` should be `Ok`.
     *
     * ```kotlin
     * val path = variable("IMPORTANT_PATH").expect("env variable `IMPORTANT_PATH` should be set by `wrapperScript.sh`")
     * ```
     *
     * **Hint**: If you're having trouble remembering how to phrase expect
     * error messages remember to focus on the word "should" as in "env
     * variable should be set by blah" or "the given binary should be available
     * and executable by the current user".
     *
     * For more detail on expect message styles and the reasoning behind our recommendation please
     * refer to the section on ["Common Message
     * Styles"](../../std/error/index.html#common-message-styles) in the
     * [`std.error`](../../std/error/index.html) module docs.
     */
    public open fun expect(msg: String): T =
        when (this) {
            is Ok -> {
                val (t) = this
                t
            }
            is Err -> {
                val (e) = this
                unwrapFailed(msg, e)
            }
        }

    /**
     * Returns the contained [Ok] value, consuming the `self` value.
     *
     * Because this function may panic, its use is generally discouraged.
     * Panics are meant for unrecoverable errors, and
     * [may abort the entire program][panic-abort].
     *
     * Instead, prefer to use [the `?` (try) operator][try-operator], or pattern matching
     * to handle the [Err] case explicitly, or call [unwrapOr],
     * [unwrapOrElse], or [unwrapOrDefault].
     *
     * [panic-abort]: https://doc.rust-lang.org/book/ch09-01-unrecoverable-errors-with-panic.html
     * [try-operator]: https://doc.rust-lang.org/book/ch09-02-recoverable-errors-with-result.html#a-shortcut-for-propagating-errors-the--operator
     * [unwrapOr]: Result.unwrapOr
     * [unwrapOrElse]: Result.unwrapOrElse
     * [unwrapOrDefault]: Result.unwrapOrDefault
     *
     * # Panics
     *
     * Panics if the value is an [Err], with a panic message provided by the
     * [Err]s value.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.unwrap() == 2)
     * ```
     *
     * ```kotlin
     * val x: Result<UInt> = Result.failure("emergency failure")
     * x.unwrap()
     * // panics with `emergency failure`
     * ```
     */
    public open fun unwrap(): T =
        when (this) {
            is Ok -> {
                val (t) = this
                t
            }
            is Err -> {
                val (e) = this
                unwrapFailed("called `Result::unwrap()` on an `Err` value", e)
            }
        }

    /**
     * Returns the contained [Ok] value or a default
     *
     * Consumes the `self` argument then, if [Ok], returns the contained
     * value, otherwise if [Err], returns the default value for that
     * type.
     *
     * # Examples
     *
     * Converts a string to an integer, turning poorly-formed strings
     * into 0 (the default value for integers). [parse] converts
     * a string to any other type that implements [FromStr], returning an
     * [Err] on error.
     *
     * ```kotlin
     * val goodYearFromInput = "1909"
     * val badYearFromInput = "190blarg"
     * val goodYear = goodYearFromInput.parse().unwrapOrDefault()
     * val badYear = badYearFromInput.parse().unwrapOrDefault()
     * check(1909 == goodYear)
     * check(0 == badYear)
     * ```
     *
     * [parse]: str.parse
     */
    public open fun unwrapOrDefault(defaultTFn: () -> T): T =
        when (this) {
            is Ok -> {
                val (x) = this
                x
            }
            is Err -> defaultTFn()
        }

    /**
     * Returns the contained [Err] value, consuming the `self` value.
     *
     * # Panics
     *
     * Panics if the value is an [Ok], with a panic message including the
     * passed message, and the content of the [Ok].
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(10u)
     * x.expectErr("Testing expectErr")
     * // panics with `Testing expectErr: 10`
     * ```
     */
    public open fun expectErr(msg: String): E =
        when (this) {
            is Ok -> {
                val (t) = this
                unwrapFailed(msg, t)
            }
            is Err -> {
                val (e) = this
                e
            }
        }

    /**
     * Returns the contained [Err] value, consuming the `self` value.
     *
     * # Panics
     *
     * Panics if the value is an [Ok], with a custom panic message provided
     * by the [Ok]s value.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * x.unwrapErr()
     * // panics with `2`
     * ```
     *
     * ```kotlin
     * val x: Result<UInt> = Result.failure("emergency failure")
     * check(x.unwrapErr() == "emergency failure")
     * ```
     */
    public open fun unwrapErr(): E =
        when (this) {
            is Ok -> {
                val (t) = this
                unwrapFailed("called `Result::unwrap_err()` on an `Ok` value", t)
            }
            is Err -> {
                val (e) = this
                e
            }
        }

    /**
     * Returns the contained [Ok] value, but never panics.
     *
     * Unlike [unwrap], this method is known to never panic on the
     * result types it is implemented for. Therefore, it can be used
     * instead of `unwrap` as a maintainability safeguard that will fail
     * to compile if the error type of the `Result` is later changed
     * to an error that can actually occur.
     *
     * [unwrap]: Result.unwrap
     *
     * # Examples
     *
     * ```kotlin
     * fun onlyGoodNews(): Result<String> {
     *     return Result.success("this is fine".into())
     * }
     * val s: String = onlyGoodNews().intoOk()
     * println("$s")
     * ```
     */
    public open fun intoOk(): T =
        when (this) {
            is Ok -> {
                val (x) = this
                x
            }
            is Err -> {
                val (e) = this
                e.into()
            }
        }

    /**
     * Returns the contained [Err] value, but never panics.
     *
     * Unlike [unwrapErr], this method is known to never panic on the
     * result types it is implemented for. Therefore, it can be used
     * instead of `unwrapErr` as a maintainability safeguard that will fail
     * to compile if the ok type of the `Result` is later changed
     * to a type that can actually occur.
     *
     * [unwrapErr]: Result.unwrapErr
     *
     * # Examples
     *
     * ```kotlin
     * fun onlyBadNews(): Result<Nothing> {
     *     return Result.failure("Oops, it failed".into())
     * }
     * val error: String = onlyBadNews().intoErr()
     * println("$error")
     * ```
     */
    public open fun intoErr(): E =
        when (this) {
            is Ok -> {
                val (x) = this
                x.into()
            }
            is Err -> {
                val (e) = this
                e
            }
        }

    // ///////////////////////////////////////////////////////////////////
    // Boolean operations on the values, eager and lazy
    // ////////////////////////////////////////////////////////////////////

    /**
     * Returns `res` if the result is [Ok], otherwise returns the [Err] value of `self`.
     *
     * Arguments passed to `and` are eagerly evaluated; if you are passing the
     * result of a function call, it is recommended to use [andThen], which is
     * lazily evaluated.
     *
     * [andThen]: Result.andThen
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * val y: Result<String> = Result.failure("late error")
     * check(x.and(y) == Result.failure("late error"))
     * val x1: Result<UInt> = Result.failure("early error")
     * val y1: Result<String> = Result.success("foo")
     * check(x1.and(y1) == Result.failure("early error"))
     * val x2: Result<UInt> = Result.failure("not a 2")
     * val y2: Result<String> = Result.failure("late error")
     * check(x2.and(y2) == Result.failure("not a 2"))
     * val x3: Result<UInt> = Result.success(2u)
     * val y3: Result<String> = Result.success("different result type")
     * check(x3.and(y3) == Result.success("different result type"))
     * ```
     */
    public open fun <U> and(res: Result<U, E>): Result<U, E> =
        when (this) {
            is Ok -> res
            is Err -> {
                val (e) = this
                Err(e)
            }
        }

    /**
     * Calls `op` if the result is [Ok], otherwise returns the [Err] value of `self`.
     *
     * This function can be used for control flow based on `Result` values.
     *
     * # Examples
     *
     * ```kotlin
     * fun sqThenToString(x: UInt): Result<String> {
     *     return (x.checkedMul(x)?.let({ sq -> sq.toString() })?.let { Result.success(it) } ?: Result.failure("overflowed"))
     * }
     * check(Result.success(2).andThen(::sqThenToString) == Result.success(4.toString()))
     * check(Result.success(1000000).andThen(::sqThenToString) == Result.failure("overflowed"))
     * check(Result.failure("not a number").andThen(::sqThenToString) == Result.failure("not a number"))
     * ```
     *
     * Often used to chain fallible operations that may return [Err].
     *
     * ```kotlin
     * import std.io.ErrorKind
     * import std.path.Path
     *
     * // Note: on Windows "/" maps to "C:\"
     * val rootModifiedTime = Path.new("/").metadata().andThen({ md -> md.modified() })
     * check(rootModifiedTime.isOk())
     * val shouldFail = Path.new("/bad/path").metadata().andThen({ md -> md.modified() })
     * check(shouldFail.isErr())
     * check(shouldFail.unwrapErr().kind() == ErrorKind.NotFound)
     * ```
     */
    public open fun <U> andThen(op: (T) -> Result<U, E>): Result<U, E> =
        when (this) {
            is Ok -> {
                val (t) = this
                op(t)
            }
            is Err -> {
                val (e) = this
                Err(e)
            }
        }

    /**
     * Returns `res` if the result is [Err], otherwise returns the [Ok] value of `self`.
     *
     * Arguments passed to `or` are eagerly evaluated; if you are passing the
     * result of a function call, it is recommended to use [orElse], which is
     * lazily evaluated.
     *
     * [orElse]: Result.orElse
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * val y: Result<UInt> = Result.failure("late error")
     * check(x.or(y) == Result.success(2))
     * val x1: Result<UInt> = Result.failure("early error")
     * val y1: Result<UInt> = Result.success(2u)
     * check(x1.or(y1) == Result.success(2))
     * val x2: Result<UInt> = Result.failure("not a 2")
     * val y2: Result<UInt> = Result.failure("late error")
     * check(x2.or(y2) == Result.failure("late error"))
     * val x3: Result<UInt> = Result.success(2u)
     * val y3: Result<UInt> = Result.success(100u)
     * check(x3.or(y3) == Result.success(2))
     * ```
     */
    public open fun <F> or(res: Result<T, F>): Result<T, F> =
        when (this) {
            is Ok -> {
                val (v) = this
                Ok(v)
            }
            is Err -> res
        }

    /**
     * Calls `op` if the result is [Err], otherwise returns the [Ok] value of `self`.
     *
     * This function can be used for control flow based on result values.
     *
     * # Examples
     *
     * ```kotlin
     * fun sq(x: UInt): Result<UInt> {
     *     return Result.success(x * x)
     * }
     * fun err(x: UInt): Result<UInt> {
     *     return Result.failure(x)
     * }
     * check(Result.success(2).orElse(::sq).orElse(::sq) == Result.success(2))
     * check(Result.success(2).orElse(::err).orElse(::sq) == Result.success(2))
     * check(Result.failure(3).orElse(::sq).orElse(::err) == Result.success(9))
     * check(Result.failure(3).orElse(::err).orElse(::err) == Result.failure(3))
     * ```
     */
    public open fun <F> orElse(op: (E) -> Result<T, F>): Result<T, F> =
        when (this) {
            is Ok -> {
                val (t) = this
                Ok(t)
            }
            is Err -> {
                val (e) = this
                op(e)
            }
        }

    /**
     * Returns the contained [Ok] value or a provided default.
     *
     * Arguments passed to `unwrapOr` are eagerly evaluated; if you are passing
     * the result of a function call, it is recommended to use [unwrapOrElse],
     * which is lazily evaluated.
     *
     * [unwrapOrElse]: Result.unwrapOrElse
     *
     * # Examples
     *
     * ```kotlin
     * val default = 2
     * val x: Result<UInt> = Result.success(9u)
     * check(x.unwrapOr(default) == 9)
     * val x1: Result<UInt> = Result.failure("error")
     * check(x1.unwrapOr(default) == default)
     * ```
     */
    public open fun unwrapOr(default: T): T =
        when (this) {
            is Ok -> {
                val (t) = this
                t
            }
            is Err -> default
        }

    /**
     * Returns the contained [Ok] value or computes it from a closure.
     *
     * # Examples
     *
     * ```kotlin
     * fun count(x: String): Int {
     *     return x.length
     * }
     * check(Result.success(2).unwrapOrElse(::count) == 2)
     * check(Result.failure("foo").unwrapOrElse(::count) == 3)
     * ```
     */
    public open fun unwrapOrElse(op: (E) -> T): T =
        when (this) {
            is Ok -> {
                val (t) = this
                t
            }
            is Err -> {
                val (e) = this
                op(e)
            }
        }

    /**
     * Returns the contained [Ok] value, consuming the `self` value,
     * without checking that the value is not an [Err].
     *
     * # Safety
     *
     * Calling this method on an [Err] is *[undefined behavior]*.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * check(x.unwrapUnchecked() == 2)
     * ```
     *
     * ```kotlin
     * val x: Result<UInt> = Result.failure("emergency failure")
     * x.unwrapUnchecked()
     * // Undefined behavior!
     * ```
     */
    public open fun unwrapUnchecked(): T =
        when (this) {
            is Ok -> {
                val (t) = this
                t
            }
            is Err -> {
                val (e) = this
                // Note(const-Note): to avoid E: const Destruct bound
                forget(e)
                // SAFETY: the safety contract must be upheld by the caller.
                unreachableUnchecked()
            }
        }

    /**
     * Returns the contained [Err] value, consuming the `self` value,
     * without checking that the value is not an [Ok].
     *
     * # Safety
     *
     * Calling this method on an [Ok] is *[undefined behavior]*.
     *
     * [undefined behavior]: https://doc.rust-lang.org/reference/behavior-considered-undefined.html
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(2u)
     * x.unwrapErrUnchecked()
     * // Undefined behavior!
     * ```
     *
     * ```kotlin
     * val x: Result<UInt> = Result.failure("emergency failure")
     * check(x.unwrapErrUnchecked() == "emergency failure")
     * ```
     */
    public open fun unwrapErrUnchecked(): E =
        when (this) {
            // SAFETY: the safety contract must be upheld by the caller.
            is Ok -> unreachableUnchecked()
            is Err -> {
                val (e) = this
                e
            }
        }

    // ////////////////////////////////////////////////////////////////////////
    // Trait implementations
    // ////////////////////////////////////////////////////////////////////////
    public override fun clone(cloneEFn: (E) -> E = { it }): Result<T, E> =
        when (this) {
            is Ok -> {
                val (x) = this
                Ok(x.clone())
            }
            is Err -> {
                val (x1) = this
                Err(cloneEFn(x1))
            }
        }

    public override fun cloneFrom(source: Result<T, E>, cloneEFn: (E) -> E = { it }) {
        run {
            val matchVal = Pair(this, source)
            when {
                matchVal.first is Ok && matchVal.second is Ok -> {
                    val (to) = matchVal.first as Ok<T, E>
                    val (from) = matchVal.second as Ok<T, E>
                    to.cloneFrom(from)
                }
                matchVal.first is Err && matchVal.second is Err -> {
                    val (to1) = matchVal.first as Err<T, E>
                    val (from1) = matchVal.second as Err<T, E>
                    to1.cloneFrom(from1)
                }
                else -> {
                    val to2 = matchVal.first
                    val from2 = matchVal.second
                    to2 = from2.clone()
                }
            }
        }
    }

    /**
     * Returns a consuming iterator over the possibly contained value.
     *
     * The iterator yields one value if the result is [Result.Ok], otherwise none.
     *
     * # Examples
     *
     * ```kotlin
     * val x: Result<UInt> = Result.success(5u)
     * val v: List<UInt> = x.intoIter().toMutableList()
     * check(v == listOf(5u))
     * val x1: Result<UInt> = Result.failure("nothing!")
     * val v1: List<UInt> = x1.intoIter().toMutableList()
     * check(v1 == listOf())
     * ```
     */
    public open fun intoIter(): IntoIter<T> = IntoIter(inner = this.ok())

    public override fun branch(): ControlFlow<Result<Nothing, E>, T> =
        when (this) {
            is Ok -> {
                val (v) = this
                ControlFlow.Continue(v)
            }
            is Err -> {
                val (e) = this
                ControlFlow.Break(Err(e))
            }
        }

    public companion object {
        public fun <T, E> fromOutput(output: T): Result<T, E> = Ok(output)
    }
}

/**
 * Maps a `Result<T, E>` to a `Result<T, E>` by copying the contents of the
 * `Ok` part.
 *
 * # Examples
 *
 * ```kotlin
 * val value = 12
 * val x: Result<Int> = Result.success(value)
 * check(x == Result.success(12))
 * val copied = x.copied()
 * check(copied == Result.success(12))
 * ```
 */
public fun <T, E> Result<T, E>.copied(): Result<T, E> {
    // Note(const-Note): this implementation, which sidesteps using `Result.map` since it's not const
    // ready yet, should be reverted when possible to avoid code repetition
    return when (this) {
        is Ok -> {
            val (v) = this
            Ok(v)
        }
        is Err -> {
            val (e) = this
            Err(e)
        }
    }
}

/**
 * Maps a `Result<T, E>` to a `Result<T, E>` by cloning the contents of the
 * `Ok` part.
 *
 * # Examples
 *
 * ```kotlin
 * val value = 12
 * val x: Result<Int> = Result.success(value)
 * check(x == Result.success(12))
 * val cloned = x.cloned()
 * check(cloned == Result.success(12))
 * ```
 */
public fun <T, E> Result<T, E>.cloned(): Result<T, E> = this.map({ t -> t.clone() })

/**
 * Transposes a `Result` of an `Option` into an `Option` of a `Result`.
 *
 * `Ok(None)` will be mapped to `None`.
 * `Ok(Some(_))` and `Err(_)` will be mapped to `Some(Ok(_))` and `Some(Err(_))`.
 *
 * # Examples
 *
 * ```kotlin
 * open class SomeErr
 * val x: Result<Int?> = Result.success(5)
 * val y: Result<Int>? = Result.success(5)
 * check(x.transpose() == y)
 * ```
 */
public fun <T, E> Result<T?, E>.transpose(): Result<T, E>? =
    when {
        this is Ok && (this as Ok<T, E>).value != null -> {
            val x = (this as Ok<T, E>).value
            Ok(x)
        }
        this is Ok && (this as Ok<T, E>).value == null -> {
            val (None) = this as Ok<T, E>
            null
        }
        else -> {
            val (e) = this
            Err(e)
        }
    }

/**
 * Converts from `Result<Result<T, E>, E>` to `Result<T, E>`
 *
 * # Examples
 *
 * ```kotlin
 * val x: Result<Result<String>> = Result.success(Result.success("hello"))
 * check(Result.success("hello") == x.flatten())
 * val x1: Result<Result<String>> = Result.success(Result.failure(6))
 * check(Result.failure(6) == x1.flatten())
 * val x2: Result<Result<String>> = Result.failure(6)
 * check(Result.failure(6) == x2.flatten())
 * ```
 *
 * Flattening only removes one level of nesting at a time:
 *
 * ```kotlin
 * val x: Result<Result<Result<String>>> = Result.success(Result.success(Result.success("hello")))
 * check(Result.success(Result.success("hello")) == x.flatten())
 * check(Result.success("hello") == x.flatten().flatten())
 * ```
 */
public fun <T, E> Result<Result<T, E>, E>.flatten(): Result<T, E> {
    // Note(const-Note): could be written with `andThen`
    return when (this) {
        is Ok -> {
            val (inner) = this
            inner
        }
        is Err -> {
            val (e) = this
            Err(e)
        }
    }
}

// This is a separate function to reduce the code size of the methods
//
// Take `error` by value so that `unwrap` doesn't need to include a landing pad to drop `error`
// during unwinding. This reduces the size of `unwrap` and allows it to be MIR inlined.
public fun <T> unwrapFailed(msg: String, error: T): Nothing {
    // Do the actual formatting with a `dynamic Debug` to reduce code size.
    fun unwrapFailedInner(msg1: String, error1: Any): Nothing {
        error("$msg: $error")
    }
    return unwrapFailedInner(msg, error)
}

// ////////////////////////////////////////////////////////////////////////
// The Result Iterators
// ////////////////////////////////////////////////////////////////////////

/**
 * An iterator over a reference to the [Ok] variant of a [Result].
 *
 * The iterator yields one value if the result is [Ok], otherwise none.
 *
 * Created by [Result.iter].
 */
public open class Iter<T>(
    public var inner: T?,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? = this.inner.also { this.inner = null }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n =
            if (this.inner != null) {
                1
            } else {
                0
            }
        return Pair(n, n)
    }

    public open fun nextBack(): T? = this.inner.also { this.inner = null }

    public open fun clone(): Iter<T> = Iter(inner = this.inner)
}

/**
 * An iterator over a mutable reference to the [Ok] variant of a [Result].
 *
 * Created by [Result.iterMut].
 */
public open class IterMut<T>(
    public var inner: T?,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IterMut.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? = this.inner.also { this.inner = null }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n =
            if (this.inner != null) {
                1
            } else {
                0
            }
        return Pair(n, n)
    }

    public open fun nextBack(): T? = this.inner.also { this.inner = null }
}

/**
 * An iterator over the value in a [Ok] variant of a [Result].
 *
 * The iterator yields one value if the result is [Ok], otherwise none.
 *
 * This struct is created by the [intoIter] method on
 * [Result] (provided by the [IntoIterator] trait).
 *
 * [intoIter]: IntoIterator.intoIter
 */
public open class IntoIter<T>(
    public var inner: T?,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IntoIter<T> = IntoIter(inner)

    public open fun next(): T? = this.inner.also { this.inner = null }

    public open fun sizeHint(): Pair<Int, Int?> {
        val n =
            if (this.inner != null) {
                1
            } else {
                0
            }
        return Pair(n, n)
    }

    public open fun nextBack(): T? = this.inner.also { this.inner = null }
}

// ////////////////////////////////////////////////////////////////////////
// FromIterator
// ////////////////////////////////////////////////////////////////////////

/**
 * Takes each element in the `Iterator`: if it is an `Err`, no further
 * elements are taken, and the `Err` is returned. Should no `Err` occur, a
 * container with the values of each `Result` is returned.
 *
 * Here is an example which increments every integer in a vector,
 * checking for overflow:
 *
 * ```kotlin
 * val v = mutableListOf(1, 2)
 * val res: Result<List<UInt>> = ArrayDeque((ArrayDeque(v)).map({ x: UInt -> (x.checkedAdd(1)?.let { Result.success(it) } ?: Result.failure("Overflow!")) })).collect()
 * check(res == Result.success(mutableListOf(2, 3)))
 * ```
 *
 * Here is another example that tries to subtract one from another list
 * of integers, this time checking for underflow:
 *
 * ```kotlin
 * val v = mutableListOf(1, 2, 0)
 * val res: Result<List<UInt>> = ArrayDeque((ArrayDeque(v)).map({ x: UInt -> (x.checkedSub(1)?.let { Result.success(it) } ?: Result.failure("Underflow!")) })).collect()
 * check(res == Result.failure("Underflow!"))
 * ```
 *
 * Here is a variation on the previous example, showing that no
 * further elements are taken from `iter` after the first `Err`.
 *
 * ```kotlin
 * val v = mutableListOf(3, 2, 1, 10)
 * var shared = 0
 * val res: Result<List<UInt>> = ArrayDeque((ArrayDeque(v)).map({ x: UInt ->
 *     shared += x
 *     (x.checkedSub(2)?.let { Result.success(it) } ?: Result.failure("Underflow!"))
 * })).collect()
 * check(res == Result.failure("Underflow!"))
 * check(shared == 6)
 * ```
 *
 * Since the third element caused an underflow, no further elements were taken,
 * so the final value of `shared` is 6 (= `3 + 2 + 1`), not 16.
 */
public fun <T, E, V, I : Iterable<Result<T, E>>> fromIteratorFromIterResultVE(iter: I): Result<V, E> = Iter.tryProcess(iter.iterator(), { i -> i.collect() })

public fun <T, E, F> fromResidualResultTF(residual: Result<Nothing, E>): Result<T, F> =
    when (residual) {
        is Err -> {
            val (e) = residual
            Err(From.from(e))
        }
    }

public fun <T, E, F> fromResidualResultTF(destructuredArg0: Yeet<E>): Result<T, F> = Err(From.from(e))
