// port-lint: source aarch64/neon/generated.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI64
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Float32x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int32x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Int8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.P128
import io.github.kotlinmania.rustlang.corearch.armshared.neon.P16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.P64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.P8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Poly8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint16x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint32x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x1x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint64x2x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x16x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x2T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x3T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.Uint8x8x4T
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vabdU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vceqF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vceqF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgeF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgeF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgezF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgtF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgtF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcgtzF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcleF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcleF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vclezF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcltF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcltF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcltzF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineP64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcombineU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcvtF16F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vcvtF32F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupLaneqU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupNU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqLaneqU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vdupqNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmaF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmaF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmaqF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmaqF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmsF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmsF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmsqF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vfmsqF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetHighU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetLaneU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneF16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneP64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vgetqLaneU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlalU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmlslU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmovlU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vmullU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqabsS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqabsS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddqS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqaddqS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmulhS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmulhS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmulhqS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmulhqS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmullS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqdmullS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovnU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovunS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovunS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqmovunS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqnegS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqnegS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqnegS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrdmulhS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrdmulhS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshlS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshlS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshlU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshlU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrnNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrunNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrunNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqrshrunNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlNU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshlU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshluNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshluNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshluNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshluNS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrnNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrunNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrunNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqshrunNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubqS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vqsubqS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretF16U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretF32U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretU32F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretU64F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretU8S8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretqF16U32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretqF32U64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretqU32F16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vreinterpretqU64F32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrshrnNU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vrsubhnU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetLaneU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneF32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneP16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneP8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vsetqLaneU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshlS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshlU64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNS8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshllNU8
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNS16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNS32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNS64
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNU16
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNU32
import io.github.kotlinmania.rustlang.corearch.armshared.neon.vshrnNU64
import io.github.kotlinmania.rustlang.corearch.deinterleaveMask
import io.github.kotlinmania.rustlang.corearch.even
import io.github.kotlinmania.rustlang.corearch.identity
import io.github.kotlinmania.rustlang.corearch.interleaveMask
import io.github.kotlinmania.rustlang.corearch.odd
import io.github.kotlinmania.rustlang.corearch.simd.F16x4
import io.github.kotlinmania.rustlang.corearch.simd.F16x8
import io.github.kotlinmania.rustlang.corearch.simd.F32x2
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x1
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x1
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x8
import kotlin.experimental.inv

public typealias V = Simd<Double>

public typealias Arr = DoubleArray

public typealias W = Simd<Double>

/**
 * CRC32-C single round checksum for quad words (64 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32cd)
 */
public fun crc32cd(crc: UInt, data: ULong): UInt {
    fun externCrc32cd(crc1: UInt, data1: ULong): UInt = 0u
    return externCrc32cd(crc, data)
}

/**
 * CRC32 single round checksum for quad words (64 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32d)
 */
public fun crc32d(crc: UInt, data: ULong): UInt {
    fun externCrc32d(crc1: UInt, data1: ULong): UInt = 0u
    return externCrc32d(crc, data)
}

/**
 * Floating-point JavaScript convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__jcvt)
 */
public fun jcvt(a: Double): Int {
    fun externJcvt(a1: Double): Int = 0
    return externJcvt(a)
}

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighS8)
 */
public fun vabalHighS8(a: Int16x8T, b: Int8x16T, c: Int8x16T): Int16x8T {
    val d = vgetHighS8(b)
    val e = vgetHighS8(c)
    val f = vabdS8(d, e)
    return run {
        val f1: Uint8x8T = run { val simdArg0 = f; Uint8x8T() }
        run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f1; Int16x8T() }; simdArg0 }
    }
}

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighS16)
 */
public fun vabalHighS16(a: Int32x4T, b: Int16x8T, c: Int16x8T): Int32x4T {
    val d = vgetHighS16(b)
    val e = vgetHighS16(c)
    val f = vabdS16(d, e)
    return run {
        val f1: Uint16x4T = run { val simdArg0 = f; Uint16x4T() }
        run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f1; Int32x4T() }; simdArg0 }
    }
}

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighS32)
 */
public fun vabalHighS32(a: Int64x2T, b: Int32x4T, c: Int32x4T): Int64x2T {
    val d = vgetHighS32(b)
    val e = vgetHighS32(c)
    val f = vabdS32(d, e)
    return run {
        val f1: Uint32x2T = run { val simdArg0 = f; Uint32x2T() }
        run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f1; Int64x2T() }; simdArg0 }
    }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighU8)
 */
public fun vabalHighU8(a: Uint16x8T, b: Uint8x16T, c: Uint8x16T): Uint16x8T {
    val d = vgetHighU8(b)
    val e = vgetHighU8(c)
    val f = vabdU8(d, e)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f; Uint16x8T() }; simdArg0 }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighU16)
 */
public fun vabalHighU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x8T): Uint32x4T {
    val d = vgetHighU16(b)
    val e = vgetHighU16(c)
    val f = vabdU16(d, e)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f; Uint32x4T() }; simdArg0 }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalHighU32)
 */
public fun vabalHighU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x4T): Uint64x2T {
    val d = vgetHighU32(b)
    val e = vgetHighU32(c)
    val f = vabdU32(d, e)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = f; Uint64x2T() }; simdArg0 }
}

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdF64)
 */
public fun vabdF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVabdF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVabdF64(a, b)
}

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqF64)
 */
public fun vabdqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVabdqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVabdqF64(a, b)
}

/**
 * Floating-point absolute difference
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabddF64)
 */
public fun vabddF64(a: Double, b: Double): Double = vgetLaneF64(vabdF64(vdupNF64(a), vdupNF64(b)), 0.0)

/**
 * Floating-point absolute difference
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdsF32)
 */
public fun vabdsF32(a: Float, b: Float): Float = vgetLaneF32(vabdF32(vdupNF32(a), vdupNF32(b)), 0.0f)

/**
 * Floating-point absolute difference
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdhF16)
 */
public fun vabdhF16(a: F16, b: F16): F16 = vgetLaneF16(vabdF16(vdupNF16(a), vdupNF16(b)), F16.fromFloat(0.0f))

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighS8)
 */
public fun vabdlHighS8(a: Int8x16T, b: Int8x16T): Int16x8T {
    val c = vgetHighS8(a)
    val d = vgetHighS8(b)
    return run {
        val e: Uint8x8T = run { val simdArg0 = vabdS8(c, d); Uint8x8T() }
        run { val simdArg0 = e; Int16x8T() }
    }
}

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighS16)
 */
public fun vabdlHighS16(a: Int16x8T, b: Int16x8T): Int32x4T {
    val c = vgetHighS16(a)
    val d = vgetHighS16(b)
    return run {
        val e: Uint16x4T = run { val simdArg0 = vabdS16(c, d); Uint16x4T() }
        run { val simdArg0 = e; Int32x4T() }
    }
}

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighS32)
 */
public fun vabdlHighS32(a: Int32x4T, b: Int32x4T): Int64x2T {
    val c = vgetHighS32(a)
    val d = vgetHighS32(b)
    return run {
        val e: Uint32x2T = run { val simdArg0 = vabdS32(c, d); Uint32x2T() }
        run { val simdArg0 = e; Int64x2T() }
    }
}

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighU8)
 */
public fun vabdlHighU8(a: Uint8x16T, b: Uint8x16T): Uint16x8T {
    val c = vgetHighU8(a)
    val d = vgetHighU8(b)
    return run { val simdArg0 = vabdU8(c, d); Uint16x8T() }
}

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighU16)
 */
public fun vabdlHighU16(a: Uint16x8T, b: Uint16x8T): Uint32x4T {
    val c = vgetHighU16(a)
    val d = vgetHighU16(b)
    return run { val simdArg0 = vabdU16(c, d); Uint32x4T() }
}

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlHighU32)
 */
public fun vabdlHighU32(a: Uint32x4T, b: Uint32x4T): Uint64x2T {
    val c = vgetHighU32(a)
    val d = vgetHighU32(b)
    return run { val simdArg0 = vabdU32(c, d); Uint64x2T() }
}

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsF64)
 */
public fun vabsF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqF64)
 */
public fun vabsqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Absolute Value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsS64)
 */
public fun vabsS64(a: Int64x1T): Int64x1T =
    run {
        val neg: Int64x1T = run { val simdArg0 = a; simdArg0 }
        val mask: Int64x1T = run { val simdArg0 = a; val simdArg1 = neg; Int64x1T() }
        run { val simdArg0 = mask; val simdArg1 = a; val simdArg2 = neg; simdArg1 }
    }

/**
 * Absolute Value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqS64)
 */
public fun vabsqS64(a: Int64x2T): Int64x2T =
    run {
        val neg: Int64x2T = run { val simdArg0 = a; simdArg0 }
        val mask: Int64x2T = run { val simdArg0 = a; val simdArg1 = neg; Int64x2T() }
        run { val simdArg0 = mask; val simdArg1 = a; val simdArg2 = neg; simdArg1 }
    }

/**
 * Absolute Value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsdS64)
 */
public fun vabsdS64(a: Long): Long {
    fun externVabsdS64(a1: Long): Long = 0L
    return externVabsdS64(a)
}

/**
 * Add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vadddS64)
 */
public fun vadddS64(a: Long, b: Long): Long = (a + (b.toLong()))

/**
 * Add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vadddU64)
 */
public fun vadddU64(a: ULong, b: ULong): ULong = (a + (b.toULong()))

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvS16)
 */
public fun vaddlvS16(a: Int16x4T): Int {
    fun externVaddlvS16(a1: Int16x4T): Int = 0
    return externVaddlvS16(a)
}

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqS16)
 */
public fun vaddlvqS16(a: Int16x8T): Int {
    fun externVaddlvqS16(a1: Int16x8T): Int = 0
    return externVaddlvqS16(a)
}

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqS32)
 */
public fun vaddlvqS32(a: Int32x4T): Long {
    fun externVaddlvqS32(a1: Int32x4T): Long = 0L
    return externVaddlvqS32(a)
}

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvS32)
 */
public fun vaddlvS32(a: Int32x2T): Long {
    fun externVaddlvS32(a1: Int32x2T): Long = 0L
    return externVaddlvS32(a)
}

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvS8)
 */
public fun vaddlvS8(a: Int8x8T): Short {
    fun externVaddlvS8(a1: Int8x8T): Int = 0
    return externVaddlvS8(a).toShort()
}

/**
 * Signed Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqS8)
 */
public fun vaddlvqS8(a: Int8x16T): Short {
    fun externVaddlvqS8(a1: Int8x16T): Int = 0
    return externVaddlvqS8(a).toShort()
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvU16)
 */
public fun vaddlvU16(a: Uint16x4T): UInt {
    fun externVaddlvU16(a1: Uint16x4T): UInt = 0u
    return externVaddlvU16(a)
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqU16)
 */
public fun vaddlvqU16(a: Uint16x8T): UInt {
    fun externVaddlvqU16(a1: Uint16x8T): UInt = 0u
    return externVaddlvqU16(a)
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqU32)
 */
public fun vaddlvqU32(a: Uint32x4T): ULong {
    fun externVaddlvqU32(a1: Uint32x4T): ULong = 0uL
    return externVaddlvqU32(a)
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvU32)
 */
public fun vaddlvU32(a: Uint32x2T): ULong {
    fun externVaddlvU32(a1: Uint32x2T): ULong = 0uL
    return externVaddlvU32(a)
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvU8)
 */
public fun vaddlvU8(a: Uint8x8T): UShort {
    fun externVaddlvU8(a1: Uint8x8T): Int = 0
    return externVaddlvU8(a).toUShort()
}

/**
 * Unsigned Add Long across Vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlvqU8)
 */
public fun vaddlvqU8(a: Uint8x16T): UShort {
    fun externVaddlvqU8(a1: Uint8x16T): Int = 0
    return externVaddlvqU8(a).toUShort()
}

/**
 * Floating-point add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvF32)
 */
public fun vaddvF32(a: Float32x2T): Float {
    fun externVaddvF32(a1: Float32x2T): Float = 0.0f
    return externVaddvF32(a)
}

/**
 * Floating-point add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqF32)
 */
public fun vaddvqF32(a: Float32x4T): Float {
    fun externVaddvqF32(a1: Float32x4T): Float = 0.0f
    return externVaddvqF32(a)
}

/**
 * Floating-point add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqF64)
 */
public fun vaddvqF64(a: Float64x2T): Double {
    fun externVaddvqF64(a1: Float64x2T): Double = 0.0
    return externVaddvqF64(a)
}

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvS32)
 */
public fun vaddvS32(a: Int32x2T): Int = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvS8)
 */
public fun vaddvS8(a: Int8x8T): Byte = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqS8)
 */
public fun vaddvqS8(a: Int8x16T): Byte = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvS16)
 */
public fun vaddvS16(a: Int16x4T): Short = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqS16)
 */
public fun vaddvqS16(a: Int16x8T): Short = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqS32)
 */
public fun vaddvqS32(a: Int32x4T): Int = run { val simdArg0 = a; val simdArg1 = 0; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvU32)
 */
public fun vaddvU32(a: Uint32x2T): UInt = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvU8)
 */
public fun vaddvU8(a: Uint8x8T): UByte = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqU8)
 */
public fun vaddvqU8(a: Uint8x16T): UByte = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvU16)
 */
public fun vaddvU16(a: Uint16x4T): UShort = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqU16)
 */
public fun vaddvqU16(a: Uint16x8T): UShort = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqU32)
 */
public fun vaddvqU32(a: Uint32x4T): UInt = run { val simdArg0 = a; val simdArg1 = 0u; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqS64)
 */
public fun vaddvqS64(a: Int64x2T): Long = run { val simdArg0 = a; val simdArg1 = 0L; simdArg1 }

/**
 * Add across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddvqU64)
 */
public fun vaddvqU64(a: Uint64x2T): ULong = run { val simdArg0 = a; val simdArg1 = 0uL; simdArg1 }

/**
 * Multi-vector floating-point absolute maximum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vamaxF16)
 */
public fun vamaxF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVamaxF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVamaxF16(a, b)
}

/**
 * Multi-vector floating-point absolute maximum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vamaxqF16)
 */
public fun vamaxqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVamaxqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVamaxqF16(a, b)
}

/**
 * Multi-vector floating-point absolute maximum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vamaxF32)
 */
public fun vamaxF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVamaxF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVamaxF32(a, b)
}

/**
 * Multi-vector floating-point absolute maximum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vamaxqF32)
 */
public fun vamaxqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVamaxqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVamaxqF32(a, b)
}

/**
 * Multi-vector floating-point absolute maximum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vamaxqF64)
 */
public fun vamaxqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVamaxqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVamaxqF64(a, b)
}

/**
 * Multi-vector floating-point absolute minimum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaminF16)
 */
public fun vaminF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVaminF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVaminF16(a, b)
}

/**
 * Multi-vector floating-point absolute minimum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaminqF16)
 */
public fun vaminqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVaminqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVaminqF16(a, b)
}

/**
 * Multi-vector floating-point absolute minimum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaminF32)
 */
public fun vaminF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVaminF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVaminF32(a, b)
}

/**
 * Multi-vector floating-point absolute minimum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaminqF32)
 */
public fun vaminqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVaminqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVaminqF32(a, b)
}

/**
 * Multi-vector floating-point absolute minimum
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaminqF64)
 */
public fun vaminqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVaminqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVaminqF64(a, b)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqS8)
 */
public fun vbcaxqS8(a: Int8x16T, b: Int8x16T, c: Int8x16T): Int8x16T {
    fun externVbcaxqS8(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T): Int8x16T = Int8x16T()
    return externVbcaxqS8(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqS16)
 */
public fun vbcaxqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T {
    fun externVbcaxqS16(a1: Int16x8T, b1: Int16x8T, c1: Int16x8T): Int16x8T = Int16x8T()
    return externVbcaxqS16(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqS32)
 */
public fun vbcaxqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T {
    fun externVbcaxqS32(a1: Int32x4T, b1: Int32x4T, c1: Int32x4T): Int32x4T = Int32x4T()
    return externVbcaxqS32(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqS64)
 */
public fun vbcaxqS64(a: Int64x2T, b: Int64x2T, c: Int64x2T): Int64x2T {
    fun externVbcaxqS64(a1: Int64x2T, b1: Int64x2T, c1: Int64x2T): Int64x2T = Int64x2T()
    return externVbcaxqS64(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqU8)
 */
public fun vbcaxqU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T {
    fun externVbcaxqU8(a1: Uint8x16T, b1: Uint8x16T, c1: Uint8x16T): Uint8x16T = Uint8x16T()
    return externVbcaxqU8(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqU16)
 */
public fun vbcaxqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T {
    fun externVbcaxqU16(a1: Uint16x8T, b1: Uint16x8T, c1: Uint16x8T): Uint16x8T = Uint16x8T()
    return externVbcaxqU16(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqU32)
 */
public fun vbcaxqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    fun externVbcaxqU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVbcaxqU32(a, b, c)
}

/**
 * Bit clear and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbcaxqU64)
 */
public fun vbcaxqU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    fun externVbcaxqU64(a1: Uint64x2T, b1: Uint64x2T, c1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVbcaxqU64(a, b, c)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddRot270F16)
 */
public fun vcaddRot270F16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVcaddRot270F16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVcaddRot270F16(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot270F16)
 */
public fun vcaddqRot270F16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVcaddqRot270F16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVcaddqRot270F16(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddRot270F32)
 */
public fun vcaddRot270F32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVcaddRot270F32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVcaddRot270F32(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot270F32)
 */
public fun vcaddqRot270F32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVcaddqRot270F32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVcaddqRot270F32(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot270F64)
 */
public fun vcaddqRot270F64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVcaddqRot270F64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVcaddqRot270F64(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddRot90F16)
 */
public fun vcaddRot90F16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVcaddRot90F16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVcaddRot90F16(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot90F16)
 */
public fun vcaddqRot90F16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVcaddqRot90F16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVcaddqRot90F16(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddRot90F32)
 */
public fun vcaddRot90F32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVcaddRot90F32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVcaddRot90F32(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot90F32)
 */
public fun vcaddqRot90F32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVcaddqRot90F32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVcaddqRot90F32(a, b)
}

/**
 * Floating-point complex add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaddqRot90F64)
 */
public fun vcaddqRot90F64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVcaddqRot90F64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVcaddqRot90F64(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageF64)
 */
public fun vcageF64(a: Float64x1T, b: Float64x1T): Uint64x1T {
    fun externVcageF64(a1: Float64x1T, b1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcageF64(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageqF64)
 */
public fun vcageqF64(a: Float64x2T, b: Float64x2T): Uint64x2T {
    fun externVcageqF64(a1: Float64x2T, b1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcageqF64(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagedF64)
 */
public fun vcagedF64(a: Double, b: Double): ULong {
    fun externVcagedF64(a1: Double, b1: Double): ULong = 0uL
    return externVcagedF64(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagesF32)
 */
public fun vcagesF32(a: Float, b: Float): UInt {
    fun externVcagesF32(a1: Float, b1: Float): UInt = 0u
    return externVcagesF32(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagehF16)
 */
public fun vcagehF16(a: F16, b: F16): UShort {
    fun externVcagehF16(a1: F16, b1: F16): Int = 0
    return externVcagehF16(a, b).toUShort()
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtF64)
 */
public fun vcagtF64(a: Float64x1T, b: Float64x1T): Uint64x1T {
    fun externVcagtF64(a1: Float64x1T, b1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcagtF64(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtqF64)
 */
public fun vcagtqF64(a: Float64x2T, b: Float64x2T): Uint64x2T {
    fun externVcagtqF64(a1: Float64x2T, b1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcagtqF64(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtdF64)
 */
public fun vcagtdF64(a: Double, b: Double): ULong {
    fun externVcagtdF64(a1: Double, b1: Double): ULong = 0uL
    return externVcagtdF64(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtsF32)
 */
public fun vcagtsF32(a: Float, b: Float): UInt {
    fun externVcagtsF32(a1: Float, b1: Float): UInt = 0u
    return externVcagtsF32(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagthF16)
 */
public fun vcagthF16(a: F16, b: F16): UShort {
    fun externVcagthF16(a1: F16, b1: F16): Int = 0
    return externVcagthF16(a, b).toUShort()
}

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleF64)
 */
public fun vcaleF64(a: Float64x1T, b: Float64x1T): Uint64x1T = vcageF64(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleqF64)
 */
public fun vcaleqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = vcageqF64(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaledF64)
 */
public fun vcaledF64(a: Double, b: Double): ULong = vcagedF64(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcalesF32)
 */
public fun vcalesF32(a: Float, b: Float): UInt = vcagesF32(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcalehF16)
 */
public fun vcalehF16(a: F16, b: F16): UShort = vcagehF16(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltF64)
 */
public fun vcaltF64(a: Float64x1T, b: Float64x1T): Uint64x1T = vcagtF64(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltqF64)
 */
public fun vcaltqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = vcagtqF64(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltdF64)
 */
public fun vcaltdF64(a: Double, b: Double): ULong = vcagtdF64(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltsF32)
 */
public fun vcaltsF32(a: Float, b: Float): UInt = vcagtsF32(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcalthF16)
 */
public fun vcalthF16(a: F16, b: F16): UShort = vcagthF16(b, a)

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqF64)
 */
public fun vceqF64(a: Float64x1T, b: Float64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqF64)
 */
public fun vceqqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqS64)
 */
public fun vceqS64(a: Int64x1T, b: Int64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqS64)
 */
public fun vceqqS64(a: Int64x2T, b: Int64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqU64)
 */
public fun vceqU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqU64)
 */
public fun vceqqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqP64)
 */
public fun vceqP64(a: Poly64x1T, b: Poly64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqP64)
 */
public fun vceqqP64(a: Poly64x2T, b: Poly64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqdF64)
 */
public fun vceqdF64(a: Double, b: Double): ULong = vgetLaneU64(vceqF64(vdupNF64(a), vdupNF64(b)), 0uL)

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqsF32)
 */
public fun vceqsF32(a: Float, b: Float): UInt = vgetLaneU32(vceqF32(vdupNF32(a), vdupNF32(b)), 0u)

/**
 * Compare bitwise equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqdS64)
 */
public fun vceqdS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vceqS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare bitwise equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqdU64)
 */
public fun vceqdU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vceqU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqhF16)
 */
public fun vceqhF16(a: F16, b: F16): UShort = vgetLaneU16(vceqF16(vdupNF16(a), vdupNF16(b)), 0u)

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzF16)
 */
public fun vceqzF16(a: Float16x4T): Uint16x4T {
    val b: F16x4 = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f)))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F16x4, Float16x4T>(b, UnresolvedTypeLayout<F16x4>(), UnresolvedTypeLayout<Float16x4T>()); Uint16x4T() }
}

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqF16)
 */
public fun vceqzqF16(a: Float16x8T): Uint16x8T {
    val b: F16x8 = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f)))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F16x8, Float16x8T>(b, UnresolvedTypeLayout<F16x8>(), UnresolvedTypeLayout<Float16x8T>()); Uint16x8T() }
}

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzF32)
 */
public fun vceqzF32(a: Float32x2T): Uint32x2T {
    val b: F32x2 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x2, Float32x2T>(b, UnresolvedTypeLayout<F32x2>(), UnresolvedTypeLayout<Float32x2T>()); Uint32x2T() }
}

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqF32)
 */
public fun vceqzqF32(a: Float32x4T): Uint32x4T {
    val b: F32x4 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x4, Float32x4T>(b, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<Float32x4T>()); Uint32x4T() }
}

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzF64)
 */
public fun vceqzF64(a: Float64x1T): Uint64x1T {
    val b: Double = 0.0
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(b, PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); Uint64x1T() }
}

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqF64)
 */
public fun vceqzqF64(a: Float64x2T): Uint64x2T {
    val b: F64x2 = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F64x2, Float64x2T>(b, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<Float64x2T>()); Uint64x2T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzS8)
 */
public fun vceqzS8(a: Int8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Int8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Int8x8T>()); Uint8x8T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqS8)
 */
public fun vceqzqS8(a: Int8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Int8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Int8x16T>()); Uint8x16T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzS16)
 */
public fun vceqzS16(a: Int16x4T): Uint16x4T {
    val b: I16x4 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x4, Int16x4T>(b, UnresolvedTypeLayout<I16x4>(), UnresolvedTypeLayout<Int16x4T>()); Uint16x4T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqS16)
 */
public fun vceqzqS16(a: Int16x8T): Uint16x8T {
    val b: I16x8 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x8, Int16x8T>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<Int16x8T>()); Uint16x8T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzS32)
 */
public fun vceqzS32(a: Int32x2T): Uint32x2T {
    val b: I32x2 = Simd<Int>(listOf(0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x2, Int32x2T>(b, UnresolvedTypeLayout<I32x2>(), UnresolvedTypeLayout<Int32x2T>()); Uint32x2T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqS32)
 */
public fun vceqzqS32(a: Int32x4T): Uint32x4T {
    val b: I32x4 = Simd<Int>(listOf(0, 0, 0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x4, Int32x4T>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<Int32x4T>()); Uint32x4T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzS64)
 */
public fun vceqzS64(a: Int64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqS64)
 */
public fun vceqzqS64(a: Int64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzP8)
 */
public fun vceqzP8(a: Poly8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Poly8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Poly8x8T>()); Uint8x8T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqP8)
 */
public fun vceqzqP8(a: Poly8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Poly8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Poly8x16T>()); Uint8x16T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzP64)
 */
public fun vceqzP64(a: Poly64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Poly64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Poly64x1T>()); Uint64x1T() }
}

/**
 * Signed compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqP64)
 */
public fun vceqzqP64(a: Poly64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Poly64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Poly64x2T>()); Uint64x2T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzU8)
 */
public fun vceqzU8(a: Uint8x8T): Uint8x8T {
    val b: U8x8 = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U8x8, Uint8x8T>(b, UnresolvedTypeLayout<U8x8>(), UnresolvedTypeLayout<Uint8x8T>()); Uint8x8T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqU8)
 */
public fun vceqzqU8(a: Uint8x16T): Uint8x16T {
    val b: U8x16 = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U8x16, Uint8x16T>(b, UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<Uint8x16T>()); Uint8x16T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzU16)
 */
public fun vceqzU16(a: Uint16x4T): Uint16x4T {
    val b: U16x4 = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U16x4, Uint16x4T>(b, UnresolvedTypeLayout<U16x4>(), UnresolvedTypeLayout<Uint16x4T>()); Uint16x4T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqU16)
 */
public fun vceqzqU16(a: Uint16x8T): Uint16x8T {
    val b: U16x8 = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U16x8, Uint16x8T>(b, UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<Uint16x8T>()); Uint16x8T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzU32)
 */
public fun vceqzU32(a: Uint32x2T): Uint32x2T {
    val b: U32x2 = Simd<UInt>(listOf(0u, 0u))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U32x2, Uint32x2T>(b, UnresolvedTypeLayout<U32x2>(), UnresolvedTypeLayout<Uint32x2T>()); Uint32x2T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqU32)
 */
public fun vceqzqU32(a: Uint32x4T): Uint32x4T {
    val b: U32x4 = Simd<UInt>(listOf(0u, 0u, 0u, 0u))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U32x4, Uint32x4T>(b, UnresolvedTypeLayout<U32x4>(), UnresolvedTypeLayout<Uint32x4T>()); Uint32x4T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzU64)
 */
public fun vceqzU64(a: Uint64x1T): Uint64x1T {
    val b: U64x1 = Simd<ULong>(listOf(0uL))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U64x1, Uint64x1T>(b, UnresolvedTypeLayout<U64x1>(), UnresolvedTypeLayout<Uint64x1T>()); Uint64x1T() }
}

/**
 * Unsigned compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzqU64)
 */
public fun vceqzqU64(a: Uint64x2T): Uint64x2T {
    val b: U64x2 = Simd<ULong>(listOf(0uL, 0uL))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<U64x2, Uint64x2T>(b, UnresolvedTypeLayout<U64x2>(), UnresolvedTypeLayout<Uint64x2T>()); Uint64x2T() }
}

/**
 * Compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzdS64)
 */
public fun vceqzdS64(a: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vceqzS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzdU64)
 */
public fun vceqzdU64(a: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vceqzU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzhF16)
 */
public fun vceqzhF16(a: F16): UShort = vgetLaneU16(vceqzF16(vdupNF16(a)), 0u)

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzsF32)
 */
public fun vceqzsF32(a: Float): UInt = vgetLaneU32(vceqzF32(vdupNF32(a)), 0u)

/**
 * Floating-point compare bitwise equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqzdF64)
 */
public fun vceqzdF64(a: Double): ULong = vgetLaneU64(vceqzF64(vdupNF64(a)), 0uL)

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeF64)
 */
public fun vcgeF64(a: Float64x1T, b: Float64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqF64)
 */
public fun vcgeqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeS64)
 */
public fun vcgeS64(a: Int64x1T, b: Int64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqS64)
 */
public fun vcgeqS64(a: Int64x2T, b: Int64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeU64)
 */
public fun vcgeU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqU64)
 */
public fun vcgeqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgedF64)
 */
public fun vcgedF64(a: Double, b: Double): ULong = vgetLaneU64(vcgeF64(vdupNF64(a), vdupNF64(b)), 0uL)

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgesF32)
 */
public fun vcgesF32(a: Float, b: Float): UInt = vgetLaneU32(vcgeF32(vdupNF32(a), vdupNF32(b)), 0u)

/**
 * Compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgedS64)
 */
public fun vcgedS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgeS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgedU64)
 */
public fun vcgedU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgeU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgehF16)
 */
public fun vcgehF16(a: F16, b: F16): UShort = vgetLaneU16(vcgeF16(vdupNF16(a), vdupNF16(b)), 0u)

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezF32)
 */
public fun vcgezF32(a: Float32x2T): Uint32x2T {
    val b: F32x2 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x2, Float32x2T>(b, UnresolvedTypeLayout<F32x2>(), UnresolvedTypeLayout<Float32x2T>()); Uint32x2T() }
}

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqF32)
 */
public fun vcgezqF32(a: Float32x4T): Uint32x4T {
    val b: F32x4 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x4, Float32x4T>(b, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<Float32x4T>()); Uint32x4T() }
}

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezF64)
 */
public fun vcgezF64(a: Float64x1T): Uint64x1T {
    val b: Double = 0.0
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(b, PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); Uint64x1T() }
}

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqF64)
 */
public fun vcgezqF64(a: Float64x2T): Uint64x2T {
    val b: F64x2 = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F64x2, Float64x2T>(b, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<Float64x2T>()); Uint64x2T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezS8)
 */
public fun vcgezS8(a: Int8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Int8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Int8x8T>()); Uint8x8T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqS8)
 */
public fun vcgezqS8(a: Int8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Int8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Int8x16T>()); Uint8x16T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezS16)
 */
public fun vcgezS16(a: Int16x4T): Uint16x4T {
    val b: I16x4 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x4, Int16x4T>(b, UnresolvedTypeLayout<I16x4>(), UnresolvedTypeLayout<Int16x4T>()); Uint16x4T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqS16)
 */
public fun vcgezqS16(a: Int16x8T): Uint16x8T {
    val b: I16x8 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x8, Int16x8T>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<Int16x8T>()); Uint16x8T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezS32)
 */
public fun vcgezS32(a: Int32x2T): Uint32x2T {
    val b: I32x2 = Simd<Int>(listOf(0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x2, Int32x2T>(b, UnresolvedTypeLayout<I32x2>(), UnresolvedTypeLayout<Int32x2T>()); Uint32x2T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqS32)
 */
public fun vcgezqS32(a: Int32x4T): Uint32x4T {
    val b: I32x4 = Simd<Int>(listOf(0, 0, 0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x4, Int32x4T>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<Int32x4T>()); Uint32x4T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezS64)
 */
public fun vcgezS64(a: Int64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
}

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqS64)
 */
public fun vcgezqS64(a: Int64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
}

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezdF64)
 */
public fun vcgezdF64(a: Double): ULong = vgetLaneU64(vcgezF64(vdupNF64(a)), 0uL)

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezsF32)
 */
public fun vcgezsF32(a: Float): UInt = vgetLaneU32(vcgezF32(vdupNF32(a)), 0u)

/**
 * Compare signed greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezdS64)
 */
public fun vcgezdS64(a: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgezS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezhF16)
 */
public fun vcgezhF16(a: F16): UShort = vgetLaneU16(vcgezF16(vdupNF16(a)), 0u)

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtF64)
 */
public fun vcgtF64(a: Float64x1T, b: Float64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqF64)
 */
public fun vcgtqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtS64)
 */
public fun vcgtS64(a: Int64x1T, b: Int64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqS64)
 */
public fun vcgtqS64(a: Int64x2T, b: Int64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtU64)
 */
public fun vcgtU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqU64)
 */
public fun vcgtqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtdF64)
 */
public fun vcgtdF64(a: Double, b: Double): ULong = vgetLaneU64(vcgtF64(vdupNF64(a), vdupNF64(b)), 0uL)

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtsF32)
 */
public fun vcgtsF32(a: Float, b: Float): UInt = vgetLaneU32(vcgtF32(vdupNF32(a), vdupNF32(b)), 0u)

/**
 * Compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtdS64)
 */
public fun vcgtdS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgtS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtdU64)
 */
public fun vcgtdU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgtU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgthF16)
 */
public fun vcgthF16(a: F16, b: F16): UShort = vgetLaneU16(vcgtF16(vdupNF16(a), vdupNF16(b)), 0u)

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzF32)
 */
public fun vcgtzF32(a: Float32x2T): Uint32x2T {
    val b: F32x2 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x2, Float32x2T>(b, UnresolvedTypeLayout<F32x2>(), UnresolvedTypeLayout<Float32x2T>()); Uint32x2T() }
}

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqF32)
 */
public fun vcgtzqF32(a: Float32x4T): Uint32x4T {
    val b: F32x4 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x4, Float32x4T>(b, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<Float32x4T>()); Uint32x4T() }
}

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzF64)
 */
public fun vcgtzF64(a: Float64x1T): Uint64x1T {
    val b: Double = 0.0
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(b, PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); Uint64x1T() }
}

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqF64)
 */
public fun vcgtzqF64(a: Float64x2T): Uint64x2T {
    val b: F64x2 = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F64x2, Float64x2T>(b, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<Float64x2T>()); Uint64x2T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzS8)
 */
public fun vcgtzS8(a: Int8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Int8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Int8x8T>()); Uint8x8T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqS8)
 */
public fun vcgtzqS8(a: Int8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Int8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Int8x16T>()); Uint8x16T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzS16)
 */
public fun vcgtzS16(a: Int16x4T): Uint16x4T {
    val b: I16x4 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x4, Int16x4T>(b, UnresolvedTypeLayout<I16x4>(), UnresolvedTypeLayout<Int16x4T>()); Uint16x4T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqS16)
 */
public fun vcgtzqS16(a: Int16x8T): Uint16x8T {
    val b: I16x8 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x8, Int16x8T>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<Int16x8T>()); Uint16x8T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzS32)
 */
public fun vcgtzS32(a: Int32x2T): Uint32x2T {
    val b: I32x2 = Simd<Int>(listOf(0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x2, Int32x2T>(b, UnresolvedTypeLayout<I32x2>(), UnresolvedTypeLayout<Int32x2T>()); Uint32x2T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqS32)
 */
public fun vcgtzqS32(a: Int32x4T): Uint32x4T {
    val b: I32x4 = Simd<Int>(listOf(0, 0, 0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x4, Int32x4T>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<Int32x4T>()); Uint32x4T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzS64)
 */
public fun vcgtzS64(a: Int64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
}

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqS64)
 */
public fun vcgtzqS64(a: Int64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
}

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzdF64)
 */
public fun vcgtzdF64(a: Double): ULong = vgetLaneU64(vcgtzF64(vdupNF64(a)), 0uL)

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzsF32)
 */
public fun vcgtzsF32(a: Float): UInt = vgetLaneU32(vcgtzF32(vdupNF32(a)), 0u)

/**
 * Compare signed greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzdS64)
 */
public fun vcgtzdS64(a: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcgtzS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzhF16)
 */
public fun vcgtzhF16(a: F16): UShort = vgetLaneU16(vcgtzF16(vdupNF16(a)), 0u)

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleF64)
 */
public fun vcleF64(a: Float64x1T, b: Float64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqF64)
 */
public fun vcleqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleS64)
 */
public fun vcleS64(a: Int64x1T, b: Int64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqS64)
 */
public fun vcleqS64(a: Int64x2T, b: Int64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleU64)
 */
public fun vcleU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqU64)
 */
public fun vcleqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcledF64)
 */
public fun vcledF64(a: Double, b: Double): ULong = vgetLaneU64(vcleF64(vdupNF64(a), vdupNF64(b)), 0uL)

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclesF32)
 */
public fun vclesF32(a: Float, b: Float): UInt = vgetLaneU32(vcleF32(vdupNF32(a), vdupNF32(b)), 0u)

/**
 * Compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcledU64)
 */
public fun vcledU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcleU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcledS64)
 */
public fun vcledS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcleS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclehF16)
 */
public fun vclehF16(a: F16, b: F16): UShort = vgetLaneU16(vcleF16(vdupNF16(a), vdupNF16(b)), 0u)

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezF32)
 */
public fun vclezF32(a: Float32x2T): Uint32x2T {
    val b: F32x2 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x2, Float32x2T>(b, UnresolvedTypeLayout<F32x2>(), UnresolvedTypeLayout<Float32x2T>()); Uint32x2T() }
}

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqF32)
 */
public fun vclezqF32(a: Float32x4T): Uint32x4T {
    val b: F32x4 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x4, Float32x4T>(b, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<Float32x4T>()); Uint32x4T() }
}

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezF64)
 */
public fun vclezF64(a: Float64x1T): Uint64x1T {
    val b: Double = 0.0
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(b, PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); Uint64x1T() }
}

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqF64)
 */
public fun vclezqF64(a: Float64x2T): Uint64x2T {
    val b: F64x2 = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F64x2, Float64x2T>(b, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<Float64x2T>()); Uint64x2T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezS8)
 */
public fun vclezS8(a: Int8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Int8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Int8x8T>()); Uint8x8T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqS8)
 */
public fun vclezqS8(a: Int8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Int8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Int8x16T>()); Uint8x16T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezS16)
 */
public fun vclezS16(a: Int16x4T): Uint16x4T {
    val b: I16x4 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x4, Int16x4T>(b, UnresolvedTypeLayout<I16x4>(), UnresolvedTypeLayout<Int16x4T>()); Uint16x4T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqS16)
 */
public fun vclezqS16(a: Int16x8T): Uint16x8T {
    val b: I16x8 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x8, Int16x8T>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<Int16x8T>()); Uint16x8T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezS32)
 */
public fun vclezS32(a: Int32x2T): Uint32x2T {
    val b: I32x2 = Simd<Int>(listOf(0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x2, Int32x2T>(b, UnresolvedTypeLayout<I32x2>(), UnresolvedTypeLayout<Int32x2T>()); Uint32x2T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqS32)
 */
public fun vclezqS32(a: Int32x4T): Uint32x4T {
    val b: I32x4 = Simd<Int>(listOf(0, 0, 0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x4, Int32x4T>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<Int32x4T>()); Uint32x4T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezS64)
 */
public fun vclezS64(a: Int64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
}

/**
 * Compare signed less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqS64)
 */
public fun vclezqS64(a: Int64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
}

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezdF64)
 */
public fun vclezdF64(a: Double): ULong = vgetLaneU64(vclezF64(vdupNF64(a)), 0uL)

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezsF32)
 */
public fun vclezsF32(a: Float): UInt = vgetLaneU32(vclezF32(vdupNF32(a)), 0u)

/**
 * Compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezdS64)
 */
public fun vclezdS64(a: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vclezS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezhF16)
 */
public fun vclezhF16(a: F16): UShort = vgetLaneU16(vclezF16(vdupNF16(a)), 0u)

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltF64)
 */
public fun vcltF64(a: Float64x1T, b: Float64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqF64)
 */
public fun vcltqF64(a: Float64x2T, b: Float64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltS64)
 */
public fun vcltS64(a: Int64x1T, b: Int64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqS64)
 */
public fun vcltqS64(a: Int64x2T, b: Int64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltU64)
 */
public fun vcltU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; Uint64x1T() }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqU64)
 */
public fun vcltqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; Uint64x2T() }

/**
 * Compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltdU64)
 */
public fun vcltdU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcltU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltdS64)
 */
public fun vcltdS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcltS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclthF16)
 */
public fun vclthF16(a: F16, b: F16): UShort = vgetLaneU16(vcltF16(vdupNF16(a), vdupNF16(b)), 0u)

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltsF32)
 */
public fun vcltsF32(a: Float, b: Float): UInt = vgetLaneU32(vcltF32(vdupNF32(a), vdupNF32(b)), 0u)

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltdF64)
 */
public fun vcltdF64(a: Double, b: Double): ULong = vgetLaneU64(vcltF64(vdupNF64(a), vdupNF64(b)), 0uL)

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzF32)
 */
public fun vcltzF32(a: Float32x2T): Uint32x2T {
    val b: F32x2 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x2, Float32x2T>(b, UnresolvedTypeLayout<F32x2>(), UnresolvedTypeLayout<Float32x2T>()); Uint32x2T() }
}

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqF32)
 */
public fun vcltzqF32(a: Float32x4T): Uint32x4T {
    val b: F32x4 = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat(), 0.0f.toFloat()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F32x4, Float32x4T>(b, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<Float32x4T>()); Uint32x4T() }
}

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzF64)
 */
public fun vcltzF64(a: Float64x1T): Uint64x1T {
    val b: Double = 0.0
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(b, PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); Uint64x1T() }
}

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqF64)
 */
public fun vcltzqF64(a: Float64x2T): Uint64x2T {
    val b: F64x2 = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<F64x2, Float64x2T>(b, UnresolvedTypeLayout<F64x2>(), UnresolvedTypeLayout<Float64x2T>()); Uint64x2T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzS8)
 */
public fun vcltzS8(a: Int8x8T): Uint8x8T {
    val b: I8x8 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x8, Int8x8T>(b, UnresolvedTypeLayout<I8x8>(), UnresolvedTypeLayout<Int8x8T>()); Uint8x8T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqS8)
 */
public fun vcltzqS8(a: Int8x16T): Uint8x16T {
    val b: I8x16 = Simd<Byte>(listOf(0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I8x16, Int8x16T>(b, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<Int8x16T>()); Uint8x16T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzS16)
 */
public fun vcltzS16(a: Int16x4T): Uint16x4T {
    val b: I16x4 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x4, Int16x4T>(b, UnresolvedTypeLayout<I16x4>(), UnresolvedTypeLayout<Int16x4T>()); Uint16x4T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqS16)
 */
public fun vcltzqS16(a: Int16x8T): Uint16x8T {
    val b: I16x8 = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I16x8, Int16x8T>(b, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<Int16x8T>()); Uint16x8T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzS32)
 */
public fun vcltzS32(a: Int32x2T): Uint32x2T {
    val b: I32x2 = Simd<Int>(listOf(0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x2, Int32x2T>(b, UnresolvedTypeLayout<I32x2>(), UnresolvedTypeLayout<Int32x2T>()); Uint32x2T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqS32)
 */
public fun vcltzqS32(a: Int32x4T): Uint32x4T {
    val b: I32x4 = Simd<Int>(listOf(0, 0, 0, 0))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I32x4, Int32x4T>(b, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<Int32x4T>()); Uint32x4T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzS64)
 */
public fun vcltzS64(a: Int64x1T): Uint64x1T {
    val b: I64x1 = Simd<Long>(listOf(0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(b, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
}

/**
 * Compare signed less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqS64)
 */
public fun vcltzqS64(a: Int64x2T): Uint64x2T {
    val b: I64x2 = Simd<Long>(listOf(0L, 0L))
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(b, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
}

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzdF64)
 */
public fun vcltzdF64(a: Double): ULong = vgetLaneU64(vcltzF64(vdupNF64(a)), 0uL)

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzsF32)
 */
public fun vcltzsF32(a: Float): UInt = vgetLaneU32(vcltzF32(vdupNF32(a)), 0u)

/**
 * Compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzdS64)
 */
public fun vcltzdS64(a: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vcltzS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Floating-point compare less than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzhF16)
 */
public fun vcltzhF16(a: F16): UShort = vgetLaneU16(vcltzF16(vdupNF16(a)), 0u)

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaF16)
 */
public fun vcmlaF16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T {
    fun externVcmlaF16(a1: Float16x4T, b1: Float16x4T, c1: Float16x4T): Float16x4T = Float16x4T()
    return externVcmlaF16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqF16)
 */
public fun vcmlaqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T {
    fun externVcmlaqF16(a1: Float16x8T, b1: Float16x8T, c1: Float16x8T): Float16x8T = Float16x8T()
    return externVcmlaqF16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaF32)
 */
public fun vcmlaF32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T {
    fun externVcmlaF32(a1: Float32x2T, b1: Float32x2T, c1: Float32x2T): Float32x2T = Float32x2T()
    return externVcmlaF32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqF32)
 */
public fun vcmlaqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T {
    fun externVcmlaqF32(a1: Float32x4T, b1: Float32x4T, c1: Float32x4T): Float32x4T = Float32x4T()
    return externVcmlaqF32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqF64)
 */
public fun vcmlaqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T {
    fun externVcmlaqF64(a1: Float64x2T, b1: Float64x2T, c1: Float64x2T): Float64x2T = Float64x2T()
    return externVcmlaqF64(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaLaneF16)
 */
public fun vcmlaLaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupLaneU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaF16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqLaneF16)
 */
public fun vcmlaqLaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupqLaneU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqF16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaLaneF32)
 */
public fun vcmlaLaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupLaneU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaF32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqLaneF32)
 */
public fun vcmlaqLaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupqLaneU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqF32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaLaneqF16)
 */
public fun vcmlaLaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupLaneqU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaF16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqLaneqF16)
 */
public fun vcmlaqLaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupqLaneqU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqF16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaLaneqF32)
 */
public fun vcmlaLaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupLaneqU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaF32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqLaneqF32)
 */
public fun vcmlaqLaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupqLaneqU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqF32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180F16)
 */
public fun vcmlaRot180F16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T {
    fun externVcmlaRot180F16(a1: Float16x4T, b1: Float16x4T, c1: Float16x4T): Float16x4T = Float16x4T()
    return externVcmlaRot180F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180F16)
 */
public fun vcmlaqRot180F16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T {
    fun externVcmlaqRot180F16(a1: Float16x8T, b1: Float16x8T, c1: Float16x8T): Float16x8T = Float16x8T()
    return externVcmlaqRot180F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180F32)
 */
public fun vcmlaRot180F32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T {
    fun externVcmlaRot180F32(a1: Float32x2T, b1: Float32x2T, c1: Float32x2T): Float32x2T = Float32x2T()
    return externVcmlaRot180F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180F32)
 */
public fun vcmlaqRot180F32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T {
    fun externVcmlaqRot180F32(a1: Float32x4T, b1: Float32x4T, c1: Float32x4T): Float32x4T = Float32x4T()
    return externVcmlaqRot180F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180F64)
 */
public fun vcmlaqRot180F64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T {
    fun externVcmlaqRot180F64(a1: Float64x2T, b1: Float64x2T, c1: Float64x2T): Float64x2T = Float64x2T()
    return externVcmlaqRot180F64(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180LaneF16)
 */
public fun vcmlaRot180LaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupLaneU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot180F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180LaneF16)
 */
public fun vcmlaqRot180LaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupqLaneU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot180F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180LaneF32)
 */
public fun vcmlaRot180LaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupLaneU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot180F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180LaneF32)
 */
public fun vcmlaqRot180LaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupqLaneU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot180F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180LaneqF16)
 */
public fun vcmlaRot180LaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupLaneqU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot180F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180LaneqF16)
 */
public fun vcmlaqRot180LaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupqLaneqU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot180F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot180LaneqF32)
 */
public fun vcmlaRot180LaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupLaneqU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot180F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot180LaneqF32)
 */
public fun vcmlaqRot180LaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupqLaneqU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot180F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270F16)
 */
public fun vcmlaRot270F16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T {
    fun externVcmlaRot270F16(a1: Float16x4T, b1: Float16x4T, c1: Float16x4T): Float16x4T = Float16x4T()
    return externVcmlaRot270F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270F16)
 */
public fun vcmlaqRot270F16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T {
    fun externVcmlaqRot270F16(a1: Float16x8T, b1: Float16x8T, c1: Float16x8T): Float16x8T = Float16x8T()
    return externVcmlaqRot270F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270F32)
 */
public fun vcmlaRot270F32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T {
    fun externVcmlaRot270F32(a1: Float32x2T, b1: Float32x2T, c1: Float32x2T): Float32x2T = Float32x2T()
    return externVcmlaRot270F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270F32)
 */
public fun vcmlaqRot270F32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T {
    fun externVcmlaqRot270F32(a1: Float32x4T, b1: Float32x4T, c1: Float32x4T): Float32x4T = Float32x4T()
    return externVcmlaqRot270F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270F64)
 */
public fun vcmlaqRot270F64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T {
    fun externVcmlaqRot270F64(a1: Float64x2T, b1: Float64x2T, c1: Float64x2T): Float64x2T = Float64x2T()
    return externVcmlaqRot270F64(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270LaneF16)
 */
public fun vcmlaRot270LaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupLaneU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot270F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270LaneF16)
 */
public fun vcmlaqRot270LaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupqLaneU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot270F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270LaneF32)
 */
public fun vcmlaRot270LaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupLaneU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot270F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270LaneF32)
 */
public fun vcmlaqRot270LaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupqLaneU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot270F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270LaneqF16)
 */
public fun vcmlaRot270LaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupLaneqU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot270F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270LaneqF16)
 */
public fun vcmlaqRot270LaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupqLaneqU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot270F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot270LaneqF32)
 */
public fun vcmlaRot270LaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupLaneqU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot270F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot270LaneqF32)
 */
public fun vcmlaqRot270LaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupqLaneqU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot270F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90F16)
 */
public fun vcmlaRot90F16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T {
    fun externVcmlaRot90F16(a1: Float16x4T, b1: Float16x4T, c1: Float16x4T): Float16x4T = Float16x4T()
    return externVcmlaRot90F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90F16)
 */
public fun vcmlaqRot90F16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T {
    fun externVcmlaqRot90F16(a1: Float16x8T, b1: Float16x8T, c1: Float16x8T): Float16x8T = Float16x8T()
    return externVcmlaqRot90F16(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90F32)
 */
public fun vcmlaRot90F32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T {
    fun externVcmlaRot90F32(a1: Float32x2T, b1: Float32x2T, c1: Float32x2T): Float32x2T = Float32x2T()
    return externVcmlaRot90F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90F32)
 */
public fun vcmlaqRot90F32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T {
    fun externVcmlaqRot90F32(a1: Float32x4T, b1: Float32x4T, c1: Float32x4T): Float32x4T = Float32x4T()
    return externVcmlaqRot90F32(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90F64)
 */
public fun vcmlaqRot90F64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T {
    fun externVcmlaqRot90F64(a1: Float64x2T, b1: Float64x2T, c1: Float64x2T): Float64x2T = Float64x2T()
    return externVcmlaqRot90F64(a, b, c)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90LaneF16)
 */
public fun vcmlaRot90LaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupLaneU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot90F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90LaneF16)
 */
public fun vcmlaqRot90LaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32F16(c)
    val c2 = vdupqLaneU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot90F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90LaneF32)
 */
public fun vcmlaRot90LaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupLaneU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot90F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90LaneF32)
 */
public fun vcmlaqRot90LaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(lane == 0)
    }
    val c1 = vreinterpretU64F32(c)
    val c2 = vdupqLaneU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot90F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90LaneqF16)
 */
public fun vcmlaRot90LaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupLaneqU32(c1, lane)
    val c3 = vreinterpretF16U32(c2)
    return vcmlaRot90F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90LaneqF16)
 */
public fun vcmlaqRot90LaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32F16(c)
    val c2 = vdupqLaneqU32(c1, lane)
    val c3 = vreinterpretqF16U32(c2)
    return vcmlaqRot90F16(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaRot90LaneqF32)
 */
public fun vcmlaRot90LaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupLaneqU64(c1, lane)
    val c3 = vreinterpretF32U64(c2)
    return vcmlaRot90F32(a, b, c3)
}

/**
 * Floating-point complex multiply accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcmlaqRot90LaneqF32)
 */
public fun vcmlaqRot90LaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretqU64F32(c)
    val c2 = vdupqLaneqU64(c1, lane)
    val c3 = vreinterpretqF32U64(c2)
    return vcmlaqRot90F32(a, b, c3)
}

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineF64)
 */
public fun vcombineF64(a: Float64x1T, b: Float64x1T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u)); Float64x2T() }

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneF32)
 */
public fun vcopyLaneF32(a: Float32x2T, b: Float32x2T, lane1: Int, lane2: Int): Float32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetLaneF32(vgetLaneF32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneS8)
 */
public fun vcopyLaneS8(a: Int8x8T, b: Int8x8T, lane1: Int, lane2: Int): Int8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneS8(vgetLaneS8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneS16)
 */
public fun vcopyLaneS16(a: Int16x4T, b: Int16x4T, lane1: Int, lane2: Int): Int16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneS16(vgetLaneS16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneS32)
 */
public fun vcopyLaneS32(a: Int32x2T, b: Int32x2T, lane1: Int, lane2: Int): Int32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetLaneS32(vgetLaneS32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneU8)
 */
public fun vcopyLaneU8(a: Uint8x8T, b: Uint8x8T, lane1: Int, lane2: Int): Uint8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneU8(vgetLaneU8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneU16)
 */
public fun vcopyLaneU16(a: Uint16x4T, b: Uint16x4T, lane1: Int, lane2: Int): Uint16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneU16(vgetLaneU16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneU32)
 */
public fun vcopyLaneU32(a: Uint32x2T, b: Uint32x2T, lane1: Int, lane2: Int): Uint32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetLaneU32(vgetLaneU32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneP8)
 */
public fun vcopyLaneP8(a: Poly8x8T, b: Poly8x8T, lane1: Int, lane2: Int): Poly8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneP8(vgetLaneP8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneP16)
 */
public fun vcopyLaneP16(a: Poly16x4T, b: Poly16x4T, lane1: Int, lane2: Int): Poly16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneP16(vgetLaneP16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneF64)
 */
public fun vcopyLaneF64(a: Float64x1T, b: Float64x1T, lane1: Int, lane2: Int): Float64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(lane2 == 0)
    }
    return b
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneS64)
 */
public fun vcopyLaneS64(a: Int64x1T, b: Int64x1T, lane1: Int, lane2: Int): Int64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(lane2 == 0)
    }
    return b
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneU64)
 */
public fun vcopyLaneU64(a: Uint64x1T, b: Uint64x1T, lane1: Int, lane2: Int): Uint64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(lane2 == 0)
    }
    return b
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneP64)
 */
public fun vcopyLaneP64(a: Poly64x1T, b: Poly64x1T, lane1: Int, lane2: Int): Poly64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(lane2 == 0)
    }
    return b
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqF32)
 */
public fun vcopyLaneqF32(a: Float32x2T, b: Float32x4T, lane1: Int, lane2: Int): Float32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneF32(vgetqLaneF32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqS8)
 */
public fun vcopyLaneqS8(a: Int8x8T, b: Int8x16T, lane1: Int, lane2: Int): Int8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetLaneS8(vgetqLaneS8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqS16)
 */
public fun vcopyLaneqS16(a: Int16x4T, b: Int16x8T, lane1: Int, lane2: Int): Int16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneS16(vgetqLaneS16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqS32)
 */
public fun vcopyLaneqS32(a: Int32x2T, b: Int32x4T, lane1: Int, lane2: Int): Int32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneS32(vgetqLaneS32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqU8)
 */
public fun vcopyLaneqU8(a: Uint8x8T, b: Uint8x16T, lane1: Int, lane2: Int): Uint8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetLaneU8(vgetqLaneU8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqU16)
 */
public fun vcopyLaneqU16(a: Uint16x4T, b: Uint16x8T, lane1: Int, lane2: Int): Uint16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneU16(vgetqLaneU16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqU32)
 */
public fun vcopyLaneqU32(a: Uint32x2T, b: Uint32x4T, lane1: Int, lane2: Int): Uint32x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetLaneU32(vgetqLaneU32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqP8)
 */
public fun vcopyLaneqP8(a: Poly8x8T, b: Poly8x16T, lane1: Int, lane2: Int): Poly8x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetLaneP8(vgetqLaneP8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqP16)
 */
public fun vcopyLaneqP16(a: Poly16x4T, b: Poly16x8T, lane1: Int, lane2: Int): Poly16x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetLaneP16(vgetqLaneP16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqF64)
 */
public fun vcopyLaneqF64(a: Float64x1T, b: Float64x2T, lane1: Int, lane2: Int): Float64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Double, Float64x1T>(vgetqLaneF64(b, lane2), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqS64)
 */
public fun vcopyLaneqS64(a: Int64x1T, b: Int64x2T, lane1: Int, lane2: Int): Int64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Long, Int64x1T>(vgetqLaneS64(b, lane2), UnresolvedTypeLayout<Long>(), UnresolvedTypeLayout<Int64x1T>())
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqU64)
 */
public fun vcopyLaneqU64(a: Uint64x1T, b: Uint64x2T, lane1: Int, lane2: Int): Uint64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<ULong, Uint64x1T>(vgetqLaneU64(b, lane2), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Uint64x1T>())
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyLaneqP64)
 */
public fun vcopyLaneqP64(a: Poly64x1T, b: Poly64x2T, lane1: Int, lane2: Int): Poly64x1T {
    run {
        check(lane1 == 0)
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<P64, Poly64x1T>(vgetqLaneP64(b, lane2), UnresolvedTypeLayout<P64>(), UnresolvedTypeLayout<Poly64x1T>())
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneF32)
 */
public fun vcopyqLaneF32(a: Float32x4T, b: Float32x2T, lane1: Int, lane2: Int): Float32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetqLaneF32(vgetLaneF32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneF64)
 */
public fun vcopyqLaneF64(a: Float64x2T, b: Float64x1T, lane1: Int, lane2: Int): Float64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(lane2 == 0)
    }
    val b1: Float64x2T = vcombineF64(b, b)
    return vsetqLaneF64(vgetqLaneF64(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneS64)
 */
public fun vcopyqLaneS64(a: Int64x2T, b: Int64x1T, lane1: Int, lane2: Int): Int64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(lane2 == 0)
    }
    val b1: Int64x2T = vcombineS64(b, b)
    return vsetqLaneS64(vgetqLaneS64(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneU64)
 */
public fun vcopyqLaneU64(a: Uint64x2T, b: Uint64x1T, lane1: Int, lane2: Int): Uint64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(lane2 == 0)
    }
    val b1: Uint64x2T = vcombineU64(b, b)
    return vsetqLaneU64(vgetqLaneU64(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneP64)
 */
public fun vcopyqLaneP64(a: Poly64x2T, b: Poly64x1T, lane1: Int, lane2: Int): Poly64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(lane2 == 0)
    }
    val b1: Poly64x2T = vcombineP64(b, b)
    return run { val simdArg0 = a; val simdArg1 = lane1.toUInt(); val simdArg2 = run { val simdArg0 = b1; val simdArg1 = lane2.toUInt(); 0uL }; simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneS8)
 */
public fun vcopyqLaneS8(a: Int8x16T, b: Int8x8T, lane1: Int, lane2: Int): Int8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    val b1: Int8x16T = vcombineS8(b, b)
    return vsetqLaneS8(vgetqLaneS8(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneS16)
 */
public fun vcopyqLaneS16(a: Int16x8T, b: Int16x4T, lane1: Int, lane2: Int): Int16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Int16x8T = vcombineS16(b, b)
    return vsetqLaneS16(vgetqLaneS16(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneS32)
 */
public fun vcopyqLaneS32(a: Int32x4T, b: Int32x2T, lane1: Int, lane2: Int): Int32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Int32x4T = vcombineS32(b, b)
    return vsetqLaneS32(vgetqLaneS32(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneU8)
 */
public fun vcopyqLaneU8(a: Uint8x16T, b: Uint8x8T, lane1: Int, lane2: Int): Uint8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    val b1: Uint8x16T = vcombineU8(b, b)
    return vsetqLaneU8(vgetqLaneU8(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneU16)
 */
public fun vcopyqLaneU16(a: Uint16x8T, b: Uint16x4T, lane1: Int, lane2: Int): Uint16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Uint16x8T = vcombineU16(b, b)
    return vsetqLaneU16(vgetqLaneU16(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneU32)
 */
public fun vcopyqLaneU32(a: Uint32x4T, b: Uint32x2T, lane1: Int, lane2: Int): Uint32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Uint32x4T = vcombineU32(b, b)
    return vsetqLaneU32(vgetqLaneU32(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneP8)
 */
public fun vcopyqLaneP8(a: Poly8x16T, b: Poly8x8T, lane1: Int, lane2: Int): Poly8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    val b1: Poly8x16T = vcombineP8(b, b)
    return vsetqLaneP8(vgetqLaneP8(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneP16)
 */
public fun vcopyqLaneP16(a: Poly16x8T, b: Poly16x4T, lane1: Int, lane2: Int): Poly16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Poly16x8T = vcombineP16(b, b)
    return vsetqLaneP16(vgetqLaneP16(b1, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqF32)
 */
public fun vcopyqLaneqF32(a: Float32x4T, b: Float32x4T, lane1: Int, lane2: Int): Float32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetqLaneF32(vgetqLaneF32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqF64)
 */
public fun vcopyqLaneqF64(a: Float64x2T, b: Float64x2T, lane1: Int, lane2: Int): Float64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetqLaneF64(vgetqLaneF64(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqS8)
 */
public fun vcopyqLaneqS8(a: Int8x16T, b: Int8x16T, lane1: Int, lane2: Int): Int8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetqLaneS8(vgetqLaneS8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqS16)
 */
public fun vcopyqLaneqS16(a: Int16x8T, b: Int16x8T, lane1: Int, lane2: Int): Int16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetqLaneS16(vgetqLaneS16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqS32)
 */
public fun vcopyqLaneqS32(a: Int32x4T, b: Int32x4T, lane1: Int, lane2: Int): Int32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetqLaneS32(vgetqLaneS32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqS64)
 */
public fun vcopyqLaneqS64(a: Int64x2T, b: Int64x2T, lane1: Int, lane2: Int): Int64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetqLaneS64(vgetqLaneS64(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqU8)
 */
public fun vcopyqLaneqU8(a: Uint8x16T, b: Uint8x16T, lane1: Int, lane2: Int): Uint8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetqLaneU8(vgetqLaneU8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqU16)
 */
public fun vcopyqLaneqU16(a: Uint16x8T, b: Uint16x8T, lane1: Int, lane2: Int): Uint16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetqLaneU16(vgetqLaneU16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqU32)
 */
public fun vcopyqLaneqU32(a: Uint32x4T, b: Uint32x4T, lane1: Int, lane2: Int): Uint32x4T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 2)) { "LANE1 doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 2)) { "LANE2 doesn't fit in 2 bits (unsigned)" }
    }
    return vsetqLaneU32(vgetqLaneU32(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqU64)
 */
public fun vcopyqLaneqU64(a: Uint64x2T, b: Uint64x2T, lane1: Int, lane2: Int): Uint64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return vsetqLaneU64(vgetqLaneU64(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqP8)
 */
public fun vcopyqLaneqP8(a: Poly8x16T, b: Poly8x16T, lane1: Int, lane2: Int): Poly8x16T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 4)) { "LANE1 doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 4)) { "LANE2 doesn't fit in 4 bits (unsigned)" }
    }
    return vsetqLaneP8(vgetqLaneP8(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqP16)
 */
public fun vcopyqLaneqP16(a: Poly16x8T, b: Poly16x8T, lane1: Int, lane2: Int): Poly16x8T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 3)) { "LANE1 doesn't fit in 3 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 3)) { "LANE2 doesn't fit in 3 bits (unsigned)" }
    }
    return vsetqLaneP16(vgetqLaneP16(b, lane2), a, lane1)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcopyqLaneqP64)
 */
public fun vcopyqLaneqP64(a: Poly64x2T, b: Poly64x2T, lane1: Int, lane2: Int): Poly64x2T {
    run {
        check(0 <= lane1 && lane1 < (1 shl 1)) { "LANE1 doesn't fit in 1 bits (unsigned)" }
    }
    run {
        check(0 <= lane2 && lane2 < (1 shl 1)) { "LANE2 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = lane1.toUInt(); val simdArg2 = run { val simdArg0 = b; val simdArg1 = lane2.toUInt(); 0uL }; simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateF64)
 */
public fun vcreateF64(a: ULong): Float64x1T = transmuteWithLayouts<ULong, Float64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Float64x1T>())

/**
 * Floating-point convert
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF32F64)
 */
public fun vcvtF32F64(a: Float64x2T): Float32x2T = run { val simdArg0 = a; Float32x2T() }

/**
 * Floating-point convert to higher precision long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF64F32)
 */
public fun vcvtF64F32(a: Float32x2T): Float64x2T = run { val simdArg0 = a; Float64x2T() }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF64S64)
 */
public fun vcvtF64S64(a: Int64x1T): Float64x1T = run { val simdArg0 = a; Float64x1T() }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF64S64)
 */
public fun vcvtqF64S64(a: Int64x2T): Float64x2T = run { val simdArg0 = a; Float64x2T() }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF64U64)
 */
public fun vcvtF64U64(a: Uint64x1T): Float64x1T = run { val simdArg0 = a; Float64x1T() }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF64U64)
 */
public fun vcvtqF64U64(a: Uint64x2T): Float64x2T = run { val simdArg0 = a; Float64x2T() }

/**
 * Floating-point convert to lower precision
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtHighF16F32)
 */
public fun vcvtHighF16F32(a: Float16x4T, b: Float32x4T): Float16x8T = vcombineF16(a, vcvtF16F32(b))

/**
 * Floating-point convert to higher precision
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtHighF32F16)
 */
public fun vcvtHighF32F16(a: Float16x8T): Float32x4T = vcvtF32F16(vgetHighF16(a))

/**
 * Floating-point convert to lower precision narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtHighF32F64)
 */
public fun vcvtHighF32F64(a: Float32x2T, b: Float64x2T): Float32x4T = vcombineF32(a, vcvtF32F64(b))

/**
 * Floating-point convert to higher precision long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtHighF64F32)
 */
public fun vcvtHighF64F32(a: Float32x4T): Float64x2T = run { val simdArg0 = vgetHighF32(a); Float64x2T() }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF64S64)
 */
public fun vcvtNF64S64(a: Int64x1T, n: Int): Float64x1T {
    fun externVcvtNF64S64(a1: Int64x1T, n: Int): Float64x1T = Float64x1T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtNF64S64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF64S64)
 */
public fun vcvtqNF64S64(a: Int64x2T, n: Int): Float64x2T {
    fun externVcvtqNF64S64(a1: Int64x2T, n: Int): Float64x2T = Float64x2T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtqNF64S64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF64U64)
 */
public fun vcvtNF64U64(a: Uint64x1T, n: Int): Float64x1T {
    fun externVcvtNF64U64(a1: Uint64x1T, n: Int): Float64x1T = Float64x1T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtNF64U64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF64U64)
 */
public fun vcvtqNF64U64(a: Uint64x2T, n: Int): Float64x2T {
    fun externVcvtqNF64U64(a1: Uint64x2T, n: Int): Float64x2T = Float64x2T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtqNF64U64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNS64F64)
 */
public fun vcvtNS64F64(a: Float64x1T, n: Int): Int64x1T {
    fun externVcvtNS64F64(a1: Float64x1T, n: Int): Int64x1T = Int64x1T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtNS64F64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNS64F64)
 */
public fun vcvtqNS64F64(a: Float64x2T, n: Int): Int64x2T {
    fun externVcvtqNS64F64(a1: Float64x2T, n: Int): Int64x2T = Int64x2T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtqNS64F64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNU64F64)
 */
public fun vcvtNU64F64(a: Float64x1T, n: Int): Uint64x1T {
    fun externVcvtNU64F64(a1: Float64x1T, n: Int): Uint64x1T = Uint64x1T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtNU64F64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNU64F64)
 */
public fun vcvtqNU64F64(a: Float64x2T, n: Int): Uint64x2T {
    fun externVcvtqNU64F64(a1: Float64x2T, n: Int): Uint64x2T = Uint64x2T()
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtqNU64F64(a, n)
}

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtS64F64)
 */
public fun vcvtS64F64(a: Float64x1T): Int64x1T {
    fun externVcvtS64F64(a1: Float64x1T): Int64x1T = Int64x1T()
    return externVcvtS64F64(a)
}

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqS64F64)
 */
public fun vcvtqS64F64(a: Float64x2T): Int64x2T {
    fun externVcvtqS64F64(a1: Float64x2T): Int64x2T = Int64x2T()
    return externVcvtqS64F64(a)
}

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtU64F64)
 */
public fun vcvtU64F64(a: Float64x1T): Uint64x1T {
    fun externVcvtU64F64(a1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcvtU64F64(a)
}

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqU64F64)
 */
public fun vcvtqU64F64(a: Float64x2T): Uint64x2T {
    fun externVcvtqU64F64(a1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcvtqU64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaS16F16)
 */
public fun vcvtaS16F16(a: Float16x4T): Int16x4T {
    fun externVcvtaS16F16(a1: Float16x4T): Int16x4T = Int16x4T()
    return externVcvtaS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqS16F16)
 */
public fun vcvtaqS16F16(a: Float16x8T): Int16x8T {
    fun externVcvtaqS16F16(a1: Float16x8T): Int16x8T = Int16x8T()
    return externVcvtaqS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaS32F32)
 */
public fun vcvtaS32F32(a: Float32x2T): Int32x2T {
    fun externVcvtaS32F32(a1: Float32x2T): Int32x2T = Int32x2T()
    return externVcvtaS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqS32F32)
 */
public fun vcvtaqS32F32(a: Float32x4T): Int32x4T {
    fun externVcvtaqS32F32(a1: Float32x4T): Int32x4T = Int32x4T()
    return externVcvtaqS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaS64F64)
 */
public fun vcvtaS64F64(a: Float64x1T): Int64x1T {
    fun externVcvtaS64F64(a1: Float64x1T): Int64x1T = Int64x1T()
    return externVcvtaS64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqS64F64)
 */
public fun vcvtaqS64F64(a: Float64x2T): Int64x2T {
    fun externVcvtaqS64F64(a1: Float64x2T): Int64x2T = Int64x2T()
    return externVcvtaqS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaU16F16)
 */
public fun vcvtaU16F16(a: Float16x4T): Uint16x4T {
    fun externVcvtaU16F16(a1: Float16x4T): Uint16x4T = Uint16x4T()
    return externVcvtaU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqU16F16)
 */
public fun vcvtaqU16F16(a: Float16x8T): Uint16x8T {
    fun externVcvtaqU16F16(a1: Float16x8T): Uint16x8T = Uint16x8T()
    return externVcvtaqU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaU32F32)
 */
public fun vcvtaU32F32(a: Float32x2T): Uint32x2T {
    fun externVcvtaU32F32(a1: Float32x2T): Uint32x2T = Uint32x2T()
    return externVcvtaU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqU32F32)
 */
public fun vcvtaqU32F32(a: Float32x4T): Uint32x4T {
    fun externVcvtaqU32F32(a1: Float32x4T): Uint32x4T = Uint32x4T()
    return externVcvtaqU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaU64F64)
 */
public fun vcvtaU64F64(a: Float64x1T): Uint64x1T {
    fun externVcvtaU64F64(a1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcvtaU64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtaqU64F64)
 */
public fun vcvtaqU64F64(a: Float64x2T): Uint64x2T {
    fun externVcvtaqU64F64(a1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcvtaqU64F64(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahS16F16)
 */
public fun vcvtahS16F16(a: F16): Short {
    fun externVcvtahS16F16(a1: F16): Short = 0
    return externVcvtahS16F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahS32F16)
 */
public fun vcvtahS32F16(a: F16): Int {
    fun externVcvtahS32F16(a1: F16): Int = 0
    return externVcvtahS32F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahS64F16)
 */
public fun vcvtahS64F16(a: F16): Long {
    fun externVcvtahS64F16(a1: F16): Long = 0L
    return externVcvtahS64F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahU16F16)
 */
public fun vcvtahU16F16(a: F16): UShort {
    fun externVcvtahU16F16(a1: F16): UShort = 0u
    return externVcvtahU16F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahU32F16)
 */
public fun vcvtahU32F16(a: F16): UInt {
    fun externVcvtahU32F16(a1: F16): UInt = 0u
    return externVcvtahU32F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtahU64F16)
 */
public fun vcvtahU64F16(a: F16): ULong {
    fun externVcvtahU64F16(a1: F16): ULong = 0uL
    return externVcvtahU64F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtasS32F32)
 */
public fun vcvtasS32F32(a: Float): Int {
    fun externVcvtasS32F32(a1: Float): Int = 0
    return externVcvtasS32F32(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtadS64F64)
 */
public fun vcvtadS64F64(a: Double): Long {
    fun externVcvtadS64F64(a1: Double): Long = 0L
    return externVcvtadS64F64(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtasU32F32)
 */
public fun vcvtasU32F32(a: Float): UInt {
    fun externVcvtasU32F32(a1: Float): UInt = 0u
    return externVcvtasU32F32(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtadU64F64)
 */
public fun vcvtadU64F64(a: Double): ULong {
    fun externVcvtadU64F64(a1: Double): ULong = 0uL
    return externVcvtadU64F64(a)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdF64S64)
 */
public fun vcvtdF64S64(a: Long): Double = a.toDouble()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsF32S32)
 */
public fun vcvtsF32S32(a: Int): Float = a.toFloat()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16S16)
 */
public fun vcvthF16S16(a: Short): F16 = F16.fromShort(a)

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16S32)
 */
public fun vcvthF16S32(a: Int): F16 = F16.fromInt(a)

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16S64)
 */
public fun vcvthF16S64(a: Long): F16 = F16.fromLong(a)

/**
 * Unsigned fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16U16)
 */
public fun vcvthF16U16(a: UShort): F16 = F16.fromUShort(a)

/**
 * Unsigned fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16U32)
 */
public fun vcvthF16U32(a: UInt): F16 = F16.fromUInt(a)

/**
 * Unsigned fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthF16U64)
 */
public fun vcvthF16U64(a: ULong): F16 = F16.fromULong(a)

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16S16)
 */
public fun vcvthNF16S16(a: Short, n: Int): F16 {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvthNF16S32(a.toInt(), n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16S32)
 */
public fun vcvthNF16S32(a: Int, n: Int): F16 {
    fun externVcvthNF16S32(a1: Int, n: Int): F16 = F16()
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNF16S32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16S64)
 */
public fun vcvthNF16S64(a: Long, n: Int): F16 {
    fun externVcvthNF16S64(a1: Long, n: Int): F16 = F16()
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNF16S64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16U16)
 */
public fun vcvthNF16U16(a: UShort, n: Int): F16 {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvthNF16U32(a.toUInt(), n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16U32)
 */
public fun vcvthNF16U32(a: UInt, n: Int): F16 {
    fun externVcvthNF16U32(a1: UInt, n: Int): F16 = F16()
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNF16U32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNF16U64)
 */
public fun vcvthNF16U64(a: ULong, n: Int): F16 {
    fun externVcvthNF16U64(a1: ULong, n: Int): F16 = F16()
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNF16U64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNS16F16)
 */
public fun vcvthNS16F16(a: F16, n: Int): Short {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvthNS32F16(a, n).toShort()
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNS32F16)
 */
public fun vcvthNS32F16(a: F16, n: Int): Int {
    fun externVcvthNS32F16(a1: F16, n: Int): Int = 0
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNS32F16(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNS64F16)
 */
public fun vcvthNS64F16(a: F16, n: Int): Long {
    fun externVcvthNS64F16(a1: F16, n: Int): Long = 0L
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNS64F16(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNU16F16)
 */
public fun vcvthNU16F16(a: F16, n: Int): UShort {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvthNU32F16(a, n).toUShort()
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNU32F16)
 */
public fun vcvthNU32F16(a: F16, n: Int): UInt {
    fun externVcvthNU32F16(a1: F16, n: Int): UInt = 0u
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNU32F16(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthNU64F16)
 */
public fun vcvthNU64F16(a: F16, n: Int): ULong {
    fun externVcvthNU64F16(a1: F16, n: Int): ULong = 0uL
    run {
        check(n >= 1 && n <= 16)
    }
    return externVcvthNU64F16(a, n)
}

/**
 * Floating-point convert to signed fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthS16F16)
 */
public fun vcvthS16F16(a: F16): Short = a.toShort()

/**
 * Floating-point convert to signed fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthS32F16)
 */
public fun vcvthS32F16(a: F16): Int = a.toInt()

/**
 * Floating-point convert to signed fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthS64F16)
 */
public fun vcvthS64F16(a: F16): Long = a.toLong()

/**
 * Floating-point convert to unsigned fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthU16F16)
 */
public fun vcvthU16F16(a: F16): UShort = a.toUShort()

/**
 * Floating-point convert to unsigned fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthU32F16)
 */
public fun vcvthU32F16(a: F16): UInt = a.toUInt()

/**
 * Floating-point convert to unsigned fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvthU64F16)
 */
public fun vcvthU64F16(a: F16): ULong = a.toULong()

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmS16F16)
 */
public fun vcvtmS16F16(a: Float16x4T): Int16x4T {
    fun externVcvtmS16F16(a1: Float16x4T): Int16x4T = Int16x4T()
    return externVcvtmS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqS16F16)
 */
public fun vcvtmqS16F16(a: Float16x8T): Int16x8T {
    fun externVcvtmqS16F16(a1: Float16x8T): Int16x8T = Int16x8T()
    return externVcvtmqS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmS32F32)
 */
public fun vcvtmS32F32(a: Float32x2T): Int32x2T {
    fun externVcvtmS32F32(a1: Float32x2T): Int32x2T = Int32x2T()
    return externVcvtmS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqS32F32)
 */
public fun vcvtmqS32F32(a: Float32x4T): Int32x4T {
    fun externVcvtmqS32F32(a1: Float32x4T): Int32x4T = Int32x4T()
    return externVcvtmqS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmS64F64)
 */
public fun vcvtmS64F64(a: Float64x1T): Int64x1T {
    fun externVcvtmS64F64(a1: Float64x1T): Int64x1T = Int64x1T()
    return externVcvtmS64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqS64F64)
 */
public fun vcvtmqS64F64(a: Float64x2T): Int64x2T {
    fun externVcvtmqS64F64(a1: Float64x2T): Int64x2T = Int64x2T()
    return externVcvtmqS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmU16F16)
 */
public fun vcvtmU16F16(a: Float16x4T): Uint16x4T {
    fun externVcvtmU16F16(a1: Float16x4T): Uint16x4T = Uint16x4T()
    return externVcvtmU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqU16F16)
 */
public fun vcvtmqU16F16(a: Float16x8T): Uint16x8T {
    fun externVcvtmqU16F16(a1: Float16x8T): Uint16x8T = Uint16x8T()
    return externVcvtmqU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmU32F32)
 */
public fun vcvtmU32F32(a: Float32x2T): Uint32x2T {
    fun externVcvtmU32F32(a1: Float32x2T): Uint32x2T = Uint32x2T()
    return externVcvtmU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqU32F32)
 */
public fun vcvtmqU32F32(a: Float32x4T): Uint32x4T {
    fun externVcvtmqU32F32(a1: Float32x4T): Uint32x4T = Uint32x4T()
    return externVcvtmqU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmU64F64)
 */
public fun vcvtmU64F64(a: Float64x1T): Uint64x1T {
    fun externVcvtmU64F64(a1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcvtmU64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmqU64F64)
 */
public fun vcvtmqU64F64(a: Float64x2T): Uint64x2T {
    fun externVcvtmqU64F64(a1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcvtmqU64F64(a)
}

/**
 * Floating-point convert to integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhS16F16)
 */
public fun vcvtmhS16F16(a: F16): Short {
    fun externVcvtmhS16F16(a1: F16): Short = 0
    return externVcvtmhS16F16(a)
}

/**
 * Floating-point convert to integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhS32F16)
 */
public fun vcvtmhS32F16(a: F16): Int {
    fun externVcvtmhS32F16(a1: F16): Int = 0
    return externVcvtmhS32F16(a)
}

/**
 * Floating-point convert to integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhS64F16)
 */
public fun vcvtmhS64F16(a: F16): Long {
    fun externVcvtmhS64F16(a1: F16): Long = 0L
    return externVcvtmhS64F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhU16F16)
 */
public fun vcvtmhU16F16(a: F16): UShort {
    fun externVcvtmhU16F16(a1: F16): UShort = 0u
    return externVcvtmhU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhU32F16)
 */
public fun vcvtmhU32F16(a: F16): UInt {
    fun externVcvtmhU32F16(a1: F16): UInt = 0u
    return externVcvtmhU32F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding towards minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmhU64F16)
 */
public fun vcvtmhU64F16(a: F16): ULong {
    fun externVcvtmhU64F16(a1: F16): ULong = 0uL
    return externVcvtmhU64F16(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmsS32F32)
 */
public fun vcvtmsS32F32(a: Float): Int {
    fun externVcvtmsS32F32(a1: Float): Int = 0
    return externVcvtmsS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmdS64F64)
 */
public fun vcvtmdS64F64(a: Double): Long {
    fun externVcvtmdS64F64(a1: Double): Long = 0L
    return externVcvtmdS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmsU32F32)
 */
public fun vcvtmsU32F32(a: Float): UInt {
    fun externVcvtmsU32F32(a1: Float): UInt = 0u
    return externVcvtmsU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtmdU64F64)
 */
public fun vcvtmdU64F64(a: Double): ULong {
    fun externVcvtmdU64F64(a1: Double): ULong = 0uL
    return externVcvtmdU64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnS16F16)
 */
public fun vcvtnS16F16(a: Float16x4T): Int16x4T {
    fun externVcvtnS16F16(a1: Float16x4T): Int16x4T = Int16x4T()
    return externVcvtnS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqS16F16)
 */
public fun vcvtnqS16F16(a: Float16x8T): Int16x8T {
    fun externVcvtnqS16F16(a1: Float16x8T): Int16x8T = Int16x8T()
    return externVcvtnqS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnS32F32)
 */
public fun vcvtnS32F32(a: Float32x2T): Int32x2T {
    fun externVcvtnS32F32(a1: Float32x2T): Int32x2T = Int32x2T()
    return externVcvtnS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqS32F32)
 */
public fun vcvtnqS32F32(a: Float32x4T): Int32x4T {
    fun externVcvtnqS32F32(a1: Float32x4T): Int32x4T = Int32x4T()
    return externVcvtnqS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnS64F64)
 */
public fun vcvtnS64F64(a: Float64x1T): Int64x1T {
    fun externVcvtnS64F64(a1: Float64x1T): Int64x1T = Int64x1T()
    return externVcvtnS64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqS64F64)
 */
public fun vcvtnqS64F64(a: Float64x2T): Int64x2T {
    fun externVcvtnqS64F64(a1: Float64x2T): Int64x2T = Int64x2T()
    return externVcvtnqS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnU16F16)
 */
public fun vcvtnU16F16(a: Float16x4T): Uint16x4T {
    fun externVcvtnU16F16(a1: Float16x4T): Uint16x4T = Uint16x4T()
    return externVcvtnU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqU16F16)
 */
public fun vcvtnqU16F16(a: Float16x8T): Uint16x8T {
    fun externVcvtnqU16F16(a1: Float16x8T): Uint16x8T = Uint16x8T()
    return externVcvtnqU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnU32F32)
 */
public fun vcvtnU32F32(a: Float32x2T): Uint32x2T {
    fun externVcvtnU32F32(a1: Float32x2T): Uint32x2T = Uint32x2T()
    return externVcvtnU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqU32F32)
 */
public fun vcvtnqU32F32(a: Float32x4T): Uint32x4T {
    fun externVcvtnqU32F32(a1: Float32x4T): Uint32x4T = Uint32x4T()
    return externVcvtnqU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnU64F64)
 */
public fun vcvtnU64F64(a: Float64x1T): Uint64x1T {
    fun externVcvtnU64F64(a1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcvtnU64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnqU64F64)
 */
public fun vcvtnqU64F64(a: Float64x2T): Uint64x2T {
    fun externVcvtnqU64F64(a1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcvtnqU64F64(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhS16F16)
 */
public fun vcvtnhS16F16(a: F16): Short {
    fun externVcvtnhS16F16(a1: F16): Short = 0
    return externVcvtnhS16F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhS32F16)
 */
public fun vcvtnhS32F16(a: F16): Int {
    fun externVcvtnhS32F16(a1: F16): Int = 0
    return externVcvtnhS32F16(a)
}

/**
 * Floating-point convert to integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhS64F16)
 */
public fun vcvtnhS64F16(a: F16): Long {
    fun externVcvtnhS64F16(a1: F16): Long = 0L
    return externVcvtnhS64F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhU16F16)
 */
public fun vcvtnhU16F16(a: F16): UShort {
    fun externVcvtnhU16F16(a1: F16): UShort = 0u
    return externVcvtnhU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhU32F16)
 */
public fun vcvtnhU32F16(a: F16): UInt {
    fun externVcvtnhU32F16(a1: F16): UInt = 0u
    return externVcvtnhU32F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnhU64F16)
 */
public fun vcvtnhU64F16(a: F16): ULong {
    fun externVcvtnhU64F16(a1: F16): ULong = 0uL
    return externVcvtnhU64F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnsS32F32)
 */
public fun vcvtnsS32F32(a: Float): Int {
    fun externVcvtnsS32F32(a1: Float): Int = 0
    return externVcvtnsS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtndS64F64)
 */
public fun vcvtndS64F64(a: Double): Long {
    fun externVcvtndS64F64(a1: Double): Long = 0L
    return externVcvtndS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtnsU32F32)
 */
public fun vcvtnsU32F32(a: Float): UInt {
    fun externVcvtnsU32F32(a1: Float): UInt = 0u
    return externVcvtnsU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtndU64F64)
 */
public fun vcvtndU64F64(a: Double): ULong {
    fun externVcvtndU64F64(a1: Double): ULong = 0uL
    return externVcvtndU64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpS16F16)
 */
public fun vcvtpS16F16(a: Float16x4T): Int16x4T {
    fun externVcvtpS16F16(a1: Float16x4T): Int16x4T = Int16x4T()
    return externVcvtpS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqS16F16)
 */
public fun vcvtpqS16F16(a: Float16x8T): Int16x8T {
    fun externVcvtpqS16F16(a1: Float16x8T): Int16x8T = Int16x8T()
    return externVcvtpqS16F16(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpS32F32)
 */
public fun vcvtpS32F32(a: Float32x2T): Int32x2T {
    fun externVcvtpS32F32(a1: Float32x2T): Int32x2T = Int32x2T()
    return externVcvtpS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqS32F32)
 */
public fun vcvtpqS32F32(a: Float32x4T): Int32x4T {
    fun externVcvtpqS32F32(a1: Float32x4T): Int32x4T = Int32x4T()
    return externVcvtpqS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpS64F64)
 */
public fun vcvtpS64F64(a: Float64x1T): Int64x1T {
    fun externVcvtpS64F64(a1: Float64x1T): Int64x1T = Int64x1T()
    return externVcvtpS64F64(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqS64F64)
 */
public fun vcvtpqS64F64(a: Float64x2T): Int64x2T {
    fun externVcvtpqS64F64(a1: Float64x2T): Int64x2T = Int64x2T()
    return externVcvtpqS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpU16F16)
 */
public fun vcvtpU16F16(a: Float16x4T): Uint16x4T {
    fun externVcvtpU16F16(a1: Float16x4T): Uint16x4T = Uint16x4T()
    return externVcvtpU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqU16F16)
 */
public fun vcvtpqU16F16(a: Float16x8T): Uint16x8T {
    fun externVcvtpqU16F16(a1: Float16x8T): Uint16x8T = Uint16x8T()
    return externVcvtpqU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpU32F32)
 */
public fun vcvtpU32F32(a: Float32x2T): Uint32x2T {
    fun externVcvtpU32F32(a1: Float32x2T): Uint32x2T = Uint32x2T()
    return externVcvtpU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqU32F32)
 */
public fun vcvtpqU32F32(a: Float32x4T): Uint32x4T {
    fun externVcvtpqU32F32(a1: Float32x4T): Uint32x4T = Uint32x4T()
    return externVcvtpqU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpU64F64)
 */
public fun vcvtpU64F64(a: Float64x1T): Uint64x1T {
    fun externVcvtpU64F64(a1: Float64x1T): Uint64x1T = Uint64x1T()
    return externVcvtpU64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpqU64F64)
 */
public fun vcvtpqU64F64(a: Float64x2T): Uint64x2T {
    fun externVcvtpqU64F64(a1: Float64x2T): Uint64x2T = Uint64x2T()
    return externVcvtpqU64F64(a)
}

/**
 * Floating-point convert to integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphS16F16)
 */
public fun vcvtphS16F16(a: F16): Short {
    fun externVcvtphS16F16(a1: F16): Short = 0
    return externVcvtphS16F16(a)
}

/**
 * Floating-point convert to integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphS32F16)
 */
public fun vcvtphS32F16(a: F16): Int {
    fun externVcvtphS32F16(a1: F16): Int = 0
    return externVcvtphS32F16(a)
}

/**
 * Floating-point convert to integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphS64F16)
 */
public fun vcvtphS64F16(a: F16): Long {
    fun externVcvtphS64F16(a1: F16): Long = 0L
    return externVcvtphS64F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphU16F16)
 */
public fun vcvtphU16F16(a: F16): UShort {
    fun externVcvtphU16F16(a1: F16): UShort = 0u
    return externVcvtphU16F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphU32F16)
 */
public fun vcvtphU32F16(a: F16): UInt {
    fun externVcvtphU32F16(a1: F16): UInt = 0u
    return externVcvtphU32F16(a)
}

/**
 * Floating-point convert to unsigned integer, rounding to plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtphU64F16)
 */
public fun vcvtphU64F16(a: F16): ULong {
    fun externVcvtphU64F16(a1: F16): ULong = 0uL
    return externVcvtphU64F16(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpsS32F32)
 */
public fun vcvtpsS32F32(a: Float): Int {
    fun externVcvtpsS32F32(a1: Float): Int = 0
    return externVcvtpsS32F32(a)
}

/**
 * Floating-point convert to signed integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpdS64F64)
 */
public fun vcvtpdS64F64(a: Double): Long {
    fun externVcvtpdS64F64(a1: Double): Long = 0L
    return externVcvtpdS64F64(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpsU32F32)
 */
public fun vcvtpsU32F32(a: Float): UInt {
    fun externVcvtpsU32F32(a1: Float): UInt = 0u
    return externVcvtpsU32F32(a)
}

/**
 * Floating-point convert to unsigned integer, rounding toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtpdU64F64)
 */
public fun vcvtpdU64F64(a: Double): ULong {
    fun externVcvtpdU64F64(a1: Double): ULong = 0uL
    return externVcvtpdU64F64(a)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsF32U32)
 */
public fun vcvtsF32U32(a: UInt): Float = a.toFloat()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdF64U64)
 */
public fun vcvtdF64U64(a: ULong): Double = a.toDouble()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsNF32S32)
 */
public fun vcvtsNF32S32(a: Int, n: Int): Float {
    fun externVcvtsNF32S32(a1: Int, n: Int): Float = 0.0f
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtsNF32S32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdNF64S64)
 */
public fun vcvtdNF64S64(a: Long, n: Int): Double {
    fun externVcvtdNF64S64(a1: Long, n: Int): Double = 0.0
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtdNF64S64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsNF32U32)
 */
public fun vcvtsNF32U32(a: UInt, n: Int): Float {
    fun externVcvtsNF32U32(a1: UInt, n: Int): Float = 0.0f
    run {
        check(n >= 1 && n <= 32)
    }
    return externVcvtsNF32U32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdNF64U64)
 */
public fun vcvtdNF64U64(a: ULong, n: Int): Double {
    fun externVcvtdNF64U64(a1: ULong, n: Int): Double = 0.0
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtdNF64U64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsNS32F32)
 */
public fun vcvtsNS32F32(a: Float, n: Int): Int {
    fun externVcvtsNS32F32(a1: Float, n: Int): Int = 0
    run {
        check(n >= 1 && n <= 32)
    }
    return externVcvtsNS32F32(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdNS64F64)
 */
public fun vcvtdNS64F64(a: Double, n: Int): Long {
    fun externVcvtdNS64F64(a1: Double, n: Int): Long = 0L
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtdNS64F64(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsNU32F32)
 */
public fun vcvtsNU32F32(a: Float, n: Int): UInt {
    fun externVcvtsNU32F32(a1: Float, n: Int): UInt = 0u
    run {
        check(n >= 1 && n <= 32)
    }
    return externVcvtsNU32F32(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdNU64F64)
 */
public fun vcvtdNU64F64(a: Double, n: Int): ULong {
    fun externVcvtdNU64F64(a1: Double, n: Int): ULong = 0uL
    run {
        check(n >= 1 && n <= 64)
    }
    return externVcvtdNU64F64(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsS32F32)
 */
public fun vcvtsS32F32(a: Float): Int = a.toInt()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdS64F64)
 */
public fun vcvtdS64F64(a: Double): Long = a.toLong()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtsU32F32)
 */
public fun vcvtsU32F32(a: Float): UInt = a.toUInt()

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtdU64F64)
 */
public fun vcvtdU64F64(a: Double): ULong = a.toULong()

/**
 * Floating-point convert to lower precision narrow, rounding to odd
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtxF32F64)
 */
public fun vcvtxF32F64(a: Float64x2T): Float32x2T {
    fun externVcvtxF32F64(a1: Float64x2T): Float32x2T = Float32x2T()
    return externVcvtxF32F64(a)
}

/**
 * Floating-point convert to lower precision narrow, rounding to odd
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtxHighF32F64)
 */
public fun vcvtxHighF32F64(a: Float32x2T, b: Float64x2T): Float32x4T = vcombineF32(a, vcvtxF32F64(b))

/**
 * Floating-point convert to lower precision narrow, rounding to odd
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtxdF32F64)
 */
public fun vcvtxdF32F64(a: Double): Float = vgetLaneF32(vcvtxF32F64(vdupqNF64(a)), 0.0f)

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivF16)
 */
public fun vdivF16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivqF16)
 */
public fun vdivqF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivF32)
 */
public fun vdivF32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivqF32)
 */
public fun vdivqF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivF64)
 */
public fun vdivF64(a: Float64x1T, b: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivqF64)
 */
public fun vdivqF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Divide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdivhF16)
 */
public fun vdivhF16(a: F16, b: F16): F16 = a / b

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneF64)
 */
public fun vdupLaneF64(a: Float64x1T, n: Int): Float64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneP64)
 */
public fun vdupLaneP64(a: Poly64x1T, n: Int): Poly64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqF64)
 */
public fun vdupLaneqF64(a: Float64x2T, n: Int): Float64x1T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Double, Float64x1T>(vgetqLaneF64(a, n), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqP64)
 */
public fun vdupLaneqP64(a: Poly64x2T, n: Int): Poly64x1T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<P64, Poly64x1T>(vgetqLaneP64(a, n), UnresolvedTypeLayout<P64>(), UnresolvedTypeLayout<Poly64x1T>())
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneS8)
 */
public fun vdupbLaneS8(a: Int8x8T, n: Int): Byte {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneS8(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneqS16)
 */
public fun vduphLaneqS16(a: Int16x8T, n: Int): Short {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetqLaneS16(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneU8)
 */
public fun vdupbLaneU8(a: Uint8x8T, n: Int): UByte {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneU8(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneqU16)
 */
public fun vduphLaneqU16(a: Uint16x8T, n: Int): UShort {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetqLaneU16(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneP8)
 */
public fun vdupbLaneP8(a: Poly8x8T, n: Int): P8 {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneP8(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneqP16)
 */
public fun vduphLaneqP16(a: Poly16x8T, n: Int): P16 {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetqLaneP16(a, n)
}

/**
 * Extract an element from a vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneqS8)
 */
public fun vdupbLaneqS8(a: Int8x16T, n: Int): Byte {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetqLaneS8(a, n)
}

/**
 * Extract an element from a vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneqU8)
 */
public fun vdupbLaneqU8(a: Uint8x16T, n: Int): UByte {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetqLaneU8(a, n)
}

/**
 * Extract an element from a vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupbLaneqP8)
 */
public fun vdupbLaneqP8(a: Poly8x16T, n: Int): P8 {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetqLaneP8(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneF64)
 */
public fun vdupdLaneF64(a: Float64x1T, n: Int): Double {
    run {
        check(n == 0)
    }
    return vgetLaneF64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneS64)
 */
public fun vdupdLaneS64(a: Int64x1T, n: Int): Long {
    run {
        check(n == 0)
    }
    return vgetLaneS64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneU64)
 */
public fun vdupdLaneU64(a: Uint64x1T, n: Int): ULong {
    run {
        check(n == 0)
    }
    return vgetLaneU64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneF16)
 */
public fun vduphLaneF16(a: Float16x4T, n: Int): F16 {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetLaneF16(a, n)
}

/**
 * Extract an element from a vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneqF16)
 */
public fun vduphLaneqF16(a: Float16x8T, n: Int): F16 {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetqLaneF16(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneF64)
 */
public fun vdupqLaneF64(a: Float64x1T, n: Int): Float64x2T {
    run {
        check(n == 0)
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); Float64x2T() }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneP64)
 */
public fun vdupqLaneP64(a: Poly64x1T, n: Int): Poly64x2T {
    run {
        check(n == 0)
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); Poly64x2T() }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqF64)
 */
public fun vdupqLaneqF64(a: Float64x2T, n: Int): Float64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); Float64x2T() }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqP64)
 */
public fun vdupqLaneqP64(a: Poly64x2T, n: Int): Poly64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); Poly64x2T() }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneF32)
 */
public fun vdupsLaneF32(a: Float32x2T, n: Int): Float {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetLaneF32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneqF64)
 */
public fun vdupdLaneqF64(a: Float64x2T, n: Int): Double {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetqLaneF64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneS32)
 */
public fun vdupsLaneS32(a: Int32x2T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetLaneS32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneqS64)
 */
public fun vdupdLaneqS64(a: Int64x2T, n: Int): Long {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetqLaneS64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneU32)
 */
public fun vdupsLaneU32(a: Uint32x2T, n: Int): UInt {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetLaneU32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupdLaneqU64)
 */
public fun vdupdLaneqU64(a: Uint64x2T, n: Int): ULong {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vgetqLaneU64(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneqF32)
 */
public fun vdupsLaneqF32(a: Float32x4T, n: Int): Float {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetqLaneF32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneS16)
 */
public fun vduphLaneS16(a: Int16x4T, n: Int): Short {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetLaneS16(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneqS32)
 */
public fun vdupsLaneqS32(a: Int32x4T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetqLaneS32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneU16)
 */
public fun vduphLaneU16(a: Uint16x4T, n: Int): UShort {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetLaneU16(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupsLaneqU32)
 */
public fun vdupsLaneqU32(a: Uint32x4T, n: Int): UInt {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetqLaneU32(a, n)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vduphLaneP16)
 */
public fun vduphLaneP16(a: Poly16x4T, n: Int): P16 {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vgetLaneP16(a, n)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qS8)
 */
public fun veor3qS8(a: Int8x16T, b: Int8x16T, c: Int8x16T): Int8x16T {
    fun externVeor3qS8(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T): Int8x16T = Int8x16T()
    return externVeor3qS8(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qS16)
 */
public fun veor3qS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T {
    fun externVeor3qS16(a1: Int16x8T, b1: Int16x8T, c1: Int16x8T): Int16x8T = Int16x8T()
    return externVeor3qS16(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qS32)
 */
public fun veor3qS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T {
    fun externVeor3qS32(a1: Int32x4T, b1: Int32x4T, c1: Int32x4T): Int32x4T = Int32x4T()
    return externVeor3qS32(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qS64)
 */
public fun veor3qS64(a: Int64x2T, b: Int64x2T, c: Int64x2T): Int64x2T {
    fun externVeor3qS64(a1: Int64x2T, b1: Int64x2T, c1: Int64x2T): Int64x2T = Int64x2T()
    return externVeor3qS64(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qU8)
 */
public fun veor3qU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T {
    fun externVeor3qU8(a1: Uint8x16T, b1: Uint8x16T, c1: Uint8x16T): Uint8x16T = Uint8x16T()
    return externVeor3qU8(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qU16)
 */
public fun veor3qU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T {
    fun externVeor3qU16(a1: Uint16x8T, b1: Uint16x8T, c1: Uint16x8T): Uint16x8T = Uint16x8T()
    return externVeor3qU16(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qU32)
 */
public fun veor3qU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    fun externVeor3qU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVeor3qU32(a, b, c)
}

/**
 * Three-way exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veor3qU64)
 */
public fun veor3qU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    fun externVeor3qU64(a1: Uint64x2T, b1: Uint64x2T, c1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVeor3qU64(a, b, c)
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqF64)
 */
public fun vextqF64(a: Float64x2T, b: Float64x2T, n: Int): Float64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); Float64x2T() }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqP64)
 */
public fun vextqP64(a: Poly64x2T, b: Poly64x2T, n: Int): Poly64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); Poly64x2T() }
}

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaF64)
 */
public fun vfmaF64(a: Float64x1T, b: Float64x1T, c: Float64x1T): Float64x1T = run { val simdArg0 = b; val simdArg1 = c; val simdArg2 = a; simdArg0 }

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneF16)
 */
public fun vfmaLaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmaF16(a, b, vdupNF16(vgetLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneqF16)
 */
public fun vfmaLaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmaF16(a, b, vdupNF16(vgetqLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneF16)
 */
public fun vfmaqLaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmaqF16(a, b, vdupqNF16(vgetLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneqF16)
 */
public fun vfmaqLaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmaqF16(a, b, vdupqNF16(vgetqLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneF32)
 */
public fun vfmaLaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmaF32(a, b, vdupNF32(vgetLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneqF32)
 */
public fun vfmaLaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmaF32(a, b, vdupNF32(vgetqLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneF32)
 */
public fun vfmaqLaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmaqF32(a, b, vdupqNF32(vgetLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneqF32)
 */
public fun vfmaqLaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmaqF32(a, b, vdupqNF32(vgetqLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneqF64)
 */
public fun vfmaqLaneqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmaqF64(a, b, vdupqNF64(vgetqLaneF64(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneF64)
 */
public fun vfmaLaneF64(a: Float64x1T, b: Float64x1T, c: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return vfmaF64(a, b, vdupNF64(vgetLaneF64(c, lane)))
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaLaneqF64)
 */
public fun vfmaLaneqF64(a: Float64x1T, b: Float64x1T, c: Float64x2T, lane: Int): Float64x1T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmaF64(a, b, vdupNF64(vgetqLaneF64(c, lane)))
}

/**
 * Floating-point fused Multiply-Subtract from accumulator.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaNF16)
 */
public fun vfmaNF16(a: Float16x4T, b: Float16x4T, c: F16): Float16x4T = vfmaF16(a, b, vdupNF16(c))

/**
 * Floating-point fused Multiply-Subtract from accumulator.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqNF16)
 */
public fun vfmaqNF16(a: Float16x8T, b: Float16x8T, c: F16): Float16x8T = vfmaqF16(a, b, vdupqNF16(c))

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaNF64)
 */
public fun vfmaNF64(a: Float64x1T, b: Float64x1T, c: Double): Float64x1T = vfmaF64(a, b, vdupNF64(c))

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmadLaneF64)
 */
public fun vfmadLaneF64(a: Double, b: Double, c: Float64x1T, lane: Int): Double {
    run {
        check(lane == 0)
    }
    val c1: Double = vgetLaneF64(c, lane)
    return fma(b, c1, a)
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmahF16)
 */
public fun vfmahF16(a: F16, b: F16, c: F16): F16 = fma(b, c, a)

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmahLaneF16)
 */
public fun vfmahLaneF16(a: F16, b: F16, v: Float16x4T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c: F16 = vgetLaneF16(v, lane)
    return vfmahF16(a, b, c)
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmahLaneqF16)
 */
public fun vfmahLaneqF16(a: F16, b: F16, v: Float16x8T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c: F16 = vgetqLaneF16(v, lane)
    return vfmahF16(a, b, c)
}

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqF64)
 */
public fun vfmaqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T = run { val simdArg0 = b; val simdArg1 = c; val simdArg2 = a; simdArg0 }

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqLaneF64)
 */
public fun vfmaqLaneF64(a: Float64x2T, b: Float64x2T, c: Float64x1T, lane: Int): Float64x2T {
    run {
        check(lane == 0)
    }
    return vfmaqF64(a, b, vdupqNF64(vgetLaneF64(c, lane)))
}

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqNF64)
 */
public fun vfmaqNF64(a: Float64x2T, b: Float64x2T, c: Double): Float64x2T = vfmaqF64(a, b, vdupqNF64(c))

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmasLaneF32)
 */
public fun vfmasLaneF32(a: Float, b: Float, c: Float32x2T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1: Float = vgetLaneF32(c, lane)
    return fma(b, c1, a)
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmasLaneqF32)
 */
public fun vfmasLaneqF32(a: Float, b: Float, c: Float32x4T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1: Float = vgetqLaneF32(c, lane)
    return fma(b, c1, a)
}

/**
 * Floating-point fused multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmadLaneqF64)
 */
public fun vfmadLaneqF64(a: Double, b: Double, c: Float64x2T, lane: Int): Double {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1: Double = vgetqLaneF64(c, lane)
    return fma(b, c1, a)
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalHighF16)
 */
public fun vfmlalHighF16(r: Float32x2T, a: Float16x4T, b: Float16x4T): Float32x2T {
    fun externVfmlalHighF16(r1: Float32x2T, a1: Float16x4T, b1: Float16x4T): Float32x2T = Float32x2T()
    return externVfmlalHighF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqHighF16)
 */
public fun vfmlalqHighF16(r: Float32x4T, a: Float16x8T, b: Float16x8T): Float32x4T {
    fun externVfmlalqHighF16(r1: Float32x4T, a1: Float16x8T, b1: Float16x8T): Float32x4T = Float32x4T()
    return externVfmlalqHighF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalLaneHighF16)
 */
public fun vfmlalLaneHighF16(r: Float32x2T, a: Float16x4T, b: Float16x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlalHighF16(r, a, vdupLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalLaneqHighF16)
 */
public fun vfmlalLaneqHighF16(r: Float32x2T, a: Float16x4T, b: Float16x8T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlalHighF16(r, a, vdupLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqLaneHighF16)
 */
public fun vfmlalqLaneHighF16(r: Float32x4T, a: Float16x8T, b: Float16x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlalqHighF16(r, a, vdupqLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqLaneqHighF16)
 */
public fun vfmlalqLaneqHighF16(r: Float32x4T, a: Float16x8T, b: Float16x8T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlalqHighF16(r, a, vdupqLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalLaneLowF16)
 */
public fun vfmlalLaneLowF16(r: Float32x2T, a: Float16x4T, b: Float16x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlalLowF16(r, a, vdupLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalLaneqLowF16)
 */
public fun vfmlalLaneqLowF16(r: Float32x2T, a: Float16x4T, b: Float16x8T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlalLowF16(r, a, vdupLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqLaneLowF16)
 */
public fun vfmlalqLaneLowF16(r: Float32x4T, a: Float16x8T, b: Float16x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlalqLowF16(r, a, vdupqLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqLaneqLowF16)
 */
public fun vfmlalqLaneqLowF16(r: Float32x4T, a: Float16x8T, b: Float16x8T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlalqLowF16(r, a, vdupqLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalLowF16)
 */
public fun vfmlalLowF16(r: Float32x2T, a: Float16x4T, b: Float16x4T): Float32x2T {
    fun externVfmlalLowF16(r1: Float32x2T, a1: Float16x4T, b1: Float16x4T): Float32x2T = Float32x2T()
    return externVfmlalLowF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Add Long to accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlalqLowF16)
 */
public fun vfmlalqLowF16(r: Float32x4T, a: Float16x8T, b: Float16x8T): Float32x4T {
    fun externVfmlalqLowF16(r1: Float32x4T, a1: Float16x8T, b1: Float16x8T): Float32x4T = Float32x4T()
    return externVfmlalqLowF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslHighF16)
 */
public fun vfmlslHighF16(r: Float32x2T, a: Float16x4T, b: Float16x4T): Float32x2T {
    fun externVfmlslHighF16(r1: Float32x2T, a1: Float16x4T, b1: Float16x4T): Float32x2T = Float32x2T()
    return externVfmlslHighF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqHighF16)
 */
public fun vfmlslqHighF16(r: Float32x4T, a: Float16x8T, b: Float16x8T): Float32x4T {
    fun externVfmlslqHighF16(r1: Float32x4T, a1: Float16x8T, b1: Float16x8T): Float32x4T = Float32x4T()
    return externVfmlslqHighF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslLaneHighF16)
 */
public fun vfmlslLaneHighF16(r: Float32x2T, a: Float16x4T, b: Float16x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlslHighF16(r, a, vdupLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslLaneqHighF16)
 */
public fun vfmlslLaneqHighF16(r: Float32x2T, a: Float16x4T, b: Float16x8T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlslHighF16(r, a, vdupLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqLaneHighF16)
 */
public fun vfmlslqLaneHighF16(r: Float32x4T, a: Float16x8T, b: Float16x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlslqHighF16(r, a, vdupqLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqLaneqHighF16)
 */
public fun vfmlslqLaneqHighF16(r: Float32x4T, a: Float16x8T, b: Float16x8T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlslqHighF16(r, a, vdupqLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslLaneLowF16)
 */
public fun vfmlslLaneLowF16(r: Float32x2T, a: Float16x4T, b: Float16x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlslLowF16(r, a, vdupLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslLaneqLowF16)
 */
public fun vfmlslLaneqLowF16(r: Float32x2T, a: Float16x4T, b: Float16x8T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlslLowF16(r, a, vdupLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqLaneLowF16)
 */
public fun vfmlslqLaneLowF16(r: Float32x4T, a: Float16x8T, b: Float16x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmlslqLowF16(r, a, vdupqLaneF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (by element).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqLaneqLowF16)
 */
public fun vfmlslqLaneqLowF16(r: Float32x4T, a: Float16x8T, b: Float16x8T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmlslqLowF16(r, a, vdupqLaneqF16(b, lane))
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslLowF16)
 */
public fun vfmlslLowF16(r: Float32x2T, a: Float16x4T, b: Float16x4T): Float32x2T {
    fun externVfmlslLowF16(r1: Float32x2T, a1: Float16x4T, b1: Float16x4T): Float32x2T = Float32x2T()
    return externVfmlslLowF16(r, a, b)
}

/**
 * Floating-point fused Multiply-Subtract Long from accumulator (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmlslqLowF16)
 */
public fun vfmlslqLowF16(r: Float32x4T, a: Float16x8T, b: Float16x8T): Float32x4T {
    fun externVfmlslqLowF16(r1: Float32x4T, a1: Float16x8T, b1: Float16x8T): Float32x4T = Float32x4T()
    return externVfmlslqLowF16(r, a, b)
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsF64)
 */
public fun vfmsF64(a: Float64x1T, b: Float64x1T, c: Float64x1T): Float64x1T =
    run {
        val b1: Float64x1T = run { val simdArg0 = b; simdArg0 }
        vfmaF64(a, b1, c)
    }

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneF16)
 */
public fun vfmsLaneF16(a: Float16x4T, b: Float16x4T, c: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmsF16(a, b, vdupNF16(vgetLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneqF16)
 */
public fun vfmsLaneqF16(a: Float16x4T, b: Float16x4T, c: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmsF16(a, b, vdupNF16(vgetqLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneF16)
 */
public fun vfmsqLaneF16(a: Float16x8T, b: Float16x8T, c: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmsqF16(a, b, vdupqNF16(vgetLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneqF16)
 */
public fun vfmsqLaneqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vfmsqF16(a, b, vdupqNF16(vgetqLaneF16(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneF32)
 */
public fun vfmsLaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmsF32(a, b, vdupNF32(vgetLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneqF32)
 */
public fun vfmsLaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmsF32(a, b, vdupNF32(vgetqLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneF32)
 */
public fun vfmsqLaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmsqF32(a, b, vdupqNF32(vgetLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneqF32)
 */
public fun vfmsqLaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vfmsqF32(a, b, vdupqNF32(vgetqLaneF32(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneqF64)
 */
public fun vfmsqLaneqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmsqF64(a, b, vdupqNF64(vgetqLaneF64(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneF64)
 */
public fun vfmsLaneF64(a: Float64x1T, b: Float64x1T, c: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return vfmsF64(a, b, vdupNF64(vgetLaneF64(c, lane)))
}

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsLaneqF64)
 */
public fun vfmsLaneqF64(a: Float64x1T, b: Float64x1T, c: Float64x2T, lane: Int): Float64x1T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vfmsF64(a, b, vdupNF64(vgetqLaneF64(c, lane)))
}

/**
 * Floating-point fused Multiply-Subtract from accumulator.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsNF16)
 */
public fun vfmsNF16(a: Float16x4T, b: Float16x4T, c: F16): Float16x4T = vfmsF16(a, b, vdupNF16(c))

/**
 * Floating-point fused Multiply-Subtract from accumulator.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqNF16)
 */
public fun vfmsqNF16(a: Float16x8T, b: Float16x8T, c: F16): Float16x8T = vfmsqF16(a, b, vdupqNF16(c))

/**
 * Floating-point fused Multiply-subtract to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsNF64)
 */
public fun vfmsNF64(a: Float64x1T, b: Float64x1T, c: Double): Float64x1T = vfmsF64(a, b, vdupNF64(c))

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmshF16)
 */
public fun vfmshF16(a: F16, b: F16, c: F16): F16 = vfmahF16(a, -b, c)

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmshLaneF16)
 */
public fun vfmshLaneF16(a: F16, b: F16, v: Float16x4T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c: F16 = vgetLaneF16(v, lane)
    return vfmshF16(a, b, c)
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmshLaneqF16)
 */
public fun vfmshLaneqF16(a: F16, b: F16, v: Float16x8T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c: F16 = vgetqLaneF16(v, lane)
    return vfmshF16(a, b, c)
}

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqF64)
 */
public fun vfmsqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T =
    run {
        val b1: Float64x2T = run { val simdArg0 = b; simdArg0 }
        vfmaqF64(a, b1, c)
    }

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqLaneF64)
 */
public fun vfmsqLaneF64(a: Float64x2T, b: Float64x2T, c: Float64x1T, lane: Int): Float64x2T {
    run {
        check(lane == 0)
    }
    return vfmsqF64(a, b, vdupqNF64(vgetLaneF64(c, lane)))
}

/**
 * Floating-point fused Multiply-subtract to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqNF64)
 */
public fun vfmsqNF64(a: Float64x2T, b: Float64x2T, c: Double): Float64x2T = vfmsqF64(a, b, vdupqNF64(c))

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmssLaneF32)
 */
public fun vfmssLaneF32(a: Float, b: Float, c: Float32x2T, lane: Int): Float = vfmasLaneF32(a, -b, c, lane)

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmssLaneqF32)
 */
public fun vfmssLaneqF32(a: Float, b: Float, c: Float32x4T, lane: Int): Float = vfmasLaneqF32(a, -b, c, lane)

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsdLaneF64)
 */
public fun vfmsdLaneF64(a: Double, b: Double, c: Float64x1T, lane: Int): Double = vfmadLaneF64(a, -b, c, lane)

/**
 * Floating-point fused multiply-subtract to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsdLaneqF64)
 */
public fun vfmsdLaneqF64(a: Double, b: Double, c: Float64x2T, lane: Int): Double = vfmadLaneqF64(a, -b, c, lane)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighF64)
 */
public fun vgetHighF64(a: Float64x2T): Float64x1T = Float64x1T(listOf(run { val simdArg0 = a; val simdArg1 = 1; Float64x1T() }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowF64)
 */
public fun vgetLowF64(a: Float64x2T): Float64x1T = Float64x1T(listOf(run { val simdArg0 = a; val simdArg1 = 0; Float64x1T() }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneF64)
 */
public fun vgetqLaneF64(a: Float64x2T, imm5: Int): Double {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = imm5.toUInt(); 0.0 }
}

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F16(ptr: ConstPtr): Float16x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF16(ptr: ConstPtr): Float16x8T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F32(ptr: ConstPtr): Float32x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF32(ptr: ConstPtr): Float32x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F64(ptr: ConstPtr): Float64x1T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF64(ptr: ConstPtr): Float64x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S8(ptr: ConstPtr): Int8x8T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS8(ptr: ConstPtr): Int8x16T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S16(ptr: ConstPtr): Int16x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS16(ptr: ConstPtr): Int16x8T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S32(ptr: ConstPtr): Int32x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS32(ptr: ConstPtr): Int32x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S64(ptr: ConstPtr): Int64x1T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS64(ptr: ConstPtr): Int64x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U8(ptr: ConstPtr): Uint8x8T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU8(ptr: ConstPtr): Uint8x16T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U16(ptr: ConstPtr): Uint16x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU16(ptr: ConstPtr): Uint16x8T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U32(ptr: ConstPtr): Uint32x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU32(ptr: ConstPtr): Uint32x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U64(ptr: ConstPtr): Uint64x1T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU64(ptr: ConstPtr): Uint64x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P8(ptr: ConstPtr): Poly8x8T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P8>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP8(ptr: ConstPtr): Poly8x16T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P8>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P16(ptr: ConstPtr): Poly16x4T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P16>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP16(ptr: ConstPtr): Poly16x8T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P16>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P64(ptr: ConstPtr): Poly64x1T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P64>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP64(ptr: ConstPtr): Poly64x2T = readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<P64>())

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F64X2(ptr: ConstPtr): Float64x1x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F64X3(ptr: ConstPtr): Float64x1x3T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F64X4(ptr: ConstPtr): Float64x1x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF64X2(ptr: ConstPtr): Float64x2x2T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF64X3(ptr: ConstPtr): Float64x2x3T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF64X4(ptr: ConstPtr): Float64x2x4T = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupF64(a: ConstPtr): Float64x1x2T {
    fun externVld2DupF64(ptr: ConstPtr): Float64x1x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).alignBytes)); res }
    return externVld2DupF64(a)
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupF64(a: ConstPtr): Float64x2x2T {
    fun externVld2qDupF64(ptr: ConstPtr): Float64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); res }
    return externVld2qDupF64(a)
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupS64(a: ConstPtr): Int64x2x2T {
    fun externVld2qDupS64(ptr: ConstPtr): Int64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); res }
    return externVld2qDupS64(a)
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2F64(a: ConstPtr): Float64x1x2T = readUnaligned(a, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneF64(a: ConstPtr, b: Float64x1x2T, lane: Int): Float64x1x2T {
    fun externVld2LaneF64(a1: Float64x1T, b1: Float64x1T, n: Long, ptr: ConstPtr): Float64x1x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x2T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld2LaneF64(b.field0, b.field1, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneS64(a: ConstPtr, b: Int64x1x2T, lane: Int): Int64x1x2T {
    fun externVld2LaneS64(a1: Int64x1T, b1: Int64x1T, n: Long, ptr: ConstPtr): Int64x1x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x1x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x1x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x2T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld2LaneS64(b.field0, b.field1, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneP64(a: ConstPtr, b: Poly64x1x2T, lane: Int): Poly64x1x2T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x2T, Poly64x1x2T>(vld2LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x1x2T, Int64x1x2T>(b, UnresolvedTypeLayout<Poly64x1x2T>(), UnresolvedTypeLayout<Int64x1x2T>()), lane), UnresolvedTypeLayout<Int64x1x2T>(), UnresolvedTypeLayout<Poly64x1x2T>())
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneU64(a: ConstPtr, b: Uint64x1x2T, lane: Int): Uint64x1x2T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x2T, Uint64x1x2T>(vld2LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x1x2T, Int64x1x2T>(b, UnresolvedTypeLayout<Uint64x1x2T>(), UnresolvedTypeLayout<Int64x1x2T>()), lane), UnresolvedTypeLayout<Int64x1x2T>(), UnresolvedTypeLayout<Uint64x1x2T>())
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupP64(a: ConstPtr): Poly64x2x2T = transmuteWithLayouts<Int64x2x2T, Poly64x2x2T>(vld2qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Poly64x2x2T>())

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupU64(a: ConstPtr): Uint64x2x2T = transmuteWithLayouts<Int64x2x2T, Uint64x2x2T>(vld2qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Uint64x2x2T>())

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qF64(a: ConstPtr): Float64x2x2T {
    fun externVld2qF64(ptr: ConstPtr): Float64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); res }
    return externVld2qF64(ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qS64(a: ConstPtr): Int64x2x2T {
    fun externVld2qS64(ptr: ConstPtr): Int64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); res }
    return externVld2qS64(ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneF64(a: ConstPtr, b: Float64x2x2T, lane: Int): Float64x2x2T {
    fun externVld2qLaneF64(a1: Float64x2T, b1: Float64x2T, n: Long, ptr: ConstPtr): Float64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x2T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld2qLaneF64(b.field0, b.field1, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneS8(a: ConstPtr, b: Int8x16x2T, lane: Int): Int8x16x2T {
    fun externVld2qLaneS8(a1: Int8x16T, b1: Int8x16T, n: Long, ptr: ConstPtr): Int8x16x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int8x16x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int8x16x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int8x16x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x2T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return externVld2qLaneS8(b.field0, b.field1, lane.toLong(), a)
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneS64(a: ConstPtr, b: Int64x2x2T, lane: Int): Int64x2x2T {
    fun externVld2qLaneS64(a1: Int64x2T, b1: Int64x2T, n: Long, ptr: ConstPtr): Int64x2x2T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x2T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x2T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld2qLaneS64(b.field0, b.field1, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneP64(a: ConstPtr, b: Poly64x2x2T, lane: Int): Poly64x2x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x2T, Poly64x2x2T>(vld2qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Poly64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()), lane), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Poly64x2x2T>())
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneU8(a: ConstPtr, b: Uint8x16x2T, lane: Int): Uint8x16x2T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x2T, Uint8x16x2T>(vld2qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint8x16x2T, Int8x16x2T>(b, UnresolvedTypeLayout<Uint8x16x2T>(), UnresolvedTypeLayout<Int8x16x2T>()), lane), UnresolvedTypeLayout<Int8x16x2T>(), UnresolvedTypeLayout<Uint8x16x2T>())
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneU64(a: ConstPtr, b: Uint64x2x2T, lane: Int): Uint64x2x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x2T, Uint64x2x2T>(vld2qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()), lane), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Uint64x2x2T>())
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneP8(a: ConstPtr, b: Poly8x16x2T, lane: Int): Poly8x16x2T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x2T, Poly8x16x2T>(vld2qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly8x16x2T, Int8x16x2T>(b, UnresolvedTypeLayout<Poly8x16x2T>(), UnresolvedTypeLayout<Int8x16x2T>()), lane), UnresolvedTypeLayout<Int8x16x2T>(), UnresolvedTypeLayout<Poly8x16x2T>())
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qP64(a: ConstPtr): Poly64x2x2T = transmuteWithLayouts<Int64x2x2T, Poly64x2x2T>(vld2qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Poly64x2x2T>())

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qU64(a: ConstPtr): Uint64x2x2T = transmuteWithLayouts<Int64x2x2T, Uint64x2x2T>(vld2qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x2T>(), UnresolvedTypeLayout<Uint64x2x2T>())

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupF64(a: ConstPtr): Float64x1x3T {
    fun externVld3DupF64(ptr: ConstPtr): Float64x1x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).alignBytes)); res }
    return externVld3DupF64(a)
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupF64(a: ConstPtr): Float64x2x3T {
    fun externVld3qDupF64(ptr: ConstPtr): Float64x2x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).alignBytes)); res }
    return externVld3qDupF64(a)
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupS64(a: ConstPtr): Int64x2x3T {
    fun externVld3qDupS64(ptr: ConstPtr): Int64x2x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).alignBytes)); res }
    return externVld3qDupS64(a)
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3F64(a: ConstPtr): Float64x1x3T = readUnaligned(a, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneF64(a: ConstPtr, b: Float64x1x3T, lane: Int): Float64x1x3T {
    fun externVld3LaneF64(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, n: Long, ptr: ConstPtr): Float64x1x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x3T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld3LaneF64(b.field0, b.field1, b.field2, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneP64(a: ConstPtr, b: Poly64x1x3T, lane: Int): Poly64x1x3T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x3T, Poly64x1x3T>(vld3LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x1x3T, Int64x1x3T>(b, UnresolvedTypeLayout<Poly64x1x3T>(), UnresolvedTypeLayout<Int64x1x3T>()), lane), UnresolvedTypeLayout<Int64x1x3T>(), UnresolvedTypeLayout<Poly64x1x3T>())
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneS64(a: ConstPtr, b: Int64x1x3T, lane: Int): Int64x1x3T {
    fun externVld3LaneS64(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, n: Long, ptr: ConstPtr): Int64x1x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x1x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x1x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x3T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld3LaneS64(b.field0, b.field1, b.field2, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneU64(a: ConstPtr, b: Uint64x1x3T, lane: Int): Uint64x1x3T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x3T, Uint64x1x3T>(vld3LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x1x3T, Int64x1x3T>(b, UnresolvedTypeLayout<Uint64x1x3T>(), UnresolvedTypeLayout<Int64x1x3T>()), lane), UnresolvedTypeLayout<Int64x1x3T>(), UnresolvedTypeLayout<Uint64x1x3T>())
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupP64(a: ConstPtr): Poly64x2x3T = transmuteWithLayouts<Int64x2x3T, Poly64x2x3T>(vld3qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Poly64x2x3T>())

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupU64(a: ConstPtr): Uint64x2x3T = transmuteWithLayouts<Int64x2x3T, Uint64x2x3T>(vld3qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Uint64x2x3T>())

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qF64(a: ConstPtr): Float64x2x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.doubleArray((2 * 3).toInt()))
        val w: W = Simd.fromArray(arr)
        val v0: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 0)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v1: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 1)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v2: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        transmuteWithLayouts<Triple<V, V, V>, Float64x2x3T>(Triple(v0, v1, v2), UnresolvedTypeLayout<Triple<V, V, V>>(), UnresolvedTypeLayout<Float64x2x3T>())
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qS64(a: ConstPtr): Int64x2x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.longArray((2 * 3).toInt()))
        val w: W = Simd.fromArray(arr)
        val v0: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 0)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v1: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 1)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v2: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 3, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        transmuteWithLayouts<Triple<V, V, V>, Int64x2x3T>(Triple(v0, v1, v2), UnresolvedTypeLayout<Triple<V, V, V>>(), UnresolvedTypeLayout<Int64x2x3T>())
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneF64(a: ConstPtr, b: Float64x2x3T, lane: Int): Float64x2x3T {
    fun externVld3qLaneF64(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, n: Long, ptr: ConstPtr): Float64x2x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x3T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld3qLaneF64(b.field0, b.field1, b.field2, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneP64(a: ConstPtr, b: Poly64x2x3T, lane: Int): Poly64x2x3T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x3T, Poly64x2x3T>(vld3qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Poly64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()), lane), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Poly64x2x3T>())
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneS8(a: ConstPtr, b: Int8x16x3T, lane: Int): Int8x16x3T {
    fun externVld3qLaneS8(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, n: Long, ptr: ConstPtr): Int8x16x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int8x16x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int8x16x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int8x16x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x3T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return externVld3qLaneS8(b.field0, b.field1, b.field2, lane.toLong(), a)
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneS64(a: ConstPtr, b: Int64x2x3T, lane: Int): Int64x2x3T {
    fun externVld3qLaneS64(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, n: Long, ptr: ConstPtr): Int64x2x3T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x3T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x3T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld3qLaneS64(b.field0, b.field1, b.field2, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneU8(a: ConstPtr, b: Uint8x16x3T, lane: Int): Uint8x16x3T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x3T, Uint8x16x3T>(vld3qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint8x16x3T, Int8x16x3T>(b, UnresolvedTypeLayout<Uint8x16x3T>(), UnresolvedTypeLayout<Int8x16x3T>()), lane), UnresolvedTypeLayout<Int8x16x3T>(), UnresolvedTypeLayout<Uint8x16x3T>())
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneU64(a: ConstPtr, b: Uint64x2x3T, lane: Int): Uint64x2x3T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x3T, Uint64x2x3T>(vld3qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()), lane), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Uint64x2x3T>())
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneP8(a: ConstPtr, b: Poly8x16x3T, lane: Int): Poly8x16x3T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x3T, Poly8x16x3T>(vld3qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly8x16x3T, Int8x16x3T>(b, UnresolvedTypeLayout<Poly8x16x3T>(), UnresolvedTypeLayout<Int8x16x3T>()), lane), UnresolvedTypeLayout<Int8x16x3T>(), UnresolvedTypeLayout<Poly8x16x3T>())
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qP64(a: ConstPtr): Poly64x2x3T = transmuteWithLayouts<Int64x2x3T, Poly64x2x3T>(vld3qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Poly64x2x3T>())

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qU64(a: ConstPtr): Uint64x2x3T = transmuteWithLayouts<Int64x2x3T, Uint64x2x3T>(vld3qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x3T>(), UnresolvedTypeLayout<Uint64x2x3T>())

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupF64(a: ConstPtr): Float64x1x4T {
    fun externVld4DupF64(ptr: ConstPtr): Float64x1x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).alignBytes)); res }
    return externVld4DupF64(a)
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupF64(a: ConstPtr): Float64x2x4T {
    fun externVld4qDupF64(ptr: ConstPtr): Float64x2x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).alignBytes)); res }
    return externVld4qDupF64(a)
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupS64(a: ConstPtr): Int64x2x4T {
    fun externVld4qDupS64(ptr: ConstPtr): Int64x2x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).alignBytes)); res }
    return externVld4qDupS64(a)
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4F64(a: ConstPtr): Float64x1x4T = readUnaligned(a, typeLayoutT = PrimitiveLayouts.F64)

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneF64(a: ConstPtr, b: Float64x1x4T, lane: Int): Float64x1x4T {
    fun externVld4LaneF64(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, d: Float64x1T, n: Long, ptr: ConstPtr): Float64x1x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x1x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x1x4T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld4LaneF64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneS64(a: ConstPtr, b: Int64x1x4T, lane: Int): Int64x1x4T {
    fun externVld4LaneS64(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, d: Int64x1T, n: Long, ptr: ConstPtr): Int64x1x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x1x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x1x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x1x4T>()).alignBytes)); res }
    run {
        check(lane == 0)
    }
    return externVld4LaneS64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneP64(a: ConstPtr, b: Poly64x1x4T, lane: Int): Poly64x1x4T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x4T, Poly64x1x4T>(vld4LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x1x4T, Int64x1x4T>(b, UnresolvedTypeLayout<Poly64x1x4T>(), UnresolvedTypeLayout<Int64x1x4T>()), lane), UnresolvedTypeLayout<Int64x1x4T>(), UnresolvedTypeLayout<Poly64x1x4T>())
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneU64(a: ConstPtr, b: Uint64x1x4T, lane: Int): Uint64x1x4T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1x4T, Uint64x1x4T>(vld4LaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x1x4T, Int64x1x4T>(b, UnresolvedTypeLayout<Uint64x1x4T>(), UnresolvedTypeLayout<Int64x1x4T>()), lane), UnresolvedTypeLayout<Int64x1x4T>(), UnresolvedTypeLayout<Uint64x1x4T>())
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupP64(a: ConstPtr): Poly64x2x4T = transmuteWithLayouts<Int64x2x4T, Poly64x2x4T>(vld4qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Poly64x2x4T>())

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupU64(a: ConstPtr): Uint64x2x4T = transmuteWithLayouts<Int64x2x4T, Uint64x2x4T>(vld4qDupS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Uint64x2x4T>())

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qF64(a: ConstPtr): Float64x2x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = UnresolvedTypeLayout<W>())
        val v0: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 0)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v1: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 1)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v2: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v3: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 3)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        transmuteWithLayouts<List<Any?>, Float64x2x4T>(listOf(v0, v1, v2, v3), UnresolvedTypeLayout<List<Any?>>(), UnresolvedTypeLayout<Float64x2x4T>())
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qS64(a: ConstPtr): Int64x2x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = UnresolvedTypeLayout<W>())
        val v0: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 0)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v1: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 1)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v2: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        val v3: V = run { val simdArg0 = w; val simdArg1 = w; val simdArg2 = SimdShuffleIdx(deinterleaveMask(2, 4, 3)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); val res = (UnresolvedTypeLayout<V>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<V>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<V>()).alignBytes)); res } }
        transmuteWithLayouts<List<Any?>, Int64x2x4T>(listOf(v0, v1, v2, v3), UnresolvedTypeLayout<List<Any?>>(), UnresolvedTypeLayout<Int64x2x4T>())
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneF64(a: ConstPtr, b: Float64x2x4T, lane: Int): Float64x2x4T {
    fun externVld4qLaneF64(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, d: Float64x2T, n: Long, ptr: ConstPtr): Float64x2x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Float64x2x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Float64x2x4T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld4qLaneF64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneS8(a: ConstPtr, b: Int8x16x4T, lane: Int): Int8x16x4T {
    fun externVld4qLaneS8(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, d: Int8x16T, n: Long, ptr: ConstPtr): Int8x16x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int8x16x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int8x16x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int8x16x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int8x16x4T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return externVld4qLaneS8(b.field0, b.field1, b.field2, b.field3, lane.toLong(), a)
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneS64(a: ConstPtr, b: Int64x2x4T, lane: Int): Int64x2x4T {
    fun externVld4qLaneS64(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, d: Int64x2T, n: Long, ptr: ConstPtr): Int64x2x4T = run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).alignBytes)); val res = (UnresolvedTypeLayout<Int64x2x4T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Int64x2x4T>()).alignBytes)); res }
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return externVld4qLaneS64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), ConstPtr(a.rawAddress, a.rawMeta))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneP64(a: ConstPtr, b: Poly64x2x4T, lane: Int): Poly64x2x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x4T, Poly64x2x4T>(vld4qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Poly64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()), lane), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Poly64x2x4T>())
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneU8(a: ConstPtr, b: Uint8x16x4T, lane: Int): Uint8x16x4T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x4T, Uint8x16x4T>(vld4qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint8x16x4T, Int8x16x4T>(b, UnresolvedTypeLayout<Uint8x16x4T>(), UnresolvedTypeLayout<Int8x16x4T>()), lane), UnresolvedTypeLayout<Int8x16x4T>(), UnresolvedTypeLayout<Uint8x16x4T>())
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneU64(a: ConstPtr, b: Uint64x2x4T, lane: Int): Uint64x2x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2x4T, Uint64x2x4T>(vld4qLaneS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Uint64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()), lane), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Uint64x2x4T>())
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneP8(a: ConstPtr, b: Poly8x16x4T, lane: Int): Poly8x16x4T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16x4T, Poly8x16x4T>(vld4qLaneS8(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>()), transmuteWithLayouts<Poly8x16x4T, Int8x16x4T>(b, UnresolvedTypeLayout<Poly8x16x4T>(), UnresolvedTypeLayout<Int8x16x4T>()), lane), UnresolvedTypeLayout<Int8x16x4T>(), UnresolvedTypeLayout<Poly8x16x4T>())
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qP64(a: ConstPtr): Poly64x2x4T = transmuteWithLayouts<Int64x2x4T, Poly64x2x4T>(vld4qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Poly64x2x4T>())

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qU64(a: ConstPtr): Uint64x2x4T = transmuteWithLayouts<Int64x2x4T, Uint64x2x4T>(vld4qS64(transmuteWithLayouts<ConstPtr, ConstPtr>(a, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<ConstPtr>())), UnresolvedTypeLayout<Int64x2x4T>(), UnresolvedTypeLayout<Uint64x2x4T>())

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1LaneS64(ptr: ConstPtr, src: Int64x1T, lane: Int): Int64x1T {
    run {
        check(lane == 0)
    }
    val atomicSrc = AtomicI64.fromPtr(MutPtr(ptr.rawAddress, ptr.rawMeta))
    return run { val simdArg0 = src; val simdArg1 = lane.toUInt(); val simdArg2 = atomicSrc.load(Ordering.Acquire); simdArg0 }
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1qLaneS64(ptr: ConstPtr, src: Int64x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val atomicSrc = AtomicI64.fromPtr(MutPtr(ptr.rawAddress, ptr.rawMeta))
    return run { val simdArg0 = src; val simdArg1 = lane.toUInt(); val simdArg2 = atomicSrc.load(Ordering.Acquire); simdArg0 }
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1qLaneF64(ptr: ConstPtr, src: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2T, Float64x2T>(vldap1qLaneS64((MutPtr(ptr.rawAddress, ptr.rawMeta)).asConstPtr(), transmuteWithLayouts<Float64x2T, Int64x2T>(src, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Float64x2T>())
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1LaneU64(ptr: ConstPtr, src: Uint64x1T, lane: Int): Uint64x1T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1T, Uint64x1T>(vldap1LaneS64((MutPtr(ptr.rawAddress, ptr.rawMeta)).asConstPtr(), transmuteWithLayouts<Uint64x1T, Int64x1T>(src, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), lane), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Uint64x1T>())
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1qLaneU64(ptr: ConstPtr, src: Uint64x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2T, Uint64x2T>(vldap1qLaneS64((MutPtr(ptr.rawAddress, ptr.rawMeta)).asConstPtr(), transmuteWithLayouts<Uint64x2T, Int64x2T>(src, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Uint64x2T>())
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1LaneP64(ptr: ConstPtr, src: Poly64x1T, lane: Int): Poly64x1T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int64x1T, Poly64x1T>(vldap1LaneS64((MutPtr(ptr.rawAddress, ptr.rawMeta)).asConstPtr(), transmuteWithLayouts<Poly64x1T, Int64x1T>(src, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), lane), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Poly64x1T>())
}

/**
 * Load-acquire RCpc one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldap1qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldap1qLaneP64(ptr: ConstPtr, src: Poly64x2T, lane: Int): Poly64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int64x2T, Poly64x2T>(vldap1qLaneS64((MutPtr(ptr.rawAddress, ptr.rawMeta)).asConstPtr(), transmuteWithLayouts<Poly64x2T, Int64x2T>(src, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Poly64x2T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneF16(a: Float16x4T, b: Uint8x8T, index: Int): Float16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti2LaneS16(transmuteWithLayouts<Float16x4T, Int16x4T>(a, UnresolvedTypeLayout<Float16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneF16(a: Float16x8T, b: Uint8x8T, index: Int): Float16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti2qLaneS16(transmuteWithLayouts<Float16x8T, Int16x8T>(a, UnresolvedTypeLayout<Float16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneU8(a: Uint8x8T, b: Uint8x8T, index: Int): Uint8x16T {
    run {
        check(index >= 0 && index <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti2LaneS8(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneU8(a: Uint8x16T, b: Uint8x8T, index: Int): Uint8x16T {
    run {
        check(index >= 0 && index <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti2qLaneS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneU16(a: Uint16x4T, b: Uint8x8T, index: Int): Uint16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti2LaneS16(transmuteWithLayouts<Uint16x4T, Int16x4T>(a, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneU16(a: Uint16x8T, b: Uint8x8T, index: Int): Uint16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti2qLaneS16(transmuteWithLayouts<Uint16x8T, Int16x8T>(a, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneP8(a: Poly8x8T, b: Uint8x8T, index: Int): Poly8x16T {
    run {
        check(index >= 0 && index <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti2LaneS8(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneP8(a: Poly8x16T, b: Uint8x8T, index: Int): Poly8x16T {
    run {
        check(index >= 0 && index <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti2qLaneS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneP16(a: Poly16x4T, b: Uint8x8T, index: Int): Poly16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti2LaneS16(transmuteWithLayouts<Poly16x4T, Int16x4T>(a, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneP16(a: Poly16x8T, b: Uint8x8T, index: Int): Poly16x8T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti2qLaneS16(transmuteWithLayouts<Poly16x8T, Int16x8T>(a, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneS8(a: Int8x8T, b: Uint8x8T, lane: Int): Int8x16T {
    fun externVluti2LaneS8(a1: Int8x8T, b1: Uint8x8T, n: Int): Int8x16T = Int8x16T()
    run {
        check(lane >= 0 && lane <= 1)
    }
    return externVluti2LaneS8(a, b, lane)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneS8(a: Int8x16T, b: Uint8x8T, lane: Int): Int8x16T {
    fun externVluti2qLaneS8(a1: Int8x16T, b1: Uint8x8T, n: Int): Int8x16T = Int8x16T()
    run {
        check(lane >= 0 && lane <= 1)
    }
    return externVluti2qLaneS8(a, b, lane)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneS16(a: Int16x4T, b: Uint8x8T, lane: Int): Int16x8T {
    fun externVluti2LaneS16(a1: Int16x4T, b1: Uint8x8T, n: Int): Int16x8T = Int16x8T()
    run {
        check(lane >= 0 && lane <= 3)
    }
    return externVluti2LaneS16(a, b, lane)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneS16(a: Int16x8T, b: Uint8x8T, lane: Int): Int16x8T {
    fun externVluti2qLaneS16(a1: Int16x8T, b1: Uint8x8T, n: Int): Int16x8T = Int16x8T()
    run {
        check(lane >= 0 && lane <= 3)
    }
    return externVluti2qLaneS16(a, b, lane)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqF16(a: Float16x4T, b: Uint8x16T, index: Int): Float16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti2LaneqS16(transmuteWithLayouts<Float16x4T, Int16x4T>(a, UnresolvedTypeLayout<Float16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqF16(a: Float16x8T, b: Uint8x16T, index: Int): Float16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti2qLaneqS16(transmuteWithLayouts<Float16x8T, Int16x8T>(a, UnresolvedTypeLayout<Float16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqU8(a: Uint8x8T, b: Uint8x16T, index: Int): Uint8x16T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti2LaneqS8(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqU8(a: Uint8x16T, b: Uint8x16T, index: Int): Uint8x16T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti2qLaneqS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqU16(a: Uint16x4T, b: Uint8x16T, index: Int): Uint16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti2LaneqS16(transmuteWithLayouts<Uint16x4T, Int16x4T>(a, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqU16(a: Uint16x8T, b: Uint8x16T, index: Int): Uint16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti2qLaneqS16(transmuteWithLayouts<Uint16x8T, Int16x8T>(a, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqP8(a: Poly8x8T, b: Uint8x16T, index: Int): Poly8x16T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti2LaneqS8(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqP8(a: Poly8x16T, b: Uint8x16T, index: Int): Poly8x16T {
    run {
        check(index >= 0 && index <= 3)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti2qLaneqS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, index), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqP16(a: Poly16x4T, b: Uint8x16T, index: Int): Poly16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti2LaneqS16(transmuteWithLayouts<Poly16x4T, Int16x4T>(a, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqP16(a: Poly16x8T, b: Uint8x16T, index: Int): Poly16x8T {
    run {
        check(index >= 0 && index <= 7)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti2qLaneqS16(transmuteWithLayouts<Poly16x8T, Int16x8T>(a, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), b, index), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqS8(a: Int8x8T, b: Uint8x16T, index: Int): Int8x16T {
    fun externVluti2LaneqS8(a1: Int8x8T, b1: Uint8x16T, n: Int): Int8x16T = Int8x16T()
    run {
        check(index >= 0 && index <= 3)
    }
    return externVluti2LaneqS8(a, b, index)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqS8(a: Int8x16T, b: Uint8x16T, index: Int): Int8x16T {
    fun externVluti2qLaneqS8(a1: Int8x16T, b1: Uint8x16T, n: Int): Int8x16T = Int8x16T()
    run {
        check(index >= 0 && index <= 3)
    }
    return externVluti2qLaneqS8(a, b, index)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2LaneqS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2LaneqS16(a: Int16x4T, b: Uint8x16T, index: Int): Int16x8T {
    fun externVluti2LaneqS16(a1: Int16x4T, b1: Uint8x16T, n: Int): Int16x8T = Int16x8T()
    run {
        check(index >= 0 && index <= 7)
    }
    return externVluti2LaneqS16(a, b, index)
}

/**
 * Lookup table read with 2-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti2qLaneqS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti2qLaneqS16(a: Int16x8T, b: Uint8x16T, index: Int): Int16x8T {
    fun externVluti2qLaneqS16(a1: Int16x8T, b1: Uint8x16T, n: Int): Int16x8T = Int16x8T()
    run {
        check(index >= 0 && index <= 7)
    }
    return externVluti2qLaneqS16(a, b, index)
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneF16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneF16X2(a: Float16x8x2T, b: Uint8x8T, lane: Int): Float16x8T {
    run {
        check(lane >= 0 && lane <= 1)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti4qLaneS16X2(transmuteWithLayouts<Float16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Float16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneU16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneU16X2(a: Uint16x8x2T, b: Uint8x8T, lane: Int): Uint16x8T {
    run {
        check(lane >= 0 && lane <= 1)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti4qLaneS16X2(transmuteWithLayouts<Uint16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Uint16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneP16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneP16X2(a: Poly16x8x2T, b: Uint8x8T, lane: Int): Poly16x8T {
    run {
        check(lane >= 0 && lane <= 1)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti4qLaneS16X2(transmuteWithLayouts<Poly16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Poly16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneS16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneS16X2(a: Int16x8x2T, b: Uint8x8T, lane: Int): Int16x8T {
    fun externVluti4qLaneS16X2(a1: Int16x8T, b1: Uint8x8T, n: Int): Int16x8T = Int16x8T()
    run {
        check(lane >= 0 && lane <= 1)
    }
    return externVluti4qLaneS16X2(a.field0, a.field1, b, lane)
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneS8(a: Int8x16T, b: Uint8x8T, lane: Int): Int8x16T {
    fun externVluti4qLaneS8(a1: Int8x16T, b1: Uint8x8T, n: Int): Int8x16T = Int8x16T()
    run {
        check(lane == 0)
    }
    return externVluti4qLaneS8(a, b, lane)
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneU8(a: Uint8x16T, b: Uint8x8T, lane: Int): Uint8x16T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti4qLaneS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, lane), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneP8(a: Poly8x16T, b: Uint8x8T, lane: Int): Poly8x16T {
    run {
        check(lane == 0)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti4qLaneS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, lane), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqF16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqF16X2(a: Float16x8x2T, b: Uint8x16T, lane: Int): Float16x8T {
    run {
        check(lane >= 0 && lane <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Float16x8T>(vluti4qLaneqS16X2(transmuteWithLayouts<Float16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Float16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqU16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqU16X2(a: Uint16x8x2T, b: Uint8x16T, lane: Int): Uint16x8T {
    run {
        check(lane >= 0 && lane <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vluti4qLaneqS16X2(transmuteWithLayouts<Uint16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Uint16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqP16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqP16X2(a: Poly16x8x2T, b: Uint8x16T, lane: Int): Poly16x8T {
    run {
        check(lane >= 0 && lane <= 3)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vluti4qLaneqS16X2(transmuteWithLayouts<Poly16x8x2T, Int16x8x2T>(a, UnresolvedTypeLayout<Poly16x8x2T>(), UnresolvedTypeLayout<Int16x8x2T>()), b, lane), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqS16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqS16X2(a: Int16x8x2T, b: Uint8x16T, lane: Int): Int16x8T {
    fun externVluti4qLaneqS16X2(a1: Int16x8T, b1: Int16x8T, c: Uint8x16T, n: Int): Int16x8T = Int16x8T()
    run {
        check(lane >= 0 && lane <= 3)
    }
    return externVluti4qLaneqS16X2(a.field0, a.field1, b, lane)
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqS8(a: Int8x16T, b: Uint8x16T, lane: Int): Int8x16T {
    fun externVluti4qLaneqS8(a1: Int8x16T, b1: Uint8x16T, n: Int): Int8x16T = Int8x16T()
    run {
        check(lane >= 0 && lane <= 1)
    }
    return externVluti4qLaneqS8(a, b, lane)
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqU8(a: Uint8x16T, b: Uint8x16T, lane: Int): Uint8x16T {
    run {
        check(lane >= 0 && lane <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vluti4qLaneqS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, lane), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Lookup table read with 4-bit indices
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vluti4qLaneqP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vluti4qLaneqP8(a: Poly8x16T, b: Uint8x16T, lane: Int): Poly8x16T {
    run {
        check(lane >= 0 && lane <= 1)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vluti4qLaneqS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b, lane), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxF64)
 */
public fun vmaxF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVmaxF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVmaxF64(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqF64)
 */
public fun vmaxqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVmaxqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVmaxqF64(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxhF16)
 */
public fun vmaxhF16(a: F16, b: F16): F16 {
    fun externVmaxhF16(a1: F16, b1: F16): F16 = F16()
    return externVmaxhF16(a, b)
}

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmF64)
 */
public fun vmaxnmF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVmaxnmF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVmaxnmF64(a, b)
}

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmqF64)
 */
public fun vmaxnmqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVmaxnmqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVmaxnmqF64(a, b)
}

/**
 * Floating-point Maximum Number
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmhF16)
 */
public fun vmaxnmhF16(a: F16, b: F16): F16 {
    fun externVmaxnmhF16(a1: F16, b1: F16): F16 = F16()
    return externVmaxnmhF16(a, b)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmvF16)
 */
public fun vmaxnmvF16(a: Float16x4T): F16 {
    fun externVmaxnmvF16(a1: Float16x4T): F16 = F16()
    return externVmaxnmvF16(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmvqF16)
 */
public fun vmaxnmvqF16(a: Float16x8T): F16 {
    fun externVmaxnmvqF16(a1: Float16x8T): F16 = F16()
    return externVmaxnmvqF16(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmvF32)
 */
public fun vmaxnmvF32(a: Float32x2T): Float {
    fun externVmaxnmvF32(a1: Float32x2T): Float = 0.0f
    return externVmaxnmvF32(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmvqF64)
 */
public fun vmaxnmvqF64(a: Float64x2T): Double {
    fun externVmaxnmvqF64(a1: Float64x2T): Double = 0.0
    return externVmaxnmvqF64(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmvqF32)
 */
public fun vmaxnmvqF32(a: Float32x4T): Float {
    fun externVmaxnmvqF32(a1: Float32x4T): Float = 0.0f
    return externVmaxnmvqF32(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvF16)
 */
public fun vmaxvF16(a: Float16x4T): F16 {
    fun externVmaxvF16(a1: Float16x4T): F16 = F16()
    return externVmaxvF16(a)
}

/**
 * Floating-point maximum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqF16)
 */
public fun vmaxvqF16(a: Float16x8T): F16 {
    fun externVmaxvqF16(a1: Float16x8T): F16 = F16()
    return externVmaxvqF16(a)
}

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvF32)
 */
public fun vmaxvF32(a: Float32x2T): Float {
    fun externVmaxvF32(a1: Float32x2T): Float = 0.0f
    return externVmaxvF32(a)
}

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqF32)
 */
public fun vmaxvqF32(a: Float32x4T): Float {
    fun externVmaxvqF32(a1: Float32x4T): Float = 0.0f
    return externVmaxvqF32(a)
}

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqF64)
 */
public fun vmaxvqF64(a: Float64x2T): Double {
    fun externVmaxvqF64(a1: Float64x2T): Double = 0.0
    return externVmaxvqF64(a)
}

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvS8)
 */
public fun vmaxvS8(a: Int8x8T): Byte = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqS8)
 */
public fun vmaxvqS8(a: Int8x16T): Byte = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvS16)
 */
public fun vmaxvS16(a: Int16x4T): Short = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqS16)
 */
public fun vmaxvqS16(a: Int16x8T): Short = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvS32)
 */
public fun vmaxvS32(a: Int32x2T): Int = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqS32)
 */
public fun vmaxvqS32(a: Int32x4T): Int = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvU8)
 */
public fun vmaxvU8(a: Uint8x8T): UByte = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqU8)
 */
public fun vmaxvqU8(a: Uint8x16T): UByte = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvU16)
 */
public fun vmaxvU16(a: Uint16x4T): UShort = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqU16)
 */
public fun vmaxvqU16(a: Uint16x8T): UShort = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvU32)
 */
public fun vmaxvU32(a: Uint32x2T): UInt = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector max.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxvqU32)
 */
public fun vmaxvqU32(a: Uint32x4T): UInt = run { val simdArg0 = a; 0u }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminF64)
 */
public fun vminF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVminF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVminF64(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqF64)
 */
public fun vminqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVminqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVminqF64(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminhF16)
 */
public fun vminhF16(a: F16, b: F16): F16 {
    fun externVminhF16(a1: F16, b1: F16): F16 = F16()
    return externVminhF16(a, b)
}

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmF64)
 */
public fun vminnmF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVminnmF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVminnmF64(a, b)
}

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmqF64)
 */
public fun vminnmqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVminnmqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVminnmqF64(a, b)
}

/**
 * Floating-point Minimum Number
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmhF16)
 */
public fun vminnmhF16(a: F16, b: F16): F16 {
    fun externVminnmhF16(a1: F16, b1: F16): F16 = F16()
    return externVminnmhF16(a, b)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmvF16)
 */
public fun vminnmvF16(a: Float16x4T): F16 {
    fun externVminnmvF16(a1: Float16x4T): F16 = F16()
    return externVminnmvF16(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmvqF16)
 */
public fun vminnmvqF16(a: Float16x8T): F16 {
    fun externVminnmvqF16(a1: Float16x8T): F16 = F16()
    return externVminnmvqF16(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmvF32)
 */
public fun vminnmvF32(a: Float32x2T): Float {
    fun externVminnmvF32(a1: Float32x2T): Float = 0.0f
    return externVminnmvF32(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmvqF64)
 */
public fun vminnmvqF64(a: Float64x2T): Double {
    fun externVminnmvqF64(a1: Float64x2T): Double = 0.0
    return externVminnmvqF64(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmvqF32)
 */
public fun vminnmvqF32(a: Float32x4T): Float {
    fun externVminnmvqF32(a1: Float32x4T): Float = 0.0f
    return externVminnmvqF32(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvF16)
 */
public fun vminvF16(a: Float16x4T): F16 {
    fun externVminvF16(a1: Float16x4T): F16 = F16()
    return externVminvF16(a)
}

/**
 * Floating-point minimum number across vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqF16)
 */
public fun vminvqF16(a: Float16x8T): F16 {
    fun externVminvqF16(a1: Float16x8T): F16 = F16()
    return externVminvqF16(a)
}

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvF32)
 */
public fun vminvF32(a: Float32x2T): Float {
    fun externVminvF32(a1: Float32x2T): Float = 0.0f
    return externVminvF32(a)
}

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqF32)
 */
public fun vminvqF32(a: Float32x4T): Float {
    fun externVminvqF32(a1: Float32x4T): Float = 0.0f
    return externVminvqF32(a)
}

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqF64)
 */
public fun vminvqF64(a: Float64x2T): Double {
    fun externVminvqF64(a1: Float64x2T): Double = 0.0
    return externVminvqF64(a)
}

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvS8)
 */
public fun vminvS8(a: Int8x8T): Byte = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqS8)
 */
public fun vminvqS8(a: Int8x16T): Byte = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvS16)
 */
public fun vminvS16(a: Int16x4T): Short = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqS16)
 */
public fun vminvqS16(a: Int16x8T): Short = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvS32)
 */
public fun vminvS32(a: Int32x2T): Int = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqS32)
 */
public fun vminvqS32(a: Int32x4T): Int = run { val simdArg0 = a; 0 }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvU8)
 */
public fun vminvU8(a: Uint8x8T): UByte = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqU8)
 */
public fun vminvqU8(a: Uint8x16T): UByte = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvU16)
 */
public fun vminvU16(a: Uint16x4T): UShort = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqU16)
 */
public fun vminvqU16(a: Uint16x8T): UShort = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvU32)
 */
public fun vminvU32(a: Uint32x2T): UInt = run { val simdArg0 = a; 0u }

/**
 * Horizontal vector min.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminvqU32)
 */
public fun vminvqU32(a: Uint32x4T): UInt = run { val simdArg0 = a; 0u }

/**
 * Floating-point multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaF64)
 */
public fun vmlaF64(a: Float64x1T, b: Float64x1T, c: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

/**
 * Floating-point multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqF64)
 */
public fun vmlaqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneS16)
 */
public fun vmlalHighLaneS16(a: Int32x4T, b: Int16x8T, c: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalHighS16(a, b, vdupqLaneS16(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneqS16)
 */
public fun vmlalHighLaneqS16(a: Int32x4T, b: Int16x8T, c: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlalHighS16(a, b, vdupqLaneqS16(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneS32)
 */
public fun vmlalHighLaneS32(a: Int64x2T, b: Int32x4T, c: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlalHighS32(a, b, vdupqLaneS32(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneqS32)
 */
public fun vmlalHighLaneqS32(a: Int64x2T, b: Int32x4T, c: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalHighS32(a, b, vdupqLaneqS32(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneU16)
 */
public fun vmlalHighLaneU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalHighU16(a, b, vdupqLaneU16(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneqU16)
 */
public fun vmlalHighLaneqU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlalHighU16(a, b, vdupqLaneqU16(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneU32)
 */
public fun vmlalHighLaneU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlalHighU32(a, b, vdupqLaneU32(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighLaneqU32)
 */
public fun vmlalHighLaneqU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalHighU32(a, b, vdupqLaneqU32(c, lane))
}

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighNS16)
 */
public fun vmlalHighNS16(a: Int32x4T, b: Int16x8T, c: Short): Int32x4T = vmlalHighS16(a, b, vdupqNS16(c))

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighNS32)
 */
public fun vmlalHighNS32(a: Int64x2T, b: Int32x4T, c: Int): Int64x2T = vmlalHighS32(a, b, vdupqNS32(c))

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighNU16)
 */
public fun vmlalHighNU16(a: Uint32x4T, b: Uint16x8T, c: UShort): Uint32x4T = vmlalHighU16(a, b, vdupqNU16(c))

/**
 * Multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighNU32)
 */
public fun vmlalHighNU32(a: Uint64x2T, b: Uint32x4T, c: UInt): Uint64x2T = vmlalHighU32(a, b, vdupqNU32(c))

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighS8)
 */
public fun vmlalHighS8(a: Int16x8T, b: Int8x16T, c: Int8x16T): Int16x8T {
    val b1 = vgetHighS8(b)
    val c1 = vgetHighS8(c)
    return vmlalS8(a, b1, c1)
}

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighS16)
 */
public fun vmlalHighS16(a: Int32x4T, b: Int16x8T, c: Int16x8T): Int32x4T {
    val b1 = vgetHighS16(b)
    val c1 = vgetHighS16(c)
    return vmlalS16(a, b1, c1)
}

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighS32)
 */
public fun vmlalHighS32(a: Int64x2T, b: Int32x4T, c: Int32x4T): Int64x2T {
    val b1 = vgetHighS32(b)
    val c1 = vgetHighS32(c)
    return vmlalS32(a, b1, c1)
}

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighU8)
 */
public fun vmlalHighU8(a: Uint16x8T, b: Uint8x16T, c: Uint8x16T): Uint16x8T {
    val b1 = vgetHighU8(b)
    val c1 = vgetHighU8(c)
    return vmlalU8(a, b1, c1)
}

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighU16)
 */
public fun vmlalHighU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x8T): Uint32x4T {
    val b1 = vgetHighU16(b)
    val c1 = vgetHighU16(c)
    return vmlalU16(a, b1, c1)
}

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalHighU32)
 */
public fun vmlalHighU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x4T): Uint64x2T {
    val b1 = vgetHighU32(b)
    val c1 = vgetHighU32(c)
    return vmlalU32(a, b1, c1)
}

/**
 * Floating-point multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsF64)
 */
public fun vmlsF64(a: Float64x1T, b: Float64x1T, c: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

/**
 * Floating-point multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqF64)
 */
public fun vmlsqF64(a: Float64x2T, b: Float64x2T, c: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneS16)
 */
public fun vmlslHighLaneS16(a: Int32x4T, b: Int16x8T, c: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslHighS16(a, b, vdupqLaneS16(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneqS16)
 */
public fun vmlslHighLaneqS16(a: Int32x4T, b: Int16x8T, c: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlslHighS16(a, b, vdupqLaneqS16(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneS32)
 */
public fun vmlslHighLaneS32(a: Int64x2T, b: Int32x4T, c: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlslHighS32(a, b, vdupqLaneS32(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneqS32)
 */
public fun vmlslHighLaneqS32(a: Int64x2T, b: Int32x4T, c: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslHighS32(a, b, vdupqLaneqS32(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneU16)
 */
public fun vmlslHighLaneU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslHighU16(a, b, vdupqLaneU16(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneqU16)
 */
public fun vmlslHighLaneqU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlslHighU16(a, b, vdupqLaneqU16(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneU32)
 */
public fun vmlslHighLaneU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlslHighU32(a, b, vdupqLaneU32(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighLaneqU32)
 */
public fun vmlslHighLaneqU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslHighU32(a, b, vdupqLaneqU32(c, lane))
}

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighNS16)
 */
public fun vmlslHighNS16(a: Int32x4T, b: Int16x8T, c: Short): Int32x4T = vmlslHighS16(a, b, vdupqNS16(c))

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighNS32)
 */
public fun vmlslHighNS32(a: Int64x2T, b: Int32x4T, c: Int): Int64x2T = vmlslHighS32(a, b, vdupqNS32(c))

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighNU16)
 */
public fun vmlslHighNU16(a: Uint32x4T, b: Uint16x8T, c: UShort): Uint32x4T = vmlslHighU16(a, b, vdupqNU16(c))

/**
 * Multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighNU32)
 */
public fun vmlslHighNU32(a: Uint64x2T, b: Uint32x4T, c: UInt): Uint64x2T = vmlslHighU32(a, b, vdupqNU32(c))

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighS8)
 */
public fun vmlslHighS8(a: Int16x8T, b: Int8x16T, c: Int8x16T): Int16x8T {
    val b1 = vgetHighS8(b)
    val c1 = vgetHighS8(c)
    return vmlslS8(a, b1, c1)
}

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighS16)
 */
public fun vmlslHighS16(a: Int32x4T, b: Int16x8T, c: Int16x8T): Int32x4T {
    val b1 = vgetHighS16(b)
    val c1 = vgetHighS16(c)
    return vmlslS16(a, b1, c1)
}

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighS32)
 */
public fun vmlslHighS32(a: Int64x2T, b: Int32x4T, c: Int32x4T): Int64x2T {
    val b1 = vgetHighS32(b)
    val c1 = vgetHighS32(c)
    return vmlslS32(a, b1, c1)
}

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighU8)
 */
public fun vmlslHighU8(a: Uint16x8T, b: Uint8x16T, c: Uint8x16T): Uint16x8T {
    val b1 = vgetHighU8(b)
    val c1 = vgetHighU8(c)
    return vmlslU8(a, b1, c1)
}

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighU16)
 */
public fun vmlslHighU16(a: Uint32x4T, b: Uint16x8T, c: Uint16x8T): Uint32x4T {
    val b1 = vgetHighU16(b)
    val c1 = vgetHighU16(c)
    return vmlslU16(a, b1, c1)
}

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslHighU32)
 */
public fun vmlslHighU32(a: Uint64x2T, b: Uint32x4T, c: Uint32x4T): Uint64x2T {
    val b1 = vgetHighU32(b)
    val c1 = vgetHighU32(c)
    return vmlslU32(a, b1, c1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighS8)
 */
public fun vmovlHighS8(a: Int8x16T): Int16x8T {
    val a1 = vgetHighS8(a)
    return vmovlS8(a1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighS16)
 */
public fun vmovlHighS16(a: Int16x8T): Int32x4T {
    val a1 = vgetHighS16(a)
    return vmovlS16(a1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighS32)
 */
public fun vmovlHighS32(a: Int32x4T): Int64x2T {
    val a1 = vgetHighS32(a)
    return vmovlS32(a1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighU8)
 */
public fun vmovlHighU8(a: Uint8x16T): Uint16x8T {
    val a1 = vgetHighU8(a)
    return vmovlU8(a1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighU16)
 */
public fun vmovlHighU16(a: Uint16x8T): Uint32x4T {
    val a1 = vgetHighU16(a)
    return vmovlU16(a1)
}

/**
 * Vector move
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlHighU32)
 */
public fun vmovlHighU32(a: Uint32x4T): Uint64x2T {
    val a1 = vgetHighU32(a)
    return vmovlU32(a1)
}

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighS16)
 */
public fun vmovnHighS16(a: Int8x8T, b: Int16x8T): Int8x16T = vcombineS8(a, run { val simdArg0 = b; Int8x16T() })

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighS32)
 */
public fun vmovnHighS32(a: Int16x4T, b: Int32x4T): Int16x8T = vcombineS16(a, run { val simdArg0 = b; Int16x8T() })

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighS64)
 */
public fun vmovnHighS64(a: Int32x2T, b: Int64x2T): Int32x4T = vcombineS32(a, run { val simdArg0 = b; Int32x4T() })

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighU16)
 */
public fun vmovnHighU16(a: Uint8x8T, b: Uint16x8T): Uint8x16T = vcombineU8(a, run { val simdArg0 = b; Uint8x16T() })

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighU32)
 */
public fun vmovnHighU32(a: Uint16x4T, b: Uint32x4T): Uint16x8T = vcombineU16(a, run { val simdArg0 = b; Uint16x8T() })

/**
 * Extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnHighU64)
 */
public fun vmovnHighU64(a: Uint32x2T, b: Uint64x2T): Uint32x4T = vcombineU32(a, run { val simdArg0 = b; Uint32x4T() })

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulF64)
 */
public fun vmulF64(a: Float64x1T, b: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqF64)
 */
public fun vmulqF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneF64)
 */
public fun vmulLaneF64(a: Float64x1T, b: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(vgetLaneF64(b, lane), PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqF16)
 */
public fun vmulLaneqF16(a: Float16x4T, b: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = vdupLaneqF16(b, lane); simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqF16)
 */
public fun vmulqLaneqF16(a: Float16x8T, b: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = vdupqLaneqF16(b, lane); simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqF64)
 */
public fun vmulLaneqF64(a: Float64x1T, b: Float64x2T, lane: Int): Float64x1T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = transmuteWithLayouts<Double, Float64x1T>(vgetqLaneF64(b, lane), PrimitiveLayouts.F64, UnresolvedTypeLayout<Float64x1T>()); simdArg0 }
}

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNF64)
 */
public fun vmulNF64(a: Float64x1T, b: Double): Float64x1T = run { val simdArg0 = a; val simdArg1 = vdupNF64(b); simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNF64)
 */
public fun vmulqNF64(a: Float64x2T, b: Double): Float64x2T = run { val simdArg0 = a; val simdArg1 = vdupqNF64(b); simdArg0 }

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmuldLaneF64)
 */
public fun vmuldLaneF64(a: Double, b: Float64x1T, lane: Int): Double {
    run {
        check(lane == 0)
    }
    val b1: Double = vgetLaneF64(b, lane)
    return a * b1
}

/**
 * Add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulhF16)
 */
public fun vmulhF16(a: F16, b: F16): F16 = a * b

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulhLaneF16)
 */
public fun vmulhLaneF16(a: F16, b: Float16x4T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1: F16 = vgetLaneF16(b, lane)
    return a * b1
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulhLaneqF16)
 */
public fun vmulhLaneqF16(a: F16, b: Float16x8T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val b1: F16 = vgetqLaneF16(b, lane)
    return a * b1
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneS16)
 */
public fun vmullHighLaneS16(a: Int16x8T, b: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullHighS16(a, vdupqLaneS16(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneqS16)
 */
public fun vmullHighLaneqS16(a: Int16x8T, b: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmullHighS16(a, vdupqLaneqS16(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneS32)
 */
public fun vmullHighLaneS32(a: Int32x4T, b: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmullHighS32(a, vdupqLaneS32(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneqS32)
 */
public fun vmullHighLaneqS32(a: Int32x4T, b: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullHighS32(a, vdupqLaneqS32(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneU16)
 */
public fun vmullHighLaneU16(a: Uint16x8T, b: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullHighU16(a, vdupqLaneU16(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneqU16)
 */
public fun vmullHighLaneqU16(a: Uint16x8T, b: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmullHighU16(a, vdupqLaneqU16(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneU32)
 */
public fun vmullHighLaneU32(a: Uint32x4T, b: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmullHighU32(a, vdupqLaneU32(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighLaneqU32)
 */
public fun vmullHighLaneqU32(a: Uint32x4T, b: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullHighU32(a, vdupqLaneqU32(b, lane))
}

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighNS16)
 */
public fun vmullHighNS16(a: Int16x8T, b: Short): Int32x4T = vmullHighS16(a, vdupqNS16(b))

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighNS32)
 */
public fun vmullHighNS32(a: Int32x4T, b: Int): Int64x2T = vmullHighS32(a, vdupqNS32(b))

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighNU16)
 */
public fun vmullHighNU16(a: Uint16x8T, b: UShort): Uint32x4T = vmullHighU16(a, vdupqNU16(b))

/**
 * Multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighNU32)
 */
public fun vmullHighNU32(a: Uint32x4T, b: UInt): Uint64x2T = vmullHighU32(a, vdupqNU32(b))

/**
 * Polynomial multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighP64)
 */
public fun vmullHighP64(a: Poly64x2T, b: Poly64x2T): P128 = vmullP64(vgetqLaneP64(a, 1uL), vgetqLaneP64(b, 1uL))

/**
 * Polynomial multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighP8)
 */
public fun vmullHighP8(a: Poly8x16T, b: Poly8x16T): Poly16x8T {
    val a1 = vgetHighP8(a)
    val b1 = vgetHighP8(b)
    return vmullP8(a1, b1)
}

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighS8)
 */
public fun vmullHighS8(a: Int8x16T, b: Int8x16T): Int16x8T {
    val a1 = vgetHighS8(a)
    val b1 = vgetHighS8(b)
    return vmullS8(a1, b1)
}

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighS16)
 */
public fun vmullHighS16(a: Int16x8T, b: Int16x8T): Int32x4T {
    val a1 = vgetHighS16(a)
    val b1 = vgetHighS16(b)
    return vmullS16(a1, b1)
}

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighS32)
 */
public fun vmullHighS32(a: Int32x4T, b: Int32x4T): Int64x2T {
    val a1 = vgetHighS32(a)
    val b1 = vgetHighS32(b)
    return vmullS32(a1, b1)
}

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighU8)
 */
public fun vmullHighU8(a: Uint8x16T, b: Uint8x16T): Uint16x8T {
    val a1 = vgetHighU8(a)
    val b1 = vgetHighU8(b)
    return vmullU8(a1, b1)
}

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighU16)
 */
public fun vmullHighU16(a: Uint16x8T, b: Uint16x8T): Uint32x4T {
    val a1 = vgetHighU16(a)
    val b1 = vgetHighU16(b)
    return vmullU16(a1, b1)
}

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullHighU32)
 */
public fun vmullHighU32(a: Uint32x4T, b: Uint32x4T): Uint64x2T {
    val a1 = vgetHighU32(a)
    val b1 = vgetHighU32(b)
    return vmullU32(a1, b1)
}

/**
 * Polynomial multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullP64)
 */
public fun vmullP64(a: P64, b: P64): P128 {
    fun externVmullP64(a1: P64, b1: P64): Int8x16T = Int8x16T()
    return transmuteWithLayouts<Int8x16T, P128>(externVmullP64(a, b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<P128>())
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneF64)
 */
public fun vmulqLaneF64(a: Float64x2T, b: Float64x1T, lane: Int): Float64x2T {
    run {
        check(lane == 0)
    }
    return run { val simdArg0 = a; val simdArg1 = vdupqLaneF64(b, lane); simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqF64)
 */
public fun vmulqLaneqF64(a: Float64x2T, b: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = vdupqLaneqF64(b, lane); simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulsLaneF32)
 */
public fun vmulsLaneF32(a: Float, b: Float32x2T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Float = vgetLaneF32(b, lane)
    return a * b1
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulsLaneqF32)
 */
public fun vmulsLaneqF32(a: Float, b: Float32x4T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Float = vgetqLaneF32(b, lane)
    return a * b1
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmuldLaneqF64)
 */
public fun vmuldLaneqF64(a: Double, b: Float64x2T, lane: Int): Double {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Double = vgetqLaneF64(b, lane)
    return a * b1
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxF16)
 */
public fun vmulxF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVmulxF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVmulxF16(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqF16)
 */
public fun vmulxqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVmulxqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVmulxqF16(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxF32)
 */
public fun vmulxF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVmulxF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVmulxF32(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqF32)
 */
public fun vmulxqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVmulxqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVmulxqF32(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxF64)
 */
public fun vmulxF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVmulxF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVmulxF64(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqF64)
 */
public fun vmulxqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVmulxqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVmulxqF64(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneF16)
 */
public fun vmulxLaneF16(a: Float16x4T, b: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxF16(a, vdupLaneF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneqF16)
 */
public fun vmulxLaneqF16(a: Float16x4T, b: Float16x8T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmulxF16(a, vdupLaneqF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneF16)
 */
public fun vmulxqLaneF16(a: Float16x8T, b: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxqF16(a, vdupqLaneF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneqF16)
 */
public fun vmulxqLaneqF16(a: Float16x8T, b: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmulxqF16(a, vdupqLaneqF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneF32)
 */
public fun vmulxLaneF32(a: Float32x2T, b: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxF32(a, vdupLaneF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneqF32)
 */
public fun vmulxLaneqF32(a: Float32x2T, b: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxF32(a, vdupLaneqF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneF32)
 */
public fun vmulxqLaneF32(a: Float32x4T, b: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxqF32(a, vdupqLaneF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneqF32)
 */
public fun vmulxqLaneqF32(a: Float32x4T, b: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxqF32(a, vdupqLaneqF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneqF64)
 */
public fun vmulxqLaneqF64(a: Float64x2T, b: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxqF64(a, vdupqLaneqF64(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneF64)
 */
public fun vmulxLaneF64(a: Float64x1T, b: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return vmulxF64(a, transmuteWithLayouts<Double, Float64x1T>(vgetLaneF64(b, lane), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>()))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxLaneqF64)
 */
public fun vmulxLaneqF64(a: Float64x1T, b: Float64x2T, lane: Int): Float64x1T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxF64(a, transmuteWithLayouts<Double, Float64x1T>(vgetqLaneF64(b, lane), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>()))
}

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxNF16)
 */
public fun vmulxNF16(a: Float16x4T, b: F16): Float16x4T = vmulxF16(a, vdupNF16(b))

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqNF16)
 */
public fun vmulxqNF16(a: Float16x8T, b: F16): Float16x8T = vmulxqF16(a, vdupqNF16(b))

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxdF64)
 */
public fun vmulxdF64(a: Double, b: Double): Double {
    fun externVmulxdF64(a1: Double, b1: Double): Double = 0.0
    return externVmulxdF64(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxsF32)
 */
public fun vmulxsF32(a: Float, b: Float): Float {
    fun externVmulxsF32(a1: Float, b1: Float): Float = 0.0f
    return externVmulxsF32(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxdLaneF64)
 */
public fun vmulxdLaneF64(a: Double, b: Float64x1T, lane: Int): Double {
    run {
        check(lane == 0)
    }
    return vmulxdF64(a, vgetLaneF64(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxdLaneqF64)
 */
public fun vmulxdLaneqF64(a: Double, b: Float64x2T, lane: Int): Double {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxdF64(a, vgetqLaneF64(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxsLaneF32)
 */
public fun vmulxsLaneF32(a: Float, b: Float32x2T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmulxsF32(a, vgetLaneF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxsLaneqF32)
 */
public fun vmulxsLaneqF32(a: Float, b: Float32x4T, lane: Int): Float {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxsF32(a, vgetqLaneF32(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxhF16)
 */
public fun vmulxhF16(a: F16, b: F16): F16 {
    fun externVmulxhF16(a1: F16, b1: F16): F16 = F16()
    return externVmulxhF16(a, b)
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxhLaneF16)
 */
public fun vmulxhLaneF16(a: F16, b: Float16x4T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmulxhF16(a, vgetLaneF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxhLaneqF16)
 */
public fun vmulxhLaneqF16(a: F16, b: Float16x8T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmulxhF16(a, vgetqLaneF16(b, lane))
}

/**
 * Floating-point multiply extended
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulxqLaneF64)
 */
public fun vmulxqLaneF64(a: Float64x2T, b: Float64x1T, lane: Int): Float64x2T {
    run {
        check(lane == 0)
    }
    return vmulxqF64(a, vdupqLaneF64(b, lane))
}

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegF64)
 */
public fun vnegF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqF64)
 */
public fun vnegqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegS64)
 */
public fun vnegS64(a: Int64x1T): Int64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqS64)
 */
public fun vnegqS64(a: Int64x2T): Int64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegdS64)
 */
public fun vnegdS64(a: Long): Long = a.wrappingNeg()

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vneghF16)
 */
public fun vneghF16(a: F16): F16 = -a

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadddF64)
 */
public fun vpadddF64(a: Float64x2T): Double {
    val a1: Double = vgetqLaneF64(a, 0.0)
    val a2: Double = vgetqLaneF64(a, 1.0)
    return a1 + a2
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddsF32)
 */
public fun vpaddsF32(a: Float32x2T): Float {
    val a1: Float = vgetLaneF32(a, 0.0f)
    val a2: Float = vgetLaneF32(a, 1.0f)
    return a1 + a2
}

/**
 * Add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadddS64)
 */
public fun vpadddS64(a: Int64x2T): Long = run { val simdArg0 = a; val simdArg1 = 0L; simdArg1 }

/**
 * Add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadddU64)
 */
public fun vpadddU64(a: Uint64x2T): ULong = run { val simdArg0 = a; val simdArg1 = 0uL; simdArg1 }

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqF16)
 */
public fun vpaddqF16(a: Float16x8T, b: Float16x8T): Float16x8T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(8)); Float16x8T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(8)); Float16x8T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqF32)
 */
public fun vpaddqF32(a: Float32x4T, b: Float32x4T): Float32x4T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(4)); Float32x4T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(4)); Float32x4T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqF64)
 */
public fun vpaddqF64(a: Float64x2T, b: Float64x2T): Float64x2T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(2)); Float64x2T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(2)); Float64x2T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqS8)
 */
public fun vpaddqS8(a: Int8x16T, b: Int8x16T): Int8x16T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(16)); Int8x16T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(16)); Int8x16T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqS16)
 */
public fun vpaddqS16(a: Int16x8T, b: Int16x8T): Int16x8T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(8)); Int16x8T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(8)); Int16x8T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqS32)
 */
public fun vpaddqS32(a: Int32x4T, b: Int32x4T): Int32x4T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(4)); Int32x4T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(4)); Int32x4T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqS64)
 */
public fun vpaddqS64(a: Int64x2T, b: Int64x2T): Int64x2T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(2)); Int64x2T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(2)); Int64x2T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqU8)
 */
public fun vpaddqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(16)); Uint8x16T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(16)); Uint8x16T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqU16)
 */
public fun vpaddqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(8)); Uint16x8T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(8)); Uint16x8T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqU32)
 */
public fun vpaddqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(4)); Uint32x4T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(4)); Uint32x4T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Add Pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddqU64)
 */
public fun vpaddqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T =
    run {
        val even = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(even(2)); Uint64x2T() }
        val odd = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(odd(2)); Uint64x2T() }
        run { val simdArg0 = even; val simdArg1 = odd; simdArg0 }
    }

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxF16)
 */
public fun vpmaxF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVpmaxF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVpmaxF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqF16)
 */
public fun vpmaxqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVpmaxqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVpmaxqF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmF16)
 */
public fun vpmaxnmF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVpmaxnmF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVpmaxnmF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmqF16)
 */
public fun vpmaxnmqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVpmaxnmqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVpmaxnmqF16(a, b)
}

/**
 * Floating-point Maximum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmF32)
 */
public fun vpmaxnmF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVpmaxnmF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVpmaxnmF32(a, b)
}

/**
 * Floating-point Maximum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmqF32)
 */
public fun vpmaxnmqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVpmaxnmqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVpmaxnmqF32(a, b)
}

/**
 * Floating-point Maximum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmqF64)
 */
public fun vpmaxnmqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVpmaxnmqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVpmaxnmqF64(a, b)
}

/**
 * Floating-point maximum number pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmqdF64)
 */
public fun vpmaxnmqdF64(a: Float64x2T): Double {
    fun externVpmaxnmqdF64(a1: Float64x2T): Double = 0.0
    return externVpmaxnmqdF64(a)
}

/**
 * Floating-point maximum number pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxnmsF32)
 */
public fun vpmaxnmsF32(a: Float32x2T): Float {
    fun externVpmaxnmsF32(a1: Float32x2T): Float = 0.0f
    return externVpmaxnmsF32(a)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqF32)
 */
public fun vpmaxqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVpmaxqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVpmaxqF32(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqF64)
 */
public fun vpmaxqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVpmaxqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVpmaxqF64(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqS8)
 */
public fun vpmaxqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun externVpmaxqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return externVpmaxqS8(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqS16)
 */
public fun vpmaxqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun externVpmaxqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return externVpmaxqS16(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqS32)
 */
public fun vpmaxqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun externVpmaxqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return externVpmaxqS32(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqU8)
 */
public fun vpmaxqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun externVpmaxqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return externVpmaxqU8(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqU16)
 */
public fun vpmaxqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun externVpmaxqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return externVpmaxqU16(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqU32)
 */
public fun vpmaxqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun externVpmaxqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVpmaxqU32(a, b)
}

/**
 * Floating-point maximum pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxqdF64)
 */
public fun vpmaxqdF64(a: Float64x2T): Double {
    fun externVpmaxqdF64(a1: Float64x2T): Double = 0.0
    return externVpmaxqdF64(a)
}

/**
 * Floating-point maximum pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxsF32)
 */
public fun vpmaxsF32(a: Float32x2T): Float {
    fun externVpmaxsF32(a1: Float32x2T): Float = 0.0f
    return externVpmaxsF32(a)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminF16)
 */
public fun vpminF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVpminF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVpminF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqF16)
 */
public fun vpminqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVpminqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVpminqF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmF16)
 */
public fun vpminnmF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun externVpminnmF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return externVpminnmF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmqF16)
 */
public fun vpminnmqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun externVpminnmqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return externVpminnmqF16(a, b)
}

/**
 * Floating-point Minimum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmF32)
 */
public fun vpminnmF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun externVpminnmF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return externVpminnmF32(a, b)
}

/**
 * Floating-point Minimum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmqF32)
 */
public fun vpminnmqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVpminnmqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVpminnmqF32(a, b)
}

/**
 * Floating-point Minimum Number Pairwise (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmqF64)
 */
public fun vpminnmqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVpminnmqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVpminnmqF64(a, b)
}

/**
 * Floating-point minimum number pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmqdF64)
 */
public fun vpminnmqdF64(a: Float64x2T): Double {
    fun externVpminnmqdF64(a1: Float64x2T): Double = 0.0
    return externVpminnmqdF64(a)
}

/**
 * Floating-point minimum number pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminnmsF32)
 */
public fun vpminnmsF32(a: Float32x2T): Float {
    fun externVpminnmsF32(a1: Float32x2T): Float = 0.0f
    return externVpminnmsF32(a)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqF32)
 */
public fun vpminqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun externVpminqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return externVpminqF32(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqF64)
 */
public fun vpminqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVpminqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVpminqF64(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqS8)
 */
public fun vpminqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun externVpminqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return externVpminqS8(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqS16)
 */
public fun vpminqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun externVpminqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return externVpminqS16(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqS32)
 */
public fun vpminqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun externVpminqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return externVpminqS32(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqU8)
 */
public fun vpminqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun externVpminqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return externVpminqU8(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqU16)
 */
public fun vpminqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun externVpminqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return externVpminqU16(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqU32)
 */
public fun vpminqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun externVpminqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVpminqU32(a, b)
}

/**
 * Floating-point minimum pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminqdF64)
 */
public fun vpminqdF64(a: Float64x2T): Double {
    fun externVpminqdF64(a1: Float64x2T): Double = 0.0
    return externVpminqdF64(a)
}

/**
 * Floating-point minimum pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminsF32)
 */
public fun vpminsF32(a: Float32x2T): Float {
    fun externVpminsF32(a1: Float32x2T): Float = 0.0f
    return externVpminsF32(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsS64)
 */
public fun vqabsS64(a: Int64x1T): Int64x1T {
    fun externVqabsS64(a1: Int64x1T): Int64x1T = Int64x1T()
    return externVqabsS64(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsqS64)
 */
public fun vqabsqS64(a: Int64x2T): Int64x2T {
    fun externVqabsqS64(a1: Int64x2T): Int64x2T = Int64x2T()
    return externVqabsqS64(a)
}

/**
 * Signed saturating absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsbS8)
 */
public fun vqabsbS8(a: Byte): Byte = vgetLaneS8(vqabsS8(vdupNS8(a)), 0)

/**
 * Signed saturating absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabshS16)
 */
public fun vqabshS16(a: Short): Short = vgetLaneS16(vqabsS16(vdupNS16(a)), 0)

/**
 * Signed saturating absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabssS32)
 */
public fun vqabssS32(a: Int): Int {
    fun externVqabssS32(a1: Int): Int = 0
    return externVqabssS32(a)
}

/**
 * Signed saturating absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsdS64)
 */
public fun vqabsdS64(a: Long): Long {
    fun externVqabsdS64(a1: Long): Long = 0L
    return externVqabsdS64(a)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddbS8)
 */
public fun vqaddbS8(a: Byte, b: Byte): Byte {
    val a1: Int8x8T = vdupNS8(a)
    val b1: Int8x8T = vdupNS8(b)
    return vgetLaneS8(vqaddS8(a1, b1), 0)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddhS16)
 */
public fun vqaddhS16(a: Short, b: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetLaneS16(vqaddS16(a1, b1), 0)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddbU8)
 */
public fun vqaddbU8(a: UByte, b: UByte): UByte {
    val a1: Uint8x8T = vdupNU8(a)
    val b1: Uint8x8T = vdupNU8(b)
    return vgetLaneU8(vqaddU8(a1, b1), 0u)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddhU16)
 */
public fun vqaddhU16(a: UShort, b: UShort): UShort {
    val a1: Uint16x4T = vdupNU16(a)
    val b1: Uint16x4T = vdupNU16(b)
    return vgetLaneU16(vqaddU16(a1, b1), 0u)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddsS32)
 */
public fun vqaddsS32(a: Int, b: Int): Int {
    fun externVqaddsS32(a1: Int, b1: Int): Int = 0
    return externVqaddsS32(a, b)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqadddS64)
 */
public fun vqadddS64(a: Long, b: Long): Long {
    fun externVqadddS64(a1: Long, b1: Long): Long = 0L
    return externVqadddS64(a, b)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddsU32)
 */
public fun vqaddsU32(a: UInt, b: UInt): UInt {
    fun externVqaddsU32(a1: UInt, b1: UInt): UInt = 0u
    return externVqaddsU32(a, b)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqadddU64)
 */
public fun vqadddU64(a: ULong, b: ULong): ULong {
    fun externVqadddU64(a1: ULong, b1: ULong): ULong = 0uL
    return externVqadddU64(a, b)
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighLaneS16)
 */
public fun vqdmlalHighLaneS16(a: Int32x4T, b: Int16x8T, c: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqaddqS32(a, vqdmullHighLaneS16(b, c, n))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighLaneqS16)
 */
public fun vqdmlalHighLaneqS16(a: Int32x4T, b: Int16x8T, c: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqaddqS32(a, vqdmullHighLaneqS16(b, c, n))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighLaneS32)
 */
public fun vqdmlalHighLaneS32(a: Int64x2T, b: Int32x4T, c: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vqaddqS64(a, vqdmullHighLaneS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighLaneqS32)
 */
public fun vqdmlalHighLaneqS32(a: Int64x2T, b: Int32x4T, c: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqaddqS64(a, vqdmullHighLaneqS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighNS16)
 */
public fun vqdmlalHighNS16(a: Int32x4T, b: Int16x8T, c: Short): Int32x4T = vqaddqS32(a, vqdmullHighNS16(b, c))

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighS16)
 */
public fun vqdmlalHighS16(a: Int32x4T, b: Int16x8T, c: Int16x8T): Int32x4T = vqaddqS32(a, vqdmullHighS16(b, c))

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighNS32)
 */
public fun vqdmlalHighNS32(a: Int64x2T, b: Int32x4T, c: Int): Int64x2T = vqaddqS64(a, vqdmullHighNS32(b, c))

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalHighS32)
 */
public fun vqdmlalHighS32(a: Int64x2T, b: Int32x4T, c: Int32x4T): Int64x2T = vqaddqS64(a, vqdmullHighS32(b, c))

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalLaneqS16)
 */
public fun vqdmlalLaneqS16(a: Int32x4T, b: Int16x4T, c: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqaddqS32(a, vqdmullLaneqS16(b, c, n))
}

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalLaneqS32)
 */
public fun vqdmlalLaneqS32(a: Int64x2T, b: Int32x2T, c: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqaddqS64(a, vqdmullLaneqS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalhLaneS16)
 */
public fun vqdmlalhLaneS16(a: Int, b: Short, c: Int16x4T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmlalhS16(a, b, vgetLaneS16(c, lane))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalhLaneqS16)
 */
public fun vqdmlalhLaneqS16(a: Int, b: Short, c: Int16x8T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqdmlalhS16(a, b, vgetqLaneS16(c, lane))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalsLaneS32)
 */
public fun vqdmlalsLaneS32(a: Long, b: Int, c: Int32x2T, lane: Int): Long {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqdmlalsS32(a, b, vgetLaneS32(c, lane))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalsLaneqS32)
 */
public fun vqdmlalsLaneqS32(a: Long, b: Int, c: Int32x4T, lane: Int): Long {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmlalsS32(a, b, vgetqLaneS32(c, lane))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalhS16)
 */
public fun vqdmlalhS16(a: Int, b: Short, c: Short): Int {
    val x: Int32x4T = vqdmullS16(vdupNS16(b), vdupNS16(c))
    return vqaddsS32(a, vgetqLaneS32(x, 0))
}

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalsS32)
 */
public fun vqdmlalsS32(a: Long, b: Int, c: Int): Long {
    val x: Long = vqadddS64(a, vqdmullsS32(b, c))
    return x
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighLaneS16)
 */
public fun vqdmlslHighLaneS16(a: Int32x4T, b: Int16x8T, c: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqsubqS32(a, vqdmullHighLaneS16(b, c, n))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighLaneqS16)
 */
public fun vqdmlslHighLaneqS16(a: Int32x4T, b: Int16x8T, c: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqsubqS32(a, vqdmullHighLaneqS16(b, c, n))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighLaneS32)
 */
public fun vqdmlslHighLaneS32(a: Int64x2T, b: Int32x4T, c: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vqsubqS64(a, vqdmullHighLaneS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighLaneqS32)
 */
public fun vqdmlslHighLaneqS32(a: Int64x2T, b: Int32x4T, c: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqsubqS64(a, vqdmullHighLaneqS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighNS16)
 */
public fun vqdmlslHighNS16(a: Int32x4T, b: Int16x8T, c: Short): Int32x4T = vqsubqS32(a, vqdmullHighNS16(b, c))

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighS16)
 */
public fun vqdmlslHighS16(a: Int32x4T, b: Int16x8T, c: Int16x8T): Int32x4T = vqsubqS32(a, vqdmullHighS16(b, c))

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighNS32)
 */
public fun vqdmlslHighNS32(a: Int64x2T, b: Int32x4T, c: Int): Int64x2T = vqsubqS64(a, vqdmullHighNS32(b, c))

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslHighS32)
 */
public fun vqdmlslHighS32(a: Int64x2T, b: Int32x4T, c: Int32x4T): Int64x2T = vqsubqS64(a, vqdmullHighS32(b, c))

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslLaneqS16)
 */
public fun vqdmlslLaneqS16(a: Int32x4T, b: Int16x4T, c: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqsubqS32(a, vqdmullLaneqS16(b, c, n))
}

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslLaneqS32)
 */
public fun vqdmlslLaneqS32(a: Int64x2T, b: Int32x2T, c: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqsubqS64(a, vqdmullLaneqS32(b, c, n))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslhLaneS16)
 */
public fun vqdmlslhLaneS16(a: Int, b: Short, c: Int16x4T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmlslhS16(a, b, vgetLaneS16(c, lane))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslhLaneqS16)
 */
public fun vqdmlslhLaneqS16(a: Int, b: Short, c: Int16x8T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqdmlslhS16(a, b, vgetqLaneS16(c, lane))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslsLaneS32)
 */
public fun vqdmlslsLaneS32(a: Long, b: Int, c: Int32x2T, lane: Int): Long {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqdmlslsS32(a, b, vgetLaneS32(c, lane))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslsLaneqS32)
 */
public fun vqdmlslsLaneqS32(a: Long, b: Int, c: Int32x4T, lane: Int): Long {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmlslsS32(a, b, vgetqLaneS32(c, lane))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslhS16)
 */
public fun vqdmlslhS16(a: Int, b: Short, c: Short): Int {
    val x: Int32x4T = vqdmullS16(vdupNS16(b), vdupNS16(c))
    return vqsubsS32(a, vgetqLaneS32(x, 0))
}

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslsS32)
 */
public fun vqdmlslsS32(a: Long, b: Int, c: Int): Long {
    val x: Long = vqsubdS64(a, vqdmullsS32(b, c))
    return x
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhLaneS16)
 */
public fun vqdmulhLaneS16(a: Int16x4T, b: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmulhS16(a, vdupNS16(vgetLaneS16(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqLaneS16)
 */
public fun vqdmulhqLaneS16(a: Int16x8T, b: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmulhqS16(a, vdupqNS16(vgetLaneS16(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhLaneS32)
 */
public fun vqdmulhLaneS32(a: Int32x2T, b: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqdmulhS32(a, vdupNS32(vgetLaneS32(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqLaneS32)
 */
public fun vqdmulhqLaneS32(a: Int32x4T, b: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqdmulhqS32(a, vdupqNS32(vgetLaneS32(b, lane)))
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhhLaneS16)
 */
public fun vqdmulhhLaneS16(a: Short, b: Int16x4T, n: Int): Short {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Short = vgetLaneS16(b, n)
    return vqdmulhhS16(a, b1)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhhLaneqS16)
 */
public fun vqdmulhhLaneqS16(a: Short, b: Int16x8T, n: Int): Short {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    val b1: Short = vgetqLaneS16(b, n)
    return vqdmulhhS16(a, b1)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhhS16)
 */
public fun vqdmulhhS16(a: Short, b: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetLaneS16(vqdmulhS16(a1, b1), 0)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhsS32)
 */
public fun vqdmulhsS32(a: Int, b: Int): Int {
    val a1: Int32x2T = vdupNS32(a)
    val b1: Int32x2T = vdupNS32(b)
    return vgetLaneS32(vqdmulhS32(a1, b1), 0)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhsLaneS32)
 */
public fun vqdmulhsLaneS32(a: Int, b: Int32x2T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Int = vgetLaneS32(b, n)
    return vqdmulhsS32(a, b1)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhsLaneqS32)
 */
public fun vqdmulhsLaneqS32(a: Int, b: Int32x4T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Int = vgetqLaneS32(b, n)
    return vqdmulhsS32(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighLaneS16)
 */
public fun vqdmullHighLaneS16(a: Int16x8T, b: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val a1 = vgetHighS16(a)
    val b1 = vdupLaneS16(b, n)
    return vqdmullS16(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighLaneqS32)
 */
public fun vqdmullHighLaneqS32(a: Int32x4T, b: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val a1 = vgetHighS32(a)
    val b1 = vdupLaneqS32(b, n)
    return vqdmullS32(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighLaneS32)
 */
public fun vqdmullHighLaneS32(a: Int32x4T, b: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    val a1 = vgetHighS32(a)
    val b1 = vdupLaneS32(b, n)
    return vqdmullS32(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighLaneqS16)
 */
public fun vqdmullHighLaneqS16(a: Int16x8T, b: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    val a1 = vgetHighS16(a)
    val b1 = vdupLaneqS16(b, n)
    return vqdmullS16(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighNS16)
 */
public fun vqdmullHighNS16(a: Int16x8T, b: Short): Int32x4T {
    val a1 = vgetHighS16(a)
    val b1 = vdupNS16(b)
    return vqdmullS16(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighNS32)
 */
public fun vqdmullHighNS32(a: Int32x4T, b: Int): Int64x2T {
    val a1 = vgetHighS32(a)
    val b1 = vdupNS32(b)
    return vqdmullS32(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighS16)
 */
public fun vqdmullHighS16(a: Int16x8T, b: Int16x8T): Int32x4T {
    val a1 = vgetHighS16(a)
    val b1 = vgetHighS16(b)
    return vqdmullS16(a1, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullHighS32)
 */
public fun vqdmullHighS32(a: Int32x4T, b: Int32x4T): Int64x2T {
    val a1 = vgetHighS32(a)
    val b1 = vgetHighS32(b)
    return vqdmullS32(a1, b1)
}

/**
 * Vector saturating doubling long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullLaneqS16)
 */
public fun vqdmullLaneqS16(a: Int16x4T, b: Int16x8T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    val b1 = vdupLaneqS16(b, n)
    return vqdmullS16(a, b1)
}

/**
 * Vector saturating doubling long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullLaneqS32)
 */
public fun vqdmullLaneqS32(a: Int32x2T, b: Int32x4T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupLaneqS32(b, n)
    return vqdmullS32(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullhLaneS16)
 */
public fun vqdmullhLaneS16(a: Short, b: Int16x4T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Short = vgetLaneS16(b, n)
    return vqdmullhS16(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullsLaneqS32)
 */
public fun vqdmullsLaneqS32(a: Int, b: Int32x4T, n: Int): Long {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1: Int = vgetqLaneS32(b, n)
    return vqdmullsS32(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullhLaneqS16)
 */
public fun vqdmullhLaneqS16(a: Short, b: Int16x8T, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    val b1: Short = vgetqLaneS16(b, n)
    return vqdmullhS16(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullhS16)
 */
public fun vqdmullhS16(a: Short, b: Short): Int {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetqLaneS32(vqdmullS16(a1, b1), 0)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullsLaneS32)
 */
public fun vqdmullsLaneS32(a: Int, b: Int32x2T, n: Int): Long {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    val b1: Int = vgetLaneS32(b, n)
    return vqdmullsS32(a, b1)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullsS32)
 */
public fun vqdmullsS32(a: Int, b: Int): Long {
    fun externVqdmullsS32(a1: Int, b1: Int): Long = 0L
    return externVqdmullsS32(a, b)
}

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighS16)
 */
public fun vqmovnHighS16(a: Int8x8T, b: Int16x8T): Int8x16T = vcombineS8(a, vqmovnS16(b))

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighS32)
 */
public fun vqmovnHighS32(a: Int16x4T, b: Int32x4T): Int16x8T = vcombineS16(a, vqmovnS32(b))

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighS64)
 */
public fun vqmovnHighS64(a: Int32x2T, b: Int64x2T): Int32x4T = vcombineS32(a, vqmovnS64(b))

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighU16)
 */
public fun vqmovnHighU16(a: Uint8x8T, b: Uint16x8T): Uint8x16T = vcombineU8(a, vqmovnU16(b))

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighU32)
 */
public fun vqmovnHighU32(a: Uint16x4T, b: Uint32x4T): Uint16x8T = vcombineU16(a, vqmovnU32(b))

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnHighU64)
 */
public fun vqmovnHighU64(a: Uint32x2T, b: Uint64x2T): Uint32x4T = vcombineU32(a, vqmovnU64(b))

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovndS64)
 */
public fun vqmovndS64(a: Long): Int {
    fun externVqmovndS64(a1: Long): Int = 0
    return externVqmovndS64(a)
}

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovndU64)
 */
public fun vqmovndU64(a: ULong): UInt {
    fun externVqmovndU64(a1: ULong): UInt = 0u
    return externVqmovndU64(a)
}

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnhS16)
 */
public fun vqmovnhS16(a: Short): Byte = vgetLaneS8(vqmovnS16(vdupqNS16(a)), 0)

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnsS32)
 */
public fun vqmovnsS32(a: Int): Short = vgetLaneS16(vqmovnS32(vdupqNS32(a)), 0)

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnhU16)
 */
public fun vqmovnhU16(a: UShort): UByte = vgetLaneU8(vqmovnU16(vdupqNU16(a)), 0u)

/**
 * Saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnsU32)
 */
public fun vqmovnsU32(a: UInt): UShort = vgetLaneU16(vqmovnU32(vdupqNU32(a)), 0u)

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunHighS16)
 */
public fun vqmovunHighS16(a: Uint8x8T, b: Int16x8T): Uint8x16T = vcombineU8(a, vqmovunS16(b))

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunHighS32)
 */
public fun vqmovunHighS32(a: Uint16x4T, b: Int32x4T): Uint16x8T = vcombineU16(a, vqmovunS32(b))

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunHighS64)
 */
public fun vqmovunHighS64(a: Uint32x2T, b: Int64x2T): Uint32x4T = vcombineU32(a, vqmovunS64(b))

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunhS16)
 */
public fun vqmovunhS16(a: Short): UByte = vgetLaneU8(vqmovunS16(vdupqNS16(a)), 0u)

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunsS32)
 */
public fun vqmovunsS32(a: Int): UShort = vgetLaneU16(vqmovunS32(vdupqNS32(a)), 0u)

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovundS64)
 */
public fun vqmovundS64(a: Long): UInt = vgetLaneU32(vqmovunS64(vdupqNS64(a)), 0u)

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegS64)
 */
public fun vqnegS64(a: Int64x1T): Int64x1T {
    fun externVqnegS64(a1: Int64x1T): Int64x1T = Int64x1T()
    return externVqnegS64(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegqS64)
 */
public fun vqnegqS64(a: Int64x2T): Int64x2T {
    fun externVqnegqS64(a1: Int64x2T): Int64x2T = Int64x2T()
    return externVqnegqS64(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegbS8)
 */
public fun vqnegbS8(a: Byte): Byte = vgetLaneS8(vqnegS8(vdupNS8(a)), 0)

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqneghS16)
 */
public fun vqneghS16(a: Short): Short = vgetLaneS16(vqnegS16(vdupNS16(a)), 0)

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegsS32)
 */
public fun vqnegsS32(a: Int): Int = vgetLaneS32(vqnegS32(vdupNS32(a)), 0)

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegdS64)
 */
public fun vqnegdS64(a: Long): Long = vgetLaneS64(vqnegS64(vdupNS64(a)), 0L)

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahLaneS16)
 */
public fun vqrdmlahLaneS16(a: Int16x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupLaneS16(c, lane)
    return vqrdmlahS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahLaneS32)
 */
public fun vqrdmlahLaneS32(a: Int32x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vdupLaneS32(c, lane)
    return vqrdmlahS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahLaneqS16)
 */
public fun vqrdmlahLaneqS16(a: Int16x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c1 = vdupLaneqS16(c, lane)
    return vqrdmlahS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahLaneqS32)
 */
public fun vqrdmlahLaneqS32(a: Int32x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupLaneqS32(c, lane)
    return vqrdmlahS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqLaneS16)
 */
public fun vqrdmlahqLaneS16(a: Int16x8T, b: Int16x8T, c: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupqLaneS16(c, lane)
    return vqrdmlahqS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqLaneS32)
 */
public fun vqrdmlahqLaneS32(a: Int32x4T, b: Int32x4T, c: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vdupqLaneS32(c, lane)
    return vqrdmlahqS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqLaneqS16)
 */
public fun vqrdmlahqLaneqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c1 = vdupqLaneqS16(c, lane)
    return vqrdmlahqS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqLaneqS32)
 */
public fun vqrdmlahqLaneqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupqLaneqS32(c, lane)
    return vqrdmlahqS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahS16)
 */
public fun vqrdmlahS16(a: Int16x4T, b: Int16x4T, c: Int16x4T): Int16x4T {
    fun externVqrdmlahS16(a1: Int16x4T, b1: Int16x4T, c1: Int16x4T): Int16x4T = Int16x4T()
    return externVqrdmlahS16(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqS16)
 */
public fun vqrdmlahqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T {
    fun externVqrdmlahqS16(a1: Int16x8T, b1: Int16x8T, c1: Int16x8T): Int16x8T = Int16x8T()
    return externVqrdmlahqS16(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahS32)
 */
public fun vqrdmlahS32(a: Int32x2T, b: Int32x2T, c: Int32x2T): Int32x2T {
    fun externVqrdmlahS32(a1: Int32x2T, b1: Int32x2T, c1: Int32x2T): Int32x2T = Int32x2T()
    return externVqrdmlahS32(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahqS32)
 */
public fun vqrdmlahqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T {
    fun externVqrdmlahqS32(a1: Int32x4T, b1: Int32x4T, c1: Int32x4T): Int32x4T = Int32x4T()
    return externVqrdmlahqS32(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahhLaneS16)
 */
public fun vqrdmlahhLaneS16(a: Short, b: Short, c: Int16x4T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmlahhS16(a, b, vgetLaneS16(c, lane))
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahhLaneqS16)
 */
public fun vqrdmlahhLaneqS16(a: Short, b: Short, c: Int16x8T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqrdmlahhS16(a, b, vgetqLaneS16(c, lane))
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahsLaneS32)
 */
public fun vqrdmlahsLaneS32(a: Int, b: Int, c: Int32x2T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqrdmlahsS32(a, b, vgetLaneS32(c, lane))
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahsLaneqS32)
 */
public fun vqrdmlahsLaneqS32(a: Int, b: Int, c: Int32x4T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmlahsS32(a, b, vgetqLaneS32(c, lane))
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahhS16)
 */
public fun vqrdmlahhS16(a: Short, b: Short, c: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    val c1: Int16x4T = vdupNS16(c)
    return vgetLaneS16(vqrdmlahS16(a1, b1, c1), 0)
}

/**
 * Signed saturating rounding doubling multiply accumulate returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlahsS32)
 */
public fun vqrdmlahsS32(a: Int, b: Int, c: Int): Int {
    val a1: Int32x2T = vdupNS32(a)
    val b1: Int32x2T = vdupNS32(b)
    val c1: Int32x2T = vdupNS32(c)
    return vgetLaneS32(vqrdmlahS32(a1, b1, c1), 0)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshLaneS16)
 */
public fun vqrdmlshLaneS16(a: Int16x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupLaneS16(c, lane)
    return vqrdmlshS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshLaneS32)
 */
public fun vqrdmlshLaneS32(a: Int32x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vdupLaneS32(c, lane)
    return vqrdmlshS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshLaneqS16)
 */
public fun vqrdmlshLaneqS16(a: Int16x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c1 = vdupLaneqS16(c, lane)
    return vqrdmlshS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshLaneqS32)
 */
public fun vqrdmlshLaneqS32(a: Int32x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupLaneqS32(c, lane)
    return vqrdmlshS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqLaneS16)
 */
public fun vqrdmlshqLaneS16(a: Int16x8T, b: Int16x8T, c: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupqLaneS16(c, lane)
    return vqrdmlshqS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqLaneS32)
 */
public fun vqrdmlshqLaneS32(a: Int32x4T, b: Int32x4T, c: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vdupqLaneS32(c, lane)
    return vqrdmlshqS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqLaneqS16)
 */
public fun vqrdmlshqLaneqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val c1 = vdupqLaneqS16(c, lane)
    return vqrdmlshqS16(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqLaneqS32)
 */
public fun vqrdmlshqLaneqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vdupqLaneqS32(c, lane)
    return vqrdmlshqS32(a, b, c1)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshS16)
 */
public fun vqrdmlshS16(a: Int16x4T, b: Int16x4T, c: Int16x4T): Int16x4T {
    fun externVqrdmlshS16(a1: Int16x4T, b1: Int16x4T, c1: Int16x4T): Int16x4T = Int16x4T()
    return externVqrdmlshS16(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqS16)
 */
public fun vqrdmlshqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T {
    fun externVqrdmlshqS16(a1: Int16x8T, b1: Int16x8T, c1: Int16x8T): Int16x8T = Int16x8T()
    return externVqrdmlshqS16(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshS32)
 */
public fun vqrdmlshS32(a: Int32x2T, b: Int32x2T, c: Int32x2T): Int32x2T {
    fun externVqrdmlshS32(a1: Int32x2T, b1: Int32x2T, c1: Int32x2T): Int32x2T = Int32x2T()
    return externVqrdmlshS32(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshqS32)
 */
public fun vqrdmlshqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T {
    fun externVqrdmlshqS32(a1: Int32x4T, b1: Int32x4T, c1: Int32x4T): Int32x4T = Int32x4T()
    return externVqrdmlshqS32(a, b, c)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshhLaneS16)
 */
public fun vqrdmlshhLaneS16(a: Short, b: Short, c: Int16x4T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmlshhS16(a, b, vgetLaneS16(c, lane))
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshhLaneqS16)
 */
public fun vqrdmlshhLaneqS16(a: Short, b: Short, c: Int16x8T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqrdmlshhS16(a, b, vgetqLaneS16(c, lane))
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshsLaneS32)
 */
public fun vqrdmlshsLaneS32(a: Int, b: Int, c: Int32x2T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqrdmlshsS32(a, b, vgetLaneS32(c, lane))
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshsLaneqS32)
 */
public fun vqrdmlshsLaneqS32(a: Int, b: Int, c: Int32x4T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmlshsS32(a, b, vgetqLaneS32(c, lane))
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshhS16)
 */
public fun vqrdmlshhS16(a: Short, b: Short, c: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    val c1: Int16x4T = vdupNS16(c)
    return vgetLaneS16(vqrdmlshS16(a1, b1, c1), 0)
}

/**
 * Signed saturating rounding doubling multiply subtract returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmlshsS32)
 */
public fun vqrdmlshsS32(a: Int, b: Int, c: Int): Int {
    val a1: Int32x2T = vdupNS32(a)
    val b1: Int32x2T = vdupNS32(b)
    val c1: Int32x2T = vdupNS32(c)
    return vgetLaneS32(vqrdmlshS32(a1, b1, c1), 0)
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhhLaneS16)
 */
public fun vqrdmulhhLaneS16(a: Short, b: Int16x4T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmulhhS16(a, vgetLaneS16(b, lane))
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhhLaneqS16)
 */
public fun vqrdmulhhLaneqS16(a: Short, b: Int16x8T, lane: Int): Short {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqrdmulhhS16(a, vgetqLaneS16(b, lane))
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhsLaneS32)
 */
public fun vqrdmulhsLaneS32(a: Int, b: Int32x2T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vqrdmulhsS32(a, vgetLaneS32(b, lane))
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhsLaneqS32)
 */
public fun vqrdmulhsLaneqS32(a: Int, b: Int32x4T, lane: Int): Int {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqrdmulhsS32(a, vgetqLaneS32(b, lane))
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhhS16)
 */
public fun vqrdmulhhS16(a: Short, b: Short): Short = vgetLaneS16(vqrdmulhS16(vdupNS16(a), vdupNS16(b)), 0)

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhsS32)
 */
public fun vqrdmulhsS32(a: Int, b: Int): Int = vgetLaneS32(vqrdmulhS32(vdupNS32(a), vdupNS32(b)), 0)

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlbS8)
 */
public fun vqrshlbS8(a: Byte, b: Byte): Byte {
    val a1: Int8x8T = vdupNS8(a)
    val b1: Int8x8T = vdupNS8(b)
    return vgetLaneS8(vqrshlS8(a1, b1), 0)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlhS16)
 */
public fun vqrshlhS16(a: Short, b: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetLaneS16(vqrshlS16(a1, b1), 0)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlbU8)
 */
public fun vqrshlbU8(a: UByte, b: Byte): UByte {
    val a1: Uint8x8T = vdupNU8(a)
    val b1: Int8x8T = vdupNS8(b)
    return vgetLaneU8(vqrshlU8(a1, b1), 0u)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlhU16)
 */
public fun vqrshlhU16(a: UShort, b: Short): UShort {
    val a1: Uint16x4T = vdupNU16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetLaneU16(vqrshlU16(a1, b1), 0u)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshldS64)
 */
public fun vqrshldS64(a: Long, b: Long): Long {
    fun externVqrshldS64(a1: Long, b1: Long): Long = 0L
    return externVqrshldS64(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlsS32)
 */
public fun vqrshlsS32(a: Int, b: Int): Int {
    fun externVqrshlsS32(a1: Int, b1: Int): Int = 0
    return externVqrshlsS32(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlsU32)
 */
public fun vqrshlsU32(a: UInt, b: Int): UInt {
    fun externVqrshlsU32(a1: UInt, b1: Int): UInt = 0u
    return externVqrshlsU32(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshldU64)
 */
public fun vqrshldU64(a: ULong, b: Long): ULong {
    fun externVqrshldU64(a1: ULong, b1: Long): ULong = 0uL
    return externVqrshldU64(a, b)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNS16)
 */
public fun vqrshrnHighNS16(a: Int8x8T, b: Int16x8T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineS8(a, vqrshrnNS16(b, n))
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNS32)
 */
public fun vqrshrnHighNS32(a: Int16x4T, b: Int32x4T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineS16(a, vqrshrnNS32(b, n))
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNS64)
 */
public fun vqrshrnHighNS64(a: Int32x2T, b: Int64x2T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineS32(a, vqrshrnNS64(b, n))
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNU16)
 */
public fun vqrshrnHighNU16(a: Uint8x8T, b: Uint16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vqrshrnNU16(b, n))
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNU32)
 */
public fun vqrshrnHighNU32(a: Uint16x4T, b: Uint32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vqrshrnNU32(b, n))
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnHighNU64)
 */
public fun vqrshrnHighNU64(a: Uint32x2T, b: Uint64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vqrshrnNU64(b, n))
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrndNU64)
 */
public fun vqrshrndNU64(a: ULong, n: Int): UInt {
    run {
        check(n >= 1 && n <= 32)
    }
    val a1: Uint64x2T = vdupqNU64(a)
    return vgetLaneU32(vqrshrnNU64(a1, n), 0u)
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnhNU16)
 */
public fun vqrshrnhNU16(a: UShort, n: Int): UByte {
    run {
        check(n >= 1 && n <= 8)
    }
    val a1: Uint16x8T = vdupqNU16(a)
    return vgetLaneU8(vqrshrnNU16(a1, n), 0u)
}

/**
 * Unsigned saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnsNU32)
 */
public fun vqrshrnsNU32(a: UInt, n: Int): UShort {
    run {
        check(n >= 1 && n <= 16)
    }
    val a1: Uint32x4T = vdupqNU32(a)
    return vgetLaneU16(vqrshrnNU32(a1, n), 0u)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnhNS16)
 */
public fun vqrshrnhNS16(a: Short, n: Int): Byte {
    run {
        check(n >= 1 && n <= 8)
    }
    val a1: Int16x8T = vdupqNS16(a)
    return vgetLaneS8(vqrshrnNS16(a1, n), 0)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnsNS32)
 */
public fun vqrshrnsNS32(a: Int, n: Int): Short {
    run {
        check(n >= 1 && n <= 16)
    }
    val a1: Int32x4T = vdupqNS32(a)
    return vgetLaneS16(vqrshrnNS32(a1, n), 0)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrndNS64)
 */
public fun vqrshrndNS64(a: Long, n: Int): Int {
    run {
        check(n >= 1 && n <= 32)
    }
    val a1: Int64x2T = vdupqNS64(a)
    return vgetLaneS32(vqrshrnNS64(a1, n), 0)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunHighNS16)
 */
public fun vqrshrunHighNS16(a: Uint8x8T, b: Int16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vqrshrunNS16(b, n))
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunHighNS32)
 */
public fun vqrshrunHighNS32(a: Uint16x4T, b: Int32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vqrshrunNS32(b, n))
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunHighNS64)
 */
public fun vqrshrunHighNS64(a: Uint32x2T, b: Int64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vqrshrunNS64(b, n))
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrundNS64)
 */
public fun vqrshrundNS64(a: Long, n: Int): UInt {
    run {
        check(n >= 1 && n <= 32)
    }
    val a1: Int64x2T = vdupqNS64(a)
    return vgetLaneU32(vqrshrunNS64(a1, n), 0u)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunhNS16)
 */
public fun vqrshrunhNS16(a: Short, n: Int): UByte {
    run {
        check(n >= 1 && n <= 8)
    }
    val a1: Int16x8T = vdupqNS16(a)
    return vgetLaneU8(vqrshrunNS16(a1, n), 0u)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunsNS32)
 */
public fun vqrshrunsNS32(a: Int, n: Int): UShort {
    run {
        check(n >= 1 && n <= 16)
    }
    val a1: Int32x4T = vdupqNS32(a)
    return vgetLaneU16(vqrshrunNS32(a1, n), 0u)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlbNS8)
 */
public fun vqshlbNS8(a: Byte, n: Int): Byte {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneS8(vqshlNS8(vdupNS8(a), n), 0)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshldNS64)
 */
public fun vqshldNS64(a: Long, n: Int): Long {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vgetLaneS64(vqshlNS64(vdupNS64(a), n), 0L)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlhNS16)
 */
public fun vqshlhNS16(a: Short, n: Int): Short {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetLaneS16(vqshlNS16(vdupNS16(a), n), 0)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlsNS32)
 */
public fun vqshlsNS32(a: Int, n: Int): Int {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vgetLaneS32(vqshlNS32(vdupNS32(a), n), 0)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlbNU8)
 */
public fun vqshlbNU8(a: UByte, n: Int): UByte {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneU8(vqshlNU8(vdupNU8(a), n), 0u)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshldNU64)
 */
public fun vqshldNU64(a: ULong, n: Int): ULong {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vgetLaneU64(vqshlNU64(vdupNU64(a), n), 0uL)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlhNU16)
 */
public fun vqshlhNU16(a: UShort, n: Int): UShort {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetLaneU16(vqshlNU16(vdupNU16(a), n), 0u)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlsNU32)
 */
public fun vqshlsNU32(a: UInt, n: Int): UInt {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vgetLaneU32(vqshlNU32(vdupNU32(a), n), 0u)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlbS8)
 */
public fun vqshlbS8(a: Byte, b: Byte): Byte {
    val c: Int8x8T = vqshlS8(vdupNS8(a), vdupNS8(b))
    return vgetLaneS8(c, 0)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlhS16)
 */
public fun vqshlhS16(a: Short, b: Short): Short {
    val c: Int16x4T = vqshlS16(vdupNS16(a), vdupNS16(b))
    return vgetLaneS16(c, 0)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlsS32)
 */
public fun vqshlsS32(a: Int, b: Int): Int {
    val c: Int32x2T = vqshlS32(vdupNS32(a), vdupNS32(b))
    return vgetLaneS32(c, 0)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlbU8)
 */
public fun vqshlbU8(a: UByte, b: Byte): UByte {
    val c: Uint8x8T = vqshlU8(vdupNU8(a), vdupNS8(b))
    return vgetLaneU8(c, 0u)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlhU16)
 */
public fun vqshlhU16(a: UShort, b: Short): UShort {
    val c: Uint16x4T = vqshlU16(vdupNU16(a), vdupNS16(b))
    return vgetLaneU16(c, 0u)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlsU32)
 */
public fun vqshlsU32(a: UInt, b: Int): UInt {
    val c: Uint32x2T = vqshlU32(vdupNU32(a), vdupNS32(b))
    return vgetLaneU32(c, 0u)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshldS64)
 */
public fun vqshldS64(a: Long, b: Long): Long {
    fun externVqshldS64(a1: Long, b1: Long): Long = 0L
    return externVqshldS64(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshldU64)
 */
public fun vqshldU64(a: ULong, b: Long): ULong {
    fun externVqshldU64(a1: ULong, b1: Long): ULong = 0uL
    return externVqshldU64(a, b)
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlubNS8)
 */
public fun vqshlubNS8(a: Byte, n: Int): UByte {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vgetLaneU8(vqshluNS8(vdupNS8(a), n), 0u)
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshludNS64)
 */
public fun vqshludNS64(a: Long, n: Int): ULong {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vgetLaneU64(vqshluNS64(vdupNS64(a), n), 0uL)
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluhNS16)
 */
public fun vqshluhNS16(a: Short, n: Int): UShort {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vgetLaneU16(vqshluNS16(vdupNS16(a), n), 0u)
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlusNS32)
 */
public fun vqshlusNS32(a: Int, n: Int): UInt {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vgetLaneU32(vqshluNS32(vdupNS32(a), n), 0u)
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNS16)
 */
public fun vqshrnHighNS16(a: Int8x8T, b: Int16x8T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineS8(a, vqshrnNS16(b, n))
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNS32)
 */
public fun vqshrnHighNS32(a: Int16x4T, b: Int32x4T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineS16(a, vqshrnNS32(b, n))
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNS64)
 */
public fun vqshrnHighNS64(a: Int32x2T, b: Int64x2T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineS32(a, vqshrnNS64(b, n))
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNU16)
 */
public fun vqshrnHighNU16(a: Uint8x8T, b: Uint16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vqshrnNU16(b, n))
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNU32)
 */
public fun vqshrnHighNU32(a: Uint16x4T, b: Uint32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vqshrnNU32(b, n))
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnHighNU64)
 */
public fun vqshrnHighNU64(a: Uint32x2T, b: Uint64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vqshrnNU64(b, n))
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrndNS64)
 */
public fun vqshrndNS64(a: Long, n: Int): Int {
    fun externVqshrndNS64(a1: Long, n: Int): Int = 0
    run {
        check(n >= 1 && n <= 32)
    }
    return externVqshrndNS64(a, n)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrndNU64)
 */
public fun vqshrndNU64(a: ULong, n: Int): UInt {
    fun externVqshrndNU64(a1: ULong, n: Int): UInt = 0u
    run {
        check(n >= 1 && n <= 32)
    }
    return externVqshrndNU64(a, n)
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnhNS16)
 */
public fun vqshrnhNS16(a: Short, n: Int): Byte {
    run {
        check(n >= 1 && n <= 8)
    }
    return vgetLaneS8(vqshrnNS16(vdupqNS16(a), n), 0)
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnsNS32)
 */
public fun vqshrnsNS32(a: Int, n: Int): Short {
    run {
        check(n >= 1 && n <= 16)
    }
    return vgetLaneS16(vqshrnNS32(vdupqNS32(a), n), 0)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnhNU16)
 */
public fun vqshrnhNU16(a: UShort, n: Int): UByte {
    run {
        check(n >= 1 && n <= 8)
    }
    return vgetLaneU8(vqshrnNU16(vdupqNU16(a), n), 0u)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnsNU32)
 */
public fun vqshrnsNU32(a: UInt, n: Int): UShort {
    run {
        check(n >= 1 && n <= 16)
    }
    return vgetLaneU16(vqshrnNU32(vdupqNU32(a), n), 0u)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunHighNS16)
 */
public fun vqshrunHighNS16(a: Uint8x8T, b: Int16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vqshrunNS16(b, n))
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunHighNS32)
 */
public fun vqshrunHighNS32(a: Uint16x4T, b: Int32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vqshrunNS32(b, n))
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunHighNS64)
 */
public fun vqshrunHighNS64(a: Uint32x2T, b: Int64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vqshrunNS64(b, n))
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrundNS64)
 */
public fun vqshrundNS64(a: Long, n: Int): UInt {
    run {
        check(n >= 1 && n <= 32)
    }
    return vgetLaneU32(vqshrunNS64(vdupqNS64(a), n), 0u)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunhNS16)
 */
public fun vqshrunhNS16(a: Short, n: Int): UByte {
    run {
        check(n >= 1 && n <= 8)
    }
    return vgetLaneU8(vqshrunNS16(vdupqNS16(a), n), 0u)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunsNS32)
 */
public fun vqshrunsNS32(a: Int, n: Int): UShort {
    run {
        check(n >= 1 && n <= 16)
    }
    return vgetLaneU16(vqshrunNS32(vdupqNS32(a), n), 0u)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubbS8)
 */
public fun vqsubbS8(a: Byte, b: Byte): Byte {
    val a1: Int8x8T = vdupNS8(a)
    val b1: Int8x8T = vdupNS8(b)
    return vgetLaneS8(vqsubS8(a1, b1), 0)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubhS16)
 */
public fun vqsubhS16(a: Short, b: Short): Short {
    val a1: Int16x4T = vdupNS16(a)
    val b1: Int16x4T = vdupNS16(b)
    return vgetLaneS16(vqsubS16(a1, b1), 0)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubbU8)
 */
public fun vqsubbU8(a: UByte, b: UByte): UByte {
    val a1: Uint8x8T = vdupNU8(a)
    val b1: Uint8x8T = vdupNU8(b)
    return vgetLaneU8(vqsubU8(a1, b1), 0u)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubhU16)
 */
public fun vqsubhU16(a: UShort, b: UShort): UShort {
    val a1: Uint16x4T = vdupNU16(a)
    val b1: Uint16x4T = vdupNU16(b)
    return vgetLaneU16(vqsubU16(a1, b1), 0u)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubsS32)
 */
public fun vqsubsS32(a: Int, b: Int): Int {
    fun externVqsubsS32(a1: Int, b1: Int): Int = 0
    return externVqsubsS32(a, b)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubdS64)
 */
public fun vqsubdS64(a: Long, b: Long): Long {
    fun externVqsubdS64(a1: Long, b1: Long): Long = 0L
    return externVqsubdS64(a, b)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubsU32)
 */
public fun vqsubsU32(a: UInt, b: UInt): UInt {
    fun externVqsubsU32(a1: UInt, b1: UInt): UInt = 0u
    return externVqsubsU32(a, b)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubdU64)
 */
public fun vqsubdU64(a: ULong, b: ULong): ULong {
    fun externVqsubdU64(a1: ULong, b1: ULong): ULong = 0uL
    return externVqsubdU64(a, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1)
 */
public fun vqtbl1(a: Int8x16T, b: Uint8x8T): Int8x8T {
    fun externVqtbl1(a1: Int8x16T, b1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbl1(a, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1q)
 */
public fun vqtbl1q(a: Int8x16T, b: Uint8x16T): Int8x16T {
    fun externVqtbl1q(a1: Int8x16T, b1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbl1q(a, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1S8)
 */
public fun vqtbl1S8(a: Int8x16T, b: Uint8x8T): Int8x8T = vqtbl1(a, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1qS8)
 */
public fun vqtbl1qS8(a: Int8x16T, b: Uint8x16T): Int8x16T = vqtbl1q(a, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1U8)
 */
public fun vqtbl1U8(a: Uint8x16T, b: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbl1(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1qU8)
 */
public fun vqtbl1qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbl1q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1P8)
 */
public fun vqtbl1P8(a: Poly8x16T, b: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbl1(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl1qP8)
 */
public fun vqtbl1qP8(a: Poly8x16T, b: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbl1q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2)
 */
public fun vqtbl2(a: Int8x16T, b: Int8x16T, c: Uint8x8T): Int8x8T {
    fun externVqtbl2(a1: Int8x16T, b1: Int8x16T, c1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbl2(a, b, c)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2q)
 */
public fun vqtbl2q(a: Int8x16T, b: Int8x16T, c: Uint8x16T): Int8x16T {
    fun externVqtbl2q(a1: Int8x16T, b1: Int8x16T, c1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbl2q(a, b, c)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2S8)
 */
public fun vqtbl2S8(a: Int8x16x2T, b: Uint8x8T): Int8x8T = vqtbl2(a.field0, a.field1, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2qS8)
 */
public fun vqtbl2qS8(a: Int8x16x2T, b: Uint8x16T): Int8x16T = vqtbl2q(a.field0, a.field1, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2U8)
 */
public fun vqtbl2U8(a: Uint8x16x2T, b: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbl2(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2qU8)
 */
public fun vqtbl2qU8(a: Uint8x16x2T, b: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbl2q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2P8)
 */
public fun vqtbl2P8(a: Poly8x16x2T, b: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbl2(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl2qP8)
 */
public fun vqtbl2qP8(a: Poly8x16x2T, b: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbl2q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3)
 */
public fun vqtbl3(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Uint8x8T): Int8x8T {
    fun externVqtbl3(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbl3(a, b, c, d)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3q)
 */
public fun vqtbl3q(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Uint8x16T): Int8x16T {
    fun externVqtbl3q(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbl3q(a, b, c, d)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3S8)
 */
public fun vqtbl3S8(a: Int8x16x3T, b: Uint8x8T): Int8x8T = vqtbl3(a.field0, a.field1, a.field2, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3qS8)
 */
public fun vqtbl3qS8(a: Int8x16x3T, b: Uint8x16T): Int8x16T = vqtbl3q(a.field0, a.field1, a.field2, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3U8)
 */
public fun vqtbl3U8(a: Uint8x16x3T, b: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbl3(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3qU8)
 */
public fun vqtbl3qU8(a: Uint8x16x3T, b: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbl3q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3P8)
 */
public fun vqtbl3P8(a: Poly8x16x3T, b: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbl3(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl3qP8)
 */
public fun vqtbl3qP8(a: Poly8x16x3T, b: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbl3q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4)
 */
public fun vqtbl4(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Uint8x8T): Int8x8T {
    fun externVqtbl4(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbl4(a, b, c, d, e)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4q)
 */
public fun vqtbl4q(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Uint8x16T): Int8x16T {
    fun externVqtbl4q(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbl4q(a, b, c, d, e)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4S8)
 */
public fun vqtbl4S8(a: Int8x16x4T, b: Uint8x8T): Int8x8T = vqtbl4(a.field0, a.field1, a.field2, a.field3, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4qS8)
 */
public fun vqtbl4qS8(a: Int8x16x4T, b: Uint8x16T): Int8x16T = vqtbl4q(a.field0, a.field1, a.field2, a.field3, b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4U8)
 */
public fun vqtbl4U8(a: Uint8x16x4T, b: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbl4(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field3, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4qU8)
 */
public fun vqtbl4qU8(a: Uint8x16x4T, b: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbl4q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(a.field3, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4P8)
 */
public fun vqtbl4P8(a: Poly8x16x4T, b: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbl4(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field3, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbl4qP8)
 */
public fun vqtbl4qP8(a: Poly8x16x4T, b: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbl4q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(a.field3, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), b), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1)
 */
public fun vqtbx1(a: Int8x8T, b: Int8x16T, c: Uint8x8T): Int8x8T {
    fun externVqtbx1(a1: Int8x8T, b1: Int8x16T, c1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbx1(a, b, c)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1q)
 */
public fun vqtbx1q(a: Int8x16T, b: Int8x16T, c: Uint8x16T): Int8x16T {
    fun externVqtbx1q(a1: Int8x16T, b1: Int8x16T, c1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbx1q(a, b, c)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1S8)
 */
public fun vqtbx1S8(a: Int8x8T, b: Int8x16T, c: Uint8x8T): Int8x8T = vqtbx1(a, b, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1qS8)
 */
public fun vqtbx1qS8(a: Int8x16T, b: Int8x16T, c: Uint8x16T): Int8x16T = vqtbx1q(a, b, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1U8)
 */
public fun vqtbx1U8(a: Uint8x8T, b: Uint8x16T, c: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbx1(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1qU8)
 */
public fun vqtbx1qU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbx1q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1P8)
 */
public fun vqtbx1P8(a: Poly8x8T, b: Poly8x16T, c: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbx1(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx1qP8)
 */
public fun vqtbx1qP8(a: Poly8x16T, b: Poly8x16T, c: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbx1q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2)
 */
public fun vqtbx2(a: Int8x8T, b: Int8x16T, c: Int8x16T, d: Uint8x8T): Int8x8T {
    fun externVqtbx2(a1: Int8x8T, b1: Int8x16T, c1: Int8x16T, d1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbx2(a, b, c, d)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2q)
 */
public fun vqtbx2q(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Uint8x16T): Int8x16T {
    fun externVqtbx2q(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbx2q(a, b, c, d)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2S8)
 */
public fun vqtbx2S8(a: Int8x8T, b: Int8x16x2T, c: Uint8x8T): Int8x8T = vqtbx2(a, b.field0, b.field1, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2qS8)
 */
public fun vqtbx2qS8(a: Int8x16T, b: Int8x16x2T, c: Uint8x16T): Int8x16T = vqtbx2q(a, b.field0, b.field1, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2U8)
 */
public fun vqtbx2U8(a: Uint8x8T, b: Uint8x16x2T, c: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbx2(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2qU8)
 */
public fun vqtbx2qU8(a: Uint8x16T, b: Uint8x16x2T, c: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbx2q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2P8)
 */
public fun vqtbx2P8(a: Poly8x8T, b: Poly8x16x2T, c: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbx2(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx2qP8)
 */
public fun vqtbx2qP8(a: Poly8x16T, b: Poly8x16x2T, c: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbx2q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3)
 */
public fun vqtbx3(a: Int8x8T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Uint8x8T): Int8x8T {
    fun externVqtbx3(a1: Int8x8T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbx3(a, b, c, d, e)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3q)
 */
public fun vqtbx3q(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Uint8x16T): Int8x16T {
    fun externVqtbx3q(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbx3q(a, b, c, d, e)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3S8)
 */
public fun vqtbx3S8(a: Int8x8T, b: Int8x16x3T, c: Uint8x8T): Int8x8T = vqtbx3(a, b.field0, b.field1, b.field2, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3qS8)
 */
public fun vqtbx3qS8(a: Int8x16T, b: Int8x16x3T, c: Uint8x16T): Int8x16T = vqtbx3q(a, b.field0, b.field1, b.field2, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3U8)
 */
public fun vqtbx3U8(a: Uint8x8T, b: Uint8x16x3T, c: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbx3(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3qU8)
 */
public fun vqtbx3qU8(a: Uint8x16T, b: Uint8x16x3T, c: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbx3q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3P8)
 */
public fun vqtbx3P8(a: Poly8x8T, b: Poly8x16x3T, c: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbx3(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx3qP8)
 */
public fun vqtbx3qP8(a: Poly8x16T, b: Poly8x16x3T, c: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbx3q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4)
 */
public fun vqtbx4(a: Int8x8T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Int8x16T, f: Uint8x8T): Int8x8T {
    fun externVqtbx4(a1: Int8x8T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Int8x16T, f1: Uint8x8T): Int8x8T = Int8x8T()
    return externVqtbx4(a, b, c, d, e, f)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4q)
 */
public fun vqtbx4q(a: Int8x16T, b: Int8x16T, c: Int8x16T, d: Int8x16T, e: Int8x16T, f: Uint8x16T): Int8x16T {
    fun externVqtbx4q(a1: Int8x16T, b1: Int8x16T, c1: Int8x16T, d1: Int8x16T, e1: Int8x16T, f1: Uint8x16T): Int8x16T = Int8x16T()
    return externVqtbx4q(a, b, c, d, e, f)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4S8)
 */
public fun vqtbx4S8(a: Int8x8T, b: Int8x16x4T, c: Uint8x8T): Int8x8T = vqtbx4(a, b.field0, b.field1, b.field2, b.field3, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4qS8)
 */
public fun vqtbx4qS8(a: Int8x16T, b: Int8x16x4T, c: Uint8x16T): Int8x16T = vqtbx4q(a, b.field0, b.field1, b.field2, b.field3, c)

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4U8)
 */
public fun vqtbx4U8(a: Uint8x8T, b: Uint8x16x4T, c: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vqtbx4(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field3, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4qU8)
 */
public fun vqtbx4qU8(a: Uint8x16T, b: Uint8x16x4T, c: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vqtbx4q(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b.field3, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4P8)
 */
public fun vqtbx4P8(a: Poly8x8T, b: Poly8x16x4T, c: Uint8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vqtbx4(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field3, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqtbx4qP8)
 */
public fun vqtbx4qP8(a: Poly8x16T, b: Poly8x16x4T, c: Uint8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vqtbx4q(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field0, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field1, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field2, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b.field3, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), c), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Rotate and exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrax1qU64)
 */
public fun vrax1qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T {
    fun externVrax1qU64(a1: Uint64x2T, b1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVrax1qU64(a, b)
}

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitS8)
 */
public fun vrbitS8(a: Int8x8T): Int8x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitqS8)
 */
public fun vrbitqS8(a: Int8x16T): Int8x16T = run { val simdArg0 = a; simdArg0 }

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitU8)
 */
public fun vrbitU8(a: Uint8x8T): Uint8x8T = transmuteWithLayouts<Int8x8T, Uint8x8T>(vrbitS8(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>())), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitqU8)
 */
public fun vrbitqU8(a: Uint8x16T): Uint8x16T = transmuteWithLayouts<Int8x16T, Uint8x16T>(vrbitqS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>())), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitP8)
 */
public fun vrbitP8(a: Poly8x8T): Poly8x8T = transmuteWithLayouts<Int8x8T, Poly8x8T>(vrbitS8(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>())), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Reverse bit order
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrbitqP8)
 */
public fun vrbitqP8(a: Poly8x16T): Poly8x16T = transmuteWithLayouts<Int8x16T, Poly8x16T>(vrbitqS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>())), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeF64)
 */
public fun vrecpeF64(a: Float64x1T): Float64x1T {
    fun externVrecpeF64(a1: Float64x1T): Float64x1T = Float64x1T()
    return externVrecpeF64(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeqF64)
 */
public fun vrecpeqF64(a: Float64x2T): Float64x2T {
    fun externVrecpeqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrecpeqF64(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpedF64)
 */
public fun vrecpedF64(a: Double): Double {
    fun externVrecpedF64(a1: Double): Double = 0.0
    return externVrecpedF64(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpesF32)
 */
public fun vrecpesF32(a: Float): Float {
    fun externVrecpesF32(a1: Float): Float = 0.0f
    return externVrecpesF32(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpehF16)
 */
public fun vrecpehF16(a: F16): F16 {
    fun externVrecpehF16(a1: F16): F16 = F16()
    return externVrecpehF16(a)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsF64)
 */
public fun vrecpsF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVrecpsF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVrecpsF64(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsqF64)
 */
public fun vrecpsqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVrecpsqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVrecpsqF64(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsdF64)
 */
public fun vrecpsdF64(a: Double, b: Double): Double {
    fun externVrecpsdF64(a1: Double, b1: Double): Double = 0.0
    return externVrecpsdF64(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpssF32)
 */
public fun vrecpssF32(a: Float, b: Float): Float {
    fun externVrecpssF32(a1: Float, b1: Float): Float = 0.0f
    return externVrecpssF32(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpshF16)
 */
public fun vrecpshF16(a: F16, b: F16): F16 {
    fun externVrecpshF16(a1: F16, b1: F16): F16 = F16()
    return externVrecpshF16(a, b)
}

/**
 * Floating-point reciprocal exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpxdF64)
 */
public fun vrecpxdF64(a: Double): Double {
    fun externVrecpxdF64(a1: Double): Double = 0.0
    return externVrecpxdF64(a)
}

/**
 * Floating-point reciprocal exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpxsF32)
 */
public fun vrecpxsF32(a: Float): Float {
    fun externVrecpxsF32(a1: Float): Float = 0.0f
    return externVrecpxsF32(a)
}

/**
 * Floating-point reciprocal exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpxhF16)
 */
public fun vrecpxhF16(a: F16): F16 {
    fun externVrecpxhF16(a1: F16): F16 = F16()
    return externVrecpxhF16(a)
}

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64F16)
 */
public fun vreinterpretF64F16(a: Float16x4T): Float64x1T = transmuteWithLayouts<Float16x4T, Float64x1T>(a, UnresolvedTypeLayout<Float16x4T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64F16)
 */
public fun vreinterpretqF64F16(a: Float16x8T): Float64x2T = transmuteWithLayouts<Float16x8T, Float64x2T>(a, UnresolvedTypeLayout<Float16x8T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16F64)
 */
public fun vreinterpretF16F64(a: Float64x1T): Float16x4T = transmuteWithLayouts<Float64x1T, Float16x4T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Float16x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16F64)
 */
public fun vreinterpretqF16F64(a: Float64x2T): Float16x8T = transmuteWithLayouts<Float64x2T, Float16x8T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Float16x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64F64)
 */
public fun vreinterpretS64F64(a: Float64x1T): Int64x1T = transmuteWithLayouts<Float64x1T, Int64x1T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Int64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64F64)
 */
public fun vreinterpretU64F64(a: Float64x1T): Uint64x1T = transmuteWithLayouts<Float64x1T, Uint64x1T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Uint64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64F64)
 */
public fun vreinterpretP64F64(a: Float64x1T): Poly64x1T = transmuteWithLayouts<Float64x1T, Poly64x1T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Poly64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64F64)
 */
public fun vreinterpretqS64F64(a: Float64x2T): Int64x2T = transmuteWithLayouts<Float64x2T, Int64x2T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64F64)
 */
public fun vreinterpretqU64F64(a: Float64x2T): Uint64x2T = transmuteWithLayouts<Float64x2T, Uint64x2T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Uint64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64F64)
 */
public fun vreinterpretqP64F64(a: Float64x2T): Poly64x2T = transmuteWithLayouts<Float64x2T, Poly64x2T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Poly64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64S64)
 */
public fun vreinterpretF64S64(a: Int64x1T): Float64x1T = transmuteWithLayouts<Int64x1T, Float64x1T>(a, UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64S64)
 */
public fun vreinterpretP64S64(a: Int64x1T): Poly64x1T = transmuteWithLayouts<Int64x1T, Poly64x1T>(a, UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Poly64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64S64)
 */
public fun vreinterpretqF64S64(a: Int64x2T): Float64x2T = transmuteWithLayouts<Int64x2T, Float64x2T>(a, UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64S64)
 */
public fun vreinterpretqP64S64(a: Int64x2T): Poly64x2T = transmuteWithLayouts<Int64x2T, Poly64x2T>(a, UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Poly64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64U64)
 */
public fun vreinterpretF64U64(a: Uint64x1T): Float64x1T = transmuteWithLayouts<Uint64x1T, Float64x1T>(a, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64U64)
 */
public fun vreinterpretP64U64(a: Uint64x1T): Poly64x1T = transmuteWithLayouts<Uint64x1T, Poly64x1T>(a, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Poly64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64U64)
 */
public fun vreinterpretqF64U64(a: Uint64x2T): Float64x2T = transmuteWithLayouts<Uint64x2T, Float64x2T>(a, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64U64)
 */
public fun vreinterpretqP64U64(a: Uint64x2T): Poly64x2T = transmuteWithLayouts<Uint64x2T, Poly64x2T>(a, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Poly64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64P64)
 */
public fun vreinterpretF64P64(a: Poly64x1T): Float64x1T = transmuteWithLayouts<Poly64x1T, Float64x1T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64P64)
 */
public fun vreinterpretS64P64(a: Poly64x1T): Int64x1T = transmuteWithLayouts<Poly64x1T, Int64x1T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64P64)
 */
public fun vreinterpretU64P64(a: Poly64x1T): Uint64x1T = transmuteWithLayouts<Poly64x1T, Uint64x1T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Uint64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64P64)
 */
public fun vreinterpretqF64P64(a: Poly64x2T): Float64x2T = transmuteWithLayouts<Poly64x2T, Float64x2T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64P64)
 */
public fun vreinterpretqS64P64(a: Poly64x2T): Int64x2T = transmuteWithLayouts<Poly64x2T, Int64x2T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64P64)
 */
public fun vreinterpretqU64P64(a: Poly64x2T): Uint64x2T = transmuteWithLayouts<Poly64x2T, Uint64x2T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Uint64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64P128)
 */
public fun vreinterpretqF64P128(a: P128): Float64x2T = transmuteWithLayouts<P128, Float64x2T>(a, UnresolvedTypeLayout<P128>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64F32)
 */
public fun vreinterpretF64F32(a: Float32x2T): Float64x1T = transmuteWithLayouts<Float32x2T, Float64x1T>(a, UnresolvedTypeLayout<Float32x2T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64F32)
 */
public fun vreinterpretP64F32(a: Float32x2T): Poly64x1T = transmuteWithLayouts<Float32x2T, Poly64x1T>(a, UnresolvedTypeLayout<Float32x2T>(), UnresolvedTypeLayout<Poly64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64F32)
 */
public fun vreinterpretqF64F32(a: Float32x4T): Float64x2T = transmuteWithLayouts<Float32x4T, Float64x2T>(a, UnresolvedTypeLayout<Float32x4T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64F32)
 */
public fun vreinterpretqP64F32(a: Float32x4T): Poly64x2T = transmuteWithLayouts<Float32x4T, Poly64x2T>(a, UnresolvedTypeLayout<Float32x4T>(), UnresolvedTypeLayout<Poly64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32F64)
 */
public fun vreinterpretF32F64(a: Float64x1T): Float32x2T = transmuteWithLayouts<Float64x1T, Float32x2T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Float32x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8F64)
 */
public fun vreinterpretS8F64(a: Float64x1T): Int8x8T = transmuteWithLayouts<Float64x1T, Int8x8T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Int8x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16F64)
 */
public fun vreinterpretS16F64(a: Float64x1T): Int16x4T = transmuteWithLayouts<Float64x1T, Int16x4T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Int16x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32F64)
 */
public fun vreinterpretS32F64(a: Float64x1T): Int32x2T = transmuteWithLayouts<Float64x1T, Int32x2T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Int32x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8F64)
 */
public fun vreinterpretU8F64(a: Float64x1T): Uint8x8T = transmuteWithLayouts<Float64x1T, Uint8x8T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Uint8x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16F64)
 */
public fun vreinterpretU16F64(a: Float64x1T): Uint16x4T = transmuteWithLayouts<Float64x1T, Uint16x4T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Uint16x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32F64)
 */
public fun vreinterpretU32F64(a: Float64x1T): Uint32x2T = transmuteWithLayouts<Float64x1T, Uint32x2T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Uint32x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8F64)
 */
public fun vreinterpretP8F64(a: Float64x1T): Poly8x8T = transmuteWithLayouts<Float64x1T, Poly8x8T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Poly8x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16F64)
 */
public fun vreinterpretP16F64(a: Float64x1T): Poly16x4T = transmuteWithLayouts<Float64x1T, Poly16x4T>(a, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Poly16x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128F64)
 */
public fun vreinterpretqP128F64(a: Float64x2T): P128 = transmuteWithLayouts<Float64x2T, P128>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<P128>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32F64)
 */
public fun vreinterpretqF32F64(a: Float64x2T): Float32x4T = transmuteWithLayouts<Float64x2T, Float32x4T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Float32x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8F64)
 */
public fun vreinterpretqS8F64(a: Float64x2T): Int8x16T = transmuteWithLayouts<Float64x2T, Int8x16T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int8x16T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16F64)
 */
public fun vreinterpretqS16F64(a: Float64x2T): Int16x8T = transmuteWithLayouts<Float64x2T, Int16x8T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int16x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32F64)
 */
public fun vreinterpretqS32F64(a: Float64x2T): Int32x4T = transmuteWithLayouts<Float64x2T, Int32x4T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int32x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8F64)
 */
public fun vreinterpretqU8F64(a: Float64x2T): Uint8x16T = transmuteWithLayouts<Float64x2T, Uint8x16T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Uint8x16T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16F64)
 */
public fun vreinterpretqU16F64(a: Float64x2T): Uint16x8T = transmuteWithLayouts<Float64x2T, Uint16x8T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Uint16x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32F64)
 */
public fun vreinterpretqU32F64(a: Float64x2T): Uint32x4T = transmuteWithLayouts<Float64x2T, Uint32x4T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Uint32x4T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8F64)
 */
public fun vreinterpretqP8F64(a: Float64x2T): Poly8x16T = transmuteWithLayouts<Float64x2T, Poly8x16T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Poly8x16T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16F64)
 */
public fun vreinterpretqP16F64(a: Float64x2T): Poly16x8T = transmuteWithLayouts<Float64x2T, Poly16x8T>(a, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Poly16x8T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64S8)
 */
public fun vreinterpretF64S8(a: Int8x8T): Float64x1T = transmuteWithLayouts<Int8x8T, Float64x1T>(a, UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64S8)
 */
public fun vreinterpretqF64S8(a: Int8x16T): Float64x2T = transmuteWithLayouts<Int8x16T, Float64x2T>(a, UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64S16)
 */
public fun vreinterpretF64S16(a: Int16x4T): Float64x1T = transmuteWithLayouts<Int16x4T, Float64x1T>(a, UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64S16)
 */
public fun vreinterpretqF64S16(a: Int16x8T): Float64x2T = transmuteWithLayouts<Int16x8T, Float64x2T>(a, UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64S32)
 */
public fun vreinterpretF64S32(a: Int32x2T): Float64x1T = transmuteWithLayouts<Int32x2T, Float64x1T>(a, UnresolvedTypeLayout<Int32x2T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64S32)
 */
public fun vreinterpretqF64S32(a: Int32x4T): Float64x2T = transmuteWithLayouts<Int32x4T, Float64x2T>(a, UnresolvedTypeLayout<Int32x4T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64U8)
 */
public fun vreinterpretF64U8(a: Uint8x8T): Float64x1T = transmuteWithLayouts<Uint8x8T, Float64x1T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64U8)
 */
public fun vreinterpretqF64U8(a: Uint8x16T): Float64x2T = transmuteWithLayouts<Uint8x16T, Float64x2T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64U16)
 */
public fun vreinterpretF64U16(a: Uint16x4T): Float64x1T = transmuteWithLayouts<Uint16x4T, Float64x1T>(a, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64U16)
 */
public fun vreinterpretqF64U16(a: Uint16x8T): Float64x2T = transmuteWithLayouts<Uint16x8T, Float64x2T>(a, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64U32)
 */
public fun vreinterpretF64U32(a: Uint32x2T): Float64x1T = transmuteWithLayouts<Uint32x2T, Float64x1T>(a, UnresolvedTypeLayout<Uint32x2T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64U32)
 */
public fun vreinterpretqF64U32(a: Uint32x4T): Float64x2T = transmuteWithLayouts<Uint32x4T, Float64x2T>(a, UnresolvedTypeLayout<Uint32x4T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64P8)
 */
public fun vreinterpretF64P8(a: Poly8x8T): Float64x1T = transmuteWithLayouts<Poly8x8T, Float64x1T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64P8)
 */
public fun vreinterpretqF64P8(a: Poly8x16T): Float64x2T = transmuteWithLayouts<Poly8x16T, Float64x2T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF64P16)
 */
public fun vreinterpretF64P16(a: Poly16x4T): Float64x1T = transmuteWithLayouts<Poly16x4T, Float64x1T>(a, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Float64x1T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF64P16)
 */
public fun vreinterpretqF64P16(a: Poly16x8T): Float64x2T = transmuteWithLayouts<Poly16x8T, Float64x2T>(a, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Float64x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32P64)
 */
public fun vreinterpretF32P64(a: Poly64x1T): Float32x2T = transmuteWithLayouts<Poly64x1T, Float32x2T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Float32x2T>())

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32P64)
 */
public fun vreinterpretqF32P64(a: Poly64x2T): Float32x4T = transmuteWithLayouts<Poly64x2T, Float32x4T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Float32x4T>())

/**
 * Floating-point round to 32-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32xF32)
 */
public fun vrnd32xF32(a: Float32x2T): Float32x2T {
    fun externVrnd32xF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return externVrnd32xF32(a)
}

/**
 * Floating-point round to 32-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32xqF32)
 */
public fun vrnd32xqF32(a: Float32x4T): Float32x4T {
    fun externVrnd32xqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return externVrnd32xqF32(a)
}

/**
 * Floating-point round to 32-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32xqF64)
 */
public fun vrnd32xqF64(a: Float64x2T): Float64x2T {
    fun externVrnd32xqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrnd32xqF64(a)
}

/**
 * Floating-point round to 32-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32xF64)
 */
public fun vrnd32xF64(a: Float64x1T): Float64x1T {
    fun externVrnd32xF64(a1: Double): Double = 0.0
    return transmuteWithLayouts<Double, Float64x1T>(externVrnd32xF64(vgetLaneF64(a, 0)), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Floating-point round to 32-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32zF32)
 */
public fun vrnd32zF32(a: Float32x2T): Float32x2T {
    fun externVrnd32zF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return externVrnd32zF32(a)
}

/**
 * Floating-point round to 32-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32zqF32)
 */
public fun vrnd32zqF32(a: Float32x4T): Float32x4T {
    fun externVrnd32zqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return externVrnd32zqF32(a)
}

/**
 * Floating-point round to 32-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32zqF64)
 */
public fun vrnd32zqF64(a: Float64x2T): Float64x2T {
    fun externVrnd32zqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrnd32zqF64(a)
}

/**
 * Floating-point round to 32-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd32zF64)
 */
public fun vrnd32zF64(a: Float64x1T): Float64x1T {
    fun externVrnd32zF64(a1: Double): Double = 0.0
    return transmuteWithLayouts<Double, Float64x1T>(externVrnd32zF64(vgetLaneF64(a, 0)), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Floating-point round to 64-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64xF32)
 */
public fun vrnd64xF32(a: Float32x2T): Float32x2T {
    fun externVrnd64xF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return externVrnd64xF32(a)
}

/**
 * Floating-point round to 64-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64xqF32)
 */
public fun vrnd64xqF32(a: Float32x4T): Float32x4T {
    fun externVrnd64xqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return externVrnd64xqF32(a)
}

/**
 * Floating-point round to 64-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64xqF64)
 */
public fun vrnd64xqF64(a: Float64x2T): Float64x2T {
    fun externVrnd64xqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrnd64xqF64(a)
}

/**
 * Floating-point round to 64-bit integer, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64xF64)
 */
public fun vrnd64xF64(a: Float64x1T): Float64x1T {
    fun externVrnd64xF64(a1: Double): Double = 0.0
    return transmuteWithLayouts<Double, Float64x1T>(externVrnd64xF64(vgetLaneF64(a, 0)), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Floating-point round to 64-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64zF32)
 */
public fun vrnd64zF32(a: Float32x2T): Float32x2T {
    fun externVrnd64zF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return externVrnd64zF32(a)
}

/**
 * Floating-point round to 64-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64zqF32)
 */
public fun vrnd64zqF32(a: Float32x4T): Float32x4T {
    fun externVrnd64zqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return externVrnd64zqF32(a)
}

/**
 * Floating-point round to 64-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64zqF64)
 */
public fun vrnd64zqF64(a: Float64x2T): Float64x2T {
    fun externVrnd64zqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrnd64zqF64(a)
}

/**
 * Floating-point round to 64-bit integer toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrnd64zF64)
 */
public fun vrnd64zF64(a: Float64x1T): Float64x1T {
    fun externVrnd64zF64(a1: Double): Double = 0.0
    return transmuteWithLayouts<Double, Float64x1T>(externVrnd64zF64(vgetLaneF64(a, 0)), UnresolvedTypeLayout<Double>(), UnresolvedTypeLayout<Float64x1T>())
}

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndF16)
 */
public fun vrndF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndqF16)
 */
public fun vrndqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndF32)
 */
public fun vrndF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndqF32)
 */
public fun vrndqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndF64)
 */
public fun vrndF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndqF64)
 */
public fun vrndqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaF16)
 */
public fun vrndaF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaqF16)
 */
public fun vrndaqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaF32)
 */
public fun vrndaF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaqF32)
 */
public fun vrndaqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaF64)
 */
public fun vrndaF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndaqF64)
 */
public fun vrndaqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndahF16)
 */
public fun vrndahF16(a: F16): F16 = round(a)

/**
 * Floating-point round to integral, to nearest with ties to away
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndhF16)
 */
public fun vrndhF16(a: F16): F16 = trunc(a)

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiF16)
 */
public fun vrndiF16(a: Float16x4T): Float16x4T {
    fun externVrndiF16(a1: Float16x4T): Float16x4T = Float16x4T()
    return externVrndiF16(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiqF16)
 */
public fun vrndiqF16(a: Float16x8T): Float16x8T {
    fun externVrndiqF16(a1: Float16x8T): Float16x8T = Float16x8T()
    return externVrndiqF16(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiF32)
 */
public fun vrndiF32(a: Float32x2T): Float32x2T {
    fun externVrndiF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return externVrndiF32(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiqF32)
 */
public fun vrndiqF32(a: Float32x4T): Float32x4T {
    fun externVrndiqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return externVrndiqF32(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiF64)
 */
public fun vrndiF64(a: Float64x1T): Float64x1T {
    fun externVrndiF64(a1: Float64x1T): Float64x1T = Float64x1T()
    return externVrndiF64(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndiqF64)
 */
public fun vrndiqF64(a: Float64x2T): Float64x2T {
    fun externVrndiqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrndiqF64(a)
}

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndihF16)
 */
public fun vrndihF16(a: F16): F16 {
    fun externVrndihF16(a1: F16): F16 = F16()
    return externVrndihF16(a)
}

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmF16)
 */
public fun vrndmF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmqF16)
 */
public fun vrndmqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmF32)
 */
public fun vrndmF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmqF32)
 */
public fun vrndmqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmF64)
 */
public fun vrndmF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmqF64)
 */
public fun vrndmqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndmhF16)
 */
public fun vrndmhF16(a: F16): F16 = floor(a)

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnF64)
 */
public fun vrndnF64(a: Float64x1T): Float64x1T {
    fun externVrndnF64(a1: Float64x1T): Float64x1T = Float64x1T()
    return externVrndnF64(a)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnqF64)
 */
public fun vrndnqF64(a: Float64x2T): Float64x2T {
    fun externVrndnqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrndnqF64(a)
}

/**
 * Floating-point round to integral, toward minus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnhF16)
 */
public fun vrndnhF16(a: F16): F16 {
    fun externVrndnhF16(a1: F16): F16 = F16()
    return externVrndnhF16(a)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnsF32)
 */
public fun vrndnsF32(a: Float): Float {
    fun externVrndnsF32(a1: Float): Float = 0.0f
    return externVrndnsF32(a)
}

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpF16)
 */
public fun vrndpF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpqF16)
 */
public fun vrndpqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpF32)
 */
public fun vrndpF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpqF32)
 */
public fun vrndpqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpF64)
 */
public fun vrndpF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndpqF64)
 */
public fun vrndpqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, toward plus infinity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndphF16)
 */
public fun vrndphF16(a: F16): F16 = ceil(a)

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxF16)
 */
public fun vrndxF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxqF16)
 */
public fun vrndxqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxF32)
 */
public fun vrndxF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxqF32)
 */
public fun vrndxqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxF64)
 */
public fun vrndxF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral exact, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxqF64)
 */
public fun vrndxqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndxhF16)
 */
public fun vrndxhF16(a: F16): F16 = roundTiesEven(a)

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshldS64)
 */
public fun vrshldS64(a: Long, b: Long): Long {
    fun externVrshldS64(a1: Long, b1: Long): Long = 0L
    return externVrshldS64(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshldU64)
 */
public fun vrshldU64(a: ULong, b: Long): ULong {
    fun externVrshldU64(a1: ULong, b1: Long): ULong = 0uL
    return externVrshldU64(a, b)
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrdNS64)
 */
public fun vrshrdNS64(a: Long, n: Int): Long {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshldS64(a, (-n).toLong())
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrdNU64)
 */
public fun vrshrdNU64(a: ULong, n: Int): ULong {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshldU64(a, (-n).toLong())
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNS16)
 */
public fun vrshrnHighNS16(a: Int8x8T, b: Int16x8T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineS8(a, vrshrnNS16(b, n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNS32)
 */
public fun vrshrnHighNS32(a: Int16x4T, b: Int32x4T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineS16(a, vrshrnNS32(b, n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNS64)
 */
public fun vrshrnHighNS64(a: Int32x2T, b: Int64x2T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineS32(a, vrshrnNS64(b, n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNU16)
 */
public fun vrshrnHighNU16(a: Uint8x8T, b: Uint16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vrshrnNU16(b, n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNU32)
 */
public fun vrshrnHighNU32(a: Uint16x4T, b: Uint32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vrshrnNU32(b, n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnHighNU64)
 */
public fun vrshrnHighNU64(a: Uint32x2T, b: Uint64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vrshrnNU64(b, n))
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteF64)
 */
public fun vrsqrteF64(a: Float64x1T): Float64x1T {
    fun externVrsqrteF64(a1: Float64x1T): Float64x1T = Float64x1T()
    return externVrsqrteF64(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteqF64)
 */
public fun vrsqrteqF64(a: Float64x2T): Float64x2T {
    fun externVrsqrteqF64(a1: Float64x2T): Float64x2T = Float64x2T()
    return externVrsqrteqF64(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtedF64)
 */
public fun vrsqrtedF64(a: Double): Double {
    fun externVrsqrtedF64(a1: Double): Double = 0.0
    return externVrsqrtedF64(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtesF32)
 */
public fun vrsqrtesF32(a: Float): Float {
    fun externVrsqrtesF32(a1: Float): Float = 0.0f
    return externVrsqrtesF32(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtehF16)
 */
public fun vrsqrtehF16(a: F16): F16 {
    fun externVrsqrtehF16(a1: F16): F16 = F16()
    return externVrsqrtehF16(a)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsF64)
 */
public fun vrsqrtsF64(a: Float64x1T, b: Float64x1T): Float64x1T {
    fun externVrsqrtsF64(a1: Float64x1T, b1: Float64x1T): Float64x1T = Float64x1T()
    return externVrsqrtsF64(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsqF64)
 */
public fun vrsqrtsqF64(a: Float64x2T, b: Float64x2T): Float64x2T {
    fun externVrsqrtsqF64(a1: Float64x2T, b1: Float64x2T): Float64x2T = Float64x2T()
    return externVrsqrtsqF64(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsdF64)
 */
public fun vrsqrtsdF64(a: Double, b: Double): Double {
    fun externVrsqrtsdF64(a1: Double, b1: Double): Double = 0.0
    return externVrsqrtsdF64(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtssF32)
 */
public fun vrsqrtssF32(a: Float, b: Float): Float {
    fun externVrsqrtssF32(a1: Float, b1: Float): Float = 0.0f
    return externVrsqrtssF32(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtshF16)
 */
public fun vrsqrtshF16(a: F16, b: F16): F16 {
    fun externVrsqrtshF16(a1: F16, b1: F16): F16 = F16()
    return externVrsqrtshF16(a, b)
}

/**
 * Signed rounding shift right and accumulate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsradNS64)
 */
public fun vrsradNS64(a: Long, b: Long, n: Int): Long {
    run {
        check(n >= 1 && n <= 64)
    }
    val b1: Long = vrshrdNS64(b, n)
    return (a + (b1.toLong()))
}

/**
 * Unsigned rounding shift right and accumulate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsradNU64)
 */
public fun vrsradNU64(a: ULong, b: ULong, n: Int): ULong {
    run {
        check(n >= 1 && n <= 64)
    }
    val b1: ULong = vrshrdNU64(b, n)
    return (a + (b1.toULong()))
}

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighS16)
 */
public fun vrsubhnHighS16(a: Int8x8T, b: Int16x8T, c: Int16x8T): Int8x16T = vcombineS8(a, vrsubhnS16(b, c))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighS32)
 */
public fun vrsubhnHighS32(a: Int16x4T, b: Int32x4T, c: Int32x4T): Int16x8T = vcombineS16(a, vrsubhnS32(b, c))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighS64)
 */
public fun vrsubhnHighS64(a: Int32x2T, b: Int64x2T, c: Int64x2T): Int32x4T = vcombineS32(a, vrsubhnS64(b, c))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighU16)
 */
public fun vrsubhnHighU16(a: Uint8x8T, b: Uint16x8T, c: Uint16x8T): Uint8x16T = vcombineU8(a, vrsubhnU16(b, c))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighU32)
 */
public fun vrsubhnHighU32(a: Uint16x4T, b: Uint32x4T, c: Uint32x4T): Uint16x8T = vcombineU16(a, vrsubhnU32(b, c))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnHighU64)
 */
public fun vrsubhnHighU64(a: Uint32x2T, b: Uint64x2T, c: Uint64x2T): Uint32x4T = vcombineU32(a, vrsubhnU64(b, c))

/**
 * Multi-vector floating-point adjust exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vscaleF16)
 */
public fun vscaleF16(vn: Float16x4T, vm: Int16x4T): Float16x4T {
    fun externVscaleF16(vn1: Float16x4T, vm1: Int16x4T): Float16x4T = Float16x4T()
    return externVscaleF16(vn, vm)
}

/**
 * Multi-vector floating-point adjust exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vscaleqF16)
 */
public fun vscaleqF16(vn: Float16x8T, vm: Int16x8T): Float16x8T {
    fun externVscaleqF16(vn1: Float16x8T, vm1: Int16x8T): Float16x8T = Float16x8T()
    return externVscaleqF16(vn, vm)
}

/**
 * Multi-vector floating-point adjust exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vscaleF32)
 */
public fun vscaleF32(vn: Float32x2T, vm: Int32x2T): Float32x2T {
    fun externVscaleF32(vn1: Float32x2T, vm1: Int32x2T): Float32x2T = Float32x2T()
    return externVscaleF32(vn, vm)
}

/**
 * Multi-vector floating-point adjust exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vscaleqF32)
 */
public fun vscaleqF32(vn: Float32x4T, vm: Int32x4T): Float32x4T {
    fun externVscaleqF32(vn1: Float32x4T, vm1: Int32x4T): Float32x4T = Float32x4T()
    return externVscaleqF32(vn, vm)
}

/**
 * Multi-vector floating-point adjust exponent
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vscaleqF64)
 */
public fun vscaleqF64(vn: Float64x2T, vm: Int64x2T): Float64x2T {
    fun externVscaleqF64(vn1: Float64x2T, vm1: Int64x2T): Float64x2T = Float64x2T()
    return externVscaleqF64(vn, vm)
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneF64)
 */
public fun vsetLaneF64(a: Double, b: Float64x1T, lane: Int): Float64x1T {
    run {
        check(lane == 0)
    }
    return run { val simdArg0 = b; val simdArg1 = lane.toUInt(); val simdArg2 = a; simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneF64)
 */
public fun vsetqLaneF64(a: Double, b: Float64x2T, lane: Int): Float64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val simdArg0 = b; val simdArg1 = lane.toUInt(); val simdArg2 = a; simdArg0 }
}

/**
 * SHA512 hash update part 2
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha512h2qU64)
 */
public fun vsha512h2qU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    fun externVsha512h2qU64(a1: Uint64x2T, b1: Uint64x2T, c1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVsha512h2qU64(a, b, c)
}

/**
 * SHA512 hash update part 1
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha512hqU64)
 */
public fun vsha512hqU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    fun externVsha512hqU64(a1: Uint64x2T, b1: Uint64x2T, c1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVsha512hqU64(a, b, c)
}

/**
 * SHA512 schedule update 0
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha512su0qU64)
 */
public fun vsha512su0qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T {
    fun externVsha512su0qU64(a1: Uint64x2T, b1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVsha512su0qU64(a, b)
}

/**
 * SHA512 schedule update 1
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha512su1qU64)
 */
public fun vsha512su1qU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    fun externVsha512su1qU64(a1: Uint64x2T, b1: Uint64x2T, c1: Uint64x2T): Uint64x2T = Uint64x2T()
    return externVsha512su1qU64(a, b, c)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshldS64)
 */
public fun vshldS64(a: Long, b: Long): Long = transmuteWithLayouts<Int64x1T, Long>(vshlS64(transmute(a), transmute(b)), UnresolvedTypeLayout<Int64x1T>(), PrimitiveLayouts.I64)

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshldU64)
 */
public fun vshldU64(a: ULong, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vshlU64(transmute(a), transmute(b)), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNS8)
 */
public fun vshllHighNS8(a: Int8x16T, n: Int): Int16x8T {
    run {
        check(n >= 0 && n <= 8)
    }
    val b = vgetHighS8(a)
    return vshllNS8(b, n)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNS16)
 */
public fun vshllHighNS16(a: Int16x8T, n: Int): Int32x4T {
    run {
        check(n >= 0 && n <= 16)
    }
    val b = vgetHighS16(a)
    return vshllNS16(b, n)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNS32)
 */
public fun vshllHighNS32(a: Int32x4T, n: Int): Int64x2T {
    run {
        check(n >= 0 && n <= 32)
    }
    val b = vgetHighS32(a)
    return vshllNS32(b, n)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNU8)
 */
public fun vshllHighNU8(a: Uint8x16T, n: Int): Uint16x8T {
    run {
        check(n >= 0 && n <= 8)
    }
    val b: Uint8x8T = vgetHighU8(a)
    return vshllNU8(b, n)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNU16)
 */
public fun vshllHighNU16(a: Uint16x8T, n: Int): Uint32x4T {
    run {
        check(n >= 0 && n <= 16)
    }
    val b: Uint16x4T = vgetHighU16(a)
    return vshllNU16(b, n)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllHighNU32)
 */
public fun vshllHighNU32(a: Uint32x4T, n: Int): Uint64x2T {
    run {
        check(n >= 0 && n <= 32)
    }
    val b: Uint32x2T = vgetHighU32(a)
    return vshllNU32(b, n)
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNS16)
 */
public fun vshrnHighNS16(a: Int8x8T, b: Int16x8T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineS8(a, vshrnNS16(b, n))
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNS32)
 */
public fun vshrnHighNS32(a: Int16x4T, b: Int32x4T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineS16(a, vshrnNS32(b, n))
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNS64)
 */
public fun vshrnHighNS64(a: Int32x2T, b: Int64x2T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineS32(a, vshrnNS64(b, n))
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNU16)
 */
public fun vshrnHighNU16(a: Uint8x8T, b: Uint16x8T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vcombineU8(a, vshrnNU16(b, n))
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNU32)
 */
public fun vshrnHighNU32(a: Uint16x4T, b: Uint32x4T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vcombineU16(a, vshrnNU32(b, n))
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnHighNU64)
 */
public fun vshrnHighNU64(a: Uint32x2T, b: Uint64x2T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vcombineU32(a, vshrnNU64(b, n))
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNS8)
 */
public fun vsliNS8(a: Int8x8T, b: Int8x8T, n: Int): Int8x8T {
    fun externVsliNS8(a1: Int8x8T, b1: Int8x8T, n: Int): Int8x8T = Int8x8T()
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return externVsliNS8(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNS8)
 */
public fun vsliqNS8(a: Int8x16T, b: Int8x16T, n: Int): Int8x16T {
    fun externVsliqNS8(a1: Int8x16T, b1: Int8x16T, n: Int): Int8x16T = Int8x16T()
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return externVsliqNS8(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNS16)
 */
public fun vsliNS16(a: Int16x4T, b: Int16x4T, n: Int): Int16x4T {
    fun externVsliNS16(a1: Int16x4T, b1: Int16x4T, n: Int): Int16x4T = Int16x4T()
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return externVsliNS16(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNS16)
 */
public fun vsliqNS16(a: Int16x8T, b: Int16x8T, n: Int): Int16x8T {
    fun externVsliqNS16(a1: Int16x8T, b1: Int16x8T, n: Int): Int16x8T = Int16x8T()
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return externVsliqNS16(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNS32)
 */
public fun vsliNS32(a: Int32x2T, b: Int32x2T, n: Int): Int32x2T {
    fun externVsliNS32(a1: Int32x2T, b1: Int32x2T, n: Int): Int32x2T = Int32x2T()
    run {
        check(n >= 0 && n <= 31)
    }
    return externVsliNS32(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNS32)
 */
public fun vsliqNS32(a: Int32x4T, b: Int32x4T, n: Int): Int32x4T {
    fun externVsliqNS32(a1: Int32x4T, b1: Int32x4T, n: Int): Int32x4T = Int32x4T()
    run {
        check(n >= 0 && n <= 31)
    }
    return externVsliqNS32(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNS64)
 */
public fun vsliNS64(a: Int64x1T, b: Int64x1T, n: Int): Int64x1T {
    fun externVsliNS64(a1: Int64x1T, b1: Int64x1T, n: Int): Int64x1T = Int64x1T()
    run {
        check(n >= 0 && n <= 63)
    }
    return externVsliNS64(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNS64)
 */
public fun vsliqNS64(a: Int64x2T, b: Int64x2T, n: Int): Int64x2T {
    fun externVsliqNS64(a1: Int64x2T, b1: Int64x2T, n: Int): Int64x2T = Int64x2T()
    run {
        check(n >= 0 && n <= 63)
    }
    return externVsliqNS64(a, b, n)
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNU8)
 */
public fun vsliNU8(a: Uint8x8T, b: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x8T, Uint8x8T>(vsliNS8(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x8T, Int8x8T>(b, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), n), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNU8)
 */
public fun vsliqNU8(a: Uint8x16T, b: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vsliqNS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), n), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNU16)
 */
public fun vsliNU16(a: Uint16x4T, b: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int16x4T, Uint16x4T>(vsliNS16(transmuteWithLayouts<Uint16x4T, Int16x4T>(a, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), transmuteWithLayouts<Uint16x4T, Int16x4T>(b, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), n), UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<Uint16x4T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNU16)
 */
public fun vsliqNU16(a: Uint16x8T, b: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vsliqNS16(transmuteWithLayouts<Uint16x8T, Int16x8T>(a, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), transmuteWithLayouts<Uint16x8T, Int16x8T>(b, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), n), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNU32)
 */
public fun vsliNU32(a: Uint32x2T, b: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 0 && n <= 31)
    }
    return transmuteWithLayouts<Int32x2T, Uint32x2T>(vsliNS32(transmuteWithLayouts<Uint32x2T, Int32x2T>(a, UnresolvedTypeLayout<Uint32x2T>(), UnresolvedTypeLayout<Int32x2T>()), transmuteWithLayouts<Uint32x2T, Int32x2T>(b, UnresolvedTypeLayout<Uint32x2T>(), UnresolvedTypeLayout<Int32x2T>()), n), UnresolvedTypeLayout<Int32x2T>(), UnresolvedTypeLayout<Uint32x2T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNU32)
 */
public fun vsliqNU32(a: Uint32x4T, b: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 0 && n <= 31)
    }
    return transmuteWithLayouts<Int32x4T, Uint32x4T>(vsliqNS32(transmuteWithLayouts<Uint32x4T, Int32x4T>(a, UnresolvedTypeLayout<Uint32x4T>(), UnresolvedTypeLayout<Int32x4T>()), transmuteWithLayouts<Uint32x4T, Int32x4T>(b, UnresolvedTypeLayout<Uint32x4T>(), UnresolvedTypeLayout<Int32x4T>()), n), UnresolvedTypeLayout<Int32x4T>(), UnresolvedTypeLayout<Uint32x4T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNU64)
 */
public fun vsliNU64(a: Uint64x1T, b: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Int64x1T, Uint64x1T>(vsliNS64(transmuteWithLayouts<Uint64x1T, Int64x1T>(a, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Uint64x1T, Int64x1T>(b, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Uint64x1T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNU64)
 */
public fun vsliqNU64(a: Uint64x2T, b: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Int64x2T, Uint64x2T>(vsliqNS64(transmuteWithLayouts<Uint64x2T, Int64x2T>(a, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), transmuteWithLayouts<Uint64x2T, Int64x2T>(b, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), n), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Uint64x2T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNP8)
 */
public fun vsliNP8(a: Poly8x8T, b: Poly8x8T, n: Int): Poly8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x8T, Poly8x8T>(vsliNS8(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x8T, Int8x8T>(b, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), n), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNP8)
 */
public fun vsliqNP8(a: Poly8x16T, b: Poly8x16T, n: Int): Poly8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vsliqNS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), n), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNP16)
 */
public fun vsliNP16(a: Poly16x4T, b: Poly16x4T, n: Int): Poly16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int16x4T, Poly16x4T>(vsliNS16(transmuteWithLayouts<Poly16x4T, Int16x4T>(a, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), transmuteWithLayouts<Poly16x4T, Int16x4T>(b, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), n), UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<Poly16x4T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNP16)
 */
public fun vsliqNP16(a: Poly16x8T, b: Poly16x8T, n: Int): Poly16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vsliqNS16(transmuteWithLayouts<Poly16x8T, Int16x8T>(a, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), transmuteWithLayouts<Poly16x8T, Int16x8T>(b, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), n), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliNP64)
 */
public fun vsliNP64(a: Poly64x1T, b: Poly64x1T, n: Int): Poly64x1T {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Int64x1T, Poly64x1T>(vsliNS64(transmuteWithLayouts<Poly64x1T, Int64x1T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Poly64x1T, Int64x1T>(b, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Poly64x1T>())
}

/**
 * Shift Left and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsliqNP64)
 */
public fun vsliqNP64(a: Poly64x2T, b: Poly64x2T, n: Int): Poly64x2T {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Int64x2T, Poly64x2T>(vsliqNS64(transmuteWithLayouts<Poly64x2T, Int64x2T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), transmuteWithLayouts<Poly64x2T, Int64x2T>(b, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), n), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Poly64x2T>())
}

/**
 * Shift left and insert
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vslidNS64)
 */
public fun vslidNS64(a: Long, b: Long, n: Int): Long {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Int64x1T, Long>(vsliNS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), PrimitiveLayouts.I64)
}

/**
 * Shift left and insert
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vslidNU64)
 */
public fun vslidNU64(a: ULong, b: ULong, n: Int): ULong {
    run {
        check(n >= 0 && n <= 63)
    }
    return transmuteWithLayouts<Uint64x1T, ULong>(vsliNU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), n), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)
}

/**
 * SM3PARTW1
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3partw1qU32)
 */
public fun vsm3partw1qU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    fun externVsm3partw1qU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVsm3partw1qU32(a, b, c)
}

/**
 * SM3PARTW2
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3partw2qU32)
 */
public fun vsm3partw2qU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    fun externVsm3partw2qU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVsm3partw2qU32(a, b, c)
}

/**
 * SM3SS1
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3ss1qU32)
 */
public fun vsm3ss1qU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    fun externVsm3ss1qU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVsm3ss1qU32(a, b, c)
}

/**
 * SM3TT1A
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3tt1aqU32)
 */
public fun vsm3tt1aqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, imm2: Int): Uint32x4T {
    fun externVsm3tt1aqU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T, n: Long): Uint32x4T = Uint32x4T()
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externVsm3tt1aqU32(a, b, c, imm2.toLong())
}

/**
 * SM3TT1B
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3tt1bqU32)
 */
public fun vsm3tt1bqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, imm2: Int): Uint32x4T {
    fun externVsm3tt1bqU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T, n: Long): Uint32x4T = Uint32x4T()
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externVsm3tt1bqU32(a, b, c, imm2.toLong())
}

/**
 * SM3TT2A
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3tt2aqU32)
 */
public fun vsm3tt2aqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, imm2: Int): Uint32x4T {
    fun externVsm3tt2aqU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T, n: Long): Uint32x4T = Uint32x4T()
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externVsm3tt2aqU32(a, b, c, imm2.toLong())
}

/**
 * SM3TT2B
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm3tt2bqU32)
 */
public fun vsm3tt2bqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, imm2: Int): Uint32x4T {
    fun externVsm3tt2bqU32(a1: Uint32x4T, b1: Uint32x4T, c1: Uint32x4T, n: Long): Uint32x4T = Uint32x4T()
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return externVsm3tt2bqU32(a, b, c, imm2.toLong())
}

/**
 * SM4 key
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm4ekeyqU32)
 */
public fun vsm4ekeyqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun externVsm4ekeyqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVsm4ekeyqU32(a, b)
}

/**
 * SM4 encode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsm4eqU32)
 */
public fun vsm4eqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun externVsm4eqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return externVsm4eqU32(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddU8)
 */
public fun vsqaddU8(a: Uint8x8T, b: Int8x8T): Uint8x8T {
    fun externVsqaddU8(a1: Uint8x8T, b1: Int8x8T): Uint8x8T = Uint8x8T()
    return externVsqaddU8(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddqU8)
 */
public fun vsqaddqU8(a: Uint8x16T, b: Int8x16T): Uint8x16T {
    fun externVsqaddqU8(a1: Uint8x16T, b1: Int8x16T): Uint8x16T = Uint8x16T()
    return externVsqaddqU8(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddU16)
 */
public fun vsqaddU16(a: Uint16x4T, b: Int16x4T): Uint16x4T {
    fun externVsqaddU16(a1: Uint16x4T, b1: Int16x4T): Uint16x4T = Uint16x4T()
    return externVsqaddU16(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddqU16)
 */
public fun vsqaddqU16(a: Uint16x8T, b: Int16x8T): Uint16x8T {
    fun externVsqaddqU16(a1: Uint16x8T, b1: Int16x8T): Uint16x8T = Uint16x8T()
    return externVsqaddqU16(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddU32)
 */
public fun vsqaddU32(a: Uint32x2T, b: Int32x2T): Uint32x2T {
    fun externVsqaddU32(a1: Uint32x2T, b1: Int32x2T): Uint32x2T = Uint32x2T()
    return externVsqaddU32(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddqU32)
 */
public fun vsqaddqU32(a: Uint32x4T, b: Int32x4T): Uint32x4T {
    fun externVsqaddqU32(a1: Uint32x4T, b1: Int32x4T): Uint32x4T = Uint32x4T()
    return externVsqaddqU32(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddU64)
 */
public fun vsqaddU64(a: Uint64x1T, b: Int64x1T): Uint64x1T {
    fun externVsqaddU64(a1: Uint64x1T, b1: Int64x1T): Uint64x1T = Uint64x1T()
    return externVsqaddU64(a, b)
}

/**
 * Unsigned saturating Accumulate of Signed value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddqU64)
 */
public fun vsqaddqU64(a: Uint64x2T, b: Int64x2T): Uint64x2T {
    fun externVsqaddqU64(a1: Uint64x2T, b1: Int64x2T): Uint64x2T = Uint64x2T()
    return externVsqaddqU64(a, b)
}

/**
 * Unsigned saturating accumulate of signed value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddbU8)
 */
public fun vsqaddbU8(a: UByte, b: Byte): UByte = vgetLaneU8(vsqaddU8(vdupNU8(a), vdupNS8(b)), 0u)

/**
 * Unsigned saturating accumulate of signed value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddhU16)
 */
public fun vsqaddhU16(a: UShort, b: Short): UShort = vgetLaneU16(vsqaddU16(vdupNU16(a), vdupNS16(b)), 0u)

/**
 * Unsigned saturating accumulate of signed value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqadddU64)
 */
public fun vsqadddU64(a: ULong, b: Long): ULong {
    fun externVsqadddU64(a1: ULong, b1: Long): ULong = 0uL
    return externVsqadddU64(a, b)
}

/**
 * Unsigned saturating accumulate of signed value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqaddsU32)
 */
public fun vsqaddsU32(a: UInt, b: Int): UInt {
    fun externVsqaddsU32(a1: UInt, b1: Int): UInt = 0u
    return externVsqaddsU32(a, b)
}

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtF16)
 */
public fun vsqrtF16(a: Float16x4T): Float16x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtqF16)
 */
public fun vsqrtqF16(a: Float16x8T): Float16x8T = run { val simdArg0 = a; simdArg0 }

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtF32)
 */
public fun vsqrtF32(a: Float32x2T): Float32x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtqF32)
 */
public fun vsqrtqF32(a: Float32x4T): Float32x4T = run { val simdArg0 = a; simdArg0 }

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtF64)
 */
public fun vsqrtF64(a: Float64x1T): Float64x1T = run { val simdArg0 = a; simdArg0 }

/**
 * Calculates the square root of each lane.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrtqF64)
 */
public fun vsqrtqF64(a: Float64x2T): Float64x2T = run { val simdArg0 = a; simdArg0 }

/**
 * Floating-point round to integral, using current rounding mode
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsqrthF16)
 */
public fun vsqrthF16(a: F16): F16 = sqrt(a)

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNS8)
 */
public fun vsriNS8(a: Int8x8T, b: Int8x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run {
        if (n.toUInt() == 8u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int8x8T, V>(a, UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int8x8T, V>(b, UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<V>())
            val mask = (UByte.MAX_VALUE.toUInt() shr n).toUByte()
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UByte>(List(8) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UByte>(List(8) { n.toUByte() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int8x8T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int8x8T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNS8)
 */
public fun vsriqNS8(a: Int8x16T, b: Int8x16T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run {
        if (n.toUInt() == 8u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int8x16T, V>(a, UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int8x16T, V>(b, UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<V>())
            val mask = (UByte.MAX_VALUE.toUInt() shr n).toUByte()
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UByte>(List(16) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UByte>(List(16) { n.toUByte() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int8x16T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int8x16T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNS16)
 */
public fun vsriNS16(a: Int16x4T, b: Int16x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run {
        if (n.toUInt() == 16u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int16x4T, V>(a, UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int16x4T, V>(b, UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<V>())
            val mask = (UShort.MAX_VALUE.toUInt() shr n).toUShort()
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UShort>(List(4) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UShort>(List(4) { n.toUShort() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int16x4T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int16x4T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNS16)
 */
public fun vsriqNS16(a: Int16x8T, b: Int16x8T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run {
        if (n.toUInt() == 16u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int16x8T, V>(a, UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int16x8T, V>(b, UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<V>())
            val mask = (UShort.MAX_VALUE.toUInt() shr n).toUShort()
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UShort>(List(8) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UShort>(List(8) { n.toUShort() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int16x8T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int16x8T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNS32)
 */
public fun vsriNS32(a: Int32x2T, b: Int32x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run {
        if (n.toUInt() == 32u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int32x2T, V>(a, UnresolvedTypeLayout<Int32x2T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int32x2T, V>(b, UnresolvedTypeLayout<Int32x2T>(), UnresolvedTypeLayout<V>())
            val mask = UInt.MAX_VALUE shr n
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UInt>(List(2) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UInt>(List(2) { n.toUInt() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int32x2T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int32x2T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNS32)
 */
public fun vsriqNS32(a: Int32x4T, b: Int32x4T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run {
        if (n.toUInt() == 32u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int32x4T, V>(a, UnresolvedTypeLayout<Int32x4T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int32x4T, V>(b, UnresolvedTypeLayout<Int32x4T>(), UnresolvedTypeLayout<V>())
            val mask = UInt.MAX_VALUE shr n
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<UInt>(List(4) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<UInt>(List(4) { n.toUInt() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int32x4T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int32x4T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNS64)
 */
public fun vsriNS64(a: Int64x1T, b: Int64x1T, n: Int): Int64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run {
        if (n.toUInt() == 64u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int64x1T, V>(a, UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int64x1T, V>(b, UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<V>())
            val mask = ULong.MAX_VALUE shr n
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<ULong>(List(1) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<ULong>(List(1) { n.toULong() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int64x1T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int64x1T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNS64)
 */
public fun vsriqNS64(a: Int64x2T, b: Int64x2T, n: Int): Int64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run {
        if (n.toUInt() == 64u) {
            a
        } else {
            val a1: V = transmuteWithLayouts<Int64x2T, V>(a, UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<V>())
            val b1: V = transmuteWithLayouts<Int64x2T, V>(b, UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<V>())
            val mask = ULong.MAX_VALUE shr n
            val kept: V = run { val simdArg0 = a1; val simdArg1 = Simd<ULong>(List(2) { mask.inv() }); simdArg0 }
            val shiftCounts = Simd<ULong>(List(2) { n.toULong() })
            val shifted = run { val simdArg0 = b1; val simdArg1 = shiftCounts; simdArg0 }
            transmuteWithLayouts<V, Int64x2T>(run { val simdArg0 = kept; val simdArg1 = shifted; simdArg0 }, UnresolvedTypeLayout<V>(), UnresolvedTypeLayout<Int64x2T>())
        }
    }
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNU8)
 */
public fun vsriNU8(a: Uint8x8T, b: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return transmuteWithLayouts<Int8x8T, Uint8x8T>(vsriNS8(transmuteWithLayouts<Uint8x8T, Int8x8T>(a, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Uint8x8T, Int8x8T>(b, UnresolvedTypeLayout<Uint8x8T>(), UnresolvedTypeLayout<Int8x8T>()), n), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Uint8x8T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNU8)
 */
public fun vsriqNU8(a: Uint8x16T, b: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return transmuteWithLayouts<Int8x16T, Uint8x16T>(vsriqNS8(transmuteWithLayouts<Uint8x16T, Int8x16T>(a, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Uint8x16T, Int8x16T>(b, UnresolvedTypeLayout<Uint8x16T>(), UnresolvedTypeLayout<Int8x16T>()), n), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Uint8x16T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNU16)
 */
public fun vsriNU16(a: Uint16x4T, b: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return transmuteWithLayouts<Int16x4T, Uint16x4T>(vsriNS16(transmuteWithLayouts<Uint16x4T, Int16x4T>(a, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), transmuteWithLayouts<Uint16x4T, Int16x4T>(b, UnresolvedTypeLayout<Uint16x4T>(), UnresolvedTypeLayout<Int16x4T>()), n), UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<Uint16x4T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNU16)
 */
public fun vsriqNU16(a: Uint16x8T, b: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return transmuteWithLayouts<Int16x8T, Uint16x8T>(vsriqNS16(transmuteWithLayouts<Uint16x8T, Int16x8T>(a, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), transmuteWithLayouts<Uint16x8T, Int16x8T>(b, UnresolvedTypeLayout<Uint16x8T>(), UnresolvedTypeLayout<Int16x8T>()), n), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Uint16x8T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNU32)
 */
public fun vsriNU32(a: Uint32x2T, b: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return transmuteWithLayouts<Int32x2T, Uint32x2T>(vsriNS32(transmuteWithLayouts<Uint32x2T, Int32x2T>(a, UnresolvedTypeLayout<Uint32x2T>(), UnresolvedTypeLayout<Int32x2T>()), transmuteWithLayouts<Uint32x2T, Int32x2T>(b, UnresolvedTypeLayout<Uint32x2T>(), UnresolvedTypeLayout<Int32x2T>()), n), UnresolvedTypeLayout<Int32x2T>(), UnresolvedTypeLayout<Uint32x2T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNU32)
 */
public fun vsriqNU32(a: Uint32x4T, b: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return transmuteWithLayouts<Int32x4T, Uint32x4T>(vsriqNS32(transmuteWithLayouts<Uint32x4T, Int32x4T>(a, UnresolvedTypeLayout<Uint32x4T>(), UnresolvedTypeLayout<Int32x4T>()), transmuteWithLayouts<Uint32x4T, Int32x4T>(b, UnresolvedTypeLayout<Uint32x4T>(), UnresolvedTypeLayout<Int32x4T>()), n), UnresolvedTypeLayout<Int32x4T>(), UnresolvedTypeLayout<Uint32x4T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNU64)
 */
public fun vsriNU64(a: Uint64x1T, b: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Int64x1T, Uint64x1T>(vsriNS64(transmuteWithLayouts<Uint64x1T, Int64x1T>(a, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Uint64x1T, Int64x1T>(b, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Uint64x1T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNU64)
 */
public fun vsriqNU64(a: Uint64x2T, b: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Int64x2T, Uint64x2T>(vsriqNS64(transmuteWithLayouts<Uint64x2T, Int64x2T>(a, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), transmuteWithLayouts<Uint64x2T, Int64x2T>(b, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), n), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Uint64x2T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNP8)
 */
public fun vsriNP8(a: Poly8x8T, b: Poly8x8T, n: Int): Poly8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return transmuteWithLayouts<Int8x8T, Poly8x8T>(vsriNS8(transmuteWithLayouts<Poly8x8T, Int8x8T>(a, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), transmuteWithLayouts<Poly8x8T, Int8x8T>(b, UnresolvedTypeLayout<Poly8x8T>(), UnresolvedTypeLayout<Int8x8T>()), n), UnresolvedTypeLayout<Int8x8T>(), UnresolvedTypeLayout<Poly8x8T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNP8)
 */
public fun vsriqNP8(a: Poly8x16T, b: Poly8x16T, n: Int): Poly8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return transmuteWithLayouts<Int8x16T, Poly8x16T>(vsriqNS8(transmuteWithLayouts<Poly8x16T, Int8x16T>(a, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), transmuteWithLayouts<Poly8x16T, Int8x16T>(b, UnresolvedTypeLayout<Poly8x16T>(), UnresolvedTypeLayout<Int8x16T>()), n), UnresolvedTypeLayout<Int8x16T>(), UnresolvedTypeLayout<Poly8x16T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNP16)
 */
public fun vsriNP16(a: Poly16x4T, b: Poly16x4T, n: Int): Poly16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return transmuteWithLayouts<Int16x4T, Poly16x4T>(vsriNS16(transmuteWithLayouts<Poly16x4T, Int16x4T>(a, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), transmuteWithLayouts<Poly16x4T, Int16x4T>(b, UnresolvedTypeLayout<Poly16x4T>(), UnresolvedTypeLayout<Int16x4T>()), n), UnresolvedTypeLayout<Int16x4T>(), UnresolvedTypeLayout<Poly16x4T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNP16)
 */
public fun vsriqNP16(a: Poly16x8T, b: Poly16x8T, n: Int): Poly16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return transmuteWithLayouts<Int16x8T, Poly16x8T>(vsriqNS16(transmuteWithLayouts<Poly16x8T, Int16x8T>(a, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), transmuteWithLayouts<Poly16x8T, Int16x8T>(b, UnresolvedTypeLayout<Poly16x8T>(), UnresolvedTypeLayout<Int16x8T>()), n), UnresolvedTypeLayout<Int16x8T>(), UnresolvedTypeLayout<Poly16x8T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriNP64)
 */
public fun vsriNP64(a: Poly64x1T, b: Poly64x1T, n: Int): Poly64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Int64x1T, Poly64x1T>(vsriNS64(transmuteWithLayouts<Poly64x1T, Int64x1T>(a, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Poly64x1T, Int64x1T>(b, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), UnresolvedTypeLayout<Poly64x1T>())
}

/**
 * Shift Right and Insert (immediate)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsriqNP64)
 */
public fun vsriqNP64(a: Poly64x2T, b: Poly64x2T, n: Int): Poly64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Int64x2T, Poly64x2T>(vsriqNS64(transmuteWithLayouts<Poly64x2T, Int64x2T>(a, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), transmuteWithLayouts<Poly64x2T, Int64x2T>(b, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), n), UnresolvedTypeLayout<Int64x2T>(), UnresolvedTypeLayout<Poly64x2T>())
}

/**
 * Shift right and insert
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsridNS64)
 */
public fun vsridNS64(a: Long, b: Long, n: Int): Long {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Int64x1T, Long>(vsriNS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), n), UnresolvedTypeLayout<Int64x1T>(), PrimitiveLayouts.I64)
}

/**
 * Shift right and insert
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsridNU64)
 */
public fun vsridNU64(a: ULong, b: ULong, n: Int): ULong {
    run {
        check(n >= 1 && n <= 64)
    }
    return transmuteWithLayouts<Uint64x1T, ULong>(vsriNU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), n), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F16(ptr: MutPtr, a: Float16x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF16(ptr: MutPtr, a: Float16x8T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F32(ptr: MutPtr, a: Float32x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF32(ptr: MutPtr, a: Float32x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F64(ptr: MutPtr, a: Float64x1T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF64(ptr: MutPtr, a: Float64x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S8(ptr: MutPtr, a: Int8x8T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS8(ptr: MutPtr, a: Int8x16T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S16(ptr: MutPtr, a: Int16x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS16(ptr: MutPtr, a: Int16x8T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S32(ptr: MutPtr, a: Int32x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS32(ptr: MutPtr, a: Int32x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S64(ptr: MutPtr, a: Int64x1T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS64(ptr: MutPtr, a: Int64x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.I64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U8(ptr: MutPtr, a: Uint8x8T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU8(ptr: MutPtr, a: Uint8x16T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U16(ptr: MutPtr, a: Uint16x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU16(ptr: MutPtr, a: Uint16x8T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U32(ptr: MutPtr, a: Uint32x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU32(ptr: MutPtr, a: Uint32x4T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U64(ptr: MutPtr, a: Uint64x1T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU64(ptr: MutPtr, a: Uint64x2T) {
    writeUnaligned(ptr, a, typeLayoutT = PrimitiveLayouts.U64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P8(ptr: MutPtr, a: Poly8x8T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P8>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP8(ptr: MutPtr, a: Poly8x16T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P8>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P16(ptr: MutPtr, a: Poly16x4T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P16>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP16(ptr: MutPtr, a: Poly16x8T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P16>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P64(ptr: MutPtr, a: Poly64x1T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P64>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP64(ptr: MutPtr, a: Poly64x2T) {
    writeUnaligned(ptr, a, typeLayoutT = UnresolvedTypeLayout<P64>())
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F64X2(a: MutPtr, b: Float64x1x2T) {
    fun externVst1F64X2(a1: Float64x1T, b1: Float64x1T, ptr: MutPtr) {}
    externVst1F64X2(b.field0, b.field1, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF64X2(a: MutPtr, b: Float64x2x2T) {
    fun externVst1qF64X2(a1: Float64x2T, b1: Float64x2T, ptr: MutPtr) {}
    externVst1qF64X2(b.field0, b.field1, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F64X3(a: MutPtr, b: Float64x1x3T) {
    fun externVst1F64X3(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, ptr: MutPtr) {}
    externVst1F64X3(b.field0, b.field1, b.field2, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF64X3(a: MutPtr, b: Float64x2x3T) {
    fun externVst1qF64X3(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, ptr: MutPtr) {}
    externVst1qF64X3(b.field0, b.field1, b.field2, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F64X4(a: MutPtr, b: Float64x1x4T) {
    fun externVst1F64X4(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, d: Float64x1T, ptr: MutPtr) {}
    externVst1F64X4(b.field0, b.field1, b.field2, b.field3, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF64X4(a: MutPtr, b: Float64x2x4T) {
    fun externVst1qF64X4(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, d: Float64x2T, ptr: MutPtr) {}
    externVst1qF64X4(b.field0, b.field1, b.field2, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneF64(a: MutPtr, b: Float64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    writeUnaligned(a, run { val simdArg0 = b; val simdArg1 = lane.toUInt(); simdArg0 }, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneF64(a: MutPtr, b: Float64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val simdArg0 = b; val simdArg1 = lane.toUInt(); simdArg0 }, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2F64(a: MutPtr, b: Float64x1x2T) {
    writeUnaligned(a, b, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneF64(a: MutPtr, b: Float64x1x2T, lane: Int) {
    fun externVst2LaneF64(a1: Float64x1T, b1: Float64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst2LaneF64(b.field0, b.field1, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneS64(a: MutPtr, b: Int64x1x2T, lane: Int) {
    fun externVst2LaneS64(a1: Int64x1T, b1: Int64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst2LaneS64(b.field0, b.field1, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneP64(a: MutPtr, b: Poly64x1x2T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst2LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x1x2T, Int64x1x2T>(b, UnresolvedTypeLayout<Poly64x1x2T>(), UnresolvedTypeLayout<Int64x1x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneU64(a: MutPtr, b: Uint64x1x2T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst2LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x1x2T, Int64x1x2T>(b, UnresolvedTypeLayout<Uint64x1x2T>(), UnresolvedTypeLayout<Int64x1x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qF64(a: MutPtr, b: Float64x2x2T) {
    run {
        val w: W = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 2, 2, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = UnresolvedTypeLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qS64(a: MutPtr, b: Int64x2x2T) {
    run {
        val w: W = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 2, 2, 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = UnresolvedTypeLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneF64(a: MutPtr, b: Float64x2x2T, lane: Int) {
    fun externVst2qLaneF64(a1: Float64x2T, b1: Float64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst2qLaneF64(b.field0, b.field1, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneS8(a: MutPtr, b: Int8x16x2T, lane: Int) {
    fun externVst2qLaneS8(a1: Int8x16T, b1: Int8x16T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    externVst2qLaneS8(b.field0, b.field1, lane.toLong(), a)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneS64(a: MutPtr, b: Int64x2x2T, lane: Int) {
    fun externVst2qLaneS64(a1: Int64x2T, b1: Int64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst2qLaneS64(b.field0, b.field1, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneP64(a: MutPtr, b: Poly64x2x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst2qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Poly64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneU8(a: MutPtr, b: Uint8x16x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst2qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint8x16x2T, Int8x16x2T>(b, UnresolvedTypeLayout<Uint8x16x2T>(), UnresolvedTypeLayout<Int8x16x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneU64(a: MutPtr, b: Uint64x2x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst2qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneP8(a: MutPtr, b: Poly8x16x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst2qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly8x16x2T, Int8x16x2T>(b, UnresolvedTypeLayout<Poly8x16x2T>(), UnresolvedTypeLayout<Int8x16x2T>()), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qP64(a: MutPtr, b: Poly64x2x2T) {
    vst2qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Poly64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qU64(a: MutPtr, b: Uint64x2x2T) {
    vst2qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x2T, Int64x2x2T>(b, UnresolvedTypeLayout<Uint64x2x2T>(), UnresolvedTypeLayout<Int64x2x2T>()))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3F64(a: MutPtr, b: Float64x1x3T) {
    writeUnaligned(a, b, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneF64(a: MutPtr, b: Float64x1x3T, lane: Int) {
    fun externVst3LaneF64(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst3LaneF64(b.field0, b.field1, b.field2, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneS64(a: MutPtr, b: Int64x1x3T, lane: Int) {
    fun externVst3LaneS64(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst3LaneS64(b.field0, b.field1, b.field2, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneP64(a: MutPtr, b: Poly64x1x3T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst3LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x1x3T, Int64x1x3T>(b, UnresolvedTypeLayout<Poly64x1x3T>(), UnresolvedTypeLayout<Int64x1x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneU64(a: MutPtr, b: Uint64x1x3T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst3LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x1x3T, Int64x1x3T>(b, UnresolvedTypeLayout<Uint64x1x3T>(), UnresolvedTypeLayout<Int64x1x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qF64(a: MutPtr, b: Float64x2x3T) {
    run {
        val v0v1: Simd<Double> = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Double>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); res } }
        val v2v2: Simd<Double> = run { val simdArg0 = b.field2; val simdArg1 = b.field2; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Double>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); res } }
        val w: W = run { val simdArg0 = v0v1; val simdArg1 = v2v2; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 3, 2, 3)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        val arr: DoubleArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.doubleArray((2 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qS64(a: MutPtr, b: Int64x2x3T) {
    run {
        val v0v1: Simd<Long> = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Long>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); res } }
        val v2v2: Simd<Long> = run { val simdArg0 = b.field2; val simdArg1 = b.field2; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Long>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); res } }
        val w: W = run { val simdArg0 = v0v1; val simdArg1 = v2v2; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 3, 2, 3)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        val arr: LongArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.longArray((2 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneF64(a: MutPtr, b: Float64x2x3T, lane: Int) {
    fun externVst3qLaneF64(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst3qLaneF64(b.field0, b.field1, b.field2, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneS8(a: MutPtr, b: Int8x16x3T, lane: Int) {
    fun externVst3qLaneS8(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    externVst3qLaneS8(b.field0, b.field1, b.field2, lane.toLong(), a)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneS64(a: MutPtr, b: Int64x2x3T, lane: Int) {
    fun externVst3qLaneS64(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst3qLaneS64(b.field0, b.field1, b.field2, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneP64(a: MutPtr, b: Poly64x2x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst3qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Poly64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneU8(a: MutPtr, b: Uint8x16x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst3qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint8x16x3T, Int8x16x3T>(b, UnresolvedTypeLayout<Uint8x16x3T>(), UnresolvedTypeLayout<Int8x16x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneU64(a: MutPtr, b: Uint64x2x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst3qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneP8(a: MutPtr, b: Poly8x16x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst3qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly8x16x3T, Int8x16x3T>(b, UnresolvedTypeLayout<Poly8x16x3T>(), UnresolvedTypeLayout<Int8x16x3T>()), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qP64(a: MutPtr, b: Poly64x2x3T) {
    vst3qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Poly64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qU64(a: MutPtr, b: Uint64x2x3T) {
    vst3qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x3T, Int64x2x3T>(b, UnresolvedTypeLayout<Uint64x2x3T>(), UnresolvedTypeLayout<Int64x2x3T>()))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4F64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4F64(a: MutPtr, b: Float64x1x4T) {
    writeUnaligned(a, b, typeLayoutT = PrimitiveLayouts.F64)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneF64(a: MutPtr, b: Float64x1x4T, lane: Int) {
    fun externVst4LaneF64(a1: Float64x1T, b1: Float64x1T, c: Float64x1T, d: Float64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst4LaneF64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneS64(a: MutPtr, b: Int64x1x4T, lane: Int) {
    fun externVst4LaneS64(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, d: Int64x1T, n: Long, ptr: MutPtr) {}
    run {
        check(lane == 0)
    }
    externVst4LaneS64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneP64(a: MutPtr, b: Poly64x1x4T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst4LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x1x4T, Int64x1x4T>(b, UnresolvedTypeLayout<Poly64x1x4T>(), UnresolvedTypeLayout<Int64x1x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneU64(a: MutPtr, b: Uint64x1x4T, lane: Int) {
    run {
        check(lane == 0)
    }
    vst4LaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x1x4T, Int64x1x4T>(b, UnresolvedTypeLayout<Uint64x1x4T>(), UnresolvedTypeLayout<Int64x1x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qF64(a: MutPtr, b: Float64x2x4T) {
    run {
        val v0v1: Simd<Double> = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Double>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); res } }
        val v2v3: Simd<Double> = run { val simdArg0 = b.field2; val simdArg1 = b.field3; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Double>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Double>>()).alignBytes)); res } }
        val w: W = run { val simdArg0 = v0v1; val simdArg1 = v2v3; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 4, 2, 4)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = UnresolvedTypeLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qS64(a: MutPtr, b: Int64x2x4T) {
    run {
        val v0v1: Simd<Long> = run { val simdArg0 = b.field0; val simdArg1 = b.field1; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Long>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); res } }
        val v2v3: Simd<Long> = run { val simdArg0 = b.field2; val simdArg1 = b.field3; val simdArg2 = SimdShuffleIdx(identity(2 * 2)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); val res = (UnresolvedTypeLayout<Simd<Long>>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<Simd<Long>>()).alignBytes)); res } }
        val w: W = run { val simdArg0 = v0v1; val simdArg1 = v2v3; val simdArg2 = SimdShuffleIdx(interleaveMask(2 * 4, 2, 4)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = UnresolvedTypeLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneF64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneF64(a: MutPtr, b: Float64x2x4T, lane: Int) {
    fun externVst4qLaneF64(a1: Float64x2T, b1: Float64x2T, c: Float64x2T, d: Float64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst4qLaneF64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneS8(a: MutPtr, b: Int8x16x4T, lane: Int) {
    fun externVst4qLaneS8(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, d: Int8x16T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    externVst4qLaneS8(b.field0, b.field1, b.field2, b.field3, lane.toLong(), a)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneS64(a: MutPtr, b: Int64x2x4T, lane: Int) {
    fun externVst4qLaneS64(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, d: Int64x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    externVst4qLaneS64(b.field0, b.field1, b.field2, b.field3, lane.toLong(), MutPtr(a.rawAddress, a.rawMeta))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneP64(a: MutPtr, b: Poly64x2x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst4qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Poly64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneU8(a: MutPtr, b: Uint8x16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst4qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint8x16x4T, Int8x16x4T>(b, UnresolvedTypeLayout<Uint8x16x4T>(), UnresolvedTypeLayout<Int8x16x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneU64(a: MutPtr, b: Uint64x2x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst4qLaneS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneP8(a: MutPtr, b: Poly8x16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    vst4qLaneS8(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly8x16x4T, Int8x16x4T>(b, UnresolvedTypeLayout<Poly8x16x4T>(), UnresolvedTypeLayout<Int8x16x4T>()), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qP64(a: MutPtr, b: Poly64x2x4T) {
    vst4qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Poly64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Poly64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qU64(a: MutPtr, b: Uint64x2x4T) {
    vst4qS64(transmuteWithLayouts<MutPtr, MutPtr>(a, PrimitiveLayouts.mutPtrLayout(64L), UnresolvedTypeLayout<MutPtr>()), transmuteWithLayouts<Uint64x2x4T, Int64x2x4T>(b, UnresolvedTypeLayout<Uint64x2x4T>(), UnresolvedTypeLayout<Int64x2x4T>()))
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1LaneF64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1LaneF64(ptr: MutPtr, value: Float64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    vstl1LaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Float64x1T, Int64x1T>(value, UnresolvedTypeLayout<Float64x1T>(), UnresolvedTypeLayout<Int64x1T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1qLaneF64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1qLaneF64(ptr: MutPtr, value: Float64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vstl1qLaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Float64x2T, Int64x2T>(value, UnresolvedTypeLayout<Float64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1LaneU64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1LaneU64(ptr: MutPtr, value: Uint64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    vstl1LaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Uint64x1T, Int64x1T>(value, UnresolvedTypeLayout<Uint64x1T>(), UnresolvedTypeLayout<Int64x1T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1qLaneU64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1qLaneU64(ptr: MutPtr, value: Uint64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vstl1qLaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Uint64x2T, Int64x2T>(value, UnresolvedTypeLayout<Uint64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1LaneP64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1LaneP64(ptr: MutPtr, value: Poly64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    vstl1LaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Poly64x1T, Int64x1T>(value, UnresolvedTypeLayout<Poly64x1T>(), UnresolvedTypeLayout<Int64x1T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1qLaneP64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1qLaneP64(ptr: MutPtr, value: Poly64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vstl1qLaneS64(MutPtr(ptr.rawAddress, ptr.rawMeta), transmuteWithLayouts<Poly64x2T, Int64x2T>(value, UnresolvedTypeLayout<Poly64x2T>(), UnresolvedTypeLayout<Int64x2T>()), lane)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1LaneS64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1LaneS64(ptr: MutPtr, value: Int64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    val atomicDst = MutPtr(ptr.rawAddress, ptr.rawMeta)
    val lane: Long = vgetLaneS64(value, lane)
    (UnresolvedTypeLayout<AtomicI64>()).readNative((atomicDst).rawAddress).store(transmuteWithLayouts<Long, Unit>(lane, PrimitiveLayouts.I64, UnresolvedTypeLayout<Unit>()), Ordering.Release)
}

/**
 * Store-Release a single-element structure from one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstl1qLaneS64)
 * ## Safety
 *  * The pointer in `ptr` must satisfy the requirements of [core.ptr.write].
 */
public fun vstl1qLaneS64(ptr: MutPtr, value: Int64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val atomicDst = MutPtr(ptr.rawAddress, ptr.rawMeta)
    val lane: Long = vgetqLaneS64(value, lane)
    (UnresolvedTypeLayout<AtomicI64>()).readNative((atomicDst).rawAddress).store(transmuteWithLayouts<Long, Unit>(lane, PrimitiveLayouts.I64, UnresolvedTypeLayout<Unit>()), Ordering.Release)
}

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubF64)
 */
public fun vsubF64(a: Float64x1T, b: Float64x1T): Float64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqF64)
 */
public fun vsubqF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubdS64)
 */
public fun vsubdS64(a: Long, b: Long): Long = (a - (b.toLong()))

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubdU64)
 */
public fun vsubdU64(a: ULong, b: ULong): ULong = (a - (b.toULong()))

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhF16)
 */
public fun vsubhF16(a: F16, b: F16): F16 = a - b

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighS8)
 */
public fun vsublHighS8(a: Int8x16T, b: Int8x16T): Int16x8T =
    run {
        val c: Int16x8T = run { val simdArg0 = vgetHighS8(a); Int16x8T() }
        val d: Int16x8T = run { val simdArg0 = vgetHighS8(b); Int16x8T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighS16)
 */
public fun vsublHighS16(a: Int16x8T, b: Int16x8T): Int32x4T =
    run {
        val c: Int32x4T = run { val simdArg0 = vgetHighS16(a); Int32x4T() }
        val d: Int32x4T = run { val simdArg0 = vgetHighS16(b); Int32x4T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighS32)
 */
public fun vsublHighS32(a: Int32x4T, b: Int32x4T): Int64x2T =
    run {
        val c: Int64x2T = run { val simdArg0 = vgetHighS32(a); Int64x2T() }
        val d: Int64x2T = run { val simdArg0 = vgetHighS32(b); Int64x2T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighU8)
 */
public fun vsublHighU8(a: Uint8x16T, b: Uint8x16T): Uint16x8T =
    run {
        val c: Uint16x8T = run { val simdArg0 = vgetHighU8(a); Uint16x8T() }
        val d: Uint16x8T = run { val simdArg0 = vgetHighU8(b); Uint16x8T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighU16)
 */
public fun vsublHighU16(a: Uint16x8T, b: Uint16x8T): Uint32x4T =
    run {
        val c: Uint32x4T = run { val simdArg0 = vgetHighU16(a); Uint32x4T() }
        val d: Uint32x4T = run { val simdArg0 = vgetHighU16(b); Uint32x4T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublHighU32)
 */
public fun vsublHighU32(a: Uint32x4T, b: Uint32x4T): Uint64x2T =
    run {
        val c: Uint64x2T = run { val simdArg0 = vgetHighU32(a); Uint64x2T() }
        val d: Uint64x2T = run { val simdArg0 = vgetHighU32(b); Uint64x2T() }
        run { val simdArg0 = c; val simdArg1 = d; simdArg0 }
    }

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighS8)
 */
public fun vsubwHighS8(a: Int16x8T, b: Int8x16T): Int16x8T {
    val c = vgetHighS8(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Int16x8T() }; simdArg0 }
}

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighS16)
 */
public fun vsubwHighS16(a: Int32x4T, b: Int16x8T): Int32x4T {
    val c = vgetHighS16(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Int32x4T() }; simdArg0 }
}

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighS32)
 */
public fun vsubwHighS32(a: Int64x2T, b: Int32x4T): Int64x2T {
    val c = vgetHighS32(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Int64x2T() }; simdArg0 }
}

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighU8)
 */
public fun vsubwHighU8(a: Uint16x8T, b: Uint8x16T): Uint16x8T {
    val c = vgetHighU8(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Uint16x8T() }; simdArg0 }
}

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighU16)
 */
public fun vsubwHighU16(a: Uint32x4T, b: Uint16x8T): Uint32x4T {
    val c = vgetHighU16(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Uint32x4T() }; simdArg0 }
}

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwHighU32)
 */
public fun vsubwHighU32(a: Uint64x2T, b: Uint32x4T): Uint64x2T {
    val c = vgetHighU32(b)
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = c; Uint64x2T() }; simdArg0 }
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl1S8)
 */
public fun vtbl1S8(a: Int8x8T, b: Int8x8T): Int8x8T = vqtbl1S8(vcombineS8(a, Int8x8T()), transmute(b))

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl1U8)
 */
public fun vtbl1U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = vqtbl1U8(vcombineU8(a, Uint8x8T()), b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl1P8)
 */
public fun vtbl1P8(a: Poly8x8T, b: Uint8x8T): Poly8x8T = vqtbl1P8(vcombineP8(a, Poly8x8T()), b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl2S8)
 */
public fun vtbl2S8(a: Int8x8x2T, b: Int8x8T): Int8x8T = vqtbl1S8(vcombineS8(a.field0, a.field1), vreinterpretU8S8(b))

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl2U8)
 */
public fun vtbl2U8(a: Uint8x8x2T, b: Uint8x8T): Uint8x8T = vqtbl1U8(vcombineU8(a.field0, a.field1), b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl2P8)
 */
public fun vtbl2P8(a: Poly8x8x2T, b: Uint8x8T): Poly8x8T = vqtbl1P8(vcombineP8(a.field0, a.field1), b)

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl3S8)
 */
public fun vtbl3S8(a: Int8x8x3T, b: Int8x8T): Int8x8T {
    val x = Int8x16x2T(vcombineS8(a.field0, a.field1), vcombineS8(a.field2, Int8x8T()))
    return vqtbl2S8(x, vreinterpretU8S8(b))
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl3U8)
 */
public fun vtbl3U8(a: Uint8x8x3T, b: Uint8x8T): Uint8x8T {
    val x = Uint8x16x2T(vcombineU8(a.field0, a.field1), vcombineU8(a.field2, Uint8x8T()))
    return vqtbl2U8(x, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl3P8)
 */
public fun vtbl3P8(a: Poly8x8x3T, b: Uint8x8T): Poly8x8T {
    val x = Poly8x16x2T(vcombineP8(a.field0, a.field1), vcombineP8(a.field2, Poly8x8T()))
    return vqtbl2P8(x, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl4S8)
 */
public fun vtbl4S8(a: Int8x8x4T, b: Int8x8T): Int8x8T {
    val x = Int8x16x2T(vcombineS8(a.field0, a.field1), vcombineS8(a.field2, a.field3))
    return vqtbl2S8(x, vreinterpretU8S8(b))
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl4U8)
 */
public fun vtbl4U8(a: Uint8x8x4T, b: Uint8x8T): Uint8x8T {
    val x = Uint8x16x2T(vcombineU8(a.field0, a.field1), vcombineU8(a.field2, a.field3))
    return vqtbl2U8(x, b)
}

/**
 * Table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbl4P8)
 */
public fun vtbl4P8(a: Poly8x8x4T, b: Uint8x8T): Poly8x8T {
    val x = Poly8x16x2T(vcombineP8(a.field0, a.field1), vcombineP8(a.field2, a.field3))
    return vqtbl2P8(x, b)
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx1S8)
 */
public fun vtbx1S8(a: Int8x8T, b: Int8x8T, c: Int8x8T): Int8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Int8x8T>(Simd<Byte>(List(8) { 8.toByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1S8(a, vcombineS8(b, Int8x8T()), vreinterpretU8S8(c)); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx1U8)
 */
public fun vtbx1U8(a: Uint8x8T, b: Uint8x8T, c: Uint8x8T): Uint8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 8u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1U8(a, vcombineU8(b, Uint8x8T()), c); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx1P8)
 */
public fun vtbx1P8(a: Poly8x8T, b: Poly8x8T, c: Uint8x8T): Poly8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 8u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1P8(a, vcombineP8(b, Poly8x8T()), c); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx2S8)
 */
public fun vtbx2S8(a: Int8x8T, b: Int8x8x2T, c: Int8x8T): Int8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Int8x8T>(Simd<Byte>(List(8) { 16.toByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1S8(a, vcombineS8(b.field0, b.field1), vreinterpretU8S8(c)); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx2U8)
 */
public fun vtbx2U8(a: Uint8x8T, b: Uint8x8x2T, c: Uint8x8T): Uint8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 16u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1U8(a, vcombineU8(b.field0, b.field1), c); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx2P8)
 */
public fun vtbx2P8(a: Poly8x8T, b: Poly8x8x2T, c: Uint8x8T): Poly8x8T = run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 16u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx1P8(a, vcombineP8(b.field0, b.field1), c); val simdArg2 = a; simdArg1 }

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx3S8)
 */
public fun vtbx3S8(a: Int8x8T, b: Int8x8x3T, c: Int8x8T): Int8x8T {
    val x = Int8x16x2T(vcombineS8(b.field0, b.field1), vcombineS8(b.field2, Int8x8T()))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Int8x8T>(Simd<Byte>(List(8) { 24.toByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2S8(a, x, vreinterpretU8S8(c)); val simdArg2 = a; simdArg1 }
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx3U8)
 */
public fun vtbx3U8(a: Uint8x8T, b: Uint8x8x3T, c: Uint8x8T): Uint8x8T {
    val x = Uint8x16x2T(vcombineU8(b.field0, b.field1), vcombineU8(b.field2, Uint8x8T()))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 24u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2U8(a, x, c); val simdArg2 = a; simdArg1 }
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx3P8)
 */
public fun vtbx3P8(a: Poly8x8T, b: Poly8x8x3T, c: Uint8x8T): Poly8x8T {
    val x = Poly8x16x2T(vcombineP8(b.field0, b.field1), vcombineP8(b.field2, Poly8x8T()))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 24u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2P8(a, x, c); val simdArg2 = a; simdArg1 }
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx4S8)
 */
public fun vtbx4S8(a: Int8x8T, b: Int8x8x4T, c: Int8x8T): Int8x8T {
    val x = Int8x16x2T(vcombineS8(b.field0, b.field1), vcombineS8(b.field2, b.field3))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Int8x8T>(Simd<Byte>(List(8) { 32.toByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2S8(a, x, vreinterpretU8S8(c)); val simdArg2 = a; simdArg1 }
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx4U8)
 */
public fun vtbx4U8(a: Uint8x8T, b: Uint8x8x4T, c: Uint8x8T): Uint8x8T {
    val x = Uint8x16x2T(vcombineU8(b.field0, b.field1), vcombineU8(b.field2, b.field3))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 32u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2U8(a, x, c); val simdArg2 = a; simdArg1 }
}

/**
 * Extended table look-up
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtbx4P8)
 */
public fun vtbx4P8(a: Poly8x8T, b: Poly8x8x4T, c: Uint8x8T): Poly8x8T {
    val x = Poly8x16x2T(vcombineP8(b.field0, b.field1), vcombineP8(b.field2, b.field3))
    return run { val simdArg0 = run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<Any?, Uint8x8T>(Simd<UByte>(List(8) { 32u.toUByte() }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Uint8x8T>()); Int8x8T() }; val simdArg1 = vqtbx2P8(a, x, c); val simdArg2 = a; simdArg1 }
}

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1F16)
 */
public fun vtrn1F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Float16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qF16)
 */
public fun vtrn1qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Float16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1F32)
 */
public fun vtrn1F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qF64)
 */
public fun vtrn1qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1S32)
 */
public fun vtrn1S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qS64)
 */
public fun vtrn1qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1U32)
 */
public fun vtrn1U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qU64)
 */
public fun vtrn1qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qP64)
 */
public fun vtrn1qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Poly64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qF32)
 */
public fun vtrn1qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Float32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1S8)
 */
public fun vtrn1S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Int8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qS8)
 */
public fun vtrn1qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 2u, 18u, 4u, 20u, 6u, 22u, 8u, 24u, 10u, 26u, 12u, 28u, 14u, 30u)); Int8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1S16)
 */
public fun vtrn1S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Int16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qS16)
 */
public fun vtrn1qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Int16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qS32)
 */
public fun vtrn1qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Int32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1U8)
 */
public fun vtrn1U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Uint8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qU8)
 */
public fun vtrn1qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 2u, 18u, 4u, 20u, 6u, 22u, 8u, 24u, 10u, 26u, 12u, 28u, 14u, 30u)); Uint8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1U16)
 */
public fun vtrn1U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Uint16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qU16)
 */
public fun vtrn1qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Uint16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qU32)
 */
public fun vtrn1qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Uint32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1P8)
 */
public fun vtrn1P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Poly8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qP8)
 */
public fun vtrn1qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 2u, 18u, 4u, 20u, 6u, 22u, 8u, 24u, 10u, 26u, 12u, 28u, 14u, 30u)); Poly8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1P16)
 */
public fun vtrn1P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 2u, 6u)); Poly16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn1qP16)
 */
public fun vtrn1qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 2u, 10u, 4u, 12u, 6u, 14u)); Poly16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2F16)
 */
public fun vtrn2F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Float16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qF16)
 */
public fun vtrn2qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Float16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2F32)
 */
public fun vtrn2F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qF64)
 */
public fun vtrn2qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2S32)
 */
public fun vtrn2S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qS64)
 */
public fun vtrn2qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2U32)
 */
public fun vtrn2U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint32x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qU64)
 */
public fun vtrn2qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qP64)
 */
public fun vtrn2qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Poly64x2T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qF32)
 */
public fun vtrn2qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Float32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2S8)
 */
public fun vtrn2S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Int8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qS8)
 */
public fun vtrn2qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 17u, 3u, 19u, 5u, 21u, 7u, 23u, 9u, 25u, 11u, 27u, 13u, 29u, 15u, 31u)); Int8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2S16)
 */
public fun vtrn2S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Int16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qS16)
 */
public fun vtrn2qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Int16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qS32)
 */
public fun vtrn2qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Int32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2U8)
 */
public fun vtrn2U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Uint8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qU8)
 */
public fun vtrn2qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 17u, 3u, 19u, 5u, 21u, 7u, 23u, 9u, 25u, 11u, 27u, 13u, 29u, 15u, 31u)); Uint8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2U16)
 */
public fun vtrn2U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Uint16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qU16)
 */
public fun vtrn2qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Uint16x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qU32)
 */
public fun vtrn2qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Uint32x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2P8)
 */
public fun vtrn2P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Poly8x8T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qP8)
 */
public fun vtrn2qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 17u, 3u, 19u, 5u, 21u, 7u, 23u, 9u, 25u, 11u, 27u, 13u, 29u, 15u, 31u)); Poly8x16T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2P16)
 */
public fun vtrn2P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 5u, 3u, 7u)); Poly16x4T() }

/**
 * Transpose vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrn2qP16)
 */
public fun vtrn2qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 9u, 3u, 11u, 5u, 13u, 7u, 15u)); Poly16x8T() }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstS64)
 */
public fun vtstS64(a: Int64x1T, b: Int64x1T): Uint64x1T =
    run {
        val c: Int64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: I64x1 = Simd<Long>(listOf(0L))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<I64x1, Int64x1T>(d, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Int64x1T>()); Uint64x1T() }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqS64)
 */
public fun vtstqS64(a: Int64x2T, b: Int64x2T): Uint64x2T =
    run {
        val c: Int64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: I64x2 = Simd<Long>(listOf(0L, 0L))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<I64x2, Int64x2T>(d, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Int64x2T>()); Uint64x2T() }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstP64)
 */
public fun vtstP64(a: Poly64x1T, b: Poly64x1T): Uint64x1T =
    run {
        val c: Poly64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: I64x1 = Simd<Long>(listOf(0L))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<I64x1, Poly64x1T>(d, UnresolvedTypeLayout<I64x1>(), UnresolvedTypeLayout<Poly64x1T>()); Uint64x1T() }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqP64)
 */
public fun vtstqP64(a: Poly64x2T, b: Poly64x2T): Uint64x2T =
    run {
        val c: Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: I64x2 = Simd<Long>(listOf(0L, 0L))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<I64x2, Poly64x2T>(d, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<Poly64x2T>()); Uint64x2T() }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstU64)
 */
public fun vtstU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T =
    run {
        val c: Uint64x1T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: U64x1 = Simd<ULong>(listOf(0uL))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<U64x1, Uint64x1T>(d, UnresolvedTypeLayout<U64x1>(), UnresolvedTypeLayout<Uint64x1T>()); Uint64x1T() }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqU64)
 */
public fun vtstqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T =
    run {
        val c: Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }
        val d: U64x2 = Simd<ULong>(listOf(0uL, 0uL))
        run { val simdArg0 = c; val simdArg1 = transmuteWithLayouts<U64x2, Uint64x2T>(d, UnresolvedTypeLayout<U64x2>(), UnresolvedTypeLayout<Uint64x2T>()); Uint64x2T() }
    }

/**
 * Compare bitwise test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstdS64)
 */
public fun vtstdS64(a: Long, b: Long): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vtstS64(transmuteWithLayouts<Long, Int64x1T>(a, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>()), transmuteWithLayouts<Long, Int64x1T>(b, PrimitiveLayouts.I64, UnresolvedTypeLayout<Int64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Compare bitwise test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstdU64)
 */
public fun vtstdU64(a: ULong, b: ULong): ULong = transmuteWithLayouts<Uint64x1T, ULong>(vtstU64(transmuteWithLayouts<ULong, Uint64x1T>(a, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>()), transmuteWithLayouts<ULong, Uint64x1T>(b, PrimitiveLayouts.U64, UnresolvedTypeLayout<Uint64x1T>())), UnresolvedTypeLayout<Uint64x1T>(), PrimitiveLayouts.U64)

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddS8)
 */
public fun vuqaddS8(a: Int8x8T, b: Uint8x8T): Int8x8T {
    fun externVuqaddS8(a1: Int8x8T, b1: Uint8x8T): Int8x8T = Int8x8T()
    return externVuqaddS8(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddqS8)
 */
public fun vuqaddqS8(a: Int8x16T, b: Uint8x16T): Int8x16T {
    fun externVuqaddqS8(a1: Int8x16T, b1: Uint8x16T): Int8x16T = Int8x16T()
    return externVuqaddqS8(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddS16)
 */
public fun vuqaddS16(a: Int16x4T, b: Uint16x4T): Int16x4T {
    fun externVuqaddS16(a1: Int16x4T, b1: Uint16x4T): Int16x4T = Int16x4T()
    return externVuqaddS16(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddqS16)
 */
public fun vuqaddqS16(a: Int16x8T, b: Uint16x8T): Int16x8T {
    fun externVuqaddqS16(a1: Int16x8T, b1: Uint16x8T): Int16x8T = Int16x8T()
    return externVuqaddqS16(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddS32)
 */
public fun vuqaddS32(a: Int32x2T, b: Uint32x2T): Int32x2T {
    fun externVuqaddS32(a1: Int32x2T, b1: Uint32x2T): Int32x2T = Int32x2T()
    return externVuqaddS32(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddqS32)
 */
public fun vuqaddqS32(a: Int32x4T, b: Uint32x4T): Int32x4T {
    fun externVuqaddqS32(a1: Int32x4T, b1: Uint32x4T): Int32x4T = Int32x4T()
    return externVuqaddqS32(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddS64)
 */
public fun vuqaddS64(a: Int64x1T, b: Uint64x1T): Int64x1T {
    fun externVuqaddS64(a1: Int64x1T, b1: Uint64x1T): Int64x1T = Int64x1T()
    return externVuqaddS64(a, b)
}

/**
 * Signed saturating Accumulate of Unsigned value.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddqS64)
 */
public fun vuqaddqS64(a: Int64x2T, b: Uint64x2T): Int64x2T {
    fun externVuqaddqS64(a1: Int64x2T, b1: Uint64x2T): Int64x2T = Int64x2T()
    return externVuqaddqS64(a, b)
}

/**
 * Signed saturating accumulate of unsigned value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddbS8)
 */
public fun vuqaddbS8(a: Byte, b: UByte): Byte = vgetLaneS8(vuqaddS8(vdupNS8(a), vdupNU8(b)), 0)

/**
 * Signed saturating accumulate of unsigned value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddhS16)
 */
public fun vuqaddhS16(a: Short, b: UShort): Short = vgetLaneS16(vuqaddS16(vdupNS16(a), vdupNU16(b)), 0)

/**
 * Signed saturating accumulate of unsigned value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqadddS64)
 */
public fun vuqadddS64(a: Long, b: ULong): Long {
    fun externVuqadddS64(a1: Long, b1: ULong): Long = 0L
    return externVuqadddS64(a, b)
}

/**
 * Signed saturating accumulate of unsigned value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuqaddsS32)
 */
public fun vuqaddsS32(a: Int, b: UInt): Int {
    fun externVuqaddsS32(a1: Int, b1: UInt): Int = 0
    return externVuqaddsS32(a, b)
}

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1F16)
 */
public fun vuzp1F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Float16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qF16)
 */
public fun vuzp1qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Float16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1F32)
 */
public fun vuzp1F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qF64)
 */
public fun vuzp1qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1S32)
 */
public fun vuzp1S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qS64)
 */
public fun vuzp1qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1U32)
 */
public fun vuzp1U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qU64)
 */
public fun vuzp1qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qP64)
 */
public fun vuzp1qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Poly64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qF32)
 */
public fun vuzp1qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Float32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1S8)
 */
public fun vuzp1S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Int8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qS8)
 */
public fun vuzp1qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u, 16u, 18u, 20u, 22u, 24u, 26u, 28u, 30u)); Int8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1S16)
 */
public fun vuzp1S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Int16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qS16)
 */
public fun vuzp1qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Int16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qS32)
 */
public fun vuzp1qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Int32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1U8)
 */
public fun vuzp1U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Uint8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qU8)
 */
public fun vuzp1qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u, 16u, 18u, 20u, 22u, 24u, 26u, 28u, 30u)); Uint8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1U16)
 */
public fun vuzp1U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Uint16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qU16)
 */
public fun vuzp1qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Uint16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qU32)
 */
public fun vuzp1qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Uint32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1P8)
 */
public fun vuzp1P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Poly8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qP8)
 */
public fun vuzp1qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u, 16u, 18u, 20u, 22u, 24u, 26u, 28u, 30u)); Poly8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1P16)
 */
public fun vuzp1P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u)); Poly16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp1qP16)
 */
public fun vuzp1qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u, 4u, 6u, 8u, 10u, 12u, 14u)); Poly16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2F16)
 */
public fun vuzp2F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Float16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qF16)
 */
public fun vuzp2qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Float16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2F32)
 */
public fun vuzp2F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qF64)
 */
public fun vuzp2qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2S32)
 */
public fun vuzp2S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qS64)
 */
public fun vuzp2qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2U32)
 */
public fun vuzp2U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint32x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qU64)
 */
public fun vuzp2qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qP64)
 */
public fun vuzp2qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Poly64x2T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qF32)
 */
public fun vuzp2qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Float32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2S8)
 */
public fun vuzp2S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Int8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qS8)
 */
public fun vuzp2qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u, 17u, 19u, 21u, 23u, 25u, 27u, 29u, 31u)); Int8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2S16)
 */
public fun vuzp2S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Int16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qS16)
 */
public fun vuzp2qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Int16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qS32)
 */
public fun vuzp2qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Int32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2U8)
 */
public fun vuzp2U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Uint8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qU8)
 */
public fun vuzp2qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u, 17u, 19u, 21u, 23u, 25u, 27u, 29u, 31u)); Uint8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2U16)
 */
public fun vuzp2U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Uint16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qU16)
 */
public fun vuzp2qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Uint16x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qU32)
 */
public fun vuzp2qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Uint32x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2P8)
 */
public fun vuzp2P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Poly8x8T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qP8)
 */
public fun vuzp2qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u, 17u, 19u, 21u, 23u, 25u, 27u, 29u, 31u)); Poly8x16T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2P16)
 */
public fun vuzp2P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u)); Poly16x4T() }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzp2qP16)
 */
public fun vuzp2qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u, 5u, 7u, 9u, 11u, 13u, 15u)); Poly16x8T() }

/**
 * Exclusive OR and rotate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vxarqU64)
 */
public fun vxarqU64(a: Uint64x2T, b: Uint64x2T, imm6: Int): Uint64x2T {
    fun externVxarqU64(a1: Uint64x2T, b1: Uint64x2T, n: Long): Uint64x2T = Uint64x2T()
    run {
        check(0 <= imm6 && imm6 < (1 shl 6)) { "IMM6 doesn't fit in 6 bits (unsigned)" }
    }
    return externVxarqU64(a, b, imm6.toLong())
}

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1F16)
 */
public fun vzip1F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Float16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qF16)
 */
public fun vzip1qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Float16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1F32)
 */
public fun vzip1F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qF32)
 */
public fun vzip1qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Float32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qF64)
 */
public fun vzip1qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Float64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1S8)
 */
public fun vzip1S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Int8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qS8)
 */
public fun vzip1qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 1u, 17u, 2u, 18u, 3u, 19u, 4u, 20u, 5u, 21u, 6u, 22u, 7u, 23u)); Int8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1S16)
 */
public fun vzip1S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Int16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qS16)
 */
public fun vzip1qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Int16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1S32)
 */
public fun vzip1S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qS32)
 */
public fun vzip1qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Int32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qS64)
 */
public fun vzip1qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Int64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1U8)
 */
public fun vzip1U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Uint8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qU8)
 */
public fun vzip1qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 1u, 17u, 2u, 18u, 3u, 19u, 4u, 20u, 5u, 21u, 6u, 22u, 7u, 23u)); Uint8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1U16)
 */
public fun vzip1U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Uint16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qU16)
 */
public fun vzip1qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Uint16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1U32)
 */
public fun vzip1U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qU32)
 */
public fun vzip1qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Uint32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qU64)
 */
public fun vzip1qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Uint64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1P8)
 */
public fun vzip1P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Poly8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qP8)
 */
public fun vzip1qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 16u, 1u, 17u, 2u, 18u, 3u, 19u, 4u, 20u, 5u, 21u, 6u, 22u, 7u, 23u)); Poly8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1P16)
 */
public fun vzip1P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 4u, 1u, 5u)); Poly16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qP16)
 */
public fun vzip1qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 8u, 1u, 9u, 2u, 10u, 3u, 11u)); Poly16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip1qP64)
 */
public fun vzip1qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 2u)); Poly64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2F16)
 */
public fun vzip2F16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Float16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qF16)
 */
public fun vzip2qF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Float16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2F32)
 */
public fun vzip2F32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qF32)
 */
public fun vzip2qF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Float32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qF64)
 */
public fun vzip2qF64(a: Float64x2T, b: Float64x2T): Float64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Float64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2S8)
 */
public fun vzip2S8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Int8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qS8)
 */
public fun vzip2qS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 24u, 9u, 25u, 10u, 26u, 11u, 27u, 12u, 28u, 13u, 29u, 14u, 30u, 15u, 31u)); Int8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2S16)
 */
public fun vzip2S16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Int16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qS16)
 */
public fun vzip2qS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Int16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2S32)
 */
public fun vzip2S32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qS32)
 */
public fun vzip2qS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Int32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qS64)
 */
public fun vzip2qS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Int64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2U8)
 */
public fun vzip2U8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Uint8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qU8)
 */
public fun vzip2qU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 24u, 9u, 25u, 10u, 26u, 11u, 27u, 12u, 28u, 13u, 29u, 14u, 30u, 15u, 31u)); Uint8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2U16)
 */
public fun vzip2U16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Uint16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qU16)
 */
public fun vzip2qU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Uint16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2U32)
 */
public fun vzip2U32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint32x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qU32)
 */
public fun vzip2qU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Uint32x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qU64)
 */
public fun vzip2qU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Uint64x2T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2P8)
 */
public fun vzip2P8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Poly8x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qP8)
 */
public fun vzip2qP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 24u, 9u, 25u, 10u, 26u, 11u, 27u, 12u, 28u, 13u, 29u, 14u, 30u, 15u, 31u)); Poly8x16T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2P16)
 */
public fun vzip2P16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 6u, 3u, 7u)); Poly16x4T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qP16)
 */
public fun vzip2qP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 12u, 5u, 13u, 6u, 14u, 7u, 15u)); Poly16x8T() }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzip2qP64)
 */
public fun vzip2qP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 3u)); Poly64x2T() }
