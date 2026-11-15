#ifndef RUSTLANG_COREARCH_X86_AVX512F_COREARCH_X86_AVX512F_WRAPPER_H
#define RUSTLANG_COREARCH_X86_AVX512F_COREARCH_X86_AVX512F_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

float rustlangCorearchX86Avx512fVfmaddssround(float a, float b, float c, int32_t rounding);
double rustlangCorearchX86Avx512fVfmaddsdround(double a, double b, double c, int32_t rounding);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_AVX512F_COREARCH_X86_AVX512F_WRAPPER_H */
