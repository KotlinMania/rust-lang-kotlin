// port-lint: source arm_shared/barrier/not_mclass.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.notmclass

import io.github.kotlinmania.rustlang.armshared.Dmb
import io.github.kotlinmania.rustlang.armshared.Dsb
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

// Access types available on v7 and v8 but not on v7(E)-M or v8-M

/**
 * Full system is the required shareability domain, writes are the required
 * access type
 */
public open class ST : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.ST)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.ST)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ST> =
            object : TypeLayout<ST> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ST =
                    ST()
                override fun writeNative(addr: Long, value: ST) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ST =
                    ST()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ST) {
                }
            }
    }
}

/**
 * Inner Shareable is the required shareability domain, reads and writes are
 * the required access types
 */
public open class Ish : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Ish)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Ish)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Ish> =
            object : TypeLayout<Ish> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Ish =
                    Ish()
                override fun writeNative(addr: Long, value: Ish) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ish =
                    Ish()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ish) {
                }
            }
    }
}

/**
 * Inner Shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Ishst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Ishst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Ishst)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Ishst> =
            object : TypeLayout<Ishst> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Ishst =
                    Ishst()
                override fun writeNative(addr: Long, value: Ishst) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ishst =
                    Ishst()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ishst) {
                }
            }
    }
}

/**
 * Non-shareable is the required shareability domain, reads and writes are the
 * required access types
 */
public open class Nsh : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Nsh)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Nsh)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Nsh> =
            object : TypeLayout<Nsh> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Nsh =
                    Nsh()
                override fun writeNative(addr: Long, value: Nsh) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nsh =
                    Nsh()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nsh) {
                }
            }
    }
}

/**
 * Non-shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Nshst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Nshst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Nshst)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Nshst> =
            object : TypeLayout<Nshst> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Nshst =
                    Nshst()
                override fun writeNative(addr: Long, value: Nshst) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nshst =
                    Nshst()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nshst) {
                }
            }
    }
}

/**
 * Outer Shareable is the required shareability domain, reads and writes are
 * the required access types
 */
public open class Osh : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Osh)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Osh)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Osh> =
            object : TypeLayout<Osh> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Osh =
                    Osh()
                override fun writeNative(addr: Long, value: Osh) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Osh =
                    Osh()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Osh) {
                }
            }
    }
}

/**
 * Outer Shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Oshst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dmb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Oshst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.armshared.barrier.dsb(io.github.kotlinmania.rustlang.armshared.barrier.notmclass.Oshst)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Oshst> =
            object : TypeLayout<Oshst> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Oshst =
                    Oshst()
                override fun writeNative(addr: Long, value: Oshst) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Oshst =
                    Oshst()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Oshst) {
                }
            }
    }
}
