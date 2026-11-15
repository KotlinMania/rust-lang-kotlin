#ifndef COREARCH_AARCH64_PREFETCH_AARCH64_PREFETCH_WRAPPER_H
#define COREARCH_AARCH64_PREFETCH_AARCH64_PREFETCH_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchAarch64PrefetchPrefetch(void* p, int32_t rw, int32_t loc, int32_t ty);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_AARCH64_PREFETCH_AARCH64_PREFETCH_WRAPPER_H */
