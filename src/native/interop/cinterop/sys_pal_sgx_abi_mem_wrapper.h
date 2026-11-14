#ifndef STD_SYS_PAL_SGX_ABI_MEM_SYS_PAL_SGX_ABI_MEM_WRAPPER_H
#define STD_SYS_PAL_SGX_ABI_MEM_SYS_PAL_SGX_ABI_MEM_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t stdSysPalSgxAbiMemGetENCLAVESIZE(void);
uint64_t stdSysPalSgxAbiMemGetHEAPBASE(void);
uint64_t stdSysPalSgxAbiMemGetHEAPSIZE(void);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_PAL_SGX_ABI_MEM_SYS_PAL_SGX_ABI_MEM_WRAPPER_H */
