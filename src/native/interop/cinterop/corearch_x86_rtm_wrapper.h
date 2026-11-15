#ifndef RUSTLANG_COREARCH_X86_RTM_COREARCH_X86_RTM_WRAPPER_H
#define RUSTLANG_COREARCH_X86_RTM_COREARCH_X86_RTM_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangCorearchX86RtmX86Xbegin(void);
void rustlangCorearchX86RtmX86Xend(void);
void rustlangCorearchX86RtmX86Xabort(int8_t imm8);
int32_t rustlangCorearchX86RtmX86Xtest(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_X86_RTM_COREARCH_X86_RTM_WRAPPER_H */
