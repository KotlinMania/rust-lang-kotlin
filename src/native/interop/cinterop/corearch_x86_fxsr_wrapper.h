#ifndef RUSTLANG_COREARCH_X86_FXSR_COREARCH_X86_FXSR_WRAPPER_H
#define RUSTLANG_COREARCH_X86_FXSR_COREARCH_X86_FXSR_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchX86FxsrFxsave(void* p);
void rustlangCorearchX86FxsrFxrstor(void* p);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_FXSR_COREARCH_X86_FXSR_WRAPPER_H */
