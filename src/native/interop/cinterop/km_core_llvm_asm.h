#ifndef KM_CORE_LLVM_ASM_H
#define KM_CORE_LLVM_ASM_H

#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void kmSpinLoopHint(void);
uint64_t kmBlackBoxU64(uint64_t val);
uint64_t kmUmulhU64(uint64_t a, uint64_t b);
int64_t kmSmulhI64(int64_t a, int64_t b);
uint8_t kmAtomicCxchgU128(
    int64_t addr,
    uint64_t expLo,
    uint64_t expHi,
    uint64_t desLo,
    uint64_t desHi,
    uint64_t* outLo,
    uint64_t* outHi
);
void kmCpuidCount(
    uint32_t leaf,
    uint32_t subleaf,
    uint32_t* eaxOut,
    uint32_t* ebxOut,
    uint32_t* ecxOut,
    uint32_t* edxOut
);
uint64_t kmXgetbv(uint32_t xcr);

#ifdef __cplusplus
}
#endif

#endif /* KM_CORE_LLVM_ASM_H */
