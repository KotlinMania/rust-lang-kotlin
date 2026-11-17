package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem

public val USE_LZ: Boolean =
    if (false) {
        if (false) {
            // ARM thumb targets have CLZ instructions if the instruction set of ARMv6T2 is
            // supported. This is needed to successfully differentiate between targets like
            // `thumbv8.base` and `thumbv8.main`.
            false
        } else {
            // Regular ARM targets have CLZ instructions if the ARMv5TE instruction set is
            // supported. Technically, ARMv5T was the first to have CLZ, but the "v5t" target
            // feature does not seem to work.
            false
        }
    } else if (false) {
        // LZD or LZCNT on SPARC only exists for the VIS 3 extension and later.
        false
    } else if (false) {
        // The `Zbb` Basic Bit-Manipulation extension on RISC-V
        // determines if a CLZ assembly instruction exists
        false
    } else if (false) {
        // The '32s' 32-bit standard feature on LoongArch
        // determines if a CLZ assembly instruction exists
        false
    } else {
        // All other common targets Rust supports should have CLZ instructions
        true
    }
