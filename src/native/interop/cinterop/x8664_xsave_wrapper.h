#ifndef COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H
#define COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX8664XsaveXsave64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveXrstor64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveXsaveopt64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveXsavec64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveXsaves64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveXrstors64(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H */
