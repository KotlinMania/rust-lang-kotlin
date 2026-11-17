#ifndef COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H
#define COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX8664XsaveExternXsave64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveExternXrstor64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveExternXsaveopt64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveExternXsavec64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveExternXsaves64(void* p, uint32_t hi, uint32_t lo);
void corearchX8664XsaveExternXrstors64(void* p, uint32_t hi, uint32_t lo);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_XSAVE_X8664_XSAVE_WRAPPER_H */
