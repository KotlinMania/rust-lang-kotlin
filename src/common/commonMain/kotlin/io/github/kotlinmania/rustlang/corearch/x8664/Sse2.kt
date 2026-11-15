// port-lint: source x86_64/sse2.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.loongarch64.lsx.M128d
import io.github.kotlinmania.rustlang.loongarch64.lsx.M128i
import io.github.kotlinmania.rustlang.x86.mmSetEpi64x

// `x8664`s Streaming SIMD Extensions 2 (SSE2)

public expect fun cvtsd2si64(a: M128d): Long

public expect fun cvttsd2si64(a: M128d): Long

/**
 * Converts the lower double-precision (64-bit) floating-point element in a to
 * a 64-bit integer.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_si64)
 */
public fun mmCvtsdSi64(a: M128d): Long = cvtsd2si64(a)

/**
 * Alias for `_mm_cvtsd_si64`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsd_si64x)
 */
public fun mmCvtsdSi64x(a: M128d): Long = mmCvtsdSi64(a)

/**
 * Converts the lower double-precision (64-bit) floating-point element in `a`
 * to a 64-bit integer with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttsd_si64)
 */
public fun mmCvttsdSi64(a: M128d): Long = cvttsd2si64(a)

/**
 * Alias for `_mm_cvttsd_si64`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttsd_si64x)
 */
public fun mmCvttsdSi64x(a: M128d): Long = mmCvttsdSi64(a)

/**
 * Stores a 64-bit integer value in an 8-byte aligned memory location.
 * To minimize caching, the data is flagged as non-temporal (unlikely to be
 * used again soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_si64)
 *
 * # Safety of non-temporal stores
 *
 * After using this intrinsic, but before any other access to the memory that this intrinsic
 * mutates, a call to [mmSfence] must be performed by the thread that used the intrinsic. In
 * particular, functions that call this intrinsic should generally call `_mm_sfence` before they
 * return.
 *
 * See [mmSfence] for details.
 */
public fun mmStreamSi64(memAddr: MutPtr, a: Long) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

/**
 * Returns a vector whose lowest element is `a` and all higher elements are
 * `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi64_si128)
 */
public fun mmCvtsi64Si128(a: Long): M128i = mmSetEpi64x(0L, a)

/**
 * Returns a vector whose lowest element is `a` and all higher elements are
 * `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi64x_si128)
 */
public fun mmCvtsi64xSi128(a: Long): M128i = mmCvtsi64Si128(a)

/**
 * Returns the lowest element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi128_si64)
 */
public fun mmCvtsi128Si64(a: M128i): Long = run { val _simdArg0 = a.asI64x2(); val _simdArg1 = 0L; _simdArg0 }

/**
 * Returns the lowest element of `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi128_si64x)
 */
public fun mmCvtsi128Si64x(a: M128i): Long = mmCvtsi128Si64(a)

/**
 * Returns `a` with its lower element replaced by `b` after converting it to
 * an `Double`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi64_sd)
 */
public fun mmCvtsi64Sd(a: M128d, b: Long): M128d = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = b.toDouble(); _simdArg0 }

/**
 * Returns `a` with its lower element replaced by `b` after converting it to
 * an `Double`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi64x_sd)
 */
public fun mmCvtsi64xSd(a: M128d, b: Long): M128d = mmCvtsi64Sd(a, b)
