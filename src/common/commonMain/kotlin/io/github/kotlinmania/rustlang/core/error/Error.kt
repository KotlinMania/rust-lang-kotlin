// rust-kotlin: manual
// port-lint: source error.rs
package io.github.kotlinmania.rustlang.core.error

import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugStruct(
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

/**
 * `Error` is a trait representing the basic expectations for error values,
 * i.e., values of type `E` in [Result.T.E].
 *
 * Errors must describe themselves through the [Display] and [Debug]
 * traits. Error messages are typically concise lowercase sentences without
 * trailing punctuation:
 *
 * ```kotlin
 * val err = ("NaN".toUIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse UInt"))).unwrapErr()
 * check(err.toString() == "invalid digit found in string")
 * ```
 *
 * # Error source
 *
 * Errors may provide cause information. [Error.source] is generally
 * used when errors cross "abstraction boundaries". If one module must report
 * an error that is caused by an error from a lower-level module, it can allow
 * accessing that error via `Error.source()`. This makes it possible for the
 * high-level module to provide its own errors while also revealing some of the
 * implementation for debugging.
 *
 * In error types that wrap an underlying error, the underlying error
 * should be either returned by the outer error's `Error.source()`, or rendered
 * by the outer error's `Display` implementation, but not both.
 *
 * # Example
 *
 * Implementing the `Error` trait only requires that `Debug` and `Display` are implemented too.
 *
 * ```kotlin
 * import std.error.Error
 * import std.fmt
 * import std.path.PathBuf
 *
 * open class ReadConfigError(public var path: PathBuf) {
 *     public open fun fmt(f: StringBuilder) {
 *         val path = this.path.display()
 *         f.append("unable to read configuration at $path")
 *     }
 * }
 * ```
 */
public interface Error {
    /**
     * Returns the lower-level source of this error, if any.
     *
     * # Examples
     *
     * ```kotlin
     * import std.error.Error
     * import std.fmt
     *
     * open class SuperErrorSideKick {
     *     public open fun fmt(f: StringBuilder) {
     *         f.append("SuperErrorSideKick is here!")
     *     }
     * }
     * open class SuperError(public var source: SuperErrorSideKick) {
     *     public open fun fmt(f: StringBuilder) {
     *         f.append("SuperError is here!")
     *     }
     *
     *     public open fun source(): Any? {
     *         return this.source
     *     }
     * }
     * fun getSuperError(): Result<Unit> {
     *     return Result.failure(SuperError(source = SuperErrorSideKick()))
     * }
     * fun main() {
     *     when (val matchVal = getSuperError()) {
     *         is Err -> {
     *             val (e) = matchVal
     *             println("Error: $e")
     *             println("Caused by: ${e.source().unwrap()}")
     *         }
     *         else -> println("No error")
     *     }
     * }
     * ```
     */
    public fun source(): Error? = null

    /**
     * Gets the `TypeId` of `self`.
     */
    public fun typeId(): RustTypeId = RustTypeId.of<Error>()

    /**
     * ```kotlin
     * when (val matchVal = ("xc".toUIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse UInt")))) {
     *     is Err -> {
     *         val (e) = matchVal
     *         // Print `e` itself, no need for description().
     *         println("Error: $e")
     *     }
     *     else -> {}
     * }
     * ```
     */
    public fun description(): String = "description() is deprecated; use Display"

    public fun cause(): Error? = this.source()

    /**
     * Provides type-based access to context intended for error reports.
     *
     * Used in conjunction with [Request.provideValue] and [Request.provideRef] to extract
     * references to member variables from `dynamic Error` trait objects.
     *
     * # Example
     *
     * ```kotlin
     * import core.error.Request
     * import core.error.requestRef
     * import core.fmt
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     sealed class MyLittleTeaPot {
     *         public data object Empty : MyLittleTeaPot()
     *
     *         private fun kmDiscriminant(): Int =
     *             when (this) {
     *             Empty -> 0
     *             }
     *
     *         public fun toByte(): Byte = kmDiscriminant().toByte()
     *
     *         public fun toUByte(): UByte = kmDiscriminant().toUByte()
     *
     *         public fun toShort(): Short = kmDiscriminant().toShort()
     *
     *         public fun toUShort(): UShort = kmDiscriminant().toUShort()
     *
     *         public fun toInt(): Int = kmDiscriminant()
     *
     *         public fun toUInt(): UInt = kmDiscriminant().toUInt()
     *
     *         public fun toLong(): Long = kmDiscriminant().toLong()
     *
     *         public fun toULong(): ULong = kmDiscriminant().toULong()
     *     }
     *     open class MyBacktrace {
     *         public open fun new(): MyBacktrace {
     *             // ...
     *         }
     *     }
     *     open class Error(public var backtrace: MyBacktrace) {
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("Example Error")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideRef<MyBacktrace>(this.backtrace)
     *         }
     *     }
     *     fun main() {
     *         val backtrace = MyBacktrace.new()
     *         val error = Error(backtrace = backtrace)
     *         val dynError = ((error as Any?) as Any)
     *         val backtraceRef = requestRef<MyBacktrace>(dynError).unwrap()
     *         check(eq(error.backtrace, backtraceRef))
     *         check(requestRef<MyLittleTeaPot>(dynError) == null)
     *     }
     * }
     * ```
     *
     * # Delegating Impls
     *
     * <div class="warning">
     *
     * **Warning**: We recommend implementors avoid delegating implementations of `provide` to
     * source error implementations.
     *
     * </div>
     *
     * This method should expose context from the current piece of the source chain only, not from
     * sources that are exposed in the chain of sources. Delegating `provide` implementations cause
     * the same context to be provided by multiple errors in the chain of sources which can cause
     * unintended duplication of information in error reports or require heuristics to deduplicate.
     *
     * In other words, the following implementation pattern for `provide` is discouraged and should
     * not be used for [Error] types exposed in public APIs to third parties.
     *
     * ```kotlin
     * open class MyError(public var source: Error) {
     *     public open fun source(): Any? {
     *         return this.source
     *     }
     *
     *     public open fun provide(request: Request) {
     *         this.source.provide(request)
     *         // <--- Discouraged
     *     }
     * }
     * ```
     */
    public fun provide(request: Request) {}
}

// Copied from `any.rs`.

/**
 * Returns `true` if the inner type is the same as `T`.
 */
public inline fun <reified T : Error> Error.isMatch(): Boolean = this is T

/**
 * Returns some reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 */
public inline fun <reified T : Error> Error.downcastRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T? =
    if (this is T) this else null

/**
 * Returns some mutable reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 */
public inline fun <reified T : Error> Error.downcastMut(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T? =
    if (this is T) this else null

/**
 * Returns an iterator starting with the current error and continuing with
 * recursively calling [Error.source].
 *
 * If you want to omit the current error and only use its sources,
 * use `skip(1)`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.error.Error
 * import std.fmt
 *
 * @feature(errorIter)
 * public fun doctestEntry() {
 *     open class A {
 *         public open fun fmt(f: StringBuilder) {
 *             f.append("A")
 *         }
 *     }
 *     open class B(public var value: Any?) {
 *         public var field0: Any?
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun fmt(f: StringBuilder) {
 *             f.append("B")
 *         }
 *
 *         public open fun source(): Any? {
 *             return this.value?.let({ e -> e.asRef() })
 *         }
 *     }
 *     val b = B(A())
 *     // let err : Holder<Error> = b.into(); // or
 *     val err = ((b as Any?) as Any)
 *     var iter = err.sources()
 *     check("B".toString() == iter.next()!!.toString())
 *     check("A".toString() == iter.next()!!.toString())
 *     check(iter.next() == null)
 *     check(iter.next() == null)
 * }
 * ```
 */
public fun Error.sources(): Source {
    // You may think this method would be better in the `Error` trait, and you'd be right.
    // Unfortunately that doesn't work, not because of the dynamic-incompatibility rules but
    // because we save a reference to `self` in `Source`s below as a trait object.
    // If this method was declared in `Error`, then `self` would have the type `T` where
    // `T` is some concrete type which implements `Error`. We would need to coerce `self`
    // to have type `dynamic Error`, but that requires that `Self` has a known size
    // (i.e., `Self: Sized`). We can't put that bound on `Error` since that would forbid
    // `Error` trait objects, and we can't put that bound on the method because that means
    // the method can't be called on trait objects (we'd also need the `static` bound,
    // but that isn't allowed because methods with bounds on `Self` other than `Sized` are
    // dynamic-incompatible). Requiring an `Unsize` bound is not backwards compatible.
    return Source(current = this)
}

/**
 * Requests a value of type `T` from the given `implementation Error`.
 *
 * # Examples
 *
 * Get a string value from an error.
 *
 * ```kotlin
 * import core.error.requestValue
 * import std.error.Error
 *
 * @feature(errorGenericMemberAccess)
 * public fun doctestEntry() {
 *     fun getString(err: Any): String {
 *         return requestValue<String>(err).unwrap()
 *     }
 * }
 * ```
 */
public inline fun <reified T> requestValue(err: Error): T? = requestByTypeTag<T, Value<T>>(err)

/**
 * Requests a reference of type `T` from the given `implementation Error`.
 *
 * # Examples
 *
 * Get a string reference from an error.
 *
 * ```kotlin
 * import core.error.Error
 * import core.error.requestRef
 *
 * @feature(errorGenericMemberAccess)
 * public fun doctestEntry() {
 *     fun getStr(err: Any): String {
 *         return requestRef<String>(err).unwrap()
 *     }
 * }
 * ```
 */
public inline fun <reified T> requestRef(err: Error): T? = requestByTypeTag<T, Ref<T, MaybeSizedValue<T>>>(err)

/**
 * Request a specific value by tag from the `Error`.
 */
public inline fun <reified Reified, reified I : TypeTag<Reified>> requestByTypeTag(err: Error): Reified? {
    val slot =
        TaggedOption<I, Reified>(
            value = null,
            fieldLayout = UnresolvedTypeLayout(),
            validator = { candidate ->
                if (candidate is Reified) Pair(true, candidate) else Pair(false, null)
            },
        )
    val tagged = Tagged<Erased>(tagId = RustTypeId.of<I>(), value = slot, payloadTypeId = RustTypeId.of<Reified>())
    err.provide(tagged.asRequest())
    return slot.value
}

// //////////////////////////////////////////////////////////////////////////
// Request and its methods
// //////////////////////////////////////////////////////////////////////////

/**
 * `Request` supports generic, type-driven access to data. Its use is currently restricted to the
 * standard library in cases where trait authors wish to allow trait implementors to share generic
 * information across trait boundaries. The motivating and prototypical use case is
 * `core.error.Error` which would otherwise require a method per concrete type (e.g.
 * `std.backtrace.Backtrace` instance that implementors want to expose to users).
 *
 * # Data flow
 *
 * To describe the intended data flow for Request objects, let's consider two conceptual users
 * separated by API boundaries:
 *
 * * Consumer - the consumer requests objects using a Request instance; e.g. a crate that offers
 *   fancy `Error`/`Result` reporting to users wants to request a Backtrace from a given `dynamic Error`.
 *
 * * Producer - the producer provides objects when requested via Request; e.g. a library with an
 *   an `Error` implementation that automatically captures backtraces at the time instances are
 *   created.
 *
 * The consumer only needs to know where to submit their request and is expected to handle the
 * request not being fulfilled by the use of `Option<T>` in the responses offered by the producer.
 *
 * * A Producer initializes the value of one of its fields of a specific type. (or is otherwise
 *   prepared to generate a value requested). e.g., `backtrace.Backtrace` or
 *   `std.backtrace.Backtrace`.
 * * A Consumer requests an object of a specific type (say `std.backtrace.Backtrace`). In the
 *   case of a `dynamic Error` trait object (the Producer), there are functions called `requestRef` and
 *   `requestValue` to simplify obtaining an `Option<T>` for a given type.
 * * The Producer, when requested, populates the given Request object which is given as a mutable
 *   reference.
 * * The Consumer extracts a value or reference to the requested type from the `Request` object
 *   wrapped in an `Option<T>`; in the case of `dynamic Error` the aforementioned `requestRef` and
 *   `requestValue` methods mean that `dynamic Error` users don't have to deal with the `Request` type at
 *   all (but `Error` implementors do). The `None` case of the `Option` suggests only that the
 *   Producer cannot currently offer an instance of the requested type, not that it can't or never will.
 *
 * # Examples
 *
 * The best way to demonstrate this is using an example implementation of `Error`s `provide` trait
 * method:
 *
 * ```kotlin
 * import core.error.Request
 * import core.error.requestRef
 * import core.fmt
 *
 * @feature(errorGenericMemberAccess)
 * public fun doctestEntry() {
 *     sealed class MyLittleTeaPot {
 *         public data object Empty : MyLittleTeaPot()
 *
 *         private fun kmDiscriminant(): Int =
 *             when (this) {
 *             Empty -> 0
 *             }
 *
 *         public fun toByte(): Byte = kmDiscriminant().toByte()
 *
 *         public fun toUByte(): UByte = kmDiscriminant().toUByte()
 *
 *         public fun toShort(): Short = kmDiscriminant().toShort()
 *
 *         public fun toUShort(): UShort = kmDiscriminant().toUShort()
 *
 *         public fun toInt(): Int = kmDiscriminant()
 *
 *         public fun toUInt(): UInt = kmDiscriminant().toUInt()
 *
 *         public fun toLong(): Long = kmDiscriminant().toLong()
 *
 *         public fun toULong(): ULong = kmDiscriminant().toULong()
 *     }
 *     open class MyBacktrace {
 *         public open fun new(): MyBacktrace {
 *             // ...
 *         }
 *     }
 *     open class Error(public var backtrace: MyBacktrace) {
 *         public open fun fmt(f: StringBuilder) {
 *             f.append("Example Error")
 *         }
 *
 *         public open fun provide(request: Request) {
 *             request.provideRef<MyBacktrace>(this.backtrace)
 *         }
 *     }
 *     fun main() {
 *         val backtrace = MyBacktrace.new()
 *         val error = Error(backtrace = backtrace)
 *         val dynError = ((error as Any?) as Any)
 *         val backtraceRef = requestRef<MyBacktrace>(dynError).unwrap()
 *         check(eq(error.backtrace, backtraceRef))
 *         check(requestRef<MyLittleTeaPot>(dynError) == null)
 *     }
 * }
 * ```
 */
public open class Request internal constructor(
    public val value: Tagged<Erased>,
) {
    public val field0: Tagged<Erased>
        get() = value

    /**
     * Provides a value or other type with only static scopes.
     *
     * # Examples
     *
     * Provides an `UByte`.
     *
     * ```kotlin
     * import core.error.Request
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class SomeConcreteType(public var field: UByte) {
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("${this.field} failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideValue<UByte>(this.field)
     *         }
     *     }
     * }
     * ```
     */
    public inline fun <reified T> provideValue(value: T): Request = this.provide<T, Value<T>>(value)

    /**
     * Provides a value or other type with only static scopes computed using a closure.
     *
     * # Examples
     *
     * Provides a `String` by cloning.
     *
     * ```kotlin
     * import core.error.Request
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class SomeConcreteType(public var field: String) {
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("${this.field} failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideValueWith<String>({ this.field.clone() })
     *         }
     *     }
     * }
     * ```
     */
    public inline fun <reified T> provideValueWith(fulfil: () -> T): Request = this.provideWith<T, Value<T>>(fulfil)

    /**
     * Provides a reference. The referee type must be bounded by `static`,
     * but may be unsized.
     *
     * # Examples
     *
     * Provides a reference to a field as a `String`.
     *
     * ```kotlin
     * import core.error.Request
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class SomeConcreteType(public var field: String) {
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("${this.field} failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideRef<String>(this.field)
     *         }
     *     }
     * }
     * ```
     */
    public inline fun <reified T> provideRef(value: T): Request = this.provide<T, Ref<T, MaybeSizedValue<T>>>(value)

    /**
     * Provides a reference computed using a closure. The referee type
     * must be bounded by `static`, but may be unsized.
     *
     * # Examples
     *
     * Provides a reference to a field as a `String`.
     *
     * ```kotlin
     * import core.error.Request
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     fun todayIsAWeekday(): Boolean {
     *         return true
     *     }
     *     open class SomeConcreteType(public var business: String, public var party: String) {
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("${this.business} failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideRefWith<String>({
     *                 if (todayIsAWeekday()) {
     *                     this.business
     *                 } else {
     *                     this.party
     *                 }
     *             })
     *         }
     *     }
     * }
     * ```
     */
    public inline fun <reified T> provideRefWith(fulfil: () -> T): Request = this.provideWith<T, Ref<T, MaybeSizedValue<T>>>(fulfil)

    /**
     * Provides a value with the given `Type` tag.
     */
    public inline fun <reified Reified, reified I : TypeTag<Reified>> provide(value: Reified): Request {
        if (this.value.tagId == RustTypeId.of<I>() && this.value.payloadTypeId == RustTypeId.of<Reified>() && this.value.value.isEmptySlot()) {
            this.value.value.writeRawValue(value)
        }
        return this
    }

    /**
     * Provides a value with the given `Type` tag, using a closure to prevent unnecessary work.
     */
    public inline fun <reified Reified, reified I : TypeTag<Reified>> provideWith(fulfil: () -> Reified): Request {
        if (this.value.tagId == RustTypeId.of<I>() && this.value.payloadTypeId == RustTypeId.of<Reified>() && this.value.value.isEmptySlot()) {
            this.value.value.writeRawValue(fulfil())
        }
        return this
    }

    /**
     * Checks if the `Request` would be satisfied if provided with a
     * value of the specified type. If the type does not match or has
     * already been provided, returns false.
     *
     * # Examples
     *
     * Checks if a `UByte` still needs to be provided and then provides
     * it.
     *
     * ```kotlin
     * import core.error.Request
     * import core.error.requestValue
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class Parent(public var value: UByte?) {
     *         public var field0: UByte?
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("a parent failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             when (val matchVal = this.value) {
     *                 null -> {}
     *                 else -> {
     *                     val v = matchVal
     *                     request.provideValue<UByte>(v)
     *                 }
     *             }
     *         }
     *     }
     *     open class Child(public var parent: Parent) {
     *         // Pretend that this takes a lot of resources to evaluate.
     *         public open fun anExpensiveComputation(): UByte? {
     *             return 99u.toUByte()
     *         }
     *
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("child failed: \n  because of parent: ${this.parent}")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             // In general, we don't know if this call will provide
     *             // an `UByte` value or not...
     *             this.parent.provide(request)
     *             // ...so we check to see if the `UByte` is needed before
     *             // we run our expensive computation.
     *             if (request.wouldBeSatisfiedByValueOf<UByte>()) {
     *                 when (val matchVal = this.anExpensiveComputation()) {
     *                     null -> {}
     *                     else -> {
     *                         val v = matchVal
     *                         request.provideValue<UByte>(v)
     *                     }
     *                 }
     *             }
     *             // The request will be satisfied now, regardless of if
     *             // the parent provided the value or we did.
     *             check(!request.wouldBeSatisfiedByValueOf<UByte>())
     *         }
     *     }
     *     val parent = Parent(42)
     *     val child = Child(parent = parent)
     *     check(42 == requestValue<UByte>(child))
     *     val parent1 = Parent(null)
     *     val child1 = Child(parent = parent1)
     *     check(99 == requestValue<UByte>(child1))
     * }
     * ```
     */
    public inline fun <reified T> wouldBeSatisfiedByValueOf(): Boolean = this.wouldBeSatisfiedBy<T, Value<T>>()

    /**
     * Checks if the `Request` would be satisfied if provided with a
     * reference to a value of the specified type.
     *
     * If the type does not match or has already been provided, returns false.
     *
     * # Examples
     *
     * Checks if a `String` still needs to be provided and then provides
     * it.
     *
     * ```kotlin
     * import core.error.Request
     * import core.error.requestRef
     *
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class Parent(public var value: String?) {
     *         public var field0: String?
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("a parent failed")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             when (val matchVal = this.value) {
     *                 null -> {}
     *                 else -> {
     *                     val v = matchVal
     *                     request.provideRef<String>(v)
     *                 }
     *             }
     *         }
     *     }
     *     open class Child(public var parent: Parent, public var name: String) {
     *         // Pretend that this takes a lot of resources to evaluate.
     *         public open fun anExpensiveComputation(): String? {
     *             return this.name
     *         }
     *
     *         public open fun fmt(f: StringBuilder) {
     *             f.append("${this.name} failed: \n  ${this.parent}")
     *         }
     *
     *         public open fun provide(request: Request) {
     *             // In general, we don't know if this call will provide
     *             // a `String` reference or not...
     *             this.parent.provide(request)
     *             // ...so we check to see if the `String` is needed before
     *             // we run our expensive computation.
     *             if (request.wouldBeSatisfiedByRefOf<String>()) {
     *                 when (val matchVal = this.anExpensiveComputation()) {
     *                     null -> {}
     *                     else -> {
     *                         val v = matchVal
     *                         request.provideRef<String>(v)
     *                     }
     *                 }
     *             }
     *             // The request will be satisfied now, regardless of if
     *             // the parent provided the reference or we did.
     *             check(!request.wouldBeSatisfiedByRefOf<String>())
     *         }
     *     }
     *     val parent = Parent("parent".into())
     *     val child = Child(parent = parent, name = "child".into())
     *     check("parent" == requestRef<String>(child))
     *     val parent1 = Parent(null)
     *     val child1 = Child(parent = parent1, name = "child".into())
     *     check("child" == requestRef<String>(child1))
     * }
     * ```
     */
    public inline fun <reified T> wouldBeSatisfiedByRefOf(): Boolean = this.wouldBeSatisfiedBy<T, Ref<T, MaybeSizedValue<T>>>()

    public inline fun <reified Reified, reified I : TypeTag<Reified>> wouldBeSatisfiedBy(): Boolean =
        this.value.tagId == RustTypeId.of<I>() &&
            this.value.payloadTypeId == RustTypeId.of<Reified>() &&
            this.value.value.isEmptySlot()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            DebugStruct(f, "Request").finishNonExhaustive()
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object {
        internal operator fun invoke(value: Tagged<Erased>): Request = Request(value)
    }
}

/**
 * This trait is implemented by specific tag types in order to allow
 * describing a type which can be requested for a given scope `a`.
 *
 * A few example implementations for type-driven tags can be found in this
 * module, although crates may also implement their own tags for more
 * complex types with internal scopes.
 */
public interface TypeTag<out Reified>

/**
 * Similar to the [Type] trait, but represents a type which may be unsized (i.e., has a
 * `? Sized` bound). E.g., `String`.
 */
public interface MaybeSizedType<out Reified>

/**
 * Type-based tag for types bounded by `static`, i.e., with no borrowed elements.
 */
public open class Value<T> : TypeTag<T> {
    public companion object
}

/**
 * Type-based tag similar to [Value] but which may be unsized (i.e., has a `? Sized` bound).
 */
public open class MaybeSizedValue<T> : MaybeSizedType<T> {
    public companion object
}

/**
 * Type-based tag for reference types (`T`, where T is represented by
 * `<I as MaybeSizedType<>>.Reified`.
 */
public open class Ref<Reified, I : MaybeSizedType<Reified>> : TypeTag<Reified> {
    public companion object
}

/**
 * An `Option` with a type tag `I`.
 *
 * Since this struct implements `Erased`, the type can be erased to make a dynamically typed
 * option. The type can be checked dynamically using `Tagged.tagId` and since this is statically
 * checked for the concrete type, there is some degree of type safety.
 */
public open class TaggedOption<I : TypeTag<Reified>, Reified>
    @PublishedApi
    internal constructor(
        public var value: Reified?,
        public val fieldLayout: TypeLayout<Reified?>,
        private val validator: (Any?) -> Pair<Boolean, Reified?>,
    ) : Erased {
        public constructor(
            value: Reified? = null,
            fieldLayout: TypeLayout<Reified?> = UnresolvedTypeLayout(),
        ) : this(value, fieldLayout, { Pair(false, null) })
        public var field0: Reified?
            get() = value
            set(v) {
                value = v
            }

        public override fun isEmptySlot(): Boolean = this.value == null

        public override fun rawValue(): Any? = this.value

        public override fun writeRawValue(newValue: Any?): Boolean {
            val (ok, typed) = validator(newValue)
            if (!ok) return false
            this.value = typed
            return true
        }

        public companion object {
            public operator fun <I : TypeTag<Reified>, Reified> invoke(value: Reified?, fieldLayout: TypeLayout<Reified?> = UnresolvedTypeLayout()): TaggedOption<I, Reified> = TaggedOption(value = value, fieldLayout = fieldLayout)
        }
    }

public fun <Reified, I : TypeTag<Reified>> Tagged<TaggedOption<I, Reified>>.asRequest(): Request {
    val erased = Tagged<Erased>(tagId = this.tagId, value = this.value, payloadTypeId = this.payloadTypeId)
    return Request(erased)
}

@kotlin.jvm.JvmName("asRequestErased")
public fun Tagged<Erased>.asRequest(): Request = Request(this)

/**
 * Represents a type-erased but identifiable object.
 *
 * This trait is exclusively implemented by the `TaggedOption` type.
 */
public interface Erased {
    public fun isEmptySlot(): Boolean

    public fun rawValue(): Any?

    public fun writeRawValue(newValue: Any?): Boolean
}

public open class Tagged<E>(
    public var tagId: RustTypeId,
    public var value: E,
    public var payloadTypeId: RustTypeId = RustTypeId.of<Unit>(),
) {
    public companion object
}

/**
 * Returns some reference to the dynamic value if it is tagged with `I`,
 * or `None` otherwise.
 */
public inline fun <reified Reified, reified I : TypeTag<Reified>> Tagged<Erased>.downcast(): TaggedOption<I, Reified>? =
    if (this.tagId == RustTypeId.of<I>() && this.payloadTypeId == RustTypeId.of<Reified>()) {
        val raw = this.value.rawValue()
        when {
            raw == null -> TaggedOption(null)
            raw is Reified -> TaggedOption(raw)
            else -> null
        }
    } else {
        null
    }

/**
 * Returns some mutable reference to the dynamic value if it is tagged with `I`,
 * or `None` otherwise.
 */
public inline fun <reified Reified, reified I : TypeTag<Reified>> Tagged<Erased>.downcastMut(): TaggedOption<I, Reified>? =
    this.downcast<Reified, I>()

/**
 * An iterator over an [Error] and its sources.
 *
 * If you want to omit the initial error and only process
 * its sources, use `skip(1)`.
 */
public open class Source(
    public var current: Error?,
) : Iterable<Error> {
    override fun iterator(): Iterator<Error> =
        object : AbstractIterator<Error>() {
            override fun computeNext() {
                when (val v = this@Source.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): Source = Source(current)

    public open fun next(): Error? {
        val current = this.current
        this.current = this.current?.source()
        return current
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.current != null) {
            Pair(1, null)
        } else {
            Pair(0, 0)
        }

    public companion object
}
