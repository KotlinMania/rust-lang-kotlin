#ifndef RUSTLANG_COREARCH_AARCH64_MTE_COREARCH_AARCH64_MTE_WRAPPER_H
#define RUSTLANG_COREARCH_AARCH64_MTE_COREARCH_AARCH64_MTE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangCorearchAarch64MteIrg(void* ptr, int64_t exclude);
int64_t rustlangCorearchAarch64MteGmi(void* ptr, int64_t exclude);
void* rustlangCorearchAarch64MteLdg(void* ptr, void* tagPtr);
void rustlangCorearchAarch64MteStg(void* taggedPtr, void* addrToTag);
void* rustlangCorearchAarch64MteAddg(void* ptr, int64_t value);
int64_t rustlangCorearchAarch64MteSubp(void* ptrA, void* ptrB);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_AARCH64_MTE_COREARCH_AARCH64_MTE_WRAPPER_H */
