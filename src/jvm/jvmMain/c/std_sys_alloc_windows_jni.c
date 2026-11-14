#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include <jni.h>
#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>
#include <stdlib.h>
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

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_windows_SysAllocWindowsJniBridge_stdSysAllocWindowsGetProcessHeap(JNIEnv* km_env, jclass km_clazz) {
    (void)km_env;
    (void)km_clazz;
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "GetProcessHeap");
    }
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr();
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
#else
    errno = ENOSYS;
    void* km_res = NULL;
#endif
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_windows_SysAllocWindowsJniBridge_stdSysAllocWindowsHeapAlloc(JNIEnv* km_env, jclass km_clazz, jlong hheap, jint dwflags, jlong dwbytes) {
    (void)km_env;
    (void)km_clazz;
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapAlloc");
    }
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)hheap, (uint32_t)dwflags, (uint64_t)dwbytes);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
#else
    (void)hheap;
    (void)dwflags;
    (void)dwbytes;
    errno = ENOSYS;
    void* km_res = NULL;
#endif
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_windows_SysAllocWindowsJniBridge_stdSysAllocWindowsHeapReAlloc(JNIEnv* km_env, jclass km_clazz, jlong hheap, jint dwflags, jlong lpmem, jlong dwbytes) {
    (void)km_env;
    (void)km_clazz;
#ifdef _WIN32
    typedef void* (__stdcall *km_fn_t)(void*, uint32_t, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapReAlloc");
    }
    void* km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)hheap, (uint32_t)dwflags, (void*)(uintptr_t)lpmem, (uint64_t)dwbytes);
    } else {
        errno = ENOSYS;
        km_res = NULL;
    }
#else
    (void)hheap;
    (void)dwflags;
    (void)lpmem;
    (void)dwbytes;
    errno = ENOSYS;
    void* km_res = NULL;
#endif
    return (jlong)(uintptr_t)km_res;
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_rustlang_std_sys_alloc_windows_SysAllocWindowsJniBridge_stdSysAllocWindowsHeapFree(JNIEnv* km_env, jclass km_clazz, jlong hheap, jint dwflags, jlong lpmem) {
    (void)km_env;
    (void)km_clazz;
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("kernel32.dll");
        if (km_h_mod != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "HeapFree");
    }
    int32_t km_res;
    if (km_fn_ptr != NULL) {
        km_res = km_fn_ptr((void*)(uintptr_t)hheap, (uint32_t)dwflags, (void*)(uintptr_t)lpmem);
    } else {
        errno = ENOSYS;
        km_res = -1;
    }
#else
    (void)hheap;
    (void)dwflags;
    (void)lpmem;
    errno = ENOSYS;
    int32_t km_res = -1;
#endif
    return (jint)km_res;
}

