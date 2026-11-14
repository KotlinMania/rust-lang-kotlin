// port-lint: source asserting.rs
package io.github.kotlinmania.rustlang.core.asserting

import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.f128.consts.E
import io.github.kotlinmania.rustlang.core.result.Result

// ***** TryCapture - Generic *****

/**
 * Marker used by [Capture]
 */
public open class TryCaptureWithoutDebug {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<TryCaptureWithoutDebug> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<TryCaptureWithoutDebug> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): TryCaptureWithoutDebug =
                    TryCaptureWithoutDebug()
                override fun writeNative(addr: Long, value: TryCaptureWithoutDebug) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): TryCaptureWithoutDebug =
                    TryCaptureWithoutDebug()
                override fun writeBytes(buf: ByteArray, offset: Int, value: TryCaptureWithoutDebug) {
                }
            }
    }
}

/**
 * Catches an arbitrary `E` and modifies `to` accordingly
 */
public interface TryCaptureGeneric<E, M> {
    /**
     * Similar to [TryCapturePrintable] but generic to any `E`.
     */
    public fun tryCapture(to: Capture<E, M>)
}

public fun <E> Wrapper<E>.tryCapture(arg0: Capture<E, TryCaptureWithoutDebug>) {}

public fun <E> Capture<E, TryCaptureWithoutDebug>.fmt(f: Formatter): Result<Unit, Unit> = run { f.append("N/A"); Result.Ok(Unit) }

// ***** TryCapture - Printable *****

/**
 * Marker used by [Capture]
 */
public open class TryCaptureWithDebug {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<TryCaptureWithDebug> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<TryCaptureWithDebug> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): TryCaptureWithDebug =
                    TryCaptureWithDebug()
                override fun writeNative(addr: Long, value: TryCaptureWithDebug) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): TryCaptureWithDebug =
                    TryCaptureWithDebug()
                override fun writeBytes(buf: ByteArray, offset: Int, value: TryCaptureWithDebug) {
                }
            }
    }
}

/**
 * Catches an arbitrary `E: Printable` and modifies `to` accordingly
 */
public interface TryCapturePrintable<E, M> {
    /**
     * Similar as [TryCaptureGeneric] but specialized to any `E: Printable`.
     */
    public fun tryCapture(to: Capture<E, M>)
}

public fun <E> Wrapper<E>.tryCaptureETryCaptureWithDebug(to: Capture<E, TryCaptureWithDebug>) where E : Printable<E> {
    to.elem = this.value
}

// ***** Others *****
// spellchecker:off
// spellchecker:on

/**
 * All possible captured `assert` elements
 *
 * # Types
 *
 * * `E`: **E**lement that is going to be displayed.
 * * `M`: **M**arker used to differentiate [Capture]s in regards to [Debug].
 */
public open class Capture<E, M>(
    // If None, then `E` does not implements [Printable] or `E` wasn't evaluated (`assert(...)`
    // short-circuited).
    //
    // If Some, then `E` implements [Printable] and was evaluated.
    public var elem: E?,
    public var phantom: Unit,
)

public fun <M, T> Capture.Companion.new(): Capture<M, T> = Capture(elem = null, phantom = Unit)

/**
 * Necessary for the implementations of `TryCapture *`
 */
public open class Wrapper<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: io.github.kotlinmania.rustlang.core.mem.TypeLayout<T>): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Wrapper<T>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Wrapper<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Wrapper<T> =
                    Wrapper(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Wrapper<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Wrapper<T> =
                    Wrapper(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Wrapper<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Tells which elements can be copied and displayed
 */
public interface Printable<T>
