@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.intrinsics

private val kmCoreNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.kmCoreNapiV2 !== 'undefined') return globalThis.kmCoreNapiV2; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; } var fs = req('fs'); var path = req('path'); var cwd = process.cwd ? process.cwd() : '.'; var dir = cwd; for (var d = 0; d < 8; d++) { var directCands = [ path.resolve(dir, 'node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'build/js/node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'build/wasm/node_modules/@kotlinmania/kmcore-native-bindings/index.js'), path.resolve(dir, 'src/js/node-kmcore/dist/build/Release/kmcore_native.node'), path.resolve(dir, 'src/js/node-kmcore/build/Release/kmcore_native.node') ]; for (var i = 0; i < directCands.length; i++) { if (fs.existsSync(directCands[i])) { req(directCands[i]); if (globalThis.kmCoreNapiV2) return globalThis.kmCoreNapiV2; } } var scopeDirs = [ path.resolve(dir, 'node_modules/@kotlinmania'), path.resolve(dir, 'build/js/node_modules/@kotlinmania'), path.resolve(dir, 'build/wasm/node_modules/@kotlinmania') ]; for (var s = 0; s < scopeDirs.length; s++) { if (fs.existsSync(scopeDirs[s])) { var entries = fs.readdirSync(scopeDirs[s]); for (var e = 0; e < entries.length; e++) { if (entries[e].endsWith('-native-bindings')) { var pkgEntry = path.resolve(scopeDirs[s], entries[e], 'index.js'); if (fs.existsSync(pkgEntry)) { req(pkgEntry); if (globalThis.kmCoreNapiV2) return globalThis.kmCoreNapiV2; } } } } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.kmCoreNapiV2 = null; return null; })()")

private fun lo32(v: Long): Int = v.toInt()
private fun hi32(v: Long): Int = (v ushr 32).toInt()
private fun uLo32(v: ULong): Int = v.toInt()
private fun uHi32(v: ULong): Int = (v shr 32).toInt()
private fun packI64(lo: Int, hi: Int): Long = (hi.toLong() shl 32) or (lo.toLong() and 0xFFFF_FFFFL)
private fun packU64(lo: Int, hi: Int): ULong =
    ((hi.toLong().toULong() and 0xFFFF_FFFFuL) shl 32) or (lo.toLong().toULong() and 0xFFFF_FFFFuL)

public actual fun kmLoadU8(addr: Long): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLoadU8(lo32(addr), hi32(addr)) as Int).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmLoadU16(addr: Long): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLoadU16(lo32(addr), hi32(addr)) as Int).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmLoadU32(addr: Long): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLoadU32(lo32(addr), hi32(addr)) as Int).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmLoadU64(addr: Long): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmLoadU64(lo32(addr), hi32(addr)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }
public actual fun kmLoadF32(addr: Long): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLoadF32(lo32(addr), hi32(addr)) as Double).toFloat() else KmLinearMemoryArena.loadF32(addr)
public actual fun kmLoadF64(addr: Long): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLoadF64(lo32(addr), hi32(addr)) as Double) else KmLinearMemoryArena.loadF64(addr)

public actual fun kmStoreU8(addr: Long, value: UByte) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreU8(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmStoreU16(addr: Long, value: UShort) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreU16(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmStoreU32(addr: Long, value: UInt) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreU32(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmStoreU64(addr: Long, value: ULong) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value)) else KmLinearMemoryArena.storeU64(addr, value)
}
public actual fun kmStoreF32(addr: Long, value: Float) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreF32(lo32(addr), hi32(addr), value.toDouble()) else KmLinearMemoryArena.storeF32(addr, value)
}
public actual fun kmStoreF64(addr: Long, value: Double) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmStoreF64(lo32(addr), hi32(addr), value) else KmLinearMemoryArena.storeF64(addr, value)
}

public actual fun kmVolatileLoadU8(addr: Long): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmVolatileLoadU8(lo32(addr), hi32(addr)) as Int).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmVolatileLoadU16(addr: Long): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmVolatileLoadU16(lo32(addr), hi32(addr)) as Int).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmVolatileLoadU32(addr: Long): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmVolatileLoadU32(lo32(addr), hi32(addr)) as Int).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmVolatileLoadU64(addr: Long): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmVolatileLoadU64(lo32(addr), hi32(addr)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }

public actual fun kmVolatileStoreU8(addr: Long, value: UByte) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmVolatileStoreU8(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmVolatileStoreU16(addr: Long, value: UShort) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmVolatileStoreU16(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmVolatileStoreU32(addr: Long, value: UInt) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmVolatileStoreU32(lo32(addr), hi32(addr), value.toInt()) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmVolatileStoreU64(addr: Long, value: ULong) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmVolatileStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value)) else KmLinearMemoryArena.storeU64(addr, value)
}

public actual fun kmCopyNonoverlapping(dst: Long, src: Long, bytes: Long) {
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmCopyNonoverlapping(lo32(dst), hi32(dst), lo32(src), hi32(src), lo32(bytes), hi32(bytes))
    } else {
        KmLinearMemoryArena.copyNonoverlapping(dst, src, bytes)
    }
}
public actual fun kmCopy(dst: Long, src: Long, bytes: Long) {
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmCopy(lo32(dst), hi32(dst), lo32(src), hi32(src), lo32(bytes), hi32(bytes))
    } else {
        KmLinearMemoryArena.copy(dst, src, bytes)
    }
}
public actual fun kmWriteBytes(dst: Long, value: UByte, bytes: Long) {
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmWriteBytes(lo32(dst), hi32(dst), value.toInt(), lo32(bytes), hi32(bytes))
    } else {
        KmLinearMemoryArena.writeBytes(dst, value, bytes)
    }
}
public actual fun kmCompareBytes(left: Long, right: Long, bytes: Long): Int =
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmCompareBytes(lo32(left), hi32(left), lo32(right), hi32(right), lo32(bytes), hi32(bytes)) as Int
    } else {
        KmLinearMemoryArena.compareBytes(left, right, bytes)
    }

public actual fun kmAlloc(size: Long, align: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmAlloc(lo32(size), hi32(size), lo32(align), hi32(align)) as Int
        packI64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.alloc(size, align)
    }
public actual fun kmAllocZeroed(size: Long, align: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmAllocZeroed(lo32(size), hi32(size), lo32(align), hi32(align)) as Int
        packI64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.allocZeroed(size, align)
    }
public actual fun kmRealloc(ptr: Long, oldSize: Long, align: Long, newSize: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo =
            kmCoreNapiAddon.kmRealloc(
                lo32(ptr),
                hi32(ptr),
                lo32(oldSize),
                hi32(oldSize),
                lo32(align),
                hi32(align),
                lo32(newSize),
                hi32(newSize),
            ) as Int
        packI64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.realloc(ptr, oldSize, align, newSize)
    }
public actual fun kmDealloc(ptr: Long, size: Long, align: Long) {
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmDealloc(lo32(ptr), hi32(ptr), lo32(size), hi32(size), lo32(align), hi32(align))
    } else {
        KmLinearMemoryArena.dealloc(ptr, size, align)
    }
}

public actual fun kmAtomicFence(ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicFence(ord)
}
public actual fun kmAtomicSignalFence(ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicSignalFence(ord)
}
public actual fun kmAtomicLoadU8(addr: Long, ord: Int): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicLoadU8(lo32(addr), hi32(addr), ord) as Int).toUByte() else KmLinearMemoryArena.loadU8(addr)
public actual fun kmAtomicLoadU16(addr: Long, ord: Int): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicLoadU16(lo32(addr), hi32(addr), ord) as Int).toUShort() else KmLinearMemoryArena.loadU16(addr)
public actual fun kmAtomicLoadU32(addr: Long, ord: Int): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicLoadU32(lo32(addr), hi32(addr), ord) as Int).toUInt() else KmLinearMemoryArena.loadU32(addr)
public actual fun kmAtomicLoadU64(addr: Long, ord: Int): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmAtomicLoadU64(lo32(addr), hi32(addr), ord) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.loadU64(addr)
    }

public actual fun kmAtomicStoreU8(addr: Long, value: UByte, ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicStoreU8(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU8(addr, value)
}
public actual fun kmAtomicStoreU16(addr: Long, value: UShort, ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicStoreU16(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU16(addr, value)
}
public actual fun kmAtomicStoreU32(addr: Long, value: UInt, ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicStoreU32(lo32(addr), hi32(addr), value.toInt(), ord) else KmLinearMemoryArena.storeU32(addr, value)
}
public actual fun kmAtomicStoreU64(addr: Long, value: ULong, ord: Int) {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAtomicStoreU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), ord) else KmLinearMemoryArena.storeU64(addr, value)
}

public actual fun kmAtomicXchgU8(addr: Long, value: UByte, ord: Int): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicXchgU8(lo32(addr), hi32(addr), value.toInt(), ord) as Int).toUByte() else KmLinearMemoryArena.atomicXchgU8(addr, value)
public actual fun kmAtomicXchgU16(addr: Long, value: UShort, ord: Int): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicXchgU16(lo32(addr), hi32(addr), value.toInt(), ord) as Int).toUShort() else KmLinearMemoryArena.atomicXchgU16(addr, value)
public actual fun kmAtomicXchgU32(addr: Long, value: UInt, ord: Int): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicXchgU32(lo32(addr), hi32(addr), value.toInt(), ord) as Int).toUInt() else KmLinearMemoryArena.atomicXchgU32(addr, value)
public actual fun kmAtomicXchgU64(addr: Long, value: ULong, ord: Int): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmAtomicXchgU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), ord) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.atomicXchgU64(addr, value)
    }

public actual fun kmAtomicCxchgU8(addr: Long, expected: UByte, desired: UByte, succ: Int, fail: Int): AtomicCxchgU8Outcome =
    if (kmCoreNapiAddon != null) {
        val packed = kmCoreNapiAddon.kmAtomicCxchgU8(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail) as Int
        AtomicCxchgU8Outcome((packed and 0xFF).toUByte(), (packed ushr 8) != 0)
    } else {
        KmLinearMemoryArena.atomicCxchgU8(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU16(addr: Long, expected: UShort, desired: UShort, succ: Int, fail: Int): AtomicCxchgU16Outcome =
    if (kmCoreNapiAddon != null) {
        val packed = kmCoreNapiAddon.kmAtomicCxchgU16(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail) as Int
        AtomicCxchgU16Outcome((packed and 0xFFFF).toUShort(), (packed ushr 16) != 0)
    } else {
        KmLinearMemoryArena.atomicCxchgU16(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU32(addr: Long, expected: UInt, desired: UInt, succ: Int, fail: Int): AtomicCxchgU32Outcome =
    if (kmCoreNapiAddon != null) {
        val ok = (kmCoreNapiAddon.kmAtomicCxchgU32(lo32(addr), hi32(addr), expected.toInt(), desired.toInt(), succ, fail) as Int) != 0
        AtomicCxchgU32Outcome((kmCoreNapiAddon.kmOut0() as Int).toUInt(), ok)
    } else {
        KmLinearMemoryArena.atomicCxchgU32(addr, expected, desired)
    }

public actual fun kmAtomicCxchgU64(addr: Long, expected: ULong, desired: ULong, succ: Int, fail: Int): AtomicCxchgU64Outcome =
    if (kmCoreNapiAddon != null) {
        val ok =
            (
                kmCoreNapiAddon.kmAtomicCxchgU64(
                    lo32(addr),
                    hi32(addr),
                    uLo32(expected),
                    uHi32(expected),
                    uLo32(desired),
                    uHi32(desired),
                    succ,
                    fail,
                ) as Int
            ) != 0
        AtomicCxchgU64Outcome(packU64(kmCoreNapiAddon.kmOut0() as Int, kmCoreNapiAddon.kmOut1() as Int), ok)
    } else {
        KmLinearMemoryArena.atomicCxchgU64(addr, expected, desired)
    }

public actual fun kmAtomicRmwU8(addr: Long, value: UByte, op: Int, ord: Int): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicRmwU8(lo32(addr), hi32(addr), value.toInt(), op, ord) as Int).toUByte() else KmLinearMemoryArena.atomicRmwU8(addr, value, op)
public actual fun kmAtomicRmwU16(addr: Long, value: UShort, op: Int, ord: Int): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicRmwU16(lo32(addr), hi32(addr), value.toInt(), op, ord) as Int).toUShort() else KmLinearMemoryArena.atomicRmwU16(addr, value, op)
public actual fun kmAtomicRmwU32(addr: Long, value: UInt, op: Int, ord: Int): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmAtomicRmwU32(lo32(addr), hi32(addr), value.toInt(), op, ord) as Int).toUInt() else KmLinearMemoryArena.atomicRmwU32(addr, value, op)
public actual fun kmAtomicRmwU64(addr: Long, value: ULong, op: Int, ord: Int): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmAtomicRmwU64(lo32(addr), hi32(addr), uLo32(value), uHi32(value), op, ord) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.atomicRmwU64(addr, value, op)
    }

public actual fun kmCtpopU64(value: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtpopU64(uLo32(value), uHi32(value)) as Int).toUInt() else KmLinearMemoryArena.ctpopU64(value)
public actual fun kmCtlzU64(value: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtlzU64(uLo32(value), uHi32(value)) as Int).toUInt() else KmLinearMemoryArena.ctlzU64(value)
public actual fun kmCttzU64(value: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCttzU64(uLo32(value), uHi32(value)) as Int).toUInt() else KmLinearMemoryArena.cttzU64(value)
public actual fun kmBswapU16(value: UShort): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmBswapU16(value.toInt()) as Int).toUShort() else KmLinearMemoryArena.bswapU16(value)
public actual fun kmBswapU32(value: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmBswapU32(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.bswapU32(value)
public actual fun kmBswapU64(value: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmBswapU64(uLo32(value), uHi32(value)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.bswapU64(value)
    }
public actual fun kmBitreverseU64(value: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmBitreverseU64(uLo32(value), uHi32(value)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.bitreverseU64(value)
    }
public actual fun kmFmaf32(a: Float, b: Float, c: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFmaf32(a.toDouble(), b.toDouble(), c.toDouble()) as Double).toFloat() else KmLinearMemoryArena.fmaf32(a, b, c)
public actual fun kmFmaf64(a: Double, b: Double, c: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFmaf64(a, b, c) as Double) else KmLinearMemoryArena.fmaf64(a, b, c)
public actual fun kmSqrtf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSqrtf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.sqrtf32(x)
public actual fun kmSqrtf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSqrtf64(x) as Double) else KmLinearMemoryArena.sqrtf64(x)
public actual fun kmAbort(): Nothing {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmAbort()
    error("Rust intrinsic abort triggered")
}

public actual fun kmSpinLoopHint() {
    if (kmCoreNapiAddon != null) kmCoreNapiAddon.kmSpinLoopHint()
}
public actual fun kmBlackBoxU64(value: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmBlackBoxU64(uLo32(value), uHi32(value)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        value
    }
public actual fun kmUmulhU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmUmulhU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int
        packU64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.umulhU64(a, b)
    }
public actual fun kmSmulhI64(a: Long, b: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo = kmCoreNapiAddon.kmSmulhI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int
        packI64(lo, kmCoreNapiAddon.kmOut1() as Int)
    } else {
        KmLinearMemoryArena.smulhI64(a, b)
    }
public actual fun kmAtomicCxchgU128Pair(
    addr: Long,
    expLo: ULong,
    expHi: ULong,
    desLo: ULong,
    desHi: ULong,
): AtomicCxchgU128Outcome =
    if (kmCoreNapiAddon != null) {
        val ok =
            (
                kmCoreNapiAddon.kmAtomicCxchgU128Pair(
                    lo32(addr),
                    hi32(addr),
                    uLo32(expLo),
                    uHi32(expLo),
                    uLo32(expHi),
                    uHi32(expHi),
                    uLo32(desLo),
                    uHi32(desLo),
                    uLo32(desHi),
                    uHi32(desHi),
                ) as Int
            ) != 0
        AtomicCxchgU128Outcome(
            packU64(kmCoreNapiAddon.kmOut0() as Int, kmCoreNapiAddon.kmOut1() as Int),
            packU64(kmCoreNapiAddon.kmOut2() as Int, kmCoreNapiAddon.kmOut3() as Int),
            ok,
        )
    } else {
        KmLinearMemoryArena.atomicCxchgU128Pair(addr, expLo, expHi, desLo, desHi)
    }

public actual fun kmSimdFmaF32x4(dstAddr: Long, aAddr: Long, bAddr: Long, cAddr: Long) {
    if (kmCoreNapiAddon != null) {
        kmCoreNapiAddon.kmSimdFmaF32x4(lo32(dstAddr), hi32(dstAddr), lo32(aAddr), hi32(aAddr), lo32(bAddr), hi32(bAddr), lo32(cAddr), hi32(cAddr))
    } else {
        KmLinearMemoryArena.simdFmaF32x4(dstAddr, aAddr, bAddr, cAddr)
    }
}
public actual fun kmSimdDotF32(aAddr: Long, bAddr: Long, len: Long): Float =
    if (kmCoreNapiAddon != null) {
        (kmCoreNapiAddon.kmSimdDotF32(lo32(aAddr), hi32(aAddr), lo32(bAddr), hi32(bAddr), lo32(len), hi32(len)) as Double).toFloat()
    } else {
        KmLinearMemoryArena.simdDotF32(aAddr, bAddr, len)
    }

public actual fun kmCtpopU8(value: UByte): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtpopU8(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctpopU8(value)
public actual fun kmCtpopU16(value: UShort): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtpopU16(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctpopU16(value)
public actual fun kmCtpopU32(value: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtpopU32(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctpopU32(value)
public actual fun kmCtpopU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtpopU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)) as Int).toUInt() else KmLinearMemoryArena.ctpopU128(lo, hi)
public actual fun kmCtlzU8(value: UByte): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtlzU8(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctlzU8(value)
public actual fun kmCtlzU16(value: UShort): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtlzU16(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctlzU16(value)
public actual fun kmCtlzU32(value: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtlzU32(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.ctlzU32(value)
public actual fun kmCtlzU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCtlzU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)) as Int).toUInt() else KmLinearMemoryArena.ctlzU128(lo, hi)
public actual fun kmCttzU8(value: UByte): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCttzU8(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.cttzU8(value)
public actual fun kmCttzU16(value: UShort): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCttzU16(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.cttzU16(value)
public actual fun kmCttzU32(value: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCttzU32(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.cttzU32(value)
public actual fun kmCttzU128(lo: ULong, hi: ULong): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCttzU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)) as Int).toUInt() else KmLinearMemoryArena.cttzU128(lo, hi)
public actual fun kmBswapU128(lo: ULong, hi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmBswapU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.bswapU128(lo, hi)
    }
public actual fun kmBitreverseU8(value: UByte): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmBitreverseU8(value.toInt()) as Int).toUByte() else KmLinearMemoryArena.bitreverseU8(value)
public actual fun kmBitreverseU16(value: UShort): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmBitreverseU16(value.toInt()) as Int).toUShort() else KmLinearMemoryArena.bitreverseU16(value)
public actual fun kmBitreverseU32(value: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmBitreverseU32(value.toInt()) as Int).toUInt() else KmLinearMemoryArena.bitreverseU32(value)
public actual fun kmBitreverseU128(lo: ULong, hi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmBitreverseU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.bitreverseU128(lo, hi)
    }
public actual fun kmRotateLeftU8(value: UByte, n: UInt): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateLeftU8(value.toInt(), n.toInt()) as Int).toUByte() else KmLinearMemoryArena.rotateLeftU8(value, n)
public actual fun kmRotateLeftU16(value: UShort, n: UInt): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateLeftU16(value.toInt(), n.toInt()) as Int).toUShort() else KmLinearMemoryArena.rotateLeftU16(value, n)
public actual fun kmRotateLeftU32(value: UInt, n: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateLeftU32(value.toInt(), n.toInt()) as Int).toUInt() else KmLinearMemoryArena.rotateLeftU32(value, n)
public actual fun kmRotateLeftU64(value: ULong, n: UInt): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmRotateLeftU64(uLo32(value), uHi32(value), n.toInt()) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.rotateLeftU64(value, n)
    }
public actual fun kmRotateLeftU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmRotateLeftU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi), n.toInt()) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.rotateLeftU128(lo, hi, n)
    }
public actual fun kmRotateRightU8(value: UByte, n: UInt): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateRightU8(value.toInt(), n.toInt()) as Int).toUByte() else KmLinearMemoryArena.rotateRightU8(value, n)
public actual fun kmRotateRightU16(value: UShort, n: UInt): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateRightU16(value.toInt(), n.toInt()) as Int).toUShort() else KmLinearMemoryArena.rotateRightU16(value, n)
public actual fun kmRotateRightU32(value: UInt, n: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRotateRightU32(value.toInt(), n.toInt()) as Int).toUInt() else KmLinearMemoryArena.rotateRightU32(value, n)
public actual fun kmRotateRightU64(value: ULong, n: UInt): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmRotateRightU64(uLo32(value), uHi32(value), n.toInt()) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.rotateRightU64(value, n)
    }
public actual fun kmRotateRightU128(lo: ULong, hi: ULong, n: UInt): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmRotateRightU128(uLo32(lo), uHi32(lo), uLo32(hi), uHi32(hi), n.toInt()) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.rotateRightU128(lo, hi, n)
    }
public actual fun kmAddWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowU8(a.toInt(), b.toInt()) as Int)
        OverflowU8Outcome(v.toUByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU8(a, b)
    }
public actual fun kmAddWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowU16(a.toInt(), b.toInt()) as Int)
        OverflowU16Outcome(v.toUShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU16(a, b)
    }
public actual fun kmAddWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowU32(a.toInt(), b.toInt()) as Int)
        OverflowU32Outcome(v.toUInt(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowU32(a, b)
    }
public actual fun kmAddWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmAddWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int) != 0
        OverflowU64Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowU64(a, b)
    }
public actual fun kmAddWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmAddWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowU128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmAddWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowI8(a.toInt(), b.toInt()) as Int)
        OverflowI8Outcome(v.toByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI8(a, b)
    }
public actual fun kmAddWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowI16(a.toInt(), b.toInt()) as Int)
        OverflowI16Outcome(v.toShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI16(a, b)
    }
public actual fun kmAddWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmAddWithOverflowI32(a, b) as Int)
        OverflowI32Outcome(v, (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.addWithOverflowI32(a, b)
    }
public actual fun kmAddWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmAddWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int) != 0
        OverflowI64Outcome(packI64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowI64(a, b)
    }
public actual fun kmAddWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmAddWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowI128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.addWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSubWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowU8(a.toInt(), b.toInt()) as Int)
        OverflowU8Outcome(v.toUByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU8(a, b)
    }
public actual fun kmSubWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowU16(a.toInt(), b.toInt()) as Int)
        OverflowU16Outcome(v.toUShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU16(a, b)
    }
public actual fun kmSubWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowU32(a.toInt(), b.toInt()) as Int)
        OverflowU32Outcome(v.toUInt(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowU32(a, b)
    }
public actual fun kmSubWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmSubWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int) != 0
        OverflowU64Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowU64(a, b)
    }
public actual fun kmSubWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmSubWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowU128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSubWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowI8(a.toInt(), b.toInt()) as Int)
        OverflowI8Outcome(v.toByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI8(a, b)
    }
public actual fun kmSubWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowI16(a.toInt(), b.toInt()) as Int)
        OverflowI16Outcome(v.toShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI16(a, b)
    }
public actual fun kmSubWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmSubWithOverflowI32(a, b) as Int)
        OverflowI32Outcome(v, (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.subWithOverflowI32(a, b)
    }
public actual fun kmSubWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmSubWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int) != 0
        OverflowI64Outcome(packI64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowI64(a, b)
    }
public actual fun kmSubWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmSubWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowI128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.subWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmMulWithOverflowU8(a: UByte, b: UByte): OverflowU8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowU8(a.toInt(), b.toInt()) as Int)
        OverflowU8Outcome(v.toUByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU8(a, b)
    }
public actual fun kmMulWithOverflowU16(a: UShort, b: UShort): OverflowU16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowU16(a.toInt(), b.toInt()) as Int)
        OverflowU16Outcome(v.toUShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU16(a, b)
    }
public actual fun kmMulWithOverflowU32(a: UInt, b: UInt): OverflowU32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowU32(a.toInt(), b.toInt()) as Int)
        OverflowU32Outcome(v.toUInt(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowU32(a, b)
    }
public actual fun kmMulWithOverflowU64(a: ULong, b: ULong): OverflowU64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmMulWithOverflowU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int) != 0
        OverflowU64Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowU64(a, b)
    }
public actual fun kmMulWithOverflowU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowU128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmMulWithOverflowU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowU128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmMulWithOverflowI8(a: Byte, b: Byte): OverflowI8Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowI8(a.toInt(), b.toInt()) as Int)
        OverflowI8Outcome(v.toByte(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI8(a, b)
    }
public actual fun kmMulWithOverflowI16(a: Short, b: Short): OverflowI16Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowI16(a.toInt(), b.toInt()) as Int)
        OverflowI16Outcome(v.toShort(), (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI16(a, b)
    }
public actual fun kmMulWithOverflowI32(a: Int, b: Int): OverflowI32Outcome =
    if (kmCoreNapiAddon != null) {
        val v = (kmCoreNapiAddon.kmMulWithOverflowI32(a, b) as Int)
        OverflowI32Outcome(v, (kmCoreNapiAddon.kmOut1() as Int) != 0)
    } else {
        KmLinearMemoryArena.mulWithOverflowI32(a, b)
    }
public actual fun kmMulWithOverflowI64(a: Long, b: Long): OverflowI64Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmMulWithOverflowI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int) != 0
        OverflowI64Outcome(packI64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowI64(a, b)
    }
public actual fun kmMulWithOverflowI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): OverflowI128Outcome =
    if (kmCoreNapiAddon != null) {
        val ovf = (kmCoreNapiAddon.kmMulWithOverflowI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int) != 0
        OverflowI128Outcome(packU64((kmCoreNapiAddon.kmOut0() as Int), (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), ovf)
    } else {
        KmLinearMemoryArena.mulWithOverflowI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingAddU8(a: UByte, b: UByte): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddU8(a.toInt(), b.toInt()) as Int).toUByte() else KmLinearMemoryArena.saturatingAddU8(a, b)
public actual fun kmSaturatingAddU16(a: UShort, b: UShort): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddU16(a.toInt(), b.toInt()) as Int).toUShort() else KmLinearMemoryArena.saturatingAddU16(a, b)
public actual fun kmSaturatingAddU32(a: UInt, b: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddU32(a.toInt(), b.toInt()) as Int).toUInt() else KmLinearMemoryArena.saturatingAddU32(a, b)
public actual fun kmSaturatingAddU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmSaturatingAddU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.saturatingAddU64(a, b)
    }
public actual fun kmSaturatingAddU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmSaturatingAddU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.saturatingAddU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingAddI8(a: Byte, b: Byte): Byte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddI8(a.toInt(), b.toInt()) as Int).toByte() else KmLinearMemoryArena.saturatingAddI8(a, b)
public actual fun kmSaturatingAddI16(a: Short, b: Short): Short =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddI16(a.toInt(), b.toInt()) as Int).toShort() else KmLinearMemoryArena.saturatingAddI16(a, b)
public actual fun kmSaturatingAddI32(a: Int, b: Int): Int =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingAddI32(a, b) as Int) else KmLinearMemoryArena.saturatingAddI32(a, b)
public actual fun kmSaturatingAddI64(a: Long, b: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmSaturatingAddI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int)
        packI64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.saturatingAddI64(a, b)
    }
public actual fun kmSaturatingAddI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmSaturatingAddI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        I128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.saturatingAddI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingSubU8(a: UByte, b: UByte): UByte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubU8(a.toInt(), b.toInt()) as Int).toUByte() else KmLinearMemoryArena.saturatingSubU8(a, b)
public actual fun kmSaturatingSubU16(a: UShort, b: UShort): UShort =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubU16(a.toInt(), b.toInt()) as Int).toUShort() else KmLinearMemoryArena.saturatingSubU16(a, b)
public actual fun kmSaturatingSubU32(a: UInt, b: UInt): UInt =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubU32(a.toInt(), b.toInt()) as Int).toUInt() else KmLinearMemoryArena.saturatingSubU32(a, b)
public actual fun kmSaturatingSubU64(a: ULong, b: ULong): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmSaturatingSubU64(uLo32(a), uHi32(a), uLo32(b), uHi32(b)) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.saturatingSubU64(a, b)
    }
public actual fun kmSaturatingSubU128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmSaturatingSubU128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.saturatingSubU128(aLo, aHi, bLo, bHi)
    }
public actual fun kmSaturatingSubI8(a: Byte, b: Byte): Byte =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubI8(a.toInt(), b.toInt()) as Int).toByte() else KmLinearMemoryArena.saturatingSubI8(a, b)
public actual fun kmSaturatingSubI16(a: Short, b: Short): Short =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubI16(a.toInt(), b.toInt()) as Int).toShort() else KmLinearMemoryArena.saturatingSubI16(a, b)
public actual fun kmSaturatingSubI32(a: Int, b: Int): Int =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSaturatingSubI32(a, b) as Int) else KmLinearMemoryArena.saturatingSubI32(a, b)
public actual fun kmSaturatingSubI64(a: Long, b: Long): Long =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmSaturatingSubI64(lo32(a), hi32(a), lo32(b), hi32(b)) as Int)
        packI64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.saturatingSubI64(a, b)
    }
public actual fun kmSaturatingSubI128(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmSaturatingSubI128(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        I128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.saturatingSubI128(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Add(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmU128Add(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.u128Add(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Sub(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmU128Sub(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.u128Sub(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Mul(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmU128Mul(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.u128Mul(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): U128DivRemOutcome =
    if (kmCoreNapiAddon != null) {
        val qLo0 = (kmCoreNapiAddon.kmU128DivRem(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        U128DivRemOutcome(packU64(qLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), packU64((kmCoreNapiAddon.kmOut4() as Int), (kmCoreNapiAddon.kmOut5() as Int)), packU64((kmCoreNapiAddon.kmOut6() as Int), (kmCoreNapiAddon.kmOut7() as Int)))
    } else {
        KmLinearMemoryArena.u128DivRem(aLo, aHi, bLo, bHi)
    }
public actual fun kmI128DivRem(aLo: ULong, aHi: ULong, bLo: ULong, bHi: ULong): I128DivRemOutcome =
    if (kmCoreNapiAddon != null) {
        val qLo0 = (kmCoreNapiAddon.kmI128DivRem(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), uLo32(bLo), uHi32(bLo), uLo32(bHi), uHi32(bHi)) as Int)
        I128DivRemOutcome(packU64(qLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)), packU64((kmCoreNapiAddon.kmOut4() as Int), (kmCoreNapiAddon.kmOut5() as Int)), packU64((kmCoreNapiAddon.kmOut6() as Int), (kmCoreNapiAddon.kmOut7() as Int)))
    } else {
        KmLinearMemoryArena.i128DivRem(aLo, aHi, bLo, bHi)
    }
public actual fun kmU128Shl(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmU128Shl(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt()) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.u128Shl(aLo, aHi, shift)
    }
public actual fun kmU128Shr(aLo: ULong, aHi: ULong, shift: UInt): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmU128Shr(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt()) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.u128Shr(aLo, aHi, shift)
    }
public actual fun kmI128Shr(aLo: ULong, aHi: ULong, shift: UInt): I128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmI128Shr(uLo32(aLo), uHi32(aLo), uLo32(aHi), uHi32(aHi), shift.toInt()) as Int)
        I128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.i128Shr(aLo, aHi, shift)
    }
public actual fun kmSinf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSinf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.sinf32(x)
public actual fun kmSinf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmSinf64(x) as Double) else KmLinearMemoryArena.sinf64(x)
public actual fun kmCosf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCosf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.cosf32(x)
public actual fun kmCosf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCosf64(x) as Double) else KmLinearMemoryArena.cosf64(x)
public actual fun kmExpf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmExpf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.expf32(x)
public actual fun kmExpf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmExpf64(x) as Double) else KmLinearMemoryArena.expf64(x)
public actual fun kmExp2f32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmExp2f32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.exp2f32(x)
public actual fun kmExp2f64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmExp2f64(x) as Double) else KmLinearMemoryArena.exp2f64(x)
public actual fun kmLogf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLogf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.logf32(x)
public actual fun kmLogf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLogf64(x) as Double) else KmLinearMemoryArena.logf64(x)
public actual fun kmLog2f32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLog2f32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.log2f32(x)
public actual fun kmLog2f64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLog2f64(x) as Double) else KmLinearMemoryArena.log2f64(x)
public actual fun kmLog10f32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLog10f32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.log10f32(x)
public actual fun kmLog10f64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmLog10f64(x) as Double) else KmLinearMemoryArena.log10f64(x)
public actual fun kmTruncf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmTruncf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.truncf32(x)
public actual fun kmTruncf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmTruncf64(x) as Double) else KmLinearMemoryArena.truncf64(x)
public actual fun kmRoundf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRoundf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.roundf32(x)
public actual fun kmRoundf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRoundf64(x) as Double) else KmLinearMemoryArena.roundf64(x)
public actual fun kmRoundTiesEvenf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRoundTiesEvenf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.roundTiesEvenf32(x)
public actual fun kmRoundTiesEvenf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmRoundTiesEvenf64(x) as Double) else KmLinearMemoryArena.roundTiesEvenf64(x)
public actual fun kmCeilf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCeilf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.ceilf32(x)
public actual fun kmCeilf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCeilf64(x) as Double) else KmLinearMemoryArena.ceilf64(x)
public actual fun kmFloorf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFloorf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.floorf32(x)
public actual fun kmFloorf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFloorf64(x) as Double) else KmLinearMemoryArena.floorf64(x)
public actual fun kmFabsf32(x: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFabsf32(x.toDouble()) as Double).toFloat() else KmLinearMemoryArena.fabsf32(x)
public actual fun kmFabsf64(x: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmFabsf64(x) as Double) else KmLinearMemoryArena.fabsf64(x)
public actual fun kmPowf32(a: Float, b: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmPowf32(a.toDouble(), b.toDouble()) as Double).toFloat() else KmLinearMemoryArena.powf32(a, b)
public actual fun kmPowf64(a: Double, b: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmPowf64(a, b) as Double) else KmLinearMemoryArena.powf64(a, b)
public actual fun kmPowif32(x: Float, exp: Int): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmPowif32(x.toDouble(), exp) as Double).toFloat() else KmLinearMemoryArena.powif32(x, exp)
public actual fun kmPowif64(x: Double, exp: Int): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmPowif64(x, exp) as Double) else KmLinearMemoryArena.powif64(x, exp)
public actual fun kmCopysignf32(a: Float, b: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCopysignf32(a.toDouble(), b.toDouble()) as Double).toFloat() else KmLinearMemoryArena.copysignf32(a, b)
public actual fun kmCopysignf64(a: Double, b: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmCopysignf64(a, b) as Double) else KmLinearMemoryArena.copysignf64(a, b)
public actual fun kmMinnumf32(a: Float, b: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmMinnumf32(a.toDouble(), b.toDouble()) as Double).toFloat() else KmLinearMemoryArena.minnumf32(a, b)
public actual fun kmMinnumf64(a: Double, b: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmMinnumf64(a, b) as Double) else KmLinearMemoryArena.minnumf64(a, b)
public actual fun kmMaxnumf32(a: Float, b: Float): Float =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmMaxnumf32(a.toDouble(), b.toDouble()) as Double).toFloat() else KmLinearMemoryArena.maxnumf32(a, b)
public actual fun kmMaxnumf64(a: Double, b: Double): Double =
    if (kmCoreNapiAddon != null) (kmCoreNapiAddon.kmMaxnumf64(a, b) as Double) else KmLinearMemoryArena.maxnumf64(a, b)
public actual fun kmFloatToU64Unchecked(v: Float): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmFloatToU64Unchecked(v.toDouble()) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.floatToU64Unchecked(v)
    }
public actual fun kmDoubleToU64Unchecked(v: Double): ULong =
    if (kmCoreNapiAddon != null) {
        val lo = (kmCoreNapiAddon.kmDoubleToU64Unchecked(v) as Int)
        packU64(lo, (kmCoreNapiAddon.kmOut1() as Int))
    } else {
        KmLinearMemoryArena.doubleToU64Unchecked(v)
    }
public actual fun kmFloatToU128Unchecked(v: Float): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmFloatToU128Unchecked(v.toDouble()) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.floatToU128Unchecked(v)
    }
public actual fun kmDoubleToU128Unchecked(v: Double): U128ValueOutcome =
    if (kmCoreNapiAddon != null) {
        val rLo0 = (kmCoreNapiAddon.kmDoubleToU128Unchecked(v) as Int)
        U128ValueOutcome(packU64(rLo0, (kmCoreNapiAddon.kmOut1() as Int)), packU64((kmCoreNapiAddon.kmOut2() as Int), (kmCoreNapiAddon.kmOut3() as Int)))
    } else {
        KmLinearMemoryArena.doubleToU128Unchecked(v)
    }

@JsName("FinalizationRegistry")
private external class KmJsFinalizationRegistry(callback: (Int) -> Unit) {
    fun register(target: Any, heldValue: Int, unregisterToken: Any)
    fun unregister(unregisterToken: Any): Boolean
}

private class KmJsCleanToken

private object KmJsCleanerRegistry {
    private var nextTokenId: Int = 1
    private val pendingCleanups = hashMapOf<Int, () -> Unit>()
    private val registry =
        KmJsFinalizationRegistry { tokenId ->
            val action = pendingCleanups.remove(tokenId)
            if (action != null) {
                action()
            }
        }

    fun register(owner: Any, cleanup: () -> Unit): KmResourceCleaner {
        val id = nextTokenId++
        val token = KmJsCleanToken()
        pendingCleanups[id] = cleanup
        registry.register(owner, id, token)
        return KmResourceCleaner().apply { attach(id, token) }
    }

    fun disarm(id: Int, token: KmJsCleanToken) {
        pendingCleanups.remove(id)
        registry.unregister(token)
    }

    fun clean(id: Int, token: KmJsCleanToken) {
        registry.unregister(token)
        val action = pendingCleanups.remove(id)
        if (action != null) {
            action()
        }
    }
}

public actual class KmResourceCleaner internal constructor() : KmForgettableResource {
    private var tokenId: Int = 0
    private var token: KmJsCleanToken? = null

    internal fun attach(id: Int, cleanToken: KmJsCleanToken) {
        tokenId = id
        token = cleanToken
    }

    public actual fun disarm() {
        val activeToken = token ?: return
        val activeId = tokenId
        token = null
        tokenId = 0
        KmJsCleanerRegistry.disarm(activeId, activeToken)
    }

    public actual fun cleanNow() {
        val activeToken = token ?: return
        val activeId = tokenId
        token = null
        tokenId = 0
        KmJsCleanerRegistry.clean(activeId, activeToken)
    }

    public actual override fun disarmCleaner(): Unit = disarm()

    public actual override fun close(): Unit = cleanNow()
}

public actual fun kmRegisterResourceCleaner(owner: Any, cleanup: () -> Unit): KmResourceCleaner =
    KmJsCleanerRegistry.register(owner, cleanup)
