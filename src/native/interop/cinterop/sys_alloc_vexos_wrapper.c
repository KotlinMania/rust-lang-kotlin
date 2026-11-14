#ifndef _GNU_SOURCE
#define _GNU_SOURCE
#endif
#include "sys_alloc_vexos_wrapper.h"
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

uint8_t stdSysAllocVexosGetHEAPSTART(void) {
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_start");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_start");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_start");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

void stdSysAllocVexosSetHEAPSTART(uint8_t km_val) {
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_start");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_start");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_start");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = km_val;
    }
}

uint8_t stdSysAllocVexosGetHEAPEND(void) {
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_end");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_end");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_end");
    }
#endif
    if (km_sym_ptr != NULL) {
        return *km_sym_ptr;
    }
    return 0;
}

void stdSysAllocVexosSetHEAPEND(uint8_t km_val) {
    static uint8_t* km_sym_ptr = NULL;
#ifdef _WIN32
    if (km_sym_ptr == NULL) {
        HMODULE km_h_msvcrt = GetModuleHandleA("msvcrt.dll");
        if (km_h_msvcrt == NULL) km_h_msvcrt = LoadLibraryA("msvcrt.dll");
        if (km_h_msvcrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_msvcrt, "__heap_end");
        if (km_sym_ptr == NULL) {
            HMODULE km_h_ucrt = GetModuleHandleA("ucrtbase.dll");
            if (km_h_ucrt == NULL) km_h_ucrt = LoadLibraryA("ucrtbase.dll");
            if (km_h_ucrt != NULL) km_sym_ptr = (uint8_t*)(void*)GetProcAddress(km_h_ucrt, "__heap_end");
        }
    }
#else
    if (km_sym_ptr == NULL) {
        km_sym_ptr = (uint8_t*)dlsym(RTLD_DEFAULT, "__heap_end");
    }
#endif
    if (km_sym_ptr != NULL) {
        *km_sym_ptr = km_val;
    }
}

