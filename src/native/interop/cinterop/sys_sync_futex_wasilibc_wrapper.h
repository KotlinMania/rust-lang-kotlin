#ifndef STD_SYS_SYNC_FUTEX_WASILIBC_SYS_SYNC_FUTEX_WASILIBC_WRAPPER_H
#define STD_SYS_SYNC_FUTEX_WASILIBC_SYS_SYNC_FUTEX_WASILIBC_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t stdSysSyncFutexWasilibcWasilibcFutexWait(void* addr, int32_t val, int32_t clock, void* at, uint32_t flags);
int32_t stdSysSyncFutexWasilibcWasilibcFutexWake(void* addr, int32_t count, uint32_t flags);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_SYNC_FUTEX_WASILIBC_SYS_SYNC_FUTEX_WASILIBC_WRAPPER_H */
