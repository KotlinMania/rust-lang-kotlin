#ifndef COREARCH_X86_SSE2_X86_SSE2_WRAPPER_H
#define COREARCH_X86_SSE2_X86_SSE2_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86Sse2Pause(void);
void corearchX86Sse2Clflush(void* p);
void corearchX86Sse2Lfence(void);
void corearchX86Sse2Mfence(void);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_SSE2_X86_SSE2_WRAPPER_H */
