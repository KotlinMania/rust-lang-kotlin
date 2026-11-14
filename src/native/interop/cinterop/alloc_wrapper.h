#ifndef ALLOC_ALLOC_WRAPPER_H
#define ALLOC_ALLOC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void allocRustAllocErrorHandler(uint64_t size, uint64_t align);

#ifdef __cplusplus
}
#endif

#endif /* ALLOC_ALLOC_WRAPPER_H */
