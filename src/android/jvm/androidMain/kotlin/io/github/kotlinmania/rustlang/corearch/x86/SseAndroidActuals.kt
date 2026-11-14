@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86SseJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86SseSfence()

    @JvmStatic
    external fun corearchX86SseStmxcsr(p: Long)

    @JvmStatic
    external fun corearchX86SseLdmxcsr(p: Long)

    @JvmStatic
    external fun corearchX86SsePrefetch(p: Long, rw: Int, loc: Int, ty: Int)
}

public actual fun rcpss(a: M128): M128 =
    M128()

public actual fun rcpps(a: M128): M128 =
    M128()

public actual fun rsqrtss(a: M128): M128 =
    M128()

public actual fun rsqrtps(a: M128): M128 =
    M128()

public actual fun minss(a: M128, b: M128): M128 =
    M128()

public actual fun minps(a: M128, b: M128): M128 =
    M128()

public actual fun maxss(a: M128, b: M128): M128 =
    M128()

public actual fun maxps(a: M128, b: M128): M128 =
    M128()

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    M128()

public actual fun comieqSs(a: M128, b: M128): Int =
    0

public actual fun comiltSs(a: M128, b: M128): Int =
    0

public actual fun comileSs(a: M128, b: M128): Int =
    0

public actual fun comigtSs(a: M128, b: M128): Int =
    0

public actual fun comigeSs(a: M128, b: M128): Int =
    0

public actual fun comineqSs(a: M128, b: M128): Int =
    0

public actual fun ucomieqSs(a: M128, b: M128): Int =
    0

public actual fun ucomiltSs(a: M128, b: M128): Int =
    0

public actual fun ucomileSs(a: M128, b: M128): Int =
    0

public actual fun ucomigtSs(a: M128, b: M128): Int =
    0

public actual fun ucomigeSs(a: M128, b: M128): Int =
    0

public actual fun ucomineqSs(a: M128, b: M128): Int =
    0

public actual fun cvtss2si(a: M128): Int =
    0

public actual fun cvttss2si(a: M128): Int =
    0

public actual fun sfence() {
    X86SseJniBridge.corearchX86SseSfence()
}

public actual fun stmxcsr(p: MutPtr) {
    X86SseJniBridge.corearchX86SseStmxcsr(p.rawAddress)
}

public actual fun ldmxcsr(p: ConstPtr) {
    X86SseJniBridge.corearchX86SseLdmxcsr(p.rawAddress)
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    X86SseJniBridge.corearchX86SsePrefetch(p.rawAddress, rw, loc, ty)
}

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    M128()
