#ifndef RUSTLANG_COREARCH_X86_XSAVE_COREARCH_X86_XSAVE_WRAPPER_H
#define RUSTLANG_COREARCH_X86_XSAVE_COREARCH_X86_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX86XsaveXsave(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX86XsaveXrstor(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX86XsaveXsetbv(uint32_t v, uint32_t hi, uint32_t lo);
int64_t rustlangCorearchX86XsaveXgetbv(uint32_t v);
void rustlangCorearchX86XsaveXsaveopt(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX86XsaveXsavec(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX86XsaveXsaves(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX86XsaveXrstors(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_XSAVE_COREARCH_X86_XSAVE_WRAPPER_H */
