// port-lint: source num/imp/int_bits.rs
package io.github.kotlinmania.rustlang.core.num.imp.intbits

// Implementations for `uN.extractBits` and `uN.depositBits`
//
// For the purposes of this implementation, the operations can be thought
// of as operating on the input bits as a list, starting from the least
// significant bit. Extraction is like `List.retain` that deletes bits
// where the mask has a zero. Deposition is like doing the inverse by
// inserting the zeros that extraction would delete.
//
// Key observation: Each extracted or deposited bit needs to be
// shifted by the count of zeros up to the corresponding mask bit.
//
// With that in mind, the general idea is to decompose the operation into
// a sequence of stages in `0 .. log2(BITS)`, where each stage shifts some
// of the bits by `n = 1<<stage`. The masks for each stage are computed
// via prefix counts of zeros in the mask.
//
// # Extraction
//
// Consider the input as a sequence of runs of data (bitstrings A,B,C,...),
// split by fixed-width groups of zeros ('.'), initially at width `n = 1`.
// Counting the groups of zeros, each stage shifts the odd-indexed runs of
// data right by `n`, effectively swapping them with the preceding zeros.
// For the next stage, `n` is doubled as all the zeros are now paired.
// ```text
// .A.B.C.D.E.F.G.H
// ..AB..CD..EF..GH
// ....ABCD....EFGH
// ........ABCDEFGH
// ```
// What makes this nontrivial is that the lengths of the bitstrings are not
// the same. Using lowercase for individual bits, the above might look like
// ```text
// .a.bbb.ccccc.dd.e..g.hh
// ..abbb..cccccdd..e..ghh
// ....abbbcccccdd....eghh
// ........abbbcccccddeghh
// ```
//
// # Deposition
//
// For `depositBits`, the stages are reversed. We start with a single run of
// data in the low bits. Each stage then splits each run of data in two by
// shifting part of it left by `n`, which is halved each stage.
// ```text
// ........ABCDEFGH
// ....ABCD....EFGH
// ..AB..CD..EF..GH
// .A.B.C.D.E.F.G.H
// ```
//
// # Stage masks
//
// To facilitate the shifts at each stage, we compute a mask that covers both
// the bitstrings to shift, and the zeros they shift into.
// ```text
// .A.B.C.D.E.F.G.H
//  ##  ##  ##  ##
// ..AB..CD..EF..GH
//   ####    ####
// ....ABCD....EFGH
//     ########
// ........ABCDEFGH
// ```

private const val UINT_IMPL_TEMPLATE: String = "public(input crate.num) mod \$u {const Stages: usize= \$u.Bits.ilog2() asTarget usize; const fun prepare(sparse: \$u)->[\$u; Stages] {val var zeros=! sparse; val var masks=[0; Stages]; val var stage= 0; whileLoop stage< Stages {val n= 1<< stage; val var parity= zeros; val var len= n; whileLoop len< \$u.Bits {parity^= parity<< len; len<<= 1; } masks[stage]= parity; zeros&=! parity; zeros^= zeros>> n; stage+= 1; } masks} public(input crate.num) const fun extractImpl(var x: \$u, sparse: \$u)-> \$u {val masks= prepare(sparse); x&= sparse; val var stage= 0; whileLoop stage< Stages {val n= 1<< stage; val q= x& masks[stage]; ifBranch cfg(all(targetArch= \"aarch64\", targetFeature= \"neon\"))& \$u.Bits<= 64 {x=(x&! masks[stage]).wrappingAdd(q>> n); } elseBranch {x^= q; x^= q>> n; } stage+= 1; } x} public(input crate.num) const fun depositImpl(var x: \$u, sparse: \$u)-> \$u {val masks= prepare(sparse); val var stage= Stages; whileLoop stage> 0 {stage-= 1; val n= 1<< stage; val q= x& masks[stage]; x^= q; x^= q<< n; } x& sparse}}"

internal fun uintImpl(u: String = ""): String =
    UINT_IMPL_TEMPLATE.replace("\$u", u)
