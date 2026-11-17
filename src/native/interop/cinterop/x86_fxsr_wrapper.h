#ifndef COREARCH_X86_FXSR_X86_FXSR_WRAPPER_H
#define COREARCH_X86_FXSR_X86_FXSR_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchX86FxsrExternFxsave(void* p);
void corearchX86FxsrExternFxrstor(void* p);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_FXSR_X86_FXSR_WRAPPER_H */
