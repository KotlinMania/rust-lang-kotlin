@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.rustlangCorearchArmsharedBarrierDmb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.rustlangCorearchArmsharedBarrierDsb
import io.github.kotlinmania.rustlang.corearch.armshared.barrier.cinterop.rustlangCorearchArmsharedBarrierIsb
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun dmb(arg0: Int) {
    rustlangCorearchArmsharedBarrierDmb(arg0)
}

public actual fun dsb(arg0: Int) {
    rustlangCorearchArmsharedBarrierDsb(arg0)
}

public actual fun isb(arg0: Int) {
    rustlangCorearchArmsharedBarrierIsb(arg0)
}
