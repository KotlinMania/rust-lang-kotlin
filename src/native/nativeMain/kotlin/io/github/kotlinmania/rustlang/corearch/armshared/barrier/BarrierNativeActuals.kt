@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierDmb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierDsb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.corearchArmsharedBarrierIsb
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun dmb(arg0: Int) {
    corearchArmsharedBarrierDmb(arg0)
}

public actual fun dsb(arg0: Int) {
    corearchArmsharedBarrierDsb(arg0)
}

public actual fun isb(arg0: Int) {
    corearchArmsharedBarrierIsb(arg0)
}
