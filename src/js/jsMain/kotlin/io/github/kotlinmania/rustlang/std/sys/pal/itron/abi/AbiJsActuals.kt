@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val sysPalItronAbiNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/std-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = null; return null; })()")

public actual fun acreTsk(pkCtsk: ConstPtr): ErId =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiAcreTsk(pkCtsk.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTid(pTskid: MutPtr): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiGetTid(pTskid.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlyTsk(dlytim: Reltim): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiDlyTsk(dlytim.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiTerTsk(tskid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiDelTsk(tskid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getPri(tskid: ID, pTskpri: MutPtr): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiGetPri(tskid, pTskpri.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rotRdq(tskpri: Pri): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiRotRdq(tskpri) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun slpTsk(): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiSlpTsk() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tslpTsk(tmout: Tmo): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiTslpTsk(tmout.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wupTsk(tskid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiWupTsk(tskid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlCpu(): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiUnlCpu() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun disDsp(): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiDisDsp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun enaDsp(): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiEnaDsp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snsDsp(): BoolT =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiSnsDsp() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTim(pSystim: MutPtr): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiGetTim(pSystim.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acreFlg(pkCflg: ConstPtr): ErId =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiAcreFlg(pkCflg.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delFlg(flgid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiDelFlg(flgid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFlg(flgid: ID, setptn: Flgptn): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiSetFlg(flgid, setptn.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clrFlg(flgid: ID, clrptn: Flgptn): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiClrFlg(flgid, clrptn.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiWaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiTwaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress.toString(), tmout.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acreMtx(pkCmtx: ConstPtr): ErId =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiAcreMtx(pkCmtx.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun delMtx(tskid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiDelMtx(tskid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun locMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiLocMtx(mtxid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun plocMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiPlocMtx(mtxid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlocMtx(mtxid: ID, tmout: Tmo): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiTlocMtx(mtxid, tmout.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlMtx(mtxid: ID): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiUnlMtx(mtxid) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exdTsk(): ER =
    if (sysPalItronAbiNapiAddon != null) (sysPalItronAbiNapiAddon.stdSysPalItronAbiExdTsk() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
