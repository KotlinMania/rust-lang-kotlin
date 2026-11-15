#ifndef COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H
#define COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86XsaveXsave(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveXrstor(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveXsetbv(uint32_t v, uint32_t hi, uint32_t lo);
int64_t corearchX86XsaveXgetbv(uint32_t v);
void corearchX86XsaveXsaveopt(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveXsavec(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveXsaves(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveXrstors(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H */
