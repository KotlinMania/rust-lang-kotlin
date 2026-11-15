#ifndef RUSTLANG_COREARCH_WASM32_ATOMIC_COREARCH_WASM32_ATOMIC_WRAPPER_H
#define RUSTLANG_COREARCH_WASM32_ATOMIC_COREARCH_WASM32_ATOMIC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangCorearchWasm32AtomicLlvmAtomicWaitI32(void* ptr, int32_t arg1Exp, int64_t timeout);
int32_t rustlangCorearchWasm32AtomicLlvmAtomicWaitI64(void* ptr, int64_t arg1Exp, int64_t timeout);
int32_t rustlangCorearchWasm32AtomicLlvmAtomicNotify(void* ptr, int32_t cnt);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_WASM32_ATOMIC_COREARCH_WASM32_ATOMIC_WRAPPER_H */
