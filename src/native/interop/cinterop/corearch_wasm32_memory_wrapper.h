#ifndef RUSTLANG_COREARCH_WASM32_MEMORY_COREARCH_WASM32_MEMORY_WRAPPER_H
#define RUSTLANG_COREARCH_WASM32_MEMORY_COREARCH_WASM32_MEMORY_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t rustlangCorearchWasm32MemoryLlvmMemoryGrow(uint32_t mem, uint64_t pages);
uint64_t rustlangCorearchWasm32MemoryLlvmMemorySize(uint32_t mem);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_WASM32_MEMORY_COREARCH_WASM32_MEMORY_WRAPPER_H */
