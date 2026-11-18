// port-lint: source mem/maybe_uninit.rs
package io.github.kotlinmania.rustlang.core.mem.maybeuninit

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.marker.Freeze
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.mem.transmuteNeo
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.fromRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.splitLastMut

// Lang item so we can wrap other types in it. This is useful for coroutines.

/**
 * A wrapper type to construct uninitialized instances of `T`.
 *
 * # Initialization invariant
 *
 * The compiler, in general, assumes that a variable is [properly initialized or "valid"][validity]
 * according to the requirements of the variable's type. For example, a variable of
 * reference type must be aligned and non-null. This is an invariant that must
 * *always* be upheld, even in unchecked code. As a consequence, zero-initializing a
 * variable of reference type causes instantaneous undefined behavior,
 * no matter whether that reference ever gets used to access memory:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * val x: Int = 0
 * // undefined behavior! ⚠️
 * // The equivalent code with `MaybeUninit<Int>`:
 * val x1: Int = MaybeUninit.zeroed(PrimitiveLayouts.I32).assumeInit()
 * // undefined behavior! ⚠️
 * ```
 *
 * This is exploited by the compiler for various optimizations, such as eliding
 * run-time checks and optimizing `sealed class` layout.
 *
 * Similarly, entirely uninitialized memory may have any content, while a `Boolean` must
 * always be `true` or `false`. Hence, creating an uninitialized `Boolean` is undefined behavior:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * val b: Boolean = mem.uninitialized()
 * // undefined behavior! ⚠️
 * // The equivalent code with `MaybeUninit<Boolean>`:
 * val b1: Boolean = MaybeUninit.uninit(PrimitiveLayouts.BOOL).assumeInit()
 * // undefined behavior! ⚠️
 * ```
 *
 * Moreover, uninitialized memory is special in that it does not have a fixed value ("fixed"
 * meaning "it won't change without being written to"). Reading the same uninitialized byte
 * multiple times can give different results. This makes it undefined behavior to have
 * uninitialized data in a variable even if that variable has an integer type, which otherwise can
 * hold any *fixed* bit pattern:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * val x: Int = mem.uninitialized()
 * // undefined behavior! ⚠️
 * // The equivalent code with `MaybeUninit<Int>`:
 * val x1: Int = MaybeUninit.uninit(PrimitiveLayouts.I32).assumeInit()
 * // undefined behavior! ⚠️
 * ```
 *
 * Conversely, sometimes it is okay to not initialize *all* bytes of a `MaybeUninit`
 * before calling `assumeInit`. For instance, padding bytes do not have to be initialized.
 * See the field-by-field struct initialization example below for a case of that.
 *
 * On top of that, remember that most types have additional invariants beyond merely
 * being considered initialized at the type level. For example, a `1`-initialized [Vec.T]
 * is considered initialized (under the current implementation; this does not constitute
 * a stable guarantee) because the only requirement the compiler knows about it
 * is that the data pointer must be non-null. Creating such a `List<T>` does not cause
 * *immediate* undefined behavior, but will cause undefined behavior with most
 * safe operations (including dropping it).
 *
 * [Vec.T]: ../../std/vec/struct.Vec.html
 *
 * # Examples
 *
 * `MaybeUninit<T>` serves to enable unchecked code to deal with uninitialized data.
 * It is a signal to the compiler indicating that the data here might *not*
 * be initialized:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * // Create an explicitly uninitialized reference. The compiler knows that data inside
 * // a `MaybeUninit<T>` may be invalid, and hence this is not UB:
 * var x = MaybeUninit.uninit(PrimitiveLayouts.I32)
 * // Set it to a valid value.
 * x.write(0)
 * // Extract the initialized data -- this is only allowed *after* properly
 * // initializing `x`!
 * val x1 = x.assumeInit()
 * ```
 *
 * The compiler then knows to not make any incorrect assumptions or optimizations on this code.
 *
 * You can think of `MaybeUninit<T>` as being a bit like `Option<T>` but without
 * any of the run-time tracking and without any of the safety checks.
 *
 * ## out-pointers
 *
 * You can use `MaybeUninit<T>` to implement "out-pointers": instead of returning data
 * from a function, pass it a pointer to some (uninitialized) memory to put the
 * result into. This can be useful when it is important for the caller to control
 * how the memory the result is stored in gets allocated, and you want to avoid
 * unnecessary moves.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.mem.MaybeUninit
 *
 * fun makeVec(out: MutPtr) {
 *     // `write` does not drop the old contents, which is important.
 *     run {
 *         (UnresolvedTypeLayout<List<Int>>()).writeNative((out).rawAddress, mutableListOf(1, 2, 3))
 *     }
 * }
 * var v = MaybeUninit.uninit(PrimitiveLayouts.U8)
 * run {
 *     makeVec(v.asMutPtr())
 * }
 * // Now we know `v` is initialized! This also makes sure the vector gets
 * // properly dropped.
 * val v1 = v.assumeInit()
 * check(v1 == listOf(1, 2, 3))
 * ```
 *
 * ## Initializing an array element-by-element
 *
 * `MaybeUninit<T>` can be used to initialize a large array element-by-element:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
 * import std.mem.MaybeUninit
 *
 * val data1 = run {
 *     // Create an uninitialized array of `MaybeUninit`.
 *     var data: List<MaybeUninit<List<UInt>>> = List(1000) { MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>()) }
 *     // Dropping a `MaybeUninit` does nothing, so if there is a panic during this loop,
 *     // we have a memory leak, but there is no memory safety issue.
 *     for (elem in data) {
 *         elem.write(mutableListOf(42))
 *     }
 *     // Everything is initialized. Transmute the array to the
 *     // initialized type.
 *     transmuteWithLayouts<List<MaybeUninit<List<UInt>>>, List<List<UInt>>>(data, UnresolvedTypeLayout<List<MaybeUninit<List<UInt>>>>(), UnresolvedTypeLayout<List<List<UInt>>>())
 * }
 * check(data1[0] == listOf(42))
 * ```
 *
 * You can also work with partially initialized arrays, which could
 * be found in low-level datastructures.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.mem.MaybeUninit
 *
 * // Create an uninitialized array of `MaybeUninit`.
 * var data: List<MaybeUninit<String>> = List(1000) { MaybeUninit.uninit(UnresolvedTypeLayout<String>()) }
 * // Count the number of elements we have assigned.
 * var dataLen: Int = 0
 * for (elem in ((data).subList(0, 500))) {
 *     elem.write("hello")
 *     dataLen += 1
 * }
 * // For each item in the array, drop if we allocated it.
 * for (elem1 in ((data).subList(0, dataLen))) {
 *     run {
 *         elem1.assumeInitDrop()
 *     }
 * }
 * ```
 *
 * ## Initializing a struct field-by-field
 *
 * You can use `MaybeUninit<T>` and the [raw.mut] syntax to initialize structs field by field:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.mem.MaybeUninit
 *
 * open class Foo(public var name: String, public var list: List<UByte>)
 * val foo = run {
 *     var uninit: MaybeUninit<Foo> = MaybeUninit.uninit(UnresolvedTypeLayout<Foo>())
 *     val ptr = uninit.asMutPtr()
 *     // Initializing the `name` field
 *     // Using `write` instead of assignment via `=` to not call `drop` on the
 *     // old, uninitialized value.
 *     run {
 *         (addrOfMut(ptr.name)).write("Bob".toString())
 *     }
 *     // Initializing the `list` field
 *     // If there is a panic here, then the `String` in the `name` field leaks.
 *     run {
 *         (addrOfMut(ptr.list)).write(mutableListOf(0, 1, 2))
 *     }
 *     // All the fields are initialized, so we call `assumeInit` to get an initialized Foo.
 *     uninit.assumeInit()
 * }
 * check(foo == Foo(name = "Bob".toString(), list = mutableListOf(0u, 1u, 2u)))
 * ```
 * [raw.mut]: https://doc.rust-lang.org/reference/types/pointer.html#r-type.pointer.raw.constructor
 * [validity]: ../../reference/behavior-considered-undefined.html#r-undefined.validity
 *
 * Note that we have not initialized the padding, but that's fine -- it does not have to be
 * initialized. In fact, even if we had initialized the padding in `uninit`, those bytes would be
 * lost when copying the result: no matter the contents of the padding bytes in `uninit`, they will
 * always be uninitialized in `foo`.
 *
 * # Layout
 *
 * `MaybeUninit<T>` is guaranteed to have the same size, alignment, and ABI as `T`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import std.mem.MaybeUninit
 *
 * check(sizeOf<MaybeUninit<ULong>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<ULong>>()) == sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64))
 * check(alignOf<MaybeUninit<ULong>>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<ULong>>()) == alignOf<ULong>(typeLayoutT = PrimitiveLayouts.U64))
 * ```
 *
 * However remember that a type *containing* a `MaybeUninit<T>` is not necessarily the same
 * layout; Rust does not in general guarantee that the fields of a `Foo<T>` have the same order as
 * a `Foo<U>` even if `T` and `U` have the same size and alignment. Furthermore because any bit
 * value is valid for a `MaybeUninit<T>` the compiler can't apply non-zero/niche-filling
 * optimizations, potentially resulting in a larger size:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 *
 * check(sizeOf<Boolean?>(typeLayoutT = UnresolvedTypeLayout<Boolean?>()) == 1)
 * check(sizeOf<MaybeUninit<Boolean>?>(typeLayoutT = UnresolvedTypeLayout<MaybeUninit<Boolean>?>()) == 2)
 * ```
 *
 * If `T` is FFI-safe, then so is `MaybeUninit<T>`.
 *
 * While `MaybeUninit` is `@repr(transparent)` (indicating it guarantees the same size,
 * alignment, and ABI as `T`), this does *not* change any of the previous caveats. `Option<T>` and
 * `Option<MaybeUninit<T>>` may still have different sizes, and types containing a field of type
 * `T` may be laid out (and sized) differently than if that field were `MaybeUninit<T>`.
 * `MaybeUninit` is a union type, and `@repr(transparent)` on unions is unstable (see [the
 * tracking issue](https://github.com/rust-lang/rust/issues/60405)). Over time, the exact
 * guarantees of `@repr(transparent)` on unions may evolve, and `MaybeUninit` may or may not
 * remain `@repr(transparent)`. That said, `MaybeUninit<T>` will *always* guarantee that it has
 * the same size, alignment, and ABI as `T`; it's just that the way `MaybeUninit` implements that
 * guarantee may evolve.
 *
 * Note that even though `T` and `MaybeUninit<T>` are ABI compatible it is still unsound to
 * reinterpret `T` to `MaybeUninit<T>` and expose that to safe code because it would allow
 * safe code to access uninitialized memory:
 *
 * ```kotlin
 * import core.mem.MaybeUninit
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
 *
 * fun <T> unsoundTransmute(value: T): MaybeUninit<T> {
 *     return transmuteWithLayouts<T, MaybeUninit<T>>(value, UnresolvedTypeLayout<T>(), UnresolvedTypeLayout<MaybeUninit<T>>())
 * }
 * fun main() {
 *     val localStorageL10C4 = AddressableLocal(0, PrimitiveLayouts.I32)
 *     val code by localStorageL10C4
 *     val code1 = RustMutableReference(localStorageL10C4.asMutPtr(), localStorageL10C4.layout)
 *     val code2 = unsoundTransmute(code1)
 *     code2 = MaybeUninit.uninit(UnresolvedTypeLayout<T>())
 *     exit(code1)
 *     // UB! Accessing uninitialized memory.
 * }
 * ```
 *
 * # Validity
 *
 * `MaybeUninit<T>` has no validity requirements – any sequence of [bytes] of
 * the appropriate length, initialized or uninitialized, are a valid
 * representation.
 *
 * Moving or copying a value of type `MaybeUninit<T>` (i.e., performing a
 * "typed copy") will exactly preserve the contents, including the
 * [provenance], of all non-padding bytes of type `T` in the value's
 * representation.
 *
 * Therefore `MaybeUninit` can be used to perform a round trip of a value from
 * type `T` to type `MaybeUninit<U>` then back to type `T`, while preserving
 * the original value, if two conditions are met. One, type `U` must have the
 * same size as type `T`. Two, for all byte offsets where type `U` has padding,
 * the corresponding bytes in the representation of the value must be
 * uninitialized.
 *
 * For example, due to the fact that the type `[UByte; sizeOf<T>]` has no
 * padding, the following is sound for any type `T` and will return the
 * original value:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 * import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
 *
 * public fun doctestEntry() {
 *     fun identity(t: T): T {
 *         return run {
 *             val u: MaybeUninit<UByteArray> = transmuteWithLayouts<T, MaybeUninit<UByteArray>>(t, UnresolvedTypeLayout<T>(), UnresolvedTypeLayout<MaybeUninit<UByteArray>>())
 *             transmuteWithLayouts<MaybeUninit<UByteArray>, T>(u, UnresolvedTypeLayout<MaybeUninit<UByteArray>>(), UnresolvedTypeLayout<T>())
 *             // OK.
 *         }
 *     }
 * }
 * ```
 *
 * Note: Copying a value that contains references may implicitly reborrow them
 * causing the provenance of the returned value to differ from that of the
 * original. This applies equally to the trivial identity function:
 *
 * ```kotlin
 * fun <T> trivialIdentity(t: T): T {
 *     return t
 * }
 * ```
 *
 * Note: Moving or copying a value whose representation has initialized bytes
 * at byte offsets where the type has padding may lose the value of those
 * bytes, so while the original value will be preserved, the original
 * *representation* of that value as bytes may not be. Again, this applies
 * equally to `trivialIdentity`.
 *
 * Note: Performing this round trip when type `U` has padding at byte offsets
 * where the representation of the original value has initialized bytes may
 * produce undefined behavior or a different value. For example, the following
 * is unsound since `T` requires all bytes to be initialized:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 * import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
 *
 * public fun doctestEntry() {
 *     @repr(C)
 *     open class T(public var value: UByteArray) {
 *         public var field0: UByteArray
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *     }
 *     @repr(C)
 *     open class U(public var field0: UByte, public var field1: UShort)
 *     fun unsoundIdentity(t: T): T {
 *         return run {
 *             val u: MaybeUninit<U> = transmuteWithLayouts<T, MaybeUninit<U>>(t, T.typeLayout(), UnresolvedTypeLayout<MaybeUninit<U>>())
 *             transmuteWithLayouts<MaybeUninit<U>, T>(u, UnresolvedTypeLayout<MaybeUninit<U>>(), T.typeLayout())
 *             // UB.
 *         }
 *     }
 * }
 * ```
 *
 * Conversely, the following is sound since `T` allows uninitialized bytes in
 * the representation of a value, but the round trip may alter the value:
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 * import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
 *
 * public fun doctestEntry() {
 *     @repr(C)
 *     open class T(public var value: MaybeUninit<UByteArray>) {
 *         public var field0: MaybeUninit<UByteArray>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *     }
 *     @repr(C)
 *     open class U(public var field0: UByte, public var field1: UShort)
 *     fun nonIdentity(t: T): T {
 *         return run {
 *             // May lose an initialized byte.
 *             val u: MaybeUninit<U> = transmuteWithLayouts<T, MaybeUninit<U>>(t, UnresolvedTypeLayout<T>(), UnresolvedTypeLayout<MaybeUninit<U>>())
 *             transmuteWithLayouts<MaybeUninit<U>, T>(u, UnresolvedTypeLayout<MaybeUninit<U>>(), UnresolvedTypeLayout<T>())
 *         }
 *     }
 * }
 * ```
 *
 * [bytes]: ../../reference/memory-model.html#bytes
 */
public open class MaybeUninit<T>(
    public var uninit: Unit? = null,
    public var value: T? = null,
) {
    public open fun clone(): MaybeUninit<T> {
        // Not calling `T.clone()`, we cannot know if we are initialized enough for that.
        return MaybeUninit(this.uninit, this.value)
    }

    // SAFETY: the clone implementation is a copy, see above.

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        // NB: there is no `.padFmt` so we can't use a simpler `formatArgs("MaybeUninit<(..)>").
        val fullName = "MaybeUninit"
        val prefixLen = (fullName.indexOf("MaybeUninit").takeIf { it >= 0 })!!
        return run { f.append(fullName.substring(prefixLen)); Result.Ok<Unit, Unit>(Unit) }
    }

    /**
     * Sets the value of the `MaybeUninit<T>`.
     *
     * This overwrites any previous value without dropping it, so be careful
     * not to use this twice unless you want to skip running the destructor.
     * For your convenience, this also returns a mutable reference to the
     * (now safely initialized) contents of `self`.
     *
     * As the content is stored inside a `ManuallyDrop`, the destructor is not
     * run for the inner data if the MaybeUninit leaves scope without a call to
     * [assumeInit], [assumeInitDrop], or similar. Code that receives
     * the mutable reference returned by this function needs to keep this in
     * mind. The safety model of Rust regards leaks as safe, but they are
     * usually still undesirable. This being said, the mutable reference
     * behaves like any other mutable reference would, so assigning a new value
     * to it will drop the old content.
     *
     * [assumeInit]: Self.assumeInit
     * [assumeInitDrop]: Self.assumeInitDrop
     *
     * # Examples
     *
     * Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UByte>>())
     * run {
     *     val hello = x.write(("Hello, world!".encodeToByteArray()).toVec())
     *     // Setting hello does not leak prior allocations, but drops them
     *     hello = ("Hello".encodeToByteArray()).toVec()
     *     hello[0] = 'h'.code.toUByte()
     * }
     * // x is initialized now:
     * val s = x.assumeInit()
     * check("hello".encodeToByteArray() == s.asSlice())
     * ```
     *
     * This usage of the method causes a leak:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<String>())
     * x.write("Hello".toString())
     * // This leaks the contained string:
     * x.write("hello".toString())
     * // x is initialized now:
     * val s = x.assumeInit()
     * ```
     *
     * This method can be used to avoid unchecked in some cases. The example below
     * shows a part of an implementation of a fixed sized arena that lends out
     * pinned references.
     * With `write`, we can avoid the need to write through a raw pointer:
     *
     * ```kotlin
     * import core.mem.MaybeUninit
     * import core.pin.Pin
     *
     * open class PinArena<T>(public var memory: List<MaybeUninit<T>>, public var len: Int) {
     *     public open fun capacity(): Int {
     *         return this.memory.size
     *     }
     *
     *     public open fun push(value: T): Pin<T> {
     *         if (this.len >= this.capacity()) {
     *             error("Attempted to push to a full pin arena!")
     *         }
     *         val ref = this.memory[this.len].write(value)
     *         this.len += 1
     *         return Pin.newUnchecked(ref)
     *     }
     * }
     * ```
     */
    public open fun write(value: T): T {
        var selfMut = this
        selfMut = MaybeUninit.new(value, UnresolvedTypeLayout<T>())
        // SAFETY: We just initialized this value.
        return selfMut.assumeInitMut()
    }

    /**
     * Gets a pointer to the contained value. Reading from this pointer or turning it
     * into a reference is undefined behavior unless the `MaybeUninit<T>` is initialized.
     * Writing to memory that this pointer (non-transitively) points to is undefined behavior
     * (except inside an `UnsafeCell<T>`).
     *
     * # Examples
     *
     * Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * x.write(mutableListOf(0, 1, 2))
     * // Create a reference into the `MaybeUninit<T>`. This is okay because we initialized it.
     * val xVec = x.asPtr()
     * check(xVec.len() == 3)
     * ```
     *
     * *Incorrect* usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * val x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * val xVec = x.asPtr()
     * // We have created a reference to an uninitialized vector! This is undefined behavior. ⚠️
     * ```
     *
     * (Notice that the rules around references to uninitialized data are not finalized yet, but
     * until they are, it is advisable to avoid them.)
     */
    public open fun asPtr(): ConstPtr {
        // `MaybeUninit` and `ManuallyDrop` are both `repr(transparent)` so we can cast the pointer.
        return (constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    }

    /**
     * Gets a mutable pointer to the contained value. Reading from this pointer or turning it
     * into a reference is undefined behavior unless the `MaybeUninit<T>` is initialized.
     *
     * # Examples
     *
     * Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * x.write(mutableListOf(0, 1, 2))
     * // Create a reference into the `MaybeUninit<List<UInt>>`.
     * // This is okay because we initialized it.
     * val xVec = x.asMutPtr()
     * xVec.push(3)
     * check(xVec.len() == 4)
     * ```
     *
     * *Incorrect* usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * val xVec = x.asMutPtr()
     * // We have created a reference to an uninitialized vector! This is undefined behavior. ⚠️
     * ```
     *
     * (Notice that the rules around references to uninitialized data are not finalized yet, but
     * until they are, it is advisable to avoid them.)
     */
    public open fun asMutPtr(): MutPtr {
        // `MaybeUninit` and `ManuallyDrop` are both `repr(transparent)` so we can cast the pointer.
        return (mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    }

    /**
     * Extracts the value from the `MaybeUninit<T>` container. This is a great way
     * to ensure that the data will get dropped, because the resulting `T` is
     * subject to the usual drop handling.
     *
     * # Safety
     *
     * It is up to the caller to guarantee that the `MaybeUninit<T>` really is in an initialized
     * state, i.e., a state that is considered ["valid" for type `T`][validity]. Calling this when
     * the content is not yet fully initialized causes immediate undefined behavior. The
     * [type-level documentation][inv] contains more information about this initialization
     * invariant.
     *
     * It is a common mistake to assume that this function is safe to call on integers because they
     * can hold all bit patterns. It is also a common mistake to think that calling this function
     * is UB if any byte is uninitialized. Both of these assumptions are wrong. If that is
     * surprising to you, please read the [type-level documentation][inv].
     *
     * [inv]: #initialization-invariant
     * [validity]: ../../reference/behavior-considered-undefined.html#r-undefined.validity
     *
     * On top of that, remember that most types have additional invariants beyond merely
     * being considered initialized at the type level. For example, a `1`-initialized [Vec.T]
     * is considered initialized (under the current implementation; this does not constitute
     * a stable guarantee) because the only requirement the compiler knows about it
     * is that the data pointer must be non-null. Creating such a `List<T>` does not cause
     * *immediate* undefined behavior, but will cause undefined behavior with most
     * safe operations (including dropping it).
     *
     * [Vec.T]: ../../std/vec/struct.Vec.html
     *
     * # Examples
     *
     * Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(PrimitiveLayouts.BOOL)
     * x.write(true)
     * val xInit = x.assumeInit()
     * check(xInit == true)
     * ```
     *
     * *Incorrect* usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import std.mem.MaybeUninit
     *
     * val x: Int = MaybeUninit.uninit(PrimitiveLayouts.I32).assumeInit()
     * // undefined behavior! ⚠️
     * ```
     *
     * See the [type-level documentation][#examples] for more examples.
     */
    public open fun assumeInit(): T {
        // SAFETY: the caller must guarantee that `self` is initialized.
        // This also means that `self` must be a `value` variant.
        return run {
            // We do this via a reinterpret instead of `ManuallyDrop.intoInner` so that there's
            // no trace of `ManuallyDrop` in Miri's error messages here.
            transmuteNeo(this)
        }
    }

    /**
     * Reads the value from the `MaybeUninit<T>` container. The resulting `T` is subject
     * to the usual drop handling.
     *
     * Whenever possible, it is preferable to use [assumeInit] instead, which
     * prevents duplicating the content of the `MaybeUninit<T>`.
     *
     * # Safety
     *
     * It is up to the caller to guarantee that the `MaybeUninit<T>` really is in an initialized
     * state. Calling this when the content is not yet fully initialized causes undefined
     * behavior. The [type-level documentation][inv] contains more information about
     * this initialization invariant.
     *
     * Moreover, similar to the [ptr.read] function, this function creates a
     * bitwise copy of the contents, regardless whether the contained type
     * implements the [Copy] trait or not. When using multiple copies of the
     * data (by calling `assumeInitRead` multiple times, or first calling
     * `assumeInitRead` and then [assumeInit]), it is your responsibility
     * to ensure that data may indeed be duplicated.
     *
     * [inv]: #initialization-invariant
     * [assumeInit]: MaybeUninit.assumeInit
     *
     * # Examples
     *
     * Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(PrimitiveLayouts.U32)
     * x.write(13)
     * val x1 = x.assumeInitRead()
     * // `UInt` is `Copy`, so we may read multiple times.
     * val x2 = x.assumeInitRead()
     * check(x1 == x2)
     * var x1 = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>?>())
     * x1.write(null)
     * val x11 = x1.assumeInitRead()
     * // Duplicating a `None` value is okay, so we may read multiple times.
     * val x21 = x1.assumeInitRead()
     * check(x11 == x21)
     * ```
     *
     * *Incorrect* usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>?>())
     * x.write(mutableListOf(0, 1, 2))
     * val x1 = x.assumeInitRead()
     * val x2 = x.assumeInitRead()
     * // We now created two copies of the same vector, leading to a double-free ⚠️ when
     * // they both get dropped!
     * ```
     */
    public open fun assumeInitRead(): T {
        // SAFETY: the caller must guarantee that `self` is initialized.
        // Reading from `self.asPtr()` is safe since `self` should be initialized.
        return run {
            this.asPtr().read()
        }
    }

    /**
     * Drops the contained value in place.
     *
     * If you have ownership of the `MaybeUninit`, you can also use
     * [assumeInit] as an alternative.
     *
     * # Safety
     *
     * It is up to the caller to guarantee that the `MaybeUninit<T>` really is
     * in an initialized state. Calling this when the content is not yet fully
     * initialized causes undefined behavior.
     *
     * On top of that, all additional invariants of the type `T` must be
     * satisfied, as the `Drop` implementation of `T` (or its members) may
     * rely on this. For example, setting a `List<T>` to an invalid but
     * non-null address makes it initialized (under the current implementation;
     * this does not constitute a stable guarantee), because the only
     * requirement the compiler knows about it is that the data pointer must be
     * non-null. Dropping such a `List<T>` however will cause undefined
     * behavior.
     *
     * [assumeInit]: MaybeUninit.assumeInit
     */
    public open fun assumeInitDrop() {
        // SAFETY: the caller must guarantee that `self` is initialized and
        // satisfies all invariants of `T`.
        // Dropping the value in place is safe if that is the case.
        dropInPlace(this.asMutPtr())
    }

    /**
     * Gets a shared reference to the contained value.
     *
     * This can be useful when we want to access a `MaybeUninit` that has been
     * initialized but don't have ownership of the `MaybeUninit` (preventing the use
     * of `.assumeInit()`).
     *
     * # Safety
     *
     * Calling this when the content is not yet fully initialized causes undefined
     * behavior: it is up to the caller to guarantee that the `MaybeUninit<T>` really
     * is in an initialized state.
     *
     * # Examples
     *
     * ### Correct usage of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * var x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * // Initialize `x`:
     * x.write(mutableListOf(1, 2, 3))
     * // Now that our `MaybeUninit<_>` is known to be initialized, it is okay to
     * // create a shared reference to it:
     * val x1: List<UInt> = x.assumeInitRef()
     * check(x1 == mutableListOf(1, 2, 3))
     * ```
     *
     * ### *Incorrect* usages of this method:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.mem.MaybeUninit
     *
     * val x = MaybeUninit.uninit(UnresolvedTypeLayout<List<UInt>>())
     * val xVec: List<UInt> = x.assumeInitRef()
     * // We have created a reference to an uninitialized vector! This is undefined behavior. ⚠️
     * ```
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.cell.Cell
     * import std.mem.MaybeUninit
     *
     * val b = MaybeUninit.uninit(UnresolvedTypeLayout<Cell<Boolean>>())
     * // Initialize the `MaybeUninit` using `Cell.set`:
     * run {
     *     b.assumeInitRef().set(true)
     *     // ^^^^^^^^^^^^^^^ Reference to an uninitialized `Cell<Boolean>`: UB!
     * }
     * ```
     */
    public open fun assumeInitRef(): T {
        // SAFETY: the caller must guarantee that `self` is initialized.
        // This also means that `self` must be a `value` variant.
        return run {
            this.asPtr()
        }
    }

    /**
     * Gets a mutable (unique) reference to the contained value.
     *
     * This can be useful when we want to access a `MaybeUninit` that has been
     * initialized but don't have ownership of the `MaybeUninit` (preventing the use
     * of `.assumeInit()`).
     *
     * # Safety
     *
     * Calling this when the content is not yet fully initialized causes undefined
     * behavior: it is up to the caller to guarantee that the `MaybeUninit<T>` really
     * is in an initialized state. For instance, `.assumeInitMut()` cannot be used to
     * initialize a `MaybeUninit`.
     *
     * # Examples
     *
     * ### Correct usage of this method:
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
     * import std.mem.MaybeUninit
     *
     * public fun doctestEntry() {
     *     / **
     *      * Initializes *all* the bytes of the input buffer.
     *      * /
     *     fun initializeBuffer(buf: MutPtr) {}
     *     var buf = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
     *     // Initialize `buf`:
     *     run {
     *         initializeBuffer(buf.asMutPtr())
     *     }
     *     // Now we know that `buf` has been initialized, so we could `.assumeInit()` it.
     *     // However, using `.assumeInit()` may trigger a `memcpy` of the 1024 bytes.
     *     // To assert our buffer has been initialized without copying it, we upgrade
     *     // the `MaybeUninit<[UByte; 1024]>` to a `[UByte; 1024]`:
     *     val buf1: UByteArray = buf.assumeInitMut()
     *     // Now we can use `buf` as a normal slice:
     *     buf1.sortUnstable()
     *     check(buf1.windows(2).all({ pair -> pair[0] <= pair[1] })) { "buffer is sorted" }
     * }
     * ```
     *
     * ### *Incorrect* usages of this method:
     *
     * You cannot use `.assumeInitMut()` to initialize a value:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import std.mem.MaybeUninit
     *
     * var b = MaybeUninit.uninit(PrimitiveLayouts.BOOL)
     * run {
     *     b.assumeInitMut() = true
     *     // We have created a (mutable) reference to an uninitialized `Boolean`!
     *     // This is undefined behavior. ⚠️
     * }
     * ```
     *
     * For instance, you cannot [Read] into an uninitialized buffer:
     *
     * [Read]: ../../std/io/trait.Read.html
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
     * import std.io
     * import std.mem.MaybeUninit
     *
     * public fun doctestEntry(): Result<Unit> {
     *     fun readChunk(reader: io.Read): io.Result<UByteArray> {
     *         var buffer = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
     *         reader.readExact(buffer.assumeInitMut()).getOrElse { return Result.failure(it) }
     *         // ^^^^^^^^^^^^^^^^^^^^^^^^
     *         // (mutable) reference to uninitialized memory!
     *         // This is undefined behavior.
     *         return Result.success(buffer.assumeInit())
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * Nor can you use direct field access to do field-by-field gradual initialization:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
     * import std.mem.MaybeUninit
     * import std.ptr
     *
     * open class Foo(public var a: UInt, public var b: UByte)
     * val foo1: Foo = run {
     *     var foo = MaybeUninit.uninit(Foo.typeLayout())
     *     ptr.write(mutPtrFromRustCast(foo.assumeInitMut().a), 1337, typeLayoutT = PrimitiveLayouts.U32)
     *     // ^^^^^^^^^^^^^^^^^^^^^
     *     // (mutable) reference to uninitialized memory!
     *     // This is undefined behavior.
     *     ptr.write(mutPtrFromRustCast(foo.assumeInitMut().b), 42, typeLayoutT = PrimitiveLayouts.U8)
     *     // ^^^^^^^^^^^^^^^^^^^^^
     *     // (mutable) reference to uninitialized memory!
     *     // This is undefined behavior.
     *     foo.assumeInit()
     * }
     * ```
     */
    public open fun assumeInitMut(): T {
        // SAFETY: the caller must guarantee that `self` is initialized.
        // This also means that `self` must be a `value` variant.
        return run {
            this.asMutPtr()
        }
    }

    /**
     * Returns the contents of this `MaybeUninit` as a slice of potentially uninitialized bytes.
     *
     * Note that even if the contents of a `MaybeUninit` have been initialized, the value may still
     * contain padding bytes which are left uninitialized.
     *
     * This method requires `T: Freeze`. It's not available for types with interior mutability,
     * such as `Cell<T>`.
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import std.mem.MaybeUninit
     *
     * @feature(maybeUninitAsBytes)
     * public fun doctestEntry() {
     *     val value = 0x12345678
     *     val uninit = MaybeUninit.new(value, PrimitiveLayouts.I32)
     *     val uninitBytes = uninit.encodeToByteArray().map { it.toUByte() }
     *     val bytes = uninitBytes.assumeInitRef()
     *     check(bytes == run { val rawBits = value.toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })
     * }
     * ```
     */
    public open fun asBytes(): List<MaybeUninit<UByte>> {
        // SAFETY: MaybeUninit<u8> is always valid, even for padding bytes
        return linearSliceView(this.asPtr().cast(), sizeOf<T>().toInt(), UnresolvedTypeLayout<MaybeUninit<UByte>>())
    }

    /**
     * Returns the contents of this `MaybeUninit` as a mutable slice of potentially uninitialized
     * bytes.
     *
     * Note that even if the contents of a `MaybeUninit` have been initialized, the value may still
     * contain padding bytes which are left uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import std.mem.MaybeUninit
     *
     * @feature(maybeUninitAsBytes)
     * public fun doctestEntry() {
     *     val value = 0x12345678
     *     var uninit = MaybeUninit.new(value, PrimitiveLayouts.I32)
     *     val uninitBytes = uninit.asBytesMut()
     *     if (true) {
     *         uninitBytes[0].write(0xcd)
     *     } else {
     *         uninitBytes[3].write(0xcd)
     *     }
     *     val val2 = uninit.assumeInit()
     *     check(val2 == 0x123456cd)
     * }
     * ```
     */
    public open fun asBytesMut(): MutableList<MaybeUninit<UByte>> {
        // SAFETY: MaybeUninit<u8> is always valid, even for padding bytes
        return linearMutSliceView(this.asMutPtr().cast(), sizeOf<T>().toInt(), UnresolvedTypeLayout<MaybeUninit<UByte>>())
    }

    public companion object {
        /**
         * Creates a new `MaybeUninit<T>` initialized with the given value.
         * It is safe to call [assumeInit] on the return value of this function.
         *
         * Note that dropping a `MaybeUninit<T>` will never call `T`s drop code.
         * It is your responsibility to make sure `T` gets dropped if it got initialized.
         *
         * # Example
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.mem.MaybeUninit
         *
         * val v: MaybeUninit<List<UByte>> = MaybeUninit.new(mutableListOf(42), UnresolvedTypeLayout<List<UByte>>())
         * ```
         *
         * [assumeInit]: MaybeUninit.assumeInit
         */
        public fun <T> new(value: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T> = MaybeUninit(value = value)

        /**
         * Creates a new `MaybeUninit<T>` in an uninitialized state.
         *
         * Note that dropping a `MaybeUninit<T>` will never call `T`s drop code.
         * It is your responsibility to make sure `T` gets dropped if it got initialized.
         *
         * See the [type-level documentation][MaybeUninit] for some examples.
         *
         * # Example
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.mem.MaybeUninit
         *
         * val v: MaybeUninit<String> = MaybeUninit.uninit(UnresolvedTypeLayout<String>())
         * ```
         */
        public fun <T> uninit(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T> = MaybeUninit(uninit = Unit)

        /**
         * Creates a new `MaybeUninit<T>` in an uninitialized state, with the memory being
         * filled with `0` bytes. It depends on `T` whether that already makes for
         * proper initialization. For example, `MaybeUninit<Int>.zeroed()` is initialized,
         * but `MaybeUninit<Int>.zeroed()` is not because references must not
         * be null.
         *
         * Note that if `T` has padding bytes, those bytes are *not* preserved when the
         * `MaybeUninit<T>` value is returned from this function, so those bytes are not
         * guaranteed to be zeroed.
         *
         * Note that dropping a `MaybeUninit<T>` will never call `T`s drop code.
         * It is your responsibility to make sure `T` gets dropped if it got initialized.
         *
         * # Example
         *
         * Correct usage of this function: initializing a struct with zero, where all
         * fields of the struct can hold the bit-pattern 0 as a valid value.
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.mem.MaybeUninit
         *
         * val x = MaybeUninit.zeroed(UnresolvedTypeLayout<Pair<UByte, Boolean>>())
         * val x1 = x.assumeInit()
         * check(x1 == Pair(0, false))
         * ```
         *
         * This can be used in const contexts, such as to indicate the end of static arrays for
         * plugin registration.
         *
         * *Incorrect* usage of this function: calling `x.zeroed().assumeInit()`
         * when `0` is not a valid bit-pattern for the type:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
         * import std.mem.MaybeUninit
         *
         * sealed class NotZero(public val value: Int) {
         *     public data object One : NotZero(1)
         *     public data object Two : NotZero(2)
         *
         *     public fun toByte(): Byte = value.toByte()
         *
         *     public fun toUByte(): UByte = value.toUByte()
         *
         *     public fun toShort(): Short = value.toShort()
         *
         *     public fun toUShort(): UShort = value.toUShort()
         *
         *     public fun toInt(): Int = value
         *
         *     public fun toUInt(): UInt = value.toUInt()
         *
         *     public fun toLong(): Long = value.toLong()
         *
         *     public fun toULong(): ULong = value.toULong()
         * }
         * val x = MaybeUninit.zeroed(UnresolvedTypeLayout<Pair<UByte, NotZero>>())
         * val x1 = x.assumeInit()
         * // Inside a pair, we create a `NotZero` that does not have a valid discriminant.
         * // This is undefined behavior. ⚠️
         * ```
         */
        public fun <T> zeroed(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<T> {
            val u = MaybeUninit.uninit(typeLayoutT)
            // SAFETY: `u.asMutPtr()` points to allocated memory.
            u.asMutPtr().writeBytes(0u.toUByte(), 1)
            return u
        }

        /**
         * Extracts the values from an array of `MaybeUninit` containers.
         *
         * # Safety
         *
         * It is up to the caller to guarantee that all elements of the array are
         * in an initialized state.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import std.mem.MaybeUninit
         *
         * @feature(maybeUninitArrayAssumeInit)
         * public fun doctestEntry() {
         *     var array: List<MaybeUninit<Int>> = List(3) { MaybeUninit.uninit(PrimitiveLayouts.I32) }
         *     array[0].write(0)
         *     array[1].write(1)
         *     array[2].write(2)
         *     // SAFETY: Now safe as we initialised all elements
         *     val array1 = MaybeUninit.arrayAssumeInit(array)
         *     check(array1 == listOf(0, 1, 2))
         * }
         * ```
         */
        public fun <T> arrayAssumeInit(array: List<MaybeUninit<T>>, n: Int): List<T> {
            // SAFETY:
            // * The caller guarantees that all elements of the array are initialized
            // * `MaybeUninit<T>` and T are guaranteed to have the same layout
            // * `MaybeUninit` does not drop, so there are no double-frees
            // And thus the conversion is safe
            return run {
                transmuteWithLayouts<List<MaybeUninit<T>>, List<T>>(array, UnresolvedTypeLayout<List<MaybeUninit<T>>>(), UnresolvedTypeLayout<List<T>>())
            }
        }

        /**
         * Converts `T` to `MaybeUninit<T>`.
         *
         * The value stays initialized. This function changes only the type of the reference.
         *
         * There is no corresponding `fromMut` method, because it could allow writing uninitialized memory
         * into the original value.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.mem.MaybeUninit
         *
         * @feature(maybeUninitFromRef, maybeUninitAsBytes)
         * public fun doctestEntry() {
         *     val value = 0x12345678u
         *     val uninit = MaybeUninit.fromRef(value)
         *     val bytes = uninit.encodeToByteArray().map { it.toUByte() }
         *     // All bytes of a u32 are initialized, so assumeInit is sound here:
         *     val byteVal = bytes[0].assumeInit()
         *     check(byteVal == run { val rawBits = value; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }[0])
         * }
         * ```
         */
        public fun <T> fromRef(r: T): MaybeUninit<T> {
            // SAFETY: `MaybeUninit<T>` is repr(transparent) and every initialized T is a valid MaybeUninit<T>
            return ConstPtr((r).rawAddress)
        }
    }
}

/**
 * Copies the elements from `src` to `self`,
 * returning a mutable reference to the now initialized contents of `self`.
 *
 * If `T` does not implement `Copy`, use [writeCloneOfSlice] instead.
 *
 * This is similar to [slice.copyFromSlice].
 *
 * # Panics
 *
 * This function will panic if the two slices have different lengths.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * var dst = MutableList(32) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 * val src = List(32) { 0 }
 * val init = dst.writeCopyOfSlice(src)
 * check(init == src)
 * ```
 *
 * ```kotlin
 * var vec = ArrayList(32)
 * val src = List(16) { 0 }
 * vec.spareCapacityMut()[0 until src.len()].writeCopyOfSlice(src)
 * // SAFETY: we have just copied all the elements of len into the spare capacity
 * // the first src.len() elements of the vec are valid now.
 * run {
 *     vec.setLen(src.len())
 * }
 * check(vec == src)
 * ```
 *
 * [writeCloneOfSlice]: slice.writeCloneOfSlice
 */
public fun <T> List<MaybeUninit<T>>.writeCopyOfSlice(src: List<T>): MutableList<T> {
    // SAFETY: &[T] and &[MaybeUninit<T>] have the same layout
    val uninitSrc: List<MaybeUninit<T>> = transmuteWithLayouts<List<T>, List<MaybeUninit<T>>>(src, UnresolvedTypeLayout<List<T>>(), UnresolvedTypeLayout<List<MaybeUninit<T>>>())
    run { val dstTmp0 = this; val srcTmp0 = uninitSrc; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
    // SAFETY: Valid elements have just been copied into `self` so it is initialized
    return this.assumeInitMut()
}

/**
 * Clones the elements from `src` to `self`,
 * returning a mutable reference to the now initialized contents of `self`.
 * Any already initialized elements will not be dropped.
 *
 * If `T` implements `Copy`, use [writeCopyOfSlice] instead.
 *
 * This is similar to [slice.cloneFromSlice] but does not drop existing elements.
 *
 * # Panics
 *
 * This function will panic if the two slices have different lengths, or if the implementation of `Clone` panics.
 *
 * If there is a panic, the already cloned elements will be dropped.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * var dst = MutableList(5) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 * val src = listOf("wibbly", "wobbly", "timey", "wimey", "stuff").map({ s -> s.toString() })
 * val init = dst.writeCloneOfSlice(src)
 * check(init == src)
 * ```
 *
 * ```kotlin
 * var vec = ArrayList(32)
 * val src = listOf("rust", "is", "a", "pretty", "cool", "language").map({ s -> s.toString() })
 * vec.spareCapacityMut()[0 until src.len()].writeCloneOfSlice(src)
 * // SAFETY: we have just cloned all the elements of len into the spare capacity
 * // the first src.len() elements of the vec are valid now.
 * run {
 *     vec.setLen(src.len())
 * }
 * check(vec == src)
 * ```
 *
 * [writeCopyOfSlice]: slice.writeCopyOfSlice
 */
public fun <T> List<MaybeUninit<T>>.writeCloneOfSlice(src: List<T>): MutableList<T> {
    when (val matchVal = Pair(this.size, src.size)) {
        else -> {
            val leftVal = matchVal.first
            val rightVal = matchVal.second
            if (!(leftVal == rightVal)) {
                val kind = AssertKind.Eq
                assertFailed(kind, leftVal, rightVal, Option.Some("destination and source slices have different lengths"))
            }
        }
    }
    // NOTE: We need to explicitly slice them to the same length
    // for bounds checking to be elided, and the optimizer will
    // generate memcpy for simple cases (for example T = u8).
    val len = this.size
    val src1 = ((src).subList(0, len))
    // guard is needed b/c panic might happen during a clone
    val guard = Guard(slice = this, initialized = 0)
    for (i in 0 until len) {
        guard.slice[i].write(src1[i].clone())
        guard.initialized += 1
    }
    forget(guard)
    // SAFETY: Valid elements have just been written into `self` so it is initialized
    return this.assumeInitMut()
}

/**
 * Fills a slice with elements by cloning `value`, returning a mutable reference to the now
 * initialized contents of the slice.
 * Any previously initialized elements will not be dropped.
 *
 * This is similar to [slice.fill].
 *
 * # Panics
 *
 * This function will panic if any call to `Clone` panics.
 *
 * If such a panic occurs, any elements previously initialized during this operation will be
 * dropped.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(10) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     val initialized = buf.writeFilled(1)
 *     check(initialized == List(10) { 1 })
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.writeFilled(value: T): MutableList<T> {
    this.specFill(value)
    // SAFETY: Valid elements have just been filled into `self` so it is initialized
    return this.assumeInitMut()
}

/**
 * Fills a slice with elements returned by calling a closure for each index.
 *
 * This method uses a closure to create new values. If you'd rather `Clone` a given value, use
 * [slice.writeFilled]. If you want to use the `Default` trait to generate values, use
 * [slice.writeDefault].
 *
 * # Panics
 *
 * This function will panic if any call to the provided closure panics.
 *
 * If such a panic occurs, any elements previously initialized during this operation will be
 * dropped.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(5) { MaybeUninit.uninit(PrimitiveLayouts.I32) }
 *     val initialized = buf.writeWith({ idx -> idx + 1 })
 *     check(initialized == listOf(1, 2, 3, 4, 5))
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.writeWith(f: (Int) -> T): MutableList<T> {
    val guard = Guard(slice = this, initialized = 0)
    for ((idx, element) in guard.slice.mapIndexed { index, item -> Pair(index, item) }) {
        element.write(f(idx))
        guard.initialized += 1
    }
    forget(guard)
    // SAFETY: Valid elements have just been written into `this` so it is initialized
    return this.assumeInitMut()
}

/**
 * Fills a slice with elements returned by calling [Default.default] for each index.
 *
 * # Panics
 *
 * This function will panic if any call to [Default.default] panics.
 *
 * If such a panic occurs, any elements previously initialized during this operation will be
 * dropped.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(5) { MaybeUninit.uninit(PrimitiveLayouts.I32) }
 *     val initialized = buf.writeDefault()
 *     check(initialized == listOf(0, 0, 0, 0, 0))
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.writeDefault(defaultTFn: () -> T, writeDefaultTFn: (List<MaybeUninit<T>>) -> MutableList<T>): MutableList<T> {
    interface DefaultSpec {
        public fun writeDefault(buf: MutableList<MaybeUninit<DefaultSpec>>): MutableList<DefaultSpec>
    }
    fun <T> defaultSpecWriteDefaultT(buf: MutableList<MaybeUninit<T>>, defaultTFn: () -> T): MutableList<T> = buf.writeWith({ defaultTFn() })
    fun defaultSpecWriteDefaultBoolean(buf: MutableList<MaybeUninit<Boolean>>): MutableList<Boolean> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Boolean>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultChar(buf: MutableList<MaybeUninit<Char>>): MutableList<Char> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Char>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultByte(buf: MutableList<MaybeUninit<Byte>>): MutableList<Byte> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Byte>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultUByte(buf: MutableList<MaybeUninit<UByte>>): MutableList<UByte> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<UByte>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultShort(buf: MutableList<MaybeUninit<Short>>): MutableList<Short> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Short>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultUShort(buf: MutableList<MaybeUninit<UShort>>): MutableList<UShort> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<UShort>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultInt(buf: MutableList<MaybeUninit<Int>>): MutableList<Int> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Int>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultUInt(buf: MutableList<MaybeUninit<UInt>>): MutableList<UInt> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<UInt>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultLong(buf: MutableList<MaybeUninit<Long>>): MutableList<Long> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Long>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultULong(buf: MutableList<MaybeUninit<ULong>>): MutableList<ULong> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<ULong>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultI128(buf: MutableList<MaybeUninit<I128>>): MutableList<I128> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<I128>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultU128(buf: MutableList<MaybeUninit<U128>>): MutableList<U128> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<U128>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultF16(buf: MutableList<MaybeUninit<F16>>): MutableList<F16> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<F16>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultFloat(buf: MutableList<MaybeUninit<kotlin.Float>>): MutableList<kotlin.Float> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<kotlin.Float>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultDouble(buf: MutableList<MaybeUninit<Double>>): MutableList<Double> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Double>>()).sizeBytes)
            buf.assumeInitMut()
        }
    fun defaultSpecWriteDefaultF128(buf: MutableList<MaybeUninit<F128>>): MutableList<F128> =
        run {
            kmWriteBytes((buf.asMutPtr()).rawAddress, (0).toUByte(), (buf.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<F128>>()).sizeBytes)
            buf.assumeInitMut()
        }
    return writeDefaultTFn(this)
}

/**
 * Fills a slice with elements yielded by an iterator until either all elements have been
 * initialized or the iterator is empty.
 *
 * Returns two slices. The first slice contains the initialized portion of the original slice.
 * The second slice is the still-uninitialized remainder of the original slice.
 *
 * # Panics
 *
 * This function panics if the iterator's `next` function panics.
 *
 * If such a panic occurs, any elements previously initialized during this operation will be
 * dropped.
 *
 * # Examples
 *
 * Completely filling the slice:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(5) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     val iter = listOf(1, 2, 3).intoIter().cycle()
 *     val (initialized, remainder) = buf.writeIter(iter)
 *     check(initialized == listOf(1, 2, 3, 1, 2))
 *     check(remainder.len() == 0)
 * }
 * ```
 *
 * Partially filling the slice:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(5) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     val iter = listOf(1, 2)
 *     val (initialized, remainder) = buf.writeIter(iter)
 *     check(initialized == listOf(1, 2))
 *     check(remainder.len() == 3)
 * }
 * ```
 *
 * Checking an iterator after filling a slice:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitFill)
 * public fun doctestEntry() {
 *     var buf = MutableList(3) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     var iter = listOf(1, 2, 3, 4, 5).intoIter()
 *     val (initialized, remainder) = buf.writeIter(iter.byRef())
 *     check(initialized == listOf(1, 2, 3))
 *     check(remainder.len() == 0)
 *     check(iter.asSlice() == listOf(4, 5))
 * }
 * ```
 */
public fun <T, I> List<MaybeUninit<T>>.writeIter(it: I): Pair<MutableList<T>, MutableList<MaybeUninit<T>>> where I : Iterable<T> {
    val iter = it.iterator()
    val guard = Guard(slice = this, initialized = 0)
    for ((element, value) in (guard.slice.zip(iter))) {
        element.write(value)
        guard.initialized += 1
    }
    val initializedLen = guard.initialized
    forget(guard)
    // SAFETY: guard.initialized <= self.len()
    val (initted, remainder) = this.splitAtMutUnchecked(initializedLen)
    // SAFETY: Valid elements have just been written into `init`, so that portion
    // of `this` is initialized.
    return Pair(initted.assumeInitMut(), remainder)
}

/**
 * Returns the contents of this `MaybeUninit` as a slice of potentially uninitialized bytes.
 *
 * Note that even if the contents of a `MaybeUninit` have been initialized, the value may still
 * contain padding bytes which are left uninitialized.
 *
 * This method requires `T: Freeze`. It's not available for types with interior mutability,
 * such as `Cell<T>`.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitAsBytes)
 * public fun doctestEntry() {
 *     val uninit = listOf(MaybeUninit.new(0x1234u.toUShort(), PrimitiveLayouts.U8), MaybeUninit.new(0x5678u.toUShort(), PrimitiveLayouts.U8))
 *     val uninitBytes = uninit.encodeToByteArray().map { it.toUByte() }
 *     val bytes = uninitBytes.assumeInitRef()
 *     val val1 = run { val bytesTmp0 = bytes[0 until 2]; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }
 *     val val2 = run { val bytesTmp1 = bytes[2 until 4]; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() }
 *     check((ushortArrayOf(val1, val2)).contentEquals(ushortArrayOf(0x1234u.toUShort(), 0x5678u.toUShort())))
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.asBytes(): List<MaybeUninit<UByte>> where T : Freeze {
    // SAFETY: MaybeUninit<u8> is always valid, even for padding bytes
    return linearSliceView(ConstPtr((this.asPtr()).rawAddress), sizeOfVal(this).toInt(), UnresolvedTypeLayout<MaybeUninit<UByte>>())
}

/**
 * Returns the contents of this `MaybeUninit` slice as a mutable slice of potentially
 * uninitialized bytes.
 *
 * Note that even if the contents of a `MaybeUninit` have been initialized, the value may still
 * contain padding bytes which are left uninitialized.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import std.mem.MaybeUninit
 *
 * @feature(maybeUninitAsBytes)
 * public fun doctestEntry() {
 *     var uninit = mutableListOf(MaybeUninit.uninit(PrimitiveLayouts.U16), MaybeUninit.uninit(PrimitiveLayouts.U16))
 *     val uninitBytes = uninit.asBytesMut()
 *     uninitBytes.writeCopyOfSlice(listOf(0x12, 0x34, 0x56, 0x78))
 *     val vals = uninit.assumeInitRef()
 *     if (true) {
 *         check(vals == ushortArrayOf(0x3412u.toUShort(), 0x7856u.toUShort()))
 *     } else {
 *         check(vals == ushortArrayOf(0x1234u.toUShort(), 0x5678u.toUShort()))
 *     }
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.asBytesMut(): MutableList<MaybeUninit<UByte>> {
    // SAFETY: MaybeUninit<u8> is always valid, even for padding bytes
    return linearMutSliceView((this.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, sizeOfVal(this).toInt(), UnresolvedTypeLayout<MaybeUninit<UByte>>())
}

/**
 * Drops the contained values in place.
 *
 * # Safety
 *
 * It is up to the caller to guarantee that every `MaybeUninit<T>` in the slice
 * really is in an initialized state. Calling this when the content is not yet
 * fully initialized causes undefined behavior.
 *
 * On top of that, all additional invariants of the type `T` must be
 * satisfied, as the `Drop` implementation of `T` (or its members) may
 * rely on this. For example, setting a `List<T>` to an invalid but
 * non-null address makes it initialized (under the current implementation;
 * this does not constitute a stable guarantee), because the only
 * requirement the compiler knows about it is that the data pointer must be
 * non-null. Dropping such a `List<T>` however will cause undefined
 * behaviour.
 */
public fun <T> List<MaybeUninit<T>>.assumeInitDrop() {
    if (!this.isEmpty()) {
        // SAFETY: the caller must guarantee that every element of `self`
        // is initialized and satisfies all invariants of `T`.
        // Dropping the value in place is safe if that is the case.
        dropInPlace((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
    }
}

/**
 * Gets a shared reference to the contained value.
 *
 * # Safety
 *
 * Calling this when the content is not yet fully initialized causes undefined
 * behavior: it is up to the caller to guarantee that every `MaybeUninit<T>` in
 * the slice really is in an initialized state.
 */
public fun <T> List<MaybeUninit<T>>.assumeInitRef(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<T> {
    // SAFETY: casting `slice` to a `* const[T]` is safe since the caller guarantees that
    // `slice` is initialized, and `MaybeUninit` is guaranteed to have the same layout as `T`.
    // The pointer obtained is valid since it refers to memory owned by `slice` which is a
    // reference and thus guaranteed to be valid for reads.
    return (UnresolvedTypeLayout<List<T>>()).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
}

/**
 * Gets a mutable (unique) reference to the contained value.
 *
 * # Safety
 *
 * Calling this when the content is not yet fully initialized causes undefined
 * behavior: it is up to the caller to guarantee that every `MaybeUninit<T>` in the
 * slice really is in an initialized state. For instance, `.assumeInitMut()` cannot
 * be used to initialize a `MaybeUninit` slice.
 */
public fun <T> List<MaybeUninit<T>>.assumeInitMut(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutableList<T> {
    // SAFETY: similar to safety notes for `sliceGetRef`, but we have a
    // mutable reference which is also guaranteed to be valid for writes.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<T>>())
}

/**
 * Transposes a `MaybeUninit<[T; N]>` into a `[MaybeUninit<T>; N]`.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 *
 * @feature(maybeUninitUninitArrayTranspose)
 * public fun doctestEntry() {
 *     val data: List<MaybeUninit<UByte>> = MaybeUninit.uninit(PrimitiveLayouts.U8).transpose()
 * }
 * ```
 */
public fun <T> MaybeUninit<List<T>>.transpose(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): List<MaybeUninit<T>> {
    // SAFETY: T and MaybeUninit<T> have the same layout
    return transmuteWithLayouts<MaybeUninit<List<T>>, List<MaybeUninit<T>>>(this, UnresolvedTypeLayout<MaybeUninit<List<T>>>(), UnresolvedTypeLayout<List<MaybeUninit<T>>>())
}

public fun <T> fromMaybeUninitTArrayn(arr: List<MaybeUninit<T>>, n: Int): MaybeUninit<List<T>> = arr.transpose()

public fun <T> MaybeUninit<List<T>>.asRef(n: Int): List<MaybeUninit<T>> {
    // SAFETY: T and MaybeUninit<T> have the same layout
    return ConstPtr((this).rawAddress)
}

public fun <T> MaybeUninit<List<T>>.asRefMaybeUninitTSlice(n: Int): List<MaybeUninit<T>> = this.asRef()

public fun <T> MaybeUninit<List<T>>.asMut(n: Int): List<MaybeUninit<T>> {
    // SAFETY: T and MaybeUninit<T> have the same layout
    return MutPtr((this).rawAddress)
}

public fun <T> MaybeUninit<List<T>>.asMutMaybeUninitTSlice(n: Int): MutableList<MaybeUninit<T>> = this.asMut()

public fun <T> fromMaybeUninitTArrayn(arr: MaybeUninit<List<T>>, n: Int): List<MaybeUninit<T>> = arr.transpose(n)

/**
 * Transposes a `[MaybeUninit<T>; N]` into a `MaybeUninit<[T; N]>`.
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
 *
 * @feature(maybeUninitUninitArrayTranspose)
 * public fun doctestEntry() {
 *     val data = List(1000) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
 *     val data1: MaybeUninit<UByteArray> = data.transpose()
 * }
 * ```
 */
public fun <T> List<MaybeUninit<T>>.transpose(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MaybeUninit<List<T>> {
    // SAFETY: T and MaybeUninit<T> have the same layout
    return transmuteWithLayouts<List<MaybeUninit<T>>, MaybeUninit<List<T>>>(this, UnresolvedTypeLayout<List<MaybeUninit<T>>>(), UnresolvedTypeLayout<MaybeUninit<List<T>>>())
}

public open class Guard<T>(
    public var slice: MutableList<MaybeUninit<T>>,
    public var initialized: Int,
) {
    public open fun drop() {
        val initializedPart = this.slice.subList(0, this.initialized)
        // SAFETY: this raw sub-slice will contain only initialized objects.
        run {
            initializedPart.assumeInitDrop()
        }
    }
}

public interface SpecFill<T> {
    public fun specFill(value: T)
}

public fun <T> List<MaybeUninit<T>>.specFill(value: T, cloneTFn: (T) -> T = { it }) {
    val guard = Guard(slice = this, initialized = 0)
    when (val matchVal = guard.slice.splitLastMut()) {
        null -> {}
        else -> {
            val last = matchVal.first
            val elems = matchVal.second
            for (el in elems) {
                el.write(cloneTFn(value))
                guard.initialized += 1
            }
            last.write(value)
        }
    }
    forget(guard)
}

public fun <T : TrivialClone<T>> List<MaybeUninit<T>>.specFill(value: T) {
    // SAFETY: because `T` is `TrivialClone`, this is equivalent to calling
    // `T.clone` for every element. Notably, `TrivialClone` also implies
    // that the `clone` implementation will not panic, so we can avoid
    // initialization guards and such.
    this.fillWith({ MaybeUninit.new(read(value, typeLayoutT = UnresolvedTypeLayout<T>()), UnresolvedTypeLayout<T>()) })
}
