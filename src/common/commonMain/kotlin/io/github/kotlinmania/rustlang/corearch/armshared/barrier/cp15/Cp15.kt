// port-lint: source arm_shared/barrier/cp15.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.cp15

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.corearch.arch.asm
import io.github.kotlinmania.rustlang.corearch.armshared.Sealed

/**
 * Full system is the required shareability domain, reads and writes are the
 * required access types
 */
public open class SY : Sealed.Dmb, Sealed.Dsb, Sealed.Isb {
    public override fun dmb() {
        asm("mcr p15, 0, {}, c7, c10, 5", reg, 0u, options(preservesFlags, nostack))
    }

    public override fun dsb() {
        asm("mcr p15, 0, {}, c7, c10, 4", reg, 0u, options(preservesFlags, nostack))
    }

    public override fun isb() {
        asm("mcr p15, 0, {}, c7, c5, 4", reg, 0u, options(preservesFlags, nostack))
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<SY> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<SY> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): SY =
                    SY()
                override fun writeNative(addr: Long, value: SY) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): SY =
                    SY()
                override fun writeBytes(buf: ByteArray, offset: Int, value: SY) {
                }
            }
    }
}
