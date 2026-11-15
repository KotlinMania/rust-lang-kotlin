// port-lint: source hasher.rs
package io.github.kotlinmania.rustlang.hashbrown.hasher

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.hash.BuildHasher

/**
 * Default hash builder for the `S` type parameter of
 * [`HashMap`](HashMap) and [`HashSet`](HashSet).
 *
 * This only implements `BuildHasher` when the "default-hasher" crate feature
 * is enabled; otherwise it just serves as a Note, and a custom `S` type
 * must be used to have a fully functional `HashMap` or `HashSet`.
 */
public open class DefaultHashBuilder(
    public var inner: RandomState,
) : BuildHasher {
    public open fun clone(): DefaultHashBuilder = DefaultHashBuilder(inner)

    public open fun buildHasher(): DefaultHasher = DefaultHasher(inner = this.inner.buildHasher())
}

/**
 * Default hasher for [`HashMap`](HashMap) and [`HashSet`](HashSet).
 */
public open class DefaultHasher(
    public var inner: Hasher,
) {
    public open fun clone(): DefaultHasher = DefaultHasher(inner)

    public open fun write(arg: List<UByte>) {
        this.inner.write(arg)
    }

    public open fun writeU8(arg: UByte) {
        this.inner.writeU8(arg)
    }

    public open fun writeU16(arg: UShort) {
        this.inner.writeU16(arg)
    }

    public open fun writeU32(arg: UInt) {
        this.inner.writeU32(arg)
    }

    public open fun writeU64(arg: ULong) {
        this.inner.writeU64(arg)
    }

    public open fun writeU128(arg: U128) {
        this.inner.writeU128(arg)
    }

    public open fun writeUsize(arg: Int) {
        this.inner.writeUsize(arg)
    }

    public open fun writeI8(arg: Byte) {
        this.inner.writeI8(arg)
    }

    public open fun writeI16(arg: Short) {
        this.inner.writeI16(arg)
    }

    public open fun writeI32(arg: Int) {
        this.inner.writeI32(arg)
    }

    public open fun writeI64(arg: Long) {
        this.inner.writeI64(arg)
    }

    public open fun writeI128(arg: I128) {
        this.inner.writeI128(arg)
    }

    public open fun writeIsize(arg: Int) {
        this.inner.writeIsize(arg)
    }

    // feature(hasherPrefixfreeExtras)
    public open fun writeLengthPrefix(arg: Int) {
        this.inner.writeLengthPrefix(arg)
    }

    public open fun writeStr(arg: String) {
        this.inner.append(arg)
    }

    public open fun finish(): ULong = this.inner.finish()
}
