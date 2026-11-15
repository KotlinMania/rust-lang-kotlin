#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "core_ffi_wrapper.h"
#include <errno.h>
#include <string.h>
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

void* rustlangCoreFfiMemcpy(void* dest, void* src, uint64_t n) {
    return (void*)memcpy(dest, src, n);
}

void* rustlangCoreFfiMemmove(void* dest, void* src, uint64_t n) {
    return (void*)memmove(dest, src, n);
}

void* rustlangCoreFfiMemset(void* s, int32_t c, uint64_t n) {
    return (void*)memset(s, c, n);
}

int32_t rustlangCoreFfiMemcmp(void* s1, void* s2, uint64_t n) {
    return (int32_t)memcmp(s1, s2, n);
}

int32_t rustlangCoreFfiBcmp(void* s1, void* s2, uint64_t n) {
    typedef int32_t (*km_fn_t)(void*, void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "bcmp");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_bcmp");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "bcmp");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_bcmp");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "bcmp");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(s1, s2, n);
    }
    errno = ENOSYS;
    return -1;
}

uint64_t rustlangCoreFfiStrlen(const char* s) {
    return (uint64_t)strlen(s);
}

