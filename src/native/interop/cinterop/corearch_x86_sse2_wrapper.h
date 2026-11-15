#ifndef RUSTLANG_COREARCH_X86_SSE2_COREARCH_X86_SSE2_WRAPPER_H
#define RUSTLANG_COREARCH_X86_SSE2_COREARCH_X86_SSE2_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX86Sse2Pause(void);
void rustlangCorearchX86Sse2Clflush(void* p);
void rustlangCorearchX86Sse2Lfence(void);
void rustlangCorearchX86Sse2Mfence(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_SSE2_COREARCH_X86_SSE2_WRAPPER_H */
