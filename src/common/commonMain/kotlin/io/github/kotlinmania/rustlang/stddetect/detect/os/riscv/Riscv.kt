// port-lint: source detect/os/riscv.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.riscv

import io.github.kotlinmania.rustlang.stddetect.detect.arch.riscv.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Initializer

// Run-time feature detection utility for RISC-V.
//
// On RISC-V, full feature detection needs a help of one or more
// feature detection mechanisms (usually provided by the operating system).
//
// RISC-V architecture defines many extensions and some have dependency to others.
// More importantly, some of them cannot be enabled without resolving such
// dependencies due to limited set of features that such mechanisms provide.
//
// This module provides an OS-independent utility to process such relations
// between RISC-V extensions.

/**
 * Imply features by the given set of enabled features.
 *
 * Note that it does not perform any consistency checks including existence of
 * conflicting extensions and/or complicated requirements.  Eliminating such
 * inconsistencies is the responsibility of the feature detection logic and
 * its provider(s).
 */
public fun implyFeatures(value: Initializer): Initializer {
    while (true) {
        // Expect that the optimizer turns repeated operations into
        // a fewer number of bit-manipulation operations.
        // Check convergence of the feature flags later.
        val prev = value
        if (uintArrayOf(Feature.Zvbb.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvkb.toUInt())
        }
        if (uintArrayOf(Feature.Zvkn.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvkned.toUInt())
            value.set(Feature.Zvknhb.toUInt())
            value.set(Feature.Zvkb.toUInt())
            value.set(Feature.Zvkt.toUInt())
        }
        if (uintArrayOf(Feature.Zvkned.toUInt(), Feature.Zvknhb.toUInt(), Feature.Zvkb.toUInt(), Feature.Zvkt.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvkn.toUInt())
        }
        if (uintArrayOf(Feature.Zvknc.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvkn.toUInt())
            value.set(Feature.Zvbc.toUInt())
        }
        if (uintArrayOf(Feature.Zvkn.toUInt(), Feature.Zvbc.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvknc.toUInt())
        }
        if (uintArrayOf(Feature.Zvkng.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvkn.toUInt())
            value.set(Feature.Zvkg.toUInt())
        }
        if (uintArrayOf(Feature.Zvkn.toUInt(), Feature.Zvkg.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvkng.toUInt())
        }
        if (uintArrayOf(Feature.Zvks.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvksed.toUInt())
            value.set(Feature.Zvksh.toUInt())
            value.set(Feature.Zvkb.toUInt())
            value.set(Feature.Zvkt.toUInt())
        }
        if (uintArrayOf(Feature.Zvksed.toUInt(), Feature.Zvksh.toUInt(), Feature.Zvkb.toUInt(), Feature.Zvkt.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvks.toUInt())
        }
        if (uintArrayOf(Feature.Zvksc.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvks.toUInt())
            value.set(Feature.Zvbc.toUInt())
        }
        if (uintArrayOf(Feature.Zvks.toUInt(), Feature.Zvbc.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvksc.toUInt())
        }
        if (uintArrayOf(Feature.Zvksg.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvks.toUInt())
            value.set(Feature.Zvkg.toUInt())
        }
        if (uintArrayOf(Feature.Zvks.toUInt(), Feature.Zvkg.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zvksg.toUInt())
        }
        if (uintArrayOf(Feature.Zvknhb.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvknha.toUInt())
        }
        if (uintArrayOf(Feature.Zvknhb.toUInt(), Feature.Zvbc.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve64x.toUInt())
        }
        if (uintArrayOf(Feature.Zvbb.toUInt(), Feature.Zvkb.toUInt(), Feature.Zvkg.toUInt(), Feature.Zvkned.toUInt(), Feature.Zvknha.toUInt(), Feature.Zvksed.toUInt(), Feature.Zvksh.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32x.toUInt())
        }
        if (uintArrayOf(Feature.Zbc.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zbkc.toUInt())
        }
        if (uintArrayOf(Feature.Zkn.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zbkb.toUInt())
            value.set(Feature.Zbkc.toUInt())
            value.set(Feature.Zbkx.toUInt())
            value.set(Feature.Zkne.toUInt())
            value.set(Feature.Zknd.toUInt())
            value.set(Feature.Zknh.toUInt())
        }
        if (uintArrayOf(Feature.Zbkb.toUInt(), Feature.Zbkc.toUInt(), Feature.Zbkx.toUInt(), Feature.Zkne.toUInt(), Feature.Zknd.toUInt(), Feature.Zknh.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zkn.toUInt())
        }
        if (uintArrayOf(Feature.Zks.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zbkb.toUInt())
            value.set(Feature.Zbkc.toUInt())
            value.set(Feature.Zbkx.toUInt())
            value.set(Feature.Zksed.toUInt())
            value.set(Feature.Zksh.toUInt())
        }
        if (uintArrayOf(Feature.Zbkb.toUInt(), Feature.Zbkc.toUInt(), Feature.Zbkx.toUInt(), Feature.Zksed.toUInt(), Feature.Zksh.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zks.toUInt())
        }
        if (uintArrayOf(Feature.Zk.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zkn.toUInt())
            value.set(Feature.Zkr.toUInt())
            value.set(Feature.Zkt.toUInt())
        }
        if (uintArrayOf(Feature.Zkn.toUInt(), Feature.Zkr.toUInt(), Feature.Zkt.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zk.toUInt())
        }
        if (uintArrayOf(Feature.Zabha.toUInt(), Feature.Zacas.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zaamo.toUInt())
        }
        if (uintArrayOf(Feature.A.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zalrsc.toUInt())
            value.set(Feature.Zaamo.toUInt())
        }
        if (uintArrayOf(Feature.Zalrsc.toUInt(), Feature.Zaamo.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.A.toUInt())
        }
        if (uintArrayOf(Feature.B.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zba.toUInt())
            value.set(Feature.Zbb.toUInt())
            value.set(Feature.Zbs.toUInt())
        }
        if (uintArrayOf(Feature.Zba.toUInt(), Feature.Zbb.toUInt(), Feature.Zbs.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.B.toUInt())
        }
        if (uintArrayOf(Feature.Zcf.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zca.toUInt())
            value.set(Feature.F.toUInt())
        }
        if (uintArrayOf(Feature.Zcd.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zca.toUInt())
            value.set(Feature.D.toUInt())
        }
        if (uintArrayOf(Feature.Zcmop.toUInt(), Feature.Zcb.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zca.toUInt())
        }
        if (uintArrayOf(Feature.Zhinx.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zhinxmin.toUInt())
        }
        if (uintArrayOf(Feature.Zdinx.toUInt(), Feature.Zhinxmin.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zfinx.toUInt())
        }
        if (uintArrayOf(Feature.Zvfh.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvfhmin.toUInt())
        }
        if (uintArrayOf(Feature.Zvfh.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32f.toUInt())
            value.set(Feature.Zfhmin.toUInt())
        }
        if (uintArrayOf(Feature.Zvfhmin.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32f.toUInt())
        }
        if (uintArrayOf(Feature.Zvfbfwma.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zvfbfmin.toUInt())
            value.set(Feature.Zfbfmin.toUInt())
        }
        if (uintArrayOf(Feature.Zvfbfmin.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32f.toUInt())
        }
        if (uintArrayOf(Feature.V.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve64d.toUInt())
        }
        if (uintArrayOf(Feature.Zve64d.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve64f.toUInt())
            value.set(Feature.D.toUInt())
        }
        if (uintArrayOf(Feature.Zve64f.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve64x.toUInt())
            value.set(Feature.Zve32f.toUInt())
        }
        if (uintArrayOf(Feature.Zve64x.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32x.toUInt())
        }
        if (uintArrayOf(Feature.Zve32f.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zve32x.toUInt())
            value.set(Feature.F.toUInt())
        }
        if (uintArrayOf(Feature.Zfh.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zfhmin.toUInt())
        }
        if (uintArrayOf(Feature.Q.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.D.toUInt())
        }
        if (uintArrayOf(Feature.D.toUInt(), Feature.Zfhmin.toUInt(), Feature.Zfa.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.F.toUInt())
        }
        if (uintArrayOf(Feature.Zfbfmin.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.F.toUInt())
        }
        if (uintArrayOf(Feature.C.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zca.toUInt())
        }
        if (uintArrayOf(Feature.C.toUInt(), Feature.D.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zcd.toUInt())
        }
        if (uintArrayOf(Feature.C.toUInt(), Feature.F.toUInt()).all({ x -> value.test(x) })) {
            value.set(Feature.Zcf.toUInt())
        }
        if (value.test(Feature.D.toUInt())) {
            if (uintArrayOf(Feature.Zcd.toUInt()).any({ x -> value.test(x) })) {
                value.set(Feature.C.toUInt())
            }
        } else {
            if (uintArrayOf(Feature.Zca.toUInt()).any({ x -> value.test(x) })) {
                value.set(Feature.C.toUInt())
            }
        }
        if (uintArrayOf(Feature.Zicntr.toUInt(), Feature.Zihpm.toUInt(), Feature.F.toUInt(), Feature.Zfinx.toUInt(), Feature.Zve32x.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zicsr.toUInt())
        }
        if (uintArrayOf(Feature.M.toUInt()).any({ x -> value.test(x) })) {
            value.set(Feature.Zmmul.toUInt())
        }
        // Loop until the feature flags converge.
        if (prev == value) {
            return value
        }
    }
}
