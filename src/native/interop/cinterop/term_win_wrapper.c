#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "term_win_wrapper.h"
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

int32_t testTermWinSetConsoleTextAttribute(void* handle, uint16_t attr) {
    typedef int32_t (*km_fn_t)(void*, uint16_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "SetConsoleTextAttribute");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_SetConsoleTextAttribute");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "SetConsoleTextAttribute");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_SetConsoleTextAttribute");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "SetConsoleTextAttribute");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "SetConsoleTextAttribute");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "SetConsoleTextAttribute");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(handle, attr);
    }
    errno = ENOSYS;
    return -1;
}

void* testTermWinGetStdHandle(uint32_t which) {
    typedef void* (*km_fn_t)(uint32_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "GetStdHandle");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_GetStdHandle");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "GetStdHandle");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_GetStdHandle");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "GetStdHandle");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetStdHandle");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetStdHandle");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(which);
    }
    errno = ENOSYS;
    return NULL;
}

int32_t testTermWinGetConsoleScreenBufferInfo(void* handle, void* info) {
    typedef int32_t (*km_fn_t)(void*, void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "GetConsoleScreenBufferInfo");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_GetConsoleScreenBufferInfo");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "GetConsoleScreenBufferInfo");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_GetConsoleScreenBufferInfo");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "GetConsoleScreenBufferInfo");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("/usr/lib/libkernel32.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetConsoleScreenBufferInfo");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_kernel32 = NULL;
            static bool km_h_kernel32_init = false;
            if (!km_h_kernel32_init) {
                km_h_kernel32_init = true;
                km_h_kernel32 = dlopen("libkernel32.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_kernel32 != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_kernel32, "GetConsoleScreenBufferInfo");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(handle, info);
    }
    errno = ENOSYS;
    return -1;
}

