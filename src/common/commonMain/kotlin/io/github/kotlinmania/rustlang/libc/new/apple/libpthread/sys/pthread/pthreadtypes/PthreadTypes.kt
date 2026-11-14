// port-lint: source new/apple/libpthread/sys/_pthread/_pthread_types.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.pthread.pthreadtypes

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.libc.*

// Header: `sys / _pthread / _pthread_types.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/sys/_pthread/_pthread_types.h>
//
// Note that the actual header defines `_opaque_pthread_ *` structs which are typedefed to
// `__darwin_pthread *` structs, and typedefed again in separate `_pthread_ *.h` files to their final
// `pthread_` name. This isn't useful for us so we simplify a bit and just define everything here.

public val PTHREAD_SIZE: Int = 8176

public val PTHREAD_ATTR_SIZE: Int = 56

public val PTHREAD_MUTEXATTR_SIZE: Int = 8

public val PTHREAD_MUTEX_SIZE: Int = 56

public val PTHREAD_CONDATTR_SIZE: Int = 8

public val PTHREAD_COND_SIZE: Int = 40

public val PTHREAD_ONCE_SIZE: Int = 8

public val PTHREAD_RWLOCK_SIZE: Int = 192

public val PTHREAD_RWLOCKATTR_SIZE: Int = 16

public data class PthreadAttrT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_ATTR_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadAttrT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadAttrT> =
            object : TypeLayout<PthreadAttrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_ATTR_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_ATTR_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadAttrT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_ATTR_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_ATTR_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadAttrT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_ATTR_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadCondT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_COND_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondT> =
            object : TypeLayout<PthreadCondT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_COND_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_COND_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadCondT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_COND_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_COND_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_COND_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadCondattrT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_CONDATTR_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondattrT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondattrT> =
            object : TypeLayout<PthreadCondattrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_CONDATTR_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_CONDATTR_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadCondattrT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_CONDATTR_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondattrT =
                    PthreadCondattrT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_CONDATTR_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondattrT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_CONDATTR_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadMutexT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_MUTEX_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexT> =
            object : TypeLayout<PthreadMutexT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_MUTEX_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEX_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEX_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEX_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEX_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadMutexattrT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_MUTEXATTR_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexattrT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexattrT> =
            object : TypeLayout<PthreadMutexattrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_MUTEXATTR_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEXATTR_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexattrT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEXATTR_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexattrT =
                    PthreadMutexattrT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEXATTR_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexattrT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_MUTEXATTR_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadOnceT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_ONCE_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadOnceT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadOnceT> =
            object : TypeLayout<PthreadOnceT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_ONCE_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadOnceT =
                    PthreadOnceT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_ONCE_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadOnceT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_ONCE_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadOnceT =
                    PthreadOnceT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_ONCE_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadOnceT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_ONCE_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadRwlockT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_RWLOCK_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockT> =
            object : TypeLayout<PthreadRwlockT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_RWLOCK_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCK_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCK_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCK_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCK_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public data class PthreadRwlockattrT(
    public var sig: CLong = 0L,
    public var opaque: UByteArray = UByteArray(PTHREAD_RWLOCKATTR_SIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockattrT) return false
        if (this.sig != other.sig) return false
        if (!this.opaque.contentEquals(other.opaque)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sig.hashCode()
        result = 31 * result + this.opaque.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockattrT> =
            object : TypeLayout<PthreadRwlockattrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + ((PTHREAD_RWLOCKATTR_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCKATTR_SIZE).toInt())).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockattrT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCKATTR_SIZE).toInt())).writeNative(addr + 8L, value.opaque)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockattrT =
                    PthreadRwlockattrT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCKATTR_SIZE).toInt())).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockattrT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.sig)
                    (PrimitiveArrayLayouts.uByteArray((PTHREAD_RWLOCKATTR_SIZE).toInt())).writeBytes(buf, offset + 8, value.opaque)
                }
            }
    }
}

public typealias PthreadKeyT = CUlong
