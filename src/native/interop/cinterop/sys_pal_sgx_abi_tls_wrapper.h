#ifndef STD_SYS_PAL_SGX_ABI_TLS_SYS_PAL_SGX_ABI_TLS_WRAPPER_H
#define STD_SYS_PAL_SGX_ABI_TLS_SYS_PAL_SGX_ABI_TLS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* stdSysPalSgxAbiTlsGetTlsPtr(void);
void stdSysPalSgxAbiTlsSetTlsPtr(void* tls);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_PAL_SGX_ABI_TLS_SYS_PAL_SGX_ABI_TLS_WRAPPER_H */
