// port-lint: source scopeguard.rs
package io.github.kotlinmania.rustlang.hashbrown.scopeguard

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.read

public open class ScopeGuard<T, F>(
    public var dropfn: F,
    public var value: T,
) {
    public open fun deref(): T = this.value

    public open fun derefMut(): T = this.value

    public open fun drop() {
        (this.dropfn)(this.value)
    }

    public companion object {
        public fun <T, F> typeLayout(typeLayoutF: TypeLayout<F>, typeLayoutT: TypeLayout<T>): TypeLayout<ScopeGuard<T, F>> =
            object : TypeLayout<ScopeGuard<T, F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutF.sizeBytes, typeLayoutT.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutT.sizeBytes), alignBytes)
                override fun readNative(addr: Long): ScopeGuard<T, F> =
                    ScopeGuard(
                        (typeLayoutF).readNative(addr),
                        (typeLayoutT).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: ScopeGuard<T, F>) {
                    (typeLayoutF).writeNative(addr, value.dropfn)
                    (typeLayoutT).writeNative(addr + off1, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ScopeGuard<T, F> =
                    ScopeGuard(
                        (typeLayoutF).readBytes(buf, offset),
                        (typeLayoutT).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ScopeGuard<T, F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.dropfn)
                    (typeLayoutT).writeBytes(buf, offset + off1.toInt(), value.value)
                }
            }

        public fun <T> intoInner(guard: ScopeGuard<T, F>): T {
            // Cannot move out of Drop-implementing types, so
            // ptr.read the value out of a ManuallyDrop<Self>
            // Don't use mem.forget as that might invalidate value
            val guard1 = guard
            return run {
                val value = read(addrOf(guard1.value))
                // read the closure so that it is dropped
                read(addrOf(guard1.dropfn))
                value
            }
        }
    }
}

public fun <T> guard(value: T, dropfn: (T) -> Unit): ScopeGuard<T, (T) -> Unit> = ScopeGuard(dropfn = dropfn, value = value)
