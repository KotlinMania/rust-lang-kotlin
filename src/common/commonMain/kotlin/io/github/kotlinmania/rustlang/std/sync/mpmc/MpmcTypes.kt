package io.github.kotlinmania.rustlang.std.sync.mpmc

import io.github.kotlinmania.rustlang.sync.mpmc.array.Channel

/**
 * Sender flavors.
 */
public sealed class SenderFlavor<T> {
    /**
     * Bounded channel based on a preallocated array.
     */
    public class Array<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>) : SenderFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value): Array<T> = Array<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Array<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>,
            ): Array<T> = Array<T>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Unbounded channel implemented as a linked list.
     */
    public class List<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>) : SenderFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value): List<T> = List<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is List<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>,
            ): List<T> = List<T>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Zero-capacity channel.
     */
    public class Zero<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>) : SenderFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>> = this.value): Zero<T> = Zero<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Zero<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Sender<Channel<T>>,
            ): Zero<T> = Zero<T>(
                valueCtorArg = value,
            )
        }
    }
}

/**
 * Receiver flavors.
 */
public sealed class ReceiverFlavor<T> {
    /**
     * Bounded channel based on a preallocated array.
     */
    public class Array<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>) : ReceiverFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value): Array<T> = Array<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Array<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>,
            ): Array<T> = Array<T>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Unbounded channel implemented as a linked list.
     */
    public class List<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>) : ReceiverFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value): List<T> = List<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is List<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>,
            ): List<T> = List<T>(
                valueCtorArg = value,
            )
        }
    }
    /**
     * Zero-capacity channel.
     */
    public class Zero<T> internal constructor(valueCtorArg: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>) : ReceiverFlavor<T>() {
        public var value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = valueCtorArg
            internal set

        public operator fun component1(): io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value

        public fun copy(value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>> = this.value): Zero<T> = Zero<T>(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Zero<*>) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun <T> invoke(
                value: io.github.kotlinmania.rustlang.sync.mpmc.Receiver<Channel<T>>,
            ): Zero<T> = Zero<T>(
                valueCtorArg = value,
            )
        }
    }
}
