#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "new_helenos_stdlib_wrapper.h"
#include <errno.h>
#include <stdlib.h>
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

void* libcNewHelenosStdlibMalloc(uint64_t size) {
    return (void*)malloc(size);
}

void* libcNewHelenosStdlibCalloc(uint64_t nmemb, uint64_t size) {
    return (void*)calloc(nmemb, size);
}

void* libcNewHelenosStdlibRealloc(void* addr, uint64_t size) {
    return (void*)realloc(addr, size);
}

void libcNewHelenosStdlibFree(void* addr) {
    free(addr);
}

void* libcNewHelenosStdlibMemalign(uint64_t align, uint64_t size) {
    typedef void* (*km_fn_t)(uint64_t, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "memalign");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_memalign");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "memalign");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_memalign");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "memalign");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(align, size);
    }
    errno = ENOSYS;
    return NULL;
}

int32_t libcNewHelenosStdlibRand(void) {
    return (int32_t)rand();
}

void libcNewHelenosStdlibSrand(uint32_t seed) {
    srand(seed);
}

void libcNewHelenosStdlibExit(int32_t code) {
    exit(code);
}

void libcNewHelenosStdlibAbort(void) {
    abort();
}

char* libcNewHelenosStdlibGetenv(const char* env) {
    return (char*)getenv(env);
}

