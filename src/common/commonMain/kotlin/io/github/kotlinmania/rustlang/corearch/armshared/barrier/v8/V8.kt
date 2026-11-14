// port-lint: source arm_shared/barrier/v8.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.v8

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.corearch.armshared.Sealed

/**
 * Full system is the required shareability domain, reads are the required
 * access type
 */
public open class LD : Sealed.Dmb, Sealed.Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.LD)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.LD)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<LD> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<LD> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): LD =
                    LD()
                override fun writeNative(addr: Long, value: LD) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): LD =
                    LD()
                override fun writeBytes(buf: ByteArray, offset: Int, value: LD) {
                }
            }
    }
}

/**
 * Inner Shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Ishld : Sealed.Dmb, Sealed.Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Ishld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Ishld)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Ishld> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Ishld> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Ishld =
                    Ishld()
                override fun writeNative(addr: Long, value: Ishld) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ishld =
                    Ishld()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ishld) {
                }
            }
    }
}

/**
 * Non-shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Nshld : Sealed.Dmb, Sealed.Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Nshld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Nshld)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nshld> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nshld> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Nshld =
                    Nshld()
                override fun writeNative(addr: Long, value: Nshld) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nshld =
                    Nshld()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nshld) {
                }
            }
    }
}

/**
 * Outer Shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Oshld : Sealed.Dmb, Sealed.Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Oshld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.arg.Oshld)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Oshld> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Oshld> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Oshld =
                    Oshld()
                override fun writeNative(addr: Long, value: Oshld) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Oshld =
                    Oshld()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Oshld) {
                }
            }
    }
}
