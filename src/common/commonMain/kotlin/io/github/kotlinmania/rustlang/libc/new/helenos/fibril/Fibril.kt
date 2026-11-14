// port-lint: source new/helenos/fibril.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

// HelenOS fibrils API
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/fibril.h>

public fun interface FibrilCreateGenericFuncCallback {
    public operator fun invoke(arg0: MutPtr): ErrnoT
}

public typealias FidT = MutPtr

public class FibrilOwnerInfoT internal constructor(
    ownedByCtorArg: MutPtr,
) {
    public var ownedBy: MutPtr = ownedByCtorArg
        internal set

    public constructor() : this(
        ownedByCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.ownedBy

    public fun copy(ownedBy: MutPtr = this.ownedBy): FibrilOwnerInfoT = FibrilOwnerInfoT(ownedByCtorArg = ownedBy)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FibrilOwnerInfoT) return false
        if (this.ownedBy != other.ownedBy) return false
        return true
    }

    override fun hashCode(): Int = this.ownedBy.hashCode()

    public fun clone(): FibrilOwnerInfoT = FibrilOwnerInfoT(ownedBy)

    public companion object {
        public fun typeLayout(): TypeLayout<FibrilOwnerInfoT> =
            object : TypeLayout<FibrilOwnerInfoT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FibrilOwnerInfoT =
                    FibrilOwnerInfoT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FibrilOwnerInfoT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ownedBy)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FibrilOwnerInfoT =
                    FibrilOwnerInfoT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FibrilOwnerInfoT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ownedBy)
                }
            }

        public operator fun invoke(
            ownedBy: MutPtr = MutPtr(0L),
        ): FibrilOwnerInfoT = FibrilOwnerInfoT(
            ownedByCtorArg = ownedBy,
        )
    }
}

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class FibrilT(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public expect fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT

public expect fun fibrilStart(f: FidT)

public expect fun fibrilExit(retval: CLong): Nothing

public expect fun fibrilDetach(f: FidT)

public expect fun fibrilYield()

public expect fun fibrilUsleep(usec: UsecT)

public expect fun fibrilGetId(): FidT
