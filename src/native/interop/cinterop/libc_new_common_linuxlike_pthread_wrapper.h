#ifndef RUSTLANG_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_WRAPPER_H
#define RUSTLANG_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(void* thread, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(void* native, void* attr);
int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(void* thread, char* name, uint64_t len);
int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(void* thread);
int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(void* thread, uint64_t cpusetsize, void* cpuset);
int32_t rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(void* thread, const char* name);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_LIBC_NEW_COMMON_LINUXLIKE_PTHREAD_WRAPPER_H */
