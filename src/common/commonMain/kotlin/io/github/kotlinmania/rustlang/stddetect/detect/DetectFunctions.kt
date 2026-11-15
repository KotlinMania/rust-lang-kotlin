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
public fun features(): Any =
    Feature.ALL.iter().copied().map({ feat ->
        val name: String = feat.toStr()
        val enabled: Boolean = checkFor(feat)
        Pair(name, enabled)
    })
