@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysPalItronAbiNapiAddon(): Boolean

private val sysPalItronAbiNapiAvailable: Boolean = initSysPalItronAbiNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiAcreTsk(p0)")
private external fun wasmStdSysPalItronAbiAcreTsk(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiGetTid(p0)")
private external fun wasmStdSysPalItronAbiGetTid(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiDlyTsk(p0)")
private external fun wasmStdSysPalItronAbiDlyTsk(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiTerTsk(p0)")
private external fun wasmStdSysPalItronAbiTerTsk(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiDelTsk(p0)")
private external fun wasmStdSysPalItronAbiDelTsk(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalItronAbiGetPri(p0, p1)")
private external fun wasmStdSysPalItronAbiGetPri(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiRotRdq(p0)")
private external fun wasmStdSysPalItronAbiRotRdq(p0: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiSlpTsk()")
private external fun wasmStdSysPalItronAbiSlpTsk(): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiTslpTsk(p0)")
private external fun wasmStdSysPalItronAbiTslpTsk(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiWupTsk(p0)")
private external fun wasmStdSysPalItronAbiWupTsk(p0: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiUnlCpu()")
private external fun wasmStdSysPalItronAbiUnlCpu(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiDisDsp()")
private external fun wasmStdSysPalItronAbiDisDsp(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiEnaDsp()")
private external fun wasmStdSysPalItronAbiEnaDsp(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiSnsDsp()")
private external fun wasmStdSysPalItronAbiSnsDsp(): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiGetTim(p0)")
private external fun wasmStdSysPalItronAbiGetTim(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiAcreFlg(p0)")
private external fun wasmStdSysPalItronAbiAcreFlg(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiDelFlg(p0)")
private external fun wasmStdSysPalItronAbiDelFlg(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalItronAbiSetFlg(p0, p1)")
private external fun wasmStdSysPalItronAbiSetFlg(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalItronAbiClrFlg(p0, p1)")
private external fun wasmStdSysPalItronAbiClrFlg(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalItronAbiWaiFlg(p0, p1, p2, p3)")
private external fun wasmStdSysPalItronAbiWaiFlg(p0: Int, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalItronAbiTwaiFlg(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalItronAbiTwaiFlg(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiAcreMtx(p0)")
private external fun wasmStdSysPalItronAbiAcreMtx(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiDelMtx(p0)")
private external fun wasmStdSysPalItronAbiDelMtx(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiLocMtx(p0)")
private external fun wasmStdSysPalItronAbiLocMtx(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiPlocMtx(p0)")
private external fun wasmStdSysPalItronAbiPlocMtx(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalItronAbiTlocMtx(p0, p1)")
private external fun wasmStdSysPalItronAbiTlocMtx(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalItronAbiUnlMtx(p0)")
private external fun wasmStdSysPalItronAbiUnlMtx(p0: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalItronAbiExdTsk()")
private external fun wasmStdSysPalItronAbiExdTsk(): Int

public actual fun acreTsk(pkCtsk: ConstPtr): ErId =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiAcreTsk(pkCtsk.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTid(pTskid: MutPtr): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiGetTid(pTskid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlyTsk(dlytim: Reltim): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiDlyTsk(dlytim.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiTerTsk(tskid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiDelTsk(tskid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getPri(tskid: ID, pTskpri: MutPtr): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiGetPri(tskid, pTskpri.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rotRdq(tskpri: Pri): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiRotRdq(tskpri) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun slpTsk(): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiSlpTsk() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tslpTsk(tmout: Tmo): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiTslpTsk(tmout.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wupTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiWupTsk(tskid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlCpu(): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiUnlCpu() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun disDsp(): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiDisDsp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun enaDsp(): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiEnaDsp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snsDsp(): BoolT =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiSnsDsp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTim(pSystim: MutPtr): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiGetTim(pSystim.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acreFlg(pkCflg: ConstPtr): ErId =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiAcreFlg(pkCflg.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delFlg(flgid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiDelFlg(flgid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFlg(flgid: ID, setptn: Flgptn): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiSetFlg(flgid, setptn.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clrFlg(flgid: ID, clrptn: Flgptn): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiClrFlg(flgid, clrptn.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiWaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiTwaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress.toString(), tmout.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acreMtx(pkCmtx: ConstPtr): ErId =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiAcreMtx(pkCmtx.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delMtx(tskid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiDelMtx(tskid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun locMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiLocMtx(mtxid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun plocMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiPlocMtx(mtxid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlocMtx(mtxid: ID, tmout: Tmo): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiTlocMtx(mtxid, tmout.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiUnlMtx(mtxid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exdTsk(): ER =
    if (sysPalItronAbiNapiAvailable) wasmStdSysPalItronAbiExdTsk() else throw UnsupportedOperationException("Native FFI addon is not available")
