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

JNIEXPORT jint JNICALL KM_JNI_FN(kmCtpopU8)(JNIEnv* env, jclass clazz, jbyte value) {
    (void)env; (void)clazz;
    return (jint)kmCtpopU8((uint8_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtpopU16)(JNIEnv* env, jclass clazz, jshort value) {
    (void)env; (void)clazz;
    return (jint)kmCtpopU16((uint16_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtpopU32)(JNIEnv* env, jclass clazz, jint value) {
    (void)env; (void)clazz;
    return (jint)kmCtpopU32((uint32_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtpopU128)(JNIEnv* env, jclass clazz, jlong lo, jlong hi) {
    (void)env; (void)clazz;
    return (jint)kmCtpopU128((uint64_t)lo, (uint64_t)hi);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtlzU8)(JNIEnv* env, jclass clazz, jbyte value) {
    (void)env; (void)clazz;
    return (jint)kmCtlzU8((uint8_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtlzU16)(JNIEnv* env, jclass clazz, jshort value) {
    (void)env; (void)clazz;
    return (jint)kmCtlzU16((uint16_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtlzU32)(JNIEnv* env, jclass clazz, jint value) {
    (void)env; (void)clazz;
    return (jint)kmCtlzU32((uint32_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCtlzU128)(JNIEnv* env, jclass clazz, jlong lo, jlong hi) {
    (void)env; (void)clazz;
    return (jint)kmCtlzU128((uint64_t)lo, (uint64_t)hi);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCttzU8)(JNIEnv* env, jclass clazz, jbyte value) {
    (void)env; (void)clazz;
    return (jint)kmCttzU8((uint8_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCttzU16)(JNIEnv* env, jclass clazz, jshort value) {
    (void)env; (void)clazz;
    return (jint)kmCttzU16((uint16_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCttzU32)(JNIEnv* env, jclass clazz, jint value) {
    (void)env; (void)clazz;
    return (jint)kmCttzU32((uint32_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmCttzU128)(JNIEnv* env, jclass clazz, jlong lo, jlong hi) {
    (void)env; (void)clazz;
    return (jint)kmCttzU128((uint64_t)lo, (uint64_t)hi);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmBswapU128Out)(JNIEnv* env, jclass clazz, jlong lo, jlong hi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmBswapU128Out((uint64_t)lo, (uint64_t)hi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmBitreverseU8)(JNIEnv* env, jclass clazz, jbyte value) {
    (void)env; (void)clazz;
    return (jbyte)kmBitreverseU8((uint8_t)value);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmBitreverseU16)(JNIEnv* env, jclass clazz, jshort value) {
    (void)env; (void)clazz;
    return (jshort)kmBitreverseU16((uint16_t)value);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmBitreverseU32)(JNIEnv* env, jclass clazz, jint value) {
    (void)env; (void)clazz;
    return (jint)kmBitreverseU32((uint32_t)value);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmBitreverseU128Out)(JNIEnv* env, jclass clazz, jlong lo, jlong hi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmBitreverseU128Out((uint64_t)lo, (uint64_t)hi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmRotateLeftU8)(JNIEnv* env, jclass clazz, jbyte value, jint n) {
    (void)env; (void)clazz;
    return (jbyte)kmRotateLeftU8((uint8_t)value, (uint32_t)n);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmRotateLeftU16)(JNIEnv* env, jclass clazz, jshort value, jint n) {
    (void)env; (void)clazz;
    return (jshort)kmRotateLeftU16((uint16_t)value, (uint32_t)n);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmRotateLeftU32)(JNIEnv* env, jclass clazz, jint value, jint n) {
    (void)env; (void)clazz;
    return (jint)kmRotateLeftU32((uint32_t)value, (uint32_t)n);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmRotateLeftU64)(JNIEnv* env, jclass clazz, jlong value, jint n) {
    (void)env; (void)clazz;
    return (jlong)kmRotateLeftU64((uint64_t)value, (uint32_t)n);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmRotateLeftU128Out)(JNIEnv* env, jclass clazz, jlong lo, jlong hi, jint n, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmRotateLeftU128Out((uint64_t)lo, (uint64_t)hi, (uint32_t)n, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmRotateRightU8)(JNIEnv* env, jclass clazz, jbyte value, jint n) {
    (void)env; (void)clazz;
    return (jbyte)kmRotateRightU8((uint8_t)value, (uint32_t)n);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmRotateRightU16)(JNIEnv* env, jclass clazz, jshort value, jint n) {
    (void)env; (void)clazz;
    return (jshort)kmRotateRightU16((uint16_t)value, (uint32_t)n);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmRotateRightU32)(JNIEnv* env, jclass clazz, jint value, jint n) {
    (void)env; (void)clazz;
    return (jint)kmRotateRightU32((uint32_t)value, (uint32_t)n);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmRotateRightU64)(JNIEnv* env, jclass clazz, jlong value, jint n) {
    (void)env; (void)clazz;
    return (jlong)kmRotateRightU64((uint64_t)value, (uint32_t)n);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmRotateRightU128Out)(JNIEnv* env, jclass clazz, jlong lo, jlong hi, jint n, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmRotateRightU128Out((uint64_t)lo, (uint64_t)hi, (uint32_t)n, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowU8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowU8Packed((uint8_t)a, (uint8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowU16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowU16Packed((uint16_t)a, (uint16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowU32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowU32Packed((uint32_t)a, (uint32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAddWithOverflowU64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    uint64_t res = 0;
    uint8_t ovf = kmAddWithOverflowU64Out((uint64_t)a, (uint64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAddWithOverflowU128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmAddWithOverflowU128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowI8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowI8Packed((int8_t)a, (int8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowI16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowI16Packed((int16_t)a, (int16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmAddWithOverflowI32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmAddWithOverflowI32Packed((int32_t)a, (int32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAddWithOverflowI64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    int64_t res = 0;
    uint8_t ovf = kmAddWithOverflowI64Out((int64_t)a, (int64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmAddWithOverflowI128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmAddWithOverflowI128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowU8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowU8Packed((uint8_t)a, (uint8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowU16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowU16Packed((uint16_t)a, (uint16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowU32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowU32Packed((uint32_t)a, (uint32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSubWithOverflowU64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    uint64_t res = 0;
    uint8_t ovf = kmSubWithOverflowU64Out((uint64_t)a, (uint64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSubWithOverflowU128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmSubWithOverflowU128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowI8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowI8Packed((int8_t)a, (int8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowI16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowI16Packed((int16_t)a, (int16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSubWithOverflowI32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmSubWithOverflowI32Packed((int32_t)a, (int32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSubWithOverflowI64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    int64_t res = 0;
    uint8_t ovf = kmSubWithOverflowI64Out((int64_t)a, (int64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSubWithOverflowI128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmSubWithOverflowI128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowU8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowU8Packed((uint8_t)a, (uint8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowU16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowU16Packed((uint16_t)a, (uint16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowU32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowU32Packed((uint32_t)a, (uint32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmMulWithOverflowU64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    uint64_t res = 0;
    uint8_t ovf = kmMulWithOverflowU64Out((uint64_t)a, (uint64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmMulWithOverflowU128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmMulWithOverflowU128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowI8Packed)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowI8Packed((int8_t)a, (int8_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowI16Packed)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowI16Packed((int16_t)a, (int16_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmMulWithOverflowI32Packed)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jlong)kmMulWithOverflowI32Packed((int32_t)a, (int32_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmMulWithOverflowI64Out)(JNIEnv* env, jclass clazz, jlong a, jlong b, jlongArray outBuf) {
    (void)clazz;
    int64_t res = 0;
    uint8_t ovf = kmMulWithOverflowI64Out((int64_t)a, (int64_t)b, &res);
    jlong buf[2];
    buf[0] = (jlong)res;
    buf[1] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmMulWithOverflowI128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    uint8_t ovf = kmMulWithOverflowI128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[3];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    buf[2] = (jlong)(uint64_t)ovf;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 3, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmSaturatingAddU8)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jbyte)kmSaturatingAddU8((uint8_t)a, (uint8_t)b);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmSaturatingAddU16)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jshort)kmSaturatingAddU16((uint16_t)a, (uint16_t)b);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmSaturatingAddU32)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jint)kmSaturatingAddU32((uint32_t)a, (uint32_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSaturatingAddU64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmSaturatingAddU64((uint64_t)a, (uint64_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSaturatingAddU128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmSaturatingAddU128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmSaturatingAddI8)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jbyte)kmSaturatingAddI8((int8_t)a, (int8_t)b);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmSaturatingAddI16)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jshort)kmSaturatingAddI16((int16_t)a, (int16_t)b);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmSaturatingAddI32)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jint)kmSaturatingAddI32((int32_t)a, (int32_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSaturatingAddI64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmSaturatingAddI64((int64_t)a, (int64_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSaturatingAddI128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmSaturatingAddI128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmSaturatingSubU8)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jbyte)kmSaturatingSubU8((uint8_t)a, (uint8_t)b);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmSaturatingSubU16)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jshort)kmSaturatingSubU16((uint16_t)a, (uint16_t)b);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmSaturatingSubU32)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jint)kmSaturatingSubU32((uint32_t)a, (uint32_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSaturatingSubU64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmSaturatingSubU64((uint64_t)a, (uint64_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSaturatingSubU128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmSaturatingSubU128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jbyte JNICALL KM_JNI_FN(kmSaturatingSubI8)(JNIEnv* env, jclass clazz, jbyte a, jbyte b) {
    (void)env; (void)clazz;
    return (jbyte)kmSaturatingSubI8((int8_t)a, (int8_t)b);
}
JNIEXPORT jshort JNICALL KM_JNI_FN(kmSaturatingSubI16)(JNIEnv* env, jclass clazz, jshort a, jshort b) {
    (void)env; (void)clazz;
    return (jshort)kmSaturatingSubI16((int16_t)a, (int16_t)b);
}
JNIEXPORT jint JNICALL KM_JNI_FN(kmSaturatingSubI32)(JNIEnv* env, jclass clazz, jint a, jint b) {
    (void)env; (void)clazz;
    return (jint)kmSaturatingSubI32((int32_t)a, (int32_t)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmSaturatingSubI64)(JNIEnv* env, jclass clazz, jlong a, jlong b) {
    (void)env; (void)clazz;
    return (jlong)kmSaturatingSubI64((int64_t)a, (int64_t)b);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmSaturatingSubI128Out)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmSaturatingSubI128Out((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128AddOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmU128AddOut((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128SubOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmU128SubOut((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128MulOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmU128MulOut((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128DivRemOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t qLo = 0ULL, qHi = 0ULL, rLo = 0ULL, rHi = 0ULL;
    kmU128DivRemOut((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &qLo, &qHi, &rLo, &rHi);
    jlong buf[4];
    buf[0] = (jlong)qLo;
    buf[1] = (jlong)qHi;
    buf[2] = (jlong)rLo;
    buf[3] = (jlong)rHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 4, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmI128DivRemOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jlong bLo, jlong bHi, jlongArray outBuf) {
    (void)clazz;
    uint64_t qLo = 0ULL, qHi = 0ULL, rLo = 0ULL, rHi = 0ULL;
    kmI128DivRemOut((uint64_t)aLo, (uint64_t)aHi, (uint64_t)bLo, (uint64_t)bHi, &qLo, &qHi, &rLo, &rHi);
    jlong buf[4];
    buf[0] = (jlong)qLo;
    buf[1] = (jlong)qHi;
    buf[2] = (jlong)rLo;
    buf[3] = (jlong)rHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 4, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128ShlOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jint shift, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmU128ShlOut((uint64_t)aLo, (uint64_t)aHi, (uint32_t)shift, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmU128ShrOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jint shift, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmU128ShrOut((uint64_t)aLo, (uint64_t)aHi, (uint32_t)shift, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmI128ShrOut)(JNIEnv* env, jclass clazz, jlong aLo, jlong aHi, jint shift, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmI128ShrOut((uint64_t)aLo, (uint64_t)aHi, (uint32_t)shift, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmSinf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmSinf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmSinf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmSinf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmCosf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmCosf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmCosf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmCosf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmExpf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmExpf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmExpf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmExpf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmExp2f32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmExp2f32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmExp2f64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmExp2f64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmLogf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmLogf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmLogf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmLogf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmLog2f32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmLog2f32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmLog2f64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmLog2f64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmLog10f32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmLog10f32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmLog10f64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmLog10f64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmTruncf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmTruncf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmTruncf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmTruncf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmRoundf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmRoundf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmRoundf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmRoundf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmRoundTiesEvenf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmRoundTiesEvenf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmRoundTiesEvenf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmRoundTiesEvenf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmCeilf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmCeilf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmCeilf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmCeilf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmFloorf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmFloorf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmFloorf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmFloorf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmFabsf32)(JNIEnv* env, jclass clazz, jfloat x) {
    (void)env; (void)clazz;
    return (jfloat)kmFabsf32((float)x);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmFabsf64)(JNIEnv* env, jclass clazz, jdouble x) {
    (void)env; (void)clazz;
    return (jdouble)kmFabsf64((double)x);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmPowf32)(JNIEnv* env, jclass clazz, jfloat a, jfloat b) {
    (void)env; (void)clazz;
    return (jfloat)kmPowf32((float)a, (float)b);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmPowf64)(JNIEnv* env, jclass clazz, jdouble a, jdouble b) {
    (void)env; (void)clazz;
    return (jdouble)kmPowf64((double)a, (double)b);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmPowif32)(JNIEnv* env, jclass clazz, jfloat x, jint exp) {
    (void)env; (void)clazz;
    return (jfloat)kmPowif32((float)x, (int32_t)exp);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmPowif64)(JNIEnv* env, jclass clazz, jdouble x, jint exp) {
    (void)env; (void)clazz;
    return (jdouble)kmPowif64((double)x, (int32_t)exp);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmCopysignf32)(JNIEnv* env, jclass clazz, jfloat a, jfloat b) {
    (void)env; (void)clazz;
    return (jfloat)kmCopysignf32((float)a, (float)b);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmCopysignf64)(JNIEnv* env, jclass clazz, jdouble a, jdouble b) {
    (void)env; (void)clazz;
    return (jdouble)kmCopysignf64((double)a, (double)b);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmMinnumf32)(JNIEnv* env, jclass clazz, jfloat a, jfloat b) {
    (void)env; (void)clazz;
    return (jfloat)kmMinnumf32((float)a, (float)b);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmMinnumf64)(JNIEnv* env, jclass clazz, jdouble a, jdouble b) {
    (void)env; (void)clazz;
    return (jdouble)kmMinnumf64((double)a, (double)b);
}
JNIEXPORT jfloat JNICALL KM_JNI_FN(kmMaxnumf32)(JNIEnv* env, jclass clazz, jfloat a, jfloat b) {
    (void)env; (void)clazz;
    return (jfloat)kmMaxnumf32((float)a, (float)b);
}
JNIEXPORT jdouble JNICALL KM_JNI_FN(kmMaxnumf64)(JNIEnv* env, jclass clazz, jdouble a, jdouble b) {
    (void)env; (void)clazz;
    return (jdouble)kmMaxnumf64((double)a, (double)b);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmFloatToU64Unchecked)(JNIEnv* env, jclass clazz, jfloat v) {
    (void)env; (void)clazz;
    return (jlong)kmFloatToU64Unchecked((float)v);
}
JNIEXPORT jlong JNICALL KM_JNI_FN(kmDoubleToU64Unchecked)(JNIEnv* env, jclass clazz, jdouble v) {
    (void)env; (void)clazz;
    return (jlong)kmDoubleToU64Unchecked((double)v);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmFloatToU128UncheckedOut)(JNIEnv* env, jclass clazz, jfloat v, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmFloatToU128UncheckedOut((float)v, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
JNIEXPORT void JNICALL KM_JNI_FN(kmDoubleToU128UncheckedOut)(JNIEnv* env, jclass clazz, jdouble v, jlongArray outBuf) {
    (void)clazz;
    uint64_t outLo = 0ULL, outHi = 0ULL;
    kmDoubleToU128UncheckedOut((double)v, &outLo, &outHi);
    jlong buf[2];
    buf[0] = (jlong)outLo;
    buf[1] = (jlong)outHi;
    (*env)->SetLongArrayRegion(env, outBuf, 0, 2, buf);
}
