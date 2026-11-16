package io.github.kotlinmania.rustlang.backtrace.symbolize

import io.github.kotlinmania.rustlang.backtrace.backtrace.Frame
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Resolve an address to a symbol, passing the symbol to the specified
 * closure.
 *
 * This function will look up the given address in areas such as the local
 * symbol table, dynamic symbol table, or DWARF debug info (depending on the
 * activated implementation) to find symbols to yield.
 *
 * The closure may not be called if resolution could not be performed, and it
 * also may be called more than once in the case of inlined functions.
 *
 * Symbols yielded represent the execution at the specified `addr`, returning
 * file/line pairs for that address (if available).
 *
 * Note that if you have a `Frame` then it's recommended to use the
 * `resolveFrame` function instead of this one.
 *
 * # Required features
 *
 * This function requires the `std` feature of the `backtrace` crate to be
 * enabled, and the `std` feature is enabled by default.
 *
 * # Panics
 *
 * This function strives to never panic, but if the `cb` provided panics then
 * some platforms will force a double panic to abort the process. Some
 * platforms use a C library which internally uses callbacks which cannot be
 * unwound through, so panicking from `cb` may trigger a process abort.
 *
 * # Example
 *
 * ```kotlin
 * import std.prelude.v1.*
 *
 * fun main() {
 *     trace({ frame ->
 *         val ip = frame.ip()
 *         resolve(ip, { symbol ->})
 *         false
 *     })
 * }
 * ```
 */
public fun resolve(addr: MutPtr, cb: (Symbol) -> Unit) {
    resolveUnsynchronized(addr, cb)
}

/**
 * Resolve a previously captured frame to a symbol, passing the symbol to the
 * specified closure.
 *
 * This function performs the same function as `resolve` except that it takes a
 * `Frame` as an argument instead of an address. This can allow some platform
 * implementations of backtracing to provide more accurate symbol information
 * or information about inline frames for example. It's recommended to use this
 * if you can.
 *
 * # Required features
 *
 * This function requires the `std` feature of the `backtrace` crate to be
 * enabled, and the `std` feature is enabled by default.
 *
 * # Panics
 *
 * This function strives to never panic, but if the `cb` provided panics then
 * some platforms will force a double panic to abort the process. Some
 * platforms use a C library which internally uses callbacks which cannot be
 * unwound through, so panicking from `cb` may trigger a process abort.
 *
 * # Example
 *
 * ```kotlin
 * import std.prelude.v1.*
 *
 * fun main() {
 *     trace({ frame ->
 *         resolveFrame(frame, { symbol ->})
 *         false
 *     })
 * }
 * ```
 */
public fun resolveFrame(frame: Frame, cb: (Symbol) -> Unit) {
    resolveFrameUnsynchronized(frame, cb)
}

// IP values from stack frames are typically (always?) the instruction
// *after* the call that's the actual stack trace. Symbolizing this on
// causes the filename/line number to be one ahead and perhaps into
// the void if it's near the end of the function.
//
// This appears to basically always be the case on all platforms, so we always
// subtract one from a resolved ip to resolve it to the previous call
// instruction instead of the instruction being returned to.
//
// Ideally we would not do this. Ideally we would require callers of the
// `resolve` APIs here to manually do the -1 and account that they want location
// information for the *previous* instruction, not the current. Ideally we'd
// also expose on `Frame` if we are indeed the address of the next instruction
// or the current.
//
// For now though this is a pretty niche concern so we just internally always
// subtract one. Consumers should keep working and getting pretty good results,
// so we should be good enough.
public fun adjustIp(a: MutPtr): MutPtr =
    if (((a).rawAddress == 0L)) {
        a
    } else {
        MutPtr((a.rawAddress.toInt() - 1).toLong())
    }

/**
 * Same as `resolve`, only unchecked as it's unsynchronized.
 *
 * This function does not have synchronization guarantees but is available when
 * the `std` feature of this crate isn't compiled in. See the `resolve`
 * function for more documentation and examples.
 *
 * # Panics
 *
 * See information on `resolve` for caveats on `cb` panicking.
 */
public fun resolveUnsynchronized(addr: MutPtr, cb: (Symbol) -> Unit) {
    io.github.kotlinmania.rustlang.backtrace.symbolize.noop.resolve(ResolveWhat.Address(addr), cb)
}

/**
 * Same as `resolveFrame`, only unchecked as it's unsynchronized.
 *
 * This function does not have synchronization guarantees but is available
 * when the `std` feature of this crate isn't compiled in. See the
 * `resolveFrame` function for more documentation and examples.
 *
 * # Panics
 *
 * See information on `resolveFrame` for caveats on `cb` panicking.
 */
public fun resolveFrameUnsynchronized(frame: Frame, cb: (Symbol) -> Unit) {
    io.github.kotlinmania.rustlang.backtrace.symbolize.noop.resolve(ResolveWhat.Frame(frame), cb)
}

public fun formatSymbolName(fmt: FnCallback, bytes: List<UByte>, f: StringBuilder): Result<Unit, Error> {
    var bytes1 = bytes
    while (bytes1.size > 0) {
        run {
            val matchVal = String.fromUtf8(bytes1)
            when (matchVal) { is Result.Ok -> {
                val name = matchVal.value
                when (val tryResult = fmt(name, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
                break
            }; is Result.Err -> {
                val err = matchVal.error
                when (val tryResult = fmt("�", f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
                when (val matchVal1 = err.errorLen()) {
                    null -> { break }
                    else -> {
                        val len = matchVal1
                        bytes1 = ((bytes1).subList(err.validUpTo() + len, (bytes1).size))
                    }
                }
            } }
        }
    }
    return Result.Ok<Unit, Error>(Unit)
}

/**
 * Attempt to reclaim that cached memory used to symbolicate addresses.
 *
 * This method will attempt to release any global data structures that have
 * otherwise been cached globally or in the thread which typically represent
 * parsed DWARF information or similar.
 *
 * # Caveats
 *
 * While this function is always available it doesn't actually do anything on
 * most implementations. Libraries like dbghelp or libbacktrace do not provide
 * facilities to deallocate state and manage the allocated memory. For now the
 * `std` feature of this crate is the only feature where this
 * function has any effect.
 */
public fun clearSymbolCache() {
    run {
        io.github.kotlinmania.rustlang.backtrace.symbolize.noop.clearSymbolCache()
    }
}
