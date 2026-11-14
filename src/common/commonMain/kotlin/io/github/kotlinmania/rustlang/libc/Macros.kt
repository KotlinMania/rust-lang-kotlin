// port-lint: source macros.rs
package io.github.kotlinmania.rustlang.libc

private const val CFG_IF_TEMPLATE: String = "cfgIf {@ items(); \$(((\$(\$meta),*)(\$(\$it)*)), )*(()(\$(\$it2)*)), }"

private const val CFG_IF_TEMPLATE_1: String = "cfgIf {@ items(); ((\$(\$iMet),*)(\$(\$iIt)*)), \$(((\$(\$eMet),*)(\$(\$eIt)*)), )*(()()), }"

private const val CFG_IF_TEMPLATE_3: String = "cfgIf {@ apply cfg(all(\$(\$m, )* not(any(\$(\$not),*)))), \$(\$it)*} cfgIf {@ items(\$(\$not, )*\$(\$m, )*); \$(\$rest)*}"

private const val CFG_IF_TEMPLATE_4: String = "\$(\$it)*"

private const val PRELUDE_TEMPLATE: String = "mod types; mod prelude {public(crate) import core.clone.Clone; public(crate) import core.cmp. {Eq, PartialEq, }; public(crate) import core.default.Default; public(crate) import core.iter.Iterator; public(crate) import core.marker. {Copy, Send, Sync, }; public(crate) import core.option.Option. {this, None, Some, }; public(crate) import core.prelude.v1.derive; public(crate) import core. {assert, cfg, compileError, debugAssert, fmt, hash, iter, mem, panic, ptr, unsupportedBranch, }; public(crate) import fmt.Debug; public(crate) import mem. {alignOf, alignOfVal, sizeOf, sizeOfVal, }; public(crate) import crate.types.u32CastIoctl; public(crate) import crate.types. {cstr, replaceArrayItems, u16CastShort, u32CastInt, u32CastLong, u8SliceCastCharSlice, ulongCastInt, ulongCastUint, CEnumRepr, Padding, }; public(crate) import crate. {cChar, cDouble, cFloat, cInt, cLong, cLonglong, cShort, cUchar, cUint, cUlong, cUlonglong, cUshort, cVoid, intptrT, sizeT, ssizeT, uintptrT, }; }"

private const val S_TEMPLATE: String = "\$(s(it:\$()* \$pub \$t \$i {\$(\$field)*}); )*"

private const val S_TEMPLATE_1: String = "compileError(\"unions cannot derive extra traits, use s_no_extra_traits instead\");"

private const val S_TEMPLATE_2: String = "\$()* \$pub class \$i {\$(\$field)*}"

private const val S_PAREN_TEMPLATE: String = "\$(\$()* \$pub class \$i(\$(\$field)*); )*"

private const val S_NO_EXTRA_TRAITS_TEMPLATE: String = "\$(sNoExtraTraits(it:\$(# \$attr)* \$pub \$t \$i {\$(\$field)*}); )*"

private const val S_NO_EXTRA_TRAITS_TEMPLATE_1: String = "unionWithDebug {\$(# \$attr)* \$pub union \$i {\$(\$field)*}}"

private const val S_NO_EXTRA_TRAITS_TEMPLATE_2: String = "\$(# \$attr)* \$pub class \$i {\$(\$field)*}"

private const val S2_TEMPLATE: String = "\$(s2(it:\$(# \$attr)* \$pub \$t \$i {\$(\$field)*}); )*"

private const val S2_TEMPLATE_1: String = "compileError(\"unions cannot derive extra traits, use `s_no_extra_traits2` \\\n             instead\");"

private const val S2_TEMPLATE_2: String = "customStruct {attrs: {}\$(# \$attr)* \$pub class \$i {\$(\$field)*}}"

private const val S_NO_EXTRA_TRAITS2_TEMPLATE: String = "\$(sNoExtraTraits2(it:\$(# \$attr)* \$pub \$t \$i {\$(\$field)*}); )*"

private const val S_NO_EXTRA_TRAITS2_TEMPLATE_1: String = "unionWithDebug {\$(# \$attr)* \$pub union \$i {\$(\$field)*}}"

private const val S_NO_EXTRA_TRAITS2_TEMPLATE_2: String = "customStruct {attrs: {}\$(# \$attr)* \$pub class \$i {\$(\$field)*}}"

private const val UNION_WITH_DEBUG_TEMPLATE: String = "unionWithDebug {@ splitAttrs, cfgAttrs: {}, otherAttrs: {}, remainingAttrs: {\$(# \$attr)*}, foundDefaultViaUnsafeZeroed: falseVal, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val UNION_WITH_DEBUG_TEMPLATE_1: String = "unionWithDebug {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, remainingAttrs: {\$(\$tail)*}, foundDefaultViaUnsafeZeroed: \$foundDefault, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val UNION_WITH_DEBUG_TEMPLATE_2: String = "unionWithDebug {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, remainingAttrs: {\$(\$tail)*}, foundDefaultViaUnsafeZeroed: trueVal, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val UNION_WITH_DEBUG_TEMPLATE_3: String = "unionWithDebug {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*# \$other}, remainingAttrs: {\$(\$tail)*}, foundDefaultViaUnsafeZeroed: \$foundDefault, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val UNION_WITH_DEBUG_TEMPLATE_4: String = "\$(\$otherAttrs)*\$(\$cfgAttrs)* \$vis union \$name {\$(\$body)*}\$(\$cfgAttrs)* class.core.fmt.Debug forLoop \$name {fun fmt(& this, f:& var.core.fmt.Formatter<>)->.core.fmt.Result {f.debugStruct(.core.stringify(\$name)).finishNonExhaustive()}} emitUnionDefaultViaUnsafeZeroed {foundDefaultViaUnsafeZeroed: \$foundDefault, cfgAttrs: {\$(\$cfgAttrs)*}, name: \$name, }"

private const val EMIT_UNION_DEFAULT_VIA_UNSAFE_ZEROED_TEMPLATE_1: String = "\$(\$cfgAttrs)* class.core.default.Default forLoop \$name {fun default()-> Self {unsafe {.core.mem.zeroed()}}}"

private const val CUSTOM_STRUCT_TEMPLATE: String = "customStruct {@ splitAttrs, cfgAttrs: {}, otherAttrs: {}, remainingAttrs: {\$(\$attrs)*\$(# \$attr)*}, foundExhaustiveAttr: falseVal, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_1: String = "customStruct {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, remainingAttrs: {\$(\$tail)*}, foundExhaustiveAttr: \$foundExhaustive, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_2: String = "customStruct {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, remainingAttrs: {\$(\$tail)*}, foundExhaustiveAttr: trueVal, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_3: String = "customStruct {@ splitAttrs, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*# \$other}, remainingAttrs: {\$(\$tail)*}, foundExhaustiveAttr: \$foundExhaustive, vis: \$vis, name: \$name, body: {\$(\$body)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_4: String = "customStruct {@ class, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, foundExhaustiveAttr: \$foundExhaustive, vis: \$vis, name: \$name, processedFields: {}, processedFieldDefaults: {}, remainingFields: {\$(\$body)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_5: String = "customStruct {@ class, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, foundExhaustiveAttr: \$foundExhaustive, vis: \$vis, name: \$name, processedFields: {\$(\$processedFields)*\$()* \$fvis \$fname: \$fty, }, processedFieldDefaults: {\$(\$processedFieldDefaults)*\$()* \$fname: \$default, }, remainingFields: {\$(\$tail)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_6: String = "customStruct {@ class, cfgAttrs: {\$(\$cfgAttrs)*}, otherAttrs: {\$(\$otherAttrs)*}, foundExhaustiveAttr: \$foundExhaustive, vis: \$vis, name: \$name, processedFields: {\$(\$processedFields)*\$()* \$fvis \$fname: \$fty, }, processedFieldDefaults: {\$(\$processedFieldDefaults)*\$()* \$fname:.core.default.Default.default(), }, remainingFields: {\$(\$tail)*}, }"

private const val CUSTOM_STRUCT_TEMPLATE_7: String = "emitStructDefinition {foundExhaustiveAttr: \$foundExhaustive, body: {\$(\$otherAttrs)*\$(\$cfgAttrs)* \$vis \$name {\$(\$processedFields)*}}}\$(\$cfgAttrs)* class.core.default.Default forLoop \$name {fun default()-> Self {emitStructDefaultBody {foundExhaustiveAttr: \$foundExhaustive, body: {\$(\$processedFieldDefaults)*}}}}"

private const val EMIT_STRUCT_DEFINITION_TEMPLATE: String = "\$()* \$vis class \$name {\$(\$field)* nonExhaustive:()}"

private const val EMIT_STRUCT_DEFINITION_TEMPLATE_1: String = "\$()* \$vis class \$name {\$(\$field)*}"

private const val EMIT_STRUCT_DEFAULT_BODY_TEMPLATE: String = "Self {\$(\$fieldDefault)* nonExhaustive:()}"

private const val EMIT_STRUCT_DEFAULT_BODY_TEMPLATE_1: String = "Self {\$(\$fieldDefault)*}"

private const val EXTERN_TY_TEMPLATE: String = "\$(\$()* \$vis class \$i {data:(), marker:.core.marker.PhantomData<(* var u8, .core.marker.PhantomPinned)>, })*"

private const val E_TEMPLATE: String = "\$(\$()* public sealed class \$i {\$(\$field)*})*"

private const val C_ENUM_TEMPLATE: String = "\$(cEnum(@ single; \$()? \$vis sealed class\$(\$tyName)?\$(# \$anon)? {\$(\$()* \$fieldVis \$variant\$(= \$value)?, )+}); )+"

private const val C_ENUM_TEMPLATE_1: String = "\$vis type \$tyName= cEnum(@ ty\$(\$repr)?); cEnum {@ variant; ty: \$tyName; default: 0; variants:[\$(\$()* \$fieldVis \$variant\$(= \$value)?, )+]}"

private const val C_ENUM_TEMPLATE_2: String = "cEnum {@ variant; ty: cEnum(@ ty\$(\$repr)?); default: 0; variants:[\$(\$()* \$fieldVis \$variant\$(= \$value)?, )+]}"

private const val C_ENUM_TEMPLATE_4: String = "\$()* \$fieldVis const \$variant: \$tyName= {val r= \$defaultVal; \$(val r= \$value; )? r}; cEnum {@ variant; ty: \$tyName; default: \$variant+ 1; variants:[\$(\$tail)*]}"

private const val C_ENUM_TEMPLATE_5: String = "\$repr"

private const val C_ENUM_TEMPLATE_6: String = "prelude.CEnumRepr"

private const val F_TEMPLATE: String = "\$(f {@ single\$()* public\$(const\$(\$constDummy)?)?\$(unsafe\$(\$unsafeDummy)?)?\$(safe\$(\$safeDummy)?)? fun \$i(\$(\$arg: \$argty),*)-> \$ret \$body})+"

private const val F_TEMPLATE_1: String = "\$()* public\$(const\$(\$constDummy)?)? unsafe extern \"C\" fun \$i(\$(\$arg: \$argty),*)-> \$ret \$body"

private const val F_TEMPLATE_2: String = "\$()* public\$(const\$(\$constDummy)?)? extern \"C\" fun \$i(\$(\$arg: \$argty),*)-> \$ret \$body"

private const val F_TEMPLATE_3: String = "compileError(\"either `safe` or `unsafe` must be specified\");"

private const val DEPRECATED_MACH_TEMPLATE: String = "public const \$id: \$ty= \$expr;"

private const val DEPRECATED_MACH_TEMPLATE_1: String = "public type \$id= \$ty;"

private const val OFFSET_OF_TEMPLATE: String = "{val \$ty {\$field: unused, ..}; val data= core.mem.MaybeUninit.< \$ty>.uninit(); val ptr= data.asPtr(); val fptr= unsafe {core.ptr.addrOf((* ptr). \$field)}; val off=(fptr asTarget usize).checkedSub(ptr asTarget usize).unwrap(); core.assert(off<= core.mem.sizeOf.< \$ty>()); off}"

/**
 * A macro for defining @cfg if-else statements.
 *
 * This is similar to the `if / elif` C preprocessor macro by allowing definition of a cascade of
 * `@cfg` cases, emitting the implementation which matches first.
 *
 * This allows you to conveniently provide a long list @cfg'd blocks of code without having to
 * rewrite each clause multiple times.
 */

internal fun cfgIf(meta: String = "", it: String = "", it2: String = "", iMet: String = "", iIt: String = "", eMet: String = "", eIt: String = "", not: String = "", m: String = "", rest: String = "", clause: String = ""): String =
    when {
        clause == "ifBranch # cfg elseBranch ifBranch # cfg" && iMet.isNotEmpty() && iIt.isNotEmpty() && eMet.isNotEmpty() && eIt.isNotEmpty() -> arrayOf("\$iMet" to iMet, "\$iIt" to iIt, "\$eMet" to eMet, "\$eIt" to eIt).fold(CFG_IF_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@items" && not.isNotEmpty() && m.isNotEmpty() && it.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$not" to not, "\$m" to m, "\$it" to it, "\$rest" to rest).fold(CFG_IF_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "ifBranch # cfg elseBranch" && meta.isNotEmpty() && it.isNotEmpty() && it2.isNotEmpty() -> arrayOf("\$meta" to meta, "\$it" to it, "\$it2" to it2).fold(CFG_IF_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@apply" && m.isNotEmpty() && it.isNotEmpty() -> CFG_IF_TEMPLATE_4.replace("\$it", it)
        clause == "@items" && not.isNotEmpty() -> ""
        else -> ""
    }

/**
 * Create an internal crate prelude with `core` reexports and common types.
 */

internal fun prelude(): String =
    PRELUDE_TEMPLATE

/**
 * Implement `Clone`, `Copy`, and `Debug` for one or more structs, as well as `PartialEq`, `Eq`,
 * and `Hash` if the `extraTraits` feature is enabled.
 *
 * Also mark the type with `repr(C)`.
 *
 * Use [sNoExtraTraits] for structs where the `extraTraits` feature does not make sense, and
 * for unions.
 */

internal fun s(attr: String = "", pub: String = "", t: String = "", i: String = "", field: String = "", clause: String = ""): String =
    when {
        clause == "#" && attr.isNotEmpty() && pub.isNotEmpty() && t.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$pub" to pub, "\$t" to t, "\$i" to i, "\$field" to field).fold(S_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # union" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> S_TEMPLATE_1
        clause == "it # class" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Implement `Clone`, `Copy`, and `Debug` for a tuple struct, as well as `PartialEq`, `Eq`, and
 * `Hash` if the `extraTraits` feature is enabled.
 *
 * Unlike `s`, this does *not* mark the type with `repr(C)`. Users should provide their own
 * `repr` attribute via `$ attr` as necessary.
 */

internal fun sParen(attr: String = "", pub: String = "", i: String = "", field: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_PAREN_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_PAREN_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

/**
 * Implement `Clone`, `Copy`, and `Debug` for one or more structs/unions, but exclude `PartialEq`,
 * `Eq`, and `Hash`.
 *
 * Also mark the type with `repr(C)`.
 *
 * Most structs will prefer to use [s].
 */

internal fun sNoExtraTraits(attr: String = "", pub: String = "", t: String = "", i: String = "", field: String = "", clause: String = ""): String =
    when {
        clause == "#" && attr.isNotEmpty() && pub.isNotEmpty() && t.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$t" to t, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # union" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # class" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Like [s], but (1) generates a `Default` impl for every struct in the block, and (2) adds a
 * private field to the struct to replicate the effects of the `nonExhaustive` attribute while
 * rust-lang/rust#132699 gets sorted out.
 *
 * To opt out of having the private field added, annotate the struct with an `exhaustive`
 * attribute, as in:
 *
 * ```ignore
 * s2! {
 *     @exhaustive
 *     struct Something {
 *         ...
 *     }
 * }
 * ```
 *
 * See [customStruct] for details.
 */

internal fun s2(attr: String = "", pub: String = "", t: String = "", i: String = "", field: String = "", clause: String = ""): String =
    when {
        clause == "#" && attr.isNotEmpty() && pub.isNotEmpty() && t.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$t" to t, "\$i" to i, "\$field" to field).fold(S2_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # union" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> S2_TEMPLATE_1
        clause == "it # class" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$i" to i, "\$field" to field).fold(S2_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Like [sNoExtraTraits], but (1) generates a `Default` impl for every struct in the block,
 * and (2) adds a private field to replicate the effects of the built-in `nonExhaustive` attribute
 * while rust-lang/rust#132699 gets sorted out.
 *
 * Unions are emitted just like `sNoExtraTraits` does, with no `Default`.  A struct field of
 * union type supplies its own default via `@customDefault(...)`.
 *
 * To opt out of having the private field added, annotate the struct with an `exhaustive`
 * attribute, as in:
 *
 * ```ignore
 * sNoExtraTraits2 {
 *     @exhaustive
 *     struct Something {
 *         ...
 *     }
 * }
 * ```
 */

internal fun sNoExtraTraits2(attr: String = "", pub: String = "", t: String = "", i: String = "", field: String = "", clause: String = ""): String =
    when {
        clause == "#" && attr.isNotEmpty() && pub.isNotEmpty() && t.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$t" to t, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS2_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # union" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS2_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "it # class" && attr.isNotEmpty() && pub.isNotEmpty() && i.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$attr" to attr, "\$pub" to pub, "\$i" to i, "\$field" to field).fold(S_NO_EXTRA_TRAITS2_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Emit a union plus its `Debug` impl.
 *
 * Unions can't derive `Debug`, so it is written out here. Attributes are split like
 * [customStruct] does. Everything goes on the union, but only the `cfg`s are repeated on the
 * impl, otherwise a union that is configured out leaves an impl behind.
 */

internal fun unionWithDebug(attr: String = "", vis: String = "", name: String = "", body: String = "", cfgAttrs: String = "", otherAttrs: String = "", cfg: String = "", tail: String = "", foundDefault: String = "", other: String = "", clause: String = ""): String =
    when {
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # cfg foundDefaultViaUnsafeZeroed vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && cfg.isNotEmpty() && tail.isNotEmpty() && foundDefault.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$cfg" to cfg, "\$tail" to tail, "\$foundDefault" to foundDefault, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(UNION_WITH_DEBUG_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # foundDefaultViaUnsafeZeroed vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && other.isNotEmpty() && tail.isNotEmpty() && foundDefault.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$other" to other, "\$tail" to tail, "\$foundDefault" to foundDefault, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(UNION_WITH_DEBUG_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # unsafe unionDefaultViaZeroed foundDefaultViaUnsafeZeroed vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && tail.isNotEmpty() && foundDefault.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$tail" to tail, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(UNION_WITH_DEBUG_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs foundDefaultViaUnsafeZeroed vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && foundDefault.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$foundDefault" to foundDefault, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(UNION_WITH_DEBUG_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# union" && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$attr" to attr, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(UNION_WITH_DEBUG_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Add a `Default` impl that is just `mem.zeroed`.
 */

internal fun emitUnionDefaultViaUnsafeZeroed(cfgAttrs: String = "", name: String = "", clause: String = ""): String =
    when {
        clause == "foundDefaultViaUnsafeZeroed falseVal cfgAttrs name" && cfgAttrs.isNotEmpty() && name.isNotEmpty() -> ""
        clause == "foundDefaultViaUnsafeZeroed trueVal cfgAttrs name" && cfgAttrs.isNotEmpty() && name.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$name" to name).fold(EMIT_UNION_DEFAULT_VIA_UNSAFE_ZEROED_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Emit a struct with the given derive attributes plus a generated `Default` impl. Ensure that the
 * record has an additional private field added to replicate `@nonExhaustive`, unless it is
 * annotated with `@exhaustive`.
 *
 * Fields default to `Default.default()`. A field whose default can't be derived must carry
 * `@customDefault(EXPR)` as its *first* attribute, and `EXPR` is used instead.
 *
 * This works by scanning each field for `@customDefault` attributes. If one exists, the
 * attribute's contents are added to `processedFieldDefaults` and will be used in the expansion
 * for `Default`. If it does not exist, `Default.default()` is used instead. In either case, the
 * field is added to `processedFields` with `@customDefault` stripped if necessary, and
 * `customStruct` is invoked again with the remaining fields.
 *
 * Attributes are split into `cfgAttrs` and `otherAttrs` before the fields are scanned. Both go
 * on the struct, but only the `cfg`s are repeated on the `Default` impl. A `cfg` decides whether
 * the type exists at all, so without it a configured-out struct leaves an impl behind referring to
 * a type that isn't there.
 *
 * Out of `otherAttrs`, we scan for `@exhaustive`. If found, we remove it but take into account
 * that the record should be expanded _without_ an additional private field. The scan for both
 * `cfg` attributes and the (non-existent) `exhaustive` attribute is done in one linear pass.
 */

internal fun customStruct(attrs: String = "", attr: String = "", vis: String = "", name: String = "", body: String = "", cfgAttrs: String = "", otherAttrs: String = "", cfg: String = "", tail: String = "", foundExhaustive: String = "", unused: String = "", other: String = "", processedFields: String = "", processedFieldDefaults: String = "", default: String = "", fattr: String = "", fvis: String = "", fname: String = "", fty: String = "", clause: String = ""): String =
    when {
        clause == "@class cfgAttrs otherAttrs foundExhaustiveAttr vis name processedFields processedFieldDefaults remainingFields # customDefault #" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && processedFields.isNotEmpty() && processedFieldDefaults.isNotEmpty() && default.isNotEmpty() && fattr.isNotEmpty() && fvis.isNotEmpty() && fname.isNotEmpty() && fty.isNotEmpty() && tail.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$processedFields" to processedFields, "\$processedFieldDefaults" to processedFieldDefaults, "\$default" to default, "\$fvis" to fvis, "\$fname" to fname, "\$fty" to fty, "\$tail" to tail).fold(CUSTOM_STRUCT_TEMPLATE_5) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@class cfgAttrs otherAttrs foundExhaustiveAttr vis name processedFields processedFieldDefaults remainingFields #" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && processedFields.isNotEmpty() && processedFieldDefaults.isNotEmpty() && fattr.isNotEmpty() && fvis.isNotEmpty() && fname.isNotEmpty() && fty.isNotEmpty() && tail.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$processedFields" to processedFields, "\$processedFieldDefaults" to processedFieldDefaults, "\$fvis" to fvis, "\$fname" to fname, "\$fty" to fty, "\$tail" to tail).fold(CUSTOM_STRUCT_TEMPLATE_6) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # cfg foundExhaustiveAttr vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && cfg.isNotEmpty() && tail.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$cfg" to cfg, "\$tail" to tail, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(CUSTOM_STRUCT_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # foundExhaustiveAttr vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && other.isNotEmpty() && tail.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$other" to other, "\$tail" to tail, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(CUSTOM_STRUCT_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs # exhaustive foundExhaustiveAttr vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && tail.isNotEmpty() && unused.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$tail" to tail, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(CUSTOM_STRUCT_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@class cfgAttrs otherAttrs foundExhaustiveAttr vis name processedFields processedFieldDefaults remainingFields" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && processedFields.isNotEmpty() && processedFieldDefaults.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$processedFields" to processedFields, "\$processedFieldDefaults" to processedFieldDefaults).fold(CUSTOM_STRUCT_TEMPLATE_7) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@splitAttrs cfgAttrs otherAttrs remainingAttrs foundExhaustiveAttr vis name body" && cfgAttrs.isNotEmpty() && otherAttrs.isNotEmpty() && foundExhaustive.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$cfgAttrs" to cfgAttrs, "\$otherAttrs" to otherAttrs, "\$foundExhaustive" to foundExhaustive, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(CUSTOM_STRUCT_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "attrs # class" && attrs.isNotEmpty() && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$attrs" to attrs, "\$attr" to attr, "\$vis" to vis, "\$name" to name, "\$body" to body).fold(CUSTOM_STRUCT_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Expands the definition of the record defined at [customStruct], with either one of an
 * additional private field or with its verbatim fields.
 */

internal fun emitStructDefinition(attr: String = "", vis: String = "", name: String = "", field: String = "", clause: String = ""): String =
    when {
        clause == "foundExhaustiveAttr falseVal body #" && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$vis" to vis, "\$name" to name, "\$field" to field).fold(EMIT_STRUCT_DEFINITION_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "foundExhaustiveAttr trueVal body #" && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && field.isNotEmpty() -> arrayOf("\$vis" to vis, "\$name" to name, "\$field" to field).fold(EMIT_STRUCT_DEFINITION_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Expands the `Default` implementation of the record defined at [customStruct], with either one
 * of an additional private field initialized to the unit value, or the record's fields verbatim.
 */

internal fun emitStructDefaultBody(fieldDefault: String = "", clause: String = ""): String =
    when {
        clause == "foundExhaustiveAttr falseVal body" && fieldDefault.isNotEmpty() -> EMIT_STRUCT_DEFAULT_BODY_TEMPLATE.replace("\$fieldDefault", fieldDefault)
        clause == "foundExhaustiveAttr trueVal body" && fieldDefault.isNotEmpty() -> EMIT_STRUCT_DEFAULT_BODY_TEMPLATE_1.replace("\$fieldDefault", fieldDefault)
        else -> ""
    }

/**
 * Create an uninhabited type that can't be constructed. It implements `Debug`, `Clone`, and
 * `Copy`, but these aren't meaningful for extern types so they should eventually be removed.
 *
 * Really what we want here is something that also can't be named without indirection (in ADTs or
 * function signatures), but this doesn't exist.
 */

internal fun externTy(attr: String = "", vis: String = "", i: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$vis" to vis, "\$i" to i).fold(EXTERN_TY_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$vis" to vis, "\$i" to i).fold(EXTERN_TY_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

/**
 * Implement `Clone` and `Copy` for an enum, as well as `Debug`, `Eq`, `Hash`, and
 * `PartialEq` if the `extraTraits` feature is enabled.
 */

internal fun e(attr: String = "", i: String = "", field: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$i" to i, "\$field" to field).fold(E_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$i" to i, "\$field" to field).fold(E_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

/**
 * Represent a C enum as Rust constants and a type.
 *
 * C enums can't soundly be mapped to Rust enums since C enums are allowed to have duplicates or
 * unlisted values, but this is UB in Rust. This enum doesn't implement any traits, its main
 * purpose is to calculate the correct enum values.
 *
 * Use the magic name `# anon` if the C enum doesn't create a type.
 *
 * See <https://github.com/rust-lang/libc/issues/4419> for more.
 */

internal fun cEnum(repr: String = "", vis: String = "", tyName: String = "", anon: String = "", meta: String = "", fieldVis: String = "", variant: String = "", value: String = "", tyNameArg: String = "", idx: String = "", defaultVal: String = "", tail: String = "", clause: String = ""): String =
    when {
        clause == "# repr sealed class # # =" && repr.isNotEmpty() && vis.isNotEmpty() && tyName.isNotEmpty() && anon.isNotEmpty() && meta.isNotEmpty() && fieldVis.isNotEmpty() && variant.isNotEmpty() && value.isNotEmpty() -> arrayOf("\$vis" to vis, "\$tyName" to tyName, "\$anon" to anon, "\$fieldVis" to fieldVis, "\$variant" to variant, "\$value" to value).fold(C_ENUM_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@single # repr sealed class # =" && repr.isNotEmpty() && vis.isNotEmpty() && tyName.isNotEmpty() && meta.isNotEmpty() && fieldVis.isNotEmpty() && variant.isNotEmpty() && value.isNotEmpty() -> arrayOf("\$repr" to repr, "\$vis" to vis, "\$tyName" to tyName, "\$fieldVis" to fieldVis, "\$variant" to variant, "\$value" to value).fold(C_ENUM_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@variant ty default variants # =" && tyName.isNotEmpty() && defaultVal.isNotEmpty() && meta.isNotEmpty() && fieldVis.isNotEmpty() && variant.isNotEmpty() && value.isNotEmpty() && tail.isNotEmpty() -> arrayOf("\$tyName" to tyName, "\$defaultVal" to defaultVal, "\$fieldVis" to fieldVis, "\$variant" to variant, "\$value" to value, "\$tail" to tail).fold(C_ENUM_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@single # repr sealed class @anon # =" && repr.isNotEmpty() && vis.isNotEmpty() && meta.isNotEmpty() && fieldVis.isNotEmpty() && variant.isNotEmpty() && value.isNotEmpty() -> arrayOf("\$repr" to repr, "\$fieldVis" to fieldVis, "\$variant" to variant, "\$value" to value).fold(C_ENUM_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@variant ty default variants" && tyNameArg.isNotEmpty() && idx.isNotEmpty() -> ""
        clause == "@ty" && repr.isNotEmpty() -> C_ENUM_TEMPLATE_5.replace("\$repr", repr)
        clause == "@ty" -> C_ENUM_TEMPLATE_6
        else -> ""
    }

/**
 * Define a function that can be either `safe` or `unchecked` and optionally `const`. This always
 * marks the function inline and adds `extern "C"`.
 */

internal fun f(attr: String = "", constDummy: String = "", unsafeDummy: String = "", safeDummy: String = "", i: String = "", arg: String = "", argty: String = "", ret: String = "", body: String = "", clause: String = ""): String =
    when {
        clause == "# public const unsafe safe - >" && attr.isNotEmpty() && constDummy.isNotEmpty() && unsafeDummy.isNotEmpty() && safeDummy.isNotEmpty() && i.isNotEmpty() && arg.isNotEmpty() && argty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$constDummy" to constDummy, "\$unsafeDummy" to unsafeDummy, "\$safeDummy" to safeDummy, "\$i" to i, "\$arg" to arg, "\$argty" to argty, "\$ret" to ret, "\$body" to body).fold(F_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@single # public const unsafe - >" && attr.isNotEmpty() && constDummy.isNotEmpty() && i.isNotEmpty() && arg.isNotEmpty() && argty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$constDummy" to constDummy, "\$i" to i, "\$arg" to arg, "\$argty" to argty, "\$ret" to ret, "\$body" to body).fold(F_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@single # public const safe - >" && attr.isNotEmpty() && constDummy.isNotEmpty() && i.isNotEmpty() && arg.isNotEmpty() && argty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$constDummy" to constDummy, "\$i" to i, "\$arg" to arg, "\$argty" to argty, "\$ret" to ret, "\$body" to body).fold(F_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@single public const - >" && constDummy.isNotEmpty() && i.isNotEmpty() && arg.isNotEmpty() && argty.isNotEmpty() && ret.isNotEmpty() && body.isNotEmpty() -> F_TEMPLATE_3
        else -> ""
    }

internal fun deprecatedMach(id: String = "", ty: String = "", expr: String = "", clause: String = ""): String =
    when {
        clause == "public const =" && id.isNotEmpty() && ty.isNotEmpty() && expr.isNotEmpty() -> arrayOf("\$id" to id, "\$ty" to ty, "\$expr" to expr).fold(DEPRECATED_MACH_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "public type =" && id.isNotEmpty() && ty.isNotEmpty() -> arrayOf("\$id" to id, "\$ty" to ty).fold(DEPRECATED_MACH_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Polyfill for std's `offsetOf`.
 */

internal fun offsetOf(ty: String = "", field: String = ""): String =
    arrayOf("\$ty" to ty, "\$field" to field).fold(OFFSET_OF_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
