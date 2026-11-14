#ifndef COREARCH_X86_RDTSC_X86_RDTSC_WRAPPER_H
#define COREARCH_X86_RDTSC_X86_RDTSC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t corearchX86RdtscRdtsc(void);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_RDTSC_X86_RDTSC_WRAPPER_H */
