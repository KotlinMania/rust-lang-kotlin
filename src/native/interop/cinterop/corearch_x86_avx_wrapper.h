#ifndef RUSTLANG_COREARCH_X86_AVX_COREARCH_X86_AVX_WRAPPER_H
#define RUSTLANG_COREARCH_X86_AVX_COREARCH_X86_AVX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX86AvxVzeroall(void);
void rustlangCorearchX86AvxVzeroupper(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_AVX_COREARCH_X86_AVX_WRAPPER_H */
