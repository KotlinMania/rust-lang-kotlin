// port-lint: source os/xous/ffi/definitions/memoryflags.rs
package io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.memoryflags

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ops.BitAnd
import io.github.kotlinmania.rustlang.core.ops.BitAndAssign
import io.github.kotlinmania.rustlang.core.ops.BitOr
import io.github.kotlinmania.rustlang.core.ops.BitOrAssign
import io.github.kotlinmania.rustlang.core.ops.BitXor
import io.github.kotlinmania.rustlang.core.ops.BitXorAssign
import io.github.kotlinmania.rustlang.core.ops.Not
import io.github.kotlinmania.rustlang.core.ops.Sub
import io.github.kotlinmania.rustlang.core.ops.SubAssign

/**
 * Flags to be passed to the MapMemory struct.
 * Note that it is an error to have memory be
 * writable and not readable.
 */
public open class MemoryFlags(
    public var bits: Int,
) : BitOr<MemoryFlags, Any?, MemoryFlags>, BitOrAssign<MemoryFlags>, BitXor<MemoryFlags, Any?, MemoryFlags>, BitXorAssign<MemoryFlags>, BitAnd<MemoryFlags, Any?, MemoryFlags>, BitAndAssign<MemoryFlags>, Sub<MemoryFlags, Any?, MemoryFlags>, SubAssign<MemoryFlags>, Not<Any?, MemoryFlags> {
    public open fun bits(): Int = this.bits

    public open fun isEmpty(): Boolean = (this.bits == 0).toUByte()

    public open fun fmt(f: StringBuilder) {
        f.append((this.bits).toString())
    }

    public open fun fmtOctal(f: StringBuilder) {
        f.append((this.bits).toString())
    }

    public open fun fmtLowerHex(f: StringBuilder) {
        f.append((this.bits).toString())
    }

    public open fun fmtUpperHex(f: StringBuilder) {
        f.append((this.bits).toString())
    }

    /**
     * Returns the union of the two sets of flags.
     */
    public open fun bitor(other: MemoryFlags): MemoryFlags = MemoryFlags(bits = this.bits or other.bits)

    /**
     * Adds the set of flags.
     */
    public open fun bitorAssign(other: MemoryFlags) {
        this.bits = this.bits or other.bits
    }

    /**
     * Returns the left flags, but with all the right flags toggled.
     */
    public open fun bitxor(other: MemoryFlags): MemoryFlags = MemoryFlags(bits = this.bits xor other.bits)

    /**
     * Toggles the set of flags.
     */
    public open fun bitxorAssign(other: MemoryFlags) {
        this.bits = this.bits xor other.bits
    }

    /**
     * Returns the intersection between the two sets of flags.
     */
    public open fun bitand(other: MemoryFlags): MemoryFlags = MemoryFlags(bits = this.bits and other.bits)

    /**
     * Disables all flags disabled in the set.
     */
    public open fun bitandAssign(other: MemoryFlags) {
        this.bits = this.bits and other.bits
    }

    /**
     * Returns the set difference of the two sets of flags.
     */
    public open fun sub(other: MemoryFlags): MemoryFlags = MemoryFlags(bits = this.bits and other.bits.inv())

    /**
     * Disables all flags enabled in the set.
     */
    public open fun subAssign(other: MemoryFlags) {
        this.bits = this.bits and other.bits.inv()
    }

    /**
     * Returns the complement of this set of flags.
     */
    public open fun not(): MemoryFlags = MemoryFlags(bits = !this.bits) and MemoryFlags(bits = 15)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<MemoryFlags> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<MemoryFlags> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): MemoryFlags =
                    MemoryFlags(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MemoryFlags) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.bits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MemoryFlags =
                    MemoryFlags(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MemoryFlags) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.bits)
                }
            }

        /**
         * Free this memory
         */
        public val FREE: MemoryFlags = MemoryFlags(bits = 0x0)

        /**
         * Immediately allocate this memory.  Otherwise it will
         * be demand-paged.  This is implicitly set when `phys`
         * is not 0.
         */
        public val RESERVE: MemoryFlags = MemoryFlags(bits = 0x1)

        /**
         * Allow the CPU to read from this page.
         */
        public val R: MemoryFlags = MemoryFlags(bits = 0x2)

        /**
         * Allow the CPU to write to this page.
         */
        public val W: MemoryFlags = MemoryFlags(bits = 0x4)

        /**
         * Allow the CPU to execute from this page.
         */
        public val X: MemoryFlags = MemoryFlags(bits = 0x8)

        public fun fromBits(raw: Int): MemoryFlags? =
            if (raw > 16) {
                null
            } else {
                MemoryFlags(bits = raw)
            }

        public fun empty(): MemoryFlags = MemoryFlags(bits = 0)

        public fun all(): MemoryFlags = MemoryFlags(bits = 15)
    }
}
