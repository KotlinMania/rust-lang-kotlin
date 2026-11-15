#ifndef STD_SYS_ALLOC_WINDOWS_SYS_ALLOC_WINDOWS_WRAPPER_H
#define STD_SYS_ALLOC_WINDOWS_SYS_ALLOC_WINDOWS_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void* stdSysAllocWindowsGetProcessHeap(void);
void* stdSysAllocWindowsHeapAlloc(void* hheap, uint32_t dwflags, uint64_t dwbytes);
void* stdSysAllocWindowsHeapReAlloc(void* hheap, uint32_t dwflags, void* lpmem, uint64_t dwbytes);
int32_t stdSysAllocWindowsHeapFree(void* hheap, uint32_t dwflags, void* lpmem);

#ifdef __cplusplus
}
#endif

#endif /* STD_SYS_ALLOC_WINDOWS_SYS_ALLOC_WINDOWS_WRAPPER_H */
