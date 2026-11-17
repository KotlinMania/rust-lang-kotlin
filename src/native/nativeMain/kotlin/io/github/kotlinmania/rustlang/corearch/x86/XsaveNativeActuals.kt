@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXgetbv
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXrstor
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXrstors
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXsave
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXsavec
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXsaveopt
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXsaves
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86XsaveExternXsetbv
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun externXsave(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXsave(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXrstor(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsetbv(v: UInt, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXsetbv(v, hi, lo)
}

public actual fun externXgetbv(v: UInt): Long =
    corearchX86XsaveExternXgetbv(v)

public actual fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXsaveopt(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsavec(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXsavec(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsaves(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXsaves(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX86XsaveExternXrstors(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}
