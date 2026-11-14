package io.github.kotlinmania.rustlang.stddetect

// Run-time feature detection for the Rust standard library.
//
// To detect whether a feature is enabled in the system running the binary
// use one of the appropriate macro for the target:
//
// * `x86` and `x8664`: [isX86FeatureDetected]
// * `arm`: [isArmFeatureDetected]
// * `aarch64`: [isAarch64FeatureDetected]
// * `riscv`: [isRiscvFeatureDetected]
// * `mips`: [isMipsFeatureDetected]
// * `mips64`: [isMips64FeatureDetected]
// * `powerpc`: [isPowerpcFeatureDetected]
// * `loongarch`: [isLoongarchFeatureDetected]
// * `s390x`: [isS390xFeatureDetected]

