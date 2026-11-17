#ifndef COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H
#define COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86XsaveExternXsave(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveExternXrstor(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveExternXsetbv(uint32_t v, uint32_t hi, uint32_t lo);
int64_t corearchX86XsaveExternXgetbv(uint32_t v);
void corearchX86XsaveExternXsaveopt(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveExternXsavec(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveExternXsaves(void* p, uint32_t hi, uint32_t lo);
void corearchX86XsaveExternXrstors(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_XSAVE_X86_XSAVE_WRAPPER_H */
