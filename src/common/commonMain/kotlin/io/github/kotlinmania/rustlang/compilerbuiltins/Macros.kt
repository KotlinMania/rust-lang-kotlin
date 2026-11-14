// port-lint: source macros.rs
package io.github.kotlinmania.rustlang.compilerbuiltins

// Macros shared throughout the compiler-builtins implementation

private const val INTRINSICS_TEMPLATE_1: String = "intrinsics {\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics {\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_2: String = "intrinsics {\$()* public unsafe extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics {\$()* public unsafe extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_3: String = "intrinsics {\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_4: String = "public\$(unsafe\$(\$empty)?)? extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {unsafe extern \$abi {fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)?; } unsafe {\$name(\$(\$argname),*)}} intrinsics {\$()* public\$(unsafe\$(\$empty)?)? extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_5: String = "intrinsics {\$(\$t)*}"

private const val INTRINSICS_TEMPLATE_6: String = "intrinsics {\$(\$t)*}"

private const val INTRINSICS_TEMPLATE_7: String = "\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*} mod \$name {\$()* extern \$abi fun \$name(\$(\$argname: u16),*)\$(-> \$ret)? {superRef. \$name(\$(f16.fromBits(\$argname)),*)}} intrinsics {\$()?\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_8: String = "\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*} mod \$name {\$()* extern \$abi fun \$name(\$(\$argname: \$ty),*)-> u16 {superRef. \$name(\$(\$argname),*).toBits()}} intrinsics {\$()?\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_9: String = "\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*} mod \$name {\$()* extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {superRef. \$name(\$(\$argname),*)}} mod \$alias {\$()* extern \"aapcs\" fun \$alias(\$(\$argname: \$ty),*)\$(-> \$ret)? {superRef. \$name(\$(\$argname),*)}} intrinsics {\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_10: String = "intrinsics {\$()* public extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics {\$()* public extern \$abi fun \$ppcName(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_11: String = "\$()* public unsafe extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*} mod \$name {\$()* unsafe extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {superRef. \$name(\$(\$argname),*)}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_12: String = "public mod \$name {\$()* public unsafe extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*}} intrinsics(\$(\$rest)*);"

private const val INTRINSICS_TEMPLATE_13: String = "\$()* public\$(unsafe\$(\$empty)?)? extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(\$body)*} mod \$name {\$()*\$(unsafe\$(\$empty)?)? extern \$abi fun \$name(\$(\$argname: \$ty),*)\$(-> \$ret)? {\$(unsafe\$(\$empty)?)? {superRef. \$name(\$(\$argname),*)}}} intrinsics(\$(\$rest)*);"

/**
 * The "main macro" used for defining intrinsics.
 *
 * The compiler-builtins library is super platform-specific with tons of crazy
 * little tweaks for various platforms. As a result it *could* involve a lot of
 * @cfg and macro soup, but the intention is that this macro alleviates a lot
 * of that complexity. Ideally this macro has all the weird ABI things
 * platforms need and elsewhere in this library it just looks like normal Rust
 * code.
 *
 * All intrinsics functions are marked with @linkage= "weak" when
 * `not(windows) and not(targetVendor = "apple")`.
 * `weak` linkage attribute is used so that these functions can be replaced
 * by another implementation at link time. This is particularly useful for mixed
 * Rust/C++ binaries that want to use the C++ intrinsics, otherwise linking against
 * the Rust stdlib will replace those from the compiler-rt library.
 *
 * This macro is structured to be invoked with a bunch of functions that looks
 * like:
 * ```ignore
 *     intrinsics! {
 *         pub extern "C" fn foo(a: i32) -> u32 {
 *             // ...
 *         }
 *
 *         @nonstandardAttribute
 *         pub extern "C" fn bar(a: i32) -> u32 {
 *             // ...
 *         }
 *     }
 * ```
 *
 * Each function is defined in a manner that looks like a normal Rust function.
 * The macro then accepts a few nonstandard attributes that can decorate
 * various functions. Each of the attributes is documented below with what it
 * can do, and each of them slightly tweaks how further expansion happens.
 *
 * A quick overview of attributes supported right now are:
 *
 * * `maybeUseOptimizedCShim` - indicates that the Rust implementation is
 *   ignored if an optimized C version was compiled.
 * * `armAeabiAlias` - handles the "aliasing" of various intrinsics on ARM
 *   their otherwise typical names to other prefixed ones.
 * * `ppcName` - changes the name of the symbol on PowerPC platforms without
 *   changing any other behavior. This is mostly for `f128`, which is `tf` on
 *   most platforms but `kf` on PowerPC.
 */

internal fun intrinsics(e: String = "", attr: String = "", attrs: String = "", abi: String = "", name: String = "", argname: String = "", ty: String = "", ret: String = "", body: String = "", rest: String = "", empty: String = "", alias: String = "", t: String = "", ppcName: String = "", clause: String = ""): String =
    when {
        clause == "# cfgAttr # public extern - >" && e.isNotEmpty() && attr.isNotEmpty() && attrs.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# cfgAttr # public unsafe extern - >" && e.isNotEmpty() && attr.isNotEmpty() && attrs.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# cfg # public extern - >" && e.isNotEmpty() && attrs.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# maybeUseOptimizedCShim # public unsafe @ extern - >" && attr.isNotEmpty() && empty.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$empty" to empty, "\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# appleF16ArgAbi armAeabiAlias = # public extern - >" && alias.isNotEmpty() && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_7) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# appleF16RetAbi armAeabiAlias = # public extern - >" && alias.isNotEmpty() && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_8) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# armAeabiAlias = # public extern - >" && alias.isNotEmpty() && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$alias" to alias, "\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_9) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# ppcName = # public extern - >" && ppcName.isNotEmpty() && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$ppcName" to ppcName, "\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_10) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# public unsafe @ extern - >" && attr.isNotEmpty() && empty.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$empty" to empty, "\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_13) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# memBuiltin # public unsafe extern - >" && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_11) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# unsafe naked # public unsafe extern - >" && attr.isNotEmpty() && abi.isNotEmpty() && name.isNotEmpty() && argname.isNotEmpty() && ty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$abi" to abi, "\$name" to name, "\$argname" to argname, "\$ty" to ty, "\$ret" to ret, "\$body" to body, "\$rest" to rest).fold(INTRINSICS_TEMPLATE_12) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# appleF16ArgAbi # armAeabiAlias =" && alias.isNotEmpty() && t.isNotEmpty() -> INTRINSICS_TEMPLATE_5.replace("\$t", t)
        clause == "# appleF16RetAbi # armAeabiAlias =" && alias.isNotEmpty() && t.isNotEmpty() -> INTRINSICS_TEMPLATE_6.replace("\$t", t)
        else -> ""
    }
