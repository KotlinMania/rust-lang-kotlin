#ifndef RUSTLANG_STD_SYS_THREADLOCAL_KEY_UNIX_STD_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H
#define RUSTLANG_STD_SYS_THREADLOCAL_KEY_UNIX_STD_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(void* key, void* destructor);
void* rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(uint32_t key);
int32_t rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(uint32_t key, void* value);
int32_t rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(uint32_t key);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_THREADLOCAL_KEY_UNIX_STD_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H */
