#ifndef RUSTLANG_STD_SYS_ALLOC_WINDOWS_STD_SYS_ALLOC_WINDOWS_WRAPPER_H
#define RUSTLANG_STD_SYS_ALLOC_WINDOWS_STD_SYS_ALLOC_WINDOWS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* rustlangStdSysAllocWindowsGetProcessHeap(void);
void* rustlangStdSysAllocWindowsHeapAlloc(void* hheap, uint32_t dwflags, uint64_t dwbytes);
void* rustlangStdSysAllocWindowsHeapReAlloc(void* hheap, uint32_t dwflags, void* lpmem, uint64_t dwbytes);
int32_t rustlangStdSysAllocWindowsHeapFree(void* hheap, uint32_t dwflags, void* lpmem);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_STD_SYS_ALLOC_WINDOWS_STD_SYS_ALLOC_WINDOWS_WRAPPER_H */
