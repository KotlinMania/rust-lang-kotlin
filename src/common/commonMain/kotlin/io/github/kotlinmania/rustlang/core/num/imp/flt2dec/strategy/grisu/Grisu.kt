// port-lint: source num/imp/flt2dec/strategy/grisu.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.flt2dec.strategy.grisu

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.imp.diyfloat.Fp
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.MAX_SIG_DIGITS
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.Decoded
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.roundUp
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import kotlin.experimental.and

// Rust adaptation of the Grisu3 algorithm described in "Printing Floating-Point Numbers Quickly
// and Accurately with Integers"[^1]. It uses about 1KB of precomputed table, and in turn, it's
// very quick for most inputs.
//
// [^1]: Florian Loitsch. 2010. Printing floating-point numbers quickly and
//   accurately with integers. SIGPLAN Not. 45, 6 (June 2010), 233-243.

// see the comments in `formatShortestOpt` for the rationale.
public val ALPHA: Short = (-60).toShort()

public val GAMMA: Short = (-32).toShort()

//
// # the following Python code generates this table:
// for i in xrange(-308, 333, 8):
// if i >= 0: f = 10**i; e = 0
// else: f = 2**(80-4*i) // 10**-i; e = 4 * i - 80
// l = f.bitLength()
// f = ((f << 64 >> (l-1)) + 1) >> 1; e += l - 64
// print '    (%#018x, %5d, %4d),' % (f, e, i)
//
public var CACHED_POW10: List<Triple<ULong, Short, Short>> = listOf(Triple(0xe61acf033d1a45dfuL, -1087, -308), Triple(0xab70fe17c79ac6cauL, -1060, -300), Triple(0xff77b1fcbebcdc4fuL, -1034, -292), Triple(0xbe5691ef416bd60cuL, -1007, -284), Triple(0x8dd01fad907ffc3cuL, -980, -276), Triple(0xd3515c2831559a83uL, -954, -268), Triple(0x9d71ac8fada6c9b5uL, -927, -260), Triple(0xea9c227723ee8bcbuL, -901, -252), Triple(0xaecc49914078536duL, -874, -244), Triple(0x823c12795db6ce57uL, -847, -236), Triple(0xc21094364dfb5637uL, -821, -228), Triple(0x9096ea6f3848984fuL, -794, -220), Triple(0xd77485cb25823ac7uL, -768, -212), Triple(0xa086cfcd97bf97f4uL, -741, -204), Triple(0xef340a98172aace5uL, -715, -196), Triple(0xb23867fb2a35b28euL, -688, -188), Triple(0x84c8d4dfd2c63f3buL, -661, -180), Triple(0xc5dd44271ad3cdbauL, -635, -172), Triple(0x936b9fcebb25c996uL, -608, -164), Triple(0xdbac6c247d62a584uL, -582, -156), Triple(0xa3ab66580d5fdaf6uL, -555, -148), Triple(0xf3e2f893dec3f126uL, -529, -140), Triple(0xb5b5ada8aaff80b8uL, -502, -132), Triple(0x87625f056c7c4a8buL, -475, -124), Triple(0xc9bcff6034c13053uL, -449, -116), Triple(0x964e858c91ba2655uL, -422, -108), Triple(0xdff9772470297ebduL, -396, -100), Triple(0xa6dfbd9fb8e5b88fuL, -369, -92), Triple(0xf8a95fcf88747d94uL, -343, -84), Triple(0xb94470938fa89bcfuL, -316, -76), Triple(0x8a08f0f8bf0f156buL, -289, -68), Triple(0xcdb02555653131b6uL, -263, -60), Triple(0x993fe2c6d07b7facuL, -236, -52), Triple(0xe45c10c42a2b3b06uL, -210, -44), Triple(0xaa242499697392d3uL, -183, -36), Triple(0xfd87b5f28300ca0euL, -157, -28), Triple(0xbce5086492111aebuL, -130, -20), Triple(0x8cbccc096f5088ccuL, -103, -12), Triple(0xd1b71758e219652cuL, -77, -4), Triple(0x9c40000000000000uL, -50, 4), Triple(0xe8d4a51000000000uL, -24, 12), Triple(0xad78ebc5ac620000uL, 3, 20), Triple(0x813f3978f8940984uL, 30, 28), Triple(0xc097ce7bc90715b3uL, 56, 36), Triple(0x8f7e32ce7bea5c70uL, 83, 44), Triple(0xd5d238a4abe98068uL, 109, 52), Triple(0x9f4f2726179a2245uL, 136, 60), Triple(0xed63a231d4c4fb27uL, 162, 68), Triple(0xb0de65388cc8ada8uL, 189, 76), Triple(0x83c7088e1aab65dbuL, 216, 84), Triple(0xc45d1df942711d9auL, 242, 92), Triple(0x924d692ca61be758uL, 269, 100), Triple(0xda01ee641a708deauL, 295, 108), Triple(0xa26da3999aef774auL, 322, 116), Triple(0xf209787bb47d6b85uL, 348, 124), Triple(0xb454e4a179dd1877uL, 375, 132), Triple(0x865b86925b9bc5c2uL, 402, 140), Triple(0xc83553c5c8965d3duL, 428, 148), Triple(0x952ab45cfa97a0b3uL, 455, 156), Triple(0xde469fbd99a05fe3uL, 481, 164), Triple(0xa59bc234db398c25uL, 508, 172), Triple(0xf6c69a72a3989f5cuL, 534, 180), Triple(0xb7dcbf5354e9beceuL, 561, 188), Triple(0x88fcf317f22241e2uL, 588, 196), Triple(0xcc20ce9bd35c78a5uL, 614, 204), Triple(0x98165af37b2153dfuL, 641, 212), Triple(0xe2a0b5dc971f303auL, 667, 220), Triple(0xa8d9d1535ce3b396uL, 694, 228), Triple(0xfb9b7cd9a4a7443cuL, 720, 236), Triple(0xbb764c4ca7a44410uL, 747, 244), Triple(0x8bab8eefb6409c1auL, 774, 252), Triple(0xd01fef10a657842cuL, 800, 260), Triple(0x9b10a4e5e9913129uL, 827, 268), Triple(0xe7109bfba19c0c9duL, 853, 276), Triple(0xac2820d9623bf429uL, 880, 284), Triple(0x80444b5e7aa7cf85uL, 907, 292), Triple(0xbf21e44003acdd2duL, 933, 300), Triple(0x8e679c2f5e44ff8fuL, 960, 308), Triple(0xd433179d9c8cb841uL, 986, 316), Triple(0x9e19db92b4e31ba9uL, 1013, 324), Triple(0xeb96bf6ebadf77d9uL, 1039, 332))

public val CACHED_POW10_FIRST_E: Short = (-1087).toShort()

public val CACHED_POW10_LAST_E: Short = 1039

public fun cachedPower(alpha: Short, gamma: Short): Pair<Short, Fp> {
    val offset = CACHED_POW10_FIRST_E.toInt()
    val range = (CACHED_POW10.size) - 1
    val domain = (CACHED_POW10_LAST_E - CACHED_POW10_FIRST_E).toInt()
    val idx = ((gamma.toInt()) - offset) * range / domain
    val (f, e, k) = CACHED_POW10[idx]
    if (false) {
        check(alpha <= e && e <= gamma)
    }
    return Pair(k, Fp(f = f, e = e.toShort()))
}

/**
 * Given `x> 0`, returns `(k, 10 ^ k)` such that `10 ^ k <= x<10 ^(k + 1)`.
 */
public fun maxPow10NoMoreThan(x: UInt): Pair<UByte, UInt> {
    val X9: UInt = 1000000000u
    val X8: UInt = 100000000u
    val X7: UInt = 10000000u
    val X6: UInt = 1000000u
    val X5: UInt = 100000u
    val X4: UInt = 10000u
    val X3: UInt = 1000u
    val X2: UInt = 100u
    val X1: UInt = 10u
    if (false) {
        check(x > 0u)
    }
    return if (x < X4) {
        if (x < X2) {
            if (x < X1) {
                Pair(0, 1)
            } else {
                Pair(1, X1)
            }
        } else {
            if (x < X3) {
                Pair(2, X2)
            } else {
                Pair(3, X3)
            }
        }
    } else {
        if (x < X6) {
            if (x < X5) {
                Pair(4, X4)
            } else {
                Pair(5, X5)
            }
        } else if (x < X8) {
            if (x < X7) {
                Pair(6, X6)
            } else {
                Pair(7, X7)
            }
        } else {
            if (x < X9) {
                Pair(8, X8)
            } else {
                Pair(9, X9)
            }
        }
    }
}

/**
 * The shortest mode implementation for Grisu.
 *
 * It returns `None` when it would return an inexact representation otherwise.
 */
public fun formatShortestOpt(d: Decoded, buf: MutableList<MaybeUninit<UByte>>): Pair<List<UByte>, Short>? {
    // we've generated all significant digits of `plus1`, but not sure if it's the optimal one.
    // for example, if `minus1` is 3.14153... and `plus1` is 3.14158..., there are 5 different
    // shortest representation from 3.14154 to 3.14158 but we only have the greatest one.
    // we have to successively decrease the last digit and check if this is the optimal repr.
    // there are at most 9 candidates (..1 to ..9), so this is fairly quick. ("rounding" phase)
    //
    // the function checks if this "optimal" repr is actually within the ulp ranges,
    // and also, it is possible that the "second-to-optimal" repr can actually be optimal
    // due to the rounding error. in either cases this returns `None`. ("weeding" phase)
    //
    // all arguments here are scaled by the common (but implicit) value `k`, so that:
    // - `remainder = (plus1 % 10 ^ kappa) * k`
    // - `threshold = (plus1 - minus1) * k` (and also, `remainder<threshold`)
    // - `plus1v = (plus1 - v) * k` (and also, `threshold> plus1v` from prior invariants)
    // - `tenKappa = 10 ^ kappa * k`
    // - `ulp = 2 ^ - e * k`
    fun roundAndWeed(buf1: MutableList<UByte>, exp: Short, remainder: ULong, threshold: ULong, plus1v: ULong, tenKappa: ULong, ulp: ULong): Pair<List<UByte>, Short>? {
        check(!buf1.isEmpty())
        // produce two approximations to `v` (actually `plus1 - v`) within 1.5 ulps.
        // the resulting representation should be the closest representation to both.
        //
        // here `plus1 - v` is used since calculations are done with respect to `plus1`
        // in order to avoid overflow/underflow (hence the seemingly swapped names).
        val plus1vDown = plus1v + ulp
        // plus1 - (v - 1 ulp)
        val plus1vUp = plus1v - ulp
        // plus1 - (v + 1 ulp)
        // decrease the last digit and stop at the closest representation to `v + 1 ulp`.
        var plus1w = remainder
        // plus1w(n) = plus1 - w(n)
        run {
            val last = buf1.lastOrNull()!!
            // we work with the approximated digits `w(n)`, which is initially equal to `plus1 -
            // plus1 % 10^kappa`.after running the loop body`n`times,`w(n) = plus1 -
            // plus1 % 10^kappa - n * 10^kappa`.we set`plus1w(n) = plus1 - w(n) =
            // plus1 % 10^kappa + n * 10^kappa`(thus`remainder = plus1w(0)`) to simplify checks.
            // note that `plus1w(n)` is always increasing.
            //
            // we have three conditions to terminate. any of them will make the loop unable to
            // proceed, but we then have at least one valid representation known to be closest to
            // `v + 1 ulp` anyway. we will denote them as TC1 through TC3 for brevity.
            //
            // TC1: `w(n) <= v + 1 ulp`, i.e., this is the last repr that can be the closest one.
            // this is equivalent to `plus1 - w(n) = plus1w(n) >= plus1 - (v + 1 ulp) = plus1vUp`.
            // combined with TC2 (which checks if `w(n + 1)` is valid), this prevents the possible
            // overflow on the calculation of `plus1w(n)`.
            //
            // TC2: `w(n + 1)<minus1`, i.e., the next repr definitely does not round to `v`.
            // this is equivalent to `plus1 - w(n) + 10^kappa = plus1w(n) + 10^kappa >
            // plus1 - minus1 = threshold`. the left hand side can overflow, but we know
            // `threshold> plus1v`, so if TC1 is false, `threshold - plus1w(n) >
            // threshold - (plus1v - 1 ulp) > 1 ulp` and we can safely test if
            // `threshold - plus1w(n)<10 ^ kappa` instead.
            //
            // TC3: `abs(w(n) - (v + 1 ulp)) <= abs(w(n + 1) - (v + 1 ulp))`, i.e., the next repr is
            // no closer to `v + 1 ulp` than the current repr. given `z(n) = plus1vUp - plus1w(n)`,
            // this becomes `abs(z(n)) <= abs(z(n + 1))`. again assuming that TC1 is false, we have
            // `z(n)> 0`. we have two cases to consider:
            //
            // - when `z(n + 1) >= 0`: TC3 becomes `z(n) <= z(n + 1)`. as `plus1w(n)` is increasing,
            // `z(n)` should be decreasing and this is clearly false.
            // - when `z(n + 1)<0`:
            // - TC3a: the precondition is `plus1vUp<plus1w(n) + 10 ^ kappa`. assuming TC2 is
            // false, `threshold >= plus1w(n) + 10 ^ kappa` so it cannot overflow.
            // - TC3b: TC3 becomes `z(n) <= - z(n + 1)`, i.e., `plus1vUp - plus1w(n) >=
            // plus1w(n+1) - plus1vUp = plus1w(n) + 10^kappa - plus1vUp`. the negated TC1
            // gives `plus1vUp> plus1w(n)`, so it cannot overflow or underflow when
            // combined with TC3a.
            //
            // consequently, we should stop when `TC1 || TC2 || (TC3a && TC3b)`. the following is
            // equal to its inverse, `!TC1 && !TC2 && (!TC3a || !TC3b)`.
            while (plus1w < plus1vUp && threshold - plus1w >= tenKappa && (plus1w + tenKappa < plus1vUp || plus1vUp - plus1w >= plus1w + tenKappa - plus1vUp)) {
                last -= 1
                if (false) {
                    check(last > 48u)
                }
                // the shortest repr cannot end with `0`
                plus1w += tenKappa
            }
        }
        // check if this representation is also the closest representation to `v - 1 ulp`.
        //
        // this is simply same to the terminating conditions for `v + 1 ulp`, with all `plus1vUp`
        // replaced by `plus1vDown` instead. overflow analysis equally holds.
        if (plus1w < plus1vDown && threshold - plus1w >= tenKappa && (plus1w + tenKappa < plus1vDown || plus1vDown - plus1w >= plus1w + tenKappa - plus1vDown)) {
            return null
        }
        // now we have the closest representation to `v` between `plus1` and `minus1`.
        // this is too liberal, though, so we reject any `w(n)` not between `plus0` and `minus0`,
        // i.e., `plus1 - plus1w(n) <= minus0` or `plus1 - plus1w(n) >= plus0`. we utilize the facts
        // that `threshold = plus1 - minus1` and `plus1 - plus0 = minus0 - minus1 = 2 ulp`.
        return if (2uL * ulp <= plus1w && plus1w <= threshold - 4uL * ulp) {
            Pair(buf1, exp)
        } else {
            null
        }
    }
    check(d.mant > 0uL)
    check(d.minus > 0uL)
    check(d.plus > 0uL)
    check(d.mant.checkedAdd(d.plus) != null)
    check(d.mant.checkedSub(d.minus) != null)
    check(buf.size >= MAX_SIG_DIGITS)
    check(d.mant + d.plus < (1uL shl 61))
    // we need at least three bits of additional precision
    // start with the normalized values with the shared exponent
    val plus = Fp(f = d.mant + d.plus, e = d.exp).normalize()
    val minus = Fp(f = d.mant - d.minus, e = d.exp).normalizeTo(plus.e)
    val v = Fp(f = d.mant, e = d.exp).normalizeTo(plus.e)
    // find any `cached = 10 ^ minusk` such that `ALPHA <= minusk + plus.e + 64 <= GAMMA`.
    // since `plus` is normalized, this means `2 ^(62 + ALPHA) <= plus * cached<2 ^(64 + GAMMA)`;
    // given our choices of `ALPHA` and `GAMMA`, this puts `plus * cached` into `[4, 2^32)`.
    //
    // it is obviously desirable to maximize `GAMMA - ALPHA`,
    // so that we don't need many cached powers of 10, but there are some considerations:
    //
    // 1. we want to keep `floor(plus * cached)` within `UInt` since it needs a costly division.
    // (this is not really avoidable, remainder is required for accuracy estimation.)
    // 2. the remainder of `floor(plus * cached)` repeatedly gets multiplied by 10,
    // and it should not overflow.
    //
    // the first gives `64 + GAMMA <= 32`, while the second gives `10 * 2 ^ - ALPHA <= 2 ^ 64`;
    // -60 and -32 is the maximal range with this constraint, and V8 also uses them.
    val (minusk, cached) = cachedPower((ALPHA - plus.e - 64).toShort(), (GAMMA - plus.e - 64).toShort())
    // scale fps. this gives the maximal error of 1 ulp (proved from Theorem 5.1).
    val plus1 = plus.mul(cached)
    val minus1 = minus.mul(cached)
    val v1 = v.mul(cached)
    if (false) {
        when (val matchVal = Pair(plus1.e, minus1.e)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = AssertKind.Eq
                    assertFailed(kind, leftVal, rightVal, Option.None())
                }
            }
        }
    }
    if (false) {
        when (val matchVal1 = Pair(plus1.e, v1.e)) {
            else -> {
                val leftVal1 = matchVal1.first
                val rightVal1 = matchVal1.second
                if (!(leftVal1 == rightVal1)) {
                    val kind1 = AssertKind.Eq
                    assertFailed(kind1, leftVal1, rightVal1, Option.None())
                }
            }
        }
    }
    // +- actual range of minus
    // | <---|---------------------- unchecked region --------------------------> |
    // |     |                                                                 |
    // |  |<--->|  | <--------------- safe region ---------------> |           |
    // |  |     |  |                                               |           |
    // |1 ulp|1 ulp|                 |1 ulp|1 ulp|                 |1 ulp|1 ulp|
    // |<--->|<--->|                 |<--->|<--->|                 |<--->|<--->|
    // |-----|-----|-------...-------|-----|-----|-------...-------|-----|-----|
    // |   minus   |                 |     v     |                 |   plus    |
    // minus1     minus0           v - 1 ulp   v + 1 ulp           plus0       plus1
    //
    // above `minus`, `v` and `plus` are *quantized* approximations (error < 1 ulp).
    // as we don't know the error is positive or negative, we use two approximations spaced equally
    // and have the maximal error of 2 ulps.
    //
    // the "unchecked region" is a liberal interval which we initially generate.
    // the "safe region" is a conservative interval which we only accept.
    // we start with the correct repr within the unchecked region, and try to find the closest repr
    // to `v` which is also within the safe region. if we can't, we give up.
    val plus1 = plus1.f + 1
    // let plus0 = plus.f - 1; // only for explanation
    // let minus0 = minus.f + 1; // only for explanation
    val minus1 = minus1.f - 1
    val e = (-plus1.e).toInt()
    // shared exponent
    // divide `plus1` into integral and fractional parts.
    // integral parts are guaranteed to fit in u32, since cached power guarantees `plus<2 ^ 32`
    // and normalized `plus.f` is always less than `2 ^ 64 - 2 ^ 4` due to the precision requirement.
    val plus1int = (plus1 shr e).toUInt()
    val plus1frac = plus1 and ((1 shl e) - 1)
    // calculate the largest `10 ^ maxKappa` no more than `plus1` (thus `plus1<10 ^(maxKappa + 1)`).
    // this is an upper bound of `kappa` below.
    val (maxKappa, maxTenKappa) = maxPow10NoMoreThan(plus1int)
    var i = 0
    val exp = maxKappa.toShort() - minusk + 1
    // Theorem 6.2: if `k` is the greatest integer s.t. `0 <= y mod 10 ^ k <= y - x`,
    // then `V = floor(y / 10 ^ k) * 10 ^ k` is in `[x, y]` and one of the shortest
    // representations (with the minimal number of significant digits) in that range.
    //
    // find the digit length `kappa` between `(minus1, plus1)` as per Theorem 6.2.
    // Theorem 6.2 can be adopted to exclude `x` by requiring `y mod 10 ^ k<y - x` instead.
    // (e.g., `x` = 32000, `y` = 32777; `kappa` = 2 since `y mod 10 ^ 3 = 777<y - x = 777`.)
    // the algorithm relies on the later verification phase to exclude `y`.
    val delta1 = plus1 - minus1
    // let delta1int = (delta1 >> e) as Int; // only for explanation
    val delta1frac = delta1 and ((1 shl e) - 1)
    // render integral parts, while checking for the accuracy at each step.
    var tenKappa = maxTenKappa
    // 10^kappa
    var remainder = plus1int
    // digits yet to be rendered
    while (true) {
        // we always have at least one digit to render, as `plus1 >= 10 ^ kappa`
        // invariants:
        // - `delta1int <= remainder<10 ^(kappa + 1)`
        // - `plus1int = d[0 .. n - 1] * 10 ^(kappa + 1) + remainder`
        // (it follows that `remainder = plus1int % 10 ^(kappa + 1)`)
        // divide `remainder` by `10 ^ kappa`. both are scaled by `2 ^ - e`.
        val q = remainder / tenKappa
        val r = remainder % tenKappa
        if (false) {
            check(q < 10u)
        }
        buf[i] = MaybeUninit.new(48u + q.toUByte(), PrimitiveLayouts.U8)
        i += 1
        val plus1rem = ((r.toULong()) shl e) + plus1frac
        // == (plus1 % 10^kappa) * 2^e
        if (plus1rem < delta1) {
            // `plus1 % 10 ^ kappa<delta1 = plus1 - minus1`; we've found the correct `kappa`.
            val tenKappa1 = (tenKappa.toULong()) shl e
            // scale 10^kappa back to the shared exponent
            return roundAndWeed(((buf).subList(0, i)).assumeInitMut(), exp, plus1rem, delta1, plus1 - v1.f, tenKappa1, 1uL)
        }
        // break the loop when we have rendered all integral digits.
        // the exact number of digits is `maxKappa + 1` as `plus1<10 ^(maxKappa + 1)`.
        if (i > maxKappa.toInt()) {
            if (false) {
                when (val matchVal2 = Pair(tenKappa, 1)) {
                    else -> {
                        val leftVal2 = matchVal2.first
                        val rightVal2 = matchVal2.second
                        if (!(leftVal2 == rightVal2)) {
                            val kind2 = AssertKind.Eq
                            assertFailed(kind2, leftVal2, rightVal2, Option.None())
                        }
                    }
                }
            }
            break
        }
        // restore invariants
        tenKappa /= 10
        remainder = r
    }
    // render fractional parts, while checking for the accuracy at each step.
    // this time we rely on repeated multiplications, as division will lose the precision.
    var remainder1 = plus1frac
    var threshold = delta1frac
    var ulp = 1
    while (true) {
        // the next digit should be significant as we've tested that before breaking out
        // invariants, where `m = maxKappa + 1` (# of digits in the integral part):
        // - `remainder<2 ^ e`
        // - `plus1frac * 10 ^(n - m) = d[m .. n - 1] * 2 ^ e + remainder`
        remainder1 *= 10
        // won't overflow, `2 ^ e * 10<2 ^ 64`
        threshold *= 10
        ulp *= 10
        // divide `remainder` by `10 ^ kappa`.
        // both are scaled by `2 ^ e / 10 ^ kappa`, so the latter is implicit here.
        val q1 = remainder1 shr e
        val r1 = remainder1 and ((1 shl e) - 1)
        if (false) {
            check(q1 < 10)
        }
        buf[i] = MaybeUninit.new(48u + q1.toUByte(), PrimitiveLayouts.U8)
        i += 1
        if (r1 < threshold) {
            val tenKappa2 = 1 shl e
            // implicit divisor
            return roundAndWeed(((buf).subList(0, i)).assumeInitMut(), exp, r1, threshold, (plus1 - v1.f) * ulp, tenKappa2, ulp)
        }
        // restore invariants
        remainder1 = r1
    }
}

/**
 * The shortest mode implementation for Grisu with Dragon fallback.
 *
 * This should be used for most cases.
 */
public fun formatShortest(d: Decoded, buf: MutableList<MaybeUninit<UByte>>): Pair<List<UByte>, Short> {
    // SAFETY: The borrow checker is not smart enough to let us use `buf`
    // in the second branch, so we launder the scope here. But we only re-use
    // `buf` if `formatShortestOpt` returned `None` so this is okay.
    return when (val matchVal = formatShortestOpt(d, RustMutableReference(MutPtr(((mutPtrFromRustCast(buf))).rawAddress), UnresolvedTypeLayout<Any?>()))) {
        null -> fallback(d, buf)
        else -> {
            val ret = matchVal
            ret
        }
    }
}

/**
 * The exact and fixed mode implementation for Grisu.
 *
 * It returns `None` when it would return an inexact representation otherwise.
 */
public fun formatExactOpt(d: Decoded, buf: MutableList<MaybeUninit<UByte>>, limit: Short): Pair<List<UByte>, Short>? {
    val POW10_UP_TO_9: UIntArray = uintArrayOf(1u, 10u, 100u, 1000u, 10000u, 100000u, 1000000u, 10000000u, 100000000u, 1000000000u)
    // we've generated all requested digits of `v`, which should be also same to corresponding
    // digits of `v - 1 ulp`. now we check if there is a unique representation shared by
    // both `v - 1 ulp` and `v + 1 ulp`; this can be either same to generated digits, or
    // to the rounded-up version of those digits. if the range contains multiple representations
    // of the same length, we cannot be sure and should return `None` instead.
    //
    // all arguments here are scaled by the common (but implicit) value `k`, so that:
    // - `remainder = (v % 10 ^ kappa) * k`
    // - `tenKappa = 10 ^ kappa * k`
    // - `ulp = 2 ^ - e * k`
    //
    // SAFETY: the first `len` bytes of `buf` must be initialized.
    fun possiblyRound(buf1: MutableList<MaybeUninit<UByte>>, len: Int, exp: Short, limit1: Short, remainder: ULong, tenKappa: ULong, ulp: ULong): Pair<List<UByte>, Short>? {
        var len1 = len
        var exp1 = exp
        if (false) {
            check(remainder < tenKappa)
        }
        // 10^kappa
        // :   :   :<->:   :
        // :   :   :   :   :
        // :|1 ulp|1 ulp|  :
        // :|<--->|<--->|  :
        // ----|-----|-----|----
        // |     v     |
        // v - 1 ulp   v + 1 ulp
        //
        // (for the reference, the dotted line indicates the exact value for
        // possible representations in given number of digits.)
        //
        // error is too large that there are at least three possible representations
        // between `v - 1 ulp` and `v + 1 ulp`. we cannot determine which one is correct.
        if (ulp >= tenKappa) {
            return null
        }
        // 10^kappa
        // :<------->:
        // :         :
        // : |1 ulp|1 ulp|
        // : |<--->|<--->|
        // ----|-----|-----|----
        // |     v     |
        // v - 1 ulp   v + 1 ulp
        //
        // in fact, 1/2 ulp is enough to introduce two possible representations.
        // (remember that we need a unique representation for both `v - 1 ulp` and `v + 1 ulp`.)
        // this won't overflow, as `ulp<tenKappa` from the first check.
        if (tenKappa - ulp <= ulp) {
            return null
        }
        // remainder
        // :<->|                           :
        // :   |                           :
        // :<--------- 10^kappa ---------->:
        // | :   |                           :
        // |1 ulp|1 ulp|                     :
        // |<--->|<--->|                     :
        // ----|-----|-----|------------------------
        // |     v     |
        // v - 1 ulp   v + 1 ulp
        //
        // if `v + 1 ulp` is closer to the rounded-down representation (which is already in `buf`),
        // then we can safely return. note that `v - 1 ulp` *can* be less than the current
        // representation, but as `1 ulp<10 ^ kappa / 2`, this condition is enough:
        // the distance between `v - 1 ulp` and the current representation
        // cannot exceed `10 ^ kappa / 2`.
        //
        // the condition equals to `remainder + ulp<10 ^ kappa / 2`.
        // since this can easily overflow, first check if `remainder<10 ^ kappa / 2`.
        // we've already verified that `ulp<10 ^ kappa / 2`, so as long as
        // `10 ^ kappa` did not overflow after all, the second check is fine.
        if (tenKappa - remainder > remainder && tenKappa - 2uL * remainder >= 2uL * ulp) {
            // SAFETY: our caller initialized that memory.
            return Pair(((buf1).subList(0, len1)).assumeInitRef(), exp1)
        }
        // :<------- remainder ------>|   :
        // :                          |   :
        // :<--------- 10^kappa --------->:
        // :                    |     |   : |
        // :                    |1 ulp|1 ulp|
        // :                    |<--->|<--->|
        // -----------------------|-----|-----|-----
        // |     v     |
        // v - 1 ulp   v + 1 ulp
        //
        // on the other hands, if `v - 1 ulp` is closer to the rounded-up representation,
        // we should round up and return. for the same reason we don't need to check `v + 1 ulp`.
        //
        // the condition equals to `remainder - ulp >= 10 ^ kappa / 2`.
        // again we first check if `remainder> ulp` (note that this is not `remainder >= ulp`,
        // as `10 ^ kappa` is never zero). also note that `remainder - ulp <= 10 ^ kappa`,
        // so the second check does not overflow.
        if (remainder > ulp && tenKappa - (remainder - ulp) <= remainder - ulp) {
            when (val matchVal = roundUp(((buf1).subList(0, len1)).assumeInitMut())) {
                null -> {}
                else -> {
                    val c = matchVal
                    // only add an additional digit when we've been requested the fixed precision.
                    // we also need to check that, if the original buffer was empty,
                    // the additional digit can only be added when `exp == limit` (edge case).
                    exp1 += 1
                    if (exp1 > limit1 && len1 < buf1.size) {
                        buf1[len1] = MaybeUninit.new(c, UnresolvedTypeLayout<Option>())
                        len1 += 1
                    }
                }
            }
            // SAFETY: we and our caller initialized that memory.
            return Pair(((buf1).subList(0, len1)).assumeInitRef(), exp1)
        }
        // otherwise we are doomed (i.e., some values between `v - 1 ulp` and `v + 1 ulp` are
        // rounding down and others are rounding up) and give up.
        return null
    }
    check(d.mant > 0uL)
    check(d.mant < (1uL shl 61))
    // we need at least three bits of additional precision
    check(!buf.isEmpty())
    // normalize and scale `v`.
    val v = Fp(f = d.mant, e = d.exp).normalize()
    val (minusk, cached) = cachedPower((ALPHA - v.e - 64).toShort(), (GAMMA - v.e - 64).toShort())
    val v1 = v.mul(cached)
    // divide `v` into integral and fractional parts.
    val e = (-v1.e).toInt()
    val vint = (v1.f shr e).toUInt()
    val vfrac = v1.f and ((1 shl e) - 1)
    val requestedDigits = buf.size
    // We deviate from the original algorithm here and do some early checks to determine if we can satisfy requestedDigits.
    // If we determine that we can't, we exit early and avoid most of the heavy lifting that the algorithm otherwise does.
    //
    // When vfrac is zero, we can easily determine if vint can satisfy requested digits:
    // If requestedDigits >= 11, vint is not able to exhaust the count by itself since 10^(11 -1) > u32 max value >= vint.
    // If vint < 10^(requestedDigits - 1), vint cannot exhaust the count.
    // Otherwise, vint might be able to exhaust the count and we need to execute the rest of the code.
    if ((vfrac == 0) && ((requestedDigits >= 11) || (vint < POW10_UP_TO_9[requestedDigits - 1]))) {
        return null
    }
    // both old `v` and new `v` (scaled by `10 ^ - k`) has an error of < 1 ulp (Theorem 5.1).
    // as we don't know the error is positive or negative, we use two approximations
    // spaced equally and have the maximal error of 2 ulps (same to the shortest case).
    //
    // the goal is to find the exactly rounded series of digits that are common to
    // both `v - 1 ulp` and `v + 1 ulp`, so that we are maximally confident.
    // if this is not possible, we don't know which one is the correct output for `v`,
    // so we give up and fall back.
    //
    // `err` is defined as `1 ulp * 2 ^ e` here (same to the ulp in `vfrac`),
    // and we will scale it whenever `v` gets scaled.
    var err = 1
    // calculate the largest `10 ^ maxKappa` no more than `v` (thus `v<10 ^(maxKappa + 1)`).
    // this is an upper bound of `kappa` below.
    val (maxKappa, maxTenKappa) = maxPow10NoMoreThan(vint)
    var i = 0
    val exp = maxKappa.toShort() - minusk + 1
    // if we are working with the last-digit limitation, we need to shorten the buffer
    // before the actual rendering in order to avoid double rounding.
    // note that we have to enlarge the buffer again when rounding up happens!
    val len =
        if (exp <= limit) {
            // oops, we cannot even produce *one* digit.
            // this is possible when, say, we've got something like 9.5 and it's being rounded to 10.
            //
            // in principle we can immediately call `possiblyRound` with an empty buffer,
            // but scaling `maxTenKappa<<e` by 10 can result in overflow.
            // thus we are being sloppy here and widen the error range by a factor of 10.
            // this will increase the false negative rate, but only very, *very* slightly;
            // it can only matter noticeably when the mantissa is bigger than 60 bits.
            //
            // SAFETY: `len = 0`, so the obligation of having initialized this memory is trivial.
            return possiblyRound(buf, 0, exp, limit, v1.f / 10uL, (maxTenKappa.toULong()) shl e, err shl e)
        } else if (((exp.toInt() - limit.toInt())) < buf.size) {
            (exp - limit).toInt()
        } else {
            buf.size
        }
    if (false) {
        check(len > 0)
    }
    // render integral parts.
    // the error is entirely fractional, so we don't need to check it in this part.
    var kappa = maxKappa.toShort()
    var tenKappa = maxTenKappa
    // 10^kappa
    var remainder = vint
    // digits yet to be rendered
    while (true) {
        // we always have at least one digit to render
        // invariants:
        // - `remainder<10 ^(kappa + 1)`
        // - `vint = d[0 .. n - 1] * 10 ^(kappa + 1) + remainder`
        // (it follows that `remainder = vint % 10 ^(kappa + 1)`)
        // divide `remainder` by `10 ^ kappa`. both are scaled by `2 ^ - e`.
        val q = remainder / tenKappa
        val r = remainder % tenKappa
        if (false) {
            check(q < 10u)
        }
        buf[i] = MaybeUninit.new(48u + q.toUByte(), PrimitiveLayouts.U8)
        i += 1
        // is the buffer full? run the rounding pass with the remainder.
        if (i == len) {
            val vrem = ((r.toULong()) shl e) + vfrac
            // == (v % 10^kappa) * 2^e
            // SAFETY: we have initialized `len` many bytes.
            return possiblyRound(buf, len, exp, limit, vrem, (tenKappa.toULong()) shl e, err shl e)
        }
        // break the loop when we have rendered all integral digits.
        // the exact number of digits is `maxKappa + 1` as `plus1<10 ^(maxKappa + 1)`.
        if (i > maxKappa.toInt()) {
            if (false) {
                when (val matchVal = Pair(tenKappa, 1)) {
                    else -> {
                        val leftVal = matchVal.first
                        val rightVal = matchVal.second
                        if (!(leftVal == rightVal)) {
                            val kind = AssertKind.Eq
                            assertFailed(kind, leftVal, rightVal, Option.None())
                        }
                    }
                }
            }
            if (false) {
                when (val matchVal1 = Pair(kappa, 0)) {
                    else -> {
                        val leftVal1 = matchVal1.first
                        val rightVal1 = matchVal1.second
                        if (!(leftVal1 == rightVal1)) {
                            val kind1 = AssertKind.Eq
                            assertFailed(kind1, leftVal1, rightVal1, Option.None())
                        }
                    }
                }
            }
            break
        }
        // restore invariants
        kappa -= 1
        tenKappa /= 10
        remainder = r
    }
    // render fractional parts.
    //
    // in principle we can continue to the last available digit and check for the accuracy.
    // unfortunately we are working with the finite-sized integers, so we need some criterion
    // to detect the overflow. V8 uses `remainder> err`, which becomes false when
    // the first `i` significant digits of `v - 1 ulp` and `v` differ. however this rejects
    // too many otherwise valid input.
    //
    // since the later phase has a correct overflow detection, we instead use tighter criterion:
    // we continue til `err` exceeds `10 ^ kappa / 2`, so that the range between `v - 1 ulp` and
    // `v + 1 ulp` definitely contains two or more rounded representations. this is same to
    // the first two comparisons from `possiblyRound`, for the reference.
    var remainder1 = vfrac
    val maxerr = 1 shl (e - 1)
    while (err < maxerr) {
        // invariants, where `m = maxKappa + 1` (# of digits in the integral part):
        // - `remainder<2 ^ e`
        // - `vfrac * 10 ^(n - m) = d[m .. n - 1] * 2 ^ e + remainder`
        // - `err = 10 ^(n - m)`
        remainder1 *= 10
        // won't overflow, `2 ^ e * 10<2 ^ 64`
        err *= 10
        // won't overflow, `err * 10<2 ^ e * 5<2 ^ 64`
        // divide `remainder` by `10 ^ kappa`.
        // both are scaled by `2 ^ e / 10 ^ kappa`, so the latter is implicit here.
        val q1 = remainder1 shr e
        val r1 = remainder1 and ((1 shl e) - 1)
        if (false) {
            check(q1 < 10)
        }
        buf[i] = MaybeUninit.new(48u + q1.toUByte(), PrimitiveLayouts.U8)
        i += 1
        // is the buffer full? run the rounding pass with the remainder.
        if (i == len) {
            // SAFETY: we have initialized `len` many bytes.
            return possiblyRound(buf, len, exp, limit, r1, 1uL shl e, err)
        }
        // restore invariants
        remainder1 = r1
    }
    // further calculation is useless (`possiblyRound` definitely fails), so we give up.
    return null
}

/**
 * The exact and fixed mode implementation for Grisu with Dragon fallback.
 *
 * This should be used for most cases.
 */
public fun formatExact(d: Decoded, buf: MutableList<MaybeUninit<UByte>>, limit: Short): Pair<List<UByte>, Short> {
    // SAFETY: The borrow checker is not smart enough to let us use `buf`
    // in the second branch, so we launder the scope here. But we only re-use
    // `buf` if `formatExactOpt` returned `None` so this is okay.
    return when (val matchVal = formatExactOpt(d, RustMutableReference(MutPtr(((mutPtrFromRustCast(buf))).rawAddress), UnresolvedTypeLayout<Any?>()), limit)) {
        null -> fallback(d, buf, limit)
        else -> {
            val ret = matchVal
            ret
        }
    }
}
