#ifndef RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_WRAPPER_H
#define RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionHookInstall(void* hook);
int32_t rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionSetspecificNp(void* thread, uint64_t key, void* value);
void* rustlangLibcNewAppleLibpthreadPthreadIntrospectionPthreadIntrospectionGetspecificNp(void* thread, uint64_t key);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_LIBC_NEW_APPLE_LIBPTHREAD_PTHREAD_INTROSPECTION_WRAPPER_H */
