@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

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
