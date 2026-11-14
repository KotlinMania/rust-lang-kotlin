#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_pal_sgx_abi_panic_wrapper.h"
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

uint8_t stdSysPalSgxAbiPanicGetDEBUG(void) {
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "DEBUG");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "_DEBUG");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "DEBUG");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "_DEBUG");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "DEBUG");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

void* stdSysPalSgxAbiPanicTakeDebugPanicBufPtr(void) {
    typedef void* (*km_fn_t)(void);
    static km_fn_t km_fn_ptr = NULL;
#ifdef _WIN32
    if (km_fn_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "take_debug_panic_buf_ptr");
        if (km_fn_ptr == NULL && km_h_msvcrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_msvcrt, "_take_debug_panic_buf_ptr");
        if (km_fn_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "take_debug_panic_buf_ptr");
            if (km_fn_ptr == NULL && km_h_ucrt != NULL) km_fn_ptr = (km_fn_t)(void*)GetProcAddress(km_h_ucrt, "_take_debug_panic_buf_ptr");
        }
    }
#else
    if (km_fn_ptr == NULL) {
        km_fn_ptr = (km_fn_t)dlsym(RTLD_DEFAULT, "take_debug_panic_buf_ptr");
    }
#endif
    if (km_fn_ptr != NULL) {
        return km_fn_ptr();
    }
    errno = ENOSYS;
    return NULL;
}

