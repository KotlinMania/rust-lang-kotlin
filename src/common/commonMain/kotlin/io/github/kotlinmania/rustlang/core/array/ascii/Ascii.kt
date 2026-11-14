// port-lint: source array/ascii.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.array.ascii

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

/**
 * Converts this array of bytes into an array of ASCII characters,
 * or returns `None` if any of the characters is non-ASCII.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(asciiChar)
 * public fun doctestEntry() {
 *     val HEX_DIGITS: List<Char> = "0123456789abcdef".encodeToByteArray().asAscii().unwrap()
 *     check(HEX_DIGITS[1] == "1")
 *     check(HEX_DIGITS[10] == "a")
 * }
 * ```
 */
public fun UByteArray.asAscii(n: Int): List<io.github.kotlinmania.rustlang.core.ascii.Char>? =
    if (this.isAscii()) {
        // SAFETY: Just checked that it's ASCII
        this.asAsciiUnchecked()
    } else {
        null
    }

/**
 * Converts this array of bytes into an array of ASCII characters,
 * without checking whether they're valid.
 *
 * # Safety
 *
 * Every byte in the array must be in `0 ..= 127`, or else this is UB.
 */
public fun UByteArray.asAsciiUnchecked(n: Int): List<io.github.kotlinmania.rustlang.core.ascii.Char> {
    val bytePtr: ConstPtr = this
    val asciiPtr = ConstPtr(bytePtr.rawAddress, bytePtr.rawMeta)
    // SAFETY: The caller promised all the bytes are ASCII
    return (ObjectSlotLayout<List<io.github.kotlinmania.rustlang.core.ascii.Char>>()).readNative((asciiPtr).rawAddress)
}
