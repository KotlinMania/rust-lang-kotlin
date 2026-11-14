// port-lint: source new/helenos/dirent_mod.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.direntmod

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

// HelenOS directory entry API
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/dirent.h>

public data class Dirent(
    public var dName: UByteArray = UByteArray(256),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dirent) return false
        if (!this.dName.contentEquals(other.dName)) return false
        return true
    }

    override fun hashCode(): Int = this.dName.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<Dirent> =
            object : TypeLayout<Dirent> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): Dirent =
                    Dirent(
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Dirent) {
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr, value.dName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent =
                    Dirent(
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent) {
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset, value.dName)
                }
            }
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
public data class DIR(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public expect fun opendir(name: ConstPtr): MutPtr

public expect fun readdir(dir: MutPtr): MutPtr

public expect fun closedir(dir: MutPtr): CInt

public expect fun rewinddir(dir: MutPtr)
