#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_alloc_windows_wrapper.h"
#include <errno.h>
#ifdef _WIN32
#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN
#endif
#include <windows.h>
#else
#include <dlfcn.h>
#ifndef RTLD_DEFAULT
#define RTLD_DEFAULT ((void*)0)
#endif
#endif

void* stdSysAllocWindowsGetProcessHeap(void) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetProcessHeap");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return NULL;
#else
    errno = ENOSYS;
    return NULL;
#endif
}

void* stdSysAllocWindowsHeapAlloc(void* hheap, uint32_t dwflags, uint64_t dwbytes) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapAlloc");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hheap, dwflags, dwbytes);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hheap;
    (void)dwflags;
    (void)dwbytes;
    errno = ENOSYS;
    return NULL;
#endif
}

void* stdSysAllocWindowsHeapReAlloc(void* hheap, uint32_t dwflags, void* lpmem, uint64_t dwbytes) {
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapReAlloc");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hheap, dwflags, lpmem, dwbytes);
    }
    errno = ENOSYS;
    return NULL;
#else
    (void)hheap;
    (void)dwflags;
    (void)lpmem;
    (void)dwbytes;
    errno = ENOSYS;
    return NULL;
#endif
}

int32_t stdSysAllocWindowsHeapFree(void* hheap, uint32_t dwflags, void* lpmem) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapFree");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(hheap, dwflags, lpmem);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)hheap;
    (void)dwflags;
    (void)lpmem;
    errno = ENOSYS;
    return -1;
#endif
}

