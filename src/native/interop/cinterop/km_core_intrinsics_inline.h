#ifndef KM_CORE_INTRINSICS_INLINE_H
#define KM_CORE_INTRINSICS_INLINE_H

#include <stdint.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#if !defined(__cplusplus)
#include <stdatomic.h>
#elif defined(_MSC_VER) && !defined(__clang__)
#include <atomic>
#endif

#if defined(_WIN32)
#include <malloc.h>
#endif

#if defined(__clang__) || defined(__GNUC__)
#define KM_ALWAYS_INLINE static inline __attribute__((always_inline))
#elif defined(_MSC_VER)
#define KM_ALWAYS_INLINE static __forceinline
#else
#define KM_ALWAYS_INLINE static inline
#endif

/* Aligned & unaligned scalar memory loads and stores */
KM_ALWAYS_INLINE uint8_t kmLoadU8(int64_t addr) {
    return *(const uint8_t*)(uintptr_t)addr;
}
KM_ALWAYS_INLINE uint16_t kmLoadU16(int64_t addr) {
    uint16_t v;
    memcpy(&v, (const void*)(uintptr_t)addr, 2);
    return v;
}
KM_ALWAYS_INLINE uint32_t kmLoadU32(int64_t addr) {
    uint32_t v;
    memcpy(&v, (const void*)(uintptr_t)addr, 4);
    return v;
}
KM_ALWAYS_INLINE uint64_t kmLoadU64(int64_t addr) {
    uint64_t v;
    memcpy(&v, (const void*)(uintptr_t)addr, 8);
    return v;
}
KM_ALWAYS_INLINE float kmLoadF32(int64_t addr) {
    float v;
    memcpy(&v, (const void*)(uintptr_t)addr, 4);
    return v;
}
KM_ALWAYS_INLINE double kmLoadF64(int64_t addr) {
    double v;
    memcpy(&v, (const void*)(uintptr_t)addr, 8);
    return v;
}

KM_ALWAYS_INLINE void kmStoreU8(int64_t addr, uint8_t val) {
    *(uint8_t*)(uintptr_t)addr = val;
}
KM_ALWAYS_INLINE void kmStoreU16(int64_t addr, uint16_t val) {
    memcpy((void*)(uintptr_t)addr, &val, 2);
}
KM_ALWAYS_INLINE void kmStoreU32(int64_t addr, uint32_t val) {
    memcpy((void*)(uintptr_t)addr, &val, 4);
}
KM_ALWAYS_INLINE void kmStoreU64(int64_t addr, uint64_t val) {
    memcpy((void*)(uintptr_t)addr, &val, 8);
}
KM_ALWAYS_INLINE void kmStoreF32(int64_t addr, float val) {
    memcpy((void*)(uintptr_t)addr, &val, 4);
}
KM_ALWAYS_INLINE void kmStoreF64(int64_t addr, double val) {
    memcpy((void*)(uintptr_t)addr, &val, 8);
}

/* Volatile loads and stores */
KM_ALWAYS_INLINE uint8_t kmVolatileLoadU8(int64_t addr) {
    return *(volatile const uint8_t*)(uintptr_t)addr;
}
KM_ALWAYS_INLINE uint16_t kmVolatileLoadU16(int64_t addr) {
    return *(volatile const uint16_t*)(uintptr_t)addr;
}
KM_ALWAYS_INLINE uint32_t kmVolatileLoadU32(int64_t addr) {
    return *(volatile const uint32_t*)(uintptr_t)addr;
}
KM_ALWAYS_INLINE uint64_t kmVolatileLoadU64(int64_t addr) {
    return *(volatile const uint64_t*)(uintptr_t)addr;
}

KM_ALWAYS_INLINE void kmVolatileStoreU8(int64_t addr, uint8_t val) {
    *(volatile uint8_t*)(uintptr_t)addr = val;
}
KM_ALWAYS_INLINE void kmVolatileStoreU16(int64_t addr, uint16_t val) {
    *(volatile uint16_t*)(uintptr_t)addr = val;
}
KM_ALWAYS_INLINE void kmVolatileStoreU32(int64_t addr, uint32_t val) {
    *(volatile uint32_t*)(uintptr_t)addr = val;
}
KM_ALWAYS_INLINE void kmVolatileStoreU64(int64_t addr, uint64_t val) {
    *(volatile uint64_t*)(uintptr_t)addr = val;
}

/* Bulk memory intrinsics */
KM_ALWAYS_INLINE void kmCopyNonoverlapping(int64_t dst, int64_t src, int64_t bytes) {
    if (bytes > 0) {
        memcpy((void*)(uintptr_t)dst, (const void*)(uintptr_t)src, (size_t)bytes);
    }
}
KM_ALWAYS_INLINE void kmCopy(int64_t dst, int64_t src, int64_t bytes) {
    if (bytes > 0) {
        memmove((void*)(uintptr_t)dst, (const void*)(uintptr_t)src, (size_t)bytes);
    }
}
KM_ALWAYS_INLINE void kmWriteBytes(int64_t dst, uint8_t val, int64_t bytes) {
    if (bytes > 0) {
        memset((void*)(uintptr_t)dst, (int)val, (size_t)bytes);
    }
}
KM_ALWAYS_INLINE int32_t kmCompareBytes(int64_t left, int64_t right, int64_t bytes) {
    if (bytes <= 0) return 0;
    return (int32_t)memcmp((const void*)(uintptr_t)left, (const void*)(uintptr_t)right, (size_t)bytes);
}

/* Aligned system allocator backing GlobalAlloc */
KM_ALWAYS_INLINE int64_t kmAlloc(int64_t size, int64_t align) {
    size_t sz = size <= 0 ? 1 : (size_t)size;
    size_t al = align <= 1 ? 1 : (size_t)align;
#if defined(_WIN32)
    return (int64_t)(uintptr_t)_aligned_malloc(sz, al < sizeof(void*) ? sizeof(void*) : al);
#else
    if (al <= sizeof(void*) * 2) {
        return (int64_t)(uintptr_t)malloc(sz);
    }
    void* out = NULL;
    if (posix_memalign(&out, al, sz) != 0) return 0;
    return (int64_t)(uintptr_t)out;
#endif
}

KM_ALWAYS_INLINE int64_t kmAllocZeroed(int64_t size, int64_t align) {
    size_t sz = size <= 0 ? 1 : (size_t)size;
    size_t al = align <= 1 ? 1 : (size_t)align;
#if defined(_WIN32)
    void* ptr = _aligned_malloc(sz, al < sizeof(void*) ? sizeof(void*) : al);
    if (ptr != NULL) memset(ptr, 0, sz);
    return (int64_t)(uintptr_t)ptr;
#else
    if (al <= sizeof(void*) * 2) {
        return (int64_t)(uintptr_t)calloc(1, sz);
    }
    void* out = NULL;
    if (posix_memalign(&out, al, sz) != 0) return 0;
    memset(out, 0, sz);
    return (int64_t)(uintptr_t)out;
#endif
}

KM_ALWAYS_INLINE int64_t kmRealloc(int64_t ptr, int64_t oldSize, int64_t align, int64_t newSize) {
    if (ptr == 0) return kmAlloc(newSize, align);
    size_t nsz = newSize <= 0 ? 1 : (size_t)newSize;
    size_t al = align <= 1 ? 1 : (size_t)align;
#if defined(_WIN32)
    (void)oldSize;
    return (int64_t)(uintptr_t)_aligned_realloc((void*)(uintptr_t)ptr, nsz, al < sizeof(void*) ? sizeof(void*) : al);
#else
    if (al <= sizeof(void*) * 2) {
        return (int64_t)(uintptr_t)realloc((void*)(uintptr_t)ptr, nsz);
    }
    int64_t fresh = kmAlloc(newSize, align);
    if (fresh != 0) {
        size_t copyLen = (size_t)oldSize < nsz ? (size_t)oldSize : nsz;
        if (copyLen > 0) memcpy((void*)(uintptr_t)fresh, (const void*)(uintptr_t)ptr, copyLen);
        free((void*)(uintptr_t)ptr);
    }
    return fresh;
#endif
}

KM_ALWAYS_INLINE void kmDealloc(int64_t ptr, int64_t size, int64_t align) {
    (void)size;
    (void)align;
    if (ptr == 0) return;
#if defined(_WIN32)
    _aligned_free((void*)(uintptr_t)ptr);
#else
    free((void*)(uintptr_t)ptr);
#endif
}

/* C11 / Clang-GCC __atomic_* operations with explicit Rust ordering constants:
 * 0 = Relaxed, 1 = Release, 2 = Acquire, 3 = AcqRel, 4 = SeqCst
 */
KM_ALWAYS_INLINE void kmAtomicFence(int32_t ord) {
#if defined(__clang__) || defined(__GNUC__)
    switch (ord) {
        case 0: __atomic_thread_fence(__ATOMIC_RELAXED); break;
        case 1: __atomic_thread_fence(__ATOMIC_RELEASE); break;
        case 2: __atomic_thread_fence(__ATOMIC_ACQUIRE); break;
        case 3: __atomic_thread_fence(__ATOMIC_ACQ_REL); break;
        default: __atomic_thread_fence(__ATOMIC_SEQ_CST); break;
    }
#elif !defined(__cplusplus)
    switch (ord) {
        case 0: atomic_thread_fence(memory_order_relaxed); break;
        case 1: atomic_thread_fence(memory_order_release); break;
        case 2: atomic_thread_fence(memory_order_acquire); break;
        case 3: atomic_thread_fence(memory_order_acq_rel); break;
        default: atomic_thread_fence(memory_order_seq_cst); break;
    }
#endif
}

KM_ALWAYS_INLINE void kmAtomicSignalFence(int32_t ord) {
#if defined(__clang__) || defined(__GNUC__)
    switch (ord) {
        case 0: __atomic_signal_fence(__ATOMIC_RELAXED); break;
        case 1: __atomic_signal_fence(__ATOMIC_RELEASE); break;
        case 2: __atomic_signal_fence(__ATOMIC_ACQUIRE); break;
        case 3: __atomic_signal_fence(__ATOMIC_ACQ_REL); break;
        default: __atomic_signal_fence(__ATOMIC_SEQ_CST); break;
    }
#elif !defined(__cplusplus)
    switch (ord) {
        case 0: atomic_signal_fence(memory_order_relaxed); break;
        case 1: atomic_signal_fence(memory_order_release); break;
        case 2: atomic_signal_fence(memory_order_acquire); break;
        case 3: atomic_signal_fence(memory_order_acq_rel); break;
        default: atomic_signal_fence(memory_order_seq_cst); break;
    }
#endif
}

#if defined(__cplusplus) && defined(_MSC_VER) && !defined(__clang__)
#define KM_DEF_ATOMIC_LOAD_STORE(Suffix, CType, SignedCType) \
KM_ALWAYS_INLINE CType kmAtomicLoad##Suffix(int64_t addr, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    std::atomic_ref<CType> ref(*p); \
    switch (ord) { \
        case 0: return ref.load(std::memory_order_relaxed); \
        case 2: return ref.load(std::memory_order_acquire); \
        default: return ref.load(std::memory_order_seq_cst); \
    } \
} \
KM_ALWAYS_INLINE void kmAtomicStore##Suffix(int64_t addr, CType val, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    std::atomic_ref<CType> ref(*p); \
    switch (ord) { \
        case 0: ref.store(val, std::memory_order_relaxed); break; \
        case 1: ref.store(val, std::memory_order_release); break; \
        default: ref.store(val, std::memory_order_seq_cst); break; \
    } \
} \
KM_ALWAYS_INLINE CType kmAtomicXchg##Suffix(int64_t addr, CType val, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    std::atomic_ref<CType> ref(*p); \
    switch (ord) { \
        case 0: return ref.exchange(val, std::memory_order_relaxed); \
        case 1: return ref.exchange(val, std::memory_order_release); \
        case 2: return ref.exchange(val, std::memory_order_acquire); \
        case 3: return ref.exchange(val, std::memory_order_acq_rel); \
        default: return ref.exchange(val, std::memory_order_seq_cst); \
    } \
} \
KM_ALWAYS_INLINE CType kmAtomicCxchg##Suffix(int64_t addr, CType expected, CType desired, int32_t succ, int32_t fail, uint8_t* okOut) { \
    CType* p = (CType*)(uintptr_t)addr; \
    std::atomic_ref<CType> ref(*p); \
    std::memory_order sm = succ == 0 ? std::memory_order_relaxed : (succ == 1 ? std::memory_order_release : (succ == 2 ? std::memory_order_acquire : (succ == 3 ? std::memory_order_acq_rel : std::memory_order_seq_cst))); \
    std::memory_order fm = fail == 0 ? std::memory_order_relaxed : (fail == 2 ? std::memory_order_acquire : std::memory_order_seq_cst); \
    uint8_t ok = ref.compare_exchange_strong(expected, desired, sm, fm) ? 1 : 0; \
    if (okOut != NULL) *okOut = ok; \
    return expected; \
} \
KM_ALWAYS_INLINE CType kmAtomicRmw##Suffix(int64_t addr, CType val, int32_t op, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    std::atomic_ref<CType> ref(*p); \
    std::memory_order model = ord == 0 ? std::memory_order_relaxed : (ord == 1 ? std::memory_order_release : (ord == 2 ? std::memory_order_acquire : (ord == 3 ? std::memory_order_acq_rel : std::memory_order_seq_cst))); \
    switch (op) { \
        case 1: return ref.fetch_add(val, model); \
        case 2: return ref.fetch_sub(val, model); \
        case 3: return ref.fetch_and(val, model); \
        case 5: return ref.fetch_or(val, model); \
        case 6: return ref.fetch_xor(val, model); \
        default: { \
            CType cur = ref.load(std::memory_order_relaxed); \
            for (;;) { \
                CType next = cur; \
                if (op == 4) next = (CType)(~(cur & val)); \
                else if (op == 7) next = ((SignedCType)cur > (SignedCType)val) ? cur : val; \
                else if (op == 8) next = ((SignedCType)cur < (SignedCType)val) ? cur : val; \
                else if (op == 9) next = (cur > val) ? cur : val; \
                else next = (cur < val) ? cur : val; \
                if (ref.compare_exchange_weak(cur, next, model, std::memory_order_relaxed)) return cur; \
            } \
        } \
    } \
}
#else
#define KM_DEF_ATOMIC_LOAD_STORE(Suffix, CType, SignedCType) \
KM_ALWAYS_INLINE CType kmAtomicLoad##Suffix(int64_t addr, int32_t ord) { \
    const CType* p = (const CType*)(uintptr_t)addr; \
    switch (ord) { \
        case 0: return __atomic_load_n(p, __ATOMIC_RELAXED); \
        case 2: return __atomic_load_n(p, __ATOMIC_ACQUIRE); \
        default: return __atomic_load_n(p, __ATOMIC_SEQ_CST); \
    } \
} \
KM_ALWAYS_INLINE void kmAtomicStore##Suffix(int64_t addr, CType val, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    switch (ord) { \
        case 0: __atomic_store_n(p, val, __ATOMIC_RELAXED); break; \
        case 1: __atomic_store_n(p, val, __ATOMIC_RELEASE); break; \
        default: __atomic_store_n(p, val, __ATOMIC_SEQ_CST); break; \
    } \
} \
KM_ALWAYS_INLINE CType kmAtomicXchg##Suffix(int64_t addr, CType val, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    switch (ord) { \
        case 0: return __atomic_exchange_n(p, val, __ATOMIC_RELAXED); \
        case 1: return __atomic_exchange_n(p, val, __ATOMIC_RELEASE); \
        case 2: return __atomic_exchange_n(p, val, __ATOMIC_ACQUIRE); \
        case 3: return __atomic_exchange_n(p, val, __ATOMIC_ACQ_REL); \
        default: return __atomic_exchange_n(p, val, __ATOMIC_SEQ_CST); \
    } \
} \
KM_ALWAYS_INLINE CType kmAtomicCxchg##Suffix(int64_t addr, CType expected, CType desired, int32_t succ, int32_t fail, uint8_t* okOut) { \
    CType* p = (CType*)(uintptr_t)addr; \
    int succModel = succ == 0 ? __ATOMIC_RELAXED : (succ == 1 ? __ATOMIC_RELEASE : (succ == 2 ? __ATOMIC_ACQUIRE : (succ == 3 ? __ATOMIC_ACQ_REL : __ATOMIC_SEQ_CST))); \
    int failModel = fail == 0 ? __ATOMIC_RELAXED : (fail == 2 ? __ATOMIC_ACQUIRE : __ATOMIC_SEQ_CST); \
    if (failModel > succModel) failModel = __ATOMIC_RELAXED; \
    uint8_t ok = __atomic_compare_exchange_n(p, &expected, desired, 0, succModel, failModel) ? 1 : 0; \
    if (okOut != NULL) *okOut = ok; \
    return expected; \
} \
KM_ALWAYS_INLINE CType kmAtomicRmw##Suffix(int64_t addr, CType val, int32_t op, int32_t ord) { \
    CType* p = (CType*)(uintptr_t)addr; \
    int model = ord == 0 ? __ATOMIC_RELAXED : (ord == 1 ? __ATOMIC_RELEASE : (ord == 2 ? __ATOMIC_ACQUIRE : (ord == 3 ? __ATOMIC_ACQ_REL : __ATOMIC_SEQ_CST))); \
    switch (op) { \
        case 1: return __atomic_fetch_add(p, val, model); \
        case 2: return __atomic_fetch_sub(p, val, model); \
        case 3: return __atomic_fetch_and(p, val, model); \
        case 4: return __atomic_fetch_nand(p, val, model); \
        case 5: return __atomic_fetch_or(p, val, model); \
        case 6: return __atomic_fetch_xor(p, val, model); \
        default: { \
            CType cur = __atomic_load_n(p, __ATOMIC_RELAXED); \
            for (;;) { \
                CType next = cur; \
                if (op == 7) next = ((SignedCType)cur > (SignedCType)val) ? cur : val; \
                else if (op == 8) next = ((SignedCType)cur < (SignedCType)val) ? cur : val; \
                else if (op == 9) next = (cur > val) ? cur : val; \
                else next = (cur < val) ? cur : val; \
                if (__atomic_compare_exchange_n(p, &cur, next, 1, model, __ATOMIC_RELAXED)) return cur; \
            } \
        } \
    } \
}
#endif

KM_DEF_ATOMIC_LOAD_STORE(U8, uint8_t, int8_t)
KM_DEF_ATOMIC_LOAD_STORE(U16, uint16_t, int16_t)
KM_DEF_ATOMIC_LOAD_STORE(U32, uint32_t, int32_t)
KM_DEF_ATOMIC_LOAD_STORE(U64, uint64_t, int64_t)

/* Bit, math, and trap compiler builtins */
KM_ALWAYS_INLINE uint32_t kmCtpopU64(uint64_t v) {
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_popcountll(v);
#else
    uint32_t c = 0;
    while (v) { v &= (v - 1); c++; }
    return c;
#endif
}

KM_ALWAYS_INLINE uint32_t kmCtlzU64(uint64_t v) {
    if (v == 0) return 64;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_clzll(v);
#else
    uint32_t n = 0;
    for (int i = 63; i >= 0 && ((v >> i) & 1ULL) == 0; i--) n++;
    return n;
#endif
}

KM_ALWAYS_INLINE uint32_t kmCttzU64(uint64_t v) {
    if (v == 0) return 64;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_ctzll(v);
#else
    uint32_t n = 0;
    while ((v & 1ULL) == 0) { v >>= 1; n++; }
    return n;
#endif
}

KM_ALWAYS_INLINE uint16_t kmBswapU16(uint16_t v) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_bswap16(v);
#else
    return (uint16_t)((v << 8) | (v >> 8));
#endif
}

KM_ALWAYS_INLINE uint32_t kmBswapU32(uint32_t v) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_bswap32(v);
#else
    return ((v & 0x000000FFu) << 24) | ((v & 0x0000FF00u) << 8) | ((v & 0x00FF0000u) >> 8) | ((v & 0xFF000000u) >> 24);
#endif
}

KM_ALWAYS_INLINE uint64_t kmBswapU64(uint64_t v) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_bswap64(v);
#else
    return ((uint64_t)kmBswapU32((uint32_t)v) << 32) | (uint64_t)kmBswapU32((uint32_t)(v >> 32));
#endif
}

KM_ALWAYS_INLINE uint64_t kmBitreverseU64(uint64_t v) {
#if defined(__clang__)
    return __builtin_bitreverse64(v);
#else
    v = ((v >> 1) & 0x5555555555555555ULL) | ((v & 0x5555555555555555ULL) << 1);
    v = ((v >> 2) & 0x3333333333333333ULL) | ((v & 0x3333333333333333ULL) << 2);
    v = ((v >> 4) & 0x0F0F0F0F0F0F0F0FULL) | ((v & 0x0F0F0F0F0F0F0F0FULL) << 4);
    return kmBswapU64(v);
#endif
}

KM_ALWAYS_INLINE float kmFmaf32(float a, float b, float c) { return fmaf(a, b, c); }
KM_ALWAYS_INLINE double kmFmaf64(double a, double b, double c) { return fma(a, b, c); }
KM_ALWAYS_INLINE float kmSqrtf32(float x) { return sqrtf(x); }
KM_ALWAYS_INLINE double kmSqrtf64(double x) { return sqrt(x); }

KM_ALWAYS_INLINE void kmAbort(void) {
#if defined(__clang__) || defined(__GNUC__)
    __builtin_trap();
#else
    abort();
#endif
}

/* Architecture-guarded SIMD f32x4 / f64x2 fused kernels for core::arch */
#if defined(__aarch64__)
#include <arm_neon.h>
KM_ALWAYS_INLINE void kmSimdFmaF32x4(int64_t dstAddr, int64_t aAddr, int64_t bAddr, int64_t cAddr) {
    float32x4_t a = vld1q_f32((const float*)(uintptr_t)aAddr);
    float32x4_t b = vld1q_f32((const float*)(uintptr_t)bAddr);
    float32x4_t c = vld1q_f32((const float*)(uintptr_t)cAddr);
    vst1q_f32((float*)(uintptr_t)dstAddr, vfmaq_f32(c, a, b));
}
KM_ALWAYS_INLINE float kmSimdDotF32(int64_t aAddr, int64_t bAddr, int64_t len) {
    const float* a = (const float*)(uintptr_t)aAddr;
    const float* b = (const float*)(uintptr_t)bAddr;
    float32x4_t acc = vdupq_n_f32(0.0f);
    int64_t i = 0;
    for (; i + 4 <= len; i += 4) {
        acc = vfmaq_f32(acc, vld1q_f32(a + i), vld1q_f32(b + i));
    }
    float sum = vaddvq_f32(acc);
    for (; i < len; i++) sum = fmaf(a[i], b[i], sum);
    return sum;
}
#elif defined(__x86_64__) || defined(_M_X64)
#include <immintrin.h>
KM_ALWAYS_INLINE void kmSimdFmaF32x4(int64_t dstAddr, int64_t aAddr, int64_t bAddr, int64_t cAddr) {
    __m128 a = _mm_loadu_ps((const float*)(uintptr_t)aAddr);
    __m128 b = _mm_loadu_ps((const float*)(uintptr_t)bAddr);
    __m128 c = _mm_loadu_ps((const float*)(uintptr_t)cAddr);
    _mm_storeu_ps((float*)(uintptr_t)dstAddr, _mm_add_ps(_mm_mul_ps(a, b), c));
}
KM_ALWAYS_INLINE float kmSimdDotF32(int64_t aAddr, int64_t bAddr, int64_t len) {
    const float* a = (const float*)(uintptr_t)aAddr;
    const float* b = (const float*)(uintptr_t)bAddr;
    __m128 acc = _mm_setzero_ps();
    int64_t i = 0;
    for (; i + 4 <= len; i += 4) {
        acc = _mm_add_ps(acc, _mm_mul_ps(_mm_loadu_ps(a + i), _mm_loadu_ps(b + i)));
    }
    float lanes[4];
    _mm_storeu_ps(lanes, acc);
    float sum = lanes[0] + lanes[1] + lanes[2] + lanes[3];
    for (; i < len; i++) sum = fmaf(a[i], b[i], sum);
    return sum;
}
#else
KM_ALWAYS_INLINE void kmSimdFmaF32x4(int64_t dstAddr, int64_t aAddr, int64_t bAddr, int64_t cAddr) {
    const float* a = (const float*)(uintptr_t)aAddr;
    const float* b = (const float*)(uintptr_t)bAddr;
    const float* c = (const float*)(uintptr_t)cAddr;
    float* dst = (float*)(uintptr_t)dstAddr;
    for (int i = 0; i < 4; i++) dst[i] = fmaf(a[i], b[i], c[i]);
}
KM_ALWAYS_INLINE float kmSimdDotF32(int64_t aAddr, int64_t bAddr, int64_t len) {
    const float* a = (const float*)(uintptr_t)aAddr;
    const float* b = (const float*)(uintptr_t)bAddr;
    float sum = 0.0f;
    for (int64_t i = 0; i < len; i++) sum = fmaf(a[i], b[i], sum);
    return sum;
}
#endif

/* Portable helpers for overflowing 32/64/128-bit and C-backed 128-bit arithmetic */
KM_ALWAYS_INLINE uint64_t kmPackOverflow32(uint32_t valueBits, uint8_t overflowed) {
    return (uint64_t)valueBits | ((uint64_t)(overflowed != 0u ? 1u : 0u) << 32);
}

KM_ALWAYS_INLINE uint64_t kmAddU64Carry(uint64_t a, uint64_t b, uint64_t* outSum) {
    uint64_t s = a + b;
    *outSum = s;
    return s < a ? 1ULL : 0ULL;
}

KM_ALWAYS_INLINE void kmMulU64Wide(uint64_t a, uint64_t b, uint64_t* outLo, uint64_t* outHi) {
#if defined(__SIZEOF_INT128__)
    __uint128_t prod = (__uint128_t)a * (__uint128_t)b;
    *outLo = (uint64_t)prod;
    *outHi = (uint64_t)(prod >> 64);
#else
    uint64_t aLo = a & 0xFFFFFFFFULL;
    uint64_t aHi = a >> 32;
    uint64_t bLo = b & 0xFFFFFFFFULL;
    uint64_t bHi = b >> 32;
    uint64_t p00 = aLo * bLo;
    uint64_t p01 = aLo * bHi;
    uint64_t p10 = aHi * bLo;
    uint64_t p11 = aHi * bHi;
    uint64_t mid = (p00 >> 32) + (p01 & 0xFFFFFFFFULL) + (p10 & 0xFFFFFFFFULL);
    *outLo = (p00 & 0xFFFFFFFFULL) | ((mid & 0xFFFFFFFFULL) << 32);
    *outHi = p11 + (p01 >> 32) + (p10 >> 32) + (mid >> 32);
#endif
}

KM_ALWAYS_INLINE void kmU128NegateRaw(uint64_t lo, uint64_t hi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t invLo = ~lo;
    uint64_t invHi = ~hi;
    uint64_t resLo = invLo + 1ULL;
    uint64_t carry = (resLo == 0ULL) ? 1ULL : 0ULL;
    *outLo = resLo;
    *outHi = invHi + carry;
}

KM_ALWAYS_INLINE void kmU128DivRemRaw(
    uint64_t nLo,
    uint64_t nHi,
    uint64_t dLo,
    uint64_t dHi,
    uint64_t* outQLo,
    uint64_t* outQHi,
    uint64_t* outRLo,
    uint64_t* outRHi
) {
    if (dLo == 0ULL && dHi == 0ULL) {
        *outQLo = 0ULL;
        *outQHi = 0ULL;
        *outRLo = 0ULL;
        *outRHi = 0ULL;
        return;
    }
#if defined(__SIZEOF_INT128__) && !defined(_MSC_VER)
    __uint128_t num = ((__uint128_t)nHi << 64) | (__uint128_t)nLo;
    __uint128_t den = ((__uint128_t)dHi << 64) | (__uint128_t)dLo;
    __uint128_t q = num / den;
    __uint128_t r = num % den;
    *outQLo = (uint64_t)q;
    *outQHi = (uint64_t)(q >> 64);
    *outRLo = (uint64_t)r;
    *outRHi = (uint64_t)(r >> 64);
#else
    uint64_t qLo = 0ULL, qHi = 0ULL, rLo = 0ULL, rHi = 0ULL;
    for (int bit = 127; bit >= 0; bit--) {
        uint64_t rCarry = rLo >> 63;
        rHi = (rHi << 1) | rCarry;
        uint64_t nBit = (bit >= 64) ? ((nHi >> (bit - 64)) & 1ULL) : ((nLo >> bit) & 1ULL);
        rLo = (rLo << 1) | nBit;
        if (rHi > dHi || (rHi == dHi && rLo >= dLo)) {
            uint64_t borrow = (rLo < dLo) ? 1ULL : 0ULL;
            rLo -= dLo;
            rHi -= (dHi + borrow);
            if (bit >= 64) {
                qHi |= (1ULL << (bit - 64));
            } else {
                qLo |= (1ULL << bit);
            }
        }
    }
    *outQLo = qLo;
    *outQHi = qHi;
    *outRLo = rLo;
    *outRHi = rHi;
#endif
}

KM_ALWAYS_INLINE void kmI128DivRemRaw(
    uint64_t nLo,
    uint64_t nHi,
    uint64_t dLo,
    uint64_t dHi,
    uint64_t* outQLo,
    uint64_t* outQHi,
    uint64_t* outRLo,
    uint64_t* outRHi
) {
    if (dLo == 0ULL && dHi == 0ULL) {
        *outQLo = 0ULL;
        *outQHi = 0ULL;
        *outRLo = 0ULL;
        *outRHi = 0ULL;
        return;
    }
    if (nHi == 0x8000000000000000ULL && nLo == 0ULL && dHi == 0xFFFFFFFFFFFFFFFFULL && dLo == 0xFFFFFFFFFFFFFFFFULL) {
        *outQLo = 0ULL;
        *outQHi = 0x8000000000000000ULL;
        *outRLo = 0ULL;
        *outRHi = 0ULL;
        return;
    }
    int nNeg = (nHi >> 63) != 0ULL;
    int dNeg = (dHi >> 63) != 0ULL;
    uint64_t aLo = nLo, aHi = nHi;
    uint64_t bLo = dLo, bHi = dHi;
    if (nNeg) kmU128NegateRaw(nLo, nHi, &aLo, &aHi);
    if (dNeg) kmU128NegateRaw(dLo, dHi, &bLo, &bHi);
    uint64_t uqLo = 0ULL, uqHi = 0ULL, urLo = 0ULL, urHi = 0ULL;
    kmU128DivRemRaw(aLo, aHi, bLo, bHi, &uqLo, &uqHi, &urLo, &urHi);
    if (nNeg != dNeg) {
        kmU128NegateRaw(uqLo, uqHi, outQLo, outQHi);
    } else {
        *outQLo = uqLo;
        *outQHi = uqHi;
    }
    if (nNeg) {
        kmU128NegateRaw(urLo, urHi, outRLo, outRHi);
    } else {
        *outRLo = urLo;
        *outRHi = urHi;
    }
}
/* Width-exact bit intrinsics */
KM_ALWAYS_INLINE uint32_t kmCtpopU8(uint8_t v) {
    return (uint32_t)kmCtpopU64((uint64_t)v);
}
KM_ALWAYS_INLINE uint32_t kmCtpopU16(uint16_t v) {
    return (uint32_t)kmCtpopU64((uint64_t)v);
}
KM_ALWAYS_INLINE uint32_t kmCtpopU32(uint32_t v) {
    return (uint32_t)kmCtpopU64((uint64_t)v);
}
KM_ALWAYS_INLINE uint32_t kmCtpopU128(uint64_t lo, uint64_t hi) {
    return kmCtpopU64(lo) + kmCtpopU64(hi);
}
KM_ALWAYS_INLINE uint32_t kmCtlzU8(uint8_t v) {
    if (v == 0u) return 8u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_clz((unsigned int)v) - 24u;
#else
    return kmCtlzU64((uint64_t)v) - 56u;
#endif
}
KM_ALWAYS_INLINE uint32_t kmCtlzU16(uint16_t v) {
    if (v == 0u) return 16u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_clz((unsigned int)v) - 16u;
#else
    return kmCtlzU64((uint64_t)v) - 48u;
#endif
}
KM_ALWAYS_INLINE uint32_t kmCtlzU32(uint32_t v) {
    if (v == 0u) return 32u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_clz(v);
#else
    return kmCtlzU64((uint64_t)v) - 32u;
#endif
}
KM_ALWAYS_INLINE uint32_t kmCtlzU128(uint64_t lo, uint64_t hi) {
    return hi != 0ULL ? kmCtlzU64(hi) : (64u + kmCtlzU64(lo));
}
KM_ALWAYS_INLINE uint32_t kmCttzU8(uint8_t v) {
    if (v == 0u) return 8u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_ctz((unsigned int)v);
#else
    return kmCttzU64((uint64_t)v);
#endif
}
KM_ALWAYS_INLINE uint32_t kmCttzU16(uint16_t v) {
    if (v == 0u) return 16u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_ctz((unsigned int)v);
#else
    return kmCttzU64((uint64_t)v);
#endif
}
KM_ALWAYS_INLINE uint32_t kmCttzU32(uint32_t v) {
    if (v == 0u) return 32u;
#if defined(__clang__) || defined(__GNUC__)
    return (uint32_t)__builtin_ctz(v);
#else
    return kmCttzU64((uint64_t)v);
#endif
}
KM_ALWAYS_INLINE uint32_t kmCttzU128(uint64_t lo, uint64_t hi) {
    return lo != 0ULL ? kmCttzU64(lo) : (64u + kmCttzU64(hi));
}
KM_ALWAYS_INLINE void kmBswapU128Out(uint64_t lo, uint64_t hi, uint64_t* outLo, uint64_t* outHi) {
    *outLo = kmBswapU64(hi);
    *outHi = kmBswapU64(lo);
}
KM_ALWAYS_INLINE uint8_t kmBitreverseU8(uint8_t v) {
#if defined(__clang__)
    return __builtin_bitreverse8(v);
#else
    return (uint8_t)(kmBitreverseU64((uint64_t)v) >> 56);
#endif
}
KM_ALWAYS_INLINE uint16_t kmBitreverseU16(uint16_t v) {
#if defined(__clang__)
    return __builtin_bitreverse16(v);
#else
    return (uint16_t)(kmBitreverseU64((uint64_t)v) >> 48);
#endif
}
KM_ALWAYS_INLINE uint32_t kmBitreverseU32(uint32_t v) {
#if defined(__clang__)
    return __builtin_bitreverse32(v);
#else
    return (uint32_t)(kmBitreverseU64((uint64_t)v) >> 32);
#endif
}
KM_ALWAYS_INLINE void kmBitreverseU128Out(uint64_t lo, uint64_t hi, uint64_t* outLo, uint64_t* outHi) {
    *outLo = kmBitreverseU64(hi);
    *outHi = kmBitreverseU64(lo);
}
KM_ALWAYS_INLINE uint8_t kmRotateLeftU8(uint8_t v, uint32_t n) {
    uint32_t s = n & 7u;
    if (s == 0u) return v;
    return (uint8_t)(uint32_t)(((uint32_t)v << s) | ((uint32_t)v >> (8u - s)));
}
KM_ALWAYS_INLINE uint16_t kmRotateLeftU16(uint16_t v, uint32_t n) {
    uint32_t s = n & 15u;
    if (s == 0u) return v;
    return (uint16_t)(uint32_t)(((uint32_t)v << s) | ((uint32_t)v >> (16u - s)));
}
KM_ALWAYS_INLINE uint32_t kmRotateLeftU32(uint32_t v, uint32_t n) {
    uint32_t s = n & 31u;
    if (s == 0u) return v;
    return (uint32_t)(uint32_t)(((uint32_t)v << s) | ((uint32_t)v >> (32u - s)));
}
KM_ALWAYS_INLINE uint64_t kmRotateLeftU64(uint64_t v, uint32_t n) {
    uint32_t s = n & 63u;
    if (s == 0u) return v;
    return (uint64_t)(uint64_t)(((uint64_t)v << s) | ((uint64_t)v >> (64u - s)));
}
KM_ALWAYS_INLINE void kmRotateLeftU128Out(uint64_t lo, uint64_t hi, uint32_t n, uint64_t* outLo, uint64_t* outHi) {
    uint32_t s = n & 127u;
    if (s == 0u) { *outLo = lo; *outHi = hi; return; }
    if (s >= 64u) {
        uint32_t k = s - 64u;
        if (k == 0u) { *outLo = hi; *outHi = lo; }
        else { *outLo = (hi << k) | (lo >> (64u - k)); *outHi = (lo << k) | (hi >> (64u - k)); }
        return;
    }
    *outLo = (lo << s) | (hi >> (64u - s));
    *outHi = (hi << s) | (lo >> (64u - s));
}
KM_ALWAYS_INLINE uint8_t kmRotateRightU8(uint8_t v, uint32_t n) {
    uint32_t s = n & 7u;
    if (s == 0u) return v;
    return (uint8_t)(uint32_t)(((uint32_t)v >> s) | ((uint32_t)v << (8u - s)));
}
KM_ALWAYS_INLINE uint16_t kmRotateRightU16(uint16_t v, uint32_t n) {
    uint32_t s = n & 15u;
    if (s == 0u) return v;
    return (uint16_t)(uint32_t)(((uint32_t)v >> s) | ((uint32_t)v << (16u - s)));
}
KM_ALWAYS_INLINE uint32_t kmRotateRightU32(uint32_t v, uint32_t n) {
    uint32_t s = n & 31u;
    if (s == 0u) return v;
    return (uint32_t)(uint32_t)(((uint32_t)v >> s) | ((uint32_t)v << (32u - s)));
}
KM_ALWAYS_INLINE uint64_t kmRotateRightU64(uint64_t v, uint32_t n) {
    uint32_t s = n & 63u;
    if (s == 0u) return v;
    return (uint64_t)(uint64_t)(((uint64_t)v >> s) | ((uint64_t)v << (64u - s)));
}
KM_ALWAYS_INLINE void kmRotateRightU128Out(uint64_t lo, uint64_t hi, uint32_t n, uint64_t* outLo, uint64_t* outHi) {
    uint32_t leftShift = (128u - (n & 127u)) & 127u;
    kmRotateLeftU128Out(lo, hi, leftShift, outLo, outHi);
}
/* Overflowing and saturating integer arithmetic intrinsics */
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowU8Packed(uint8_t a, uint8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint8_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
uint8_t res = (uint8_t)((unsigned int)a + (unsigned int)b);
uint8_t ovf = res < a ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowU16Packed(uint16_t a, uint16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint16_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
uint16_t res = (uint16_t)((unsigned int)a + (unsigned int)b);
uint8_t ovf = res < a ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowU32Packed(uint32_t a, uint32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint32_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
uint32_t res = (uint32_t)((unsigned int)a + (unsigned int)b);
uint8_t ovf = res < a ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmAddWithOverflowU64Out(uint64_t a, uint64_t b, uint64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_add_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t res = a + b; *outVal = res; return res < a ? 1u : 0u;
#endif
}
KM_ALWAYS_INLINE uint8_t kmAddWithOverflowU128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t sLo = 0ULL, sHi = 0ULL;
    uint64_t cLo = kmAddU64Carry(aLo, bLo, &sLo);
    uint64_t cHi1 = kmAddU64Carry(aHi, bHi, &sHi);
    uint64_t sHi2 = 0ULL;
    uint64_t cHi2 = kmAddU64Carry(sHi, cLo, &sHi2);
    *outLo = sLo;
    *outHi = sHi2;
    return (cHi1 | cHi2) != 0ULL ? 1u : 0u;
}
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowI8Packed(int8_t a, int8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int8_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint8_t)a;
uint32_t ub = (uint32_t)(uint8_t)b;
uint32_t ures = ua + ub;
uint8_t truncRes = (uint8_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((~(ua ^ ub)) & (ua ^ rBits)) >> 7) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowI16Packed(int16_t a, int16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int16_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint16_t)a;
uint32_t ub = (uint32_t)(uint16_t)b;
uint32_t ures = ua + ub;
uint16_t truncRes = (uint16_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((~(ua ^ ub)) & (ua ^ rBits)) >> 15) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmAddWithOverflowI32Packed(int32_t a, int32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int32_t res = 0;
    uint8_t ovf = __builtin_add_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint32_t)a;
uint32_t ub = (uint32_t)(uint32_t)b;
uint32_t ures = ua + ub;
uint32_t truncRes = (uint32_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((~(ua ^ ub)) & (ua ^ rBits)) >> 31) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmAddWithOverflowI64Out(int64_t a, int64_t b, int64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_add_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t ua = (uint64_t)a; uint64_t ub = (uint64_t)b; uint64_t ur = ua + ub; *outVal = (int64_t)ur; return (uint8_t)((((~(ua ^ ub)) & (ua ^ ur)) >> 63) & 1ULL);
#endif
}
KM_ALWAYS_INLINE uint8_t kmAddWithOverflowI128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    kmAddWithOverflowU128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    *outLo = rLo;
    *outHi = rHi;
    return (uint8_t)((((~(aHi ^ bHi)) & (aHi ^ rHi)) >> 63) & 1ULL);
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowU8Packed(uint8_t a, uint8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint8_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
uint8_t res = (uint8_t)((unsigned int)a - (unsigned int)b);
uint8_t ovf = a < b ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowU16Packed(uint16_t a, uint16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint16_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
uint16_t res = (uint16_t)((unsigned int)a - (unsigned int)b);
uint8_t ovf = a < b ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowU32Packed(uint32_t a, uint32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint32_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
uint32_t res = (uint32_t)((unsigned int)a - (unsigned int)b);
uint8_t ovf = a < b ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmSubWithOverflowU64Out(uint64_t a, uint64_t b, uint64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_sub_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t res = a - b; *outVal = res; return a < b ? 1u : 0u;
#endif
}
KM_ALWAYS_INLINE uint8_t kmSubWithOverflowU128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t borrow = aLo < bLo ? 1ULL : 0ULL;
    *outLo = aLo - bLo;
    *outHi = aHi - bHi - borrow;
    return (aHi < bHi || (aHi == bHi && aLo < bLo)) ? 1u : 0u;
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowI8Packed(int8_t a, int8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int8_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint8_t)a;
uint32_t ub = (uint32_t)(uint8_t)b;
uint32_t ures = ua - ub;
uint8_t truncRes = (uint8_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((ua ^ ub) & (ua ^ rBits)) >> 7) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowI16Packed(int16_t a, int16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int16_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint16_t)a;
uint32_t ub = (uint32_t)(uint16_t)b;
uint32_t ures = ua - ub;
uint16_t truncRes = (uint16_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((ua ^ ub) & (ua ^ rBits)) >> 15) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmSubWithOverflowI32Packed(int32_t a, int32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int32_t res = 0;
    uint8_t ovf = __builtin_sub_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
uint32_t ua = (uint32_t)(uint32_t)a;
uint32_t ub = (uint32_t)(uint32_t)b;
uint32_t ures = ua - ub;
uint32_t truncRes = (uint32_t)ures;
uint32_t rBits = (uint32_t)truncRes;
uint32_t ovf = (((ua ^ ub) & (ua ^ rBits)) >> 31) & 1u;
return kmPackOverflow32(rBits, (uint8_t)ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmSubWithOverflowI64Out(int64_t a, int64_t b, int64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_sub_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t ua = (uint64_t)a; uint64_t ub = (uint64_t)b; uint64_t ur = ua - ub; *outVal = (int64_t)ur; return (uint8_t)((((ua ^ ub) & (ua ^ ur)) >> 63) & 1ULL);
#endif
}
KM_ALWAYS_INLINE uint8_t kmSubWithOverflowI128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    kmSubWithOverflowU128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    *outLo = rLo;
    *outHi = rHi;
    return (uint8_t)((((aHi ^ bHi) & (aHi ^ rHi)) >> 63) & 1ULL);
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowU8Packed(uint8_t a, uint8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint8_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
uint64_t wide = (uint64_t)a * (uint64_t)b;
uint8_t res = (uint8_t)wide;
uint8_t ovf = wide > (uint64_t)UINT8_MAX ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowU16Packed(uint16_t a, uint16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint16_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
uint64_t wide = (uint64_t)a * (uint64_t)b;
uint16_t res = (uint16_t)wide;
uint8_t ovf = wide > (uint64_t)UINT16_MAX ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowU32Packed(uint32_t a, uint32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    uint32_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
uint64_t wide = (uint64_t)a * (uint64_t)b;
uint32_t res = (uint32_t)wide;
uint8_t ovf = wide > (uint64_t)UINT32_MAX ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmMulWithOverflowU64Out(uint64_t a, uint64_t b, uint64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_mul_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t lo = 0ULL, hi = 0ULL; kmMulU64Wide(a, b, &lo, &hi); *outVal = lo; return hi != 0ULL ? 1u : 0u;
#endif
}
KM_ALWAYS_INLINE uint8_t kmMulWithOverflowU128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t p00Lo = 0ULL, p00Hi = 0ULL;
    uint64_t p01Lo = 0ULL, p01Hi = 0ULL;
    uint64_t p10Lo = 0ULL, p10Hi = 0ULL;
    kmMulU64Wide(aLo, bLo, &p00Lo, &p00Hi);
    kmMulU64Wide(aLo, bHi, &p01Lo, &p01Hi);
    kmMulU64Wide(aHi, bLo, &p10Lo, &p10Hi);
    uint64_t mid1 = 0ULL;
    uint64_t c1 = kmAddU64Carry(p00Hi, p01Lo, &mid1);
    uint64_t mid2 = 0ULL;
    uint64_t c2 = kmAddU64Carry(mid1, p10Lo, &mid2);
    *outLo = p00Lo;
    *outHi = mid2;
    uint8_t highNonZero = (p01Hi != 0ULL || p10Hi != 0ULL || c1 != 0ULL || c2 != 0ULL || (aHi != 0ULL && bHi != 0ULL)) ? 1u : 0u;
    return highNonZero;
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowI8Packed(int8_t a, int8_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int8_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint8_t)res, ovf);
#else
int64_t wide = (int64_t)a * (int64_t)b;
uint8_t res = (uint8_t)(uint64_t)wide;
uint8_t ovf = (wide < (int64_t)INT8_MIN || wide > (int64_t)INT8_MAX) ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowI16Packed(int16_t a, int16_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int16_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint16_t)res, ovf);
#else
int64_t wide = (int64_t)a * (int64_t)b;
uint16_t res = (uint16_t)(uint64_t)wide;
uint8_t ovf = (wide < (int64_t)INT16_MIN || wide > (int64_t)INT16_MAX) ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint64_t kmMulWithOverflowI32Packed(int32_t a, int32_t b) {
#if defined(__clang__) || defined(__GNUC__)
    int32_t res = 0;
    uint8_t ovf = __builtin_mul_overflow(a, b, &res) ? 1u : 0u;
    return kmPackOverflow32((uint32_t)(uint32_t)res, ovf);
#else
int64_t wide = (int64_t)a * (int64_t)b;
uint32_t res = (uint32_t)(uint64_t)wide;
uint8_t ovf = (wide < (int64_t)INT32_MIN || wide > (int64_t)INT32_MAX) ? 1u : 0u;
return kmPackOverflow32((uint32_t)res, ovf);
#endif
}
KM_ALWAYS_INLINE uint8_t kmMulWithOverflowI64Out(int64_t a, int64_t b, int64_t* outVal) {
#if defined(__clang__) || defined(__GNUC__)
    return __builtin_mul_overflow(a, b, outVal) ? 1u : 0u;
#else
    uint64_t ua = (uint64_t)a;
uint64_t ub = (uint64_t)b;
int sA = a < 0;
int sB = b < 0;
uint64_t magA = sA ? (0ULL - ua) : ua;
uint64_t magB = sB ? (0ULL - ub) : ub;
uint64_t lo = 0ULL, hi = 0ULL;
kmMulU64Wide(magA, magB, &lo, &hi);
int neg = sA != sB;
uint64_t ur = neg ? (0ULL - lo) : lo;
*outVal = (int64_t)ur;
if (hi != 0ULL) return 1u;
if (neg) return lo > 0x8000000000000000ULL ? 1u : 0u;
return (lo >> 63) != 0ULL ? 1u : 0u;
#endif
}
KM_ALWAYS_INLINE uint8_t kmMulWithOverflowI128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    int sA = (aHi >> 63) != 0ULL;
    int sB = (bHi >> 63) != 0ULL;
    uint64_t mALo = aLo, mAHi = aHi, mBLo = bLo, mBHi = bHi;
    if (sA) kmU128NegateRaw(aLo, aHi, &mALo, &mAHi);
    if (sB) kmU128NegateRaw(bLo, bHi, &mBLo, &mBHi);
    uint64_t pLo = 0ULL, pHi = 0ULL;
    uint8_t hiNonZero = kmMulWithOverflowU128Out(mALo, mAHi, mBLo, mBHi, &pLo, &pHi);
    int neg = sA != sB;
    if (neg) {
        kmU128NegateRaw(pLo, pHi, outLo, outHi);
        if (hiNonZero != 0u) return 1u;
        return (pHi > 0x8000000000000000ULL || (pHi == 0x8000000000000000ULL && pLo != 0ULL)) ? 1u : 0u;
    } else {
        *outLo = pLo;
        *outHi = pHi;
        if (hiNonZero != 0u) return 1u;
        return (pHi >> 63) != 0ULL ? 1u : 0u;
    }
}
KM_ALWAYS_INLINE uint8_t kmSaturatingAddU8(uint8_t a, uint8_t b) {
    uint64_t packed = kmAddWithOverflowU8Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint8_t resBits = (uint8_t)(packed & 0xFFFFFFFFULL);
    uint8_t res = (uint8_t)resBits;
    if (ovf != 0u) return (uint8_t)UINT8_MAX;
    return res;
}
KM_ALWAYS_INLINE uint16_t kmSaturatingAddU16(uint16_t a, uint16_t b) {
    uint64_t packed = kmAddWithOverflowU16Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint16_t resBits = (uint16_t)(packed & 0xFFFFFFFFULL);
    uint16_t res = (uint16_t)resBits;
    if (ovf != 0u) return (uint16_t)UINT16_MAX;
    return res;
}
KM_ALWAYS_INLINE uint32_t kmSaturatingAddU32(uint32_t a, uint32_t b) {
    uint64_t packed = kmAddWithOverflowU32Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint32_t resBits = (uint32_t)(packed & 0xFFFFFFFFULL);
    uint32_t res = (uint32_t)resBits;
    if (ovf != 0u) return (uint32_t)UINT32_MAX;
    return res;
}
KM_ALWAYS_INLINE uint64_t kmSaturatingAddU64(uint64_t a, uint64_t b) {
    uint64_t res = 0;
    uint8_t ovf = kmAddWithOverflowU64Out(a, b, &res);
    if (ovf != 0u) return UINT64_MAX;
    return res;
}
KM_ALWAYS_INLINE void kmSaturatingAddU128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    uint8_t ovf = kmAddWithOverflowU128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    if (ovf != 0u) { *outLo = 0xFFFFFFFFFFFFFFFFULL; *outHi = 0xFFFFFFFFFFFFFFFFULL; return; }
    *outLo = rLo;
    *outHi = rHi;
}
KM_ALWAYS_INLINE int8_t kmSaturatingAddI8(int8_t a, int8_t b) {
    uint64_t packed = kmAddWithOverflowI8Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint8_t resBits = (uint8_t)(packed & 0xFFFFFFFFULL);
    int8_t res = (int8_t)resBits;
    if (ovf != 0u) return (int8_t)(((uint8_t)res >> 7) != 0u ? (int8_t)INT8_MAX : (int8_t)INT8_MIN);
    return res;
}
KM_ALWAYS_INLINE int16_t kmSaturatingAddI16(int16_t a, int16_t b) {
    uint64_t packed = kmAddWithOverflowI16Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint16_t resBits = (uint16_t)(packed & 0xFFFFFFFFULL);
    int16_t res = (int16_t)resBits;
    if (ovf != 0u) return (int16_t)(((uint16_t)res >> 15) != 0u ? (int16_t)INT16_MAX : (int16_t)INT16_MIN);
    return res;
}
KM_ALWAYS_INLINE int32_t kmSaturatingAddI32(int32_t a, int32_t b) {
    uint64_t packed = kmAddWithOverflowI32Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint32_t resBits = (uint32_t)(packed & 0xFFFFFFFFULL);
    int32_t res = (int32_t)resBits;
    if (ovf != 0u) return (int32_t)(((uint32_t)res >> 31) != 0u ? (int32_t)INT32_MAX : (int32_t)INT32_MIN);
    return res;
}
KM_ALWAYS_INLINE int64_t kmSaturatingAddI64(int64_t a, int64_t b) {
    int64_t res = 0;
    uint8_t ovf = kmAddWithOverflowI64Out(a, b, &res);
    if (ovf != 0u) return ((uint64_t)res >> 63) != 0ULL ? INT64_MAX : INT64_MIN;
    return res;
}
KM_ALWAYS_INLINE void kmSaturatingAddI128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    uint8_t ovf = kmAddWithOverflowI128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    if (ovf != 0u) {
    if ((rHi >> 63) != 0ULL) { *outLo = 0xFFFFFFFFFFFFFFFFULL; *outHi = 0x7FFFFFFFFFFFFFFFULL; }
    else { *outLo = 0ULL; *outHi = 0x8000000000000000ULL; }
    return;
}
    *outLo = rLo;
    *outHi = rHi;
}
KM_ALWAYS_INLINE uint8_t kmSaturatingSubU8(uint8_t a, uint8_t b) {
    uint64_t packed = kmSubWithOverflowU8Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint8_t resBits = (uint8_t)(packed & 0xFFFFFFFFULL);
    uint8_t res = (uint8_t)resBits;
    if (ovf != 0u) return (uint8_t)0u;
    return res;
}
KM_ALWAYS_INLINE uint16_t kmSaturatingSubU16(uint16_t a, uint16_t b) {
    uint64_t packed = kmSubWithOverflowU16Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint16_t resBits = (uint16_t)(packed & 0xFFFFFFFFULL);
    uint16_t res = (uint16_t)resBits;
    if (ovf != 0u) return (uint16_t)0u;
    return res;
}
KM_ALWAYS_INLINE uint32_t kmSaturatingSubU32(uint32_t a, uint32_t b) {
    uint64_t packed = kmSubWithOverflowU32Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint32_t resBits = (uint32_t)(packed & 0xFFFFFFFFULL);
    uint32_t res = (uint32_t)resBits;
    if (ovf != 0u) return (uint32_t)0u;
    return res;
}
KM_ALWAYS_INLINE uint64_t kmSaturatingSubU64(uint64_t a, uint64_t b) {
    uint64_t res = 0;
    uint8_t ovf = kmSubWithOverflowU64Out(a, b, &res);
    if (ovf != 0u) return 0ULL;
    return res;
}
KM_ALWAYS_INLINE void kmSaturatingSubU128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    uint8_t ovf = kmSubWithOverflowU128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    if (ovf != 0u) { *outLo = 0ULL; *outHi = 0ULL; return; }
    *outLo = rLo;
    *outHi = rHi;
}
KM_ALWAYS_INLINE int8_t kmSaturatingSubI8(int8_t a, int8_t b) {
    uint64_t packed = kmSubWithOverflowI8Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint8_t resBits = (uint8_t)(packed & 0xFFFFFFFFULL);
    int8_t res = (int8_t)resBits;
    if (ovf != 0u) return (int8_t)(((uint8_t)res >> 7) != 0u ? (int8_t)INT8_MAX : (int8_t)INT8_MIN);
    return res;
}
KM_ALWAYS_INLINE int16_t kmSaturatingSubI16(int16_t a, int16_t b) {
    uint64_t packed = kmSubWithOverflowI16Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint16_t resBits = (uint16_t)(packed & 0xFFFFFFFFULL);
    int16_t res = (int16_t)resBits;
    if (ovf != 0u) return (int16_t)(((uint16_t)res >> 15) != 0u ? (int16_t)INT16_MAX : (int16_t)INT16_MIN);
    return res;
}
KM_ALWAYS_INLINE int32_t kmSaturatingSubI32(int32_t a, int32_t b) {
    uint64_t packed = kmSubWithOverflowI32Packed(a, b);
    uint8_t ovf = (uint8_t)(packed >> 32);
    uint32_t resBits = (uint32_t)(packed & 0xFFFFFFFFULL);
    int32_t res = (int32_t)resBits;
    if (ovf != 0u) return (int32_t)(((uint32_t)res >> 31) != 0u ? (int32_t)INT32_MAX : (int32_t)INT32_MIN);
    return res;
}
KM_ALWAYS_INLINE int64_t kmSaturatingSubI64(int64_t a, int64_t b) {
    int64_t res = 0;
    uint8_t ovf = kmSubWithOverflowI64Out(a, b, &res);
    if (ovf != 0u) return ((uint64_t)res >> 63) != 0ULL ? INT64_MAX : INT64_MIN;
    return res;
}
KM_ALWAYS_INLINE void kmSaturatingSubI128Out(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    uint64_t rLo = 0ULL, rHi = 0ULL;
    uint8_t ovf = kmSubWithOverflowI128Out(aLo, aHi, bLo, bHi, &rLo, &rHi);
    if (ovf != 0u) {
    if ((rHi >> 63) != 0ULL) { *outLo = 0xFFFFFFFFFFFFFFFFULL; *outHi = 0x7FFFFFFFFFFFFFFFULL; }
    else { *outLo = 0ULL; *outHi = 0x8000000000000000ULL; }
    return;
}
    *outLo = rLo;
    *outHi = rHi;
}
/* C-backed 128-bit arithmetic and float math intrinsics */
KM_ALWAYS_INLINE void kmU128AddOut(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    kmAddWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo, outHi);
}

KM_ALWAYS_INLINE void kmU128SubOut(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    kmSubWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo, outHi);
}

KM_ALWAYS_INLINE void kmU128MulOut(uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi, uint64_t* outLo, uint64_t* outHi) {
    kmMulWithOverflowU128Out(aLo, aHi, bLo, bHi, outLo, outHi);
}

KM_ALWAYS_INLINE void kmU128DivRemOut(
    uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi,
    uint64_t* outQLo, uint64_t* outQHi, uint64_t* outRLo, uint64_t* outRHi
) {
    kmU128DivRemRaw(aLo, aHi, bLo, bHi, outQLo, outQHi, outRLo, outRHi);
}

KM_ALWAYS_INLINE void kmI128DivRemOut(
    uint64_t aLo, uint64_t aHi, uint64_t bLo, uint64_t bHi,
    uint64_t* outQLo, uint64_t* outQHi, uint64_t* outRLo, uint64_t* outRHi
) {
    kmI128DivRemRaw(aLo, aHi, bLo, bHi, outQLo, outQHi, outRLo, outRHi);
}

KM_ALWAYS_INLINE void kmU128ShlOut(uint64_t aLo, uint64_t aHi, uint32_t shift, uint64_t* outLo, uint64_t* outHi) {
    uint32_t s = shift & 127u;
    if (s == 0u) { *outLo = aLo; *outHi = aHi; return; }
    if (s == 64u) { *outLo = 0ULL; *outHi = aLo; return; }
    if (s > 64u) { *outLo = 0ULL; *outHi = aLo << (s - 64u); return; }
    *outLo = aLo << s;
    *outHi = (aHi << s) | (aLo >> (64u - s));
}

KM_ALWAYS_INLINE void kmU128ShrOut(uint64_t aLo, uint64_t aHi, uint32_t shift, uint64_t* outLo, uint64_t* outHi) {
    uint32_t s = shift & 127u;
    if (s == 0u) { *outLo = aLo; *outHi = aHi; return; }
    if (s == 64u) { *outLo = aHi; *outHi = 0ULL; return; }
    if (s > 64u) { *outLo = aHi >> (s - 64u); *outHi = 0ULL; return; }
    *outLo = (aLo >> s) | (aHi << (64u - s));
    *outHi = aHi >> s;
}

KM_ALWAYS_INLINE void kmI128ShrOut(uint64_t aLo, uint64_t aHi, uint32_t shift, uint64_t* outLo, uint64_t* outHi) {
    uint32_t s = shift & 127u;
    uint64_t signFill = ((aHi >> 63) != 0ULL) ? 0xFFFFFFFFFFFFFFFFULL : 0ULL;
    if (s == 0u) { *outLo = aLo; *outHi = aHi; return; }
    if (s == 64u) { *outLo = aHi; *outHi = signFill; return; }
    if (s > 64u) {
        uint32_t k = s - 64u;
        *outLo = (aHi >> k) | (signFill << (64u - k));
        *outHi = signFill;
        return;
    }
    *outLo = (aLo >> s) | (aHi << (64u - s));
    *outHi = (aHi >> s) | (signFill << (64u - s));
}

KM_ALWAYS_INLINE float kmPowif32(float x, int32_t exp) {
    if (exp == 0) return 1.0f;
    uint32_t e = (exp < 0) ? (uint32_t)(0u - (uint32_t)exp) : (uint32_t)exp;
    float base = (exp < 0) ? (1.0f / x) : x;
    float acc = 1.0f;
    while (e != 0u) {
        if ((e & 1u) != 0u) acc *= base;
        e >>= 1;
        if (e != 0u) base *= base;
    }
    return acc;
}

KM_ALWAYS_INLINE double kmPowif64(double x, int32_t exp) {
    if (exp == 0) return 1.0;
    uint32_t e = (exp < 0) ? (uint32_t)(0u - (uint32_t)exp) : (uint32_t)exp;
    double base = (exp < 0) ? (1.0 / x) : x;
    double acc = 1.0;
    while (e != 0u) {
        if ((e & 1u) != 0u) acc *= base;
        e >>= 1;
        if (e != 0u) base *= base;
    }
    return acc;
}
KM_ALWAYS_INLINE float kmSinf32(float x) { return sinf(x); }
KM_ALWAYS_INLINE double kmSinf64(double x) { return sin(x); }
KM_ALWAYS_INLINE float kmCosf32(float x) { return cosf(x); }
KM_ALWAYS_INLINE double kmCosf64(double x) { return cos(x); }
KM_ALWAYS_INLINE float kmExpf32(float x) { return expf(x); }
KM_ALWAYS_INLINE double kmExpf64(double x) { return exp(x); }
KM_ALWAYS_INLINE float kmExp2f32(float x) { return exp2f(x); }
KM_ALWAYS_INLINE double kmExp2f64(double x) { return exp2(x); }
KM_ALWAYS_INLINE float kmLogf32(float x) { return logf(x); }
KM_ALWAYS_INLINE double kmLogf64(double x) { return log(x); }
KM_ALWAYS_INLINE float kmLog2f32(float x) { return log2f(x); }
KM_ALWAYS_INLINE double kmLog2f64(double x) { return log2(x); }
KM_ALWAYS_INLINE float kmLog10f32(float x) { return log10f(x); }
KM_ALWAYS_INLINE double kmLog10f64(double x) { return log10(x); }
KM_ALWAYS_INLINE float kmTruncf32(float x) { return truncf(x); }
KM_ALWAYS_INLINE double kmTruncf64(double x) { return trunc(x); }
KM_ALWAYS_INLINE float kmRoundf32(float x) { return roundf(x); }
KM_ALWAYS_INLINE double kmRoundf64(double x) { return round(x); }
KM_ALWAYS_INLINE float kmRoundTiesEvenf32(float x) { return nearbyintf(x); }
KM_ALWAYS_INLINE double kmRoundTiesEvenf64(double x) { return nearbyint(x); }
KM_ALWAYS_INLINE float kmCeilf32(float x) { return ceilf(x); }
KM_ALWAYS_INLINE double kmCeilf64(double x) { return ceil(x); }
KM_ALWAYS_INLINE float kmFloorf32(float x) { return floorf(x); }
KM_ALWAYS_INLINE double kmFloorf64(double x) { return floor(x); }
KM_ALWAYS_INLINE float kmFabsf32(float x) { return fabsf(x); }
KM_ALWAYS_INLINE double kmFabsf64(double x) { return fabs(x); }
KM_ALWAYS_INLINE float kmPowf32(float a, float b) { return powf(a, b); }
KM_ALWAYS_INLINE double kmPowf64(double a, double b) { return pow(a, b); }
KM_ALWAYS_INLINE float kmCopysignf32(float a, float b) { return copysignf(a, b); }
KM_ALWAYS_INLINE double kmCopysignf64(double a, double b) { return copysign(a, b); }
KM_ALWAYS_INLINE float kmMinnumf32(float a, float b) { return fminf(a, b); }
KM_ALWAYS_INLINE double kmMinnumf64(double a, double b) { return fmin(a, b); }
KM_ALWAYS_INLINE float kmMaxnumf32(float a, float b) { return fmaxf(a, b); }
KM_ALWAYS_INLINE double kmMaxnumf64(double a, double b) { return fmax(a, b); }
KM_ALWAYS_INLINE uint64_t kmFloatToU64Unchecked(float v) {
    return (v >= 0.0f) ? (uint64_t)v : 0ULL;
}

KM_ALWAYS_INLINE uint64_t kmDoubleToU64Unchecked(double v) {
    return (v >= 0.0) ? (uint64_t)v : 0ULL;
}

KM_ALWAYS_INLINE void kmFloatToU128UncheckedOut(float v, uint64_t* outLo, uint64_t* outHi) {
    if (!(v >= 0.0f)) { *outLo = 0ULL; *outHi = 0ULL; return; }
#if defined(__SIZEOF_INT128__) && !defined(_MSC_VER)
    __uint128_t r = (__uint128_t)v;
    *outLo = (uint64_t)r;
    *outHi = (uint64_t)(r >> 64);
#else
    double hiScale = 18446744073709551616.0;
    double dv = (double)v;
    uint64_t hi = (dv >= hiScale) ? (uint64_t)(dv / hiScale) : 0ULL;
    double rem = dv - (double)hi * hiScale;
    uint64_t lo = (rem >= 0.0) ? (uint64_t)rem : 0ULL;
    *outLo = lo;
    *outHi = hi;
#endif
}

KM_ALWAYS_INLINE void kmDoubleToU128UncheckedOut(double v, uint64_t* outLo, uint64_t* outHi) {
    if (!(v >= 0.0)) { *outLo = 0ULL; *outHi = 0ULL; return; }
#if defined(__SIZEOF_INT128__) && !defined(_MSC_VER)
    __uint128_t r = (__uint128_t)v;
    *outLo = (uint64_t)r;
    *outHi = (uint64_t)(r >> 64);
#else
    double hiScale = 18446744073709551616.0;
    uint64_t hi = (v >= hiScale) ? (uint64_t)(v / hiScale) : 0ULL;
    double rem = v - (double)hi * hiScale;
    uint64_t lo = (rem >= 0.0) ? (uint64_t)rem : 0ULL;
    *outLo = lo;
    *outHi = hi;
#endif
}



#endif /* KM_CORE_INTRINSICS_INLINE_H */
