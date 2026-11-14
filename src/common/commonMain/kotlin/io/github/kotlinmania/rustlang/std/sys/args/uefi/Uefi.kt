// port-lint: source sys/args/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.args.uefi

import io.github.kotlinmania.refi.protocols.LoadedImage
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.env.currentExe
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.imageHandleProtocol

public fun args(): Args {
    val lazyCurrentExe = { listOf(currentExe().map(Into::into).unwrapOrDefault()).toMutableList() }
    // Each loaded image has an image handle that supports `EFI_LOADED_IMAGE_PROTOCOL`. Thus, this
    // will never fail.
    val protocol = imageHandleProtocol<Protocol>(LoadedImage.PROTOCOL_GUID).unwrap()
    val lpSize = (protocol.asPtr().loadOptionsSize).toInt()
    // Break if we are sure that it cannot be UTF-16
    if (lpSize < sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) || lpSize % sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) != 0) {
        return Args.new(lazyCurrentExe())
    }
    val lpSize1 = lpSize / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)
    val lpCmdLine = constPtrFromRustCast(protocol.asPtr().loadOptions)
    if (!lpCmdLine.isAligned()) {
        return Args.new(lazyCurrentExe())
    }
    val lpCmdLine1 = rawSliceList(lpCmdLine, lpSize1.toInt(), PrimitiveLayouts.U16)
    return Args.new((parseLpCmdLine(lpCmdLine1) ?: lazyCurrentExe()))
}

/**
 * Implements the UEFI command-line argument parsing algorithm.
 *
 * This implementation is based on what is defined in Section 3.4 of
 * [UEFI Shell Specification](https://uefi.org/sites/default/files/resources/UefiShellSpec20.pdf)
 *
 * Returns None in the following cases:
 * - Invalid UTF-16 (unpaired surrogate)
 * - Empty/improper arguments
 */
public fun parseLpCmdLine(codeUnits: List<UShort>): List<OsString>? {
    val QUOTE: Char = '"'
    val SPACE: Char = ' '
    val CARET: Char = '^'
    val NULL: Char = '\u0000'
    val retVal = mutableListOf()
    val codeUnitsIter = Char.decodeUtf16(codeUnits.iter().cloned()).peekable()
    // The executable name at the beginning is special.
    var inQuotes = false
    var cur = ""
    while (true) {
        val matchVal = codeUnitsIter.next()
        if (matchVal == null) break
        val w = matchVal
        val w1 = (w.ok() ?: return null)
        when {
            // break on NULL
            w1 == NULL -> { break }
            // A quote mark always toggles `inQuotes` no matter what because
            // there are no escape characters when parsing the executable name.
            w1 == QUOTE -> inQuotes = !inQuotes
            // If not `inQuotes` then whitespace ends argv[0].
            w1 == SPACE && !inQuotes -> { break }
            // In all other cases the code unit is taken literally.
            else -> cur += w1
        }
    }
    // If exe name is missing, the cli args are invalid
    if (cur.isEmpty()) {
        return null
    }
    retVal.add(OsString.from(cur))
    // Skip whitespace.
    while (codeUnitsIter.nextIfEq(Result.Ok(SPACE)) != null) {}
    // Parse the arguments according to these rules:
    // * All code units are taken literally except space, quote and caret.
    // * When not `inQuotes`, space separate arguments. Consecutive spaces are
    // treated as a single separator.
    // * A space `inQuotes` is taken literally.
    // * A quote toggles `inQuotes` mode unless it's escaped. An escaped quote is taken literally.
    // * A quote can be escaped if preceded by caret.
    // * A caret can be escaped if preceded by caret.
    var cur1 = ""
    var inQuotes1 = false
    while (true) {
        val matchVal1 = codeUnitsIter.next()
        if (matchVal1 == null) break
        val w2 = matchVal1
        val w3 = (w2.ok() ?: return null)
        when {
            // break on NULL
            w3 == NULL -> { break }
            // If not `inQuotes`, a space or tab ends the argument.
            w3 == SPACE && !inQuotes1 -> {
                retVal.add(OsString.from(cur1))
                cur1 = ""
                // Skip whitespace.
                while (codeUnitsIter.nextIfEq(Result.Ok(SPACE)) != null) {}
            }
            // Caret can escape quotes or carets
            w3 == CARET && inQuotes1 -> {
                when (val matchVal2 = codeUnitsIter.next()) {
                    null -> {}
                    else -> {
                        val x = matchVal2
                        cur1 += (x.ok() ?: return null)
                    }
                }
            }
            // If quote then flip `inQuotes`
            w3 == QUOTE -> inQuotes1 = !inQuotes1
            // Everything else is always taken literally.
            else -> cur1 += w3
        }
    }
    // Push the final argument, if any.
    if (!cur1.isEmpty() || inQuotes1) {
        retVal.add(OsString.from(cur1))
    }
    return retVal
}
