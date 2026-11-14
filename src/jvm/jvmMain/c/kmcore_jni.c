#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include <jni.h>
#if __has_include("km_core_intrinsics_inline.h")
#include "km_core_intrinsics_inline.h"
#else
#include "../../../native/interop/cinterop/km_core_intrinsics_inline.h"
#endif
#if __has_include("km_core_llvm_asm.h")
#include "km_core_llvm_asm.h"
#else
#include "../../../native/interop/cinterop/km_core_llvm_asm.h"
#endif

#define KM_JNI_FN(Name) Java_io_github_kotlinmania_rustlang_core_intrinsics_KmCoreJniBridge_##Name

JNIEXPORT jbyte JNICALL KM_JNI_FN(kmLoadU8)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jbyte)kmLoadU8((int64_t)addr);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmLoadU16)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jshort)kmLoadU16((int64_t)addr);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmLoadU32)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jint)kmLoadU32((int64_t)addr);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmLoadU64)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jlong)kmLoadU64((int64_t)addr);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmLoadF32)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jfloat)kmLoadF32((int64_t)addr);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmLoadF64)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jdouble)kmLoadF64((int64_t)addr);
}

JNIEXPORT void JNICALL KM_JNI_FN(kmStoreU8)(JNIEnv* env, jclass clazz, jlong addr, jbyte value) {
    (void)env; (void)clazz;
    kmStoreU8((int64_t)addr, (uint8_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmStoreU16)(JNIEnv* env, jclass clazz, jlong addr, jshort value) {
    (void)env; (void)clazz;
    kmStoreU16((int64_t)addr, (uint16_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmStoreU32)(JNIEnv* env, jclass clazz, jlong addr, jint value) {
    (void)env; (void)clazz;
    kmStoreU32((int64_t)addr, (uint32_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmStoreU64)(JNIEnv* env, jclass clazz, jlong addr, jlong value) {
    (void)env; (void)clazz;
    kmStoreU64((int64_t)addr, (uint64_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmStoreF32)(JNIEnv* env, jclass clazz, jlong addr, jfloat value) {
    (void)env; (void)clazz;
    kmStoreF32((int64_t)addr, (float)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmStoreF64)(JNIEnv* env, jclass clazz, jlong addr, jdouble value) {
    (void)env; (void)clazz;
    kmStoreF64((int64_t)addr, (double)value);
}

JNIEXPORT jbyte JNICALL KM_JNI_FN(kmVolatileLoadU8)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jbyte)kmVolatileLoadU8((int64_t)addr);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmVolatileLoadU16)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jshort)kmVolatileLoadU16((int64_t)addr);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmVolatileLoadU32)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jint)kmVolatileLoadU32((int64_t)addr);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmVolatileLoadU64)(JNIEnv* env, jclass clazz, jlong addr) {
    (void)env; (void)clazz;
    return (jlong)kmVolatileLoadU64((int64_t)addr);
}

JNIEXPORT void JNICALL KM_JNI_FN(kmVolatileStoreU8)(JNIEnv* env, jclass clazz, jlong addr, jbyte value) {
    (void)env; (void)clazz;
    kmVolatileStoreU8((int64_t)addr, (uint8_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmVolatileStoreU16)(JNIEnv* env, jclass clazz, jlong addr, jshort value) {
    (void)env; (void)clazz;
    kmVolatileStoreU16((int64_t)addr, (uint16_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmVolatileStoreU32)(JNIEnv* env, jclass clazz, jlong addr, jint value) {
    (void)env; (void)clazz;
    kmVolatileStoreU32((int64_t)addr, (uint32_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmVolatileStoreU64)(JNIEnv* env, jclass clazz, jlong addr, jlong value) {
    (void)env; (void)clazz;
    kmVolatileStoreU64((int64_t)addr, (uint64_t)value);
}

JNIEXPORT void JNICALL KM_JNI_FN(kmCopyNonoverlapping)(JNIEnv* env, jclass clazz, jlong dst, jlong src, jlong bytes) {
    (void)env; (void)clazz;
    kmCopyNonoverlapping((int64_t)dst, (int64_t)src, (int64_t)bytes);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmCopy)(JNIEnv* env, jclass clazz, jlong dst, jlong src, jlong bytes) {
    (void)env; (void)clazz;
    kmCopy((int64_t)dst, (int64_t)src, (int64_t)bytes);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmWriteBytes)(JNIEnv* env, jclass clazz, jlong dst, jbyte value, jlong bytes) {
    (void)env; (void)clazz;
    kmWriteBytes((int64_t)dst, (uint8_t)value, (int64_t)bytes);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCompareBytes)(JNIEnv* env, jclass clazz, jlong left, jlong right, jlong bytes) {
    (void)env; (void)clazz;
    return (jint)kmCompareBytes((int64_t)left, (int64_t)right, (int64_t)bytes);
}

JNIEXPORT jlong JNICALL KM_JNI_FN(kmAlloc)(JNIEnv* env, jclass clazz, jlong size, jlong align) {
    (void)env; (void)clazz;
    return (jlong)kmAlloc((int64_t)size, (int64_t)align);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAllocZeroed)(JNIEnv* env, jclass clazz, jlong size, jlong align) {
    (void)env; (void)clazz;
    return (jlong)kmAllocZeroed((int64_t)size, (int64_t)align);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmRealloc)(JNIEnv* env, jclass clazz, jlong ptr, jlong oldSize, jlong align, jlong newSize) {
    (void)env; (void)clazz;
    return (jlong)kmRealloc((int64_t)ptr, (int64_t)oldSize, (int64_t)align, (int64_t)newSize);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmDealloc)(JNIEnv* env, jclass clazz, jlong ptr, jlong size, jlong align) {
    (void)env; (void)clazz;
    kmDealloc((int64_t)ptr, (int64_t)size, (int64_t)align);
}

JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicFence)(JNIEnv* env, jclass clazz, jint ord) {
    (void)env; (void)clazz;
    kmAtomicFence((int32_t)ord);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicSignalFence)(JNIEnv* env, jclass clazz, jint ord) {
    (void)env; (void)clazz;
    kmAtomicSignalFence((int32_t)ord);
}

JNIEXPORT jbyte JNICALL KM_JNI_FN(kmAtomicLoadU8)(JNIEnv* env, jclass clazz, jlong addr, jint ord) {
    (void)env; (void)clazz;
    return (jbyte)kmAtomicLoadU8((int64_t)addr, (int32_t)ord);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmAtomicLoadU16)(JNIEnv* env, jclass clazz, jlong addr, jint ord) {
    (void)env; (void)clazz;
    return (jshort)kmAtomicLoadU16((int64_t)addr, (int32_t)ord);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmAtomicLoadU32)(JNIEnv* env, jclass clazz, jlong addr, jint ord) {
    (void)env; (void)clazz;
    return (jint)kmAtomicLoadU32((int64_t)addr, (int32_t)ord);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicLoadU64)(JNIEnv* env, jclass clazz, jlong addr, jint ord) {
    (void)env; (void)clazz;
    return (jlong)kmAtomicLoadU64((int64_t)addr, (int32_t)ord);
}

JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicStoreU8)(JNIEnv* env, jclass clazz, jlong addr, jbyte value, jint ord) {
    (void)env; (void)clazz;
    kmAtomicStoreU8((int64_t)addr, (uint8_t)value, (int32_t)ord);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicStoreU16)(JNIEnv* env, jclass clazz, jlong addr, jshort value, jint ord) {
    (void)env; (void)clazz;
    kmAtomicStoreU16((int64_t)addr, (uint16_t)value, (int32_t)ord);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicStoreU32)(JNIEnv* env, jclass clazz, jlong addr, jint value, jint ord) {
    (void)env; (void)clazz;
    kmAtomicStoreU32((int64_t)addr, (uint32_t)value, (int32_t)ord);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicStoreU64)(JNIEnv* env, jclass clazz, jlong addr, jlong value, jint ord) {
    (void)env; (void)clazz;
    kmAtomicStoreU64((int64_t)addr, (uint64_t)value, (int32_t)ord);
}

JNIEXPORT jbyte JNICALL KM_JNI_FN(kmAtomicXchgU8)(JNIEnv* env, jclass clazz, jlong addr, jbyte value, jint ord) {
    (void)env; (void)clazz;
    return (jbyte)kmAtomicXchgU8((int64_t)addr, (uint8_t)value, (int32_t)ord);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmAtomicXchgU16)(JNIEnv* env, jclass clazz, jlong addr, jshort value, jint ord) {
    (void)env; (void)clazz;
    return (jshort)kmAtomicXchgU16((int64_t)addr, (uint16_t)value, (int32_t)ord);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmAtomicXchgU32)(JNIEnv* env, jclass clazz, jlong addr, jint value, jint ord) {
    (void)env; (void)clazz;
    return (jint)kmAtomicXchgU32((int64_t)addr, (uint32_t)value, (int32_t)ord);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicXchgU64)(JNIEnv* env, jclass clazz, jlong addr, jlong value, jint ord) {
    (void)env; (void)clazz;
    return (jlong)kmAtomicXchgU64((int64_t)addr, (uint64_t)value, (int32_t)ord);
}

JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicCxchgU8Packed)(
    JNIEnv* env, jclass clazz, jlong addr, jbyte expected, jbyte desired, jint succ, jint fail
) {
    (void)env; (void)clazz;
    uint8_t ok = 0;
    uint8_t prev = kmAtomicCxchgU8((int64_t)addr, (uint8_t)expected, (uint8_t)desired, (int32_t)succ, (int32_t)fail, &ok);
    return ((jlong)(uint64_t)ok << 32) | (jlong)(uint64_t)prev;
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicCxchgU16Packed)(
    JNIEnv* env, jclass clazz, jlong addr, jshort expected, jshort desired, jint succ, jint fail
) {
    (void)env; (void)clazz;
    uint8_t ok = 0;
    uint16_t prev = kmAtomicCxchgU16((int64_t)addr, (uint16_t)expected, (uint16_t)desired, (int32_t)succ, (int32_t)fail, &ok);
    return ((jlong)(uint64_t)ok << 32) | (jlong)(uint64_t)prev;
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicCxchgU32Packed)(
    JNIEnv* env, jclass clazz, jlong addr, jint expected, jint desired, jint succ, jint fail
) {
    (void)env; (void)clazz;
    uint8_t ok = 0;
    uint32_t prev = kmAtomicCxchgU32((int64_t)addr, (uint32_t)expected, (uint32_t)desired, (int32_t)succ, (int32_t)fail, &ok);
    return ((jlong)(uint64_t)ok << 32) | (jlong)(uint64_t)prev;
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicCxchgU64Out)(
    JNIEnv* env, jclass clazz, jlong addr, jlong expected, jlong desired, jint succ, jint fail, jlongArray outBuf
) {
    (void)clazz;
    uint8_t ok = 0;
    uint64_t prev = kmAtomicCxchgU64((int64_t)addr, (uint64_t)expected, (uint64_t)desired, (int32_t)succ, (int32_t)fail, &ok);
    jlong buf[2];
    buf[0] = (jlong)prev;
    buf[1] = (jlong)(uint64_t)ok;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}

JNIEXPORT jbyte JNICALL KM_JNI_FN(kmAtomicRmwU8)(JNIEnv* env, jclass clazz, jlong addr, jbyte value, jint op, jint ord) {
    (void)env; (void)clazz;
    return (jbyte)kmAtomicRmwU8((int64_t)addr, (uint8_t)value, (int32_t)op, (int32_t)ord);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmAtomicRmwU16)(JNIEnv* env, jclass clazz, jlong addr, jshort value, jint op, jint ord) {
    (void)env; (void)clazz;
    return (jshort)kmAtomicRmwU16((int64_t)addr, (uint16_t)value, (int32_t)op, (int32_t)ord);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmAtomicRmwU32)(JNIEnv* env, jclass clazz, jlong addr, jint value, jint op, jint ord) {
    (void)env; (void)clazz;
    return (jint)kmAtomicRmwU32((int64_t)addr, (uint32_t)value, (int32_t)op, (int32_t)ord);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAtomicRmwU64)(JNIEnv* env, jclass clazz, jlong addr, jlong value, jint op, jint ord) {
    (void)env; (void)clazz;
    return (jlong)kmAtomicRmwU64((int64_t)addr, (uint64_t)value, (int32_t)op, (int32_t)ord);
}

JNIEXPORT jint JNICALL KM_JNI_FN(kmCtpopU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jint)kmCtpopU64((uint64_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtlzU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jint)kmCtlzU64((uint64_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCttzU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jint)kmCttzU64((uint64_t)value);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmBswapU16)(JNIEnv* env, jclass clazz, jshort value) {
    (void)env; (void)clazz;
    return (jshort)kmBswapU16((uint16_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmBswapU32)(JNIEnv* env, jclass clazz, jint value) {
    (void)env; (void)clazz;
    return (jint)kmBswapU32((uint32_t)value);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmBswapU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jlong)kmBswapU64((uint64_t)value);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmBitreverseU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jlong)kmBitreverseU64((uint64_t)value);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmFmaf32)(JNIEnv* env, jclass clazz, jfloat a, jfloat b, jfloat c) {
    (void)env; (void)clazz;
    return (jfloat)kmFmaf32((float)a, (float)b, (float)c);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmFmaf64)(JNIEnv* env, jclass clazz, jdouble a, jdouble b, jdouble c) {
    (void)env; (void)clazz;
    return (jdouble)kmFmaf64((double)a, (double)b, (double)c);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmSqrtf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmSqrtf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmSqrtf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmSqrtf64((double)x);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAbort)(JNIEnv* env, jclass clazz) {
    (void)env; (void)clazz;
    kmAbort();
}

JNIEXPORT void JNICALL KM_JNI_FN(kmSpinLoopHint)(JNIEnv* env, jclass clazz) {
    (void)env; (void)clazz;
    kmSpinLoopHint();
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmBlackBoxU64)(JNIEnv* env, jclass clazz, jlong value) {
    (void)env; (void)clazz;
    return (jlong)kmBlackBoxU64((uint64_t)value);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmUmulhU64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmUmulhU64((uint64_t)a, (uint64_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSmulhI64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmSmulhI64((int64_t)a, (int64_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAtomicCxchgU128Out)(
    JNIEnv* env, jclass clazz, jlong addr, jlong expLo, jlong expHi, jlong desLo, jlong desHi, jlongArray outBuf
) {
    (void)clazz;
    uint64_t prevLo = 0;
    uint64_t prevHi = 0;
    uint8_t ok = kmAtomicCxchgU128(
        (int64_t)addr,
        (uint64_t)expLo,
        (uint64_t)expHi,
        (uint64_t)desLo,
        (uint64_t)desHi,
        &prevLo,
        &prevHi
    );
    jlong buf[3];
    buf[0] = (jlong)prevLo;
    buf[1] = (jlong)prevHi;
    buf[2] = (jlong)(uint64_t)ok;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSimdFmaF32x4)(
    JNIEnv* env, jclass clazz, jlong dstAddr, jlong aAddr, jlong bAddr, jlong cAddr
) {
    (void)env; (void)clazz;
    kmSimdFmaF32x4((int64_t)dstAddr, (int64_t)aAddr, (int64_t)bAddr, (int64_t)cAddr);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmSimdDotF32)(
    JNIEnv* env, jclass clazz, jlong aAddr, jlong bAddr, jlong len
) {
    (void)env; (void)clazz;
    return (jfloat)kmSimdDotF32((int64_t)aAddr, (int64_t)bAddr, (int64_t)len);
}
