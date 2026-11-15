#ifndef RUSTLANG_COREARCH_X8664_XSAVE_COREARCH_X8664_XSAVE_WRAPPER_H
#define RUSTLANG_COREARCH_X8664_XSAVE_COREARCH_X8664_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX8664XsaveXsave64(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX8664XsaveXrstor64(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX8664XsaveXsaveopt64(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX8664XsaveXsavec64(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX8664XsaveXsaves64(void* p, uint32_t hi, uint32_t lo);
void rustlangCorearchX8664XsaveXrstors64(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X8664_XSAVE_COREARCH_X8664_XSAVE_WRAPPER_H */
