// port-lint: source collections/btree/set_val.rs
package io.github.kotlinmania.rustlang.alloc.collections.btree.setval

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

/**
 * Zero-Sized Type (ZST) for internal `BTreeSet` values.
 * Used instead of `()` to differentiate between:
 * * `BTreeMap<T, ()>` (possible user-defined map)
 * * `BTreeMap<T, SetValZST>` (internal set representation)
 */
public open class SetValZST : IsSetVal {
    public open fun clone(): SetValZST = SetValZST()

    public companion object {
        public fun typeLayout(): TypeLayout<SetValZST> =
            object : TypeLayout<SetValZST> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): SetValZST =
                    SetValZST()
                override fun writeNative(addr: Long, value: SetValZST) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): SetValZST =
                    SetValZST()
                override fun writeBytes(buf: ByteArray, offset: Int, value: SetValZST) {
                }
            }

        // Specialization
        public fun isSetVal(): Boolean = true
    }
}

/**
 * A trait to differentiate between `BTreeMap` and `BTreeSet` values.
 * Returns `true` only for type `SetValZST`, `false` for all other types (blanket implementation).
 * `TypeId` requires a `static` scope, use of this trait avoids that restriction.
 */
public interface IsSetVal {
    public fun isSetVal(): Boolean

    public companion object {
        public fun isSetVal(): Boolean = SetValZST.isSetVal()
    }
}

// Blanket implementation
public fun <V> isSetValV(): Boolean = false
