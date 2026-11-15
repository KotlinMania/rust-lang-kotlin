#ifndef RUSTLANG_COREARCH_AARCH64_PREFETCH_COREARCH_AARCH64_PREFETCH_WRAPPER_H
#define RUSTLANG_COREARCH_AARCH64_PREFETCH_COREARCH_AARCH64_PREFETCH_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchAarch64PrefetchPrefetch(void* p, int32_t rw, int32_t loc, int32_t ty);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_AARCH64_PREFETCH_COREARCH_AARCH64_PREFETCH_WRAPPER_H */
