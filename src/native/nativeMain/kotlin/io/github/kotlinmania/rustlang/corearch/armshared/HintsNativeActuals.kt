@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared

import io.github.kotlinmania.rustlang.corearch.armshared.cinterop.corearchArmsharedHintsHint
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun hint(arg0: Int) {
    corearchArmsharedHintsHint(arg0)
}
