#ifndef RUSTLANG_COREARCH_X8664_FXSR_COREARCH_X8664_FXSR_WRAPPER_H
#define RUSTLANG_COREARCH_X8664_FXSR_COREARCH_X8664_FXSR_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX8664FxsrFxsave64(void* p);
void rustlangCorearchX8664FxsrFxrstor64(void* p);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X8664_FXSR_COREARCH_X8664_FXSR_WRAPPER_H */
