@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared

import io.github.kotlinmania.rustlang.corearch.armshared.cinterop.corearchArmsharedHintsHint

public actual fun hint(arg0: Int) {
    corearchArmsharedHintsHint(arg0)
}
