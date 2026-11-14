#ifndef COREARCH_X86_AVX_X86_AVX_WRAPPER_H
#define COREARCH_X86_AVX_X86_AVX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86AvxVzeroall(void);
void corearchX86AvxVzeroupper(void);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_AVX_X86_AVX_WRAPPER_H */
