#ifndef RUSTLANG_LIBC_NEW_BIONICLIBC_SYS_SOCKET_LIBC_NEW_BIONICLIBC_SYS_SOCKET_WRAPPER_H
#define RUSTLANG_LIBC_NEW_BIONICLIBC_SYS_SOCKET_LIBC_NEW_BIONICLIBC_SYS_SOCKET_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewBioniclibcSysSocketRecvmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, int32_t flags, void* timeout);
int32_t rustlangLibcNewBioniclibcSysSocketSendmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, int32_t flags);
int64_t rustlangLibcNewBioniclibcSysSocketRecvfrom(int32_t arg0Socket, void* buf, uint64_t len, int32_t flags, void* addr, void* addrlen);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_BIONICLIBC_SYS_SOCKET_LIBC_NEW_BIONICLIBC_SYS_SOCKET_WRAPPER_H */
