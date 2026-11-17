package io.github.kotlinmania.rustlang.corearch.armshared

// ARM C Language Extensions (ACLE)
//
// # Developer notes
//
// Below is a list of built-in targets that are representative of the different ARM
// architectures; the list includes the `targetFeature`s they possess.
//
// - `armv4t - unknown - linux - gnueabi` - **ARMv4** - `+ v4t`
// - `armv5te - unknown - linux - gnueabi` - **ARMv5TE** - `+ v4t + v5te`
// - `arm - unknown - linux - gnueabi` - **ARMv6** - `+ v4t + v5te + v6`
// - `thumbv6m - none - eabi` - **ARMv6-M** - `+ v4t + v5te + v6 + thumb - mode + mclass`
// - `armv7 - unknown - linux - gnueabihf` - **ARMv7-A** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + dsp + thumb2 + aclass`
// - `armv7r - none - eabi` - **ARMv7-R** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + dsp + thumb2 + rclass`
// - `thumbv7m - none - eabi` - **ARMv7-M** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + thumb2 + thumb - mode + mclass`
// - `thumbv7em - none - eabi` - **ARMv7E-M** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + dsp + thumb2 + thumb - mode + mclass`
// - `thumbv8m.main - none - eabi` - **ARMv8-M** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + thumb2 + thumb - mode + mclass`
// - `armv8r - none - eabi` - **ARMv8-R** - `+ v4t + v5te + v6 + v6k + v6t2 + v7 + v8 + thumb2 + rclass`
// - `aarch64 - unknown - linux - gnu` - **ARMv8-A (AArch64)** - `+ fp + neon`
//
// Section 10.1 of ACLE says:
//
// - "In the sequence of Arm architectures ( v5, v5TE, v6, v6T2, v7 ) each architecture includes
//   its predecessor's instruction set."
//
// - "In the sequence of Thumb-only architectures ( v6-M, v7-M, v7E-M ) each architecture includes
//   its predecessor's instruction set."
//
// From that info and from looking at how LLVM features work (using custom targets) we can identify
// features that are subsets of others:
//
// Legend: `a<b` reads as "`a` is a subset of `b`"; this means that if `b` is enabled then `a` is
// enabled as well.
//
// - `v4t<v5te<v6<v6k<v6t2<v7<v8`
// - `v6<v8m<v6t2`
// - `v7<v8m.main`
//
// *NOTE*: Section 5.4.7 of ACLE says:
//
// - "__ARM_FEATURE_DSP is defined to 1 if the DSP (v5E) instructions are supported and the
//   intrinsics defined in Saturating intrinsics are available."
//
// This does *not* match how LLVM uses the '+dsp' feature; this feature is not set for v5te
// targets so we have to work around this difference.
//
// # References
//
// - [ACLE Q2 2018](https://developer.arm.com/docs/101028/latest)

