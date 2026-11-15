#ifndef RUSTLANG_COREARCH_X86_SSE_COREARCH_X86_SSE_WRAPPER_H
#define RUSTLANG_COREARCH_X86_SSE_COREARCH_X86_SSE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX86SseSfence(void);
void rustlangCorearchX86SseStmxcsr(void* p);
void rustlangCorearchX86SseLdmxcsr(void* p);
void rustlangCorearchX86SsePrefetch(void* p, int32_t rw, int32_t loc, int32_t ty);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_SSE_COREARCH_X86_SSE_WRAPPER_H */
