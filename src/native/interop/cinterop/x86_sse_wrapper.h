#ifndef COREARCH_X86_SSE_X86_SSE_WRAPPER_H
#define COREARCH_X86_SSE_X86_SSE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86SseSfence(void);
void corearchX86SseStmxcsr(void* p);
void corearchX86SseLdmxcsr(void* p);
void corearchX86SsePrefetch(void* p, int32_t rw, int32_t loc, int32_t ty);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_SSE_X86_SSE_WRAPPER_H */
