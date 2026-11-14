package io.github.kotlinmania.rustlang.stddetect.detect

import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.test

/**
 * Performs run-time feature detection.
 */
public fun checkFor(x: Feature): Boolean = test(x.toUInt())

/**
 * Returns an `Iterator<Item = (String, Boolean)>` where
 * `Item.0` is the feature name, and `Item.1` is a `Boolean` which
 * is `true` if the feature is supported by the host and `false` otherwise.
 */
public fun features(): Any = cfgSelect(any, targetArch, "x86", targetArch, "x86_64", targetArch, "arm", targetArch, "aarch64", targetArch, "arm64ec", targetArch, "riscv32", targetArch, "riscv64", targetArch, "powerpc", targetArch, "powerpc64", targetArch, "mips", targetArch, "mips64", targetArch, "loongarch32", targetArch, "loongarch64", targetArch, "s390x", Feature, ALL, iter, copied, map, feat, name, feat, toStr, enabled, checkFor, feat, name, enabled, None, intoIter)
