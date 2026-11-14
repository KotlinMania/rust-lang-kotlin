// port-lint: source detect/macros.rs
package io.github.kotlinmania.rustlang.stddetect.detect

private const val DETECT_FEATURE_TEMPLATE: String = "detectFeature(\$feature, \$featureLit: \$featureLit)"

private const val DETECT_FEATURE_TEMPLATE_1: String = "\$(cfg(targetFeature= \$targetFeatureLit)||)* detect.isFeatureDetected. \$feature()"

private const val DETECT_FEATURE_TEMPLATE_2: String = "detect.isFeatureDetected. \$feature()"

private const val CHECK_CFG_FEATURE_TEMPLATE: String = "checkCfgFeature(\$feature, \$featureLit: \$featureLit)"

private const val CHECK_CFG_FEATURE_TEMPLATE_1: String = "\$(cfg(targetFeature= \$targetFeatureLit); )*"

private const val CHECK_CFG_FEATURE_TEMPLATE_2: String = "{cfg(targetFeature= \$featureLit)}"

private const val FEATURES_TEMPLATE: String = "\$()* macro \$macroName {\$((\$featureLit)=> {detectFeature(\$feature, \$featureLit\$(, without cfg check: \$featureCfgCheck)?\$(:\$(\$targetFeatureLit),*)?)}; )*\$((\$bindFeature)=> { {\$(macro deprecatedFeature { {}=> {}; } deprecatedFeature {}; )? \$macroName!(\$featureImpl)}}; )*\$((\$nortFeature)=> {compileError(concat(stringify(\$nortFeature), \" feature cannot be detected at run-time\"))}; )*(\$t: tt, )=> {\$macroName!(\$t)}; (\$t: tt)=> {compileError(concat(\"unknown \", stringify(\$target), \" target feature: \", \$t))}; }\$()* macro \$macroName {\$((\$featureLit)=> {compileError(concat(\"This macro cannot be used on the current target. You can prevent\\n\\\n                            it from being used in other architectures by guarding it behind\\n\\\n                            a `cfg(\", stringify(\$cfg), \")`.\"))}; )*\$((\$bindFeature)=> {\$macroName!(\$featureImpl)}; )*\$((\$nortFeature)=> {compileError(concat(stringify(\$nortFeature), \" feature cannot be detected at run-time\"))}; )*(\$t: tt, )=> {\$macroName!(\$t); }; (\$t: tt)=> {compileError(concat(\"unknown \", stringify(\$target), \" target feature: \", \$t))}; } const unused:()= {\$(checkCfgFeature(\$feature, \$featureLit\$(, without cfg check: \$featureCfgCheck)?\$(:\$(\$targetFeatureLit),*)?); )*}; public(crate) sealed class Feature {\$(\$()* \$feature, )*} class Feature {public(crate) const All:&[Feature]=&[\$(Feature. \$feature, )*]; public(crate) fun toStr(this)->& str {when this {\$(Feature. \$feature=> \$featureLit, )*}} public(crate) fun fromStr(s:& str)-> Result< Feature, ()> {when s {\$(\$featureLit=> Ok(Feature. \$feature), )* unused=> Err(())}}} public mod isFeatureDetected {\$(public fun \$feature()-> bool {detect.checkFor(detect.Feature. \$feature)})*}"

/**
 * Forward a literal to the relevant `__is_feature_detected` function.
 */

public fun detectFeature(feature: String = "", featureLit: String = "", targetFeatureLit: String = "", clause: String = ""): String =
    when {
        clause == "without cfg check trueVal" && feature.isNotEmpty() && featureLit.isNotEmpty() -> DETECT_FEATURE_TEMPLATE_2.replace("\$feature", feature)
        feature.isNotEmpty() && featureLit.isNotEmpty() && targetFeatureLit.isNotEmpty() -> arrayOf("\$feature" to feature, "\$targetFeatureLit" to targetFeatureLit).fold(DETECT_FEATURE_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        feature.isNotEmpty() && featureLit.isNotEmpty() -> arrayOf("\$feature" to feature, "\$featureLit" to featureLit).fold(DETECT_FEATURE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Verify that all features are known by `cfg`.
 */

internal fun checkCfgFeature(feature: String = "", featureLit: String = "", targetFeatureLit: String = "", featureCfgCheck: String = "", clause: String = ""): String =
    when {
        clause == "without cfg check" && feature.isNotEmpty() && featureLit.isNotEmpty() && featureCfgCheck.isNotEmpty() -> arrayOf("\$feature" to feature, "\$featureLit" to featureLit).fold(CHECK_CFG_FEATURE_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        feature.isNotEmpty() && featureLit.isNotEmpty() && targetFeatureLit.isNotEmpty() -> CHECK_CFG_FEATURE_TEMPLATE_1.replace("\$targetFeatureLit", targetFeatureLit)
        feature.isNotEmpty() && featureLit.isNotEmpty() -> arrayOf("\$feature" to feature, "\$featureLit" to featureLit).fold(CHECK_CFG_FEATURE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Create the `is_ * _feature_detected` macro for an architecture.
 */

internal fun features(target: String = "", cfg: String = "", macroName: String = "", macroAttrs: String = "", bindFeature: String = "", featureImpl: String = "", deprecateAttr: String = "", nortFeature: String = "", stabilityAttr: String = "", feature: String = "", featureLit: String = "", featureCfgCheck: String = "", targetFeatureLit: String = "", featureComment: String = ""): String =
    if (macroAttrs.isEmpty() && deprecateAttr.isEmpty() && stabilityAttr.isEmpty() && featureComment.isEmpty()) {
        arrayOf("\$target" to target, "\$cfg" to cfg, "\$macroName" to macroName, "\$bindFeature" to bindFeature, "\$featureImpl" to featureImpl, "\$nortFeature" to nortFeature, "\$feature" to feature, "\$featureLit" to featureLit, "\$featureCfgCheck" to featureCfgCheck, "\$targetFeatureLit" to targetFeatureLit).fold(FEATURES_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$target" to target, "\$cfg" to cfg, "\$macroName" to macroName, "\$bindFeature" to bindFeature, "\$featureImpl" to featureImpl, "\$nortFeature" to nortFeature, "\$feature" to feature, "\$featureLit" to featureLit, "\$featureCfgCheck" to featureCfgCheck, "\$targetFeatureLit" to targetFeatureLit).fold(FEATURES_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }
