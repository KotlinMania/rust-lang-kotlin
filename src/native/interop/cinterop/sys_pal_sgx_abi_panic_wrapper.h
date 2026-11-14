#ifndef STD_SYS_PAL_SGX_ABI_PANIC_SYS_PAL_SGX_ABI_PANIC_WRAPPER_H
#define STD_SYS_PAL_SGX_ABI_PANIC_SYS_PAL_SGX_ABI_PANIC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint8_t stdSysPalSgxAbiPanicGetDEBUG(void);
void* stdSysPalSgxAbiPanicTakeDebugPanicBufPtr(void);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_PAL_SGX_ABI_PANIC_SYS_PAL_SGX_ABI_PANIC_WRAPPER_H */
