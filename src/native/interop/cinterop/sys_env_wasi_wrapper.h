#ifndef STD_SYS_ENV_WASI_SYS_ENV_WASI_WRAPPER_H
#define STD_SYS_ENV_WASI_SYS_ENV_WASI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* stdSysEnvWasiWasilibcGetEnviron(void);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_ENV_WASI_SYS_ENV_WASI_WRAPPER_H */
