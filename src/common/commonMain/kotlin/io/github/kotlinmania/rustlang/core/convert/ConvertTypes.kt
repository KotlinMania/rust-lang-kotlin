package io.github.kotlinmania.rustlang.core.convert

import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Used to do a cheap reference-to-reference conversion.
 *
 * This trait is similar to [AsMut] which is used for converting between mutable references.
 * If you need to do a costly conversion it is better to implement [From] with type
 * `T` or write a custom function.
 *
 * # Relation to `Borrow`
 *
 * `AsRef` has the same signature as [Borrow], but [Borrow] is different in a few aspects:
 *
 * - Unlike `AsRef`, [Borrow] has a blanket impl for any `T`, and can be used to accept either
 *   a reference or a value. (See also note on `AsRef`s reflexibility below.)
 * - [Borrow] also requires that [Hash], [Eq] and [Ord] for a borrowed value are
 *   equivalent to those of the owned value. For this reason, if you want to
 *   borrow only a single field of a struct you can implement `AsRef`, but not [Borrow].
 *
 * **Note: This trait must not fail**. If the conversion can fail, use a
 * dedicated method which returns an [Option.T] or a [Result.T.E].
 *
 * # Generic Implementations
 *
 * `AsRef` auto-dereferences if the inner type is a reference or a mutable reference
 * (e.g.: `foo.asRef()` will work the same if `foo` has type `Foo` or `&& Foo`).
 *
 * Note that due to historic reasons, the above currently does not hold generally for all
 * [dereferenceable types], e.g. `foo.asRef()` will *not* work the same as
 * `Holder.new(foo).asRef()`. Instead, many smart pointers provide an `asRef` implementation which
 * simply returns a reference to the [pointed-to value] (but do not perform a cheap
 * reference-to-reference conversion for that value). However, [AsRef.asRef] should not be
 * used for the sole purpose of dereferencing; instead ['`Deref` coercion'] can be used:
 *
 * ```kotlin
 * val x = 5
 * // Avoid this:
 * // let y: &i32 = x.asRef();
 * // Better just write:
 * val y: Int = x
 * ```
 *
 * Types which implement [Deref] should consider implementing `AsRef<T>` as follows:
 *
 * ```kotlin
 * fun <T> SomeType.asRef(): T {
 *     return this.deref().asRef()
 * }
 * ```
 *
 * # Reflexivity
 *
 * Ideally, `AsRef` would be reflexive, i.e. there would be an `implementation<T: ? Sized> AsRef<T> for T`
 * with [asRef] simply returning its argument unchanged.
 * Such a blanket implementation is currently *not* provided due to technical restrictions of
 * Rust's type system (it would be overlapping with another existing blanket implementation for
 * `T where T: AsRef<U>` which allows `AsRef` to auto-dereference, see "Generic Implementations"
 * above).
 *
 * [asRef]: AsRef.asRef
 *
 * A trivial implementation of `AsRef<T> for T` must be added explicitly for a particular type `T`
 * where needed or desired. Note, however, that not all types from `std` contain such an
 * implementation, and those cannot be added by external code due to orphan rules.
 *
 * # Examples
 *
 * By using trait bounds we can accept arguments of different types as long as they can be
 * converted to the specified type `T`.
 *
 * For example: By creating a generic function that takes an `AsRef<String>` we express that we
 * want to accept all references that can be converted to [str] as an argument.
 * Since both [String] and [str] implement `AsRef<String>` we can accept both as input argument.
 *
 * [str]: primitive@str
 * [String]: ../../std/string/struct.String.html
 *
 * ```kotlin
 * fun <T : AsRef<String>> isHello(s: T) {
 *     check("hello" == s.asRef())
 * }
 * val s = "hello"
 * isHello(s)
 * val s1 = "hello".toString()
 * isHello(s1)
 * ```
 */
public interface AsRef<T> {
    /**
     * Converts this type into a shared reference of the (usually inferred) input type.
     */
    public fun asRef(): T
}

/**
 * Used to do a cheap mutable-to-mutable reference conversion.
 *
 * This trait is similar to [AsRef] but used for converting between mutable
 * references. If you need to do a costly conversion it is better to
 * implement [From] with type `T` or write a custom function.
 *
 * **Note: This trait must not fail**. If the conversion can fail, use a
 * dedicated method which returns an [Option.T] or a [Result.T.E].
 *
 * # Generic Implementations
 *
 * `AsMut` auto-dereferences if the inner type is a mutable reference
 * (e.g.: `foo.asMut()` will work the same if `foo` has type `Foo` or `Foo`).
 *
 * Note that due to historic reasons, the above currently does not hold generally for all
 * [mutably dereferenceable types], e.g. `foo.asMut()` will *not* work the same as
 * `Holder.new(foo).asMut()`. Instead, many smart pointers provide an `asMut` implementation which
 * simply returns a reference to the [pointed-to value] (but do not perform a cheap
 * reference-to-reference conversion for that value). However, [AsMut.asMut] should not be
 * used for the sole purpose of mutable dereferencing; instead ['`Deref` coercion'] can be used:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 *
 * var x = 5
 * // Avoid this:
 * // let y: i32 = x.asMut();
 * // Better just write:
 * val y: RustMutableReference<Int> = x
 * ```
 *
 * Types which implement [DerefMut] should consider to add an implementation of `AsMut<T>` as
 * follows:
 *
 * ```kotlin
 * fun <T> SomeType.asMut(): T {
 *     return this.derefMut().asMut()
 * }
 * ```
 *
 * # Reflexivity
 *
 * Ideally, `AsMut` would be reflexive, i.e. there would be an `implementation<T: ? Sized> AsMut<T> for T`
 * with [asMut] simply returning its argument unchanged.
 * Such a blanket implementation is currently *not* provided due to technical restrictions of
 * Rust's type system (it would be overlapping with another existing blanket implementation for
 * `T where T: AsMut<U>` which allows `AsMut` to auto-dereference, see "Generic
 * Implementations" above).
 *
 * [asMut]: AsMut.asMut
 *
 * A trivial implementation of `AsMut<T> for T` must be added explicitly for a particular type `T`
 * where needed or desired. Note, however, that not all types from `std` contain such an
 * implementation, and those cannot be added by external code due to orphan rules.
 *
 * # Examples
 *
 * Using `AsMut` as trait bound for a generic function, we can accept all mutable references that
 * can be converted to type `T`. Unlike [dereference], which has a single [target type],
 * there can be multiple implementations of `AsMut` for a type. In particular, `List<T>` implements
 * both `AsMut<List<T>>` and `AsMut<[T]>`.
 *
 * In the following, the example functions `caesar` and `nullTerminate` provide a generic
 * interface which works with any type that can be converted by cheap mutable-to-mutable conversion
 * into a byte slice (`[UByte]`) or a byte vector (`List<UByte>`), respectively.
 *
 * ```kotlin
 * open class Document(public var info: String, public var content: List<UByte>) : AsMut<T> {
 *     public override fun asMut(): T {
 *         return this.content.asMut()
 *     }
 * }
 * fun <T : AsMut<List<UByte>>> caesar(data: T, key: UByte) {
 *     for (byte in data.asMut()) {
 *         byte = byte.wrappingAdd(key)
 *     }
 * }
 * fun <T : AsMut<List<UByte>>> nullTerminate(data: T) {
 *     // Using a non-generic inner function, which contains most of the
 *     // functionality, helps to minimize monomorphization overhead.
 *     fun doit(data1: List<UByte>) {
 *         val len = data1.size
 *         if (len == 0 || data1[len - 1] != 0u.toUByte()) {
 *             data1.push(0)
 *         }
 *     }
 *     doit(data.asMut())
 * }
 * fun main() {
 *     var v: List<UByte> = mutableListOf(1u, 2u, 3u)
 *     caesar(v, 5u)
 *     check(v == listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte()))
 *     nullTerminate(v)
 *     check(v == listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 0u.toUByte()))
 *     var doc = Document(info = String.From("Example"), content = mutableListOf(17u, 19u, 8u))
 *     caesar(doc, 1u)
 *     check(doc.content == listOf(18u.toUByte(), 20u.toUByte(), 9u.toUByte()))
 *     nullTerminate(doc)
 *     check(doc.content == listOf(18u.toUByte(), 20u.toUByte(), 9u.toUByte(), 0u.toUByte()))
 * }
 * ```
 *
 * Note, however, that APIs don't need to be generic. In many cases taking a `[UByte]` or
 * `List<UByte>`, for example, is the better choice (callers need to pass the correct type then).
 */
public interface AsMut<T> {
    /**
     * Converts this type into a mutable reference of the (usually inferred) input type.
     */
    public fun asMut(): T
}

/**
 * A value-to-value conversion that consumes the input value. The
 * opposite of [From].
 *
 * One should avoid implementing [Into] and implement [From] instead.
 * Implementing [From] automatically provides one with an implementation of [Into]
 * thanks to the blanket implementation in the standard library.
 *
 * Prefer using [Into] over [From] when specifying trait bounds on a generic function
 * to ensure that types that only implement [Into] can be used as well.
 *
 * **Note: This trait must not fail**. If the conversion can fail, use [TryInto].
 *
 * # Generic Implementations
 *
 * - [From]`<T> for U` implies `Into<U> for T`
 * - [Into] is reflexive, which means that `Into<T> for T` is implemented
 *
 * # Implementing [Into] for conversions to external types in old versions of Rust
 *
 * Prior to Rust 1.41, if the destination type was not part of the current crate
 * then you couldn't implement [From] directly.
 * For example, take this code:
 *
 * ```kotlin
 * open class Wrapper<T>(public var value: List<T>) {
 *     public var field0: List<T>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 * }
 * fun <T> fromVecT(w: Wrapper<T>): List<T> {
 *     return w.value
 * }
 * ```
 * This will fail to compile in older versions of the language because Rust's orphaning rules
 * used to be a little bit more strict. To bypass this, you could implement [Into] directly:
 *
 * ```kotlin
 * open class Wrapper<T>(public var value: List<T>) : Into<List<T>> {
 *     public var field0: List<T>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public override fun into(): List<T> {
 *         return this.value
 *     }
 * }
 * ```
 *
 * It is important to understand that [Into] does not provide a [From] implementation
 * (as [From] does with [Into]). Therefore, you should always try to implement [From]
 * and then fall back to [Into] if [From] can't be implemented.
 *
 * # Examples
 *
 * [String] implements [Into]`<`[Vec]`<`[u8]`>>`:
 *
 * In order to express that we want a generic function to take all arguments that can be
 * converted to a specified type `T`, we can use a trait bound of [Into]`<T>`.
 * For example: The function `isHello` takes all arguments that can be converted into a
 * [Vec]`<`[u8]`>`.
 *
 * ```kotlin
 * fun <T : Into<List<UByte>>> isHello(s: T) {
 *     val bytes = "hello".encodeToByteArray().toVec()
 *     check(bytes == s.into())
 * }
 * val s = "hello".toString()
 * isHello(s)
 * ```
 *
 * [String]: ../../std/string/struct.String.html
 * [Vec]: ../../std/vec/struct.Vec.html
 */
public interface Into<T> {
    /**
     * Converts this type into the (usually inferred) input type.
     */
    public fun into(): T
}

/**
 * Used to do value-to-value conversions while consuming the input value. It is the reciprocal of
 * [Into].
 *
 * One should always prefer implementing `From` over [Into]
 * because implementing `From` automatically provides one with an implementation of [Into]
 * thanks to the blanket implementation in the standard library.
 *
 * Only implement [Into] when targeting a version prior to Rust 1.41 and converting to a type
 * outside the current crate.
 * `From` was not able to do these types of conversions in earlier versions because of Rust's
 * orphaning rules.
 * See [Into] for more details.
 *
 * Prefer using [Into] over [From] when specifying trait bounds on a generic function
 * to ensure that types that only implement [Into] can be used as well.
 *
 * The `From` trait is also very useful when performing error handling. When constructing a function
 * that is capable of failing, the return type will generally be of the form `Result<T, E>`.
 * `From` simplifies error handling by allowing a function to return a single error type
 * that encapsulates multiple error types. See the "Examples" section and [the book][book] for more
 * details.
 *
 * **Note: This trait must not fail**. The `From` trait is intended for perfect conversions.
 * If the conversion can fail or is not perfect, use [TryFrom].
 *
 * # Generic Implementations
 *
 * - `From<T> for U` implies [Into]`<U> for T`
 * - `From` is reflexive, which means that `From<T> for T` is implemented
 *
 * # When to implement `From`
 *
 * While there's no technical restrictions on which conversions can be done using
 * a `From` implementation, the general expectation is that the conversions
 * should typically be restricted as follows:
 *
 * * The conversion is *infallible*: if the conversion can fail, use [TryFrom]
 *   instead; don't provide a `From` impl that panics.
 *
 * * The conversion is *lossless*: semantically, it should not lose or discard
 *   information. For example, `Int: From<UShort>` exists, where the original
 *   value can be recovered using `UShort: TryFrom<Int>`.  And `String: From<String>`
 *   exists, where you can get something equivalent to the original value via
 *   `Deref`.  But `From` cannot be used to convert from `UInt` to `UShort`, since
 *   that cannot succeed in a lossless way.  (There's some wiggle room here for
 *   information not considered semantically relevant.  For example,
 *   `Holder<[T]>: From<List<T>>` exists even though it might not preserve capacity,
 *   like how two vectors can be equal despite differing capacities.)
 *
 * * The conversion is *value-preserving*: the conceptual kind and meaning of
 *   the resulting value is the same, even though the Rust type and technical
 *   representation might be different.  For example `- 1_i8 as UByte` is *lossless*,
 *   since `as` casting back can recover the original value, but that conversion
 *   is *not* available via `From` because `- 1` and `255` are different conceptual
 *   values (despite being identical bit patterns technically).  But
 *   `Float: From<Short>` *is* available because `1_i16` and `1.0_f32` are conceptually
 *   the same real number (despite having very different bit patterns technically).
 *   `String: From<char>` is available because they're both *text*, but
 *   `String: From<UInt>` is *not* available, since `1` (a number) and `"1"`
 *   (text) are too different.  (Converting values to text is instead covered
 *   by the [`Display`](fmt.Display) trait.)
 *
 * * The conversion is *obvious*: it's the only reasonable conversion between
 *   the two types.  Otherwise it's better to have it be a named method or
 *   constructor, like how [str.asBytes] is a method and how integers have
 *   methods like [u32.fromNeBytes], [u32.fromLeBytes], and
 *   [u32.fromBeBytes], none of which are `From` implementations.  Whereas
 *   there's only one reasonable way to wrap an [`Ipv6Addr`](net.Ipv6Addr)
 *   into an [`IpAddr`](net.IpAddr), thus `IpAddr: From<Ipv6Addr>` exists.
 *
 * # Examples
 *
 * [String] implements `From<String>`:
 *
 * An explicit conversion from a `String` to a String is done as follows:
 *
 * ```kotlin
 * val string = "hello".toString()
 * val otherString = String.From("hello")
 * check(string == otherString)
 * ```
 *
 * While performing error handling it is often useful to implement `From` for your own error type.
 * By converting underlying error types to our own custom error type that encapsulates the
 * underlying error type, we can return a single error type without losing information on the
 * underlying cause. The '?' operator automatically converts the underlying error type to our
 * custom error type with `From.from`.
 *
 * ```kotlin
 * import std.io
 * import std.num
 *
 * sealed class CliError : From<io.Error, CliError> {
 *     public data class IoError(public var value: io.Error) : CliError()
 *     public data class ParseError(public var value: num.ParseIntError) : CliError()
 *
 *     public open fun from(error: io.Error): CliError {
 *         return CliError.IoError(error)
 *     }
 *
 *     public open fun from(error: num.ParseIntError): CliError {
 *         return CliError.ParseError(error)
 *     }
 * }
 * fun openAndParseFile(fileName: String): Result<Int> {
 *     var contents = readToString(fileName).getOrElse { return Result.failure(it) }
 *     val num: Int = (contents.trim().toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).getOrElse { return Result.failure(it) }
 *     return Result.success(num)
 * }
 * ```
 *
 * [String]: ../../std/string/struct.String.html
 * [from]: From.from
 * [book]: ../../book/ch09-00-error-handling.html
 */
public interface From<T, SelfType> {
    /**
     * Converts to this type from the input type.
     */
    public fun from(value: T): SelfType
}

/**
 * An attempted conversion that consumes `self`, which may or may not be
 * expensive.
 *
 * Library authors should usually not directly implement this trait,
 * but should prefer implementing the [TryFrom] trait, which offers
 * greater flexibility and provides an equivalent `TryInto`
 * implementation for free, thanks to a blanket implementation in the
 * standard library. For more information on this, see the
 * documentation for [Into].
 *
 * Prefer using [TryInto] over [TryFrom] when specifying trait bounds on a generic function
 * to ensure that types that only implement [TryInto] can be used as well.
 *
 * # Implementing `TryInto`
 *
 * This suffers the same restrictions and reasoning as implementing
 * [Into], see there for details.
 */
public interface TryInto<T, out Error> {
    /**
     * Performs the conversion.
     */
    public fun tryInto(): Result<T, Error>
}

/**
 * Simple and safe type conversions that may fail in a controlled
 * way under some circumstances. It is the reciprocal of [TryInto].
 *
 * This is useful when you are doing a type conversion that may
 * trivially succeed but may also need special handling.
 * For example, there is no way to convert an [i64] into an [i32]
 * using the [From] trait, because an [i64] may contain a value
 * that an [i32] cannot represent and so the conversion would lose data.
 * This might be handled by truncating the [i64] to an [i32] or by
 * simply returning [i32.Max], or by some other method.  The [From]
 * trait is intended for perfect conversions, so the `TryFrom` trait
 * informs the programmer when a type conversion could go bad and lets
 * them decide how to handle it.
 *
 * # Generic Implementations
 *
 * - `TryFrom<T> for U` implies [TryInto]`<U> for T`
 * - [tryFrom] is reflexive, which means that `TryFrom<T> for T`
 * is implemented and cannot fail -- the associated `Error` type for
 * calling `T.tryFrom()` on a value of type `T` is [Nothing].
 * When the [!] type is stabilized [Nothing] and [!] will be
 * equivalent.
 *
 * Prefer using [TryInto] over [TryFrom] when specifying trait bounds on a generic function
 * to ensure that types that only implement [TryInto] can be used as well.
 *
 * `TryFrom<T>` can be implemented as follows:
 *
 * ```kotlin
 * open class GreaterThanZero(public var value: Int) : TryFrom<Int, GreaterThanZero, String> {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun tryFrom(value: Int): Result<GreaterThanZero> {
 *         return if (value <= 0) {
 *             Result.failure("GreaterThanZero only accepts values greater than zero!")
 *         } else {
 *             Result.success(GreaterThanZero(value))
 *         }
 *     }
 * }
 * ```
 *
 * # Examples
 *
 * As described, [i32] implements `TryFrom<`[i64]`>`:
 *
 * ```kotlin
 * val bigNumber = 1000000000000L
 * // Silently truncates `bigNumber`, requires detecting
 * // and handling the truncation after the fact.
 * val smallerNumber = bigNumber.toInt()
 * check(smallerNumber == -727379968)
 * // Returns an error because `bigNumber` is too big to
 * // fit in an `Int`.
 * val trySmallerNumber = Int.TryFrom(bigNumber)
 * check(trySmallerNumber.isErr())
 * // Returns `Ok(3)`.
 * val trySuccessfulSmallerNumber = Int.TryFrom(3)
 * check(trySuccessfulSmallerNumber.isOk())
 * ```
 *
 * [tryFrom]: TryFrom.tryFrom
 */
public interface TryFrom<T, SelfType, out Error> {
    /**
     * Performs the conversion.
     */
    public fun tryFrom(value: T): Result<SelfType, Error>
}

// ///////////////////////////////////////////////////////////////////////////
// THE NO-ERROR ERROR TYPE
// ///////////////////////////////////////////////////////////////////////////

/**
 * The error type for errors that can never happen.
 *
 * Since this is an alias to the never type, a value of this type can never actually exist.
 * This can be useful for generic APIs that use [Result] and parameterize the error type,
 * to indicate that the result is always [Ok].
 *
 * For example, the [TryFrom] trait (conversion that returns a [Result])
 * has a blanket implementation for all types where a reverse [Into] implementation exists.
 *
 * ```ignore (illustrates std code, duplicating the impl in a doctest would be an error)
 * impl<T, U> TryFrom<U> for T where U: Into<T> {
 *     type Error = Infallible;
 *
 *     fn tryFrom(value: U) -> Result<Self, Infallible> {
 *         Ok(U.into(value))  // Never returns `Err`
 *     }
 * }
 * ```
 *
 * Note: Since [!] (the "never" type) was stabilized in Rust 1.100.0, it is
 * preferred to use `!` directly unless targeting older Rust versions. `Infallible` may be
 * deprecated in a future Rust version.
 */
public typealias Nothing = kotlin.Nothing
