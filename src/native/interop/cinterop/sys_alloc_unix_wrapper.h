#ifndef STD_SYS_ALLOC_UNIX_SYS_ALLOC_UNIX_WRAPPER_H
#define STD_SYS_ALLOC_UNIX_SYS_ALLOC_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void stdSysAllocUnixFree(void* ptr);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_ALLOC_UNIX_SYS_ALLOC_UNIX_WRAPPER_H */
