// port-lint: source random.rs
package io.github.kotlinmania.rustlang.std.random

import io.github.kotlinmania.rustlang.core.random.*

// Random value generation.
//
// This module provides two low-level interfaces for random number generation:
// * The [Rng] trait abstracts over all random number generators (RNGs) and
//   is intended to be used in all cases where the choice of RNG is left to the
//   user. It provides the [`fillBytes`](Rng.fillBytes) method that fills a
//   buffer of [u8]s with freshly-generated random data.
// * The [SystemRng] implements the [Rng] trait by asking the operating
//   system for cryptographically-secure random data on every call to
//   `fillBytes`.
//
// In the future, higher-level interfaces for features like sampling distributions
// may be added to this module. Until that time, users of `fillBytes` should
// take care to avoid sampling bias when using the filled byte buffer to create
// an instance of another type. In particular, the modulo operation is **not**
// suitable for constraining the range of an unconstrained number:
// ```compile_fail
// let mut buf = [0; 2];
// rng.fillBytes(buf);
// // 💀 **DO NOT DO THIS** 💀
// // Numbers below ca. 22000 will be twice as likely.
// let veryBadRandomNumber = u16.fromNeBytes(buf) % 45000;
// ```
//
// # Examples
//
// Generating a [version 4/variant 1 UUID] represented as text:
// ```kotlin
// import std.random.Rng
// import std.random.SystemRng
//
// @feature(random)
// public fun doctestEntry() {
//     fun uuid(rng: Rng): String {
//         var buf = MutableList(16) { 0 }
//         rng.fillBytes(buf)
//         // Use little-endian to make the result reproducible across architectures.
//         val bits = U128.fromLeBytes(buf)
//         val g1 = (bits shr 96).toUInt()
//         val g2 = (bits shr 80).toUShort()
//         val g3 = (U128(0x0uL, 0x4000uL) or (bits shr 64) and U128(0x0uL, 0xfffuL)).toUShort()
//         val g4 = (U128(0x0uL, 0x8000uL) or (bits shr 48) and U128(0x0uL, 0x3fffuL)).toUShort()
//         val g5 = (bits and U128(0x0uL, 0xffffffffffffuL)).toULong()
//         return "$g1-$g2-$g3-$g4-$g5"
//     }
//     println("${uuid(SystemRng)}")
// }
// ```
//
// [version 4/variant 1 UUID]: https://en.wikipedia.org/wiki/UniversallyUniqueIdentifier#Version_4_(random)

/**
 * The system random number generator.
 *
 * This asks the system for random data suitable for cryptographic purposes
 * such as key generation. If security is a concern, consult the platform
 * documentation below for the specific guarantees your target provides.
 *
 * The high quality of randomness provided by this source means it can be quite
 * slow on some targets. If you need a large quantity of random numbers and
 * security is not a concern, you might want to consider using an alternative
 * random number generator. That said, `std` attempts to use the fastest source
 * available on the system that still provides strong security. A custom random
 * number generator will in nearly all cases have weaker security attributes
 * than `SystemRng`.
 *
 * # Blocking
 *
 * The underlying syscalls might block the calling thread until there is
 * sufficient [entropy] in the system to seed a procedural random number
 * generator. This is usually only a concern if the program runs immediately
 * after the system boots.
 *
 * # Panicking
 *
 * Calling `fillBytes` will panic if the system cannot provide the required
 * random data. Due to the blocking behaviour described above, this is a
 * permanent condition in nearly all cases and implies that the system lacks
 * the facilities (hardware or software) necessary for collecting entropy.
 * Non-embedded systems usually do not suffer from this limitation.
 *
 * [entropy]: https://en.wikipedia.org/wiki/Entropy_(informationTheory)
 *
 * # Underlying sources
 *
 * Platform               | Source
 * -----------------------|---------------------------------------------------------------
 * Linux                  | [getrandom] or [dev.urandom] after polling `/ dev / random`
 * Windows                | [`ProcessPrng`](https://learn.microsoft.com/en-us/windows/win32/seccng/processprng)
 * Apple                  | `CCRandomGenerateBytes`
 * DragonFly              | [`arc4randomBuf`](https://man.dragonflybsd.org/?command=arc4random)
 * ESP-IDF                | [`espFillRandom`](https://docs.espressif.com/projects/esp-idf/en/latest/esp32/api-reference/system/random.html#_CPPv415esp_fill_randomPv6size_t)
 * FreeBSD                | [`arc4randomBuf`](https://man.freebsd.org/cgi/man.cgi?query=arc4random)
 * Fuchsia                | [`cprngDraw`](https://fuchsia.dev/reference/syscalls/cprngDraw)
 * Haiku                  | `arc4randomBuf`
 * Illumos                | [`arc4randomBuf`](https://www.illumos.org/man/3C/arc4random)
 * NetBSD                 | [`arc4randomBuf`](https://man.netbsd.org/arc4random.3)
 * OpenBSD                | [`arc4randomBuf`](https://man.openbsd.org/arc4random.3)
 * Solaris                | [`arc4randomBuf`](https://docs.oracle.com/cd/E88353_01/html/E37843/arc4random-3c.html)
 * Vita                   | `arc4randomBuf`
 * Hermit                 | `readEntropy`
 * Horizon, Cygwin, Redox | `getrandom`
 * AIX, Hurd, QNX         | `/ dev / urandom`
 * RTEMS                  | [`arc4randomBuf`](https://docs.rtems.org/branches/main/bsp-howto/getentropy.html)
 * SGX                    | [`rdrand`](https://en.wikipedia.org/wiki/RDRAND)
 * SOLID                  | `SolidRngSampleRandomBytes`
 * TEEOS                  | `TeeGenerateRandom`
 * UEFI                   | [`EfiRngProtocol`](https://uefi.org/specs/UEFI/2.10/37_Secure_Technologies.html#random-number-generator-protocol)
 * VxWorks                | `randABytes` after waiting for `randSecure` to become ready
 * WASIp1                 | [`randomGet`](https://github.com/WebAssembly/WASI/blob/wasi-0.1/preview1/docs.md#-randomGetbuf-pointeru8-bufLen-size---result-errno)
 * WASIp2                 | [get.random.bytes]
 * WASIp3                 | [get.random.bytes]
 * ZKVM                   | `sysRand`
 *
 * Note that the sources used might change over time.
 *
 * Consult the documentation for the underlying operations on your supported
 * targets to determine whether they provide any particular desired properties,
 * such as support for reseeding on VM fork operations.
 *
 * [getrandom]: https://www.man7.org/linux/man-pages/man2/getrandom.2.html
 * [dev.urandom]: https://www.man7.org/linux/man-pages/man4/random.4.html
 * [get.random.bytes]: https://github.com/WebAssembly/WASI/blob/main/proposals/random/imports.md#get-random-bytes-func
 *
 * # Examples
 *
 * Filling a buffer with random bytes
 * ```kotlin
 * import std.random.Rng
 * import std.random.SystemRng
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     var buf = MutableList(16) { 0 }
 *     SystemRng.fillBytes(buf)
 *     dbg(buf)
 * }
 * ```
 */
public open class SystemRng : Rng {
    public open fun clone(): SystemRng = SystemRng()

    public open fun fillBytes(bytes: MutableList<UByte>) {
        fillBytes(bytes)
    }

    public companion object
}

/**
 * Generates a random value from a distribution, using the default random source.
 *
 * This is a convenience function for `dist.sample(SystemRng)` and will sample according to
 * the same distribution as the underlying [Distribution] trait implementation. See [SystemRng]
 * for more information about how randomness is sourced.
 *
 * # Examples
 *
 * Generating a [version 4/variant 1 UUID] represented as text:
 * ```kotlin
 * import std.random.random
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val bits: U128 = random(0 until 0)
 *     val g1 = (bits shr 96).toUInt()
 *     val g2 = (bits shr 80).toUShort()
 *     val g3 = (U128(0x0uL, 0x4000uL) or (bits shr 64) and U128(0x0uL, 0xfffuL)).toUShort()
 *     val g4 = (U128(0x0uL, 0x8000uL) or (bits shr 48) and U128(0x0uL, 0x3fffuL)).toUShort()
 *     val g5 = (bits and U128(0x0uL, 0xffffffffffffuL)).toULong()
 *     val uuid = "$g1-$g2-$g3-$g4-$g5"
 *     println("$uuid")
 * }
 * ```
 *
 * [version 4/variant 1 UUID]: https://en.wikipedia.org/wiki/UniversallyUniqueIdentifier#Version_4_(random)
 */
public fun <T> random(dist: Distribution<T>): T = dist.sample(SystemRng())
