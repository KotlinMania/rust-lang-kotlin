// port-lint: tests x86_64/avx512f.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.x86.MM_FROUND_NO_EXC
import io.github.kotlinmania.rustlang.x86.MM_FROUND_TO_NEAREST_INT
import io.github.kotlinmania.rustlang.x86.MM_FROUND_TO_POS_INF
import io.github.kotlinmania.rustlang.x86.MM_FROUND_TO_ZERO
import io.github.kotlinmania.rustlang.x86.Mmask8
import io.github.kotlinmania.rustlang.x86.mm256AbsEpi64
import io.github.kotlinmania.rustlang.x86.mm256AlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm256Castsi256Pd
import io.github.kotlinmania.rustlang.x86.mm256CmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpPdMask
import io.github.kotlinmania.rustlang.x86.mm256CmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256CmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256Cvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256Cvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256Cvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256Cvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm256CvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256Cvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256Cvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256Cvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256CvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256Cvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256Cvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256Cvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256FixupimmPd
import io.github.kotlinmania.rustlang.x86.mm256GetexpPd
import io.github.kotlinmania.rustlang.x86.mm256GetmantPd
import io.github.kotlinmania.rustlang.x86.mm256I32scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm256I32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm256I32scatterPd
import io.github.kotlinmania.rustlang.x86.mm256I32scatterPs
import io.github.kotlinmania.rustlang.x86.mm256I64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm256I64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm256I64scatterPd
import io.github.kotlinmania.rustlang.x86.mm256I64scatterPs
import io.github.kotlinmania.rustlang.x86.mm256LoadEpi64
import io.github.kotlinmania.rustlang.x86.mm256LoaduEpi64
import io.github.kotlinmania.rustlang.x86.mm256Mask2Permutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm256Mask2Permutex2varPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FmaddPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FmsubPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FnmaddPd
import io.github.kotlinmania.rustlang.x86.mm256Mask3FnmsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskAbsEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskAddEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskAddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskAndEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskBlendPd
import io.github.kotlinmania.rustlang.x86.mm256MaskBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskBroadcastsdPd
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpPdMask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskCompressEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCompressPd
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtpdPs
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtsepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm256MaskCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskCvtusepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm256MaskDivPd
import io.github.kotlinmania.rustlang.x86.mm256MaskExpandEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskExpandPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFixupimmPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFmaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFmsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFnmaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskFnmsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskGetexpPd
import io.github.kotlinmania.rustlang.x86.mm256MaskGetmantPd
import io.github.kotlinmania.rustlang.x86.mm256MaskI32scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskI32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskI32scatterPd
import io.github.kotlinmania.rustlang.x86.mm256MaskI32scatterPs
import io.github.kotlinmania.rustlang.x86.mm256MaskI64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskI64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskI64scatterPd
import io.github.kotlinmania.rustlang.x86.mm256MaskI64scatterPs
import io.github.kotlinmania.rustlang.x86.mm256MaskMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm256MaskMaxPd
import io.github.kotlinmania.rustlang.x86.mm256MaskMinEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskMinEpu64
import io.github.kotlinmania.rustlang.x86.mm256MaskMinPd
import io.github.kotlinmania.rustlang.x86.mm256MaskMovEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskMovPd
import io.github.kotlinmania.rustlang.x86.mm256MaskMovedupPd
import io.github.kotlinmania.rustlang.x86.mm256MaskMulEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskMulEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskMulPd
import io.github.kotlinmania.rustlang.x86.mm256MaskOrEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutePd
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutevarPd
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutexPd
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskPermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm256MaskRcp14Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskRolEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskRolvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskRorEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskRorvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskRoundscalePd
import io.github.kotlinmania.rustlang.x86.mm256MaskRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskScalefPd
import io.github.kotlinmania.rustlang.x86.mm256MaskSet1Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm256MaskShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm256MaskShufflePd
import io.github.kotlinmania.rustlang.x86.mm256MaskSllEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSlliEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSllvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSqrtPd
import io.github.kotlinmania.rustlang.x86.mm256MaskSraEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSraiEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSravEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSrlEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSrliEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSubEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskSubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskTestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskTestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256MaskUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mm256MaskUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskUnpackloPd
import io.github.kotlinmania.rustlang.x86.mm256MaskXorEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzAbsEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzAddEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzAddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzAndEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzBroadcastsdPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzCompressEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCompressPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtpdPs
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm256MaskzDivPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzExpandEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzExpandPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFixupimmPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFmaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFmsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFnmaddPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzFnmsubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzGetexpPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzGetmantPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm256MaskzMaxPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzMinEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzMinEpu64
import io.github.kotlinmania.rustlang.x86.mm256MaskzMinPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzMovEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzMovPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzMovedupPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzMulEpi32
import io.github.kotlinmania.rustlang.x86.mm256MaskzMulEpu32
import io.github.kotlinmania.rustlang.x86.mm256MaskzMulPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzOrEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutePd
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutevarPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutexPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzPermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzRcp14Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskzRolEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzRolvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzRorEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzRorvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzRoundscalePd
import io.github.kotlinmania.rustlang.x86.mm256MaskzRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm256MaskzScalefPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzSet1Epi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm256MaskzShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm256MaskzShufflePd
import io.github.kotlinmania.rustlang.x86.mm256MaskzSllEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSlliEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSllvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSqrtPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzSraEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSraiEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSravEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSrlEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSrliEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSubEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzSubPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaskzUnpackloPd
import io.github.kotlinmania.rustlang.x86.mm256MaskzXorEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaxEpi64
import io.github.kotlinmania.rustlang.x86.mm256MaxEpu64
import io.github.kotlinmania.rustlang.x86.mm256MinEpi64
import io.github.kotlinmania.rustlang.x86.mm256MinEpu64
import io.github.kotlinmania.rustlang.x86.mm256MmaskI32gatherEpi32
import io.github.kotlinmania.rustlang.x86.mm256MmaskI32gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm256MmaskI32gatherPd
import io.github.kotlinmania.rustlang.x86.mm256MmaskI32gatherPs
import io.github.kotlinmania.rustlang.x86.mm256MmaskI64gatherEpi32
import io.github.kotlinmania.rustlang.x86.mm256MmaskI64gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm256MmaskI64gatherPd
import io.github.kotlinmania.rustlang.x86.mm256MmaskI64gatherPs
import io.github.kotlinmania.rustlang.x86.mm256OrEpi64
import io.github.kotlinmania.rustlang.x86.mm256Permutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm256Permutex2varPd
import io.github.kotlinmania.rustlang.x86.mm256PermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm256PermutexPd
import io.github.kotlinmania.rustlang.x86.mm256PermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm256PermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm256Rcp14Pd
import io.github.kotlinmania.rustlang.x86.mm256RolEpi64
import io.github.kotlinmania.rustlang.x86.mm256RolvEpi64
import io.github.kotlinmania.rustlang.x86.mm256RorEpi64
import io.github.kotlinmania.rustlang.x86.mm256RorvEpi64
import io.github.kotlinmania.rustlang.x86.mm256RoundscalePd
import io.github.kotlinmania.rustlang.x86.mm256Rsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm256ScalefPd
import io.github.kotlinmania.rustlang.x86.mm256Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm256Set1Epi64x
import io.github.kotlinmania.rustlang.x86.mm256Set1Pd
import io.github.kotlinmania.rustlang.x86.mm256Set1Ps
import io.github.kotlinmania.rustlang.x86.mm256SetEpi32
import io.github.kotlinmania.rustlang.x86.mm256SetEpi64x
import io.github.kotlinmania.rustlang.x86.mm256SetPd
import io.github.kotlinmania.rustlang.x86.mm256SetrEpi32
import io.github.kotlinmania.rustlang.x86.mm256SetrEpi64x
import io.github.kotlinmania.rustlang.x86.mm256SetrPd
import io.github.kotlinmania.rustlang.x86.mm256SetrPs
import io.github.kotlinmania.rustlang.x86.mm256SetzeroPd
import io.github.kotlinmania.rustlang.x86.mm256SetzeroPs
import io.github.kotlinmania.rustlang.x86.mm256SetzeroSi256
import io.github.kotlinmania.rustlang.x86.mm256ShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm256ShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm256SraEpi64
import io.github.kotlinmania.rustlang.x86.mm256SraiEpi64
import io.github.kotlinmania.rustlang.x86.mm256SravEpi64
import io.github.kotlinmania.rustlang.x86.mm256StoreEpi64
import io.github.kotlinmania.rustlang.x86.mm256StoreuEpi64
import io.github.kotlinmania.rustlang.x86.mm256TernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm256TestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256TestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm256UndefinedSi256
import io.github.kotlinmania.rustlang.x86.mm256XorEpi64
import io.github.kotlinmania.rustlang.x86.mm512AbsEpi64
import io.github.kotlinmania.rustlang.x86.mm512AbsPd
import io.github.kotlinmania.rustlang.x86.mm512AddEpi64
import io.github.kotlinmania.rustlang.x86.mm512AddPd
import io.github.kotlinmania.rustlang.x86.mm512AddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512AlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm512AndEpi64
import io.github.kotlinmania.rustlang.x86.mm512AndnotEpi64
import io.github.kotlinmania.rustlang.x86.mm512AndnotSi512
import io.github.kotlinmania.rustlang.x86.mm512BroadcastF64x4
import io.github.kotlinmania.rustlang.x86.mm512BroadcastI64x4
import io.github.kotlinmania.rustlang.x86.mm512BroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mm512BroadcastsdPd
import io.github.kotlinmania.rustlang.x86.mm512Castpd128Pd512
import io.github.kotlinmania.rustlang.x86.mm512Castpd256Pd512
import io.github.kotlinmania.rustlang.x86.mm512Castpd512Pd128
import io.github.kotlinmania.rustlang.x86.mm512Castpd512Pd256
import io.github.kotlinmania.rustlang.x86.mm512CastpdPs
import io.github.kotlinmania.rustlang.x86.mm512CastpdSi512
import io.github.kotlinmania.rustlang.x86.mm512Castsi128Si512
import io.github.kotlinmania.rustlang.x86.mm512Castsi256Si512
import io.github.kotlinmania.rustlang.x86.mm512Castsi512Pd
import io.github.kotlinmania.rustlang.x86.mm512Castsi512Ps
import io.github.kotlinmania.rustlang.x86.mm512Castsi512Si128
import io.github.kotlinmania.rustlang.x86.mm512Castsi512Si256
import io.github.kotlinmania.rustlang.x86.mm512CmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpRoundPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpeqPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmplePdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpltPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512CmpneqPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpnlePdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpnltPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpordPdMask
import io.github.kotlinmania.rustlang.x86.mm512CmpunordPdMask
import io.github.kotlinmania.rustlang.x86.mm512CvtRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512CvtRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512CvtRoundpdPs
import io.github.kotlinmania.rustlang.x86.mm512CvtRoundpsPd
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi32loPd
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mm512Cvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mm512Cvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mm512Cvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm512Cvtepu32loPd
import io.github.kotlinmania.rustlang.x86.mm512Cvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mm512CvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512CvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512CvtpdPs
import io.github.kotlinmania.rustlang.x86.mm512CvtpdPslo
import io.github.kotlinmania.rustlang.x86.mm512CvtpsPd
import io.github.kotlinmania.rustlang.x86.mm512CvtpsloPd
import io.github.kotlinmania.rustlang.x86.mm512Cvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512Cvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512Cvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512CvttRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512CvttRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512CvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512CvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512Cvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512Cvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512Cvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512DivPd
import io.github.kotlinmania.rustlang.x86.mm512DivRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Extractf64x4Pd
import io.github.kotlinmania.rustlang.x86.mm512Extracti64x4Epi64
import io.github.kotlinmania.rustlang.x86.mm512FixupimmPd
import io.github.kotlinmania.rustlang.x86.mm512FixupimmRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FmaddPd
import io.github.kotlinmania.rustlang.x86.mm512FmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm512FmaddsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FmsubPd
import io.github.kotlinmania.rustlang.x86.mm512FmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm512FmsubaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FnmaddPd
import io.github.kotlinmania.rustlang.x86.mm512FnmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512FnmsubPd
import io.github.kotlinmania.rustlang.x86.mm512FnmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512GetexpPd
import io.github.kotlinmania.rustlang.x86.mm512GetexpRoundPd
import io.github.kotlinmania.rustlang.x86.mm512GetmantPd
import io.github.kotlinmania.rustlang.x86.mm512GetmantRoundPd
import io.github.kotlinmania.rustlang.x86.mm512I32gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512I32gatherPd
import io.github.kotlinmania.rustlang.x86.mm512I32logatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512I32logatherPd
import io.github.kotlinmania.rustlang.x86.mm512I32loscatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512I32loscatterPd
import io.github.kotlinmania.rustlang.x86.mm512I32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512I32scatterPd
import io.github.kotlinmania.rustlang.x86.mm512I64gatherEpi32
import io.github.kotlinmania.rustlang.x86.mm512I64gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512I64gatherPd
import io.github.kotlinmania.rustlang.x86.mm512I64gatherPs
import io.github.kotlinmania.rustlang.x86.mm512I64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm512I64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512I64scatterPd
import io.github.kotlinmania.rustlang.x86.mm512I64scatterPs
import io.github.kotlinmania.rustlang.x86.mm512Insertf64x4
import io.github.kotlinmania.rustlang.x86.mm512Inserti64x4
import io.github.kotlinmania.rustlang.x86.mm512LoadEpi64
import io.github.kotlinmania.rustlang.x86.mm512LoadPd
import io.github.kotlinmania.rustlang.x86.mm512LoaduEpi64
import io.github.kotlinmania.rustlang.x86.mm512Mask2Permutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm512Mask2Permutex2varPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmaddPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmaddsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmsubPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FmsubaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FnmaddPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FnmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FnmsubPd
import io.github.kotlinmania.rustlang.x86.mm512Mask3FnmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskAbsEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskAbsPd
import io.github.kotlinmania.rustlang.x86.mm512MaskAddEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskAddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskAddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskAndEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskBlendPd
import io.github.kotlinmania.rustlang.x86.mm512MaskBroadcastF64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskBroadcastI64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskBroadcastsdPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpRoundPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpeqPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmplePdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpltPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpneqPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpnlePdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpnltPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpordPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCmpunordPdMask
import io.github.kotlinmania.rustlang.x86.mm512MaskCompressEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCompressPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtRoundpdPs
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtRoundpsPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi32loPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepu32loPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpdPs
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpdPslo
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpsPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtpsloPd
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtsepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm512MaskCvttRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvttRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskCvtusepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mm512MaskDivPd
import io.github.kotlinmania.rustlang.x86.mm512MaskDivRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskExpandEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskExpandPd
import io.github.kotlinmania.rustlang.x86.mm512MaskExtractf64x4Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskExtracti64x4Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskFixupimmPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFixupimmRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmaddsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFmsubaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFnmaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFnmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFnmsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskFnmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskGetexpPd
import io.github.kotlinmania.rustlang.x86.mm512MaskGetexpRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskGetmantPd
import io.github.kotlinmania.rustlang.x86.mm512MaskGetmantRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI32gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI32gatherPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI32logatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI32logatherPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI32loscatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI32loscatterPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI32scatterPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI64gatherEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskI64gatherEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI64gatherPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI64gatherPs
import io.github.kotlinmania.rustlang.x86.mm512MaskI64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskI64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskI64scatterPd
import io.github.kotlinmania.rustlang.x86.mm512MaskI64scatterPs
import io.github.kotlinmania.rustlang.x86.mm512MaskInsertf64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskInserti64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskMaxPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMaxRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMinEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskMinEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskMinPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMinRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMovEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskMovPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMovedupPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMulEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskMulEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskMulPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMulRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskMulloxEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskOrEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutePd
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutevarPd
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutexPd
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskPermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm512MaskRcp14Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceAddEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceAddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceAndEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMaxPd
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMinEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMinEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMinPd
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMulEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceMulPd
import io.github.kotlinmania.rustlang.x86.mm512MaskReduceOrEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskRolEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskRolvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskRorEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskRorvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskRoundscalePd
import io.github.kotlinmania.rustlang.x86.mm512MaskRoundscaleRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskScalefPd
import io.github.kotlinmania.rustlang.x86.mm512MaskScalefRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskSet1Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm512MaskShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm512MaskSllEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSlliEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSllvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSqrtPd
import io.github.kotlinmania.rustlang.x86.mm512MaskSqrtRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskSraEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSraiEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSravEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSrlEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSrliEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSubEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskSubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskSubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskTestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskTestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512MaskUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mm512MaskUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskUnpackloPd
import io.github.kotlinmania.rustlang.x86.mm512MaskXorEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzAbsEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzAddEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzAddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzAddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzAndEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzBroadcastF64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskzBroadcastI64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskzBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzBroadcastsdPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCompressEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCompressPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtRoundpdPs
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtRoundpsPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtpdPs
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtpsPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvttRoundpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvttRoundpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mm512MaskzDivPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzDivRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzExpandEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzExpandPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzExtractf64x4Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskzExtracti64x4Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzFixupimmPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFixupimmRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmaddsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFmsubaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFnmaddPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFnmaddRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFnmsubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzFnmsubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzGetexpPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzGetexpRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzGetmantPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzGetmantRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzInsertf64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskzInserti64x4
import io.github.kotlinmania.rustlang.x86.mm512MaskzMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskzMaxPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMaxRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMinEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzMinEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaskzMinPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMinRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMovEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzMovPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMovedupPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMulEpi32
import io.github.kotlinmania.rustlang.x86.mm512MaskzMulEpu32
import io.github.kotlinmania.rustlang.x86.mm512MaskzMulPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzMulRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzOrEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutePd
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutevarPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutexPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzPermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzRcp14Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskzRolEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzRolvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzRorEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzRorvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzRoundscalePd
import io.github.kotlinmania.rustlang.x86.mm512MaskzRoundscaleRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm512MaskzScalefPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzScalefRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzSet1Epi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm512MaskzShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm512MaskzSllEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSlliEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSllvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSqrtPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzSqrtRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzSraEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSraiEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSravEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSrlEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSrliEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSubEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzSubPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzSubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaskzUnpackloPd
import io.github.kotlinmania.rustlang.x86.mm512MaskzXorEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaxEpi64
import io.github.kotlinmania.rustlang.x86.mm512MaxEpu64
import io.github.kotlinmania.rustlang.x86.mm512MaxPd
import io.github.kotlinmania.rustlang.x86.mm512MaxRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MinEpi64
import io.github.kotlinmania.rustlang.x86.mm512MinEpu64
import io.github.kotlinmania.rustlang.x86.mm512MinPd
import io.github.kotlinmania.rustlang.x86.mm512MinRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MovedupPd
import io.github.kotlinmania.rustlang.x86.mm512MulEpi32
import io.github.kotlinmania.rustlang.x86.mm512MulEpu32
import io.github.kotlinmania.rustlang.x86.mm512MulPd
import io.github.kotlinmania.rustlang.x86.mm512MulRoundPd
import io.github.kotlinmania.rustlang.x86.mm512MulloxEpi64
import io.github.kotlinmania.rustlang.x86.mm512OrEpi64
import io.github.kotlinmania.rustlang.x86.mm512PermutePd
import io.github.kotlinmania.rustlang.x86.mm512PermutevarPd
import io.github.kotlinmania.rustlang.x86.mm512Permutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mm512Permutex2varPd
import io.github.kotlinmania.rustlang.x86.mm512PermutexEpi64
import io.github.kotlinmania.rustlang.x86.mm512PermutexPd
import io.github.kotlinmania.rustlang.x86.mm512PermutexvarEpi64
import io.github.kotlinmania.rustlang.x86.mm512PermutexvarPd
import io.github.kotlinmania.rustlang.x86.mm512Rcp14Pd
import io.github.kotlinmania.rustlang.x86.mm512ReduceAddEpi64
import io.github.kotlinmania.rustlang.x86.mm512ReduceAddPd
import io.github.kotlinmania.rustlang.x86.mm512ReduceAndEpi64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMaxEpi64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMaxEpu64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMaxPd
import io.github.kotlinmania.rustlang.x86.mm512ReduceMinEpi64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMinEpu64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMinPd
import io.github.kotlinmania.rustlang.x86.mm512ReduceMulEpi64
import io.github.kotlinmania.rustlang.x86.mm512ReduceMulPd
import io.github.kotlinmania.rustlang.x86.mm512ReduceOrEpi64
import io.github.kotlinmania.rustlang.x86.mm512RolEpi64
import io.github.kotlinmania.rustlang.x86.mm512RolvEpi64
import io.github.kotlinmania.rustlang.x86.mm512RorEpi64
import io.github.kotlinmania.rustlang.x86.mm512RorvEpi64
import io.github.kotlinmania.rustlang.x86.mm512RoundscalePd
import io.github.kotlinmania.rustlang.x86.mm512RoundscaleRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Rsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mm512ScalefPd
import io.github.kotlinmania.rustlang.x86.mm512ScalefRoundPd
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi32
import io.github.kotlinmania.rustlang.x86.mm512Set1Epi64
import io.github.kotlinmania.rustlang.x86.mm512Set1Pd
import io.github.kotlinmania.rustlang.x86.mm512Set1Ps
import io.github.kotlinmania.rustlang.x86.mm512Set4Epi64
import io.github.kotlinmania.rustlang.x86.mm512Set4Pd
import io.github.kotlinmania.rustlang.x86.mm512SetEpi32
import io.github.kotlinmania.rustlang.x86.mm512SetEpi64
import io.github.kotlinmania.rustlang.x86.mm512SetPd
import io.github.kotlinmania.rustlang.x86.mm512SetPs
import io.github.kotlinmania.rustlang.x86.mm512Setr4Epi64
import io.github.kotlinmania.rustlang.x86.mm512Setr4Pd
import io.github.kotlinmania.rustlang.x86.mm512SetrEpi32
import io.github.kotlinmania.rustlang.x86.mm512SetrEpi64
import io.github.kotlinmania.rustlang.x86.mm512SetrPd
import io.github.kotlinmania.rustlang.x86.mm512SetrPs
import io.github.kotlinmania.rustlang.x86.mm512SetzeroPd
import io.github.kotlinmania.rustlang.x86.mm512SetzeroSi512
import io.github.kotlinmania.rustlang.x86.mm512ShuffleF64x2
import io.github.kotlinmania.rustlang.x86.mm512ShuffleI64x2
import io.github.kotlinmania.rustlang.x86.mm512SllEpi64
import io.github.kotlinmania.rustlang.x86.mm512SlliEpi64
import io.github.kotlinmania.rustlang.x86.mm512SllvEpi64
import io.github.kotlinmania.rustlang.x86.mm512SqrtPd
import io.github.kotlinmania.rustlang.x86.mm512SqrtRoundPd
import io.github.kotlinmania.rustlang.x86.mm512SraEpi64
import io.github.kotlinmania.rustlang.x86.mm512SraiEpi64
import io.github.kotlinmania.rustlang.x86.mm512SravEpi64
import io.github.kotlinmania.rustlang.x86.mm512SrlEpi64
import io.github.kotlinmania.rustlang.x86.mm512SrliEpi64
import io.github.kotlinmania.rustlang.x86.mm512SrlvEpi64
import io.github.kotlinmania.rustlang.x86.mm512StoreEpi64
import io.github.kotlinmania.rustlang.x86.mm512StorePd
import io.github.kotlinmania.rustlang.x86.mm512StoreuEpi64
import io.github.kotlinmania.rustlang.x86.mm512SubEpi64
import io.github.kotlinmania.rustlang.x86.mm512SubPd
import io.github.kotlinmania.rustlang.x86.mm512SubRoundPd
import io.github.kotlinmania.rustlang.x86.mm512TernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mm512TestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512TestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mm512UndefinedPd
import io.github.kotlinmania.rustlang.x86.mm512UnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mm512UnpackhiPd
import io.github.kotlinmania.rustlang.x86.mm512UnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mm512UnpackloPd
import io.github.kotlinmania.rustlang.x86.mm512XorEpi64
import io.github.kotlinmania.rustlang.x86.mm512Zextpd128Pd512
import io.github.kotlinmania.rustlang.x86.mm512Zextpd256Pd512
import io.github.kotlinmania.rustlang.x86.mm512Zextsi128Si512
import io.github.kotlinmania.rustlang.x86.mm512Zextsi256Si512
import io.github.kotlinmania.rustlang.x86.mmAbsEpi64
import io.github.kotlinmania.rustlang.x86.mmAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mmCastsi128Pd
import io.github.kotlinmania.rustlang.x86.mmCmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpPdMask
import io.github.kotlinmania.rustlang.x86.mmCmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmCmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mmCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mmCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mmCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmFixupimmPd
import io.github.kotlinmania.rustlang.x86.mmGetexpPd
import io.github.kotlinmania.rustlang.x86.mmGetmantPd
import io.github.kotlinmania.rustlang.x86.mmI32scatterEpi32
import io.github.kotlinmania.rustlang.x86.mmI32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mmI32scatterPd
import io.github.kotlinmania.rustlang.x86.mmI32scatterPs
import io.github.kotlinmania.rustlang.x86.mmI64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mmI64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mmI64scatterPd
import io.github.kotlinmania.rustlang.x86.mmI64scatterPs
import io.github.kotlinmania.rustlang.x86.mmLoadEpi64
import io.github.kotlinmania.rustlang.x86.mmLoaduEpi64
import io.github.kotlinmania.rustlang.x86.mmMask2Permutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mmMask2Permutex2varPd
import io.github.kotlinmania.rustlang.x86.mmMask3FmaddPd
import io.github.kotlinmania.rustlang.x86.mmMask3FmaddsubPd
import io.github.kotlinmania.rustlang.x86.mmMask3FmsubPd
import io.github.kotlinmania.rustlang.x86.mmMask3FmsubaddPd
import io.github.kotlinmania.rustlang.x86.mmMask3FnmaddPd
import io.github.kotlinmania.rustlang.x86.mmMask3FnmsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskAbsEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskAddEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskAddPd
import io.github.kotlinmania.rustlang.x86.mmMaskAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskAndEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskBlendEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskBlendPd
import io.github.kotlinmania.rustlang.x86.mmMaskBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskCmpEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpPdMask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpeqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpeqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpgeEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpgeEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpgtEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpgtEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpleEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpleEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpltEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpltEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpneqEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCmpneqEpu64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskCompressEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskCompressPd
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mmMaskCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtpdPs
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtsepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mmMaskCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64StoreuEpi16
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64StoreuEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskCvtusepi64StoreuEpi8
import io.github.kotlinmania.rustlang.x86.mmMaskDivPd
import io.github.kotlinmania.rustlang.x86.mmMaskExpandEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskExpandPd
import io.github.kotlinmania.rustlang.x86.mmMaskFixupimmPd
import io.github.kotlinmania.rustlang.x86.mmMaskFmaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskFmsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskFnmaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskFnmsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskGetexpPd
import io.github.kotlinmania.rustlang.x86.mmMaskGetmantPd
import io.github.kotlinmania.rustlang.x86.mmMaskI32scatterEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskI32scatterEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskI32scatterPd
import io.github.kotlinmania.rustlang.x86.mmMaskI32scatterPs
import io.github.kotlinmania.rustlang.x86.mmMaskI64scatterEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskI64scatterEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskI64scatterPd
import io.github.kotlinmania.rustlang.x86.mmMaskI64scatterPs
import io.github.kotlinmania.rustlang.x86.mmMaskMaxEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskMaxEpu64
import io.github.kotlinmania.rustlang.x86.mmMaskMaxPd
import io.github.kotlinmania.rustlang.x86.mmMaskMinEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskMinEpu64
import io.github.kotlinmania.rustlang.x86.mmMaskMinPd
import io.github.kotlinmania.rustlang.x86.mmMaskMovEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskMovPd
import io.github.kotlinmania.rustlang.x86.mmMaskMovedupPd
import io.github.kotlinmania.rustlang.x86.mmMaskMulEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskMulEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskMulPd
import io.github.kotlinmania.rustlang.x86.mmMaskOrEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskPermutePd
import io.github.kotlinmania.rustlang.x86.mmMaskPermutevarPd
import io.github.kotlinmania.rustlang.x86.mmMaskPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mmMaskRcp14Pd
import io.github.kotlinmania.rustlang.x86.mmMaskRolEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskRolvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskRorEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskRorvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskRoundscalePd
import io.github.kotlinmania.rustlang.x86.mmMaskRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mmMaskScalefPd
import io.github.kotlinmania.rustlang.x86.mmMaskSet1Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskShufflePd
import io.github.kotlinmania.rustlang.x86.mmMaskSllEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSlliEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSllvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSqrtPd
import io.github.kotlinmania.rustlang.x86.mmMaskSraEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSraiEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSravEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSrlEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSrliEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSubEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskSubPd
import io.github.kotlinmania.rustlang.x86.mmMaskTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskTestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskTestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmMaskUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mmMaskUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskUnpackloPd
import io.github.kotlinmania.rustlang.x86.mmMaskXorEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzAbsEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzAddEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzAddPd
import io.github.kotlinmania.rustlang.x86.mmMaskzAlignrEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzAndEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzAndnotEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzBroadcastqEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCompressEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCompressPd
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi16Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi32Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi32Pd
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepi8Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepu16Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepu32Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepu32Pd
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtepu8Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtpdEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtpdEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtpdPs
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtsepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtsepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtsepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskzCvttpdEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvttpdEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtusepi64Epi16
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtusepi64Epi32
import io.github.kotlinmania.rustlang.x86.mmMaskzCvtusepi64Epi8
import io.github.kotlinmania.rustlang.x86.mmMaskzDivPd
import io.github.kotlinmania.rustlang.x86.mmMaskzExpandEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzExpandPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFixupimmPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFmaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFmaddsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFmsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFmsubaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFnmaddPd
import io.github.kotlinmania.rustlang.x86.mmMaskzFnmsubPd
import io.github.kotlinmania.rustlang.x86.mmMaskzGetexpPd
import io.github.kotlinmania.rustlang.x86.mmMaskzGetmantPd
import io.github.kotlinmania.rustlang.x86.mmMaskzMaxEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzMaxEpu64
import io.github.kotlinmania.rustlang.x86.mmMaskzMaxPd
import io.github.kotlinmania.rustlang.x86.mmMaskzMinEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzMinEpu64
import io.github.kotlinmania.rustlang.x86.mmMaskzMinPd
import io.github.kotlinmania.rustlang.x86.mmMaskzMovEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzMovPd
import io.github.kotlinmania.rustlang.x86.mmMaskzMovedupPd
import io.github.kotlinmania.rustlang.x86.mmMaskzMulEpi32
import io.github.kotlinmania.rustlang.x86.mmMaskzMulEpu32
import io.github.kotlinmania.rustlang.x86.mmMaskzMulPd
import io.github.kotlinmania.rustlang.x86.mmMaskzOrEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzPermutePd
import io.github.kotlinmania.rustlang.x86.mmMaskzPermutevarPd
import io.github.kotlinmania.rustlang.x86.mmMaskzPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mmMaskzRcp14Pd
import io.github.kotlinmania.rustlang.x86.mmMaskzRolEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzRolvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzRorEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzRorvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzRoundscalePd
import io.github.kotlinmania.rustlang.x86.mmMaskzRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mmMaskzScalefPd
import io.github.kotlinmania.rustlang.x86.mmMaskzSet1Epi64
import io.github.kotlinmania.rustlang.x86.mmMaskzShufflePd
import io.github.kotlinmania.rustlang.x86.mmMaskzSllEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSlliEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSllvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSqrtPd
import io.github.kotlinmania.rustlang.x86.mmMaskzSraEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSraiEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSravEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSrlEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSrliEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSrlvEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSubEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzSubPd
import io.github.kotlinmania.rustlang.x86.mmMaskzTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzUnpackhiEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzUnpackhiPd
import io.github.kotlinmania.rustlang.x86.mmMaskzUnpackloEpi64
import io.github.kotlinmania.rustlang.x86.mmMaskzUnpackloPd
import io.github.kotlinmania.rustlang.x86.mmMaskzXorEpi64
import io.github.kotlinmania.rustlang.x86.mmMaxEpi64
import io.github.kotlinmania.rustlang.x86.mmMaxEpu64
import io.github.kotlinmania.rustlang.x86.mmMinEpi64
import io.github.kotlinmania.rustlang.x86.mmMinEpu64
import io.github.kotlinmania.rustlang.x86.mmMmaskI32gatherEpi32
import io.github.kotlinmania.rustlang.x86.mmMmaskI32gatherEpi64
import io.github.kotlinmania.rustlang.x86.mmMmaskI32gatherPd
import io.github.kotlinmania.rustlang.x86.mmMmaskI32gatherPs
import io.github.kotlinmania.rustlang.x86.mmMmaskI64gatherEpi32
import io.github.kotlinmania.rustlang.x86.mmMmaskI64gatherEpi64
import io.github.kotlinmania.rustlang.x86.mmMmaskI64gatherPd
import io.github.kotlinmania.rustlang.x86.mmMmaskI64gatherPs
import io.github.kotlinmania.rustlang.x86.mmOrEpi64
import io.github.kotlinmania.rustlang.x86.mmPermutex2varEpi64
import io.github.kotlinmania.rustlang.x86.mmPermutex2varPd
import io.github.kotlinmania.rustlang.x86.mmRcp14Pd
import io.github.kotlinmania.rustlang.x86.mmRolEpi64
import io.github.kotlinmania.rustlang.x86.mmRolvEpi64
import io.github.kotlinmania.rustlang.x86.mmRorEpi64
import io.github.kotlinmania.rustlang.x86.mmRorvEpi64
import io.github.kotlinmania.rustlang.x86.mmRoundscalePd
import io.github.kotlinmania.rustlang.x86.mmRsqrt14Pd
import io.github.kotlinmania.rustlang.x86.mmScalefPd
import io.github.kotlinmania.rustlang.x86.mmSet1Epi16
import io.github.kotlinmania.rustlang.x86.mmSet1Epi32
import io.github.kotlinmania.rustlang.x86.mmSet1Epi64x
import io.github.kotlinmania.rustlang.x86.mmSet1Epi8
import io.github.kotlinmania.rustlang.x86.mmSet1Pd
import io.github.kotlinmania.rustlang.x86.mmSet1Ps
import io.github.kotlinmania.rustlang.x86.mmSetEpi16
import io.github.kotlinmania.rustlang.x86.mmSetEpi32
import io.github.kotlinmania.rustlang.x86.mmSetEpi64x
import io.github.kotlinmania.rustlang.x86.mmSetEpi8
import io.github.kotlinmania.rustlang.x86.mmSetPd
import io.github.kotlinmania.rustlang.x86.mmSetPs
import io.github.kotlinmania.rustlang.x86.mmSetrEpi32
import io.github.kotlinmania.rustlang.x86.mmSetrPd
import io.github.kotlinmania.rustlang.x86.mmSetrPs
import io.github.kotlinmania.rustlang.x86.mmSetzeroPd
import io.github.kotlinmania.rustlang.x86.mmSetzeroPs
import io.github.kotlinmania.rustlang.x86.mmSetzeroSi128
import io.github.kotlinmania.rustlang.x86.mmSraEpi64
import io.github.kotlinmania.rustlang.x86.mmSraiEpi64
import io.github.kotlinmania.rustlang.x86.mmSravEpi64
import io.github.kotlinmania.rustlang.x86.mmStoreEpi64
import io.github.kotlinmania.rustlang.x86.mmStoreuEpi64
import io.github.kotlinmania.rustlang.x86.mmTernarylogicEpi64
import io.github.kotlinmania.rustlang.x86.mmTestEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmTestnEpi64Mask
import io.github.kotlinmania.rustlang.x86.mmUndefinedSi128
import io.github.kotlinmania.rustlang.x86.mmXorEpi64

public fun testMm512AbsEpi64() {
    val a = mm512SetEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val r = mm512AbsEpi64(a)
    val e = mm512SetEpi64(0L, 1L, 1L, Long.MAX_VALUE, (Long.MAX_VALUE + 1L), 100L, 100L, 32L)
    assertEqM512i(r, e)
}

public fun testMm512MaskAbsEpi64() {
    val a = mm512SetEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val r = mm512MaskAbsEpi64(a, 0u, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskAbsEpi64(a, 0xFFu, a)
    val e = mm512SetEpi64(0L, 1L, 1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, 100L, 32L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAbsEpi64() {
    val a = mm512SetEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val r = mm512MaskzAbsEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAbsEpi64(0xFFu, a)
    val e = mm512SetEpi64(0L, 1L, 1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, 100L, 32L)
    assertEqM512i(r1, e)
}

public fun testMm256AbsEpi64() {
    val a = mm256SetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val r = mm256AbsEpi64(a)
    val e = mm256SetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L), 100L, 100L)
    assertEqM256i(r, e)
}

public fun testMm256MaskAbsEpi64() {
    val a = mm256SetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val r = mm256MaskAbsEpi64(a, 0u, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskAbsEpi64(a, 0xFu, a)
    val e = mm256SetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L), 100L, 100L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAbsEpi64() {
    val a = mm256SetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val r = mm256MaskzAbsEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAbsEpi64(0xFu, a)
    val e = mm256SetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L), 100L, 100L)
    assertEqM256i(r1, e)
}

public fun testMmAbsEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val r = mmAbsEpi64(a)
    val e = mmSetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L))
    assertEqM128i(r, e)
    val a1 = mmSetEpi64x(100L, -100L)
    val r1 = mmAbsEpi64(a1)
    val e1 = mmSetEpi64x(100L, 100L)
    assertEqM128i(r1, e1)
}

public fun testMmMaskAbsEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val r = mmMaskAbsEpi64(a, 0u, a)
    assertEqM128i(r, a)
    val r1 = mmMaskAbsEpi64(a, 0x3u, a)
    val e = mmSetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L))
    assertEqM128i(r1, e)
    val a1 = mmSetEpi64x(100L, -100L)
    val r2 = mmMaskAbsEpi64(a1, 0x3u, a1)
    val e1 = mmSetEpi64x(100L, 100L)
    assertEqM128i(r2, e1)
}

public fun testMmMaskzAbsEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val r = mmMaskzAbsEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAbsEpi64(0x3u, a)
    val e = mmSetEpi64x(Long.MAX_VALUE, (Long.MAX_VALUE + 1L))
    assertEqM128i(r1, e)
    val a1 = mmSetEpi64x(100L, -100L)
    val r2 = mmMaskzAbsEpi64(0x3u, a1)
    val e1 = mmSetEpi64x(100L, 100L)
    assertEqM128i(r2, e1)
}

public fun testMm512AbsPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val r = mm512AbsPd(a)
    val e = mm512SetrPd(0.0, 1.0, 1.0, Double.MAX_VALUE, Double.MAX_VALUE, 100.0, 100.0, 32.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskAbsPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val r = mm512MaskAbsPd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskAbsPd(a, 0xFu, a)
    val e = mm512SetrPd(0.0, 1.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskMovEpi64() {
    val src = mm512Set1Epi64(1L)
    val a = mm512Set1Epi64(2L)
    val r = mm512MaskMovEpi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskMovEpi64(src, 0xFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm512MaskzMovEpi64() {
    val a = mm512Set1Epi64(2L)
    val r = mm512MaskzMovEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMovEpi64(0xFFu, a)
    assertEqM512i(r1, a)
}

public fun testMm256MaskMovEpi64() {
    val src = mm256Set1Epi64x(1L)
    val a = mm256Set1Epi64x(2L)
    val r = mm256MaskMovEpi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskMovEpi64(src, 0xFu, a)
    assertEqM256i(r1, a)
}

public fun testMm256MaskzMovEpi64() {
    val a = mm256Set1Epi64x(2L)
    val r = mm256MaskzMovEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMovEpi64(0xFu, a)
    assertEqM256i(r1, a)
}

public fun testMmMaskMovEpi64() {
    val src = mmSet1Epi64x(1L)
    val a = mmSet1Epi64x(2L)
    val r = mmMaskMovEpi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskMovEpi64(src, 0x3u, a)
    assertEqM128i(r1, a)
}

public fun testMmMaskzMovEpi64() {
    val a = mmSet1Epi64x(2L)
    val r = mmMaskzMovEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMovEpi64(0x3u, a)
    assertEqM128i(r1, a)
}

public fun testMm512MaskMovPd() {
    val src = mm512Set1Pd(1.0)
    val a = mm512Set1Pd(2.0)
    val r = mm512MaskMovPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskMovPd(src, 0xFFu, a)
    assertEqM512d(r1, a)
}

public fun testMm512MaskzMovPd() {
    val a = mm512Set1Pd(2.0)
    val r = mm512MaskzMovPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMovPd(0xFFu, a)
    assertEqM512d(r1, a)
}

public fun testMm256MaskMovPd() {
    val src = mm256Set1Pd(1.0)
    val a = mm256Set1Pd(2.0)
    val r = mm256MaskMovPd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskMovPd(src, 0xFu, a)
    assertEqM256d(r1, a)
}

public fun testMm256MaskzMovPd() {
    val a = mm256Set1Pd(2.0)
    val r = mm256MaskzMovPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzMovPd(0xFu, a)
    assertEqM256d(r1, a)
}

public fun testMmMaskMovPd() {
    val src = mmSet1Pd(1.0)
    val a = mmSet1Pd(2.0)
    val r = mmMaskMovPd(src, 0u, a)
    assertEqM128d(r, src)
    val r1 = mmMaskMovPd(src, 0x3u, a)
    assertEqM128d(r1, a)
}

public fun testMmMaskzMovPd() {
    val a = mmSet1Pd(2.0)
    val r = mmMaskzMovPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzMovPd(0x3u, a)
    assertEqM128d(r1, a)
}

public fun testMm512AddEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512AddEpi64(a, b)
    val e = mm512SetrEpi64(1L, 2L, 0L, Long.MIN_VALUE, Long.MIN_VALUE + 1L, 101L, -99L, -31L)
    assertEqM512i(r, e)
}

public fun testMm512MaskAddEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512MaskAddEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAddEpi64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(1L, 2L, 0L, Long.MIN_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAddEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512MaskzAddEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAddEpi64(0xFu, a, b)
    val e = mm512SetrEpi64(1L, 2L, 0L, Long.MIN_VALUE, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAddEpi64() {
    val a = mm256SetEpi64x(1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mm256Set1Epi64x(1L)
    val r = mm256MaskAddEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAddEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(2L, 0L, Long.MIN_VALUE, Long.MIN_VALUE + 1L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAddEpi64() {
    val a = mm256SetEpi64x(1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mm256Set1Epi64x(1L)
    val r = mm256MaskzAddEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAddEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(2L, 0L, Long.MIN_VALUE, Long.MIN_VALUE + 1L)
    assertEqM256i(r1, e)
}

public fun testMmMaskAddEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mmSet1Epi64x(1L)
    val r = mmMaskAddEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAddEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(Long.MIN_VALUE, Long.MIN_VALUE + 1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAddEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mmSet1Epi64x(1L)
    val r = mmMaskzAddEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAddEpi64(0x3u, a, b)
    val e = mmSetEpi64x(Long.MIN_VALUE, Long.MIN_VALUE + 1L)
    assertEqM128i(r1, e)
}

public fun testMm512AddPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512AddPd(a, b)
    val e = mm512SetrPd(1.0, 2.0, 0.0, Double.MAX_VALUE, (-Double.MAX_VALUE) + 1.0, 101.0, -99.0, -31.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskAddPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskAddPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskAddPd(a, 0xFu, a, b)
    val e = mm512SetrPd(1.0, 2.0, 0.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzAddPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskzAddPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzAddPd(0xFu, a, b)
    val e = mm512SetrPd(1.0, 2.0, 0.0, Double.MAX_VALUE, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskAddPd() {
    val a = mm256SetPd(1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(1.0)
    val r = mm256MaskAddPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskAddPd(a, 0xFu, a, b)
    val e = mm256SetPd(2.0, 0.0, Double.MAX_VALUE, (-Double.MAX_VALUE) + 1.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzAddPd() {
    val a = mm256SetPd(1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(1.0)
    val r = mm256MaskzAddPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzAddPd(0xFu, a, b)
    val e = mm256SetPd(2.0, 0.0, Double.MAX_VALUE, (-Double.MAX_VALUE) + 1.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskAddPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(1.0)
    val r = mmMaskAddPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskAddPd(a, 0x3u, a, b)
    val e = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE) + 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzAddPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(1.0)
    val r = mmMaskzAddPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzAddPd(0x3u, a, b)
    val e = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE) + 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512SubEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512SubEpi64(a, b)
    val e = mm512SetrEpi64(-1L, 0L, -2L, Long.MAX_VALUE - 1L, Long.MAX_VALUE, 99L, -101L, -33L)
    assertEqM512i(r, e)
}

public fun testMm512MaskSubEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512MaskSubEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskSubEpi64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(-1L, 0L, -2L, Long.MAX_VALUE - 1L, Long.MIN_VALUE, 100L, -100L, -32L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSubEpi64() {
    val a = mm512SetrEpi64(0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(1L)
    val r = mm512MaskzSubEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSubEpi64(0xFu, a, b)
    val e = mm512SetrEpi64(-1L, 0L, -2L, Long.MAX_VALUE - 1L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSubEpi64() {
    val a = mm256SetEpi64x(1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mm256Set1Epi64x(1L)
    val r = mm256MaskSubEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskSubEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(0L, -2L, Long.MAX_VALUE - 1L, Long.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSubEpi64() {
    val a = mm256SetEpi64x(1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mm256Set1Epi64x(1L)
    val r = mm256MaskzSubEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSubEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(0L, -2L, Long.MAX_VALUE - 1L, Long.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMmMaskSubEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mmSet1Epi64x(1L)
    val r = mmMaskSubEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskSubEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(Long.MAX_VALUE - 1L, Long.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSubEpi64() {
    val a = mmSetEpi64x(Long.MAX_VALUE, Long.MIN_VALUE)
    val b = mmSet1Epi64x(1L)
    val r = mmMaskzSubEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSubEpi64(0x3u, a, b)
    val e = mmSetEpi64x(Long.MAX_VALUE - 1L, Long.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512SubPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512SubPd(a, b)
    val e = mm512SetrPd(-1.0, 0.0, -2.0, Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE), 99.0, -101.0, -33.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskSubPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskSubPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskSubPd(a, 0xFu, a, b)
    val e = mm512SetrPd(-1.0, 0.0, -2.0, Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzSubPd() {
    val a = mm512SetrPd(0.0, 1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0, -32.0)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskzSubPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzSubPd(0xFu, a, b)
    val e = mm512SetrPd(-1.0, 0.0, -2.0, Double.MAX_VALUE - 1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskSubPd() {
    val a = mm256SetPd(1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(1.0)
    val r = mm256MaskSubPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskSubPd(a, 0xFu, a, b)
    val e = mm256SetPd(0.0, -2.0, Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE))
    assertEqM256d(r1, e)
}

public fun testMm256MaskzSubPd() {
    val a = mm256SetPd(1.0, -1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(1.0)
    val r = mm256MaskzSubPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzSubPd(0xFu, a, b)
    val e = mm256SetPd(0.0, -2.0, Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE))
    assertEqM256d(r1, e)
}

public fun testMmMaskSubPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(1.0)
    val r = mmMaskSubPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskSubPd(a, 0x3u, a, b)
    val e = mmSetPd(Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE))
    assertEqM128d(r1, e)
}

public fun testMmMaskzSubPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(1.0)
    val r = mmMaskzSubPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzSubPd(0x3u, a, b)
    val e = mmSetPd(Double.MAX_VALUE - 1.0, (-Double.MAX_VALUE))
    assertEqM128d(r1, e)
}

public fun testMm512MulEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MulEpi32(a, b)
    val e = mm512SetEpi64(15L, 13L, 11L, 9L, 7L, 5L, 3L, 1L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskMulEpi32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulEpi32(a, 0xFu, a, b)
    val e = mm512SetEpi64(1L or 1L shl 32, 1L or 1L shl 32, 1L or 1L shl 32, 1L or 1L shl 32, 7L, 5L, 3L, 1L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulEpi32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzMulEpi32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulEpi32(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 7L, 5L, 3L, 1L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskMulEpi32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulEpi32(a, 0xFu, a, b)
    val e = mm256SetEpi64x(2L, 4L, 6L, 8L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulEpi32() {
    val a = mm256Set1Epi32(1)
    val b = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzMulEpi32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulEpi32(0xFu, a, b)
    val e = mm256SetEpi64x(2L, 4L, 6L, 8L)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskMulEpi32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulEpi32(a, 0x3u, a, b)
    val e = mmSetEpi64x(2L, 4L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulEpi32() {
    val a = mmSet1Epi32(1)
    val b = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzMulEpi32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulEpi32(0x3u, a, b)
    val e = mmSetEpi64x(2L, 4L)
    assertEqM128i(r1, e)
}

public fun testMm512MulEpu32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MulEpu32(a, b)
    val e = mm512SetEpi64(15L, 13L, 11L, 9L, 7L, 5L, 3L, 1L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulEpu32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskMulEpu32(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulEpu32(a, 0xFu, a, b)
    val e = mm512SetEpi64(1L or 1L shl 32, 1L or 1L shl 32, 1L or 1L shl 32, 1L or 1L shl 32, 7L, 5L, 3L, 1L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMulEpu32() {
    val a = mm512Set1Epi32(1)
    val b = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm512MaskzMulEpu32(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMulEpu32(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 7L, 5L, 3L, 1L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskMulEpu32() {
    val a = mm256Set1Epi32(1)
    val b = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskMulEpu32(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMulEpu32(a, 0xFu, a, b)
    val e = mm256SetEpi64x(2L, 4L, 6L, 8L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMulEpu32() {
    val a = mm256Set1Epi32(1)
    val b = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256MaskzMulEpu32(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMulEpu32(0xFu, a, b)
    val e = mm256SetEpi64x(2L, 4L, 6L, 8L)
    assertEqM256i(r1, e)
}

public fun testMmMaskMulEpu32() {
    val a = mmSet1Epi32(1)
    val b = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskMulEpu32(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMulEpu32(a, 0x3u, a, b)
    val e = mmSetEpi64x(2L, 4L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMulEpu32() {
    val a = mmSet1Epi32(1)
    val b = mmSetEpi32(1, 2, 3, 4)
    val r = mmMaskzMulEpu32(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMulEpu32(0x3u, a, b)
    val e = mmSetEpi64x(2L, 4L)
    assertEqM128i(r1, e)
}

public fun testMm512MulloxEpi64() {
    val a = mm512SetrEpi64(0L, 1L, Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(2L)
    val r = mm512MulloxEpi64(a, b)
    val e = mm512SetrEpi64(0L, 2L, -2L, 0L, -2L, 200L, -200L, -64L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMulloxEpi64() {
    val a = mm512SetrEpi64(0L, 1L, Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE, 100L, -100L, -32L)
    val b = mm512Set1Epi64(2L)
    val r = mm512MaskMulloxEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMulloxEpi64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(0L, 2L, -2L, 0L, Long.MAX_VALUE, 100L, -100L, -32L)
    assertEqM512i(r1, e)
}

public fun testMm512MulPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512Set1Pd(2.0)
    val r = mm512MulPd(a, b)
    val e = mm512SetrPd(0.0, 2.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, -200.0, -64.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMulPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512Set1Pd(2.0)
    val r = mm512MaskMulPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskMulPd(a, 0xFu, a, b)
    val e = mm512SetrPd(0.0, 2.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMulPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512Set1Pd(2.0)
    val r = mm512MaskzMulPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMulPd(0xFu, a, b)
    val e = mm512SetrPd(0.0, 2.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskMulPd() {
    val a = mm256SetPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(2.0)
    val r = mm256MaskMulPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskMulPd(a, 0xFu, a, b)
    val e = mm256SetPd(0.0, 2.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzMulPd() {
    val a = mm256SetPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256Set1Pd(2.0)
    val r = mm256MaskzMulPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzMulPd(0xFu, a, b)
    val e = mm256SetPd(0.0, 2.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM256d(r1, e)
}

public fun testMmMaskMulPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(2.0)
    val r = mmMaskMulPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskMulPd(a, 0x3u, a, b)
    val e = mmSetPd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM128d(r1, e)
}

public fun testMmMaskzMulPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSet1Pd(2.0)
    val r = mmMaskzMulPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzMulPd(0x3u, a, b)
    val e = mmSetPd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM128d(r1, e)
}

public fun testMm512DivPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512SetrPd(2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0)
    val r = mm512DivPd(a, b)
    val e = mm512SetrPd(0.0, 0.5, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, -50.0, -16.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskDivPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512SetrPd(2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0)
    val r = mm512MaskDivPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskDivPd(a, 0xFu, a, b)
    val e = mm512SetrPd(0.0, 0.5, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzDivPd() {
    val a = mm512SetrPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.MAX_VALUE, (-Double.MAX_VALUE), -100.0, -32.0)
    val b = mm512SetrPd(2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0)
    val r = mm512MaskzDivPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzDivPd(0xFu, a, b)
    val e = mm512SetrPd(0.0, 0.5, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskDivPd() {
    val a = mm256SetPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256SetPd(2.0, 2.0, 0.0, 0.0)
    val r = mm256MaskDivPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskDivPd(a, 0xFu, a, b)
    val e = mm256SetPd(0.0, 0.5, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzDivPd() {
    val a = mm256SetPd(0.0, 1.0, Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mm256SetPd(2.0, 2.0, 0.0, 0.0)
    val r = mm256MaskzDivPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzDivPd(0xFu, a, b)
    val e = mm256SetPd(0.0, 0.5, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM256d(r1, e)
}

public fun testMmMaskDivPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSetPd(0.0, 0.0)
    val r = mmMaskDivPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskDivPd(a, 0x3u, a, b)
    val e = mmSetPd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM128d(r1, e)
}

public fun testMmMaskzDivPd() {
    val a = mmSetPd(Double.MAX_VALUE, (-Double.MAX_VALUE))
    val b = mmSetPd(0.0, 0.0)
    val r = mmMaskzDivPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzDivPd(0x3u, a, b)
    val e = mmSetPd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
    assertEqM128d(r1, e)
}

public fun testMm512MaxEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaxEpi64(a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 4L, 5L, 6L, 7L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskMaxEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpi64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 4L, 5L, 6L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskzMaxEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpi64(0xFu, a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaxEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaxEpi64(a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r, e)
}

public fun testMm256MaskMaxEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskMaxEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskzMaxEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r1, e)
}

public fun testMmMaxEpi64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaxEpi64(a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r, e)
}

public fun testMmMaskMaxEpi64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskMaxEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpi64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskzMaxEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpi64(0x3u, a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r1, e)
}

public fun testMm512MaxPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaxPd(a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMaxPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskMaxPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskMaxPd(a, 0xFu, a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMaxPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskzMaxPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMaxPd(0xFu, a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskMaxPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskMaxPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskMaxPd(a, 0xFu, a, b)
    val e = mm256SetPd(3.0, 2.0, 2.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzMaxPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskzMaxPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzMaxPd(0xFu, a, b)
    val e = mm256SetPd(3.0, 2.0, 2.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskMaxPd() {
    val a = mmSetPd(2.0, 3.0)
    val b = mmSetPd(3.0, 2.0)
    val r = mmMaskMaxPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskMaxPd(a, 0x3u, a, b)
    val e = mmSetPd(3.0, 3.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzMaxPd() {
    val a = mmSetPd(2.0, 3.0)
    val b = mmSetPd(3.0, 2.0)
    val r = mmMaskzMaxPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzMaxPd(0x3u, a, b)
    val e = mmSetPd(3.0, 3.0)
    assertEqM128d(r1, e)
}

public fun testMm512MaxEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaxEpu64(a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 4L, 5L, 6L, 7L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMaxEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskMaxEpu64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMaxEpu64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 4L, 5L, 6L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMaxEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskzMaxEpu64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMaxEpu64(0xFu, a, b)
    val e = mm512SetrEpi64(7L, 6L, 5L, 4L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaxEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaxEpu64(a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r, e)
}

public fun testMm256MaskMaxEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskMaxEpu64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMaxEpu64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMaxEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskzMaxEpu64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMaxEpu64(0xFu, a, b)
    val e = mm256SetEpi64x(3L, 2L, 2L, 3L)
    assertEqM256i(r1, e)
}

public fun testMmMaxEpu64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaxEpu64(a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r, e)
}

public fun testMmMaskMaxEpu64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskMaxEpu64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMaxEpu64(a, 0x3u, a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMaxEpu64() {
    val a = mmSetEpi64x(2L, 3L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskzMaxEpu64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMaxEpu64(0x3u, a, b)
    val e = mmSetEpi64x(3L, 3L)
    assertEqM128i(r1, e)
}

public fun testMm512MinEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MinEpi64(a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 3L, 2L, 1L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskMinEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpi64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskzMinEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpi64(0xFu, a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MinEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MinEpi64(a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskMinEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskMinEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskzMinEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmMinEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMinEpi64(a, b)
    val e = mmSetEpi64x(0L, 1L)
    assertEqM128i(r, e)
    val a1 = mmSetEpi64x(2L, 3L)
    val b1 = mmSetEpi64x(1L, 0L)
    val r1 = mmMinEpi64(a1, b1)
    val e1 = mmSetEpi64x(1L, 0L)
    assertEqM128i(r1, e1)
}

public fun testMmMaskMinEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskMinEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(0L, 1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(3L, 2L)
    val r = mmMaskzMinEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpi64(0x3u, a, b)
    val e = mmSetEpi64x(0L, 1L)
    assertEqM128i(r1, e)
}

public fun testMm512MinPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MinPd(a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMinPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskMinPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskMinPd(a, 0xFu, a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMinPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskzMinPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMinPd(0xFu, a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskMinPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskMinPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskMinPd(a, 0xFu, a, b)
    val e = mm256SetPd(0.0, 1.0, 1.0, 0.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzMinPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskzMinPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzMinPd(0xFu, a, b)
    val e = mm256SetPd(0.0, 1.0, 1.0, 0.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskMinPd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(1.0, 0.0)
    val r = mmMaskMinPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskMinPd(a, 0x3u, a, b)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzMinPd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSetPd(1.0, 0.0)
    val r = mmMaskzMinPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzMinPd(0x3u, a, b)
    val e = mmSetPd(0.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMm512MinEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MinEpu64(a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 3L, 2L, 1L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512MaskMinEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskMinEpu64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskMinEpu64(a, 0xFu, a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzMinEpu64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val b = mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L)
    val r = mm512MaskzMinEpu64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzMinEpu64(0xFu, a, b)
    val e = mm512SetrEpi64(0L, 1L, 2L, 3L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MinEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MinEpu64(a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskMinEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskMinEpu64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskMinEpu64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzMinEpu64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskzMinEpu64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzMinEpu64(0xFu, a, b)
    val e = mm256SetEpi64x(0L, 1L, 1L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmMinEpu64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(1L, 0L)
    val r = mmMinEpu64(a, b)
    val e = mmSetEpi64x(0L, 0L)
    assertEqM128i(r, e)
}

public fun testMmMaskMinEpu64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(1L, 0L)
    val r = mmMaskMinEpu64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskMinEpu64(a, 0x3u, a, b)
    val e = mmSetEpi64x(0L, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzMinEpu64() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(1L, 0L)
    val r = mmMaskzMinEpu64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzMinEpu64(0x3u, a, b)
    val e = mmSetEpi64x(0L, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512SqrtPd() {
    val a = mm512SetrPd(0.0, 1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0)
    val r = mm512SqrtPd(a)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskSqrtPd() {
    val a = mm512SetrPd(0.0, 1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0)
    val r = mm512MaskSqrtPd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskSqrtPd(a, 0xFu, a)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 16.0, 25.0, 36.0, 49.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzSqrtPd() {
    val a = mm512SetrPd(0.0, 1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 49.0)
    val r = mm512MaskzSqrtPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzSqrtPd(0xFu, a)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskSqrtPd() {
    val a = mm256SetPd(0.0, 1.0, 4.0, 9.0)
    val r = mm256MaskSqrtPd(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskSqrtPd(a, 0xFu, a)
    val e = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzSqrtPd() {
    val a = mm256SetPd(0.0, 1.0, 4.0, 9.0)
    val r = mm256MaskzSqrtPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzSqrtPd(0xFu, a)
    val e = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskSqrtPd() {
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskSqrtPd(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskSqrtPd(a, 0x3u, a)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzSqrtPd() {
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskzSqrtPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzSqrtPd(0x3u, a)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512FmaddPd() {
    val a = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val r = mm512FmaddPd(a, b, c)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFmaddPd() {
    val a = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val r = mm512MaskFmaddPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmaddPd(a, 0xFu, b, c)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmaddPd() {
    val a = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val r = mm512MaskzFmaddPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmaddPd(0xFu, a, b, c)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmaddPd() {
    val a = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FmaddPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmaddPd(a, b, c, 0xFu)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFmaddPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFmaddPd(a, 0xFu, b, c)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFmaddPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFmaddPd(0xFu, a, b, c)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FmaddPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FmaddPd(a, b, c, 0xFu)
    val e = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFmaddPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmaddPd(a, 0x3u, b, c)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFmaddPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFmaddPd(0x3u, a, b, c)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FmaddPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmaddPd(a, b, c, 0x3u)
    val e = mmSetPd(1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMm512FmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FmsubPd(a, b, c)
    val e = mm512SetrPd(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFmsubPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmsubPd(a, 0xFu, b, c)
    val e = mm512SetrPd(-1.0, 0.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFmsubPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmsubPd(0xFu, a, b, c)
    val e = mm512SetrPd(-1.0, 0.0, 1.0, 2.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FmsubPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmsubPd(a, b, c, 0xFu)
    val e = mm512SetrPd(-1.0, 0.0, 1.0, 2.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFmsubPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFmsubPd(a, 0xFu, b, c)
    val e = mm256SetPd(-1.0, 0.0, 1.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFmsubPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFmsubPd(0xFu, a, b, c)
    val e = mm256SetPd(-1.0, 0.0, 1.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FmsubPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FmsubPd(a, b, c, 0xFu)
    val e = mm256SetPd(-1.0, 0.0, 1.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFmsubPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmsubPd(a, 0x3u, b, c)
    val e = mmSetPd(-1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFmsubPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFmsubPd(0x3u, a, b, c)
    val e = mmSetPd(-1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FmsubPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmsubPd(a, b, c, 0x3u)
    val e = mmSetPd(-1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMm512FmaddsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FmaddsubPd(a, b, c)
    val e = mm512SetrPd(-1.0, 2.0, 1.0, 4.0, 3.0, 6.0, 5.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFmaddsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFmaddsubPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmaddsubPd(a, 0xFu, b, c)
    val e = mm512SetrPd(-1.0, 2.0, 1.0, 4.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmaddsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFmaddsubPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmaddsubPd(0xFu, a, b, c)
    val e = mm512SetrPd(-1.0, 2.0, 1.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmaddsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FmaddsubPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmaddsubPd(a, b, c, 0xFu)
    val e = mm512SetrPd(-1.0, 2.0, 1.0, 4.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFmaddsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFmaddsubPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFmaddsubPd(a, 0xFu, b, c)
    val e = mm256SetPd(1.0, 0.0, 3.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFmaddsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFmaddsubPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFmaddsubPd(0xFu, a, b, c)
    val e = mm256SetPd(1.0, 0.0, 3.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FmaddsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FmaddsubPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FmaddsubPd(a, b, c, 0xFu)
    val e = mm256SetPd(1.0, 0.0, 3.0, 2.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFmaddsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFmaddsubPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmaddsubPd(a, 0x3u, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmaddsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFmaddsubPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFmaddsubPd(0x3u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FmaddsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FmaddsubPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmaddsubPd(a, b, c, 0x3u)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMm512FmsubaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FmsubaddPd(a, b, c)
    val e = mm512SetrPd(1.0, 0.0, 3.0, 2.0, 5.0, 4.0, 7.0, 6.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFmsubaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFmsubaddPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmsubaddPd(a, 0xFu, b, c)
    val e = mm512SetrPd(1.0, 0.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmsubaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFmsubaddPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmsubaddPd(0xFu, a, b, c)
    val e = mm512SetrPd(1.0, 0.0, 3.0, 2.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmsubaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FmsubaddPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmsubaddPd(a, b, c, 0xFu)
    val e = mm512SetrPd(1.0, 0.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFmsubaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFmsubaddPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFmsubaddPd(a, 0xFu, b, c)
    val e = mm256SetPd(-1.0, 2.0, 1.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFmsubaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFmsubaddPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFmsubaddPd(0xFu, a, b, c)
    val e = mm256SetPd(-1.0, 2.0, 1.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FmsubaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FmsubaddPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FmsubaddPd(a, b, c, 0xFu)
    val e = mm256SetPd(-1.0, 2.0, 1.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFmsubaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFmsubaddPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFmsubaddPd(a, 0x3u, b, c)
    val e = mmSetPd(-1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFmsubaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFmsubaddPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFmsubaddPd(0x3u, a, b, c)
    val e = mmSetPd(-1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FmsubaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FmsubaddPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FmsubaddPd(a, b, c, 0x3u)
    val e = mmSetPd(-1.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMm512FnmaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FnmaddPd(a, b, c)
    val e = mm512SetrPd(1.0, 0.0, -1.0, -2.0, -3.0, -4.0, -5.0, -6.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFnmaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFnmaddPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFnmaddPd(a, 0xFu, b, c)
    val e = mm512SetrPd(1.0, 0.0, -1.0, -2.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFnmaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFnmaddPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFnmaddPd(0xFu, a, b, c)
    val e = mm512SetrPd(1.0, 0.0, -1.0, -2.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FnmaddPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FnmaddPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FnmaddPd(a, b, c, 0xFu)
    val e = mm512SetrPd(1.0, 0.0, -1.0, -2.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFnmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFnmaddPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFnmaddPd(a, 0xFu, b, c)
    val e = mm256SetPd(1.0, 0.0, -1.0, -2.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFnmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFnmaddPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFnmaddPd(0xFu, a, b, c)
    val e = mm256SetPd(1.0, 0.0, -1.0, -2.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FnmaddPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FnmaddPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FnmaddPd(a, b, c, 0xFu)
    val e = mm256SetPd(1.0, 0.0, -1.0, -2.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFnmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFnmaddPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmaddPd(a, 0x3u, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFnmaddPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFnmaddPd(0x3u, a, b, c)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FnmaddPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FnmaddPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmaddPd(a, b, c, 0x3u)
    val e = mmSetPd(1.0, 0.0)
    assertEqM128d(r1, e)
}

public fun testMm512FnmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FnmsubPd(a, b, c)
    val e = mm512SetrPd(-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFnmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFnmsubPd(a, 0u, b, c)
    assertEqM512d(r, a)
    val r1 = mm512MaskFnmsubPd(a, 0xFu, b, c)
    val e = mm512SetrPd(-1.0, -2.0, -3.0, -4.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFnmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFnmsubPd(0u, a, b, c)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFnmsubPd(0xFu, a, b, c)
    val e = mm512SetrPd(-1.0, -2.0, -3.0, -4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FnmsubPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val c = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0)
    val r = mm512Mask3FnmsubPd(a, b, c, 0u)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FnmsubPd(a, b, c, 0xFu)
    val e = mm512SetrPd(-1.0, -2.0, -3.0, -4.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskFnmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskFnmsubPd(a, 0u, b, c)
    assertEqM256d(r, a)
    val r1 = mm256MaskFnmsubPd(a, 0xFu, b, c)
    val e = mm256SetPd(-1.0, -2.0, -3.0, -4.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzFnmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256MaskzFnmsubPd(0u, a, b, c)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzFnmsubPd(0xFu, a, b, c)
    val e = mm256SetPd(-1.0, -2.0, -3.0, -4.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask3FnmsubPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val c = mm256Set1Pd(1.0)
    val r = mm256Mask3FnmsubPd(a, b, c, 0u)
    assertEqM256d(r, c)
    val r1 = mm256Mask3FnmsubPd(a, b, c, 0xFu)
    val e = mm256SetPd(-1.0, -2.0, -3.0, -4.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskFnmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskFnmsubPd(a, 0u, b, c)
    assertEqM128d(r, a)
    val r1 = mmMaskFnmsubPd(a, 0x3u, b, c)
    val e = mmSetPd(-1.0, -2.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzFnmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMaskzFnmsubPd(0u, a, b, c)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzFnmsubPd(0x3u, a, b, c)
    val e = mmSetPd(-1.0, -2.0)
    assertEqM128d(r1, e)
}

public fun testMmMask3FnmsubPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSetPd(0.0, 1.0)
    val c = mmSet1Pd(1.0)
    val r = mmMask3FnmsubPd(a, b, c, 0u)
    assertEqM128d(r, c)
    val r1 = mmMask3FnmsubPd(a, b, c, 0x3u)
    val e = mmSetPd(-1.0, -2.0)
    assertEqM128d(r1, e)
}

public fun testMm512Rcp14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512Rcp14Pd(a)
    val e = mm512Set1Pd(0.3333320617675781)
    assertEqM512d(r, e)
}

public fun testMm512MaskRcp14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskRcp14Pd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskRcp14Pd(a, 0xF0u, a)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 0.3333320617675781, 0.3333320617675781, 0.3333320617675781, 0.3333320617675781)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzRcp14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskzRcp14Pd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzRcp14Pd(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 0.3333320617675781, 0.3333320617675781, 0.3333320617675781, 0.3333320617675781)
    assertEqM512d(r1, e)
}

public fun testMm256Rcp14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256Rcp14Pd(a)
    val e = mm256Set1Pd(0.3333320617675781)
    assertEqM256d(r, e)
}

public fun testMm256MaskRcp14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskRcp14Pd(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskRcp14Pd(a, 0xFu, a)
    val e = mm256Set1Pd(0.3333320617675781)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzRcp14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskzRcp14Pd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzRcp14Pd(0xFu, a)
    val e = mm256Set1Pd(0.3333320617675781)
    assertEqM256d(r1, e)
}

public fun testMmRcp14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmRcp14Pd(a)
    val e = mmSet1Pd(0.3333320617675781)
    assertEqM128d(r, e)
}

public fun testMmMaskRcp14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskRcp14Pd(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskRcp14Pd(a, 0x3u, a)
    val e = mmSet1Pd(0.3333320617675781)
    assertEqM128d(r1, e)
}

public fun testMmMaskzRcp14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskzRcp14Pd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzRcp14Pd(0x3u, a)
    val e = mmSet1Pd(0.3333320617675781)
    assertEqM128d(r1, e)
}

public fun testMm512Rsqrt14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512Rsqrt14Pd(a)
    val e = mm512Set1Pd(0.5773391723632813)
    assertEqM512d(r, e)
}

public fun testMm512MaskRsqrt14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskRsqrt14Pd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskRsqrt14Pd(a, 0xF0u, a)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 0.5773391723632813, 0.5773391723632813, 0.5773391723632813, 0.5773391723632813)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzRsqrt14Pd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskzRsqrt14Pd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzRsqrt14Pd(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 0.5773391723632813, 0.5773391723632813, 0.5773391723632813, 0.5773391723632813)
    assertEqM512d(r1, e)
}

public fun testMm256Rsqrt14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256Rsqrt14Pd(a)
    val e = mm256Set1Pd(0.5773391723632813)
    assertEqM256d(r, e)
}

public fun testMm256MaskRsqrt14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskRsqrt14Pd(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskRsqrt14Pd(a, 0xFu, a)
    val e = mm256Set1Pd(0.5773391723632813)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzRsqrt14Pd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskzRsqrt14Pd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzRsqrt14Pd(0xFu, a)
    val e = mm256Set1Pd(0.5773391723632813)
    assertEqM256d(r1, e)
}

public fun testMmRsqrt14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmRsqrt14Pd(a)
    val e = mmSet1Pd(0.5773391723632813)
    assertEqM128d(r, e)
}

public fun testMmMaskRsqrt14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskRsqrt14Pd(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskRsqrt14Pd(a, 0x3u, a)
    val e = mmSet1Pd(0.5773391723632813)
    assertEqM128d(r1, e)
}

public fun testMmMaskzRsqrt14Pd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskzRsqrt14Pd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzRsqrt14Pd(0x3u, a)
    val e = mmSet1Pd(0.5773391723632813)
    assertEqM128d(r1, e)
}

public fun testMm512GetexpPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512GetexpPd(a)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskGetexpPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskGetexpPd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskGetexpPd(a, 0xF0u, a)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzGetexpPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskzGetexpPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzGetexpPd(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm256GetexpPd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256GetexpPd(a)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskGetexpPd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskGetexpPd(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskGetexpPd(a, 0xFu, a)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzGetexpPd() {
    val a = mm256Set1Pd(3.0)
    val r = mm256MaskzGetexpPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzGetexpPd(0xFu, a)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r1, e)
}

public fun testMmGetexpPd() {
    val a = mmSet1Pd(3.0)
    val r = mmGetexpPd(a)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskGetexpPd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskGetexpPd(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskGetexpPd(a, 0x3u, a)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzGetexpPd() {
    val a = mmSet1Pd(3.0)
    val r = mmMaskzGetexpPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzGetexpPd(0x3u, a)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r1, e)
}

public fun testMm512RoundscalePd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512RoundscalePd(a, 0x0)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskRoundscalePd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512MaskRoundscalePd(a, 0u, a, 0x0)
    val e = mm512Set1Pd(1.1)
    assertEqM512d(r, e)
    val r1 = mm512MaskRoundscalePd(a, 0xFFu, a, 0x0)
    val e1 = mm512Set1Pd(1.0)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskzRoundscalePd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512MaskzRoundscalePd(0u, a, 0x0)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzRoundscalePd(0xFFu, a, 0x0)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r1, e)
}

public fun testMm256RoundscalePd() {
    val a = mm256Set1Pd(1.1)
    val r = mm256RoundscalePd(a, 0x0)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskRoundscalePd() {
    val a = mm256Set1Pd(1.1)
    val r = mm256MaskRoundscalePd(a, 0u, a, 0x0)
    assertEqM256d(r, a)
    val r1 = mm256MaskRoundscalePd(a, 0xFu, a, 0x0)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzRoundscalePd() {
    val a = mm256Set1Pd(1.1)
    val r = mm256MaskzRoundscalePd(0u, a, 0x0)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzRoundscalePd(0xFu, a, 0x0)
    val e = mm256Set1Pd(1.0)
    assertEqM256d(r1, e)
}

public fun testMmRoundscalePd() {
    val a = mmSet1Pd(1.1)
    val r = mmRoundscalePd(a, 0x0)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r, e)
}

public fun testMmMaskRoundscalePd() {
    val a = mmSet1Pd(1.1)
    val r = mmMaskRoundscalePd(a, 0u, a, 0x0)
    val e = mmSet1Pd(1.1)
    assertEqM128d(r, e)
    val r1 = mmMaskRoundscalePd(a, 0x3u, a, 0x0)
    val e1 = mmSet1Pd(1.0)
    assertEqM128d(r1, e1)
}

public fun testMmMaskzRoundscalePd() {
    val a = mmSet1Pd(1.1)
    val r = mmMaskzRoundscalePd(0u, a, 0x0)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzRoundscalePd(0x3u, a, 0x0)
    val e = mmSet1Pd(1.0)
    assertEqM128d(r1, e)
}

public fun testMm512ScalefPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512ScalefPd(a, b)
    val e = mm512Set1Pd(8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskScalefPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskScalefPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskScalefPd(a, 0xF0u, a, b)
    val e = mm512SetPd(8.0, 8.0, 8.0, 8.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzScalefPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskzScalefPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzScalefPd(0xF0u, a, b)
    val e = mm512SetPd(8.0, 8.0, 8.0, 8.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256ScalefPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(3.0)
    val r = mm256ScalefPd(a, b)
    val e = mm256Set1Pd(8.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskScalefPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(3.0)
    val r = mm256MaskScalefPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskScalefPd(a, 0xFu, a, b)
    val e = mm256Set1Pd(8.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzScalefPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(3.0)
    val r = mm256MaskzScalefPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzScalefPd(0xFu, a, b)
    val e = mm256Set1Pd(8.0)
    assertEqM256d(r1, e)
}

public fun testMmScalefPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmScalefPd(a, b)
    val e = mmSet1Pd(8.0)
    assertEqM128d(r, e)
}

public fun testMmMaskScalefPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskScalefPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskScalefPd(a, 0x3u, a, b)
    val e = mmSet1Pd(8.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzScalefPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(3.0)
    val r = mmMaskzScalefPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzScalefPd(0x3u, a, b)
    val e = mmSet1Pd(8.0)
    assertEqM128d(r1, e)
}

public fun testMm512FixupimmPd() {
    val a = mm512Set1Pd(Double.NaN)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512FixupimmPd(a, b, c, 5)
    val e = mm512Set1Pd(0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFixupimmPd() {
    val a = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 1.0, 1.0, 1.0, 1.0)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512MaskFixupimmPd(a, 0xF0u, b, c, 5)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzFixupimmPd() {
    val a = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 1.0, 1.0, 1.0, 1.0)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512MaskzFixupimmPd(0xF0u, a, b, c, 5)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm256FixupimmPd() {
    val a = mm256Set1Pd(Double.NaN)
    val b = mm256Set1Pd(Double.MAX_VALUE)
    val c = mm256Set1Epi64x(Int.MAX_VALUE.toLong())
    val r = mm256FixupimmPd(a, b, c, 5)
    val e = mm256Set1Pd(0.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskFixupimmPd() {
    val a = mm256Set1Pd(Double.NaN)
    val b = mm256Set1Pd(Double.MAX_VALUE)
    val c = mm256Set1Epi64x(Int.MAX_VALUE.toLong())
    val r = mm256MaskFixupimmPd(a, 0xFu, b, c, 5)
    val e = mm256Set1Pd(0.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskzFixupimmPd() {
    val a = mm256Set1Pd(Double.NaN)
    val b = mm256Set1Pd(Double.MAX_VALUE)
    val c = mm256Set1Epi64x(Int.MAX_VALUE.toLong())
    val r = mm256MaskzFixupimmPd(0xFu, a, b, c, 5)
    val e = mm256Set1Pd(0.0)
    assertEqM256d(r, e)
}

public fun testMmFixupimmPd() {
    val a = mmSet1Pd(Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmFixupimmPd(a, b, c, 5)
    val e = mmSet1Pd(0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskFixupimmPd() {
    val a = mmSet1Pd(Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskFixupimmPd(a, 0x3u, b, c, 5)
    val e = mmSet1Pd(0.0)
    assertEqM128d(r, e)
}

public fun testMmMaskzFixupimmPd() {
    val a = mmSet1Pd(Double.NaN)
    val b = mmSet1Pd(Double.MAX_VALUE)
    val c = mmSet1Epi64x(Int.MAX_VALUE.toLong())
    val r = mmMaskzFixupimmPd(0x3u, a, b, c, 5)
    val e = mmSet1Pd(0.0)
    assertEqM128d(r, e)
}

public fun testMm512TernarylogicEpi64() {
    val a = mm512Set1Epi64(1L shl 2)
    val b = mm512Set1Epi64(1L shl 1)
    val c = mm512Set1Epi64(1L shl 0)
    val r = mm512TernarylogicEpi64(a, b, c, 8)
    val e = mm512Set1Epi64(0L)
    assertEqM512i(r, e)
}

public fun testMm512MaskTernarylogicEpi64() {
    val src = mm512Set1Epi64(1L shl 2)
    val a = mm512Set1Epi64(1L shl 1)
    val b = mm512Set1Epi64(1L shl 0)
    val r = mm512MaskTernarylogicEpi64(src, 0u, a, b, 8)
    assertEqM512i(r, src)
    val r1 = mm512MaskTernarylogicEpi64(src, 0xFFu, a, b, 8)
    val e = mm512Set1Epi64(0L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzTernarylogicEpi64() {
    val a = mm512Set1Epi64(1L shl 2)
    val b = mm512Set1Epi64(1L shl 1)
    val c = mm512Set1Epi64(1L shl 0)
    val r = mm512MaskzTernarylogicEpi64(0u, a, b, c, 8)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzTernarylogicEpi64(0xFFu, a, b, c, 8)
    val e = mm512Set1Epi64(0L)
    assertEqM512i(r1, e)
}

public fun testMm256TernarylogicEpi64() {
    val a = mm256Set1Epi64x(1L shl 2)
    val b = mm256Set1Epi64x(1L shl 1)
    val c = mm256Set1Epi64x(1L shl 0)
    val r = mm256TernarylogicEpi64(a, b, c, 8)
    val e = mm256Set1Epi64x(0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskTernarylogicEpi64() {
    val src = mm256Set1Epi64x(1L shl 2)
    val a = mm256Set1Epi64x(1L shl 1)
    val b = mm256Set1Epi64x(1L shl 0)
    val r = mm256MaskTernarylogicEpi64(src, 0u, a, b, 8)
    assertEqM256i(r, src)
    val r1 = mm256MaskTernarylogicEpi64(src, 0xFu, a, b, 8)
    val e = mm256Set1Epi64x(0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzTernarylogicEpi64() {
    val a = mm256Set1Epi64x(1L shl 2)
    val b = mm256Set1Epi64x(1L shl 1)
    val c = mm256Set1Epi64x(1L shl 0)
    val r = mm256MaskzTernarylogicEpi64(0u, a, b, c, 9)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzTernarylogicEpi64(0xFu, a, b, c, 8)
    val e = mm256Set1Epi64x(0L)
    assertEqM256i(r1, e)
}

public fun testMmTernarylogicEpi64() {
    val a = mmSet1Epi64x(1L shl 2)
    val b = mmSet1Epi64x(1L shl 1)
    val c = mmSet1Epi64x(1L shl 0)
    val r = mmTernarylogicEpi64(a, b, c, 8)
    val e = mmSet1Epi64x(0L)
    assertEqM128i(r, e)
}

public fun testMmMaskTernarylogicEpi64() {
    val src = mmSet1Epi64x(1L shl 2)
    val a = mmSet1Epi64x(1L shl 1)
    val b = mmSet1Epi64x(1L shl 0)
    val r = mmMaskTernarylogicEpi64(src, 0u, a, b, 8)
    assertEqM128i(r, src)
    val r1 = mmMaskTernarylogicEpi64(src, 0x3u, a, b, 8)
    val e = mmSet1Epi64x(0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzTernarylogicEpi64() {
    val a = mmSet1Epi64x(1L shl 2)
    val b = mmSet1Epi64x(1L shl 1)
    val c = mmSet1Epi64x(1L shl 0)
    val r = mmMaskzTernarylogicEpi64(0u, a, b, c, 9)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzTernarylogicEpi64(0x3u, a, b, c, 8)
    val e = mmSet1Epi64x(0L)
    assertEqM128i(r1, e)
}

public fun testMm512GetmantPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512GetmantPd<MmMantNorm12, MmMantSignSrc>(a)
    val e = mm512Set1Pd(1.25)
    assertEqM512d(r, e)
}

public fun testMm512MaskGetmantPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512MaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0xF0u, a)
    val e = mm512SetrPd(10.0, 10.0, 10.0, 10.0, 1.25, 1.25, 1.25, 1.25)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzGetmantPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512MaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.25, 1.25, 1.25, 1.25)
    assertEqM512d(r1, e)
}

public fun testMm256GetmantPd() {
    val a = mm256Set1Pd(10.0)
    val r = mm256GetmantPd<MmMantNorm12, MmMantSignSrc>(a)
    val e = mm256Set1Pd(1.25)
    assertEqM256d(r, e)
}

public fun testMm256MaskGetmantPd() {
    val a = mm256Set1Pd(10.0)
    val r = mm256MaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0xFu, a)
    val e = mm256Set1Pd(1.25)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzGetmantPd() {
    val a = mm256Set1Pd(10.0)
    val r = mm256MaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0xFu, a)
    val e = mm256Set1Pd(1.25)
    assertEqM256d(r1, e)
}

public fun testMmGetmantPd() {
    val a = mmSet1Pd(10.0)
    val r = mmGetmantPd<MmMantNorm12, MmMantSignSrc>(a)
    val e = mmSet1Pd(1.25)
    assertEqM128d(r, e)
}

public fun testMmMaskGetmantPd() {
    val a = mmSet1Pd(10.0)
    val r = mmMaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskGetmantPd<MmMantNorm12, MmMantSignSrc>(a, 0x3u, a)
    val e = mmSet1Pd(1.25)
    assertEqM128d(r1, e)
}

public fun testMmMaskzGetmantPd() {
    val a = mmSet1Pd(10.0)
    val r = mmMaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzGetmantPd<MmMantNorm12, MmMantSignSrc>(0x3u, a)
    val e = mmSet1Pd(1.25)
    assertEqM128d(r1, e)
}

public fun testMm512CvtpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val r = mm512CvtpsPd(a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val src = mm512Set1Pd(0.0)
    val r = mm512MaskCvtpsPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtpsPd(src, 0xFu, a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzCvtpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val r = mm512MaskzCvtpsPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzCvtpsPd(0xFu, a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512CvtpsloPd() {
    val v2 = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f)
    val r = mm512CvtpsloPd(v2)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtpsloPd() {
    val v2 = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f)
    val src = mm512Set1Pd(0.0)
    val r = mm512MaskCvtpsloPd(src, 0u, v2)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtpsloPd(src, 0xFu, v2)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512CvtpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtpdPs(a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Ps(0.0f)
    val r = mm512MaskCvtpdPs(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm512MaskCvtpdPs(src, 0xFu, a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMm512MaskzCvtpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvtpdPs(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm512MaskzCvtpdPs(0xFu, a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMm256MaskCvtpdPs() {
    val a = mm256SetPd(4.0, -5.5, 6.0, -7.5)
    val src = mmSet1Ps(0.0f)
    val r = mm256MaskCvtpdPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mm256MaskCvtpdPs(src, 0xFu, a)
    val e = mmSetPs(4.0f, -5.5f, 6.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMm256MaskzCvtpdPs() {
    val a = mm256SetPd(4.0, -5.5, 6.0, -7.5)
    val r = mm256MaskzCvtpdPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mm256MaskzCvtpdPs(0xFu, a)
    val e = mmSetPs(4.0f, -5.5f, 6.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMmMaskCvtpdPs() {
    val a = mmSetPd(6.0, -7.5)
    val src = mmSet1Ps(0.0f)
    val r = mmMaskCvtpdPs(src, 0u, a)
    assertEqM128(r, src)
    val r1 = mmMaskCvtpdPs(src, 0x3u, a)
    val e = mmSetPs(0.0f, 0.0f, 6.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMmMaskzCvtpdPs() {
    val a = mmSetPd(6.0, -7.5)
    val r = mmMaskzCvtpdPs(0u, a)
    assertEqM128(r, mmSetzeroPs())
    val r1 = mmMaskzCvtpdPs(0x3u, a)
    val e = mmSetPs(0.0f, 0.0f, 6.0f, -7.5f)
    assertEqM128(r1, e)
}

public fun testMm512CvtpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtpdEpi32(a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvtpdEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtpdEpi32(src, 0xFFu, a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvtpdEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtpdEpi32(0xFFu, a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8)
    assertEqM256i(r1, e)
}

public fun testMm256MaskCvtpdEpi32() {
    val a = mm256SetPd(4.0, -5.5, 6.0, -7.5)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvtpdEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtpdEpi32(src, 0xFu, a)
    val e = mmSetEpi32(4, -6, 6, -8)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtpdEpi32() {
    val a = mm256SetPd(4.0, -5.5, 6.0, -7.5)
    val r = mm256MaskzCvtpdEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtpdEpi32(0xFu, a)
    val e = mmSetEpi32(4, -6, 6, -8)
    assertEqM128i(r1, e)
}

public fun testMmMaskCvtpdEpi32() {
    val a = mmSetPd(6.0, -7.5)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtpdEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtpdEpi32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 6, -8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtpdEpi32() {
    val a = mmSetPd(6.0, -7.5)
    val r = mmMaskzCvtpdEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtpdEpi32(0x3u, a)
    val e = mmSetEpi32(0, 0, 6, -8)
    assertEqM128i(r1, e)
}

public fun testMm512CvtpdEpu32() {
    val a = mm512SetrPd(0.0, 1.5, 2.0, 3.5, 4.0, 5.5, 6.0, 7.5)
    val r = mm512CvtpdEpu32(a)
    val e = mm256SetrEpi32(0, 2, 2, 4, 4, 6, 6, 8)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtpdEpu32() {
    val a = mm512SetrPd(0.0, 1.5, 2.0, 3.5, 4.0, 5.5, 6.0, 7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvtpdEpu32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtpdEpu32(src, 0xFFu, a)
    val e = mm256SetrEpi32(0, 2, 2, 4, 4, 6, 6, 8)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtpdEpu32() {
    val a = mm512SetrPd(0.0, 1.5, 2.0, 3.5, 4.0, 5.5, 6.0, 7.5)
    val r = mm512MaskzCvtpdEpu32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtpdEpu32(0xFFu, a)
    val e = mm256SetrEpi32(0, 2, 2, 4, 4, 6, 6, 8)
    assertEqM256i(r1, e)
}

public fun testMm256CvtpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val r = mm256CvtpdEpu32(a)
    val e = mmSetEpi32(4, 6, 6, 8)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvtpdEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtpdEpu32(src, 0xFu, a)
    val e = mmSetEpi32(4, 6, 6, 8)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val r = mm256MaskzCvtpdEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtpdEpu32(0xFu, a)
    val e = mmSetEpi32(4, 6, 6, 8)
    assertEqM128i(r1, e)
}

public fun testMmCvtpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val r = mmCvtpdEpu32(a)
    val e = mmSetEpi32(0, 0, 6, 8)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtpdEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtpdEpu32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 6, 8)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val r = mmMaskzCvtpdEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtpdEpu32(0x3u, a)
    val e = mmSetEpi32(0, 0, 6, 8)
    assertEqM128i(r1, e)
}

public fun testMm512CvtpdPslo() {
    val v2 = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtpdPslo(v2)
    val e = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtpdPslo() {
    val v2 = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm512Set1Ps(0.0f)
    val r = mm512MaskCvtpdPslo(src, 0u, v2)
    assertEqM512(r, src)
    val r1 = mm512MaskCvtpdPslo(src, 0xFu, v2)
    val e = mm512SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM512(r1, e)
}

public fun testMm512Cvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi8Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepi8Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi8Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi8Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi8Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepi8Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi8Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepi8Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi8Epi64(0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi64x(-1L)
    val r = mmMaskCvtepi8Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi8Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepi8Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi8Epi64(0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu8Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepu8Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu8Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu8Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu8Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepu8Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu8Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepu8Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu8Epi64(0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi64x(-1L)
    val r = mmMaskCvtepu8Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu8Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu8Epi64() {
    val a = mmSetEpi8(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepu8Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu8Epi64(0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi16Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepi16Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi16Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi16Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi16Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepi16Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi16Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepi16Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi16Epi64(0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi64x(-1L)
    val r = mmMaskCvtepi16Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi16Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepi16Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi16Epi64(0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu16Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepu16Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu16Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu16Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu16Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepu16Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu16Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256MaskzCvtepu16Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu16Epi64(0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mmSet1Epi64x(-1L)
    val r = mmMaskCvtepu16Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu16Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu16Epi64() {
    val a = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mmMaskzCvtepu16Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu16Epi64(0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepi32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepi32Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepi32Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepi32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi32Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepi32Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepi32Epi64() {
    val a = mmSetEpi32(8, 9, 10, 11)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepi32Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepi32Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(8L, 9L, 10L, 11L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepi32Epi64() {
    val a = mmSetEpi32(8, 9, 10, 11)
    val r = mm256MaskzCvtepi32Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepi32Epi64(0xFu, a)
    val e = mm256SetEpi64x(8L, 9L, 10L, 11L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepi32Epi64() {
    val a = mmSetEpi32(8, 9, 10, 11)
    val src = mmSet1Epi64x(0L)
    val r = mmMaskCvtepi32Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi32Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(10L, 11L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi32Epi64() {
    val a = mmSetEpi32(8, 9, 10, 11)
    val r = mmMaskzCvtepi32Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi32Epi64(0x3u, a)
    val e = mmSetEpi64x(10L, 11L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepu32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu32Epi64(a)
    val e = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    assertEqM512i(r, e)
}

public fun testMm512MaskCvtepu32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Epi64(-1L)
    val r = mm512MaskCvtepu32Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCvtepu32Epi64(src, 0xFu, a)
    val e = mm512SetEpi64(-1L, -1L, -1L, -1L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCvtepu32Epi64() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu32Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCvtepu32Epi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 12L, 13L, 14L, 15L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCvtepu32Epi64() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mm256Set1Epi64x(-1L)
    val r = mm256MaskCvtepu32Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCvtepu32Epi64(src, 0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCvtepu32Epi64() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mm256MaskzCvtepu32Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCvtepu32Epi64(0xFu, a)
    val e = mm256SetEpi64x(12L, 13L, 14L, 15L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCvtepu32Epi64() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mmSet1Epi64x(-1L)
    val r = mmMaskCvtepu32Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepu32Epi64(src, 0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepu32Epi64() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mmMaskzCvtepu32Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepu32Epi64(0x3u, a)
    val e = mmSetEpi64x(14L, 15L)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32Pd(a)
    val e = mm512SetPd(8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepi32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Pd(-1.0)
    val r = mm512MaskCvtepi32Pd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtepi32Pd(src, 0xFu, a)
    val e = mm512SetPd(-1.0, -1.0, -1.0, -1.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzCvtepi32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepi32Pd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzCvtepi32Pd(0xFu, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskCvtepi32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mm256Set1Pd(-1.0)
    val r = mm256MaskCvtepi32Pd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskCvtepi32Pd(src, 0xFu, a)
    val e = mm256SetPd(12.0, 13.0, 14.0, 15.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzCvtepi32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mm256MaskzCvtepi32Pd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzCvtepi32Pd(0xFu, a)
    val e = mm256SetPd(12.0, 13.0, 14.0, 15.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskCvtepi32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mmSet1Pd(-1.0)
    val r = mmMaskCvtepi32Pd(src, 0u, a)
    assertEqM128d(r, src)
    val r1 = mmMaskCvtepi32Pd(src, 0x3u, a)
    val e = mmSetPd(14.0, 15.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzCvtepi32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mmMaskzCvtepi32Pd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzCvtepi32Pd(0x3u, a)
    val e = mmSetPd(14.0, 15.0)
    assertEqM128d(r1, e)
}

public fun testMm512Cvtepu32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu32Pd(a)
    val e = mm512SetPd(8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepu32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Pd(-1.0)
    val r = mm512MaskCvtepu32Pd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtepu32Pd(src, 0xFu, a)
    val e = mm512SetPd(-1.0, -1.0, -1.0, -1.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzCvtepu32Pd() {
    val a = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512MaskzCvtepu32Pd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzCvtepu32Pd(0xFu, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm256Cvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mm256Cvtepu32Pd(a)
    val e = mm256SetPd(12.0, 13.0, 14.0, 15.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskCvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mm256Set1Pd(-1.0)
    val r = mm256MaskCvtepu32Pd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskCvtepu32Pd(src, 0xFu, a)
    val e = mm256SetPd(12.0, 13.0, 14.0, 15.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzCvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mm256MaskzCvtepu32Pd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzCvtepu32Pd(0xFu, a)
    val e = mm256SetPd(12.0, 13.0, 14.0, 15.0)
    assertEqM256d(r1, e)
}

public fun testMmCvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mmCvtepu32Pd(a)
    val e = mmSetPd(14.0, 15.0)
    assertEqM128d(r, e)
}

public fun testMmMaskCvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val src = mmSet1Pd(-1.0)
    val r = mmMaskCvtepu32Pd(src, 0u, a)
    assertEqM128d(r, src)
    val r1 = mmMaskCvtepu32Pd(src, 0x3u, a)
    val e = mmSetPd(14.0, 15.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzCvtepu32Pd() {
    val a = mmSetEpi32(12, 13, 14, 15)
    val r = mmMaskzCvtepu32Pd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzCvtepu32Pd(0x3u, a)
    val e = mmSetPd(14.0, 15.0)
    assertEqM128d(r1, e)
}

public fun testMm512Cvtepi32loPd() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepi32loPd(a)
    val e = mm512SetPd(8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepi32loPd() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Pd(-1.0)
    val r = mm512MaskCvtepi32loPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtepi32loPd(src, 0xFu, a)
    val e = mm512SetPd(-1.0, -1.0, -1.0, -1.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm512Cvtepu32loPd() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm512Cvtepu32loPd(a)
    val e = mm512SetPd(8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtepu32loPd() {
    val a = mm512SetEpi32(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val src = mm512Set1Pd(-1.0)
    val r = mm512MaskCvtepu32loPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtepu32loPd(src, 0xFu, a)
    val e = mm512SetPd(-1.0, -1.0, -1.0, -1.0, 12.0, 13.0, 14.0, 15.0)
    assertEqM512d(r1, e)
}

public fun testMm512Cvtepi64Epi32() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512Cvtepi64Epi32(a)
    val e = mm256SetEpi32(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtepi64Epi32() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val src = mm256Set1Epi32(-1)
    val r = mm512MaskCvtepi64Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtepi64Epi32(src, 0xFu, a)
    val e = mm256SetEpi32(-1, -1, -1, -1, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtepi64Epi32() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512MaskzCvtepi64Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtepi64Epi32(0xFu, a)
    val e = mm256SetEpi32(0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtepi64Epi32() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Cvtepi64Epi32(a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi64Epi32() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvtepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi64Epi32(src, 0xFu, a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi64Epi32() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val r = mm256MaskzCvtepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi64Epi32(0xFu, a)
    val e = mmSetEpi32(1, 2, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi64Epi32() {
    val a = mmSetEpi64x(3L, 4L)
    val r = mmCvtepi64Epi32(a)
    val e = mmSetEpi32(0, 0, 3, 4)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64Epi32() {
    val a = mmSetEpi64x(3L, 4L)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi64Epi32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi64Epi32() {
    val a = mmSetEpi64x(3L, 4L)
    val r = mmMaskzCvtepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi64Epi32(0x3u, a)
    val e = mmSetEpi32(0, 0, 3, 4)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi64Epi16() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512Cvtepi64Epi16(a)
    val e = mmSetEpi16(8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi64Epi16() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val src = mmSet1Epi16((-1).toShort())
    val r = mm512MaskCvtepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtepi64Epi16(src, 0xFu, a)
    val e = mmSetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtepi64Epi16() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512MaskzCvtepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtepi64Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtepi64Epi16() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val r = mm256Cvtepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi64Epi16() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi64Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi64Epi16() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val r = mm256MaskzCvtepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi64Epi16(0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi64Epi16() {
    val a = mmSetEpi64x(14L, 15L)
    val r = mmCvtepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64Epi16() {
    val a = mmSetEpi64x(14L, 15L)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi64Epi16(src, 0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi64Epi16() {
    val a = mmSetEpi64x(14L, 15L)
    val r = mmMaskzCvtepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi64Epi16(0xFFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtepi64Epi8() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512Cvtepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi64Epi8() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    val r = mm512MaskCvtepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtepi64Epi8() {
    val a = mm512SetEpi64(8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L)
    val r = mm512MaskzCvtepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtepi64Epi8() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val r = mm256Cvtepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi64Epi8() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtepi64Epi8() {
    val a = mm256SetEpi64x(12L, 13L, 14L, 15L)
    val r = mm256MaskzCvtepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmCvtepi64Epi8() {
    val a = mmSetEpi64x(14L, 15L)
    val r = mmCvtepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64Epi8() {
    val a = mmSetEpi64x(14L, 15L)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtepi64Epi8(src, 0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtepi64Epi8() {
    val a = mmSetEpi64x(14L, 15L)
    val r = mmMaskzCvtepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtepi64Epi8(0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512Cvtsepi64Epi32(a)
    val e = mm256SetEpi32(0, 1, 2, 3, 4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtsepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mm256Set1Epi32(-1)
    val r = mm512MaskCvtsepi64Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtsepi64Epi32(src, 0xFu, a)
    val e = mm256SetEpi32(-1, -1, -1, -1, 4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtsepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512MaskzCvtsepi64Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtsepi64Epi32(0xFu, a)
    val e = mm256SetEpi32(0, 0, 0, 0, 4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtsepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256Cvtsepi64Epi32(a)
    val e = mmSetEpi32(4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi32(-1)
    val r = mm256MaskCvtsepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi64Epi32(src, 0xFu, a)
    val e = mmSetEpi32(4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256MaskzCvtsepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi64Epi32(0xFu, a)
    val e = mmSetEpi32(4, 5, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi64Epi32() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmCvtsepi64Epi32(a)
    val e = mmSetEpi32(0, 0, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64Epi32() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtsepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi64Epi32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi64Epi32() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmMaskzCvtsepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi64Epi32(0x3u, a)
    val e = mmSetEpi32(0, 0, Int.MIN_VALUE, Int.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512Cvtsepi64Epi16(a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi16((-1).toShort())
    val r = mm512MaskCvtsepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtsepi64Epi16(src, 0xFu, a)
    val e = mmSetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtsepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512MaskzCvtsepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtsepi64Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtsepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256Cvtsepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtsepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi64Epi16(src, 0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256MaskzCvtsepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi64Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi64Epi16() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmCvtsepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64Epi16() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtsepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi64Epi16(src, 0x3u, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi64Epi16() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmMaskzCvtsepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi64Epi16(0x3u, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, Short.MIN_VALUE, Short.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtsepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512Cvtsepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    val r = mm512MaskCvtsepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtsepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtsepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm512MaskzCvtsepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtsepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256Cvtsepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256Cvtsepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtsepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtsepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtsepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mm256MaskzCvtsepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtsepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmCvtsepi64Epi8() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmCvtsepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64Epi8() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtsepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtsepi64Epi8(src, 0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtsepi64Epi8() {
    val a = mmSetEpi64x(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = mmMaskzCvtsepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtsepi64Epi8(0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r1, e)
}

public fun testMm512Cvtusepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512Cvtusepi64Epi32(a)
    val e = mm256SetEpi32(0, 1, 2, 3, 4, 5, -1, -1)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtusepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val src = mm256Set1Epi32(-1)
    val r = mm512MaskCvtusepi64Epi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtusepi64Epi32(src, 0xFu, a)
    val e = mm256SetEpi32(-1, -1, -1, -1, 4, 5, -1, -1)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtusepi64Epi32() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512MaskzCvtusepi64Epi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtusepi64Epi32(0xFu, a)
    val e = mm256SetEpi32(0, 0, 0, 0, 4, 5, -1, -1)
    assertEqM256i(r1, e)
}

public fun testMm256Cvtusepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256Cvtusepi64Epi32(a)
    val e = mmSetEpi32(4, 5, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvtusepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi64Epi32(src, 0xFu, a)
    val e = mmSetEpi32(4, 5, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi64Epi32() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256MaskzCvtusepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi64Epi32(0xFu, a)
    val e = mmSetEpi32(4, 5, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi64Epi32() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmCvtusepi64Epi32(a)
    val e = mmSetEpi32(0, 0, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64Epi32() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvtusepi64Epi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi64Epi32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi64Epi32() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmMaskzCvtusepi64Epi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi64Epi32(0x3u, a)
    val e = mmSetEpi32(0, 0, 6, UInt.MAX_VALUE.toInt())
    assertEqM128i(r1, e)
}

public fun testMm512Cvtusepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512Cvtusepi64Epi16(a)
    val e = mmSetEpi16(0, 1, 2, 3, 4, 5, (-1).toShort(), (-1).toShort())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val src = mmSet1Epi16((-1).toShort())
    val r = mm512MaskCvtusepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtusepi64Epi16(src, 0xFu, a)
    val e = mmSetEpi16((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 4, 5, (-1).toShort(), (-1).toShort())
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtusepi64Epi16() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512MaskzCvtusepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtusepi64Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, (-1).toShort(), (-1).toShort())
    assertEqM128i(r1, e)
}

public fun testMm256Cvtusepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256Cvtusepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val src = mmSet1Epi16(0)
    val r = mm256MaskCvtusepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi64Epi16(src, 0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi64Epi16() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256MaskzCvtusepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi64Epi16(0xFu, a)
    val e = mmSetEpi16(0, 0, 0, 0, 4, 5, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi64Epi16() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmCvtusepi64Epi16(a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64Epi16() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val src = mmSet1Epi16(0)
    val r = mmMaskCvtusepi64Epi16(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi64Epi16(src, 0x3u, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi64Epi16() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmMaskzCvtusepi64Epi16(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi64Epi16(0x3u, a)
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 6, UShort.MAX_VALUE.toShort())
    assertEqM128i(r1, e)
}

public fun testMm512Cvtusepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512Cvtusepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, (-1).toByte(), (-1).toByte())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val src = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte())
    val r = mm512MaskCvtusepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm512MaskCvtusepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 4, 5, (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm512MaskzCvtusepi64Epi8() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, Long.MIN_VALUE, Long.MIN_VALUE)
    val r = mm512MaskzCvtusepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm512MaskzCvtusepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, (-1).toByte(), (-1).toByte())
    assertEqM128i(r1, e)
}

public fun testMm256Cvtusepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256Cvtusepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mm256MaskCvtusepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvtusepi64Epi8(src, 0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvtusepi64Epi8() {
    val a = mm256SetEpi64x(4L, 5L, 6L, Long.MAX_VALUE)
    val r = mm256MaskzCvtusepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvtusepi64Epi8(0xFu, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 5, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMmCvtusepi64Epi8() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmCvtusepi64Epi8(a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64Epi8() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val src = mmSet1Epi8(0)
    val r = mmMaskCvtusepi64Epi8(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvtusepi64Epi8(src, 0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvtusepi64Epi8() {
    val a = mmSetEpi64x(6L, Long.MAX_VALUE)
    val r = mmMaskzCvtusepi64Epi8(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvtusepi64Epi8(0x3u, a)
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, UByte.MAX_VALUE.toByte())
    assertEqM128i(r1, e)
}

public fun testMm512CvttRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvttRoundpdEpi32<MmFroundNoExc>(a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvttRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvttRoundpdEpi32<MmFroundNoExc>(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvttRoundpdEpi32<MmFroundNoExc>(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvttRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvttRoundpdEpi32<MmFroundNoExc>(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvttRoundpdEpi32<MmFroundNoExc>(0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512CvttRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvttRoundpdEpu32<MmFroundNoExc>(a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvttRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvttRoundpdEpu32<MmFroundNoExc>(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvttRoundpdEpu32<MmFroundNoExc>(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvttRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvttRoundpdEpu32<MmFroundNoExc>(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvttRoundpdEpu32<MmFroundNoExc>(0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512CvttpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvttpdEpi32(a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 4, -5, 6, -7)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvttpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvttpdEpi32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvttpdEpi32(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvttpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvttpdEpi32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvttpdEpi32(0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -3, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskCvttpdEpi32() {
    val a = mm256SetrPd(4.0, -5.5, 6.0, -7.5)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvttpdEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvttpdEpi32(src, 0xFu, a)
    val e = mmSetrEpi32(4, -5, 6, -7)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvttpdEpi32() {
    val a = mm256SetrPd(4.0, -5.5, 6.0, -7.5)
    val r = mm256MaskzCvttpdEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvttpdEpi32(0xFu, a)
    val e = mmSetrEpi32(4, -5, 6, -7)
    assertEqM128i(r1, e)
}

public fun testMmMaskCvttpdEpi32() {
    val a = mmSetPd(6.0, -7.5)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvttpdEpi32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvttpdEpi32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 6, -7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvttpdEpi32() {
    val a = mmSetPd(6.0, -7.5)
    val r = mmMaskzCvttpdEpi32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvttpdEpi32(0x3u, a)
    val e = mmSetEpi32(0, 0, 6, -7)
    assertEqM128i(r1, e)
}

public fun testMm512CvttpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvttpdEpu32(a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvttpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvttpdEpu32(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvttpdEpu32(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvttpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvttpdEpu32(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvttpdEpu32(0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm256CvttpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val r = mm256CvttpdEpu32(a)
    val e = mmSetEpi32(4, 5, 6, 7)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvttpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val src = mmSet1Epi32(0)
    val r = mm256MaskCvttpdEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mm256MaskCvttpdEpu32(src, 0xFu, a)
    val e = mmSetEpi32(4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm256MaskzCvttpdEpu32() {
    val a = mm256SetPd(4.0, 5.5, 6.0, 7.5)
    val r = mm256MaskzCvttpdEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mm256MaskzCvttpdEpu32(0xFu, a)
    val e = mmSetEpi32(4, 5, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmCvttpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val r = mmCvttpdEpu32(a)
    val e = mmSetEpi32(0, 0, 6, 7)
    assertEqM128i(r, e)
}

public fun testMmMaskCvttpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val src = mmSet1Epi32(0)
    val r = mmMaskCvttpdEpu32(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCvttpdEpu32(src, 0x3u, a)
    val e = mmSetEpi32(0, 0, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCvttpdEpu32() {
    val a = mmSetPd(6.0, 7.5)
    val r = mmMaskzCvttpdEpu32(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCvttpdEpu32(0x3u, a)
    val e = mmSetEpi32(0, 0, 6, 7)
    assertEqM128i(r1, e)
}

public fun testMm512AddRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(-1.0)
    val r = mm512AddRoundPd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(7.0, 8.5, 9.0, 10.5, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r, e)
    val r1 = mm512AddRoundPd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPd(7.0, 8.5, 9.0, 10.5, 11.0, 12.5, 13.0, -0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskAddRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(-1.0)
    val r = mm512MaskAddRoundPd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskAddRoundPd(a, 0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzAddRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(-1.0)
    val r = mm512MaskzAddRoundPd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzAddRoundPd(0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512SubRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val r = mm512SubRoundPd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(7.0, 8.5, 9.0, 10.5, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r, e)
    val r1 = mm512SubRoundPd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPd(7.0, 8.5, 9.0, 10.5, 11.0, 12.5, 13.0, -0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskSubRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskSubRoundPd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskSubRoundPd(a, 0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzSubRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val r = mm512MaskzSubRoundPd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzSubRoundPd(0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 11.0, 12.5, 13.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MulRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.0)
    val b = mm512Set1Pd(0.1)
    val r = mm512MulRoundPd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.8, 0.9500000000000001, 1.0, 1.1500000000000001, 1.2000000000000002, 1.35, 1.4000000000000001, 0.0)
    assertEqM512d(r, e)
    val r1 = mm512MulRoundPd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPd(0.8, 0.95, 1.0, 1.15, 1.2, 1.3499999999999999, 1.4, 0.0)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskMulRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.0)
    val b = mm512Set1Pd(0.1)
    val r = mm512MaskMulRoundPd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskMulRoundPd(a, 0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 1.2000000000000002, 1.35, 1.4000000000000001, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMulRoundPd() {
    val a = mm512SetrPd(8.0, 9.5, 10.0, 11.5, 12.0, 13.5, 14.0, 0.0)
    val b = mm512Set1Pd(0.1)
    val r = mm512MaskzMulRoundPd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMulRoundPd(0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.2000000000000002, 1.35, 1.4000000000000001, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512DivRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512DivRoundPd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(0.3333333333333333)
    assertEqM512d(r, e)
    val r1 = mm512DivRoundPd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(0.3333333333333333)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskDivRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskDivRoundPd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskDivRoundPd(a, 0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 0.3333333333333333, 0.3333333333333333, 0.3333333333333333, 0.3333333333333333)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzDivRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskzDivRoundPd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzDivRoundPd(0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 0.3333333333333333, 0.3333333333333333, 0.3333333333333333, 0.3333333333333333)
    assertEqM512d(r1, e)
}

public fun testMm512SqrtRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512SqrtRoundPd(a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(1.7320508075688772)
    assertEqM512d(r, e)
    val r1 = mm512SqrtRoundPd(a, MM_FROUND_TO_POS_INF or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(1.7320508075688774)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskSqrtRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskSqrtRoundPd(a, 0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskSqrtRoundPd(a, 0xF0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 1.7320508075688772, 1.7320508075688772, 1.7320508075688772, 1.7320508075688772)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzSqrtRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskzSqrtRoundPd(0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzSqrtRoundPd(0xF0u, a, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.7320508075688772, 1.7320508075688772, 1.7320508075688772, 1.7320508075688772)
    assertEqM512d(r1, e)
}

public fun testMm512FmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512FmaddRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(-1.0)
    assertEqM512d(r, e)
    val r1 = mm512FmaddRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(-0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskFmaddRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmaddRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskzFmaddRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmaddRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512Mask3FmaddRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmaddRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512FmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FmsubRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(-1.0)
    assertEqM512d(r, e)
    val r1 = mm512FmsubRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(-0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFmsubRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmsubRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFmsubRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmsubRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512Mask3FmsubRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmsubRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512FmaddsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512FmaddsubRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0)
    assertEqM512d(r, e)
    val r1 = mm512FmaddsubRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPd(1.0, -0.9999999999999999, 1.0, -0.9999999999999999, 1.0, -0.9999999999999999, 1.0, -0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFmaddsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskFmaddsubRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmaddsubRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, -1.0, 1.0, -1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmaddsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskzFmaddsubRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmaddsubRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmaddsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512Mask3FmaddsubRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmaddsubRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, -1.0, 1.0, -1.0, -1.0, -1.0, -1.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512FmsubaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512FmsubaddRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0)
    assertEqM512d(r, e)
    val r1 = mm512FmsubaddRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512SetrPd(-0.9999999999999999, 1.0, -0.9999999999999999, 1.0, -0.9999999999999999, 1.0, -0.9999999999999999, 1.0)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFmsubaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskFmsubaddRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFmsubaddRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, 1.0, -1.0, 1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFmsubaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskzFmsubaddRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFmsubaddRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, 1.0, -1.0, 1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FmsubaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512Mask3FmsubaddRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FmsubaddRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(-1.0, 1.0, -1.0, 1.0, -1.0, -1.0, -1.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512FnmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512FnmaddRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
    val r1 = mm512FnmaddRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFnmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskFnmaddRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFnmaddRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFnmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512MaskzFnmaddRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFnmaddRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FnmaddRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(1.0)
    val r = mm512Mask3FnmaddRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FnmaddRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512FnmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512FnmsubRoundPd(a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
    val r1 = mm512FnmsubRoundPd(a, b, c, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e1 = mm512Set1Pd(0.9999999999999999)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskFnmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskFnmsubRoundPd(a, 0u, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskFnmsubRoundPd(a, 0xFu, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007, 0.000000000000000007)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzFnmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512MaskzFnmsubRoundPd(0u, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzFnmsubRoundPd(0xFu, a, b, c, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask3FnmsubRoundPd() {
    val a = mm512Set1Pd(0.000000000000000007)
    val b = mm512Set1Pd(1.0)
    val c = mm512Set1Pd(-1.0)
    val r = mm512Mask3FnmsubRoundPd(a, b, c, 0u, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, c)
    val r1 = mm512Mask3FnmsubRoundPd(a, b, c, 0xFu, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetrPd(1.0, 1.0, 1.0, 1.0, -1.0, -1.0, -1.0, -1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaxRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaxRoundPd<MmFroundCurDirection>(a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMaxRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskMaxRoundPd<MmFroundCurDirection>(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskMaxRoundPd<MmFroundCurDirection>(a, 0xFu, a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMaxRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskzMaxRoundPd<MmFroundCurDirection>(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMaxRoundPd<MmFroundCurDirection>(0xFu, a, b)
    val e = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512MinRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MinRoundPd<MmFroundCurDirection>(a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMinRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskMinRoundPd<MmFroundCurDirection>(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskMinRoundPd<MmFroundCurDirection>(a, 0xFu, a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMinRoundPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512SetrPd(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.0)
    val r = mm512MaskzMinRoundPd<MmFroundCurDirection>(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMinRoundPd<MmFroundCurDirection>(0xFu, a, b)
    val e = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512GetexpRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512GetexpRoundPd<MmFroundCurDirection>(a)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskGetexpRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskGetexpRoundPd<MmFroundCurDirection>(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskGetexpRoundPd<MmFroundCurDirection>(a, 0xF0u, a)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzGetexpRoundPd() {
    val a = mm512Set1Pd(3.0)
    val r = mm512MaskzGetexpRoundPd<MmFroundCurDirection>(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzGetexpRoundPd<MmFroundCurDirection>(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512RoundscaleRoundPd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512RoundscaleRoundPd<MmFroundCurDirection>(a, 0)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskRoundscaleRoundPd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512MaskRoundscaleRoundPd<MmFroundCurDirection>(a, 0u, a, 0)
    val e = mm512Set1Pd(1.1)
    assertEqM512d(r, e)
    val r1 = mm512MaskRoundscaleRoundPd<MmFroundCurDirection>(a, 0xFFu, a, 0)
    val e1 = mm512Set1Pd(1.0)
    assertEqM512d(r1, e1)
}

public fun testMm512MaskzRoundscaleRoundPd() {
    val a = mm512Set1Pd(1.1)
    val r = mm512MaskzRoundscaleRoundPd<MmFroundCurDirection>(0u, a, 0)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzRoundscaleRoundPd<MmFroundCurDirection>(0xFFu, a, 0)
    val e = mm512Set1Pd(1.0)
    assertEqM512d(r1, e)
}

public fun testMm512ScalefRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512ScalefRoundPd(a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512Set1Pd(8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskScalefRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskScalefRoundPd(a, 0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, a)
    val r1 = mm512MaskScalefRoundPd(a, 0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(8.0, 8.0, 8.0, 8.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzScalefRoundPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(3.0)
    val r = mm512MaskzScalefRoundPd(0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzScalefRoundPd(0xF0u, a, b, MM_FROUND_TO_NEAREST_INT or MM_FROUND_NO_EXC)
    val e = mm512SetPd(8.0, 8.0, 8.0, 8.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512FixupimmRoundPd() {
    val a = mm512Set1Pd(Double.NaN)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512FixupimmRoundPd<MmFroundCurDirection>(a, b, c, 5)
    val e = mm512Set1Pd(0.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskFixupimmRoundPd() {
    val a = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 1.0, 1.0, 1.0, 1.0)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512MaskFixupimmRoundPd<MmFroundCurDirection>(a, 0xF0u, b, c, 5)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskzFixupimmRoundPd() {
    val a = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 1.0, 1.0, 1.0, 1.0)
    val b = mm512Set1Pd(Double.MAX_VALUE)
    val c = mm512Set1Epi64(Int.MAX_VALUE.toLong())
    val r = mm512MaskzFixupimmRoundPd<MmFroundCurDirection>(0xF0u, a, b, c, 5)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512GetmantRoundPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512GetmantRoundPd<MmMantNorm12, MmMantSignSrc, MmFroundCurDirection>(a)
    val e = mm512Set1Pd(1.25)
    assertEqM512d(r, e)
}

public fun testMm512MaskGetmantRoundPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512MaskGetmantRoundPd<MmMantNorm12, MmMantSignSrc, MmFroundCurDirection>(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskGetmantRoundPd<MmMantNorm12, MmMantSignSrc, MmFroundCurDirection>(a, 0xF0u, a)
    val e = mm512SetrPd(10.0, 10.0, 10.0, 10.0, 1.25, 1.25, 1.25, 1.25)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzGetmantRoundPd() {
    val a = mm512Set1Pd(10.0)
    val r = mm512MaskzGetmantRoundPd<MmMantNorm12, MmMantSignSrc, MmFroundCurDirection>(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzGetmantRoundPd<MmMantNorm12, MmMantSignSrc, MmFroundCurDirection>(0xF0u, a)
    val e = mm512SetrPd(0.0, 0.0, 0.0, 0.0, 1.25, 1.25, 1.25, 1.25)
    assertEqM512d(r1, e)
}

public fun testMm512CvtRoundpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val r = mm512CvtRoundpsPd<MmFroundCurDirection>(a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    assertEqM512d(r, e)
}

public fun testMm512MaskCvtRoundpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val src = mm512Set1Pd(0.0)
    val r = mm512MaskCvtRoundpsPd<MmFroundCurDirection>(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCvtRoundpsPd<MmFroundCurDirection>(src, 0xFu, a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzCvtRoundpsPd() {
    val a = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    val r = mm512MaskzCvtRoundpsPd<MmFroundCurDirection>(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzCvtRoundpsPd<MmFroundCurDirection>(0xFu, a)
    val e = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512CvtRoundpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtRoundpdPs<MmFroundCurDirection>(a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 4.0f, -5.5f, 6.0f, -7.5f)
    assertEqM256(r, e)
}

public fun testMm512MaskCvtRoundpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Ps(0.0f)
    val r = mm512MaskCvtRoundpdPs<MmFroundCurDirection>(src, 0u, a)
    assertEqM256(r, src)
    val r1 = mm512MaskCvtRoundpdPs<MmFroundCurDirection>(src, 0xFu, a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMm512MaskzCvtRoundpdPs() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvtRoundpdPs<MmFroundCurDirection>(0u, a)
    assertEqM256(r, mm256SetzeroPs())
    val r1 = mm512MaskzCvtRoundpdPs<MmFroundCurDirection>(0xFu, a)
    val e = mm256SetrPs(0.0f, -1.5f, 2.0f, -3.5f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r1, e)
}

public fun testMm512CvtRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtRoundpdEpi32<MmFroundCurDirection>(a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 4, -6, 6, -8)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvtRoundpdEpi32<MmFroundCurDirection>(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtRoundpdEpi32<MmFroundCurDirection>(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtRoundpdEpi32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvtRoundpdEpi32<MmFroundCurDirection>(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtRoundpdEpi32<MmFroundCurDirection>(0xFu, a)
    val e = mm256SetrEpi32(0, -2, 2, -4, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512CvtRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512CvtRoundpdEpu32<MmFroundCurDirection>(a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 4, -1, 6, -1)
    assertEqM256i(r, e)
}

public fun testMm512MaskCvtRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val src = mm256Set1Epi32(0)
    val r = mm512MaskCvtRoundpdEpu32<MmFroundCurDirection>(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm512MaskCvtRoundpdEpu32<MmFroundCurDirection>(src, 0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzCvtRoundpdEpu32() {
    val a = mm512SetrPd(0.0, -1.5, 2.0, -3.5, 4.0, -5.5, 6.0, -7.5)
    val r = mm512MaskzCvtRoundpdEpu32<MmFroundCurDirection>(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzCvtRoundpdEpu32<MmFroundCurDirection>(0xFu, a)
    val e = mm256SetrEpi32(0, -1, 2, -1, 0, 0, 0, 0)
    assertEqM256i(r1, e)
}

public fun testMm512SetzeroPd() {
    assertEqM512d(mm512SetzeroPd(), mm512Set1Pd(0.0))
}

public fun testMm512Set1Epi64() {
    val r = mm512SetEpi64(2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L)
    assertEqM512i(r, mm512Set1Epi64(2L))
}

public fun testMm512Set1Pd() {
    val expected = mm512SetPd(2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(expected, mm512Set1Pd(2.0))
}

public fun testMm512Set4Epi64() {
    val r = mm512SetEpi64(4L, 3L, 2L, 1L, 4L, 3L, 2L, 1L)
    assertEqM512i(r, mm512Set4Epi64(4L, 3L, 2L, 1L))
}

public fun testMm512Set4Pd() {
    val r = mm512SetPd(4.0, 3.0, 2.0, 1.0, 4.0, 3.0, 2.0, 1.0)
    assertEqM512d(r, mm512Set4Pd(4.0, 3.0, 2.0, 1.0))
}

public fun testMm512Setr4Epi64() {
    val r = mm512SetEpi64(4L, 3L, 2L, 1L, 4L, 3L, 2L, 1L)
    assertEqM512i(r, mm512Setr4Epi64(1L, 2L, 3L, 4L))
}

public fun testMm512Setr4Pd() {
    val r = mm512SetPd(4.0, 3.0, 2.0, 1.0, 4.0, 3.0, 2.0, 1.0)
    assertEqM512d(r, mm512Setr4Pd(1.0, 2.0, 3.0, 4.0))
}

public fun testMm512CmpltPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val m = mm512CmpltPdMask(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpltPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x66
    val r = mm512MaskCmpltPdMask(mask, a, b)
    check(r == 0x4u.toUByte())
}

public fun testMm512CmpnltPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    check(mm512CmpnltPdMask(a, b) == !mm512CmpltPdMask(a, b))
}

public fun testMm512MaskCmpnltPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x7A
    check(mm512MaskCmpnltPdMask(mask, a, b) == 0x7Au.toUByte())
}

public fun testMm512CmplePdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    check(mm512CmplePdMask(a, b) == 0x25u.toUByte())
}

public fun testMm512MaskCmplePdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x7A
    check(mm512MaskCmplePdMask(mask, a, b) == 0x20u.toUByte())
}

public fun testMm512CmpnlePdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val m = mm512CmpnlePdMask(b, a)
    check(m == 0xDu.toUByte())
}

public fun testMm512MaskCmpnlePdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x66
    val r = mm512MaskCmpnlePdMask(mask, b, a)
    check(r == 0x4u.toUByte())
}

public fun testMm512CmpeqPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val b = mm512SetPd(0.0, 1.0, 13.0, 42.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val m = mm512CmpeqPdMask(b, a)
    check(m == 0xCDu.toUByte())
}

public fun testMm512MaskCmpeqPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val b = mm512SetPd(0.0, 1.0, 13.0, 42.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val mask = 0x7A
    val r = mm512MaskCmpeqPdMask(mask, b, a)
    check(r == 0x48u.toUByte())
}

public fun testMm512CmpneqPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val b = mm512SetPd(0.0, 1.0, 13.0, 42.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val m = mm512CmpneqPdMask(b, a)
    check(m == 0x32u.toUByte())
}

public fun testMm512MaskCmpneqPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val b = mm512SetPd(0.0, 1.0, 13.0, 42.0, Double.MAX_VALUE, (-Double.MAX_VALUE), Double.NaN, -100.0)
    val mask = 0x7A
    val r = mm512MaskCmpneqPdMask(mask, b, a)
    check(r == 0x32u.toUByte())
}

public fun testMm512CmpPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val m = mm512CmpPdMask<CmpLtOq>(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x66
    val r = mm512MaskCmpPdMask<CmpLtOq>(mask, a, b)
    check(r == 0x4u.toUByte())
}

public fun testMm256CmpPdMask() {
    val a = mm256SetPd(0.0, 1.0, -1.0, 13.0)
    val b = mm256Set1Pd(1.0)
    val m = mm256CmpPdMask<CmpLtOq>(a, b)
    check(m == 0xAu.toUByte())
}

public fun testMm256MaskCmpPdMask() {
    val a = mm256SetPd(0.0, 1.0, -1.0, 13.0)
    val b = mm256Set1Pd(1.0)
    val mask = 0xFF
    val r = mm256MaskCmpPdMask<CmpLtOq>(mask, a, b)
    check(r == 0xAu.toUByte())
}

public fun testMmCmpPdMask() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSet1Pd(1.0)
    val m = mmCmpPdMask<CmpLtOq>(a, b)
    check(m == 0x2u.toUByte())
}

public fun testMmMaskCmpPdMask() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSet1Pd(1.0)
    val mask = 0xFF
    val r = mmMaskCmpPdMask<CmpLtOq>(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm512CmpRoundPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val m = mm512CmpRoundPdMask<CmpLtOq, MmFroundCurDirection>(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpRoundPdMask() {
    val a = mm512SetPd(0.0, 1.0, -1.0, 13.0, Double.MAX_VALUE, (-Double.MAX_VALUE), 100.0, -100.0)
    val b = mm512Set1Pd(-1.0)
    val mask = 0x66
    val r = mm512MaskCmpRoundPdMask<CmpLtOq, MmFroundCurDirection>(mask, a, b)
    check(r == 0x4u.toUByte())
}

public fun testMm512CmpordPdMask() {
    val a = mm512SetPd(Double.NaN, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), Double.NaN, -1.0, Double.NaN, 0.0)
    val b = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, (-Double.MAX_VALUE), Double.MAX_VALUE, -1.0, 0.0)
    val m = mm512CmpordPdMask(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpordPdMask() {
    val a = mm512SetPd(Double.NaN, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), Double.NaN, -1.0, Double.NaN, 0.0)
    val b = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, (-Double.MAX_VALUE), Double.MAX_VALUE, -1.0, 0.0)
    val mask = 0xC3
    val m = mm512MaskCmpordPdMask(mask, a, b)
    check(m == 0x1u.toUByte())
}

public fun testMm512CmpunordPdMask() {
    val a = mm512SetPd(Double.NaN, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), Double.NaN, -1.0, Double.NaN, 0.0)
    val b = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, (-Double.MAX_VALUE), Double.MAX_VALUE, -1.0, 0.0)
    val m = mm512CmpunordPdMask(a, b)
    check(m == 0xFAu.toUByte())
}

public fun testMm512MaskCmpunordPdMask() {
    val a = mm512SetPd(Double.NaN, Double.MAX_VALUE, Double.NaN, (-Double.MAX_VALUE), Double.NaN, -1.0, Double.NaN, 0.0)
    val b = mm512SetPd(Double.NaN, Double.NaN, Double.NaN, Double.NaN, (-Double.MAX_VALUE), Double.MAX_VALUE, -1.0, 0.0)
    val mask = 0xF
    val m = mm512MaskCmpunordPdMask(mask, a, b)
    check(m == 0xAu.toUByte())
}

public fun testMm512CmpltEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpltEpu64Mask(a, b)
    check(m == 0xCFu.toUByte())
}

public fun testMm512MaskCmpltEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x7A
    val r = mm512MaskCmpltEpu64Mask(mask, a, b)
    check(r == 0x4Au.toUByte())
}

public fun testMm256CmpltEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 100L)
    val b = mm256Set1Epi64x(2L)
    val r = mm256CmpltEpu64Mask(a, b)
    check(r == 0xCu.toUByte())
}

public fun testMm256MaskCmpltEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 100L)
    val b = mm256Set1Epi64x(2L)
    val mask = 0xFF
    val r = mm256MaskCmpltEpu64Mask(mask, a, b)
    check(r == 0xCu.toUByte())
}

public fun testMmCmpltEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(2L)
    val r = mmCmpltEpu64Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpltEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(2L)
    val mask = 0xFF
    val r = mmMaskCmpltEpu64Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpgtEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpgtEpu64Mask(b, a)
    check(m == 0xCFu.toUByte())
}

public fun testMm512MaskCmpgtEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x7A
    val r = mm512MaskCmpgtEpu64Mask(mask, b, a)
    check(r == 0x4Au.toUByte())
}

public fun testMm256CmpgtEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256Set1Epi64x(1L)
    val r = mm256CmpgtEpu64Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm256MaskCmpgtEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val b = mm256Set1Epi64x(1L)
    val mask = 0xFF
    val r = mm256MaskCmpgtEpu64Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmCmpgtEpu64Mask() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSet1Epi64x(1L)
    val r = mmCmpgtEpu64Mask(a, b)
    check(r == 0x1u.toUByte())
}

public fun testMmMaskCmpgtEpu64Mask() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpgtEpu64Mask(mask, a, b)
    check(r == 0x1u.toUByte())
}

public fun testMm512CmpleEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    check(mm512CmpleEpu64Mask(a, b) == !mm512CmpgtEpu64Mask(a, b))
}

public fun testMm512MaskCmpleEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x7A
    check(mm512MaskCmpleEpu64Mask(mask, a, b) == 0x7Au.toUByte())
}

public fun testMm256CmpleEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 1L)
    val b = mm256Set1Epi64x(1L)
    val r = mm256CmpleEpu64Mask(a, b)
    check(r == 0xDu.toUByte())
}

public fun testMm256MaskCmpleEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 1L)
    val b = mm256Set1Epi64x(1L)
    val mask = 0xFF
    val r = mm256MaskCmpleEpu64Mask(mask, a, b)
    check(r == 0xDu.toUByte())
}

public fun testMmCmpleEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val r = mmCmpleEpu64Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpleEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpleEpu64Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpgeEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    check(mm512CmpgeEpu64Mask(a, b) == !mm512CmpltEpu64Mask(a, b))
}

public fun testMm512MaskCmpgeEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0xFF
    val r = mm512MaskCmpgeEpu64Mask(mask, a, b)
    check(r == 0x30u.toUByte())
}

public fun testMm256CmpgeEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, ULong.MAX_VALUE.toLong())
    val b = mm256Set1Epi64x(1L)
    val r = mm256CmpgeEpu64Mask(a, b)
    check(r == 0x7u.toUByte())
}

public fun testMm256MaskCmpgeEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, 2L, ULong.MAX_VALUE.toLong())
    val b = mm256Set1Epi64x(1L)
    val mask = 0xFF
    val r = mm256MaskCmpgeEpu64Mask(mask, a, b)
    check(r == 0x7u.toUByte())
}

public fun testMmCmpgeEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val r = mmCmpgeEpu64Mask(a, b)
    check(r == 0x1u.toUByte())
}

public fun testMmMaskCmpgeEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpgeEpu64Mask(mask, a, b)
    check(r == 0x1u.toUByte())
}

public fun testMm512CmpeqEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val m = mm512CmpeqEpu64Mask(b, a)
    check(m == 0xCFu.toUByte())
}

public fun testMm512MaskCmpeqEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val mask = 0x7A
    val r = mm512MaskCmpeqEpu64Mask(mask, b, a)
    check(r == 0x4Au.toUByte())
}

public fun testMm256CmpeqEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, ULong.MAX_VALUE.toLong())
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val m = mm256CmpeqEpu64Mask(b, a)
    check(m == 0xCu.toUByte())
}

public fun testMm256MaskCmpeqEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, ULong.MAX_VALUE.toLong())
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val mask = 0xFF
    val r = mm256MaskCmpeqEpu64Mask(mask, b, a)
    check(r == 0xCu.toUByte())
}

public fun testMmCmpeqEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(0L, 1L)
    val m = mmCmpeqEpu64Mask(b, a)
    check(m == 0x3u.toUByte())
}

public fun testMmMaskCmpeqEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(0L, 1L)
    val mask = 0xFF
    val r = mmMaskCmpeqEpu64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpneqEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val m = mm512CmpneqEpu64Mask(b, a)
    check(m == !mm512CmpeqEpu64Mask(b, a))
}

public fun testMm512MaskCmpneqEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, -100L, 100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val mask = 0x7A
    val r = mm512MaskCmpneqEpu64Mask(mask, b, a)
    check(r == 0x32u.toUByte())
}

public fun testMm256CmpneqEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, ULong.MAX_VALUE.toLong())
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val r = mm256CmpneqEpu64Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm256MaskCmpneqEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, ULong.MAX_VALUE.toLong())
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val mask = 0xFF
    val r = mm256MaskCmpneqEpu64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmCmpneqEpu64Mask() {
    val a = mmSetEpi64x(-1L, ULong.MAX_VALUE.toLong())
    val b = mmSetEpi64x(13L, 42L)
    val r = mmCmpneqEpu64Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpneqEpu64Mask() {
    val a = mmSetEpi64x(-1L, ULong.MAX_VALUE.toLong())
    val b = mmSetEpi64x(13L, 42L)
    val mask = 0xFF
    val r = mmMaskCmpneqEpu64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpEpu64Mask<MmCmpintLt>(a, b)
    check(m == 0xCFu.toUByte())
}

public fun testMm512MaskCmpEpu64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x7A
    val r = mm512MaskCmpEpu64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x4Au.toUByte())
}

public fun testMm256CmpEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 100L)
    val b = mm256Set1Epi64x(1L)
    val m = mm256CmpEpu64Mask<MmCmpintLt>(a, b)
    check(m == 0x8u.toUByte())
}

public fun testMm256MaskCmpEpu64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 100L)
    val b = mm256Set1Epi64x(1L)
    val mask = 0xFF
    val r = mm256MaskCmpEpu64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x8u.toUByte())
}

public fun testMmCmpEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val m = mmCmpEpu64Mask<MmCmpintLt>(a, b)
    check(m == 0x2u.toUByte())
}

public fun testMmMaskCmpEpu64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpEpu64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm512CmpltEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpltEpi64Mask(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpltEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x66
    val r = mm512MaskCmpltEpi64Mask(mask, a, b)
    check(r == 0x4u.toUByte())
}

public fun testMm256CmpltEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, -13L)
    val b = mm256Set1Epi64x(-1L)
    val r = mm256CmpltEpi64Mask(a, b)
    check(r == 0x1u.toUByte())
}

public fun testMm256MaskCmpltEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, -13L)
    val b = mm256Set1Epi64x(-1L)
    val mask = 0xFF
    val r = mm256MaskCmpltEpi64Mask(mask, a, b)
    check(r == 0x1u.toUByte())
}

public fun testMmCmpltEpi64Mask() {
    val a = mmSetEpi64x(-1L, -13L)
    val b = mmSet1Epi64x(-1L)
    val r = mmCmpltEpi64Mask(a, b)
    check(r == 0x1u.toUByte())
}

public fun testMmMaskCmpltEpi64Mask() {
    val a = mmSetEpi64x(-1L, -13L)
    val b = mmSet1Epi64x(-1L)
    val mask = 0xFF
    val r = mmMaskCmpltEpi64Mask(mask, a, b)
    check(r == 0x1u.toUByte())
}

public fun testMm512CmpgtEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpgtEpi64Mask(b, a)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpgtEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x66
    val r = mm512MaskCmpgtEpi64Mask(mask, b, a)
    check(r == 0x4u.toUByte())
}

public fun testMm256CmpgtEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256Set1Epi64x(-1L)
    val r = mm256CmpgtEpi64Mask(a, b)
    check(r == 0xDu.toUByte())
}

public fun testMm256MaskCmpgtEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256Set1Epi64x(-1L)
    val mask = 0xFF
    val r = mm256MaskCmpgtEpi64Mask(mask, a, b)
    check(r == 0xDu.toUByte())
}

public fun testMmCmpgtEpi64Mask() {
    val a = mmSetEpi64x(0L, -1L)
    val b = mmSet1Epi64x(-1L)
    val r = mmCmpgtEpi64Mask(a, b)
    check(r == 0x2u.toUByte())
}

public fun testMmMaskCmpgtEpi64Mask() {
    val a = mmSetEpi64x(0L, -1L)
    val b = mmSet1Epi64x(-1L)
    val mask = 0xFF
    val r = mmMaskCmpgtEpi64Mask(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm512CmpleEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    check(mm512CmpleEpi64Mask(a, b) == !mm512CmpgtEpi64Mask(a, b))
}

public fun testMm512MaskCmpleEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x7A
    check(mm512MaskCmpleEpi64Mask(mask, a, b) == 0x30u.toUByte())
}

public fun testMm256CmpleEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, Long.MAX_VALUE)
    val b = mm256Set1Epi64x(-1L)
    val r = mm256CmpleEpi64Mask(a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm256MaskCmpleEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, Long.MAX_VALUE)
    val b = mm256Set1Epi64x(-1L)
    val mask = 0xFF
    val r = mm256MaskCmpleEpi64Mask(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMmCmpleEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val r = mmCmpleEpi64Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpleEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpleEpi64Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpgeEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    check(mm512CmpgeEpi64Mask(a, b) == !mm512CmpltEpi64Mask(a, b))
}

public fun testMm512MaskCmpgeEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, ULong.MAX_VALUE.toLong(), Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0xFF
    val r = mm512MaskCmpgeEpi64Mask(mask, a, b)
    check(r == 0xFAu.toUByte())
}

public fun testMm256CmpgeEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, Long.MAX_VALUE)
    val b = mm256Set1Epi64x(-1L)
    val r = mm256CmpgeEpi64Mask(a, b)
    check(r == 0xFu.toUByte())
}

public fun testMm256MaskCmpgeEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, Long.MAX_VALUE)
    val b = mm256Set1Epi64x(-1L)
    val mask = 0xFF
    val r = mm256MaskCmpgeEpi64Mask(mask, a, b)
    check(r == 0xFu.toUByte())
}

public fun testMmCmpgeEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(-1L)
    val r = mmCmpgeEpi64Mask(a, b)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpgeEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(-1L)
    val mask = 0xFF
    val r = mmMaskCmpgeEpi64Mask(mask, a, b)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpeqEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val m = mm512CmpeqEpi64Mask(b, a)
    check(m == 0xCFu.toUByte())
}

public fun testMm512MaskCmpeqEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val mask = 0x7A
    val r = mm512MaskCmpeqEpi64Mask(mask, b, a)
    check(r == 0x4Au.toUByte())
}

public fun testMm256CmpeqEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val m = mm256CmpeqEpi64Mask(b, a)
    check(m == 0xCu.toUByte())
}

public fun testMm256MaskCmpeqEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val mask = 0xFF
    val r = mm256MaskCmpeqEpi64Mask(mask, b, a)
    check(r == 0xCu.toUByte())
}

public fun testMmCmpeqEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(0L, 1L)
    val m = mmCmpeqEpi64Mask(b, a)
    check(m == 0x3u.toUByte())
}

public fun testMmMaskCmpeqEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSetEpi64x(0L, 1L)
    val mask = 0xFF
    val r = mmMaskCmpeqEpi64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512SetEpi64() {
    val r = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    assertEqM512i(r, mm512SetEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L))
}

public fun testMm512SetrEpi64() {
    val r = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    assertEqM512i(r, mm512SetrEpi64(7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L))
}

public fun testMm512CmpneqEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val m = mm512CmpneqEpi64Mask(b, a)
    check(m == !mm512CmpeqEpi64Mask(b, a))
}

public fun testMm512MaskCmpneqEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, -100L, 100L)
    val b = mm512SetEpi64(0L, 1L, 13L, 42L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val mask = 0x7A
    val r = mm512MaskCmpneqEpi64Mask(mask, b, a)
    check(r == 0x32u.toUByte())
}

public fun testMm256CmpneqEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val r = mm256CmpneqEpi64Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm256MaskCmpneqEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256SetEpi64x(0L, 1L, 13L, 42L)
    val mask = 0xFF
    val r = mm256MaskCmpneqEpi64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmCmpneqEpi64Mask() {
    val a = mmSetEpi64x(-1L, 13L)
    val b = mmSetEpi64x(13L, 42L)
    val r = mmCmpneqEpi64Mask(b, a)
    check(r == 0x3u.toUByte())
}

public fun testMmMaskCmpneqEpi64Mask() {
    val a = mmSetEpi64x(-1L, 13L)
    val b = mmSetEpi64x(13L, 42L)
    val mask = 0xFF
    val r = mmMaskCmpneqEpi64Mask(mask, b, a)
    check(r == 0x3u.toUByte())
}

public fun testMm512CmpEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val m = mm512CmpEpi64Mask<MmCmpintLt>(a, b)
    check(m == 0x5u.toUByte())
}

public fun testMm512MaskCmpEpi64Mask() {
    val a = mm512SetEpi64(0L, 1L, -1L, 13L, Long.MAX_VALUE, Long.MIN_VALUE, 100L, -100L)
    val b = mm512Set1Epi64(-1L)
    val mask = 0x66
    val r = mm512MaskCmpEpi64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x4u.toUByte())
}

public fun testMm256CmpEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256Set1Epi64x(1L)
    val m = mm256CmpEpi64Mask<MmCmpintLt>(a, b)
    check(m == 0xAu.toUByte())
}

public fun testMm256MaskCmpEpi64Mask() {
    val a = mm256SetEpi64x(0L, 1L, -1L, 13L)
    val b = mm256Set1Epi64x(1L)
    val mask = 0xFF
    val r = mm256MaskCmpEpi64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0xAu.toUByte())
}

public fun testMmCmpEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val m = mmCmpEpi64Mask<MmCmpintLt>(a, b)
    check(m == 0x2u.toUByte())
}

public fun testMmMaskCmpEpi64Mask() {
    val a = mmSetEpi64x(0L, 1L)
    val b = mmSet1Epi64x(1L)
    val mask = 0xFF
    val r = mmMaskCmpEpi64Mask<MmCmpintLt>(mask, a, b)
    check(r == 0x2u.toUByte())
}

public fun testMm512I32gatherPd() {
    val localStorageL7450C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val r = mm512I32gatherPd(index, localStorageL7450C8.elementConstPtr(0), 8)
    assertEqM512d(r, mm512SetrPd(0.0, 16.0, 32.0, 48.0, 64.0, 80.0, 96.0, 112.0))
}

public fun testMm512MaskI32gatherPd() {
    val localStorageL7459C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    val src = mm512Set1Pd(2.0)
    val mask = 0xAA
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    // A multiplier of 8 is word-addressing
    val r = mm512MaskI32gatherPd(src, mask, index, localStorageL7459C8.elementConstPtr(0), 8)
    assertEqM512d(r, mm512SetrPd(2.0, 16.0, 2.0, 48.0, 2.0, 80.0, 2.0, 112.0))
}

public fun testMm512I64gatherPd() {
    val localStorageL7470C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    // A multiplier of 8 is word-addressing
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val r = mm512I64gatherPd(index, localStorageL7470C8.elementConstPtr(0), 8)
    assertEqM512d(r, mm512SetrPd(0.0, 16.0, 32.0, 48.0, 64.0, 80.0, 96.0, 112.0))
}

public fun testMm512MaskI64gatherPd() {
    val localStorageL7479C8 = AddressableArrayLocal.fromDoubleArray(fromFn({ i -> i.toDouble() }))
    val src = mm512Set1Pd(2.0)
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    // A multiplier of 8 is word-addressing
    val r = mm512MaskI64gatherPd(src, mask, index, localStorageL7479C8.elementConstPtr(0), 8)
    assertEqM512d(r, mm512SetrPd(2.0, 16.0, 2.0, 48.0, 2.0, 80.0, 2.0, 112.0))
}

public fun testMm512I64gatherPs() {
    val localStorageL7490C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    // A multiplier of 4 is word-addressing
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val r = mm512I64gatherPs(index, localStorageL7490C8.elementConstPtr(0), 4)
    assertEqM256(r, mm256SetrPs(0.0f, 16.0f, 32.0f, 48.0f, 64.0f, 80.0f, 96.0f, 112.0f))
}

public fun testMm512MaskI64gatherPs() {
    val localStorageL7500C8 = AddressableArrayLocal.fromFloatArray(fromFn({ i -> i.toFloat() }))
    val src = mm256Set1Ps(2.0f)
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    // A multiplier of 4 is word-addressing
    val r = mm512MaskI64gatherPs(src, mask, index, localStorageL7500C8.elementConstPtr(0), 4)
    assertEqM256(r, mm256SetrPs(2.0f, 16.0f, 2.0f, 48.0f, 2.0f, 80.0f, 2.0f, 112.0f))
}

public fun testMm512I32gatherEpi64() {
    val localStorageL7512C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7512C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    // A multiplier of 8 is word-addressing
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val r = mm512I32gatherEpi64(index, localStorageL7512C8.elementConstPtr(0), 8)
    assertEqM512i(r, mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L))
}

public fun testMm512MaskI32gatherEpi64() {
    val localStorageL7524C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7524C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    val src = mm512Set1Epi64(2L)
    val mask = 0xAA
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    // A multiplier of 8 is word-addressing
    val r = mm512MaskI32gatherEpi64(src, mask, index, localStorageL7524C8.elementConstPtr(0), 8)
    assertEqM512i(r, mm512SetrEpi64(2L, 16L, 2L, 48L, 2L, 80L, 2L, 112L))
}

public fun testMm512I64gatherEpi64() {
    val localStorageL7538C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7538C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    // A multiplier of 8 is word-addressing
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val r = mm512I64gatherEpi64(index, localStorageL7538C8.elementConstPtr(0), 8)
    assertEqM512i(r, mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L))
}

public fun testMm512MaskI64gatherEpi64() {
    val localStorageL7550C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7550C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    val src = mm512Set1Epi64(2L)
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    // A multiplier of 8 is word-addressing
    val r = mm512MaskI64gatherEpi64(src, mask, index, localStorageL7550C8.elementConstPtr(0), 8)
    assertEqM512i(r, mm512SetrEpi64(2L, 16L, 2L, 48L, 2L, 80L, 2L, 112L))
}

public fun testMm512I64gatherEpi32() {
    val localStorageL7564C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7564C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    // A multiplier of 8 is word-addressing
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val r = mm512I64gatherEpi32(index, (localStorageL7564C8.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 8)
    assertEqM256i(r, mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112))
}

public fun testMm512MaskI64gatherEpi32() {
    val localStorageL7576C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    val arr by localStorageL7576C8
    for (i in 0 until 128L) {
        arr[i.toInt()] = i
    }
    val src = mm256Set1Epi32(2)
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    // A multiplier of 8 is word-addressing
    val r = mm512MaskI64gatherEpi32(src, mask, index, (localStorageL7576C8.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 8)
    assertEqM256i(r, mm256SetrEpi32(2, 16, 2, 48, 2, 80, 2, 112))
}

public fun testMm512I32scatterPd() {
    val arr = MutableList(128) { 0.0 }
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val src = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    // A multiplier of 8 is word-addressing
    run {
        mm512I32scatterPd(arr.asMutPtr(), index, src, 8)
    }
    val expected = MutableList(128) { 0.0 }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toDouble()
    }
    check(arr == expected)
}

public fun testMm512MaskI32scatterPd() {
    val arr = MutableList(128) { 0.0 }
    val mask = 0xAA
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val src = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    // A multiplier of 8 is word-addressing
    run {
        mm512MaskI32scatterPd(arr.asMutPtr(), mask, index, src, 8)
    }
    val expected = MutableList(128) { 0.0 }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2.0 * (i + 1).toDouble()
    }
    check(arr == expected)
}

public fun testMm512I64scatterPd() {
    val arr = MutableList(128) { 0.0 }
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    // A multiplier of 8 is word-addressing
    run {
        mm512I64scatterPd(arr.asMutPtr(), index, src, 8)
    }
    val expected = MutableList(128) { 0.0 }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toDouble()
    }
    check(arr == expected)
}

public fun testMm512MaskI64scatterPd() {
    val arr = MutableList(128) { 0.0 }
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    // A multiplier of 8 is word-addressing
    run {
        mm512MaskI64scatterPd(arr.asMutPtr(), mask, index, src, 8)
    }
    val expected = MutableList(128) { 0.0 }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2.0 * (i + 1).toDouble()
    }
    check(arr == expected)
}

public fun testMm512I64scatterPs() {
    val arr = MutableList(128) { 0.0f }
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    // A multiplier of 4 is word-addressing
    run {
        mm512I64scatterPs(arr.asMutPtr(), index, src, 4)
    }
    val expected = MutableList(128) { 0.0f }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toFloat()
    }
    check(arr == expected)
}

public fun testMm512MaskI64scatterPs() {
    val arr = MutableList(128) { 0.0f }
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    // A multiplier of 4 is word-addressing
    run {
        mm512MaskI64scatterPs(arr.asMutPtr(), mask, index, src, 4)
    }
    val expected = MutableList(128) { 0.0f }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2.0f * (i + 1).toFloat()
    }
    check(arr == expected)
}

public fun testMm512I32scatterEpi64() {
    val localStorageL7691C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    var arr by localStorageL7691C8
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val src = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    // A multiplier of 8 is word-addressing
    run {
        mm512I32scatterEpi64(localStorageL7691C8.elementMutPtr(0), index, src, 8)
    }
    val expected = LongArray(128) { 0L }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toLong()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512MaskI32scatterEpi64() {
    val localStorageL7707C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    var arr by localStorageL7707C8
    val mask = 0xAA
    val index = mm256SetrEpi32(0, 16, 32, 48, 64, 80, 96, 112)
    val src = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    // A multiplier of 8 is word-addressing
    run {
        mm512MaskI32scatterEpi64(localStorageL7707C8.elementMutPtr(0), mask, index, src, 8)
    }
    val expected = LongArray(128) { 0L }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2L * (i + 1).toLong()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512I64scatterEpi64() {
    val localStorageL7724C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    var arr by localStorageL7724C8
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    // A multiplier of 8 is word-addressing
    run {
        mm512I64scatterEpi64(localStorageL7724C8.elementMutPtr(0), index, src, 8)
    }
    val expected = LongArray(128) { 0L }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toLong()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512MaskI64scatterEpi64() {
    val localStorageL7740C8 = AddressableArrayLocal.fromLongArray(LongArray(128) { 0L })
    var arr by localStorageL7740C8
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    // A multiplier of 8 is word-addressing
    run {
        mm512MaskI64scatterEpi64(localStorageL7740C8.elementMutPtr(0), mask, index, src, 8)
    }
    val expected = LongArray(128) { 0L }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2L * (i + 1).toLong()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512I64scatterEpi32() {
    val localStorageL7757C8 = AddressableArrayLocal.fromIntArray(IntArray(128) { 0 })
    var arr by localStorageL7757C8
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    // A multiplier of 4 is word-addressing
    run {
        mm512I64scatterEpi32(localStorageL7757C8.elementMutPtr(0), index, src, 4)
    }
    val expected = IntArray(128) { 0 }
    for (i in 0 until 8) {
        expected[i * 16] = (i + 1).toInt()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512MaskI64scatterEpi32() {
    val localStorageL7773C8 = AddressableArrayLocal.fromIntArray(IntArray(128) { 0 })
    var arr by localStorageL7773C8
    val mask = 0xAA
    val index = mm512SetrEpi64(0L, 16L, 32L, 48L, 64L, 80L, 96L, 112L)
    val src = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    // A multiplier of 4 is word-addressing
    run {
        mm512MaskI64scatterEpi32(localStorageL7773C8.elementMutPtr(0), mask, index, src, 4)
    }
    val expected = IntArray(128) { 0 }
    for (i in 0 until 4) {
        expected[i * 32 + 16] = 2 * (i + 1).toInt()
    }
    check((arr).contentEquals(expected))
}

public fun testMm512I32logatherEpi64() {
    val localStorageL7790C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L))
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val r = mm512I32logatherEpi64(vindex, localStorageL7790C8.elementConstPtr(0), 8)
    val expected = mm512SetrEpi64(2L, 3L, 4L, 5L, 6L, 7L, 8L, 1L)
    assertEqM512i(expected, r)
}

public fun testMm512MaskI32logatherEpi64() {
    val localStorageL7799C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L))
    val src = mm512SetrEpi64(9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L)
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val r = mm512MaskI32logatherEpi64(src, 0x55u, vindex, localStorageL7799C8.elementConstPtr(0), 8)
    val expected = mm512SetrEpi64(2L, 10L, 4L, 12L, 6L, 14L, 8L, 16L)
    assertEqM512i(expected, r)
}

public fun testMm512I32logatherPd() {
    val localStorageL7811C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0))
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val r = mm512I32logatherPd(vindex, localStorageL7811C8.elementConstPtr(0), 8)
    val expected = mm512SetrPd(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0)
    assertEqM512d(expected, r)
}

public fun testMm512MaskI32logatherPd() {
    val localStorageL7820C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0))
    val src = mm512SetrPd(9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0)
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val r = mm512MaskI32logatherPd(src, 0x55u, vindex, localStorageL7820C8.elementConstPtr(0), 8)
    val expected = mm512SetrPd(2.0, 10.0, 4.0, 12.0, 6.0, 14.0, 8.0, 16.0)
    assertEqM512d(expected, r)
}

public fun testMm512I32loscatterEpi64() {
    val localStorageL7831C8 = AddressableArrayLocal.fromLongArray(LongArray(8) { 0L })
    var baseAddr by localStorageL7831C8
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val src = mm512SetrEpi64(2L, 3L, 4L, 5L, 6L, 7L, 8L, 1L)
    run {
        mm512I32loscatterEpi64(localStorageL7831C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    check(expected == baseAddr)
}

public fun testMm512MaskI32loscatterEpi64() {
    val localStorageL7843C8 = AddressableArrayLocal.fromLongArray(LongArray(8) { 0L })
    var baseAddr by localStorageL7843C8
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val src = mm512SetrEpi64(2L, 3L, 4L, 5L, 6L, 7L, 8L, 1L)
    run {
        mm512MaskI32loscatterEpi64(localStorageL7843C8.elementMutPtr(0), 0x55u, vindex, src, 8)
    }
    val expected = listOf(0, 2, 0, 4, 0, 6, 0, 8)
    check(expected == baseAddr)
}

public fun testMm512I32loscatterPd() {
    val localStorageL7855C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(8) { 0.0 })
    var baseAddr by localStorageL7855C8
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val src = mm512SetrPd(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0)
    run {
        mm512I32loscatterPd(localStorageL7855C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    check(expected == baseAddr)
}

public fun testMm512MaskI32loscatterPd() {
    val localStorageL7867C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(8) { 0.0 })
    var baseAddr by localStorageL7867C8
    val vindex = mm512SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0, -1, -1, -1, -1, -1, -1, -1, -1)
    val src = mm512SetrPd(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0)
    run {
        mm512MaskI32loscatterPd(localStorageL7867C8.elementMutPtr(0), 0x55u, vindex, src, 8)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0, 0.0, 6.0, 0.0, 8.0)
    check(expected == baseAddr)
}

public fun testMmMmaskI32gatherEpi32() {
    val localStorageL7879C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4))
    val src = mmSetrEpi32(5, 6, 7, 8)
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val r = mmMmaskI32gatherEpi32(src, 0x5u, vindex, localStorageL7879C8.elementConstPtr(0), 4)
    val expected = mmSetrEpi32(2, 6, 4, 8)
    assertEqM128i(expected, r)
}

public fun testMmMmaskI32gatherEpi64() {
    val localStorageL7889C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L))
    val src = mmSetrEpi64x(5, 6)
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val r = mmMmaskI32gatherEpi64(src, 0x1u, vindex, localStorageL7889C8.elementConstPtr(0), 8)
    val expected = mmSetrEpi64x(2, 6)
    assertEqM128i(expected, r)
}

public fun testMmMmaskI32gatherPd() {
    val localStorageL7899C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0))
    val src = mmSetrPd(5.0, 6.0)
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val r = mmMmaskI32gatherPd(src, 0x1u, vindex, localStorageL7899C8.elementConstPtr(0), 8)
    val expected = mmSetrPd(2.0, 6.0)
    assertEqM128d(expected, r)
}

public fun testMmMmaskI32gatherPs() {
    val localStorageL7909C8 = AddressableArrayLocal.fromFloatArray(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
    val src = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val r = mmMmaskI32gatherPs(src, 0x5u, vindex, localStorageL7909C8.elementConstPtr(0), 4)
    val expected = mmSetrPs(2.0f, 6.0f, 4.0f, 8.0f)
    assertEqM128(expected, r)
}

public fun testMmMmaskI64gatherEpi32() {
    val localStorageL7919C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2))
    val src = mmSetrEpi32(5, 6, 7, 8)
    val vindex = mmSetrEpi64x(1, 0)
    val r = mmMmaskI64gatherEpi32(src, 0x1u, vindex, localStorageL7919C8.elementConstPtr(0), 4)
    val expected = mmSetrEpi32(2, 6, 0, 0)
    assertEqM128i(expected, r)
}

public fun testMmMmaskI64gatherEpi64() {
    val localStorageL7929C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L))
    val src = mmSetrEpi64x(5, 6)
    val vindex = mmSetrEpi64x(1, 0)
    val r = mmMmaskI64gatherEpi64(src, 0x1u, vindex, localStorageL7929C8.elementConstPtr(0), 8)
    val expected = mmSetrEpi64x(2, 6)
    assertEqM128i(expected, r)
}

public fun testMmMmaskI64gatherPd() {
    val localStorageL7939C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0))
    val src = mmSetrPd(5.0, 6.0)
    val vindex = mmSetrEpi64x(1, 0)
    val r = mmMmaskI64gatherPd(src, 0x1u, vindex, localStorageL7939C8.elementConstPtr(0), 8)
    val expected = mmSetrPd(2.0, 6.0)
    assertEqM128d(expected, r)
}

public fun testMmMmaskI64gatherPs() {
    val localStorageL7949C8 = AddressableArrayLocal.fromFloatArray(floatArrayOf(1.0f, 2.0f))
    val src = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val vindex = mmSetrEpi64x(1, 0)
    val r = mmMmaskI64gatherPs(src, 0x1u, vindex, localStorageL7949C8.elementConstPtr(0), 4)
    val expected = mmSetrPs(2.0f, 6.0f, 0.0f, 0.0f)
    assertEqM128(expected, r)
}

public fun testMm256MmaskI32gatherEpi32() {
    val localStorageL7959C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8))
    val src = mm256SetrEpi32(9, 10, 11, 12, 13, 14, 15, 16)
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val r = mm256MmaskI32gatherEpi32(src, 0x55u, vindex, localStorageL7959C8.elementConstPtr(0), 4)
    val expected = mm256SetrEpi32(2, 10, 4, 12, 6, 14, 8, 16)
    assertEqM256i(expected, r)
}

public fun testMm256MmaskI32gatherEpi64() {
    val localStorageL7971C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L, 3L, 4L))
    val src = mm256SetrEpi64x(9L, 10L, 11L, 12L)
    val vindex = mmSetrEpi32(1, 2, 3, 4)
    val r = mm256MmaskI32gatherEpi64(src, 0x5u, vindex, localStorageL7971C8.elementConstPtr(0), 8)
    val expected = mm256SetrEpi64x(2L, 10L, 4L, 12L)
    assertEqM256i(expected, r)
}

public fun testMm256MmaskI32gatherPd() {
    val localStorageL7982C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0, 3.0, 4.0))
    val src = mm256SetrPd(9.0, 10.0, 11.0, 12.0)
    val vindex = mmSetrEpi32(1, 2, 3, 4)
    val r = mm256MmaskI32gatherPd(src, 0x5u, vindex, localStorageL7982C8.elementConstPtr(0), 8)
    val expected = mm256SetrPd(2.0, 10.0, 4.0, 12.0)
    assertEqM256d(expected, r)
}

public fun testMm256MmaskI32gatherPs() {
    val localStorageL7992C8 = AddressableArrayLocal.fromFloatArray(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
    val src = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val r = mm256MmaskI32gatherPs(src, 0x55u, vindex, localStorageL7992C8.elementConstPtr(0), 4)
    val expected = mm256SetrPs(2.0f, 10.0f, 4.0f, 12.0f, 6.0f, 14.0f, 8.0f, 16.0f)
    assertEqM256(expected, r)
}

public fun testMm256MmaskI64gatherEpi32() {
    val localStorageL8003C8 = AddressableArrayLocal.fromIntArray(intArrayOf(1, 2, 3, 4))
    val src = mmSetrEpi32(9, 10, 11, 12)
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val r = mm256MmaskI64gatherEpi32(src, 0x5u, vindex, localStorageL8003C8.elementConstPtr(0), 4)
    val expected = mmSetrEpi32(2, 10, 4, 12)
    assertEqM128i(expected, r)
}

public fun testMm256MmaskI64gatherEpi64() {
    val localStorageL8014C8 = AddressableArrayLocal.fromLongArray(longArrayOf(1L, 2L, 3L, 4L))
    val src = mm256SetrEpi64x(9L, 10L, 11L, 12L)
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val r = mm256MmaskI64gatherEpi64(src, 0x5u, vindex, localStorageL8014C8.elementConstPtr(0), 8)
    val expected = mm256SetrEpi64x(2L, 10L, 4L, 12L)
    assertEqM256i(expected, r)
}

public fun testMm256MmaskI64gatherPd() {
    val localStorageL8025C8 = AddressableArrayLocal.fromDoubleArray(doubleArrayOf(1.0, 2.0, 3.0, 4.0))
    val src = mm256SetrPd(9.0, 10.0, 11.0, 12.0)
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val r = mm256MmaskI64gatherPd(src, 0x5u, vindex, localStorageL8025C8.elementConstPtr(0), 8)
    val expected = mm256SetrPd(2.0, 10.0, 4.0, 12.0)
    assertEqM256d(expected, r)
}

public fun testMm256MmaskI64gatherPs() {
    val localStorageL8035C8 = AddressableArrayLocal.fromFloatArray(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f))
    val src = mmSetrPs(9.0f, 10.0f, 11.0f, 12.0f)
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val r = mm256MmaskI64gatherPs(src, 0x5u, vindex, localStorageL8035C8.elementConstPtr(0), 4)
    val expected = mmSetrPs(2.0f, 10.0f, 4.0f, 12.0f)
    assertEqM128(expected, r)
}

public fun testMmI32scatterEpi32() {
    val localStorageL8045C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    var baseAddr by localStorageL8045C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mmSetrEpi32(2, 3, 4, 1)
    run {
        mmI32scatterEpi32(localStorageL8045C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1, 2, 3, 4)
    check(expected == baseAddr)
}

public fun testMmMaskI32scatterEpi32() {
    val localStorageL8057C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    var baseAddr by localStorageL8057C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mmSetrEpi32(2, 3, 4, 1)
    run {
        mmMaskI32scatterEpi32(localStorageL8057C8.elementMutPtr(0), 0x5u, vindex, src, 4)
    }
    val expected = listOf(0, 2, 0, 4)
    check(expected == baseAddr)
}

public fun testMmI32scatterEpi64() {
    val localStorageL8069C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 0L })
    var baseAddr by localStorageL8069C8
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val src = mmSetrEpi64x(2, 1)
    run {
        mmI32scatterEpi64(localStorageL8069C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1, 2)
    check(expected == baseAddr)
}

public fun testMmMaskI32scatterEpi64() {
    val localStorageL8081C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 0L })
    var baseAddr by localStorageL8081C8
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val src = mmSetrEpi64x(2, 1)
    run {
        mmMaskI32scatterEpi64(localStorageL8081C8.elementMutPtr(0), 0x1u, vindex, src, 8)
    }
    val expected = listOf(0, 2)
    check(expected == baseAddr)
}

public fun testMmI32scatterPd() {
    val localStorageL8093C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(2) { 0.0 })
    var baseAddr by localStorageL8093C8
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val src = mmSetrPd(2.0, 1.0)
    run {
        mmI32scatterPd(localStorageL8093C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1.0, 2.0)
    check(expected == baseAddr)
}

public fun testMmMaskI32scatterPd() {
    val localStorageL8105C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(2) { 0.0 })
    var baseAddr by localStorageL8105C8
    val vindex = mmSetrEpi32(1, 0, -1, -1)
    val src = mmSetrPd(2.0, 1.0)
    run {
        mmMaskI32scatterPd(localStorageL8105C8.elementMutPtr(0), 0x1u, vindex, src, 8)
    }
    val expected = listOf(0.0, 2.0)
    check(expected == baseAddr)
}

public fun testMmI32scatterPs() {
    val localStorageL8117C8 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.0f })
    var baseAddr by localStorageL8117C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mmSetrPs(2.0f, 3.0f, 4.0f, 1.0f)
    run {
        mmI32scatterPs(localStorageL8117C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0)
    check(expected == baseAddr)
}

public fun testMmMaskI32scatterPs() {
    val localStorageL8129C8 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.0f })
    var baseAddr by localStorageL8129C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mmSetrPs(2.0f, 3.0f, 4.0f, 1.0f)
    run {
        mmMaskI32scatterPs(localStorageL8129C8.elementMutPtr(0), 0x5u, vindex, src, 4)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0)
    check(expected == baseAddr)
}

public fun testMmI64scatterEpi32() {
    val localStorageL8141C8 = AddressableArrayLocal.fromIntArray(IntArray(2) { 0 })
    var baseAddr by localStorageL8141C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrEpi32(2, 1, -1, -1)
    run {
        mmI64scatterEpi32(localStorageL8141C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1, 2)
    check(expected == baseAddr)
}

public fun testMmMaskI64scatterEpi32() {
    val localStorageL8153C8 = AddressableArrayLocal.fromIntArray(IntArray(2) { 0 })
    var baseAddr by localStorageL8153C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrEpi32(2, 1, -1, -1)
    run {
        mmMaskI64scatterEpi32(localStorageL8153C8.elementMutPtr(0), 0x1u, vindex, src, 4)
    }
    val expected = listOf(0, 2)
    check(expected == baseAddr)
}

public fun testMmI64scatterEpi64() {
    val localStorageL8165C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 0L })
    var baseAddr by localStorageL8165C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrEpi64x(2, 1)
    run {
        mmI64scatterEpi64(localStorageL8165C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1, 2)
    check(expected == baseAddr)
}

public fun testMmMaskI64scatterEpi64() {
    val localStorageL8177C8 = AddressableArrayLocal.fromLongArray(LongArray(2) { 0L })
    var baseAddr by localStorageL8177C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrEpi64x(2, 1)
    run {
        mmMaskI64scatterEpi64(localStorageL8177C8.elementMutPtr(0), 0x1u, vindex, src, 8)
    }
    val expected = listOf(0, 2)
    check(expected == baseAddr)
}

public fun testMmI64scatterPd() {
    val localStorageL8189C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(2) { 0.0 })
    var baseAddr by localStorageL8189C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrPd(2.0, 1.0)
    run {
        mmI64scatterPd(localStorageL8189C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1.0, 2.0)
    check(expected == baseAddr)
}

public fun testMmMaskI64scatterPd() {
    val localStorageL8201C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(2) { 0.0 })
    var baseAddr by localStorageL8201C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrPd(2.0, 1.0)
    run {
        mmMaskI64scatterPd(localStorageL8201C8.elementMutPtr(0), 0x1u, vindex, src, 8)
    }
    val expected = listOf(0.0, 2.0)
    check(expected == baseAddr)
}

public fun testMmI64scatterPs() {
    val localStorageL8213C8 = AddressableArrayLocal.fromFloatArray(FloatArray(2) { 0.0f })
    var baseAddr by localStorageL8213C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrPs(2.0f, 1.0f, -1.0f, -1.0f)
    run {
        mmI64scatterPs(localStorageL8213C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1.0, 2.0)
    check(expected == baseAddr)
}

public fun testMmMaskI64scatterPs() {
    val localStorageL8225C8 = AddressableArrayLocal.fromFloatArray(FloatArray(2) { 0.0f })
    var baseAddr by localStorageL8225C8
    val vindex = mmSetrEpi64x(1, 0)
    val src = mmSetrPs(2.0f, 1.0f, -1.0f, -1.0f)
    run {
        mmMaskI64scatterPs(localStorageL8225C8.elementMutPtr(0), 0x1u, vindex, src, 4)
    }
    val expected = listOf(0.0, 2.0)
    check(expected == baseAddr)
}

public fun testMm256I32scatterEpi32() {
    val localStorageL8237C8 = AddressableArrayLocal.fromIntArray(IntArray(8) { 0 })
    var baseAddr by localStorageL8237C8
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val src = mm256SetrEpi32(2, 3, 4, 5, 6, 7, 8, 1)
    run {
        mm256I32scatterEpi32(localStorageL8237C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    check(expected == baseAddr)
}

public fun testMm256MaskI32scatterEpi32() {
    val localStorageL8249C8 = AddressableArrayLocal.fromIntArray(IntArray(8) { 0 })
    var baseAddr by localStorageL8249C8
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val src = mm256SetrEpi32(2, 3, 4, 5, 6, 7, 8, 1)
    run {
        mm256MaskI32scatterEpi32(localStorageL8249C8.elementMutPtr(0), 0x55u, vindex, src, 4)
    }
    val expected = listOf(0, 2, 0, 4, 0, 6, 0, 8)
    check(expected == baseAddr)
}

public fun testMm256I32scatterEpi64() {
    val localStorageL8261C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0L })
    var baseAddr by localStorageL8261C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mm256SetrEpi64x(2L, 3L, 4L, 1L)
    run {
        mm256I32scatterEpi64(localStorageL8261C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1, 2, 3, 4)
    check(expected == baseAddr)
}

public fun testMm256MaskI32scatterEpi64() {
    val localStorageL8273C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0L })
    var baseAddr by localStorageL8273C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mm256SetrEpi64x(2L, 3L, 4L, 1L)
    run {
        mm256MaskI32scatterEpi64(localStorageL8273C8.elementMutPtr(0), 0x5u, vindex, src, 8)
    }
    val expected = listOf(0, 2, 0, 4)
    check(expected == baseAddr)
}

public fun testMm256I32scatterPd() {
    val localStorageL8285C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(4) { 0.0 })
    var baseAddr by localStorageL8285C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mm256SetrPd(2.0, 3.0, 4.0, 1.0)
    run {
        mm256I32scatterPd(localStorageL8285C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm256MaskI32scatterPd() {
    val localStorageL8297C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(4) { 0.0 })
    var baseAddr by localStorageL8297C8
    val vindex = mmSetrEpi32(1, 2, 3, 0)
    val src = mm256SetrPd(2.0, 3.0, 4.0, 1.0)
    run {
        mm256MaskI32scatterPd(localStorageL8297C8.elementMutPtr(0), 0x5u, vindex, src, 8)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm256I32scatterPs() {
    val localStorageL8309C8 = AddressableArrayLocal.fromFloatArray(FloatArray(8) { 0.0f })
    var baseAddr by localStorageL8309C8
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val src = mm256SetrPs(2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 1.0f)
    run {
        mm256I32scatterPs(localStorageL8309C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    check(expected == baseAddr)
}

public fun testMm256MaskI32scatterPs() {
    val localStorageL8321C8 = AddressableArrayLocal.fromFloatArray(FloatArray(8) { 0.0f })
    var baseAddr by localStorageL8321C8
    val vindex = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    val src = mm256SetrPs(2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 1.0f)
    run {
        mm256MaskI32scatterPs(localStorageL8321C8.elementMutPtr(0), 0x55u, vindex, src, 4)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0, 0.0, 6.0, 0.0, 8.0)
    check(expected == baseAddr)
}

public fun testMm256I64scatterEpi32() {
    val localStorageL8333C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    var baseAddr by localStorageL8333C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mmSetrEpi32(2, 3, 4, 1)
    run {
        mm256I64scatterEpi32(localStorageL8333C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1, 2, 3, 4)
    check(expected == baseAddr)
}

public fun testMm256MaskI64scatterEpi32() {
    val localStorageL8345C8 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    var baseAddr by localStorageL8345C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mmSetrEpi32(2, 3, 4, 1)
    run {
        mm256MaskI64scatterEpi32(localStorageL8345C8.elementMutPtr(0), 0x5u, vindex, src, 4)
    }
    val expected = listOf(0, 2, 0, 4)
    check(expected == baseAddr)
}

public fun testMm256I64scatterEpi64() {
    val localStorageL8357C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0L })
    var baseAddr by localStorageL8357C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mm256SetrEpi64x(2L, 3L, 4L, 1L)
    run {
        mm256I64scatterEpi64(localStorageL8357C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1, 2, 3, 4)
    check(expected == baseAddr)
}

public fun testMm256MaskI64scatterEpi64() {
    val localStorageL8369C8 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0L })
    var baseAddr by localStorageL8369C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mm256SetrEpi64x(2L, 3L, 4L, 1L)
    run {
        mm256MaskI64scatterEpi64(localStorageL8369C8.elementMutPtr(0), 0x5u, vindex, src, 8)
    }
    val expected = listOf(0, 2, 0, 4)
    check(expected == baseAddr)
}

public fun testMm256I64scatterPd() {
    val localStorageL8381C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(4) { 0.0 })
    var baseAddr by localStorageL8381C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mm256SetrPd(2.0, 3.0, 4.0, 1.0)
    run {
        mm256I64scatterPd(localStorageL8381C8.elementMutPtr(0), vindex, src, 8)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm256MaskI64scatterPd() {
    val localStorageL8393C8 = AddressableArrayLocal.fromDoubleArray(DoubleArray(4) { 0.0 })
    var baseAddr by localStorageL8393C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mm256SetrPd(2.0, 3.0, 4.0, 1.0)
    run {
        mm256MaskI64scatterPd(localStorageL8393C8.elementMutPtr(0), 0x5u, vindex, src, 8)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm256I64scatterPs() {
    val localStorageL8405C8 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.0f })
    var baseAddr by localStorageL8405C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mmSetrPs(2.0f, 3.0f, 4.0f, 1.0f)
    run {
        mm256I64scatterPs(localStorageL8405C8.elementMutPtr(0), vindex, src, 4)
    }
    val expected = listOf(1.0, 2.0, 3.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm256MaskI64scatterPs() {
    val localStorageL8417C8 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.0f })
    var baseAddr by localStorageL8417C8
    val vindex = mm256SetrEpi64x(1L, 2L, 3L, 0L)
    val src = mmSetrPs(2.0f, 3.0f, 4.0f, 1.0f)
    run {
        mm256MaskI64scatterPs(localStorageL8417C8.elementMutPtr(0), 0x5u, vindex, src, 4)
    }
    val expected = listOf(0.0, 2.0, 0.0, 4.0)
    check(expected == baseAddr)
}

public fun testMm512RolEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512RolEpi64(a, 1)
    val e = mm512SetEpi64(1L shl 0, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r, e)
}

public fun testMm512MaskRolEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512MaskRolEpi64(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskRolEpi64(a, 0xFFu, a, 1)
    val e = mm512SetEpi64(1L shl 0, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRolEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 63)
    val r = mm512MaskzRolEpi64(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRolEpi64(0xFu, a, 1)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 0)
    assertEqM512i(r1, e)
}

public fun testMm256RolEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256RolEpi64(a, 1)
    val e = mm256SetEpi64x(1L shl 0, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r, e)
}

public fun testMm256MaskRolEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskRolEpi64(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskRolEpi64(a, 0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 0, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRolEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskzRolEpi64(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRolEpi64(0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 0, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMmRolEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val r = mmRolEpi64(a, 1)
    val e = mmSetEpi64x(1L shl 0, 1L shl 33)
    assertEqM128i(r, e)
}

public fun testMmMaskRolEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val r = mmMaskRolEpi64(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskRolEpi64(a, 0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 0, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRolEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val r = mmMaskzRolEpi64(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRolEpi64(0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 0, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMm512RorEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512RorEpi64(a, 1)
    val e = mm512SetEpi64(1L shl 63, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r, e)
}

public fun testMm512MaskRorEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512MaskRorEpi64(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskRorEpi64(a, 0xFFu, a, 1)
    val e = mm512SetEpi64(1L shl 63, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRorEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 0)
    val r = mm512MaskzRorEpi64(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRorEpi64(0xFu, a, 1)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 63)
    assertEqM512i(r1, e)
}

public fun testMm256RorEpi64() {
    val a = mm256SetEpi64x(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256RorEpi64(a, 1)
    val e = mm256SetEpi64x(1L shl 63, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM256i(r, e)
}

public fun testMm256MaskRorEpi64() {
    val a = mm256SetEpi64x(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskRorEpi64(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskRorEpi64(a, 0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 63, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRorEpi64() {
    val a = mm256SetEpi64x(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskzRorEpi64(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRorEpi64(0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 63, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM256i(r1, e)
}

public fun testMmRorEpi64() {
    val a = mmSetEpi64x(1L shl 0, 1L shl 32)
    val r = mmRorEpi64(a, 1)
    val e = mmSetEpi64x(1L shl 63, 1L shl 31)
    assertEqM128i(r, e)
}

public fun testMmMaskRorEpi64() {
    val a = mmSetEpi64x(1L shl 0, 1L shl 32)
    val r = mmMaskRorEpi64(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskRorEpi64(a, 0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 63, 1L shl 31)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRorEpi64() {
    val a = mmSetEpi64x(1L shl 0, 1L shl 32)
    val r = mmMaskzRorEpi64(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRorEpi64(0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 63, 1L shl 31)
    assertEqM128i(r1, e)
}

public fun testMm512SlliEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512SlliEpi64(a, 1)
    val e = mm512SetEpi64(0L, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r, e)
}

public fun testMm512MaskSlliEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512MaskSlliEpi64(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskSlliEpi64(a, 0xFFu, a, 1)
    val e = mm512SetEpi64(0L, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSlliEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 63)
    val r = mm512MaskzSlliEpi64(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSlliEpi64(0xFu, a, 1)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 33, 1L shl 33, 1L shl 33, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSlliEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskSlliEpi64(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSlliEpi64(a, 0xFu, a, 1)
    val e = mm256SetEpi64x(0L, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSlliEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm256MaskzSlliEpi64(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSlliEpi64(0xFu, a, 1)
    val e = mm256SetEpi64x(0L, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMmMaskSlliEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val r = mmMaskSlliEpi64(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSlliEpi64(a, 0x3u, a, 1)
    val e = mmSetEpi64x(0L, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSlliEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val r = mmMaskzSlliEpi64(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSlliEpi64(0x3u, a, 1)
    val e = mmSetEpi64x(0L, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMm512SrliEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512SrliEpi64(a, 1)
    val e = mm512SetEpi64(0L, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrliEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val r = mm512MaskSrliEpi64(a, 0u, a, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrliEpi64(a, 0xFFu, a, 1)
    val e = mm512SetEpi64(0L, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrliEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 0)
    val r = mm512MaskzSrliEpi64(0u, a, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrliEpi64(0xFu, a, 1)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 31, 1L shl 31, 1L shl 31, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrliEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val r = mm256MaskSrliEpi64(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrliEpi64(a, 0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrliEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val r = mm256MaskzSrliEpi64(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrliEpi64(0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrliEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val r = mmMaskSrliEpi64(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSrliEpi64(a, 0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrliEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val r = mmMaskzSrliEpi64(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrliEpi64(0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512RolvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512RolvEpi64(a, b)
    val e = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 34, 1L shl 35, 1L shl 36, 1L shl 37, 1L shl 38, 1L shl 39)
    assertEqM512i(r, e)
}

public fun testMm512MaskRolvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskRolvEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskRolvEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 34, 1L shl 35, 1L shl 36, 1L shl 37, 1L shl 38, 1L shl 39)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRolvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 62)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 2L)
    val r = mm512MaskzRolvEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRolvEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 36, 1L shl 37, 1L shl 38, 1L shl 0)
    assertEqM512i(r1, e)
}

public fun testMm256RolvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256RolvEpi64(a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 34, 1L shl 35)
    assertEqM256i(r, e)
}

public fun testMm256MaskRolvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskRolvEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskRolvEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 34, 1L shl 35)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRolvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzRolvEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRolvEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 34, 1L shl 35)
    assertEqM256i(r1, e)
}

public fun testMmRolvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 63)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmRolvEpi64(a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 0)
    assertEqM128i(r, e)
}

public fun testMmMaskRolvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 63)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmMaskRolvEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskRolvEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRolvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 63)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmMaskzRolvEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRolvEpi64(0x3u, a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 0)
    assertEqM128i(r1, e)
}

public fun testMm512RorvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512RorvEpi64(a, b)
    val e = mm512SetEpi64(1L shl 32, 1L shl 63, 1L shl 30, 1L shl 29, 1L shl 28, 1L shl 27, 1L shl 26, 1L shl 25)
    assertEqM512i(r, e)
}

public fun testMm512MaskRorvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskRorvEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskRorvEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(1L shl 32, 1L shl 63, 1L shl 30, 1L shl 29, 1L shl 28, 1L shl 27, 1L shl 26, 1L shl 25)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzRorvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 0)
    val b = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 2L)
    val r = mm512MaskzRorvEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzRorvEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 28, 1L shl 27, 1L shl 26, 1L shl 62)
    assertEqM512i(r1, e)
}

public fun testMm256RorvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256RorvEpi64(a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 30, 1L shl 29)
    assertEqM256i(r, e)
}

public fun testMm256MaskRorvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskRorvEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskRorvEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 30, 1L shl 29)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzRorvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32)
    val b = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzRorvEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzRorvEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 63, 1L shl 30, 1L shl 29)
    assertEqM256i(r1, e)
}

public fun testMmRorvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 0)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmRorvEpi64(a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 63)
    assertEqM128i(r, e)
}

public fun testMmMaskRorvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 0)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmMaskRorvEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskRorvEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 63)
    assertEqM128i(r1, e)
}

public fun testMmMaskzRorvEpi64() {
    val a = mmSetEpi64x(1L shl 32, 1L shl 0)
    val b = mmSetEpi64x(0L, 1L)
    val r = mmMaskzRorvEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzRorvEpi64(0x3u, a, b)
    val e = mmSetEpi64x(1L shl 32, 1L shl 63)
    assertEqM128i(r1, e)
}

public fun testMm512SllvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mm512SetEpi64(0L, 2L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512SllvEpi64(a, count)
    val e = mm512SetEpi64(1L shl 32, 0L, 1L shl 34, 1L shl 35, 1L shl 36, 1L shl 37, 1L shl 38, 1L shl 39)
    assertEqM512i(r, e)
}

public fun testMm512MaskSllvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskSllvEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllvEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(1L shl 32, 1L shl 33, 0L, 1L shl 35, 1L shl 36, 1L shl 37, 1L shl 38, 1L shl 39)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 63)
    val count = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 1L)
    val r = mm512MaskzSllvEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllvEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 36, 1L shl 37, 1L shl 38, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSllvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 32, 1L shl 63, 1L shl 32)
    val count = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskSllvEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllvEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 33, 0L, 1L shl 35)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllvEpi64() {
    val a = mm256SetEpi64x(1L shl 32, 1L shl 32, 1L shl 63, 1L shl 32)
    val count = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzSllvEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllvEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 32, 1L shl 33, 0L, 1L shl 35)
    assertEqM256i(r1, e)
}

public fun testMmMaskSllvEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val count = mmSetEpi64x(2L, 3L)
    val r = mmMaskSllvEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllvEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(0L, 1L shl 35)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllvEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val count = mmSetEpi64x(2L, 3L)
    val r = mmMaskzSllvEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllvEpi64(0x3u, a, count)
    val e = mmSetEpi64x(0L, 1L shl 35)
    assertEqM128i(r1, e)
}

public fun testMm512SrlvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512SrlvEpi64(a, count)
    val e = mm512SetEpi64(1L shl 32, 0L, 1L shl 30, 1L shl 29, 1L shl 28, 1L shl 27, 1L shl 26, 1L shl 25)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskSrlvEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlvEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(1L shl 32, 0L, 1L shl 30, 1L shl 29, 1L shl 28, 1L shl 27, 1L shl 26, 1L shl 25)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlvEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 0)
    val count = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskzSrlvEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlvEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 28, 1L shl 27, 1L shl 26, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrlvEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256MaskSrlvEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlvEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlvEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256MaskzSrlvEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlvEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrlvEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSet1Epi64x(1L)
    val r = mmMaskSrlvEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlvEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlvEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSet1Epi64x(1L)
    val r = mmMaskzSrlvEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlvEpi64(0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512SllEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512SllEpi64(a, count)
    val e = mm512SetEpi64(0L, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r, e)
    val count1 = mmSetEpi64x(1L, 0L)
    val r1 = mm512SllEpi64(a, count1)
    assertEqM512i(r1, a)
}

public fun testMm512MaskSllEpi64() {
    val a = mm512SetEpi64(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512MaskSllEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSllEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(0L, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSllEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 63)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512MaskzSllEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSllEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 33, 1L shl 33, 1L shl 33, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSllEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskSllEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSllEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(0L, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSllEpi64() {
    val a = mm256SetEpi64x(1L shl 63, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskzSllEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSllEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(0L, 1L shl 33, 1L shl 33, 1L shl 33)
    assertEqM256i(r1, e)
}

public fun testMmMaskSllEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskSllEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSllEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(0L, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSllEpi64() {
    val a = mmSetEpi64x(1L shl 63, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskzSllEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSllEpi64(0x3u, a, count)
    val e = mmSetEpi64x(0L, 1L shl 33)
    assertEqM128i(r1, e)
}

public fun testMm512SrlEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512SrlEpi64(a, count)
    val e = mm512SetEpi64(0L, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r, e)
}

public fun testMm512MaskSrlEpi64() {
    val a = mm512SetEpi64(1L shl 0, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512MaskSrlEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSrlEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(0L, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31, 1L shl 31)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSrlEpi64() {
    val a = mm512SetEpi64(1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 32, 1L shl 0)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm512MaskzSrlEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSrlEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 31, 1L shl 31, 1L shl 31, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSrlEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskSrlEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSrlEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSrlEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskzSrlEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSrlEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmMaskSrlEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskSrlEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSrlEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSrlEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskzSrlEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSrlEpi64(0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512SraEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mmSetEpi64x(0L, 2L)
    val r = mm512SraEpi64(a, count)
    val e = mm512SetEpi64(0L, -2L, 0L, 0L, 0L, 0L, 3L, -4L)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mmSetEpi64x(0L, 2L)
    val r = mm512MaskSraEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(0L, -2L, 0L, 0L, 0L, 0L, 3L, -4L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mmSetEpi64x(0L, 2L)
    val r = mm512MaskzSraEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 3L, -4L)
    assertEqM512i(r1, e)
}

public fun testMm256SraEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256SraEpi64(a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskSraEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskSraEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mm256MaskzSraEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmSraEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmSraEpi64(a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r, e)
}

public fun testMmMaskSraEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskSraEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSraEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSetEpi64x(0L, 1L)
    val r = mmMaskzSraEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraEpi64(0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512SravEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mm512SetEpi64(2L, 2L, 0L, 0L, 0L, 0L, 2L, 1L)
    val r = mm512SravEpi64(a, count)
    val e = mm512SetEpi64(0L, -2L, 0L, 0L, 0L, 0L, 3L, -8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskSravEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mm512SetEpi64(2L, 2L, 0L, 0L, 0L, 0L, 2L, 1L)
    val r = mm512MaskSravEpi64(a, 0u, a, count)
    assertEqM512i(r, a)
    val r1 = mm512MaskSravEpi64(a, 0xFFu, a, count)
    val e = mm512SetEpi64(0L, -2L, 0L, 0L, 0L, 0L, 3L, -8L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSravEpi64() {
    val a = mm512SetEpi64(1L, -8L, 0L, 0L, 0L, 0L, 15L, -16L)
    val count = mm512SetEpi64(2L, 2L, 0L, 0L, 0L, 0L, 2L, 1L)
    val r = mm512MaskzSravEpi64(0u, a, count)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSravEpi64(0xFu, a, count)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 3L, -8L)
    assertEqM512i(r1, e)
}

public fun testMm256SravEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256SravEpi64(a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskSravEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256MaskSravEpi64(a, 0u, a, count)
    assertEqM256i(r, a)
    val r1 = mm256MaskSravEpi64(a, 0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSravEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val count = mm256Set1Epi64x(1L)
    val r = mm256MaskzSravEpi64(0u, a, count)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSravEpi64(0xFu, a, count)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmSravEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSet1Epi64x(1L)
    val r = mmSravEpi64(a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r, e)
    val a1 = mmSetEpi64x(-1L, -2L)
    val b = mmSetEpi64x(64L, 65L)
    val r1 = mmSravEpi64(a1, b)
    val e1 = mmSetEpi64x((-1L).unboundedShr(64), (-2L).unboundedShr(65))
    assertEqM128i(r1, e1)
    val e2 = mmSetEpi64x(-1L, -1L)
    assertEqM128i(r1, e2)
}

public fun testMmMaskSravEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSet1Epi64x(1L)
    val r = mmMaskSravEpi64(a, 0u, a, count)
    assertEqM128i(r, a)
    val r1 = mmMaskSravEpi64(a, 0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSravEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val count = mmSet1Epi64x(1L)
    val r = mmMaskzSravEpi64(0u, a, count)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSravEpi64(0x3u, a, count)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512SraiEpi64() {
    val a = mm512SetEpi64(1L, -4L, 15L, 0L, 0L, 0L, 0L, -16L)
    val r = mm512SraiEpi64(a, 2)
    val e = mm512SetEpi64(0L, -1L, 3L, 0L, 0L, 0L, 0L, -4L)
    assertEqM512i(r, e)
}

public fun testMm512MaskSraiEpi64() {
    val a = mm512SetEpi64(1L, -4L, 15L, 0L, 0L, 0L, 0L, -16L)
    val r = mm512MaskSraiEpi64(a, 0u, a, 2)
    assertEqM512i(r, a)
    val r1 = mm512MaskSraiEpi64(a, 0xFFu, a, 2)
    val e = mm512SetEpi64(0L, -1L, 3L, 0L, 0L, 0L, 0L, -4L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSraiEpi64() {
    val a = mm512SetEpi64(1L, -4L, 15L, 0L, 0L, 0L, 0L, -16L)
    val r = mm512MaskzSraiEpi64(0u, a, 2)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSraiEpi64(0xFu, a, 2)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, -4L)
    assertEqM512i(r1, e)
}

public fun testMm256SraiEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val r = mm256SraiEpi64(a, 1)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256MaskSraiEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val r = mm256MaskSraiEpi64(a, 0u, a, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskSraiEpi64(a, 0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSraiEpi64() {
    val a = mm256SetEpi64x(1L shl 5, 0L, 0L, 0L)
    val r = mm256MaskzSraiEpi64(0u, a, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSraiEpi64(0xFu, a, 1)
    val e = mm256SetEpi64x(1L shl 4, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMmSraiEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val r = mmSraiEpi64(a, 1)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r, e)
}

public fun testMmMaskSraiEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val r = mmMaskSraiEpi64(a, 0u, a, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskSraiEpi64(a, 0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSraiEpi64() {
    val a = mmSetEpi64x(1L shl 5, 0L)
    val r = mmMaskzSraiEpi64(0u, a, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSraiEpi64(0x3u, a, 1)
    val e = mmSetEpi64x(1L shl 4, 0L)
    assertEqM128i(r1, e)
}

public fun testMm512PermutePd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512PermutePd(a, 0xFF)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskPermutePd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskPermutePd(a, 0u, a, 0xFF)
    assertEqM512d(r, a)
    val r1 = mm512MaskPermutePd(a, 0xFFu, a, 0xFF)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzPermutePd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskzPermutePd(0u, a, 0xFF)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzPermutePd(0xFFu, a, 0xFF)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskPermutePd() {
    val a = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskPermutePd(a, 0u, a, 0xF)
    assertEqM256d(r, a)
    val r1 = mm256MaskPermutePd(a, 0xFu, a, 0xF)
    val e = mm256SetPd(3.0, 3.0, 1.0, 1.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzPermutePd() {
    val a = mm256SetPd(3.0, 2.0, 1.0, 0.0)
    val r = mm256MaskzPermutePd(0u, a, 0xF)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzPermutePd(0xFu, a, 0xF)
    val e = mm256SetPd(3.0, 3.0, 1.0, 1.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskPermutePd() {
    val a = mmSetPd(1.0, 0.0)
    val r = mmMaskPermutePd(a, 0u, a, 0x3)
    assertEqM128d(r, a)
    val r1 = mmMaskPermutePd(a, 0x3u, a, 0x3)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzPermutePd() {
    val a = mmSetPd(1.0, 0.0)
    val r = mmMaskzPermutePd(0u, a, 0x3)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzPermutePd(0x3u, a, 0x3)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512PermutexEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512PermutexEpi64(a, 0xFF)
    val e = mm512SetrEpi64(3L, 3L, 3L, 3L, 7L, 7L, 7L, 7L)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutexEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskPermutexEpi64(a, 0u, a, 0xFF)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutexEpi64(a, 0xFFu, a, 0xFF)
    val e = mm512SetrEpi64(3L, 3L, 3L, 3L, 7L, 7L, 7L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutexEpi64() {
    val a = mm512SetrEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskzPermutexEpi64(0u, a, 0xFF)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutexEpi64(0xFFu, a, 0xFF)
    val e = mm512SetrEpi64(3L, 3L, 3L, 3L, 7L, 7L, 7L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm256PermutexEpi64() {
    val a = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256PermutexEpi64(a, 0xFF)
    val e = mm256SetEpi64x(3L, 3L, 3L, 3L)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutexEpi64() {
    val a = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskPermutexEpi64(a, 0u, a, 0xFF)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutexEpi64(a, 0xFu, a, 0xFF)
    val e = mm256SetEpi64x(3L, 3L, 3L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutexEpi64() {
    val a = mm256SetEpi64x(3L, 2L, 1L, 0L)
    val r = mm256MaskzPermutexEpi64(0u, a, 0xFF)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutexEpi64(0xFu, a, 0xFF)
    val e = mm256SetEpi64x(3L, 3L, 3L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm512PermutexPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512PermutexPd(a, 0xFF)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 7.0, 7.0, 7.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskPermutexPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskPermutexPd(a, 0u, a, 0xFF)
    assertEqM512d(r, a)
    val r1 = mm512MaskPermutexPd(a, 0xFFu, a, 0xFF)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 7.0, 7.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzPermutexPd() {
    val a = mm512SetrPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskzPermutexPd(0u, a, 0xFF)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzPermutexPd(0xFFu, a, 0xFF)
    val e = mm512SetrPd(3.0, 3.0, 3.0, 3.0, 7.0, 7.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256PermutexPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256PermutexPd(a, 0xFF)
    val e = mm256SetPd(0.0, 0.0, 0.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskPermutexPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskPermutexPd(a, 0u, a, 0xFF)
    assertEqM256d(r, a)
    val r1 = mm256MaskPermutexPd(a, 0xFu, a, 0xFF)
    val e = mm256SetPd(0.0, 0.0, 0.0, 0.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzPermutexPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskzPermutexPd(0u, a, 0xFF)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzPermutexPd(0xFu, a, 0xFF)
    val e = mm256SetPd(0.0, 0.0, 0.0, 0.0)
    assertEqM256d(r1, e)
}

public fun testMm512PermutevarPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512Set1Epi64(0x1L)
    val r = mm512PermutevarPd(a, b)
    val e = mm512SetPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskPermutevarPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512Set1Epi64(0x1L)
    val r = mm512MaskPermutevarPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskPermutevarPd(a, 0xFFu, a, b)
    val e = mm512SetPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzPermutevarPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val b = mm512Set1Epi64(0x1L)
    val r = mm512MaskzPermutevarPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzPermutevarPd(0xFu, a, b)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskPermutevarPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256Set1Epi64x(0x1L)
    val r = mm256MaskPermutevarPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskPermutevarPd(a, 0xFu, a, b)
    val e = mm256SetPd(1.0, 1.0, 3.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzPermutevarPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val b = mm256Set1Epi64x(0x1L)
    val r = mm256MaskzPermutevarPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzPermutevarPd(0xFu, a, b)
    val e = mm256SetPd(1.0, 1.0, 3.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskPermutevarPd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSet1Epi64x(0x1L)
    val r = mmMaskPermutevarPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskPermutevarPd(a, 0x3u, a, b)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzPermutevarPd() {
    val a = mmSetPd(0.0, 1.0)
    val b = mmSet1Epi64x(0x1L)
    val r = mmMaskzPermutevarPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzPermutevarPd(0x3u, a, b)
    val e = mmSetPd(1.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512PermutexvarEpi64() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512PermutexvarEpi64(idx, a)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutexvarEpi64() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskPermutexvarEpi64(a, 0u, idx, a)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutexvarEpi64(a, 0xFFu, idx, a)
    val e = mm512Set1Epi64(6L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutexvarEpi64() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskzPermutexvarEpi64(0u, idx, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutexvarEpi64(0xFu, idx, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 6L, 6L, 6L, 6L)
    assertEqM512i(r1, e)
}

public fun testMm256PermutexvarEpi64() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256PermutexvarEpi64(idx, a)
    val e = mm256Set1Epi64x(2L)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutexvarEpi64() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskPermutexvarEpi64(a, 0u, idx, a)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutexvarEpi64(a, 0xFu, idx, a)
    val e = mm256Set1Epi64x(2L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutexvarEpi64() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzPermutexvarEpi64(0u, idx, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutexvarEpi64(0xFu, idx, a)
    val e = mm256Set1Epi64x(2L)
    assertEqM256i(r1, e)
}

public fun testMm512PermutexvarPd() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512PermutexvarPd(idx, a)
    val e = mm512Set1Pd(6.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskPermutexvarPd() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskPermutexvarPd(a, 0u, idx, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskPermutexvarPd(a, 0xFFu, idx, a)
    val e = mm512Set1Pd(6.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzPermutexvarPd() {
    val idx = mm512Set1Epi64(1L)
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskzPermutexvarPd(0u, idx, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzPermutexvarPd(0xFu, idx, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 6.0, 6.0, 6.0, 6.0)
    assertEqM512d(r1, e)
}

public fun testMm256PermutexvarPd() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256PermutexvarPd(idx, a)
    val e = mm256Set1Pd(2.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskPermutexvarPd() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskPermutexvarPd(a, 0u, idx, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskPermutexvarPd(a, 0xFu, idx, a)
    val e = mm256Set1Pd(2.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzPermutexvarPd() {
    val idx = mm256Set1Epi64x(1L)
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskzPermutexvarPd(0u, idx, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzPermutexvarPd(0xFu, idx, a)
    val e = mm256Set1Pd(2.0)
    assertEqM256d(r1, e)
}

public fun testMm512Permutex2varEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Epi64(100L)
    val r = mm512Permutex2varEpi64(a, idx, b)
    val e = mm512SetEpi64(6L, 100L, 5L, 100L, 4L, 100L, 3L, 100L)
    assertEqM512i(r, e)
}

public fun testMm512MaskPermutex2varEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Epi64(100L)
    val r = mm512MaskPermutex2varEpi64(a, 0u, idx, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskPermutex2varEpi64(a, 0xFFu, idx, b)
    val e = mm512SetEpi64(6L, 100L, 5L, 100L, 4L, 100L, 3L, 100L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzPermutex2varEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Epi64(100L)
    val r = mm512MaskzPermutex2varEpi64(0u, a, idx, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzPermutex2varEpi64(0xFu, a, idx, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 4L, 100L, 3L, 100L)
    assertEqM512i(r1, e)
}

public fun testMm512Mask2Permutex2varEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val idx = mm512SetEpi64(1000L, 1L shl 3, 2000L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Epi64(100L)
    val r = mm512Mask2Permutex2varEpi64(a, idx, 0u, b)
    assertEqM512i(r, idx)
    val r1 = mm512Mask2Permutex2varEpi64(a, idx, 0xFu, b)
    val e = mm512SetEpi64(1000L, 1L shl 3, 2000L, 1L shl 3, 4L, 100L, 3L, 100L)
    assertEqM512i(r1, e)
}

public fun testMm256Permutex2varEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Epi64x(100L)
    val r = mm256Permutex2varEpi64(a, idx, b)
    val e = mm256SetEpi64x(2L, 100L, 1L, 100L)
    assertEqM256i(r, e)
}

public fun testMm256MaskPermutex2varEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Epi64x(100L)
    val r = mm256MaskPermutex2varEpi64(a, 0u, idx, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskPermutex2varEpi64(a, 0xFu, idx, b)
    val e = mm256SetEpi64x(2L, 100L, 1L, 100L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzPermutex2varEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Epi64x(100L)
    val r = mm256MaskzPermutex2varEpi64(0u, a, idx, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzPermutex2varEpi64(0xFu, a, idx, b)
    val e = mm256SetEpi64x(2L, 100L, 1L, 100L)
    assertEqM256i(r1, e)
}

public fun testMm256Mask2Permutex2varEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Epi64x(100L)
    val r = mm256Mask2Permutex2varEpi64(a, idx, 0u, b)
    assertEqM256i(r, idx)
    val r1 = mm256Mask2Permutex2varEpi64(a, idx, 0xFu, b)
    val e = mm256SetEpi64x(2L, 100L, 1L, 100L)
    assertEqM256i(r1, e)
}

public fun testMmPermutex2varEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Epi64x(100L)
    val r = mmPermutex2varEpi64(a, idx, b)
    val e = mmSetEpi64x(0L, 100L)
    assertEqM128i(r, e)
}

public fun testMmMaskPermutex2varEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Epi64x(100L)
    val r = mmMaskPermutex2varEpi64(a, 0u, idx, b)
    assertEqM128i(r, a)
    val r1 = mmMaskPermutex2varEpi64(a, 0x3u, idx, b)
    val e = mmSetEpi64x(0L, 100L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzPermutex2varEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Epi64x(100L)
    val r = mmMaskzPermutex2varEpi64(0u, a, idx, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzPermutex2varEpi64(0x3u, a, idx, b)
    val e = mmSetEpi64x(0L, 100L)
    assertEqM128i(r1, e)
}

public fun testMmMask2Permutex2varEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Epi64x(100L)
    val r = mmMask2Permutex2varEpi64(a, idx, 0u, b)
    assertEqM128i(r, idx)
    val r1 = mmMask2Permutex2varEpi64(a, idx, 0x3u, b)
    val e = mmSetEpi64x(0L, 100L)
    assertEqM128i(r1, e)
}

public fun testMm512Permutex2varPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Pd(100.0)
    val r = mm512Permutex2varPd(a, idx, b)
    val e = mm512SetPd(6.0, 100.0, 5.0, 100.0, 4.0, 100.0, 3.0, 100.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskPermutex2varPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Pd(100.0)
    val r = mm512MaskPermutex2varPd(a, 0u, idx, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskPermutex2varPd(a, 0xFFu, idx, b)
    val e = mm512SetPd(6.0, 100.0, 5.0, 100.0, 4.0, 100.0, 3.0, 100.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzPermutex2varPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Pd(100.0)
    val r = mm512MaskzPermutex2varPd(0u, a, idx, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzPermutex2varPd(0xFu, a, idx, b)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 4.0, 100.0, 3.0, 100.0)
    assertEqM512d(r1, e)
}

public fun testMm512Mask2Permutex2varPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val idx = mm512SetEpi64(1L, 1L shl 3, 2L, 1L shl 3, 3L, 1L shl 3, 4L, 1L shl 3)
    val b = mm512Set1Pd(100.0)
    val r = mm512Mask2Permutex2varPd(a, idx, 0u, b)
    assertEqM512d(r, mm512Castsi512Pd(idx))
    val r1 = mm512Mask2Permutex2varPd(a, idx, 0xFFu, b)
    val e = mm512SetPd(6.0, 100.0, 5.0, 100.0, 4.0, 100.0, 3.0, 100.0)
    assertEqM512d(r1, e)
}

public fun testMm256Permutex2varPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Pd(100.0)
    val r = mm256Permutex2varPd(a, idx, b)
    val e = mm256SetPd(2.0, 100.0, 1.0, 100.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskPermutex2varPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Pd(100.0)
    val r = mm256MaskPermutex2varPd(a, 0u, idx, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskPermutex2varPd(a, 0xFu, idx, b)
    val e = mm256SetPd(2.0, 100.0, 1.0, 100.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzPermutex2varPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Pd(100.0)
    val r = mm256MaskzPermutex2varPd(0u, a, idx, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzPermutex2varPd(0xFu, a, idx, b)
    val e = mm256SetPd(2.0, 100.0, 1.0, 100.0)
    assertEqM256d(r1, e)
}

public fun testMm256Mask2Permutex2varPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val idx = mm256SetEpi64x(1L, 1L shl 2, 2L, 1L shl 2)
    val b = mm256Set1Pd(100.0)
    val r = mm256Mask2Permutex2varPd(a, idx, 0u, b)
    assertEqM256d(r, mm256Castsi256Pd(idx))
    val r1 = mm256Mask2Permutex2varPd(a, idx, 0xFu, b)
    val e = mm256SetPd(2.0, 100.0, 1.0, 100.0)
    assertEqM256d(r1, e)
}

public fun testMmPermutex2varPd() {
    val a = mmSetPd(0.0, 1.0)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Pd(100.0)
    val r = mmPermutex2varPd(a, idx, b)
    val e = mmSetPd(0.0, 100.0)
    assertEqM128d(r, e)
}

public fun testMmMaskPermutex2varPd() {
    val a = mmSetPd(0.0, 1.0)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Pd(100.0)
    val r = mmMaskPermutex2varPd(a, 0u, idx, b)
    assertEqM128d(r, a)
    val r1 = mmMaskPermutex2varPd(a, 0x3u, idx, b)
    val e = mmSetPd(0.0, 100.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzPermutex2varPd() {
    val a = mmSetPd(0.0, 1.0)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Pd(100.0)
    val r = mmMaskzPermutex2varPd(0u, a, idx, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzPermutex2varPd(0x3u, a, idx, b)
    val e = mmSetPd(0.0, 100.0)
    assertEqM128d(r1, e)
}

public fun testMmMask2Permutex2varPd() {
    val a = mmSetPd(0.0, 1.0)
    val idx = mmSetEpi64x(1L, 1L shl 1)
    val b = mmSet1Pd(100.0)
    val r = mmMask2Permutex2varPd(a, idx, 0u, b)
    assertEqM128d(r, mmCastsi128Pd(idx))
    val r1 = mmMask2Permutex2varPd(a, idx, 0x3u, b)
    val e = mmSetPd(0.0, 100.0)
    assertEqM128d(r1, e)
}

public fun testMm256MaskShufflePd() {
    val a = mm256SetPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MaskShufflePd(a, 0u, a, b, 0xFF)
    assertEqM256d(r, a)
    val r1 = mm256MaskShufflePd(a, 0xFu, a, b, 0xFF)
    val e = mm256SetPd(2.0, 1.0, 6.0, 5.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzShufflePd() {
    val a = mm256SetPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MaskzShufflePd(0u, a, b, 0xFF)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzShufflePd(0xFu, a, b, 0xFF)
    val e = mm256SetPd(2.0, 1.0, 6.0, 5.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskShufflePd() {
    val a = mmSetPd(1.0, 4.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskShufflePd(a, 0u, a, b, 0xFF)
    assertEqM128d(r, a)
    val r1 = mmMaskShufflePd(a, 0x3u, a, b, 0xFF)
    val e = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzShufflePd() {
    val a = mmSetPd(1.0, 4.0)
    val b = mmSetPd(2.0, 3.0)
    val r = mmMaskzShufflePd(0u, a, b, 0xFF)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzShufflePd(0x3u, a, b, 0xFF)
    val e = mmSetPd(2.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512ShuffleI64x2() {
    val a = mm512SetrEpi64(1L, 4L, 5L, 8L, 9L, 12L, 13L, 16L)
    val b = mm512SetrEpi64(2L, 3L, 6L, 7L, 10L, 11L, 14L, 15L)
    val r = mm512ShuffleI64x2(a, b, 0x0)
    val e = mm512SetrEpi64(1L, 4L, 1L, 4L, 2L, 3L, 2L, 3L)
    assertEqM512i(r, e)
}

public fun testMm512MaskShuffleI64x2() {
    val a = mm512SetrEpi64(1L, 4L, 5L, 8L, 9L, 12L, 13L, 16L)
    val b = mm512SetrEpi64(2L, 3L, 6L, 7L, 10L, 11L, 14L, 15L)
    val r = mm512MaskShuffleI64x2(a, 0u, a, b, 0x0)
    assertEqM512i(r, a)
    val r1 = mm512MaskShuffleI64x2(a, 0xFFu, a, b, 0x0)
    val e = mm512SetrEpi64(1L, 4L, 1L, 4L, 2L, 3L, 2L, 3L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzShuffleI64x2() {
    val a = mm512SetrEpi64(1L, 4L, 5L, 8L, 9L, 12L, 13L, 16L)
    val b = mm512SetrEpi64(2L, 3L, 6L, 7L, 10L, 11L, 14L, 15L)
    val r = mm512MaskzShuffleI64x2(0u, a, b, 0x0)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzShuffleI64x2(0xFu, a, b, 0x0)
    val e = mm512SetrEpi64(1L, 4L, 1L, 4L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256ShuffleI64x2() {
    val a = mm256SetEpi64x(1L, 4L, 5L, 8L)
    val b = mm256SetEpi64x(2L, 3L, 6L, 7L)
    val r = mm256ShuffleI64x2(a, b, 0x0)
    val e = mm256SetEpi64x(6L, 7L, 5L, 8L)
    assertEqM256i(r, e)
}

public fun testMm256MaskShuffleI64x2() {
    val a = mm256SetEpi64x(1L, 4L, 5L, 8L)
    val b = mm256SetEpi64x(2L, 3L, 6L, 7L)
    val r = mm256MaskShuffleI64x2(a, 0u, a, b, 0x0)
    assertEqM256i(r, a)
    val r1 = mm256MaskShuffleI64x2(a, 0xFu, a, b, 0x0)
    val e = mm256SetEpi64x(6L, 7L, 5L, 8L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzShuffleI64x2() {
    val a = mm256SetEpi64x(1L, 4L, 5L, 8L)
    val b = mm256SetEpi64x(2L, 3L, 6L, 7L)
    val r = mm256MaskzShuffleI64x2(0u, a, b, 0x0)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzShuffleI64x2(0xFu, a, b, 0x0)
    val e = mm256SetEpi64x(6L, 7L, 5L, 8L)
    assertEqM256i(r1, e)
}

public fun testMm512ShuffleF64x2() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 9.0, 12.0, 13.0, 16.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 10.0, 11.0, 14.0, 15.0)
    val r = mm512ShuffleF64x2(a, b, 0x0)
    val e = mm512SetrPd(1.0, 4.0, 1.0, 4.0, 2.0, 3.0, 2.0, 3.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskShuffleF64x2() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 9.0, 12.0, 13.0, 16.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 10.0, 11.0, 14.0, 15.0)
    val r = mm512MaskShuffleF64x2(a, 0u, a, b, 0x0)
    assertEqM512d(r, a)
    val r1 = mm512MaskShuffleF64x2(a, 0xFFu, a, b, 0x0)
    val e = mm512SetrPd(1.0, 4.0, 1.0, 4.0, 2.0, 3.0, 2.0, 3.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzShuffleF64x2() {
    val a = mm512SetrPd(1.0, 4.0, 5.0, 8.0, 9.0, 12.0, 13.0, 16.0)
    val b = mm512SetrPd(2.0, 3.0, 6.0, 7.0, 10.0, 11.0, 14.0, 15.0)
    val r = mm512MaskzShuffleF64x2(0u, a, b, 0x0)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzShuffleF64x2(0xFu, a, b, 0x0)
    val e = mm512SetrPd(1.0, 4.0, 1.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256ShuffleF64x2() {
    val a = mm256SetPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256ShuffleF64x2(a, b, 0x0)
    val e = mm256SetPd(6.0, 7.0, 5.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskShuffleF64x2() {
    val a = mm256SetPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MaskShuffleF64x2(a, 0u, a, b, 0x0)
    assertEqM256d(r, a)
    val r1 = mm256MaskShuffleF64x2(a, 0xFu, a, b, 0x0)
    val e = mm256SetPd(6.0, 7.0, 5.0, 8.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzShuffleF64x2() {
    val a = mm256SetPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MaskzShuffleF64x2(0u, a, b, 0x0)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzShuffleF64x2(0xFu, a, b, 0x0)
    val e = mm256SetPd(6.0, 7.0, 5.0, 8.0)
    assertEqM256d(r1, e)
}

public fun testMm512MovedupPd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MovedupPd(a)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskMovedupPd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskMovedupPd(a, 0u, a)
    assertEqM512d(r, a)
    val r1 = mm512MaskMovedupPd(a, 0xFFu, a)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 5.0, 5.0, 7.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzMovedupPd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzMovedupPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzMovedupPd(0xFu, a)
    val e = mm512SetrPd(1.0, 1.0, 3.0, 3.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskMovedupPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskMovedupPd(a, 0u, a)
    assertEqM256d(r, a)
    val r1 = mm256MaskMovedupPd(a, 0xFu, a)
    val e = mm256SetPd(2.0, 2.0, 4.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzMovedupPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MaskzMovedupPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzMovedupPd(0xFu, a)
    val e = mm256SetPd(2.0, 2.0, 4.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskMovedupPd() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskMovedupPd(a, 0u, a)
    assertEqM128d(r, a)
    val r1 = mmMaskMovedupPd(a, 0x3u, a)
    val e = mmSetPd(2.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzMovedupPd() {
    val a = mmSetPd(1.0, 2.0)
    val r = mmMaskzMovedupPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzMovedupPd(0x3u, a)
    val e = mmSetPd(2.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMm512Inserti64x4() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    val r = mm512Inserti64x4(a, b, 1)
    val e = mm512SetrEpi64(1L, 2L, 3L, 4L, 17L, 18L, 19L, 20L)
    assertEqM512i(r, e)
}

public fun testMm512MaskInserti64x4() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    val r = mm512MaskInserti64x4(a, 0u, a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskInserti64x4(a, 0xFFu, a, b, 1)
    val e = mm512SetrEpi64(1L, 2L, 3L, 4L, 17L, 18L, 19L, 20L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzInserti64x4() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    val r = mm512MaskzInserti64x4(0u, a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzInserti64x4(0xFu, a, b, 1)
    val e = mm512SetrEpi64(1L, 2L, 3L, 4L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm512Insertf64x4() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512Insertf64x4(a, b, 1)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 17.0, 18.0, 19.0, 20.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskInsertf64x4() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512MaskInsertf64x4(a, 0u, a, b, 1)
    assertEqM512d(r, a)
    val r1 = mm512MaskInsertf64x4(a, 0xFFu, a, b, 1)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 17.0, 18.0, 19.0, 20.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzInsertf64x4() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512MaskzInsertf64x4(0u, a, b, 1)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzInsertf64x4(0xFu, a, b, 1)
    val e = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r1, e)
}

public fun testMm512Castpd128Pd512() {
    val a = mmSetrPd(17.0, 18.0)
    val r = mm512Castpd128Pd512(a)
    assertEqM128d(mm512Castpd512Pd128(r), a)
}

public fun testMm512Castpd256Pd512() {
    val a = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512Castpd256Pd512(a)
    assertEqM256d(mm512Castpd512Pd256(r), a)
}

public fun testMm512Zextpd128Pd512() {
    val a = mmSetrPd(17.0, 18.0)
    val r = mm512Zextpd128Pd512(a)
    val e = mm512SetrPd(17.0, 18.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512Zextpd256Pd512() {
    val a = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512Zextpd256Pd512(a)
    val e = mm512SetrPd(17.0, 18.0, 19.0, 20.0, 0.0, 0.0, 0.0, 0.0)
    assertEqM512d(r, e)
}

public fun testMm512Castpd512Pd128() {
    val a = mm512SetrPd(17.0, 18.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0)
    val r = mm512Castpd512Pd128(a)
    val e = mmSetrPd(17.0, 18.0)
    assertEqM128d(r, e)
}

public fun testMm512Castpd512Pd256() {
    val a = mm512SetrPd(17.0, 18.0, 19.0, 20.0, -1.0, -1.0, -1.0, -1.0)
    val r = mm512Castpd512Pd256(a)
    val e = mm256SetrPd(17.0, 18.0, 19.0, 20.0)
    assertEqM256d(r, e)
}

public fun testMm512CastpdPs() {
    val a = mm512Set1Pd(1.0)
    val r = mm512CastpdPs(a)
    val e = mm512SetPs(1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f, 1.875f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512CastpdSi512() {
    val a = mm512Set1Pd(1.0)
    val r = mm512CastpdSi512(a)
    val e = mm512SetEpi32(1072693248, 0, 1072693248, 0, 1072693248, 0, 1072693248, 0, 1072693248, 0, 1072693248, 0, 1072693248, 0, 1072693248, 0)
    assertEqM512i(r, e)
}

public fun testMm512Castsi128Si512() {
    val a = mmSetrEpi64x(17, 18)
    val r = mm512Castsi128Si512(a)
    assertEqM128i(mm512Castsi512Si128(r), a)
}

public fun testMm512Castsi256Si512() {
    val a = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    val r = mm512Castsi256Si512(a)
    assertEqM256i(mm512Castsi512Si256(r), a)
}

public fun testMm512Zextsi128Si512() {
    val a = mmSetrEpi64x(17, 18)
    val r = mm512Zextsi128Si512(a)
    val e = mm512SetrEpi64(17L, 18L, 0L, 0L, 0L, 0L, 0L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512Zextsi256Si512() {
    val a = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    val r = mm512Zextsi256Si512(a)
    val e = mm512SetrEpi64(17L, 18L, 19L, 20L, 0L, 0L, 0L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512Castsi512Si128() {
    val a = mm512SetrEpi64(17L, 18L, -1L, -1L, -1L, -1L, -1L, -1L)
    val r = mm512Castsi512Si128(a)
    val e = mmSetrEpi64x(17, 18)
    assertEqM128i(r, e)
}

public fun testMm512Castsi512Si256() {
    val a = mm512SetrEpi64(17L, 18L, 19L, 20L, -1L, -1L, -1L, -1L)
    val r = mm512Castsi512Si256(a)
    val e = mm256SetrEpi64x(17L, 18L, 19L, 20L)
    assertEqM256i(r, e)
}

public fun testMm512Castsi512Ps() {
    val a = mm512Set1Epi64(1L shl 62)
    val r = mm512Castsi512Ps(a)
    val e = mm512SetPs(2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f, 2.0f, 0.0f)
    assertEqM512(r, e)
}

public fun testMm512Castsi512Pd() {
    val a = mm512Set1Epi64(1L shl 62)
    val r = mm512Castsi512Pd(a)
    val e = mm512SetPd(2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0)
    assertEqM512d(r, e)
}

public fun testMm512BroadcastqEpi64() {
    val a = mmSetrEpi64x(17, 18)
    val r = mm512BroadcastqEpi64(a)
    val e = mm512Set1Epi64(17L)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastqEpi64() {
    val src = mm512Set1Epi64(18L)
    val a = mmSetrEpi64x(17, 18)
    val r = mm512MaskBroadcastqEpi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastqEpi64(src, 0xFFu, a)
    val e = mm512Set1Epi64(17L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastqEpi64() {
    val a = mmSetrEpi64x(17, 18)
    val r = mm512MaskzBroadcastqEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastqEpi64(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 17L, 17L, 17L, 17L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskBroadcastqEpi64() {
    val src = mm256Set1Epi64x(18L)
    val a = mmSetEpi64x(17L, 18L)
    val r = mm256MaskBroadcastqEpi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskBroadcastqEpi64(src, 0xFu, a)
    val e = mm256Set1Epi64x(18L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzBroadcastqEpi64() {
    val a = mmSetEpi64x(17L, 18L)
    val r = mm256MaskzBroadcastqEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzBroadcastqEpi64(0xFu, a)
    val e = mm256Set1Epi64x(18L)
    assertEqM256i(r1, e)
}

public fun testMmMaskBroadcastqEpi64() {
    val src = mmSet1Epi64x(18L)
    val a = mmSetEpi64x(17L, 18L)
    val r = mmMaskBroadcastqEpi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskBroadcastqEpi64(src, 0x3u, a)
    val e = mmSet1Epi64x(18L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzBroadcastqEpi64() {
    val a = mmSetEpi64x(17L, 18L)
    val r = mmMaskzBroadcastqEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzBroadcastqEpi64(0x3u, a)
    val e = mmSet1Epi64x(18L)
    assertEqM128i(r1, e)
}

public fun testMm512BroadcastsdPd() {
    val a = mmSetPd(17.0, 18.0)
    val r = mm512BroadcastsdPd(a)
    val e = mm512Set1Pd(18.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskBroadcastsdPd() {
    val src = mm512Set1Pd(18.0)
    val a = mmSetPd(17.0, 18.0)
    val r = mm512MaskBroadcastsdPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskBroadcastsdPd(src, 0xFFu, a)
    val e = mm512Set1Pd(18.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzBroadcastsdPd() {
    val a = mmSetPd(17.0, 18.0)
    val r = mm512MaskzBroadcastsdPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzBroadcastsdPd(0xFu, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 18.0, 18.0, 18.0, 18.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskBroadcastsdPd() {
    val src = mm256Set1Pd(18.0)
    val a = mmSetPd(17.0, 18.0)
    val r = mm256MaskBroadcastsdPd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskBroadcastsdPd(src, 0xFu, a)
    val e = mm256Set1Pd(18.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzBroadcastsdPd() {
    val a = mmSetPd(17.0, 18.0)
    val r = mm256MaskzBroadcastsdPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzBroadcastsdPd(0xFu, a)
    val e = mm256Set1Pd(18.0)
    assertEqM256d(r1, e)
}

public fun testMm512BroadcastI64x4() {
    val a = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm512BroadcastI64x4(a)
    val e = mm512SetEpi64(17L, 18L, 19L, 20L, 17L, 18L, 19L, 20L)
    assertEqM512i(r, e)
}

public fun testMm512MaskBroadcastI64x4() {
    val src = mm512Set1Epi64(18L)
    val a = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm512MaskBroadcastI64x4(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskBroadcastI64x4(src, 0xFFu, a)
    val e = mm512SetEpi64(17L, 18L, 19L, 20L, 17L, 18L, 19L, 20L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzBroadcastI64x4() {
    val a = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm512MaskzBroadcastI64x4(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzBroadcastI64x4(0xFu, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 17L, 18L, 19L, 20L)
    assertEqM512i(r1, e)
}

public fun testMm512BroadcastF64x4() {
    val a = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512BroadcastF64x4(a)
    val e = mm512SetPd(17.0, 18.0, 19.0, 20.0, 17.0, 18.0, 19.0, 20.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskBroadcastF64x4() {
    val src = mm512Set1Pd(18.0)
    val a = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512MaskBroadcastF64x4(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskBroadcastF64x4(src, 0xFFu, a)
    val e = mm512SetPd(17.0, 18.0, 19.0, 20.0, 17.0, 18.0, 19.0, 20.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzBroadcastF64x4() {
    val a = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm512MaskzBroadcastF64x4(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzBroadcastF64x4(0xFu, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 17.0, 18.0, 19.0, 20.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskBlendEpi64() {
    val a = mm512Set1Epi64(1L)
    val b = mm512Set1Epi64(2L)
    val r = mm512MaskBlendEpi64(0xF0u, a, b)
    val e = mm512SetEpi64(2L, 2L, 2L, 2L, 1L, 1L, 1L, 1L)
    assertEqM512i(r, e)
}

public fun testMm256MaskBlendEpi64() {
    val a = mm256Set1Epi64x(1L)
    val b = mm256Set1Epi64x(2L)
    val r = mm256MaskBlendEpi64(0xFu, a, b)
    val e = mm256Set1Epi64x(2L)
    assertEqM256i(r, e)
}

public fun testMmMaskBlendEpi64() {
    val a = mmSet1Epi64x(1L)
    val b = mmSet1Epi64x(2L)
    val r = mmMaskBlendEpi64(0x3u, a, b)
    val e = mmSet1Epi64x(2L)
    assertEqM128i(r, e)
}

public fun testMm512MaskBlendPd() {
    val a = mm512Set1Pd(1.0)
    val b = mm512Set1Pd(2.0)
    val r = mm512MaskBlendPd(0xF0u, a, b)
    val e = mm512SetPd(2.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0)
    assertEqM512d(r, e)
}

public fun testMm256MaskBlendPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(2.0)
    val r = mm256MaskBlendPd(0xFu, a, b)
    val e = mm256Set1Pd(2.0)
    assertEqM256d(r, e)
}

public fun testMmMaskBlendPd() {
    val a = mmSet1Pd(1.0)
    val b = mmSet1Pd(2.0)
    val r = mmMaskBlendPd(0x3u, a, b)
    val e = mmSet1Pd(2.0)
    assertEqM128d(r, e)
}

public fun testMm512UnpackhiEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512UnpackhiEpi64(a, b)
    val e = mm512SetEpi64(17L, 1L, 19L, 3L, 21L, 5L, 23L, 7L)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackhiEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512MaskUnpackhiEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackhiEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(17L, 1L, 19L, 3L, 21L, 5L, 23L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackhiEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512MaskzUnpackhiEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackhiEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 21L, 5L, 23L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackhiEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm256MaskUnpackhiEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackhiEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(17L, 1L, 19L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackhiEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm256MaskzUnpackhiEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackhiEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(17L, 1L, 19L, 3L)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackhiEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(17L, 18L)
    val r = mmMaskUnpackhiEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackhiEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(17L, 1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackhiEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(17L, 18L)
    val r = mmMaskzUnpackhiEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackhiEpi64(0x3u, a, b)
    val e = mmSetEpi64x(17L, 1L)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackhiPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512UnpackhiPd(a, b)
    val e = mm512SetPd(17.0, 1.0, 19.0, 3.0, 21.0, 5.0, 23.0, 7.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskUnpackhiPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512MaskUnpackhiPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskUnpackhiPd(a, 0xFFu, a, b)
    val e = mm512SetPd(17.0, 1.0, 19.0, 3.0, 21.0, 5.0, 23.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzUnpackhiPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512MaskzUnpackhiPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzUnpackhiPd(0xFu, a, b)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 21.0, 5.0, 23.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskUnpackhiPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm256MaskUnpackhiPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskUnpackhiPd(a, 0xFu, a, b)
    val e = mm256SetPd(17.0, 1.0, 19.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzUnpackhiPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm256MaskzUnpackhiPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzUnpackhiPd(0xFu, a, b)
    val e = mm256SetPd(17.0, 1.0, 19.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskUnpackhiPd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(17.0, 18.0)
    val r = mmMaskUnpackhiPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskUnpackhiPd(a, 0x3u, a, b)
    val e = mmSetPd(17.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzUnpackhiPd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(17.0, 18.0)
    val r = mmMaskzUnpackhiPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzUnpackhiPd(0x3u, a, b)
    val e = mmSetPd(17.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512UnpackloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512UnpackloEpi64(a, b)
    val e = mm512SetEpi64(18L, 2L, 20L, 4L, 22L, 6L, 24L, 8L)
    assertEqM512i(r, e)
}

public fun testMm512MaskUnpackloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512MaskUnpackloEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskUnpackloEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(18L, 2L, 20L, 4L, 22L, 6L, 24L, 8L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzUnpackloEpi64() {
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L)
    val r = mm512MaskzUnpackloEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzUnpackloEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 22L, 6L, 24L, 8L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskUnpackloEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm256MaskUnpackloEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskUnpackloEpi64(a, 0xFu, a, b)
    val e = mm256SetEpi64x(18L, 2L, 20L, 4L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzUnpackloEpi64() {
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(17L, 18L, 19L, 20L)
    val r = mm256MaskzUnpackloEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzUnpackloEpi64(0xFu, a, b)
    val e = mm256SetEpi64x(18L, 2L, 20L, 4L)
    assertEqM256i(r1, e)
}

public fun testMmMaskUnpackloEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(17L, 18L)
    val r = mmMaskUnpackloEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskUnpackloEpi64(a, 0x3u, a, b)
    val e = mmSetEpi64x(18L, 2L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzUnpackloEpi64() {
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(17L, 18L)
    val r = mmMaskzUnpackloEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzUnpackloEpi64(0x3u, a, b)
    val e = mmSetEpi64x(18L, 2L)
    assertEqM128i(r1, e)
}

public fun testMm512UnpackloPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512UnpackloPd(a, b)
    val e = mm512SetPd(18.0, 2.0, 20.0, 4.0, 22.0, 6.0, 24.0, 8.0)
    assertEqM512d(r, e)
}

public fun testMm512MaskUnpackloPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512MaskUnpackloPd(a, 0u, a, b)
    assertEqM512d(r, a)
    val r1 = mm512MaskUnpackloPd(a, 0xFFu, a, b)
    val e = mm512SetPd(18.0, 2.0, 20.0, 4.0, 22.0, 6.0, 24.0, 8.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzUnpackloPd() {
    val a = mm512SetPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val b = mm512SetPd(17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)
    val r = mm512MaskzUnpackloPd(0u, a, b)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzUnpackloPd(0xFu, a, b)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 22.0, 6.0, 24.0, 8.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskUnpackloPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm256MaskUnpackloPd(a, 0u, a, b)
    assertEqM256d(r, a)
    val r1 = mm256MaskUnpackloPd(a, 0xFu, a, b)
    val e = mm256SetPd(18.0, 2.0, 20.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzUnpackloPd() {
    val a = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetPd(17.0, 18.0, 19.0, 20.0)
    val r = mm256MaskzUnpackloPd(0u, a, b)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzUnpackloPd(0xFu, a, b)
    val e = mm256SetPd(18.0, 2.0, 20.0, 4.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskUnpackloPd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(17.0, 18.0)
    val r = mmMaskUnpackloPd(a, 0u, a, b)
    assertEqM128d(r, a)
    val r1 = mmMaskUnpackloPd(a, 0x3u, a, b)
    val e = mmSetPd(18.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzUnpackloPd() {
    val a = mmSetPd(1.0, 2.0)
    val b = mmSetPd(17.0, 18.0)
    val r = mmMaskzUnpackloPd(0u, a, b)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzUnpackloPd(0x3u, a, b)
    val e = mmSetPd(18.0, 2.0)
    assertEqM128d(r1, e)
}

public fun testMm512AlignrEpi64() {
    val a = mm512SetEpi64(8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L)
    val b = mm512SetEpi64(16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L)
    val r = mm512AlignrEpi64(a, b, 0)
    assertEqM512i(r, b)
    val r1 = mm512AlignrEpi64(a, b, 8)
    assertEqM512i(r1, b)
    val r2 = mm512AlignrEpi64(a, b, 1)
    val e = mm512SetEpi64(1L, 16L, 15L, 14L, 13L, 12L, 11L, 10L)
    assertEqM512i(r2, e)
}

public fun testMm512MaskAlignrEpi64() {
    val a = mm512SetEpi64(8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L)
    val b = mm512SetEpi64(16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L)
    val r = mm512MaskAlignrEpi64(a, 0u, a, b, 1)
    assertEqM512i(r, a)
    val r1 = mm512MaskAlignrEpi64(a, 0xFFu, a, b, 1)
    val e = mm512SetEpi64(1L, 16L, 15L, 14L, 13L, 12L, 11L, 10L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAlignrEpi64() {
    val a = mm512SetEpi64(8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L)
    val b = mm512SetEpi64(16L, 15L, 14L, 13L, 12L, 11L, 10L, 9L)
    val r = mm512MaskzAlignrEpi64(0u, a, b, 1)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAlignrEpi64(0xFu, a, b, 1)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 13L, 12L, 11L, 10L)
    assertEqM512i(r1, e)
}

public fun testMm256AlignrEpi64() {
    val a = mm256SetEpi64x(4L, 3L, 2L, 1L)
    val b = mm256SetEpi64x(8L, 7L, 6L, 5L)
    val r = mm256AlignrEpi64(a, b, 0)
    val e = mm256SetEpi64x(8L, 7L, 6L, 5L)
    assertEqM256i(r, e)
    val r1 = mm256AlignrEpi64(a, b, 1)
    val e1 = mm256SetEpi64x(1L, 8L, 7L, 6L)
    assertEqM256i(r1, e1)
    val r2 = mm256AlignrEpi64(a, b, 6)
    val e2 = mm256SetEpi64x(2L, 1L, 8L, 7L)
    assertEqM256i(r2, e2)
}

public fun testMm256MaskAlignrEpi64() {
    val a = mm256SetEpi64x(4L, 3L, 2L, 1L)
    val b = mm256SetEpi64x(8L, 7L, 6L, 5L)
    val r = mm256MaskAlignrEpi64(a, 0u, a, b, 1)
    assertEqM256i(r, a)
    val r1 = mm256MaskAlignrEpi64(a, 0xFu, a, b, 0)
    val e = mm256SetEpi64x(8L, 7L, 6L, 5L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAlignrEpi64() {
    val a = mm256SetEpi64x(4L, 3L, 2L, 1L)
    val b = mm256SetEpi64x(8L, 7L, 6L, 5L)
    val r = mm256MaskzAlignrEpi64(0u, a, b, 1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAlignrEpi64(0xFu, a, b, 0)
    val e = mm256SetEpi64x(8L, 7L, 6L, 5L)
    assertEqM256i(r1, e)
}

public fun testMmAlignrEpi64() {
    val a = mmSetEpi64x(2L, 1L)
    val b = mmSetEpi64x(4L, 3L)
    val r = mmAlignrEpi64(a, b, 0)
    val e = mmSetEpi64x(4L, 3L)
    assertEqM128i(r, e)
}

public fun testMmMaskAlignrEpi64() {
    val a = mmSetEpi64x(2L, 1L)
    val b = mmSetEpi64x(4L, 3L)
    val r = mmMaskAlignrEpi64(a, 0u, a, b, 1)
    assertEqM128i(r, a)
    val r1 = mmMaskAlignrEpi64(a, 0x3u, a, b, 0)
    val e = mmSetEpi64x(4L, 3L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAlignrEpi64() {
    val a = mmSetEpi64x(2L, 1L)
    val b = mmSetEpi64x(4L, 3L)
    val r = mmMaskzAlignrEpi64(0u, a, b, 1)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAlignrEpi64(0x3u, a, b, 0)
    val e = mmSetEpi64x(4L, 3L)
    assertEqM128i(r1, e)
}

public fun testMm512AndEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512AndEpi64(a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r, e)
}

public fun testMm512MaskAndEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskAndEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAndEpi64(a, 0x7Fu, a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAndEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskzAndEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAndEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAndEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 0)
    val r = mm256MaskAndEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAndEpi64(a, 0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAndEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 0)
    val r = mm256MaskzAndEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAndEpi64(0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0)
    assertEqM256i(r1, e)
}

public fun testMmMaskAndEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 0)
    val r = mmMaskAndEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAndEpi64(a, 0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAndEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 0)
    val r = mmMaskzAndEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAndEpi64(0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0)
    assertEqM128i(r1, e)
}

public fun testMm512AndSi512() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512AndEpi64(a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r, e)
}

public fun testMm512OrEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512OrEpi64(a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r, e)
}

public fun testMm512MaskOrEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskOrEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskOrEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzOrEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskzOrEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzOrEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r1, e)
}

public fun testMm256OrEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256OrEpi64(a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r, e)
}

public fun testMm256MaskOrEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256MaskOrEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskOrEpi64(a, 0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzOrEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256MaskzOrEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzOrEpi64(0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r1, e)
}

public fun testMmOrEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmOrEpi64(a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r, e)
}

public fun testMmMaskOrEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmMaskOrEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskOrEpi64(a, 0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzOrEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmMaskzOrEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzOrEpi64(0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r1, e)
}

public fun testMm512OrSi512() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512OrEpi64(a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    assertEqM512i(r, e)
}

public fun testMm512XorEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512XorEpi64(a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512MaskXorEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskXorEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskXorEpi64(a, 0xFFu, a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzXorEpi64() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512MaskzXorEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzXorEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L)
    assertEqM512i(r1, e)
}

public fun testMm256XorEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256XorEpi64(a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r, e)
}

public fun testMm256MaskXorEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256MaskXorEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskXorEpi64(a, 0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzXorEpi64() {
    val a = mm256Set1Epi64x(1L shl 0 or 1L shl 15)
    val b = mm256Set1Epi64x(1L shl 13)
    val r = mm256MaskzXorEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzXorEpi64(0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM256i(r1, e)
}

public fun testMmXorEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmXorEpi64(a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r, e)
}

public fun testMmMaskXorEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmMaskXorEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskXorEpi64(a, 0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r1, e)
}

public fun testMmMaskzXorEpi64() {
    val a = mmSet1Epi64x(1L shl 0 or 1L shl 15)
    val b = mmSet1Epi64x(1L shl 13)
    val r = mmMaskzXorEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzXorEpi64(0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 0 or 1L shl 13 or 1L shl 15)
    assertEqM128i(r1, e)
}

public fun testMm512XorSi512() {
    val a = mm512SetEpi64(1L shl 0 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val b = mm512SetEpi64(1L shl 13, 0L, 0L, 0L, 0L, 0L, 0L, 1L shl 1 or 1L shl 2 or 1L shl 3)
    val r = mm512XorEpi64(a, b)
    val e = mm512SetEpi64(1L shl 0 or 1L shl 13 or 1L shl 15, 0L, 0L, 0L, 0L, 0L, 0L, 0L)
    assertEqM512i(r, e)
}

public fun testMm512AndnotEpi64() {
    val a = mm512Set1Epi64(0L)
    val b = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    val r = mm512AndnotEpi64(a, b)
    val e = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    assertEqM512i(r, e)
}

public fun testMm512MaskAndnotEpi64() {
    val a = mm512Set1Epi64(1L shl 1 or 1L shl 2)
    val b = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    val r = mm512MaskAndnotEpi64(a, 0u, a, b)
    assertEqM512i(r, a)
    val r1 = mm512MaskAndnotEpi64(a, 0xFFu, a, b)
    val e = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzAndnotEpi64() {
    val a = mm512Set1Epi64(1L shl 1 or 1L shl 2)
    val b = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    val r = mm512MaskzAndnotEpi64(0u, a, b)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzAndnotEpi64(0xFu, a, b)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L shl 3 or 1L shl 4, 1L shl 3 or 1L shl 4, 1L shl 3 or 1L shl 4, 1L shl 3 or 1L shl 4)
    assertEqM512i(r1, e)
}

public fun testMm256MaskAndnotEpi64() {
    val a = mm256Set1Epi64x(1L shl 1 or 1L shl 2)
    val b = mm256Set1Epi64x(1L shl 3 or 1L shl 4)
    val r = mm256MaskAndnotEpi64(a, 0u, a, b)
    assertEqM256i(r, a)
    val r1 = mm256MaskAndnotEpi64(a, 0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 3 or 1L shl 4)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzAndnotEpi64() {
    val a = mm256Set1Epi64x(1L shl 1 or 1L shl 2)
    val b = mm256Set1Epi64x(1L shl 3 or 1L shl 4)
    val r = mm256MaskzAndnotEpi64(0u, a, b)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzAndnotEpi64(0xFu, a, b)
    val e = mm256Set1Epi64x(1L shl 3 or 1L shl 4)
    assertEqM256i(r1, e)
}

public fun testMmMaskAndnotEpi64() {
    val a = mmSet1Epi64x(1L shl 1 or 1L shl 2)
    val b = mmSet1Epi64x(1L shl 3 or 1L shl 4)
    val r = mmMaskAndnotEpi64(a, 0u, a, b)
    assertEqM128i(r, a)
    val r1 = mmMaskAndnotEpi64(a, 0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 3 or 1L shl 4)
    assertEqM128i(r1, e)
}

public fun testMmMaskzAndnotEpi64() {
    val a = mmSet1Epi64x(1L shl 1 or 1L shl 2)
    val b = mmSet1Epi64x(1L shl 3 or 1L shl 4)
    val r = mmMaskzAndnotEpi64(0u, a, b)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzAndnotEpi64(0x3u, a, b)
    val e = mmSet1Epi64x(1L shl 3 or 1L shl 4)
    assertEqM128i(r1, e)
}

public fun testMm512AndnotSi512() {
    val a = mm512Set1Epi64(0L)
    val b = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    val r = mm512AndnotSi512(a, b)
    val e = mm512Set1Epi64(1L shl 3 or 1L shl 4)
    assertEqM512i(r, e)
}

public fun testMm512ReduceAddEpi64() {
    val a = mm512Set1Epi64(1L)
    val e: Long = mm512ReduceAddEpi64(a)
    check(8L == e)
}

public fun testMm512MaskReduceAddEpi64() {
    val a = mm512Set1Epi64(1L)
    val e: Long = mm512MaskReduceAddEpi64(0xF0u, a)
    check(4L == e)
}

public fun testMm512ReduceAddPd() {
    val a = mm512Set1Pd(1.0)
    val e: Double = mm512ReduceAddPd(a)
    check(8.0.toDouble() == e)
}

public fun testMm512MaskReduceAddPd() {
    val a = mm512Set1Pd(1.0)
    val e: Double = mm512MaskReduceAddPd(0xF0u, a)
    check(4.0.toDouble() == e)
}

public fun testMm512ReduceMulEpi64() {
    val a = mm512Set1Epi64(2L)
    val e: Long = mm512ReduceMulEpi64(a)
    check(256L == e)
}

public fun testMm512MaskReduceMulEpi64() {
    val a = mm512Set1Epi64(2L)
    val e: Long = mm512MaskReduceMulEpi64(0xF0u, a)
    check(16L == e)
}

public fun testMm512ReduceMulPd() {
    val a = mm512Set1Pd(2.0)
    val e: Double = mm512ReduceMulPd(a)
    check(256.0.toDouble() == e)
}

public fun testMm512MaskReduceMulPd() {
    val a = mm512Set1Pd(2.0)
    val e: Double = mm512MaskReduceMulPd(0xF0u, a)
    check(16.0.toDouble() == e)
}

public fun testMm512ReduceMaxEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: Long = mm512ReduceMaxEpi64(a)
    check(7L == e)
}

public fun testMm512MaskReduceMaxEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: Long = mm512MaskReduceMaxEpi64(0xF0u, a)
    check(3L == e)
}

public fun testMm512ReduceMaxEpu64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: ULong = mm512ReduceMaxEpu64(a)
    check(7uL == e)
}

public fun testMm512MaskReduceMaxEpu64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: ULong = mm512MaskReduceMaxEpu64(0xF0u, a)
    check(3uL == e)
}

public fun testMm512ReduceMaxPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val e: Double = mm512ReduceMaxPd(a)
    check(7.0.toDouble() == e)
}

public fun testMm512MaskReduceMaxPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val e: Double = mm512MaskReduceMaxPd(0xF0u, a)
    check(3.0.toDouble() == e)
}

public fun testMm512ReduceMinEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: Long = mm512ReduceMinEpi64(a)
    check(0L == e)
}

public fun testMm512MaskReduceMinEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: Long = mm512MaskReduceMinEpi64(0xF0u, a)
    check(0L == e)
}

public fun testMm512ReduceMinEpu64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: ULong = mm512ReduceMinEpu64(a)
    check(0uL == e)
}

public fun testMm512MaskReduceMinEpu64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val e: ULong = mm512MaskReduceMinEpu64(0xF0u, a)
    check(0uL == e)
}

public fun testMm512ReduceMinPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val e: Double = mm512ReduceMinPd(a)
    check(0.0.toDouble() == e)
}

public fun testMm512MaskReduceMinPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val e: Double = mm512MaskReduceMinPd(0xF0u, a)
    check(0.0.toDouble() == e)
}

public fun testMm512ReduceAndEpi64() {
    val a = mm512SetEpi64(1L, 1L, 1L, 1L, 2L, 2L, 2L, 2L)
    val e: Long = mm512ReduceAndEpi64(a)
    check(0L == e)
}

public fun testMm512MaskReduceAndEpi64() {
    val a = mm512SetEpi64(1L, 1L, 1L, 1L, 2L, 2L, 2L, 2L)
    val e: Long = mm512MaskReduceAndEpi64(0xF0u, a)
    check(1L == e)
}

public fun testMm512ReduceOrEpi64() {
    val a = mm512SetEpi64(1L, 1L, 1L, 1L, 2L, 2L, 2L, 2L)
    val e: Long = mm512ReduceOrEpi64(a)
    check(3L == e)
}

public fun testMm512MaskReduceOrEpi64() {
    val a = mm512SetEpi64(1L, 1L, 1L, 1L, 2L, 2L, 2L, 2L)
    val e: Long = mm512MaskReduceOrEpi64(0xF0u, a)
    check(1L == e)
}

public fun testMm512Extractf64x4Pd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512Extractf64x4Pd(a, 1)
    val e = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm512MaskExtractf64x4Pd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val src = mm256Set1Pd(100.0)
    val r = mm512MaskExtractf64x4Pd(src, 0u, a, 1)
    assertEqM256d(r, src)
    val r1 = mm512MaskExtractf64x4Pd(src, 0xFFu, a, 1)
    val e = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    assertEqM256d(r1, e)
}

public fun testMm512MaskzExtractf64x4Pd() {
    val a = mm512SetrPd(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)
    val r = mm512MaskzExtractf64x4Pd(0u, a, 1)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm512MaskzExtractf64x4Pd(0x1u, a, 1)
    val e = mm256SetrPd(5.0, 0.0, 0.0, 0.0)
    assertEqM256d(r1, e)
}

public fun testMm512Extracti64x4Epi64() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512Extracti64x4Epi64(a, 0x1)
    val e = mm256SetrEpi64x(5L, 6L, 7L, 8L)
    assertEqM256i(r, e)
}

public fun testMm512MaskExtracti64x4Epi64() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val src = mm256Set1Epi64x(100L)
    val r = mm512MaskExtracti64x4Epi64(src, 0u, a, 0x1)
    assertEqM256i(r, src)
    val r1 = mm512MaskExtracti64x4Epi64(src, 0xFFu, a, 0x1)
    val e = mm256SetrEpi64x(5L, 6L, 7L, 8L)
    assertEqM256i(r1, e)
}

public fun testMm512MaskzExtracti64x4Epi64() {
    val a = mm512SetrEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val r = mm512MaskzExtracti64x4Epi64(0u, a, 0x1)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm512MaskzExtracti64x4Epi64(0x1u, a, 0x1)
    val e = mm256SetrEpi64x(5L, 0L, 0L, 0L)
    assertEqM256i(r1, e)
}

public fun testMm512MaskCompressEpi64() {
    val src = mm512Set1Epi64(200L)
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskCompressEpi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskCompressEpi64(src, 0x55u, a)
    val e = mm512SetEpi64(200L, 200L, 200L, 200L, 1L, 3L, 5L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzCompressEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskzCompressEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzCompressEpi64(0x55u, a)
    val e = mm512SetEpi64(0L, 0L, 0L, 0L, 1L, 3L, 5L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskCompressEpi64() {
    val src = mm256Set1Epi64x(200L)
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskCompressEpi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskCompressEpi64(src, 0x5u, a)
    val e = mm256SetEpi64x(200L, 200L, 1L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzCompressEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzCompressEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzCompressEpi64(0x5u, a)
    val e = mm256SetEpi64x(0L, 0L, 1L, 3L)
    assertEqM256i(r1, e)
}

public fun testMmMaskCompressEpi64() {
    val src = mmSet1Epi64x(200L)
    val a = mmSetEpi64x(0L, 1L)
    val r = mmMaskCompressEpi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskCompressEpi64(src, 0x1u, a)
    val e = mmSetEpi64x(200L, 1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzCompressEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val r = mmMaskzCompressEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzCompressEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 1L)
    assertEqM128i(r1, e)
}

public fun testMm512MaskCompressPd() {
    val src = mm512Set1Pd(200.0)
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskCompressPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskCompressPd(src, 0x55u, a)
    val e = mm512SetPd(200.0, 200.0, 200.0, 200.0, 1.0, 3.0, 5.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzCompressPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskzCompressPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzCompressPd(0x55u, a)
    val e = mm512SetPd(0.0, 0.0, 0.0, 0.0, 1.0, 3.0, 5.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskCompressPd() {
    val src = mm256Set1Pd(200.0)
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskCompressPd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskCompressPd(src, 0x5u, a)
    val e = mm256SetPd(200.0, 200.0, 1.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzCompressPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskzCompressPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzCompressPd(0x5u, a)
    val e = mm256SetPd(0.0, 0.0, 1.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskCompressPd() {
    val src = mmSet1Pd(200.0)
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskCompressPd(src, 0u, a)
    assertEqM128d(r, src)
    val r1 = mmMaskCompressPd(src, 0x1u, a)
    val e = mmSetPd(200.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzCompressPd() {
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskzCompressPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzCompressPd(0x1u, a)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512MaskExpandEpi64() {
    val src = mm512Set1Epi64(200L)
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskExpandEpi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskExpandEpi64(src, 0x55u, a)
    val e = mm512SetEpi64(200L, 4L, 200L, 5L, 200L, 6L, 200L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzExpandEpi64() {
    val a = mm512SetEpi64(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val r = mm512MaskzExpandEpi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzExpandEpi64(0x55u, a)
    val e = mm512SetEpi64(0L, 4L, 0L, 5L, 0L, 6L, 0L, 7L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskExpandEpi64() {
    val src = mm256Set1Epi64x(200L)
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskExpandEpi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskExpandEpi64(src, 0x5u, a)
    val e = mm256SetEpi64x(200L, 2L, 200L, 3L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzExpandEpi64() {
    val a = mm256SetEpi64x(0L, 1L, 2L, 3L)
    val r = mm256MaskzExpandEpi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzExpandEpi64(0x5u, a)
    val e = mm256SetEpi64x(0L, 2L, 0L, 3L)
    assertEqM256i(r1, e)
}

public fun testMmMaskExpandEpi64() {
    val src = mmSet1Epi64x(200L)
    val a = mmSetEpi64x(0L, 1L)
    val r = mmMaskExpandEpi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskExpandEpi64(src, 0x1u, a)
    val e = mmSetEpi64x(200L, 1L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzExpandEpi64() {
    val a = mmSetEpi64x(0L, 1L)
    val r = mmMaskzExpandEpi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzExpandEpi64(0x1u, a)
    val e = mmSetEpi64x(0L, 1L)
    assertEqM128i(r1, e)
}

public fun testMm512MaskExpandPd() {
    val src = mm512Set1Pd(200.0)
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskExpandPd(src, 0u, a)
    assertEqM512d(r, src)
    val r1 = mm512MaskExpandPd(src, 0x55u, a)
    val e = mm512SetPd(200.0, 4.0, 200.0, 5.0, 200.0, 6.0, 200.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm512MaskzExpandPd() {
    val a = mm512SetPd(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val r = mm512MaskzExpandPd(0u, a)
    assertEqM512d(r, mm512SetzeroPd())
    val r1 = mm512MaskzExpandPd(0x55u, a)
    val e = mm512SetPd(0.0, 4.0, 0.0, 5.0, 0.0, 6.0, 0.0, 7.0)
    assertEqM512d(r1, e)
}

public fun testMm256MaskExpandPd() {
    val src = mm256Set1Pd(200.0)
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskExpandPd(src, 0u, a)
    assertEqM256d(r, src)
    val r1 = mm256MaskExpandPd(src, 0x5u, a)
    val e = mm256SetPd(200.0, 2.0, 200.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMm256MaskzExpandPd() {
    val a = mm256SetPd(0.0, 1.0, 2.0, 3.0)
    val r = mm256MaskzExpandPd(0u, a)
    assertEqM256d(r, mm256SetzeroPd())
    val r1 = mm256MaskzExpandPd(0x5u, a)
    val e = mm256SetPd(0.0, 2.0, 0.0, 3.0)
    assertEqM256d(r1, e)
}

public fun testMmMaskExpandPd() {
    val src = mmSet1Pd(200.0)
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskExpandPd(src, 0u, a)
    assertEqM128d(r, src)
    val r1 = mmMaskExpandPd(src, 0x1u, a)
    val e = mmSetPd(200.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMmMaskzExpandPd() {
    val a = mmSetPd(0.0, 1.0)
    val r = mmMaskzExpandPd(0u, a)
    assertEqM128d(r, mmSetzeroPd())
    val r1 = mmMaskzExpandPd(0x1u, a)
    val e = mmSetPd(0.0, 1.0)
    assertEqM128d(r1, e)
}

public fun testMm512LoaduEpi64() {
    val a = listOf(4, 3, 2, 5, -8, -9, -64, -50)
    val p = a.asPtr()
    val r = mm512LoaduEpi64(blackBox(p))
    val e = mm512SetrEpi64(4L, 3L, 2L, 5L, -8L, -9L, -64L, -50L)
    assertEqM512i(r, e)
}

public fun testMm256LoaduEpi64() {
    val a = listOf(4, 3, 2, 5)
    val p = a.asPtr()
    val r = mm256LoaduEpi64(blackBox(p))
    val e = mm256SetrEpi64x(4L, 3L, 2L, 5L)
    assertEqM256i(r, e)
}

public fun testMmLoaduEpi64() {
    val a = listOf(4, 3)
    val p = a.asPtr()
    val r = mmLoaduEpi64(blackBox(p))
    val e = mmSetrEpi64x(4, 3)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi64StoreuEpi16() {
    val a = mm512Set1Epi64(9L)
    val localStorageL12315C8 = AddressableLocal(mmUndefinedSi128(), ObjectSlotLayout<M128i>())
    val r by localStorageL12315C8
    run {
        mm512MaskCvtepi64StoreuEpi16((MutPtr(localStorageL12315C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(9)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi64StoreuEpi16() {
    val a = mm256Set1Epi64x(9L)
    val localStorageL12326C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12326C8
    run {
        mm256MaskCvtepi64StoreuEpi16((MutPtr(localStorageL12326C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64StoreuEpi16() {
    val a = mmSet1Epi64x(9L)
    val localStorageL12337C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12337C8
    run {
        mmMaskCvtepi64StoreuEpi16((MutPtr(localStorageL12337C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi64StoreuEpi16() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12348C8 = AddressableLocal(mmUndefinedSi128(), ObjectSlotLayout<M128i>())
    val r by localStorageL12348C8
    run {
        mm512MaskCvtsepi64StoreuEpi16((MutPtr(localStorageL12348C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi64StoreuEpi16() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12359C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12359C8
    run {
        mm256MaskCvtsepi64StoreuEpi16((MutPtr(localStorageL12359C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64StoreuEpi16() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12370C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12370C8
    run {
        mmMaskCvtsepi64StoreuEpi16((MutPtr(localStorageL12370C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi64StoreuEpi16() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12381C8 = AddressableLocal(mmUndefinedSi128(), ObjectSlotLayout<M128i>())
    val r by localStorageL12381C8
    run {
        mm512MaskCvtusepi64StoreuEpi16((MutPtr(localStorageL12381C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSet1Epi16(UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi64StoreuEpi16() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12392C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12392C8
    run {
        mm256MaskCvtusepi64StoreuEpi16((MutPtr(localStorageL12392C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64StoreuEpi16() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12412C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12412C8
    run {
        mmMaskCvtusepi64StoreuEpi16((MutPtr(localStorageL12412C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi16(0, 0, 0, 0, 0, 0, UShort.MAX_VALUE.toShort(), UShort.MAX_VALUE.toShort())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi64StoreuEpi8() {
    val a = mm512Set1Epi64(9L)
    val localStorageL12423C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12423C8
    run {
        mm512MaskCvtepi64StoreuEpi8((MutPtr(localStorageL12423C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtepi64StoreuEpi8() {
    val a = mm256Set1Epi64x(9L)
    val localStorageL12434C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12434C8
    run {
        mm256MaskCvtepi64StoreuEpi8((MutPtr(localStorageL12434C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64StoreuEpi8() {
    val a = mmSet1Epi64x(9L)
    val localStorageL12445C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12445C8
    run {
        mmMaskCvtepi64StoreuEpi8((MutPtr(localStorageL12445C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi64StoreuEpi8() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12456C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12456C8
    run {
        mm512MaskCvtsepi64StoreuEpi8((MutPtr(localStorageL12456C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtsepi64StoreuEpi8() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12473C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12473C8
    run {
        mm256MaskCvtsepi64StoreuEpi8((MutPtr(localStorageL12473C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64StoreuEpi8() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12490C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12490C8
    run {
        mmMaskCvtsepi64StoreuEpi8((MutPtr(localStorageL12490C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MAX_VALUE, Byte.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi64StoreuEpi8() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12501C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12501C8
    run {
        mm512MaskCvtusepi64StoreuEpi8((MutPtr(localStorageL12501C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm256MaskCvtusepi64StoreuEpi8() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12518C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12518C8
    run {
        mm256MaskCvtusepi64StoreuEpi8((MutPtr(localStorageL12518C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64StoreuEpi8() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12535C8 = AddressableLocal(mmSet1Epi8(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12535C8
    run {
        mmMaskCvtusepi64StoreuEpi8((MutPtr(localStorageL12535C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi8(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, UByte.MAX_VALUE.toByte(), UByte.MAX_VALUE.toByte())
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtepi64StoreuEpi32() {
    val a = mm512Set1Epi64(9L)
    val localStorageL12552C8 = AddressableLocal(mm256UndefinedSi256(), ObjectSlotLayout<M256i>())
    val r by localStorageL12552C8
    run {
        mm512MaskCvtepi64StoreuEpi32((MutPtr(localStorageL12552C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mm256Set1Epi32(9)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtepi64StoreuEpi32() {
    val a = mm256Set1Epi64x(9L)
    val localStorageL12563C8 = AddressableLocal(mmSet1Epi32(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12563C8
    run {
        mm256MaskCvtepi64StoreuEpi32((MutPtr(localStorageL12563C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi32(9, 9, 9, 9)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtepi64StoreuEpi32() {
    val a = mmSet1Epi64x(9L)
    val localStorageL12574C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12574C8
    run {
        mmMaskCvtepi64StoreuEpi32((MutPtr(localStorageL12574C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mmSetEpi32(0, 0, 9, 9)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtsepi64StoreuEpi32() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12585C8 = AddressableLocal(mm256UndefinedSi256(), ObjectSlotLayout<M256i>())
    val r by localStorageL12585C8
    run {
        mm512MaskCvtsepi64StoreuEpi32((MutPtr(localStorageL12585C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mm256Set1Epi32(Int.MAX_VALUE)
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtsepi64StoreuEpi32() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12596C8 = AddressableLocal(mmSet1Epi32(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12596C8
    run {
        mm256MaskCvtsepi64StoreuEpi32((MutPtr(localStorageL12596C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFu, a)
    }
    val e = mmSet1Epi32(Int.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMmMaskCvtsepi64StoreuEpi32() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12607C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12607C8
    run {
        mmMaskCvtsepi64StoreuEpi32((MutPtr(localStorageL12607C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0x3u, a)
    }
    val e = mmSetEpi32(0, 0, Int.MAX_VALUE, Int.MAX_VALUE)
    assertEqM128i(r, e)
}

public fun testMm512MaskCvtusepi64StoreuEpi32() {
    val a = mm512Set1Epi64(Long.MAX_VALUE)
    val localStorageL12618C8 = AddressableLocal(mm256UndefinedSi256(), ObjectSlotLayout<M256i>())
    val r by localStorageL12618C8
    run {
        mm512MaskCvtusepi64StoreuEpi32((MutPtr(localStorageL12618C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFFu, a)
    }
    val e = mm256Set1Epi32(UInt.MAX_VALUE.toInt())
    assertEqM256i(r, e)
}

public fun testMm256MaskCvtusepi64StoreuEpi32() {
    val a = mm256Set1Epi64x(Long.MAX_VALUE)
    val localStorageL12629C8 = AddressableLocal(mmSet1Epi32(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12629C8
    run {
        mm256MaskCvtusepi64StoreuEpi32((MutPtr(localStorageL12629C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0xFu, a)
    }
    val e = mmSet1Epi32(UInt.MAX_VALUE.toInt())
    assertEqM128i(r, e)
}

public fun testMmMaskCvtusepi64StoreuEpi32() {
    val a = mmSet1Epi64x(Long.MAX_VALUE)
    val localStorageL12640C8 = AddressableLocal(mmSet1Epi16(0), ObjectSlotLayout<M128i>())
    val r by localStorageL12640C8
    run {
        mmMaskCvtusepi64StoreuEpi32((MutPtr(localStorageL12640C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0x3u, a)
    }
    val e = mmSetEpi32(0, 0, UInt.MAX_VALUE.toInt(), UInt.MAX_VALUE.toInt())
    assertEqM128i(r, e)
}

public fun testMm512StoreuEpi64() {
    val a = mm512Set1Epi64(9L)
    val localStorageL12651C8 = AddressableLocal(mm512Set1Epi64(0L), ObjectSlotLayout<M512i>())
    val r by localStorageL12651C8
    run {
        mm512StoreuEpi64((MutPtr(localStorageL12651C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreuEpi64() {
    val a = mm256Set1Epi64x(9L)
    val localStorageL12661C8 = AddressableLocal(mm256Set1Epi64x(0L), ObjectSlotLayout<M256i>())
    val r by localStorageL12661C8
    run {
        mm256StoreuEpi64((MutPtr(localStorageL12661C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreuEpi64() {
    val a = mmSet1Epi64x(9L)
    val localStorageL12671C8 = AddressableLocal(mmSet1Epi64x(0L), ObjectSlotLayout<M128i>())
    val r by localStorageL12671C8
    run {
        mmStoreuEpi64((MutPtr(localStorageL12671C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512LoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray>,
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = doubleArrayOf(4.0, 3.0, 2.0, 5.0, -8.0, -9.0, -64.0, -50.0))
    val p = (a.data).asPtr()
    val r = mm512LoadEpi64(blackBox(p))
    val e = mm512SetrEpi64(4L, 3L, 2L, 5L, -8L, -9L, -64L, -50L)
    assertEqM512i(r, e)
}

public fun testMm256LoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray>,
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = doubleArrayOf(4.0, 3.0, 2.0, 5.0))
    val p = (a.data).asPtr()
    val r = mm256LoadEpi64(blackBox(p))
    val e = mm256SetEpi64x(5L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMmLoadEpi64() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<LongArray>,
    ) {
        public var data: LongArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: LongArray, fieldLayout: TypeLayout<LongArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = doubleArrayOf(4.0, 3.0))
    val p = (a.data).asPtr()
    val r = mmLoadEpi64(blackBox(p))
    val e = mmSetEpi64x(3L, 4L)
    assertEqM128i(r, e)
}

public fun testMm512StoreEpi64() {
    val a = mm512Set1Epi64(9L)
    val localStorageL12722C8 = AddressableLocal(mm512Set1Epi64(0L), ObjectSlotLayout<M512i>())
    val r by localStorageL12722C8
    run {
        mm512StoreEpi64((MutPtr(localStorageL12722C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512i(r, a)
}

public fun testMm256StoreEpi64() {
    val a = mm256Set1Epi64x(9L)
    val localStorageL12732C8 = AddressableLocal(mm256Set1Epi64x(0L), ObjectSlotLayout<M256i>())
    val r by localStorageL12732C8
    run {
        mm256StoreEpi64((MutPtr(localStorageL12732C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM256i(r, a)
}

public fun testMmStoreEpi64() {
    val a = mmSet1Epi64x(9L)
    val localStorageL12742C8 = AddressableLocal(mmSet1Epi64x(0L), ObjectSlotLayout<M128i>())
    val r by localStorageL12742C8
    run {
        mmStoreEpi64((MutPtr(localStorageL12742C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM128i(r, a)
}

public fun testMm512LoadPd() {
    open class Align(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray>,
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align(ptr, layout) }, { it.data })
    }
    val a = Align(data = doubleArrayOf(4.0, 3.0, 2.0, 5.0, -8.0, -9.0, -64.0, -50.0))
    val p = (a.data).asPtr()
    val r = mm512LoadPd(blackBox(p))
    val e = mm512SetrPd(4.0, 3.0, 2.0, 5.0, -8.0, -9.0, -64.0, -50.0)
    assertEqM512d(r, e)
}

public fun testMm512StorePd() {
    val a = mm512Set1Pd(9.0)
    val localStorageL12767C8 = AddressableLocal(mm512UndefinedPd(), ObjectSlotLayout<M512d>())
    val r by localStorageL12767C8
    run {
        mm512StorePd((MutPtr(localStorageL12767C8.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, a)
    }
    assertEqM512d(r, a)
}

public fun testMm512TestEpi64Mask() {
    val a = mm512Set1Epi64(1L shl 0)
    val b = mm512Set1Epi64(1L shl 0 or 1L shl 1)
    val r = mm512TestEpi64Mask(a, b)
    val e: Mmask8 = 0xFFu
    check(r == e)
}

public fun testMm512MaskTestEpi64Mask() {
    val a = mm512Set1Epi64(1L shl 0)
    val b = mm512Set1Epi64(1L shl 0 or 1L shl 1)
    val r = mm512MaskTestEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm512MaskTestEpi64Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFFu
    check(r1 == e)
}

public fun testMm256TestEpi64Mask() {
    val a = mm256Set1Epi64x(1L shl 0)
    val b = mm256Set1Epi64x(1L shl 0 or 1L shl 1)
    val r = mm256TestEpi64Mask(a, b)
    val e: Mmask8 = 0xFu
    check(r == e)
}

public fun testMm256MaskTestEpi64Mask() {
    val a = mm256Set1Epi64x(1L shl 0)
    val b = mm256Set1Epi64x(1L shl 0 or 1L shl 1)
    val r = mm256MaskTestEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm256MaskTestEpi64Mask(0xFu, a, b)
    val e: Mmask8 = 0xFu
    check(r1 == e)
}

public fun testMmTestEpi64Mask() {
    val a = mmSet1Epi64x(1L shl 0)
    val b = mmSet1Epi64x(1L shl 0 or 1L shl 1)
    val r = mmTestEpi64Mask(a, b)
    val e: Mmask8 = 0x3u
    check(r == e)
}

public fun testMmMaskTestEpi64Mask() {
    val a = mmSet1Epi64x(1L shl 0)
    val b = mmSet1Epi64x(1L shl 0 or 1L shl 1)
    val r = mmMaskTestEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestEpi64Mask(0x3u, a, b)
    val e: Mmask8 = 0x3u
    check(r1 == e)
}

public fun testMm512TestnEpi64Mask() {
    val a = mm512Set1Epi64(1L shl 0)
    val b = mm512Set1Epi64(1L shl 0 or 1L shl 1)
    val r = mm512TestnEpi64Mask(a, b)
    val e: Mmask8 = 0x0u
    check(r == e)
}

public fun testMm512MaskTestnEpi64Mask() {
    val a = mm512Set1Epi64(1L shl 0)
    val b = mm512Set1Epi64(1L shl 1)
    val r = mm512MaskTestnEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm512MaskTestnEpi64Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFFu
    check(r1 == e)
}

public fun testMm256TestnEpi64Mask() {
    val a = mm256Set1Epi64x(1L shl 0)
    val b = mm256Set1Epi64x(1L shl 1)
    val r = mm256TestnEpi64Mask(a, b)
    val e: Mmask8 = 0xFu
    check(r == e)
}

public fun testMm256MaskTestnEpi64Mask() {
    val a = mm256Set1Epi64x(1L shl 0)
    val b = mm256Set1Epi64x(1L shl 1)
    val r = mm256MaskTestnEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mm256MaskTestnEpi64Mask(0xFFu, a, b)
    val e: Mmask8 = 0xFu
    check(r1 == e)
}

public fun testMmTestnEpi64Mask() {
    val a = mmSet1Epi64x(1L shl 0)
    val b = mmSet1Epi64x(1L shl 1)
    val r = mmTestnEpi64Mask(a, b)
    val e: Mmask8 = 0x3u
    check(r == e)
}

public fun testMmMaskTestnEpi64Mask() {
    val a = mmSet1Epi64x(1L shl 0)
    val b = mmSet1Epi64x(1L shl 1)
    val r = mmMaskTestnEpi64Mask(0u, a, b)
    check(r == 0u.toUByte())
    val r1 = mmMaskTestnEpi64Mask(0xFFu, a, b)
    val e: Mmask8 = 0x3u
    check(r1 == e)
}

public fun testMm512MaskSet1Epi64() {
    val src = mm512Set1Epi64(2L)
    val a: Long = 11L
    val r = mm512MaskSet1Epi64(src, 0u, a)
    assertEqM512i(r, src)
    val r1 = mm512MaskSet1Epi64(src, 0xFFu, a)
    val e = mm512Set1Epi64(11L)
    assertEqM512i(r1, e)
}

public fun testMm512MaskzSet1Epi64() {
    val a: Long = 11L
    val r = mm512MaskzSet1Epi64(0u, a)
    assertEqM512i(r, mm512SetzeroSi512())
    val r1 = mm512MaskzSet1Epi64(0xFFu, a)
    val e = mm512Set1Epi64(11L)
    assertEqM512i(r1, e)
}

public fun testMm256MaskSet1Epi64() {
    val src = mm256Set1Epi64x(2L)
    val a: Long = 11L
    val r = mm256MaskSet1Epi64(src, 0u, a)
    assertEqM256i(r, src)
    val r1 = mm256MaskSet1Epi64(src, 0xFu, a)
    val e = mm256Set1Epi64x(11L)
    assertEqM256i(r1, e)
}

public fun testMm256MaskzSet1Epi64() {
    val a: Long = 11L
    val r = mm256MaskzSet1Epi64(0u, a)
    assertEqM256i(r, mm256SetzeroSi256())
    val r1 = mm256MaskzSet1Epi64(0xFu, a)
    val e = mm256Set1Epi64x(11L)
    assertEqM256i(r1, e)
}

public fun testMmMaskSet1Epi64() {
    val src = mmSet1Epi64x(2L)
    val a: Long = 11L
    val r = mmMaskSet1Epi64(src, 0u, a)
    assertEqM128i(r, src)
    val r1 = mmMaskSet1Epi64(src, 0x3u, a)
    val e = mmSet1Epi64x(11L)
    assertEqM128i(r1, e)
}

public fun testMmMaskzSet1Epi64() {
    val a: Long = 11L
    val r = mmMaskzSet1Epi64(0u, a)
    assertEqM128i(r, mmSetzeroSi128())
    val r1 = mmMaskzSet1Epi64(0x3u, a)
    val e = mmSet1Epi64x(11L)
    assertEqM128i(r1, e)
}

public fun testMmCvtsdI64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtsdI64(a)
    val e: Long = -2L
    check(r == e)
}

public fun testMmCvtssI64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtssI64(a)
    val e: Long = -2L
    check(r == e)
}

public fun testMmCvtRoundi64Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Long = 9L
    val r = mmCvtRoundi64Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtRoundsi64Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Long = 9L
    val r = mmCvtRoundsi64Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvti64Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: Long = 9L
    val r = mmCvti64Ss(a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvti64Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: Long = 9L
    val r = mmCvti64Sd(a, b)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmCvtRoundsdSi64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtRoundsdSi64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvtRoundsdI64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtRoundsdI64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvtRoundsdU64() {
    val a = mmSetPd(1.0, Double.MAX_VALUE)
    val r = mmCvtRoundsdU64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvtsdU64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvtsdU64(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvtRoundssI64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssI64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvtRoundssSi64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssSi64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvtRoundssU64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtRoundssU64(a, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvtssU64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvtssU64(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvttsdI64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttsdI64(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundsdI64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdI64<MmFroundNoExc>(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundsdSi64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdSi64<MmFroundNoExc>(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundsdU64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttRoundsdU64<MmFroundNoExc>(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvttsdU64() {
    val a = mmSetPd(1.0, -1.5)
    val r = mmCvttsdU64(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvttssI64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttssI64(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundssI64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssI64<MmFroundNoExc>(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundssSi64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssSi64<MmFroundNoExc>(a)
    val e: Long = -1L
    check(r == e)
}

public fun testMmCvttRoundssU64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttRoundssU64<MmFroundNoExc>(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvttssU64() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val r = mmCvttssU64(a)
    val e: ULong = ULong.MAX_VALUE
    check(r == e)
}

public fun testMmCvtu64Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: ULong = 9uL
    val r = mmCvtu64Ss(a, b)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtu64Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: ULong = 9uL
    val r = mmCvtu64Sd(a, b)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmCvtRoundu64Ss() {
    val a = mmSetPs(0.0f, -0.5f, 1.0f, -1.5f)
    val b: ULong = 9uL
    val r = mmCvtRoundu64Ss(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPs(0.0f, -0.5f, 1.0f, 9.0f)
    assertEqM128(r, e)
}

public fun testMmCvtRoundu64Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: ULong = 9uL
    val r = mmCvtRoundu64Sd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmCvtRoundi64Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: Long = 9L
    val r = mmCvtRoundi64Sd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}

public fun testMmCvtRoundsi64Sd() {
    val a = mmSetPd(1.0, -1.5)
    val b: Long = 9L
    val r = mmCvtRoundsi64Sd(a, b, MM_FROUND_TO_ZERO or MM_FROUND_NO_EXC)
    val e = mmSetPd(1.0, 9.0)
    assertEqM128d(r, e)
}
