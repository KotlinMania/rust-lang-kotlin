@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXgetbv
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXrstor
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXrstors
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXsave
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXsavec
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXsaveopt
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXsaves
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveXsetbv
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXsave(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXrstor(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    corearchX86XsaveXsetbv(v, hi, lo)
}

public actual fun externXgetbv(v: UInt): Long =
    corearchX86XsaveExternXgetbv(v)

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXsaveopt(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXsavec(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXsaves(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveXrstors(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}
