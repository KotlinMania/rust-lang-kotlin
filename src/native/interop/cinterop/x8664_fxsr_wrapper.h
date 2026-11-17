#ifndef COREARCH_X8664_FXSR_X8664_FXSR_WRAPPER_H
#define COREARCH_X8664_FXSR_X8664_FXSR_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX8664FxsrExternFxsave64(void* p);
void corearchX8664FxsrExternFxrstor64(void* p);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X8664_FXSR_X8664_FXSR_WRAPPER_H */
