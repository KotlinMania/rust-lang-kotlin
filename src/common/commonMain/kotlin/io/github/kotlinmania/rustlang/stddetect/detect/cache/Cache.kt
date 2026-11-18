// port-lint: source detect/cache.rs
package io.github.kotlinmania.rustlang.stddetect.detect.cache

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.stddetect.detect.arch.aarch64.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.os.detectFeatures

// Caches run-time feature detection so that it only needs to be computed
// once.

/**
 * Sets the `bit` of `x`.
 */
public fun setBit(x: U128, bit: UInt): U128 = x or U128(0x0uL, 0x1uL) shl bit.toInt()

/**
 * Tests the `bit` of `x`.
 */
public fun testBit(x: U128, bit: UInt): Boolean = x and (U128(0x0uL, 0x1uL) shl bit.toInt()) != U128(0x0uL, 0x0uL)

/**
 * Unset the `bit of`x`.
 */
public fun unsetBit(x: U128, bit: UInt): U128 = x and (U128(0x0uL, 0x1uL) shl bit.toInt()).inv()

/**
 * Maximum number of features that can be cached.
 */
public val CACHE_CAPACITY: UInt = 93u

// The derived `Default` implementation will initialize the field to zero,
// which is what we want.

/**
 * This type is used to initialize the cache
 */
public open class Initializer(
    public var value: U128,
) {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Initializer = Initializer(value)

    // NOTE: the `debugAssert` would catch that we do not add more Features than
    // the one fitting our cache.

    /**
     * Tests the `bit` of the cache.
     */
    public open fun test(bit: UInt): Boolean {
        check(bit < CACHE_CAPACITY) { "too many features, time to increase the cache size!" }
        return testBit(this.value, bit)
    }

    /**
     * Sets the `bit` of the cache.
     */
    public open fun set(bit: UInt) {
        check(bit < CACHE_CAPACITY) { "too many features, time to increase the cache size!" }
        val v = this.value
        this.value = setBit(v, bit)
    }

    /**
     * Unsets the `bit` of the cache.
     */
    public open fun unset(bit: UInt) {
        check(bit < CACHE_CAPACITY) { "too many features, time to increase the cache size!" }
        val v = this.value
        this.value = unsetBit(v, bit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Initializer> =
            object : TypeLayout<Initializer> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Initializer =
                    Initializer(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Initializer) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Initializer =
                    Initializer(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Initializer) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset, value.value)
                }
            }
    }
}

// Note: the third slot is only used in x86
// Another Slot can be added if needed without any change to `Initializer`

/**
 * This global variable is a cache of the features supported by the CPU.
 */
public var CACHE: List<Cache> = listOf(Cache.uninitialized(), Cache.uninitialized(), Cache.uninitialized())

/**
 * Feature cache with capacity for `sizeOf<Int>() * 8 - 1` features.
 *
 * Note: 0 is used to represent an uninitialized cache, and (at least) the most
 * significant bit is set on any cache which has been initialized.
 *
 * Note: we use `Relaxed` atomic operations, because we are only interested in
 * the effects of operations on a single memory location. That is, we only need
 * "modification order", and not the full-blown "happens before".
 */
public open class Cache(
    public var value: AtomicUsize,
) {
    public var field0: AtomicUsize
        get() = value
        set(v) {
            value = v
        }

    /**
     * Is the `bit` in the cache set? Returns `None` if the cache has not been initialized.
     */
    public open fun test(bit: UInt): Boolean? {
        val cached = this.value.load(Ordering.Relaxed)
        return if (cached == 0) {
            null
        } else {
            testBit(U128.fromLong(cached.toLong()), bit)
        }
    }

    /**
     * Initializes the cache.
     */
    public open fun initialize(value: Int): Int {
        check((value and Cache.MASK.inv()) == 0)
        this.value.store(value or Cache.INITIALIZED_BIT, Ordering.Relaxed)
        return value
    }

    public companion object {
        public val CAPACITY: UInt = (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1).toUInt()

        public val MASK: Int = (1 shl Cache.CAPACITY.toInt()) - 1

        public val INITIALIZED_BIT: Int = 1 shl Cache.CAPACITY.toInt()

        /**
         * Creates an uninitialized cache.
         */
        public fun uninitialized(): Cache = Cache(AtomicUsize.new(0))
    }
}

public fun disableFeatures(disable: List<UByte>, value: Initializer) {
    when (val matchVal = String.fromUtf8(disable)) {
        is Result.Ok<*, *> -> {
            val disable1 = matchVal.okValue()
            for (v in disable1.split(" ")) {
                Feature.fromStr(v).map({ v1 -> value.unset(v1.toUInt()) })
            }
        }
        else -> {}
    }
}

public fun initialize(value: Initializer): Initializer {
    val RUST_STD_DETECT_UNSTABLE: CStr = "RUST_STD_DETECT_UNSTABLE"
    fun getEnvironmentVariableA(name: ConstPtr, buffer: MutPtr, size: UInt): UInt = 0u
    val len = GetEnvironmentVariableA(RUST_STD_DETECT_UNSTABLE.asPtr().cast(), MutPtr(0L), 0)
    if (len > 0u) {
        // +1 to include the null terminator.
        val env = mutableListOf(0, len.toInt() + 1)
        val len1 = GetEnvironmentVariableA(RUST_STD_DETECT_UNSTABLE.asPtr().cast(), env.asMutPtr(), len + 1u)
        if (len1 > 0u) {
            disableFeatures(env[0 until len1.toInt()], value)
        }
    }
    doInitialize(value)
    return value
}

public fun doInitialize(value: Initializer) {
    CACHE[0].initialize((value.value).toInt() and Cache.MASK)
    CACHE[1].initialize((value.value shr Cache.CAPACITY.toInt()).toInt() and Cache.MASK)
    CACHE[2].initialize((value.value shr (2 * Cache.CAPACITY).toInt()).toInt() and Cache.MASK)
}

// We only have to detect features once, and it's fairly costly, so hint to LLVM
// that it should assume that cache hits are more common than misses (which is
// the point of caching). It's possibly unfortunate that this function needs to
// reach across modules like this to call `os.detectFeatures`, but it produces
// the best code out of several attempted variants.
//
// The `Initializer` that the cache was initialized with is returned, so that
// the caller can call `test()` on it without having to load the value from the
// cache again.
public fun detectAndInitialize(): Initializer = initialize(detectFeatures())

/**
 * Tests the `bit` of the storage. If the storage has not been initialized,
 * initializes it with the result of `os.detectFeatures()`.
 *
 * On its first invocation, it detects the CPU features and caches them in the
 * `CACHE` global variable as an `AtomicU64`.
 *
 * It uses the `Feature` variant to index into this variable as a bitset. If
 * the bit is set, the feature is enabled, and otherwise it is disabled.
 *
 * If the feature `stdDetectEnvOverride` is enabled looks for the env
 * variable `RUST_STD_DETECT_UNSTABLE` and uses its content to disable
 * Features that would had been otherwise detected.
 */
public fun test(bit: UInt): Boolean {
    val (relativeBit, idx) =
        if (bit < Cache.CAPACITY) {
            Pair(bit, 0)
        } else if (bit < 2u * Cache.CAPACITY) {
            Pair(bit - Cache.CAPACITY, 1)
        } else {
            Pair(bit - 2u * Cache.CAPACITY, 2)
        }
    return (CACHE[idx].test(relativeBit) ?: detectAndInitialize().test(bit))
}
