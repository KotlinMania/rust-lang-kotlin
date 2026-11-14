#ifndef LIBC_NEW_HELENOS_INET_TCP_NEW_HELENOS_INET_TCP_WRAPPER_H
#define LIBC_NEW_HELENOS_INET_TCP_NEW_HELENOS_INET_TCP_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t libcNewHelenosInetTcpTcpCreate(void* tcp);
void libcNewHelenosInetTcpTcpDestroy(void* tcp);
int32_t libcNewHelenosInetTcpTcpConnCreate(void* tcp, void* epp, void* callbacks, void* arg, void* conn);
void libcNewHelenosInetTcpTcpConnDestroy(void* conn);
int32_t libcNewHelenosInetTcpTcpListenerCreate(void* tcp, void* ep, void* listenCallbacks, void* listenArg, void* connCallbacks, void* connArg, void* listener);
void libcNewHelenosInetTcpTcpListenerDestroy(void* listener);
void* libcNewHelenosInetTcpTcpConnUserptr(void* conn);
void* libcNewHelenosInetTcpTcpListenerUserptr(void* listener);
int32_t libcNewHelenosInetTcpTcpConnWaitConnected(void* conn);
int32_t libcNewHelenosInetTcpTcpConnSend(void* conn, void* buf, uint64_t len);
int32_t libcNewHelenosInetTcpTcpConnSendFin(void* conn);
int32_t libcNewHelenosInetTcpTcpConnPush(void* conn);
int32_t libcNewHelenosInetTcpTcpConnReset(void* conn);
int32_t libcNewHelenosInetTcpTcpConnRecv(void* conn, void* buf, uint64_t bufsize, void* receivedLen);
int32_t libcNewHelenosInetTcpTcpConnRecvWait(void* conn, void* buf, uint64_t bufsize, void* receivedLen);

#ifdef __cplusplus
}
#endif

#endif /* LIBC_NEW_HELENOS_INET_TCP_NEW_HELENOS_INET_TCP_WRAPPER_H */
