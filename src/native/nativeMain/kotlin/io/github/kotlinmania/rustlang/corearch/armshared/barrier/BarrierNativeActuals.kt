@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierExternDmb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierExternDsb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierExternIsb
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externDmb(arg0: Int) {
    corearchArmsharedBarrierExternDmb(arg0)
}

public actual fun externDsb(arg0: Int) {
    corearchArmsharedBarrierExternDsb(arg0)
}

public actual fun externIsb(arg0: Int) {
    corearchArmsharedBarrierExternIsb(arg0)
}
