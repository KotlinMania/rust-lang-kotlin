#ifndef RUSTLANG_STD_SYS_ALLOC_UNIX_STD_SYS_ALLOC_UNIX_WRAPPER_H
#define RUSTLANG_STD_SYS_ALLOC_UNIX_STD_SYS_ALLOC_UNIX_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangStdSysAllocUnixFree(void* ptr);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_ALLOC_UNIX_STD_SYS_ALLOC_UNIX_WRAPPER_H */
