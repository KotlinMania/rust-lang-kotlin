@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.*
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x4x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x4x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x4x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1F32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1P8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1S8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1U8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qF32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qP8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qS8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld1qU8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld2qU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld3qU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vld4qU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1F32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1P8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1S8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1U8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qF32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qP8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qS8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU16X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU16X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU16X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU32X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU32X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU32X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU64X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU64X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU64X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU8X2
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU8X3
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst1qU8X4
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst2qU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst3qU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4S16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4S32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4S64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4U16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4U8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vst4qU8
import io.github.kotlinmania.rustlang.corearch.simd.F64x1
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x1
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U64x1
import io.github.kotlinmania.rustlang.corearch.simd.U64x2

// ARMv8 ASIMD intrinsics

public fun testVaddF64() {
    val a = Simd<Double>((listOf(1.0)).toList())
    val b = Simd<Double>((listOf(8.0)).toList())
    val e = Simd<Double>((listOf(9.0)).toList())
    val r = F64x1.from(vaddF64(a.into(), b.into()))
    check(r == e)
}

public fun testVaddqF64() {
    val a = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    val b = Simd<Double>(listOf(8.0.toDouble(), 7.0.toDouble()))
    val e = Simd<Double>(listOf(9.0.toDouble(), 9.0.toDouble()))
    val r = F64x2.from(vaddqF64(a.into(), b.into()))
    check(r == e)
}

public fun testVaddS64() {
    val a = Simd<Long>((listOf(1)).toList())
    val b = Simd<Long>((listOf(8)).toList())
    val e = Simd<Long>((listOf(9)).toList())
    val r = I64x1.from(vaddS64(a.into(), b.into()))
    check(r == e)
}

public fun testVaddU64() {
    val a = Simd<ULong>((listOf(1)).toList())
    val b = Simd<ULong>((listOf(8)).toList())
    val e = Simd<ULong>((listOf(9)).toList())
    val r = U64x1.from(vaddU64(a.into(), b.into()))
    check(r == e)
}

public fun testVadddS64() {
    val a = 1L
    val b = 8L
    val e = 9L
    val r: Long = vadddS64(a, b)
    check(r == e)
}

public fun testVadddU64() {
    val a = 1uL
    val b = 8uL
    val e = 9uL
    val r: ULong = vadddU64(a, b)
    check(r == e)
}

public fun testVextP64() {
    val a = Simd<ULong>(listOf(0uL))
    val b = Simd<ULong>(listOf(1uL))
    val e = Simd<ULong>(listOf(0uL))
    val r = U64x1.from(vextP64(a.into(), b.into(), 0))
    check(r == e)
}

public fun testVextF64() {
    val a = Simd<Double>(listOf(0.0.toDouble()))
    val b = Simd<Double>(listOf(1.0.toDouble()))
    val e = Simd<Double>(listOf(0.0.toDouble()))
    val r = F64x1.from(vextF64(a.into(), b.into(), 0))
    check(r == e)
}

public fun testVshldNS64() {
    val a: Long = 1L
    val e: Long = 4L
    val r: Long = vshldNS64(a, 2L)
    check(r == e)
}

public fun testVshldNU64() {
    val a: ULong = 1uL
    val e: ULong = 4uL
    val r: ULong = vshldNU64(a, 2uL)
    check(r == e)
}

public fun testVshrdNS64() {
    val a: Long = 4L
    val e: Long = 1L
    val r: Long = vshrdNS64(a, 2L)
    check(r == e)
}

public fun testVshrdNU64() {
    val a: ULong = 4uL
    val e: ULong = 1uL
    val r: ULong = vshrdNU64(a, 2uL)
    check(r == e)
}

public fun testVsradNS64() {
    val a: Long = 1L
    val b: Long = 4L
    val e: Long = 2L
    val r: Long = vsradNS64(a, b, 2L)
    check(r == e)
}

public fun testVsradNU64() {
    val a: ULong = 1uL
    val b: ULong = 4uL
    val e: ULong = 2uL
    val r: ULong = vsradNU64(a, b, 2uL)
    check(r == e)
}

public fun testVdupNF64() {
    val a: Double = 3.3
    val e = Simd<Double>(listOf(3.3.toDouble()))
    val r = F64x1.from(vdupNF64(a))
    check(r == e)
}

public fun testVdupNP64() {
    val a: ULong = 3uL
    val e = Simd<ULong>(listOf(3uL))
    val r = U64x1.from(vdupNP64(a))
    check(r == e)
}

public fun testVdupqNF64() {
    val a: Double = 3.3
    val e = Simd<Double>(listOf(3.3.toDouble(), 3.3.toDouble()))
    val r = F64x2.from(vdupqNF64(a))
    check(r == e)
}

public fun testVdupqNP64() {
    val a: ULong = 3uL
    val e = Simd<ULong>(listOf(3uL, 3uL))
    val r = U64x2.from(vdupqNP64(a))
    check(r == e)
}

public fun testVmovNP64() {
    val a: ULong = 3uL
    val e = Simd<ULong>(listOf(3uL))
    val r = U64x1.from(vmovNP64(a))
    check(r == e)
}

public fun testVmovNF64() {
    val a: Double = 3.3
    val e = Simd<Double>(listOf(3.3.toDouble()))
    val r = F64x1.from(vmovNF64(a))
    check(r == e)
}

public fun testVmovqNP64() {
    val a: ULong = 3uL
    val e = Simd<ULong>(listOf(3uL, 3uL))
    val r = U64x2.from(vmovqNP64(a))
    check(r == e)
}

public fun testVmovqNF64() {
    val a: Double = 3.3
    val e = Simd<Double>(listOf(3.3.toDouble(), 3.3.toDouble()))
    val r = F64x2.from(vmovqNF64(a))
    check(r == e)
}

public fun testVgetLaneF64() {
    val v = Simd<Double>(listOf(1.0.toDouble()))
    val r = vgetLaneF64(v.into(), 0)
    check(r == 1.0.toDouble())
}

public fun testVcopyLaneS64() {
    val a = Simd<Long>(listOf(1L))
    val b = Simd<Long>(listOf(0x7FFFFFFFFFFFFFFFL))
    val e = Simd<Long>(listOf(0x7FFFFFFFFFFFFFFFL))
    val r = I64x1.from(vcopyLaneS64(a.into(), b.into(), 0, 0))
    check(r == e)
}

public fun testVcopyLaneU64() {
    val a = Simd<ULong>(listOf(1uL))
    val b = Simd<ULong>(listOf(0xFFFFFFFFFFFFFFFFuL))
    val e = Simd<ULong>(listOf(0xFFFFFFFFFFFFFFFFuL))
    val r = U64x1.from(vcopyLaneU64(a.into(), b.into(), 0, 0))
    check(r == e)
}

public fun testVcopyLaneP64() {
    val a = Simd<ULong>(listOf(1uL))
    val b = Simd<ULong>(listOf(0x7FFFFFFFFFFFFFFFuL))
    val e = Simd<ULong>(listOf(0x7FFFFFFFFFFFFFFFuL))
    val r = U64x1.from(vcopyLaneP64(a.into(), b.into(), 0, 0))
    check(r == e)
}

public fun testVcopyLaneF64() {
    val a = Simd<Double>((listOf(1.0)).toList())
    val b = Simd<Double>((listOf(0.0)).toList())
    val e = Simd<Double>((listOf(0.0)).toList())
    val r = F64x1.from(vcopyLaneF64(a.into(), b.into(), 0, 0))
    check(r == e)
}

public fun testVbslF64() {
    val a = Simd<ULong>(listOf(0x8000000000000000uL))
    val b = Simd<Double>(listOf(-1.23))
    val c = Simd<Double>(listOf(2.34))
    val e = Simd<Double>(listOf(-2.34))
    val r = F64x1.from(vbslF64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslP64() {
    val a = Simd<ULong>(listOf(1uL))
    val b = Simd<ULong>(listOf(ULong.MAX_VALUE))
    val c = Simd<ULong>(listOf(ULong.MIN_VALUE))
    val e = Simd<ULong>(listOf(1uL))
    val r = U64x1.from(vbslP64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqF64() {
    val a = Simd<ULong>(listOf(1uL, 0x8000000000000000uL))
    val b = Simd<Double>(listOf(Double.MAX_VALUE, -1.23))
    val c = Simd<Double>(listOf((-Double.MAX_VALUE), 2.34))
    val e = Simd<Double>(listOf((-Double.MAX_VALUE), -2.34))
    val r = F64x2.from(vbslqF64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqP64() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val b = Simd<ULong>(listOf(ULong.MAX_VALUE, ULong.MAX_VALUE))
    val c = Simd<ULong>(listOf(ULong.MIN_VALUE, ULong.MIN_VALUE))
    val e = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val r = U64x2.from(vbslqP64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVld1F64() {
    val a: DoubleArray = doubleArrayOf(0.0, 1.0)
    val e = Simd<Double>(listOf(1.0.toDouble()))
    val r = F64x1.from(vld1F64(((a).sliceArray(1 until a.size)).asPtr()))
    check(r == e)
}

public fun testVld1qF64() {
    val a: DoubleArray = doubleArrayOf(0.0, 1.0, 2.0)
    val e = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    val r = F64x2.from(vld1qF64(((a).sliceArray(1 until a.size)).asPtr()))
    check(r == e)
}

public fun testVld1DupF64() {
    val a: DoubleArray = doubleArrayOf(1.0, 42.0)
    val e = Simd<Double>(listOf(42.0.toDouble()))
    val r = F64x1.from(vld1DupF64(((a).sliceArray(1 until a.size)).asPtr()))
    check(r == e)
}

public fun testVld1qDupF64() {
    val e = Simd<Double>(listOf(42.0.toDouble(), 42.0.toDouble()))
    val r = F64x2.from(vld1qDupF64(elem))
    check(r == e)
}

public fun testVld1LaneF64() {
    val a = Simd<Double>(listOf(0.0.toDouble()))
    val e = Simd<Double>(listOf(42.0.toDouble()))
    val r = F64x1.from(vld1LaneF64(elem, a.into(), 0))
    check(r == e)
}

public fun testVld1qLaneF64() {
    val a = Simd<Double>(listOf(0.0.toDouble(), 1.0.toDouble()))
    val e = Simd<Double>(listOf(0.0.toDouble(), 42.0.toDouble()))
    val r = F64x2.from(vld1qLaneF64(elem, a.into(), 1))
    check(r == e)
}

public fun testVst1F64() {
    val vals = MutableList(2) { 0.0 }
    val a = Simd<Double>(listOf(1.0.toDouble()))
    run {
        vst1F64(vals[1 until vals.size].asMutPtr(), a.into())
    }
    check(vals[0] == 0.0)
    check(vals[1] == 1.0)
}

public fun testVst1qF64() {
    val vals = MutableList(3) { 0.0 }
    val a = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    run {
        vst1qF64(vals[1 until vals.size].asMutPtr(), a.into())
    }
    check(vals[0] == 0.0)
    check(vals[1] == 1.0)
    check(vals[2] == 2.0)
}

public fun testVld1F16X2() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x2T = transmuteWithLayouts<List<F16>, Float16x4x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt()), UnresolvedTypeLayout<Float16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1F16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x2T = vld1F16X2(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1F16X3() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x3T = transmuteWithLayouts<List<F16>, Float16x4x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (12).toInt()), UnresolvedTypeLayout<Float16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1F16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x3T = vld1F16X3(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1F16X4() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x4T = transmuteWithLayouts<List<F16>, Float16x4x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1F16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x4T = vld1F16X4(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1qF16X2() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x2T = transmuteWithLayouts<List<F16>, Float16x8x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1qF16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x2T = vld1qF16X2(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1qF16X3() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x3T = transmuteWithLayouts<List<F16>, Float16x8x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (24).toInt()), UnresolvedTypeLayout<Float16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1qF16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x3T = vld1qF16X3(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1qF16X4() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x4T = transmuteWithLayouts<List<F16>, Float16x8x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (32).toInt()), UnresolvedTypeLayout<Float16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst1qF16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x4T = vld1qF16X4(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld2F16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x2T = transmuteWithLayouts<List<F16>, Float16x4x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt()), UnresolvedTypeLayout<Float16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst2F16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x2T = vld2F16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld3F16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x3T = transmuteWithLayouts<List<F16>, Float16x4x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (12).toInt()), UnresolvedTypeLayout<Float16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst3F16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x3T = vld3F16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld4F16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x4T = transmuteWithLayouts<List<F16>, Float16x4x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst4F16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x4x4T = vld4F16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x4x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld2qF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x2T = transmuteWithLayouts<List<F16>, Float16x8x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst2qF16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x2T = vld2qF16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld3qF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x3T = transmuteWithLayouts<List<F16>, Float16x8x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (24).toInt()), UnresolvedTypeLayout<Float16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst3qF16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x3T = vld3qF16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld4qF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x4T = transmuteWithLayouts<List<F16>, Float16x8x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (32).toInt()), UnresolvedTypeLayout<Float16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<List<F16>>())
    vst4qF16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float16x8x4T = vld4qF16(tmp1.asPtr().cast())
    val out: List<F16> = transmuteWithLayouts<Float16x8x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld1P64X2() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x1x2T = transmuteWithLayouts<ULongArray, Poly64x1x2T>(vals, PrimitiveArrayLayouts.uLongArray((2).toInt()), UnresolvedTypeLayout<Poly64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1P64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x1x2T = vld1P64X2(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x1x2T, ULongArray>(r, UnresolvedTypeLayout<Poly64x1x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P64X3() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x1x3T = transmuteWithLayouts<ULongArray, Poly64x1x3T>(vals, PrimitiveArrayLayouts.uLongArray((3).toInt()), UnresolvedTypeLayout<Poly64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1P64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x1x3T = vld1P64X3(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x1x3T, ULongArray>(r, UnresolvedTypeLayout<Poly64x1x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P64X4() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x1x4T = transmuteWithLayouts<ULongArray, Poly64x1x4T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Poly64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1P64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x1x4T = vld1P64X4(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x1x4T, ULongArray>(r, UnresolvedTypeLayout<Poly64x1x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP64X2() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x2x2T = transmuteWithLayouts<ULongArray, Poly64x2x2T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Poly64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qP64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x2x2T = vld1qP64X2(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x2x2T, ULongArray>(r, UnresolvedTypeLayout<Poly64x2x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP64X3() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x2x3T = transmuteWithLayouts<ULongArray, Poly64x2x3T>(vals, PrimitiveArrayLayouts.uLongArray((6).toInt()), UnresolvedTypeLayout<Poly64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qP64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x2x3T = vld1qP64X3(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x2x3T, ULongArray>(r, UnresolvedTypeLayout<Poly64x2x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP64X4() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Poly64x2x4T = transmuteWithLayouts<ULongArray, Poly64x2x4T>(vals, PrimitiveArrayLayouts.uLongArray((8).toInt()), UnresolvedTypeLayout<Poly64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qP64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly64x2x4T = vld1qP64X4(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Poly64x2x4T, ULongArray>(r, UnresolvedTypeLayout<Poly64x2x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F32X2() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x2T = transmuteWithLayouts<FloatArray, Float32x2x2T>(vals, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<Float32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1F32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x2T = vld1F32X2(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F32X3() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x3T = transmuteWithLayouts<FloatArray, Float32x2x3T>(vals, PrimitiveArrayLayouts.floatArray((6).toInt()), UnresolvedTypeLayout<Float32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1F32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x3T = vld1F32X3(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F32X4() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x4T = transmuteWithLayouts<FloatArray, Float32x2x4T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1F32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x4T = vld1F32X4(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF32X2() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x2T = transmuteWithLayouts<FloatArray, Float32x4x2T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1qF32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x2T = vld1qF32X2(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF32X3() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x3T = transmuteWithLayouts<FloatArray, Float32x4x3T>(vals, PrimitiveArrayLayouts.floatArray((12).toInt()), UnresolvedTypeLayout<Float32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1qF32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x3T = vld1qF32X3(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF32X4() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x4T = transmuteWithLayouts<FloatArray, Float32x4x4T>(vals, PrimitiveArrayLayouts.floatArray((16).toInt()), UnresolvedTypeLayout<Float32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst1qF32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x4T = vld1qF32X4(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F64X2() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x2T = transmuteWithLayouts<DoubleArray, Float64x1x2T>(vals, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<Float64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1F64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x2T = vld1F64X2(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x2T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x2T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F64X3() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x3T = transmuteWithLayouts<DoubleArray, Float64x1x3T>(vals, PrimitiveArrayLayouts.doubleArray((3).toInt()), UnresolvedTypeLayout<Float64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1F64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x3T = vld1F64X3(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x3T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x3T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1F64X4() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x4T = transmuteWithLayouts<DoubleArray, Float64x1x4T>(vals, PrimitiveArrayLayouts.doubleArray((4).toInt()), UnresolvedTypeLayout<Float64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1F64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x4T = vld1F64X4(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x4T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x4T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF64X2() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x2T = transmuteWithLayouts<DoubleArray, Float64x2x2T>(vals, PrimitiveArrayLayouts.doubleArray((4).toInt()), UnresolvedTypeLayout<Float64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1qF64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x2T = vld1qF64X2(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x2T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x2T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF64X3() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x3T = transmuteWithLayouts<DoubleArray, Float64x2x3T>(vals, PrimitiveArrayLayouts.doubleArray((6).toInt()), UnresolvedTypeLayout<Float64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1qF64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x3T = vld1qF64X3(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x3T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x3T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qF64X4() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x4T = transmuteWithLayouts<DoubleArray, Float64x2x4T>(vals, PrimitiveArrayLayouts.doubleArray((8).toInt()), UnresolvedTypeLayout<Float64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst1qF64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x4T = vld1qF64X4(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x4T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x4T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S8X2() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x2T = transmuteWithLayouts<ByteArray, Int8x8x2T>(vals, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<Int8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1S8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x2T = vld1S8X2(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S8X3() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x3T = transmuteWithLayouts<ByteArray, Int8x8x3T>(vals, PrimitiveArrayLayouts.byteArray((24).toInt()), UnresolvedTypeLayout<Int8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1S8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x3T = vld1S8X3(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S8X4() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x4T = transmuteWithLayouts<ByteArray, Int8x8x4T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1S8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x4T = vld1S8X4(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS8X2() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x2T = transmuteWithLayouts<ByteArray, Int8x16x2T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1qS8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x2T = vld1qS8X2(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS8X3() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x3T = transmuteWithLayouts<ByteArray, Int8x16x3T>(vals, PrimitiveArrayLayouts.byteArray((48).toInt()), UnresolvedTypeLayout<Int8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1qS8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x3T = vld1qS8X3(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS8X4() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x4T = transmuteWithLayouts<ByteArray, Int8x16x4T>(vals, PrimitiveArrayLayouts.byteArray((64).toInt()), UnresolvedTypeLayout<Int8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst1qS8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x4T = vld1qS8X4(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S16X2() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x2T = transmuteWithLayouts<ShortArray, Int16x4x2T>(vals, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<Int16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1S16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x2T = vld1S16X2(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S16X3() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x3T = transmuteWithLayouts<ShortArray, Int16x4x3T>(vals, PrimitiveArrayLayouts.shortArray((12).toInt()), UnresolvedTypeLayout<Int16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1S16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x3T = vld1S16X3(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S16X4() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x4T = transmuteWithLayouts<ShortArray, Int16x4x4T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1S16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x4T = vld1S16X4(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS16X2() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x2T = transmuteWithLayouts<ShortArray, Int16x8x2T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1qS16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x2T = vld1qS16X2(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS16X3() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x3T = transmuteWithLayouts<ShortArray, Int16x8x3T>(vals, PrimitiveArrayLayouts.shortArray((24).toInt()), UnresolvedTypeLayout<Int16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1qS16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x3T = vld1qS16X3(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS16X4() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x4T = transmuteWithLayouts<ShortArray, Int16x8x4T>(vals, PrimitiveArrayLayouts.shortArray((32).toInt()), UnresolvedTypeLayout<Int16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst1qS16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x4T = vld1qS16X4(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S32X2() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x2T = transmuteWithLayouts<IntArray, Int32x2x2T>(vals, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<Int32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1S32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x2T = vld1S32X2(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x2T, IntArray>(r, UnresolvedTypeLayout<Int32x2x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S32X3() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x3T = transmuteWithLayouts<IntArray, Int32x2x3T>(vals, PrimitiveArrayLayouts.intArray((6).toInt()), UnresolvedTypeLayout<Int32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1S32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x3T = vld1S32X3(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x3T, IntArray>(r, UnresolvedTypeLayout<Int32x2x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S32X4() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x4T = transmuteWithLayouts<IntArray, Int32x2x4T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1S32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x4T = vld1S32X4(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x4T, IntArray>(r, UnresolvedTypeLayout<Int32x2x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS32X2() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x2T = transmuteWithLayouts<IntArray, Int32x4x2T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1qS32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x2T = vld1qS32X2(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x2T, IntArray>(r, UnresolvedTypeLayout<Int32x4x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS32X3() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x3T = transmuteWithLayouts<IntArray, Int32x4x3T>(vals, PrimitiveArrayLayouts.intArray((12).toInt()), UnresolvedTypeLayout<Int32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1qS32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x3T = vld1qS32X3(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x3T, IntArray>(r, UnresolvedTypeLayout<Int32x4x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS32X4() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x4T = transmuteWithLayouts<IntArray, Int32x4x4T>(vals, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<Int32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst1qS32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x4T = vld1qS32X4(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x4T, IntArray>(r, UnresolvedTypeLayout<Int32x4x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S64X2() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x2T = transmuteWithLayouts<LongArray, Int64x1x2T>(vals, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<Int64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1S64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x2T = vld1S64X2(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x2T, LongArray>(r, UnresolvedTypeLayout<Int64x1x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S64X3() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x3T = transmuteWithLayouts<LongArray, Int64x1x3T>(vals, PrimitiveArrayLayouts.longArray((3).toInt()), UnresolvedTypeLayout<Int64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1S64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x3T = vld1S64X3(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x3T, LongArray>(r, UnresolvedTypeLayout<Int64x1x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1S64X4() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x4T = transmuteWithLayouts<LongArray, Int64x1x4T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1S64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x4T = vld1S64X4(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x4T, LongArray>(r, UnresolvedTypeLayout<Int64x1x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS64X2() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x2T = transmuteWithLayouts<LongArray, Int64x2x2T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1qS64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x2T = vld1qS64X2(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x2T, LongArray>(r, UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS64X3() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x3T = transmuteWithLayouts<LongArray, Int64x2x3T>(vals, PrimitiveArrayLayouts.longArray((6).toInt()), UnresolvedTypeLayout<Int64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1qS64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x3T = vld1qS64X3(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x3T, LongArray>(r, UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qS64X4() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x4T = transmuteWithLayouts<LongArray, Int64x2x4T>(vals, PrimitiveArrayLayouts.longArray((8).toInt()), UnresolvedTypeLayout<Int64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst1qS64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x4T = vld1qS64X4(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x4T, LongArray>(r, UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U8X2() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x2T = transmuteWithLayouts<UByteArray, Uint8x8x2T>(vals, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Uint8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1U8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x2T = vld1U8X2(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U8X3() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x3T = transmuteWithLayouts<UByteArray, Uint8x8x3T>(vals, PrimitiveArrayLayouts.uByteArray((24).toInt()), UnresolvedTypeLayout<Uint8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1U8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x3T = vld1U8X3(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U8X4() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x4T = transmuteWithLayouts<UByteArray, Uint8x8x4T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1U8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x4T = vld1U8X4(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU8X2() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x2T = transmuteWithLayouts<UByteArray, Uint8x16x2T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qU8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x2T = vld1qU8X2(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU8X3() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x3T = transmuteWithLayouts<UByteArray, Uint8x16x3T>(vals, PrimitiveArrayLayouts.uByteArray((48).toInt()), UnresolvedTypeLayout<Uint8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qU8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x3T = vld1qU8X3(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU8X4() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x4T = transmuteWithLayouts<UByteArray, Uint8x16x4T>(vals, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Uint8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qU8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x4T = vld1qU8X4(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U16X2() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x2T = transmuteWithLayouts<UShortArray, Uint16x4x2T>(vals, PrimitiveArrayLayouts.uShortArray((8).toInt()), UnresolvedTypeLayout<Uint16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1U16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x2T = vld1U16X2(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U16X3() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x3T = transmuteWithLayouts<UShortArray, Uint16x4x3T>(vals, PrimitiveArrayLayouts.uShortArray((12).toInt()), UnresolvedTypeLayout<Uint16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1U16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x3T = vld1U16X3(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U16X4() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x4T = transmuteWithLayouts<UShortArray, Uint16x4x4T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1U16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x4T = vld1U16X4(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU16X2() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x2T = transmuteWithLayouts<UShortArray, Uint16x8x2T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qU16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x2T = vld1qU16X2(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU16X3() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x3T = transmuteWithLayouts<UShortArray, Uint16x8x3T>(vals, PrimitiveArrayLayouts.uShortArray((24).toInt()), UnresolvedTypeLayout<Uint16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qU16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x3T = vld1qU16X3(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU16X4() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x4T = transmuteWithLayouts<UShortArray, Uint16x8x4T>(vals, PrimitiveArrayLayouts.uShortArray((32).toInt()), UnresolvedTypeLayout<Uint16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qU16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x4T = vld1qU16X4(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U32X2() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x2T = transmuteWithLayouts<UIntArray, Uint32x2x2T>(vals, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<Uint32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1U32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x2T = vld1U32X2(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U32X3() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x3T = transmuteWithLayouts<UIntArray, Uint32x2x3T>(vals, PrimitiveArrayLayouts.uIntArray((6).toInt()), UnresolvedTypeLayout<Uint32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1U32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x3T = vld1U32X3(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U32X4() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x4T = transmuteWithLayouts<UIntArray, Uint32x2x4T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1U32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x4T = vld1U32X4(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU32X2() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x2T = transmuteWithLayouts<UIntArray, Uint32x4x2T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1qU32X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x2T = vld1qU32X2(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU32X3() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x3T = transmuteWithLayouts<UIntArray, Uint32x4x3T>(vals, PrimitiveArrayLayouts.uIntArray((12).toInt()), UnresolvedTypeLayout<Uint32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1qU32X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x3T = vld1qU32X3(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU32X4() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x4T = transmuteWithLayouts<UIntArray, Uint32x4x4T>(vals, PrimitiveArrayLayouts.uIntArray((16).toInt()), UnresolvedTypeLayout<Uint32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst1qU32X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x4T = vld1qU32X4(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U64X2() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x2T = transmuteWithLayouts<ULongArray, Uint64x1x2T>(vals, PrimitiveArrayLayouts.uLongArray((2).toInt()), UnresolvedTypeLayout<Uint64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1U64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x2T = vld1U64X2(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U64X3() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x3T = transmuteWithLayouts<ULongArray, Uint64x1x3T>(vals, PrimitiveArrayLayouts.uLongArray((3).toInt()), UnresolvedTypeLayout<Uint64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1U64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x3T = vld1U64X3(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1U64X4() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x4T = transmuteWithLayouts<ULongArray, Uint64x1x4T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1U64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x4T = vld1U64X4(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU64X2() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x2T = transmuteWithLayouts<ULongArray, Uint64x2x2T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qU64X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x2T = vld1qU64X2(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU64X3() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x3T = transmuteWithLayouts<ULongArray, Uint64x2x3T>(vals, PrimitiveArrayLayouts.uLongArray((6).toInt()), UnresolvedTypeLayout<Uint64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qU64X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x3T = vld1qU64X3(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qU64X4() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x4T = transmuteWithLayouts<ULongArray, Uint64x2x4T>(vals, PrimitiveArrayLayouts.uLongArray((8).toInt()), UnresolvedTypeLayout<Uint64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst1qU64X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x4T = vld1qU64X4(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P8X2() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x2T = transmuteWithLayouts<UByteArray, Poly8x8x2T>(vals, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Poly8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1P8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x2T = vld1P8X2(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x2T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P8X3() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x3T = transmuteWithLayouts<UByteArray, Poly8x8x3T>(vals, PrimitiveArrayLayouts.uByteArray((24).toInt()), UnresolvedTypeLayout<Poly8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1P8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x3T = vld1P8X3(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x3T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P8X4() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x4T = transmuteWithLayouts<UByteArray, Poly8x8x4T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Poly8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1P8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x4T = vld1P8X4(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x4T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP8X2() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x2T = transmuteWithLayouts<UByteArray, Poly8x16x2T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Poly8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qP8X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x2T = vld1qP8X2(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x2T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP8X3() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x3T = transmuteWithLayouts<UByteArray, Poly8x16x3T>(vals, PrimitiveArrayLayouts.uByteArray((48).toInt()), UnresolvedTypeLayout<Poly8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qP8X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x3T = vld1qP8X3(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x3T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP8X4() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x4T = transmuteWithLayouts<UByteArray, Poly8x16x4T>(vals, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Poly8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst1qP8X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x4T = vld1qP8X4(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x4T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P16X2() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x2T = transmuteWithLayouts<UShortArray, Poly16x4x2T>(vals, PrimitiveArrayLayouts.uShortArray((8).toInt()), UnresolvedTypeLayout<Poly16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1P16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x2T = vld1P16X2(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x2T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P16X3() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x3T = transmuteWithLayouts<UShortArray, Poly16x4x3T>(vals, PrimitiveArrayLayouts.uShortArray((12).toInt()), UnresolvedTypeLayout<Poly16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1P16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x3T = vld1P16X3(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x3T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1P16X4() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x4T = transmuteWithLayouts<UShortArray, Poly16x4x4T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Poly16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1P16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x4T = vld1P16X4(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x4T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP16X2() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x2T = transmuteWithLayouts<UShortArray, Poly16x8x2T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Poly16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qP16X2(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x2T = vld1qP16X2(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x2T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP16X3() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x3T = transmuteWithLayouts<UShortArray, Poly16x8x3T>(vals, PrimitiveArrayLayouts.uShortArray((24).toInt()), UnresolvedTypeLayout<Poly16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qP16X3(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x3T = vld1qP16X3(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x3T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld1qP16X4() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x4T = transmuteWithLayouts<UShortArray, Poly16x8x4T>(vals, PrimitiveArrayLayouts.uShortArray((32).toInt()), UnresolvedTypeLayout<Poly16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst1qP16X4(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x4T = vld1qP16X4(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x4T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2F32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x2T = transmuteWithLayouts<FloatArray, Float32x2x2T>(vals, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<Float32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst2F32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x2T = vld2F32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld3F32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x3T = transmuteWithLayouts<FloatArray, Float32x2x3T>(vals, PrimitiveArrayLayouts.floatArray((6).toInt()), UnresolvedTypeLayout<Float32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst3F32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x3T = vld3F32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld4F32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x4T = transmuteWithLayouts<FloatArray, Float32x2x4T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst4F32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x2x4T = vld4F32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x2x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x2T = transmuteWithLayouts<FloatArray, Float32x4x2T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst2qF32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x2T = vld2qF32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x3T = transmuteWithLayouts<FloatArray, Float32x4x3T>(vals, PrimitiveArrayLayouts.floatArray((12).toInt()), UnresolvedTypeLayout<Float32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst3qF32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x3T = vld3qF32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x4T = transmuteWithLayouts<FloatArray, Float32x4x4T>(vals, PrimitiveArrayLayouts.floatArray((16).toInt()), UnresolvedTypeLayout<Float32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<FloatArray>())
    vst4qF32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float32x4x4T = vld4qF32(tmp1.asPtr().cast())
    val out: FloatArray = transmuteWithLayouts<Float32x4x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld2F64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x2T = transmuteWithLayouts<DoubleArray, Float64x1x2T>(vals, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<Float64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst2F64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x2T = vld2F64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x2T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x2T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld3F64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x3T = transmuteWithLayouts<DoubleArray, Float64x1x3T>(vals, PrimitiveArrayLayouts.doubleArray((3).toInt()), UnresolvedTypeLayout<Float64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst3F64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x3T = vld3F64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x3T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x3T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld4F64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x1x4T = transmuteWithLayouts<DoubleArray, Float64x1x4T>(vals, PrimitiveArrayLayouts.doubleArray((4).toInt()), UnresolvedTypeLayout<Float64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst4F64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x1x4T = vld4F64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x1x4T, DoubleArray>(r, UnresolvedTypeLayout<Float64x1x4T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qF64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x2T = transmuteWithLayouts<DoubleArray, Float64x2x2T>(vals, PrimitiveArrayLayouts.doubleArray((4).toInt()), UnresolvedTypeLayout<Float64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst2qF64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x2T = vld2qF64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x2T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x2T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qF64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x3T = transmuteWithLayouts<DoubleArray, Float64x2x3T>(vals, PrimitiveArrayLayouts.doubleArray((6).toInt()), UnresolvedTypeLayout<Float64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst3qF64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x3T = vld3qF64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x3T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x3T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qF64() {
    val vals: DoubleArray = fromFn({ i -> i.toDouble() })
    val a: Float64x2x4T = transmuteWithLayouts<DoubleArray, Float64x2x4T>(vals, PrimitiveArrayLayouts.doubleArray((8).toInt()), UnresolvedTypeLayout<Float64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<DoubleArray>())
    vst4qF64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Float64x2x4T = vld4qF64(tmp1.asPtr().cast())
    val out: DoubleArray = transmuteWithLayouts<Float64x2x4T, DoubleArray>(r, UnresolvedTypeLayout<Float64x2x4T>(), UnresolvedTypeLayout<DoubleArray>())
    check((out).contentEquals(vals))
}

public fun testVld2S8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x2T = transmuteWithLayouts<ByteArray, Int8x8x2T>(vals, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<Int8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst2S8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x2T = vld2S8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3S8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x3T = transmuteWithLayouts<ByteArray, Int8x8x3T>(vals, PrimitiveArrayLayouts.byteArray((24).toInt()), UnresolvedTypeLayout<Int8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst3S8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x3T = vld3S8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4S8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x4T = transmuteWithLayouts<ByteArray, Int8x8x4T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst4S8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x8x4T = vld4S8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x8x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x2T = transmuteWithLayouts<ByteArray, Int8x16x2T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst2qS8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x2T = vld2qS8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x3T = transmuteWithLayouts<ByteArray, Int8x16x3T>(vals, PrimitiveArrayLayouts.byteArray((48).toInt()), UnresolvedTypeLayout<Int8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst3qS8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x3T = vld3qS8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x4T = transmuteWithLayouts<ByteArray, Int8x16x4T>(vals, PrimitiveArrayLayouts.byteArray((64).toInt()), UnresolvedTypeLayout<Int8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ByteArray>())
    vst4qS8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int8x16x4T = vld4qS8(tmp1.asPtr().cast())
    val out: ByteArray = transmuteWithLayouts<Int8x16x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2S16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x2T = transmuteWithLayouts<ShortArray, Int16x4x2T>(vals, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<Int16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst2S16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x2T = vld2S16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3S16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x3T = transmuteWithLayouts<ShortArray, Int16x4x3T>(vals, PrimitiveArrayLayouts.shortArray((12).toInt()), UnresolvedTypeLayout<Int16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst3S16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x3T = vld3S16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4S16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x4T = transmuteWithLayouts<ShortArray, Int16x4x4T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst4S16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x4x4T = vld4S16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x4x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x2T = transmuteWithLayouts<ShortArray, Int16x8x2T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst2qS16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x2T = vld2qS16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x3T = transmuteWithLayouts<ShortArray, Int16x8x3T>(vals, PrimitiveArrayLayouts.shortArray((24).toInt()), UnresolvedTypeLayout<Int16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst3qS16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x3T = vld3qS16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x4T = transmuteWithLayouts<ShortArray, Int16x8x4T>(vals, PrimitiveArrayLayouts.shortArray((32).toInt()), UnresolvedTypeLayout<Int16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ShortArray>())
    vst4qS16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int16x8x4T = vld4qS16(tmp1.asPtr().cast())
    val out: ShortArray = transmuteWithLayouts<Int16x8x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2S32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x2T = transmuteWithLayouts<IntArray, Int32x2x2T>(vals, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<Int32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst2S32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x2T = vld2S32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x2T, IntArray>(r, UnresolvedTypeLayout<Int32x2x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3S32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x3T = transmuteWithLayouts<IntArray, Int32x2x3T>(vals, PrimitiveArrayLayouts.intArray((6).toInt()), UnresolvedTypeLayout<Int32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst3S32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x3T = vld3S32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x3T, IntArray>(r, UnresolvedTypeLayout<Int32x2x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4S32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x4T = transmuteWithLayouts<IntArray, Int32x2x4T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst4S32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x2x4T = vld4S32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x2x4T, IntArray>(r, UnresolvedTypeLayout<Int32x2x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x2T = transmuteWithLayouts<IntArray, Int32x4x2T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst2qS32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x2T = vld2qS32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x2T, IntArray>(r, UnresolvedTypeLayout<Int32x4x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x3T = transmuteWithLayouts<IntArray, Int32x4x3T>(vals, PrimitiveArrayLayouts.intArray((12).toInt()), UnresolvedTypeLayout<Int32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst3qS32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x3T = vld3qS32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x3T, IntArray>(r, UnresolvedTypeLayout<Int32x4x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x4T = transmuteWithLayouts<IntArray, Int32x4x4T>(vals, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<Int32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<IntArray>())
    vst4qS32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int32x4x4T = vld4qS32(tmp1.asPtr().cast())
    val out: IntArray = transmuteWithLayouts<Int32x4x4T, IntArray>(r, UnresolvedTypeLayout<Int32x4x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2S64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x2T = transmuteWithLayouts<LongArray, Int64x1x2T>(vals, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<Int64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst2S64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x2T = vld2S64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x2T, LongArray>(r, UnresolvedTypeLayout<Int64x1x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3S64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x3T = transmuteWithLayouts<LongArray, Int64x1x3T>(vals, PrimitiveArrayLayouts.longArray((3).toInt()), UnresolvedTypeLayout<Int64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst3S64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x3T = vld3S64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x3T, LongArray>(r, UnresolvedTypeLayout<Int64x1x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4S64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x4T = transmuteWithLayouts<LongArray, Int64x1x4T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst4S64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x1x4T = vld4S64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x1x4T, LongArray>(r, UnresolvedTypeLayout<Int64x1x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x2T = transmuteWithLayouts<LongArray, Int64x2x2T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst2qS64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x2T = vld2qS64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x2T, LongArray>(r, UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x3T = transmuteWithLayouts<LongArray, Int64x2x3T>(vals, PrimitiveArrayLayouts.longArray((6).toInt()), UnresolvedTypeLayout<Int64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst3qS64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x3T = vld3qS64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x3T, LongArray>(r, UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x4T = transmuteWithLayouts<LongArray, Int64x2x4T>(vals, PrimitiveArrayLayouts.longArray((8).toInt()), UnresolvedTypeLayout<Int64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<LongArray>())
    vst4qS64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Int64x2x4T = vld4qS64(tmp1.asPtr().cast())
    val out: LongArray = transmuteWithLayouts<Int64x2x4T, LongArray>(r, UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2U8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x2T = transmuteWithLayouts<UByteArray, Uint8x8x2T>(vals, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Uint8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst2U8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x2T = vld2U8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3U8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x3T = transmuteWithLayouts<UByteArray, Uint8x8x3T>(vals, PrimitiveArrayLayouts.uByteArray((24).toInt()), UnresolvedTypeLayout<Uint8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst3U8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x3T = vld3U8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4U8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x4T = transmuteWithLayouts<UByteArray, Uint8x8x4T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst4U8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x8x4T = vld4U8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x8x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x2T = transmuteWithLayouts<UByteArray, Uint8x16x2T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst2qU8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x2T = vld2qU8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x3T = transmuteWithLayouts<UByteArray, Uint8x16x3T>(vals, PrimitiveArrayLayouts.uByteArray((48).toInt()), UnresolvedTypeLayout<Uint8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst3qU8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x3T = vld3qU8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x4T = transmuteWithLayouts<UByteArray, Uint8x16x4T>(vals, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Uint8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst4qU8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint8x16x4T = vld4qU8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Uint8x16x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2U16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x2T = transmuteWithLayouts<UShortArray, Uint16x4x2T>(vals, PrimitiveArrayLayouts.uShortArray((8).toInt()), UnresolvedTypeLayout<Uint16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst2U16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x2T = vld2U16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3U16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x3T = transmuteWithLayouts<UShortArray, Uint16x4x3T>(vals, PrimitiveArrayLayouts.uShortArray((12).toInt()), UnresolvedTypeLayout<Uint16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst3U16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x3T = vld3U16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4U16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x4T = transmuteWithLayouts<UShortArray, Uint16x4x4T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst4U16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x4x4T = vld4U16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x4x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x2T = transmuteWithLayouts<UShortArray, Uint16x8x2T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst2qU16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x2T = vld2qU16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x3T = transmuteWithLayouts<UShortArray, Uint16x8x3T>(vals, PrimitiveArrayLayouts.uShortArray((24).toInt()), UnresolvedTypeLayout<Uint16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst3qU16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x3T = vld3qU16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x4T = transmuteWithLayouts<UShortArray, Uint16x8x4T>(vals, PrimitiveArrayLayouts.uShortArray((32).toInt()), UnresolvedTypeLayout<Uint16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst4qU16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint16x8x4T = vld4qU16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Uint16x8x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2U32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x2T = transmuteWithLayouts<UIntArray, Uint32x2x2T>(vals, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<Uint32x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst2U32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x2T = vld2U32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3U32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x3T = transmuteWithLayouts<UIntArray, Uint32x2x3T>(vals, PrimitiveArrayLayouts.uIntArray((6).toInt()), UnresolvedTypeLayout<Uint32x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst3U32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x3T = vld3U32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4U32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x4T = transmuteWithLayouts<UIntArray, Uint32x2x4T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst4U32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x2x4T = vld4U32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x2x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x2T = transmuteWithLayouts<UIntArray, Uint32x4x2T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst2qU32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x2T = vld2qU32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x3T = transmuteWithLayouts<UIntArray, Uint32x4x3T>(vals, PrimitiveArrayLayouts.uIntArray((12).toInt()), UnresolvedTypeLayout<Uint32x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst3qU32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x3T = vld3qU32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x4T = transmuteWithLayouts<UIntArray, Uint32x4x4T>(vals, PrimitiveArrayLayouts.uIntArray((16).toInt()), UnresolvedTypeLayout<Uint32x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UIntArray>())
    vst4qU32(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint32x4x4T = vld4qU32(tmp1.asPtr().cast())
    val out: UIntArray = transmuteWithLayouts<Uint32x4x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2U64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x2T = transmuteWithLayouts<ULongArray, Uint64x1x2T>(vals, PrimitiveArrayLayouts.uLongArray((2).toInt()), UnresolvedTypeLayout<Uint64x1x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst2U64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x2T = vld2U64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3U64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x3T = transmuteWithLayouts<ULongArray, Uint64x1x3T>(vals, PrimitiveArrayLayouts.uLongArray((3).toInt()), UnresolvedTypeLayout<Uint64x1x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst3U64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x3T = vld3U64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4U64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x4T = transmuteWithLayouts<ULongArray, Uint64x1x4T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x1x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst4U64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x1x4T = vld4U64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x1x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x2T = transmuteWithLayouts<ULongArray, Uint64x2x2T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x2x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst2qU64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x2T = vld2qU64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x3T = transmuteWithLayouts<ULongArray, Uint64x2x3T>(vals, PrimitiveArrayLayouts.uLongArray((6).toInt()), UnresolvedTypeLayout<Uint64x2x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst3qU64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x3T = vld3qU64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x4T = transmuteWithLayouts<ULongArray, Uint64x2x4T>(vals, PrimitiveArrayLayouts.uLongArray((8).toInt()), UnresolvedTypeLayout<Uint64x2x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<ULongArray>())
    vst4qU64(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Uint64x2x4T = vld4qU64(tmp1.asPtr().cast())
    val out: ULongArray = transmuteWithLayouts<Uint64x2x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2P8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x2T = transmuteWithLayouts<UByteArray, Poly8x8x2T>(vals, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Poly8x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst2P8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x2T = vld2P8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x2T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3P8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x3T = transmuteWithLayouts<UByteArray, Poly8x8x3T>(vals, PrimitiveArrayLayouts.uByteArray((24).toInt()), UnresolvedTypeLayout<Poly8x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst3P8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x3T = vld3P8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x3T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4P8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x8x4T = transmuteWithLayouts<UByteArray, Poly8x8x4T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Poly8x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst4P8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x8x4T = vld4P8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x8x4T, UByteArray>(r, UnresolvedTypeLayout<Poly8x8x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qP8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x2T = transmuteWithLayouts<UByteArray, Poly8x16x2T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Poly8x16x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst2qP8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x2T = vld2qP8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x2T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qP8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x3T = transmuteWithLayouts<UByteArray, Poly8x16x3T>(vals, PrimitiveArrayLayouts.uByteArray((48).toInt()), UnresolvedTypeLayout<Poly8x16x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst3qP8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x3T = vld3qP8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x3T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qP8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Poly8x16x4T = transmuteWithLayouts<UByteArray, Poly8x16x4T>(vals, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Poly8x16x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UByteArray>())
    vst4qP8(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly8x16x4T = vld4qP8(tmp1.asPtr().cast())
    val out: UByteArray = transmuteWithLayouts<Poly8x16x4T, UByteArray>(r, UnresolvedTypeLayout<Poly8x16x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2P16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x2T = transmuteWithLayouts<UShortArray, Poly16x4x2T>(vals, PrimitiveArrayLayouts.uShortArray((8).toInt()), UnresolvedTypeLayout<Poly16x4x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst2P16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x2T = vld2P16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x2T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3P16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x3T = transmuteWithLayouts<UShortArray, Poly16x4x3T>(vals, PrimitiveArrayLayouts.uShortArray((12).toInt()), UnresolvedTypeLayout<Poly16x4x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst3P16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x3T = vld3P16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x3T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4P16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x4x4T = transmuteWithLayouts<UShortArray, Poly16x4x4T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Poly16x4x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst4P16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x4x4T = vld4P16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x4x4T, UShortArray>(r, UnresolvedTypeLayout<Poly16x4x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qP16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x2T = transmuteWithLayouts<UShortArray, Poly16x8x2T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Poly16x8x2T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst2qP16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x2T = vld2qP16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x2T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qP16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x3T = transmuteWithLayouts<UShortArray, Poly16x8x3T>(vals, PrimitiveArrayLayouts.uShortArray((24).toInt()), UnresolvedTypeLayout<Poly16x8x3T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst3qP16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x3T = vld3qP16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x3T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qP16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Poly16x8x4T = transmuteWithLayouts<UShortArray, Poly16x8x4T>(vals, PrimitiveArrayLayouts.uShortArray((32).toInt()), UnresolvedTypeLayout<Poly16x8x4T>())
    val tmp = MaybeUninit.uninit(UnresolvedTypeLayout<UShortArray>())
    vst4qP16(tmp.asMutPtr().cast(), a)
    val tmp1 = tmp.assumeInit()
    val r: Poly16x8x4T = vld4qP16(tmp1.asPtr().cast())
    val out: UShortArray = transmuteWithLayouts<Poly16x8x4T, UShortArray>(r, UnresolvedTypeLayout<Poly16x8x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x2T = transmuteWithLayouts<ByteArray, Int8x16x2T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x16x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst2qLaneS8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Int8x16x2T = vld2qLaneS8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: ByteArray = transmuteWithLayouts<Int8x16x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x3T = transmuteWithLayouts<ByteArray, Int8x16x3T>(vals, PrimitiveArrayLayouts.byteArray((48).toInt()), UnresolvedTypeLayout<Int8x16x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst3qLaneS8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Int8x16x3T = vld3qLaneS8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: ByteArray = transmuteWithLayouts<Int8x16x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x16x4T = transmuteWithLayouts<ByteArray, Int8x16x4T>(vals, PrimitiveArrayLayouts.byteArray((64).toInt()), UnresolvedTypeLayout<Int8x16x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst4qLaneS8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Int8x16x4T = vld4qLaneS8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: ByteArray = transmuteWithLayouts<Int8x16x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x16x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x2T = transmuteWithLayouts<UByteArray, Uint8x16x2T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x16x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst2qLaneU8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Uint8x16x2T = vld2qLaneU8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: UByteArray = transmuteWithLayouts<Uint8x16x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x3T = transmuteWithLayouts<UByteArray, Uint8x16x3T>(vals, PrimitiveArrayLayouts.uByteArray((48).toInt()), UnresolvedTypeLayout<Uint8x16x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst3qLaneU8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Uint8x16x3T = vld3qLaneU8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: UByteArray = transmuteWithLayouts<Uint8x16x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x16x4T = transmuteWithLayouts<UByteArray, Uint8x16x4T>(vals, PrimitiveArrayLayouts.uByteArray((64).toInt()), UnresolvedTypeLayout<Uint8x16x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst4qLaneU8(localStorageL1C0.elementMutPtr(0), a, 15)
    val r: Uint8x16x4T = vld4qLaneU8(localStorageL1C0.elementConstPtr(0), a, 15)
    val out: UByteArray = transmuteWithLayouts<Uint8x16x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x16x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x2T = transmuteWithLayouts<LongArray, Int64x1x2T>(vals, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<Int64x1x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst2LaneS64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Int64x1x2T = vld2LaneS64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: LongArray = transmuteWithLayouts<Int64x1x2T, LongArray>(r, UnresolvedTypeLayout<Int64x1x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x3T = transmuteWithLayouts<LongArray, Int64x1x3T>(vals, PrimitiveArrayLayouts.longArray((3).toInt()), UnresolvedTypeLayout<Int64x1x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst3LaneS64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Int64x1x3T = vld3LaneS64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: LongArray = transmuteWithLayouts<Int64x1x3T, LongArray>(r, UnresolvedTypeLayout<Int64x1x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x1x4T = transmuteWithLayouts<LongArray, Int64x1x4T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x1x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst4LaneS64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Int64x1x4T = vld4LaneS64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: LongArray = transmuteWithLayouts<Int64x1x4T, LongArray>(r, UnresolvedTypeLayout<Int64x1x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x2T = transmuteWithLayouts<LongArray, Int64x2x2T>(vals, PrimitiveArrayLayouts.longArray((4).toInt()), UnresolvedTypeLayout<Int64x2x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst2qLaneS64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int64x2x2T = vld2qLaneS64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: LongArray = transmuteWithLayouts<Int64x2x2T, LongArray>(r, UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x3T = transmuteWithLayouts<LongArray, Int64x2x3T>(vals, PrimitiveArrayLayouts.longArray((6).toInt()), UnresolvedTypeLayout<Int64x2x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst3qLaneS64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int64x2x3T = vld3qLaneS64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: LongArray = transmuteWithLayouts<Int64x2x3T, LongArray>(r, UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneS64() {
    val vals: LongArray = fromFn({ i -> i.toLong() })
    val a: Int64x2x4T = transmuteWithLayouts<LongArray, Int64x2x4T>(vals, PrimitiveArrayLayouts.longArray((8).toInt()), UnresolvedTypeLayout<Int64x2x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromLongArray(LongArray(4) { 0.toLong() })
    vst4qLaneS64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int64x2x4T = vld4qLaneS64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: LongArray = transmuteWithLayouts<Int64x2x4T, LongArray>(r, UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<LongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x2T = transmuteWithLayouts<ULongArray, Uint64x1x2T>(vals, PrimitiveArrayLayouts.uLongArray((2).toInt()), UnresolvedTypeLayout<Uint64x1x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst2LaneU64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Uint64x1x2T = vld2LaneU64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: ULongArray = transmuteWithLayouts<Uint64x1x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x3T = transmuteWithLayouts<ULongArray, Uint64x1x3T>(vals, PrimitiveArrayLayouts.uLongArray((3).toInt()), UnresolvedTypeLayout<Uint64x1x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst3LaneU64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Uint64x1x3T = vld3LaneU64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: ULongArray = transmuteWithLayouts<Uint64x1x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x1x4T = transmuteWithLayouts<ULongArray, Uint64x1x4T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x1x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst4LaneU64(localStorageL1C0.elementMutPtr(0), a, 0)
    val r: Uint64x1x4T = vld4LaneU64(localStorageL1C0.elementConstPtr(0), a, 0)
    val out: ULongArray = transmuteWithLayouts<Uint64x1x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x1x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x2T = transmuteWithLayouts<ULongArray, Uint64x2x2T>(vals, PrimitiveArrayLayouts.uLongArray((4).toInt()), UnresolvedTypeLayout<Uint64x2x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst2qLaneU64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint64x2x2T = vld2qLaneU64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: ULongArray = transmuteWithLayouts<Uint64x2x2T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x3T = transmuteWithLayouts<ULongArray, Uint64x2x3T>(vals, PrimitiveArrayLayouts.uLongArray((6).toInt()), UnresolvedTypeLayout<Uint64x2x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst3qLaneU64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint64x2x3T = vld3qLaneU64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: ULongArray = transmuteWithLayouts<Uint64x2x3T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneU64() {
    val vals: ULongArray = fromFn({ i -> i.toULong() })
    val a: Uint64x2x4T = transmuteWithLayouts<ULongArray, Uint64x2x4T>(vals, PrimitiveArrayLayouts.uLongArray((8).toInt()), UnresolvedTypeLayout<Uint64x2x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromULongArray(ULongArray(4) { 0.toULong() })
    vst4qLaneU64(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint64x2x4T = vld4qLaneU64(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: ULongArray = transmuteWithLayouts<Uint64x2x4T, ULongArray>(r, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<ULongArray>())
    check((out).contentEquals(vals))
}
