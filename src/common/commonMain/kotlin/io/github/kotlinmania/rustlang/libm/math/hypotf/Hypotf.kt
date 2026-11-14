// port-lint: source math/hypotf.rs
package io.github.kotlinmania.rustlang.libm.math.hypotf

import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrtf

public fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float {
    var x1 = x
    var y1 = y
    val x1p90 = kotlin.Float.fromBits(0x6c800000u.toInt())
    // 0x1p90f === 2 ^ 90
    val x1p90 = kotlin.Float.fromBits(0x12800000u.toInt())
    // 0x1p-90f === 2 ^ -90
    var uxi = x1.toRawBits().toUInt()
    var uyi = y1.toRawBits().toUInt()
    val uti: Any?
    var z: kotlin.Float
    uxi = uxi and ((-1).toUInt() shr 1)
    uyi = uyi and ((-1).toUInt() shr 1)
    if (uxi < uyi) {
        uti = uxi
        uxi = uyi
        uyi = uti
    }
    x1 = kotlin.Float.fromBits(uxi.toInt())
    y1 = kotlin.Float.fromBits(uyi.toInt())
    if (uyi == 0xffu shl 23) {
        return y1
    }
    if (uxi >= 0xffu shl 23 || uyi == 0u || uxi - uyi >= 25u shl 23) {
        return x1 + y1
    }
    z = 1.0f
    if (uxi >= (0x7fu + 60u) shl 23) {
        z = x1p90
        x1 *= x1p90
        y1 *= x1p90
    } else if (uyi < (0x7fu - 60u) shl 23) {
        z = x1p90
        x1 *= x1p90
        y1 *= x1p90
    }
    return z * sqrtf((x1.toDouble() * x1.toDouble() + y1.toDouble() * y1.toDouble()).toFloat())
}
