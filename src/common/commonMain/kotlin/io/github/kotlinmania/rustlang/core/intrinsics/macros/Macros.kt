// port-lint: source intrinsics/macros.rs
package io.github.kotlinmania.rustlang.core.intrinsics.macros

import io.github.kotlinmania.rustlang.intrinsics.macros.intrinsicDispatchOnType

// Macros used to declare intrinsics.

private const val INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE: String = "intrinsicDispatchOnType {@ declare[]\$()* \$vis fun \$name< \$generic: \$bound>(\$(\$arg: \$argTy),*)-> \$retTy; \$(\$concrete=> \$body)*}"

private const val INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_1: String = "intrinsicDispatchOnType {@ declare\$()* \$vis fun \$name< \$generic: \$bound>(\$(\$arg: \$argTy),*)-> \$retTy; \$(\$concrete=> \$body)*}"

private const val INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_2: String = "mod \$name {import superRef.*; public interface Dispatch< \$generic= Self>: \$bound {fun dispatch(\$(\$arg: \$argTy),*)-> \$retTy; } intrinsicDispatchOnType {@ impls \$generic, (\$(\$arg: \$argTy),*)-> \$retTy, \$(\$concrete=> \$body)*}}\$()* \$vis\$(\$constness)? fun \$name< \$generic: \$name.Dispatch>(\$(\$arg: \$argTy),*)-> \$retTy {< \$generic asTarget \$name.Dispatch>.dispatch(\$(\$arg),*)}"

private const val INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_3: String = "\$(intrinsicDispatchOnType {@ class \$generic= \$concrete, \$args-> \$retTy \$body})*"

private const val INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_4: String = "const unused:()= {type \$generic= \$concrete; class Dispatch< \$concrete> forLoop \$concrete {fun dispatch(\$(\$arg: \$argTy),*)-> \$retTy \$body}};"

/**
 * Declares an intrinsic that is generic over its type, but whose fallback body has to be
 * written per-type. This is needed for the float intrinsics, which are generics but may
 * have different fallback bodies for each float width.
 *
 * ```ignore (illustrative)
 * intrinsicDispatchOnType {
 *     /// Returns the exponential of a float.
 *     @rustcNounwind
 *     @rustcIntrinsic
 *     pub fn expf<T: bounds.FloatPrimitive>(x: T) -> T;
 *
 *     f16 => { expf(x as f32) as f16 }
 *     f32 => { libm.likelyAvailable.expf(x) }
 *     f64 => { libm.likelyAvailable.exp(x) }
 *     f128 => { libm.maybeAvailable.expf128(x) }
 * }
 * ```
 */

internal fun intrinsicDispatchOnType(attr: String = "", vis: String = "", name: String = "", generic: String = "", bound: String = "", arg: String = "", argTy: String = "", retTy: String = "", concrete: String = "", body: String = "", constness: String = "", args: String = "", clause: String = ""): String =
    when {
        clause == "@declare # < > - > = >" && constness.isNotEmpty() && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && generic.isNotEmpty() && bound.isNotEmpty() && arg.isNotEmpty() && argTy.isNotEmpty() && retTy.isNotEmpty() && concrete.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$constness" to constness, "\$vis" to vis, "\$name" to name, "\$generic" to generic, "\$bound" to bound, "\$arg" to arg, "\$argTy" to argTy, "\$retTy" to retTy, "\$concrete" to concrete, "\$body" to body).fold(INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# < > - > = >" && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && generic.isNotEmpty() && bound.isNotEmpty() && arg.isNotEmpty() && argTy.isNotEmpty() && retTy.isNotEmpty() && concrete.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$vis" to vis, "\$name" to name, "\$generic" to generic, "\$bound" to bound, "\$arg" to arg, "\$argTy" to argTy, "\$retTy" to retTy, "\$concrete" to concrete, "\$body" to body).fold(INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "# const < > - > = >" && attr.isNotEmpty() && vis.isNotEmpty() && name.isNotEmpty() && generic.isNotEmpty() && bound.isNotEmpty() && arg.isNotEmpty() && argTy.isNotEmpty() && retTy.isNotEmpty() && concrete.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$vis" to vis, "\$name" to name, "\$generic" to generic, "\$bound" to bound, "\$arg" to arg, "\$argTy" to argTy, "\$retTy" to retTy, "\$concrete" to concrete, "\$body" to body).fold(INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@class = - >" && generic.isNotEmpty() && concrete.isNotEmpty() && arg.isNotEmpty() && argTy.isNotEmpty() && retTy.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$generic" to generic, "\$concrete" to concrete, "\$arg" to arg, "\$argTy" to argTy, "\$retTy" to retTy, "\$body" to body).fold(INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@impls - > = >" && generic.isNotEmpty() && args.isNotEmpty() && retTy.isNotEmpty() && concrete.isNotEmpty() && body.isNotEmpty() -> arrayOf("\$generic" to generic, "\$args" to args, "\$retTy" to retTy, "\$concrete" to concrete, "\$body" to body).fold(INTRINSIC_DISPATCH_ON_TYPE_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }
