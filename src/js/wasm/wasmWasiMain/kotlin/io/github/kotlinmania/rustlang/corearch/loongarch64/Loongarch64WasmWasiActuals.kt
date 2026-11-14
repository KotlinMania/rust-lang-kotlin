@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrcWBW")
private external fun wasiCorearchLoongarch64CrcWBW(a: Int, b: Int): Int

public actual fun crcWBW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrcWBW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrcWHW")
private external fun wasiCorearchLoongarch64CrcWHW(a: Int, b: Int): Int

public actual fun crcWHW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrcWHW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrcWWW")
private external fun wasiCorearchLoongarch64CrcWWW(a: Int, b: Int): Int

public actual fun crcWWW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrcWWW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrcWDW")
private external fun wasiCorearchLoongarch64CrcWDW(a: Long, b: Int): Int

public actual fun crcWDW(a: Long, b: Int): Int =
    wasiCorearchLoongarch64CrcWDW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrccWBW")
private external fun wasiCorearchLoongarch64CrccWBW(a: Int, b: Int): Int

public actual fun crccWBW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrccWBW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrccWHW")
private external fun wasiCorearchLoongarch64CrccWHW(a: Int, b: Int): Int

public actual fun crccWHW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrccWHW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrccWWW")
private external fun wasiCorearchLoongarch64CrccWWW(a: Int, b: Int): Int

public actual fun crccWWW(a: Int, b: Int): Int =
    wasiCorearchLoongarch64CrccWWW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64CrccWDW")
private external fun wasiCorearchLoongarch64CrccWDW(a: Long, b: Int): Int

public actual fun crccWDW(a: Long, b: Int): Int =
    wasiCorearchLoongarch64CrccWDW(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Cacop")
private external fun wasiCorearchLoongarch64Cacop(a: Long, b: Long, c: Long): Unit

public actual fun cacop(a: Long, b: Long, c: Long): Unit =
    wasiCorearchLoongarch64Cacop(a, b, c)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Csrrd")
private external fun wasiCorearchLoongarch64Csrrd(a: Int): Long

public actual fun csrrd(a: Int): Long =
    wasiCorearchLoongarch64Csrrd(a).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Csrwr")
private external fun wasiCorearchLoongarch64Csrwr(a: Long, b: Int): Long

public actual fun csrwr(a: Long, b: Int): Long =
    wasiCorearchLoongarch64Csrwr(a, b).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Csrxchg")
private external fun wasiCorearchLoongarch64Csrxchg(a: Long, b: Long, c: Int): Long

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    wasiCorearchLoongarch64Csrxchg(a, b, c).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64IocsrrdD")
private external fun wasiCorearchLoongarch64IocsrrdD(a: Int): Long

public actual fun iocsrrdD(a: Int): Long =
    wasiCorearchLoongarch64IocsrrdD(a).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64IocsrwrD")
private external fun wasiCorearchLoongarch64IocsrwrD(a: Long, b: Int): Unit

public actual fun iocsrwrD(a: Long, b: Int): Unit =
    wasiCorearchLoongarch64IocsrwrD(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Asrtle")
private external fun wasiCorearchLoongarch64Asrtle(a: Long, b: Long): Unit

public actual fun asrtle(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64Asrtle(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Asrtgt")
private external fun wasiCorearchLoongarch64Asrtgt(a: Long, b: Long): Unit

public actual fun asrtgt(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64Asrtgt(a, b)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Lddir")
private external fun wasiCorearchLoongarch64Lddir(a: Long, b: Long): Long

public actual fun lddir(a: Long, b: Long): Long =
    wasiCorearchLoongarch64Lddir(a, b).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch64Ldpte")
private external fun wasiCorearchLoongarch64Ldpte(a: Long, b: Long): Unit

public actual fun ldpte(a: Long, b: Long): Unit =
    wasiCorearchLoongarch64Ldpte(a, b)
