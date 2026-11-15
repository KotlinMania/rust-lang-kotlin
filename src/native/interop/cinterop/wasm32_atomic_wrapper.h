#ifndef COREARCH_WASM32_ATOMIC_WASM32_ATOMIC_WRAPPER_H
#define COREARCH_WASM32_ATOMIC_WASM32_ATOMIC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchWasm32AtomicLlvmAtomicWaitI32(void* ptr, int32_t arg1Exp, int64_t timeout);
int32_t corearchWasm32AtomicLlvmAtomicWaitI64(void* ptr, int64_t arg1Exp, int64_t timeout);
int32_t corearchWasm32AtomicLlvmAtomicNotify(void* ptr, int32_t cnt);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_WASM32_ATOMIC_WASM32_ATOMIC_WRAPPER_H */
