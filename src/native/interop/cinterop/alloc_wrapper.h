#ifndef RUSTLANG_ALLOC_ALLOC_WRAPPER_H
#define RUSTLANG_ALLOC_ALLOC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangAllocRustAllocErrorHandler(uint64_t size, uint64_t align);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_ALLOC_ALLOC_WRAPPER_H */
