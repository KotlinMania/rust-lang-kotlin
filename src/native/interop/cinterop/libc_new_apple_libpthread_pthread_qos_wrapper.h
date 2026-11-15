#ifndef RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_WRAPPER_H
#define RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(void* attr, void* arg1Class, void* priority);
int32_t rustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(void* thread, void* arg1Class, void* priority);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_QOS_WRAPPER_H */
