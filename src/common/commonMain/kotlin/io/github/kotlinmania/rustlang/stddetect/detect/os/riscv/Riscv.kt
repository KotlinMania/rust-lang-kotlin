// port-lint: source detect/os/riscv.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.riscv

import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache

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
internal fun implyFeatures(value: cache.Initializer): cache.Initializer {
    while (true) {
        // Expect that the optimizer turns repeated operations into
        // a fewer number of bit-manipulation operations.
        // Check convergence of the feature flags later.
        val prev = value
        if (uintArrayOf((Feature::zvbb).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvkb).toUInt())
        }
        if (uintArrayOf((Feature::zvkn).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvkned).toUInt())
            value.set((Feature::zvknhb).toUInt())
            value.set((Feature::zvkb).toUInt())
            value.set((Feature::zvkt).toUInt())
        }
        if (uintArrayOf((Feature::zvkned).toUInt(), (Feature::zvknhb).toUInt(), (Feature::zvkb).toUInt(), (Feature::zvkt).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvkn).toUInt())
        }
        if (uintArrayOf((Feature::zvknc).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvkn).toUInt())
            value.set((Feature::zvbc).toUInt())
        }
        if (uintArrayOf((Feature::zvkn).toUInt(), (Feature::zvbc).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvknc).toUInt())
        }
        if (uintArrayOf((Feature::zvkng).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvkn).toUInt())
            value.set((Feature::zvkg).toUInt())
        }
        if (uintArrayOf((Feature::zvkn).toUInt(), (Feature::zvkg).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvkng).toUInt())
        }
        if (uintArrayOf((Feature::zvks).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvksed).toUInt())
            value.set((Feature::zvksh).toUInt())
            value.set((Feature::zvkb).toUInt())
            value.set((Feature::zvkt).toUInt())
        }
        if (uintArrayOf((Feature::zvksed).toUInt(), (Feature::zvksh).toUInt(), (Feature::zvkb).toUInt(), (Feature::zvkt).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvks).toUInt())
        }
        if (uintArrayOf((Feature::zvksc).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvks).toUInt())
            value.set((Feature::zvbc).toUInt())
        }
        if (uintArrayOf((Feature::zvks).toUInt(), (Feature::zvbc).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvksc).toUInt())
        }
        if (uintArrayOf((Feature::zvksg).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvks).toUInt())
            value.set((Feature::zvkg).toUInt())
        }
        if (uintArrayOf((Feature::zvks).toUInt(), (Feature::zvkg).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zvksg).toUInt())
        }
        if (uintArrayOf((Feature::zvknhb).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvknha).toUInt())
        }
        if (uintArrayOf((Feature::zvknhb).toUInt(), (Feature::zvbc).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve64x).toUInt())
        }
        if (uintArrayOf((Feature::zvbb).toUInt(), (Feature::zvkb).toUInt(), (Feature::zvkg).toUInt(), (Feature::zvkned).toUInt(), (Feature::zvknha).toUInt(), (Feature::zvksed).toUInt(), (Feature::zvksh).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32x).toUInt())
        }
        if (uintArrayOf((Feature::zbc).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zbkc).toUInt())
        }
        if (uintArrayOf((Feature::zkn).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zbkb).toUInt())
            value.set((Feature::zbkc).toUInt())
            value.set((Feature::zbkx).toUInt())
            value.set((Feature::zkne).toUInt())
            value.set((Feature::zknd).toUInt())
            value.set((Feature::zknh).toUInt())
        }
        if (uintArrayOf((Feature::zbkb).toUInt(), (Feature::zbkc).toUInt(), (Feature::zbkx).toUInt(), (Feature::zkne).toUInt(), (Feature::zknd).toUInt(), (Feature::zknh).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zkn).toUInt())
        }
        if (uintArrayOf((Feature::zks).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zbkb).toUInt())
            value.set((Feature::zbkc).toUInt())
            value.set((Feature::zbkx).toUInt())
            value.set((Feature::zksed).toUInt())
            value.set((Feature::zksh).toUInt())
        }
        if (uintArrayOf((Feature::zbkb).toUInt(), (Feature::zbkc).toUInt(), (Feature::zbkx).toUInt(), (Feature::zksed).toUInt(), (Feature::zksh).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zks).toUInt())
        }
        if (uintArrayOf((Feature::zk).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zkn).toUInt())
            value.set((Feature::zkr).toUInt())
            value.set((Feature::zkt).toUInt())
        }
        if (uintArrayOf((Feature::zkn).toUInt(), (Feature::zkr).toUInt(), (Feature::zkt).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zk).toUInt())
        }
        if (uintArrayOf((Feature::zabha).toUInt(), (Feature::zacas).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zaamo).toUInt())
        }
        if (uintArrayOf((Feature::a).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zalrsc).toUInt())
            value.set((Feature::zaamo).toUInt())
        }
        if (uintArrayOf((Feature::zalrsc).toUInt(), (Feature::zaamo).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::a).toUInt())
        }
        if (uintArrayOf((Feature::b).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zba).toUInt())
            value.set((Feature::zbb).toUInt())
            value.set((Feature::zbs).toUInt())
        }
        if (uintArrayOf((Feature::zba).toUInt(), (Feature::zbb).toUInt(), (Feature::zbs).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::b).toUInt())
        }
        if (uintArrayOf((Feature::zcf).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zca).toUInt())
            value.set((Feature::f).toUInt())
        }
        if (uintArrayOf((Feature::zcd).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zca).toUInt())
            value.set((Feature::d).toUInt())
        }
        if (uintArrayOf((Feature::zcmop).toUInt(), (Feature::zcb).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zca).toUInt())
        }
        if (uintArrayOf((Feature::zhinx).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zhinxmin).toUInt())
        }
        if (uintArrayOf((Feature::zdinx).toUInt(), (Feature::zhinxmin).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zfinx).toUInt())
        }
        if (uintArrayOf((Feature::zvfh).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvfhmin).toUInt())
        }
        if (uintArrayOf((Feature::zvfh).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32f).toUInt())
            value.set((Feature::zfhmin).toUInt())
        }
        if (uintArrayOf((Feature::zvfhmin).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32f).toUInt())
        }
        if (uintArrayOf((Feature::zvfbfwma).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zvfbfmin).toUInt())
            value.set((Feature::zfbfmin).toUInt())
        }
        if (uintArrayOf((Feature::zvfbfmin).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32f).toUInt())
        }
        if (uintArrayOf((Feature::v).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve64d).toUInt())
        }
        if (uintArrayOf((Feature::zve64d).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve64f).toUInt())
            value.set((Feature::d).toUInt())
        }
        if (uintArrayOf((Feature::zve64f).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve64x).toUInt())
            value.set((Feature::zve32f).toUInt())
        }
        if (uintArrayOf((Feature::zve64x).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32x).toUInt())
        }
        if (uintArrayOf((Feature::zve32f).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zve32x).toUInt())
            value.set((Feature::f).toUInt())
        }
        if (uintArrayOf((Feature::zfh).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zfhmin).toUInt())
        }
        if (uintArrayOf((Feature::q).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::d).toUInt())
        }
        if (uintArrayOf((Feature::d).toUInt(), (Feature::zfhmin).toUInt(), (Feature::zfa).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::f).toUInt())
        }
        if (uintArrayOf((Feature::zfbfmin).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::f).toUInt())
        }
        if (uintArrayOf((Feature::c).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zca).toUInt())
        }
        if (uintArrayOf((Feature::c).toUInt(), (Feature::d).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zcd).toUInt())
        }
        if (uintArrayOf((Feature::c).toUInt(), (Feature::f).toUInt()).all({ x -> value.test(x) })) {
            value.set((Feature::zcf).toUInt())
        }
        // (to "C"; defined as superset)
        cfgSelect(targetArch, "riscv32", value, test, Feature, d, imply, zcf, zcd, c, value, test, Feature, f, imply, zcf, c, imply, zca, c, value, test, Feature, d, imply, zcd, c, imply, zca, c)
        if (uintArrayOf((Feature::zicntr).toUInt(), (Feature::zihpm).toUInt(), (Feature::f).toUInt(), (Feature::zfinx).toUInt(), (Feature::zve32x).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zicsr).toUInt())
        }
        if (uintArrayOf((Feature::m).toUInt()).any({ x -> value.test(x) })) {
            value.set((Feature::zmmul).toUInt())
        }
        // Loop until the feature flags converge.
        if (prev == value) {
            return value
        }
    }
}
