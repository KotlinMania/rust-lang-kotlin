package io.github.kotlinmania.rustlang.core.array

import io.github.kotlinmania.rustlang.core.array.drain.Drain
import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.intrinsics.transmuteUnchecked
import io.github.kotlinmania.rustlang.core.iter.sources.repeatn.repeatN
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.trytrait.ChangeOutputType
import io.github.kotlinmania.rustlang.core.ops.trytrait.FromResidual
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.iter.Iter

/**
 * Creates an array of type `[T; N]` by repeatedly cloning a value.
 *
 * This is the same as `[val; N]`, but it also works for types that do not
 * implement [Copy].
 *
 * The provided value will be used as an element of the resulting array and
 * will be cloned N - 1 times to fill up the rest. If N is zero, the value
 * will be dropped.
 *
 * # Example
 *
 * Creating multiple copies of a `String`:
 * ```kotlin
 * import std.array
 *
 * val string = "Hello there!".toString()
 * val strings = array.repeat(string)
 * check(strings == listOf("Hello there!", "Hello there!"))
 * ```
 */
public fun <T> repeat(value: T, n: Int): List<T> {
    val iter = repeatN(value, n)
    // SAFETY: Unless a panic occurs, fromFn will call the closure N times,
    // and repeatN's next() will return Some for N times.
    return fromFn({ iter.next().unwrapUnchecked() })
}

/**
 * Creates an array where each element is produced by calling `f` with
 * that element's index while walking forward through the array.
 *
 * This is essentially the same as writing
 * ```text
 * [f(0), f(1), f(2), …, f(N - 2), f(N - 1)]
 * ```
 * and is similar to `(0 .. i).map(f)`, just for arrays not iterators.
 *
 * If `N == 0`, this produces an empty array without ever calling `f`.
 *
 * # Example
 *
 * ```kotlin
 * // type inference is helping us here, the way `fromFn` knows how many
 * // elements to produce is the length of array down there: only arrays of
 * // equal lengths can be compared, so the const generic parameter `N` is
 * // inferred to be 5, thus creating array of 5 elements.
 * val array = fromFn({ i -> i })
 * // indexes are:    0  1  2  3  4
 * check(array == listOf(0, 1, 2, 3, 4))
 * val array2: IntArray = fromFn({ i -> i * 2 })
 * // indexes are:     0  1  2  3  4  5   6   7
 * check(array2 == intArrayOf(0, 2, 4, 6, 8, 10, 12, 14))
 * val boolArr = fromFn({ i -> i % 2 == 0 }, 5)
 * // indexes are:       0     1      2     3      4
 * check(boolArr == booleanArrayOf(true, false, true, false, true))
 * ```
 *
 * You can also capture things, for example to create an array full of clones
 * where you can't just use `[item; N]` because it's not `Copy`:
 * ```kotlin
 * val myString: List<String> = fromFn({ i -> "Hello $i" })
 * check(myString == listOf("Hello 0", "Hello 1"))
 * ```
 *
 * The array is generated in ascending index order, starting from the front
 * and going towards the back, so you can use closures with mutable state:
 * ```kotlin
 * var state = 1
 * val a = fromFn({
 *     val x = state
 *     state *= 2
 *     x
 * })
 * check(a == listOf(1, 2, 4, 8, 16, 32))
 * ```
 */
public fun <T> fromFn(f: (Int) -> T, n: Int): List<T> = tryFromFn(NeverShortCircuit.wrapMut1(f)).first

/**
 * Creates an array `[T; N]` where each fallible array element `T` is returned by the `cb` call.
 * Unlike [fromFn], where the element creation can't fail, this version will return an error
 * if any element creation was unsuccessful.
 *
 * The return type of this function depends on the return type of the closure.
 * If you return `Result<T, E>` from the closure, you'll get a `Result<[T; N], E>`.
 * If you return `Option<T>` from the closure, you'll get an `Option<[T; N]>`.
 *
 * # Arguments
 *
 * * `cb`: Callback where the passed argument is the current array index.
 *
 * # Example
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(arrayTryFromFn)
 * public fun doctestEntry() {
 *     val array: Result<UByteArray> = tryFromFn({ i -> i.tryInto() })
 *     check(array == Result.success(listOf(0, 1, 2, 3, 4)))
 *     val array1: Result<ByteArray> = tryFromFn({ i -> i.tryInto() })
 *     check(array1.isErr())
 *     val array2: List<Any>? = tryFromFn({ i -> i.checkedAdd(100) })
 *     check(array2 == listOf(100, 101, 102, 103))
 *     val array3: List<Any>? = tryFromFn({ i -> i.checkedSub(100) })
 *     check(array3 == null)
 * }
 * ```
 */
public fun <R> tryFromFn(cb: (Int) -> R, n: Int): ChangeOutputType<R, List<Output>> where R : Try<R, Any?, Any?> {
    val array = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<Output>()) }
    return when (val matchVal = tryFromFnErased(array, cb)) {
        is ControlFlow.Break -> {
            val (r) = matchVal
            FromResidual.fromResidual(r)
        }
        is ControlFlow.Continue -> {
            // SAFETY: All elements of the array were populated.
            MaybeUninit.arrayAssumeInit(array)
        }
    }
}

/**
 * Converts a reference to `T` into a reference to an array of length 1 (without copying).
 */
public fun <T> fromRef(s: T): List<T> {
    // SAFETY: Converting `T` to `[T; 1]` is sound.
    return (UnresolvedTypeLayout<List<T>>()).readNative((ConstPtr(((constPtrFromRustCast(s))).rawAddress)).rawAddress)
}

/**
 * Converts a mutable reference to `T` into a mutable reference to an array of length 1 (without copying).
 */
public fun <T> fromMut(s: T): List<T> {
    // SAFETY: Converting `T` to `[T; 1]` is sound.
    return RustMutableReference(MutPtr((MutPtr(((mutPtrFromRustCast(s))).rawAddress)).rawAddress), UnresolvedTypeLayout<List<T>>())
}

/**
 * Version of [tryFromFn] using a passed-in slice in order to avoid
 * needing to monomorphize for every array length.
 *
 * This takes a generator rather than an iterator so that *at the type level*
 * it never needs to worry about running out of items.  When combined with
 * an infallible `Try` type, that means the loop canonicalizes easily, allowing
 * it to optimize well.
 *
 * It would be *possible* to unify this and [iterNextChunkErased] into one
 * function that does the union of both things, but last time it was that way
 * it resulted in poor codegen from the "are there enough source items?" checks
 * not optimizing away.  So if you give it a shot, make sure to watch what
 * happens in the codegen tests.
 */
public fun <R : Try<R, Any?, Any?>> tryFromFnErased(buffer: MutableList<MaybeUninit<Output>>, generator: (Int) -> R): ControlFlow<Residual> {
    val guard = Guard(arrayMut = buffer, initialized = 0)
    while (guard.initialized < guard.arrayMut.size) {
        val item =
            when (val tryResult = generator(guard.initialized).branch()) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
        // SAFETY: The loop condition ensures we have space to push the item
        guard.pushUnchecked(item)
    }
    forget(guard)
    return ControlFlow.Continue()
}

/**
 * Pulls `N` items from `iter` and returns them as an array. If the iterator
 * yields fewer than `N` items, `Err` is returned containing an iterator over
 * the already yielded items.
 *
 * Since the iterator is passed as a mutable reference and this function calls
 * `next` at most `N` times, the iterator can still be used afterwards to
 * retrieve the remaining items.
 *
 * If `iter.next()` panics, all items already yielded by the iterator are
 * dropped.
 *
 * Used for [Iterator.nextChunk].
 */
public fun <T> iterNextChunk(iter: Any, n: Int): Result<List<T>, IntoIter<T>> = iter.specNextChunk()

// SAFETY: `from` must have len items, and len items must be < N.
public fun <T> write(to: List<MaybeUninit<T>>, from: Any, len: Int, n: Int) {
    val guard = Guard(arrayMut = to, initialized = 0)
    while (guard.initialized < len) {
        // SAFETY: caller has guaranteed, from has len items.
        val item = from.next().unwrapUnchecked()
        // SAFETY: guard.initialized < len < N
        guard.pushUnchecked(item)
    }
    forget(guard)
}

/**
 * Version of [iterNextChunk] using a passed-in slice in order to avoid
 * needing to monomorphize for every array length.
 *
 * Unfortunately this loop has two exit conditions, the buffer filling up
 * or the iterator running out of items, making it tend to optimize poorly.
 */
public fun <T> iterNextChunkErased(buffer: MutableList<MaybeUninit<T>>, iter: Any): Result<Unit, Int> {
    // if `Iterator.next` panics, this guard will drop already initialized items
    val guard = Guard(arrayMut = buffer, initialized = 0)
    while (guard.initialized < guard.arrayMut.size) {
        val item =
            iter.next() ?: run {
                // Unlike `tryFromFnErased`, we want to keep the partial results,
                // so we need to defuse the guard instead of using `?`.
                val initialized = guard.initialized
                forget(guard)
                return Result.Err(initialized)
            }
        // SAFETY: The loop condition ensures we have space to push the item
        guard.pushUnchecked(item)
    }
    forget(guard)
    return Result.Ok(Unit)
}

/**
 * Pulls `N` items from the back of `iter` and returns them as an array.
 * If the iterator yields fewer than `N` items, `Err` is returned containing
 * an iterator over the already yielded items.
 *
 * Since the iterator is passed as a mutable reference and this function calls
 * `nextBack` at most `N` times, the iterator can still be used afterwards to
 * retrieve the remaining items.
 *
 * If `iter.nextBack()` panics, all items already yielded by the iterator are
 * dropped.
 *
 * Used for [DoubleEndedIterator.nextChunkBack].
 */
public fun <T> iterNextChunkBack(iter: Any, n: Int): Result<List<T>, IntoIter<T>> = iter.specNextChunkBack()

// SAFETY: `from` must have len items, and len items must be < N.
public fun <T> writeBack(to: List<MaybeUninit<T>>, from: Any, len: Int, n: Int) {
    val guard = GuardBack(arrayMut = to, initialized = 0)
    while (guard.initialized < len) {
        // SAFETY: caller has guaranteed, from has len items.
        val item = from.nextBack().unwrapUnchecked()
        // SAFETY: guard.initialized < len < N
        guard.pushUnchecked(item)
    }
    forget(guard)
}

/**
 * Version of [iterNextChunkBack] using a passed-in slice
 * in order to avoid needing to monomorphize for every array length.
 *
 * Unfortunately this loop has two exit conditions, the buffer filling up
 * or the iterator running out of items, making it tend to optimize poorly.
 */
public fun <T> iterNextChunkBackErased(buffer: MutableList<MaybeUninit<T>>, iter: Any): Result<Unit, Int> {
    // if `Iterator.nextBack` panics, this guard will drop already initialized items
    val guard = GuardBack(arrayMut = buffer, initialized = 0)
    while (guard.initialized < guard.arrayMut.size) {
        val item =
            iter.nextBack() ?: run {
                val initialized = guard.initialized
                forget(guard)
                return Result.Err(initialized)
            }
        // SAFETY: The loop condition ensures we have space to push the item
        guard.pushUnchecked(item)
    }
    forget(guard)
    return Result.Ok(Unit)
}

public fun <T> List<T>.asRefTSliceFromTArrayn(n: Int): List<T> = this

public fun <T> List<T>.asMutTSliceFromTArrayn(n: Int): MutableList<T> = this

public fun <T> List<T>.borrowTSliceFromTArrayn(n: Int): List<T> = this

public fun <T> List<T>.borrowMutTSliceFromTArrayn(n: Int): MutableList<T> = this

/**
 * Tries to create an array `[T; N]` by copying from a slice `[T]`.
 * Succeeds if `slice.len() == N`.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     val bytes: UByteArray = ubyteArrayOf(1u, 0u, 2u)
 *     val bytesHead: UByteArray = UByteArray.tryFrom(((bytes).sliceArray(0 until 2))).unwrap()
 *     check(1u.toUShort() == run { val bytesTmp0 = bytesHead; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() })
 *     val bytesTail: UByteArray = ((bytes).sliceArray(1 until 3))
 *     check(512u.toUShort() == run { val bytesTmp1 = bytesTail; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
 * }
 * ```
 */
public fun <T> tryFromTArrayn(slice: List<T>, n: Int): Result<List<T>, TryFromSliceError> = List.tryFrom(slice).copied()

/**
 * Tries to create an array `[T; N]` by copying from a mutable slice `[T]`.
 * Succeeds if `slice.len() == N`.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     var bytes: UByteArray = ubyteArrayOf(1u, 0u, 2u)
 *     val bytesHead: UByteArray = run { val subSlice0 = bytes.sliceArray(0 until 2); val subCall0 = UByteArray.tryFrom(subSlice0); subSlice0.copyInto(bytes, destinationOffset = 0); subCall0 }.unwrap()
 *     check(1u.toUShort() == run { val bytesTmp0 = bytesHead; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() })
 *     val bytesTail: UByteArray = (((bytes).sliceArray(1 until 3)))
 *     check(512u.toUShort() == run { val bytesTmp1 = bytesTail; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
 * }
 * ```
 */
public fun <T> tryFromTArrayn(slice: MutableList<T>, n: Int): Result<List<T>, TryFromSliceError> = List.tryFrom(slice)

/**
 * Tries to create an array ref `[T; N]` from a slice ref `[T]`. Succeeds if
 * `slice.len() == N`.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     val bytes: UByteArray = ubyteArrayOf(1u, 0u, 2u)
 *     val bytesHead: UByteArray = UByteArray.tryFrom(((bytes).sliceArray(0 until 2))).unwrap()
 *     check(1u.toUShort() == run { val bytesTmp0 = bytesHead; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() })
 *     val bytesTail: UByteArray = ((bytes).sliceArray(1 until 3))
 *     check(512u.toUShort() == run { val bytesTmp1 = bytesTail; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
 * }
 * ```
 */

/**
 * Tries to create a mutable array ref `[T; N]` from a mutable slice ref
 * `[T]`. Succeeds if `slice.len() == N`.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * public fun doctestEntry() {
 *     var bytes: UByteArray = ubyteArrayOf(1u, 0u, 2u)
 *     val bytesHead: UByteArray = run { val subSlice0 = bytes.sliceArray(0 until 2); val subCall0 = UByteArray.tryFrom(subSlice0); subSlice0.copyInto(bytes, destinationOffset = 0); subCall0 }.unwrap()
 *     check(1u.toUShort() == run { val bytesTmp0 = bytesHead; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() })
 *     val bytesTail: UByteArray = (((bytes).sliceArray(1 until 3)))
 *     check(512u.toUShort() == run { val bytesTmp1 = bytesTail; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toUShort() })
 * }
 * ```
 */

/**
 * The hash of an array is the same as that of the corresponding slice,
 * as required by the `Borrow` implementation.
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * import hash
 *
 * public fun doctestEntry() {
 *     val b = hash.RandomState.new()
 *     val a: UByteArray = ubyteArrayOf(0xa8u, 0x3cu, 0x09u)
 *     val s: List<UByte> = listOf(0xa8u.toUByte(), 0x3cu.toUByte(), 0x09u.toUByte())
 *     check(b.hashOne(a) == b.hashOne(s))
 * }
 * ```
 */
public fun <T, H> List<T>.hashFromTArrayn(state: H, n: Int) {
    this.hash(state)
}

public fun <T> List<T>.fmtFromTArrayn(f: StringBuilder, n: Int): Result<Unit, Unit> =
    run {
        f.append((this).toString())
        Result.Ok(Unit)
    }

public fun <T> List<T>.intoIter(n: Int): Iter<T> = this.iter()

public fun <T, I> List<T>.indexIFromTArrayn(index: I, n: Int): Output = (((this as Any?) as List<T>)).index(index)

public fun <T, I> List<T>.indexMutIFromTArrayn(index: I, n: Int): Output = (((this as Any?) as MutableList<T>)).indexMut(index)

/**
 * Implements comparison of arrays [lexicographically](Ord#lexicographical-comparison).
 */
public fun <T> List<T>.partialCmpFromTArrayn(other: List<T>, n: Int): Ordering? = List.partialCmp(this, other)

public fun <T> List<T>.ltFromTArrayn(other: List<T>, n: Int): Boolean = List.lt(this, other)

public fun <T> List<T>.leFromTArrayn(other: List<T>, n: Int): Boolean = List.le(this, other)

public fun <T> List<T>.geFromTArrayn(other: List<T>, n: Int): Boolean = List.ge(this, other)

public fun <T> List<T>.gtFromTArrayn(other: List<T>, n: Int): Boolean = List.gt(this, other)

public fun <T> List<T>.chainingLessFromTArrayn(other: List<T>, n: Int): ControlFlow<Boolean> = List.chainingLess(this, other)

public fun <T> List<T>.chainingGreaterFromTArrayn(other: List<T>, n: Int): ControlFlow<Boolean> = List.chainingGreater(this, other)

/**
 * Implements comparison of arrays [lexicographically](Ord#lexicographical-comparison).
 */
public fun <T> List<T>.cmpFromTArrayn(other: List<T>, n: Int): Ordering = this.cmp(other)

public fun <T> List<T>.cloneFromTArrayn(n: Int): List<T> = SpecArrayClone.clone(this)

public fun <T> List<T>.cloneFromFromTArrayn(other: List<T>, n: Int) {
    this.cloneFromSlice(other)
}

public fun <T> specArrayCloneCloneT(array: List<T>, n: Int): List<T> {
    var ptr: ConstPtr = array.asPtr()
    // SAFETY: Unless a panic occurs, fromFn will call the closure N times,
    // so our pointer arithmetic will be in bounds for the N-element array.
    // This works even for ZSTs, since in that case, add() is a no-op.
    return fromFn({
        run {
            val old = ptr
            ptr = ConstPtr((ptr).rawAddress + ((1).toLong() * (UnresolvedTypeLayout<T>()).strideBytes))
            ((UnresolvedTypeLayout<T>()).readNative((old).rawAddress)).clone()
        }
    })
}

public fun <T> defaultTArray32(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray3211111111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn(), defaultTFn())

public fun <T> defaultTArray32111111111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn(), defaultTFn())

public fun <T> defaultTArray321111111111111111111111111111111(defaultTFn: () -> T): List<T> = listOf(defaultTFn())

public fun <T> defaultTArray3211111111111111111111111111111111(): List<T> = listOf()

/**
 * Returns an array of the same size as `self`, with function `f` applied to each element
 * in order.
 *
 * If you don't necessarily need a new fixed-size array, consider using
 * [Iterator.map] instead.
 *
 * # Note on performance and stack usage
 *
 * Note that this method is *eager*.  It evaluates `f` all `N` times before
 * returning the new array.
 *
 * That means that `arr.map(f).map(g)` is, in general, *not* equivalent to
 * `array.map(| x | g(f(x)))`, as the former calls `f` 4 times then `g` 4 times,
 * whereas the latter interleaves the calls (`fgfgfgfg`).
 *
 * A consequence of this is that it can have fairly-high stack usage, especially
 * in debug mode or for long arrays.  The backend may be able to optimize it
 * away, but especially for complicated mappings it might not be able to.
 *
 * If you're doing a one-step `map` and really want an array as the result,
 * then absolutely use this method.  Its implementation uses a bunch of tricks
 * to help the optimizer handle it well.  Particularly for simple arrays,
 * like `[UByte; 3]` or `[Float; 4]`, there's nothing to be concerned about.
 *
 * However, if you don't actually need an *array* of the results specifically,
 * just to process them, then you likely want [Iterator.map] instead.
 *
 * For example, rather than doing an array-to-array map of all the elements
 * in the array up-front and only iterating after that completes,
 *
 * ```kotlin
 * for (x in myArray.map(f)) {
 *     // ...
 * }
 * ```
 *
 * It's often better to use an iterator along the lines of
 *
 * ```kotlin
 * for (x in myArray.intoIter().map(f)) {
 *     // ...
 * }
 * ```
 *
 * as that's more likely to avoid large temporaries.
 *
 * # Examples
 *
 * ```kotlin
 * val x = listOf(1, 2, 3)
 * val y = x.map({ v -> v + 1 })
 * check(y == listOf(2, 3, 4))
 * val x1 = listOf(1, 2, 3)
 * var temp = 0
 * val y1 = x1.map({ v ->
 *     temp += 1
 *     v * temp
 * })
 * check(y1 == listOf(1, 4, 9))
 * val x2 = listOf("Ferris", "Bueller's", "Day", "Off")
 * val y2 = x2.map({ v -> v.len() })
 * check(y2 == listOf(6, 9, 3, 3))
 * ```
 */
public fun <T, U> List<T>.map(f: (T) -> U, n: Int): List<U> = this.tryMap(NeverShortCircuit.wrapMut1(f)).first

/**
 * A fallible function `f` applied to each element on array `self` in order to
 * return an array the same size as `self` or the first error encountered.
 *
 * The return type of this function depends on the return type of the closure.
 * If you return `Result<T, E>` from the closure, you'll get a `Result<[T; N], E>`.
 * If you return `Option<T>` from the closure, you'll get an `Option<[T; N]>`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero
 *
 * @feature(arrayTryMap)
 * public fun doctestEntry() {
 *     val a = listOf("1", "2", "3")
 *     val b = a.tryMap({ v -> (v.toUIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse UInt"))) }).unwrap().map({ v -> v + 1 })
 *     check(b == listOf(2, 3, 4))
 *     val a1 = listOf("1", "2a", "3")
 *     val b1 = a1.tryMap({ v -> (v.toUIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse UInt"))) })
 *     check(b1.isErr())
 *     val z = listOf(1, 2, 0, 3, 4)
 *     check(z.tryMap(NonZero.new) == null)
 *     val a2 = listOf(1, 2, 3)
 *     val b2 = a2.tryMap(NonZero.new)
 *     val c = b2.map({ x -> x.map(NonZero.get) })
 *     check(c == a2)
 * }
 * ```
 */
public fun <T, R> List<T>.tryMap(f: (T) -> R, n: Int): ChangeOutputType<R, List<Output>> where R : Try<R, Any?, Any?> {
    val me = this
    // SAFETY: tryFromFn calls `f` N times.
    val f1 = Drain.new(me, f)
    return tryFromFn(f1)
}

/**
 * Returns a slice containing the entire array. Equivalent to `s[..]`.
 */
public fun <T> List<T>.asSlice(n: Int): List<T> = this

/**
 * Returns a mutable slice containing the entire array. Equivalent to
 * `s[..]`.
 */
public fun <T> List<T>.asMutSlice(n: Int): MutableList<T> = this

/**
 * Borrows each element and returns an array of references with the same
 * size as `self`.
 *
 * # Example
 *
 * ```kotlin
 * val floats = listOf(3.1, 2.7, -1.0)
 * val floatRefs: DoubleArray = floats.eachRef()
 * check(floatRefs == doubleArrayOf(3.1, 2.7, -1.0))
 * ```
 *
 * This method is particularly useful if combined with other methods, like
 * [`map`](#method.map). This way, you can avoid moving the original
 * array if its elements are not [Copy].
 *
 * ```kotlin
 * val strings = listOf("Ferris".toString(), "♥".toString(), "Rust".toString())
 * val isAscii = strings.eachRef().map({ s -> s.isAscii() })
 * check(isAscii == booleanArrayOf(true, false, true))
 * // We can still access the original array: it has not been moved.
 * check(strings.len() == 3)
 * ```
 */
public fun <T> List<T>.eachRef(n: Int): List<T> {
    val buf = MutableList(n) { ConstPtr(0L) }
    // Note(constTraitImpl): We would like to simply use iterators for this (as in the original implementation), but this is not allowed in constant expressions.
    var i = 0
    while (i < n) {
        buf[i] = addrOf(this[i])
        i += 1
    }
    // SAFETY: `* const T` has the same layout as `T`, and we've also initialised each pointer as a valid reference.
    return transmuteUnchecked(buf)
}

/**
 * Borrows each element mutably and returns an array of mutable references
 * with the same size as `self`.
 *
 * # Example
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 *
 * var floats = mutableListOf(3.1, 2.7, -1.0)
 * val floatRefs: List<RustMutableReference<Double>> = floats.eachMut()
 * (floatRefs[0]).write(0.0)
 * check(floatRefs == listOf(0.0, 2.7, -1.0))
 * check(floats == listOf(0.0, 2.7, -1.0))
 * ```
 */
public fun <T> List<T>.eachMut(n: Int): List<T> {
    val buf = MutableList(n) { MutPtr(0L) }
    // Note(constTraitImpl): We would like to simply use iterators for this (as in the original implementation), but this is not allowed in constant expressions.
    var i = 0
    while (i < n) {
        buf[i] = addrOfMut(this[i])
        i += 1
    }
    // SAFETY: `* T` has the same layout as `T`, and we've also initialised each pointer as a valid reference.
    return transmuteUnchecked(buf)
}

/**
 * Divides one array reference into two at an index.
 *
 * The first will contain all indices from `[0, M)` (excluding
 * the index `M` itself) and the second will contain all
 * indices from `[M, N)` (excluding the index `N` itself).
 *
 * # Panics
 *
 * Panics if `M> N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(splitArray)
 * public fun doctestEntry() {
 *     val v = listOf(1, 2, 3, 4, 5, 6)
 *     run {
 *         val (left, right) = v.splitArrayRef()
 *         check(left == listOf())
 *         check(right == listOf(1, 2, 3, 4, 5, 6))
 *     }
 *     run {
 *         val (left1, right1) = v.splitArrayRef()
 *         check(left1 == listOf(1, 2))
 *         check(right1 == listOf(3, 4, 5, 6))
 *     }
 *     run {
 *         val (left2, right2) = v.splitArrayRef()
 *         check(left2 == listOf(1, 2, 3, 4, 5, 6))
 *         check(right2 == listOf())
 *     }
 * }
 * ```
 */
public fun <T> List<T>.splitArrayRef(n: Int, m: Int): Pair<List<T>, List<T>> = this.splitFirstChunk().unwrap()

/**
 * Divides one mutable array reference into two at an index.
 *
 * The first will contain all indices from `[0, M)` (excluding
 * the index `M` itself) and the second will contain all
 * indices from `[M, N)` (excluding the index `N` itself).
 *
 * # Panics
 *
 * Panics if `M> N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(splitArray)
 * public fun doctestEntry() {
 *     var v = mutableListOf(1, 0, 3, 0, 5, 6)
 *     val (left, right) = v.splitArrayMut()
 *     check(left == listOf(1, 0))
 *     check(right == listOf(3, 0, 5, 6))
 *     left[1] = 2
 *     right[1] = 4
 *     check(v == listOf(1, 2, 3, 4, 5, 6))
 * }
 * ```
 */
public fun <T> List<T>.splitArrayMut(n: Int, m: Int): Pair<List<T>, MutableList<T>> = this.splitFirstChunkMut().unwrap()

/**
 * Divides one array reference into two at an index from the end.
 *
 * The first will contain all indices from `[0, N - M)` (excluding
 * the index `N - M` itself) and the second will contain all
 * indices from `[N - M, N)` (excluding the index `N` itself).
 *
 * # Panics
 *
 * Panics if `M> N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(splitArray)
 * public fun doctestEntry() {
 *     val v = listOf(1, 2, 3, 4, 5, 6)
 *     run {
 *         val (left, right) = v.rsplitArrayRef()
 *         check(left == listOf(1, 2, 3, 4, 5, 6))
 *         check(right == listOf())
 *     }
 *     run {
 *         val (left1, right1) = v.rsplitArrayRef()
 *         check(left1 == listOf(1, 2, 3, 4))
 *         check(right1 == listOf(5, 6))
 *     }
 *     run {
 *         val (left2, right2) = v.rsplitArrayRef()
 *         check(left2 == listOf())
 *         check(right2 == listOf(1, 2, 3, 4, 5, 6))
 *     }
 * }
 * ```
 */
public fun <T> List<T>.rsplitArrayRef(n: Int, m: Int): Pair<List<T>, List<T>> = this.splitLastChunk().unwrap()

/**
 * Divides one mutable array reference into two at an index from the end.
 *
 * The first will contain all indices from `[0, N - M)` (excluding
 * the index `N - M` itself) and the second will contain all
 * indices from `[N - M, N)` (excluding the index `N` itself).
 *
 * # Panics
 *
 * Panics if `M> N`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(splitArray)
 * public fun doctestEntry() {
 *     var v = mutableListOf(1, 0, 3, 0, 5, 6)
 *     val (left, right) = v.rsplitArrayMut()
 *     check(left == listOf(1, 0))
 *     check(right == listOf(3, 0, 5, 6))
 *     left[1] = 2
 *     right[1] = 4
 *     check(v == listOf(1, 2, 3, 4, 5, 6))
 * }
 * ```
 */
public fun <T> List<T>.rsplitArrayMut(n: Int, m: Int): Pair<MutableList<T>, List<T>> = this.splitLastChunkMut().unwrap()

public fun <I : Iterator<T>, T> I.specNextChunk(n: Int): Result<List<T>, IntoIter<T>> {
    val array = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }
    val r = iterNextChunkErased(array, this)
    return when (r) {
        is Result.Ok<*, *> -> {
            // SAFETY: All elements of `array` were populated.
            Result.Ok(MaybeUninit.arrayAssumeInit(array))
        }
        is Result.Err<*, *> -> {
            val initialized = r.error
            // SAFETY: Only the first `initialized` elements were populated
            Result.Err(IntoIter.newUnchecked(array, 0 until initialized))
        }
    }
}

public fun <I, T> I.specNextChunkTN(n: Int): Result<List<T>, IntoIter<T>> where I : Iterator<T>, I : TrustedLen<I> {
    val len = this.sizeHint().first
    val array = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }
    return if (len < n) {
        // SAFETY: `TrustedLen`, an unchecked trait, requires that i can get len items out of it.
        write(array, this, len)
        // SAFETY: Only the first `len` elements were populated
        Result.Err(IntoIter.newUnchecked(array, 0 until len))
    } else {
        // SAFETY: `TrustedLen`, an unchecked trait, requires that i can get N items out of it.
        write(array, this, n)
        // SAFETY: All N items were populated
        Result.Ok(MaybeUninit.arrayAssumeInit(array))
    }
}

public fun <I, T> I.specNextChunkBack(n: Int): Result<List<T>, IntoIter<T>> {
    val array = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }
    val r = iterNextChunkBackErased(array, this)
    return when (r) {
        is Result.Ok<*, *> -> {
            // SAFETY: All elements of `array` were populated.
            Result.Ok(MaybeUninit.arrayAssumeInit(array))
        }
        is Result.Err<*, *> -> {
            val initialized = r.error
            // SAFETY: Only the last `initialized` elements were populated
            Result.Err(IntoIter.newUnchecked(array, n - initialized until n))
        }
    }
}

public fun <I : TrustedLen<I>, T> I.specNextChunkBackTN(n: Int): Result<List<T>, IntoIter<T>> {
    val len = this.sizeHint().first
    val array = MutableList(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }
    return if (len < n) {
        // SAFETY: `TrustedLen`, an unchecked trait, requires that i can get len items out of it.
        writeBack(array, this, len)
        // SAFETY: Only the last `len` elements were populated
        Result.Err(IntoIter.newUnchecked(array, n - len until n))
    } else {
        // SAFETY: `TrustedLen`, an unchecked trait, requires that i can get N items out of it.
        writeBack(array, this, n)
        // SAFETY: All N items were populated
        Result.Ok(MaybeUninit.arrayAssumeInit(array))
    }
}
