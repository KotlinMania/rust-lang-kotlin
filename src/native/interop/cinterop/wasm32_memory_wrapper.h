#ifndef COREARCH_WASM32_MEMORY_WASM32_MEMORY_WRAPPER_H
#define COREARCH_WASM32_MEMORY_WASM32_MEMORY_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t corearchWasm32MemoryLlvmMemoryGrow(uint32_t mem, uint64_t pages);
uint64_t corearchWasm32MemoryLlvmMemorySize(uint32_t mem);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_WASM32_MEMORY_WASM32_MEMORY_WRAPPER_H */
