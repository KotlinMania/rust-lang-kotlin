// port-lint: source hasher.rs
package io.github.kotlinmania.rustlang.hashbrown.hasher

import io.github.kotlinmania.rustlang.core.mem.TypeLayout

/**
 * Default hash builder for the `S` type parameter of
 * [`HashMap`](HashMap) and [`HashSet`](HashSet).
 *
 * This only implements `BuildHasher` when the "default-hasher" crate feature
 * is enabled; otherwise it just serves as a Note, and a custom `S` type
 * must be used to have a fully functional `HashMap` or `HashSet`.
 */
public open class DefaultHashBuilder {
    public open fun clone(): DefaultHashBuilder = DefaultHashBuilder()

    public companion object {
        public fun typeLayout(): TypeLayout<DefaultHashBuilder> =
            object : TypeLayout<DefaultHashBuilder> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): DefaultHashBuilder =
                    DefaultHashBuilder()
                override fun writeNative(addr: Long, value: DefaultHashBuilder) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): DefaultHashBuilder =
                    DefaultHashBuilder()
                override fun writeBytes(buf: ByteArray, offset: Int, value: DefaultHashBuilder) {
                }
            }
    }
}
