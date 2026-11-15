// port-lint: source math/support/feature_detect.rs
package io.github.kotlinmania.rustlang.libm.math.support.featuredetect

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.math.support.coldPath

// Helpers for runtime target feature detection that are shared across architectures.
//
// Given a list of identifiers, assign each one a unique sequential single-bit mask.
//
// Call `init` once to choose an implementation, then use it for the rest of the program.
//
// - `sig` is the function type.
// - `init` is an expression called at startup that chooses an implementation and returns a
//   function pointer.
// - `call` is an expression to call a function returned by `init`, encapsulating any safety
//   preconditions.
//
// The type `Func` is available in `init` and `call`.
//
// This is effectively our version of an ifunc without linker support. Note that `init` may be
// called more than once until one completes.

/**
 * Helper for working with bit flags, based on `bitflags`.
 */
public open class Flags(
    public var value: UInt,
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Flags = Flags(value)

    // only used on some architectures

    /**
     * Get the integer representation.
     */
    public open fun bits(): UInt = this.value

    /**
     * Set any bits in `mask`.
     */
    public open fun insert(mask: UInt) {
        this.value = this.value or mask
    }

    /**
     * Check whether the mask is set.
     */
    public open fun contains(mask: UInt): Boolean = this.value and mask == mask

    /**
     * Check whether the nth bit is set.
     */
    public open fun testNth(bit: UInt): Boolean {
        check(bit < 32u) { "bit index out-of-bounds" }
        return this.value and (1u shl bit.toInt()) != 0u
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Flags> =
            object : TypeLayout<Flags> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Flags =
                    Flags(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Flags) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flags =
                    Flags(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flags) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * No bits set.
         */
        public fun empty(): Flags = Flags(0)

        /**
         * Create with bits already set.
         */
        public fun fromBits(value: UInt): Flags = Flags(value)
    }
}

// only used on some architectures

/**
 * Load flags from an atomic value. If the flags have not yet been initialized, call `init`
 * to do so.
 *
 * Note that `init` may run more than once.
 */
public fun getOrInitFlagsCache(cache: AtomicU32, init: () -> Flags): Flags {
    // The top bit is used to indicate that the values have already been set once.
    val INITIALIZED: UInt = 1u shl 31
    // Relaxed ops are sufficient since the result should always be the same.
    var flags = Flags.fromBits(cache.load(Ordering.Relaxed))
    if (!flags.contains(INITIALIZED)) {
        // Without this, `init` is inlined and the bit check gets wrapped in `init`s lengthy
        // prologue/epilogue. Cold pathing gives a preferable load->test->?jmp->ret.
        coldPath()
        flags = init()
        check(!flags.contains(INITIALIZED)) { "initialized bit shouldn't be set" }
        flags.insert(INITIALIZED)
        cache.store(flags.bits(), Ordering.Relaxed)
    }
    return flags
}
