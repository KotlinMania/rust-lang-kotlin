package io.github.kotlinmania.rustlang.backtrace.symbolize

import io.github.kotlinmania.rustlang.backtrace.backtrace.Frame
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public sealed class ResolveWhat {
    public class Address internal constructor(valueCtorArg: MutPtr) : ResolveWhat() {
        public var value: MutPtr = valueCtorArg
            internal set

        public operator fun component1(): MutPtr = this.value

        public fun copy(value: MutPtr = this.value): Address = Address(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Address) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: MutPtr,
            ): Address = Address(
                valueCtorArg = value,
            )
        }
    }
    public data class Frame(public var value: Frame) : ResolveWhat()

    public open fun addressOrIp(): MutPtr =
        when (this) {
            is ResolveWhat.Address -> {
                val (a) = this as ResolveWhat.Address
                adjustIp((PrimitiveLayouts.U8).readNative((a).rawAddress))
            }
            is ResolveWhat.Frame -> {
                val (f) = this as ResolveWhat.Frame
                adjustIp(f.ip())
            }
        }
}
