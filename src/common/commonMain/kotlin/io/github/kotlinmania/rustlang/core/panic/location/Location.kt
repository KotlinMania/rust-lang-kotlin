// port-lint: source panic/location.rs
package io.github.kotlinmania.rustlang.core.panic.location

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.intrinsics.callerLocation
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.sizeOfValRaw
import io.github.kotlinmania.rustlang.core.num.uncheckedAdd
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * A struct containing information about the location of a panic.
 *
 * This structure is created by [PanicHookInfo.location] and [PanicInfo.location].
 *
 * [PanicHookInfo.location]: ../../std/panic/struct.PanicHookInfo.html#method.location
 *
 * # Examples
 *
 * ```kotlin
 * import std.panic
 *
 * panic.setHook({ panicInfo ->
 *     when (val matchVal = panicInfo.location()) {
 *         null -> {
 *             println("panicked at unknown location")
 *         }
 *         else -> {
 *             val location = matchVal
 *             println("panic occurred in file '${location.file()}' at line ${location.line()}")
 *         }
 *     }
 * })
 * error("Normal panic")
 * ```
 *
 * # Comparisons
 *
 * Comparisons for equality and ordering are made in file, line, then column priority.
 * Files are compared as strings, not `Path`, which could be unexpected.
 * See [Location.file]s documentation for more discussion.
 */
public open class Location internal constructor(
    filenameCtorArg: NonNull,
    public var line: UInt,
    public var col: UInt,
) {
    // A raw pointer is used rather than a reference because the pointer is valid for one more byte
    // than the length stored in this pointer; the additional byte is the NUL-terminator used by
    // `Location.fileAsCStr`.
    public var filename: NonNull = filenameCtorArg
        internal set

    public open fun clone(): Location = Location(filename, line, col)

    public open fun eq(other: Location): Boolean {
        // Compare col / line first as they're cheaper to compare and more likely to differ,
        // while not impacting the result.
        return this.col == other.col && this.line == other.line && this.file() == other.file()
    }

    public open fun cmp(other: Location): Ordering = this.file().cmp(other.file()).thenWith({ this.line.cmp(other.line) }).thenWith({ this.col.cmp(other.col) })

    public open fun partialCmp(other: Location): Ordering? = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.file()).hashCode()
        state = 31 * state + (this.line).hashCode()
        state = 31 * state + (this.col).hashCode()
        return state
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Location").field("file", this.file()).field("line", this.line).field("column", this.col).finish(); Result.Ok<Unit, Unit>(Unit) }

    /**
     * Returns the name of the source file from which the panic originated.
     *
     * # `String`, not `Path`
     *
     * The returned name refers to a source path on the compiling system, but it isn't valid to
     * represent this directly as a `Path`. The compiled code may run on a different system with
     * a different `Path` implementation than the system providing the contents and this library
     * does not currently have a different "host path" type.
     *
     * The most surprising behavior occurs when "the same" file is reachable via multiple paths in
     * the module system (usually using the `@path= "..."` attribute or similar), which can
     * cause what appears to be identical code to return differing values from this function.
     *
     * # Cross-compilation
     *
     * This value is not suitable for passing to `Path.new` or similar constructors when the host
     * platform and target platform differ.
     *
     * # Examples
     *
     * ```kotlin
     * import std.panic
     *
     * panic.setHook({ panicInfo ->
     *     when (val matchVal = panicInfo.location()) {
     *         null -> {
     *             println("panicked at unknown location")
     *         }
     *         else -> {
     *             val location = matchVal
     *             println("panic occurred in file '${location.file()}'")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun file(): String {
        // SAFETY: The filename is valid.
        return this.filename.asRef()
    }

    /**
     * Returns the name of the source file as a nul-terminated `CStr`.
     *
     * This is useful for interop with APIs that expect C/C++ `__FILE__` or
     * `std.sourceLocation.fileName`, both of which return a nul-terminated `const char *`.
     */
    public open fun fileAsCStr(): CStr {
        val filename = this.filename.asPtr()
        // SAFETY: The filename is valid for `filenameLen + 1` bytes, so this addition can't
        // overflow.
        val cstrLen = sizeOfValRaw(filename).uncheckedAdd(1)
        // SAFETY: The filename is valid for `filenameLen + 1` bytes.
        val slice = linearSliceView(filename.cast(), cstrLen.toInt(), PrimitiveLayouts.U8)
        // SAFETY: The filename is guaranteed to have a trailing nul byte and no interior nul bytes.
        return CStr.fromBytesWithNulUnchecked(slice)
    }

    /**
     * Returns the line number from which the panic originated.
     *
     * # Examples
     *
     * ```kotlin
     * import std.panic
     *
     * panic.setHook({ panicInfo ->
     *     when (val matchVal = panicInfo.location()) {
     *         null -> {
     *             println("panicked at unknown location")
     *         }
     *         else -> {
     *             val location = matchVal
     *             println("panic occurred at line ${location.line()}")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun line(): UInt = this.line

    /**
     * Returns the column from which the panic originated.
     *
     * # Examples
     *
     * ```kotlin
     * import std.panic
     *
     * panic.setHook({ panicInfo ->
     *     when (val matchVal = panicInfo.location()) {
     *         null -> {
     *             println("panicked at unknown location")
     *         }
     *         else -> {
     *             val location = matchVal
     *             println("panic occurred at column ${location.column()}")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun column(): UInt = this.col

    public open fun fmtDisplay(formatter: StringBuilder): Result<Unit, Unit> = run { formatter.append("${this.file()}:${this.line}:${this.col}"); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            filename: NonNull,
            line: UInt,
            col: UInt,
        ): Location = Location(
            filenameCtorArg = filename,
            line = line,
            col = col,
        )

        /**
         * Returns the source location of the caller of this function. If that function's caller is
         * annotated then its call location will be returned, and so on up the stack to the first call
         * within a non-tracked function body.
         *
         * # Examples
         *
         * ```kotlin
         * import std.panic.Location
         *
         * / **
         *  * ```kotlin
         *  * | 1 | 11 | 21 | 31 | 41 + - | - - - - - - - - - | - - - - - - - - - | - - - - - - - - - | - - - - - - - - - | - - - - - - - - 15 | @trackCaller 16 | fun newLocation() -> Location<> { 17 | Location.caller() | - - - - - - - - - - - - - - - - - - | the value of this expression depends on the caller, | | since the function is marked @trackCaller 18 | }
         *  * ```
         *  * /
         * fun newLocation(): Location {
         *     return Location.caller()
         * }
         * / **
         *  * ```kotlin
         *  * | 1 | 5 | 11 | 21 | 31 | 41 | 51 + - | - - - | - - - - - | - - - - - - - - - | - - - - - - - - - | - - - - - - - - - | - - - - - - - - - | - - - 29 | fun constantLocation() -> Location<> { 30 | newLocation() | ^ any invocation of constantLocation() points here, | no matter the location it is called from 31 | }
         *  * ```
         *  * /
         * fun constantLocation(): Location {
         *     return newLocation()
         * }
         * fun main() {
         *     // |1  |5    |11       |21       |31       |41       |51
         *     // +-|---|-----|---------|---------|---------|---------|---
         *     // 29 | fn constantLocation() -> &static Location<static> (
         *     // 30 |     newLocation()
         *     // |     ^ `val constant` points here
         *     // 31 | )
         *     val constant = constantLocation()
         *     check(constant.file() == "")
         *     check(Pair(constant.line(), constant.column()) == Pair(30, 5))
         *     val constant2 = constantLocation()
         *     check(Triple(constant.file(), constant.line(), constant.column()) == Triple(constant2.file(), constant2.line(), constant2.column()))
         *     // |1        |11  |16  |21       |31
         *     // +-|---------|----|----|---------|------
         *     // 55 |     let here = newLocation();
         *     // |                ^ `val here` points here, as `newLocation()` is the callsite
         *     // 56 |     assertEq(here.file(), file());
         *     val here = newLocation()
         *     check(here.file() == "")
         *     check(Pair(here.line(), here.column()) == Pair(55, 16))
         *     // |1        |11       |21       ||32      |41       |51
         *     // +-|---------|---------|---------||--------|---------|------
         *     // 64 |     let yetAnotherLocation = newLocation();
         *     // |                                ^ `val yetAnotherLocation` points here
         *     // 65 |     assertEq(here.file(), yetAnotherLocation.file());
         *     val yetAnotherLocation = newLocation()
         *     check(here.file() == yetAnotherLocation.file())
         *     check(Pair(here.line(), here.column()) != Pair(yetAnotherLocation.line(), yetAnotherLocation.column()))
         * }
         * ```
         */
        public fun caller(): Location = callerLocation()
    }
}
