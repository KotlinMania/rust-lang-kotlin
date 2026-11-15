#ifndef COREARCH_X86_RTM_X86_RTM_WRAPPER_H
#define COREARCH_X86_RTM_X86_RTM_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t corearchX86RtmX86Xbegin(void);
void corearchX86RtmX86Xend(void);
void corearchX86RtmX86Xabort(int8_t imm8);
int32_t corearchX86RtmX86Xtest(void);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_X86_RTM_X86_RTM_WRAPPER_H */
