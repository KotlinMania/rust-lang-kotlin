#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_pal_sgx_abi_mem_wrapper.h"
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

uint64_t stdSysPalSgxAbiMemGetENCLAVESIZE(void) {
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "ENCLAVE_SIZE");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_ENCLAVE_SIZE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "ENCLAVE_SIZE");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_ENCLAVE_SIZE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "ENCLAVE_SIZE");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

uint64_t stdSysPalSgxAbiMemGetHEAPBASE(void) {
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "HEAP_BASE");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_HEAP_BASE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "HEAP_BASE");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_HEAP_BASE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "HEAP_BASE");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

uint64_t stdSysPalSgxAbiMemGetHEAPSIZE(void) {
    static uint64_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "HEAP_SIZE");
        if (km_sym_ptr == NULL && km_h_msvcrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_msvcrt, "_HEAP_SIZE");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "HEAP_SIZE");
            if (km_sym_ptr == NULL && km_h_ucrt != NULL) km_sym_ptr = (uint64_t*)(void*)GetProcAddress(km_h_ucrt, "_HEAP_SIZE");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint64_t*)dlsym(RTLD_DEFAULT, "HEAP_SIZE");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

