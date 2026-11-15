// port-lint: source math/exp2f.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libm.math.exp2f

import io.github.kotlinmania.rustlang.libm.math.arch.x87Exp2f

// origin: FreeBSD /usr/src/lib/msun/src/sExp2f.c
// -
// Copyright (c) 2005 David Schultz <das@FreeBSD.ORG>
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS AS IS'' AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
// OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
public val TBLSIZE: Int = 16

public var EXP2FT: ULongArray = ulongArrayOf(0x3fe6a09e667f3bcduL, 0x3fe7a11473eb0187uL, 0x3fe8ace5422aa0dbuL, 0x3fe9c49182a3f090uL, 0x3feae89f995ad3aduL, 0x3fec199bdd85529cuL, 0x3fed5818dcfba487uL, 0x3feea4afa2a490dauL, 0x3ff0000000000000uL, 0x3ff0b5586cf9890fuL, 0x3ff172b83c7d517buL, 0x3ff2387a6e756238uL, 0x3ff306fe0a31b715uL, 0x3ff3dea64c123422uL, 0x3ff4bfdad5362a27uL, 0x3ff5ab07dd485429uL)

// exp2f(x): compute the base 2 exponential of x
//
// Accuracy: Peak error < 0.501 ulp; location of peak: -0.030110927.
//
// Method: (equally-spaced tables)
//
// Reduce x:
// x = k + y, for integer k and |y| <= 1/2.
// Thus we have exp2f(x) = 2**k * exp2(y).
//
// Reduce y:
// y = i/TBLSIZE + z for integer i near y * TBLSIZE.
// Thus we have exp2(y) = exp2(i/TBLSIZE) * exp2(z),
// with |z| <= 2**-(TBLSIZE+1).
//
// We compute exp2(i/TBLSIZE) via table lookup and exp2(z) via a
// degree-4 minimax polynomial with maximum error under 1.4 * 2**-33.
// Using double precision for everything except the reduction makes
// roundoff error insignificant and simplifies the scaling step.
//
// This method is due to Tang, but I do not use his suggested parameters:
//
// Tang, P.  Table-driven Implementation of the Exponential Function
// in IEEE Floating-Point Arithmetic.  TOMS 15(2), 144-157 (1989).

/**
 * Exponential, base 2 (f32)
 *
 * Calculate `2 ^ x`, that is, 2 raised to the power `x`.
 */
public fun exp2f(x: kotlin.Float): kotlin.Float {
    var x1 = x
    if (true) {
        return x87Exp2f(x1)
    }
    val redux = kotlin.Float.fromBits(0x4b400000u.toInt()) / TBLSIZE.toFloat()
    val p1 = kotlin.Float.fromBits(0x3f317218u.toInt())
    val p2 = kotlin.Float.fromBits(0x3e75fdf0u.toInt())
    val p3 = kotlin.Float.fromBits(0x3d6359a4u.toInt())
    val p4 = kotlin.Float.fromBits(0x3c1d964eu.toInt())
    // doubleT t, r, z;
    // uint32T ix, i0, k;
    val x1p127 = kotlin.Float.fromBits(0x7f000000u.toInt())
    // Filter out exceptional cases.
    val ui = x1.toRawBits().toUInt()
    val ix = ui and 0x7fffffff
    if (ix > 0x42fc0000) {
        // |x| > 126
        if (ix > 0x7f800000) {
            // NaN
            return x1
        }
        if ((0x43000000 until 0x80000000).contains(ui)) {
            // x >= 128
            x1 *= x1p127
            return x1
        }
        if (ui >= 0x80000000) {
            // x < -126
            if (ui >= 0xc3160000 || (ui and 0x0000ffff != 0)) {
                readVolatile(kotlin.Float.fromBits(0x80000001u.toInt()) / x1)
            }
            if (ui >= 0xc3160000) {
                // x <= -150
                return 0.0f
            }
        }
    } else if (ix <= 0x33000000) {
        // |x| <= 0x1p-25
        return 1.0f + x1
    }
    // Reduce x, computing z, i0, and k.
    val ui1 = (x1 + redux).toRawBits().toUInt()
    var i0 = ui1
    i0 += TBLSIZE.toUInt() / 2u
    val k = i0 / TBLSIZE.toUInt()
    val ukf = Double.fromBits((((0x3ffu + k).toULong()) shl 52).toLong())
    i0 = i0 and (TBLSIZE.toUInt() - 1u)
    var uf = kotlin.Float.fromBits(ui1.toInt())
    uf -= redux
    val z: Double = (x1 - uf).toDouble()
    // Compute r = exp2(y) = exp2ft[i0] * p(z).
    val r: Double = Double.fromBits((EXP2FT.getUnchecked(i0.toInt())).toLong())
    val t: Double = r * z
    val r1: Double = r + t * (p1.toDouble() + z * p2.toDouble()) + t * (z * z) * (p3.toDouble() + z * p4.toDouble())
    // Scale by 2**k
    return (r1 * ukf).toFloat()
}
