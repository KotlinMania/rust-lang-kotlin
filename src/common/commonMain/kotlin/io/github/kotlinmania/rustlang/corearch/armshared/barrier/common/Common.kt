// port-lint: source arm_shared/barrier/common.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.common

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.corearch.armshared.Sealed

// Access types available on all architectures

/**
 * Full system is the required shareability domain, reads and writes are the
 * required access types
 */
public open class SY : Sealed.Dmb, Sealed.Dsb, Sealed.Isb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.SY)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.SY)
    }

    public override fun isb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.isb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.SY)
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
