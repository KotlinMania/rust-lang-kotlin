#ifndef RUSTLANG_LIBC_NEW_MUSL_SYS_SOCKET_LIBC_NEW_MUSL_SYS_SOCKET_WRAPPER_H
#define RUSTLANG_LIBC_NEW_MUSL_SYS_SOCKET_LIBC_NEW_MUSL_SYS_SOCKET_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewMuslSysSocketSendmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, uint32_t flags);
int32_t rustlangLibcNewMuslSysSocketRecvmmsg(int32_t sockfd, void* msgvec, uint32_t vlen, uint32_t flags, void* timeout);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_MUSL_SYS_SOCKET_LIBC_NEW_MUSL_SYS_SOCKET_WRAPPER_H */
