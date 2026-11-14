#ifndef COREARCH_AARCH64_MTE_AARCH64_MTE_WRAPPER_H
#define COREARCH_AARCH64_MTE_AARCH64_MTE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* corearchAarch64MteIrg(void* ptr, int64_t exclude);
int64_t corearchAarch64MteGmi(void* ptr, int64_t exclude);
void* corearchAarch64MteLdg(void* ptr, void* tagPtr);
void corearchAarch64MteStg(void* taggedPtr, void* addrToTag);
void* corearchAarch64MteAddg(void* ptr, int64_t value);
int64_t corearchAarch64MteSubp(void* ptrA, void* ptrB);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_AARCH64_MTE_AARCH64_MTE_WRAPPER_H */
