@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrcWBW")
private external fun wasiRustlangCorearchLoongarch64CrcWBW(a: Int, b: Int): Int

public actual fun crcWBW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrcWBW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrcWHW")
private external fun wasiRustlangCorearchLoongarch64CrcWHW(a: Int, b: Int): Int

public actual fun crcWHW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrcWHW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrcWWW")
private external fun wasiRustlangCorearchLoongarch64CrcWWW(a: Int, b: Int): Int

public actual fun crcWWW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrcWWW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrcWDW")
private external fun wasiRustlangCorearchLoongarch64CrcWDW(a: Long, b: Int): Int

public actual fun crcWDW(a: Long, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrcWDW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrccWBW")
private external fun wasiRustlangCorearchLoongarch64CrccWBW(a: Int, b: Int): Int

public actual fun crccWBW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrccWBW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrccWHW")
private external fun wasiRustlangCorearchLoongarch64CrccWHW(a: Int, b: Int): Int

public actual fun crccWHW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrccWHW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrccWWW")
private external fun wasiRustlangCorearchLoongarch64CrccWWW(a: Int, b: Int): Int

public actual fun crccWWW(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrccWWW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64CrccWDW")
private external fun wasiRustlangCorearchLoongarch64CrccWDW(a: Long, b: Int): Int

public actual fun crccWDW(a: Long, b: Int): Int =
    wasiRustlangCorearchLoongarch64CrccWDW(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Cacop")
private external fun wasiRustlangCorearchLoongarch64Cacop(a: Long, b: Long, c: Long): Unit

public actual fun cacop(a: Long, b: Long, c: Long): Unit =
    wasiRustlangCorearchLoongarch64Cacop(a, b, c)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Csrrd")
private external fun wasiRustlangCorearchLoongarch64Csrrd(a: Int): Long

public actual fun csrrd(a: Int): Long =
    wasiRustlangCorearchLoongarch64Csrrd(a).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Csrwr")
private external fun wasiRustlangCorearchLoongarch64Csrwr(a: Long, b: Int): Long

public actual fun csrwr(a: Long, b: Int): Long =
    wasiRustlangCorearchLoongarch64Csrwr(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Csrxchg")
private external fun wasiRustlangCorearchLoongarch64Csrxchg(a: Long, b: Long, c: Int): Long

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    wasiRustlangCorearchLoongarch64Csrxchg(a, b, c).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64IocsrrdD")
private external fun wasiRustlangCorearchLoongarch64IocsrrdD(a: Int): Long

public actual fun iocsrrdD(a: Int): Long =
    wasiRustlangCorearchLoongarch64IocsrrdD(a).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64IocsrwrD")
private external fun wasiRustlangCorearchLoongarch64IocsrwrD(a: Long, b: Int): Unit

public actual fun iocsrwrD(a: Long, b: Int): Unit =
    wasiRustlangCorearchLoongarch64IocsrwrD(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Asrtle")
private external fun wasiRustlangCorearchLoongarch64Asrtle(a: Long, b: Long): Unit

public actual fun asrtle(a: Long, b: Long): Unit =
    wasiRustlangCorearchLoongarch64Asrtle(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Asrtgt")
private external fun wasiRustlangCorearchLoongarch64Asrtgt(a: Long, b: Long): Unit

public actual fun asrtgt(a: Long, b: Long): Unit =
    wasiRustlangCorearchLoongarch64Asrtgt(a, b)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Lddir")
private external fun wasiRustlangCorearchLoongarch64Lddir(a: Long, b: Long): Long

public actual fun lddir(a: Long, b: Long): Long =
    wasiRustlangCorearchLoongarch64Lddir(a, b).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch64Ldpte")
private external fun wasiRustlangCorearchLoongarch64Ldpte(a: Long, b: Long): Unit

public actual fun ldpte(a: Long, b: Long): Unit =
    wasiRustlangCorearchLoongarch64Ldpte(a, b)
