// port-lint: source str/count.rs
package io.github.kotlinmania.rustlang.core.str.count

import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.intrinsics.unlikely
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.slice.chunks
import io.github.kotlinmania.rustlang.core.str.validations.utf8IsContByte

// Code for efficiently counting the number of `char`s in a UTF-8 encoded
// string.
//
// Broadly, UTF-8 encodes `char`s as a "leading" byte which begins the `char`,
// followed by some number (possibly 0) of continuation bytes.
//
// The leading byte can have a number of bit-patterns (with the specific
// pattern indicating how many continuation bytes follow), but the continuation
// bytes are always in the format `0b10XxXxxx` (where the `X`s can take any
// value). That is, the most significant bit is set, and the second most
// significant bit is unset.
//
// To count the number of characters, we can just count the number of bytes in
// the string which are not continuation bytes, which can be done many bytes at
// a time fairly easily.
//
// Note: Because the term "leading byte" can sometimes be ambiguous (for
// example, it could also refer to the first byte of a slice), we'll often use
// the term "non-continuation byte" to refer to these bytes in the code.

public val UNROLL_INNER: Int = 4

public val USIZE_SIZE: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

internal fun countChars(s: String): Int =
    if (false || s.length < USIZE_SIZE * UNROLL_INNER) {
        // Avoid entering the optimized implementation for strings where the
        // difference is not likely to matter, or where it might even be slower.
        // That said, a ton of thought was not spent on the particular threshold
        // here, beyond "this value seems to make sense".
        charCountGeneralCase(s.encodeToByteArray().map { it.toUByte() })
    } else {
        doCountChars(s)
    }

public fun doCountChars(s: String): Int {
    // For correctness, `CHUNK_SIZE` must be:
    //
    // - Less than or equal to 255, otherwise we'll overflow bytes in `counts`.
    // - A multiple of `UNROLL_INNER`, otherwise our `break` inside the
    // `body.chunks(CHUNK_SIZE)` loop is incorrect.
    //
    // For performance, `CHUNK_SIZE` should be:
    // - Relatively cheap to `/` against (so some simple sum of powers of two).
    // - Large enough to avoid paying for the cost of the `sumBytesInUsize`
    // too often.
    val CHUNK_SIZE: Int = 192
    // Check the properties of `CHUNK_SIZE` and `UNROLL_INNER` that are required
    // for correctness.
    // SAFETY: transmuting `[UByte]` to `[Int]` is safe except for size
    // differences which are handled by `alignTo`.
    val (head, body, tail) = s.encodeToByteArray().map { it.toUByte() }.alignTo<Int>()
    // This should be quite rare, and basically exists to handle the degenerate
    // cases where alignTo fails (as well as miri under symbolic alignment
    // mode).
    //
    // The `unlikely` helps discourage LLVM from inlining the body, which is
    // nice, as we would rather not mark the `charCountGeneralCase` function
    // as cold.
    if (unlikely(body.isEmpty() || head.len() > USIZE_SIZE || tail.len() > USIZE_SIZE)) {
        return charCountGeneralCase(s.encodeToByteArray().map { it.toUByte() })
    }
    var total = charCountGeneralCase(head) + charCountGeneralCase(tail)
    // Split `body` into `CHUNK_SIZE` chunks to reduce the frequency with which
    // we call `sumBytesInUsize`.
    for (chunk in body.chunks(CHUNK_SIZE)) {
        // We accumulate intermediate sums in `counts`, where each byte contains
        // a subset of the sum of this chunk, like a `[UByte; sizeOf<Int>()]`.
        var counts = 0
        val (unrolledChunks, remainder) = chunk.asChunks()
        for (unrolled in unrolledChunks) {
            for (word in unrolled) {
                // Because `CHUNK_SIZE` is < 256, this addition can't cause the
                // count in any of the bytes to overflow into a subsequent byte.
                counts += containsNonContinuationByte(word)
            }
        }
        // Sum the values in `counts` (which, again, is conceptually a `[u8;
        // sizeOf::<Int>()]`), and accumulate the result into`total`.
        total += sumBytesInUsize(counts)
        // If there's any data in `remainder`, then handle it. This will only
        // happen for the last `chunk` in `body.chunks()` (because `CHUNK_SIZE`
        // is divisible by `UNROLL_INNER`), so we explicitly break at the end
        // (which seems to help LLVM out).
        if (!remainder.isEmpty()) {
            // Accumulate all the data in the remainder.
            var counts1 = 0
            for (word1 in remainder) {
                counts1 += containsNonContinuationByte(word1)
            }
            total += sumBytesInUsize(counts1)
            break
        }
    }
    return total
}

// Checks each byte of `w` to see if it contains the first byte in a UTF-8
// sequence. Bytes in `w` which are continuation bytes are left as `0x00` (e.g.
// false), and bytes which are non-continuation bytes are left as `0x01` (e.g.
// true)
public fun containsNonContinuationByte(w: Int): Int {
    val LSB: Int = Int.repeatU8(0x01u)
    return ((!w shr 7) or (w shr 6)) and LSB
}

// Morally equivalent to `values.toNeBytes().intoIter().sum<Int>()`, but
// more efficient.
public fun sumBytesInUsize(values: Int): Int {
    val LSB_SHORTS: Int = Int.repeatU16(0x0001u)
    val SKIP_BYTES: Int = Int.repeatU16(0x00ffu)
    val pairSum: Int = (values and SKIP_BYTES) + ((values shr 8) and SKIP_BYTES)
    return (pairSum * (LSB_SHORTS.toInt())) shr ((USIZE_SIZE - 2) * 8)
}

// This is the most direct implementation of the concept of "count the number of
// bytes in the string which are not continuation bytes", and is used for the
// head and tail of the input string (the first and last item in the tuple
// returned by `slice.alignTo`).
public fun charCountGeneralCase(s: List<UByte>): Int = s.filter({ byte -> !utf8IsContByte(byte) }).count()
