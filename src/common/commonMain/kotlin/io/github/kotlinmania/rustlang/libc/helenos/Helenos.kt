// port-lint: source helenos.rs
package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

public fun interface PthreadKeyCreateDestructorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public typealias IntmaxT = Long

public typealias UintmaxT = ULong

public typealias IntptrT = Long

public typealias UintptrT = ULong

public typealias SizeT = ULong

public typealias SsizeT = Long

// uspace/lib/posix/include/posix/pthread.h
public typealias PthreadKeyT = CInt

// uspace/lib/posix/include/posix/sys/types.h
public typealias ClockidT = CInt

public typealias PidT = CInt

// static inline' functions from libc
// common/include/adt/list.h
// common/include/adt/list.h
public class LinkT internal constructor(
    nextCtorArg: MutPtr,
    prevCtorArg: MutPtr,
) {
    public var next: MutPtr = nextCtorArg
        internal set

    public var prev: MutPtr = prevCtorArg
        internal set

    public constructor() : this(
        nextCtorArg = MutPtr(0L),
        prevCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.next

    public operator fun component2(): MutPtr = this.prev

    public fun copy(next: MutPtr = this.next, prev: MutPtr = this.prev): LinkT = LinkT(nextCtorArg = next, prevCtorArg = prev)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinkT) return false
        if (this.next != other.next) return false
        if (this.prev != other.prev) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.next.hashCode()
        result = 31 * result + this.prev.hashCode()
        return result
    }

    public fun clone(): LinkT = LinkT(next, prev)

    public companion object {
        public fun typeLayout(): TypeLayout<LinkT> =
            object : TypeLayout<LinkT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): LinkT =
                    LinkT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: LinkT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.next)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.prev)
                }
                override fun readBytes(buf: ByteArray, offset: Int): LinkT =
                    LinkT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: LinkT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.next)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.prev)
                }
            }

        public operator fun invoke(
            next: MutPtr = MutPtr(0L),
            prev: MutPtr = MutPtr(0L),
        ): LinkT = LinkT(
            nextCtorArg = next,
            prevCtorArg = prev,
        )
    }
}

public data class ListT(
    public var head: LinkT = LinkT(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ListT> =
            object : TypeLayout<ListT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ListT =
                    ListT(
                        (LinkT.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ListT) {
                    (LinkT.typeLayout()).writeNative(addr, value.head)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ListT =
                    ListT(
                        (LinkT.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ListT) {
                    (LinkT.typeLayout()).writeBytes(buf, offset, value.head)
                }
            }
    }
}

// uspace/lib/posix/include/posix/time.h
public val CLOCK_REALTIME: ClockidT = 0

public val CLOCK_MONOTONIC: ClockidT = 1

public fun listInitialize(list: ListT) {
    list.head.next = list.head
    list.head.prev = list.head
}

// common/include/strError.h
public expect fun strError(err: ErrnoT): ConstPtr

public expect fun strErrorName(err: ErrnoT): ConstPtr

// uspace/lib/posix/include/posix/pthread.h
public expect fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt

public expect fun pthreadGetspecific(key: PthreadKeyT): MutPtr

public expect fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt

public expect fun pthreadKeyDelete(key: PthreadKeyT): CInt

// uspace/lib/posix/include/posix/time.h
public expect fun clockGetres(clockId: ClockidT, res: MutPtr): CInt

public expect fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt

public expect fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt

public expect fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt

// uspace/lib/posix/include/posix/unistd.h
public expect fun getpid(): PidT

public expect fun getcwd(buf: MutPtr, size: SizeT): MutPtr

public expect fun chdir(buf: ConstPtr): CInt

public expect val ENVIRON: ConstPtr

public expect fun isatty(fd: CInt): CInt
