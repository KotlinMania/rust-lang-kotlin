#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "std_sys_io_error_unix_wrapper.h"
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

int32_t rustlangStdSysIoErrorUnixGetLIBCERRNO(void) {
    static int32_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "errno");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "_errno");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "errno");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "_errno");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (int32_t*)dlsym(RTLD_DEFAULT, "errno");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return -1;
}

void rustlangStdSysIoErrorUnixSetLIBCERRNO(int32_t km_val) {
    static int32_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "errno");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_msvcrt, "_errno");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "errno");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (int32_t*)(void*)GetProcAddress(km_h_ucrt, "_errno");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (int32_t*)dlsym(RTLD_DEFAULT, "errno");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = km_val;
    }
}

void* rustlangStdSysIoErrorUnixErrnoLocation(void) {
    typedef void* (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "errno_location");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_errno_location");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "errno_location");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_errno_location");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "errno_location");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return NULL;
}

