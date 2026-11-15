// port-lint: source error.rs
package io.github.kotlinmania.rustlang.core.error

import io.github.kotlinmania.rustlang.core.RustTypeId
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.fmt.Formatter

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
    public fun typeId(arg0: Internal): RustTypeId = RustTypeId.of<Error>()

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
    public fun provide(request: RustMutableReference<Request>) {}
}

// This is a Note to prevent `typeId` from being overridden by `Error`
// implementations, since that can enable unsound downcasting.
public open class Internal {
    public companion object {
        public fun typeLayout(): TypeLayout<Internal> =
            object : TypeLayout<Internal> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Internal =
                    Internal()
                override fun writeNative(addr: Long, value: Internal) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Internal =
                    Internal()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Internal) {
                }
            }
    }
}

public fun <E : Error> errorTypeId(err: E, typeIdEFn: (E, Internal) -> RustTypeId): RustTypeId = typeIdEFn(err, Internal())

// Copied from `any.rs`.

/**
 * Returns `true` if the inner type is the same as `T`.
 */
public fun <T : Error> Error.isMatch(): Boolean {
    // Get `TypeId` of the type this function is instantiated with.
    val t = RustTypeId.of<T>()
    // Get `TypeId` of the type in the trait object (`self`).
    val concrete = errorTypeId<Error>(this)
    // Compare both `TypeId`s on equality.
    return t == concrete
}

/**
 * Returns some reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 */
public fun <T : Error> Error.downcastRef(typeLayoutT: TypeLayout<T>): T? =
    if (this.isMatch<T>()) {
        // SAFETY: `is` ensures this type cast is correct
        (typeLayoutT).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
    } else {
        null
    }

/**
 * Returns some mutable reference to the inner value if it is of type `T`, or
 * `None` if it isn't.
 */
public fun <T : Error> Error.downcastMut(typeLayoutT: TypeLayout<T>): T? =
    if (this.isMatch<T>()) {
        // SAFETY: `is` ensures this type cast is correct
        RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), typeLayoutT)
    } else {
        null
    }

/**
 * Forwards to the method defined on the type `dynamic Error`.
 */
public inline fun <reified T : Error> Error.downcastRef(): T? = Error.downcastRef<T>(this)

/**
 * Forwards to the method defined on the type `dynamic Error`.
 */
public inline fun <reified T : Error> Error.downcastMut(): T? = Error.downcastMut<T>(this)

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
public inline fun <reified T> requestValue(err: Error): T? = requestByTypeTag<Value<T>>(err)

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
public inline fun <reified T> requestRef(err: Error): T? = requestByTypeTag<Ref<MaybeSizedValue<T>>>(err)

/**
 * Request a specific value by tag from the `Error`.
 */
public fun <I> requestByTypeTag(err: Error): Reified? where I : Type<Any?> {
    val tagged = Tagged(tagId = RustTypeId.of<I>(), value = TaggedOption(null, fieldLayout = ObjectSlotLayout<Reified?>()))
    err.provide(tagged.asRequest())
    return tagged.value.first
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
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Tagged<Erased>>,
) {
    public var field0: Tagged<Erased>
        get() = value
        set(v) {
            value = v
        }

    public var value: Tagged<Erased>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Tagged<Erased>, fieldLayout: TypeLayout<Tagged<Erased>>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Request> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Request(ptr, layout) }, { it.value })

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
    public open fun <T> provideValue(value: T): RustMutableReference<Request> = this.provide<Value<T>>(value)

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
    public open fun <T> provideValueWith(fulfil: () -> T): RustMutableReference<Request> = this.provideWith<Value<T>>(fulfil)

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
    public open fun <T> provideRef(value: T): RustMutableReference<Request> = this.provide<Ref<MaybeSizedValue<T>>>(value)

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
    public open fun <T> provideRefWith(fulfil: () -> T): RustMutableReference<Request> = this.provideWith<Ref<MaybeSizedValue<T>>>(fulfil)

    /**
     * Provides a value with the given `Type` tag.
     */
    public open fun <I> provide(value: Reified): RustMutableReference<Request> where I : Type<Any?> {
        run {
            val matchVal = this.value.downcastMut<I>()
            if (matchVal != null && matchVal is TaggedOption && matchVal.value == null) {
                val res = matchVal
                val (None) = matchVal as TaggedOption
                res.value = value
            }
        }
        return this
    }

    /**
     * Provides a value with the given `Type` tag, using a closure to prevent unnecessary work.
     */
    public open fun <I> provideWith(fulfil: () -> Reified): RustMutableReference<Request> where I : Type<Any?> {
        run {
            val matchVal = this.value.downcastMut<I>()
            if (matchVal != null && matchVal is TaggedOption && matchVal.value == null) {
                val res = matchVal
                val (None) = matchVal as TaggedOption
                res.value = fulfil()
            }
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
     *             return 99u
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
    public open fun <T> wouldBeSatisfiedByValueOf(): Boolean = this.wouldBeSatisfiedBy<Value<T>>()

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
    public open fun <T> wouldBeSatisfiedByRefOf(): Boolean = this.wouldBeSatisfiedBy<Ref<MaybeSizedValue<T>>>()

    public open fun <I> wouldBeSatisfiedBy(): Boolean where I : Type<Any?> =
        run {
            val matchVal = this.value.downcast<I>()
            when {
                matchVal != null && matchVal is TaggedOption && matchVal.value == null -> {
                    val (None) = matchVal as TaggedOption
                    true
                }
                else -> false
            }
        }

    public open fun fmt(f: Formatter): Result<Unit, Unit> = DebugStruct(f, "Request").finishNonExhaustive()

    public companion object {
        public operator fun invoke(value: Tagged<Erased>, fieldLayout: TypeLayout<Tagged<Erased>>): Request = Request(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Tagged<Erased>>): Request = Request(fieldStorage, fieldLayout)
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
public interface Type<out Reified>

/**
 * Similar to the [Type] trait, but represents a type which may be unsized (i.e., has a
 * `? Sized` bound). E.g., `String`.
 */
public interface MaybeSizedType<out Reified>

/**
 * Type-based tag for types bounded by `static`, i.e., with no borrowed elements.
 */
public open class Value<T>(
    public var value: Unit,
) : Type<T> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

/**
 * Type-based tag similar to [Value] but which may be unsized (i.e., has a `? Sized` bound).
 */
public open class MaybeSizedValue<T>(
    public var value: Unit,
) : MaybeSizedType<T> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

/**
 * Type-based tag for reference types (`T`, where T is represented by
 * `<I as MaybeSizedType<>>.Reified`.
 */
public open class Ref<I>(
    public var value: Unit,
) : Type<Reified> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }
}

/**
 * An `Option` with a type tag `I`.
 *
 * Since this struct implements `Erased`, the type can be erased to make a dynamically typed
 * option. The type can be checked dynamically using `Tagged.tagId` and since this is statically
 * checked for the concrete type, there is some degree of type safety.
 */
public open class TaggedOption<I : Type<Any?>, Reified> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Reified?>,
) : Erased {
    public var field0: I.Reified?
        get() = value
        set(v) {
            value = v
        }

    public var value: Reified?
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Reified?, fieldLayout: TypeLayout<Reified?>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<TaggedOption<I, Reified>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> TaggedOption<I, Reified>(ptr, layout) }, { it.value })

    public companion object {
        public operator fun <I : Type<Any?>, Reified> invoke(value: Reified?, fieldLayout: TypeLayout<Reified?>): TaggedOption<I, Reified> = TaggedOption<I, Reified>(value, fieldLayout)

        public fun <I : Type<Any?>, Reified> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Reified?>): TaggedOption<I, Reified> = TaggedOption<I, Reified>(fieldStorage, fieldLayout)
    }
}

public fun <I : Type<Any?>> Tagged<TaggedOption<I, Reified>>.asRequest(): RustMutableReference<Request> {
    val erased = ((this as Any?) as Tagged<Erased>)
    // SAFETY: transmuting `Tagged<dynamic Erased<> + >` to `Request<>` is safe since
    // `Request` is repr(transparent).
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(erased)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), ObjectSlotLayout<Request>())
}

/**
 * Represents a type-erased but identifiable object.
 *
 * This trait is exclusively implemented by the `TaggedOption` type.
 */
public interface Erased

public open class Tagged<E>(
    public var tagId: RustTypeId,
    public var value: E,
)

/**
 * Returns some reference to the dynamic value if it is tagged with `I`,
 * or `None` otherwise.
 */
public fun <I> Tagged<Erased>.downcast(): TaggedOption<I, Reified>? where I : Type<Any?> =
    if (this.tagId == RustTypeId.of<I>()) {
        // SAFETY: Just checked whether we're pointing to an I.
        (ObjectSlotLayout<Tagged<TaggedOption<I, Reified>>>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).value
    } else {
        null
    }

/**
 * Returns some mutable reference to the dynamic value if it is tagged with `I`,
 * or `None` otherwise.
 */
public fun <I> Tagged<Erased>.downcastMut(): RustMutableReference<TaggedOption<I, Reified>>? where I : Type<Any?> =
    if (this.tagId == RustTypeId.of<I>()) {
        RustMutableReference(MutPtr((MutPtr(((mutPtrFromRustCast(this))).rawAddress)).rawAddress), ObjectSlotLayout<Tagged<TaggedOption<I, Reified>>>()).value
    } else {
        null
    }

/**
 * An iterator over an [Error] and its sources.
 *
 * If you want to omit the initial error and only process
 * its sources, use `skip(1)`.
 */
public open class Source(
    public var current: Error?,
) : Iterable<Error> {
    override fun iterator(): Iterator<Error> = object : AbstractIterator<Error>() {
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
        this.current = this.current?.let({ Error.source(it) })
        return current
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.current != null) {
            Pair(1, null)
        } else {
            Pair(0, 0)
        }
}
