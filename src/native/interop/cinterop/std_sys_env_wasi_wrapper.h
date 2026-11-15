#ifndef RUSTLANG_STD_SYS_ENV_WASI_STD_SYS_ENV_WASI_WRAPPER_H
#define RUSTLANG_STD_SYS_ENV_WASI_STD_SYS_ENV_WASI_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangStdSysEnvWasiWasilibcGetEnviron(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_ENV_WASI_STD_SYS_ENV_WASI_WRAPPER_H */
