// port-lint: source asserting.rs
package io.github.kotlinmania.rustlang.core.asserting

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.result.Result

// ***** TryCapture - Generic *****

/**
 * Marker used by [Capture]
 */
public open class TryCaptureWithoutDebug {
    public companion object
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

public fun <E> Capture<E, TryCaptureWithoutDebug>.fmt(f: Formatter): Result<Unit, Error> = run { f.append("N/A"); Result.Ok<Unit, Error>(Unit) }

// ***** TryCapture - Printable *****

/**
 * Marker used by [Capture]
 */
public open class TryCaptureWithDebug {
    public companion object
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

public fun <E> Wrapper<E>.tryCaptureETryCaptureWithDebug(to: Capture<E, TryCaptureWithDebug>) where E : Printable {
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
) {
    public companion object
}

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

    public companion object
}

/**
 * Tells which elements can be copied and displayed
 */
public interface Printable
