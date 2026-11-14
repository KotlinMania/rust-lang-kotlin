#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_pal_windows_c_wrapper.h"
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

int32_t stdSysPalWindowsCProcessPrng(void* pbdata, uint64_t cbdata) {
    typedef int32_t (*km_fn_t)(void*, uint64_t);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "ProcessPrng");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_ProcessPrng");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "ProcessPrng");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_ProcessPrng");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "ProcessPrng");
#ifdef __APPLE__
        if (km_fn_ptr == NULL) {
            static void* km_h_bcryptprimitives = NULL;
            static bool km_h_bcryptprimitives_init = false;
            if (!km_h_bcryptprimitives_init) {
                km_h_bcryptprimitives_init = true;
                km_h_bcryptprimitives = dlopen("/usr/lib/libbcryptprimitives.dylib", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_bcryptprimitives != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_bcryptprimitives, "ProcessPrng");
        }
#elif defined(__linux__) && !defined(__ANDROID__)
        if (km_fn_ptr == NULL) {
            static void* km_h_bcryptprimitives = NULL;
            static bool km_h_bcryptprimitives_init = false;
            if (!km_h_bcryptprimitives_init) {
                km_h_bcryptprimitives_init = true;
                km_h_bcryptprimitives = dlopen("libbcryptprimitives.so", RTLD_LAZY | RTLD_LOCAL);
            }
            if (km_h_bcryptprimitives != NULL) km_fn_ptr = (km_fn_t)dlsym(km_h_bcryptprimitives, "ProcessPrng");
        }
#endif
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(pbdata, cbdata);
    }
    errno = ENOSYS;
    return -1;
}

int32_t stdSysPalWindowsCNtCreateNamedPipeFile(void* filehandle, uint32_t desiredaccess, void* objectattributes, void* iostatusblock, uint32_t shareaccess, uint32_t createdisposition, uint32_t createoptions, uint32_t namedpipetype, uint32_t readmode, uint32_t completionmode, uint32_t maximuminstances, uint32_t inboundquota, uint32_t outboundquota, void* defaulttimeout) {
#ifdef _WIN32
    typedef int32_t (__stdcall *km_fn_t)(void*, uint32_t, void*, void*, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, uint32_t, void*);
    static km_fn_t km_fn_ptr = NULL;
    if (km_fn_ptr == NULL) {
        HMODULE km_h_mod = LoadLibraryA("ntdll.dll");
        if (km_h_mod != NULL) {
            km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_mod, "NtCreateNamedPipeFile");
        }
    }
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(filehandle, desiredaccess, objectattributes, iostatusblock, shareaccess, createdisposition, createoptions, namedpipetype, readmode, completionmode, maximuminstances, inboundquota, outboundquota, defaulttimeout);
    }
    errno = ENOSYS;
    return -1;
#else
    (void)filehandle;
    (void)desiredaccess;
    (void)objectattributes;
    (void)iostatusblock;
    (void)shareaccess;
    (void)createdisposition;
    (void)createoptions;
    (void)namedpipetype;
    (void)readmode;
    (void)completionmode;
    (void)maximuminstances;
    (void)inboundquota;
    (void)outboundquota;
    (void)defaulttimeout;
    errno = ENOSYS;
    return -1;
#endif
}

int32_t stdSysPalWindowsCAtexit(void* cb) {
    if (cb == NULL) {
        errno = EINVAL;
        return -1;
    }
#ifdef __ANDROID__
    return (int32_t)atexit((void (*)(void))(uintptr_t)cb);
#else
    typedef int32_t (*km_fn_t)(void*);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "atexit");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_atexit");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "atexit");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_atexit");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "atexit");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr(cb);
    }
    errno = ENOSYS;
    return -1;
#endif
}

