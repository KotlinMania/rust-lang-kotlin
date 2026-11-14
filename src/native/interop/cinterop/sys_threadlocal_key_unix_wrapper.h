#ifndef STD_SYS_THREADLOCAL_KEY_UNIX_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H
#define STD_SYS_THREADLOCAL_KEY_UNIX_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

int32_t stdSysThreadlocalKeyUnixPthreadKeyCreate(void* key, void* destructor);
void* stdSysThreadlocalKeyUnixPthreadGetspecific(uint32_t key);
int32_t stdSysThreadlocalKeyUnixPthreadSetspecific(uint32_t key, void* value);
int32_t stdSysThreadlocalKeyUnixPthreadKeyDelete(uint32_t key);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_THREADLOCAL_KEY_UNIX_SYS_THREADLOCAL_KEY_UNIX_WRAPPER_H */
